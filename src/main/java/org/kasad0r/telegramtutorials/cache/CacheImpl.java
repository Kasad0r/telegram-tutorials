package org.kasad0r.telegramtutorials.cache;

import org.kasad0r.telegramtutorials.domain.BotUser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CacheImpl implements Cache<BotUser> {
  private final Map<Long, BotUser> users;

  public CacheImpl() {
    this.users = new HashMap<>();
  }


  @Override
  public BotUser findById(Long id) {
    return users.get(id);
  }

  @Override
  public void removeById(Long id) {
    users.remove(id);
  }

  @Override
  public List<BotUser> getAll() {
    return new ArrayList<>(users.values());
  }

  @Override
  public void add(BotUser botUser) {
    if (botUser.getId() != null) {
      users.put(botUser.getId(), botUser);
    } else {
      throw new NullPointerException("No id");
    }
  }
}
