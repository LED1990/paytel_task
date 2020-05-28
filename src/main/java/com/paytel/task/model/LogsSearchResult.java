package com.paytel.task.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class LogsSearchResult {
    private Long totalResults;
    private List<LogData> results;
}
