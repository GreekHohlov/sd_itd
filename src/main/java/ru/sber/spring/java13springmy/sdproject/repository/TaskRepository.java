package ru.sber.spring.java13springmy.sdproject.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.sber.spring.java13springmy.sdproject.model.Task;

import java.util.List;
@Repository
public interface TaskRepository extends GenericRepository<Task>{
    @Query(nativeQuery = true, value = """
        select *
        from tasks t
        where t.name_task ilike '%' || coalesce(:nameTask, '%') || '%'
        and cast(t.id as char) ilike coalesce(:id, '%')
        and cast(t.status as char) like coalesce(:statusTask, '%')
        """)
    List<Task> searchTasks(@Param(value = "id") String id,
                           @Param(value = "nameTask") String nameTask,
//                           @Param(value = "userFio") String userFio,
//                           @Param(value = "workerFio") String workerFio,
//                           @Param(value = "nameCategory") String nameCategory,
                           @Param(value = "statusTask") String statusTask
    );
}
