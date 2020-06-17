package com.server.tools.send;


import com.server.tools.common.SendEmail;
import com.server.tools.encryption.AESUtil;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import java.util.Date;
import java.util.Properties;

public class SendEmailUtils extends SendEmail {
    public static boolean sendAccountActivateEmailToUrl(String email, String url, String username) {
        Session session = getSession();
        MimeMessage message = new MimeMessage(session);
        try {
            message.setSubject("这是一封激活账号的邮件");
            message.setSentDate(new Date());
            //setFrom 表示用哪个邮箱发送邮件
            message.setFrom(new InternetAddress(FROM));
            message.setRecipient(RecipientType.TO, new InternetAddress(email));
            message.setContent(username + "先生/女士您好，请点击此链接激活账号" + "<a target='_BLANK' href='" + url + "'>" + url + "</a>", "text/html;charset=utf-8");
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean sendAccountActivateEmailToYzm(String email, String code, String username) {
        Session session = getSession();
        MimeMessage message = new MimeMessage(session);
        try {
            message.setSubject("这是一封激活账号的邮件");
            message.setSentDate(new Date());
            //setFrom 表示用哪个邮箱发送邮件
            message.setFrom(new InternetAddress(FROM));
            message.setRecipient(RecipientType.TO, new InternetAddress(email));
            message.setContent(username + "先生/女士您好，您的验证码为：" + code + "，验证码五分钟内有效", "text/html;charset=utf-8");
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static Session getSession() {
        Properties props = new Properties();
        props.setProperty(PROTOCOL_KEY, PROTOCOL_VALUE);//指定发送的邮箱的邮箱协议
        props.setProperty(HOST_KEY, HOST_VALUE);//指定SMTP服务器
        props.setProperty(PORT_KEY, PORT_VALUE);  //smtp是发信邮件服务器,端口是25
        props.setProperty(AUTH_KEY, AUTH_VALUE);//指定是否需要SMTP验证

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                try {
                    return new PasswordAuthentication(FROM, AESUtil.aesDecrypt(EMAIL_PASSWORD));//该地方是填写客户端的授权码
                } catch (Exception e) {
                    return new PasswordAuthentication(FROM, "123456");//该地方是填写客户端的授权码
                }
            }
        });
        return session;
    }
}
