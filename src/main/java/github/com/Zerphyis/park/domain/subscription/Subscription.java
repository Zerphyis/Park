package github.com.Zerphyis.park.domain.subscription;

import github.com.Zerphyis.park.application.subscription.DataSubscription;
import github.com.Zerphyis.park.domain.vehicle.Vehicle;
import jakarta.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "descrição")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @OneToOne
    @JoinColumn(name = "veiculo_id")
    private Vehicle vehicle;
    @Column(name = "data_comeco")
    private LocalDate dateStart;
    @Column(name="data_fim")
    private LocalDate dateEnd;

    public Subscription(){

    }

    public Subscription(DataSubscription data){
        this.vehicle=data.vehicle();
        this.dateStart=LocalDate.now();
        this.dateEnd=dateStart.plusMonths(1);

    }
    public Long getId() {
        return id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }
}
