package org.example.userapp.application.specifications;

import jakarta.persistence.criteria.Predicate;
import org.example.userapp.application.entity.UserEntity;
import org.example.userapp.application.entity.UserEntity_;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserSpecifications {

    public static Specification<UserEntity> withFilters(String firstName, String lastName, String email, LocalDate birthdate) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (firstName != null && !firstName.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get(UserEntity_.firstName)), "%" + firstName.toLowerCase() + "%"));
            }

            if (lastName != null && !lastName.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get(UserEntity_.lastName)), "%" + lastName.toLowerCase() + "%"));
            }

            if (email != null && !email.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get(UserEntity_.email)), "%" + email.toLowerCase() + "%"));
            }

            if (birthdate != null) {
                predicates.add(cb.equal(root.get(UserEntity_.birthdate), birthdate));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
