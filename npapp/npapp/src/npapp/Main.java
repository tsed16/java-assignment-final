//javac npapp\Main.java
//java npapp.Main
package npapp;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Main extends JFrame implements ActionListener {
    JTextArea textArea;
    JFileChooser fileChooser;

    public Main() {
        setTitle("Simple Notepad");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textArea = new JTextArea();
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem openItem = new JMenuItem("Open");
        openItem.addActionListener(this);
        fileMenu.add(openItem);
        JMenuItem saveItem = new JMenuItem("Save As");
        saveItem.addActionListener(this);
        fileMenu.add(saveItem);
        JMenuItem saveAsItem = new JMenuItem("Save");
        saveAsItem.addActionListener(this);
        fileMenu.add(saveAsItem);
        JMenuItem newItem = new JMenuItem("New");
        newItem.addActionListener(this);
        fileMenu.add(newItem);
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(this);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        JMenu editMenu = new JMenu("Edit");
        JMenuItem cutItem = new JMenuItem("Cut");
        cutItem.addActionListener(this);
        editMenu.add(cutItem);
        JMenuItem copyItem = new JMenuItem("Copy");
        copyItem.addActionListener(this);
        editMenu.add(copyItem);
        JMenuItem pasteItem = new JMenuItem("Paste");
        pasteItem.addActionListener(this);
        editMenu.add(pasteItem);
        menuBar.add(editMenu);

        setJMenuBar(menuBar);

        fileChooser = new JFileChooser();
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Open")) {
            int returnVal = fileChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    textArea.read(reader, null);
                    reader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else if (command.equals("Save")) {
            int returnVal = fileChooser.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                    textArea.write(writer);
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else if (command.equals("Cut")) {
            textArea.cut();
        } else if (command.equals("Copy")) {
            textArea.copy();
        } else if (command.equals("Paste")) {
            textArea.paste();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }
}
