package github.com.Zerphyis.park.infra.payment;



import github.com.Zerphyis.park.application.payment.DataPaymentRequest;
import github.com.Zerphyis.park.application.payment.DataPaymentResponse;
import github.com.Zerphyis.park.domain.payment.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagamentos")
public class ControllerPayment {
    @Autowired
    ServicePayment service;

    @PostMapping
    ResponseEntity<DataPaymentResponse> create(@RequestBody DataPaymentRequest data){
        var response= service.create(data);
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping
    ResponseEntity<DataPaymentResponse> update(@PathVariable("id") Long id,@RequestBody DataPaymentRequest data){
        var response=service.update(id,data);
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping
            ResponseEntity<List<Payment>> listAll(){
        List<Payment> response=service.findAll();
        return ResponseEntity.ok(response);
    }
    @DeleteMapping
    ResponseEntity<Void> delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
