package org.example.userapp.application.repository;

import org.example.userapp.application.entity.UserEntity;
import org.example.userapp.application.specifications.UserSpecifications;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        UserEntity user1 = new UserEntity();
        user1.setUuid("1uuid1");
        user1.setFirstName("1max1");
        user1.setLastName("1meier1");
        user1.setEmail("1email1");
        user1.setBirthdate(LocalDate.of(1990,1,1));

        userRepository.save(user1);

        UserEntity user2 = new UserEntity();
        user2.setUuid("2uuid2");
        user2.setFirstName("2max2");
        user2.setLastName("2meier2");
        user2.setEmail("2email2");
        user2.setBirthdate(LocalDate.of(1990,1,2));

        userRepository.save(user2);

        UserEntity user3 = new UserEntity();
        user3.setUuid("3uuid3");
        user3.setFirstName("3max3");
        user3.setLastName("3meier3");
        user3.setEmail("3email3");
        user3.setBirthdate(LocalDate.of(1990,1,3));

        userRepository.save(user3);
    }

    @Test
    void getUserByUuidTestNull() {
        // given
        // when
        UserEntity result = userRepository.findByUuidEquals(null);
        // then
        assertNull(result);
    }

    @Test
    void getUserByUuid() {
        // given
        // when
        UserEntity result = userRepository.findByUuidEquals("1uuid1");
        // then
        assertNotNull(result);
        assertEquals("1uuid1", result.getUuid());
    }

    @Test
    void deleteByUuidTestNull() {
        // given
        assertNotNull(userRepository.findByUuidEquals("1uuid1"));
        // when
        userRepository.deleteByUuidEquals("1uuid1");
        // then
        assertNull(userRepository.findByUuidEquals("1uuid1"));
    }

    @Test
    void findAllWithFiltersNull() {
        // given
        // when
        List<UserEntity> all = userRepository.findAll(UserSpecifications.withFilters(null, null, null, null));
        // then
        assertNotNull(all);
        assertEquals(3, all.size());
        assertEquals("1uuid1", all.get(0).getUuid());
        assertEquals("2uuid2", all.get(1).getUuid());
        assertEquals("3uuid3", all.get(2).getUuid());
    }

    @Test
    void findAllWithFiltersFirstNameStartsWith() {
        // given
        // when
        List<UserEntity> all = userRepository.findAll(UserSpecifications.withFilters("1ma", null, null, null));
        // then
        assertNotNull(all);
        assertEquals(1, all.size());
        assertEquals("1uuid1", all.getFirst().getUuid());
        assertEquals("1max1", all.getFirst().getFirstName());
    }

    @Test
    void findAllWithFiltersFirstNameContains() {
        // given
        // when
        List<UserEntity> all = userRepository.findAll(UserSpecifications.withFilters("ma", null, null, null));
        // then
        assertNotNull(all);
        assertEquals(3, all.size());
        assertEquals("1uuid1", all.get(0).getUuid());
        assertEquals("1max1", all.get(0).getFirstName());
        assertEquals("2uuid2", all.get(1).getUuid());
        assertEquals("2max2", all.get(1).getFirstName());
        assertEquals("3uuid3", all.get(2).getUuid());
        assertEquals("3max3", all.get(2).getFirstName());
    }

    @Test
    void findAllWithFiltersFirstNameEndsWith() {
        // given
        // when
        List<UserEntity> all = userRepository.findAll(UserSpecifications.withFilters("ax1", null, null, null));
        // then
        assertNotNull(all);
        assertEquals(1, all.size());
        assertEquals("1uuid1", all.getFirst().getUuid());
        assertEquals("1max1", all.getFirst().getFirstName());
    }

    @Test
    void findAllWithFiltersLastNameStartsWith() {
        // given
        // when
        List<UserEntity> all = userRepository.findAll(UserSpecifications.withFilters(null, "1me", null, null));
        // then
        assertNotNull(all);
        assertEquals(1, all.size());
        assertEquals("1uuid1", all.getFirst().getUuid());
        assertEquals("1meier1", all.getFirst().getLastName());
    }

    @Test
    void findAllWithFiltersLastNameContains() {
        // given
        // when
        List<UserEntity> all = userRepository.findAll(UserSpecifications.withFilters(null, "me", null, null));
        // then
        assertNotNull(all);
        assertEquals(3, all.size());
        assertEquals("1uuid1", all.get(0).getUuid());
        assertEquals("1meier1", all.get(0).getLastName());
        assertEquals("2uuid2", all.get(1).getUuid());
        assertEquals("2meier2", all.get(1).getLastName());
        assertEquals("3uuid3", all.get(2).getUuid());
        assertEquals("3meier3", all.get(2).getLastName());
    }

    @Test
    void findAllWithFiltersLastNameEndsWith() {
        // given
        // when
        List<UserEntity> all = userRepository.findAll(UserSpecifications.withFilters(null, "er1", null, null));
        // then
        assertNotNull(all);
        assertEquals(1, all.size());
        assertEquals("1uuid1", all.getFirst().getUuid());
        assertEquals("1meier1", all.getFirst().getLastName());
    }

    @Test
    void findAllWithFiltersEmailStartsWith() {
        // given
        // when
        List<UserEntity> all = userRepository.findAll(UserSpecifications.withFilters(null, null, "1em", null));
        // then
        assertNotNull(all);
        assertEquals(1, all.size());
        assertEquals("1uuid1", all.getFirst().getUuid());
        assertEquals("1email1", all.getFirst().getEmail());
    }

    @Test
    void findAllWithFiltersEmailContains() {
        // given
        // when
        List<UserEntity> all = userRepository.findAll(UserSpecifications.withFilters(null, null, "em", null));
        // then
        assertNotNull(all);
        assertEquals(3, all.size());
        assertEquals("1uuid1", all.get(0).getUuid());
        assertEquals("1email1", all.get(0).getEmail());
        assertEquals("2uuid2", all.get(1).getUuid());
        assertEquals("2email2", all.get(1).getEmail());
        assertEquals("3uuid3", all.get(2).getUuid());
        assertEquals("3email3", all.get(2).getEmail());
    }

    @Test
    void findAllWithFiltersEmailEndsWith() {
        // given
        // when
        List<UserEntity> all = userRepository.findAll(UserSpecifications.withFilters(null, null, "il1", null));
        // then
        assertNotNull(all);
        assertEquals(1, all.size());
        assertEquals("1uuid1", all.getFirst().getUuid());
        assertEquals("1email1", all.getFirst().getEmail());
    }

    @Test
    void findAllWithFiltersBirthdateEquals() {
        // given
        // when
        List<UserEntity> all = userRepository.findAll(UserSpecifications.withFilters(null, null, null, LocalDate.of(1990,1,1)));
        // then
        assertNotNull(all);
        assertEquals(1, all.size());
        assertEquals("1uuid1", all.getFirst().getUuid());
        assertEquals(LocalDate.of(1990,1,1), all.getFirst().getBirthdate());
    }

    @Test
    void findAllWithFiltersFindWithAll() {
        // given
        // when
        List<UserEntity> all = userRepository.findAll(UserSpecifications.withFilters("max1", "1meier1", "1email", LocalDate.of(1990,1,1)));
        // then
        assertNotNull(all);
        assertEquals(1, all.size());
        assertEquals("1uuid1", all.getFirst().getUuid());
        assertEquals("1max1", all.getFirst().getFirstName());
        assertEquals("1meier1", all.getFirst().getLastName());
        assertEquals("1email1", all.getFirst().getEmail());
        assertEquals(LocalDate.of(1990,1,1), all.getFirst().getBirthdate());
    }
}