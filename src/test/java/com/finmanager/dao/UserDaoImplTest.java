package com.finmanager.dao;

import com.finmanager.exceptions.DbOperationException;
import com.finmanager.exceptions.NotFoundException;
import com.finmanager.model.Role;
import com.finmanager.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoImplTest extends BaseTest {

    @Autowired
    private UserDAOImpl userDAO;

    private User user = new User(4L, "mail@mail.com", "Password",
            "name", "surname", "9087238", Role.ADMIN, LocalDateTime.now(), LocalDateTime.now());

    @BeforeEach
    void createTestUser() {
        user = userDAO.create(user);
    }

    @Test
    void createUser() {
        user.setEmail("new@mail.com");
        assertEquals(user, userDAO.create(user));
    }

    @Test
    void createUser_emailAlreadyExist() {
        assertThrows(DbOperationException.class, () -> userDAO.create(user));
    }

    @Test
    void createUser_emailIsNull() {
        user.setEmail(null);
        assertThrows(DbOperationException.class, () -> userDAO.create(user));
    }

    @Test
    void getUserById() {
        assertEquals(user, userDAO.findById(user.getId()));
    }

    @Test
    void getUserById_invalidId() {
        assertThrows(NotFoundException.class, () -> userDAO.findById(Long.MAX_VALUE));
    }

    @Test
    void getUserByEmail() {
        assertEquals(user, userDAO.findByEmail(user.getEmail()));
    }

    @Test
    void getUserByEmail_invalidEmail() {
        assertThrows(NotFoundException.class, () -> userDAO.findByEmail("not.exist@mail.com"));
    }

    @Test
    void deleteUser() {
        assertTrue(userDAO.delete(user.getId()));
    }

    @Test
    void deleteUser_invalidId() {
        assertThrows(NotFoundException.class, () -> userDAO.delete(Long.MAX_VALUE));
    }

    @Test
    void getAllUsers() {
        cleanUsers();
        List<User> users = Arrays.asList(userDAO.create(user),
                userDAO.create(new User(5L, "5mail@mail.com", "Pass", "mme", "sur",
                        "9087238", Role.USER, LocalDateTime.now(), LocalDateTime.now())),
                userDAO.create(new User(6L, "6mail@mail.com", "word", "na", "ser",
                        "9087238", Role.ADMIN, LocalDateTime.now(), LocalDateTime.now())));

        List<User> usersFromDb = userDAO.findAll();
        for (int i = 0; i < users.size(); i++) {
            assertEquals(users.get(i), usersFromDb.get(i));
        }
    }

    @Test
    void updateUser() {
        user.setEmail("edited.mail");
        user.setRole(Role.USER);
        assertEquals(user, userDAO.update(user));
    }

    @Test
    void updateUser_invalidId() {
        user.setId(Long.MAX_VALUE);
        assertThrows(NotFoundException.class, () -> userDAO.update(user));
    }

    @Test
    void updateUser_setExistedEmail() {
        User testUser = userDAO.create(new User(user.getId(), "user.getEmail", user.getPassword(),
                user.getName(), user.getSurname(), user.getPhone(), user.getRole(), user.getCreatedDate(), user.getUpdatedDate()));
        testUser.setEmail(user.getEmail());
        assertThrows(DbOperationException.class, () -> userDAO.update(testUser));
    }

    @Test
    void updateUser_setNullValue() {
        user.setEmail(null);
        assertThrows(DbOperationException.class, () -> userDAO.update(user));
    }

    @AfterEach
    void cleanUsers() {
        clearAllTables();
    }
}
