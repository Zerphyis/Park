package github.com.Zerphyis.park.application.exit;

import java.time.LocalDateTime;

public record DataExitResponse(
                               Integer numberPark,
                               String carPlate,
                               LocalDateTime exitDateTime,
                               Double valueCharged) {
}
