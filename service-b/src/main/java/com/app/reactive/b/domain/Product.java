package com.app.reactive.b.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author Anish Panthi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Product implements Serializable {

  @Id
  @Column("ID")
  private Long id;

  @Column("NAME")
  private String name;

  @Column("PRICE")
  private Double price;

  @Override
  public String toString() {
    return "{\n"
        + "\t\"id\": \"" + this.id + "\",\n"
        + "\t\"name\": \"" + this.name + "\",\n"
        + "\t\"price\": \"" + this.price + "\"\n"
        + "}";
  }
}
