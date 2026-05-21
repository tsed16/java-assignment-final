# Simple Notepad Application

## Objective
The objective of this project is to develop a simple text editor application using Java Swing.  
The application allows users to create, edit, open, and save text files through a graphical user interface (GUI).  
It is designed to demonstrate the use of Java Swing components, event handling, and file handling in Java.

---

# Features
- Create a new text document
- Open existing text files
- Save text files
- Cut text
- Copy text
- Paste text
- Simple and user-friendly graphical interface

---

# Components Used

## 1. JFrame
Used to create the main application window.

## 2. JTextArea
Used for writing and editing text content.

## 3. JScrollPane
Adds scrolling functionality to the text area.

## 4. JMenuBar
Creates the menu bar at the top of the application.

## 5. JMenu
Used to create menus such as:
- File
- Edit

## 6. JMenuItem
Used to create selectable menu options like:
- Open
- Save
- Cut
- Copy
- Paste

## 7. JFileChooser
Allows users to select files for opening and saving.

## 8. ActionListener
Handles button and menu item events.

## 9. BufferedReader
Reads text data from files.

## 10. BufferedWriter
Writes text data to files.

---

# Technologies Used
- Java
- Java Swing
- AWT Event Handling
- File Handling

---

# Program Flow

1. The application window opens.
2. User writes text in the text area.
3. User selects options from the menu bar.
4. File menu handles:
   - Open file
   - Save file
   - New file
   - Exit
5. Edit menu handles:
   - Cut
   - Copy
   - Paste

---

# Explanation of Important Methods

## Constructor `Main()`
- Creates the GUI
- Initializes menus and menu items
- Adds action listeners
- Sets window properties

## `actionPerformed(ActionEvent e)`
Handles menu actions based on the selected command.

### Open
Reads text from a selected file and displays it.

### Save
Writes text from the text area into a file.

### Cut
Removes selected text and stores it in clipboard.

### Copy
Copies selected text to clipboard.

### Paste
Pastes clipboard content into the text area.

## `main()`
Starts the application using SwingUtilities.

---

# Advantages
- Easy to use
- Lightweight
- Demonstrates GUI programming concepts
- Demonstrates file handling concepts

---

# Limitations
- No font customization
- No undo/redo feature
- Supports only plain text
- No automatic save

---

# Future Improvements
- Add dark mode
- Add font styles and colors
- Add undo and redo functionality
- Add search functionality
- Add keyboard shortcuts

---

# Conclusion
The Simple Notepad project successfully demonstrates how Java Swing can be used to build desktop GUI applications.  
It combines graphical interface design, event handling, and file operations into a practical text editor application.

---

# Compile and Run

```bash
javac npapp\Main.java
java npapp.Main
