package github.com.Zerphyis.park.domain.spot;

import github.com.Zerphyis.park.application.spot.TypeSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorySpot extends JpaRepository<Spot,Long> {
    List<Spot> findByTypeSpot(TypeSpot status);
    boolean existsByNumberPark(Integer numero);


}
