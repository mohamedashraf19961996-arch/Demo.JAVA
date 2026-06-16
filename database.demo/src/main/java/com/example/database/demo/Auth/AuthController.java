package com.example.database.demo.Auth;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import com.example.database.demo.RefreshToken;
import com.example.database.demo.Entity.Role;
import com.example.database.demo.Entity.User;
import com.example.database.demo.Security.JwtService;
import com.example.database.demo.Security.RefreshTokenService;
import com.example.database.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtService jwtService,
                          RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Email already exists";
        }
    User user = new User();

    user.setEmail(request.getEmail());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setRole(Role.USER);

    userRepository.save(user);

        return "User registered successfully";
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String accessToken = jwtService.generateToken(user.getEmail());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getEmail());

        return new AuthResponse(accessToken, refreshToken.getToken());
    }
@PostMapping("/refresh-token")
public AuthResponse refreshToken(@RequestBody RefreshTokenRequest request) {

    RefreshToken refreshToken =
            refreshTokenService.verifyRefreshToken(request.getRefreshToken());

    String newAccessToken =
            jwtService.generateToken(refreshToken.getEmail());

    return new AuthResponse(newAccessToken, refreshToken.getToken());
}

@PostMapping("/logout")
public String logout(@RequestBody LogoutRequest request) {

    refreshTokenService.logout(
            request.getRefreshToken()
    );

    return "Logged out successfully";
}


}