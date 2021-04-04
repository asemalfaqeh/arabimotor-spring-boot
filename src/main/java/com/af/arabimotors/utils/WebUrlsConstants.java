package com.af.arabimotors.utils;

public interface WebUrlsConstants {

	  // Public Routes
      String LOGIN = "/login";
      String SIGN_UP = "/signup";
      //ADMIN Routes //
      String ADMIN_DASHBOARD = "/dashboard";
      String ADMIN_CONDITIONS = "/conditions";
      String ADMIN_ADD_CONDTITION = "/NewCondition";
      String ADMIN_POST_NEW_CONDITION = "/SaveCondition";
      String ADMIN_EDIT_CONDITION = "/EditCondition";
      String ADMIN_DELETE_CODNITION_BY_ID = "/DeleteCondition";
      // USER Routes //
      String WEB_HOME_PAGE = "/home";
      String BASE_URL = "/";
      String ACCOUNT_DETAILS = "/account-details";
      String UPDATE_USER_PROFILE = "/account-update";
      String UPDATE_PHOTO_PROFILE = "/update-photo";
      String UPLOAD_PHOTO = "/user-photos/**";
    
}
