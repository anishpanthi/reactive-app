package com.app.reactive.a.dto;

import com.app.reactive.a.domain.Product;
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

  private Product product1;

  private Product product2;

}
