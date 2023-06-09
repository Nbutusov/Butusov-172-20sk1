package edu.nmu.fit.ButusovNYProgect;

import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadWriter {
    private static final String FILE_NAME_TO_SAVE = "Result";

    private static final String FILE_NAME_TO_READ = "src/main/java/resources/Input";
    private static final String EXTENTION = ".xlsx";
    private static final String SHEET_NAME = "Items";
    private static int ROW_NUM = 0;
    public byte[] saveToFile(XSSFWorkbook workbook, String version){
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                workbook.write(bos);
            } finally {
                bos.close();
            }
            byte[] bytes = bos.toByteArray();
            workbook.close();
            System.out.println("File saved successfully");
            return bytes;
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }


        return null;
    }

    public XSSFWorkbook readFromFile(String filePath){
        if (filePath==null){
            filePath=FILE_NAME_TO_READ+EXTENTION;
        }
        XSSFWorkbook workbook = null;
        try {
            FileInputStream excelFile = new FileInputStream(new File(filePath));
            workbook = new XSSFWorkbook(excelFile);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return workbook;
    }

    public void pushDataToFile(List<String> data, XSSFWorkbook workbook){
        if (data==null) return;
        XSSFSheet sheet = workbook.getSheet(SHEET_NAME);
        XSSFRow row = sheet.createRow(ROW_NUM);

        for (int i=0; i< data.size();i++){
            row.createCell(i).setCellValue(data.get(i));
        }
        ROW_NUM++;


    }

    public XSSFWorkbook startFile(){
        ROW_NUM = 0;
        XSSFWorkbook workbook = new XSSFWorkbook();
        workbook.createSheet(SHEET_NAME);
        List<String> data = new ArrayList<String>();
        data.add("Number");
        data.add("Description");

        data.add("Link");
        data.add("Price");
        pushDataToFile(data, workbook);
        return workbook;
    }
}