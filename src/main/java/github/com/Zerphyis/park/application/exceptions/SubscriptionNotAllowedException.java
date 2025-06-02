package github.com.Zerphyis.park.application.exceptions;

public class SubscriptionNotAllowedException extends RuntimeException {
    public SubscriptionNotAllowedException(String message) {
        super(message);
    }
}
