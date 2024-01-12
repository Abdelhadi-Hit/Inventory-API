package ma.nemo.assignment.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import ma.nemo.assignment.dto.ProductDto;
import ma.nemo.assignment.dto.ThresholdDto;
import ma.nemo.assignment.exceptions.ProductNotFound;
import ma.nemo.assignment.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "threshold-alerts", description = "Inventory Threshold Alerts API")
@RestController
@RequestMapping("/api")

public class ThresholdAlertController {

    private Logger LOGGER = LoggerFactory.getLogger(ThresholdAlertController.class);

    @Autowired
    private ProductService productService;

    @Operation(
            description = "Set thresholds",
            tags = { "ThresholdDto", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = ThresholdDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping("/set-threshold")
    public ResponseEntity<ProductDto> setProductThreshold(@Valid @RequestBody ThresholdDto request) {
        LOGGER.info("Setting threshold for product with code: {}", request.getProductCode());

        try {
            ProductDto updatedProduct = productService.setProductThreshold(request.getProductCode(), request.getThresholdQuantity());
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (ProductNotFound e) {
            LOGGER.error("Product not found: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LOGGER.error("Error while setting threshold: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
            description = "fetch products below the threshold",
            tags = { "ThresholdDto", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = ThresholdDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/threshold-alerts")
    public ResponseEntity<List<ProductDto>> getAllProductBelowQuantityThreshold(){
        LOGGER.info("Getting list of product below quantity threshold");
        return new ResponseEntity<List<ProductDto>>(productService.getProductsBelowThreshold(), HttpStatus.OK);
    }

}
