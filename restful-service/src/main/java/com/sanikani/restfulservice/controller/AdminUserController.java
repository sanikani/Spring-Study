package com.sanikani.restfulservice.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.sanikani.restfulservice.bean.AdminUser;
import com.sanikani.restfulservice.bean.AdminUserV2;
import com.sanikani.restfulservice.bean.User;
import com.sanikani.restfulservice.dao.UserDaoService;
import com.sanikani.restfulservice.excetion.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminUserController {
    private final UserDaoService service;

    //    @GetMapping(value = "/users/{id}", params = "version=1")
    @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=1")
    public MappingJacksonValue retrieveUser4Admin(@PathVariable int id) {
        User user = service.findOne(id);
        AdminUser adminUser = new AdminUser();

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        } else {
            BeanUtils.copyProperties(user, adminUser);
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filters);

        return mapping;
    }

    //    @GetMapping(value = "/users/{id}", params = "version=2")
    @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=2")
    public MappingJacksonValue retrieveUser4AdminV2(@PathVariable int id) {
        User user = service.findOne(id);
        AdminUserV2 adminUserV2 = new AdminUserV2();

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        } else {
            BeanUtils.copyProperties(user, adminUserV2);
            adminUserV2.setGrade("VIP");
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "grade");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUserV2);
        mapping.setFilters(filters);

        return mapping;
    }

    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsers4Admin() {
        List<User> users = service.findAll();
        List<AdminUser> adminUsers = new ArrayList<>();

        AdminUser adminUser = null;
        for (User user : users) {
            adminUser = new AdminUser();
            BeanUtils.copyProperties(user, adminUser);

            adminUsers.add(adminUser);
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUsers);
        mapping.setFilters(filters);

        return mapping;
    }
}

