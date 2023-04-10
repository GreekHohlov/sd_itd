package ru.sber.spring.java13springmy.sdproject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.sber.spring.java13springmy.sdproject.model.Task;

import java.util.List;

@Repository
public interface TaskRepository extends GenericRepository<Task> {
    @Query(nativeQuery = true, value = """
            select t.*
                      from tasks t
                      left join users u on t.user_id = u.id
                      left join users w on t.worker_id = w.id
                      join category c on t.category = c.id
                      where cast(t.id as char) SIMILAR TO coalesce(:id, '%')
                      and t.name_task ilike '%' || coalesce(:nameTask, '%') || '%'
                      and u.last_name || ' ' || u.first_name || ' ' || u.middle_name ilike '%' || coalesce(:userFio, '%') || '%'
                      and w.last_name || ' ' || w.first_name || ' ' || w.middle_name ilike '%' || coalesce(:workerFio, '%') || '%'
                      and c.name_category ilike '%' || coalesce(:nameCategory, '%') || '%'
                      and cast(t.status as char) like coalesce(:statusTask, '%')
              """)
    Page<Task> searchTasks(@Param(value = "id") String id,
                           @Param(value = "nameTask") String nameTask,
                           @Param(value = "userFio") String userFio,
                           @Param(value = "workerFio") String workerFio,
                           @Param(value = "nameCategory") String nameCategory,
                           @Param(value = "statusTask") String statusTask,
                           Pageable pageable);

    Page<Task> findAllByIsDeletedFalse(Pageable pageable);

    @Query(nativeQuery = true, value = """
            select t.*
            from tasks t
                    left join users u on t.user_id = u.id
            where u.login ilike :login
              and t.is_deleted is false
                        """)
    Page<Task> findAllTaskByLogin(String login, Pageable pageable);

    @Query(nativeQuery = true, value = """
            select *
            from tasks
            where is_deleted is false
            """)
    Page<Task> findAllNotDeletedTask(Pageable pageable);
}
