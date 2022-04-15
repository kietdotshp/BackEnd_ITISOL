package com.itsol.recruit_managerment.repositories.jobRegisterRp;

import com.itsol.recruit_managerment.model.JobsRegister;
import com.itsol.recruit_managerment.vm.JobRegisterSearchVm;

import java.util.List;

public interface JobRegisterRepo {
    List<JobsRegister> search(JobRegisterSearchVm jobRegisterSearchVm);
}
