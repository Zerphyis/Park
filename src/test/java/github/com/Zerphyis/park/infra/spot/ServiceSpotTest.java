package github.com.Zerphyis.park.infra.spot;

import static org.junit.jupiter.api.Assertions.*;
import github.com.Zerphyis.park.application.exceptions.SpotNotFound;
import github.com.Zerphyis.park.application.spot.DataSpot;
import github.com.Zerphyis.park.application.spot.TypeSpot;
import github.com.Zerphyis.park.domain.spot.RepositorySpot;
import github.com.Zerphyis.park.domain.spot.Spot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ServiceSpotTest {


    @Mock
    private RepositorySpot repository;

    @InjectMocks
    private ServiceSpot service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerSpot_success() {
        DataSpot data = new DataSpot(1, TypeSpot.LIVRE);

        when(repository.existsByNumberPark(1)).thenReturn(false);
        when(repository.save(any(Spot.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Spot saved = service.registerSpot(data);

        assertNotNull(saved);
        assertEquals(1, saved.getNumberPark());
        assertEquals(TypeSpot.LIVRE, saved.getTypeSpot());
        verify(repository).save(any(Spot.class));
    }

    @Test
    void registerSpot_alreadyExists_throwsException() {
        DataSpot data = new DataSpot(1, TypeSpot.LIVRE);
        when(repository.existsByNumberPark(1)).thenReturn(true);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> service.registerSpot(data));
        assertEquals("Já existe uma vaga com o número 1", ex.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void listSpot_returnsFreeSpots() {
        Spot spot1 = new Spot(new DataSpot(1, TypeSpot.LIVRE));
        Spot spot2 = new Spot(new DataSpot(2, TypeSpot.LIVRE));

        when(repository.findByTypeSpot(TypeSpot.LIVRE)).thenReturn(List.of(spot1, spot2));

        List<Spot> spots = service.listSpot();

        assertEquals(2, spots.size());
        assertTrue(spots.stream().allMatch(s -> s.getTypeSpot() == TypeSpot.LIVRE));
        verify(repository).findByTypeSpot(TypeSpot.LIVRE);
    }

    @Test
    void deleteSpot_success() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        assertDoesNotThrow(() -> service.deleteSpot(1L));
        verify(repository).deleteById(1L);
    }

    @Test
    void deleteSpot_notFound_throwsSpotNotFound() {
        when(repository.existsById(1L)).thenReturn(false);

        SpotNotFound ex = assertThrows(SpotNotFound.class, () -> service.deleteSpot(1L));
        assertEquals("Vaga com id 1 não encontrado.", ex.getMessage());
        verify(repository, never()).deleteById(anyLong());
    }

}