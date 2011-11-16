/*
 * Created on Nov 2, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.businessimplementation.job;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

/**
 * @author kduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class JobDetails implements Serializable 
{
	private int job_Id;
	private String job_Name;
	private int cust_Id;
	private String cust_Name;
	private int job_Gnrl_Id;
	private String gnrl_Name;
	private int gnrl_IsValid;
	private Date gnrl_DateStamp;
	private int cust_Typ_Id;
	private String cust_Typ_Name;
	private String job_Dwg_No;
	private String job_Rvsn_No;
	private String job_Matl_Type;
	private float job_stdHrs;
	private String job_Incntv_Flag;
	private Date job_Created_Date;
	
	private int job_IsValid;
	private Date job_DateStamp;
	private Vector vec_OpnDetails;
	
	
	//private OperationGroupDetails objOperationGroupDetails;
	
	public JobDetails()
	{
		this.job_Name = "";
		this.cust_Id = 0;
		this.job_Gnrl_Id = 0;
		this.job_Dwg_No = "";
		this.job_Rvsn_No = "";
		this.job_Matl_Type = "";
		this.job_Incntv_Flag = "";
		this.job_Created_Date = null;
		this.job_IsValid = 0;
		this.job_DateStamp = null;
		this.job_stdHrs = 0;
	}
	
	/**
	 * @return Returns the job_Gnrl_Name.
	 */
	public int getJob_Gnrl_Id() {
		return job_Gnrl_Id;
	}
	/**
	 * @param job_Gnrl_Name The job_Gnrl_Name to set.
	 */
	public void setJob_Gnrl_Id(int job_Gnrl_Id) {
		this.job_Gnrl_Id = job_Gnrl_Id;
	}
	/**
	 * @return Returns the job_Incntv_Flag.
	 */
	public String getJob_Incntv_Flag() {
		return job_Incntv_Flag;
	}
	/**
	 * @param job_Incntv_Flag The job_Incntv_Flag to set.
	 */
	public void setJob_Incntv_Flag(String job_Incntv_Flag) {
		this.job_Incntv_Flag = job_Incntv_Flag;
	}
	/**
	 * @return Returns the job_Matl_Type.
	 */
	public String getJob_Matl_Type() {
		return job_Matl_Type;
	}
	/**
	 * @param job_Matl_Type The job_Matl_Type to set.
	 */
	public void setJob_Matl_Type(String job_Matl_Type) {
		this.job_Matl_Type = job_Matl_Type;
	}
	/**
	 * @return Returns the job_Name.
	 */
	public String getJob_Name() {
		return job_Name;
	}
	/**
	 * @param job_Name The job_Name to set.
	 */
	public void setJob_Name(String job_Name) {
		this.job_Name = job_Name;
	}
	/**
	 * @return Returns the job_Rvsn_No.
	 */
	public String getJob_Rvsn_No() {
		return job_Rvsn_No;
	}
	/**
	 * @param job_Rvsn_No The job_Rvsn_No to set.
	 */
	public void setJob_Rvsn_No(String job_Rvsn_No) {
		this.job_Rvsn_No = job_Rvsn_No;
	}
	/**
	 * @return Returns the cust_Id.
	 */
	public int getCust_Id() {
		return cust_Id;
	}
	/**
	 * @param cust_Id The cust_Id to set.
	 */
	public void setCust_Id(int cust_Id) {
		this.cust_Id = cust_Id;
	}
	/**
	 * @return Returns the job_Created_Date.
	 */
	public Date getJob_Created_Date() {
		return job_Created_Date;
	}
	/**
	 * @param job_Created_Date The job_Created_Date to set.
	 */
	public void setJob_Created_Date(Date job_Created_Date) {
		this.job_Created_Date = job_Created_Date;
	}
	/**
	 * @return Returns the job_Dwg_No.
	 */
	public String getJob_Dwg_No() {
		return job_Dwg_No;
	}
	/**
	 * @param job_Dwg_No The job_Dwg_No to set.
	 */
	public void setJob_Dwg_No(String job_Dwg_No) {
		this.job_Dwg_No = job_Dwg_No;
	}
	/**
	 * @return Returns the cust_Name.
	 */
	public String getCust_Name() {
		return cust_Name;
	}
	/**
	 * @param cust_Name The cust_Name to set.
	 */
	public void setCust_Name(String cust_Name) {
		this.cust_Name = cust_Name;
	}
	/**
	 * @return Returns the cust_Typ_Name.
	 */
	public String getCust_Typ_Name() {
		return cust_Typ_Name;
	}
	/**
	 * @param cust_Typ_Name The cust_Typ_Name to set.
	 */
	public void setCust_Typ_Name(String cust_Typ_Name) {
		this.cust_Typ_Name = cust_Typ_Name;
	}
	/**
	 * @return Returns the gnrl_Name.
	 */
	public String getGnrl_Name() {
		return gnrl_Name;
	}
	/**
	 * @param gnrl_Name The gnrl_Name to set.
	 */
	public void setGnrl_Name(String gnrl_Name) {
		this.gnrl_Name = gnrl_Name;
	}
	/**
	 * @return Returns the cust_Typ_Id.
	 */
	public int getCust_Typ_Id() {
		return cust_Typ_Id;
	}
	/**
	 * @param cust_Typ_Id The cust_Typ_Id to set.
	 */
	public void setCust_Typ_Id(int cust_Typ_Id) {
		this.cust_Typ_Id = cust_Typ_Id;
	}
	/**
	 * @return Returns the job_Id.
	 */
	public int getJob_Id() {
		return job_Id;
	}
	/**
	 * @param job_Id The job_Id to set.
	 */
	public void setJob_Id(int job_Id) {
		this.job_Id = job_Id;
	}
	
	
	/**
	 * @return Returns the job_DateStamp.
	 */
	public Date getJob_DateStamp() {
		return job_DateStamp;
	}
	/**
	 * @param job_DateStamp The job_DateStamp to set.
	 */
	public void setJob_DateStamp(Date job_DateStamp) {
		this.job_DateStamp = job_DateStamp;
	}
	/**
	 * @return Returns the job_IsValid.
	 */
	public int getJob_IsValid() {
		return job_IsValid;
	}
	/**
	 * @param job_IsValid The job_IsValid to set.
	 */
	public void setJob_IsValid(int job_IsValid) {
		this.job_IsValid = job_IsValid;
	}
	/**
	 * @return Returns the gnrl_DateStamp.
	 */
	public Date getGnrl_DateStamp() {
		return gnrl_DateStamp;
	}
	/**
	 * @param gnrl_DateStamp The gnrl_DateStamp to set.
	 */
	public void setGnrl_DateStamp(Date gnrl_DateStamp) {
		this.gnrl_DateStamp = gnrl_DateStamp;
	}
	/**
	 * @return Returns the gnrl_IsValid.
	 */
	public int getGnrl_IsValid() {
		return gnrl_IsValid;
	}
	/**
	 * @param gnrl_IsValid The gnrl_IsValid to set.
	 */
	public void setGnrl_IsValid(int gnrl_IsValid) {
		this.gnrl_IsValid = gnrl_IsValid;
	}
	/**
	 * @return Returns the vec_OpnDetails.
	 */
	public Vector getVec_OpnDetails() {
		return vec_OpnDetails;
	}
	/**
	 * @param vec_OpnDetails The vec_OpnDetails to set.
	 */
	public void setVec_OpnDetails(Vector vec_OpnDetails) {
		this.vec_OpnDetails = vec_OpnDetails;
	}
	/**
	 * @return Returns the job_stdHrs.
	 */
	public float getJob_stdHrs() {
		return job_stdHrs;
	}
	/**
	 * @param job_stdHrs The job_stdHrs to set.
	 */
	public void setJob_stdHrs(float job_stdHrs) {
		this.job_stdHrs = job_stdHrs;
	}
}
/***
$Log: JobDetails.java,v $
Revision 1.9  2005/06/24 05:17:47  vkrishnamoorthy
Job StdHrs added.

Revision 1.8  2004/11/23 13:17:48  kduraisamy
vec_OpnGpDet field deleted

Revision 1.7  2004/11/23 12:31:01  sduraisamy
JobDetails ,OperationGroupDetails ,OperationDetails Entity Objects modified and errors Corrected

Revision 1.6  2004/11/21 11:42:32  sduraisamy
Operation Std Hrs,SerialNo included

Revision 1.5  2004/11/21 09:58:59  kduraisamy
Vector included in Entity Object JobDetails

Revision 1.4  2004/11/18 07:03:46  sduraisamy
jobid,custname,custtypename,jobgnrlname included

Revision 1.3  2004/11/16 11:22:36  kduraisamy
job gnrl name is changed to job gnrl id

Revision 1.2  2004/11/09 04:58:20  kduraisamy
Log added.

***/
