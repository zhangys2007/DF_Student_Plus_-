package com.example.df.zhiyun.mvp.model.entity;


/**
 * Auto-generated: 2019-07-24 11:50:29
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class ClassMember {
    private int senderIdentity; //1学生 ，2老师 3家长
    private String memberImage;
    private String memberName;
    private int memberId;
    private String memberEmail;
    public void setSenderIdentity(int senderIdentity) {
        this.senderIdentity = senderIdentity;
    }
    public int getSenderIdentity() {
        return senderIdentity;
    }

    public void setMemberImage(String memberImage) {
        this.memberImage = memberImage;
    }
    public String getMemberImage() {
        return memberImage;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
    public String getMemberName() {
        return memberName;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
    public int getMemberId() {
        return memberId;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }
    public String getMemberEmail() {
        return memberEmail;
    }

}
