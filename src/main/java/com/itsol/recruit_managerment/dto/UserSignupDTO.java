package com.itsol.recruit_managerment.dto;

import com.itsol.recruit_managerment.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSignupDTO {
    private String fullName;
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email invalid!")
    private String email;
    private String userName;
    private String password;
    @Length(message = "Phone invalid!", min = 8, max = 15)
    private String phoneNumber;
    private String homeTown;
    private String gender;
    private String birthDay;
    private Set<Role> roles;
}
