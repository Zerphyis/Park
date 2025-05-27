package github.com.Zerphyis.park.infra.entry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryEntry extends JpaRepository<Entry,Long> {
    boolean existsBySpotId(Long spotId);
    boolean existsByVehicleId(Long vehicleId);



}
