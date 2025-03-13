# Telegram Bot

This project is a simple Telegram bot built using the Telegram Bot API and Java. It supports basic message handling and interaction with users.

## Features

- **Command Handling:**
  - `/start` - Sends a welcome message to the user.
  
- **Message Processing:**
  - Responds to text messages.
  
- **Configuration via Properties File:**
  - The bot reads its username and token from `application.properties`.
  
## Project Structure

- **TelegramBot.java**  
  Implements the bot logic, including message handling and interaction with Telegram API.
  
- **Main.java**  
  Initializes and registers the bot with Telegram API.
  
- **application.properties**  
  Contains the bot's username and token (should be placed in `src/main/resources`).

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven (for building the project and managing dependencies)

### Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/grata-salve/Test_task_telegramBot.git
   cd telegram-bot
   ```

2. Create a `application.properties` file in `src/main/resources/`:
   ```properties
   bot.username=your_bot_username
   bot.token=your_bot_token
   ```

3. Build the project:
   ```bash
   mvn clean install
   ```

### Run the Bot

Execute the following command:
```bash
mvn exec:java -Dexec.mainClass="org.example.Main"
```

## Usage Example

Here’s how you can interact with the bot:

1. Start the bot and send the `/start` command.
2. The bot will respond with a welcome message.
3. You can modify the `onUpdateReceived` method to handle additional commands.
