package by.zhuk.buber.repository;

import by.zhuk.buber.connectionpool.ConnectionPool;
import by.zhuk.buber.exeption.RepositoryException;
import by.zhuk.buber.model.User;
import by.zhuk.buber.model.UserType;
import by.zhuk.buber.specification.find.FindSpecification;
import by.zhuk.buber.specification.find.FindUserByLoginSpecification;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.testng.Assert.fail;

public class UserRepositoryTest {
    //TODO rewrite tests
    private User user;
    private User updateUser;
    private User findUser;
//    private UserRepository repository;
//
//    @BeforeTest
//    public void start() {
//        ConnectionPool.getInstance();
//        repository = new UserRepository();
//        user = new User();
//        user.setLogin("testLogin");
//        user.setFirstName("testName");
//        user.setLastName("testLastName");
//        user.setPassword("password");
//        user.setType(UserType.USER);
//        user.setBalance(new BigDecimal(99.89));
//        user.setBirthDay(LocalDate.now());
//        user.setPhoneNumber("+375291713229");
//
//        findUser = new User();
//        findUser.setLogin("san91130324@gmail.com");
//        findUser.setFirstName("Alex");
//        findUser.setLastName("Zhuk");
//        findUser.setPassword("dc76e9f0c0006e8f919e0c515c66dbba3982f785");
//        findUser.setType(UserType.ADMIN);
//        findUser.setBalance(new BigDecimal("12.00"));
//        findUser.setBirthDay(LocalDate.parse("1998-04-13"));
//        findUser.setPhoneNumber("+375291713227");
//        findUser.setBaned(false);
//
//        updateUser = new User();
//        updateUser.setLogin("testLogin");
//        updateUser.setFirstName("testName");
//        updateUser.setLastName("testLastName");
//        updateUser.setPassword("password");
//        updateUser.setType(UserType.USER);
//        updateUser.setBalance(new BigDecimal(96.89));
//        updateUser.setBirthDay(LocalDate.now());
//        updateUser.setPhoneNumber("+375291713229");
//
//    }
//
//    @AfterTest
//    public void end() {
//
//    }
//
//    @Test
//    public void testAddPositive() {
//        try {
//            repository.add(user);
//        } catch (RepositoryException e) {
//            fail("Test does not throws RepositoryException", e);
//        }
//    }
//
//    @Test
//    public void testUpdatePositive() {
//        ConnectionPool.getInstance();
//        UserRepository repository = new UserRepository();
//        User user = new User();
//        user.setLogin("testAddLogin");
//        user.setFirstName("testAddName");
//        user.setLastName("testAddLastName");
//        user.setPassword("password");
//        user.setType(UserType.USER);
//        user.setBalance(new BigDecimal(98.89));
//        user.setBirthDay(LocalDate.now());
//        user.setPhoneNumber("+375291713229");
//        try {
//            repository.update(user);
//        } catch (RepositoryException e) {
//            fail("Test does not throws RepositoryException", e);
//        }
//    }

//    @Test
//    public void testDeletePositive() {
//        ConnectionPool.getInstance();
//        UserRepository repository = new UserRepository();
//        User user = new User();
//        user.setLogin("testAddLogin");
//        try {
//            repository.delete(user);
//        } catch (RepositoryException e) {
//            fail("Test does not throws RepositoryException", e);
//        }
//    }
//
//    @Test
//    public void testFindPositive() {
//        FindSpecification<User> specification = new FindUserByLoginSpecification("san91130324@gmail.com");
//        ConnectionPool.getInstance();
//        UserRepository repository = new UserRepository();
//        User user = new User();
//        user.setLogin("san91130324@gmail.com");
//        user.setFirstName("Alex");
//        user.setLastName("Zhuk");
//        user.setPassword("dc76e9f0c0006e8f919e0c515c66dbba3982f785");
//        user.setType(UserType.ADMIN);
//        user.setBalance(new BigDecimal("12.00"));
//        user.setBirthDay(LocalDate.now());
//        user.setPhoneNumber("+375291713227");
//        user.setBaned(false);
//        try {
//            List<User> users = repository.find(specification);
//            User result = users.get(0);
//            Assert.assertEquals(result, user);
//        } catch (RepositoryException e) {
//            fail("Test does not throws RepositoryException", e);
//        }
//    }
}