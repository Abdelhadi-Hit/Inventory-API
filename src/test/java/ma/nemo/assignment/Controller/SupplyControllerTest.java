package ma.nemo.assignment.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ma.nemo.assignment.dto.SupplyDto;
import ma.nemo.assignment.mapper.SupplyMapper;
import ma.nemo.assignment.service.SupplyService;
import ma.nemo.assignment.web.SupplyController;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
public class SupplyControllerTest {
    private MockMvc mockMvc;

    @Mock
    private SupplyService supplyService;

    @InjectMocks
    private SupplyController supplyController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(supplyController).build();
    }

    @Test
    public void testAddSupply() throws Exception {
        SupplyDto supplyDTO = new SupplyDto();
        supplyDTO.setProductCode("TEST1");
        supplyDTO.setQuantity(10);
        supplyDTO.setExpirationDate(LocalDateTime.parse("2023-12-20T14:30:00"));

        Mockito.when(supplyService.addProductToInventory(supplyDTO)).thenReturn(supplyDTO);

        String supplyJson = objectMapper.writeValueAsString(supplyDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/supply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(supplyJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }
}
