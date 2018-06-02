package by.zhuk.buber.receiver;

import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.exception.RepositoryException;
import by.zhuk.buber.model.CarMark;
import by.zhuk.buber.model.Driver;
import by.zhuk.buber.repository.Repository;
import by.zhuk.buber.repository.RepositoryController;
import by.zhuk.buber.specification.Specification;
import by.zhuk.buber.specification.find.FindSpecification;
import by.zhuk.buber.specification.find.driver.FindDriverByCarNumberSpecification;
import by.zhuk.buber.specification.find.driver.FindDriverByDocumentIdSpecification;
import by.zhuk.buber.specification.find.driver.FindDriverByLoginSpecification;
import by.zhuk.buber.specification.find.driver.FindDriverEarnedMoneySpecification;
import by.zhuk.buber.specification.find.driver.FindDriverInfoForRideSpecification;
import by.zhuk.buber.specification.find.driver.FindDriverTariffSpecification;
import by.zhuk.buber.specification.find.driver.FindDriverToUpdateSpecification;
import by.zhuk.buber.specification.find.driver.FindSuitableDriverByLoginSpecification;
import by.zhuk.buber.specification.find.driver.FindSuitableDriverSpecification;
import by.zhuk.buber.specification.update.driver.UpdateDriverEarnedMoneySpecification;
import by.zhuk.buber.specification.update.driver.UpdateDriverIncrementNegativeMarkSpecification;
import by.zhuk.buber.specification.update.driver.UpdateDriverIncrementPositiveMarkSpecification;
import by.zhuk.buber.specification.update.driver.UpdateDriverInfoSpecification;
import by.zhuk.buber.specification.update.driver.UpdateDriverIsWorkingSpecification;
import by.zhuk.buber.specification.update.driver.UpdateDriverProfileCoordinateSpecification;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Class include method to interaction with things connection with Driver business logic
 */
public class DriverReceiver {
    private static final BigDecimal EMPTY_BALANCE = new BigDecimal("0.00");

    /**
     * Method check is driver login exist in system
     *
     * @return true if exist,else is not exist
     * @throws ReceiverException throws when there are problems with the database
     * @see Specification,Finder,FindDriverByLoginSpecification
     */
    public boolean isDriverExist(String driverLogin) throws ReceiverException {
        FindSpecification<Driver> specification = new FindDriverByLoginSpecification(driverLogin);
        Finder<Driver> finder = new Finder<>();
        List<Driver> drivers = finder.findBySpecification(specification);
        return !drivers.isEmpty();
    }

    /**
     * Method check is car number exist in system
     *
     * @return true if exist,else is not exist
     * @throws ReceiverException throws when there are problems with the database
     * @see Specification,Finder,FindDriverByCarNumberSpecification
     */
    public boolean isCarNumberExist(String carNumber) throws ReceiverException {
        FindSpecification<Driver> specification = new FindDriverByCarNumberSpecification(carNumber);
        Finder<Driver> finder = new Finder<>();
        List<Driver> drivers = finder.findBySpecification(specification);
        return !drivers.isEmpty();
    }

    /**
     * Method check is document id exist in system
     *
     * @return true if exist,else is not exist
     * @throws ReceiverException throws when there are problems with the database
     * @see Specification,Finder,FindDriverByCarNumberSpecification
     */
    public boolean isDriverDocumentIdExist(String documentId) throws ReceiverException {
        FindSpecification<Driver> specification = new FindDriverByDocumentIdSpecification(documentId);
        Finder<Driver> finder = new Finder<>();
        List<Driver> drivers = finder.findBySpecification(specification);
        return !drivers.isEmpty();
    }


    /**
     * Method update driver car number, document id,car mark and tariff at the same time
     * Include transaction:
     * 1)update car number, document id and tariff
     * 2)add or find car mark
     *
     * @throws ReceiverException throws when there are problems with the database
     * @see Specification,RepositoryController,UpdateDriverInfoSpecification,CarMarkReceiver
     */
    public void updateDriver(String driverLogin, String carNumber, String documentId, String carMarkName, BigDecimal tariff) throws ReceiverException {

        Repository<Driver> driverRepository = new Repository<>();
        Repository<CarMark> carMarkRepository = new Repository<>();
        RepositoryController controller = new RepositoryController(driverRepository, carMarkRepository);
        try {
            controller.startTransaction();
            CarMarkReceiver carMarkReceiver = new CarMarkReceiver();
            CarMark carMark = carMarkReceiver.saveCarMark(carMarkName, carMarkRepository);

            Specification driverUpdateSpecification = new UpdateDriverInfoSpecification(driverLogin, carNumber, documentId, carMark.getIndex(), tariff);
            driverRepository.update(driverUpdateSpecification);

            controller.commit();
            controller.endTransaction();
        } catch (RepositoryException | ReceiverException e) {
            controller.rollBack();
            throw new ReceiverException(e);
        }
    }

    /**
     * Method find driver by login, if driver not exist return Optional.empty()
     *
     * @return Optional<Driver>
     * @throws ReceiverException throws when there are problems with the database
     * @see Specification,Finder,FindDriverToUpdateSpecification,Optional
     */
    public Optional<Driver> findDriverByLogin(String driverLogin) throws ReceiverException {
        FindSpecification<Driver> specification = new FindDriverToUpdateSpecification(driverLogin);
        Finder<Driver> driverFinder = new Finder<>();
        List<Driver> drivers = driverFinder.findBySpecification(specification);
        Optional<Driver> driver = Optional.empty();
        if (!drivers.isEmpty()) {
            driver = Optional.ofNullable(drivers.get(0));
        }
        return driver;
    }

    /**
     * Method find suitable drivers(working and are not far from the target and driver not equal passenger)
     *
     * @param lat current target latitude
     * @param lng current target longitude
     * @return list of driver
     * @throws ReceiverException throws when there are problems with the database
     * @see Specification,Finder,FindSuitableDriverSpecification
     */
    public List<Driver> findSuitableDrivers(float lat, float lng, String login) throws ReceiverException {
        FindSpecification<Driver> specification = new FindSuitableDriverSpecification(lat, lng, login);
        Finder<Driver> finder = new Finder<>();
        return finder.findBySpecification(specification);
    }

    /**
     * Method check is driver suitable(working and are not far from the target and driver not equal passenger)
     *
     * @param lat current target latitude
     * @param lng current target longitude
     * @return true if driver suitable, else return false
     * @throws ReceiverException throws when there are problems with the database
     * @see Specification,Finder,FindSuitableDriverByLoginSpecification
     */
    public boolean isDriverSuitable(float lat, float lng, String driverLogin) throws ReceiverException {
        FindSpecification<Driver> specification = new FindSuitableDriverByLoginSpecification(lat, lng, driverLogin);
        Finder<Driver> finder = new Finder<>();
        List<Driver> drivers = finder.findBySpecification(specification);
        return !drivers.isEmpty();
    }

    /**
     * Method find driver traffic
     *
     * @return Optional<BigDecimal>
     * @throws ReceiverException throws when there are problems with the database
     * @see Specification,Finder,FindDriverTariffSpecification
     */
    public Optional<BigDecimal> findDriverTariff(String driverLogin) throws ReceiverException {
        FindSpecification<Driver> specification = new FindDriverTariffSpecification(driverLogin);
        Finder<Driver> finder = new Finder<>();
        List<Driver> drivers = finder.findBySpecification(specification);
        Optional<BigDecimal> tariff = Optional.empty();
        if (!drivers.isEmpty()) {
            tariff = Optional.ofNullable(drivers.get(0).getTariff());
        }
        return tariff;
    }

    /**
     * Method update current driver coordinate(latitude,longitude)
     *
     * @param lat current target latitude
     * @param lng current target longitude
     * @throws ReceiverException throws when there are problems with the database
     * @see Specification,Repository,RepositoryController,UpdateDriverProfileCoordinateSpecification
     */
    public void updateCurrentCoordinate(float lat, float lng, String driverLogin) throws ReceiverException {
        Repository<Driver> driverRepository = new Repository<>();
        RepositoryController controller = new RepositoryController(driverRepository);

        Specification driverUpdateSpecification = new UpdateDriverProfileCoordinateSpecification(lat, lng, driverLogin);
        try {
            driverRepository.update(driverUpdateSpecification);
            controller.end();
        } catch (RepositoryException e) {
            throw new ReceiverException(e);
        }
    }

    /**
     * Method find driver info for ride:
     * @return Optional<Driver> if exist,else  Optional.empty()
     * @throws ReceiverException throws when there are problems with the database
     * @see Specification,Finder,FindDriverInfoForRideSpecification
     */
    public Optional<Driver> findDriverInfoForRide(String driverLogin) throws ReceiverException {
        FindSpecification<Driver> specification = new FindDriverInfoForRideSpecification(driverLogin);
        Finder<Driver> finder = new Finder<>();
        List<Driver> drivers = finder.findBySpecification(specification);
        Optional<Driver> driver = Optional.empty();
        if (!drivers.isEmpty()) {
            driver = Optional.ofNullable(drivers.get(0));
        }
        return driver;
    }

    /**
     * Method increment positive mark
     *
     * @throws ReceiverException throws when there are problems with the database
     * @see Specification,RepositoryController,UpdateDriverIncrementPositiveMarkSpecification,Repository
     */
    public void incrementPositiveMark(String driver) throws ReceiverException {
        Repository<Driver> repository = new Repository<>();
        RepositoryController controller = new RepositoryController(repository);
        try {
            Specification updateSpecification = new UpdateDriverIncrementPositiveMarkSpecification(driver);
            repository.update(updateSpecification);
            controller.end();
        } catch (RepositoryException e) {
            throw new ReceiverException(e);
        }
    }
    /**
     * Method increment negative mark
     *
     * @throws ReceiverException throws when there are problems with the database
     * @see Specification,RepositoryController,UpdateDriverIncrementNegativeMarkSpecification,Repository
     */
    public void incrementNegativeMark(String driver) throws ReceiverException {
        Repository<Driver> repository = new Repository<>();
        RepositoryController controller = new RepositoryController(repository);
        try {
            Specification updateSpecification = new UpdateDriverIncrementNegativeMarkSpecification(driver);
            repository.update(updateSpecification);
            controller.end();
        } catch (RepositoryException e) {
            throw new ReceiverException(e);
        }
    }
    /**
     * Method set driver(login) working status isWorking
     *
     * @throws ReceiverException throws when there are problems with the database
     * @see Specification,RepositoryController,UpdateDriverIsWorkingSpecification,Repository
     */
    public void updateDriverWorkingStatus(String login, boolean isWorking) throws ReceiverException {
        Repository<Driver> driverRepository = new Repository<>();
        RepositoryController controller = new RepositoryController(driverRepository);

        Specification driverUpdateSpecification = new UpdateDriverIsWorkingSpecification(isWorking, login);
        try {
            driverRepository.update(driverUpdateSpecification);
            controller.end();
        } catch (RepositoryException e) {
            throw new ReceiverException(e);
        }
    }
    /**
     * Method find driver earned money
     *
     * @return Optional<BigDecimal> id exist, else return Optional.empty()
     * @throws ReceiverException throws when there are problems with the database
     * @see Specification,Finder,FindDriverEarnedMoneySpecification
     */
    public Optional<BigDecimal> findDriverEarnedMoney(String login) throws ReceiverException {
        FindSpecification<Driver> specification = new FindDriverEarnedMoneySpecification(login);
        Finder<Driver> finder = new Finder<>();
        List<Driver> drivers = finder.findBySpecification(specification);
        Optional<BigDecimal> earnedMoney = Optional.empty();
        if (!drivers.isEmpty()) {
            earnedMoney = Optional.ofNullable(drivers.get(0).getEarnedMoney());
        }
        return earnedMoney;
    }
    /**
     * Method withdraw earning money
     *
     * @throws ReceiverException throws when there are problems with the database
     * @see Specification,Repository,RepositoryController,UpdateDriverEarnedMoneySpecification
     */
    public void withdrawEarningMoney(String login) throws ReceiverException {
        Repository<Driver> driverRepository = new Repository<>();
        RepositoryController controller = new RepositoryController(driverRepository);

        Specification driverUpdateSpecification = new UpdateDriverEarnedMoneySpecification(login, EMPTY_BALANCE);
        try {
            driverRepository.update(driverUpdateSpecification);
            controller.end();
        } catch (RepositoryException e) {
            throw new ReceiverException(e);
        }
    }
}