package com.itsol.recruit_managerment.repositories.JeRepo;

import com.itsol.recruit_managerment.model.User;
import com.itsol.recruit_managerment.vm.JobRegisterSearchVm;
import com.itsol.recruit_managerment.vm.UserVM;

import java.util.List;

public interface jeRepo {
    List<User> search(UserVM userVM);
}
