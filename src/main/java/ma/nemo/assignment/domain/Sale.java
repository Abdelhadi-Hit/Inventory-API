package ma.nemo.assignment.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(name = "Sales")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sale {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long saleId;

  @ManyToOne
  @JoinColumn(name = "productId")
  private Product product;

  private Integer soldQuantity;

  private Double totalPrice;

  @Column(columnDefinition = "TIMESTAMP")
  private LocalDateTime saleDate;

  @ManyToOne
  @JoinColumn(name = "userId")
  private User user;


}
