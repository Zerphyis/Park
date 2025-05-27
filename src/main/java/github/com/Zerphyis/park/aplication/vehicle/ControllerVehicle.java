package github.com.Zerphyis.park.aplication.vehicle;

import github.com.Zerphyis.park.domain.vehicle.DataVehicle;
import github.com.Zerphyis.park.infra.vehicle.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/veiculos")
public class ControllerVehicle {
    @Autowired
    ServiceVehicle service;

    public ResponseEntity<Vehicle> register(@RequestBody DataVehicle data, UriComponentsBuilder uriBuilder) {
        Vehicle created = service.registerVehicle(data);
        URI uri = uriBuilder.path("/veiculos/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uri).body(created);
    }


    @PutMapping
    public ResponseEntity<Vehicle> update(@RequestParam("id") Long id, @RequestBody DataVehicle data){
        var update=service.updateveihcle(id,data);
        return  ResponseEntity.ok(update);
    }

    @GetMapping
    public ResponseEntity<List<Vehicle>> list(){
        var list=service.listVehicle();
        return ResponseEntity.ok(list);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam("id")Long id){
        service.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }
}
