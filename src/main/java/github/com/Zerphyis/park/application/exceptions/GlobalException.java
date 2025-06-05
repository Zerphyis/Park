package github.com.Zerphyis.park.application.exceptions;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;


@RestControllerAdvice
public class GlobalException {


    private static final Logger log = LoggerFactory.getLogger(GlobalException.class);

    public record ErrorResponse(String error, String message) {}

    @ExceptionHandler(SpotNotFound.class)
    public ResponseEntity<ErrorResponse> handleSpotNotFound(SpotNotFound ex) {
        var error = new ErrorResponse("SpotNotFound", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(VehicleNotFound.class)
    public ResponseEntity<ErrorResponse> handleVehicleNotFound(VehicleNotFound ex) {
        var error = new ErrorResponse("VehicleNotFound", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(EntryNotFound.class)
    public ResponseEntity<ErrorResponse> handleEntryNotFound(EntryNotFound ex) {
        var error = new ErrorResponse("EntryNotFound", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ExitAlreadyExist.class)
    public ResponseEntity<ErrorResponse> handleExitAlreadyExist(ExitAlreadyExist ex) {
        var error = new ErrorResponse("ExitAlreadyExist", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(ExitNotFound.class)
    public ResponseEntity<ErrorResponse> exitNotFound(ExitNotFound ex) {
        var error = new ErrorResponse("ExitNotFound", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(SubscriptionNotAllowedException.class)
    public ResponseEntity<ErrorResponse> handleSubscriptionNotAllowed(SubscriptionNotAllowedException ex) {
        var error = new ErrorResponse("SubscriptionNotAllowed", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        var error = new ErrorResponse("IllegalArgument", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        log.error("Erro interno inesperado", ex);
        var error = new ErrorResponse("InternalServerError", "Erro interno: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
