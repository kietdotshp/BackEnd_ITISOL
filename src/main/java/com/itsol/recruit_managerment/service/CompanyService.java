package com.itsol.recruit_managerment.service;

import com.itsol.recruit_managerment.model.Company;

import java.util.List;

public interface CompanyService {

    List<Company> getListAll();

    Company findByID(Integer id);

    Company save(Company companyEntity);

}
