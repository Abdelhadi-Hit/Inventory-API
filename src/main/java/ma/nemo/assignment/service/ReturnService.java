package ma.nemo.assignment.service;

import ma.nemo.assignment.domain.Product;
import ma.nemo.assignment.domain.Return;
import ma.nemo.assignment.domain.TransactionHistory;
import ma.nemo.assignment.domain.util.EventType;
import ma.nemo.assignment.dto.ReturnDto;
import ma.nemo.assignment.dto.TransactionHistoryDto;
import ma.nemo.assignment.exceptions.ProductNotFound;
import ma.nemo.assignment.mapper.TransactionHistoryMapper;
import ma.nemo.assignment.repository.ProductRepository;
import ma.nemo.assignment.repository.ReturnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ReturnService {

    @Autowired
    private ReturnRepository returnProductRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TransactionHistoryMapper transactionMapper;

    @Autowired
    private TransactionHistoryService transactionHistoryService;


    public ReturnDto returnProduct(ReturnDto returnProductDto) throws ProductNotFound {
        Optional<Product> Optproduct = productRepository.findByProductCode(returnProductDto.getProductCode());

        if (!Optproduct.isPresent()) {
            throw new ProductNotFound("Product not found");
        }

        Product product = Optproduct.get();

        if (returnProductDto.getQuantity() <= 0) {
            throw new IllegalArgumentException("Return quantity must be greater then 0 .");
        }

        // Create a return object
        Return returnedProduct = new Return();
        returnedProduct.setProduct(product);
        returnedProduct.setReturnQuantity(returnProductDto.getQuantity());
        returnedProduct.setReturnDate(LocalDateTime.now());

        returnProductRepository.save(returnedProduct);

        // update the product quantity in stock
        int newQuantityInStock = product.getQuantityInStock() + returnProductDto.getQuantity();
        product.setQuantityInStock(newQuantityInStock);

        // update product infos
        product.setModificationDate(LocalDateTime.now());

        // save the transaction
        transactionHistoryService.createTransaction(product,EventType.RETURN,returnProductDto.getQuantity());

        // return object of ReturnDto
        ReturnDto returnResult = new ReturnDto();
        returnResult.setProductCode(product.getProductCode());
        returnResult.setQuantity(returnProductDto.getQuantity());
        returnResult.setReason("Product returned succesfully.");

        return returnResult;
    }
}
