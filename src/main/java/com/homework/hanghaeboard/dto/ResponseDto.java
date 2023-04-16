package com.homework.hanghaeboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "set")
public class ResponseDto<D> {
    private boolean success;
    private D data;

    public static <D> ResponseDto setSuccess (D data) {
        return ResponseDto.set(true, data);
    }


    public static <D> ResponseDto<D> setFailed () { return ResponseDto.set(false, null); }


}
