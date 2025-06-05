package github.com.Zerphyis.park.infra.payment;

import github.com.Zerphyis.park.application.exceptions.ExitNotFound;
import github.com.Zerphyis.park.application.payment.DataPayment;
import github.com.Zerphyis.park.application.payment.DataPaymentRequest;
import github.com.Zerphyis.park.application.payment.DataPaymentResponse;
import github.com.Zerphyis.park.application.payment.MethodPayment;
import github.com.Zerphyis.park.domain.exit.Exit;
import github.com.Zerphyis.park.domain.exit.RepositoryExit;
import github.com.Zerphyis.park.domain.payment.Payment;
import github.com.Zerphyis.park.domain.payment.RepositoryPayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ServicePayment {
    @Autowired
    RepositoryPayment repoPayment;

    @Autowired
    RepositoryExit repoxit;

    public DataPaymentResponse create(DataPaymentRequest request){
      Exit exit =repoxit.findById(request.exitId()).orElseThrow(()-> new ExitNotFound("saida com id"+request.exitId()+"não existe"));
        MethodPayment method = MethodPayment.valueOf(request.method().toUpperCase());
        boolean confirmed = request.confimed();

        DataPayment dataPayment = new DataPayment(exit, method, confirmed);
        Payment payment = new Payment(dataPayment);

        repoPayment.save(payment);

        return new DataPaymentResponse(exit.getId(), exit.getValueCharged(), method.name(), confirmed);


    }

    public List<Payment> findAll() {
        return repoPayment.findAll();
    }


    public DataPaymentResponse update(Long id, DataPaymentRequest request){
        Payment payment=repoPayment.findById(id).orElseThrow(() -> new IllegalArgumentException("Pagamento com ID " + id + " não encontrado."));;


        Exit exit = repoxit.findById(request.exitId())
                .orElseThrow(() -> new ExitNotFound("Exit ID " + request.exitId() + " não encontrado."));

        MethodPayment method = MethodPayment.valueOf(request.method().toUpperCase());
        boolean confirmed = request.confimed();

        DataPayment dataPayment = new DataPayment(exit, method, confirmed);

        payment.setExit(dataPayment.exit());
        payment.setMethodPayment(dataPayment.methodPayment());
        payment.setConfirmed(dataPayment.confirmed());

        repoPayment.save(payment);

        return new DataPaymentResponse(exit.getId(), exit.getValueCharged(), method.name(), confirmed);
    }

    public void delete(Long id) {
        Payment payment = repoPayment.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pagamento com ID " + id + " não encontrado."));
        repoPayment.delete(payment);
    }




}
