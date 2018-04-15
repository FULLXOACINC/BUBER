package by.zhuk.buber.command;

import by.zhuk.buber.constant.PagesConstant;
import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.receiver.SignInReceiver;
import by.zhuk.buber.receiver.SignUpReceiver;
import by.zhuk.buber.receiver.UserReceiver;
import by.zhuk.buber.validator.SignInValidator;
import by.zhuk.buber.validator.SignUpUserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class SignUpUserCommand implements Command {
    private static Logger logger = LogManager.getLogger(SignUpUserCommand.class);
    private static final String FIRST_NAME = "firstName";
    private static final String SECOND_NAME = "secondName";
    private static final String PASSWORD = "password";
    private static final String REPEAT_PASSWORD = "repeatPassword";
    private static final String BIRTH_DAY = "birthDay";
    private static final String PHONE_NUMBER = "phoneNumber";


    private static final String NOT_VALID_LOGIN_ERROR = "notValidLoginError";
    private static final String LOGIN_EXIST_ERROR = "loginExistError";
    private static final String PHONE_NUMBER_EXIST_ERROR = "phoneNumberExistError";
    private static final String FIRST_NAME_ERROR = "firstNameError";
    private static final String SECOND_NAME_ERROR = "secondNameError";
    private static final String NOT_VALID_PASSWORD = "notValidPasswordError";
    private static final String PASSWORD_NOT_EQ = "passwordNotEq";
    private static final String BIRTH_DAY_ERROR = "birthDayError";
    private static final String NOT_VALID_PHONE_NUMBER_ERROR = "notValidPhoneNumberError";

    private static final String OLD_LOGIN = "oldLogin";
    private static final String OLD_FIRST_NAME = "oldFirstName";
    private static final String OLD_SECOND_NAME = "oldSecondName";
    private static final String OLD_BIRTH_DAY = "oldBirthDay";
    private static final String OLD_PHONE_NUMBER = "oldPhoneNumber";

    private static final String ALL_CORRECT = "allCorrect";


    @Override
    public Router execute(HttpServletRequest request) {
        String login = request.getParameter(UserConstant.LOGIN);
        String firstName = request.getParameter(FIRST_NAME);
        String secondName = request.getParameter(SECOND_NAME);
        String password = request.getParameter(PASSWORD);
        String repeatPassword = request.getParameter(REPEAT_PASSWORD);
        String birthDay = request.getParameter(BIRTH_DAY);
        String phoneNumber = request.getParameter(PHONE_NUMBER);

        UserReceiver userReceiver = new UserReceiver();
        SignInReceiver signInReceiver = new SignInReceiver();
        SignUpReceiver signUpReceiver = new SignUpReceiver();

        boolean signUpProblem = false;

        if (!SignInValidator.isLoginValid(login)) {
            signUpProblem = true;
            request.setAttribute(NOT_VALID_LOGIN_ERROR, true);
        }

        try {
            if (signInReceiver.isLoginExist(login)) {
                signUpProblem = true;
                request.setAttribute(LOGIN_EXIST_ERROR, true);
            }
            if (userReceiver.isPhoneNumberExist(phoneNumber)) {
                signUpProblem = true;
                request.setAttribute(PHONE_NUMBER_EXIST_ERROR, true);
            }
        } catch (ReceiverException e) {
            //TODO error page
            logger.catching(e);
            return new Router(TransitionType.FORWARD, PagesConstant.LOGIN_PAGE);
        }

        if (!SignUpUserValidator.isNameValid(firstName)) {
            signUpProblem = true;
            request.setAttribute(FIRST_NAME_ERROR, true);
        }
        if (!SignUpUserValidator.isNameValid(secondName)) {
            signUpProblem = true;
            request.setAttribute(SECOND_NAME_ERROR, true);
        }
        if (!SignUpUserValidator.isPasswordValid(password)) {
            signUpProblem = true;
            request.setAttribute(NOT_VALID_PASSWORD, true);
        }
        if (!password.equals(repeatPassword)) {
            signUpProblem = true;
            request.setAttribute(PASSWORD_NOT_EQ, true);
        }
        if (!SignUpUserValidator.isBirthDayValid(birthDay)) {
            signUpProblem = true;
            request.setAttribute(BIRTH_DAY_ERROR, true);
        }
        if (!SignUpUserValidator.isPhoneNumberValid(phoneNumber)) {
            signUpProblem = true;
            request.setAttribute(NOT_VALID_PHONE_NUMBER_ERROR, true);
        }


        if (!signUpProblem) {
            signUpReceiver.sendAcceptMail(login, firstName, secondName, password, birthDay, phoneNumber);
            request.setAttribute(ALL_CORRECT, true);

        } else {
            request.setAttribute(OLD_LOGIN, login);
            request.setAttribute(OLD_FIRST_NAME, firstName);
            request.setAttribute(OLD_SECOND_NAME, secondName);
            request.setAttribute(OLD_BIRTH_DAY, birthDay);
            request.setAttribute(OLD_PHONE_NUMBER, phoneNumber);
        }
        return new Router(TransitionType.FORWARD, PagesConstant.SING_UP_PAGE);
    }
}