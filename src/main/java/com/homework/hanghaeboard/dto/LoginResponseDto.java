package com.homework.hanghaeboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "set")
public class LoginResponseDto {

    private String msg;
    private int statusCode;

    public static LoginResponseDto setSuccess () {
        return LoginResponseDto.set("로그인 성공", 200);
    }
    public static LoginResponseDto setFailed () { return LoginResponseDto.set("로그인 실패", 403); }
}
