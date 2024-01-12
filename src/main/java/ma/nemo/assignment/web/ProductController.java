package ma.nemo.assignment.web;


import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import ma.nemo.assignment.domain.Product;

import ma.nemo.assignment.domain.Sale;
import ma.nemo.assignment.dto.ProductDto;
import ma.nemo.assignment.exceptions.ProductAlreadyExists;
import ma.nemo.assignment.exceptions.ProductNotFound;
import ma.nemo.assignment.exceptions.ProductValidationException;
import ma.nemo.assignment.mapper.ProductMapper;
import ma.nemo.assignment.service.ProductService;
import ma.nemo.assignment.service.SupplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Product", description = "Product management APIs")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private SupplyService supplyService;


    @Operation(
            description = "handle the add of products",
            tags = { "Product", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Product.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })

    @PostMapping
    //@PreAuthorize("hasAuthority('SCOPE_ADMIN')") // remove this line to create product without having admin role
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDto productDto) {
        LOGGER.info("Creating product: {}", productDto);
        try {
            Product createdProduct = productService.createProduct(productDto);
            ProductDto createdProductDto = productMapper.toDTO(createdProduct);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProductDto);
        } catch (ProductAlreadyExists e) {
            LOGGER.error("Product already exists: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (ProductValidationException e) {
            LOGGER.error("Product validation error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(
            description = "Fetch products",
            tags = { "Product", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Product.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })

    @GetMapping("")
    public ResponseEntity<?> listProducts() {
        LOGGER.info("Listing products");
        List<ProductDto> products = productService.getListProducts();

        if (products.isEmpty()) {
            LOGGER.info("Products list is empty");
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(products);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        try {
            LOGGER.info("Getting product with id " + id);
            ProductDto productDto = productService.getProductById(id);
            return ResponseEntity.ok(productDto);
        } catch (ProductNotFound e) {
            LOGGER.error("Product with id {} not found ", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        try {
            boolean deleted = productService.deleteProduct(id);
            if (deleted) {
                LOGGER.info("Product with id {} deleted", id);
                return ResponseEntity.ok("Product with id " + id + " has been deleted successfully");
            } else {
                LOGGER.error("Product with id {} not found for deletion", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
            }
        } catch (ProductNotFound e) {
            LOGGER.error("Product with id {} not found for deletion", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
    }




}

