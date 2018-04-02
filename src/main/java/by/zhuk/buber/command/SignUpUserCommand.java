package by.zhuk.buber.command;

import by.zhuk.buber.constant.PagesConstant;
import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.receiver.SignInReceiver;
import by.zhuk.buber.receiver.SignUpReceiver;
import by.zhuk.buber.validator.SignInValidator;
import by.zhuk.buber.validator.SignUpValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class SignUpUserCommand implements Command {
    private static Logger logger = LogManager.getLogger(SignUpUserCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request) {
        String login = request.getParameter("login");
        String firstName = request.getParameter("firstName");
        String secondName = request.getParameter("secondName");
        String password = request.getParameter("password");
        String repeatPassword = request.getParameter("repeatPassword");
        String age = request.getParameter("age");
        String phoneNumber = request.getParameter("phoneNumber");

        SignInReceiver signInReceiver = new SignInReceiver();
        SignUpReceiver signUpReceiver = new SignUpReceiver();

        boolean signUpProblem = false;

        if (!SignInValidator.isLoginValid(login)) {
            signUpProblem = true;
            request.setAttribute("notValidLoginError", true);
        }

        try {
            if (signInReceiver.isLoginExist(login)) {
                signUpProblem = true;
                request.setAttribute("loginExistError", true);
            }
            if (signUpReceiver.isPhoneNumberExist(phoneNumber)) {
                signUpProblem = true;
                request.setAttribute("phoneNumberExistError", true);
            }
        } catch (ReceiverException e) {
            //TODO error page
            logger.catching(e);
            return new CommandResult(TransitionType.FORWARD, PagesConstant.LOGIN_PAGE);
        }

        if (!SignUpValidator.isNameValid(firstName)) {
            signUpProblem = true;
            request.setAttribute("firstNameError", true);
        }
        if (!SignUpValidator.isNameValid(secondName)) {
            signUpProblem = true;
            request.setAttribute("secondNameError", true);
        }
        if (!SignUpValidator.isPasswordValid(password)) {
            signUpProblem = true;
            request.setAttribute("notValidPasswordError", true);
        }
        if (!password.equals(repeatPassword)) {
            signUpProblem = true;
            request.setAttribute("passwordNotEq", true);
        }
        if (!SignUpValidator.isAgeValid(age)) {
            signUpProblem = true;
            request.setAttribute("ageError", true);
        }
        if (!SignUpValidator.isPhoneNumberValid(phoneNumber)) {
            signUpProblem = true;
            request.setAttribute("notValidPhoneNumberError", true);
        }


        if (signUpProblem) {
            request.setAttribute("oldLogin", login);
            request.setAttribute("oldFirstName", firstName);
            request.setAttribute("oldSecondName", secondName);
            request.setAttribute("oldAge", age);
            request.setAttribute("oldPhoneNumber", phoneNumber);

            return new CommandResult(TransitionType.FORWARD, PagesConstant.SING_UP_PAGE);
        } else {
            signUpReceiver.sendAcceptMail(login,firstName,secondName,password,age,phoneNumber);
            request.setAttribute("allCorrect", true);
            return new CommandResult(TransitionType.FORWARD, PagesConstant.SING_UP_PAGE);
        }

    }
}