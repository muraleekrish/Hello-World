/*
 * Created on Jan 4, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.payroll;

import org.apache.struts.action.ActionForm;

/**
 * @author vkrishnamoorthy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

public class PayrollCycleForm extends ActionForm
{
	private String cycleType = "";
	private String week = "";
	private String byWeek = "";
	private String byWeekSelect = "";
	private String month = "";
	private String fortNight = "";
	private String byFortNight = "";
	
	private String hidCycleType = "";
	private String hidCycle = "";

	/**
	 * @return Returns the byFortNight.
	 */
	public String getByFortNight() {
		return byFortNight;
	}
	/**
	 * @param byFortNight The byFortNight to set.
	 */
	public void setByFortNight(String byFortNight) {
		this.byFortNight = byFortNight;
	}
	/**
	 * @return Returns the byWeek.
	 */
	public String getByWeek() {
		return byWeek;
	}
	/**
	 * @param byWeek The byWeek to set.
	 */
	public void setByWeek(String byWeek) {
		this.byWeek = byWeek;
	}
	/**
	 * @return Returns the byWeekSelect.
	 */
	public String getByWeekSelect() {
		return byWeekSelect;
	}
	/**
	 * @param byWeekSelect The byWeekSelect to set.
	 */
	public void setByWeekSelect(String byWeekSelect) {
		this.byWeekSelect = byWeekSelect;
	}
	/**
	 * @return Returns the cycleType.
	 */
	public String getCycleType() {
		return cycleType;
	}
	/**
	 * @param cycleType The cycleType to set.
	 */
	public void setCycleType(String cycleType) {
		this.cycleType = cycleType;
	}
	/**
	 * @return Returns the fortNight.
	 */
	public String getFortNight() {
		return fortNight;
	}
	/**
	 * @param fortNight The fortNight to set.
	 */
	public void setFortNight(String fortNight) {
		this.fortNight = fortNight;
	}
	/**
	 * @return Returns the month.
	 */
	public String getMonth() {
		return month;
	}
	/**
	 * @param month The month to set.
	 */
	public void setMonth(String month) {
		this.month = month;
	}
	/**
	 * @return Returns the week.
	 */
	public String getWeek() {
		return week;
	}
	/**
	 * @param week The week to set.
	 */
	public void setWeek(String week) {
		this.week = week;
	}
	/**
	 * @return Returns the hidCycle.
	 */
	public String getHidCycle() {
		return hidCycle;
	}
	/**
	 * @param hidCycle The hidCycle to set.
	 */
	public void setHidCycle(String hidCycle) {
		this.hidCycle = hidCycle;
	}
	/**
	 * @return Returns the hidCycleType.
	 */
	public String getHidCycleType() {
		return hidCycleType;
	}
	/**
	 * @param hidCycleType The hidCycleType to set.
	 */
	public void setHidCycleType(String hidCycleType) {
		this.hidCycleType = hidCycleType;
	}
}

/***
$Log: PayrollCycleForm.java,v $
Revision 1.2  2005/01/21 09:18:02  sponnusamy
In filter woId and machine id are modified.

***/
