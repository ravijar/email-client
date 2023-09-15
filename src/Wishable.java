package src;

public interface Wishable {
  public String getBirthday();

  public Email wishOnBirthday(String date);

  public default Email sendEmail(
    String recipientEmail,
    String content,
    String date
  ) {
    String subject = "Birthday Wish";
    EmailHandler emailHandler = new EmailHandler();
    boolean isSent = emailHandler.sendEmail(recipientEmail, subject, content);
    Email email = null;
    if (isSent) {
      email = new Email(recipientEmail, subject, content, date);
    }
    return email;
  }
}
