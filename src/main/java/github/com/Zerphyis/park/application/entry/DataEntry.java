package github.com.Zerphyis.park.application.entry;

import github.com.Zerphyis.park.domain.spot.Spot;
import github.com.Zerphyis.park.domain.vehicle.Vehicle;


public record DataEntry(Spot spot, Vehicle vehicle) {
}
