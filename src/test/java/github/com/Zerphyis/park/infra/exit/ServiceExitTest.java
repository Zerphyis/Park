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
import github.com.Zerphyis.park.application.exceptions.ExitAlreadyExist;
import github.com.Zerphyis.park.domain.exit.Exit;
import org.mockito.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
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

    @Test
    void registerExit_ExitAlreadyExist() {
        Long entryId = 3L;
        Entry mockEntry = mock(Entry.class);
        when(mockEntry.getId()).thenReturn(entryId);
        when(repoEntry.findById(entryId)).thenReturn(Optional.of(mockEntry));
        when(repoExit.existsByEntry_Id(entryId)).thenReturn(true);

        ExitAlreadyExist exception = assertThrows(ExitAlreadyExist.class, () -> {
            service.registerExit(new DataExitRequest(entryId));
        });

        assertEquals("Já existe uma saída para esta entrada.", exception.getMessage());
        verify(repoExit, never()).save(any());
    }

    @Test
    void listAllExits_Success() {
        Exit mockExit = mock(Exit.class);
        Entry mockEntry = mock(Entry.class);
        var mockSpot = mock(github.com.Zerphyis.park.domain.spot.Spot.class);
        var mockVehicle = mock(github.com.Zerphyis.park.domain.vehicle.Vehicle.class);

        when(mockSpot.getNumberPark()).thenReturn(10);
        when(mockVehicle.getCarPlate()).thenReturn("ABC-9999");
        when(mockEntry.getSpot()).thenReturn(mockSpot);
        when(mockEntry.getVehicle()).thenReturn(mockVehicle);
        when(mockExit.getEntry()).thenReturn(mockEntry);
        when(mockExit.getExitDateTime()).thenReturn(LocalDateTime.now());
        when(mockExit.getValueCharged()).thenReturn(20.0);

        when(repoExit.findAll()).thenReturn(List.of(mockExit));

        List<DataExitResponse> list = service.listAllExits();

        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(10, list.get(0).numberPark());
        assertEquals("ABC-9999", list.get(0).carPlate());
        assertTrue(list.get(0).valueCharged() > 0);
    }

    @Test
    void listAllExits_EmptyList() {
        when(repoExit.findAll()).thenReturn(Collections.emptyList());

        List<DataExitResponse> list = service.listAllExits();

        assertNotNull(list);
        assertTrue(list.isEmpty());
    }


}