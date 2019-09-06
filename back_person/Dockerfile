# docket hub的centos镜像开始构建
FROM centos

# 作者蒲政宇, 邮箱xxx@163.com
MAINTAINER puzhengyu 15210108209@163.com

# 将jdk环境包添加到容器中
ADD tools/jdk1.8.0_221 /home/tools/jdk1.8.0_221
ADD .bashrc /root/.bashrc

# 添加tomcat
ADD tools/tomcat8.5.29 /home/tools/tomcat8.5.29
ADD tools/tomcat.service /lib/systemd/system/tomcat.service

# 切换路径
WORKDIR /home/tools/

# 运行yum命令，升级系统，安装wget、vim、ssh、mysql等
# 配置ssh && 并且 \ 换行
RUN yum -y update \
&& yum -y install wget vim net-tools openssh-server \
&& wget https://dev.mysql.com/get/mysql57-community-release-el7-9.noarch.rpm \
&& rpm -ivh mysql57-community-release-el7-9.noarch.rpm --force --nodeps \
&& yum -y install mysql-server \
&& source ~/.bashrc \ 
&& ssh-keygen -t dsa -f /etc/ssh/ssh_host_dsa_key \
&& ssh-keygen -t rsa -f /etc/ssh/ssh_host_rsa_key \
&& ssh-keygen -t ed25519 -f /etc/ssh/ssh_host_ED25519_key \
&& ssh-keygen -t ecdsa -f /etc/ssh/ssh_host_ecdsa_key \
&& mkdir /var/run/sshd \
&& sed -i 's/session required pam_loginuid.so/#session required pam_loginuid.so/g' /etc/pam.d/sshd \
&& echo 'root:123456' | chpasswd

# mysql密码，service服务刷新，需要进入容器运行命令

EXPOSE 22 3306 8080 80



