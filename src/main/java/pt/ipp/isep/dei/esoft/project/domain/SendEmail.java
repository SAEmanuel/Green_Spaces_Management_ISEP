package pt.ipp.isep.dei.esoft.project.domain;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.Session;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import com.sun.mail.smtp.SMTPTransport;

public class SendEmail {
    private static Properties config;

    // Load email configuration from properties file
    private static void loadEmailConfig(String filePath) throws IOException {
        config = new Properties();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            config.load(fis);
        }
    }

    // Send email using the specified service
    public static void sendEmail(String filePath, String serviceName, String[] toEmails, String subject, String body) throws IOException {
        loadEmailConfig(filePath);

        String defaultService = config.getProperty("email.default_service");
        String servicePrefix = serviceName != null ? serviceName : defaultService;

        String smtpServer = config.getProperty(servicePrefix + ".smtp_server");
        int port = Integer.parseInt(config.getProperty(servicePrefix + ".port"));
        boolean useTls = Boolean.parseBoolean(config.getProperty(servicePrefix + ".use_tls"));
        boolean useSsl = Boolean.parseBoolean(config.getProperty(servicePrefix + ".use_ssl"));
        String username = config.getProperty(servicePrefix + ".username");
        String password = config.getProperty(servicePrefix + ".password");

        Properties props = new Properties();
        props.put("mail.smtp.host", smtpServer);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        if (useTls) {
            props.put("mail.smtp.starttls.enable", "true");
        }
        if (useSsl) {
            props.put("mail.smtp.socketFactory.port", port);
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        }


        System.out.println();
        System.out.println(smtpServer);
        System.out.println(port);
        System.out.println(useTls);
        System.out.println(useSsl);
        System.out.println(username);
        System.out.println(password);
        System.out.println();
        System.out.println(serviceName);
        for (String s : toEmails)
            System.out.println(s);
        System.out.println(subject);
        System.out.println(body);

//        System.out.println("oi2");
//
//        Session session = Session.getInstance(props, null);
//        MimeMessage msg = new MimeMessage(session);
//        msg.setFrom(new InternetAddress(username));
//        for (String toEmail : toEmails) {
//            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
//        }
//        msg.setSubject(subject);
//        msg.setText(body);
//        System.out.println("oi3");
//
//        SMTPTransport transport = (SMTPTransport) session.getTransport("smtp");
//        transport.connect(smtpServer, username, password);
//        System.out.println("oi4");
//
//        transport.sendMessage(msg, msg.getAllRecipients());
//        System.out.println("oi5");
//
//        transport.close();

    }
}
