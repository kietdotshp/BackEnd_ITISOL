package com.itsol.recruit_managerment.repositories.jobrepo;

import com.itsol.recruit_managerment.model.Jobs;
import com.itsol.recruit_managerment.vm.JobSearchVM;
import org.springframework.stereotype.Repository;



import java.util.List;

@Repository
public interface JobRepo {

    List<Jobs> search(JobSearchVM jobSearchVM);

}
