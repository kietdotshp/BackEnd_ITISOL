package com.itsol.recruit_managerment.model;

//import com.fasterxml.jackson.annotation.Js
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User implements Serializable {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERS_SEQ")
    @SequenceGenerator(name = "USERS_SEQ", sequenceName = "USERS_SEQ", allocationSize = 1, initialValue = 1)
    Long id;

    @NotBlank(message = "fullName không được để trống")
    @Column(name = "FULL_NAME")
    String fullName;
    @Column(name = "AVATAR")
    String avatar;
    @Column(name = "EMAIL")
    String email;

    @Column(name = "USER_NAME")
    String userName;

    @Column(name = "PASSWORD")
    @JsonIgnore
    String password;

    @Column(name = "PHONE_NUMBER")
    String phoneNumber;

    @Column(name = "HOME_TOWN")
    String homeTown;

    @Column(name = "GENDER")
    String gender;

    @Column(name = "BIRTH_DAY")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    Date birthDay;

    @Column(name = "IS_DELETE")
    int isDelete;
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Set<Role> roles = new HashSet<>();
//@ManyToMany(fetch = FetchType.EAGER)
//@JoinTable(name = "authority",
//        joinColumns = @JoinColumn(name = "user_id"),
//        inverseJoinColumns = @JoinColumn(name = "role_id"))
//private Set<Role> roles = new HashSet<>();

    @Column(name = "ISACTIVE")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isActive;
}
