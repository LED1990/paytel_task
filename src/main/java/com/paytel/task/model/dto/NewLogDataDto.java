package com.paytel.task.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewLogDataDto {
    private String sourceAppName;
    private String logMessage;
}
