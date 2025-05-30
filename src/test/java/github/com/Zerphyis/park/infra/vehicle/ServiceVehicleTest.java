package github.com.Zerphyis.park.infra.vehicle;

import github.com.Zerphyis.park.application.VehicleNotFound;
import github.com.Zerphyis.park.application.vehicle.DataVehicle;
import github.com.Zerphyis.park.domain.vehicle.RepositoryVehicle;
import github.com.Zerphyis.park.domain.vehicle.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceVehicleTest {
    @InjectMocks
    private ServiceVehicle service;

    @Mock
    private RepositoryVehicle repository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerVehicle_success() {
        DataVehicle data = new DataVehicle("ABC1234", null);
        Vehicle vehicle = new Vehicle(data);

        when(repository.save(any(Vehicle.class))).thenReturn(vehicle);

        Vehicle created = service.registerVehicle(data);

        assertEquals(data.carPlate(), created.getCarPlate());
        verify(repository).save(any(Vehicle.class));
    }

    @Test
    void listVehicle_success() {
        List<Vehicle> list = List.of(new Vehicle(new DataVehicle("ABC1234", null)));
        when(repository.findAll()).thenReturn(list);

        List<Vehicle> result = service.listVehicle();

        assertFalse(result.isEmpty());
        verify(repository).findAll();
    }

    @Test
    void updateVehicle_success() {
        Long id = 1L;
        DataVehicle data = new DataVehicle("ABC1234", null);
        Vehicle existingVehicle = new Vehicle(new DataVehicle("XYZ9876", null));

        when(repository.findById(id)).thenReturn(Optional.of(existingVehicle));
        when(repository.save(existingVehicle)).thenReturn(existingVehicle);

        Vehicle updated = service.updateveihcle(id, data);

        assertEquals(data.typeClient(), updated.getTypeClient());
        verify(repository).findById(id);
        verify(repository).save(existingVehicle);
    }

    @Test
    void updateVehicle_notFound() {
        Long id = 1L;
        DataVehicle data = new DataVehicle("ABC1234", null);

        when(repository.findById(id)).thenReturn(Optional.empty());

        VehicleNotFound exception = assertThrows(VehicleNotFound.class, () -> {
            service.updateveihcle(id, data);
        });

        assertTrue(exception.getMessage().contains("não encontrado"));
    }

    @Test
    void deleteVehicle_success() {
        Long id = 1L;

        when(repository.existsById(id)).thenReturn(true);
        doNothing().when(repository).deleteById(id);

        assertDoesNotThrow(() -> service.deleteVehicle(id));
        verify(repository).deleteById(id);
    }

    @Test
    void deleteVehicle_notFound() {
        Long id = 1L;

        when(repository.existsById(id)).thenReturn(false);

        VehicleNotFound exception = assertThrows(VehicleNotFound.class, () -> {
            service.deleteVehicle(id);
        });

        assertTrue(exception.getMessage().contains("não encontrado"));
    }

}