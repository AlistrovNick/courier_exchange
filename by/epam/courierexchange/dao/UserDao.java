package by.epam.courierexchange.dao;

import by.epam.courierexchange.entity.RoleType;
import by.epam.courierexchange.entity.User;
import by.epam.courierexchange.exception.DaoException;

import java.util.List;

public interface UserDao {
    List<User> findById(int id) throws DaoException;
    List<User> findByEmail(String name) throws DaoException;
    List<User> findByRole(RoleType role) throws DaoException;
    String findPasswordById(int id) throws DaoException;
    void insert(User user, String password) throws DaoException;
    void changeFirstName(User user, String firstName) throws DaoException;
    void changeLastName(User user, String lastName) throws DaoException;
    void changePassword(User user, String password) throws DaoException;
}
