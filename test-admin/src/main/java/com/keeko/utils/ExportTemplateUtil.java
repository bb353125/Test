package com.keeko.utils;

import com.keeko.common.exception.CustomException;
import com.keeko.master.vo.ExcelVO;
import com.keeko.master.vo.ImageVO;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 导出模板工具
 *
 * @author chensq
 */
public class ExportTemplateUtil {
    /**
     * 导出不规则公共方法 .xlsx .xls两种格式
     *
     * @param excelVO  入参
     * @param response 响应结果
     */
    public void exportTemplate(ExcelVO excelVO, HttpServletResponse response) {
        URL url = this.getClass().getClassLoader().getResource("");
        assert url != null;
        String path = url.getPath();
        //获得模版路径
        String templateUrl = path + excelVO.getTemplateUrl();
        //生成的导出文件路径
        String templateNewUrl = path + excelVO.getTemplateNewUrl();
        //transformer转到Excel
        XLSTransformer transformer = new XLSTransformer();
        // 设置输出的格式
        response.reset();
        response.setContentType("bin");
        // 循环取出流中的数据
        byte[] b = new byte[1024];
        int len;
        try {
            // 下载本地文件 文件的默认保存名
            String fileName = URLEncoder.encode(excelVO.getFileName(), "utf-8");
            //更新模板信息
            transformer.transformXLS(templateUrl, excelVO.getMap(), templateNewUrl);

            // 读到流中 文件的存放路径
            InputStream inStream = new FileInputStream(templateNewUrl);
            response.addHeader("Content-Disposition", "attachment; filename=" + fileName + "");
            while ((len = inStream.read(b)) > 0) {
                response.getOutputStream().write(b, 0, len);
            }
            inStream.close();
        } catch (IOException e) {
            throw new CustomException("IO异常");
        } catch (InvalidFormatException e) {
            throw new CustomException("参数异常");
        }
    }

    /**
     * 拼接时间
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     */
    public static String linkTime(String beginTime, String endTime) throws ParseException {
        String time = "";
        if (StringUtils.isNotEmpty(beginTime) && StringUtils.isNotEmpty(endTime)) {
            SimpleDateFormat stringTime = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            beginTime = dateFormat.format(stringTime.parse(beginTime));
            endTime = dateFormat.format(stringTime.parse(endTime));
            time = "(" + beginTime + "-" + endTime + ")";
        }
        return time;
    }


    /**
     * 导出有图有文字的Excel
     *
     * @param imageList 图片列表
     */
    public static void exportExcel(List<ImageVO> imageList) {
        FileOutputStream fileOut = null;
        try {
            HSSFWorkbook wb = new HSSFWorkbook();
            // 创建表
            HSSFSheet sheet = wb.createSheet("sheet1");
            // 设置单元格默认宽度为15个字符
            sheet.setDefaultColumnWidth(18);
            short height = 500;
            sheet.setDefaultRowHeight(height);
            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
            // 设置表头样式
            HSSFCellStyle style = wb.createCellStyle();
            // 设置居左
            style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
            // 设置细边框
            style.setWrapText(true);



            List<ImageVO> images = new ArrayList<>();
            ImageVO imageVO = new ImageVO();
            imageVO.setImgPath1("/static/test/123.jpg");
            imageVO.setImgPath2("/static/test/456.jpg");
            imageVO.setImgPath3("/static/test/123.jpg");
            imageVO.setImgName1("07:40:38:73:09:95");
            imageVO.setImgName2("07:40:38:73:09:96");
            imageVO.setImgName3("07:40:38:73:09:97");
            images.add(imageVO);
            ImageVO imageVO2 = new ImageVO();
            imageVO2.setImgPath1("/static/test/123.jpg");
            imageVO2.setImgPath2("/static/test/456.jpg");
            imageVO2.setImgPath3("/static/test/123.jpg");
            imageVO2.setImgName1("07:40:38:73:09:95");
            imageVO2.setImgName2("07:40:38:73:09:96");
            imageVO2.setImgName3("07:40:38:73:09:97");
            images.add(imageVO2);
            ImageVO imageVO3 = new ImageVO();
            imageVO3.setImgPath1("/static/test/123.jpg");
            imageVO3.setImgPath2("/static/test/456.jpg");
            imageVO3.setImgPath3("/static/test/123.jpg");
            imageVO3.setImgName1("07:40:38:73:09:95");
            imageVO3.setImgName2("07:40:38:73:09:96");
            imageVO3.setImgName3("07:40:38:73:09:97");
            images.add(imageVO3);
            //创建文字
            createMac(sheet, wb, images);
            //创建图片
            createPicture(patriarch, wb, images);

            fileOut = new FileOutputStream("D:/static/test/123excel.xls");
            // 输出文件
            wb.write(fileOut);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建Excel文字
     *
     * @param sheet 表
     * @param wb    输出
     */
    private static void createMac(HSSFSheet sheet, HSSFWorkbook wb, List<ImageVO> imageList){
        //单元格样式以及单元格中字体
        HSSFCellStyle style = wb.createCellStyle();
        // 设置背景色
        style.setFillForegroundColor((short) 13);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        // 居中
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //字体
        HSSFFont font = wb.createFont();
        // 字体号码
        font.setFontHeightInPoints((short) 12);
        // 字体名称
        font.setFontName("黑体");
        style.setFont(font);
        //列
        int col = 1;
        //行
        int row = 13;
        for (int i = 0; i < imageList.size(); i++) {
            ImageVO imageVO = imageList.get(i);
            if (i > 0) {
                col = 1;
                row = row + 15;
            }
            //在第几行(动态)
            HSSFRow hssfRow = sheet.createRow(row);
            //设置 行高
            hssfRow.setHeight((short) (15 * 33.35));
            //设置单元格格式
            hssfRow.createCell(col).setCellStyle(style);
            //往单元格中填充内容
            hssfRow.getCell(col).setCellValue(imageVO.getImgName1());
            col = col + 4;
            //设置单元格格式
            hssfRow.createCell(col).setCellStyle(style);
            //往单元格中填充内容
            hssfRow.getCell(col).setCellValue(imageVO.getImgName2());
            col = col + 4;
            //设置单元格格式
            hssfRow.createCell(col).setCellStyle(style);
            //往单元格中填充内容
            hssfRow.getCell(col).setCellValue(imageVO.getImgName3());
        }

    }

    /**
     * 创建Excel图片
     * dx1 第1个单元格中x轴的偏移量
     * dy1 第1个单元格中y轴的偏移量
     * dx2 第2个单元格中x轴的偏移量
     * dy2 第2个单元格中y轴的偏移量
     * col1 第1个单元格的列号 左靠左
     * row1 第1个单元格的行号 上靠上
     * col2 第2个单元格的列号 右靠右
     * row2 第2个单元格的行号 下靠下
     *
     * @param patriarch Excel
     * @param wb        输出
     */
    private static void createPicture(HSSFPatriarch patriarch, HSSFWorkbook wb, List<ImageVO> imageList) throws IOException {
        int col1 = 0;
        int row1 = 0;
        int col2 = 3;
        int row2 = 13;
        for (int i = 0; i < imageList.size(); i++) {
            ImageVO imageVO = imageList.get(i);
            if (i > 0) {
                col1 = 0;
                row1 = row1 + 15;
                col2 = 3;
                row2 = row2 + 15;
            }
            //图1
            ByteArrayOutputStream byteArrayOut1 = new ByteArrayOutputStream();
            //加载图片
            BufferedImage bufferImg1 = ImageIO.read(new File(imageVO.getImgPath1()));
            ImageIO.write(bufferImg1, "jpg", byteArrayOut1);
            //图片1
            HSSFClientAnchor anchor1 = new HSSFClientAnchor(0, 0, 0, 0, (short) col1, row1, (short) col2, row2);
            //表中插入图片1
            patriarch.createPicture(anchor1, wb.addPicture(byteArrayOut1.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));

            col1 = col1 + 4;
            col2 = col2 + 4;
            //图2
            ByteArrayOutputStream byteArrayOut2 = new ByteArrayOutputStream();
            //加载图片
            BufferedImage bufferImg2 = ImageIO.read(new File(imageVO.getImgPath2()));
            ImageIO.write(bufferImg2, "jpg", byteArrayOut2);
            //图片1
            HSSFClientAnchor anchor2 = new HSSFClientAnchor(0, 0, 0, 0, (short) col1, row1, (short) col2, row2);
            //表中插入图片1
            patriarch.createPicture(anchor2, wb.addPicture(byteArrayOut2.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));

            col1 = col1 + 4;
            col2 = col2 + 4;
            //图3
            ByteArrayOutputStream byteArrayOut3 = new ByteArrayOutputStream();
            //加载图片
            BufferedImage bufferImg3 = ImageIO.read(new File(imageVO.getImgPath3()));
            ImageIO.write(bufferImg3, "jpg", byteArrayOut3);
            //图片1
            HSSFClientAnchor anchor3 = new HSSFClientAnchor(0, 0, 0, 0, (short) col1, row1, (short) col2, row2);
            //表中插入图片1
            patriarch.createPicture(anchor3, wb.addPicture(byteArrayOut3.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
        }
    }


}
