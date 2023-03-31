package ru.sber.spring.java13springmy.sdproject.constants;

public interface Errors {
    class Tasks {
        public static final String TASK_DELETE_ERROR = "Заявка не может быть удалена";
    }
    
    class Users {
        public static final String USER_FORBIDDEN_ERROR = "У вас нет прав просматривать информацию о пользователе";
    }
    
    class REST {
        public static final String DELETE_ERROR_MESSAGE = "Удаление невозможно";
        public static final String AUTH_ERROR_MESSAGE = "Неавторизованный пользователь";
        public static final String ACCESS_ERROR_MESSAGE = "Отказано в доступе!";
    }
}
