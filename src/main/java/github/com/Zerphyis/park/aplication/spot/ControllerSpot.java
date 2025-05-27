package github.com.Zerphyis.park.aplication.spot;

import github.com.Zerphyis.park.domain.spot.DataSpot;
import github.com.Zerphyis.park.domain.spot.ResponseDataSpot;
import github.com.Zerphyis.park.infra.spot.Spot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/vaga")
public class ControllerSpot {

        @Autowired
        private ServiceSpot service;

        @PostMapping
        public ResponseEntity<ResponseDataSpot> register(@RequestBody DataSpot data, UriComponentsBuilder uriBuilder) {
            Spot created = service.registerSpot(data);
            URI uri = uriBuilder.path("/vaga/{id}").buildAndExpand(created.getId()).toUri();
            return ResponseEntity.created(uri).body(toResponse(created));
        }

        @GetMapping
        public ResponseEntity<List<ResponseDataSpot>> listFreeSpots() {
            List<ResponseDataSpot> freeSpots = service.listSpot()
                    .stream()
                    .map(this::toResponse)
                    .toList();
            return ResponseEntity.ok(freeSpots);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
            service.deleteSpot(id);
            return ResponseEntity.noContent().build();
        }

    private ResponseDataSpot toResponse(Spot s) {
        return new ResponseDataSpot(s.getNumberPark(),s.getTypeSpot());
    }

}

