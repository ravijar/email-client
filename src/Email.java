package src;

import java.io.Serializable;

public class Email implements Serializable {

  private String recipientEmail;
  private String subject;
  private String content;
  private String sentDate;

  public Email(
    String recipientEmail,
    String subject,
    String content,
    String sentDate
  ) {
    this.recipientEmail = recipientEmail;
    this.subject = subject;
    this.content = content;
    this.sentDate = sentDate;
  }

  public String getRecipientEmail() {
    return recipientEmail;
  }

  public String getSubject() {
    return subject;
  }

  public String getContent() {
    return content;
  }

  public String getSentDate() {
    return sentDate;
  }
}
