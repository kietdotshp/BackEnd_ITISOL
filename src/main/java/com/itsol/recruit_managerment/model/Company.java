package com.itsol.recruit_managerment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "company")
public class Company implements Serializable {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMPANY_SEQ")
    @SequenceGenerator(name = "COMPANY_SEQ", sequenceName = "COMPANY_SEQ", allocationSize = 1, initialValue = 1)
    int Id;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "email", nullable = false)
    String email;

    @Column(name = "hot_line", nullable = false)
    String hotLine;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "date_incoporation", nullable = false)
    Date dateIncorporation;

    @Column(name = "tax_code", nullable = false)
    String taxCode;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "tax_date", nullable = false)
    Date taxDate;

    @Column(name = "tax_place", nullable = false)
    String taxPlace;

    @Column(name = "head_office", nullable = false)
    String headOffice;

    @Column(name = "number_staff", nullable = false)
    int numberStaff;

    @Column(name = "link_web", nullable = false)
    String linkWeb;

    @Column(name = "description", nullable = false)
    String description;

    @Column(name = "avatar", nullable = false)
    String avatar;

//    @Column(name = "backdrop_img", nullable = false)
//    String backdropImg;
@Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(name = "is_delete", nullable = false)
    boolean isDelete;
}
