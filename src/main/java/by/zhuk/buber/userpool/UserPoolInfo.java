package by.zhuk.buber.userpool;

import by.zhuk.buber.model.User;

import java.time.LocalTime;
/**
 * Class include user info and time when this object add to pool
 * @see RestorePasswordUserPool,SignUpUserPool
 */
public class UserPoolInfo {
    private User user;
    private LocalTime time;

    public UserPoolInfo(User user, LocalTime time) {
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