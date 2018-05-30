package by.zhuk.buber.receiver;

import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.exception.RepositoryException;
import by.zhuk.buber.repository.Repository;
import by.zhuk.buber.repository.RepositoryController;
import by.zhuk.buber.specification.Specification;

class Adder<T> {
    void addBySpecification(Specification specification) throws ReceiverException {
        Repository<T> repository = new Repository<>();
        RepositoryController controller = new RepositoryController(repository);
        try {
            repository.add(specification);
            controller.end();
        } catch (RepositoryException e) {
            throw new ReceiverException(e);
        }
    }
}