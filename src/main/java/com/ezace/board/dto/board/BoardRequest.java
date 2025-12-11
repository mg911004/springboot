package com.ezace.board.dto.board;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED , force = true)
@AllArgsConstructor

public class BoardRequest {

    @NotBlank
    @Size(max = 255)
    private final String subject;

    @NotBlank
    private final String content;
}
