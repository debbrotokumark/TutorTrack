package com.raselraihande.tutortrackbd.Model;

import com.google.gson.annotations.SerializedName;

public class LatestPostsResponse {

    @SerializedName("latestTuation")
    private LatestTuation latestTuation;

    @SerializedName("tuationError")
    private String tuationError;

    @SerializedName("latestTutor")
    private LatestTutor latestTutor;

    @SerializedName("tutorError")
    private String tutorError;

    // Getters and Setters
    public LatestTuation getLatestTuation() {
        return latestTuation;
    }

    public void setLatestTuation(LatestTuation latestTuation) {
        this.latestTuation = latestTuation;
    }

    public String getTuationError() {
        return tuationError;
    }

    public void setTuationError(String tuationError) {
        this.tuationError = tuationError;
    }

    public LatestTutor getLatestTutor() {
        return latestTutor;
    }

    public void setLatestTutor(LatestTutor latestTutor) {
        this.latestTutor = latestTutor;
    }

    public String getTutorError() {
        return tutorError;
    }

    public void setTutorError(String tutorError) {
        this.tutorError = tutorError;
    }

    // Nested Class for LatestTuation
    public static class LatestTuation {

        @SerializedName("id")
        private String id;

        @SerializedName("name")
        private String name;

        @SerializedName("details")
        private String details;

        @SerializedName("studyAt")
        private String studyAt;

        @SerializedName("location")
        private String location;

        @SerializedName("studentClass")
        private String studentClass;

        @SerializedName("weekDay")
        private String weekDay;

        @SerializedName("medium")
        private String medium;

        @SerializedName("subject")
        private String subject;

        @SerializedName("studentsNo")
        private String studentsNo;

        @SerializedName("salary")
        private String salary;

        @SerializedName("studyTime")
        private String studyTime;

        @SerializedName("number")
        private String number;

        @SerializedName("imageBase64")
        private String imageBase64;

        @SerializedName("postdate")
        private String postdate;

        // Getters and Setters
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getStudyAt() {
            return studyAt;
        }

        public void setStudyAt(String studyAt) {
            this.studyAt = studyAt;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getStudentClass() {
            return studentClass;
        }

        public void setStudentClass(String studentClass) {
            this.studentClass = studentClass;
        }

        public String getWeekDay() {
            return weekDay;
        }

        public void setWeekDay(String weekDay) {
            this.weekDay = weekDay;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getStudentsNo() {
            return studentsNo;
        }

        public void setStudentsNo(String studentsNo) {
            this.studentsNo = studentsNo;
        }

        public String getSalary() {
            return salary;
        }

        public void setSalary(String salary) {
            this.salary = salary;
        }

        public String getStudyTime() {
            return studyTime;
        }

        public void setStudyTime(String studyTime) {
            this.studyTime = studyTime;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getImageBase64() {
            return imageBase64;
        }

        public void setImageBase64(String imageBase64) {
            this.imageBase64 = imageBase64;
        }

        public String getPostdate() {
            return postdate;
        }

        public void setPostdate(String postdate) {
            this.postdate = postdate;
        }
    }

    // Nested Class for LatestTutor
    public static class LatestTutor {

        @SerializedName("id")
        private String id;

        @SerializedName("name")
        private String name;

        @SerializedName("details")
        private String details;

        @SerializedName("studyAt")
        private String studyAt;

        @SerializedName("location")
        private String location;

        @SerializedName("studentClass")
        private String studentClass;

        @SerializedName("weekDay")
        private String weekDay;

        @SerializedName("medium")
        private String medium;

        @SerializedName("subject")
        private String subject;

        @SerializedName("salary")
        private String salary;

        @SerializedName("number")
        private String number;

        @SerializedName("imageBase64")
        private String imageBase64;

        @SerializedName("postdate")
        private String postdate;

        // Getters and Setters
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getStudyAt() {
            return studyAt;
        }

        public void setStudyAt(String studyAt) {
            this.studyAt = studyAt;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getStudentClass() {
            return studentClass;
        }

        public void setStudentClass(String studentClass) {
            this.studentClass = studentClass;
        }

        public String getWeekDay() {
            return weekDay;
        }

        public void setWeekDay(String weekDay) {
            this.weekDay = weekDay;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getSalary() {
            return salary;
        }

        public void setSalary(String salary) {
            this.salary = salary;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getImageBase64() {
            return imageBase64;
        }

        public void setImageBase64(String imageBase64) {
            this.imageBase64 = imageBase64;
        }

        public String getPostdate() {
            return postdate;
        }

        public void setPostdate(String postdate) {
            this.postdate = postdate;
        }
    }
}
