package fun.liljaksha.blog.controller;

import fun.liljaksha.blog.exceptions.*;
import fun.liljaksha.blog.model.User;
import fun.liljaksha.blog.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String signupProcess(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        user.setRole("ROLE_USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            userService.registerNewUser(user);
            redirectAttributes.addFlashAttribute("success", "Sign up successful! Please log in.");
            redirectAttributes.addAttribute("success", "Sign up successful! Please log in.");
            return "redirect:/login";  // Redirect to login page with success message
        } catch (UserAlreadyExistsException e) {
            redirectAttributes.addFlashAttribute("error", "A user with this email already exists.");
            return "redirect:/signup";  // Redirect back to signup page with error message
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "An error occurred during sign up. Please try again.");
            return "redirect:/signup";  // Redirect back to signup page with general error message
        }
    }
}