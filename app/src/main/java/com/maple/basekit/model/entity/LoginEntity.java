package com.maple.basekit.model.entity;

import com.maple.baselib.http.resp.BaseResp;

import java.util.Date;

public class LoginEntity extends BaseResp<LoginEntity>{


    private String userId;
    private String idNo;
    private String name;
    private String idAddr;
    private String addr;
    private String phone;
    private String roleRange;
    private String policeOfficeId;
    private String orgId;
    private String idType;
    private String birthdate;
    private int sex;
    private String orgName;
    private String policeName;
    private String saToken;
    private String idNoPic1;
    private String idNoPic2;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdAddr() {
        return idAddr;
    }

    public void setIdAddr(String idAddr) {
        this.idAddr = idAddr;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRoleRange() {
        return roleRange;
    }

    public void setRoleRange(String roleRange) {
        this.roleRange = roleRange;
    }

    public String getPoliceOfficeId() {
        return policeOfficeId;
    }

    public void setPoliceOfficeId(String policeOfficeId) {
        this.policeOfficeId = policeOfficeId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getPoliceName() {
        return policeName;
    }

    public void setPoliceName(String policeName) {
        this.policeName = policeName;
    }

    public String getSaToken() {
        return saToken;
    }

    public void setSaToken(String saToken) {
        this.saToken = saToken;
    }

    public String getIdNoPic1() {
        return idNoPic1;
    }

    public void setIdNoPic1(String idNoPic1) {
        this.idNoPic1 = idNoPic1;
    }

    public String getIdNoPic2() {
        return idNoPic2;
    }

    public void setIdNoPic2(String idNoPic2) {
        this.idNoPic2 = idNoPic2;
    }

    @Override
    public String toString() {
        return "LoginEntity{" +
                "userId='" + userId + '\'' +
                ", idNo='" + idNo + '\'' +
                ", name='" + name + '\'' +
                ", idAddr='" + idAddr + '\'' +
                ", addr='" + addr + '\'' +
                ", phone='" + phone + '\'' +
                ", roleRange='" + roleRange + '\'' +
                ", policeOfficeId='" + policeOfficeId + '\'' +
                ", orgId='" + orgId + '\'' +
                ", idType='" + idType + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", sex=" + sex +
                ", orgName='" + orgName + '\'' +
                ", policeName='" + policeName + '\'' +
                ", saToken='" + saToken + '\'' +
                ", idNoPic1='" + idNoPic1 + '\'' +
                ", idNoPic2='" + idNoPic2 + '\'' +
                '}';
    }
}
