package com.itsol.recruit_managerment.repositories;

import com.itsol.recruit_managerment.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRespository extends JpaRepository<User, Long> {

    User findByUserName(String userName);

    User findByEmail(String email);

    User getUserById(Long id);

    @Query(value = "from Users u where u.fullName like %:fullName%")
    List<User> findByFullName(@Param("fullName") String fullName);


    @Query(nativeQuery = true, value = "select * from users u where u.id in (select USERS_ID from USERS_ROLES r where r.ROLES_ID = 1)")
    List<User> getAllUSER();

    @Query(nativeQuery = true, value = "select * from users u where u.id in (select USERS_ID from USERS_ROLES r where r.ROLES_ID = 3)")
    List<User> getAllJE();

    @Query("SELECT p FROM  Users p " +
            "WHERE (:userName IS NULL OR UPPER(p.userName) LIKE CONCAT('%',UPPER(:userName),'%'))" +
            "AND (:fullName IS NULL OR UPPER(p.fullName) LIKE CONCAT('%',UPPER(:fullName),'%'))" +
            "AND (:phoneNumber IS NULL OR UPPER(p.phoneNumber) LIKE CONCAT('%',UPPER(:phoneNumber),'%'))" +
            "AND (:email IS NULL OR UPPER(p.email) LIKE CONCAT('%',UPPER(:email),'%'))")
    Page<User> getListFullnameByContaining(Pageable pageable,
                                           @Param("userName") String userName,
                                           @Param("fullName") String fullName,
                                           @Param("phoneNumber") String phoneNumber,
                                           @Param("email") String email);


//    void getUserById();
}


