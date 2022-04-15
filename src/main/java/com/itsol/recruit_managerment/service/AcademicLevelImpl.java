package com.itsol.recruit_managerment.service;

import com.itsol.recruit_managerment.model.AcademicLevel;
import com.itsol.recruit_managerment.repositories.AcademicLevelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Optional;

@Service
public class AcademicLevelImpl implements AcademicLevelService{
    @Autowired
    private AcademicLevelRepo academic_levelRepository;

    @Autowired
    private EntityManager entityManager;
    @Override
    public AcademicLevel findById(Long id) {
        try {
            if(id==null) {
                return null;
            }
            Optional<AcademicLevel> optional = academic_levelRepository.findById(id);
            if(optional.isPresent()) {
                return optional.get();
            }
            return null;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public AcademicLevel findAcademic_nameById(String academicName) {
        try {
            String sql = "select t from AcademicLevel t where t.academicName =:academic_name";
            Query query = entityManager.createQuery(sql);
            query.setMaxResults(1);
            query.setParameter("academic_name", academicName);
            return (AcademicLevel) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public AcademicLevel save(AcademicLevel academicLevel) {
        try {
            return academic_levelRepository.save(academicLevel);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
