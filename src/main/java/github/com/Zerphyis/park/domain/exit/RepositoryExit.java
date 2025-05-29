package github.com.Zerphyis.park.domain.exit;

import github.com.Zerphyis.park.domain.entry.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryExit extends JpaRepository<Exit,Long> {
    boolean existsByEntry(Long entryid);
}
