package com.woof.api.bookmark.model.dto;

import lombok.Builder;
import lombok.Data;
import org.intellij.lang.annotations.Pattern;
import org.jetbrains.annotations.NotNull;

@Builder
@Data
public class BookmarkListRes {
    private Long bookmarkIdx;
    private Long memberIdx;
    private Long productSchoolIdx;
    private Long productManagerIdx;

    @NotNull
//  @Pattern(regexp = "[가-힣0-9]{2,5}|[a-zA-Z]{2,10}\\s[a-zA-Z]{2,10}$")
    private String productSchoolName;
    @NotNull
//  @Pattern(regexp = "[가-힣0-9]{2,5}|[a-zA-Z]{2,10}\\s[a-zA-Z]{2,10}$")
    private String productMangerName;
}
