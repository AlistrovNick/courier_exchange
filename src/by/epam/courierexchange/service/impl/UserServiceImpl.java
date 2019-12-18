package by.epam.courierexchange.service.impl;

import by.epam.courierexchange.controller.SessionRequestContent;
import by.epam.courierexchange.dao.impl.UserDaoImpl;
import by.epam.courierexchange.entity.RoleType;
import by.epam.courierexchange.entity.User;
import by.epam.courierexchange.exception.DaoException;
import by.epam.courierexchange.exception.ServiceException;
import by.epam.courierexchange.service.UserService;
import by.epam.courierexchange.validator.ValidatorPersonalData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import static by.epam.courierexchange.command.ParamName.*;

public class UserServiceImpl implements UserService {
    private final static String CHARSET = "UTF-8";
    private final static String ALGORITHM = "SHA-1";
    private static Logger logger = LogManager.getLogger();

    public List<User> findById(int id) throws ServiceException {
        List<User> userList = new ArrayList<>();
        try {
            userList.addAll(UserDaoImpl.INSTANCE.findById(id));
        } catch (DaoException e) {
            logger.error("findById", e);
            throw new ServiceException("findById", e);
        }
        return userList;
    }

    public List<User> findByRole(RoleType role) throws ServiceException {
        List<User> findList;
        try {
            findList = UserDaoImpl.INSTANCE.findByRole(role);
        } catch (DaoException e) {
            logger.error("findByRole", e);
            throw new ServiceException("findByRole", e);
        }
        return findList;
    }

    public List<User> findByEmail(String email) throws ServiceException {
        List<User> findList;
        try {
            findList = UserDaoImpl.INSTANCE.findByEmail(email);
        } catch (DaoException e) {
            logger.error("findByEmail", e);
            throw new ServiceException("findByEmail", e);
        }
        return findList;
    }

    public String findPasswordById(int id) throws ServiceException {
        String password;
        try {
            password = UserDaoImpl.INSTANCE.findPasswordById(id);
        } catch (DaoException e) {
            logger.error("findPasswordById", e);
            throw new ServiceException("findPasswordById", e);
        }
        return password;
    }

    public boolean checkByEmail(String email) throws ServiceException {
        List<User> findList;
        try {
            findList = UserDaoImpl.INSTANCE.findByEmail(email);
        } catch (DaoException e) {
            logger.error("checkByEmail", e);
            throw new ServiceException("checkByEmail", e);
        }
        return findList.size() != 0;
    }

    public void insert(User user, String password) throws ServiceException {
        try {
            UserDaoImpl.INSTANCE.insert(user, password);
        } catch (DaoException e) {
            logger.error("insert", e);
            throw new ServiceException("insert", e);
        }
    }

    public boolean changeFirstName(User user, String firstName) throws ServiceException {
        ValidatorPersonalData validator = new ValidatorPersonalData();
        if (!validator.validateName(firstName)) {
            return false;
        } else {
            try {
                UserDaoImpl.INSTANCE.changeFirstName(user, firstName);
                return true;
            } catch (DaoException e) {
                logger.error("changeFirstName", e);
                throw new ServiceException("changeFirstName", e);
            }
        }
    }

    public boolean changeLastName(User user, String lastName) throws ServiceException {
        ValidatorPersonalData validator = new ValidatorPersonalData();
        if (!validator.validateName(lastName)) {
            return false;
        } else {
            try {
                UserDaoImpl.INSTANCE.changeLastName(user, lastName);
                return true;
            } catch (DaoException e) {
                logger.error("changeLastName", e);
                throw new ServiceException("changeLastName", e);
            }
        }
    }

    public boolean changePassword(User user, String password, String confirmPassword) throws ServiceException {
        ValidatorPersonalData validator = new ValidatorPersonalData();
        if (!password.equals(confirmPassword) || (!validator.validatePassword(password) && !password.isEmpty())) {
            return false;
        } else {
            try {
                if (!password.isEmpty()) {
                    UserDaoImpl.INSTANCE.changePassword(user, codingPassword(password));
                }
                return true;
            } catch (DaoException e) {
                logger.error("changePassword", e);
                throw new ServiceException("changePassword", e);
            }
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
