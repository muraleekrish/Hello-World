/*
 * Created on Nov 1, 2004
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
public class BaseCalendarAddForm extends ActionForm 
{
	private String baseCalendarName = "";
	private String startDayofWeek = "0";
	private String endDayofWeek = "0";
	private String dayofWeek = "0";
	private String shiftName = "";
	private String startHours = "";
	private String startMins = "";
	private String endHours = "";
	private String endMins = "";
	private String startTimeDay = "0";
	private String endTimeDay = "0";
	private String predecessorShift = "0";
	private String formAction = "";
	private String hidShiftDets = "";
	
	
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
	 * @return Returns the endDayofWeek.
	 */
	public String getEndDayofWeek() {
		return endDayofWeek;
	}
	/**
	 * @param endDayofWeek The endDayofWeek to set.
	 */
	public void setEndDayofWeek(String endDayofWeek) {
		this.endDayofWeek = endDayofWeek;
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
	 * @return Returns the predecessorShift.
	 */
	public String getPredecessorShift() {
		return predecessorShift;
	}
	/**
	 * @param predecessorShift The predecessorShift to set.
	 */
	public void setPredecessorShift(String predecessorShift) {
		this.predecessorShift = predecessorShift;
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
	 * @return Returns the startDayofWeek.
	 */
	public String getStartDayofWeek() {
		return startDayofWeek;
	}
	/**
	 * @param startDayofWeek The startDayofWeek to set.
	 */
	public void setStartDayofWeek(String startDayofWeek) {
		this.startDayofWeek = startDayofWeek;
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
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{ 
		ActionErrors errors = new ActionErrors();
		if (formAction.equalsIgnoreCase("add"))
		{
			if(baseCalendarName==null || baseCalendarName.trim().equals(""))
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","Base Calendar Name"));
			
		}
		return errors;
	}	

	/**
	 * @return Returns the hidShiftDets.
	 */
	public String getHidShiftDets() {
		return hidShiftDets;
	}
	/**
	 * @param hidShiftDets The hidShiftDets to set.
	 */
	public void setHidShiftDets(String hidShiftDets) {
		this.hidShiftDets = hidShiftDets;
	}
}
/***
$Log: BaseCalendarAddForm.java,v $
Revision 1.6  2005/04/07 04:27:11  sponnusamy
Availcalendar add on progress

Revision 1.5  2005/03/19 04:22:51  vkrishnamoorthy
Unused fields removed.

Revision 1.4  2005/03/18 12:11:03  vkrishnamoorthy
Modified as per time and day rules.

Revision 1.3  2005/03/18 09:27:03  sponnusamy
Basecalendar addForm changed

Revision 1.2  2004/12/07 07:46:59  sponnusamy
BaseCalendar Models are Completed

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/