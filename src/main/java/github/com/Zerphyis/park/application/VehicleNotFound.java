package github.com.Zerphyis.park.application;

public class VehicleNotFound extends RuntimeException {
    public VehicleNotFound(String message) {
        super(message);
    }
}
