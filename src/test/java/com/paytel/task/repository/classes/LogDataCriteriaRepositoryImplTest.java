package com.paytel.task.repository.classes;

import com.paytel.task.config.DbTestConfig;
import com.paytel.task.model.LogsSearchCriteria;
import com.paytel.task.model.LogsSearchResult;
import com.paytel.task.repository.LogDataCriteriaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ContextConfiguration(classes = DbTestConfig.class, loader = AnnotationConfigContextLoader.class)
@DataJpaTest
class LogDataCriteriaRepositoryImplTest {

    @Resource
    @Qualifier("logDataCriteriaRepositoryImpl")
    private LogDataCriteriaRepository logDataCriteriaRepository;

    @ParameterizedTest
    @ValueSource(strings = {
            "App2", "App1"
    })
    void testSearchLogsByCriteriaShouldReturnLogsByAppName(String appName) {
        //given
        LogsSearchCriteria logsSearchCriteria = new LogsSearchCriteria();
        logsSearchCriteria.setAppName(appName);

        //when
        Optional<LogsSearchResult> result = logDataCriteriaRepository.searchLogsByCriteria(logsSearchCriteria);

        //then
        result.ifPresent(logsSearchResult -> logsSearchResult.getResults().forEach(logData -> {
            assertTrue(logData.getSourceAppName().equals(appName));
        }));
    }


    @ParameterizedTest
    @ValueSource(ints = {
            3, 5, 10
    })
    void testSearchLogsByCriteriaShouldReturnPageSize(int pageSize) {
        //given
        LogsSearchCriteria logsSearchCriteria = new LogsSearchCriteria();
        logsSearchCriteria.setPageSize(pageSize);

        //when
        Optional<LogsSearchResult> result = logDataCriteriaRepository.searchLogsByCriteria(logsSearchCriteria);

        //then
        result.ifPresent(logsSearchResult -> assertTrue(logsSearchResult.getResults().size() == pageSize));
    }

    @Test
    void testSearchLogsByCriteriaShouldReturnLogsBeforeDate() {
        //given
        LogsSearchCriteria logsSearchCriteria = new LogsSearchCriteria();
        logsSearchCriteria.setTo(new Date());

        //when
        Optional<LogsSearchResult> result = logDataCriteriaRepository.searchLogsByCriteria(logsSearchCriteria);

        //then
        result.ifPresent(logsSearchResult -> logsSearchResult.getResults().forEach(logData -> {
            assertTrue(logData.getLogDate().before(new Date()));
        }));
    }

    @Test
    void testSearchLogsByCriteriaShouldReturnLogsAfterDate() {
        //given
        LogsSearchCriteria logsSearchCriteria = new LogsSearchCriteria();
        logsSearchCriteria.setFrom(new Date());

        //when
        Optional<LogsSearchResult> result = logDataCriteriaRepository.searchLogsByCriteria(logsSearchCriteria);

        //then
        result.ifPresent(logsSearchResult -> logsSearchResult.getResults().forEach(logData -> {
            assertTrue(logData.getLogDate().after(new Date()));
        }));
    }

    @Test
    void testSearchLogsByCriteriaShouldReturnLogsFromDateRange() {
        //given
        LogsSearchCriteria logsSearchCriteria = new LogsSearchCriteria();
        logsSearchCriteria.setFrom(new Date());

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, 10);
        logsSearchCriteria.setTo(c.getTime());

        //when
        Optional<LogsSearchResult> result = logDataCriteriaRepository.searchLogsByCriteria(logsSearchCriteria);

        //then
        result.ifPresent(logsSearchResult -> logsSearchResult.getResults().forEach(logData -> {
            assertTrue(logData.getLogDate().after(new Date()) && logData.getLogDate().before(c.getTime()));
        }));
    }

}