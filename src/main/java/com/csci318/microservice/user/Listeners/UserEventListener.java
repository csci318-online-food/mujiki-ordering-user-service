package com.csci318.microservice.user.Listeners;

import com.csci318.microservice.user.DTOs.UserDTOResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserEventListener {
    private static final Logger log = LoggerFactory.getLogger(UserEventListener.class);

    @EventListener
    public void handleUserHandler(UserDTOResponse event) {
        log.info("Restaurant created: {}", event.getId());
    }
}
