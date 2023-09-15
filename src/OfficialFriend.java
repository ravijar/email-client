package src;

public class OfficialFriend extends OfficialRecipient implements Wishable {

  private String birthday;

  public OfficialFriend(
    String name,
    String email,
    String designation,
    String birthday
  ) {
    super(name, email, designation);
    this.birthday = birthday;
  }

  @Override
  public String getBirthday() {
    return birthday;
  }

  public void setBirthday(String birthday) {
    this.birthday = birthday;
  }

  @Override
  public Email wishOnBirthday(String date) {
    String content = "Wish you a Happy Birthday.\n-Ravija-";
    return sendEmail(email, content, date);
  }
}
