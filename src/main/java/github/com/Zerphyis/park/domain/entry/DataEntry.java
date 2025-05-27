package github.com.Zerphyis.park.domain.entry;

import github.com.Zerphyis.park.infra.spot.Spot;
import github.com.Zerphyis.park.infra.vehicle.Vehicle;

import java.time.LocalDateTime;

public record DataEntry(Spot spot, Vehicle vehicle, LocalDateTime entryDateTime) {
}
