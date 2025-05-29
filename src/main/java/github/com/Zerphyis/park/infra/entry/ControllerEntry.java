package github.com.Zerphyis.park.infra.entry;

import github.com.Zerphyis.park.application.entry.DataEntryRequest;
import github.com.Zerphyis.park.application.entry.DataEntryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entrada")
public class ControllerEntry {
    @Autowired
   private ServiceEntry service;

    @PostMapping
    public ResponseEntity<DataEntryResponse> createEntry(@RequestBody DataEntryRequest request) {
        DataEntryResponse response = service.createEntry(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DataEntryResponse>> listAllEntries() {
        List<DataEntryResponse> entries = service.listAllEntries();
        return ResponseEntity.ok(entries);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntry(@PathVariable Long id) {
        service.deleteEntry(id);
        return ResponseEntity.noContent().build();
    }

}
