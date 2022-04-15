package com.itsol.recruit_managerment.service;

import com.itsol.recruit_managerment.model.DesiredWork;

public interface DesireWorkService {
    DesiredWork findById(Long id);
    DesiredWork findDesiredWorkIdByDesiredworkname(String desiredworkname);
    public DesiredWork save(DesiredWork desiredwork);
}
