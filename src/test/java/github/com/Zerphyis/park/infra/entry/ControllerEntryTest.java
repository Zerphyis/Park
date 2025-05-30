package github.com.Zerphyis.park.infra.entry;

import com.fasterxml.jackson.databind.ObjectMapper;
import github.com.Zerphyis.park.application.SpotNotFound;
import github.com.Zerphyis.park.application.EntryNotFound;
import github.com.Zerphyis.park.application.entry.DataEntryRequest;
import github.com.Zerphyis.park.application.entry.DataEntryResponse;
import github.com.Zerphyis.park.infra.entry.ControllerEntry;
import github.com.Zerphyis.park.infra.entry.ServiceEntry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@ExtendWith(SpringExtension.class)
@WebMvcTest(ControllerEntry.class)

class ControllerEntryTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceEntry service;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldCreateEntrySuccessfully() throws Exception {
        DataEntryRequest request = new DataEntryRequest(1L, 2L);
        DataEntryResponse response = new DataEntryResponse("ABC-1234", 42, LocalDateTime.of(2024, 5, 30, 10, 0));

        when(service.createEntry(request)).thenReturn(response);

        mockMvc.perform(post("/entrada")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.carPlate").value("ABC-1234"))
                .andExpect(jsonPath("$.numberPark").value(42))
                .andExpect(jsonPath("$.entryDateTime").exists());
    }

    @Test
    void shouldThrowExceptionWhenCreatingEntryWithSpotInUse() throws Exception {
        DataEntryRequest request = new DataEntryRequest(1L, 2L);

        when(service.createEntry(request))
                .thenThrow(new SpotNotFound("Vaga já está ocupada: 2"));

        mockMvc.perform(post("/entrada")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldListAllEntriesSuccessfully() throws Exception {
        List<DataEntryResponse> responses = List.of(
                new DataEntryResponse("XYZ-9999", 101, LocalDateTime.of(2024, 5, 30, 12, 0))
        );

        when(service.listAllEntries()).thenReturn(responses);

        mockMvc.perform(get("/entrada"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].carPlate").value("XYZ-9999"))
                .andExpect(jsonPath("$[0].numberPark").value(101));
    }

    @Test
    void shouldDeleteEntrySuccessfully() throws Exception {
        mockMvc.perform(delete("/entrada/{id}", 5L))
                .andExpect(status().isNoContent());

        verify(service).deleteEntry(5L);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonexistentEntry() throws Exception {
        doThrow(new EntryNotFound("Entrada com o Id não encontrada 99"))
                .when(service).deleteEntry(99L);

        mockMvc.perform(delete("/entrada/{id}", 99L))
                .andExpect(status().isNotFound());
    }
}