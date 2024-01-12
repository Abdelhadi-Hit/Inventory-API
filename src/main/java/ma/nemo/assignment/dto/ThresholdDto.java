package ma.nemo.assignment.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThresholdDto {
    private String productCode;
    private Integer thresholdQuantity;

}
