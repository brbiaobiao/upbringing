package com.teaching.upbringing.entity;

public class IdentityAuthStatusEntity {

    /**
     * studentRepulse : {"applyType":0,"id":0,"ifFirst":false,"remark":"string","reviewStatus":0}
     * teacherRepulse : {"applyType":0,"id":0,"ifFirst":false,"remark":"string","reviewStatus":0}
     */

    private StudentRepulseBean studentRepulse;
    private TeacherRepulseBean teacherRepulse;

    public StudentRepulseBean getStudentRepulse() {
        return studentRepulse;
    }

    public void setStudentRepulse(StudentRepulseBean studentRepulse) {
        this.studentRepulse = studentRepulse;
    }

    public TeacherRepulseBean getTeacherRepulse() {
        return teacherRepulse;
    }

    public void setTeacherRepulse(TeacherRepulseBean teacherRepulse) {
        this.teacherRepulse = teacherRepulse;
    }

    public static class StudentRepulseBean {
        /**
         * applyType : 0
         * id : 0
         * ifFirst : false
         * remark : string
         * reviewStatus : 0
         */

        private int applyType;
        private int id;
        private boolean ifFirst;
        private String remark;
        private int reviewStatus;

        public int getApplyType() {
            return applyType;
        }

        public void setApplyType(int applyType) {
            this.applyType = applyType;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isIfFirst() {
            return ifFirst;
        }

        public void setIfFirst(boolean ifFirst) {
            this.ifFirst = ifFirst;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getReviewStatus() {
            return reviewStatus;
        }

        public void setReviewStatus(int reviewStatus) {
            this.reviewStatus = reviewStatus;
        }
    }

    public static class TeacherRepulseBean {
        /**
         * applyType : 0
         * id : 0
         * ifFirst : false
         * remark : string
         * reviewStatus : 0
         */

        private int applyType;
        private int id;
        private boolean ifFirst;
        private String remark;
        private int reviewStatus;

        public int getApplyType() {
            return applyType;
        }

        public void setApplyType(int applyType) {
            this.applyType = applyType;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isIfFirst() {
            return ifFirst;
        }

        public void setIfFirst(boolean ifFirst) {
            this.ifFirst = ifFirst;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getReviewStatus() {
            return reviewStatus;
        }

        public void setReviewStatus(int reviewStatus) {
            this.reviewStatus = reviewStatus;
        }
    }
}
