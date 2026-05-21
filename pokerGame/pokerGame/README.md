# High Card Poker UI 

## Objective

The objective of this project is to create a simple poker card game simulation using Java Swing.  
The program demonstrates:

- Graphical User Interface (GUI) development using Java Swing
- Random card generation
- Array manipulation and sorting
- Event handling in Java
- Basic game logic implementation

The game deals 5 random cards to two players and determines the winner based on the highest card.

---

# Project Description

This application is a desktop-based Java program that simulates a simple **High Card Poker Game**.  
Two players receive randomly generated cards, and the program compares their highest cards to decide the winner.

The project uses Java Swing and AWT Graphics to draw cards and display the game visually.

---

# Components of the System

## 1. JFrame

Used as the main application window.

```java
public class HighCardPokerUI extends JFrame
```

---

## 2. JButton

The **Deal Cards** button allows users to start a new game and generate random cards.

```java
JButton deal = new JButton("Deal Cards");
```

---

## 3. Arrays

Two-dimensional arrays store the cards for both players.

```java
private int[][] p1Cards = new int[5][2];
private int[][] p2Cards = new int[5][2];
```

Each card contains:
- Card value
- Card suit

---

## 4. Random Class

Used to generate random card values and suits.

```java
Random rand = new Random();
```

---

## 5. Graphics Class

Used to draw:
- Cards
- Text
- Player names
- Game result

```java
public void paint(Graphics g)
```

---

## 6. Sorting Algorithm

Cards are sorted from lowest to highest value using `Arrays.sort()`.

```java
Arrays.sort(p1Cards, (a, b) -> a[0] - b[0]);
```

---

## 7. Winner Determination Logic

The highest cards of both players are compared.

```java
if (p1Best > p2Best)
    result = "Player 1 Wins!";
```

---

# Features

- Simple graphical user interface
- Random card dealing
- Card visualization
- Automatic card sorting
- Winner determination
- Easy-to-understand game logic

---

# Technologies Used

- Java
- Java Swing
- AWT Graphics

---

# Project Structure

```text
src/
 └── PokerGame/
      └── HighCardPokerUI.java
```

---

# How to Run the Program

## Step 1: Open terminal inside the `src` folder

```bash
cd src
```

## Step 2: Compile the program

```bash
javac PokerGame/HighCardPokerUI.java
```

## Step 3: Run the application

```bash
java PokerGame.HighCardPokerUI
```

---

# Game Rules

1. Each player receives 5 random cards.
2. Cards are sorted automatically.
3. The highest card from each player is selected.
4. The player with the higher card wins.
5. If both highest cards are equal, the game ends in a tie.

---

# Example Output

```text
Player 1 Wins!
```

or

```text
Tie!
```

---

# Future Improvements

Possible future enhancements include:

- Preventing duplicate cards
- Adding complete poker hand rankings
- Multiplayer functionality
- Better card graphics
- Score tracking system
- Sound effects and animations

---

# Conclusion

This project provides a simple introduction to Java GUI programming and game development concepts.  
It combines object-oriented programming, graphics, arrays, sorting, and event handling into a fun and interactive application.

---
