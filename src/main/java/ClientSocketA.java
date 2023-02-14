import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class ClientSocketA extends Thread {

  public ClientSocketA() throws IOException {


  }

  @Override
  public void run() {
    try {
      Socket socket = new Socket("localhost", 8888);
      InputStream input = socket.getInputStream();
      OutputStream output = socket.getOutputStream();
      while (true) {
        output.write("GET /index.html HTTP/1.1\r\n".getBytes());
        output.write("Host: localhost\r\n".getBytes());
        output.write("\r\n".getBytes());

        // Read the server's response
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line;
        while ((line = reader.readLine()) != null) {
          System.out.println(line);
        }
        // Wait for some time before sending the next request
        Thread.sleep(1000);
        socket.close();
      }
    } catch (InterruptedException | IOException e) {
      throw new RuntimeException(e);
    }
  }
}
