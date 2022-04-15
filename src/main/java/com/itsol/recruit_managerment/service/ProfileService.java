package com.itsol.recruit_managerment.service;

import com.itsol.recruit_managerment.model.Profiles;

import java.util.List;

public interface ProfileService {
    List<Profiles> findAll();

    Profiles save(Profiles profileEntity);

    Profiles findByUserID(Long id);

    public Boolean checkUserID(Long userID);
}
