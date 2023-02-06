package ru.sber.spring.java13springmy.sdproject.model;

public enum StatusTask {
    OPEN("Открыта"),
    AT_WORK("В работе"),
    STOPPED("Остановлена"),
    COMPLETED("Выполнена"),
    CLOSED("Закрыта");
    private final String statusTaskTextDisplay;

    StatusTask(String text) {
        this.statusTaskTextDisplay = text;
    }

    public String statusTaskTextDisplay() {
        return this.statusTaskTextDisplay;
    }
}
