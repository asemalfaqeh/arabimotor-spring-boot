package com.af.arabimotors.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ContactUsRequest {

    @NotBlank(message = "يرجى ادخال الاسم الكامل")
    @Size(max = 125)
    private String name;
    @NotBlank(message = "يرجى ادخال البريد الالكتروني")
    @Size(max = 50)
    private String email;
    @NotBlank(message = "يرجى ادخال الرسالة")
    @Size(max = 500)
    private String message;
    @NotBlank(message = "يرجى ادخال الموضوع")
    @Size(max = 200)
    private String subject;
    @NotBlank(message = "يرجى ادخال رقم الهاتف المحمول")
    @Size(max = 20)
    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "ContactUsRequest{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", message='" + message + '\'' +
                ", subject='" + subject + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
