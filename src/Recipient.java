package src;

public abstract class Recipient {

  protected String name;
  protected String email;
  private static int count;

  public Recipient(String name, String email) {
    this.name = name;
    this.email = email;
    count += 1;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public static int getCount() {
    return count;
  }
}
