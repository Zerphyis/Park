package github.com.Zerphyis.park.infra.vehicle;

import github.com.Zerphyis.park.application.VehicleNotFound;
import github.com.Zerphyis.park.application.vehicle.DataVehicle;
import github.com.Zerphyis.park.domain.vehicle.RepositoryVehicle;
import github.com.Zerphyis.park.domain.vehicle.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServiceVehicle {
    @Autowired
    private RepositoryVehicle repository;

    @Transactional
    public Vehicle registerVehicle(DataVehicle data){
        var newVehicle= new Vehicle(data);
       return repository.save(newVehicle);
    }

    @Transactional(readOnly = true)
    public List<Vehicle> listVehicle(){
        return repository.findAll();
    }

    @Transactional
    public Vehicle updateveihcle(Long id,DataVehicle data){
        var vehicle = repository.findById(id)
                .orElseThrow(() -> new VehicleNotFound("Veículo com id " + id + " não encontrado."));

        vehicle.setTypeClient(data.typeClient());

        return repository.save(vehicle);
    }

    @Transactional
    public void deleteVehicle(Long id) {
        if (!repository.existsById(id)) {
            throw new VehicleNotFound("Veículo com id " + id + " não encontrado.");
        }
        repository.deleteById(id);
    }
}
