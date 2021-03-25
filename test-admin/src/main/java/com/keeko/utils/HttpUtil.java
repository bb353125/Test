package com.keeko.utils;

import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Http 请求
 *
 * @author chensq
 */
public class HttpUtil {

    /**
     * 向指定URL发送GET方法的请求
     *
     * 若路径上是有中文: URLEncoder.encode("消息内容","utf-8");
     */
    public static String get(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     */
    public static String post(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type","application/json");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 普通GET请求 ?后面带参数
     *
     * @param url 发送的地址加上参数
     * @return {结果
     */
    public static String sendGet(String url) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setDoOutput(true);
            // 腾讯地图使用GET
            conn.setRequestMethod("GET");
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            // 获取地址解析结果
            while ((line = in.readLine()) != null) {
                result.append(line).append("\n");
            }
        } catch (IOException e) {
            e.getMessage();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result.toString();
    }


    /**
     * 发送post请求
     *
     * @throws "相龙"
     */
    public static String posts(String url, String paramStr) {
        InputStream in = null;
        OutputStream os = null;
        String result = "";
        try {
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) new URL(url)
                    .openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            // 发送POST请求须设置
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            os = conn.getOutputStream();
            // 注意编码格式，防止中文乱码
            if (StringUtils.hasText(paramStr)) {
                os.write(paramStr.getBytes("utf-8"));
                os.close();
            }
            in = conn.getInputStream();
            result = StreamUtils.copyToString(in, Charset.forName("utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 发送get请求 相龙
     *
     * @throws Exception
     */
    public static String gets(String url) {

        String result = "";
        InputStream in = null;
        try {
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) new URL(url)
                    .openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestMethod("GET");
            // 建立实际的连接
            conn.connect();
            // 定义输入流来读取URL的响应
            in = conn.getInputStream();
            result = StreamUtils.copyToString(in, Charset.forName("utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static String sendPostForJsonData(String url, String params) {
        // 返回的结果
        StringBuilder result = new StringBuilder();
        // 读取响应输入流
        BufferedReader in = null;
        OutputStreamWriter out = null;
        try {
            // 创建URL对象
            URL connURL = new URL(url);
            // 打开URL连接
            HttpURLConnection httpConn = (HttpURLConnection) connURL.openConnection();
            // 设置通用属性
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            // 设定 请求格式 json，也可以设定xml格式的
            httpConn.setRequestProperty("Content-Type", " application/json");
            // 设置编码语言
            httpConn.setRequestProperty("Accept-Charset", "utf-8");
            httpConn.setConnectTimeout(5000);
            // 设置POST方式
            httpConn.setRequestMethod("POST");
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            // 当存在post的值时，才打开OutputStreamWriter
            if (params != null && params.trim().length() > 0) {
                out = new OutputStreamWriter(httpConn.getOutputStream(), "UTF-8");
                out.write(params);
                out.flush();
                out.close();
            }
            // 如果状态不为200则读取errorsTream
            if (httpConn.getResponseCode() == 500) {
                in = new BufferedReader(new InputStreamReader(httpConn.getErrorStream(), "UTF-8"));
            } else {
                in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
            }
            String line;
            // 读取返回的内容
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            httpConn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result.toString();
    }

    /**
     * 普通发送get请求 未测试
     */
    public static String sendGetRequest(String url) {
        //String url = "http://localhost:8080/weChat/getUrl";
        BufferedReader in = null;
        StringBuilder json = new StringBuilder();
        try {
            URL urlObject = new URL(url);
            URLConnection uc = urlObject.openConnection();
            in = new BufferedReader(new InputStreamReader(uc.getInputStream(), "utf-8"));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return json.toString();
    }


    /**
     * 发送post请求 获取小程序参数二维码
     */
    public static void getParamCode(String paramJson, String requestUrl, HttpServletResponse response) {
        OutputStream os = null;
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            // 提交模式
            httpURLConnection.setRequestMethod("POST");
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());

            printWriter.write(paramJson);
            // flush输出流的缓冲
            printWriter.flush();
            //开始获取数据
            BufferedInputStream in = new BufferedInputStream(httpURLConnection.getInputStream());

            os = response.getOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    /**
     * 发送post请求 获取小程序参数二维码 直接浏览器下载
     *
     * @param fileName   文件名
     * @param requestUrl 请求地址
     * @paramJson paramJson 内容json参数
     */
    public static void createParamCode(String fileName, String paramJson, String requestUrl, HttpServletResponse response) {
        OutputStream os = null;
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            // 提交模式
            httpURLConnection.setRequestMethod("POST");
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());

            printWriter.write(paramJson);
            // flush输出流的缓冲
            printWriter.flush();
            //开始获取数据
            BufferedInputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
            //当文件名不是英文名的时候，最好使用url解码器去编码一下，
            fileName = URLEncoder.encode(fileName, "UTF-8");
            //将响应的类型设置为图片
            response.setContentType("image/jpeg");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            os = response.getOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGetUtf8(String url, Map<String, String> param) {
        String result = "";
        BufferedReader in = null;
        try {
            StringBuffer query = new StringBuffer();

            for (Map.Entry<String, String> kv : param.entrySet()) {
                query.append(URLEncoder.encode(kv.getKey(), "UTF-8") + "=");
                query.append(URLEncoder.encode(kv.getValue(), "UTF-8") + "&");
            }
            if (query.lastIndexOf("&") > 0) {
                query.deleteCharAt(query.length() - 1);
            }

            String urlNameString = url + "?" + query.toString();
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 获取所有请求的值
     */
    public static Map<String, String> getRequestParameters() {
        HashMap<String, String> values = new HashMap<>();
        HttpServletRequest request = getRequest();
        Enumeration<String> enums = request.getParameterNames();
        while (enums.hasMoreElements()) {
            String paramName = (String) enums.nextElement();
            String paramValue = request.getParameter(paramName);
            values.put(paramName, paramValue);
        }
        return values;
    }

    /**
     * 获取 包装防Xss Sql注入的 HttpServletRequest
     *
     * @return request
     */
    public static HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return new WafRequestWrapper(request);
    }

    /**
     * 下载文件
     *
     * @param downloadPath 下载路径 http://www.cnblogs.com/images/logo_small.gif
     * @param saveFilePath  保存目录 d:/sss.gif
     * @param saveFileName 保存文件名称 sss.gif
     */
    public static void getfile(String downloadPath, String saveFilePath, String saveFileName) throws IOException {
        FileOutputStream out = null;
        InputStream ins = null;
        try {
            URL url = new URL(downloadPath);
            URLConnection con = url.openConnection();
            Path path = Paths.get(saveFilePath + saveFileName);
            // 如果没有files文件夹，则创建
            if (!Files.isWritable(path)) {
                Files.createDirectories(Paths.get(saveFilePath));
            }
            out = new FileOutputStream(saveFilePath + saveFileName);
            ins = con.getInputStream();
            byte[] b = new byte[1024];
            int i = 0;
            while ((i = ins.read(b)) != -1) {
                out.write(b, 0, i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ins != null) {
                ins.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

}




















