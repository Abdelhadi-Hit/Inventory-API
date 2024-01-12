package ma.nemo.assignment.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ma.nemo.assignment.dto.SaleDto;
import ma.nemo.assignment.service.SaleService;
import ma.nemo.assignment.web.SaleController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class SaleControllerTest {
    private MockMvc mockMvc;

    @Mock
    private SaleService saleService;

    @InjectMocks
    private SaleController saleController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(saleController).build();
    }

    @Test
    public void testAddSupply() throws Exception {
        SaleDto saleDTO = new SaleDto();
        saleDTO.setProductCode("TEST1");
        saleDTO.setQuantity(10);

        Mockito.when(saleService.addSale(saleDTO)).thenReturn(saleDTO);

        String supplyJson = objectMapper.writeValueAsString(saleDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/sale")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(supplyJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }
}
