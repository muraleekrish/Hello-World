/*
 * Created on Nov 2, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.workcalendar;

import org.apache.struts.action.ActionForm;

/**
 * @author sponnusamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class AvailCalendarEditForm extends ActionForm 
{
	private String availCalendarName = "";
	private String fromDate = "";
	private String toDate = "";
	private String baseCalendarName = "";
	private String customNonAvlDetails;
	private String bcStarnEndDay;
	private String customAvlDetails;

	private String dayofWeek = "0";
	private String shiftName;
	private String startHours;
	private String startMins;
	private String startTimeDay = "0";
	private String endHours;
	private String endMins;
	private String endTimeDay = "0";
	
	private String sftDetails = "";
	private String avlDetails = "";
	
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
	private String formAction = "";
	private int id;
	private String[] ids = new String[0];
	
	/**
	 * @return Returns the sftDetails.
	 */
	public String getSftDetails() {
		return sftDetails;
	}
	/**
	 * @param sftDetails The sftDetails to set.
	 */
	public void setSftDetails(String sftDetails) {
		this.sftDetails = sftDetails;
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
	 * @return Returns the customAvlDetails.
	 */
	public String getCustomAvlDetails() {
		return customAvlDetails;
	}
	/**
	 * @param customAvlDetails The customAvlDetails to set.
	 */
	public void setCustomAvlDetails(String customAvlDetails) {
		this.customAvlDetails = customAvlDetails;
	}
	/**
	 * @return Returns the customNonAvlDetails.
	 */
	public String getCustomNonAvlDetails() {
		return customNonAvlDetails;
	}
	/**
	 * @param customNonAvlDetails The customNonAvlDetails to set.
	 */
	public void setCustomNonAvlDetails(String customNonAvlDetails) {
		this.customNonAvlDetails = customNonAvlDetails;
	}
	/**
	 * @return Returns the availCalendarName.
	 */
	public String getAvailCalendarName() {
		return availCalendarName;
	}
	/**
	 * @param availCalendarName The availCalendarName to set.
	 */
	public void setAvailCalendarName(String availCalendarName) {
		this.availCalendarName = availCalendarName;
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
	 * @return Returns the id.
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return Returns the ids.
	 */
	public String[] getIds() {
		return ids;
	}
	/**
	 * @param ids The ids to set.
	 */
	public void setIds(String[] ids) {
		this.ids = ids;
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
}
/***
$Log: AvailCalendarEditForm.java,v $
Revision 1.2  2005/05/03 07:23:31  vkrishnamoorthy
AvailCalEdit Completed.

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/