package edu.cs309.cycloneinsider.api.models;

import java.util.List;

public class PostCreateRequestModel {
  public String content;
  public List<String> tags;
  public String title;

  public PostCreateRequestModel(String content, List<String> tags, String title) {
    this.content = content;
    this.tags = tags;
    this.title = title;
  }

  public PostCreateRequestModel() {
  }
}
