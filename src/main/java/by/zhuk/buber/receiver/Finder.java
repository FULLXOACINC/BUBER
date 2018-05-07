package by.zhuk.buber.receiver;

import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.exeption.RepositoryException;
import by.zhuk.buber.repository.Repository;
import by.zhuk.buber.repository.RepositoryController;
import by.zhuk.buber.specification.find.FindSpecification;

import java.util.List;

class Finder<T> {
    List<T> findBySpecification(FindSpecification<T> specification) throws ReceiverException {
        Repository<T> repository = new Repository<>();
        RepositoryController controller = new RepositoryController(repository);
        List<T> entity;
        try {
            entity = repository.find(specification);
            controller.end();
        } catch (RepositoryException e) {
            throw new ReceiverException(e);
        }
        return entity;
    }
}