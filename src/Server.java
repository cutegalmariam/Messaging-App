import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public class Server {
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(9999);
        while (true) {
            try {

                Socket socket = serverSocket.accept();

                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();

                while (true){
                    String messageToRead = String.valueOf(inputStream.read());
                    System.out.println("Client: "+messageToRead);
                    outputStream.write(("Message Received" + "\n").getBytes());
                    outputStream.flush();

                    if(Objects.equals(messageToRead, "EXIT")){
                        break;
                    }
                    socket.close();
                    inputStream.close();
                    outputStream.close();
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
