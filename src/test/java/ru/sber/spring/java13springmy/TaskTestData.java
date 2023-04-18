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
            1L,
            StatusTask.OPEN,
            "files1",
            "decision1",
            new HashSet<Long>());
    TaskDTO TASK_DTO_2 = new TaskDTO("taskName2",
            2L,
            Priority.HIGH,
            2L,
            "description2",
            LocalDateTime.now(),
            LocalDateTime.now().plusDays(2L),
            new HashSet<Long>(),
            2L,
            2L,
            StatusTask.AT_WORK,
            "files2",
            "decision2",
            new HashSet<Long>());
    TaskDTO TASK_DTO_3 = new TaskDTO("taskName3",
            3L,
            Priority.HIGH,
            3L,
            "description3",
            LocalDateTime.now(),
            LocalDateTime.now().plusDays(3L),
            new HashSet<Long>(),
            3L,
            3L,
            StatusTask.AT_WORK,
            "files3",
            "decision3",
            new HashSet<Long>());

    List<TaskDTO> TASK_DTO_LIST = Arrays.asList(TASK_DTO_1, TASK_DTO_2, TASK_DTO_3);

    Task TASK_1 = new Task("taskName1",
            TypeTaskTestData.TYPE_TASK_1,
            Priority.HIGH,
            CategoryTestData.CATEGORY_1,
            "description1",
            LocalDateTime.now(),
            LocalDateTime.now().plusDays(3L),
            new HashSet<>(),
            "file1",
            UserTestData.USER_1,
            UserTestData.USER_3,
            StatusTask.OPEN,
            "decision1",
            new HashSet<>());

    Task TASK_2 = new Task("taskName2",
            TypeTaskTestData.TYPE_TASK_2,
            Priority.HIGH,
            CategoryTestData.CATEGORY_2,
            "description2",
            LocalDateTime.now(),
            LocalDateTime.now().plusDays(3L),
            new HashSet<>(),
            "file2",
            UserTestData.USER_2,
            UserTestData.USER_3,
            StatusTask.OPEN,
            "decision2",
            new HashSet<>());
    Task TASK_3 = new Task("taskName3",
            TypeTaskTestData.TYPE_TASK_3,
            Priority.HIGH,
            CategoryTestData.CATEGORY_3,
            "description3",
            LocalDateTime.now(),
            LocalDateTime.now().plusDays(3L),
            new HashSet<>(),
            "file3",
            UserTestData.USER_3,
            UserTestData.USER_3,
            StatusTask.OPEN,
            "decision3",
            new HashSet<>());

    List<Task> TASK_LIST = Arrays.asList(TASK_1, TASK_2, TASK_3);
}
