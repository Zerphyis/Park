package github.com.Zerphyis.park.application.payment;

public record DataPaymentRequest(Long exitId, String method, boolean confimed) {
}
