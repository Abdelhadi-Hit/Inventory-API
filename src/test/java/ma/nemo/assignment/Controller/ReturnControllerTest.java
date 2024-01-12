package ma.nemo.assignment.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ma.nemo.assignment.dto.ReturnDto;
import ma.nemo.assignment.dto.SaleDto;
import ma.nemo.assignment.service.ReturnService;
import ma.nemo.assignment.service.SaleService;
import ma.nemo.assignment.web.ReturnController;
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
public class ReturnControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ReturnService returnService;

    @InjectMocks
    private ReturnController returnController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(returnController).build();
    }

    @Test
    public void testReturnProduct() throws Exception {
        ReturnDto returnDto = new ReturnDto();
        returnDto.setProductCode("TEST1");
        returnDto.setQuantity(10);
        returnDto.setReason("Just a test");

        Mockito.when(returnService.returnProduct(returnDto)).thenReturn(returnDto);

        String supplyJson = objectMapper.writeValueAsString(returnDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/return")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(supplyJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }
}
