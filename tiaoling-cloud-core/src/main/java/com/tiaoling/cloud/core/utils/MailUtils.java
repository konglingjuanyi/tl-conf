package com.tiaoling.cloud.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.InetAddress;
import java.util.Properties;

/**
 * Created by yhl on 2016/9/30.
 */
public class MailUtils {
    private static String host = "smtpsrv01.rfdoa.cn";
    private static String moniterHost = "monitor.rfdoa.cn";
    private static String user = "rfd";
    private static String password = "Rfd@123";

    /**
     *
     * @param mailServerHost
     *            设置邮件服务
     *
     * @param fromPerson
     *            发件人邮箱
     *
     * @param toPerson
     *            收件人邮箱
     * @param subJect
     *            邮件主题
     * @param text
     *            邮件内容
     */
    public static void SendMail(String mailServerHost, String fromPerson,
                                String username, String password, String toPerson, String subJect,
                                String text) {
        JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
        // 建立邮件消息
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        try{
            String hostIp= InetAddress.getLocalHost().getHostAddress();
            subJect=subJect+"["+hostIp+"]";
            senderImpl.setHost(mailServerHost);// 设定mail server
            if (StringUtils.isNotBlank(toPerson) && toPerson.contains(";")) {
                String[] array = toPerson.split(";");
                mailMessage.setTo(array);
            } else {
                mailMessage.setTo(toPerson);
            }
            // 设置收件人，寄件人 用数组发送多个邮件
            // String[] array = new String[] {"sun111@163.com","sun222@sohu.com"};
            // mailMessage.setTo(array);
            mailMessage.setFrom(fromPerson);
            mailMessage.setSubject(subJect);
            // 设置邮件的字体大小
            mailMessage.setText(text);
            senderImpl.setUsername(username); // 根据自己的情况,设置username
            senderImpl.setPassword(password); // 根据自己的情况, 设置password
            Properties prop = new Properties();
            prop.put(" mail.smtp.auth ", " true "); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
            prop.put(" mail.smtp.timeout ", " 25000 ");
            senderImpl.setJavaMailProperties(prop);
            // 发送邮件
            senderImpl.send(mailMessage);
        }catch(Exception e)
        {
            // 发送邮件
            senderImpl.send(mailMessage);
        }
    }
    public static void SendMail(String mailServerHost, String fromPerson,
                                String username, String password, String toPerson, String subJect,
                                String text,boolean hasHostIp) {
        JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
        // 建立邮件消息
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        try{
            if(hasHostIp)
            {
                String hostIp=InetAddress.getLocalHost().getHostAddress();
                subJect=subJect+"["+hostIp+"]";
            }
            senderImpl.setHost(mailServerHost);// 设定mail server
            if (StringUtils.isNotBlank(toPerson) && toPerson.contains(";")) {
                String[] array = toPerson.split(";");
                mailMessage.setTo(array);
            } else {
                mailMessage.setTo(toPerson);
            }
            // 设置收件人，寄件人 用数组发送多个邮件
            // String[] array = new String[] {"sun111@163.com","sun222@sohu.com"};
            // mailMessage.setTo(array);
            mailMessage.setFrom(fromPerson);
            mailMessage.setSubject(subJect);
            // 设置邮件的字体大小
            mailMessage.setText(text);
            senderImpl.setUsername(username); // 根据自己的情况,设置username
            senderImpl.setPassword(password); // 根据自己的情况, 设置password
            Properties prop = new Properties();
            prop.put(" mail.smtp.auth ", " true "); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
            prop.put(" mail.smtp.timeout ", " 25000 ");
            senderImpl.setJavaMailProperties(prop);
            // 发送邮件
            senderImpl.send(mailMessage);
        }catch(Exception e)
        {
            // 发送邮件
            senderImpl.send(mailMessage);
        }
    }
    public static void SendMineMail(String mailServerHost, String fromPerson,
                                    String username, String password, String toPerson, String subJect,
                                    String text, Boolean isHTML) throws MessagingException {
        JavaMailSenderImpl senderMail = new JavaMailSenderImpl();

        // 设定 Mail Server
        senderMail.setHost(mailServerHost);
        // senderMail.setPort(25);

        Properties prop = new Properties();
        prop.setProperty("mail.smtp.auth", "true");

        // SMTP验证时，需要用户名和密码
        senderMail.setUsername(username);
        senderMail.setPassword(password);
        senderMail.setJavaMailProperties(prop); // 如果要密码验证,这里必须加,不然会报553错误

        // 发送HTML格式的邮件
        // 建立邮件信息，可发送HTML格式
        MimeMessage mimeMessage = senderMail.createMimeMessage(); // MimeMessage-->java的
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,
                true, "UTF-8"); // MimeMessageHelper-->spring的 不加后面两个参数会乱码

        // 设置收件人，主题，内容
        messageHelper.setSubject(subJect);
        messageHelper.setFrom(fromPerson);

        String[] toAddress = toPerson.split(";");
        InternetAddress[] addressTo = new InternetAddress[toAddress.length];
        for (int i = 0; i < toAddress.length; i++) {
            addressTo[i] = new InternetAddress(toAddress[i]);
        }

        messageHelper.setTo(addressTo);

        messageHelper.setText(text, isHTML); // 为true-->发送转义HTML

        senderMail.send(mimeMessage); // 这个是不带附件的
    }

    public static void SendMail(String from, String toAddress, String subJect,
                                String body) {
        SendMail(moniterHost, from, user, password, toAddress, subJect, body);
    }
    public static void SendMail(String from, String toAddress, String subJect,
                                String body,boolean hasHostIp) {
        SendMail(moniterHost, from, user, password, toAddress, subJect, body,hasHostIp);
    }

    public static void SendMineMail(String from, String toAddress,
                                    String subJect, String body, Boolean isHTML)
            throws MessagingException {
        SendMineMail(moniterHost, from, user, password, toAddress, subJect,
                body, isHTML);
    }
}
