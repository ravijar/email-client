package src;

public class OfficialRecipient extends Recipient {

  protected String designation;

  public OfficialRecipient(String name, String email, String designation) {
    super(name, email);
    this.designation = designation;
  }

  public String getDesignation() {
    return designation;
  }

  public void setDesignation(String designation) {
    this.designation = designation;
  }
}
