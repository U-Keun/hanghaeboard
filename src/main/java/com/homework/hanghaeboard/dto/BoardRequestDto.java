package com.homework.hanghaeboard.dto;

import com.homework.hanghaeboard.entity.Users;
import lombok.Getter;

@Getter
public class BoardRequestDto {
    private String username;
    private String title;
    private String contents;
}
