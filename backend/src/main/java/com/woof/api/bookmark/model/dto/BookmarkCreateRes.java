package com.woof.api.bookmark.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkCreateRes {

    private Long bookmarkIdx;
    private Long memberIdx;
    private Long productSchoolIdx;
    private Long productManagerIdx;
}
