import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 9999);
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            Scanner scanner = new Scanner(System.in);

            // Thread to handle incoming messages from server
            new Thread(() -> {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String messageFromServer;
                    while ((messageFromServer = reader.readLine()) != null) {
                        System.out.println("Server: " + messageFromServer);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Main thread to handle outgoing messages to server
            while (true) {
                String messageToSend = scanner.nextLine();
                PrintWriter writer = new PrintWriter(outputStream, true);
                writer.println(messageToSend);

                if (Objects.equals(messageToSend, "EXIT")) {
                    break;
                }
            }
            socket.close();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
