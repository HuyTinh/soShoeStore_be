package com.personal.soshoestore_be.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class ApiException {
    private Object message;
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
    private Date date;
}
