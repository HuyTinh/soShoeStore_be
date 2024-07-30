package com.personal.soshoestore_be.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MailSender {
    private String to;
    private String subject;
    private String content;
    private String otp;
}
