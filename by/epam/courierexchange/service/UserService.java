package by.epam.courierexchange.service;

import by.epam.courierexchange.dao.impl.UserDaoImp;
import by.epam.courierexchange.entity.RoleType;
import by.epam.courierexchange.entity.User;
import by.epam.courierexchange.exception.DaoException;
import by.epam.courierexchange.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private static Logger logger = LogManager.getLogger();
    private final static String CHARSET = "UTF-8";
    private final static String ALGORITHM = "SHA-1";

    public List<User> findById(int id) throws ServiceException {
        List<User> userList = new ArrayList<>();
        try {
            userList.addAll(UserDaoImp.INSTANCE.findById(id));
        } catch (DaoException e) {
            logger.error("findById", e);
            throw new ServiceException("findById", e);
        }
        return userList;
    }

    public List<User> findByRole(RoleType role) throws ServiceException {
        List<User> findList;
        try {
            findList = UserDaoImp.INSTANCE.findByRole(role);
        } catch (DaoException e) {
            logger.error("findByRole", e);
            throw new ServiceException("findByRole", e);
        }
        return findList;
    }

    public List<User> findByEmail(String email) throws ServiceException {
        List<User> findList;
        try {
            findList = UserDaoImp.INSTANCE.findByEmail(email);
        } catch (DaoException e) {
            logger.error("findByEmail", e);
            throw new ServiceException("findByEmail", e);
        }
        return findList;
    }

    public String findPasswordById(int id) throws ServiceException {
        String password;
        try {
            password = UserDaoImp.INSTANCE.findPasswordById(id);
        } catch (DaoException e) {
            logger.error("findPasswordById", e);
            throw new ServiceException("findPasswordById", e);
        }
        return password;
    }

    public boolean checkByEmail(String email) throws ServiceException {
        List<User> findList;
        try {
            findList = UserDaoImp.INSTANCE.findByEmail(email);
        } catch (DaoException e) {
            logger.error("checkByEmail", e);
            throw new ServiceException("checkByEmail", e);
        }
        return findList.size() != 0;
    }

    public void insert(User user, String password) throws ServiceException {
        try {
            UserDaoImp.INSTANCE.insert(user, password);
        } catch (DaoException e) {
            logger.error("insert", e);
            throw new ServiceException("insert", e);
        }
    }

    public void changeFirstName(User user, String firstName) throws ServiceException {
        try {
            UserDaoImp.INSTANCE.changeFirstName(user, firstName);
        } catch (DaoException e) {
            logger.error("changeFirstName", e);
            throw new ServiceException("changeFirstName", e);
        }
    }

    public void changeLastName(User user, String lastName) throws ServiceException {
        try {
            UserDaoImp.INSTANCE.changeLastName(user, lastName);
        } catch (DaoException e) {
            logger.error("changeLastName", e);
            throw new ServiceException("changeLastName", e);
        }
    }

    public void changePassword(User user, String password) throws ServiceException {
        try {
            UserDaoImp.INSTANCE.changePassword(user, codingPassword(password));
        } catch (DaoException e) {
            logger.error("changePassword", e);
            throw new ServiceException("changePassword", e);
        }
    }

    public String codingPassword(String password) throws ServiceException {
        byte bytes[];
        try {
            MessageDigest message = MessageDigest.getInstance(ALGORITHM);
            message.update(password.getBytes(CHARSET));
            bytes = message.digest();
        } catch (NoSuchAlgorithmException e) {
            logger.error("coding", e);
            throw new ServiceException("coding", e);
        } catch (UnsupportedEncodingException e) {
            logger.error("coding", e);
            throw new ServiceException("coding", e);
        }
        return new BigInteger(bytes).toString();
    }
}
