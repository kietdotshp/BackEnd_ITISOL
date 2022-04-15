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
@Table(name = "desiredwork")
public class DesiredWork implements Serializable {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DESIRED_WORK_SEQ")
    @SequenceGenerator(name = "DESIRED_WORK_SEQ", sequenceName = "DESIRED_WORK_SEQ", allocationSize = 1, initialValue = 1)
    Long id;

    @Column(name = "desired_work_name ", nullable = false)
    String desiredworkname;

    @Column(name = "description", nullable = false)
    String description;
    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(name = "is_delete", nullable = false)
    boolean isDelete;
}
