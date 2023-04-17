package com.homework.hanghaeboard.dto;

import lombok.Getter;

@Getter
public class BoardRequestDto {
    private String username;
    private String password;
    private String title;
    private String contents;
}
