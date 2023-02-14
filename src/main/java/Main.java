import java.io.IOException;

public class Main {

  private static final int port = 8888;


  public static void main(String[] args) throws IOException {
    new Server(port);
  }
}
