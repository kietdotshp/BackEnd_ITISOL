package com.itsol.recruit_managerment.vm;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobSearchVM {
    String searchName;
    String positionName;
    String applicationTimeTo;
    String applicationTimeFrom;
    float minSalary;
    float maxSalary;


}
