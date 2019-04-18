package com.pan.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


/**
 * @author panzhigao
 * 邮件工具类
 */
public class EmailUtils {

    private static final Logger logger = LoggerFactory.getLogger(EmailUtils.class);

    private static JavaMailSender javaMailSender;

    public static void setJavaMailSender(JavaMailSender javaMailSender) {
        EmailUtils.javaMailSender = javaMailSender;
    }

    public static String sendMail(String text, String subject,String formUser,String toUser, Boolean type) {
        // 创建邮件对象
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mMessageHelper;
        try {
            // 从配置文件中拿到发件人邮箱地址
            mMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            // 发件人邮箱
            mMessageHelper.setFrom(formUser);
            // 收件人邮箱
            mMessageHelper.setTo(toUser);
            // 邮件的主题也就是邮件的标题
            mMessageHelper.setSubject(subject);
            // 邮件的文本内容，true表示文本以html格式打开
            if (type) {
                mMessageHelper.setText(text, true);
            } else {
                mMessageHelper.setText(text, false);
            }
            // 发送邮件
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            logger.error("发送邮件失败",e);
        }
        return "发送成功";
    }

}
