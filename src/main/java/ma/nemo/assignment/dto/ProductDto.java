package ma.nemo.assignment.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
  private Long productId;
  

  @NotBlank(message = "product code is required !")
  @Size(min = 4,max = 9,message = "product code should range between 4 and 9")
  private String productCode;

  @NotBlank(message = "Product name is required !")
  private String productName;

  private String description;

  @NotNull(message = "Unit price is required !")
  @DecimalMin(value = "0.01", message = "Unit price should be equal or greater than 0.01")
  private Double unitPrice;

  @NotNull(message = "Quantity in stock is required !")
  @DecimalMin(value = "1",message = "Quantity in stock must be equal or greater than 1")
  private Integer quantityInStock;

  private Date creationDate;
  private Date modificationDate;

  @Min(value = 1,message = "Threshold quantity must not fall below 1 ")
  private Integer thresholdQuantity;

}
