package mz.co.kr.mzoauth2security.controller;

import mz.co.kr.mzoauth2security.entity.User;
import mz.co.kr.mzoauth2security.exception.ResourceNotFoundException;
import mz.co.kr.mzoauth2security.repository.UserRepository;
import mz.co.kr.mzoauth2security.security.model.CurrentUser;
import mz.co.kr.mzoauth2security.security.model.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }
}
