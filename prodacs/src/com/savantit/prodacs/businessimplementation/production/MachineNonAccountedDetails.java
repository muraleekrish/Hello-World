/*
 * Created on Nov 7, 2005
 *
 * ClassName	:  	MachineUnAccountedDetails.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savantit.prodacs.businessimplementation.production;

import java.io.Serializable;
import java.util.Date;

/**
 * @author kduraisamy
 *
 */
public class MachineNonAccountedDetails implements Serializable
{
   private Date prodDate;
   private int shiftId;
   private String shiftName;
   private String mcCde;
   private float prodHrs;
   private float nonProdHrs;
   private float nonAccuntedHrs;
   
   public MachineNonAccountedDetails()
   {
       this.prodDate = null;
       this.shiftId = 0;
       this.shiftName = "";
       this.mcCde = "";
       this.prodHrs = 0;
       this.nonProdHrs = 0;
       this.nonAccuntedHrs = 0;
   }
   

/**
 * @return Returns the mcCde.
 */
public String getMcCde() {
	return mcCde;
}
/**
 * @param mcCde The mcCde to set.
 */
public void setMcCde(String mcCde) {
	this.mcCde = mcCde;
}
/**
 * @return Returns the nonAccuntedHrs.
 */
public float getNonAccuntedHrs() {
	return nonAccuntedHrs;
}
/**
 * @param nonAccuntedHrs The nonAccuntedHrs to set.
 */
public void setNonAccuntedHrs(float nonAccuntedHrs) {
	this.nonAccuntedHrs = nonAccuntedHrs;
}
/**
 * @return Returns the prodDate.
 */
public Date getProdDate() {
	return prodDate;
}
/**
 * @param prodDate The prodDate to set.
 */
public void setProdDate(Date prodDate) {
	this.prodDate = prodDate;
}
/**
 * @return Returns the prodHrs.
 */
public float getProdHrs() {
	return prodHrs;
}
/**
 * @param prodHrs The prodHrs to set.
 */
public void setProdHrs(float prodHrs) {
	this.prodHrs = prodHrs;
}
/**
 * @return Returns the shiftId.
 */
public int getShiftId() {
	return shiftId;
}
/**
 * @param shiftId The shiftId to set.
 */
public void setShiftId(int shiftId) {
	this.shiftId = shiftId;
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
 * @return Returns the nonProdHrs.
 */
public float getNonProdHrs() {
	return nonProdHrs;
}
/**
 * @param nonProdHrs The nonProdHrs to set.
 */
public void setNonProdHrs(float nonProdHrs) {
	this.nonProdHrs = nonProdHrs;
}
}

/*
*$Log: MachineNonAccountedDetails.java,v $
*Revision 1.2  2005/11/08 14:42:18  vkrishnamoorthy
*FieldName changed in Value object.
*
*Revision 1.1  2005/11/07 13:40:45  kduraisamy
*ProductionAccounting Added.
*
*
*/