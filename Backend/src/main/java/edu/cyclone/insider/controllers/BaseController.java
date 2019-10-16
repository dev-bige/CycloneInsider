package edu.cyclone.insider.controllers;

import edu.cyclone.insider.controllers.post.models.PostCreateRequestModel;
import edu.cyclone.insider.models.InsiderUser;
import edu.cyclone.insider.models.Post;
import edu.cyclone.insider.models.Room;
import edu.cyclone.insider.repos.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public class BaseController {
    protected UsersRepository usersRepository;

    @Autowired
    public BaseController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public final InsiderUser getCurrentUser() {
        return usersRepository.findUserByUsername(getCurrentUsername());
    }

    public final String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }


}
