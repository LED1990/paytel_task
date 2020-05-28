package com.paytel.task.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class LogsSearchCriteria {

    private Date from;
    private Date to;
    private String appName;

    private int page = 1; //default
    private int pageSize = 5; // default
}
