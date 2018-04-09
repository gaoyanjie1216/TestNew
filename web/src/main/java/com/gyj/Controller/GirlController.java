package com.gyj.Controller;

import com.gyj.Dao.GirlRepository;
import com.gyj.Service.IGirlService;
import com.gyj.Utils.ResultUtils;
import com.gyj.base.GirlResult;
import com.gyj.entity.Girl;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Gao on 2017/11/21.
 */
@RestController
@RequestMapping(value = "/girl", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class GirlController {

    public static final Log log = LogFactory.getLog(GirlController.class);

    @Autowired
    private GirlRepository girlRepository;

    @Autowired
    private IGirlService girlService;

    /**
     * 获取列表
     * @return
     */
    @ApiOperation(value = "获取列表",notes = "")
    @RequestMapping(value = "/getGirlsList", method = RequestMethod.GET)
    public List<Girl> girlList() {
        log.info("girlList");
        List<Girl> girlList = girlRepository.findAll();
        return girlList;
    }

    /**
     * 添加一个女生
     * @param cupSize
     * @param age
     * @return
     */
    @ApiOperation(value = "添加",notes = "")
    @RequestMapping(value = "/addGirl", method = RequestMethod.GET)
    public Girl girlAdd(@RequestParam("cupSize") String cupSize,
                        @RequestParam("age") Integer age) {

        Girl girl = new Girl();
        girl.setCupSize(cupSize);
        girl.setAge(age);
        return girlRepository.save(girl);
    }

    /**
     * 添加一个女生
     * @param girl
     * @return
     */
    @ApiOperation(value = "添加",notes = "")
    @GetMapping(value = "/addGirl1")
    public Girl addGirl1(@Valid Girl girl, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            //打印在age字段中的message的内容，未成年不能进入！
            log.info(bindingResult.getFieldError().getDefaultMessage());
            return null;
        }

        girl.setCupSize(girl.getCupSize());
        girl.setAge(girl.getAge());
        return girlRepository.save(girl);
    }

    /**
     * 添加一个女生
     * @param girl
     * @return
     */
    @ApiOperation(value = "添加",notes = "")
    @GetMapping(value = "/addGirl2")
    public GirlResult addGirl2(@Valid Girl girl, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultUtils.error(1, bindingResult.getFieldError().getDefaultMessage());
        }

        girl.setCupSize(girl.getCupSize());
        girl.setAge(girl.getAge());
        return ResultUtils.success(girlRepository.save(girl));
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id查询", notes = "")
    @GetMapping(value = "/findOneGirl/{id}")
    public Girl girlFindOne(@PathVariable("id") Integer id) {

        return girlRepository.findOne(id);
    }

    /**
     * 根据id查询年龄,异常处理
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id查询年龄", notes = "")
    @GetMapping(value = "/getAgeById")
    public void getAgeById(@RequestParam("id") Integer id) throws Exception {

        girlService.getAge(id);

    }

    /**
     * 根据年龄查询
     * @param age
     * @return
     */
    @ApiOperation(value = "根据age查询",notes = "")
    @GetMapping(value = "/findGirlsListByAge/{age}")
    public List<Girl> findGirlsListByAge(@PathVariable("age") Integer age) {

        List<Girl> girlsList = girlRepository.findByAge(age);
        return girlsList;
    }

    /**
     * 更新信息
     * @param id
     * @param cupSize
     * @param age
     * @return
     */
    @ApiOperation(value = "更新信息",notes = "")
    @GetMapping(value = "updateGirl")
    public Girl updateGirl(@RequestParam("id") Integer id,
                           @RequestParam("cupSize") String cupSize,
                           @RequestParam("age") Integer age) {
        Girl girl = new Girl();
        girl.setCupSize(cupSize);
        girl.setAge(age);
        girl.setId(id);
        return girlRepository.save(girl);
    }

    /**
     * 根据id删除单个
     * @param id
     */
    @ApiOperation(value = "根据id删除单个对象")
    @GetMapping(value = "/deleteOneGirl")
    public void deleteOneGirl(@RequestParam("id") Integer id) {
        girlRepository.delete(id);
    }

    /**
     * 根据id删除多个
     * @param ids
     */
    @ApiOperation(value = "根据id删除多个对象")
    @GetMapping(value = "/deleteGirls")
    public void deleteGirls(@RequestParam("ids") List<Integer> ids) {
        for (Integer id : ids) {
            girlRepository.delete(id);
        }
    }

}
