package kr.co.farmstory.security;

import kr.co.farmstory.entity.User;
import kr.co.farmstory.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class SecurityUserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> result = userRepository.findById(username);

        UserDetails userDetails = null;

        if(!result.isEmpty()){
            userDetails = MyUserDetails.builder()
                        .user(result.get())
                        .build();
        }

        return userDetails;
    }
}