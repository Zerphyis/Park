package github.com.Zerphyis.park.application.subscription;

import java.time.LocalDate;

public record DataResponseSubscription(String carPlate, LocalDate start,LocalDate end, Double pagmentMouth) {
}
