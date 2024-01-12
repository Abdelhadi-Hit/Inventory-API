package ma.nemo.assignment.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.nemo.assignment.domain.util.EventType;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TransactionHistory")
public class TransactionHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long transactionId;

  @ManyToOne
  @JoinColumn(name = "productId")
  private Product product;

  private Integer quantity;

  @Enumerated(EnumType.STRING)
  private EventType transactionType;

  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime transactionDate;

  @ManyToOne
  @JoinColumn(name = "userId")
  private User user;


}
