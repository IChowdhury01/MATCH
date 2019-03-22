package edu.cooper.ece366.bookstore;

import io.norberg.automatter.AutoMatter;

@AutoMatter
public interface Book {
  String title();

  String author();
}
