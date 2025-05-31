package github.com.Zerphyis.park.infra.vehicle;
import com.fasterxml.jackson.databind.ObjectMapper;
import github.com.Zerphyis.park.application.vehicle.DataVehicle;
import github.com.Zerphyis.park.application.vehicle.TypeClient;
import github.com.Zerphyis.park.domain.vehicle.Vehicle;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(ControllerVehicle.class)

class ControllerVehicleTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceVehicle service;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void register_success() throws Exception {
        DataVehicle data = new DataVehicle("ABC1234", TypeClient.HORISTA);
        Vehicle created = new Vehicle(data);
        created.setCarPlate("ABC1234");

        Mockito.when(service.registerVehicle(any(DataVehicle.class))).thenReturn(created);

        mockMvc.perform(post("/veiculos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(data)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.carPlate").value("ABC1234"));
    }

    @Test
    void register_validationError() throws Exception {
        DataVehicle invalid = new DataVehicle("", null);

        mockMvc.perform(post("/veiculos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void list_success() throws Exception {
        Vehicle vehicle = new Vehicle(new DataVehicle("DEF5678", TypeClient.MENSALISTA));
        vehicle.setCarPlate("DEF5678");

        Mockito.when(service.listVehicle()).thenReturn(List.of(vehicle));

        mockMvc.perform(get("/veiculos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].carPlate").value("DEF5678"));
    }

}