package ru.sber.spring.java13springmy.sdproject.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sber.spring.java13springmy.sdproject.model.Group;
import ru.sber.spring.java13springmy.sdproject.model.User;

@Repository
public interface UserRepository extends GenericRepository<User> {
    @Query("select u from User u where u.login = ?1")
    User findUsersByLogin(String login);

    User findUsersByEmail(String email);

    User findUsersByGroup(Group group);

    User findUserByChangePasswordToken(String token);
}
