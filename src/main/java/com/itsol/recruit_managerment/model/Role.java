package com.itsol.recruit_managerment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_ROLE_ID")
    @SequenceGenerator(name = "GEN_ROLE_ID", sequenceName = "ROLE_SEQ", allocationSize = 1)

    @Column(nullable = false)
    private Long id;

    @Column(name = "ROLE_CODE", nullable = false)
    private String roleCode;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "NAME")
    private String name;
}
