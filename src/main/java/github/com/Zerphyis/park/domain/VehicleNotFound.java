package github.com.Zerphyis.park.domain;

public class VehicleNotFound extends RuntimeException {
    public VehicleNotFound(String message) {
        super(message);
    }
}
