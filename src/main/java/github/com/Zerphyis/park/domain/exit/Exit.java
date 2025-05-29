package github.com.Zerphyis.park.domain.exit;

import github.com.Zerphyis.park.application.exit.DataExit;
import github.com.Zerphyis.park.domain.entry.Entry;
import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "saida")
public class Exit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @OneToOne
    @JoinColumn(name = "entrada_id", referencedColumnName = "id", nullable = false, unique = true)
    private Entry entry;

    @Column(name = "saida_data_hora", nullable = false)
    private LocalDateTime exitDateTime;

    @Column(name = "valor_cobrado",nullable = false)
    private Double valueCharged;

    public Exit(){

    }
    public Exit(DataExit data){
        this.entry=data.entry();
        this.exitDateTime=LocalDateTime.now();
        this.valueCharged = calculateValue(entry.getEntryDateTime(), this.exitDateTime);
    }


    private double calculateValue(LocalDateTime entry, LocalDateTime exit) {
        long minutos = Duration.between(entry, exit).toMinutes();
        return minutos * 0.25; //R$ 0,25 por minuto
    }

    public Double getValueCharged() {
        return valueCharged;
    }

    public void setValueCharged(Double valueCharged) {
        this.valueCharged = valueCharged;
    }

    public Long getId() {
        return id;
    }

    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    public LocalDateTime getExitDateTime() {
        return exitDateTime;
    }

    public void setExitDateTime(LocalDateTime exitDateTime) {
        this.exitDateTime = exitDateTime;
    }
}
