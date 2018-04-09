package com.gyj.Service.Impl;

import com.gyj.Dao.GirlRepository;
import com.gyj.Service.IGirlService;
import com.gyj.configuration.GirlException;
import com.gyj.entity.Girl;
import com.gyj.enums.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Gao on 2017/11/22.
 */
@Transactional
@Service
public class GirlServiceImpl implements IGirlService {

    @Autowired
    private GirlRepository girlRepository;

    @Override
    //默认的Exception只是接收msg异常,还有code因此自定义的异常
    public void getAge(Integer id) throws Exception {

        Girl girl = girlRepository.findOne(id);
        Integer age = girl.getAge();

        if (age < 10) {
            throw new GirlException(ResultEnum.PRIMARY_SCHOOL);
        }
        if (age > 10 && age < 16) {
            throw new GirlException(ResultEnum.MIDDLE_SCHOOL);
        }
    }

}
