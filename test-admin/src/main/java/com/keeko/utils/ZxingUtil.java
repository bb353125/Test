package com.keeko.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.keeko.master.entity.Book;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * 谷歌提供相关
 *
 * @author chensq
 */
public class ZxingUtil {

    /**
     * 解析出 ISBN 号码
     *
     * @param imgPath 需要解析的条形码路径
     */
    public static String getQRCodeParam(String imgPath) {
        BufferedImage image;
        String param = "";
        try {
            image = ImageIO.read(new File(imgPath));
            if (image == null) {
                System.out.println("解码图像可能没有退出");
            }
            assert image != null;
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));
            Map<DecodeHintType, Object> hints = new Hashtable<>(16);
            // 解码设置编码方式为：utf-8,
            hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
            //优化精度
            hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
            //复杂模式，开启PURE_BARCODE模式
            hints.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);

            Result result = new MultiFormatReader().decode(binaryBitmap, hints);
            param = result.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return param;
    }

    /**
     * 根据ISBN请求 极速数据 查询书籍相关内容
     * https://api.jisuapi.com/isbn/query?appkey=a8b6061e8802d215&isbn=
     */
    public static void getBookContent(Book book) {
        String isbn = book.getIsbn();
        if (StringUtils.isNotEmpty(isbn)) {
            String result = HttpUtil.get("https://api.jisuapi.com/isbn/query?appkey=a8b6061e8802d215&isbn=" + isbn);
            //把json字符串转换为json对象
            JSONObject object = (JSONObject) JSON.parse(result);
            String msg = object.getString("msg");
            if ("ok".equals(msg)) {
                String jsonData = object.getString("result");
                JSONObject obj = (JSONObject) JSON.parse(jsonData);
                String title = obj.getString("title");
                book.setName(title);
                String subtitle = obj.getString("subtitle");
                book.setSubTitle(subtitle);
                String pic = obj.getString("pic");
                book.setPic(pic);
                String author = obj.getString("author");
                book.setAuthor(author);
                String summary = obj.getString("summary");
                book.setSummary(summary);
                String publisher = obj.getString("publisher");
                book.setPublisher(publisher);
                String pubplace = obj.getString("pubplace");
                book.setPubplace(pubplace);
                String pubdate = obj.getString("pubdate");
                book.setPubdate(pubdate);
                String price = obj.getString("price");
                book.setPrice(price);
                String binding = obj.getString("binding");
                book.setBinding(binding);
                String isbn10 = obj.getString("isbn10");
                book.setIsbn10(isbn10);
                String keyword = obj.getString("keyword");
                book.setKeyword(keyword);
                String edition = obj.getString("edition");
                book.setEdition(edition);
                String impression = obj.getString("impression");
                book.setImpression(impression);
                String language = obj.getString("language");
                book.setLanguage(language);
                String format = obj.getString("format");
                book.setFormat(format);
                String type = obj.getString("class");
                book.setType(type);
                String sellerlist = obj.getString("sellerlist");
                book.setSellerList(sellerlist);
            }
        }
    }

    /**
     * 图片分割
     * 参考: https://blog.csdn.net/qq_16471893/article/details/88892436
     *
     * @param imgPath 大图路径 "/static/1/1570757404567.jpg"
     * @param rows 分割成4*4(16)个小图
     * @param cols 分割成4*4(16)个小图
     */
    public static List<String> imgSegmentation(String imgPath, int rows, int cols) throws Exception {
        // 读入大图
        File file = new File(imgPath);
        FileInputStream fis = new FileInputStream(file);
        BufferedImage image = ImageIO.read(fis);
        // 分割成4*4(16)个小图
        int chunks = rows * cols;
        // 计算每个小图的宽度和高度
        int chunkWidth = image.getWidth() / cols;
        int chunkHeight = image.getHeight() / rows;

        int count = 0;
        BufferedImage imgs[] = new BufferedImage[chunks];
        List<String> imgPathList = new ArrayList<>();
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                //设置小图的大小和类型
                imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());

                //写入图像内容
                Graphics2D gr = imgs[count++].createGraphics();
                gr.drawImage(image, 0, 0,
                        chunkWidth, chunkHeight,
                        chunkWidth * y, chunkHeight * x,
                        chunkWidth * y + chunkWidth,
                        chunkHeight * x + chunkHeight, null);
                gr.dispose();
            }
        }
        // 输出小图
        for (int i = 0; i < imgs.length; i++) {
            String filePath = "/static/book/temp" + i + ".jpg";
            ImageIO.write(imgs[i], "jpg", new File(filePath));
            imgPathList.add(filePath);
        }
        return imgPathList;
    }

}
