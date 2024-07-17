package com.woof.api.member.model.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatchMemberCancelRes {

    private Boolean status;
    private LocalDateTime updatedAt;
}