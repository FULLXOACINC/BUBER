package by.zhuk.buber.singleton;

import by.zhuk.buber.model.User;

import java.time.LocalTime;

public class SignUpUserInfo {
    private User user;
    private LocalTime time;

    public SignUpUserInfo(User user, LocalTime time) {
        this.user = user;
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}