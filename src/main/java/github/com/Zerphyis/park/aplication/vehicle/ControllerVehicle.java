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
    public Vehicle register(@RequestBody DataVehicle data){
        return service.registerVehicle(data);
    }

    @PutMapping
    public Vehicle update(@RequestParam("id") Long id,@RequestBody DataVehicle data){
        return service.updateveihcle(id,data);
    }

    @GetMapping
    public List<Vehicle> list(){
        return service.listVehicle();
    }

    @DeleteMapping
    public void delete(@RequestParam("id")Long id){
        service.deleteVehicle(id);
    }
}
