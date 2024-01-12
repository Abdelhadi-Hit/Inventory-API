package ma.nemo.assignment.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ma.nemo.assignment.domain.Product;
import ma.nemo.assignment.dto.ProductDto;
import ma.nemo.assignment.service.SupplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "expiry-alerts", description = "Product Expiry Tracking API")
@RestController
@RequestMapping("/api/expiry-alerts")
public class ProductExpiryAlertsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductExpiryAlertsController.class);

    @Autowired
    private SupplyService supplyService;

    @Operation(
            description = "Track and fetch products nearing their expiration date.",
            tags = { "products", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Product.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })

    @GetMapping()
    public ResponseEntity<List<ProductDto>> listProductsNearingExpiryDate(@RequestParam int thresholdDays) {
        LOGGER.info("Listing products nearing their expiry date");
        List<ProductDto> productsNearingExpiryDate = supplyService.getProductsNearingExpiryDate(thresholdDays);

        if (productsNearingExpiryDate.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(productsNearingExpiryDate, HttpStatus.OK);
        }
    }
}
