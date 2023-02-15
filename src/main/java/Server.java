import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server implements IntegerChecker{


  public Server(int port) throws IOException {

  }
  private void run(String[] args) throws IOException {
    try (ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]))) {
      if (args.length != 2) {
        System.err.println("There must be two program arguments, the listening port and a relative folder path");
        System.exit(1);
      } else if (IntegerChecker.integerCheck(args[0])) {
        System.err.println("error");
      }
      // ready to receive messages
      System.out.println("Server has started.\nListening on port " + args[0]);
      while (true) {
        // client that's accepted
        // can accept multiple connections since in while(true) loop
        try (Socket client = serverSocket.accept()) {
          // client and messages queued up
          //System.out.println("Debug message: got a new message " + client.toString());

          // read the requests and listen to the message
          InputStreamReader inputStreamReader = new InputStreamReader(client.getInputStream());

          BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

          // read the first request from the client
          StringBuilder requestBuilder = new StringBuilder();

          String line = bufferedReader.readLine(); // holds one line at the time
          while (!line.isBlank()) {
            requestBuilder.append(line).append("\n");
            line = bufferedReader.readLine();
          }
          System.out.println("--- REQUEST ---");
          System.out.println(requestBuilder);

          // Get the first line of the request
          String firstline =  requestBuilder.toString().split("\n")[0];
          // Get the second thing 'resource' from the first line
          String resource = firstline.split(" ")[1];
          if (resource.equals("/joke.png") || resource.equals("/joke.png/")) {
            FileInputStream image = new FileInputStream("public/joke.png");
            System.out.println(image);
            OutputStream clientOutput = client.getOutputStream();
            clientOutput.write(("HTTP/1.1 200 OK\r\n").getBytes());
            clientOutput.write(("\r\n").getBytes());
            clientOutput.write(image.readAllBytes());
            clientOutput.flush();

          } else if (resource.equals("/hello.html") || resource.equals("/hello.html/")) {
            OutputStream clientOutput = client.getOutputStream();
            clientOutput.write(("HTTP/1.1 200 OK\r\n").getBytes());
            clientOutput.write(("\r\n").getBytes());
            clientOutput.write(("Hello world!").getBytes());
            clientOutput.flush();
          } else {
            OutputStream clientOutput = client.getOutputStream();
            clientOutput.write(("HTTP/1.1 200 OK\r\n").getBytes());
            clientOutput.write(("\r\n").getBytes());
            clientOutput.write(("THIS IS THE HOMEPAGE OF YOUR WEBSERVER!!!\n\n").getBytes());
            clientOutput.write(("Type /joke.png to see the joke.png file.\n").getBytes());
            clientOutput.write(("Type hello.html to see the hello.html message.\n").getBytes());
            clientOutput.flush();
          }

        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    }
  }


}
