package org.example.userapp.application.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.example.userapp.application.entity.UserEntity;
import org.example.userapp.application.enums.OrderDirectionEnum;
import org.example.userapp.application.enums.UserSearchOrderByEnum;
import org.example.userapp.application.record.UserSearchQueryParamsRecord;
import org.example.userapp.application.validators.ParamValidator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserSearchRepositoryImpl implements UserSearchRepository{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<UserEntity> findUserEntities(final UserSearchQueryParamsRecord userSearchQueryParamsDto) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = cb.createQuery(UserEntity.class);
        Root<UserEntity> root = query.from(UserEntity.class);

        Path<String> firstNamePath = root.get("firstName");
        Path<String> lastNamePath = root.get("lastName");
        Path<String> emailPath = root.get("email");
        Path<String> birthdatePath = root.get("birthdate");

        List<Predicate> predicates = setupPredicates(userSearchQueryParamsDto.firstName(), userSearchQueryParamsDto.lastName(), userSearchQueryParamsDto.email(), userSearchQueryParamsDto.birthdate(), cb, firstNamePath, lastNamePath, emailPath, birthdatePath);

        Order order = getOrder(userSearchQueryParamsDto.orderBy(), userSearchQueryParamsDto.orderDirection(), cb, root);

        int startIndex = getStartIndex(userSearchQueryParamsDto.page(), userSearchQueryParamsDto.limit());
        query.select(root).where(cb.and(predicates.toArray(new Predicate[0]))).orderBy(order);

        return em.createQuery(query).setFirstResult(startIndex).setMaxResults(userSearchQueryParamsDto.limit()).getResultList();
    }

    List<Predicate> setupPredicates(String firstName, String lastName, String email, LocalDate birthdate, CriteriaBuilder cb, Path<String> firstNamePath, Path<String> lastNamePath, Path<String> emailPath, Path<String> birthdatePath) {
        List<Predicate> predicates = new ArrayList<>();

        addPredicateIfNotEmpty(cb, cb.like(firstNamePath, "%" + firstName + "%"), firstName);
        addPredicateIfNotEmpty(cb, cb.like(lastNamePath, "%" + lastName + "%"), lastName);
        addPredicateIfNotEmpty(cb, cb.like(emailPath, "%" + email + "%"), email);

        if (birthdate != null) {
            predicates.add(cb.equal(birthdatePath, birthdate));
        }

        return predicates;
    }

    private void addPredicateIfNotEmpty(CriteriaBuilder cb, Predicate predicate, String param) {
        if(ParamValidator.isNotNullAndNotBlank(param)) {
            cb.and(predicate);
        }
    }

    Order getOrder(UserSearchOrderByEnum orderBy, OrderDirectionEnum orderDirection, CriteriaBuilder cb, Root<UserEntity> root) {
        Order order;
        if(orderDirection == OrderDirectionEnum.DESC) {
            order = cb.desc(root.get(orderBy.getEnumName()));
        } else {
            order = cb.asc(root.get(orderBy.getEnumName()));
        }
        return order;
    }

    int getStartIndex(int page, int limit) {
        page -= 1;
        if(page < 0) {
            page = 0;
        }
        int startIndex = page * limit;
        return startIndex;
    }
}
