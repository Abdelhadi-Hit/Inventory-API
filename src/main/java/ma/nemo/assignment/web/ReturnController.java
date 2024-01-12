package ma.nemo.assignment.web;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import ma.nemo.assignment.domain.Return;
import ma.nemo.assignment.dto.ReturnDto;
import ma.nemo.assignment.exceptions.ProductNotFound;
import ma.nemo.assignment.service.ReturnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Product Return", description = "Product Return API")
@RestController
@RequestMapping("/api/return")
public class ReturnController {

    @Autowired
    private ReturnService returnService;
    private Logger LOGGER = LoggerFactory.getLogger(ReturnController.class);


    @Operation(
            description = "Handle the return of products.",
            tags = { "Return", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Return.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })

    @PostMapping
    public ResponseEntity<?> returnProduct(@Valid @RequestBody ReturnDto returnProductDTO) {
        try {
            LOGGER.info("Return Product to stock: " + returnProductDTO.getProductCode());
            ReturnDto returnResult = returnService.returnProduct(returnProductDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(returnResult);
        } catch (ProductNotFound ex) {
            LOGGER.error("Product not found: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found: " + ex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("An error occurred: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
        }
    }
}
