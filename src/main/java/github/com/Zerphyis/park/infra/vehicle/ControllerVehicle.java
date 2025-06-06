package github.com.Zerphyis.park.infra.vehicle;

import github.com.Zerphyis.park.application.vehicle.DataVehicle;
import github.com.Zerphyis.park.application.vehicle.ResponseDataVehicle;
import github.com.Zerphyis.park.domain.vehicle.Vehicle;
import jakarta.validation.Valid;
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
   private ServiceVehicle service;

    @PostMapping
    public ResponseEntity<ResponseDataVehicle> register(@RequestBody @Valid DataVehicle data, UriComponentsBuilder uriBuilder) {
        Vehicle created = service.registerVehicle(data);
        URI uri = uriBuilder.path("/veiculos/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uri).body(toResponse(created));
    }

    @PutMapping
    public ResponseEntity<ResponseDataVehicle> update(@RequestParam("id") Long id, @RequestBody DataVehicle data){
        Vehicle updated = service.updateVehicle(id, data);
        return ResponseEntity.ok(toResponse(updated));
    }

    @GetMapping
    public ResponseEntity<List<ResponseDataVehicle>> list(){
        List<ResponseDataVehicle> list = service.listVehicle()
                .stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(list);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam("id") Long id){
        service.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }

    private ResponseDataVehicle toResponse(Vehicle v) {
        return new ResponseDataVehicle(v.getCarPlate(), v.getTypeClient());
    }
}