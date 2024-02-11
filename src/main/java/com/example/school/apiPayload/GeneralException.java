package com.example.school.apiPayload;

import com.example.school.apiPayload.status.ErrorStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class GeneralException extends RuntimeException{
    private ErrorStatus errorStatus;
}
