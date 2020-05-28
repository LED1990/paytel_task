package com.paytel.task.repository;

import com.paytel.task.model.LogData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogDataRepository extends JpaRepository<LogData, Long>, LogDataCriteriaRepository {
}
