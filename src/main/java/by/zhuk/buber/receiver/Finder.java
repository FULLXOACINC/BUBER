package by.zhuk.buber.receiver;

import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.exception.RepositoryException;
import by.zhuk.buber.repository.Repository;
import by.zhuk.buber.repository.RepositoryController;
import by.zhuk.buber.specification.find.FindSpecification;

import java.util.List;

/**
 * Class template find T entities by FindSpecification
 */
class Finder<T> {

    /**
     * Method find T entities by FindSpecification
     *
     * @return T entities list
     * @throws ReceiverException throws when there are problems with the database
     * @see RepositoryController,Repository,FindSpecification
     */
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