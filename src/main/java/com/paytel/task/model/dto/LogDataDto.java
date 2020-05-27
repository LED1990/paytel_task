package com.paytel.task.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogDataDto {

    @JsonIgnore
    private Long id;
    private String sourceAppName;
    private Date logDate;
    private String logLevel;
    private String className;
    private String logMessage;

}
