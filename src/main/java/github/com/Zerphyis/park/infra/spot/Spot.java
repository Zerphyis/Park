package github.com.Zerphyis.park.infra.spot;


import github.com.Zerphyis.park.domain.spot.DataSpot;
import github.com.Zerphyis.park.domain.spot.TypeSpot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name ="vaga" )
public class Spot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message ="número da vaga deve estar preenchido")
    @Column(name = "numero_vaga")
    private Integer numberPark;
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_vaga")
    private TypeSpot typeSpot;

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
