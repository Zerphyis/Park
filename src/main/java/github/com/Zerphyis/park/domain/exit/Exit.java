package github.com.Zerphyis.park.domain.exit;

import github.com.Zerphyis.park.application.exit.DataExit;
import github.com.Zerphyis.park.domain.entry.Entry;
import jakarta.persistence.*;

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

    public Exit(){

    }
    public Exit(DataExit data){
        this.entry=data.entry();
        this.exitDateTime=LocalDateTime.now();
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
