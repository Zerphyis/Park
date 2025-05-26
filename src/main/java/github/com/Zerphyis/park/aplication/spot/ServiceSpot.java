package github.com.Zerphyis.park.aplication.spot;

import github.com.Zerphyis.park.domain.SpotNotFound;
import github.com.Zerphyis.park.domain.spot.DataSpot;
import github.com.Zerphyis.park.domain.spot.TypeSpot;
import github.com.Zerphyis.park.infra.spot.RepositorySpot;
import github.com.Zerphyis.park.infra.spot.Spot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceSpot {
    @Autowired
    RepositorySpot repository;

    public Spot registerSpot(DataSpot data){
        if (repository.existsByNumberPark(data.numberPark())) {
            throw new IllegalArgumentException("Já existe uma vaga com o número " + data.numberPark());
        }

        var newSpot = new Spot(data);
        return  repository.save(newSpot);
    }
    public List<Spot> listSpot(){
        return repository.findByTypeSpot(TypeSpot.LIVRE);
    }

    public void deleteSpot(Long id) {
        if (!repository.existsById(id)) {
            throw new SpotNotFound("Vaga com id " + id + " não encontrado.");
        }
        repository.deleteById(id);
    }
}
