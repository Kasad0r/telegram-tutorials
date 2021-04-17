package org.kasad0r.telegramtutorials.handlers;

import org.kasad0r.telegramtutorials.messagesender.MessageSender;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class MessageHandler implements Handler<Message> {

  private final MessageSender messageSender;

  public MessageHandler(MessageSender messageSender) {
    this.messageSender = messageSender;
  }

  @Override
  public void choose(Message message) {
    if (message.hasText()) {
      if (message.getText().equals("/get_poem")) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setText("Як умру, то поховайте\n" +
                "\n" +
                "Мене на могилі\n" +
                "\n" +
                "Серед степу широкого\n" +
                "\n" +
                "На Вкраїні милій,\n" +
                "\n" +
                "Щоб лани широкополі,\n" +
                "\n" +
                "І Дніпро, і кручі\n" +
                "\n" +
                "Було видно, було чути,\n" +
                "\n" +
                "Як реве ревучий.");
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(
                Collections.singletonList(
                        InlineKeyboardButton.builder()
                                .text("Новий вірш")
                                .callbackData("next_poem")
                                .build()));
        inlineKeyboardMarkup.setKeyboard(keyboard);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        messageSender.sendMessage(sendMessage);
      }
    }
  }
}
