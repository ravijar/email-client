package src;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class CustomObjectOutputStream extends ObjectOutputStream {

  public CustomObjectOutputStream() throws IOException {
    super();
  }

  public CustomObjectOutputStream(OutputStream outputStream)
    throws IOException {
    super(outputStream);
  }

  public void writeStreamHeader() throws IOException {
    // writeStreamHeader() method is overridden and kept blank
    // to avoid writing the header.
    return;
  }
}
