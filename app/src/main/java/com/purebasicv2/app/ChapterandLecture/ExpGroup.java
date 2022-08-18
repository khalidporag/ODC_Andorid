package com.purebasicv2.app.ChapterandLecture;

public class ExpGroup {
    String groupName = "";
    Boolean isExpanded = false;

    public ExpGroup(String groupName, Boolean isExpanded) {
        this.groupName = groupName;
        this.isExpanded = isExpanded;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Boolean getExpanded() {
        return isExpanded;
    }

    public void setExpanded(Boolean expanded) {
        isExpanded = expanded;
    }
}
