package ru.sber.spring.java13springmy.sdproject.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sber.spring.java13springmy.sdproject.model.TypeTask;
@Repository
public interface TypeTaskRepository extends GenericRepository<TypeTask>{
    @Query(nativeQuery = true, value = """
            select *
            from type_task
            where name_type ilike '%' || coalesce(:nameType, '%') || '%'
            """)
    TypeTask findByNameType(String nameType);
}
