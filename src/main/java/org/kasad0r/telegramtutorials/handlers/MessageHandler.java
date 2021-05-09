package org.kasad0r.telegramtutorials.handlers;

import org.kasad0r.telegramtutorials.cache.Cache;
import org.kasad0r.telegramtutorials.domain.BotUser;
import org.kasad0r.telegramtutorials.domain.Position;
import org.kasad0r.telegramtutorials.messagesender.MessageSender;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class MessageHandler implements Handler<Message> {

  private final MessageSender messageSender;

  private final Cache<BotUser> cache;

  public MessageHandler(MessageSender messageSender, Cache<BotUser> cache) {
    this.messageSender = messageSender;
    this.cache = cache;
  }

  private BotUser generateUserFromMessage(Message message) {
    BotUser user = new BotUser();
    user.setUsername(message.getFrom().getUserName());
    user.setId(message.getChatId());
    user.setPosition(Position.INPUT_USERNAME);
    return user;
  }

  @Override
  public void choose(Message message) {
    BotUser user = cache.findBy(message.getChatId());
    if (user != null) {
      switch (user.getPosition()) {
        case INPUT_USERNAME:
          user.setFullName(message.getText());
          user.setPosition(Position.INPUT_PHONE_NUMBER);
          messageSender.sendMessage(
                  SendMessage.builder()
                          .text("Натисни на клавішу щоб відправити номер телефону. ")
                          .chatId(String.valueOf(message.getChatId()))
                          .replyMarkup(ReplyKeyboardMarkup.builder()
                                  .oneTimeKeyboard(true)
                                  .resizeKeyboard(true)
                                  .keyboardRow(new KeyboardRow() {{
                                    add(KeyboardButton.builder()
                                            .text("Відправити номер")
                                            .requestContact(true)
                                            .build());
                                  }}).build())
                          .build()
          );
          break;
        case INPUT_PHONE_NUMBER:
          if (message.hasContact()) {
            user.setPhoneNumber(message.getContact().getPhoneNumber());
            user.setPosition(Position.NONE);
            messageSender.sendMessage(
                    SendMessage.builder()
                            .text("<b>Id</b>" + user.getId() + "\n<b>ПІБ</b>" +
                                    user.getFullName() + "\n<b>Моб. телефон</b>"
                                    + user.getPhoneNumber())
                            .parseMode("HTML")
                            .chatId(String.valueOf(message.getChatId()))
                            .build()
            );
          }
          break;
      }
    } else if (message.hasText()) {
      if (message.getText().equals("/reg")) {
        cache.add(generateUserFromMessage(message));
        messageSender.sendMessage(
                SendMessage.builder()
                        .text("Введи свій ПІБ:")
                        .chatId(String.valueOf(message.getChatId()))
                        .build()
        );
      } else if (message.getText().equals("/get_poem")) {
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
