package ma.nemo.assignment.repository;

import ma.nemo.assignment.domain.Product;
import ma.nemo.assignment.domain.Sale;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale,Long> {
    @Query("SELECT COALESCE(SUM(s.soldQuantity), 0) FROM Sale s WHERE s.product = :product AND s.saleDate BETWEEN :startDate AND :endDate")
    int sumSoldQuantityByProductAndSaleDateBetween(Product product, LocalDateTime startDate, LocalDateTime endDate);

}
