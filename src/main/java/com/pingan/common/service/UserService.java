package com.pingan.common.service;

import com.alibaba.fastjson.JSON;
import com.pingan.common.entity.User;
import com.pingan.common.model.Page;
import com.pingan.common.repository.UserRepository;
import com.pingan.enums.GenderEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
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
    @PersistenceUnit
    private EntityManagerFactory emf;

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

    public Page getByEntityManager() {
        int p = 2;
        int size = 2;
        Page<User> page = new Page<>();
        page.setPage(p);
        page.setSize(size);
        EntityManager ef = emf.createEntityManager();
        StringBuffer sql = new StringBuffer();
        sql.append("select id ,user_name,mobile,gender,create_date from sys_user order by create_date desc");
        int total = ef.createNativeQuery(sql.toString()).getResultList().size();
        page.setTotalElements(total);
        if (0 == total){
            return page;
        }
        page.setTotalPage(returnTotalPage(size,total));
        Query query = ef.createNativeQuery(sql.toString(),User.class);
        query.setFirstResult((p-1)*size).setMaxResults(size);
        List<User> resultList = query.getResultList();
        page.setElements(resultList);
        logger.info("UserService respbody is"+JSON.toJSONString(page));
        return page;
    }

    private int returnTotalPage(int size,int totalSize){
        int totalPage = totalSize/size;
        if (totalSize%size != 0){
            totalPage += 1;
        }
        return totalPage;
    }

    /**
     * @Author: shouwangqingzhong
     * @Description:  测试更新null值
     * @Date: 2019/10/25 14:45
     * @Param: []
     * @return: com.pingan.common.entity.User
     * @version: 3.0.0
     **/
    public User testUpdateNull() {
        Optional<User> userOptional = userRepository.findById(50L);
        Assert.isTrue(userOptional.isPresent(),String.format("id为%s的用户不存在",50L));
        User user = userOptional.get();
        user.setGender(null);
        User save = userRepository.save(user);
        return save;
    }
}
