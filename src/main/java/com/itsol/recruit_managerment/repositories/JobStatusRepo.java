package com.itsol.recruit_managerment.repositories;

import com.itsol.recruit_managerment.model.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobStatusRepo extends JpaRepository<JobStatus,Long> {
}
