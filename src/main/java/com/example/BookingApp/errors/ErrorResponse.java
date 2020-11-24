package com.example.BookingApp.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {

    private String statusCode;
    private String errorContent;
    private List<String> messages;

    public static ResponseEntity<Object> generateErrorList(BindingResult bindingResult){
        List<String> errors = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        return ResponseEntity.ok(new ErrorResponse("404", "Validation failure", errors));
    }

}
