package by.zhuk.buber.receiver;

import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.exeption.RepositoryException;
import by.zhuk.buber.model.User;
import by.zhuk.buber.repository.Repository;
import by.zhuk.buber.repository.UserRepository;
import by.zhuk.buber.singleton.SignUpUserInfo;
import by.zhuk.buber.singleton.SignUpUserPool;
import by.zhuk.buber.specification.Specification;
import by.zhuk.buber.specification.impl.FindUserByLoginAndPasswordSpecification;
import by.zhuk.buber.specification.impl.FindUserByLoginSpecification;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class SignInReceiver {

    public boolean isLoginExist(String login) throws ReceiverException {
        Repository<User> repository = new UserRepository();
        Specification specification = new FindUserByLoginSpecification(login);
        List<User> users;
        try {
            users = repository.find(specification);
        } catch (RepositoryException e) {
            throw new ReceiverException(e);
        }
        ConcurrentHashMap<String, SignUpUserInfo> signUpMap = SignUpUserPool.getInstance().takeSignUpMap();
        boolean isLoginExistSignUp = false;
        for (SignUpUserInfo info : signUpMap.values()) {
            if (info.getUser().getLogin().equals(login)) {
                isLoginExistSignUp = true;
            }

        }
        return !users.isEmpty() || isLoginExistSignUp;
    }


    public boolean checkPassword(String login, String password) throws ReceiverException {
        Repository<User> repository = new UserRepository();
        Specification specification = new FindUserByLoginAndPasswordSpecification(login, password);
        List<User> users;
        try {
            users = repository.find(specification);
        } catch (RepositoryException e) {
            throw new ReceiverException(e);
        }
        return !users.isEmpty();

    }
}