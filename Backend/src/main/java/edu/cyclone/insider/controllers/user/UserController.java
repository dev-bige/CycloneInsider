package edu.cyclone.insider.controllers.user;

import edu.cyclone.insider.controllers.user.models.SignUpRequestModel;
import edu.cyclone.insider.models.InsiderUser;
import edu.cyclone.insider.repos.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("users")
public class UserController {

    private UsersRepository usersRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(value = "current", method = RequestMethod.GET)
    public InsiderUser current() {
        return usersRepository.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @RequestMapping(value = "sign-up", method = RequestMethod.POST)
    public void signUp(@RequestBody SignUpRequestModel request) {
        InsiderUser newUser = new InsiderUser();
        newUser.setFirstName(request.firstName);
        newUser.setLastName(request.lastName);
        newUser.setPassword(passwordEncoder.encode(request.password));
        newUser.setUsername(request.username);
        usersRepository.save(newUser);
    }
}
