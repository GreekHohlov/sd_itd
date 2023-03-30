package ru.sber.spring.java13springmy.sdproject.model;

public enum Priority {
    HIGH("Высокий"),
    MIDDLE("Средний"),
    LOW("Низкий");

    private final String priorityTextDisplay;

    Priority(String text) {
        this.priorityTextDisplay = text;
    }

    public String priorityTextDisplay() {
        return this.priorityTextDisplay;
    }
}
