package com.itsol.recruit_managerment.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "academic_level")
public class AcademicLevel  implements Serializable {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACADEMIC_LEVEL_SEQ")
    @SequenceGenerator(name = "ACADEMIC_LEVEL_SEQ", sequenceName = "ACADEMIC_LEVEL_SEQ", allocationSize = 1, initialValue = 1)
    long id;

//    @OneToOne(targetEntity = Jobs.class, fetch = FetchType.EAGER)
//    @JoinColumn(name = "jobs_id", nullable = false)
//    Jobs jobs;

//    @OneToOne(targetEntity = Profiles.class, fetch = FetchType.EAGER)
//    @JoinColumn(name = "profile_id", nullable = false)
//    Profiles profiles;

    @Column(name = "academic_name", nullable = false)
    String academicName;

    @Column(name = "description", nullable = false)
    String description;

    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(name = "is_delete", nullable = false)
    boolean isDelete;

}
