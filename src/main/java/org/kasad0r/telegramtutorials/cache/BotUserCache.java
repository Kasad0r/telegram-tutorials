package org.kasad0r.telegramtutorials.cache;

import org.kasad0r.telegramtutorials.domain.BotUser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BotUserCache implements Cache<BotUser> {
  private final Map<Long, BotUser> users;

  public BotUserCache() {
    this.users = new HashMap<>();
  }

  @Override
  public void add(BotUser botUser) {
    if (botUser.getId() != null) {
      users.put(botUser.getId(), botUser);
    }
  }

  @Override
  public void remove(BotUser botUser) {
    users.remove(botUser.getId());
  }

  @Override
  public BotUser findBy(Long id) {
    return users.get(id);
  }

  @Override
  public List<BotUser> getAll() {
    return new ArrayList<>(users.values());
  }
}
