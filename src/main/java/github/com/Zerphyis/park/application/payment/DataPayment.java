package github.com.Zerphyis.park.application.payment;

import github.com.Zerphyis.park.domain.exit.Exit;

public record DataPayment(Exit exit, MethodPayment methodPayment,boolean confirmed) {
}
