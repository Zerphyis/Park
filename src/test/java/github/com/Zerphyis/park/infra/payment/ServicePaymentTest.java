package github.com.Zerphyis.park.infra.payment;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import github.com.Zerphyis.park.application.exceptions.ExitNotFound;
import github.com.Zerphyis.park.application.payment.DataPaymentRequest;
import github.com.Zerphyis.park.application.payment.DataPaymentResponse;
import github.com.Zerphyis.park.application.payment.MethodPayment;
import github.com.Zerphyis.park.domain.exit.Exit;
import github.com.Zerphyis.park.domain.exit.RepositoryExit;
import github.com.Zerphyis.park.domain.payment.Payment;
import github.com.Zerphyis.park.domain.payment.RepositoryPayment;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
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

    @Test
    void update_shouldReturnUpdatedResponse_whenPaymentAndExitExist() {
        Payment payment = mock(Payment.class);
        Exit exit = mock(Exit.class);

        when(repoPayment.findById(10L)).thenReturn(Optional.of(payment));
        when(repoExit.findById(1L)).thenReturn(Optional.of(exit));
        when(exit.getId()).thenReturn(1L);
        when(exit.getValueCharged()).thenReturn(70.0);

        DataPaymentRequest request = new DataPaymentRequest(1L, "DINHEIRO", false);

        DataPaymentResponse response = servicePayment.update(10L, request);

        assertEquals(1L, response.exitId());
        assertEquals(70.0, response.value());
        assertEquals("DINHEIRO", response.method());
        assertFalse(response.confirmado());

        verify(payment).setExit(exit);
        verify(payment).setMethodPayment(MethodPayment.DINHEIRO);
        verify(payment).setConfirmed(false);
        verify(repoPayment).save(payment);
    }

    @Test
    void update_shouldThrowIllegalArgumentException_whenPaymentNotFound() {
        when(repoPayment.findById(10L)).thenReturn(Optional.empty());

        DataPaymentRequest request = new DataPaymentRequest(1L, "DINHEIRO", false);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            servicePayment.update(10L, request);
        });

        assertTrue(ex.getMessage().contains("Pagamento com ID 10 não encontrado"));
    }

    @Test
    void update_shouldThrowExitNotFound_whenExitDoesNotExist() {
        Payment payment = mock(Payment.class);
        when(repoPayment.findById(10L)).thenReturn(Optional.of(payment));
        when(repoExit.findById(1L)).thenReturn(Optional.empty());

        DataPaymentRequest request = new DataPaymentRequest(1L, "PIX", false);

        ExitNotFound ex = assertThrows(ExitNotFound.class, () -> {
            servicePayment.update(10L, request);
        });

        assertTrue(ex.getMessage().contains("Exit ID 1 não encontrado"));
    }

    @Test
    void delete_shouldDeletePayment_whenPaymentExists() {
        Payment payment = mock(Payment.class);
        when(repoPayment.findById(5L)).thenReturn(Optional.of(payment));

        servicePayment.delete(5L);

        verify(repoPayment).delete(payment);
    }

    @Test
    void delete_shouldThrowIllegalArgumentException_whenPaymentNotFound() {
        when(repoPayment.findById(5L)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            servicePayment.delete(5L);
        });

        assertTrue(ex.getMessage().contains("Pagamento com ID 5 não encontrado"));
    }

    @Test
    void findAll_shouldReturnListOfPayments() {
        List<Payment> payments = List.of(mock(Payment.class), mock(Payment.class));
        when(repoPayment.findAll()).thenReturn(payments);

        List<Payment> result = servicePayment.findAll();

        assertEquals(2, result.size());
        verify(repoPayment).findAll();
    }

}