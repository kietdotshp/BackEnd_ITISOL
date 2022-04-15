package com.itsol.recruit_managerment.service;

import com.itsol.recruit_managerment.model.Profiles;
import com.itsol.recruit_managerment.model.User;
import com.itsol.recruit_managerment.repositories.ProfileRepo;
import com.itsol.recruit_managerment.repositories.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProfileServiceImpl implements ProfileService {
    @Autowired
    private ProfileRepo profileRepository;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Profiles> findAll() {
        try {
            return profileRepository.findAll();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Profiles save(Profiles profileEntity) {
        return profileRepository.save(profileEntity);
    }

    @Override
    public Profiles findByUserID(Long userId) {
        try {
            String sql = "select t from Profiles t where users.id=:UserID";
            Query query = entityManager.createQuery(sql)
                    .setParameter("UserID", userId).setMaxResults(1);//nếu có 2 đối tượng có userid trong bảng lấy giá trị đàu tiên
            return (Profiles) query.getResultList().get(0);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    // Hàm check đã tồn tại userid trong profile chưa
    @Override
    public Boolean checkUserID(Long userID) {
        try {
            Query query = entityManager.createNativeQuery("select count(*) from profiles where user_id=:userID")
                    .setParameter("userID", userID);
            int check = ((BigInteger) query.getSingleResult()).intValue();
            //Chưa tồn tại
            if (check == 0)
                return false;
                // tồn tại
            else
                return false;
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            return true;
        }
    }


}
