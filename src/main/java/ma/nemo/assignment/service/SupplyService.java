package ma.nemo.assignment.service;

import ma.nemo.assignment.domain.Product;
import ma.nemo.assignment.domain.Supply;
import ma.nemo.assignment.domain.TransactionHistory;
import ma.nemo.assignment.domain.util.EventType;
import ma.nemo.assignment.dto.ProductDto;
import ma.nemo.assignment.dto.SupplyDto;
import ma.nemo.assignment.dto.TransactionHistoryDto;
import ma.nemo.assignment.exceptions.ProductNotFound;
import ma.nemo.assignment.mapper.ProductMapper;
import ma.nemo.assignment.mapper.TransactionHistoryMapper;
import ma.nemo.assignment.repository.ProductRepository;
import ma.nemo.assignment.repository.SupplyRepository;
import ma.nemo.assignment.repository.TransactionHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SupplyService {

    @Autowired
    private SupplyRepository supplyRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private TransactionHistoryService transactionHistoryService;

    public SupplyDto addProductToInventory(SupplyDto supplyDto) throws ProductNotFound {
        Optional<Product> productOptional = productRepository.findByProductCode(supplyDto.getProductCode());

        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            Supply supply = new Supply();
            supply.setProduct(product);
            supply.setQuantity(supplyDto.getQuantity());
            supply.setExpirationDate(supplyDto.getExpirationDate());
            supply.setSupplyDate(LocalDateTime.now());

            supplyRepository.save(supply);

            // Updating the quantity in stock of the product
            int newQuantityInStock = product.getQuantityInStock() + supplyDto.getQuantity();
            product.setQuantityInStock(newQuantityInStock);
            product.setModificationDate(LocalDateTime.now());

            productRepository.save(product);
            transactionHistoryService.createTransaction(product,EventType.SUPPLY, supplyDto.getQuantity());

            return supplyDto;
        } else {
            throw new ProductNotFound("Product with code " + supplyDto.getProductCode() + " wasn't found");
        }
    }

    //

    public List<ProductDto> getProductsNearingExpiryDate(int thresholdDays) {
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime thresholdDate = currentDate.plusDays(thresholdDays);

        List<Supply> supplies = supplyRepository.findByExpirationDateBetween(currentDate, thresholdDate);

        return supplies.stream()
                .map(supply -> productMapper.toDTO(supply.getProduct()))
                .collect(Collectors.toList());
    }

}
