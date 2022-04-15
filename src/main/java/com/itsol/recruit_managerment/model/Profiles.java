package com.itsol.recruit_managerment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "profiles")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Profiles implements Serializable {
    @Id
    @Column(name="id",nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROFILES_SEQ")
    @SequenceGenerator(name = "PROFILES_SEQ", sequenceName = "PROFILES_SEQ", allocationSize = 1, initialValue = 1)
    Long id;


//    @Column(name = "user_id", insertable = false, updatable = false)
//    Long userId;


    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "user_id", nullable = false)
    User users;

    @ManyToOne(targetEntity = DesiredWork.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "desire_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    DesiredWork desiredwork;

    @ManyToOne(targetEntity = AcademicLevel.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "academic_level_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    AcademicLevel academicLevel;

    @Column(name = "skill", nullable = false)
    String skill;

    @Column(name = "number_years_experience", nullable = false)
    Integer numberYearsExperience;

    @Column(name = "desired_salary", nullable = false)
    Integer desiredSalary;

    @Column(name = "desired_working_address", nullable = false)
    String desiredWorkingAddress;

    @Column(name = "is_delete", nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    boolean isDelete;
}
