package github.com.Zerphyis.park.domain.spot;


import github.com.Zerphyis.park.application.spot.DataSpot;
import github.com.Zerphyis.park.application.spot.TypeSpot;
import github.com.Zerphyis.park.domain.entry.Entry;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="vaga" )
public class Spot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message ="número da vaga deve estar preenchido")
    @Column(name = "numero_vaga")
    private Integer numberPark;
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_vaga")
    private TypeSpot typeSpot;

    @OneToMany(mappedBy = "spot", cascade = CascadeType.ALL)
    private List<Entry> entries = new ArrayList<>();

    public Spot(){


    }

    public  Spot(DataSpot data){
        this.numberPark= data.numberPark();
        this.typeSpot=data.typeSpot();

    }

    public Long getId() {
        return id;
    }

    public Integer getNumberPark() {
        return numberPark;
    }

    public void setNumberPark(Integer numberPark) {
        this.numberPark = numberPark;
    }

    public TypeSpot getTypeSpot() {
        return typeSpot;
    }

    public void setTypeSpot(TypeSpot typeSpot) {
        this.typeSpot = typeSpot;
    }
}
