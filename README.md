# Tic Tac Toe Game 🎮

Java-based GUI Tic Tac Toe game with:
- ✅ AI and 2-Player Modes
- ✅ MySQL integration to track players, stats, and match history
- ✅ Custom Swing-based user interface

## Run Instructions

1. Clone this repo
2. Set up MySQL using `schema.sql`
3. Configure your DB username/password in `DBHandler.java`
4. Compile and run `Main.java`

## Technologies Used

- Java SE 22
- JDBC
- MySQL


## STRUCTURE 
TicTacToeGame/
├── src/
│   └── com/tictactoe/app/
│       ├── GameGUI.java
│       ├── DBHandler.java
│       └── (other Java files)
├── config.properties        ← ✅ your DB credentials (this will be hidden)
├── schema.sql               ← 💾 your MySQL setup
├── .gitignore               ← 🛡️ hides sensitive files
├── README.md                ← 📘 instructions file

