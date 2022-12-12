package com.example.NestDigitalApp.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    @Entity
    @Table(name = "leavedetails")
    public class Leaves1 {
        @Id
        @GeneratedValue()
        private int id;
        private String empCode;
        private String year;
        private int casualLeave;
        private int sickLeave;
        private int specialLeave;

        public Leaves1() {
        }

        public Leaves1(int id, String empCode, String year, int casualLeave, int sickLeave, int specialLeave) {
            this.id = id;
            this.empCode = empCode;
            this.year = year;
            this.casualLeave = casualLeave;
            this.sickLeave = sickLeave;
            this.specialLeave = specialLeave;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getEmpCode() {
            return empCode;
        }

        public void setEmpCode(String empCode) {
            this.empCode = empCode;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public int getCasualLeave() {
            return casualLeave;
        }

        public void setCasualLeave(int casualLeave) {
            this.casualLeave = casualLeave;
        }

        public int getSickLeave() {
            return sickLeave;
        }

        public void setSickLeave(int sickLeave) {
            this.sickLeave = sickLeave;
        }

        public int getSpecialLeave() {
            return specialLeave;
        }

        public void setSpecialLeave(int specialLeave) {
            this.specialLeave = specialLeave;
        }
    }


