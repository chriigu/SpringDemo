package org.example.userapp.application.mapper.api;

import org.example.userapp.application.enums.OrderDirectionEnum;
import org.example.userapp.application.enums.UserSearchOrderByEnum;
import org.example.userapp.application.openapi.model.*;
import org.example.userapp.application.record.CreateUserRequestRecord;
import org.example.userapp.application.record.UpdateUserRequestRecord;
import org.example.userapp.application.record.UserRecord;
import org.example.userapp.application.record.UserSearchResultRecord;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class APIMapper {


    public OAUserDto mapUserRecordToOAUserDto (final UserRecord userRecord) {
        OAUserDto result = new OAUserDto();
        result.setUuid(userRecord.uuid());
        result.setEmail(userRecord.email());
        result.setFirstName(userRecord.firstName());
        result.setLastName(userRecord.lastName());
        result.setBirthdate(userRecord.birthdate());
        return result;
    }

    public OAUserSearchResult mapUserSearchResultRecordToOAUserSearchResult(final UserSearchResultRecord userSearchResultRecord) {
        OAUserSearchResult result = new OAUserSearchResult();
        result.setResultList(userSearchResultRecord.resultList().stream().map(this::mapUserRecordToOAUserDto).toList());
        result.setTotalResults((int) userSearchResultRecord.totalElements());
        result.setTotalPages(userSearchResultRecord.totalPages());
        result.setPageNumber(userSearchResultRecord.pageNumber());
        result.setPageSize(userSearchResultRecord.pageSize());

        return result;
    }

    public UserSearchOrderByEnum mapOAUserSearchOrderEnumToUserSearchOrderByEnum(final OAUserSearchOrderEnum userSearchOrderEnum) {
        switch (userSearchOrderEnum) {
            case FIRST_NAME -> {
                return UserSearchOrderByEnum.FIRST_NAME;
            }
            case LAST_NAME -> {
                return UserSearchOrderByEnum.LAST_NAME;
            }
            case EMAIL -> {
                return UserSearchOrderByEnum.EMAIL;
            }
            case BIRTHDATE -> {
                return UserSearchOrderByEnum.BIRTHDATE;
            }
        }
        throw new IllegalArgumentException("Invalid userSearchOrderEnum: " + userSearchOrderEnum);
    }

    public OrderDirectionEnum mapOAOrderDirectionEnumToOrderDirectionEnum(final OAOrderDirectionEnum orderDirectionEnum) {
        if (Objects.requireNonNull(orderDirectionEnum) == OAOrderDirectionEnum.ASC) {
            return OrderDirectionEnum.ASC;
        } else if (orderDirectionEnum == OAOrderDirectionEnum.DESC) {
            return OrderDirectionEnum.DESC;
        }
        throw new IllegalArgumentException("Invalid orderDirectionEnum: " + orderDirectionEnum);
    }

    public CreateUserRequestRecord mapOACreateUserRequestToCreateUserRequestRecord(final OACreateUserRequest request) {
        return new CreateUserRequestRecord(request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getBirthdate());
    }

    public UpdateUserRequestRecord mapOAUpdateUserRequestToUpdateUserRequestRecord(final OAUpdateUserRequest request) {
        return new UpdateUserRequestRecord(request.getFirstName(),
                request.getLastName(),
                request.getEmail());
    }
}
