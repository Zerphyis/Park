package github.com.Zerphyis.park.application;

public class ExitAlreadyExist extends RuntimeException {
    public ExitAlreadyExist(String message) {
        super(message);
    }
}
