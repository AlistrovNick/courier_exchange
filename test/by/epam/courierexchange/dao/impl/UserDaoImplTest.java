package test.by.epam.courierexchange.dao.impl;

import by.epam.courierexchange.dao.impl.UserDaoImpl;
import by.epam.courierexchange.entity.RoleType;
import by.epam.courierexchange.entity.User;
import by.epam.courierexchange.entity.UserStatusType;
import by.epam.courierexchange.exception.DaoException;
import by.epam.courierexchange.pool.ConnectionPool;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.testng.Assert.assertEquals;

public class UserDaoImplTest {

    @BeforeMethod
    public void createTableUsers() throws SQLException {
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        try (Statement statement = connection.createStatement()) {
            statement.execute("USE courier_exchange_test;");
            statement.executeUpdate("CREATE TABLE users (user_id INT(5) AUTO_INCREMENT, email VARCHAR(40), first_name VARCHAR(20), last_name VARCHAR(20), user_password VARCHAR(20), user_role VARCHAR(20), user_status VARCHAR(20) DEFAULT 'active', PRIMARY KEY (user_id));");
            statement.executeUpdate("INSERT INTO users (user_id, email, first_name, last_name, user_password, user_role, user_status) VALUE (1, '123@mail.ru', 'Ivan', 'Ivanov', '12345', 'admin', 'active');");
            statement.executeUpdate("INSERT INTO users (user_id, email, first_name, last_name, user_password, user_role, user_status) VALUE (2, '123@gmail.com', 'Petr', 'Petrov', '12345', 'courier', 'active');");
            statement.executeUpdate("INSERT INTO users (user_id, email, first_name, last_name, user_password, user_role, user_status) VALUE (3, '12345@gmail.com', 'Andrei', 'Sidorov', '12345', 'client', 'active');");
            statement.executeUpdate("INSERT INTO users (user_id, email, first_name, last_name, user_password, user_role, user_status) VALUE (4, 'qwerty@mail.ru', 'Sergei', 'Fedorov', '12345', 'admin', 'active');");
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }

    @AfterMethod
    public void deleteTableUsers() throws SQLException {
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        try (Statement statement = connection.createStatement();) {
            statement.executeUpdate("DROP TABLE users;");
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }

    @DataProvider(name = "dpTestFindByEmail")
    public Object[][] dpTestFindByEmail() {
        return new Object[][]{
                {"123@mail.ru", new User(1, "123@mail.ru", "Ivan", "Ivanov", RoleType.ADMIN, UserStatusType.ACTIVE)},
                {"123@gmail.com", new User(2, "123@gmail.com", "Petr", "Petrov", RoleType.COURIER, UserStatusType.ACTIVE)},
                {"12345@gmail.com", new User(3, "12345@gmail.com", "Andrei", "Sidorov", RoleType.CLIENT, UserStatusType.ACTIVE)}

        };
    }

    @Test(dataProvider = "dpTestFindByEmail")
    public void testFindByEmail(String email, User expected) throws DaoException {
        User actual = UserDaoImpl.INSTANCE.findByEmail(email).get(0);
        assertEquals(actual, expected);
    }

    @DataProvider(name = "dpTestInsert")
    public Object[][] dpTestInsert() {
        return new Object[][]{
                {new User(5, "test@gmail.com", "Max", "Mayers", RoleType.COURIER, UserStatusType.ACTIVE), "test@gmail.com"},
                {new User(6, "test1@gmail.com", "Ilay", "Wood", RoleType.COURIER, UserStatusType.ACTIVE), "test1@gmail.com"},
                {new User(7, "test2@gmail.com", "Andrei", "Smith", RoleType.COURIER, UserStatusType.ACTIVE), "test2@gmail.com"}
        };
    }

    @Test(dataProvider = "dpTestInsert")
    public void testInsert(User expected, String email) throws DaoException {
        UserDaoImpl.INSTANCE.insert(expected, null);
        User actual = UserDaoImpl.INSTANCE.findByEmail(email).get(0);
        assertEquals(actual, expected);
    }

    @DataProvider(name = "dpTestChangeFirstName")
    public Object[][] dpTestChangeFirstName() {
        return new Object[][]{
                {new User(1, "123@mail.ru", "Ivan", "Ivanov", RoleType.ADMIN, UserStatusType.ACTIVE), "Sergei"},
                {new User(2, "123@gmail.com", "Petr", "Petrov", RoleType.COURIER, UserStatusType.ACTIVE), "Vladimir"},
                {new User(3, "12345@gmail.com", "Andrei", "Sidorov", RoleType.CLIENT, UserStatusType.ACTIVE), "Alex"}
        };
    }

    @Test(dataProvider = "dpTestChangeFirstName")
    public void testChangeFirstName(User user, String newFirstName) throws DaoException {
        UserDaoImpl.INSTANCE.changeFirstName(user, newFirstName);
        User actual = UserDaoImpl.INSTANCE.findByEmail(user.getEmail()).get(0);
        user.setFirstName(newFirstName);
        User expected = user;
        assertEquals(actual, expected);
    }
}