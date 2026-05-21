# Multi-Client Chat Application Report

## Project Title
**Java Multi-Client Chat Application with File Sharing and MySQL Database**

---

# 1. Introduction

This project is a desktop-based chat application developed using Java Socket Programming, Swing GUI, and MySQL Database.

The system allows multiple users to:

- Connect to a server
- Send and receive text messages
- Share files
- Store chat history in a database
- Reload previous messages

The application follows a Client-Server Architecture where:

- The Server manages communication
- Multiple Clients connect to the server
- The Database stores chat history and file information

---

# 2. Objective

The main objectives of this project are:

- To understand socket programming in Java
- To implement real-time communication
- To learn multi-threading
- To create a GUI-based application using Swing
- To integrate MySQL database connectivity (JDBC)
- To implement file transfer functionality
- To store and retrieve chat history from a database

---

# 3. Technologies Used

| Technology | Purpose |
|---|---|
| Java | Main programming language |
| Java Swing | Graphical User Interface |
| Socket Programming | Client-server communication |
| Multi-threading | Handling multiple clients |
| MySQL | Database storage |
| JDBC | Database connectivity |
| File I/O | Sending and receiving files |

---

# 4. System Components

The project contains three major components:

---

## 4.1 Client Component

The client is responsible for:

- Connecting to the server
- Sending messages
- Receiving messages
- Sending files
- Displaying chat history
- Showing the graphical interface

### Main Features

- GUI chat window
- Send text messages
- File sharing
- Refresh chat history
- Save received files locally
- Display timestamps

### Important Classes Used

| Class | Purpose |
|---|---|
| `Socket` | Connects to server |
| `DataInputStream` | Receives data |
| `DataOutputStream` | Sends data |
| `JFrame` | Main GUI window |
| `JTextArea` | Chat display |
| `JButton` | Buttons |
| `JFileChooser` | Select files |

---

## 4.2 Server Component

The server handles all connected clients.

### Responsibilities

- Accept client connections
- Broadcast messages
- Broadcast files
- Manage online users
- Save messages to database
- Run multiple client threads

### Features

- Multi-client support
- Real-time communication
- File broadcasting
- Database integration

### Important Concepts

#### Multi-threading

Each client gets its own thread using:

```java
new Thread(ch).start();
```

This allows multiple users to communicate simultaneously.

#### Broadcasting

The server sends messages/files to all connected clients.

---

## 4.3 Database Component

The database stores:

- User messages
- File names
- File data
- Timestamps

### Database Name

```sql
chatapp
```

### Table Structure

```sql
CREATE TABLE messages (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50),
    message TEXT,
    file_name TEXT,
    file_data LONGBLOB,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

---

# 5. Project Workflow

## Step 1 — Start Server

The server:

- Initializes database
- Loads old messages
- Waits for clients

```java
ServerSocket server = new ServerSocket(5000);
```

---

## Step 2 — Client Connects

The client enters username and connects to server.

```java
socket = new Socket("localhost", 5000);
```

---

## Step 3 — Send Message

The client sends:

```java
out.writeUTF("TEXT");
out.writeUTF(message);
```

The server broadcasts the message to all users.

---

## Step 4 — Store Message

The server saves messages using JDBC:

```java
INSERT INTO messages(username, message)
```

---

## Step 5 — File Sharing

Files are sent as byte arrays.

```java
byte[] data = fis.readAllBytes();
```

The server broadcasts the file to all clients.

---

## Step 6 — Load Chat History

Clients retrieve old messages from MySQL database.

---

# 6. GUI Design

The GUI contains:

| Component | Function |
|---|---|
| Chat Area | Displays messages |
| Text Field | Enter message |
| Send Button | Sends messages |
| Send File Button | Sends files |
| Refresh Button | Reloads old messages |

---

# 7. Important Methods

## Client Methods

| Method | Purpose |
|---|---|
| `loadOldMessages()` | Loads previous messages |
| `sendFile()` | Sends files |
| `receive()` | Receives messages/files |
| `appendMessage()` | Updates chat area |

---

## Server Methods

| Method | Purpose |
|---|---|
| `broadcast()` | Sends message to all clients |
| `broadcastFile()` | Sends file to all clients |

---

## Database Methods

| Method | Purpose |
|---|---|
| `init()` | Creates database/table |
| `saveMessage()` | Saves messages |
| `loadAndPrintAllMessages()` | Loads all messages |

---

# 8. Features of the System

## Implemented Features

- Multi-client communication
- Real-time messaging
- File sharing
- MySQL database integration
- Chat history loading
- GUI using Swing
- Timestamps for messages
- Thread-based client handling
- Automatic database/table creation

---

# 9. Advantages

- Easy to use
- Supports multiple users
- Stores chat history permanently
- Simple graphical interface
- Real-time communication
- File transfer support

---

# 10. Limitations

- Works only on local network/localhost
- No message encryption
- No authentication system
- Limited file size (10MB)
- No private messaging

---

# 11. Future Improvements

Possible future enhancements:

- User authentication/login system
- End-to-end encryption
- Online user list
- Group chats
- Emojis and stickers
- Voice/video calling
- Cloud database hosting
- Better UI design
- File download manager

---

# 12. Challenges Faced

During development, the following challenges may occur:

- Managing multiple client connections
- Handling socket exceptions
- Synchronizing threads
- Database connectivity issues
- File transfer handling
- Updating GUI safely with threads

---

# 13. Conclusion

This project successfully demonstrates the implementation of a multi-client chat application using Java technologies.

The project combines:

- Socket Programming
- Swing GUI
- JDBC
- MySQL
- Multi-threading
- File handling

It provides practical understanding of network programming, database integration, and real-time communication systems.

The application can be expanded into a more advanced messaging system with additional security and networking features.

---

# 14. Compilation and Execution

## Compile

```bash
javac -cp ".;lib\mysql-connector-j-9.7.0.jar" src\ChatApp\*.java
```

---

## Run Server

```bash
java -cp ".;lib\mysql-connector-j-9.7.0.jar;src" ChatApp.Server
```

---

## Run Client

```bash
java -cp ".;lib\mysql-connector-j-9.7.0.jar;src" ChatApp.Client
```

---

# 15. Sample Output

## Server Console

```text
Starting server...
✓ DB ready
✓ Server running on port 5000
Client connected
John: Hello
```

---

## Client Window

```text
[10:20:11] 💬 John: Hello everyone
📎 Mike sent file: notes.pdf
```

---

# 16. References

- Java Documentation
- MySQL Documentation
- JDBC Documentation
- Java Swing Tutorials
- Socket Programming Concepts
