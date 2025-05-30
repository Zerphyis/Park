package github.com.Zerphyis.park.infra.entry;


import github.com.Zerphyis.park.application.entry.DataEntry;
import github.com.Zerphyis.park.application.entry.DataEntryRequest;
import github.com.Zerphyis.park.application.entry.DataEntryResponse;
import github.com.Zerphyis.park.application.spot.TypeSpot;
import github.com.Zerphyis.park.application.vehicle.TypeClient;
import github.com.Zerphyis.park.domain.entry.Entry;
import github.com.Zerphyis.park.domain.entry.RepositoryEntry;
import github.com.Zerphyis.park.domain.spot.RepositorySpot;
import github.com.Zerphyis.park.domain.spot.Spot;
import github.com.Zerphyis.park.domain.vehicle.RepositoryVehicle;
import github.com.Zerphyis.park.domain.vehicle.Vehicle;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

class ServiceEntryTest {

    @Mock
    private RepositoryEntry repoEntry;

    @Mock
    private RepositorySpot repoSpot;

    @Mock
    private RepositoryVehicle repoVehicle;

    @InjectMocks
    private ServiceEntry service;

    private Vehicle mockVehicle() {
        Vehicle v = new Vehicle();
        v.setCarPlate("ABC-1234");
        v.setTypeClient(TypeClient.MENSALISTA);
        return v;
    }

    private Spot mockSpot() {
        Spot s = new Spot();
        s.setNumberPark(42);
        s.setTypeSpot(TypeSpot.LIVRE);
        return s;
    }

    @Test
    void shouldCreateEntrySuccessfully() {
        Spot spot = mockSpot();
        Vehicle vehicle = mockVehicle();

        DataEntryRequest request = new DataEntryRequest(vehicle.getId(), spot.getId());
        DataEntry dataEntry = new DataEntry(spot, vehicle);
        Entry entry = new Entry(dataEntry);

        LocalDateTime now = LocalDateTime.now();
        entry.setEntryDateTime(now);

        when(repoEntry.existsBySpotId(spot.getId())).thenReturn(false);
        when(repoEntry.existsByVehicleId(vehicle.getId())).thenReturn(false);
        when(repoSpot.findById(spot.getId())).thenReturn(Optional.of(spot));
        when(repoVehicle.findById(vehicle.getId())).thenReturn(Optional.of(vehicle));
        when(repoEntry.save(any(Entry.class))).thenReturn(entry);

        DataEntryResponse response = service.createEntry(request);

        assertEquals("ABC-1234", response.carPlate());
        assertEquals(42, response.numberPark());
        assertEquals(now, response.entryDateTime());
    }
}