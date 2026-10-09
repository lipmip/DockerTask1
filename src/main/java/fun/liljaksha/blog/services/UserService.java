package fun.liljaksha.blog.services;

import fun.liljaksha.blog.model.User;
import fun.liljaksha.blog.repository.UserRepository;
import fun.liljaksha.blog.exceptions.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    final UserRepository userRepository;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public void registerNewUser(User user) throws UserAlreadyExistsException {
        if (userExists(user.getEmail())) {
            throw new UserAlreadyExistsException("User already exists");
        }
        userRepository.save(user);
    }

    private boolean userExists(String username) {
        Optional<User> user = userRepository.findByEmail(username);
        return user.isPresent();
    }
}
