package com.woof.api.bookmark.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkCreateReq {
    private Long productSchoolIdx;
    private Long productManagerIdx;
    private Long memberIdx;
}
