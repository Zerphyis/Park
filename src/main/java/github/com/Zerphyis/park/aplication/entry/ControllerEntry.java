package github.com.Zerphyis.park.aplication.entry;

import github.com.Zerphyis.park.domain.entry.DataEntryRequest;
import github.com.Zerphyis.park.domain.entry.DataEntryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entrada")
public class ControllerEntry {
    @Autowired
    ServiceEntry service;

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
