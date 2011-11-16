/*
 * Created on Feb 28, 2005
*/
package com.savantit.prodacs.businessimplementation.securityadmin;

import java.io.Serializable;
import java.util.Vector;

/**
 * @author sduraisamy
 */
public class UserAuthDetails implements Serializable
{
    private String userId = "";
	private String password="";
	private ContactDetails contactDetails = new ContactDetails();
	private Vector vecUserAuth = new Vector();

    /**
     * @return Returns the contactDetails.
     */
    public ContactDetails getContactDetails() {
        return contactDetails;
    }
    /**
     * @param contactDetails The contactDetails to set.
     */
    public void setContactDetails(ContactDetails contactDetails) {
        this.contactDetails = contactDetails;
    }
    /**
     * @return Returns the password.
     */
    public String getPassword() {
        return password;
    }
    /**
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }
  
    /**
     * @return Returns the userId.
     */
    public String getUserId() {
        return userId;
    }
    /**
     * @param userId The userId to set.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
    /**
     * @return Returns the vecUserAuth.
     */
    public Vector getVecUserAuth() {
        return vecUserAuth;
    }
    /**
     * @param vecUserAuth The vecUserAuth to set.
     */
    public void setVecUserAuth(Vector vecUserAuth) {
        this.vecUserAuth = vecUserAuth;
    }
}
