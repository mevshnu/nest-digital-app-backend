package com.example.NestDigitalApp.Dao;

import com.example.NestDigitalApp.Model.Leave;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface LeaveDao extends CrudRepository<Leave,Integer>
{
    @Query(value = "SELECT l.`id`, l.`apply_date`, e.emp_code, e.name, l.`from_date`, l.`leave_status`, l.`remarks`, l.`to_date`, l.`leave_type` FROM `leave2` AS l JOIN `employee` AS e ON e.id = l.emp_id", nativeQuery = true)
    List<Map<String, String>> GetAllLeaves();

    @Query(value = "SELECT `id`, `apply_date`, `emp_id`, `from_date`, `leave_status`, `leave_type`, `remarks`, `to_date` FROM `leave2` WHERE `emp_id`= :id", nativeQuery = true)
    List<Leave> GetEmployeeLeaves(@Param("id") int id);
}
