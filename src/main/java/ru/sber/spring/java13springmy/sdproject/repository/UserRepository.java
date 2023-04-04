package ru.sber.spring.java13springmy.sdproject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sber.spring.java13springmy.sdproject.model.Group;
import ru.sber.spring.java13springmy.sdproject.model.User;

import java.util.List;

@Repository
public interface UserRepository extends GenericRepository<User> {
    @Query("select u from User u where u.login = ?1")
    User findUsersByLogin(String login);

    User findUsersByEmail(String email);

    User findUsersByGroup(Group group);

    @Query(nativeQuery = true,
            value = """
                    SELECT *
                    from users u
                    where u.is_worker is true
                    """)
    List<User> findUserIsWorker();

    User findUserByChangePasswordToken(String token);

    @Query(nativeQuery = true,
            value = """
                    select u.*
                    from users u
                    where u.first_name ilike '%' || coalesce(:firstName, '%') || '%'
                    and u.last_name ilike '%' || coalesce(:lastName, '%') || '%'
                    and u.login ilike '%' || coalesce(:login, '%') || '%'
                     """)
    Page<User> searchUsers(String firstName,
                           String lastName,
                           String login,
                           Pageable pageable);

// Отправка уведамления о просрочке
//    @Query(nativeQuery = true,
//            value = """
//                 select email
//                 from users u join book_rent_info bri on u.id = bri.user_id
//                 where bri.return_date >= now()
//                 and bri.returned = false
//                 and u.is_deleted = false
//                 """)
//    List<String> getDelayedEmails();

}
