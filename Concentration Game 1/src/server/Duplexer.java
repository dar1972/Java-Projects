package server;

import java.io.*;
import java.net.Socket;

/**
 * Author: Dhruv Rajpurohit
 * Description: The Code is the duplexer of the Concentration game
 */
public class Duplexer implements AutoCloseable{
    private PrintWriter out;
    private Socket socket;
    private BufferedReader reader;

    /**
     * The constructor of duplexer
     * The function makes an instance of socket
     * @param socket socket for networking
     * @throws IOException
     */
    public Duplexer(Socket socket) throws IOException {
        this.socket = socket;

        OutputStream output = socket.getOutputStream();
        writer = new PrintWriter(output);

        InputStream input = socket.getInputStream();
        InputStreamReader iReader = new InputStreamReader(input);
        reader = new BufferedReader(iReader);

    }

    /**
     * The function send messages
     * @param message
     */
    public static void send(String message){
        System.out.println("Sending: " + message);
        writer.println(message);
        writer.flush();
    }

    /**
     * The function receives messages
     * @return
     * @throws IOException
     */
    public String receive() throws IOException {
        String response = reader.readLine();
        System.out.println("received" + response);
        return reader.readLine();
    }

    @Override
    public void close() throws Exception {
        socket.close();
    }
}
