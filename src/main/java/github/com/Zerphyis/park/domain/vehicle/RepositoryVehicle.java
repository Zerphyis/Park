package github.com.Zerphyis.park.domain.vehicle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryVehicle extends JpaRepository<Vehicle,Long> {
}
