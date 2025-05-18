package com.raselraihande.tutortrackbd.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TutorResponse {

    @SerializedName("allposts")
    List<TutorPostModel> tutorPostModels;

    @SerializedName("error")
    String error;

    // Constructor
    public TutorResponse(String error, List<TutorPostModel> tutorPostModels) {
        this.error = error;
        this.tutorPostModels = tutorPostModels;
    }

    // Getters and Setters
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<TutorPostModel> getTutorPostModels() {
        return tutorPostModels;
    }

    public void setTutorPostModels(List<TutorPostModel> tutorPostModels) {
        this.tutorPostModels = tutorPostModels;
    }

    // Nested TutorPostModel class
    public static class TutorPostModel {

        private String id;
        private String name;
        private String details;
        private String studyAt;
        private String location;
        private String studentClass;
        private String weekDay;
        private String medium;
        private String subject;
        private String salary;
        private String number;
        private String imageBase64;
        private String postdate;

        // Constructor
        public TutorPostModel(String id, String name, String details, String studyAt, String location,
                              String studentClass, String weekDay, String medium, String subject,
                               String salary, String number, String imageBase64,
                              String postdate) {
            this.id = id;
            this.name = name;
            this.details = details;
            this.studyAt = studyAt;
            this.location = location;
            this.studentClass = studentClass;
            this.weekDay = weekDay;
            this.medium = medium;
            this.subject = subject;
            this.salary = salary;
            this.number = number;
            this.imageBase64 = imageBase64;
            this.postdate = postdate;
        }

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

        public String getSearchlist() {

            return name + details+studentClass+subject+number + location + studyAt;


        }

    }
}
