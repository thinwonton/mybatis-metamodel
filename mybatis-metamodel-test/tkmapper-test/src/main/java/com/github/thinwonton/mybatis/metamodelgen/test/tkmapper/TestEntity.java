package com.github.thinwonton.mybatis.metamodelgen.test.tkmapper;

import com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.entity.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * TestEntity
 *
 * @author hugo
 * @date 2020/1/3
 */
public class TestEntity{
    private List<UserInfo> userInfos;
    private Map<String, Integer> userAgeMap;
    private int age;
    private String name;
    private Integer baby;

    public List<UserInfo> getUserInfos() {
        return userInfos;
    }

    public void setUserInfos(List<UserInfo> userInfos) {
        this.userInfos = userInfos;
    }

    public Map<String, Integer> getUserAgeMap() {
        return userAgeMap;
    }

    public void setUserAgeMap(Map<String, Integer> userAgeMap) {
        this.userAgeMap = userAgeMap;
    }
}
