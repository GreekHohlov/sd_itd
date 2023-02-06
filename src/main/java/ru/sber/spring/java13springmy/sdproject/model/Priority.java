package ru.sber.spring.java13springmy.sdproject.model;

public enum Priority {
    LOW("Низкий"),
    MIDDLE("Средний"),
    HIGH("Высокий");

    private final String priorityTextDisplay;

    Priority(String text) {
        this.priorityTextDisplay = text;
    }

    public String priorityTextDisplay() {
        return this.priorityTextDisplay;
    }
}
