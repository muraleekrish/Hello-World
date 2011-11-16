/*
 * Created on Feb 26, 2005
*/
package com.savantit.prodacs.businessimplementation.securityadmin;

import java.io.Serializable;
import java.util.Date;

/**
 * @author kduraisamy
 */
public class ContactDetails implements Serializable 
{
    private int cntct_Id;
    private String cntct_Fname;
    private String cntct_Lname;
    private String cntct_Title;
    private Date cntct_Dob;
    private int cntct_Company;
    private String companyName;
    private String cntct_Position;
    private String cntct_Designation;
    private String cntct_Address1;
    private String cntct_Address2;
    private String cntct_City;
    private String cntct_State;
    private String cntct_Pincode;
    private String cntct_Country;
    private String cntct_HPhone;
    private String cntct_WPhone;
    private String cntct_Extension;
    private String cntct_Mobile;
    private String cntct_Fax;
    private String cntct_Email;
    private Date datestamp;
    private int isValid;
            
    public ContactDetails()
    {
        this.cntct_Id = 0;
        this.cntct_Fname = "";
        this.cntct_Lname = "";
        this.cntct_Title = "";
        this.cntct_Designation = "";
        this.cntct_Address1 = "";
        this.cntct_Address2 = "";
        this.cntct_Dob = null;
        this.cntct_City = "";
        this.cntct_State = "";
        this.cntct_Pincode = "";
        this.cntct_Country = "";
        this.cntct_HPhone = "";
        this.cntct_WPhone = "";
        this.cntct_Extension = "";
        this.cntct_Mobile = "";
        this.cntct_Fax = "";
        this.cntct_Email = "";
        this.cntct_Company = 0;
        this.companyName = "";
        this.cntct_Position = "";
        this.datestamp = null;
        this.isValid = 0;
    }
    
    /**
     * @return Returns the cntct_Address1.
     */
    public String getCntct_Address1() {
        return cntct_Address1;
    }
    /**
     * @param cntct_Address1 The cntct_Address1 to set.
     */
    public void setCntct_Address1(String cntct_Address1) {
        this.cntct_Address1 = cntct_Address1;
    }
    /**
     * @return Returns the cntct_City.
     */
    public String getCntct_City() {
        return cntct_City;
    }
    /**
     * @param cntct_City The cntct_City to set.
     */
    public void setCntct_City(String cntct_City) {
        this.cntct_City = cntct_City;
    }
    /**
     * @return Returns the cntct_Country.
     */
    public String getCntct_Country() {
        return cntct_Country;
    }
    /**
     * @param cntct_Country The cntct_Country to set.
     */
    public void setCntct_Country(String cntct_Country) {
        this.cntct_Country = cntct_Country;
    }
    /**
     * @return Returns the cntct_Designation.
     */
    public String getCntct_Designation() {
        return cntct_Designation;
    }
    /**
     * @param cntct_Designation The cntct_Designation to set.
     */
    public void setCntct_Designation(String cntct_Designation) {
        this.cntct_Designation = cntct_Designation;
    }
    /**
     * @return Returns the cntct_Email.
     */
    public String getCntct_Email() {
        return cntct_Email;
    }
    /**
     * @param cntct_Email The cntct_Email to set.
     */
    public void setCntct_Email(String cntct_Email) {
        this.cntct_Email = cntct_Email;
    }
    /**
     * @return Returns the cntct_Fax.
     */
    public String getCntct_Fax() {
        return cntct_Fax;
    }
    /**
     * @param cntct_Fax The cntct_Fax to set.
     */
    public void setCntct_Fax(String cntct_Fax) {
        this.cntct_Fax = cntct_Fax;
    }
    /**
     * @return Returns the cntct_Fname.
     */
    public String getCntct_Fname() {
        return cntct_Fname;
    }
    /**
     * @param cntct_Fname The cntct_Fname to set.
     */
    public void setCntct_Fname(String cntct_Fname) {
        this.cntct_Fname = cntct_Fname;
    }
    /**
     * @return Returns the cntct_HPhone.
     */
    public String getCntct_HPhone() {
        return cntct_HPhone;
    }
    /**
     * @param cntct_HPhone The cntct_HPhone to set.
     */
    public void setCntct_HPhone(String cntct_HPhone) {
        this.cntct_HPhone = cntct_HPhone;
    }
    /**
     * @return Returns the cntct_Id.
     */
    public int getCntct_Id() {
        return cntct_Id;
    }
    /**
     * @param cntct_Id The cntct_Id to set.
     */
    public void setCntct_Id(int cntct_Id) {
        this.cntct_Id = cntct_Id;
    }
    /**
     * @return Returns the cntct_Lname.
     */
    public String getCntct_Lname() {
        return cntct_Lname;
    }
    /**
     * @param cntct_Lname The cntct_Lname to set.
     */
    public void setCntct_Lname(String cntct_Lname) {
        this.cntct_Lname = cntct_Lname;
    }
    /**
     * @return Returns the cntct_State.
     */
    public String getCntct_State() {
        return cntct_State;
    }
    /**
     * @param cntct_State The cntct_State to set.
     */
    public void setCntct_State(String cntct_State) {
        this.cntct_State = cntct_State;
    }
    /**
     * @return Returns the cntct_WPhone.
     */
    public String getCntct_WPhone() {
        return cntct_WPhone;
    }
    /**
     * @param cntct_WPhone The cntct_WPhone to set.
     */
    public void setCntct_WPhone(String cntct_WPhone) {
        this.cntct_WPhone = cntct_WPhone;
    }
    /**
     * @return Returns the cntct_Title.
     */
    public String getCntct_Title()
    {
        return cntct_Title;
    }
    /**
     * @param cntct_Title The cntct_Title to set.
     */
    public void setCntct_Title(String cntct_Title)
    {
        this.cntct_Title = cntct_Title;
    }
    /**
     * @return Returns the cntct_Position.
     */
    public String getCntct_Position()
    {
        return cntct_Position;
    }
    /**
     * @param cntct_Position The cntct_Position to set.
     */
    public void setCntct_Position(String cntct_Position)
    {
        this.cntct_Position = cntct_Position;
    }
    /**
     * @return Returns the cntct_Extension.
     */
    public String getCntct_Extension()
    {
        return cntct_Extension;
    }
    /**
     * @param cntct_Extension The cntct_Extension to set.
     */
    public void setCntct_Extension(String cntct_Extension)
    {
        this.cntct_Extension = cntct_Extension;
    }
    /**
     * @return Returns the cntct_Mobile.
     */
    public String getCntct_Mobile()
    {
        return cntct_Mobile;
    }
    /**
     * @param cntct_Mobile The cntct_Mobile to set.
     */
    public void setCntct_Mobile(String cntct_Mobile)
    {
        this.cntct_Mobile = cntct_Mobile;
    }
    /**
     * @return Returns the cntct_Address2.
     */
    public String getCntct_Address2()
    {
        return cntct_Address2;
    }
    /**
     * @param cntct_Address2 The cntct_Address2 to set.
     */
    public void setCntct_Address2(String cntct_Address2)
    {
        this.cntct_Address2 = cntct_Address2;
    }
    /**
     * @return Returns the cntct_Pincode.
     */
    public String getCntct_Pincode()
    {
        return cntct_Pincode;
    }
    /**
     * @param cntct_Pincode The cntct_Pincode to set.
     */
    public void setCntct_Pincode(String cntct_Pincode)
    {
        this.cntct_Pincode = cntct_Pincode;
    }
    /**
     * @return Returns the cntct_Dob.
     */
    public Date getCntct_Dob()
    {
        return cntct_Dob;
    }
    /**
     * @param cntct_Dob The cntct_Dob to set.
     */
    public void setCntct_Dob(Date cntct_Dob)
    {
        this.cntct_Dob = cntct_Dob;
    }
    /**
     * @return Returns the datestamp.
     */
    public Date getDatestamp()
    {
        return datestamp;
    }
    /**
     * @param datestamp The datestamp to set.
     */
    public void setDatestamp(Date datestamp)
    {
        this.datestamp = datestamp;
    }
    /**
     * @return Returns the isValid.
     */
    public int getIsValid()
    {
        return isValid;
    }
    /**
     * @param isValid The isValid to set.
     */
    public void setIsValid(int isValid)
    {
        this.isValid = isValid;
    }
    /**
     * @return Returns the cntct_Company.
     */
    public int getCntct_Company()
    {
        return cntct_Company;
    }
    /**
     * @param cntct_Company The cntct_Company to set.
     */
    public void setCntct_Company(int cntct_Company)
    {
        this.cntct_Company = cntct_Company;
    }
    /**
     * @return Returns the companyName.
     */
    public String getCompanyName()
    {
        return companyName;
    }
    /**
     * @param companyName The companyName to set.
     */
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }
}
