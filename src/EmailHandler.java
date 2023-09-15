package src;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailHandler {

  private final String userName = "";
  private final String password = "";

  public boolean sendEmail(String recipient, String subject, String content) {
    Properties properties = new Properties();
    properties.put("mail.smtp.host", "smtp.gmail.com");
    properties.put("mail.smtp.port", "587");
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", "true"); //TLS
    Session session = Session.getInstance(
      properties,
      new javax.mail.Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(userName, password);
        }
      }
    );
    try {
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(userName));
      message.setRecipients(
        Message.RecipientType.TO,
        InternetAddress.parse(recipient)
      );
      message.setSubject(subject);
      message.setText(content);
      Transport.send(message);
    } catch (MessagingException e) {
      System.out.println(e.getMessage() + "\n");
      return false;
    }
    return true;
  }
}
