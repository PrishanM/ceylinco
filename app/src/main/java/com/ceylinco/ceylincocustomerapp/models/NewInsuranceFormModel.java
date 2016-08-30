package com.ceylinco.ceylincocustomerapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Prishan Maduka on 8/30/2016.
 */
public class NewInsuranceFormModel implements Parcelable {

    private String vType;
    private String vNo;
    private String regType;
    private String name;
    private String contactNo;
    private String email;
    private String address;
    private String nic;
    private String chasisNo;
    private String engineNo;
    private String makeYear;
    private String branch;
    private String location;
    private String make;
    private String model;
    private String vehValue;
    private String leasingCompany;
    private String vehCondition;
    private String purpose;
    private String currMeter;
    private String firstRegDate;

    public String getvType() {
        return vType;
    }

    public void setvType(String vType) {
        this.vType = vType;
    }

    public String getvNo() {
        return vNo;
    }

    public void setvNo(String vNo) {
        this.vNo = vNo;
    }

    public String getRegType() {
        return regType;
    }

    public void setRegType(String regType) {
        this.regType = regType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getChasisNo() {
        return chasisNo;
    }

    public void setChasisNo(String chasisNo) {
        this.chasisNo = chasisNo;
    }

    public String getEngineNo() {
        return engineNo;
    }

    public void setEngineNo(String engineNo) {
        this.engineNo = engineNo;
    }

    public String getMakeYear() {
        return makeYear;
    }

    public void setMakeYear(String makeYear) {
        this.makeYear = makeYear;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVehValue() {
        return vehValue;
    }

    public void setVehValue(String vehValue) {
        this.vehValue = vehValue;
    }

    public String getLeasingCompany() {
        return leasingCompany;
    }

    public void setLeasingCompany(String leasingCompany) {
        this.leasingCompany = leasingCompany;
    }

    public String getVehCondition() {
        return vehCondition;
    }

    public void setVehCondition(String vehCondition) {
        this.vehCondition = vehCondition;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getCurrMeter() {
        return currMeter;
    }

    public void setCurrMeter(String currMeter) {
        this.currMeter = currMeter;
    }

    public String getFirstRegDate() {
        return firstRegDate;
    }

    public void setFirstRegDate(String firstRegDate) {
        this.firstRegDate = firstRegDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.vType);
        dest.writeString(this.vNo);
        dest.writeString(this.regType);
        dest.writeString(this.name);
        dest.writeString(this.contactNo);
        dest.writeString(this.email);
        dest.writeString(this.address);
        dest.writeString(this.nic);
        dest.writeString(this.chasisNo);
        dest.writeString(this.engineNo);
        dest.writeString(this.makeYear);
        dest.writeString(this.branch);
        dest.writeString(this.location);
        dest.writeString(this.make);
        dest.writeString(this.model);
        dest.writeString(this.vehValue);
        dest.writeString(this.leasingCompany);
        dest.writeString(this.vehCondition);
        dest.writeString(this.purpose);
        dest.writeString(this.currMeter);
        dest.writeString(this.firstRegDate);
    }

    public NewInsuranceFormModel() {
    }

    protected NewInsuranceFormModel(Parcel in) {
        this.vType = in.readString();
        this.vNo = in.readString();
        this.regType = in.readString();
        this.name = in.readString();
        this.contactNo = in.readString();
        this.email = in.readString();
        this.address = in.readString();
        this.nic = in.readString();
        this.chasisNo = in.readString();
        this.engineNo = in.readString();
        this.makeYear = in.readString();
        this.branch = in.readString();
        this.location = in.readString();
        this.make = in.readString();
        this.model = in.readString();
        this.vehValue = in.readString();
        this.leasingCompany = in.readString();
        this.vehCondition = in.readString();
        this.purpose = in.readString();
        this.currMeter = in.readString();
        this.firstRegDate = in.readString();
    }

    public static final Parcelable.Creator<NewInsuranceFormModel> CREATOR = new Parcelable.Creator<NewInsuranceFormModel>() {
        @Override
        public NewInsuranceFormModel createFromParcel(Parcel source) {
            return new NewInsuranceFormModel(source);
        }

        @Override
        public NewInsuranceFormModel[] newArray(int size) {
            return new NewInsuranceFormModel[size];
        }
    };
}
