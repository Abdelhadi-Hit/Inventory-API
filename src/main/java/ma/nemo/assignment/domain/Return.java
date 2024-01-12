package ma.nemo.assignment.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Returns")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Return {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long returnId;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    private Integer returnQuantity;

    private String reason;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime returnDate;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

}
