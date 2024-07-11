import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try{
            Socket socket = new Socket("localhost", 9999 );
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            Scanner scanner = new Scanner(System.in);

            new Thread(() -> {
                try {
                    Scanner inScanner = new Scanner(inputStream);
                    while (inScanner.hasNextLine()) {
                        System.out.println(inScanner.nextLine());
                    }
                } catch (Exception e) {
                    System.err.println("Error reading from server: " + e.getMessage());
                }
            }).start();

            // Write to server
            while (true) {
                String messageToSend = scanner.nextLine();
                outputStream.write((messageToSend + "\n").getBytes());
                outputStream.flush();

                if (scanner.hasNextLine()) {
                    System.out.println("Server: " + scanner.nextLine());
                }

                if(Objects.equals(messageToSend, "EXIT")){
                    break;
                }
            }
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
