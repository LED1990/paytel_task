package com.paytel.task.repository;



import com.paytel.task.model.LogsSearchCriteria;
import com.paytel.task.model.LogsSearchResult;

import java.util.Optional;

public interface LogDataCriteriaRepository {

    Optional<LogsSearchResult> searchLogsByCriteria(LogsSearchCriteria searchCriteria);
}
