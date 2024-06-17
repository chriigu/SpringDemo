package org.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String uuid;
    private String email;
    private String firstName;
    private String lastName;

    protected UserEntity() {
    }

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
