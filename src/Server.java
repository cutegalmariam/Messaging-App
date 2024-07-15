import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(9999);
        while (true) {
            try {
                Socket socket = serverSocket.accept();

                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
                Scanner scanner = new Scanner(System.in);

                // Thread to handle incoming messages from client
                new Thread(() -> {
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        String messageFromClient;
                        while ((messageFromClient = reader.readLine()) != null) {
                            System.out.println("Client: " + messageFromClient);
                            if (Objects.equals(messageFromClient, "EXIT")) {
                                break;
                            }
                        }
                        serverSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();

                // Thread to handle outgoing messages to client
                new Thread(() -> {
                    try {
                        PrintWriter writer = new PrintWriter(outputStream, true);
                        while (true) {
                            String messageToSend = scanner.nextLine();
                            writer.println(messageToSend);
                            if (Objects.equals(messageToSend, "EXIT")) {
                                break;
                            }
                        }
                        serverSocket.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
