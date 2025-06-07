package github.com.Zerphyis.park.infra.payment;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import github.com.Zerphyis.park.application.exceptions.ExitNotFound;
import github.com.Zerphyis.park.application.payment.DataPaymentRequest;
import github.com.Zerphyis.park.application.payment.DataPaymentResponse;
import github.com.Zerphyis.park.domain.exit.Exit;
import github.com.Zerphyis.park.domain.exit.RepositoryExit;
import github.com.Zerphyis.park.domain.payment.Payment;
import github.com.Zerphyis.park.domain.payment.RepositoryPayment;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServicePaymentTest {

    @InjectMocks
    private ServicePayment servicePayment;

    @Mock
    private RepositoryPayment repoPayment;

    @Mock
    private RepositoryExit repoExit;

    @Test
    void create_shouldReturnDataPaymentResponse_whenExitExists() {
        Exit exit = mock(Exit.class);
        when(repoExit.findById(1L)).thenReturn(Optional.of(exit));
        when(exit.getId()).thenReturn(1L);
        when(exit.getValueCharged()).thenReturn(50.0);

        DataPaymentRequest request = new DataPaymentRequest(1L, "CARTAO", true);

        DataPaymentResponse response = servicePayment.create(request);

        assertEquals(1L, response.exitId());
        assertEquals(50.0, response.value());
        assertEquals("CARTAO", response.method());
        assertTrue(response.confirmado());

        verify(repoPayment, times(1)).save(any(Payment.class));
    }

    @Test
    void create_shouldThrowExitNotFound_whenExitDoesNotExist() {
        when(repoExit.findById(1L)).thenReturn(Optional.empty());

        DataPaymentRequest request = new DataPaymentRequest(1L, "PIX", true);

        ExitNotFound thrown = assertThrows(ExitNotFound.class, () -> {
            servicePayment.create(request);
        });

        assertTrue(thrown.getMessage().contains("saida com id"));
    }


}