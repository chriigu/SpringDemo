package org.example.userapp.application.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.example.userapp.application.entity.UserEntity;
import org.example.userapp.application.enums.OrderDirectionEnum;
import org.example.userapp.application.enums.UserSearchOrderByEnum;
import org.example.userapp.application.record.UserSearchQueryParamsRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserSearchRepositoryImplTest {

    @Mock
    private EntityManager em;

    private CriteriaBuilder cbMock;
    private CriteriaQuery<UserEntity> queryMock;
    private TypedQuery<UserEntity> typedQueryMock;
    private Root<UserEntity> rootMock;
    private Predicate predicateMock;

    @InjectMocks
    private UserSearchRepositoryImpl userSearchRepository;


    @BeforeEach
    void setUp() {
        cbMock = mock(CriteriaBuilder.class);
        queryMock = (CriteriaQuery<UserEntity>) mock(CriteriaQuery.class);
        typedQueryMock = (TypedQuery<UserEntity>) mock(TypedQuery.class);
        rootMock = (Root<UserEntity>) mock(Root.class);
        predicateMock = mock(Predicate.class);
    }

    @Test
    void findUserEntitiesTest() {
        // given
        var input = new UserSearchQueryParamsRecord(
                "firstName",
                "lastName",
                "email",
                LocalDate.of(1990,1,1),
                UserSearchOrderByEnum.EMAIL,
                OrderDirectionEnum.ASC,
                3,
                10);

        when(em.getCriteriaBuilder()).thenReturn(cbMock);
        when(em.createQuery(queryMock)).thenReturn(typedQueryMock);
        when(cbMock.createQuery(UserEntity.class)).thenReturn(queryMock);
        when(queryMock.from(UserEntity.class)).thenReturn(rootMock);
        when(queryMock.select(rootMock)).thenReturn(queryMock);
        when(queryMock.where(ArgumentMatchers.<Predicate>any())).thenReturn(queryMock);
        when(queryMock.orderBy(ArgumentMatchers.<Order>any())).thenReturn(queryMock);
        when(typedQueryMock.setFirstResult(anyInt())).thenReturn(typedQueryMock);
        when(typedQueryMock.setMaxResults(anyInt())).thenReturn(typedQueryMock);
        when(typedQueryMock.getResultList()).thenReturn(createTestUserResult());

        // when
        List<UserEntity> userEntities = userSearchRepository.findUserEntities(input);

        // then
        assertNotNull(userEntities);
        assertEquals(2, userEntities.size());

        assertNotNull(userEntities.getFirst());
        assertEquals(0L, userEntities.getFirst().getId());
        assertEquals("uuid1", userEntities.getFirst().getUuid());
        assertEquals("f1", userEntities.getFirst().getFirstName());
        assertEquals("l1", userEntities.getFirst().getLastName());
        assertEquals("email1", userEntities.getFirst().getEmail());
        assertEquals(LocalDate.of(1990,1,1), userEntities.getFirst().getBirthdate());
        assertEquals(LocalDateTime.of(2000,1,1,12,0,0), userEntities.getFirst().getCreatedTs());
        assertEquals(LocalDateTime.of(2000,1,1,12,0,1), userEntities.getFirst().getLastUpdatedTs());

        assertNotNull(userEntities.getLast());
        assertEquals(1L, userEntities.getLast().getId());
        assertEquals("uuid2", userEntities.getLast().getUuid());
        assertEquals("f2", userEntities.getLast().getFirstName());
        assertEquals("l2", userEntities.getLast().getLastName());
        assertEquals("email2", userEntities.getLast().getEmail());
        assertEquals(LocalDate.of(1991,2,2), userEntities.getLast().getBirthdate());
        assertEquals(LocalDateTime.of(2000,1,1,12,0,2), userEntities.getLast().getCreatedTs());
        assertEquals(LocalDateTime.of(2000,1,1,12,0,3), userEntities.getLast().getLastUpdatedTs());



        verify(em, times(1)).getCriteriaBuilder();
        verify(em, times(1)).createQuery(eq(queryMock));

        verify(queryMock, times(1)).select(eq(rootMock));
        verify(queryMock, times(1)).where(ArgumentMatchers.<Predicate>any());

        verify(typedQueryMock, times(1)).setFirstResult(eq(20));
        verify(typedQueryMock, times(1)).setMaxResults(eq(10));
        verify(typedQueryMock, times(1)).getResultList();
    }

    @Test
    void setupPredicatesFirstNameOnlyTest() {
        // given
        when(cbMock.like(ArgumentMatchers.<Path<String>>any(), anyString())).thenReturn(predicateMock);
        when(queryMock.from(UserEntity.class)).thenReturn(rootMock);
        when(queryMock.select(rootMock)).thenReturn(queryMock);


        // when
//        userSearchRepository.setupPredicates("firstName", null, null)

        // then
    }

    @Test
    void setupPredicatesLastNameOnlyTest() {
        // given
        // when
        // then
    }

    @Test
    void setupPredicatesEmailOnlyTest() {
        // given
        // when
        // then
    }

    @Test
    void setupPredicatesBirthdateOnlyTest() {
        // given
        // when
        // then
    }

    @Test
    void setupPredicatesStringsOnlyTest() {
        // given
        // when
        // then
    }

    @Test
    void setupPredicatesAllValuesTest() {
        // given
        // when
        // then
    }

    @Test
    void setupPredicatesNoValuesTest() {
        // given
        // when
        // then
    }

    @Test
    void getOrder() {
        // given
        // when
        // then
    }

    @Test
    void getStartIndex() {
    }

    private List<UserEntity> createTestUserResult() {
        UserEntity userEntity1 = new UserEntity();
        userEntity1.setId(0L);
        userEntity1.setUuid("uuid1");
        userEntity1.setEmail("email1");
        userEntity1.setFirstName("f1");
        userEntity1.setLastName("l1");
        userEntity1.setBirthdate(LocalDate.of(1990,1,1));
        userEntity1.setCreatedTs(LocalDateTime.of(2000,1,1, 12,0,0));
        userEntity1.setLastUpdatedTs(LocalDateTime.of(2000,1,1, 12,0,1));

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setId(1L);
        userEntity2.setUuid("uuid2");
        userEntity2.setEmail("email2");
        userEntity2.setFirstName("f2");
        userEntity2.setLastName("l2");
        userEntity2.setBirthdate(LocalDate.of(1991,2,2));
        userEntity2.setCreatedTs(LocalDateTime.of(2000,1,1, 12,0,2));
        userEntity2.setLastUpdatedTs(LocalDateTime.of(2000,1,1, 12,0,3));

        return List.of(userEntity1, userEntity2);
    }
}