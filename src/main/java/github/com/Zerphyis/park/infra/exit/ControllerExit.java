package github.com.Zerphyis.park.infra.exit;

import github.com.Zerphyis.park.application.exit.DataExitRequest;
import github.com.Zerphyis.park.application.exit.DataExitResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/saida")
public class ControllerExit {
    @Autowired
    private ServiceExit service;


    @PostMapping
    public ResponseEntity<DataExitResponse> registerExit(@RequestBody DataExitRequest request) {
        return ResponseEntity.ok(service.registerExit(request));
    }

    @GetMapping
    public ResponseEntity<List<DataExitResponse>> listAll() {
        return ResponseEntity.ok(service.listAllExits());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteExit(id);
        return ResponseEntity.noContent().build();
    }
}
