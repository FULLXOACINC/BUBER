package by.zhuk.buber.receiver;

import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.exeption.RepositoryException;
import by.zhuk.buber.model.Driver;
import by.zhuk.buber.repository.Repository;
import by.zhuk.buber.repository.RepositoryController;
import by.zhuk.buber.specification.find.FindDriverByLoginSpecification;
import by.zhuk.buber.specification.find.FindSpecification;

import java.util.List;

public class DriverReceiver {

    public boolean isDriverExist(String login) throws ReceiverException {
        FindSpecification<Driver> specification = new FindDriverByLoginSpecification(login);
        Repository<Driver> repository = new Repository<>();
        RepositoryController controller = new RepositoryController(repository);
        List<Driver> drivers;
        try {
            drivers = repository.find(specification);
            controller.end();
        } catch (RepositoryException e) {
            throw new ReceiverException(e);
        }
        return !drivers.isEmpty();
    }

}