package com.itsol.recruit_managerment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobDTO {
    String jobName;
    String jobPosition;
    Date createDate;
    Date dueDate;
    float minSalary;
    float maxSalary;
    int view;
    boolean isDelete;

}
