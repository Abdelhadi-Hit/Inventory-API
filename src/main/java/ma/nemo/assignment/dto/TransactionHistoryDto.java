package ma.nemo.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.nemo.assignment.domain.Product;
import ma.nemo.assignment.domain.User;
import ma.nemo.assignment.domain.util.EventType;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionHistoryDto {
    private Product product;
    private Integer quantity;
    private EventType transactionType;
    private LocalDateTime transactionDate;
    private User user;
}
