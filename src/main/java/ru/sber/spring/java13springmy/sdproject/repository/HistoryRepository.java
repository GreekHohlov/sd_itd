package ru.sber.spring.java13springmy.sdproject.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sber.spring.java13springmy.sdproject.model.History;

import java.util.List;

@Repository
public interface HistoryRepository extends GenericRepository<History>{
    List<History> getAllById(Long id);
    @Query(nativeQuery = true, value = """
            select *
            from history
            where task_id = :taskId
                        """)
    List<History> findAllByTaskId(Long taskId);
}
