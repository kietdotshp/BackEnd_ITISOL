package com.itsol.recruit_managerment.repositories;


import com.itsol.recruit_managerment.model.MethodWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MethodWorkRepo extends JpaRepository<MethodWork,Long>{
}
