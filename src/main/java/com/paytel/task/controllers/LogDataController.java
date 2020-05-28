package com.paytel.task.controllers;

import com.paytel.task.model.LogsSearchCriteria;
import com.paytel.task.model.dto.LogDataDto;
import com.paytel.task.model.dto.LogDataSearchResultDto;
import com.paytel.task.model.dto.NewLogDataDto;
import com.paytel.task.services.interfaces.LogDataSearchService;
import com.paytel.task.services.interfaces.LogDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/logs")
public class LogDataController {

    private final LogDataService logDataService;
    private final LogDataSearchService logDataSearchService;

    @Autowired
    public LogDataController(LogDataService logDataService, LogDataSearchService logDataSearchService) {
        this.logDataService = logDataService;
        this.logDataSearchService = logDataSearchService;
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> addNewLog(@RequestBody NewLogDataDto newLogDataDto) {
        LogDataDto logDataDto = logDataService.addNewLogEntry(newLogDataDto);
        logDataDto.add(linkTo(methodOn(LogDataController.class).getOne(logDataDto.getId())).withSelfRel());
        return new ResponseEntity<>(logDataDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> searchLogs(@RequestParam(required = false) String appName,
                                        @RequestParam(required = false) Integer pageNumber,
                                        @RequestParam(required = false) Integer pageSize,
                                        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:SSS") Date dateFrom,
                                        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:SSS") Date dateTo) {
        LogsSearchCriteria logsSearchCriteria = new LogsSearchCriteria();
        logsSearchCriteria.setAppName(appName);
        logsSearchCriteria.setPage(pageNumber != null ? pageNumber : 1);//default 1
        logsSearchCriteria.setPageSize(pageSize != null ? pageSize : 5);//default 5
        logsSearchCriteria.setFrom(dateFrom);
        logsSearchCriteria.setTo(dateTo);

        LogDataSearchResultDto result = logDataSearchService.getLogsByCriteria(logsSearchCriteria);
        result.getLogs().forEach(logDataDto -> logDataDto.add(linkTo(methodOn(LogDataController.class).getOne(logDataDto.getId())).withSelfRel()));

        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @GetMapping("{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        LogDataDto result = logDataService.getOne(id);
        result.add(linkTo(methodOn(LogDataController.class).getOne(id)).withSelfRel());
        return ResponseEntity.ok(result);
    }


}
