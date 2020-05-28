package com.paytel.task.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss:SSS")
    private Date logDate;
    private String logLevel;
    private String className;
    private String logMessage;

}
