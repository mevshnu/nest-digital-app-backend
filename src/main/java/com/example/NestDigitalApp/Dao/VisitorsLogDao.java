package com.example.NestDigitalApp.Dao;

import com.example.NestDigitalApp.Model.VisitorLog;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface VisitorsLogDao extends CrudRepository<VisitorLog,Integer>
{
    @Modifying
    @Transactional
    @Query(value = "UPDATE `visitorlog` SET `exit_time`= :time WHERE `phone`= :phone", nativeQuery = true)
    void UpdateExitEntry(@Param("phone") String phone, @Param("time") String time);
}
