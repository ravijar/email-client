package src;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class EmailClient {

  public static void main(String[] args) {
    ArrayList<Recipient> recipients = new ArrayList<Recipient>();
    ArrayList<Wishable> birthdayRecipients = new ArrayList<Wishable>();
    ArrayList<Wishable> birthdayRecipientsTemp = new ArrayList<Wishable>();
    ArrayList<Email> sentEmails = new ArrayList<Email>();
    boolean programState = true;
    Scanner scanner = new Scanner(System.in);
    String date = LocalDate
      .now()
      .format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

    System.out.println("Starting Email Client...\n");
    // Load recipient data to the program
    loadRecipientData(recipients, birthdayRecipients);
    // Load sent email data to the program
    loadEmails(sentEmails);
    TextFileHandler logTextFileHandler = new TextFileHandler("log.txt");
    ArrayList<String> logData = logTextFileHandler.read();
    String lastWishedDate = "";
    if (!logData.isEmpty()) {
      lastWishedDate = logData.get(0);
    }
    if (!lastWishedDate.equals(date)) {
      // Send birthday wishes to the recipients having birthday on the current date,
      // only if birthday wishes were not sent on the current date.
      System.out.println("Checking for birthday recipients...\n");
      sendBirthdayWishes(date, birthdayRecipients, sentEmails);
      // storing the final access date on log.txt
      logTextFileHandler.write(date, false);
    }
    while (programState) {
      System.out.println(
        "Enter option type: \n" +
        "1 - Adding a new recipient\n" +
        "2 - Sending an email\n" +
        "3 - Printing out all the recipients who have birthdays\n" +
        "4 - Printing out details of all the emails sent\n" +
        "5 - Printing out the number of recipient objects in the application\n" +
        "6 - Exit the program"
      );
      int option = 0;
      try {
        option = scanner.nextInt();
      } catch (InputMismatchException e) {
        System.out.println("Invalid Input!\n");
      }
      scanner.nextLine();
      switch (option) {
        case 1:
          // Add a new recipient
          // input format - <Recipient type>: <Recipient's details separated with ",">
          System.out.print("Enter recipient data : ");
          String data = scanner.nextLine();
          Recipient recipient = null;
          try {
            recipient = createRecipient(data);
          } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid input format!\n");
          }
          if (recipient != null) {
            recipients.add(recipient);
            TextFileHandler recipientTextFileHandler = new TextFileHandler(
              "clientList.txt"
            );
            recipientTextFileHandler.write(data, true);
            System.out.println("New Recipient added!\n");
          }
          if (recipient instanceof Wishable) {
            Wishable birthdayRecipient = (Wishable) recipient;
            birthdayRecipients.add(birthdayRecipient);
            // storing newly added birthday recipients temporarily
            birthdayRecipientsTemp.add(birthdayRecipient);
          }
          break;
        case 2:
          // Send an email
          // input format - <Recipient's email>, <subject>, <content>
          System.out.print("Enter email data : ");
          String[] emailInfo = getSplitData(scanner.nextLine());
          if (emailInfo.length == 3) {
            EmailHandler emailHandler = new EmailHandler();
            boolean isSent = emailHandler.sendEmail(
              emailInfo[0],
              emailInfo[1],
              emailInfo[2]
            );
            if (isSent) {
              Email email = new Email(
                emailInfo[0],
                emailInfo[1],
                emailInfo[2],
                date
              );
              sentEmails.add(email);
              saveEmail(email);
              System.out.println(
                "Email sent to " + emailInfo[0] + " successfully!\n"
              );
            }
          } else {
            System.out.println("Invalid input format!\n");
          }
          break;
        case 3:
          // Print recipients who have birthdays on the given date
          // input format - yyyy/MM/dd
          System.out.print("Enter the date : ");
          String inputDate = scanner.nextLine().substring(5);
          System.out.println("Recipients with birthday on " + inputDate + " :");
          for (Wishable birthdayRecipient : birthdayRecipients) {
            if (
              birthdayRecipient.getBirthday().substring(5).equals(inputDate)
            ) {
              recipient = (Recipient) birthdayRecipient;
              System.out.println("\t" + recipient.getName());
            }
          }
          System.out.println();
          break;
        case 4:
          // Print the details of all the emails sent on the input date
          // input format - yyyy/MM/dd
          System.out.print("Enter the date : ");
          inputDate = scanner.nextLine();
          System.out.println("Emails sent on " + inputDate + " :");
          for (Email email : sentEmails) {
            if (email.getSentDate().equals(inputDate)) {
              System.out.println("\tSubject : " + email.getSubject());
              System.out.println(
                "\tRecipient : " + email.getRecipientEmail() + "\n"
              );
            }
          }
          System.out.println();
          break;
        case 5:
          // Print the number of recipient objects in the application
          System.out.println(
            "Number of Recipients : " + Recipient.getCount() + "\n"
          );
          break;
        case 6:
          // Exit the program
          if (birthdayRecipientsTemp.size() != 0) {
            // checks for any newly added birthday recipients to send birthday wishes.
            System.out.println("Checking for new birthday recipients...\n");
            sendBirthdayWishes(date, birthdayRecipientsTemp, sentEmails);
          }
          System.out.println("Exiting the program...");
          programState = false;
          break;
      }
    }
    scanner.close();
  }

  public static String[] getSplitData(String data) {
    // splits a string by "," and returns them
    String[] splitData = data.split(",");
    for (int i = 0; i < splitData.length; i++) {
      splitData[i] = splitData[i].trim();
    }
    return splitData;
  }

  public static Recipient createRecipient(String data)
    throws ArrayIndexOutOfBoundsException {
    // creates a recipient according to the input
    String[] splitData = data.split(":");
    String recipientType = splitData[0].trim();
    String[] recipientInfo = getSplitData(splitData[1]);
    Recipient recipient = null;
    switch (recipientType) {
      case "Official":
        recipient =
          new OfficialRecipient(
            recipientInfo[0],
            recipientInfo[1],
            recipientInfo[2]
          );
        break;
      case "Office_friend":
        recipient =
          new OfficialFriend(
            recipientInfo[0],
            recipientInfo[1],
            recipientInfo[2],
            recipientInfo[3]
          );
        break;
      case "Personal":
        recipient =
          new PersonalRecipient(
            recipientInfo[0],
            recipientInfo[1],
            recipientInfo[2],
            recipientInfo[3]
          );
        break;
    }
    return recipient;
  }

  public static void loadRecipientData(
    ArrayList<Recipient> recipients,
    ArrayList<Wishable> birthdayRecipients
  ) {
    // loads recipient data stored in hdd to the program
    TextFileHandler textFileHandler = new TextFileHandler("clientList.txt");
    ArrayList<String> recipientData = textFileHandler.read();
    for (String data : recipientData) {
      Recipient recipient = createRecipient(data);
      if (recipient != null) {
        recipients.add(recipient);
      }
      if (recipient instanceof Wishable) {
        birthdayRecipients.add((Wishable) recipient);
      }
    }
  }

  public static void sendBirthdayWishes(
    String date,
    ArrayList<Wishable> birthdayRecipients,
    ArrayList<Email> sentEmails
  ) {
    // sends emails to recipients having birthday on the current day
    for (Wishable recipient : birthdayRecipients) {
      if (recipient.getBirthday().substring(5).equals(date.substring(5))) {
        Email email = recipient.wishOnBirthday(date);
        if (email != null) {
          sentEmails.add(email);
          saveEmail(email);
          System.out.println(
            "Birthday wish sent to " + email.getRecipientEmail() + "!\n"
          );
        }
      }
    }
  }

  public static void saveEmail(Email email) {
    // saves sent emails on hdd
    ObjectFileHandler objectFileHandler = new ObjectFileHandler("Emails.ser");
    objectFileHandler.saveObject(email);
  }

  public static void loadEmails(ArrayList<Email> sentEmails) {
    // loads saved emails on hdd to the program
    ArrayList<Object> Objects = new ObjectFileHandler("Emails.ser")
      .loadObjects();
    for (Object object : Objects) {
      sentEmails.add((Email) object);
    }
  }
}
