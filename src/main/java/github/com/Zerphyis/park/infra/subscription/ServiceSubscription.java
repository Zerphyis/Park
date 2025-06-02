package github.com.Zerphyis.park.infra.subscription;

import github.com.Zerphyis.park.application.exceptions.SubscriptionNotAllowedException;
import github.com.Zerphyis.park.application.exceptions.VehicleNotFound;
import github.com.Zerphyis.park.application.subscription.DataRequestSubscription;
import github.com.Zerphyis.park.application.subscription.DataResponseSubscription;
import github.com.Zerphyis.park.application.subscription.DataSubscription;
import github.com.Zerphyis.park.domain.subscription.RepositorySubscription;
import github.com.Zerphyis.park.domain.subscription.Subscription;
import github.com.Zerphyis.park.domain.vehicle.RepositoryVehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ServiceSubscription {
    @Autowired
    private RepositorySubscription subscriptionRepository;

    @Autowired
    private RepositoryVehicle vehicleRepository;

    @Transactional
    public DataResponseSubscription create(DataRequestSubscription request) {
        var vehicle = vehicleRepository.findById(request.vehicleId())
                .orElseThrow(() -> new VehicleNotFound("Veículo com id " + request.vehicleId() + " não encontrado."));

        if (!"MENSALISTA".equalsIgnoreCase(vehicle.getTypeClient().name())) {
            throw new SubscriptionNotAllowedException("Somente clientes MENSALISTAS podem ter assinatura.");
        }
        var  datasubscription=new DataSubscription(vehicle);
        var subscription = new Subscription(datasubscription);
        subscriptionRepository.save(subscription);

        return toResponse(subscription);
    }

    @Transactional
    public DataResponseSubscription update(Long id, DataRequestSubscription request) {
        var subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assinatura com id " + id + " não encontrada."));

        var vehicle = vehicleRepository.findById(request.vehicleId())
                .orElseThrow(() -> new VehicleNotFound("Veículo com id " + request.vehicleId() + " não encontrado."));

        if (!"MENSALISTA".equalsIgnoreCase(vehicle.getTypeClient().name())) {
            throw new SubscriptionNotAllowedException("Somente clientes MENSALISTAS podem ter assinatura.");
        }

        subscription.setVehicle(vehicle);
        subscription.setDateStart(LocalDate.now());
        subscription.setDateEnd(LocalDate.now().plusMonths(1));
        subscriptionRepository.save(subscription);

        return toResponse(subscription);
    }

    @Transactional
    public void delete(Long id) {
        if (!subscriptionRepository.existsById(id)) {
            throw new RuntimeException("Assinatura com id " + id + " não encontrada.");
        }
        subscriptionRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<DataResponseSubscription> listAll() {
        return subscriptionRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<DataResponseSubscription> listExpiringSoon() {
        LocalDate today = LocalDate.now();
        LocalDate threshold = today.plusDays(5);

        return subscriptionRepository.findAll().stream()
                .filter(s -> s.getDateEnd().isAfter(today) && s.getDateEnd().isBefore(threshold))
                .map(this::toResponse)
                .toList();
    }

    private DataResponseSubscription toResponse(Subscription s) {
        return new DataResponseSubscription(
                s.getVehicle().getCarPlate(),
                s.getDateStart(),
                s.getDateEnd(),
                50.0
        );
    }
}
