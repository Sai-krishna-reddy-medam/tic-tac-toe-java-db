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



### 🛠️ SQL Database Setup

```sql
-- 1. Create the database if it doesn't exist
CREATE DATABASE IF NOT EXISTS tic_tac_toe;
USE tic_tac_toe;

-- 2. Drop old tables to avoid conflicts (⚠ only use in development/testing)
DROP TABLE IF EXISTS game_history;
DROP TABLE IF EXISTS players;

-- 3. Create the players table
--    - Uses player_name as the PRIMARY KEY (no separate id)
--    - Simplifies foreign key reference
CREATE TABLE players (
    player_name VARCHAR(50) PRIMARY KEY,
    games_played INT DEFAULT 0,
    wins INT DEFAULT 0
);

-- 4. Create the game_history table
--    - References players.player_name
--    - Stores a log of games from the perspective of each player
CREATE TABLE game_history (
    id INT AUTO_INCREMENT PRIMARY KEY,
    player_name VARCHAR(50) NOT NULL,
    opponent_name VARCHAR(50),
    result ENUM('Win', 'Loss', 'Draw') NOT NULL,
    mode ENUM('AI', '2P') NOT NULL,
    date_played TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_player
        FOREIGN KEY (player_name)
        REFERENCES players(player_name)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- 5. Sample query: view all registered players
SELECT * FROM players;

-- 6. Sample query: view the latest 10 games for a specific player
SELECT * FROM game_history
WHERE player_name = 'YourName'
ORDER BY date_played DESC
LIMIT 10;
