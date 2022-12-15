package com.example.NestDigitalApp.Controller;

import com.example.NestDigitalApp.Dao.Leave1Dao;
import com.example.NestDigitalApp.Dao.LeaveDao;
import com.example.NestDigitalApp.Model.Employee;
import com.example.NestDigitalApp.Model.Leave;
import com.example.NestDigitalApp.Model.Leaves1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter ytf = DateTimeFormatter.ofPattern("yyyy");

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/applyLeave", produces = "application/json", consumes = "application/json")
    public HashMap<String, String> ApplyLeave(@RequestBody Leave lv){
        HashMap<String, String> hashMap = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();
        String leaveType = lv.getLeaveType();
        List<Leaves1> l1 = (List<Leaves1>) l1dao.GetLeaveDetails(lv.getEmpId(),ytf.format(now));
        LocalDate dateBefore = LocalDate.parse(lv.getFromDate());
        LocalDate dateAfter = LocalDate.parse(lv.getToDate());
        int daysOfLeave = (int) ChronoUnit.DAYS.between(dateBefore, dateAfter)+1;
        if(leaveType.equals("casualLeave") && (l1.get(0).getCasualLeave()-daysOfLeave)>=0){
            lv.setApplyDate(dtf.format(now));
            lv.setLeaveStatus(0);
            ldao.save(lv);
            hashMap.put("status","success");
        } else if (leaveType.equals("sickLeave") && (l1.get(0).getSickLeave()-daysOfLeave)>=0) {
            lv.setApplyDate(dtf.format(now));
            lv.setLeaveStatus(0);
            ldao.save(lv);
            hashMap.put("status","success");
        }else if (leaveType.equals("specialLeave") && (l1.get(0).getSpecialLeave()-daysOfLeave)>=0){
            lv.setApplyDate(dtf.format(now));
            lv.setLeaveStatus(0);
            ldao.save(lv);
            hashMap.put("status","success");
        }
        else{
            hashMap.put("status","failed");
        }
        return hashMap;
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/getLeaveDetails", produces = "application/json", consumes = "application/json")
    public List<Leaves1> GetLeaveDetails(@RequestBody Employee emp){
        LocalDateTime now = LocalDateTime.now();
        List<Leaves1> leave =  l1dao.GetLeaveDetails(Integer.valueOf(emp.getId()),ytf.format(now));
        if(leave.size()==0){
            Leaves1 l1 = new Leaves1();
            l1.setEmpId(String.valueOf(emp.getId()));
            l1.setYear(ytf.format(now));
            l1.setCasualLeave(20);
            l1.setSickLeave(7);
            l1.setSpecialLeave(3);
            l1dao.save(l1);
            List<Leaves1> temp_leave =  l1dao.GetLeaveDetails(Integer.valueOf(emp.getId()),ytf.format(now));
            return temp_leave;
        }else{
            return leave;
        }
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
    public HashMap<String, String> UpdateLeaves(@RequestBody Leave lv) throws ParseException {
        HashMap<String, String> hashMap = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();
        String leaveType = lv.getLeaveType();
        List<Leaves1> l1 = (List<Leaves1>) l1dao.GetLeaveDetails(lv.getEmpId(),ytf.format(now));
        LocalDate dateBefore = LocalDate.parse(lv.getFromDate());
        LocalDate dateAfter = LocalDate.parse(lv.getToDate());
        int daysOfLeave = (int) ChronoUnit.DAYS.between(dateBefore, dateAfter)+1;
        if(lv.getLeaveStatus()==1){
            if(leaveType.equals("casualLeave") && (l1.get(0).getCasualLeave()-daysOfLeave)>=0){
                ldao.UpdateLeaves(lv.getId(), lv.getLeaveStatus());
                l1dao.UpdateLeave(lv.getEmpId(),(l1.get(0).getCasualLeave()-daysOfLeave),l1.get(0).getSickLeave(),l1.get(0).getSpecialLeave());
                hashMap.put("status","success");
            } else if (leaveType.equals("sickLeave") && (l1.get(0).getSickLeave()-daysOfLeave)>=0) {
                ldao.UpdateLeaves(lv.getId(), lv.getLeaveStatus());
                l1dao.UpdateLeave(lv.getEmpId(),l1.get(0).getCasualLeave(),(l1.get(0).getSickLeave()-daysOfLeave),l1.get(0).getSpecialLeave());
                hashMap.put("status","success");
            }else if (leaveType.equals("specialLeave") && (l1.get(0).getSpecialLeave()-daysOfLeave)>=0){
                ldao.UpdateLeaves(lv.getId(), lv.getLeaveStatus());
                l1dao.UpdateLeave(lv.getEmpId(),l1.get(0).getCasualLeave(),l1.get(0).getSickLeave(),(l1.get(0).getSpecialLeave()-daysOfLeave));
                hashMap.put("status","success");
            }
            else{
                hashMap.put("status","failed");
            }
        }else if (lv.getLeaveStatus()==-1){
            ldao.UpdateLeaves(lv.getId(), lv.getLeaveStatus());
        }
        return hashMap;
    }
}
