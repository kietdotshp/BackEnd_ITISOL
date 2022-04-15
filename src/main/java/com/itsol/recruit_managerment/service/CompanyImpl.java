package com.itsol.recruit_managerment.service;

import com.itsol.recruit_managerment.model.Company;
import com.itsol.recruit_managerment.repositories.CompanyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyImpl implements CompanyService {
    @Autowired
    private CompanyRepo companyRepo;


    @Override
    public List<Company> getListAll() {
        return companyRepo.findAll();
    }

    @Override
    public Company findByID(Integer id) {
        try {
            Optional<Company> optional = companyRepo.findById(id);
            if (optional.isPresent()) {
                return optional.get();
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Company save(Company companyEntity) {
        try {
            return companyRepo.save(companyEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
