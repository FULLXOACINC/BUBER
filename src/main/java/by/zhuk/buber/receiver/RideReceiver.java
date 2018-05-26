package by.zhuk.buber.receiver;

import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.exeption.RepositoryException;
import by.zhuk.buber.model.Ride;
import by.zhuk.buber.repository.Repository;
import by.zhuk.buber.repository.RepositoryController;
import by.zhuk.buber.specification.Specification;
import by.zhuk.buber.specification.add.AddRideSpecification;

import java.math.BigDecimal;

public class RideReceiver {
    public void createRide(String driver, String passenger,float startLat, float startLng, float endLat, float endLng, BigDecimal price) throws ReceiverException {
        Repository<Ride> repository = new Repository<>();
        RepositoryController controller = new RepositoryController(repository);
        try {
            Specification rideAddSpecification = new AddRideSpecification(driver,passenger, startLat, startLng, endLat, endLng, price);
            repository.add(rideAddSpecification);
            controller.end();
        } catch (RepositoryException e) {
            throw new ReceiverException(e);
        }
    }
}