package github.com.Zerphyis.park.infra.exit;

import github.com.Zerphyis.park.application.exit.DataExitRequest;
import github.com.Zerphyis.park.application.exit.DataExitResponse;
import github.com.Zerphyis.park.domain.entry.Entry;
import github.com.Zerphyis.park.domain.entry.RepositoryEntry;
import github.com.Zerphyis.park.domain.exit.RepositoryExit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import github.com.Zerphyis.park.application.exceptions.EntryNotFound;
import github.com.Zerphyis.park.domain.exit.Exit;
import org.mockito.*;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class ServiceExitTest {
    @InjectMocks
    private ServiceExit service;

    @Mock
    private RepositoryExit repoExit;

    @Mock
    private RepositoryEntry repoEntry;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerExit_Sucess(){
        Long entryId=1L;
        Entry mockEntry =mock(Entry.class);
        when(mockEntry.getId()).thenReturn(entryId);
        when(mockEntry.getEntryDateTime()).thenReturn(LocalDateTime.now().minusHours(1));

        var mockSpot=mock(github.com.Zerphyis.park.domain.spot.Spot.class);
        when(mockSpot.getNumberPark()).thenReturn(1);
        when(mockEntry.getSpot()).thenReturn(mockSpot);
        var mockVehicle = mock(github.com.Zerphyis.park.domain.vehicle.Vehicle.class);
        when(mockVehicle.getCarPlate()).thenReturn("XYZ-1234");
        when(mockEntry.getVehicle()).thenReturn(mockVehicle);

        when(repoEntry.findById(entryId)).thenReturn(Optional.of(mockEntry));
        when(repoExit.existsByEntry_Id(entryId)).thenReturn(false);

        DataExitResponse response = service.registerExit(new DataExitRequest(entryId));

        assertNotNull(response);
        assertEquals(1, response.numberPark());
        assertEquals("XYZ-1234", response.carPlate());
        assertNotNull(response.exitDateTime());
        assertTrue(response.valueCharged() > 0);

        verify(repoExit, times(1)).save(any(Exit.class));
    }

    @Test
    void registerExit_EntryNotFound() {
        Long entryId = 2L;
        when(repoEntry.findById(entryId)).thenReturn(Optional.empty());

        EntryNotFound exception = assertThrows(EntryNotFound.class, () -> {
            service.registerExit(new DataExitRequest(entryId));
        });

        assertEquals("Entrada não encontrada com id: " + entryId, exception.getMessage());
        verify(repoExit, never()).save(any());
    }

}