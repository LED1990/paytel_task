package com.paytel.task.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogDataSearchResultDto {
    private Long totalResults;
    private List<LogDataDto> logs;
}
