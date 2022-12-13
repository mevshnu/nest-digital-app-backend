package com.example.NestDigitalApp.Controller;

import com.example.NestDigitalApp.Dao.Leave1Dao;
import com.example.NestDigitalApp.Dao.LeaveDao;
import com.example.NestDigitalApp.Model.Employee;
import com.example.NestDigitalApp.Model.Leave;
import com.example.NestDigitalApp.Model.Leaves1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class LeaveController {
    @Autowired
    private LeaveDao ldao;
    @Autowired
    private Leave1Dao l1dao;

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/applyLeave", produces = "application/json", consumes = "application/json")
    public HashMap<String, String> ApplyLeave(@RequestBody Leave lv){
        ldao.save(lv);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("status","success");
        return hashMap;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getAllLeaves")
    public List<Map<String, String>> GetAllLeaves(){
        return ldao.GetAllLeaves();
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/getEmployeeLeaves", produces = "application/json", consumes = "application/json")
    public List<Leave> GetEmployeeLeaves(@RequestBody Employee emp){
        return ldao.GetEmployeeLeaves(Integer.valueOf(emp.getId()));
    }
    @CrossOrigin(origins = "*")
    @PostMapping(path = "/updateLeaves", produces = "application/json", consumes = "application/json")
    public HashMap<String, String> UpdateLeaves(@RequestBody Leave lv){
        ldao.UpdateLeaves(lv.getId(), lv.getLeaveStatus());
        String leaveType = lv.getLeaveType();
        System.out.println(lv.getFromDate());
        System.out.println(lv.getToDate());
        LocalDate dateBefore = LocalDate.parse(lv.getFromDate());
        LocalDate dateAfter = LocalDate.parse(lv.getToDate());
        int daysOfLeave = (int) ChronoUnit.DAYS.between(dateBefore, dateAfter)+1;
        List<Leaves1> l1 = (List<Leaves1>) l1dao.GetLeaveDetails(lv.getEmpId());
        System.out.println(l1.get(0).getCasualLeave()-daysOfLeave);
        if(leaveType.equals("casualLeave")){
            l1dao.UpdateLeave(lv.getEmpId(),(l1.get(0).getCasualLeave()-daysOfLeave),l1.get(0).getSickLeave(),l1.get(0).getSpecialLeave());
        } else if (leaveType.equals("sickLeave")) {
            l1dao.UpdateLeave(lv.getEmpId(),l1.get(0).getCasualLeave(),(l1.get(0).getSickLeave()-daysOfLeave),l1.get(0).getSpecialLeave());
        }else if (leaveType.equals("specialLeave")){
            l1.get(0).setSpecialLeave(l1.get(0).getSickLeave()-daysOfLeave);
            l1dao.UpdateLeave(lv.getEmpId(),l1.get(0).getCasualLeave(),l1.get(0).getSickLeave(),(l1.get(0).getSickLeave()-daysOfLeave));
        }
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("status","success");
        return hashMap;
    }

}
