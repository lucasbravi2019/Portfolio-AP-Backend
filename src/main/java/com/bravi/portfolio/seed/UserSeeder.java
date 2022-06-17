package com.bravi.portfolio.seed;

import com.bravi.portfolio.entity.User;
import com.bravi.portfolio.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${custom.app.seed.users.enabled}")
    private Boolean enabled;

    @Override
    public void run(String... args) throws Exception {
        if (!enabled) return;
        if (userRepository.findAll().size() == 0) {
            userRepository.save(User.builder().username("Lucas").password(passwordEncoder.encode("password")).build());
        }
    }
}
