package ru.sber.spring.java13springmy;

import ru.sber.spring.java13springmy.sdproject.dto.SLADTO;
import ru.sber.spring.java13springmy.sdproject.model.Role;
import ru.sber.spring.java13springmy.sdproject.model.SLA;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public interface SLATestData {
    SLADTO SLA_DTO_1 = new SLADTO("nameSLA1",
            4,
            8,
            new HashSet<>());
    SLADTO SLA_DTO_2 = new SLADTO("nameSLA2",
            4,
            8,
            new HashSet<>());
    SLADTO SLA_DTO_3 = new SLADTO("nameSLA3",
            10,
            20,
            new HashSet<>());

    List<SLADTO> SLADTO_LIST = Arrays.asList(SLA_DTO_1, SLA_DTO_2, SLA_DTO_3);
    SLA SLA_1 = new SLA("nameSLA3",
            4,
            8,
            new HashSet<>());
    SLA SLA_2 = new SLA("nameSLA2",
            4,
            8,
            new HashSet<>());
    SLA SLA_3 = new SLA("nameSLA3",
            10,
            20,
            new HashSet<>());

    List<SLA> SLA_LIST = Arrays.asList(SLA_1, SLA_2, SLA_3);
}
