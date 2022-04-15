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

@Table(name = "job_register_status")
public class JobRegisterStatus implements Serializable {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "JOB_REGISTER_STATUS_SEQ")
    @SequenceGenerator(name = "JOB_REGISTER_STATUS_SEQ", sequenceName = "JOB_REGISTER_STATUS_SEQ", allocationSize = 1, initialValue = 1)
    Integer id;

    @Column(name = "status_name", nullable = false)
    String statusName;

    @Column(name = "is_delete", nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    boolean isDelete;
}
