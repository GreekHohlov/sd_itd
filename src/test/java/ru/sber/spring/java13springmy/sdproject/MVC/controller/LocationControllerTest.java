package ru.sber.spring.java13springmy.sdproject.MVC.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.sber.spring.java13springmy.sdproject.model.Location;
import ru.sber.spring.java13springmy.sdproject.service.userdetails.CustomUserDetailsService;

import java.util.HashSet;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
public class LocationControllerTest {

    @Autowired
    protected MockMvc mvc;
    @Autowired
    protected CustomUserDetailsService userDetailsService;

    @Test
    @Order(1)
    protected void getAll() throws Exception {
        log.info("Тест по просмотра всех Площадок через MVC начат успешно");
        String result = mvc.perform(MockMvcRequestBuilders.get("/locations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        log.info(result);
        log.info("Тест по просмотра всех Плоощадки через MVC закончен успешно");
    }

    @Test
    @Order(2)
    public void create() throws Exception {
//        mvc.perform(MockMvcRequestBuilders.get("/locations/add"))
//                        .andExpect(status().isOk());


    }

    @Test
    @Order(3)
    public void deleteSoft() throws Exception {
//        Location location1 = new Location("name1",new HashSet<>());
//
//        log.info("Тест по удалению  начат успешно");
//        mvc.perform(MockMvcRequestBuilders.delete("/locations/deleteSoft/{id}", 1L)
//
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                )
//
//                .andExpect(status().is2xxSuccessful());


//
//        mvc.perform(MockMvcRequestBuilders.get("/locations/deleteSoft/{id}", 1L)
//                        //    .headers(headers)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                )
//           //     .andExpect(MockMvcResultMatchers.redirectedUrl("localhost:9090/locations"))
//                .andExpect(status().is3xxRedirection());


    }


}

