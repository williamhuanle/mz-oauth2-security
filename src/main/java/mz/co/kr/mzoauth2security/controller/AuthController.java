package mz.co.kr.mzoauth2security.controller;

import mz.co.kr.mzoauth2security.entity.User;
import mz.co.kr.mzoauth2security.enums.OAuth2Provider;
import mz.co.kr.mzoauth2security.exception.BadRequestException;
import mz.co.kr.mzoauth2security.payload.ApiResponse;
import mz.co.kr.mzoauth2security.payload.AuthResponse;
import mz.co.kr.mzoauth2security.payload.LoginRequest;
import mz.co.kr.mzoauth2security.payload.SignUpRequest;
import mz.co.kr.mzoauth2security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Autowired
//    private TokenProvider tokenProvider;

    @PostMapping("/login")
    public AuthResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //TODO String token = tokenProvider.createToken(authentication);
        return new AuthResponse("token", "Bearer");
    }

    @PostMapping("/signup")
    public ApiResponse registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email address already in use.");
        }

        // Creating user's account
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setProvider(OAuth2Provider.local);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.getId()).toUri();

        return new ApiResponse(true, "User registered successfully!");
    }

}
