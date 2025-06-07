package github.com.Zerphyis.park.infra.subscription;

import static org.junit.jupiter.api.Assertions.*;

import github.com.Zerphyis.park.application.exceptions.VehicleNotFound;
import github.com.Zerphyis.park.application.subscription.DataRequestSubscription;
import github.com.Zerphyis.park.application.subscription.DataResponseSubscription;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;


import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ControllerSubscriptionTest {
    @Mock
    private ServiceSubscription service;

    @InjectMocks
    private ControllerSubscription controller;

    private DataRequestSubscription validRequest;
    private DataResponseSubscription validResponse;

    @BeforeEach
    void setup() {
        validRequest = new DataRequestSubscription(1L);
        validResponse = new DataResponseSubscription("ABC1234", LocalDate.now(), LocalDate.now().plusMonths(1), 50.0);
    }

    @Test
    void create_ReturnsResponse_WhenServiceSucceeds() {
        when(service.create(validRequest)).thenReturn(validResponse);

        var response = controller.create(validRequest);

        assertNotNull(response);
        assertEquals("ABC1234", response.carPlate());
        verify(service).create(validRequest);
    }

    @Test
    void create_ThrowsVehicleNotFound_WhenServiceThrows() {
        when(service.create(validRequest)).thenThrow(new VehicleNotFound("Veículo não encontrado"));

        assertThrows(VehicleNotFound.class, () -> controller.create(validRequest));
        verify(service).create(validRequest);
    }


}