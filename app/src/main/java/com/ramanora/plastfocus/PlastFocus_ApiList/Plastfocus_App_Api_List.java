package com.ramanora.plastfocus.PlastFocus_ApiList;

public interface Plastfocus_App_Api_List {
    String BAse_Url = "https://ramanora.com/plastfocus/public/api/";
    String Api_Login = BAse_Url+"login";

    String Api_ExhibitorNotification =BAse_Url+"notifications/exhibitor";
    String Api_VisitorNotification =BAse_Url+"notifications/visitor";
    String Api_Registration = BAse_Url+"register";
    String Api_ExhibitorLogin =BAse_Url+ "exhibitors/login";
    String Api_ExhibitorList = BAse_Url+"exhibitors/";
    String Api_VisitorList = BAse_Url+"visited-exhibitor";
    String Api_BookMeeting = BAse_Url+"book-meeting";
    String Api_MeetingDatesAndTime = BAse_Url+"schedule";
    String Api_QrcodeDatUploadServer = BAse_Url+"store-visited-exhibitor";
    String Api_BannersImageApi = BAse_Url+"banners";
    String Api_RemoveAccount =BAse_Url+ "remove-account";
    String Api_VIsitorLogout = BAse_Url+"logout-account";
    String Api_ExhibitorLogout = BAse_Url+"exhibitors/logout";
    String Api_VisitorMeetings = BAse_Url;
    String Api_ExhibitorMeetings = BAse_Url+"exhibitors/";
    String BAseURlBAnnerLOgo = "https://ramanora.com/plastfocus/public/";


    // contants define
    String PUSH_NOTIFICATION = "pushNotification";
    public static final String REGISTRATION_COMPLETE = "registrationComplete";

    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;
}
