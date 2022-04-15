package com.itsol.recruit_managerment.repositories;

import com.itsol.recruit_managerment.model.Profiles;
import com.itsol.recruit_managerment.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ProfileRepo extends JpaRepository<Profiles,Long> {

    Profiles findByUsers(User user);

}
