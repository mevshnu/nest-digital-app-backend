package com.example.NestDigitalApp.Dao;

import com.example.NestDigitalApp.Model.Employee;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeDao extends CrudRepository<Employee,Integer>
{
    @Query(value = "SELECT `id`, `designation`, `email`, `emp_code`, `name`, `password`, `phone`, `username` FROM `employee` WHERE `username`= :username AND `password`= :password", nativeQuery = true)
    List<Employee> UserLoginDetails(@Param("username") String username, @Param("password") String password);

    @Query(value = "SELECT `id`, `designation`, `email`, `emp_code`, `name`, `password`, `phone`, `username` FROM `employee` WHERE `name` LIKE %:name%", nativeQuery = true)
    List<Employee> SearchEmployee(@Param("name") String name);

    @Modifying
    @Transactional
    @Query(value = "UPDATE `employee` SET `designation`= :designation,`email`= :email,`emp_code`= :emp_code,`name`= :name,`password`= :password,`phone`= :phone,`username`= :username WHERE `id`= :id", nativeQuery = true)
    void EditEmployee(@Param("id") int id, @Param("designation") String designation,@Param("email") String email,@Param("emp_code") String emp_code,@Param("name") String name,@Param("password") String password,@Param("phone") String phone,@Param("username") String username);
    @Query(value = "SELECT `id`, `designation`, `email`, `emp_code`, `name`, `password`, `phone`, `username` FROM `employee` WHERE `id`= :id", nativeQuery = true)
    List<Employee> GetEmployeeProfile(@Param("id") int id);


}
