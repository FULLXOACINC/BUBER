package by.zhuk.buber.receiver;

import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.exeption.RepositoryException;
import by.zhuk.buber.model.CarMark;
import by.zhuk.buber.model.Driver;
import by.zhuk.buber.repository.Repository;
import by.zhuk.buber.repository.RepositoryController;
import by.zhuk.buber.specification.Specification;
import by.zhuk.buber.specification.add.AddCarMarkSpecification;
import by.zhuk.buber.specification.find.FindSpecification;
import by.zhuk.buber.specification.find.carmark.FindCarMarkByNameSpecification;
import by.zhuk.buber.specification.find.driver.FindDriverByCarNumberSpecification;
import by.zhuk.buber.specification.find.driver.FindDriverByDocumentIdSpecification;
import by.zhuk.buber.specification.find.driver.FindDriverByLoginSpecification;
import by.zhuk.buber.specification.find.driver.FindDriverCarNumDocIdMarkNameByLoginSpecification;
import by.zhuk.buber.specification.update.UpdateDriverSpecification;

import java.util.List;
import java.util.Optional;

public class DriverReceiver {

    public boolean isDriverExist(String login) throws ReceiverException {
        FindSpecification<Driver> specification = new FindDriverByLoginSpecification(login);
        Finder<Driver> finder = new Finder<>();
        List<Driver> drivers = finder.findBySpecification(specification);
        return !drivers.isEmpty();
    }

    public boolean isCarNumberExist(String carNumber) throws ReceiverException {
        FindSpecification<Driver> specification = new FindDriverByCarNumberSpecification(carNumber);
        Finder<Driver> finder = new Finder<>();
        List<Driver> drivers = finder.findBySpecification(specification);
        return !drivers.isEmpty();
    }

    public boolean isDriverDocumentIdExist(String documentId) throws ReceiverException {
        FindSpecification<Driver> specification = new FindDriverByDocumentIdSpecification(documentId);
        Finder<Driver> finder = new Finder<>();
        List<Driver> drivers = finder.findBySpecification(specification);
        return !drivers.isEmpty();
    }

    public void updateDriver(String login, String carNumber, String documentId, String carMarkName) throws ReceiverException {

        Repository<Driver> driverRepository = new Repository<>();
        Repository<CarMark> carMarkRepository = new Repository<>();
        RepositoryController controller = new RepositoryController(driverRepository, carMarkRepository);
        try {
            controller.startTransaction();
            FindSpecification<CarMark> specification = new FindCarMarkByNameSpecification(carMarkName);
            List<CarMark> carMarks = carMarkRepository.find(specification);
            CarMark carMark;
            if (carMarks.isEmpty()) {
                Specification carAddSpecification = new AddCarMarkSpecification(carMarkName);
                carMarkRepository.add(carAddSpecification);
                carMarks = carMarkRepository.find(specification);
                carMark = carMarks.get(0);
            } else {
                carMark = carMarks.get(0);
            }

            Driver driver = new Driver();
            driver.setLogin(login);
            driver.setCarNumber(carNumber);
            driver.setDocumentId(documentId);

            driver.setCarMark(carMark);
            Specification driverAddSpecification = new UpdateDriverSpecification(driver);
            driverRepository.update(driverAddSpecification);

            controller.commit();
            controller.endTransaction();


        } catch (RepositoryException e) {
            controller.rollBack();
            throw new ReceiverException(e);
        }
    }

    public Optional<Driver> findDriverByLogin(String login) throws ReceiverException {
        FindSpecification<Driver> specification = new FindDriverCarNumDocIdMarkNameByLoginSpecification(login);
        Finder<Driver> driverFinder = new Finder<>();
        List<Driver> drivers = driverFinder.findBySpecification(specification);
        Optional<Driver> driver = Optional.empty();
        if (!drivers.isEmpty()) {
            driver = Optional.ofNullable(drivers.get(0));
        }
        return driver;
    }
}