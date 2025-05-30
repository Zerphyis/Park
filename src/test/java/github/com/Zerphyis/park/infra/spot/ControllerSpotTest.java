package github.com.Zerphyis.park.infra.spot;

import static org.junit.jupiter.api.Assertions.*;

import github.com.Zerphyis.park.application.exceptions.SpotNotFound;
import github.com.Zerphyis.park.application.spot.DataSpot;
import github.com.Zerphyis.park.application.spot.ResponseDataSpot;
import github.com.Zerphyis.park.application.spot.TypeSpot;
import github.com.Zerphyis.park.domain.spot.Spot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ControllerSpotTest {
    @Mock
    private ServiceSpot service;

    @InjectMocks
    private ControllerSpot controller;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void register_happyPath_returnsCreated() {
        DataSpot data = new DataSpot(1, TypeSpot.LIVRE);
        Spot spot = new Spot(data);
        spot.setNumberPark(1);
        spot.setTypeSpot(TypeSpot.LIVRE);

        when(service.registerSpot(data)).thenReturn(spot);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
        ResponseEntity<ResponseDataSpot> response = controller.register(data, uriBuilder);

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getHeaders().getLocation());
        assertEquals(data.numberPark(), response.getBody().numberPark());
        assertEquals(data.typeSpot(), response.getBody().typeSpot());

        verify(service).registerSpot(data);
    }

    @Test
    void register_sadPath_throwsException() {
        DataSpot data = new DataSpot(1, TypeSpot.LIVRE);
        when(service.registerSpot(data)).thenThrow(new IllegalArgumentException("Já existe uma vaga com o número 1"));

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> controller.register(data, uriBuilder));
        assertEquals("Já existe uma vaga com o número 1", ex.getMessage());

        verify(service).registerSpot(data);
    }

    @Test
    void listFreeSpots_happyPath_returnsList() {
        Spot spot1 = new Spot(new DataSpot(1, TypeSpot.LIVRE));
        Spot spot2 = new Spot(new DataSpot(2, TypeSpot.LIVRE));

        when(service.listSpot()).thenReturn(List.of(spot1, spot2));

        ResponseEntity<List<ResponseDataSpot>> response = controller.listFreeSpots();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        assertEquals(1, response.getBody().get(0).numberPark());
        verify(service).listSpot();
    }

    @Test
    void delete_happyPath_noContent() {
        doNothing().when(service).deleteSpot(1L);

        ResponseEntity<Void> response = controller.delete(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(service).deleteSpot(1L);
    }

    @Test
    void delete_sadPath_throwsSpotNotFound() {
        doThrow(new SpotNotFound("Vaga com id 1 não encontrado."))
                .when(service).deleteSpot(1L);

        SpotNotFound ex = assertThrows(
                SpotNotFound.class,
                () -> controller.delete(1L));

        assertEquals("Vaga com id 1 não encontrado.", ex.getMessage());
        verify(service).deleteSpot(1L);
    }

}