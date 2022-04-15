package com.itsol.recruit_managerment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.*;
import java.util.Date;

public class InformationUserDTO {
    @NotEmpty(message = "Thiếu email")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotEmpty(message = "Thiếu số điện thoại")
    private String phone_number;
    private String homeTown;
    private String gender;
    private String fullName;

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    @NotNull
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date birth_day;

    private String avatar;

    private String skill;

    @NotNull
    @DecimalMin(value = "0")
    private Integer number_years_experience;

    @NotEmpty(message = "Thiếu trình độ học vấn")
    private String academic_name;

    @NotEmpty(message = "Thiếu mức lương mong muốn")
    @DecimalMin(value = "1")
    private Integer desired_salary;

    @NotEmpty(message = "Thiếu địa chỉ làm việc mong muốn")
    private String desired_working_address;

    @NotEmpty(message = "Thiếu hình thức làm việc mong muốn")
    private String desiredworkname;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirth_day() {
        return birth_day;
    }

    public void setBirth_day(Date birth_day) {
        this.birth_day = birth_day;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public Integer getNumber_years_experience() {
        return number_years_experience;
    }

    public void setNumber_years_experience(Integer number_years_experience) {
        this.number_years_experience = number_years_experience;
    }

    public String getAcademic_name() {
        return academic_name;
    }

    public void setAcademic_name(String academic_name) {
        this.academic_name = academic_name;
    }

//    public String getDesired_salary() {
//        return desired_salary;
//    }

//    public void setDesired_salary(String desired_salary) {
//        this.desired_salary = desired_salary;
//    }

    public void setDesired_salary(Integer desired_salary) {
        this.desired_salary = desired_salary;
    }

    public Integer getDesired_salary() {
        return desired_salary;
    }

    public String getDesired_working_address() {
        return desired_working_address;
    }

    public void setDesired_working_address(String desired_working_address) {
        this.desired_working_address = desired_working_address;
    }

    public String getDesiredworkname() {
        return desiredworkname;
    }

    public void setDesiredworkname(String desiredworkname) {
        this.desiredworkname = desiredworkname;
    }



    public InformationUserDTO(
            @NotBlank @NotEmpty(message = "Thiếu email") @Email(message = "Email không hợp lệ") String email,
            @NotEmpty(message = "Thiếu số điện thoại") String phone_number, String homeTown, String gender,
            @NotEmpty(message = "Thiếu ngày tháng năm sinh") Date birth_day, String avatar, String skill,
            @NotEmpty(message = "Thiếu số năm kinh nghiệm") @DecimalMin("0") Integer number_years_experience,
            @NotEmpty(message = "Thiếu trình độ học vấn") String academic_name,
            @NotEmpty(message = "Thiếu mức lương mong muốn") @DecimalMin(value = "1") Integer desired_salary,
            @NotEmpty(message = "Thiếu địa chỉ làm việc mong muốn") String desired_working_address,
            @NotEmpty(message = "Thiếu hình thức làm việc mong muốn") String desiredworkname) {
        super();
        this.email = email;
        this.phone_number = phone_number;
        this.homeTown = homeTown;
        this.gender = gender;
        this.birth_day = birth_day;
//        this.avatar = avatar;
        this.skill = skill;
        this.number_years_experience = number_years_experience;
        this.academic_name = academic_name;
        this.desired_salary = desired_salary;
        this.desired_working_address = desired_working_address;
        this.desiredworkname = desiredworkname;
    }

    public InformationUserDTO() {
        super();
        // TODO Auto-generated constructor stub
    }
}
