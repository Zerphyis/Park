package github.com.Zerphyis.park.domain.entry;

import github.com.Zerphyis.park.application.entry.DataEntry;
import github.com.Zerphyis.park.domain.spot.Spot;
import github.com.Zerphyis.park.domain.vehicle.Vehicle;
import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "entrada")
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "vaga_id", referencedColumnName = "id", nullable = false, unique = true)
    private Spot spot;

    @OneToOne
    @JoinColumn(name = "veiculos_id", referencedColumnName = "id", nullable = false, unique = true)
    private Vehicle vehicle;

    @Column(name = "entrada_data_hora", nullable = false)
    private LocalDateTime entryDateTime;

    public Entry() {}

    public Entry(DataEntry entry) {
        this.spot = entry.spot();
        this.vehicle = entry.vehicle();
        this.entryDateTime = entry.entryDateTime();
    }

    public Long getId() {
        return id;
    }

    public Spot getSpot() {
        return spot;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public LocalDateTime getEntryDateTime() {
        return entryDateTime;
    }

    public void setEntryDateTime(LocalDateTime entryDateTime) {
        this.entryDateTime = entryDateTime;
    }
}
