package com.app.reactive.a.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Anish Panthi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto implements Serializable {

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
