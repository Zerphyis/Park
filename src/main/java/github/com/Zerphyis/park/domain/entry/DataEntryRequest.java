package github.com.Zerphyis.park.domain.entry;

import java.time.LocalDateTime;

public record DataEntryRequest(Long vehicleId, Long spotId, LocalDateTime dateTime) {
}
