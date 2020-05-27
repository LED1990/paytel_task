package com.paytel.task.model.dto;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogDataDto {

    private String sourceAppName;
    private Date logDate;
    private String logLevel;
    private String className;
    private String logMessage;

}
