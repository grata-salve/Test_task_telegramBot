package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class TelegramBot extends TelegramLongPollingBot {

  private final Properties properties = new Properties();

  public TelegramBot() {
    loadProperties();
  }

  private void loadProperties() {
    try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
      if (input == null) {
        System.out.println("Файл application.properties не знайдено!");
        return;
      }
      properties.load(input);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public void onUpdateReceived(Update update) {
    // Обробка текстових повідомлень (наприклад, команда /start)
    if (update.hasMessage() && update.getMessage().hasText()) {
      String text = update.getMessage().getText();
      long chatId = update.getMessage().getChatId();
      if ("/start".equals(text)) {
        sendMenu1(chatId);
      }
    }
    // Обробка callback-запитів від inline-кнопок
    else if (update.hasCallbackQuery()) {
      String callbackData = update.getCallbackQuery().getData();
      long chatId = update.getCallbackQuery().getMessage().getChatId();

      switch (callbackData) {
        case "MENU1_BUTTON1":
          sendTextMessage(chatId, "Кнопка 1");
          break;
        case "MENU1_BUTTON2":
          sendTextMessage(chatId, "Кнопка 2");
          break;
        case "GO_TO_MENU2":
          sendMenu2(chatId);
          break;
        case "MENU2_BUTTON1":
          sendTextMessage(chatId, "Кнопка 1");
          break;
        case "MENU2_BUTTON2":
          sendTextMessage(chatId, "Кнопка 2");
          break;
        case "BACK_TO_MENU1":
          sendMenu1(chatId);
          break;
        default:
          break;
      }
    }
  }

  // Метод для надсилання першого меню (Меню 1)
  private void sendMenu1(long chatId) {
    SendMessage message = new SendMessage();
    message.setChatId(String.valueOf(chatId));
    message.setText("Меню 1");

    InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
    List<List<InlineKeyboardButton>> rows = new ArrayList<>();

    // Перший ряд: кнопки "Кнопка 1" і "Кнопка 2"
    List<InlineKeyboardButton> row1 = new ArrayList<>();
    InlineKeyboardButton btn1 = new InlineKeyboardButton();
    btn1.setText("Кнопка 1");
    btn1.setCallbackData("MENU1_BUTTON1");
    row1.add(btn1);

    InlineKeyboardButton btn2 = new InlineKeyboardButton();
    btn2.setText("Кнопка 2");
    btn2.setCallbackData("MENU1_BUTTON2");
    row1.add(btn2);

    // Другий ряд: кнопка "Далі"
    List<InlineKeyboardButton> row2 = new ArrayList<>();
    InlineKeyboardButton btn3 = new InlineKeyboardButton();
    btn3.setText("Далі");
    btn3.setCallbackData("GO_TO_MENU2");
    row2.add(btn3);

    rows.add(row1);
    rows.add(row2);
    markup.setKeyboard(rows);
    message.setReplyMarkup(markup);

    try {
      execute(message);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }

  // Метод для надсилання другого меню (Меню 2)
  private void sendMenu2(long chatId) {
    SendMessage message = new SendMessage();
    message.setChatId(String.valueOf(chatId));
    message.setText("Меню 2");

    InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
    List<List<InlineKeyboardButton>> rows = new ArrayList<>();

    // Перший ряд: кнопки "Кнопка 1" і "Кнопка 2"
    List<InlineKeyboardButton> row1 = new ArrayList<>();
    InlineKeyboardButton btn1 = new InlineKeyboardButton();
    btn1.setText("Кнопка 1");
    btn1.setCallbackData("MENU2_BUTTON1");
    row1.add(btn1);

    InlineKeyboardButton btn2 = new InlineKeyboardButton();
    btn2.setText("Кнопка 2");
    btn2.setCallbackData("MENU2_BUTTON2");
    row1.add(btn2);

    // Другий ряд: кнопка "Назад"
    List<InlineKeyboardButton> row2 = new ArrayList<>();
    InlineKeyboardButton btn3 = new InlineKeyboardButton();
    btn3.setText("Назад");
    btn3.setCallbackData("BACK_TO_MENU1");
    row2.add(btn3);

    rows.add(row1);
    rows.add(row2);
    markup.setKeyboard(rows);
    message.setReplyMarkup(markup);

    try {
      execute(message);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }

  // Метод для надсилання простого текстового повідомлення
  private void sendTextMessage(long chatId, String text) {
    SendMessage message = new SendMessage();
    message.setChatId(String.valueOf(chatId));
    message.setText(text);
    try {
      execute(message);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }

  @Override
  public String getBotUsername() {
    return properties.getProperty("bot.username");
  }

  @Override
  public String getBotToken() {
    return properties.getProperty("bot.token");
  }
}
