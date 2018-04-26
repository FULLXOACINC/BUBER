package by.zhuk.buber.receiver;

import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.exeption.RepositoryException;
import by.zhuk.buber.model.User;
import by.zhuk.buber.repository.Repository;
import by.zhuk.buber.repository.RepositoryTransaction;
import by.zhuk.buber.repository.UserRepository;
import by.zhuk.buber.signuppool.SignUpUserInfo;
import by.zhuk.buber.signuppool.SignUpUserPool;
import by.zhuk.buber.specification.Specification;
import by.zhuk.buber.specification.impl.FindUserByLoginAndPasswordSpecification;
import by.zhuk.buber.specification.impl.FindUserByLoginSpecification;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class SignInReceiver {

    public boolean isLoginExist(String login) throws ReceiverException {
        Specification specification = new FindUserByLoginSpecification(login);
        UserReceiver userReceiver = new UserReceiver();
        List<User> users=userReceiver.findUsersBySpecification(specification);
        ConcurrentHashMap<String, SignUpUserInfo> signUpMap = SignUpUserPool.getInstance().takeSignUpMap();

        boolean isLoginExistSignUp = false;
        for (SignUpUserInfo info : signUpMap.values()) {
            if (info.getUser().getLogin().equals(login)) {
                isLoginExistSignUp = true;
            }

        }
        return !users.isEmpty() || isLoginExistSignUp;
    }


    public boolean checkPassword(String login, String password) throws ReceiverException { ;
        Specification specification = new FindUserByLoginAndPasswordSpecification(login, password);
        UserReceiver userReceiver = new UserReceiver();
        List<User> users=userReceiver.findUsersBySpecification(specification);
        return !users.isEmpty();

    }
}