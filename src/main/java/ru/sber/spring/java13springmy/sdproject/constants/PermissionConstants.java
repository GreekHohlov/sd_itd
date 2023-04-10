package ru.sber.spring.java13springmy.sdproject.constants;

import java.util.List;

public interface PermissionConstants {

    /*
    Настройка доступа к ресурсам
    */
    List<String> RESOURCES_WHITE_LIST = List.of("/resources/**",
            "/js/**",
            "/templates/**",
            "/swagger-ui/**");
    /*
    Общий доступ к сервису для всех, в том числе и не авторизовавшимся пользователям.
     */
    List<String> ALL_WHITE_LIST = List.of("/login",
            "/users/registration",
            "/users/remember-password",
            "/users/change-password/user",
            "/users/change-password",
            "/logout");
    /*
    Базовый доступ к сервису
    Главная страница, просмотр заявок, создание заявок.
    Предоставляется всем группам пользователей.
    */
    List<String> BASE_PERMISSION_LIST = List.of("/",
            "index",
            "/task",
            "/task/add",
            "/task/add",
            "/task/search",
            "/task/download/{taskId}",
            "/error",
            "/users/profile/{id}",
            "/task/noexecuteTask/{id}",
            "/task/history/{id}");
    /*
    Полный доступ.
    Предоставляется группам EXECUTOR и ADMIN.
     */
    List<String> SETTINGS_PERMISSION_LIST = List.of("/users",
            "/users/add",
            "/users/update",
            "/users/delete",
            "/users/restore",
            "/users",
            "/task/add",
            "/task/update",
            "/task/delete",
            "/locations",
            "/locations/add",
            "/locations/update",
            "/locations/delete",
            "/category",
            "/category/add",
            "/category/update",
            "/category/delete",
            "/typetask",
            "/typetask/add",
            "/typetask/update",
            "/typetask/delete",
            "/sla",
            "/sla/add",
            "/sla/update",
            "/sla/delete",
            "/groups",
            "/groups/add",
            "/groups/update",
            "/groups/delete",
            "/role",
            "/role/add",
            "/role/update",
            "/role/delete",
            "/workSchedule",
            "/workSchedule/add",
            "/workSchedule/update",
            "/workSchedule/delete",
            "/reports",
            "/task/stopTask/{id}",
            "/task/stopTask",
            "/task/unstopTask/{id}",
            "/reports",
            "reports/report");

    List<String> EXECUTOR_PERMISSION_LIST = List.of("/task/search/noAssign",
            "/task/search/assignToMe",
            "/task/search/myTask");
}
