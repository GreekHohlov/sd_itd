package ru.sber.spring.java13springmy.sdproject.MVC.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
public abstract class CommonTestMVC {
    @Autowired
    protected MockMvc mvc;
    protected ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    public void prepare() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    protected abstract void getAll() throws Exception;
    protected abstract void create() throws Exception;

    protected abstract void update() throws Exception;

    protected abstract void delete() throws Exception;



    protected String asJsonString(final Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
