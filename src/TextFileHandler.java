package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TextFileHandler {

  private File file;

  public TextFileHandler(String fileName) {
    this.file = new File(fileName);
  }

  public void write(String data, boolean append) {
    try {
      FileWriter fileWriter = new FileWriter(file, append);
      fileWriter.write(data + "\n");
      fileWriter.close();
    } catch (IOException e) {
      System.out.println(e.getMessage() + "\n");
    }
  }

  public ArrayList<String> read() {
    ArrayList<String> data = new ArrayList<String>();
    String line;
    try {
      FileReader fileReader = new FileReader(file);
      // a buffered reader is used as this method reads the whole file.
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      while ((line = bufferedReader.readLine()) != null) {
        data.add(line);
      }
      bufferedReader.close();
    } catch (IOException e) {
      System.out.println(e.getMessage() + "\n");
    }
    return data;
  }
}
