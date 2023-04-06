package ru.sber.spring.java13springmy;

import ru.sber.spring.java13springmy.sdproject.dto.RoleDTO;
import ru.sber.spring.java13springmy.sdproject.dto.TypeTaskDTO;
import ru.sber.spring.java13springmy.sdproject.model.Role;
import ru.sber.spring.java13springmy.sdproject.model.TypeTask;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public interface TypeTaskTestData {
    TypeTaskDTO TYPE_TASK_DTO1 = new TypeTaskDTO("nameType1",
            1L,
            SLATestData.SLA_1,
            new HashSet<>());
    TypeTaskDTO TYPE_TASK_DTO2 = new TypeTaskDTO("nameType2",
            2L,
            SLATestData.SLA_2,
            new HashSet<>());
    TypeTaskDTO TYPE_TASK_DTO3 = new TypeTaskDTO("nameType3",
            3L,
            SLATestData.SLA_3,
            new HashSet<>());

    List<TypeTaskDTO> TASK_DTO_LIST = Arrays.asList(TYPE_TASK_DTO1, TYPE_TASK_DTO2, TYPE_TASK_DTO3);

    TypeTask TYPE_TASK_1 = new TypeTask("nameType1",
            SLATestData.SLA_1,
            new HashSet<>());

    TypeTask TYPE_TASK_2 = new TypeTask("nameType2",
            SLATestData.SLA_2,
            new HashSet<>());

    TypeTask TYPE_TASK_3 = new TypeTask("nameType3",
            SLATestData.SLA_3,
            new HashSet<>());

    List<TypeTask> TYPE_TASK_LIST = Arrays.asList(TYPE_TASK_1, TYPE_TASK_1, TYPE_TASK_1);
}
