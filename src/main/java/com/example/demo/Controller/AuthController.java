package com.example.demo.Controller;


import com.example.demo.model.User;
import com.example.demo.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController
{
    private final UserService userService;

    public AuthController(UserService userService)
    {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user, @RequestParam String role)
    {
        try{
            userService.saveUser(user,role);
            return ResponseEntity.ok("{\"message\": \"User registered successfully\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}
