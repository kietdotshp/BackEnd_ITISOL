package com.itsol.recruit_managerment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "jobs_register")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class JobsRegister implements Serializable {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "JOBS_REGISTER_SEQ")
    @SequenceGenerator(name = "JOBS_REGISTER_SEQ", sequenceName = "JOBS_REGISTER_SEQ", allocationSize = 1, initialValue = 1)
    Integer id;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    User user;

    @OneToOne(targetEntity = Jobs.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "jobs_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    Jobs jobs;

    @ManyToOne(targetEntity = JobRegisterStatus.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "job_register_status_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    JobRegisterStatus jobRegisterStatus;

    @OneToOne(targetEntity = Profiles.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "profiles_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    Profiles profiles;

    @Column(name = "application_time")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    Date applicationTime;

    @Column(name = "cv_file")
    String cvFile;

    @Column(name = "cv_mimetype")
    String cvMimetype;

    @Column(name = "reason")
    String reason;

    @Column(name = "date_interview")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    Date dateInterview;

    @Column(name = "method_interview")
    String methodInterview;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "is_delete")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    boolean isDelete;
}
