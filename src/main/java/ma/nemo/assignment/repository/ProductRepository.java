package ma.nemo.assignment.repository;

import ma.nemo.assignment.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  Optional<Product> findByProductCode(String productCode);

  @Query("SELECT p FROM Product p Where p.quantityInStock < p.thresholdQuantity ")
  List<Product> findProductsBelowThreshold();


}
