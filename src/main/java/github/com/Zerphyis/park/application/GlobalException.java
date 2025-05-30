package github.com.Zerphyis.park.application;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(SpotNotFound.class)
    public ResponseEntity<String> handleSpotNotFound(SpotNotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(VehicleNotFound.class)
    public ResponseEntity<String> handleVehicleNotFound(VehicleNotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(EntryNotFound.class)
    public ResponseEntity<String> handleEntryNotFound(EntryNotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ExitAlreadyExist.class)
    public ResponseEntity<String> handleExitAlreadyExist(ExitAlreadyExist ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
