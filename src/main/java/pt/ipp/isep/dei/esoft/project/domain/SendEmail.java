package pt.ipp.isep.dei.esoft.project.domain;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.ANSI_BRIGHT_RED;
import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.ANSI_RESET;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.Session;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import com.sun.mail.smtp.SMTPTransport;

public class SendEmail {
    private static Properties config;

    /**
     * Loads the email configuration from the properties file.
     *
     * @throws IOException if there is an error reading the properties file
     */
    private static void loadEmailConfig() throws IOException {
        config = new Properties();
        try (FileInputStream fis = new FileInputStream("src/main/resources/config.properties")) {
            config.load(fis);
        }
    }

    /**
     * Sends an email using the specified service.
     *
     * @param serviceName the name of the email service to use (can be null for default service)
     * @param toEmails the list of recipient email addresses
     * @param subject the subject of the email
     * @param body the body of the email
     * @throws IOException if there is an error loading the email configuration
     */
    public static boolean sendEmail(String serviceName, List<String> toEmails, String subject, String body) throws IOException {
        loadEmailConfig();

        try {
            serviceName = serviceName != null ? "email_service." + serviceName : null;
            String convertToPrefix = config.getProperty(serviceName + ".smtp_server_name");
            String servicePrefix = "email_service." + convertToPrefix;

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
                return true;
            }
            catch (IOException e) {
                System.out.println(ANSI_BRIGHT_RED + "Sending Email processes didn't complete (Writting to file)" + ANSI_RESET);
            };
            return false;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * Retrieves the available email services from the configuration.
     *
     * @return a list of available email service names
     * @throws IOException if there is an error loading the email configuration
     */
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
