package com.itsol.recruit_managerment.repositories;

import com.itsol.recruit_managerment.dto.InformationUserDTO;
import com.itsol.recruit_managerment.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
//    User findByEmail(String email);
//    public List<InformationUserDTO> getInforbyId(@Param("userid") Integer userid);
}
