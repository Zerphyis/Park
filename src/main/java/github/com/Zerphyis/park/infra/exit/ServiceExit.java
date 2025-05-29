package github.com.Zerphyis.park.infra.exit;

import github.com.Zerphyis.park.application.EntryNotFound;
import github.com.Zerphyis.park.application.ExitAlreadyExist;
import github.com.Zerphyis.park.application.exit.DataExit;
import github.com.Zerphyis.park.application.exit.DataExitRequest;
import github.com.Zerphyis.park.application.exit.DataExitResponse;
import github.com.Zerphyis.park.domain.entry.Entry;
import github.com.Zerphyis.park.domain.entry.RepositoryEntry;
import github.com.Zerphyis.park.domain.exit.Exit;
import github.com.Zerphyis.park.domain.exit.RepositoryExit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceExit {

    @Autowired
    private RepositoryExit repoExit;

    @Autowired
    private RepositoryEntry repoEntry;

    @Transactional
    public DataExitResponse registerExit(DataExitRequest request) {
        Entry entry = repoEntry.findById(request.entryId())
                .orElseThrow(() -> new EntryNotFound("Entrada não encontrada com id: " + request.entryId()));

        if (repoExit.existsByEntry_Id(entry.getId())) {
            throw new ExitAlreadyExist("Já existe uma saída para esta entrada.");
        }

        Exit exit = new Exit(new DataExit(entry));
        repoExit.save(exit);

        return new DataExitResponse(
                entry.getSpot().getNumberPark(),
                entry.getVehicle().getCarPlate(),
                exit.getExitDateTime(),
                exit.getValueCharged()
        );
    }

    @Transactional(readOnly = true)
    public List<DataExitResponse> listAllExits() {
        return repoExit.findAll().stream()
                .map(exit -> {
                    Entry entry = exit.getEntry();
                    return new DataExitResponse(
                            entry.getSpot().getNumberPark(),
                            entry.getVehicle().getCarPlate(),
                            exit.getExitDateTime(),
                            exit.getValueCharged()
                    );
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteExit(Long id) {
        if (!repoExit.existsById(id)) {
            throw new RuntimeException("Saída com ID " + id + " não encontrada.");
        }
        repoExit.deleteById(id);
    }
}
