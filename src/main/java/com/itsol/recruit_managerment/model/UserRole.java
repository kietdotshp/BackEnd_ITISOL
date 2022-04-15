package com.itsol.recruit_managerment.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users_roles")
@Data
public class UserRole {
    @EmbeddedId
    private MyIdclass id;

    @Embeddable
    @Data
    public static class MyIdclass implements Serializable {

        @Column(name = "users_id")
        private Long usersId;
        @Column(name = "roles_id")
        private Integer rolesId;
    }

}
