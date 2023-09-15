package src;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ObjectFileHandler {

  private File file;

  public ObjectFileHandler(String fileName) {
    this.file = new File(fileName);
  }

  public void saveObject(Object object) {
    try {
      FileOutputStream fileOutputStream = new FileOutputStream(file, true);
      if (file.length() == 0) {
        // built in objectOutputStream can be used for the first object
        // appending to the file.
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
          fileOutputStream
        );
        objectOutputStream.writeObject(object);
        objectOutputStream.close();
      } else {
        // to avoid the header created by the built in objectOutputStream
        // when appending more than one object to the same file,
        // a custom objectOutputStream is used.
        CustomObjectOutputStream customObjectOutputStream = new CustomObjectOutputStream(
          fileOutputStream
        );
        customObjectOutputStream.writeObject(object);
        customObjectOutputStream.close();
      }
    } catch (IOException e) {
      System.out.println(e.getMessage() + "\n");
    }
  }

  public ArrayList<Object> loadObjects() {
    ArrayList<Object> objects = new ArrayList<Object>();
    if (file.length() != 0) {
      try {
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(
          fileInputStream
        );
        while (fileInputStream.available() != 0) {
          Object object = objectInputStream.readObject();
          objects.add(object);
        }
        objectInputStream.close();
      } catch (IOException | ClassNotFoundException e) {
        System.out.println(e.getMessage() + "\n");
      }
    }
    return objects;
  }
}
