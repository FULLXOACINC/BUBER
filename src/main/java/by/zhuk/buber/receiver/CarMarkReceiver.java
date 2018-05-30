package by.zhuk.buber.receiver;

import by.zhuk.buber.exception.RepositoryException;
import by.zhuk.buber.model.CarMark;
import by.zhuk.buber.repository.Repository;
import by.zhuk.buber.specification.Specification;
import by.zhuk.buber.specification.add.AddCarMarkSpecification;
import by.zhuk.buber.specification.find.FindSpecification;
import by.zhuk.buber.specification.find.carmark.FindCarMarkByNameSpecification;

import java.util.List;

public class CarMarkReceiver {
    CarMark saveCarMark(String carMarkName, Repository<CarMark> carMarkRepository) throws RepositoryException {
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
        return carMark;
    }
}