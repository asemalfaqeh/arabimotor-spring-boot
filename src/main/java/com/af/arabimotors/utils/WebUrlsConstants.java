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
      String CHANGE_PASSWORD = "/change-password";
      String SOCIAL_MEDIA_LINKS = "/user-social";
      String CONFIRM_ACCOUNT = "/confirm-account";
      String ERROR_PAGE = "/error-404";
      String RESEND_VERIFY_EMAIL = "/resend-verify-email";
      String VERIFY_EMAIL_LINK = "http://localhost:8080/confirm-account?token=";
      String RESET_PASSWORD_LINK = "http://localhost:8080/reset-password?token=";
      String FORGOT_PASSWORD = "/forgot-password";
      String RESET_PASSWORD_POST = "/reset-password";
      String SUBMIT_VEHICLE = "/post-vehicle";
      String VEHICLE_DETAILS = "/vehicle-details";
      String ALL_VEHICLES = "/all-vehicles";
      String BODY_TYPE = "/body-types";
      String VEHICLES_SORT = "/sort-vehicles";
      String ADVANCED_SEARCH = "/advanced-search";
      String MESSAGES = "/messages";
      String DELETE_VEHICLE = "/delete-vehicle";
      String MY_VEHICLES = "/my-vehicles";
      String EDIT_VEHICLE = "/edit-vehicle";
}
