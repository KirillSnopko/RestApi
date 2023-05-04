package com.snopko.RestApi.security.config;

import com.snopko.RestApi.security.dao.entity.RoleDao;
import com.snopko.RestApi.security.dao.entity.UserDao;
import com.snopko.RestApi.security.dao.repository.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConfig {
    private static final Logger log = LoggerFactory.getLogger(DatabaseConfig.class);
    @Autowired
    private PasswordEncoder encoder;
    @Value("${default.admin.username}")
    private String adminUsername;
    @Value("${default.admin.password}")
    private String adminPassword;

    @Bean
    CommandLineRunner initDatabase(IUserRepository repository) {
        UserDao admin = new UserDao();
        admin.setUsername(adminUsername);
        admin.setPassword(encoder.encode(adminPassword));
        admin.setRole(RoleDao.ADMIN);

        return args -> {
            if (!repository.existsByUsername(adminUsername)) {
                log.info("Preloading " + repository.save(admin));
            }
        };
    }
}
