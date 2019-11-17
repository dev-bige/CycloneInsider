package edu.cyclone.insider.services;

import edu.cyclone.insider.controllers.user.models.SignUpRequestModel;
import edu.cyclone.insider.models.InsiderUser;
import edu.cyclone.insider.repos.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private PasswordEncoder passwordEncoder;
    private UsersRepository usersRepository;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UsersRepository usersRepository) {
        this.passwordEncoder = passwordEncoder;
        this.usersRepository = usersRepository;
    }

    public InsiderUser getByUUID(UUID uuid) {
        Optional<InsiderUser> user = usersRepository.findById(uuid);
        if (user.isPresent()) {
            return user.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public InsiderUser findByUsername(String username) {
        Optional<InsiderUser> userByUsername = usersRepository.findUserByUsername(username);
        if (userByUsername.isPresent()) {
            return userByUsername.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public InsiderUser signUp(SignUpRequestModel request) {
        Optional<InsiderUser> userByUsername = usersRepository.findUserByUsername(request.username);
        if (userByUsername.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }

        InsiderUser newUser = new InsiderUser();
        newUser.setFirstName(request.firstName);
        newUser.setLastName(request.lastName);
        newUser.setPassword(passwordEncoder.encode(request.password));
        newUser.setUsername(request.username);
        if (request.isProfessor) {
            newUser.setProfPending(true);
        }
        usersRepository.save(newUser);
        return newUser;
    }

    public InsiderUser save(InsiderUser user) {
        return usersRepository.save(user);
    }

    public List<InsiderUser> getPendingProfessors() {
        return usersRepository.getAllPendingProfs();
    }
}
