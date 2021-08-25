package com.app.reactive.a.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Anish Panthi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "product")
public class Product implements Serializable {

  @Id
  private String id;

  private String name;

  private Double price;

  private String source;

  @Override
  public String toString() {
    return "{\n"
        + "\t\"id\": \"" + this.id + "\",\n"
        + "\t\"name\": \"" + this.name + "\",\n"
        + "\t\"price\": \"" + this.price + "\"\n"
        + "\t\"source\": \"" + this.source + "\"\n"
        + "}";
  }
}
