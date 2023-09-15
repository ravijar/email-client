package src;

public class PersonalRecipient extends Recipient implements Wishable {

  private String nickName;
  private String birthday;

  public PersonalRecipient(
    String name,
    String nickName,
    String email,
    String birthday
  ) {
    super(name, email);
    this.nickName = nickName;
    this.birthday = birthday;
  }

  @Override
  public String getBirthday() {
    return birthday;
  }

  public void setBirthday(String birthday) {
    this.birthday = birthday;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  @Override
  public Email wishOnBirthday(String date) {
    String content = "Hugs and love on your birthday.\n-Ravija-";
    return sendEmail(email, content, date);
  }
}