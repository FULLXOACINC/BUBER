package by.zhuk.buber.receiver;

import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.exeption.RepositoryException;
import by.zhuk.buber.model.CarMark;
import by.zhuk.buber.model.Driver;
import by.zhuk.buber.repository.Repository;
import by.zhuk.buber.repository.RepositoryController;
import by.zhuk.buber.specification.Specification;
import by.zhuk.buber.specification.find.FindSpecification;
import by.zhuk.buber.specification.find.driver.FindDriverByCarNumberSpecification;
import by.zhuk.buber.specification.find.driver.FindDriverByDocumentIdSpecification;
import by.zhuk.buber.specification.find.driver.FindDriverByLoginSpecification;
import by.zhuk.buber.specification.find.driver.FindDriverToUpdateSpecification;
import by.zhuk.buber.specification.find.driver.FindSuitableDriverSpecification;
import by.zhuk.buber.specification.update.UpdateDriverSpecification;

import java.math.BigDecimal;
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

    public void updateDriver(String login, String carNumber, String documentId, String carMarkName, BigDecimal tariff) throws ReceiverException {

        Repository<Driver> driverRepository = new Repository<>();
        Repository<CarMark> carMarkRepository = new Repository<>();
        RepositoryController controller = new RepositoryController(driverRepository, carMarkRepository);
        try {
            controller.startTransaction();
            CarMarkReceiver carMarkReceiver = new CarMarkReceiver();
            CarMark carMark = carMarkReceiver.saveCarMark(carMarkName, carMarkRepository);

            Driver driver = new Driver();
            driver.setLogin(login);
            driver.setCarNumber(carNumber);
            driver.setDocumentId(documentId);
            driver.setTariff(tariff);

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
        FindSpecification<Driver> specification = new FindDriverToUpdateSpecification(login);
        Finder<Driver> driverFinder = new Finder<>();
        List<Driver> drivers = driverFinder.findBySpecification(specification);
        Optional<Driver> driver = Optional.empty();
        if (!drivers.isEmpty()) {
            driver = Optional.ofNullable(drivers.get(0));
        }
        return driver;
    }

    public List<Driver> findSuitableDrivers(float lat, float lng) throws ReceiverException {
        FindSpecification<Driver> specification = new FindSuitableDriverSpecification(lat, lng);
        Finder<Driver> finder = new Finder<>();
        return finder.findBySpecification(specification);
    }
}