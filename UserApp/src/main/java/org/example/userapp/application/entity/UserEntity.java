package org.example.userapp.application.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Getter
@Setter
@Table(name = "USERS", schema = "springdemo")
public class UserEntity {

    public static final String UUID_COLUMN_ID = "UUID";
    public static final String FIRST_NAME_COLUMN_ID = "FIRST_NAME";
    public static final String LAST_NAME_COLUMN_ID = "LAST_NAME";
    public static final String EMAIL_COLUMN_ID = "EMAIL";
    public static final String BIRTHDATE_COLUMN_ID = "BIRTHDATE";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = UUID_COLUMN_ID)
    private String uuid;
    @Column(name = FIRST_NAME_COLUMN_ID)
    private String email;
    @Column(name = LAST_NAME_COLUMN_ID)
    private String firstName;
    @Column(name = EMAIL_COLUMN_ID)
    private String lastName;
    @Column(name = BIRTHDATE_COLUMN_ID)
    private LocalDate birthdate;

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
