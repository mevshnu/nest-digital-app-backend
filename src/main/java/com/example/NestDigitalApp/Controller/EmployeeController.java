package com.example.NestDigitalApp.Controller;

import com.example.NestDigitalApp.Dao.EmployeeDao;
import com.example.NestDigitalApp.Model.Employee;
import com.example.NestDigitalApp.Model.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeDao empdao;
    @CrossOrigin(origins = "*")
    @PostMapping(path = "/employeeLogin", consumes = "application/json", produces = "application/json")
    public HashMap<String, String> EmployeeLogin(@RequestBody Employee emp) {
        HashMap<String, String> hashMap = new HashMap<>();
        List<Employee> empDetails = empdao.UserLoginDetails(emp.getUsername(), emp.getPassword());
        if (empDetails.size() == 0) {
            hashMap.put("status", "failed");
        } else {
            hashMap.put("status", "success");
            hashMap.put("userInfo", String.valueOf(empDetails.get(0).getId()));
        }
        return hashMap;

    }
    @CrossOrigin(origins = "*")
    @PostMapping(path = "/viewemployee", consumes = "application/json", produces = "application/json")
    public List<Employee> ViewEmployee(@RequestBody Employee em)
    {
        System.out.println(em.getId());
        return (List<Employee>) empdao.GetEmployeeProfile(em.getId());
    }

}
