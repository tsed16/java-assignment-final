package ChatApp;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

    static List<ClientHandler> clients = new ArrayList<>();
    static Set<String> onlineUsers = new HashSet<>();

    public static void main(String[] args) throws Exception {

        System.out.println("Starting server...");
        DB.init();
        DB.loadAndPrintAllMessages();

        ServerSocket server = new ServerSocket(5000);
        System.out.println("✓ Server running on port 5000");

        while (true) {
            Socket socket = server.accept();
            System.out.println("Client connected: " + socket);

            ClientHandler ch = new ClientHandler(socket);
            clients.add(ch);

            new Thread(ch).start();
        }
    }

    static class ClientHandler implements Runnable {

        Socket socket;
        DataInputStream in;
        DataOutputStream out;
        String username;

        ClientHandler(Socket socket) throws Exception {
            this.socket = socket;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        }

        public void run() {
            try {
                username = in.readUTF();

                synchronized (onlineUsers) {
                    onlineUsers.add(username);
                }

                broadcast("TEXT", "🔵 " + username + " joined");

                while (true) {

                    String type = in.readUTF();

                    if (type.equals("TEXT")) {

                        String msg = in.readUTF();

                        System.out.println(username + ": " + msg);

                        DB.saveMessage(username, msg, null, null);

                        broadcast("TEXT", username + ": " + msg);

                    } else if (type.equals("FILE")) {

                        String fileName = in.readUTF();
                        int size = in.readInt();
                        byte[] data = new byte[size];
                        in.readFully(data);

                        System.out.println(username + " sent file: " + fileName);

                        DB.saveMessage(username, "[FILE] " + fileName, fileName, data);

                        broadcastFile(username, fileName, data);
                    }
                }

            } catch (Exception e) {
                System.out.println(username + " disconnected");
            }
        }

        void broadcast(String type, String msg) throws IOException {
            for (ClientHandler c : clients) {
                c.out.writeUTF(type);
                c.out.writeUTF(msg);
            }
        }

        void broadcastFile(String user, String fileName, byte[] data) throws IOException {
            for (ClientHandler c : clients) {
                c.out.writeUTF("FILE");
                c.out.writeUTF(user);
                c.out.writeUTF(fileName);
                c.out.writeInt(data.length);
                c.out.write(data);
            }
        }
    }
}