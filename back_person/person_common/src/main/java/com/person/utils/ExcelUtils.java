package com.person.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyDescriptor;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * excel操作工具类
 *
 * @author: pzy
 * @create: 2019/8/17 9:40
 */
@Slf4j
public class ExcelUtils {

    private static NumberFormat numberFormat = NumberFormat.getInstance();

    static {
        //不分组显示数据 三位数为一个分组 例子: true-> 999,999 false->999999
        numberFormat.setGroupingUsed(false);
    }

    /**
     * 导出excel
     */
    public static <T> void exportExcel(Collection<T> collection, String fileName, String sheetName,
                                       HttpServletResponse response, Class<?> clazz) throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建一个Excel表单，参数为sheet的名字
        HSSFSheet sheet = workbook.createSheet(sheetName);
        //创建表头
        setTitle(workbook, sheet, clazz);
        //新增数据行，并且设置单元格数据
        int rowNum = 1;
        for (T t : collection) {
            HSSFRow row = sheet.createRow(rowNum);
            List<Field> fieldList = new ArrayList<>();
            recursionClass(clazz, fieldList);
            for (Field field : fieldList) {
                if (field.isAnnotationPresent(Excel.class)) {
                    PropertyDescriptor pd = new PropertyDescriptor(field.getName(),
                            clazz);
                    Method getMethod = pd.getReadMethod();//获得get方法
                    Excel excel = field.getAnnotation(Excel.class);
                    if (field.getType() == int.class || field.getType() == Integer.class) {
                        row.createCell(excel.orderNum()).setCellValue(Integer.parseInt(getMethod.invoke(t).toString()));
                    } else if (field.getType() == double.class || field.getType() == Double.class) {
                        row.createCell(excel.orderNum()).setCellValue(Double.parseDouble(getMethod.invoke(t).toString()));
                    } else if (field.getType() == String.class) {
                        row.createCell(excel.orderNum()).setCellValue(getMethod.invoke(t).toString());
                    } else if (field.getType() == boolean.class) {
                        row.createCell(excel.orderNum()).setCellValue(Boolean.valueOf(getMethod.invoke(t).toString()));
                    } else if (field.getType() == Date.class) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        row.createCell(excel.orderNum()).setCellValue(sdf.format(getMethod.invoke(t)));
                    } else if (field.getType() == List.class) {
                        row.createCell(excel.orderNum()).setCellValue(getMethod.invoke(t).toString());
                    }
                }

            }
            rowNum++;
        }
        responseHeader(workbook, fileName, response);
    }

    /**
     * 导入excel
     */
    public static <T> List<T> importExcel(MultipartFile file, Class<T> clazz) throws Exception {
        Field[] fields = clazz.getDeclaredFields();
        List<T> list = new ArrayList<>();

        InputStream is = file.getInputStream();
        Workbook workbook = new HSSFWorkbook(is);

        // 第一个Sheet
        Sheet sheet = workbook.getSheetAt(0);
        //表头
        Row rowHead = sheet.getRow(0);
        //总列数
        int columns = rowHead.getPhysicalNumberOfCells();
        //数据的数量
        int lines = sheet.getPhysicalNumberOfRows();
        //循环获取每行数据
        for (int i = 1; i < lines; i++) {
            Row row = sheet.getRow(i);
            T bean = clazz.newInstance();

            for (int j = 0; j < columns; j++) {
                String headName = String.valueOf(rowHead.getCell(j));

                for (Field field : fields) {
                    if (field.isAnnotationPresent(Excel.class)) {
                        //获取注解
                        Excel excel = field.getAnnotation(Excel.class);
                        //判断头部值和类的值一样 必须一样
                        if (excel.name().equals(headName)) {
                            //获取属性名字
                            String fieldName = field.getName();
                            Method method = null;
                            //获取set方法
                            fieldName = ("set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1));

                            String cell = recursionExcelValue(row, i, j, sheet, field);
                            //判断属性类型
                            if (field.getType() == int.class) {
                                method = clazz.getDeclaredMethod(fieldName, int.class);
                                method.invoke(bean, Integer.parseInt(cell));
                            } else if (field.getType() == Integer.class) {
                                method = clazz.getDeclaredMethod(fieldName, Integer.class);
                                method.invoke(bean, Integer.parseInt(cell));
                            } else if (field.getType() == double.class) {
                                method = clazz.getDeclaredMethod(fieldName, double.class);
                                method.invoke(bean, Double.parseDouble(cell));
                            }  else if (field.getType() == boolean.class) {
                                method = clazz.getDeclaredMethod(fieldName, boolean.class);
                                method.invoke(bean, Boolean.valueOf(cell));
                            } else if (field.getType() == String.class) {
                                method = clazz.getDeclaredMethod(fieldName, String.class);
                                method.invoke(bean, cell);
                            } else if (field.getType() == Date.class) {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                method = clazz.getDeclaredMethod(fieldName, Date.class);
                                method.invoke(bean, sdf.parse(cell));
                            }
                        }

                    }
                }

            }

            list.add(bean);
        }

        return list;
    }


    /**
     * 下载excel模板
     *
     * @param fileName  文件名称
     * @param sheetName 表格名称
     * @param response
     * @param clazz     实体类
     * @param <T>       任意类型
     * @throws Exception
     */
    public static <T> void downloadTemplate(String fileName, String sheetName,
                                            HttpServletResponse response, Class<T> clazz) throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建一个excel表单
        HSSFSheet sheet = workbook.createSheet(sheetName);
        //设置表头
        setTitle(workbook, sheet, clazz);
        responseHeader(workbook, fileName, response);
    }

    /**
     * 创建表头
     */
    private static void setTitle(HSSFWorkbook workbook, HSSFSheet sheet, Class clazz) {
        HSSFRow row = sheet.createRow(0);
        List<Field> fieldList = new ArrayList<>();
        recursionClass(clazz, fieldList);
        //设置为居中加粗
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        //设置字体
        font.setFontName("宋体");
        //设置粗体
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //设置字号
        font.setFontHeightInPoints((short) 14);
        //设置颜色
        font.setColor(IndexedColors.BLACK.index);
        style.setFont(font);
        HSSFCell cell;
        for (Field field : fieldList) {
            if (field.isAnnotationPresent(Excel.class)) {
                Excel excel = field.getAnnotation(Excel.class);
                cell = row.createCell(excel.orderNum());
                cell.setCellValue(excel.name());
                cell.setCellStyle(style);
                sheet.setColumnWidth(excel.orderNum(), 30 * 256);
            }
        }
    }

    private static void responseHeader(HSSFWorkbook workbook, String fileName, HttpServletResponse response) throws Exception {
        //清空response
        response.reset();
        // 告诉浏览器用什么软件可以打开此文件
//        response.setHeader("content-Type", "application/json");
        response.setHeader("content-Type", "application/vnd.ms-excel;charset=utf-8"); //用这个springboot报警告
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName+ ".xls", "UTF-8") );
        OutputStream os;
        //可以将生成Excel默认下载到某个目录下
        //os = new BufferedOutputStream(new FileOutputStream("D:\\" + fileName));
        //也可以通过response得到输出流，写到输出流中，在页面中进行下载
        os = new BufferedOutputStream(response.getOutputStream());
        //将excel写入到输出流中
        workbook.write(os);
        os.flush();
        os.close();
    }

    /**
     * 递归查询数据
     *
     * @param row   第几行数据
     * @param i     行 下表
     * @param j     行内 列的下表
     * @param sheet excel表格
     * @return
     */
    private static String recursionExcelValue(Row row, int i, int j, Sheet sheet, Field field) {
        if (row.getCell(j) == null) {
            i--;
            row = sheet.getRow(i);
            recursionExcelValue(row, i, j, sheet, field);
        }
        Cell cell = row.getCell(j);

        if (field.getType() == String.class || field.getType() == Date.class) {
            cell.setCellType(Cell.CELL_TYPE_STRING);
            return cell.getStringCellValue();
        }
        return numberFormat.format(cell.getNumericCellValue());
    }

    /**
     * 递归查找父类的属性方法
     */
    private static void recursionClass(Class<?> clazz, List<Field> fieldList) {
        if (clazz != null && !clazz.getName().toLowerCase().equals("java.lang.object")) {
            fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
            recursionClass(clazz, fieldList);
        }
    }

}
