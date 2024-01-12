package ma.nemo.assignment.service;


import ma.nemo.assignment.domain.Product;
import ma.nemo.assignment.domain.Sale;
import ma.nemo.assignment.domain.TransactionHistory;
import ma.nemo.assignment.domain.util.EventType;
import ma.nemo.assignment.dto.SaleDto;
import ma.nemo.assignment.dto.TransactionHistoryDto;
import ma.nemo.assignment.exceptions.ProductNotFound;
import ma.nemo.assignment.exceptions.ProductStockShortage;
import ma.nemo.assignment.mapper.TransactionHistoryMapper;
import ma.nemo.assignment.repository.ProductRepository;
import ma.nemo.assignment.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TransactionHistoryMapper transactionHistoryMapper;

    @Autowired
    private TransactionHistoryService transactionHistoryService;


    public SaleDto addSale(SaleDto saleDto) throws ProductNotFound, ProductStockShortage {
        // search product by code in saleDto
        Optional<Product> productOptional = productRepository.findByProductCode(saleDto.getProductCode());




        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            //verify if daily sales exceeded
            if (limitExceeded(product, saleDto.getQuantity())) {
                throw new ProductStockShortage("100 products daily sales exceeded");
            }


            // verify quantity in stock
            if (product.getQuantityInStock() >= saleDto.getQuantity()) {

                // create sale
                Sale sale = new Sale();
                sale.setProduct(product);
                sale.setSoldQuantity(saleDto.getQuantity());
                sale.setTotalPrice(product.getUnitPrice() * saleDto.getQuantity());
                sale.setSaleDate(LocalDateTime.now());

                // sale registration



                // update quantity in stock
                int newQuantityInStock = product.getQuantityInStock() - saleDto.getQuantity();
                product.setQuantityInStock(newQuantityInStock);


                product.setModificationDate(LocalDateTime.now());
                productRepository.save(product);



                transactionHistoryService.createTransaction(product, EventType.SALE, saleDto.getQuantity());

            } else {
                throw new ProductStockShortage("Not enough product quantity in stock.");
            }
        } else {
            throw new ProductNotFound("Product with code " + saleDto.getProductCode() + " not found.");
        }



        return saleDto;
    }
    private boolean limitExceeded (Product product, int quantity) {
        LocalDate today = LocalDate.now();
        int sold = saleRepository.sumSoldQuantityByProductAndSaleDateBetween(
                product,
                today.atStartOfDay(),
                today.atTime(LocalTime.MAX)
        );

        return sold + quantity > 100;
    }


}
