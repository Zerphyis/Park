package github.com.Zerphyis.park.infra.subscription;

import static org.junit.jupiter.api.Assertions.*;
import github.com.Zerphyis.park.application.exceptions.SubscriptionNotAllowedException;
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
import java.util.List;

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

    @Test
    void update_ReturnsResponse_WhenServiceSucceeds() {
        when(service.update(eq(1L), any(DataRequestSubscription.class))).thenReturn(validResponse);

        var response = controller.update(1L, validRequest);

        assertNotNull(response);
        assertEquals("ABC1234", response.carPlate());
        verify(service).update(1L, validRequest);
    }

    @Test
    void update_ThrowsSubscriptionNotAllowedException_WhenServiceThrows() {
        when(service.update(eq(1L), any(DataRequestSubscription.class)))
                .thenThrow(new SubscriptionNotAllowedException("Assinatura não permitida"));

        assertThrows(SubscriptionNotAllowedException.class, () -> controller.update(1L, validRequest));
        verify(service).update(1L, validRequest);
    }

    @Test
    void delete_CallsServiceDelete() {
        doNothing().when(service).delete(1L);

        controller.delete(1L);

        verify(service).delete(1L);
    }

    @Test
    void delete_ThrowsRuntimeException_WhenServiceThrows() {
        doThrow(new RuntimeException("Não encontrado")).when(service).delete(1L);

        assertThrows(RuntimeException.class, () -> controller.delete(1L));
        verify(service).delete(1L);
    }

    @Test
    void listAll_ReturnsList() {
        when(service.listAll()).thenReturn(List.of(validResponse));

        var list = controller.listAll();

        assertNotNull(list);
        assertEquals(1, list.size());
        verify(service).listAll();
    }

    @Test
    void expiringSoon_ReturnsList() {
        when(service.listExpiringSoon()).thenReturn(List.of(validResponse));

        var list = controller.expiringSoon();

        assertNotNull(list);
        assertEquals(1, list.size());
        verify(service).listExpiringSoon();
    }

}