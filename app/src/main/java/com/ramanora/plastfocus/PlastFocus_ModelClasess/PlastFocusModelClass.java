package com.ramanora.plastfocus.PlastFocus_ModelClasess;

import java.io.Serializable;

/**
 * Created by admin on 9/14/2017.
 */

public class PlastFocusModelClass implements Serializable {

    String Mid,productnames;

    public String getProductnames() {
        return productnames;
    }

    public void setProductnames(String productnames) {
        this.productnames = productnames;
    }

    public String getMeetingtitle() {
        return meetingtitle;
    }

    public void setMeetingtitle(String meetingtitle) {
        this.meetingtitle = meetingtitle;
    }

    String meetingtitle;
    String meetingdates;

    public String getMeetingdates() {
        return meetingdates;
    }

    public void setMeetingdates(String meetingdates) {
        this.meetingdates = meetingdates;
    }

    public String getMeetingtimes() {
        return meetingtimes;
    }

    public void setMeetingtimes(String meetingtimes) {
        this.meetingtimes = meetingtimes;
    }

    String meetingtimes;

    public String getMid() {
        return Mid;
    }

    public void setMid(String mid) {
        Mid = mid;
    }

    String visitor_id;
    String exhibitor_id;

    public String getVisitor_id() {
        return visitor_id;
    }

    public void setVisitor_id(String visitor_id) {
        this.visitor_id = visitor_id;
    }

    public String getExhibitor_id() {
        return exhibitor_id;
    }

    public void setExhibitor_id(String exhibitor_id) {
        this.exhibitor_id = exhibitor_id;
    }

    public String getMeeting_date() {
        return meeting_date;
    }

    public void setMeeting_date(String meeting_date) {
        this.meeting_date = meeting_date;
    }

    public String getMeeting_time() {
        return meeting_time;
    }

    public void setMeeting_time(String meeting_time) {
        this.meeting_time = meeting_time;
    }

    public String getMeeting_start_date() {
        return meeting_start_date;
    }

    public void setMeeting_start_date(String meeting_start_date) {
        this.meeting_start_date = meeting_start_date;
    }

    public String getMeeting_status() {
        return meeting_status;
    }

    public void setMeeting_status(String meeting_status) {
        this.meeting_status = meeting_status;
    }

    String meeting_date;
    String meeting_time;
    String meeting_start_date;
    String meeting_status;
String count
        ,company_name, salutation, coordinator_name, coordinator_last_name, coordinator_designation, address, country, state, city, pincode, mobile_number, alternate_number, fax, email, alternate_email, website, product_group, company_profile, hall, stall_number, cord_x, cord_y;
    private String mtxt,id, Notification,tv_itemname;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTv_itemname() {
        return tv_itemname;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getCoordinator_name() {
        return coordinator_name;
    }

    public void setCoordinator_name(String coordinator_name) {
        this.coordinator_name = coordinator_name;
    }

    public String getCoordinator_last_name() {
        return coordinator_last_name;
    }

    public void setCoordinator_last_name(String coordinator_last_name) {
        this.coordinator_last_name = coordinator_last_name;
    }

    public String getCoordinator_designation() {
        return coordinator_designation;
    }

    public void setCoordinator_designation(String coordinator_designation) {
        this.coordinator_designation = coordinator_designation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getAlternate_number() {
        return alternate_number;
    }

    public void setAlternate_number(String alternate_number) {
        this.alternate_number = alternate_number;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlternate_email() {
        return alternate_email;
    }

    public void setAlternate_email(String alternate_email) {
        this.alternate_email = alternate_email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getProduct_group() {
        return product_group;
    }

    public void setProduct_group(String product_group) {
        this.product_group = product_group;
    }

    public String getCompany_profile() {
        return company_profile;
    }

    public void setCompany_profile(String company_profile) {
        this.company_profile = company_profile;
    }

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }

    public String getStall_number() {
        return stall_number;
    }

    public void setStall_number(String stall_number) {
        this.stall_number = stall_number;
    }

    public String getCord_x() {
        return cord_x;
    }

    public void setCord_x(String cord_x) {
        this.cord_x = cord_x;
    }

    public String getCord_y() {
        return cord_y;
    }

    public void setCord_y(String cord_y) {
        this.cord_y = cord_y;
    }

    public void setTv_itemname(String tv_itemname) {
        this.tv_itemname = tv_itemname;
    }

    private int mImg;


    public PlastFocusModelClass(String mtxt, int mImg) {
        this.mtxt = mtxt;
        this.mImg = mImg;
    }

    public PlastFocusModelClass() {

    }

    public String getNotification() {
        return Notification;
    }

    public void setNotification(String notification) {
        Notification = notification;
    }
    public String getMtxt() {
        return mtxt;
    }

    public void setMtxt(String mtxt) {
        this.mtxt = mtxt;
    }

    public int getmImg() {
        return mImg;
    }

    public void setmImg(int mImg) {
        this.mImg = mImg;
    }
}
