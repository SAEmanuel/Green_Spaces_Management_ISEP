package pt.ipp.isep.dei.esoft.project.domain;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
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
    private static void loadEmailConfig() throws IOException {
        config = new Properties();
        try (FileInputStream fis = new FileInputStream("src/main/resources/config.properties")) {
            config.load(fis);
        }
    }

    // Send email using the specified service
    public static void sendEmail(String serviceName, List<String> toEmails, String subject, String body) throws IOException {
        loadEmailConfig();

        String defaultService = config.getProperty("email_service.email.default_service");
        serviceName = serviceName != null ? "email_service." + serviceName : null;
        String convertToPrefix = config.getProperty(serviceName + ".smtp_server_name");
        String servicePrefix = serviceName != null ? "email_service." + convertToPrefix : "email_service." + defaultService;

        String smtpServer = config.getProperty(servicePrefix + ".smtp_server");
        int port = Integer.parseInt(config.getProperty(servicePrefix + ".port"));
        boolean useTls = Boolean.parseBoolean(config.getProperty(servicePrefix + ".use_tls"));
        boolean useSsl = Boolean.parseBoolean(config.getProperty(servicePrefix + ".use_ssl"));

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

        try {
            BufferedWriter writer = null;
            try {
                // Create BufferedWriter object
                writer = new BufferedWriter(new FileWriter("src/main/java/pt/ipp/isep/dei/esoft/project/ui/email_log.txt"));
                // Write data to the file
                writer.write(smtpServer);
                writer.newLine();
                writer.write(String.valueOf(port));
                writer.newLine();
                writer.write(String.valueOf(useTls));
                writer.newLine();
                writer.write(String.valueOf(useSsl));
                writer.newLine();
                writer.newLine();
                writer.write(serviceName);
                writer.newLine();
                StringBuilder emailStringBuilder = new StringBuilder();
                for (int i = 0; i < toEmails.size(); i++) {
                    if (i > 0) {
                        emailStringBuilder.append(", ");
                    }
                    emailStringBuilder.append(toEmails.get(i));
                }
                writer.write(emailStringBuilder.toString());
                writer.newLine();
                writer.write(subject);
                writer.newLine();
                writer.write(body);
            }finally {
                writer.flush();
                writer.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

//        System.out.println();
//        System.out.println(smtpServer);
//        System.out.println(port);
//        System.out.println(useTls);
//        System.out.println(useSsl);
//        System.out.println();
//        System.out.println(serviceName);
//        for (int i = 0; i < toEmails.size(); i++) {
//            if(i == 0)
//                System.out.printf(toEmails.get(i));
//            else
//                System.out.printf(", " + toEmails.get(i));
//        }
//        System.out.println();
//        System.out.println(subject);
//        System.out.println(body);

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

    public static List<String> getEmailServices() throws IOException {
        loadEmailConfig();
        List<String> emailServices = new ArrayList<>();

        for (String key : config.stringPropertyNames()) {
            if (key.startsWith("email_service.") && key.endsWith("smtp_server")) {
                String value = config.getProperty(key);
                emailServices.add(String.valueOf(value.subSequence(5,value.length())));
            }
        }

        return emailServices;
    }
}
