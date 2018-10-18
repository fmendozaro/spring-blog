package us.tacos4.blog.services;

import us.tacos4.blog.repositories.UserRoles;
import us.tacos4.blog.models.User;
import us.tacos4.blog.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("customUserDetailsService")
public class UserDetailsLoader implements UserDetailsService {
    private final UsersRepository usersRepository;
    private final UserRoles roles;

    @Autowired
    public UserDetailsLoader(UsersRepository usersRepository, UserRoles roles) {
        this.usersRepository = usersRepository;
        this.roles = roles;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user found for " + username);
        }

        List<String> userRoles = roles.ofUserWith(username);
        return new UserWithRoles(user, userRoles);
    }
}