package com.jiecheng.zhike.mydemo.httpJson.model;

/**
 * Created by 13159 on 2017/8/31.
 */

public class Bank {
    private String bankid;
    private String bankname;
    private String classname;
    private String cyuserid;
    private String datecreated;
    private String userid;
    private String username;
    private String version;
    private String workid;
    private String workname;
    private String wtype;
    private String qtype;
    private String schoolid;
    private String classid;
    private String classtype;
    private String cyclassname;
    private String pic_url;
    private String service_mac;
//    private String json="{\"code\":0,\"data\":[{\"bankid\":\"00\",\"bankname\":\"其他\",\"classname\":\"智慧课堂\",\"cyuserid\":\"1010000001000161460\",\"datecreated\":\"1503731400000\",\"userid\":\"1010000001000161460\",\"username\":\"zktsmychen\",\"version\":\"2.5\",\"workid\":\"55b7b201-01a1-4861-9f53-41121823f50a\",\"workname\":\"选择题\",\"wtype\":\"all\",\"qtype\":\"choice\",\"schoolid\":\"1010000001000000174\",\"classid\":\"freeclass\",\"classtype\":\"class_type_zy\",\"cyclassname\":\"\",\"pic_url\":\"61f55fbae14bf531eb847c83d5c4b24a.jpg\",\"service_mac\":\"285714562\"},{\"bankid\":\"00\",\"bankname\":\"其他\",\"classname\":\"智慧课堂\",\"cyuserid\":\"1010000001000161460\",\"datecreated\":\"1503731129000\",\"userid\":\"1010000001000161460\",\"username\":\"zktsmychen\",\"version\":\"2.5\",\"workid\":\"160110e6-c7e8-49a4-a7df-bb74e01c9856\",\"workname\":\"选择题\",\"wtype\":\"all\",\"qtype\":\"choice\",\"schoolid\":\"1010000001000000174\",\"classid\":\"freeclass\",\"classtype\":\"class_type_zy\",\"cyclassname\":\"\",\"pic_url\":\"9a8b0f236145402e3bbae0938a9c8b93.jpg\",\"service_mac\":\"285714562\"},{\"bankid\":\"00\",\"bankname\":\"其他\",\"classname\":\"智慧课堂\",\"cyuserid\":\"1010000001000161460\",\"datecreated\":\"1503730491000\",\"userid\":\"1010000001000161460\",\"username\":\"zktsmychen\",\"version\":\"2.5\",\"workid\":\"740e9b64-52b0-48d9-b9d2-b19db7e466af\",\"workname\":\"判断题\",\"wtype\":\"all\",\"qtype\":\"judge\",\"schoolid\":\"1010000001000000174\",\"classid\":\"freeclass\",\"classtype\":\"class_type_zy\",\"cyclassname\":\"\",\"pic_url\":\"615b70653c6b89f3ae39b18677e4efa7.jpg\",\"service_mac\":\"285714562\"},{\"bankid\":\"00\",\"bankname\":\"其他\",\"classname\":\"智慧课堂\",\"cyuserid\":\"1010000001000161460\",\"datecreated\":\"1503729931000\",\"userid\":\"1010000001000161460\",\"username\":\"zktsmychen\",\"version\":\"2.5\",\"workid\":\"97b0481b-8be6-4dea-a298-83fe636585d0\",\"workname\":\"选择题\",\"wtype\":\"all\",\"qtype\":\"choice\",\"schoolid\":\"1010000001000000174\",\"classid\":\"freeclass\",\"classtype\":\"class_type_zy\",\"cyclassname\":\"\",\"pic_url\":\"db4bf4ea3e79bc991ed2e443bcb46b33.jpg\",\"service_mac\":\"285714562\"},{\"bankid\":\"00\",\"bankname\":\"其他\",\"classname\":\"智慧课堂\",\"cyuserid\":\"1010000001000161460\",\"datecreated\":\"1503729729000\",\"userid\":\"1010000001000161460\",\"username\":\"zktsmychen\",\"version\":\"2.5\",\"workid\":\"9590d24e-ef0e-4a82-a041-8577143192a0\",\"workname\":\"选择题\",\"wtype\":\"all\",\"qtype\":\"choice\",\"schoolid\":\"1010000001000000174\",\"classid\":\"freeclass\",\"classtype\":\"class_type_zy\",\"cyclassname\":\"\",\"pic_url\":\"c07c6d1c307c58ef6737721cd6a720aa.jpg\",\"service_mac\":\"285714562\"},{\"bankid\":\"00\",\"bankname\":\"其他\",\"classname\":\"离线正\",\"cyuserid\":\"1010000001000161460\",\"datecreated\":\"1503729258000\",\"userid\":\"1010000001000161460\",\"username\":\"zktsmychen\",\"version\":\"2.5\",\"workid\":\"24fa6b10-0d0e-4d2a-b131-c247b07d8d38\",\"workname\":\"选择题\",\"wtype\":\"all\",\"qtype\":\"choice\",\"schoolid\":\"1010000001000000174\",\"classid\":\"freeclass\",\"classtype\":\"class_type_zy\",\"cyclassname\":\"\",\"pic_url\":\"3c8a86e93e92dfb1ef061a14fa12b3fc.jpg\",\"service_mac\":\"283095915858463\"},{\"bankid\":\"00\",\"bankname\":\"其他\",\"classname\":\"离线正\",\"cyuserid\":\"1010000001000161460\",\"datecreated\":\"1503727867000\",\"userid\":\"1010000001000161460\",\"username\":\"zktsmychen\",\"version\":\"2.5\",\"workid\":\"4dfa253f-ab6a-439e-90f8-4fa48ff1cccf\",\"workname\":\"选择题\",\"wtype\":\"all\",\"qtype\":\"choice\",\"schoolid\":\"1010000001000000174\",\"classid\":\"freeclass\",\"classtype\":\"class_type_zy\",\"cyclassname\":\"\",\"pic_url\":\"553290f8ee3f6f4b62a77b60bf7c959e.jpg\",\"service_mac\":\"283095915858463\"},{\"bankid\":\"00\",\"bankname\":\"其他\",\"classname\":\"离线正\",\"cyuserid\":\"1010000001000161460\",\"datecreated\":\"1503574492000\",\"userid\":\"1010000001000161460\",\"username\":\"zktsmychen\",\"version\":\"2.5\",\"workid\":\"82e8f49f-dbf0-4d7f-9605-720e063cc98d\",\"workname\":\"选择题\",\"wtype\":\"all\",\"qtype\":\"choice\",\"schoolid\":\"1010000001000000174\",\"classid\":\"freeclass\",\"classtype\":\"class_type_zy\",\"cyclassname\":\"\",\"pic_url\":\"17c9bae6a830396c8ab0be1fb50e722f.jpg\",\"service_mac\":\"283095915858463\"},{\"bankid\":\"00\",\"bankname\":\"其他\",\"classname\":\"离线正\",\"cyuserid\":\"1010000001000161460\",\"datecreated\":\"1503574417000\",\"userid\":\"1010000001000161460\",\"username\":\"zktsmychen\",\"version\":\"2.5\",\"workid\":\"59c0a0dd-c58e-4a8a-ac27-\n";

    public Bank() {
    }

    public Bank(String bankid, String bankname, String classname, String cyuserid, String datecreated, String userid, String username, String version, String workid, String workname, String wtype, String qtype, String schoolid, String classid, String classtype, String cyclassname, String pic_url, String service_mac) {
        this.bankid = bankid;
        this.bankname = bankname;
        this.classname = classname;
        this.cyuserid = cyuserid;
        this.datecreated = datecreated;
        this.userid = userid;
        this.username = username;
        this.version = version;
        this.workid = workid;
        this.workname = workname;
        this.wtype = wtype;
        this.qtype = qtype;
        this.schoolid = schoolid;
        this.classid = classid;
        this.classtype = classtype;
        this.cyclassname = cyclassname;
        this.pic_url = pic_url;
        this.service_mac = service_mac;
    }

    public String getBankid() {
        return bankid;
    }

    public void setBankid(String bankid) {
        this.bankid = bankid;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getCyuserid() {
        return cyuserid;
    }

    public void setCyuserid(String cyuserid) {
        this.cyuserid = cyuserid;
    }

    public String getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(String datecreated) {
        this.datecreated = datecreated;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getWorkid() {
        return workid;
    }

    public void setWorkid(String workid) {
        this.workid = workid;
    }

    public String getWorkname() {
        return workname;
    }

    public void setWorkname(String workname) {
        this.workname = workname;
    }

    public String getWtype() {
        return wtype;
    }

    public void setWtype(String wtype) {
        this.wtype = wtype;
    }

    public String getQtype() {
        return qtype;
    }

    public void setQtype(String qtype) {
        this.qtype = qtype;
    }

    public String getSchoolid() {
        return schoolid;
    }

    public void setSchoolid(String schoolid) {
        this.schoolid = schoolid;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getClasstype() {
        return classtype;
    }

    public void setClasstype(String classtype) {
        this.classtype = classtype;
    }

    public String getCyclassname() {
        return cyclassname;
    }

    public void setCyclassname(String cyclassname) {
        this.cyclassname = cyclassname;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getService_mac() {
        return service_mac;
    }

    public void setService_mac(String service_mac) {
        this.service_mac = service_mac;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "bankid='" + bankid + '\'' +
                ", bankname='" + bankname + '\'' +
                ", classname='" + classname + '\'' +
                ", cyuserid='" + cyuserid + '\'' +
                ", datecreated='" + datecreated + '\'' +
                ", userid='" + userid + '\'' +
                ", username='" + username + '\'' +
                ", version='" + version + '\'' +
                ", workid='" + workid + '\'' +
                ", workname='" + workname + '\'' +
                ", wtype='" + wtype + '\'' +
                ", qtype='" + qtype + '\'' +
                ", schoolid='" + schoolid + '\'' +
                ", classid='" + classid + '\'' +
                ", classtype='" + classtype + '\'' +
                ", cyclassname='" + cyclassname + '\'' +
                ", pic_url='" + pic_url + '\'' +
                ", service_mac='" + service_mac + '\'' +
                '}';
    }
}
