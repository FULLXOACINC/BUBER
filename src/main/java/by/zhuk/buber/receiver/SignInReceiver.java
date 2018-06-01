package by.zhuk.buber.receiver;

import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.model.User;
import by.zhuk.buber.specification.find.FindSpecification;
import by.zhuk.buber.specification.find.user.FindUserByLoginAndPasswordSpecification;
import by.zhuk.buber.specification.find.user.FindUserByLoginSpecification;
import by.zhuk.buber.userpool.SignUpUserPool;
import by.zhuk.buber.userpool.UserPoolInfo;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class SignInReceiver {

    public boolean isLoginExist(String login) throws ReceiverException {
        FindSpecification<User> specification = new FindUserByLoginSpecification(login);
        Finder<User> userFinder = new Finder<>();
        List<User> users = userFinder.findBySpecification(specification);
        ConcurrentHashMap<String, UserPoolInfo> signUpMap = SignUpUserPool.getInstance().takeSignUpMap();

        boolean isLoginExistSignUp = false;
        for (UserPoolInfo info : signUpMap.values()) {
            if (info.getUser().getLogin().equals(login)) {
                isLoginExistSignUp = true;
            }

        }
        return !users.isEmpty() || isLoginExistSignUp;
    }


    public boolean checkPassword(String login, String password) throws ReceiverException {
        FindSpecification<User> specification = new FindUserByLoginAndPasswordSpecification(login, password);
        Finder<User> userFinder = new Finder<>();
        List<User> users = userFinder.findBySpecification(specification);
        return !users.isEmpty();

    }
}