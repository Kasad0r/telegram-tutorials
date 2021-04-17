package org.kasad0r.telegramtutorials;

import org.kasad0r.telegramtutorials.services.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class HelloWorldBot extends TelegramLongPollingBot {
  @Value("${telegram.bot.username}")
  private String username;
  @Value("${telegram.bot.token}")
  private String token;
  private SendMessageService sendMessageService;

  @Override
  public String getBotUsername() {
    return username;
  }

  @Override
  public String getBotToken() {
    return token;
  }

  @Override
  public void onUpdateReceived(Update update) {
    if (update.hasMessage()) {
      Message message = update.getMessage();
      if (message.hasText()) {
        sendMessageService.test2(message);
      }
    }
  }

  @Autowired
  public void setSendMessageService(SendMessageService sendMessageService) {
    this.sendMessageService = sendMessageService;
  }
}
