package com.colvir.spring;

import java.util.Objects;

public class User {
  String login;
  String name;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(login, user.login) && Objects.equals(name, user.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(login, name);
  }
}
