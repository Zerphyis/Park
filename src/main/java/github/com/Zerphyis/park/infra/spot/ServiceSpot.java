package github.com.Zerphyis.park.infra.spot;

import github.com.Zerphyis.park.application.SpotNotFound;
import github.com.Zerphyis.park.application.spot.DataSpot;
import github.com.Zerphyis.park.application.spot.TypeSpot;
import github.com.Zerphyis.park.domain.spot.RepositorySpot;
import github.com.Zerphyis.park.domain.spot.Spot;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceSpot {
    @Autowired
   private RepositorySpot repository;

    @Transactional
    public Spot registerSpot(DataSpot data){
        if (repository.existsByNumberPark(data.numberPark())) {
            throw new IllegalArgumentException("Já existe uma vaga com o número " + data.numberPark());
        }

        var newSpot = new Spot(data);
        return  repository.save(newSpot);
    }

    @Transactional(readOnly = true)
    public List<Spot> listSpot(){
        return repository.findByTypeSpot(TypeSpot.LIVRE);
    }

    @Transactional
    public void deleteSpot(Long id) {
        if (!repository.existsById(id)) {
            throw new SpotNotFound("Vaga com id " + id + " não encontrado.");
        }
        repository.deleteById(id);
    }
}
