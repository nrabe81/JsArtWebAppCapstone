package com.ecommerce.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto
{
    @Size(min = 2, max = 20, message = "First name should have 2-20 characters")
    private String firstName;

    @Size(min = 2, max = 20, message = "Last name should have 2-20 characters")
    private String lastName;

    private String username;

    @Size(min = 8, max = 20, message = "Password should have 8-20 characters")
    private String password;

    private String repeatPassword;
}
