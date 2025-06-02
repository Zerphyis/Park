package github.com.Zerphyis.park.domain.exit;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryExit extends JpaRepository<Exit,Long> {
    boolean existsByEntry_Id(Long entryId);;
}
