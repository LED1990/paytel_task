package com.paytel.task.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LogDataDto extends RepresentationModel<LogDataDto>{

    private Long id;
    private String sourceAppName;
    private Date logDate;
    private String logLevel;
    private String className;
    private String logMessage;

}
