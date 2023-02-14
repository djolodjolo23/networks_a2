import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

  private static final int port = 8888;

  public static void main(String[] args) throws IOException {

    try (ServerSocket serverSocket = new ServerSocket(port)) {
      System.out.println("Server has started.\nListening on port " + port);
      while (true) {
        // client that's accepted
        try (Socket client = serverSocket.accept()) {
          // simple debug message
          System.out.println("Debug message: got a new message " + client.toString());

          InputStreamReader inputStreamReader = new InputStreamReader(client.getInputStream());

          BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

          //
          StringBuilder requestBuilder = new StringBuilder();

          String line = bufferedReader.readLine(); // holds one line at the time
          while (!line.isBlank()) {
            requestBuilder.append(line);
            line = bufferedReader.readLine();
          }
          System.out.println("--- REQUEST ---");
          System.out.println(requestBuilder);

          OutputStream clientOutput = client.getOutputStream();
          clientOutput.write(("HTTP/1.1 200 OK\r\n").getBytes());
          clientOutput.write(("\r\n").getBytes());
          clientOutput.write(("Hello world!").getBytes());
          clientOutput.flush();




          client.close();
        }
      }
    }
  }

}
