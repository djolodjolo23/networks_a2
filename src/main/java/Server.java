import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {


  public Server(int port) throws IOException {
    run(port);
  }
  private void run(int port) throws IOException {
    try (ServerSocket serverSocket = new ServerSocket(port)) {
      // ready to receive messages
      System.out.println("Server has started.\nListening on port " + port);
      while (true) {
        // client that's accepted
        try (Socket client = serverSocket.accept()) {
          // client and messages queued up
          System.out.println("Debug message: got a new message " + client.toString());

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
          //System.out.println("--- REQUEST ---");
          //System.out.println(requestBuilder);

          // TODO: How to respond?
          // Get the first line of the request
          // Get the second thing 'resource' from the first line
          // Compare the 'resource' to ouyr list of things
          // send back the appropriate thing based on the resource

          // Get the first line of the request
          String firstline =  requestBuilder.toString().split("\n")[0];
          // Get the second thing 'resource' from the first line
          String resource = firstline.split(" ")[1];
          if (resource.equals("/stuff.html")) {
            FileInputStream image = new FileInputStream("public/joke.png");
            System.out.println(image);
            OutputStream clientOutput = client.getOutputStream();
            clientOutput.write(("HTTP/1.1 200 OK\r\n").getBytes());
            clientOutput.write(("\r\n").getBytes());
            clientOutput.write(image.readAllBytes());
            clientOutput.flush();

          } else if (resource.equals("/hello")) {
            OutputStream clientOutput = client.getOutputStream();
            clientOutput.write(("HTTP/1.1 200 OK\r\n").getBytes());
            clientOutput.write(("\r\n").getBytes());
            clientOutput.write(("Hello world!").getBytes());
            clientOutput.flush();
          } else {
            OutputStream clientOutput = client.getOutputStream();
            clientOutput.write(("HTTP/1.1 200 OK\r\n").getBytes());
            clientOutput.write(("\r\n").getBytes());
            clientOutput.write(("What are you looking for???????").getBytes());
            clientOutput.flush();
          }
          //System.out.println(resource);





          // send back an image???
          // Load the image from the file system
          // turn the image into bytes???
          // Set the ContentType?
          /**
           FileInputStream image = new FileInputStream("joke.png");
           System.out.println(image);

           OutputStream clientOutput = client.getOutputStream();
           clientOutput.write(("HTTP/1.1 200 OK\r\n").getBytes());
           clientOutput.write(("\r\n").getBytes());
           clientOutput.write(image.readAllBytes());
           clientOutput.flush();
           */


          client.close();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    }
  }


}
