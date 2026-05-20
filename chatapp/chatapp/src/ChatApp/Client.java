package ChatApp;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Client{

    private DataOutputStream out;
    private DataInputStream in;
    private String username;
    private JTextArea chat;
    private Socket socket;

    public Client() {
        username = JOptionPane.showInputDialog("Enter your name:");
        if (username == null || username.trim().isEmpty()) {
            System.exit(0);
        }

        JFrame frame = new JFrame("Chat - " + username);
        chat = new JTextArea();
        chat.setEditable(false);
        chat.setBackground(new Color(240, 240, 240));
        chat.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JTextField input = new JTextField();
        JButton sendFile = new JButton("Send File");
        JButton refreshBtn = new JButton("Refresh History");

        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(chat), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(input, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(sendFile);
        buttonPanel.add(refreshBtn);
        bottom.add(buttonPanel, BorderLayout.EAST);
        
        frame.add(bottom, BorderLayout.SOUTH);

        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        try {
            socket = new Socket("localhost", 5000);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(username);
            
            chat.append("✓ Connected to server as '" + username + "'\n");
            chat.append("Loading message history...\n");

            // Load message history from database
            loadOldMessages();

            input.addActionListener(e -> {
                String message = input.getText().trim();
                if (!message.isEmpty()) {
                    try {
                        out.writeUTF("TEXT");
                        out.writeUTF(message);
                        input.setText("");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        chat.append("✗ Error sending message: " + ex.getMessage() + "\n");
                    }
                }
            });

            sendFile.addActionListener(e -> sendFile());
            refreshBtn.addActionListener(e -> {
                chat.append("\n--- Reloading message history ---\n");
                loadOldMessages();
            });

            new Thread(this::receive).start();
            
            frame.addWindowListener(new java.awt.event.WindowAdapter() {
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    try {
                        if (socket != null && !socket.isClosed()) {
                            socket.close();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot connect to server!\nMake sure server is running.\nError: " + e.getMessage(), 
                                        "Connection Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
    
    private void appendMessage(String msg) {
        SwingUtilities.invokeLater(() -> {
            chat.append(msg + "\n");
            chat.setCaretPosition(chat.getDocument().getLength());
        });
    }

    void loadOldMessages() {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/chatapp?useSSL=false", 
                    "root", "");

            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT username, message, timestamp FROM messages ORDER BY timestamp ASC LIMIT 100");

            int count = 0;
            while (rs.next()) {
                String user = rs.getString("username");
                String msg = rs.getString("message");
                Timestamp ts = rs.getTimestamp("timestamp");
                
                String timeStr = ts.toLocalDateTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                
                if (msg != null && msg.startsWith("[FILE]")) {
                    appendMessage("[" + timeStr + "] 📎 " + user + " sent file: " + msg);
                } else {
                    appendMessage("[" + timeStr + "] 💬 " + user + ": " + msg);
                }
                count++;
            }
            
            if (count > 0) {
                appendMessage("--- Loaded " + count + " previous messages ---\n");
            } else {
                appendMessage("--- No previous messages found ---\n");
            }

        } catch (ClassNotFoundException e) {
            appendMessage("✗ MySQL JDBC Driver not found!");
            appendMessage("Make sure mysql-connector-j-9.7.0.jar is in the classpath");
            e.printStackTrace();
        } catch (SQLException e) {
            appendMessage("✗ Database error: " + e.getMessage());
            appendMessage("Make sure MySQL is running and database 'chatapp' exists");
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
    }

    void sendFile() {
        try {
            JFileChooser chooser = new JFileChooser();
            int res = chooser.showOpenDialog(null);

            if (res == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                
                if (file.length() > 10 * 1024 * 1024) {
                    appendMessage("✗ File too large! Maximum size is 10MB");
                    return;
                }

                FileInputStream fis = new FileInputStream(file);
                byte[] data = fis.readAllBytes();
                fis.close();

                out.writeUTF("FILE");
                out.writeUTF(file.getName());
                out.writeInt(data.length);
                out.write(data);
                out.flush();

                appendMessage("📤 You sent file: " + file.getName());
            }

        } catch (Exception e) {
            e.printStackTrace();
            appendMessage("✗ Error sending file: " + e.getMessage());
        }
    }

    void receive() {
        try {
            while (true) {
                String type = in.readUTF();

                if (type.equals("TEXT")) {
                    String message = in.readUTF();
                    appendMessage(message);

                } else if (type.equals("FILE")) {
                    String user = in.readUTF();
                    String fileName = in.readUTF();
                    int size = in.readInt();

                    byte[] data = new byte[size];
                    in.readFully(data);

                    File receivedDir = new File("received_files");
                    if (!receivedDir.exists()) {
                        receivedDir.mkdir();
                    }
                    
                    File file = new File(receivedDir, System.currentTimeMillis() + "_" + fileName);
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(data);
                    fos.close();

                    appendMessage("📎 " + user + " sent file: " + fileName + " (saved as " + file.getName() + ")");
                }
            }

        } catch (IOException e) {
            appendMessage("✗ Disconnected from server!");
            System.out.println("Connection closed");
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> new Client());
    }
}