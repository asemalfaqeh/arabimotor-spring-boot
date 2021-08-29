package com.af.arabimotors.model.request;

import com.af.arabimotors.entities.BloggerEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class BlogCommentRequest {

    private BloggerEntity bloggerEntity;

    @NotBlank(message = "يرجي ادخال الاسم")
    @Size(max = 50)
    private String name;
    @NotBlank(message = "يرجي ادخال بريد الالكتروني")
    @Size(max = 50)
    private String email;
    @NotBlank(message = "يرجى كتابة التعليق")
    @Size(max = 50)
    private String comment;

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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public BloggerEntity getBloggerEntity() {
        return bloggerEntity;
    }

    public void setBloggerEntity(BloggerEntity bloggerEntity) {
        this.bloggerEntity = bloggerEntity;
    }

    @Override
    public String toString() {
        return "BlogCommentRequest{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
