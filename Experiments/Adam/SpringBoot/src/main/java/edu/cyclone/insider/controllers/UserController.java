package edu.cyclone.insider.controllers;

import edu.cyclone.insider.models.InsiderUser;
import edu.cyclone.insider.repos.UsersRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/v1")
public class UserController {

    private UsersRepository usersRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UsersRepository usersRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(value = "current", method = RequestMethod.GET)
    public List<InsiderUser> getAllUsers() {
        return usersRepository.findAll();
    }

    @RequestMapping(value = "sign-up", method = RequestMethod.POST)
    public void signUp(@RequestBody SignUpRequestModel request) {
        InsiderUser newUser = new InsiderUser();
        newUser.setName(request.name);
        newUser.setPassword(passwordEncoder.encode(request.password));
        newUser.setUsername(request.username);
        usersRepository.save(newUser);
    }
}
