package com.gyj.Controller;

import com.gyj.Service.IPersonService;
import com.gyj.entity.Person;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Gao on 2017/11/30.
 */

@RestController
@RequestMapping(value = "/person", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PersonController {

    @Autowired
    IPersonService personService;

    @ApiOperation(value = "添加")
    @GetMapping(value = "/savePerson")
    public Person savePerson(Person person) {
        return personService.save(person);
    }

    @ApiOperation(value = "查询")
    @GetMapping(value = "/findOnePerson")
    public Person findOnePerson(@RequestParam("id") Integer id) {
        return personService.findOne(id);
    }

    @ApiOperation(value = "删除")
    @GetMapping(value = "/removeOnePerson")
    public String removeOnePerson(@RequestParam("id") Integer id) {
        personService.remove(id);
        return "ok";
    }
}
