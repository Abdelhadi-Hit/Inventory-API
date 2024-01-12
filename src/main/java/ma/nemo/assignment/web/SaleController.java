package ma.nemo.assignment.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import ma.nemo.assignment.domain.Sale;
import ma.nemo.assignment.dto.SaleDto;
import ma.nemo.assignment.dto.SupplyDto;
import ma.nemo.assignment.exceptions.ProductNotFound;
import ma.nemo.assignment.exceptions.ProductStockShortage;
import ma.nemo.assignment.service.SaleService;
import ma.nemo.assignment.service.SupplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Sale Product", description = "Sale API")
@RestController
@RequestMapping("/api/sale")
public class SaleController {

    @Autowired
    private SaleService saleService;

    private Logger LOGGER = LoggerFactory.getLogger(SaleController.class);

    @Operation(
            description = "handle the sale of products",
            tags = { "Sale", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Sale.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping
    public ResponseEntity<?> addSale(@Valid @RequestBody SaleDto saleDTO) {
        try {
            LOGGER.info("New Sale for product: " + saleDTO.getProductCode());
            SaleDto result = saleService.addSale(saleDTO);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (ProductNotFound e) {
            LOGGER.error("Product not found: " + e.getMessage());
            return new ResponseEntity<>("Product not found: " + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ProductStockShortage e) {
            LOGGER.error("Product quantity not in stock: " + e.getMessage());
            return new ResponseEntity<>("Product quantity not in stock: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
