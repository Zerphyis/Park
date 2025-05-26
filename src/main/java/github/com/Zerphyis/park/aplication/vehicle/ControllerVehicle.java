package github.com.Zerphyis.park.aplication.vehicle;

import github.com.Zerphyis.park.domain.vehicle.DataVehicle;
import github.com.Zerphyis.park.infra.vehicle.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicle")
public class ControllerVehicle {
    @Autowired
    ServiceVehicle service;

    @PostMapping
    public Vehicle register(DataVehicle data){
        return service.registerVehicle(data);
    }

    @PutMapping
    public Vehicle update(Long id,DataVehicle data){
        return service.updateveihcle(id,data);
    }

    @GetMapping
    public List<Vehicle> list(){
        return service.listVehicle();
    }

    @DeleteMapping
    public void delete(Long id){
        service.deleteVehicle(id);
    }
}
