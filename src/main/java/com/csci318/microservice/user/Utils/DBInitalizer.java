package com.csci318.microservice.user.Utils;

import com.csci318.microservice.user.Constants.Roles;
import com.csci318.microservice.user.Entities.User;
import com.csci318.microservice.user.Repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.UUID;

@Component
public class DBInitalizer implements CommandLineRunner {

    private final UserRepository userRepository;

    public DBInitalizer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (userRepository.count() == 0) {
            User user1 = new User();
            user1.setId(UuidUtils.getFixedUUID());
            user1.setUsername("evanle");
            user1.setPassword("password1");
            user1.setFirstName("Evan");
            user1.setLastName("Le");
            user1.setEmail("evan.le@example.com");
            user1.setPhone("1234567890");
            user1.setRole(Roles.USER);
            user1.setCreateAt(new Timestamp(System.currentTimeMillis()));
            user1.setModifyAt(new Timestamp(System.currentTimeMillis()));
            user1.setCreateBy("system");
            user1.setModifyBy("system");

            User user2 = new User();
            user2.setId(UUID.fromString("e50f4c17-a0af-4c74-b589-c815e87ba820"));
            user2.setUsername("jasminewang");
            user2.setPassword("password2");
            user2.setFirstName("Jasmine");
            user2.setLastName("Wang");
            user2.setEmail("jasmine.wang@example.com");
            user2.setPhone("0987654321");
            user2.setRole(Roles.USER);
            user2.setCreateAt(new Timestamp(System.currentTimeMillis()));
            user2.setModifyAt(new Timestamp(System.currentTimeMillis()));
            user2.setCreateBy("system");
            user2.setModifyBy("system");

            userRepository.save(user1);
            userRepository.save(user2);


        }
    }
}
