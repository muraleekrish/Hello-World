/*
 * Created on Nov 2, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.workcalendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author sponnusamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class AvailCalendarAddForm extends ActionForm 
{
	private String baseCalendarName = "";
	private String bcStarnEndDay = "";
	private String fromDate = "";
	private String toDate = "";
	private String selectBaseCalendar = "0";
	private String nonAvailCal = "";
	private String shiftDetails = "";
	private String formAction = "";
	private String avlDetails = "";
	private String dayofWeek = "0";
	private String shiftName = "";
	private String startHours = "";
	private String startMins = "";
	private String endHours = "";
	private String endMins = "";
	private String startTimeDay = "0";
	private String endTimeDay = "0";
	private String cstmAvlDay = "";
	private String cstmAvlDate = "";
	private String cstmAvlSftDetails = "";
	
	
	
	/**
	 * @return Returns the avlDetails.
	 */
	public String getAvlDetails() {
		return avlDetails;
	}
	/**
	 * @param avlDetails The avlDetails to set.
	 */
	public void setAvlDetails(String avlDetails) {
		this.avlDetails = avlDetails;
	}
	/**
	 * @return Returns the bcStarnEndDay.
	 */
	public String getBcStarnEndDay() {
		return bcStarnEndDay;
	}
	/**
	 * @param bcStarnEndDay The bcStarnEndDay to set.
	 */
	public void setBcStarnEndDay(String bcStarnEndDay) {
		this.bcStarnEndDay = bcStarnEndDay;
	}
	/**
	 * @return Returns the shiftDetails.
	 */
	public String getShiftDetails() {
		return shiftDetails;
	}
	/**
	 * @param shiftDetails The shiftDetails to set.
	 */
	public void setShiftDetails(String shiftDetails) {
		this.shiftDetails = shiftDetails;
	}
	/**
	 * @return Returns the nonAvailCal.
	 */
	public String getNonAvailCal() {
		return nonAvailCal;
	}
	/**
	 * @param nonAvailCal The nonAvailCal to set.
	 */
	public void setNonAvailCal(String nonAvailCal) {
		this.nonAvailCal = nonAvailCal;
	}
	
	/**
	 * @return Returns the formAction.
	 */
	public String getFormAction() {
		return formAction;
	}
	/**
	 * @param formAction The formAction to set.
	 */
	public void setFormAction(String formAction) {
		this.formAction = formAction;
	}
	/**
	 * @return Returns the baseCalendarName.
	 */
	public String getBaseCalendarName() {
		return baseCalendarName;
	}
	/**
	 * @param baseCalendarName The baseCalendarName to set.
	 */
	public void setBaseCalendarName(String baseCalendarName) {
		this.baseCalendarName = baseCalendarName;
	}
	/**
	 * @return Returns the fromDate.
	 */
	public String getFromDate() {
		return fromDate;
	}
	/**
	 * @param fromDate The fromDate to set.
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return Returns the selectBaseCalendar.
	 */
	public String getSelectBaseCalendar() {
		return selectBaseCalendar;
	}
	/**
	 * @param selectBaseCalendar The selectBaseCalendar to set.
	 */
	public void setSelectBaseCalendar(String selectBaseCalendar) {
		this.selectBaseCalendar = selectBaseCalendar;
	}
	/**
	 * @return Returns the toDate.
	 */
	public String getToDate() {
		return toDate;
	}
	/**
	 * @param toDate The toDate to set.
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	/**
	 * @return Returns the dayofWeek.
	 */
	public String getDayofWeek() {
		return dayofWeek;
	}
	/**
	 * @param dayofWeek The dayofWeek to set.
	 */
	public void setDayofWeek(String dayofWeek) {
		this.dayofWeek = dayofWeek;
	}
	/**
	 * @return Returns the endHours.
	 */
	public String getEndHours() {
		return endHours;
	}
	/**
	 * @param endHours The endHours to set.
	 */
	public void setEndHours(String endHours) {
		this.endHours = endHours;
	}
	/**
	 * @return Returns the endMins.
	 */
	public String getEndMins() {
		return endMins;
	}
	/**
	 * @param endMins The endMins to set.
	 */
	public void setEndMins(String endMins) {
		this.endMins = endMins;
	}
	/**
	 * @return Returns the endTimeDay.
	 */
	public String getEndTimeDay() {
		return endTimeDay;
	}
	/**
	 * @param endTimeDay The endTimeDay to set.
	 */
	public void setEndTimeDay(String endTimeDay) {
		this.endTimeDay = endTimeDay;
	}
	/**
	 * @return Returns the shiftName.
	 */
	public String getShiftName() {
		return shiftName;
	}
	/**
	 * @param shiftName The shiftName to set.
	 */
	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}
	/**
	 * @return Returns the startHours.
	 */
	public String getStartHours() {
		return startHours;
	}
	/**
	 * @param startHours The startHours to set.
	 */
	public void setStartHours(String startHours) {
		this.startHours = startHours;
	}
	/**
	 * @return Returns the startMins.
	 */
	public String getStartMins() {
		return startMins;
	}
	/**
	 * @param startMins The startMins to set.
	 */
	public void setStartMins(String startMins) {
		this.startMins = startMins;
	}
	/**
	 * @return Returns the startTimeDay.
	 */
	public String getStartTimeDay() {
		return startTimeDay;
	}
	/**
	 * @param startTimeDay The startTimeDay to set.
	 */
	public void setStartTimeDay(String startTimeDay) {
		this.startTimeDay = startTimeDay;
	}
	/**
	 * @return Returns the cstmAvlDate.
	 */
	public String getCstmAvlDate() {
		return cstmAvlDate;
	}
	/**
	 * @param cstmAvlDate The cstmAvlDate to set.
	 */
	public void setCstmAvlDate(String cstmAvlDate) {
		this.cstmAvlDate = cstmAvlDate;
	}
	/**
	 * @return Returns the cstmAvlDay.
	 */
	public String getCstmAvlDay() {
		return cstmAvlDay;
	}
	/**
	 * @param cstmAvlDay The cstmAvlDay to set.
	 */
	public void setCstmAvlDay(String cstmAvlDay) {
		this.cstmAvlDay = cstmAvlDay;
	}
	/**
	 * @return Returns the cstmAvlSftDetails.
	 */
	public String getCstmAvlSftDetails() {
		return cstmAvlSftDetails;
	}
	/**
	 * @param cstmAvlSftDetails The cstmAvlSftDetails to set.
	 */
	public void setCstmAvlSftDetails(String cstmAvlSftDetails) {
		this.cstmAvlSftDetails = cstmAvlSftDetails;
	}
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{ 
		ActionErrors errors = new ActionErrors();
		if (formAction.equalsIgnoreCase("loadMandatory"))
		{
			if (baseCalendarName==null || baseCalendarName.trim().equals(""))
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","Availability Calendar Name"));
			
			if ((fromDate == null) || fromDate.trim().equals(""))
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","Start Date"));

			if ((toDate == null) || toDate.trim().equals(""))
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","End Date"));
			
			if ((selectBaseCalendar == null) || selectBaseCalendar.trim().equals("0"))
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","Base Calendar Name"));
		}
		return errors;
	}	
}
/***
$Log: AvailCalendarAddForm.java,v $
Revision 1.4  2005/04/20 10:56:05  sponnusamy
Modified according to availability calendar

Revision 1.3  2005/04/07 04:27:11  sponnusamy
Availcalendar add on progress

Revision 1.2  2005/03/26 11:33:26  sponnusamy
Changes made according to Availability
calendar

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/