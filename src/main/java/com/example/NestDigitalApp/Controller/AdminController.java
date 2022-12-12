package com.example.NestDigitalApp.Controller;

import com.example.NestDigitalApp.Dao.EmployeeDao;
import com.example.NestDigitalApp.Dao.Leave1Dao;
import com.example.NestDigitalApp.Dao.SecurityDao;
import com.example.NestDigitalApp.Model.Employee;
import com.example.NestDigitalApp.Model.Leaves1;
import com.example.NestDigitalApp.Model.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

@RestController
public class AdminController {

    @Autowired
    private EmployeeDao empdao;
    @Autowired
    private Leave1Dao l1dao;
    @Autowired
    private SecurityDao sdao;


    @CrossOrigin(origins = "*")
    @PostMapping(path="/addEmployee",consumes = "application/json",produces = "application/json")
    public HashMap<String,String> AddEmployee(@RequestBody Employee emp)
    {
        empdao.save(emp);
        HashMap<String,String> status = new HashMap<>();
        status.put("status","success");
        return status;
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

    @CrossOrigin(origins = "*")
    @PostMapping(path="/addSecurity",consumes = "application/json",produces = "application/json")
    public HashMap<String,String> AddSecurity(@RequestBody Security sec)
    {
      sdao.save(sec);
      HashMap<String,String> status = new HashMap<>();
      status.put("status","success");
      return status;
    }


}
