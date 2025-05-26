package github.com.Zerphyis.park.infra.vehicle;

import github.com.Zerphyis.park.domain.vehicle.DataVehicle;
import github.com.Zerphyis.park.domain.vehicle.TypeClient;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "veiculos")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Deve ser informado uma placa de carro")
    @Size(max = 9, message = "A placa do carro deve ter no máximo 9 caracteres")
    private String carPlate;

    @Enumerated(EnumType.STRING)
    private TypeClient typeClient;


    public Vehicle(){

    }
    public Vehicle(DataVehicle data){
        this.carPlate=data.carPlate();
        this.typeClient=data.typeClient();
    }


    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public TypeClient getTypeClient() {
        return typeClient;
    }

    public void setTypeClient(TypeClient typeClient) {
        this.typeClient = typeClient;
    }
}
