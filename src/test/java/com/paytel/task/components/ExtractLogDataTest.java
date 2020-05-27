package com.paytel.task.components;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ExtractLogDataTest {

    private ExtractLogData extractLogData;

    @BeforeEach
    void init() {
        extractLogData = new ExtractLogData();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "2020-05-27 12:58:29.013  INFO 17284 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 22ms. Found 0 JPA repository interfaces.",
            "2020-05-27 12:58:29.739  WARN 17284 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]",
            "2020-05-27 12:58:30,833  DEBUG 17284 --- [         task-1] o.hibernate.annotations.common.Version   : HCANN000001: Hibernate Commons Annotations {5.1.0.Final}",
            "2020-05-27 12:58:31,220  TRACE 17284 --- [           main] DeferredRepositoryInitializationListener : Triggering deferred initialization of Spring Data repositories…",
            "2020-05-27 12:58:29.013  ERROR 17284 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 22ms. Found 0 JPA repository interfaces.",
            "2020-05-27 [main] INFO com.paytel.task.TaskApplication - info log",
            "2020-05-28 12:58:30,833 [main] ERROR com.paytel.task.TaskApplication - error log",
            "2020-05-29 12:58:26.302 [main] DEBUG com.paytel.task.TaskApplication - debug log",
            "2020-05-27 [main] WARN com.paytel.task.TaskApplication - warn log"
    })
    void testExtractLogDataFromString(String log) {
        Map<String, String> result = extractLogData.extractLogDataFromString(log);

        assertTrue(result.get("date") != null);
        assertTrue(result.get("lvl") != null);
        assertTrue(result.get("msg") != null);
        assertTrue(result.get("className") != null);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "2020-05-27 12:58:29.013",
            "2020-05-27 12:58:29,739]",
            "2020-05-27",
    })
    void testConvertDate(String dateString) {
        //given
        Map<String, String> logsInfoMap = new HashMap<>();
        logsInfoMap.put(ExtractLogData.DATE_KEY, dateString);

        //when
        Date result = extractLogData.convertDate(logsInfoMap);

        //then
        assertTrue(result != null);
    }
}