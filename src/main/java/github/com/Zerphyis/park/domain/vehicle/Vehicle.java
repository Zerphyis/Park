package github.com.Zerphyis.park.domain.vehicle;

import github.com.Zerphyis.park.application.vehicle.DataVehicle;
import github.com.Zerphyis.park.application.vehicle.TypeClient;
import github.com.Zerphyis.park.domain.entry.Entry;
import github.com.Zerphyis.park.domain.subscription.Subscription;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "veiculos")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Deve ser informado uma placa de carro")
    @Size(max = 9, message = "A placa do carro deve ter no máximo 9 caracteres")
    @Column(name = "placa_carro")
    private String carPlate;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cliente")
    private TypeClient typeClient;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private List<Entry> entries = new ArrayList<>();

    @OneToOne
    private Subscription subscription;


    public Vehicle(){

    }
    public Vehicle(DataVehicle data){
        this.carPlate=data.carPlate();
        this.typeClient=data.typeClient();
    }

    public Long getId() {
        return id;
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
