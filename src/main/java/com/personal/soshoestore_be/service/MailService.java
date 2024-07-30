package com.personal.soshoestore_be.service;

import com.personal.soshoestore_be.exception.DataNotFoundException;
import com.personal.soshoestore_be.model.MailSender;
import com.personal.soshoestore_be.model.User;
import com.personal.soshoestore_be.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;

    private final UserRepository userRepository;

    @Value("${spring.mail.username}")
    private String from;

    public Integer sendOtp(Long userId) {
        User exsitingUser = userRepository.findById(userId).orElseThrow(
                () -> new DataNotFoundException(String.format("User with id %d not found", userId))
        );

        SimpleMailMessage message = new SimpleMailMessage();
        int otp = otpGenerator();

        message.setFrom(from);
        message.setTo(exsitingUser.getEmail());
        message.setSubject("Verify OTP");
        message.setText("This is the OTP for your Forgot Password request: " + otp);

        javaMailSender.send(message);
        return otp;
    }

    private Integer otpGenerator() {
        Random random = new Random();
        return random.nextInt(100_000,999_999);
    }
}
