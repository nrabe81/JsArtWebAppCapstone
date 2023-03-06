package com.ecommerce.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDto
{
    @Size(min = 2, max = 20, message = "Invalid first name (2-20 characters)")
    private String firstName;

    @Size(min = 2, max = 20, message = "Invalid last name (2-20 characters)")
    private String lastName;

    private String username;

    @Size(min = 8, max = 20, message = "Invalid password (8-20 characters)")
    private String password;

    private String repeatPassword;
}
