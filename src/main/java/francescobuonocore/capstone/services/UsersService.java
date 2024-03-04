package francescobuonocore.capstone.services;

import francescobuonocore.capstone.entities.User;
import francescobuonocore.capstone.exceptions.BadRequestException;
import francescobuonocore.capstone.exceptions.NotFoundException;
import francescobuonocore.capstone.payloads.NewUserDTO;
import francescobuonocore.capstone.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    private UserRepository userRepository;

    public User findById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }
    public Page<User> getUsers(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return userRepository.findAll(pageable);
    }
    public User save(NewUserDTO payload) {
        userRepository.findByEmail(payload.email()).ifPresent(user -> {
            throw new BadRequestException("The email " + user.getEmail() + " has already been used");
        });
        User newUser = new User(payload.username(), payload.name(), payload.surname(), payload.email(), payload.password());
        return userRepository.save(newUser);
    }
    public User findAndUpdate(long id, User newUser) {
        User found = this.findById(id);
        found.setUsername(newUser.getUsername());
        found.setName(newUser.getName());
        found.setSurname(newUser.getSurname());
        found.setEmail(newUser.getEmail());
        found.setPassword(newUser.getPassword());
        return this.userRepository.save(found);
    }
    public void findAndDelete(long id) {
        User found = this.findById(id);
        this.userRepository.delete(found);
    }
}
