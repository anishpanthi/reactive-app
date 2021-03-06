package com.app.reactive.b.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author Anish Panthi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("reactive.product")
@Builder
public class Product implements Serializable {

  @Id
  private Long id;

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
