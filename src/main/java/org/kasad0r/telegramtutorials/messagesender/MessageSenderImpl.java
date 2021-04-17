package org.kasad0r.telegramtutorials.messagesender;


import org.kasad0r.telegramtutorials.HelloWorldBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class MessageSenderImpl implements MessageSender {
  private HelloWorldBot helloWorldBot;

  @Override
  public void sendMessage(SendMessage sendMessage) {
    try {
      helloWorldBot.execute(sendMessage);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }

  @Autowired
  public void setHelloWorldBot(HelloWorldBot helloWorldBot) {
    this.helloWorldBot = helloWorldBot;
  }
}
