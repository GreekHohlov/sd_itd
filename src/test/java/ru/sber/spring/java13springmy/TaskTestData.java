package ru.sber.spring.java13springmy;

import ru.sber.spring.java13springmy.sdproject.dto.TaskDTO;
import ru.sber.spring.java13springmy.sdproject.model.Priority;
import ru.sber.spring.java13springmy.sdproject.model.StatusTask;
import ru.sber.spring.java13springmy.sdproject.model.Task;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public interface TaskTestData {
    TaskDTO TASK_DTO_1 = new TaskDTO("taskName1",
            1L,
            Priority.HIGH,
            1L,
            "description1",
            LocalDateTime.now(),
            LocalDateTime.now().plusDays(1L),
            new HashSet<Long>(),
            1L,
            10L,
            StatusTask.OPEN,
            "files1",
            "decision1");
    TaskDTO TASK_DTO_2 = new TaskDTO("taskName2",
            2L,
            Priority.HIGH,
            2L,
            "description2",
            LocalDateTime.now(),
            LocalDateTime.now().plusDays(2L),
            new HashSet<Long>(),
            2L,
            9L,
            StatusTask.AT_WORK,
            "files2",
            "decision2");
    TaskDTO TASK_DTO_3 = new TaskDTO("taskName3",
            3L,
            Priority.HIGH,
            3L,
            "description3",
            LocalDateTime.now(),
            LocalDateTime.now().plusDays(3L),
            new HashSet<Long>(),
            3L,
            8L,
            StatusTask.AT_WORK,
            "files3",
            "decision3");

    List<TaskDTO> TASK_DTO_LIST = Arrays.asList(TASK_DTO_1 ,TASK_DTO_2,TASK_DTO_3 );



//    Task  TASK_1 = new Task("taskName1",
//
//            );
//
//
//    Author AUTHOR_1 = new Author("author1",
//            LocalDate.now(),
//            "description1",
//            null);
//
//    Author AUTHOR_2 = new Author("author2",
//            LocalDate.now(),
//            "description2",
//            null);
//
//    Author AUTHOR_3 = new Author("author3",
//            LocalDate.now(),
//            "description3",
//            null);
//
//    List<Author> AUTHOR_LIST = Arrays.asList(AUTHOR_1, AUTHOR_2, AUTHOR_3);

}
