package com.itsol.recruit_managerment.request;

import com.itsol.recruit_managerment.model.AcademicLevel;
import com.itsol.recruit_managerment.model.DesiredWork;
import com.itsol.recruit_managerment.model.User;
import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InformationUserRequest {
    private Long id;
    private User users;
    private DesiredWork desiredwork;
    private AcademicLevel academicLevel;
    private String  skill;
    private String fullName;
    private String avatar;
    private String email;
    private String homeTown;
    private String phoneNumber;
    private String gender;
    private Date birthDay;
    private String desiredworkname;
    private String academicName;
    private Integer numberYearsExperience;
    private Integer desiredSalary;
    private String desiredWorkingAddress;
    private Boolean delete;


}