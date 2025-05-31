package github.com.Zerphyis.park.application.vehicle;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DataVehicle(@NotBlank(message = "Deve ser informado uma placa de carro")String carPlate,  @NotNull(message = "O tipo de cliente deve ser informado") TypeClient typeClient) {
}
