package com.pingan.enums;

/**
 * 性别枚举类型
 */
public enum GenderEnum {

    FEMALE("女性",1),

    MALE("男性",2),

    UNKNOWN("未知",3);

    private String description;

    private int index;

    private GenderEnum(String description,int index){
        this.description = description;
        this.index = index;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
