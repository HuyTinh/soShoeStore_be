package com.personal.soshoestore_be.controller;

import com.personal.soshoestore_be.dto.*;
import com.personal.soshoestore_be.mapper.UserMapper;
import com.personal.soshoestore_be.service.MailService;
import com.personal.soshoestore_be.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    private final MailService mailService;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(
            @Valid @RequestBody UserRegisterDTO userRegisterDTO) {
            String token = userService.createUser(userRegisterDTO);
            return ResponseEntity.ok(Map.of(
                    "jwt", token
            ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody UserLoginDTO userLoginDto) {
            String token = userService.login(userLoginDto.getEmail(), userLoginDto.getPassword());
            return ResponseEntity.ok(Map.of(
                    "jwt", token
            ));
    }

    @PutMapping("/profile/{userId}")
    public ResponseEntity<?> updateProfile(@PathVariable("userId") Long userId,
                                           @Valid @RequestBody UserUpdateProfileDTO userUpdateProfileDTO)
    {
        String token = userService.updateUserProfile(userId,userUpdateProfileDTO);
        return ResponseEntity.ok(Map.of(
                "jwt", token));
    }

    @PutMapping("/contact/{userId}")
    public ResponseEntity<?> updateContact(@PathVariable("userId") Long userId,
                                           @Valid @RequestBody UserUpdateContactDTO userUpdateContactDTO)
    {
        String token = userService.updateUserContact(userId,userUpdateContactDTO);
        return ResponseEntity.ok(Map.of(
                "jwt", token));
    }

    @GetMapping("/profile/{token}")
    public ResponseEntity<?> getProfile(@PathVariable("token") String token){
        return ResponseEntity.ok(userMapper.toResponse(userService.getUserByToken(token)));
    }


    @PutMapping("/change-password/{userId}")
    public ResponseEntity<?> changePassword(@PathVariable("userId") Long userId,
                                            @Valid @RequestBody UserUpdatePasswordDTO userUpdatePasswordDTO)
    {
        userService.changePassword(userId , userUpdatePasswordDTO);
        return ResponseEntity.ok("Change password successfully!");
    }

    @GetMapping("/send-otp/{userId}")
    public ResponseEntity<?> sendOtp(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(mailService.sendOtp(userId));
    }
}
