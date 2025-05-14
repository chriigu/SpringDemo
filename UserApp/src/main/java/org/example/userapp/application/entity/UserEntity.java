package org.example.userapp.application.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Table(name = "USERS", schema = "springdemo")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "UUID")
    private String uuid;
    @Column(name = "FIRST_NAME")
    private String email;
    @Column(name = "LAST_NAME")
    private String firstName;
    @Column(name = "EMAIL")
    private String lastName;
    @Column(name = "BIRTHDATE")
    private LocalDate birthdate;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_TS")
    private LocalDateTime createdTs;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_UPDATED_TS")
    private LocalDateTime lastUpdatedTs;

    public UserEntity() {}

    public UserEntity(final String uuid, final String email, final String firstName, final String lastName, final LocalDate birthdate) {
        this.uuid = uuid;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
    }

    @Override
    public String toString() {
        return "UserEntity{id=%d, uuid='%s', email='%s', firstName='%s', lastName='%s', birthdate='%s'}".formatted(id, uuid, email, firstName, lastName, birthdate);
    }
}
