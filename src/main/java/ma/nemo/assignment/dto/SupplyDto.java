package ma.nemo.assignment.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplyDto {
    @NotBlank(message = "Product Code is required")
    @Size(min = 3, max = 10, message = "Product code length must be between 3 and 10 characters")

    private String productCode;
    @Max(value = 500, message = "quantity should be less than 500")
    private Integer quantity;

    private LocalDateTime expirationDate;
}
