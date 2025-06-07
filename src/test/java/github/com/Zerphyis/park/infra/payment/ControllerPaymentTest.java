package github.com.Zerphyis.park.infra.payment;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import github.com.Zerphyis.park.application.payment.DataPaymentRequest;
import github.com.Zerphyis.park.application.payment.DataPaymentResponse;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ControllerPaymentTest {

    @InjectMocks
    private ControllerPayment controllerPayment;

    @Mock
    private ServicePayment servicePayment;

    @Test
    void create_shouldReturnResponseEntityWithDataPaymentResponse() {
        DataPaymentRequest request = new DataPaymentRequest(1L, "DINHEIRO", true);
        DataPaymentResponse response = new DataPaymentResponse(1L, 50.0, "DINHEIRO", true);

        when(servicePayment.create(request)).thenReturn(response);

        ResponseEntity<DataPaymentResponse> result = controllerPayment.create(request);

        assertEquals(201, result.getStatusCodeValue());
        assertEquals(response, result.getBody());
    }

    @Test
    void update_shouldReturnResponseEntityWithUpdatedDataPaymentResponse() {
        Long id = 10L;
        DataPaymentRequest request = new DataPaymentRequest(1L, "PIX", false);
        DataPaymentResponse response = new DataPaymentResponse(1L, 70.0, "PIX", false);

        when(servicePayment.update(id, request)).thenReturn(response);

        ResponseEntity<DataPaymentResponse> result = controllerPayment.update(id, request);

        assertEquals(200, result.getStatusCodeValue());
        assertEquals(response, result.getBody());
    }



}