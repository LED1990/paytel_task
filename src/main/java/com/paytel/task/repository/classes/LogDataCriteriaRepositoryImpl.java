package com.paytel.task.repository.classes;

import com.paytel.task.model.LogData;
import com.paytel.task.model.LogData_;
import com.paytel.task.model.LogsSearchCriteria;
import com.paytel.task.model.LogsSearchResult;
import com.paytel.task.repository.LogDataCriteriaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class LogDataCriteriaRepositoryImpl implements LogDataCriteriaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<LogsSearchResult> searchLogsByCriteria(LogsSearchCriteria searchCriteria) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<LogData> criteriaQuery = criteriaBuilder.createQuery(LogData.class);

        Root<LogData> root = criteriaQuery.from(LogData.class);

        List<Predicate> predicates = new ArrayList<>();

        prepareQueryData(root, searchCriteria, predicates, criteriaBuilder);

        Predicate finalPredicate;
        if (predicates.size() != 0) {
            finalPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
            criteriaQuery.select(root).where(finalPredicate).distinct(true);
        } else {
            criteriaQuery.select(root).distinct(true);
        }

        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(LogData_.LOG_DATE)));

        TypedQuery<LogData> query = entityManager.createQuery(criteriaQuery);

        query.setFirstResult((searchCriteria.getPage() - 1) * searchCriteria.getPageSize());
        query.setMaxResults(searchCriteria.getPageSize());


        Long totalCount = countResults(searchCriteria);

        LogsSearchResult results = new LogsSearchResult(totalCount, query.getResultList());

        return Optional.of(results);
    }

    private Long countResults(LogsSearchCriteria evaluationSearchCriteria) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        Root<LogData> root = countQuery.from(LogData.class);
        Expression<Long> countExpression = builder.countDistinct(root);

        List<Predicate> predicates = new ArrayList<>();
        prepareQueryData(root, evaluationSearchCriteria, predicates, builder);

        if (predicates.size() == 0) {
            countQuery.select(countExpression);
        } else {
            Predicate finalPredicate = builder.and(predicates.toArray(new Predicate[]{}));
            countQuery.select(countExpression).where(finalPredicate);
        }
        TypedQuery<Long> typedStudentQuery = entityManager.createQuery(countQuery);

        return typedStudentQuery.getSingleResult();
    }

    private void prepareQueryData(Root<LogData> root, LogsSearchCriteria searchCriteria, List<Predicate> predicates, CriteriaBuilder criteriaBuilder) {

        if (!StringUtils.isEmpty(searchCriteria.getAppName())) {
            predicates.add(criteriaBuilder.like(root.get(LogData_.SOURCE_APP_NAME), searchCriteria.getAppName()));
        }

        if (searchCriteria.getFrom() == null && searchCriteria.getTo() != null) {
            //search with only to date
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(LogData_.LOG_DATE), searchCriteria.getTo()));
        }

        if (searchCriteria.getFrom() != null && searchCriteria.getTo() == null) {
            //search with only from date
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(LogData_.LOG_DATE), searchCriteria.getFrom()));
        }

        if (searchCriteria.getFrom() != null && searchCriteria.getTo() != null) {
            //search in range
            predicates.add(criteriaBuilder.between(root.get(LogData_.LOG_DATE), searchCriteria.getFrom(), searchCriteria.getTo()));
        }
    }
}
