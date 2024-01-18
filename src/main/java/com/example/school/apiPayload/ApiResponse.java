package com.example.school.apiPayload;

import com.example.school.apiPayload.status.SuccessStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private final Boolean isSuccess;
    private final String code;
    private final String message;
    private T result;

    public static <T> ApiResponse<T> onSuccess(T data){
        return new ApiResponse<>(true, SuccessStatus.OK.getCode(),SuccessStatus.OK.getMessage(), data);
    }

    public static <T> ApiResponse<T> onFailure(String code, String message){
        return new ApiResponse<>(false, code, message, null);
    }
}
