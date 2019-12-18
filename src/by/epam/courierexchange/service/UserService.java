package by.epam.courierexchange.service;

import by.epam.courierexchange.controller.SessionRequestContent;
import by.epam.courierexchange.entity.RoleType;
import by.epam.courierexchange.entity.User;
import by.epam.courierexchange.exception.ServiceException;

import java.util.List;

public interface UserService {
    List<User> findById(int id) throws ServiceException;

    List<User> findByRole(RoleType role) throws ServiceException;

    List<User> findByEmail(String email) throws ServiceException;

    String findPasswordById(int id) throws ServiceException;

    boolean checkByEmail(String email) throws ServiceException;

    void insert(User user, String password) throws ServiceException;

    boolean changeFirstName(User user, String firstName) throws ServiceException;

    boolean changeLastName(User user, String lastName) throws ServiceException;

    boolean changePassword(User user, String password, String confirmPassword) throws ServiceException;

    String codingPassword(String password) throws ServiceException;
}
