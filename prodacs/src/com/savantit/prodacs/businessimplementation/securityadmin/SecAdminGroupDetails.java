/*
 * Created on Feb 26, 2005
*/
package com.savantit.prodacs.businessimplementation.securityadmin;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;


/**
 * @author sduraisamy
 */
public class SecAdminGroupDetails implements Serializable 
{
    private int group_Id;
	private String group_Name;
	private String group_Desc;
	private Date group_DateStamp;
	private int group_IsValid;
	private Vector vResources;
	private HashMap hmResources;
	

public SecAdminGroupDetails()
{
    this.group_Id = 0;
    this.group_Name = "";
    this.group_Desc = "";
    this.group_IsValid = 1;
}
    /**
     * @return Returns the group_DateStamp.
     */
    public Date getGroup_DateStamp() {
        return group_DateStamp;
    }
    /**
     * @param group_DateStamp The group_DateStamp to set.
     */
    public void setGroup_DateStamp(Date group_DateStamp) {
        this.group_DateStamp = group_DateStamp;
    }
    /**
     * @return Returns the group_Desc.
     */
    public String getGroup_Desc() {
        return group_Desc;
    }
    /**
     * @param group_Desc The group_Desc to set.
     */
    public void setGroup_Desc(String group_Desc) {
        this.group_Desc = group_Desc;
    }
    /**
     * @return Returns the group_Id.
     */
    public int getGroup_Id() {
        return group_Id;
    }
    /**
     * @param group_Id The group_Id to set.
     */
    public void setGroup_Id(int group_Id) {
        this.group_Id = group_Id;
    }
    
    /**
     * @return Returns the group_IsValid.
     */
    public int getGroup_IsValid()
    {
        return group_IsValid;
    }
    /**
     * @param group_IsValid The group_IsValid to set.
     */
    public void setGroup_IsValid(int group_IsValid)
    {
        this.group_IsValid = group_IsValid;
    }
    /**
     * @return Returns the group_Name.
     */
    public String getGroup_Name() {
        return group_Name;
    }
    /**
     * @param group_Name The group_Name to set.
     */
    public void setGroup_Name(String group_Name) {
        this.group_Name = group_Name;
    }
    
    /**
     * @return Returns the hmResources.
     */
    public HashMap getHmResources() {
        return hmResources;
    }
    /**
     * @param hmResources The hmResources to set.
     */
    public void setHmResources(HashMap hmResources) {
        this.hmResources = hmResources;
    }
    /**
     * @return Returns the vResources.
     */
    public Vector getVResources() {
        return vResources;
    }
    /**
     * @param resources The vResources to set.
     */
    public void setVResources(Vector resources) {
        vResources = resources;
    }
}
