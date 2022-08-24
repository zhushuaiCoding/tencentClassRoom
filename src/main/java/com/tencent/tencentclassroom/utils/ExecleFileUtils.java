package com.tencent.tencentclassroom.utils;

import com.tencent.tencentclassroom.model.M3u8Execle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述:
 *
 * @author zhushuai$
 * 创建日期 2022/6/21$
 * @since com.tencent.tencentclassroom.utils
 */
public class ExecleFileUtils {


    public static void main(String[]args) throws Exception {
        String path="C:\\Users\\binar\\Documents\\WeChat Files\\wxid_qxssx0el7b0022\\FileStorage\\File\\2022-06\\【图灵VIP严选课程】JAVA互联网架构师专题分布式高并第四期.xlsx";
        List<M3u8Execle> m3u8Execles = readExecle(path);
        System.out.println(m3u8Execles);

    }

    /**
     * 获取execl内容
     * @param filePath
     * @return
     */
    public static List<M3u8Execle> readExecleFile(MultipartFile filePath) throws Exception {
        // 创建输入流，读取Excel
        InputStream is = filePath.getInputStream();
        // jxl提供的Workbook类
        XSSFWorkbook wb = new XSSFWorkbook(is);
        // 只有一个sheet,直接处理
        //创建一个Sheet对象
        XSSFSheet sheetAt = wb.getSheetAt(0);
        // 得到所有的行数
        XSSFRow rows = sheetAt.getRow(0);
        // 所有的数据
        List<M3u8Execle> allData = new ArrayList();
        // 越过第一行 它是列名称
        String folderName="";
        int folderNumber=1;
        for (int j = 1; j < sheetAt.getPhysicalNumberOfRows(); j++) {
            // 得到每一行的单元格的数据
            XSSFRow row = sheetAt.getRow(j);
            M3u8Execle oneData = new M3u8Execle();
            if (row.getCell(0)!=null){

                folderName=row.getCell(0).toString().trim();
                folderNumber=0;
                continue;
            }else {
                oneData.setFolderName(folderName);
                folderNumber=folderNumber+1;
            }
            if (row.getCell(1)!=null){
                oneData.setFileName(folderNumber+"."+row.getCell(1).toString());
            }
            if (row.getCell(2)!=null){
                oneData.setLink(row.getCell(2).toString());
            }
            // 存储每一条数据
            allData.add(oneData);
            // 打印出每一条数据
        }
        return allData;
    }

    /**
     * 获取execl内容
     * @param filePath
     * @return
     */
    public static List<M3u8Execle> readExecle(String filePath) throws Exception {
        // 创建输入流，读取Excel
        InputStream is = new FileInputStream(filePath);
        // jxl提供的Workbook类
        XSSFWorkbook wb = new XSSFWorkbook(is);
        // 只有一个sheet,直接处理
        //创建一个Sheet对象
        XSSFSheet sheetAt = wb.getSheetAt(0);
        // 得到所有的行数
        XSSFRow rows = sheetAt.getRow(0);
        // 所有的数据
        List<M3u8Execle> allData = new ArrayList();
        // 越过第一行 它是列名称
        String folderName="";
        int folderNumber=1;
        for (int j = 1; j < sheetAt.getPhysicalNumberOfRows(); j++) {
            // 得到每一行的单元格的数据
            XSSFRow row = sheetAt.getRow(j);
            M3u8Execle oneData = new M3u8Execle();
            if (row.getCell(0)!=null){

                folderName=row.getCell(0).toString().trim();
                folderNumber=0;
                continue;
            }else {
                oneData.setFolderName(folderName);
                folderNumber=folderNumber+1;
            }
            if (row.getCell(1)!=null){
                oneData.setFileName(folderNumber+"."+row.getCell(1).toString());
            }
            if (row.getCell(2)!=null){
                oneData.setLink(row.getCell(2).toString());
            }
            // 存储每一条数据
            allData.add(oneData);
            // 打印出每一条数据
        }
        return allData;
    }


}
