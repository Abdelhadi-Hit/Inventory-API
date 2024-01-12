package ma.nemo.assignment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleDto {

    @NotBlank(message = "Product Code is required")
    @Size(min = 3, max = 10, message = "Product code length must be between 3 and 10 characters")
    private String productCode;
    private Integer quantity;


}
