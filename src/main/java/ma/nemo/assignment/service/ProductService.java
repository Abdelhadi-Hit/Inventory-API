package ma.nemo.assignment.service;



import ma.nemo.assignment.domain.Product;
import ma.nemo.assignment.dto.ProductDto;
import ma.nemo.assignment.exceptions.ProductAlreadyExists;
import ma.nemo.assignment.exceptions.ProductNotFound;
import ma.nemo.assignment.exceptions.ProductValidationException;
import ma.nemo.assignment.mapper.ProductMapper;
import ma.nemo.assignment.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    public ProductDto getProductById(Long id) throws ProductNotFound {
        Optional<Product>  product = productRepository.findById(id);
        if(product.isPresent()){
            ProductDto productDto = productMapper.toDTO(product.get());
            return productDto;

        }
        throw new ProductNotFound("Product with id:"+id+"not found");

    }

    public ProductDto getProductByCode(String productCode) throws ProductNotFound {
       Optional<Product> product = productRepository.findByProductCode(productCode);
        if (product.isPresent()) {
            ProductDto productDto = productMapper.toDTO(product.get());
            return productDto;
        }
        throw new ProductNotFound("Product with code " + productCode + " not found");
    }

    public List<ProductDto> getListProducts() {
        List<Product> listProducts = productRepository.findAll();
        return listProducts.stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    //

    public Product createProduct(ProductDto productDto) throws ProductAlreadyExists, ProductValidationException {
        // verify if there is an existing product with same code
        Optional<Product> checkingProduct = productRepository.findByProductCode(productDto.getProductCode());
        if (checkingProduct.isPresent()) {
            throw new ProductAlreadyExists("Product with code: " + productDto.getProductCode() + " already exists");
        }

        Product newProduct = productMapper.toEntity(productDto);

        LocalDateTime currentDate = LocalDateTime.now();

        // Inisialize the creation and modification date
        newProduct.setCreationDate(currentDate);
        newProduct.setModificationDate(currentDate);

        // save product
        try {
            return productRepository.save(newProduct);
        } catch (DataIntegrityViolationException ex) {
            throw new ProductValidationException("Saving product Failed :( , database error !!");
        }
    }


    //delete Poduct

    public boolean deleteProduct(Long id) throws ProductNotFound{
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.deleteById(id);
            return true;
        } else {
            throw new ProductNotFound("Product with id " + id + " not found");
        }
    }

    //Get product Below threshold

    public List<ProductDto> getProductsBelowThreshold() {
        List<Product> productsBelowThreshold = productRepository.findProductsBelowThreshold();
        if (productsBelowThreshold.isEmpty()) {
            return Collections.emptyList(); //Retuen empty list
        }
        List<ProductDto> productsBelowThresholdDtos = productsBelowThreshold.stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());

        return productsBelowThresholdDtos;
    }


    //setting product threshold

    public ProductDto setProductThreshold(String productCode, int threshold) throws ProductNotFound {
        Optional<Product> productOptional = productRepository.findByProductCode(productCode);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setThresholdQuantity(threshold);
            productRepository.save(product);

            return productMapper.toDTO(product);
        } else {
            throw new ProductNotFound("Product with code " + productCode + " not found");
        }
    }



}
