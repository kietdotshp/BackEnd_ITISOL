package com.itsol.recruit_managerment.repositories;

import com.itsol.recruit_managerment.model.JobStatus;
import com.itsol.recruit_managerment.model.LevelRank;
import com.itsol.recruit_managerment.model.MethodWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelRankRepo extends JpaRepository<LevelRank,Long> {
}
