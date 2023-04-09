package ru.sber.spring.java13springmy.sdproject.service;

import ru.sber.spring.java13springmy.sdproject.dto.GenericDTO;
import ru.sber.spring.java13springmy.sdproject.exception.MyDeleteException;
import ru.sber.spring.java13springmy.sdproject.mapper.GenericMapper;
import ru.sber.spring.java13springmy.sdproject.model.GenericModel;
import ru.sber.spring.java13springmy.sdproject.repository.GenericRepository;
import ru.sber.spring.java13springmy.sdproject.service.userdetails.CustomUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

//TODO: https://habr.com/ru/post/444982/ - Mokito
public abstract class GenericTest<E extends GenericModel, D extends GenericDTO> {
    protected GenericService<E, D> service;
    protected GenericRepository<E> repository;
    protected GenericMapper<E, D> mapper;
    
    @BeforeEach
    void init() {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(CustomUserDetails
                                                                                                           .builder()
                                                                                                           .username("USER"),
                                                                                                     null,
                                                                                                     null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    
    protected abstract void listAll();
    
    protected abstract void getOne();
    
    protected abstract void create();
    
    protected abstract void update();
    
    protected abstract void delete() throws MyDeleteException;
    
}
