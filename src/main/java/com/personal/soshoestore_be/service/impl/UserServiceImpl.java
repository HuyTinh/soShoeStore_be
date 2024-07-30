package com.personal.soshoestore_be.service.impl;

import com.personal.soshoestore_be.component.JwtTokenUtil;
import com.personal.soshoestore_be.dto.UserRegisterDTO;
import com.personal.soshoestore_be.dto.UserUpdateContactDTO;
import com.personal.soshoestore_be.dto.UserUpdatePasswordDTO;
import com.personal.soshoestore_be.dto.UserUpdateProfileDTO;
import com.personal.soshoestore_be.exception.DataNotFoundException;
import com.personal.soshoestore_be.exception.PermissionDennyException;
import com.personal.soshoestore_be.mapper.UserMapper;
import com.personal.soshoestore_be.model.Role;
import com.personal.soshoestore_be.model.User;
import com.personal.soshoestore_be.repository.RoleRepository;
import com.personal.soshoestore_be.repository.UserRepository;
import com.personal.soshoestore_be.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenUtil JwtTokenUtil;

    private final UserMapper userMapper;

    private final AuthenticationManager authenticationManager;

    @Override
    public String createUser(UserRegisterDTO userRegisterDto){
        String email = userRegisterDto.getEmail();
        if(userRepository.existsByEmail(email)){
            throw new DataIntegrityViolationException("Email already exists");
        }
        User newUser = userMapper.toEntity(userRegisterDto);

        Role role = roleRepository.findById(userRegisterDto.getRoleId()).orElseThrow(() -> new DataNotFoundException("Role not found"));
        if(role.getName().equals(Role.ADMIN)){
            throw new PermissionDennyException("Cannot create  user with role admin");
        }
        newUser.setRole(role);

        if(!userRegisterDto.getFacebookAccountId() && !userRegisterDto.getGoogleAccountId()){
            if (userRegisterDto.getPassword().equals(userRegisterDto.getRetypePassword())) {
                newUser.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
            } else {
                throw new DataIntegrityViolationException("Password and confirm password do not match");
            }
        }
        User savedUser = userRepository.save(newUser);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userRegisterDto.getEmail(), userRegisterDto.getPassword(), AuthorityUtils.createAuthorityList(Role.USER));
        authenticationManager.authenticate(authenticationToken);

        return JwtTokenUtil.generateToken(savedUser);
    }

    @Override
    public String login(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new DataNotFoundException("Invalid email or password");
        }
        User existingUser = user.get();

        if(!existingUser.getFacebookAccountId() && !existingUser.getGoogleAccountId()){
            if(!passwordEncoder.matches(password, existingUser.getPassword())){
                throw new DataIntegrityViolationException("Invalid email or password");
            }
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,password, AuthorityUtils.createAuthorityList(Role.USER));
        authenticationManager.authenticate(authenticationToken);

        return JwtTokenUtil.generateToken(existingUser);
    }

    @Override
    public String updateUserProfile(Long id, UserUpdateProfileDTO userUpdateProfileDTO) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new DataNotFoundException(String.format("User with id %d not found", id)));

        User savedUser = userRepository.save(userMapper.mapToEntity(userUpdateProfileDTO, existingUser));

        return JwtTokenUtil.generateToken(existingUser);
    }

    @Override
    public String updateUserContact(Long id, UserUpdateContactDTO userUpdateContactDTO) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new DataNotFoundException(String.format("User with id %d not found", id)));

        User savedUser = userRepository.save(userMapper.mapToEntity(userUpdateContactDTO, existingUser));

        return JwtTokenUtil.generateToken(existingUser);
    }

    @Override
    public void changePassword(Long id, UserUpdatePasswordDTO userUpdatePasswordDTO) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new DataNotFoundException(String.format("User with id %d not found", id)));

        if (userUpdatePasswordDTO.getPassword().equals(userUpdatePasswordDTO.getConfirmPassword())) {
            existingUser.setPassword(passwordEncoder.encode(userUpdatePasswordDTO.getPassword()));
        } else {
            throw new DataIntegrityViolationException("Password and confirm password do not match");
        }
        userRepository.save(existingUser);
    }

    @Override
    public User getUserByToken(String token) {
        String email = JwtTokenUtil.extractEmail(token);
        return userRepository.findByEmail(email).orElseThrow(() -> new DataNotFoundException(String.format("User with email %s not found", email)));
    }
}
