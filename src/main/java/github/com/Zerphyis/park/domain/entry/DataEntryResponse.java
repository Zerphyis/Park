package github.com.Zerphyis.park.domain.entry;

import java.time.LocalDateTime;

public record DataEntryResponse(String carPlate, Integer numberPark, LocalDateTime entryDateTime) {
}
