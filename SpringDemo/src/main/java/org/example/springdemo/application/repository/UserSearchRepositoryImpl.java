package org.example.springdemo.application.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.example.springdemo.application.entity.UserEntity;
import org.example.springdemo.application.enums.OrderDirectionEnum;
import org.example.springdemo.application.enums.UserSearchOrderByEnum;
import org.example.springdemo.application.validators.ParamValidator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserSearchRepositoryImpl implements UserSearchRepository{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<UserEntity> findUserEntities(String firstName, String lastName, String email, LocalDate birthdate
    , UserSearchOrderByEnum orderBy, OrderDirectionEnum orderDirection, int page, int limit) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = cb.createQuery(UserEntity.class);
        Root<UserEntity> root = query.from(UserEntity.class);

        Path<String> firstNamePath = root.get("firstName");
        Path<String> lastNamePath = root.get("lastName");
        Path<String> emailPath = root.get("email");
        Path<String> birthdatePath = root.get("birthdate");

        List<Predicate> predicates = new ArrayList<>();

        addPredicateIfNotEmpty(cb, cb.like(firstNamePath, "%" + firstName + "%"), firstName);
        addPredicateIfNotEmpty(cb, cb.like(lastNamePath, "%" + lastName + "%"), lastName);
        addPredicateIfNotEmpty(cb, cb.like(emailPath, "%" + email + "%"), email);

        if (birthdate != null) {
            predicates.add(cb.equal(birthdatePath, birthdate));
        }

        Order order;
        if(orderDirection == OrderDirectionEnum.DESC) {
            order = cb.desc(root.get(orderBy.getEnumName()));
        } else {
            order = cb.asc(root.get(orderBy.getEnumName()));
        }
        int startIndex = (page - 1) * limit;

        query.select(root).where(cb.and(predicates.toArray(new Predicate[0]))).orderBy(order);

        return em.createQuery(query).setFirstResult(startIndex).setMaxResults(limit).getResultList();
    }

    private void addPredicateIfNotEmpty(CriteriaBuilder cb, Predicate predicate, String param) {
        if(ParamValidator.isNotNullAndNotBlank(param)) {
            cb.and(predicate);
        }
    }
}
