package com.example.NestDigitalApp.Controller;

import com.example.NestDigitalApp.Dao.EmployeeDao;
import com.example.NestDigitalApp.Dao.Leave1Dao;
import com.example.NestDigitalApp.Model.Employee;
import com.example.NestDigitalApp.Model.Leaves1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

public class AdminController {

    @Autowired
    private EmployeeDao empdao;
    @Autowired
    private Leave1Dao l1dao;

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy");

    @CrossOrigin(origins="*")
    @PostMapping(path="/addEmployee",consumes = "application/json",produces = "application/json")
    public HashMap<String,String> AddEmployee(@RequestBody Employee emp)
    {
        List<Employee> emp1 = (List<Employee>) empdao.UserLoginDetails(emp.getUsername(), emp.getPassword());
        HashMap<String, String> hashMap = new HashMap<>();
        if(emp1.size()==0){
            LocalDateTime now = LocalDateTime.now();
            empdao.save(emp);
            Leaves1 l1 = new Leaves1();
            l1.setEmpCode(emp.getEmpCode());
            l1.setYear(dtf.format(now));
            l1.setCasualLeave(20);
            l1.setSickLeave(7);
            l1.setSpecialLeave(3);
            l1dao.save(l1);

            hashMap.put("status","success");
        }else{
            hashMap.put("status","failed");
        }
        return hashMap;
    }
    @CrossOrigin(origins="*")
    @PostMapping(path = "/searchEmployee", consumes = "application/json", produces = "application/json")
    public List<Employee> SearchEmployee(@RequestBody Employee emp)
    {
        return  (List<Employee>) empdao.SearchEmployee(emp.getName());
    }
    @CrossOrigin(origins="*")
    @PostMapping(path = "/editEmployee", consumes = "application/json", produces = "application/json")
    public HashMap<String, String> EditEmployee(@RequestBody Employee emp) {
        System.out.println(emp.getId());
        empdao.EditEmployee(emp.getId(), emp.getDesignation(), emp.getEmail(), emp.getEmpCode(), emp.getName(), emp.getPassword(), emp.getPhone(), emp.getUsername());
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("status", "success");
        return hashMap;
    }
    @GetMapping("/viewAllEmployee")
    public List<Employee> GetAllEmployee(){
        return (List<Employee>) empdao.findAll();
    }

}
