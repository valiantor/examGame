package com.valiantor.dao;

import com.valiantor.entity.Admin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("adminDao")
public interface AdminDao {

    public Admin findAdminByAIdAndPassword(@Param("aId") String aId, @Param("password") String password);
}
