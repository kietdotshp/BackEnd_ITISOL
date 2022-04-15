package com.itsol.recruit_managerment.service;

import com.itsol.recruit_managerment.model.Jobs;
import com.itsol.recruit_managerment.vm.JobSearchVM;

import java.util.List;

public interface JobsService {
    List<Jobs> search(JobSearchVM jobSearchVM);
}
