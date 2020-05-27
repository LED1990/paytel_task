package com.paytel.task.mappers;

import com.paytel.task.model.LogData;
import com.paytel.task.model.dto.LogDataDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LogDataMapper {

    LogDataMapper INSTANCE = Mappers.getMapper(LogDataMapper.class);

    LogDataDto logDataToDto(LogData logData);
}
