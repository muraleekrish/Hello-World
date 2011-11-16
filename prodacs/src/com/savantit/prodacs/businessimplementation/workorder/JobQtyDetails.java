/*
 * Created on Dec 8, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.businessimplementation.workorder;

import java.io.Serializable;
import java.util.Vector;

/**
 * @author kduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class JobQtyDetails implements Serializable 
{
	private int woJbStatId;
	private int jobQtySno;
	private Vector WOJobOpenOpnDetails;
	private Vector WOJobOpnDetails;
	private String WOJobProductionEnteredOpnString;
	private String WOJobOpenOpnString;
	private String WOJobCmpltdOpnString;
	private String WOJobReworkOpnString;
	
	public JobQtyDetails()
	{
		woJbStatId = 0;
		jobQtySno = 0;
	}
	/**
	 * @return Returns the jobQtySno.
	 */
	public int getJobQtySno() {
		return jobQtySno;
	}
	/**
	 * @param jobQtySno The jobQtySno to set.
	 */
	public void setJobQtySno(int jobQtySno) {
		this.jobQtySno = jobQtySno;
	}
	/**
	 * @return Returns the woJbStatId.
	 */
	public int getWoJbStatId() {
		return woJbStatId;
	}
	/**
	 * @param woJbStatId The woJbStatId to set.
	 */
	public void setWoJbStatId(int woJbStatId) {
		this.woJbStatId = woJbStatId;
	}
	/**
	 * @return Returns the wOJobOpnDetails.
	 */
	public Vector getWOJobOpenOpnDetails() {
		return WOJobOpenOpnDetails;
	}
	/**
	 * @param jobOpnDetails The wOJobOpnDetails to set.
	 */
	public void setWOJobOpenOpnDetails(Vector jobOpenOpnDetails) {
		WOJobOpenOpnDetails = jobOpenOpnDetails;
	}
	
	/**
	 * @return Returns the wOJobCmpltdOpnString.
	 */
	public String getWOJobCmpltdOpnString() {
		return WOJobCmpltdOpnString;
	}
	/**
	 * @param jobCmpltdOpnString The wOJobCmpltdOpnString to set.
	 */
	public void setWOJobCmpltdOpnString(String jobCmpltdOpnString) {
		WOJobCmpltdOpnString = jobCmpltdOpnString;
	}
	/**
	 * @return Returns the wOJobOpenOpnString.
	 */
	public String getWOJobOpenOpnString() {
		return WOJobOpenOpnString;
	}
	/**
	 * @param jobOpenOpnString The wOJobOpenOpnString to set.
	 */
	public void setWOJobOpenOpnString(String jobOpenOpnString) {
		WOJobOpenOpnString = jobOpenOpnString;
	}
	/**
	 * @return Returns the wOJobProductionEnteredOpnString.
	 */
	public String getWOJobProductionEnteredOpnString() {
		return WOJobProductionEnteredOpnString;
	}
	/**
	 * @param jobProductionEnteredOpnString The wOJobProductionEnteredOpnString to set.
	 */
	public void setWOJobProductionEnteredOpnString(
			String jobProductionEnteredOpnString) {
		WOJobProductionEnteredOpnString = jobProductionEnteredOpnString;
	}
	/**
	 * @return Returns the wOJobReworkOpnString.
	 */
	public String getWOJobReworkOpnString() {
		return WOJobReworkOpnString;
	}
	/**
	 * @param jobReworkOpnString The wOJobReworkOpnString to set.
	 */
	public void setWOJobReworkOpnString(String jobReworkOpnString) {
		WOJobReworkOpnString = jobReworkOpnString;
	}
	/**
	 * @return Returns the wOJobOpnDetails.
	 */
	public Vector getWOJobOpnDetails() {
		return WOJobOpnDetails;
	}
	/**
	 * @param jobOpnDetails The wOJobOpnDetails to set.
	 */
	public void setWOJobOpnDetails(Vector jobOpnDetails) {
		WOJobOpnDetails = jobOpnDetails;
	}
}
/***
$Log: JobQtyDetails.java,v $
Revision 1.5  2004/12/18 10:40:43  kduraisamy
additional field added in JobQtyDetails and new field is used.

Revision 1.4  2004/12/17 13:28:27  kduraisamy
field changed

Revision 1.3  2004/12/17 11:18:35  kduraisamy
additional fields added.

Revision 1.2  2004/12/09 05:54:46  kduraisamy
Log added.

***/