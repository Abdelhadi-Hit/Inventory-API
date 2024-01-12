package ma.nemo.assignment.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import ma.nemo.assignment.domain.Supply;
import ma.nemo.assignment.dto.SupplyDto;
import ma.nemo.assignment.exceptions.ProductNotFound;
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

@Tag(name = "Supply", description = "Supply API")
@RestController
@RequestMapping("/api/supply")
public class SupplyController {
    @Autowired
    private SupplyService supplyService;

    private Logger LOGGER = LoggerFactory.getLogger(SupplyController.class);




    @Operation(
            description = "handle the addition of products to the inventory",
            tags = { "Supply", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Supply.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping
    public ResponseEntity<?> addProductToInventory(@Valid @RequestBody SupplyDto supplyDTO) {
        LOGGER.info("Create a new Supply Transaction: {}", supplyDTO);

        try {
            SupplyDto result = supplyService.addProductToInventory(supplyDTO);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (ProductNotFound e) {
            LOGGER.error("Product not found: {}", e.getMessage());
            return new ResponseEntity<>("Product not found: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
