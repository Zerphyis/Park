package github.com.Zerphyis.park.domain.subscription;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface RepositorySubscription extends JpaRepository<Subscription,Long> {
}
