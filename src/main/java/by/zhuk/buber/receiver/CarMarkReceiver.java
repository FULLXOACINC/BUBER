package by.zhuk.buber.receiver;

import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.exception.RepositoryException;
import by.zhuk.buber.model.CarMark;
import by.zhuk.buber.repository.Repository;
import by.zhuk.buber.specification.Specification;
import by.zhuk.buber.specification.add.AddCarMarkSpecification;
import by.zhuk.buber.specification.find.FindSpecification;
import by.zhuk.buber.specification.find.carmark.FindCarMarkByNameSpecification;

import java.util.List;

/**
 * class that use to add new car mark
 */
class CarMarkReceiver {


    /**
     * Method use only as part of transaction
     * If carMarkName not exist add to db,else, and then return this
     *
     * @param carMarkName
     * @param carMarkRepository<CarMark>
     * @throws ReceiverException throws when there are problems with the database
     * @see Specification,Repository,FindCarMarkByNameSpecification
     */
    CarMark saveCarMark(String carMarkName, Repository<CarMark> carMarkRepository) throws ReceiverException {
        FindSpecification<CarMark> specification = new FindCarMarkByNameSpecification(carMarkName);
        CarMark carMark;
        try {
            List<CarMark> carMarks = carMarkRepository.find(specification);


            if (carMarks.isEmpty()) {
                Specification carAddSpecification = new AddCarMarkSpecification(carMarkName);

                carMarkRepository.add(carAddSpecification);

                carMarks = carMarkRepository.find(specification);
                carMark = carMarks.get(0);
            } else {
                carMark = carMarks.get(0);
            }
        } catch (RepositoryException e) {
            throw new ReceiverException(e);
        }
        return carMark;
    }
}