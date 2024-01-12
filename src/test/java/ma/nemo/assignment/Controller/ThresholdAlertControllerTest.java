package ma.nemo.assignment.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ma.nemo.assignment.dto.ProductDto;
import ma.nemo.assignment.dto.ThresholdDto;
import ma.nemo.assignment.service.ProductService;
import ma.nemo.assignment.web.ThresholdAlertController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc

public class ThresholdAlertControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ThresholdAlertController thresholdAlertController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(thresholdAlertController).build();
    }

    @Test
    public void testSetProductThreshold() throws Exception {
        ThresholdDto request = new ThresholdDto();
        request.setProductCode("TEST_PRODUCT");
        request.setThresholdQuantity(10);
        ObjectMapper objectMapper = new ObjectMapper();
        String setThresholdJson = objectMapper.writeValueAsString(request);

        ProductDto expectedProduct = new ProductDto();

        when(productService.setProductThreshold(eq(request.getProductCode()), eq(request.getThresholdQuantity()))).thenReturn(expectedProduct);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/set-threshold")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(setThresholdJson))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetProductsBelowThreshold() throws Exception {
        List<ProductDto> products = new ArrayList<>();
        ProductDto product1 = new ProductDto();
        product1.setProductName("Product 1");
        product1.setProductCode("TEST1");
        product1.setDescription("Test description");
        product1.setUnitPrice(16.0);
        product1.setQuantityInStock(100);
        product1.setThresholdQuantity(200);

        products.add(product1);

        when(productService.getProductsBelowThreshold()).thenReturn(products);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/threshold-alerts")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


}
