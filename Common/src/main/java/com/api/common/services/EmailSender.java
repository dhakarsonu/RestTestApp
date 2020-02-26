package com.api.common.services;

import com.api.common.utils.ObjUtil;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Set;

public class EmailSender {

    private InternetAddress fromEmailAddress;
    private Set<InternetAddress> toEmailAddress, ccEmailAddress;
    private String subject;
    private String message, contentType;

    public EmailSender() {
    }

    public EmailSender(String fromAddress, String fromName, String subject) throws UnsupportedEncodingException {
        this.setFromAddress(fromAddress, fromName);
        this.setSubject(subject);
    }

    public EmailSender setFromAddress(String fromAddress, String fromName) throws UnsupportedEncodingException {
        if (ObjUtil.isNullOrEmpty(fromAddress)) {
            fromEmailAddress = null;
            return this;
        }
        fromEmailAddress = new InternetAddress(fromAddress, ObjUtil.nullToEmpty(fromName));
        return this;
    }

    public EmailSender setSubject(String emailSubject) {

        this.subject = ObjUtil.nullToEmpty(emailSubject);
        return this;
    }

    public EmailSender setMessage(String msg, String contentType) {

        this.message = ObjUtil.nullToEmpty(msg);
        this.contentType = ObjUtil.isNullOrEmpty(contentType) ? "text/html" : contentType.trim();
        return this;
    }

    public EmailSender addToAddress(String emailAddress) throws AddressException {
        if (ObjUtil.isNullOrEmpty(toEmailAddress))
            toEmailAddress = new HashSet<>();

        if (!ObjUtil.isNullOrEmpty(emailAddress))
            toEmailAddress.add(new InternetAddress(emailAddress));
        return this;
    }

    public EmailSender addAllToAddress(Collection<String> emailAddresses) throws AddressException {
        if (ObjUtil.isNullOrEmpty(emailAddresses))
            return this;

        if (ObjUtil.isNullOrEmpty(toEmailAddress))
            toEmailAddress = new HashSet<>();

        for (String emailAddress : emailAddresses) {
            if (!ObjUtil.isNullOrEmpty(emailAddress))
                toEmailAddress.add(new InternetAddress(emailAddress));
        }
        return this;
    }

    public EmailSender addCCAddress(String emailAddress) throws AddressException {
        if (ObjUtil.isNullOrEmpty(ccEmailAddress))
            ccEmailAddress = new HashSet<>();

        if (!ObjUtil.isNullOrEmpty(emailAddress))
            ccEmailAddress.add(new InternetAddress(emailAddress));
        return this;
    }

    public boolean send() throws Exception {
        if (fromEmailAddress == null || ObjUtil.isNullOrEmpty(toEmailAddress))
            throw new IllegalArgumentException("Invalid From / To Address");

        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        Message msg = new MimeMessage(session);

        msg.setFrom(fromEmailAddress);

        for (InternetAddress toAddress : toEmailAddress) {
            msg.addRecipient(Message.RecipientType.TO, toAddress);
        }

        if (!ObjUtil.isNullOrEmpty(ccEmailAddress)) {
            for (InternetAddress ccAddress : ccEmailAddress) {
                msg.addRecipient(Message.RecipientType.CC, ccAddress);
            }
        }

        msg.setSubject(subject);

        Multipart mp = new MimeMultipart();
        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(message, contentType);
        mp.addBodyPart(htmlPart);
        msg.setContent(mp);
        Transport.send(msg);
        return true;

    }

}
