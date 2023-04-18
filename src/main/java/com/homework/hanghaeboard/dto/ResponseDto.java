package com.homework.hanghaeboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "set")
public class ResponseDto<D> {
    private boolean success;
    private String message;
    private int statusCode;
    private D data;

    public static <D> ResponseDto setSuccess (String message, D data) {
        return ResponseDto.set(true, message, 200, data);
    }

    public static <D> ResponseDto<D> setFailed (String message, int statusCode) {
        return ResponseDto.set(false, message, statusCode, null); }
}
