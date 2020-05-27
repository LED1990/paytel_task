package com.paytel.task.services.interfaces;

import com.paytel.task.model.dto.LogDataDto;
import com.paytel.task.model.dto.NewLogDataDto;

public interface LogDataService {

    LogDataDto addNewLogEntry(NewLogDataDto logDataDto);
}
