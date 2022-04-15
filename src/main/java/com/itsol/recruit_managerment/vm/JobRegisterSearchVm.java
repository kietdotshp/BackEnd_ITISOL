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
public class JobRegisterSearchVm {

    String applicantName;
    String positionName;
    String jobRegisterStatus;
    String applicationTimeFrom;
    String applicationTimeTo;
}
