package by.zhuk.buber.receiver;

import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.exception.RepositoryException;
import by.zhuk.buber.repository.Repository;
import by.zhuk.buber.repository.RepositoryController;
import by.zhuk.buber.specification.Specification;

/**
 * Class adding to the specification database
 * @see Specification
 */
class Adder {

    /**
     * @param specification the specification on which adds to the database
     * @see Specification,Repository,RepositoryController
     * @exception ReceiverException throws when there are problems with the database
     */
    void addBySpecification(Specification specification) throws ReceiverException {
        Repository repository = new Repository<>();
        RepositoryController controller = new RepositoryController(repository);
        try {
            repository.add(specification);
            controller.end();
        } catch (RepositoryException e) {
            throw new ReceiverException(e);
        }
    }
}