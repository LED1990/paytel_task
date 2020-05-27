package com.paytel.task.controllers;

import com.paytel.task.model.dto.LogDataDto;
import com.paytel.task.model.dto.NewLogDataDto;
import com.paytel.task.services.interfaces.LogDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/logs")
public class LogDataController {

    private final LogDataService logDataService;

    @Autowired
    public LogDataController(LogDataService logDataService) {
        this.logDataService = logDataService;
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> addNewLog(@RequestBody NewLogDataDto newLogDataDto) {
        LogDataDto logDataDto = logDataService.addNewLogEntry(newLogDataDto);
        logDataDto.add(linkTo(methodOn(LogDataController.class).getOne(logDataDto.getId())).withSelfRel());
        return new ResponseEntity<>(logDataDto, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        LogDataDto result = logDataService.getOne(id);
        result.add(linkTo(methodOn(LogDataController.class).getOne(id)).withSelfRel());
        return ResponseEntity.ok(result);
    }
}
