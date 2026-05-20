//javac -cp ".;lib\mysql-connector-j-9.7.0.jar" src\ChatApp\*.java
//java -cp ".;lib\mysql-connector-j-9.7.0.jar;src" ChatApp.Server
//java -cp ".;lib\mysql-connector-j-9.7.0.jar;src" ChatApp.Client
package ChatApp;

import java.sql.*;

public class DB {

    private static final String URL = "jdbc:mysql://localhost:3306/chatapp";
    private static final String USER = "root";
    private static final String PASS = "";

    public static void init() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/",
                    USER,
                    PASS
            );

            Statement stmt = con.createStatement();

            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS chatapp");
            stmt.execute("USE chatapp");

            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS messages (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "username VARCHAR(50)," +
                "message TEXT," +
                "file_name TEXT," +
                "file_data LONGBLOB," +
                "timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP)"
            );

            System.out.println("✓ DB ready (chatapp + messages)");

            con.close();

        } catch (Exception e) {
            System.out.println("✗ DB INIT ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }

  public static boolean saveMessage(String user, String msg, String fileName, byte[] fileData) {

    try {
        System.out.println("➡ TRYING INSERT: " + user + " -> " + msg);

        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/chatapp",
                "root",
                ""
        );

        String sql = "INSERT INTO messages(username, message, file_name, file_data) VALUES (?, ?, ?, ?)";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, user);
        ps.setString(2, msg);
        ps.setString(3, fileName);
        ps.setBytes(4, fileData);

        int result = ps.executeUpdate();

        System.out.println("✔ INSERT RESULT ROWS = " + result);

        ps.close();
        con.close();

        return result > 0;

    } catch (Exception e) {
        System.out.println("❌ SAVE MESSAGE FAILED!");
        e.printStackTrace();
        return false;
    }
}

    public static void loadAndPrintAllMessages() {
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM messages ORDER BY timestamp ASC");

            System.out.println("\n=== DB MESSAGES ===");

            while (rs.next()) {
                System.out.println(
                    rs.getString("username") + ": " +
                    rs.getString("message")
                );
            }

            System.out.println("===================\n");

            con.close();

        } catch (Exception e) {
            System.out.println("✗ LOAD ERROR: " + e.getMessage());
        }
    }
}