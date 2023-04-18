package ru.sber.spring.java13springmy.sdproject.service.userdetails;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.sber.spring.java13springmy.sdproject.model.User;
import ru.sber.spring.java13springmy.sdproject.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Value("${spring.security.user.name}")
    private String adminUserName;
    @Value("${spring.security.user.password}")
    private String adminUserPassword;
    @Value("${spring.security.user.roles}")
    private String adminUserRole;
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.equals(adminUserName)) {
            return new CustomUserDetails(null, username, adminUserPassword,
                    List.of(new SimpleGrantedAuthority("ROLE_" + adminUserRole)));
        } else {
            User user = userRepository.findUsersByLogin(username);
            String role = user.getRole().getNameRole();
            System.out.println(role);
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            return new CustomUserDetails(user.getId().intValue(), username, user.getPassword(), authorities);
        }
    }


}
