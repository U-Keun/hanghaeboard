package com.homework.hanghaeboard.dto;

import com.homework.hanghaeboard.entity.User;
import lombok.Getter;

@Getter
public class BoardRequestDto {
    private User user;
    private String title;
    private String contents;
}
