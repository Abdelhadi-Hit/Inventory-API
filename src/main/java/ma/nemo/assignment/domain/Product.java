package ma.nemo.assignment.domain;

import lombok.*;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import ma.nemo.assignment.domain.Return;
import ma.nemo.assignment.domain.Sale;
import ma.nemo.assignment.domain.Supply;
import ma.nemo.assignment.domain.TransactionHistory;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "Products")

public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long productId;

  @Column(unique = true, nullable = false)
  @NotBlank(message = "Product code is required")
  private String productCode;

  @Column(nullable = false)
  @NotBlank(message = "Product name is required")
  private String productName;

  private String description;

  private Double unitPrice;

  private Integer quantityInStock;

  @Column(columnDefinition = "TIMESTAMP")
  private LocalDateTime creationDate;

  @Column(columnDefinition = "TIMESTAMP")
  private LocalDateTime modificationDate;

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Supply> supplies;

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Sale> sales;

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Return> returns;

  private Integer thresholdQuantity;

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<TransactionHistory> transactions;

}
