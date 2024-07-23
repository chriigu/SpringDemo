package org.example.application.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "USERS", schema = "SPRINGDEMO")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "UUID")
    private String uuid;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;

    public UserEntity() {}

    public UserEntity(String uuid, String email, String firstName, String lastName) {
        this.uuid = uuid;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "UserEntity{id=%d, uuid='%s', email='%s', firstName='%s', lastName='%s'}".formatted(id, uuid, email, firstName, lastName);
    }
}
