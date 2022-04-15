package com.itsol.recruit_managerment.service;

import com.itsol.recruit_managerment.model.DesiredWork;
import com.itsol.recruit_managerment.repositories.DesireWorkRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Optional;
@Service
public class DesiredWorkServiceImpl implements DesireWorkService{
    @Autowired
    private DesireWorkRepo desiredWorkRepository;

    @Autowired
    private EntityManager entityManager;
    @Override
    public DesiredWork findById(Long id) {
        try {
            if(id==null) {
                return null;
            }
            Optional<DesiredWork> optional = desiredWorkRepository.findById(id);
            if(optional.isPresent()) {
                return optional.get();
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public DesiredWork findDesiredWorkIdByDesiredworkname(String desiredworkname) {
        try {
            String sql = "select t from DesiredWork t where t.desiredworkname =:desiredworkname";
            Query query = entityManager.createQuery(sql);
            query.setMaxResults(1);
            query.setParameter("desiredworkname", desiredworkname);
            return (DesiredWork) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public DesiredWork save(DesiredWork desiredwork) {
        try {
            return desiredWorkRepository.save(desiredwork);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//    @Override
//    public DesiredWork save(DesiredWork desiredwork) {
//        try {
//            return desiredWorkRepository.save(desiredwork);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }


}
