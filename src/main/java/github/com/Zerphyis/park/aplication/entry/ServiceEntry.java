package github.com.Zerphyis.park.aplication.entry;

import github.com.Zerphyis.park.domain.EntryNotFound;
import github.com.Zerphyis.park.domain.SpotNotFound;
import github.com.Zerphyis.park.domain.VehicleNotFound;
import github.com.Zerphyis.park.domain.entry.DataEntry;
import github.com.Zerphyis.park.domain.entry.DataEntryRequest;
import github.com.Zerphyis.park.domain.entry.DataEntryResponse;
import github.com.Zerphyis.park.infra.entry.Entry;
import github.com.Zerphyis.park.infra.entry.RepositoryEntry;
import github.com.Zerphyis.park.infra.spot.RepositorySpot;
import github.com.Zerphyis.park.infra.spot.Spot;
import github.com.Zerphyis.park.infra.vehicle.RepositoryVehicle;
import github.com.Zerphyis.park.infra.vehicle.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServiceEntry {
    @Autowired
    RepositoryEntry repoEntry;
    @Autowired
    RepositorySpot repoSpot;
    @Autowired
    RepositoryVehicle repoVehi;


    private boolean isSpotInUse(Long spotId) {
        return repoEntry.existsBySpotId(spotId);
    }

    private boolean isVehicleInUse(Long vehicleId) {
        return repoEntry.existsByVehicleId(vehicleId);
    }

    @Transactional
    public DataEntryResponse createEntry(DataEntryRequest request) {
        if (isSpotInUse(request.spotId())) {
            throw new SpotNotFound("Vaga já está ocupada: " + request.spotId());
        }
        if (isVehicleInUse(request.vehicleId())) {
            throw new VehicleNotFound("Veiculo já esta em uso:" + request.vehicleId());
        }

        Spot spot = repoSpot.findById(request.spotId())
                .orElseThrow(() -> new SpotNotFound("Vaga não encontrada com id " + request.spotId()));

        Vehicle vehicle = repoVehi.findById(request.vehicleId())
                .orElseThrow(() -> new VehicleNotFound("Veiculo não encontrado com id " + request.vehicleId()));

        DataEntry dataEntry = new DataEntry(spot, vehicle, request.dateTime());
        Entry entry = new Entry(dataEntry);

        Entry savedEntry = repoEntry.save(entry);

        return new DataEntryResponse(
                savedEntry.getVehicle().getCarPlate(),
                savedEntry.getSpot().getNumberPark(),
                savedEntry.getEntryDateTime()
        );
    }

    @Transactional(readOnly = true)
    public List<DataEntryResponse> listAllEntries() {
        return repoEntry.findAll()
                .stream()
                .map(entry -> new DataEntryResponse(
                        entry.getVehicle().getCarPlate(),
                        entry.getSpot().getNumberPark(),
                        entry.getEntryDateTime()
                ))
                .toList();
    }

    @Transactional
    public void deleteEntry(Long entryId) {
        if (!repoEntry.existsById(entryId)) {
            throw new EntryNotFound("Entrada com o Id não encontrada " + entryId);
        }
        repoEntry.deleteById(entryId);
    }
}

