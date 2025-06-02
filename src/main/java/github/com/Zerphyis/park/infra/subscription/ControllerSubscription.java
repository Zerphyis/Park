package github.com.Zerphyis.park.infra.subscription;

import github.com.Zerphyis.park.application.subscription.DataRequestSubscription;
import github.com.Zerphyis.park.application.subscription.DataResponseSubscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("assinaturas")
public class ControllerSubscription {

    @Autowired
    private ServiceSubscription service;

    @PostMapping
    public DataResponseSubscription create(@RequestBody DataRequestSubscription request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public DataResponseSubscription update(@PathVariable("id") Long id, @RequestBody DataRequestSubscription request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }

    @GetMapping
    public List<DataResponseSubscription> listAll() {
        return service.listAll();
    }

    @GetMapping("/expirando")
    public List<DataResponseSubscription> expiringSoon() {
        return service.listExpiringSoon();
    }
}
