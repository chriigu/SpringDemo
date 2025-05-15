package org.example.userapp.application.record;

import java.util.List;

public record UserSearchResultRecord (
        List<UserRecord> resultList,
        long totalElements,
        int pageNumber,
        int pageSize,
        int totalPages
){
}
