package github.com.Zerphyis.park.infra.subscription;

import static org.junit.jupiter.api.Assertions.*;
import github.com.Zerphyis.park.application.exceptions.SubscriptionNotAllowedException;
import github.com.Zerphyis.park.application.exceptions.VehicleNotFound;
import github.com.Zerphyis.park.application.subscription.DataRequestSubscription;
import github.com.Zerphyis.park.application.subscription.DataResponseSubscription;
import github.com.Zerphyis.park.application.vehicle.TypeClient;
import github.com.Zerphyis.park.domain.subscription.RepositorySubscription;
import github.com.Zerphyis.park.domain.subscription.Subscription;
import github.com.Zerphyis.park.domain.vehicle.RepositoryVehicle;
import github.com.Zerphyis.park.domain.vehicle.Vehicle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)

class ServiceSubscriptionTest {

    @Mock
    private RepositorySubscription subscriptionRepository;

    @Mock
    private RepositoryVehicle vehicleRepository;

    @InjectMocks
    private ServiceSubscription service;

    @Test
    void createSubscription_HappyPath() {
        Vehicle vehicle = new Vehicle(1L, "ABC1234", TypeClient.MENSALISTA);
        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));

        DataRequestSubscription request = new DataRequestSubscription(1L);
        DataResponseSubscription response = service.create(request);

        assertEquals("ABC1234", response.carPlate());
        assertEquals(50.0, response.pagmentMouth());
        verify(subscriptionRepository).save(any(Subscription.class));
    }

    @Test
    void createSubscription_VehicleNotFound() {
        when(vehicleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(VehicleNotFound.class, () -> service.create(new DataRequestSubscription(1L)));
    }

    @Test
    void createSubscription_NotAllowed() {
        Vehicle vehicle = new Vehicle(1L, "XYZ9999", TypeClient.HORISTA);
        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));

        assertThrows(SubscriptionNotAllowedException.class, () -> service.create(new DataRequestSubscription(1L)));
    }

    @Test
    void deleteSubscription_HappyPath() {
        when(subscriptionRepository.existsById(1L)).thenReturn(true);
        assertDoesNotThrow(() -> service.delete(1L));
        verify(subscriptionRepository).deleteById(1L);
    }

}