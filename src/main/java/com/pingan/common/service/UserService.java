package com.pingan.common.service;

import com.alibaba.fastjson.JSON;
import com.pingan.common.entity.User;
import com.pingan.common.repository.UserRepository;
import com.pingan.enums.GenderEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Service
public class UserService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;

    public User createUser(){
        User user = User.of();
        user.setUserName("慧智兰心");
        user.setGender(GenderEnum.FEMALE);
        user.setMobile("18196646735");
        user.setCreateDate(new Date());
        logger.info(JSON.toJSONString(user));
        User save = userRepository.save(user);
        return save;
    }

    public User createUser(User user){
        User save = userRepository.save(user);
        return save;
    }
    /**
     * @Author: shouwangqingzhong
     * @Description: 
     * @Date: 2019/8/22 16:24
     * @Param: [userId]
     * @return: com.pingan.common.entity.User
     * @version: 3.0.0
     **/
    public User getById(Long userId){
        User user = userRepository.getOne(userId);
        return user;
    }


    public List<User> findAll(){
        return userRepository.findAll();
    }

    public List<User> findByList(){
        List<Long> ids = new ArrayList<>();
        ids.add(50L);
        ids.add(4952L);

        List<String> mobiles = new ArrayList<>();
        mobiles.add("18196646735");
        List<User> all = userRepository.findAll(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Set<Predicate> predicates = new HashSet<>();
                Predicate id = cb.not(root.get("id").in(ids));
                predicates.add(id);
                Predicate mobile = cb.not(root.get("mobile").in(mobiles));
                predicates.add(mobile);
                query.where(predicates.toArray(new Predicate[predicates.size()]));
                return query.getRestriction();
            }
        });
        logger.info("UserService findByList all is"+JSON.toJSONString(all));
        return all;
    }
}
