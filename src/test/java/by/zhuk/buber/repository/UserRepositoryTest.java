package by.zhuk.buber.repository;

import by.zhuk.buber.connectionpool.ConnectionPool;
import by.zhuk.buber.exeption.RepositoryException;
import by.zhuk.buber.model.User;
import by.zhuk.buber.model.UserType;
import by.zhuk.buber.specification.SQLSpecification;
import by.zhuk.buber.specification.impl.FindUserByLoginSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.testng.Assert.fail;

public class UserRepositoryTest {

    @Test
    public void testAddPositive() {
        ConnectionPool.getInstance();
        UserRepository repository = new UserRepository();
        User user = new User();
        user.setLogin("testAddLogin");
        user.setFirstName("testAddName");
        user.setLastName("testAddLastName");
        user.setPassword("password");
        user.setType(UserType.USER);
        user.setBalance(new BigDecimal(99.89));
        user.setAge(21);
        user.setPhoneNumber("291713229");
        try {
            repository.add(user);
        } catch (RepositoryException e) {
            fail("Test does not throws RepositoryException",e);
        }
    }

    @Test
    public void testUpdatePositive() {
        ConnectionPool.getInstance();
        UserRepository repository = new UserRepository();
        User user = new User();
        user.setLogin("testAddLogin");
        user.setFirstName("testAddName");
        user.setLastName("testAddLastName");
        user.setPassword("password");
        user.setType(UserType.USER);
        user.setBalance(new BigDecimal(98.89));
        user.setAge(21);
        user.setPhoneNumber("291713229");
        try {
            repository.update(user);
        } catch (RepositoryException e) {
            fail("Test does not throws RepositoryException",e);
        }
    }

    @Test
    public void testDeletePositive() {
        ConnectionPool.getInstance();
        UserRepository repository = new UserRepository();
        User user =new User();
        user.setLogin("testAddLogin");
        try {
            repository.delete(user);
        } catch (RepositoryException e) {
            fail("Test does not throws RepositoryException",e);
        }
    }

    @Test
    public void testFindPositive() {
        SQLSpecification sqlSpecification = new FindUserByLoginSpecification("san91130324@gmail.com");
        ConnectionPool.getInstance();
        UserRepository repository = new UserRepository();
        User user = new User();
        user.setLogin("san91130324@gmail.com");
        user.setFirstName("Alex");
        user.setLastName("Zhuk");
        user.setPassword("dc76e9f0c0006e8f919e0c515c66dbba3982f785");
        user.setType(UserType.ADMIN);
        user.setBalance(new BigDecimal("12.00"));
        user.setAge(20);
        user.setPhoneNumber("291713227");
        user.setBaned(false);
        try {
            List<User> users=repository.find(sqlSpecification);
            User result=users.get(0);
            Assert.assertEquals(result,user);
        } catch (RepositoryException e) {
            fail("Test does not throws RepositoryException",e);
        }
    }
}