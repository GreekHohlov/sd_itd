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
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import ru.sber.spring.java13springmy.sdproject.service.userdetails.CustomUserDetailsService;



@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
public abstract class CommonTest {
    @Autowired
    protected MockMvc mvc;
//    @Autowired
//    protected JWTTokenUtil jwtTokenUtil;
    @Autowired
    protected CustomUserDetailsService userDetailsService;

  //  protected String token = "";
    protected HttpHeaders headers = new HttpHeaders();

    protected ObjectMapper objectMapper = new ObjectMapper();

//    private String generateToken(final String username) {
//        return jwtTokenUtil.generateToken(userDetailsService.loadUserByUsername(username));
//    }

//    @BeforeAll
//    public void prepare() {
////        token = generateToken("admin");
//      //  headers.add("Authorization", "Bearer " + token);
//        objectMapper.registerModule(new JavaTimeModule());
//    }

    protected abstract void createObject() throws Exception;

    protected abstract void updateObject() throws Exception;

    protected abstract void deleteObject() throws Exception;

    protected abstract void listAll() throws Exception;

    protected String asJsonString(final Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        }
        catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return null;
        }
    }

}
