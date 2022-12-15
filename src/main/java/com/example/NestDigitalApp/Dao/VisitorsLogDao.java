package com.example.NestDigitalApp.Dao;

import com.example.NestDigitalApp.Model.VisitorLog;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VisitorsLogDao extends CrudRepository<VisitorLog,Integer> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE `visitorlog` SET `exit_time`= :time WHERE `phone`= :phone", nativeQuery = true)
    void UpdateExitEntry(@Param("phone") String phone, @Param("time") String time);


    @Query(value = "SELECT `id`, `date`, `entry_time`, `exit_time`, `name`, `phone` FROM `visitorlog` WHERE `date`= :date", nativeQuery = true)
    List<VisitorLog> SearchVisLog(@Param("date") String date);
}
