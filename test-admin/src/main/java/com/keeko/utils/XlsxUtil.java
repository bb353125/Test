package com.keeko.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class XlsxUtil {

    /**
     * 导入Excel
     */
    public static List<List<Object>> setHSSFWorkbook(InputStream in, String fileName) throws IOException {
        List<List<Object>> list = new ArrayList<>();
        try {
            Workbook workbook = getWorkbok(in, fileName);
            //创建Excel工作薄
            if (null == workbook) {
                throw new Exception("创建Excel工作薄为空！");
            }
            System.out.println("导入数据Sheets="+workbook.getNumberOfSheets());
            System.out.println("导入数据RowNum="+workbook.getSheetAt(0).getLastRowNum());
            System.out.println("导入数据CellNum="+workbook.getSheetAt(0).getRow(0).getLastCellNum());
            // 循环工作表Sheet
            for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
                Sheet hssfSheet = workbook.getSheetAt(numSheet);
                if (hssfSheet == null) {
                    continue;
                }
                // 循环行Row
                for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                    Row row = hssfSheet.getRow(rowNum);
                    if (row == null) {
                        continue;
                    }
                    // 循环列Cell
                    List<Object> li = new ArrayList<>();
                    for (int y = 0; y < row.getLastCellNum(); y++) {
                        Cell cell = row.getCell(y);
                        li.add(getCellValue(cell));
                    }
                    list.add(li);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    //获取单元格各类型值，返回字符串类型
    public static Object getCellValue(Cell cell) {
        Object value = "";
        try {
            DecimalFormat df = new DecimalFormat("0");  //格式化number String字符
            SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");  //日期格式化
            DecimalFormat df2 = new DecimalFormat("0.00");  //格式化数字
            if (cell == null) {
                return value;
            }
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    value = cell.getRichStringCellValue().getString();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                        value = df.format(cell.getNumericCellValue());
                    } else if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {
                        value = sdf.format(cell.getDateCellValue());
                        value = sdf.parse(value + "");
                    } else {
                        value = df2.format(cell.getNumericCellValue());
                    }

                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    value = cell.getBooleanCellValue();
                    break;
                case Cell.CELL_TYPE_BLANK:
                    value = "";
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    DecimalFormat dft = new DecimalFormat("#");
                    try {
                        value = cell.getStringCellValue();
                    } catch (IllegalStateException e) {
                        value = String.valueOf(dft.format(cell.getNumericCellValue()));
                    }
                    break;

                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    private final static String excel2003L = ".xls";    //2003- 版本的excel
    private final static String excel2007U = ".xlsx";   //2007+ 版本的excel


    public static Workbook getWorkbok(InputStream in, String filename) throws IOException {
        Workbook wb = null;
        if (filename.endsWith(excel2003L)) {  //Excel 2003
            wb = new HSSFWorkbook(in);
        } else if (filename.endsWith(excel2007U)) {  // Excel 2007/2010
            wb = new XSSFWorkbook(in);
        }
        return wb;
    }
}
