package github.com.Zerphyis.park.application.entry;

import java.time.LocalDateTime;

public record DataEntryResponse(String carPlate, Integer numberPark, LocalDateTime entryDateTime) {
}
