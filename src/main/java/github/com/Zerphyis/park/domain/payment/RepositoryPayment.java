package github.com.Zerphyis.park.domain.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryPayment extends JpaRepository<Payment,Long> {
}
