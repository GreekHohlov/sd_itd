package ru.sber.spring.java13springmy.sdproject.repository;

import org.springframework.stereotype.Repository;
import ru.sber.spring.java13springmy.sdproject.model.User;

@Repository
public interface UserRepository extends GenericRepository<User> {
    User findUsersByLogin(String login);
    User findUsersByEmail(String email);
}
