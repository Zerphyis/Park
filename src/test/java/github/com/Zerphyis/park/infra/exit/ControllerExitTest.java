package github.com.Zerphyis.park.infra.exit;

import github.com.Zerphyis.park.application.exit.DataExitRequest;
import github.com.Zerphyis.park.application.exit.DataExitResponse;
import github.com.Zerphyis.park.application.exceptions.EntryNotFound;
import github.com.Zerphyis.park.application.exceptions.ExitAlreadyExist;

import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ControllerExit.class)
class ControllerExitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceExit serviceExit;

    private final String baseUrl = "/saida";

    @Test
    void registerExit_Success() throws Exception {
        DataExitResponse mockResponse = new DataExitResponse(
                1, "XYZ-1234", LocalDateTime.now(), 15.0);

        when(serviceExit.registerExit(any(DataExitRequest.class))).thenReturn(mockResponse);

        String jsonRequest = "{\"entryId\":1}";

        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberPark").value(1))
                .andExpect(jsonPath("$.carPlate").value("XYZ-1234"))
                .andExpect(jsonPath("$.exitDateTime").exists())
                .andExpect(jsonPath("$.valueCharged").value(15.0));

        verify(serviceExit, times(1)).registerExit(any(DataExitRequest.class));
    }

    @Test
    void registerExit_EntryNotFound() throws Exception {
        when(serviceExit.registerExit(any(DataExitRequest.class)))
                .thenThrow(new EntryNotFound("Entrada não encontrada com id: 1"));

        String jsonRequest = "{\"entryId\":1}";

        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Entrada não encontrada com id: 1"));

        verify(serviceExit, times(1)).registerExit(any(DataExitRequest.class));
    }

    @Test
    void registerExit_ExitAlreadyExist() throws Exception {
        when(serviceExit.registerExit(any(DataExitRequest.class)))
                .thenThrow(new ExitAlreadyExist("Já existe uma saída para esta entrada."));

        String jsonRequest = "{\"entryId\":1}";

        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isConflict())
                .andExpect(content().string("Já existe uma saída para esta entrada."));

        verify(serviceExit, times(1)).registerExit(any(DataExitRequest.class));
    }

    @Test
    void listAllExits_Success() throws Exception {
        DataExitResponse mockResponse1 = new DataExitResponse(
                1, "ABC-1234", LocalDateTime.now(), 20.0);
        DataExitResponse mockResponse2 = new DataExitResponse(
                2, "XYZ-5678", LocalDateTime.now(), 30.0);

        when(serviceExit.listAllExits()).thenReturn(java.util.List.of(mockResponse1, mockResponse2));

        mockMvc.perform(get(baseUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].numberPark").value(1))
                .andExpect(jsonPath("$[0].carPlate").value("ABC-1234"))
                .andExpect(jsonPath("$[1].numberPark").value(2))
                .andExpect(jsonPath("$[1].carPlate").value("XYZ-5678"));

        verify(serviceExit, times(1)).listAllExits();
    }


}