package github.com.Zerphyis.park.domain.payment;

import github.com.Zerphyis.park.application.payment.DataPayment;
import github.com.Zerphyis.park.application.payment.MethodPayment;
import github.com.Zerphyis.park.domain.exit.Exit;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "pagamento")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "saida_id", unique = true)
    private Exit exit;

    @Enumerated(EnumType.STRING)
    private MethodPayment methodPayment;

    private boolean confirmed;

    public Payment(){

    }
    public Payment(DataPayment data){
        this.exit=data.exit();
        this.methodPayment=data.methodPayment();
        this.confirmed= data.confirmed();

    }

    public Long getId() {
        return id;
    }

    public Exit getExit() {
        return exit;
    }

    public void setExit(Exit exit) {
        this.exit = exit;
    }

    public MethodPayment getMethodPayment() {
        return methodPayment;
    }

    public void setMethodPayment(MethodPayment methodPayment) {
        this.methodPayment = methodPayment;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
}
