package com.keeko.utils;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Properties;

/**
 * 发送邮件相关
 *
 * @author chensq
 */
public class SendEmailUtils2 {

    private static String HOST = "smtp.exmail.qq.com";
    private static String USER = "xmzy@zhuying.com";
    private static String PASSWORD = "XM@zykj76276";

    //用户名密码验证，需要实现抽象类Authenticator的抽象方法PasswordAuthentication
    static class MyAuthenricator extends Authenticator {
        String u = null;
        String p = null;

        public MyAuthenricator(String u, String p) {
            this.u = u;
            this.p = p;
        }

        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(u, p);
        }
    }

    /**
     * 发送邮件
     *
     * @param subject     邮件主题
     * @param sendHtml    邮件内容
     * @param receiveUser 收件人地址
     * @param attachments  附件
     */
    public void doSendHtmlEmail(String subject, String sendHtml, String receiveUser, List<File> attachments) {
        Transport transport = null;
        try {
            Properties prop = new Properties();
            //协议
            prop.setProperty("mail.transport.protocol", "smtp");
            //服务器
            prop.setProperty("mail.smtp.host", HOST);
            //端口
            prop.setProperty("mail.smtp.port", "465");
            //使用smtp身份验证
            prop.setProperty("mail.smtp.auth", "true");
            //使用SSL，企业邮箱必需！
            //开启安全协议
            MailSSLSocketFactory sf = null;
            try {
                sf = new MailSSLSocketFactory();
                sf.setTrustAllHosts(true);
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
            prop.put("mail.smtp.ssl.enable", "true");
            prop.put("mail.smtp.ssl.socketFactory", sf);
            Session session = Session.getDefaultInstance(prop, new MyAuthenricator(USER, PASSWORD));
            session.setDebug(false);

            MimeMessage message = new MimeMessage(session);

            // 发件人
            InternetAddress from = new InternetAddress(USER);
            message.setFrom(from);

            // 收件人
            InternetAddress to = new InternetAddress(receiveUser);
            message.setRecipient(Message.RecipientType.TO, to);

            // 邮件主题
            message.setSubject(subject);

            // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
            Multipart multipart = new MimeMultipart();

            // 添加邮件正文
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setContent(sendHtml, "text/html;charset=UTF-8");
            multipart.addBodyPart(contentPart);

            // 添加附件的内容
            //List<File> attachments=mailInfo.getAttachments();
            if(attachments!=null){
                for (int i = 0; i < attachments.size(); i++) {
                    BodyPart attachmentBodyPart = new MimeBodyPart();
                    DataSource source = new FileDataSource(attachments.get(i));
                    attachmentBodyPart.setDataHandler(new DataHandler(source));
                    // MimeUtility.encodeWord可以避免文件名乱码
                    attachmentBodyPart.setFileName(MimeUtility.encodeWord(attachments.get(i).getName()));
                    multipart.addBodyPart(attachmentBodyPart);
                }
            }

            // 添加附件的内容
           /* if (attachment != null) {
                BodyPart attachmentBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(attachment);
                attachmentBodyPart.setDataHandler(new DataHandler(source));

                // 网上流传的解决文件名乱码的方法，其实用MimeUtility.encodeWord就可以很方便的搞定
                // 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
                //sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
                //messageBodyPart.setFileName("=?GBK?B?" + enc.encode(attachment.getName().getBytes()) + "?=");

                //MimeUtility.encodeWord可以避免文件名乱码
                attachmentBodyPart.setFileName(MimeUtility.encodeWord(attachment.getName()));
                multipart.addBodyPart(attachmentBodyPart);
            }*/

            // 将multipart对象放到message中
            message.setContent(multipart);
            // 保存邮件
            message.saveChanges();

            transport = session.getTransport("smtp");
            // smtp验证，就是你用来发邮件的邮箱用户名密码
            transport.connect(HOST, USER, PASSWORD);
            // 发送
            transport.sendMessage(message, message.getAllRecipients());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (transport != null) {
                try {
                    transport.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        SendEmailUtils se = new SendEmailUtils();
        File affix = new File("C:\\Users\\admin\\Desktop\\images\\test.txt");
        //se.doSendHtmlEmail("邮件主题1", "邮件内容1", "18206079949@163.com", affix);
    }

}
