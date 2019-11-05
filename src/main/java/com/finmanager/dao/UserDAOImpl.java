package com.finmanager.dao;

import com.finmanager.exceptions.DbOperationException;
import com.finmanager.exceptions.NotFoundException;
import com.finmanager.mapper.UserMapper;
import com.finmanager.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class UserDAOImpl implements IUserDAO {

    private UserMapper userMapper;
    private JdbcTemplate jdbcTemplate;
    private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

    @Autowired
    public UserDAOImpl(UserMapper userMapper, DataSource dataSource) {
        this.userMapper = userMapper;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User create(User user) {
        LocalDateTime time = LocalDateTime.now();
        user.setCreatedDate(time);
        user.setUpdatedDate(time);

        try {
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> createStatement(user, connection), keyHolder);
            user.setId((Long) Optional.ofNullable(Objects.requireNonNull(keyHolder.getKeys()).get("id"))
                    .orElseThrow(() -> new DbOperationException("Create User Dao method error: AutoGeneratedKey = null")));
            return user;
        } catch (Exception e) {
            logger.error("Can't create user: {} Error: ", user, e);
            throw new DbOperationException("Can't create user with id = " + user.getId() + " User: " + user + " " + e.getMessage());
        }
    }

    @Override
    public User findById(Long id) {
        try {
            return jdbcTemplate.queryForObject(Queries.SQL_FIND_USER_BY_ID, userMapper, id);
        } catch (EmptyResultDataAccessException | NotFoundException e) {
            throw getAndLogUserNotFoundException(id);
        } catch (Exception e) {
            logger.error("Can't get user with id={}. Error: ", id, e);
            throw new DbOperationException("Can't get user with id=" + id);
        }
    }

    @Override
    public List<User> findAll() {
        try {
            return jdbcTemplate.query(Queries.SQL_FIND_ALL_USERS, userMapper);
        } catch (Exception e) {
            logger.error("Can't get all users. Error: ", e);
            throw new DbOperationException("Can't get all users. Error: " + e.getMessage());
        }
    }

    @Override
    public User update(User user) {
        int rowsAffected;
        try {
            user.setUpdatedDate(LocalDateTime.now());
            rowsAffected = jdbcTemplate.update(Queries.SQL_UPDATE_USER,
                    user.getEmail(),
                    user.getPassword(),
                    user.getName(),
                    user.getSurname(),
                    user.getPhone(),
                    user.getRole().toString(),
                    user.getUpdatedDate(),
                    user.getId());
        } catch (Exception e) {
            logger.error("Updating user error: " + user + " " + e.getMessage());
            throw new DbOperationException("Can't update user: " + user);
        }
        if (rowsAffected < 1) {
            throw getAndLogUserNotFoundException(user.getId());
        }
        return user;
    }

    @Override
    public User findByEmail(String email) {
        try {
            return jdbcTemplate.queryForObject(Queries.SQL_FIND_USER_BY_EMAIL, userMapper, email);
        } catch (EmptyResultDataAccessException | NotFoundException e) {
            throw getAndLogUserNotFoundException(email);
        } catch (Exception e) {
            logger.error("Can't get user with email = {}. Error: ", email, e);
            throw new DbOperationException("Can't get user with email = " + email);
        }
    }

    @Override
    public boolean delete(Long id) {
        int rowsAffected;
        try {
            rowsAffected = jdbcTemplate.update(Queries.SQL_DELETE_USER, id);
        } catch (Exception e) {
            logger.error("Deleting user with id = {} error: ", id, e);
            throw new DbOperationException("Deleting user with id = " + id + " error: " + e.getMessage());
        }
        if (rowsAffected < 1) {
            throw getAndLogUserNotFoundException(id);
        } else return true;
    }

    private PreparedStatement createStatement(User user, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(Queries.SQL_CREATE_USER, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, user.getEmail());
        ps.setString(2, user.getPassword());
        ps.setString(3, user.getName());
        ps.setString(4, user.getSurname());
        ps.setString(5, user.getPhone());
        ps.setString(6, user.getRole().toString());
        ps.setObject(7, user.getCreatedDate());
        ps.setObject(8, user.getUpdatedDate());
        return ps;
    }

    private NotFoundException getAndLogUserNotFoundException(Long id) {
        NotFoundException notFoundException = new NotFoundException("User not found");
        logger.error("Runtime exception. User not found id = {}. Message: {}", id, notFoundException.getMessage());
        return notFoundException;
    }

    private NotFoundException getAndLogUserNotFoundException(String str) {
        NotFoundException notFoundException = new NotFoundException("User not found");
        logger.error("Runtime exception. User not found email = {}. Message: {}", str, notFoundException.getMessage());
        return notFoundException;
    }

    private boolean isRecordWithMailExist(User user) {
        if (null != jdbcTemplate.query(Queries.SQL_FIND_USER_BY_EMAIL, userMapper, user.getEmail()))
            return true;
        else return false;
    }


    private class Queries {
        static final String SQL_CREATE_USER = "insert into users(email, password, name, surname, phone, role, created_date, updated_date) values (?,?,?,?,?,?,?,?)";
        static final String SQL_FIND_USER_BY_EMAIL = "select * from users where email = ?";
        static final String SQL_FIND_USER_BY_ID = "select * from users where id = ?";
        static final String SQL_DELETE_USER = "delete from users where id = ?";
        static final String SQL_FIND_ALL_USERS = "select * from users";
        static final String SQL_UPDATE_USER = "update users set email = ?, password = ?, name = ?, surname = ?, phone = ?, role = ?, updated_date = ? where id =?";
    }
}
