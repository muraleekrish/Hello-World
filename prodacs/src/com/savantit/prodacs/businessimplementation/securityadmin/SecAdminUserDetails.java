/*
 * Created on Feb 26, 2005
*/
package com.savantit.prodacs.businessimplementation.securityadmin;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;


/**
 * @author sduraisamy
 */
public class SecAdminUserDetails implements Serializable 
{
   
    private String user_Name;
	private int contactId;
    private String user_Desc;
	private String user_Password;
	private ContactDetails obj_Contact_Details;
	private Vector user_Rolls;
	private Date user_DateStamp=null;
	private boolean user_IsValid;

	public SecAdminUserDetails()
	{
		this.user_Name="";
		this.user_Desc="";
		this.user_Password="";
		this.obj_Contact_Details = new ContactDetails();
		this.user_Rolls = new Vector();
		this.contactId = 0;
		this.user_DateStamp = new Date();
		this.user_IsValid=true;
	}

   
      
    
    /**
     * @return Returns the contactId.
     */
    public int getContactId()
    {
        return contactId;
    }
    /**
     * @param contactId The contactId to set.
     */
    public void setContactId(int contactId)
    {
        this.contactId = contactId;
    }
    /**
     * @return Returns the obj_Contact_Details.
     */
    public ContactDetails getObj_Contact_Details() {
        return obj_Contact_Details;
    }
    /**
     * @param obj_Contact_Details The obj_Contact_Details to set.
     */
    public void setObj_Contact_Details(ContactDetails obj_Contact_Details) {
        this.obj_Contact_Details = obj_Contact_Details;
    }
    /**
     * @return Returns the user_DateStamp.
     */
    public Date getUser_DateStamp() {
        return user_DateStamp;
    }
    /**
     * @param user_DateStamp The user_DateStamp to set.
     */
    public void setUser_DateStamp(Date user_DateStamp) {
        this.user_DateStamp = user_DateStamp;
    }
    /**
     * @return Returns the user_Desc.
     */
    public String getUser_Desc() {
        return user_Desc;
    }
    /**
     * @param user_Desc The user_Desc to set.
     */
    public void setUser_Desc(String user_Desc) {
        this.user_Desc = user_Desc;
    }
    
    
    /**
     * @return Returns the user_Name.
     */
    public String getUser_Name() {
        return user_Name;
    }
    /**
     * @param user_Name The user_Name to set.
     */
    public void setUser_Name(String user_Name) {
        this.user_Name = user_Name;
    }
  
    /**
     * @return Returns the user_IsValid.
     */
    public boolean isUser_IsValid() {
        return user_IsValid;
    }
    /**
     * @param user_IsValid The user_IsValid to set.
     */
    public void setUser_IsValid(boolean user_IsValid) {
        this.user_IsValid = user_IsValid;
    }
    /**
     * @return Returns the user_Password.
     */
    public String getUser_Password() {
        return user_Password;
    }
    /**
     * @param user_Password The user_Password to set.
     */
    public void setUser_Password(String user_Password) {
        this.user_Password = user_Password;
    }
    
    
    
    /**
     * @return Returns the user_Rolls.
     */
    public Vector getUser_Rolls()
    {
        return user_Rolls;
    }
    /**
     * @param user_Rolls The user_Rolls to set.
     */
    public void setUser_Rolls(Vector user_Rolls)
    {
        this.user_Rolls = user_Rolls;
    }
}
