环境配置
=
Linux环境
-
### 1. jdk1.8，tomcat8.5，MySQL5.7，Redis3.0以上，MyCatServer1.6，Nginx，ftp服务器管理图片
### 2. docker pull puzhengyu/centos 拉取镜像，镜像包含ssh，jdk1.8，tomcat8.5
### 3. docker容器
    3.1 java -version 查看java环境

    3.2 修改mysql密码和允许远程连接

    3.3 systemctl enable tomcat 允许tomcat开机自启动      
  
    3.4 systemctl start tomcat 启动tomcat
        
### 4. 配置主从复制
4.1 vim /etc/my.conf 配置主从环境，看个人需要

    4.1.1 主数据库 my.conf (master)
        log-bin=mysql-bin  开启二进制日志
        binlog-do-db=person_managent  需要同步的数据库，binlog日志记录
        binlog-do-db=student_managent  多个可复制粘贴
        binlog-ignore-db=mysql  不需要同步mysql数据库    
        
    4.1.2 从数据库 my.conf (slave)
        server-id=1  serverId的值不可重复，master根据server-id进行表示，如果一直前一个踢掉
        log-bin=mysql-bin  开启二进制日志
        replicate-do-db=person_managent  要复制的数据库
        replicate-do-db=student_managent  多个可复制粘贴    
        replicat-ignore-db=mysql  不需要复制mysql数据库
        
    4.1.3  1593错误 mysql数据库 server-uuid 不可重复 
        vim /var/lib/mysql/auto.conf  修改server-uuid的值    
        
4.2 主数据库 (slave)

     4.2.1 flush logs;
     
     4.2.2 `show master status\G;`
        file->master_log_file，position->master_log_pos  
        
4.3 从数据库 (slave)

    4.3.1 mysql -u root -p  登录 
    
    4.3.2 stop slave;      
    
    4.3.3 change master to 
        master_host='ip地址', master_port=3306, 
        master_user='root', master_password='root',
        master_log_file='mysql-bin.000014', master_log_pos='1710';
        4.3.3.1 `master_log_file`、`master_log_pos`的值在主数据库得来  
            
    4.3.4 start slave;
    
    4.3.5 show slave status\G;
        Slave_IO_Running YES， Slave_SQL_Running YES 配置成功     
         
4.4 主数据库 (master)

    4.4.1 create table test (
            id int PRIMARY KEY,
            name varchar(30)
          );
            创建test表
            查看主数据库和从数据库如果都有test表，测试成功
            
    4.4.2 drop table test;
            删除test表，主从数据库test表如果都删除了，测试成功
            
### 5. 配置Mycat实现读写分离 (我没有使用分库分表)

5.1 `只读写分离，只需要配置server.xml的用户名、密码、逻辑数据库名，schema.xml的schema标签、dataNode标签、dataHost标签、heartbeat标签、writeHost和readHost标签`
        
5.2 [MyCat压缩包 Linux](https://pan.baidu.com/s/1Yb78CzEr9Fpqlie-Y1J-4w)

5.3 解压:  vim mycat/conf/server.xml  server.xml管理项目连接的用户名，密码，数据库
    <user name='pzy'>标签 pzy是jdbc连接的用户名
    <property name='password'>标签 值是jdbc连接的密码
    <property name='schemas'>标签 值是连接的逻辑数据库名
    <property name='readOnly'>标签 boolean 只读
    默认带的<user name='user'>，不用可以注释或者删除，或者直接更改值使用

5.4 vim mycat/conf/schema.xml  schema.xml管理逻辑课、表、分片规则 
    默认自带的数据可以删除掉
    
    5.4.1 schema标签 划分不同的逻辑数据库
        name属性 逻辑数据名
        checkSQLschema属性 
            true (select * from db.user) Mycat会把语句修改成(select * from user) 避免1146找不到表
                db.user db是自己定义的逻辑数据库名，不是该库名，还会报错，最好别带db名
            false 不检查表明的schema (建议设置false)
        sqlMaxLimit属性
            每条执行的SQL语句一次查询的数据
        dataNode属性 不设置table标签，就必须设置dataNode属性
            绑定某个具体的database，如果定义了这个属性，逻辑库就不能工作在分库分表模式下了，可以用作读写分离和主从切换
            <schema name="personManagent" checkSQLschema="false" sqlMaxLimit="100" dataNode="master1">
            <!—这里不能配置任何逻辑表信息-->
            </schema>
            
        5.4.1.1 table标签定义了逻辑表，所有需要拆分的表都需要在这个标签定义 (我没有使用分库分表)
            name属性 定义逻辑表的表名，名字必须唯一
            dataNode属性 定义这个逻辑板所属的dataNode`(标签)`
            rule属性 用于指定逻辑表使用的规则名字，规则名字在rule.xml定义，必须与tableRule标签name属性值对应
            primaryKey属性 逻辑板对应的真实表的主键
            type属性 定义了逻辑表的类型 全局表和普通表两种类型 全局表->global 普通表->不指定该值为 globla 的所有表
            autoIncrement属性 默认false  true指定这个表有使用自增长主键，mycat才会不抛出分片键找不到的异常 (具体自己可以了解一下)
            needAddLimit属性 指定表是否需要自动的在每个语句加上limit限制，由于使用了分库分表，数据量有时会特别巨大，所以mycat自动的为我们加上limit 100，如果语句有limit，就不会再添加了
            
            5.4.1.1.1 table子标签childTable标签 用于定义E-R分片的子表,通过标签上的属性与父表关联
                如果自己用到了，去百度配置了解吧
                
    5.4.2 dataNode标签 定义MyCat的数据节点，数据分片，一个dataNode加上一个独立的数据分片
        <dataNode name="master1" dataHost="masterHost1" database="person_managent" />
            例子意思 使用名字为masterHost1数据库实例上的person_managent物理数据库，组成一个数据分片，最后使用master1标识这个分片
        name属性 定义数据节点的名字，名字唯一, schema标签会用到这个名字或table标签
        dataHost属性 定义该分片属于哪个数据库实例，属性值引用dataHost标签定义的name属性值
        database属性 定义属性哪个数据库实例
        
    5.4.3 dataHost标签 定义具体的数据库实例，读写分离配置和心跳语句
        name属性 唯一标识dataHost标签，供上层的标签使用 dataNode
        maxCon属性 每个读写实例连接池的最大连接，标签内嵌套的writeHost、readHost标签都会用这个属性的值实例化出连接池的最大连接数
        minCon属性 每个读写连接池的最小连接，初始化连接池的大小
        `balance属性` 负载均衡类型
            `balance='0' 不开启读写分离机制，所有读操作都发送到当前的writeHost上`
            `balance='1' 所有读操作都随机的发送到readHost和writeHost (所有writeHost)`
            `balance='2' 所有读操作都随机的在writeHost、readHost上分发` 
            `balance='3' 所有读操作随机发送到writeHost的readHost上，writeHost不负担读压力 （1.4及其以后版本以后才有）`
        `writeType属性` 负载均衡类型
             `writeType='0' 所有写操作发送到配置的第一个writeHost，第一个挂了切到生存的第二个writeHost，重启已切换后的为准，切换记录在配置文件:dnindex.properties`
             `writeType='1' 所有写操作都随机的发送到配置的writeHost，1.5以后废弃不推荐`
        `dbType属性` 指定连接的数据库类型mysql, JDBC可以连接的数据库:mongodb、oracle、spack等
        `dbDriver属性` 指定后端数据库使用的Driver，可选值native和JDBC
            native 执行二进制的mysql协议
            JDBC 可以连接其他类型的数据库
        `switchType属性`
            `switchType='1' 默认值，自动切换`
            `switchType='-1' 不自动切换`
            `switchType='2' 基于MySQL主从同步的状态决定是否切换,心跳语句为 show slave status
    
    5.4.4 heartbeat标签
        知名用于和后端数据库进行心跳检测的语句，
            例如Mysql可以使用select user()，Oracle可以使用select 1 from dual等
    
    5.4.5 `writeHost标签、readHost标签`       
        指定后端数据库的相关配置给mycat，用于实例化后端连接池
        writeHost指定写实例，readHost指定读实例，可以定义多个writeHost和readHost，但是，如果writeHost指定的后端数据库宕机，那么writeHost绑定的readHost都不将可用
        这个writeHost宕机系统会自动的检测到，并切换到备用的writeHost上去         
        `这两个标签属性相同`
        host属性 标识不同实例，writeHost一般我们使用M1，readHost使用S1 
        url属性 数据库连接地址 
            dbDriver使用native： 127.0.0.1:3306 这种形式 
            dbDriver使用JDBC：jdbc:mysql://localhost:3306/
        user属性 数据库连接用户名    
        password属性 数据库连接密码
        
### 6. Nginx配置负载均衡

        6.1 配置upstream docker_centos {
            server ip地址:port ; 集群服务器ip和项目端口号 (tomcat端口)
            server ip地址:port ;
            server ip地址:port ;
        }
        
        6.2 server { # 可以配置ssl升级http为https, ws升级wss
            listen 80; 客户端访问的端口号
            server_name inmeyu.com; 客户端访问的域名
            
            location / {
                root personManagent/dist;  vue build后的前端项目，页面
                try_files $uri $uri/ /index.html; 解决使用history刷新页面404问题
                index index.html index.html;
            }
            location /images/ {
                root /home/ftpuser;  访问inmeyu.com/images 转到/home/ftpuser/images/路径下的图片
                # alias /home/ftpuser/images/; 是准备的路径 root和alias使用一个就行
                autoindex on; 打开目录浏览功能
            }
            location /api {
                proxy_pass http://docker_centos/chat/api/person/v1/chat ; 映射到docekr_centos下的随机服务器的chat项目的接口路径
                proxy_connect_timeout 5; #宕机检测，设置5秒，5秒不响应切换到另外的服务器进行访问
            }
            location /ws { #配置webSocket映射
                proxy_pass http://docker_centos/chat/ws;
                proxy_set_header Upgrade $http_upgrade ; # http升级带websocket
                peoxy_set_header Connection "upgrade" ; # Upgrade请求
            }
        }
        
### 7. vsftp linux搭建挺简单的，自己百度一个教程，注意几个地方就行了

        7.1 vim /etc/vsftpd/vsfptd.conf 没有的话使用: find / -name 'vsftp*'查找
        
        7.2 ftpClient本地上传图片是没有问题的，部署显示会发现有时成功有时失败，连接超时，解决办法
            7.2.1 ftpClient.enterLocalPassiveMode(); 默认主动模式，设置成被动模式，服务端开启端口，因为客户端防火墙等原因，不可控
            7.2.2 vsftpd.conf文件 设置被动模式端口范围 (别忘了配置安全组规则，放开端口)
                pasv_min_port=30000  最小端口30000
                pasv_max_port=30999  最大端口30999
            7.2.3 ftpClient.changeWorkingDirectory、makeDirectory、storeFile返回false，可能原因
                7.2.3.1 cd /home  切换到ftpuser目录
                    执行 ll命令，查询所属用户是你新建的ftpuser用户吗，有可能是root用户
                    chmod -R 755 /home/ftpuser  分配权限
                    chown -R 用户名 /home/ftpuser 分配所属用户
                7.2.3.2
                    getsebool -a | grep ftp  执行linux命令
                    ftpd_full_access和tftp_home_dir 如果是off，代表没有开启外网的访问
                        setsebool -P ftpd_full_access on  执行linux命令
                        setsebool -P tftp_home_dir on  执行linux命令
                7.2.3.3 setenforce: SELinux is disabled 解决办法
                            vim /etc/selinux/config 
                            更改SELINUX=1 必须重启linux，要不然没办法立刻开启selinux
                7.2.3.4 systemctl restart vsftpd 重新启动vsftpd服务
                
        7.3 安全组配置 21端口开放   其他的按网上教程来基本没问题    
                
                    
                
### docker build -t 镜像名称 .   镜像名称后面有的点别忘了，执行dockerfile命令

### 文档和压缩包
    
1. [MyCat压缩包 提取码: bw83](https://pan.baidu.com/s/1Yb78CzEr9Fpqlie-Y1J-4w)
2. [Redis4.0 提取码: avlo](https://pan.baidu.com/s/1A9G_AzeCbifqYuNaqIeEdQ)
3. [Linux配置systemctl service步骤 提取码: hson](https://pan.baidu.com/s/17_qWYUhUvi4HJfadWttaug)
4. [Linux搭建redis 提取码: ybjb](https://pan.baidu.com/s/1ba38HrVj6lbc73yLWqblaA)
5. [Linux搭建Nginx 提取码: m315](https://pan.baidu.com/s/1rFNz0p_MYZaKCfyvTO1TYw)
6. [Docker常用命令 提取码: 8swc](https://pan.baidu.com/s/1kb_sd50JIaOXohnQnPVN1w)
7. [Git常用命令 提取码: 0m4z](https://pan.baidu.com/s/1Rog-JbUPVlTY3g0V7BlRWw)



