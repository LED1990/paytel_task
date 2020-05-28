package com.paytel.task.services;

import com.paytel.task.exceptions.LogNotFoundException;
import com.paytel.task.model.LogData;
import com.paytel.task.model.LogsSearchCriteria;
import com.paytel.task.model.LogsSearchResult;
import com.paytel.task.model.dto.LogDataSearchResultDto;
import com.paytel.task.repository.LogDataRepository;
import com.paytel.task.services.interfaces.LogDataSearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LogDataSearchServiceImplTest {

    private LogDataSearchService logDataSearchService;

    @Mock
    private LogDataRepository logDataRepository;

    @BeforeEach
    void init() {
        logDataSearchService = new LogDataSearchServiceImpl(logDataRepository);
    }


    @Test
    void testGetLogsByCriteriaInvalidDatesShouldThrowException() {
        //given
        LogsSearchCriteria searchCriteria = new LogsSearchCriteria();
        searchCriteria.setFrom(new Date());
        searchCriteria.setTo(new Date(2019 - 1900, 1, 1));

        //when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> logDataSearchService.getLogsByCriteria(searchCriteria));

        //then
        assertTrue(exception.getMessage().equals("Invalid time range, date 'from' greater then date 'to'"));
    }

    @Test
    void testGetLogsByCriteriaInvalidPageShouldThrowException() {
        //given
        LogsSearchCriteria searchCriteria = new LogsSearchCriteria();
        searchCriteria.setPage(0);

        //when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> logDataSearchService.getLogsByCriteria(searchCriteria));

        //then
        assertTrue(exception.getMessage().equals("Result start page must be greater then 0"));
    }

    @Test
    void testGetLogsByCriteriaInvalidPageSizeShouldThrowException() {
        //given
        LogsSearchCriteria searchCriteria = new LogsSearchCriteria();
        searchCriteria.setPageSize(0);

        //when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> logDataSearchService.getLogsByCriteria(searchCriteria));

        //then
        assertTrue(exception.getMessage().equals("Page size must be greater then or equal 1"));
    }

    @Test
    void testGetLogsByCriteriaNoResultsShouldThrowException() {
        //when
        when(logDataRepository.searchLogsByCriteria(any())).thenReturn(Optional.empty());

        Exception exception = assertThrows(LogNotFoundException.class, () -> logDataSearchService.getLogsByCriteria(new LogsSearchCriteria()));

        //then
        assertTrue(exception.getMessage().equals("No results for criteria"));
    }

    @Test
    void testGetLogsByCriteria() {
        //given
        LogData logData = new LogData(1L, "App name", new Date(), "INFO", "class name", "msg");
        LogsSearchResult searchResult = new LogsSearchResult(1L, Collections.singletonList(logData));

        //when
        when(logDataRepository.searchLogsByCriteria(any())).thenReturn(Optional.of(searchResult));

        LogDataSearchResultDto result = logDataSearchService.getLogsByCriteria(new LogsSearchCriteria());

        //then
        assertTrue(result != null);
        assertTrue(result.getTotalResults() == 1);
        assertTrue(result.getLogs().size() == 1);
        assertTrue(result.getLogs().get(0).getId().equals(1L));
    }

}