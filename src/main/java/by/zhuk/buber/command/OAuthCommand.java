package by.zhuk.buber.command;

import by.zhuk.buber.constant.OAuthConstant;
import by.zhuk.buber.constant.PagesConstant;
import by.zhuk.buber.exeption.UnknownOAuthException;
import by.zhuk.buber.oauth.OAuth;
import by.zhuk.buber.validator.LoginValidator;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class OAuthCommand implements Command {
    private static final String LOGIN_TYPE = "loginType";

    @Override
    public CommandResult execute(HttpServletRequest request) {
        if (LoginValidator.isAuthorization(request.getSession())) {
            return new CommandResult(TransitionType.FORWARD, PagesConstant.WELCOM_PAGE);
        }

        String loginType = request.getParameter(LOGIN_TYPE);
        if (loginType == null) {
            loginType = (String) request.getSession().getAttribute(LOGIN_TYPE);
        }
        OAuth oAuth;
        try {
            oAuth = OAuthFactory.create(loginType);
        } catch (UnknownOAuthException e) {
            return new CommandResult(TransitionType.FORWARD, PagesConstant.LOGIN_PAGE);
        }


        String accessToken;
        String userDisplayName;
        if (oAuth.hasError(request.getParameter("error"))) {
            return new CommandResult(TransitionType.REDIRECT, PagesConstant.LOGIN_PAGE);
        } else {
            if (request.getParameter("code") == null) {
                request.getSession().setAttribute(LOGIN_TYPE, loginType);
                return new CommandResult(TransitionType.REDIRECT, oAuth.getAuthUrl());
            } else {
                HttpClient httpclient = new HttpClient();
                PostMethod post = new PostMethod(oAuth.getTokenUrl());
                post.addParameter("grant_type", "authorization_code");
                post.addParameter("code", request.getParameter("code"));
                post.addParameter("client_id", oAuth.getClientId());
                post.addParameter("client_secret", oAuth.getClientSecret());
                post.addParameter("redirect_uri", oAuth.getRedirectUri());
                try {
                    httpclient.executeMethod(post);
                    JSONObject jsonPostResult = oAuth.parseResult(post);
                    accessToken = jsonPostResult.getString(OAuthConstant.ACCESS_TOKEN);

                    GetMethod get = new GetMethod(oAuth.takeLoginInfoUrl(jsonPostResult));
                    httpclient.executeMethod(get);
                    JSONObject jsonGetResult = oAuth.parseResult(get);
                    userDisplayName = oAuth.takeDisplayName(jsonGetResult);
                } catch (IOException e) {
                    return new CommandResult(TransitionType.FORWARD, PagesConstant.LOGIN_PAGE);
                }
            }
        }
        request.getSession().setAttribute(OAuthConstant.ACCESS_TOKEN, accessToken);
        request.getSession().setAttribute(OAuthConstant.DISPLAY_USER_NAME, userDisplayName);
        return new CommandResult(TransitionType.FORWARD, PagesConstant.WELCOM_PAGE);

    }
}
