/*
 * Created on Jan 20, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.facade;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;

import javax.ejb.SessionBean;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.savantit.prodacs.businessimplementation.employee.EmployeeDetailsManager;
import com.savantit.prodacs.businessimplementation.employee.EmployeeException;
import com.savantit.prodacs.businessimplementation.payroll.PayRollDetailsManager;
import com.savantit.prodacs.businessimplementation.payroll.PayRollException;
import com.savantit.prodacs.businessimplementation.production.ProductionException;
import com.savantit.prodacs.businessimplementation.workcalendar.WorkCalendarDetailsManager;
import com.savantit.prodacs.businessimplementation.workcalendar.WorkCalendarException;
import com.savantit.prodacs.infra.beans.Filter;
/**
 * @ejb.bean name="SessionPayrollDetailsManager"
 *	jndi-name="SessionPayrollDetailsManagerBean"
 *	type="Stateless" 
 * 
 *--
 * This is needed for JOnAS.
 * If you are not using JOnAS you can safely remove the tags below.
 * @jonas.bean ejb-name="SessionPayrollDetailsManager"
 *	jndi-name="SessionPayrollDetailsManagerBean"
 * 
 *--
 **/
public abstract class SessionPayrollDetailsManagerBean implements SessionBean
{
	PayRollDetailsManager objPayRollDetailsManager = new PayRollDetailsManager();
	EmployeeDetailsManager objEmployeeDetailsManager = new EmployeeDetailsManager();
	WorkCalendarDetailsManager objWorkCalendarDetailsManager = new WorkCalendarDetailsManager();
	
/**
 * @throws PayRollException
 * @throws SQLException
 * @ejb.interface-method
 *	view-type="remote" 
**/
public Vector getEmployeeDetails(int payrlStatId,int val) throws SQLException, PayRollException{ 
 return objPayRollDetailsManager.getEmployeeDetails(payrlStatId,val); 
}
/**
 * @throws PayRollException
 * @throws SQLException
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean createNewPayrollCycle(String cycleType,String cycle) throws SQLException, PayRollException{ 
 return objPayRollDetailsManager.createNewPayrollCycle(cycleType,cycle);
}
/**
 * @throws PayRollException
 * @throws SQLException
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap getAllPyrlCycleStatForInterface() throws SQLException, PayRollException{ 
 return objPayRollDetailsManager.getAllPyrlCycleStatForInterface(); 
}
/**
 * @throws PayRollException
 * @throws SQLException
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap getForPayRoll(int pyrlCycleStatId,int startIndex,int displayCnt) throws SQLException, PayRollException{ 
 return objPayRollDetailsManager.getForPayRoll(pyrlCycleStatId,startIndex,displayCnt); 
}
/**
 * @throws SQLException
 * @throws PayRollException
 * @throws SAXException
 * @throws IOException
 * @throws ParserConfigurationException
 * @throws ProductionException
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean createPyrlInterface(int pyrlCycleStatId,String createdBy) throws SQLException, PayRollException, SAXException, IOException, ParserConfigurationException, ProductionException
{
 return objPayRollDetailsManager.createPyrlInterface(pyrlCycleStatId,createdBy);
}
/**
 * @throws PayRollException
 * @throws SQLException
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap createPayRoll(Vector prePyrlIds,String createdBy) throws SQLException, PayRollException
{
 return objPayRollDetailsManager.createPayRoll(prePyrlIds,createdBy);
}
/**
 * @throws PayRollException
 * @throws SQLException
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap getAllPyrlCycleStatForPayroll() throws SQLException, PayRollException{ 
 return objPayRollDetailsManager.getAllPyrlCycleStatForPayroll(); 
}
/**
 * @throws SQLException
 * @throws PayRollException
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean adjustPayRollDetails(Vector vec_PyrlDetails,String modifiedBy) throws SQLException, PayRollException 
{
 return objPayRollDetailsManager.adjustPayRollDetails(vec_PyrlDetails,modifiedBy);
}
/**
 * @throws PayRollException
 * @throws SQLException
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean closePayrollDetails(int pyrlCycleStatId) throws PayRollException, SQLException{ 
 return objPayRollDetailsManager.closePayrollDetails(pyrlCycleStatId);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap getForPayRollAdjstmnt(Filter[] filters,String sortBy,boolean ascending) throws PayRollException, SQLException{ 
 return objPayRollDetailsManager.getForPayRollAdjstmnt(filters,sortBy,ascending); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public LinkedHashMap getAllEmployeesByType(int empTypeId) throws EmployeeException, SQLException{ 
 return objEmployeeDetailsManager.getAllEmployeesByType(empTypeId); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public LinkedHashMap getAllEmployeeTypes() throws SQLException,EmployeeException{ 
 return objEmployeeDetailsManager.getAllEmployeeTypes(); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public LinkedHashMap getShiftDefnNameList() throws WorkCalendarException,SQLException{ 
 return objWorkCalendarDetailsManager.getShiftDefnNameList(); 
}
/**
 * @throws PayRollException
 * @throws SQLException
 * @ejb.interface-method
 *	view-type="remote" 
**/
public  Vector getPayRollDateShiftDetails(int payrlStatId) throws SQLException, PayRollException{ 
 return objPayRollDetailsManager.getPayRollDateShiftDetails(payrlStatId); 
}
/**
 * @throws PayRollException
 * @throws SQLException
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap getForClosePayRoll(int pyrlCycleStatId,int startIndex,int displayCnt) throws SQLException, PayRollException{ 
 return objPayRollDetailsManager.getForClosePayRoll(pyrlCycleStatId,startIndex,displayCnt); 
}
/**
 * @throws PayRollException
 * @throws SQLException
 * @throws ParseException
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean createNewPayrollCycleStat() throws PayRollException, SQLException, ParseException
{ 
 return objPayRollDetailsManager.createNewPayrollCycleStat();
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap getAllPayRollIdsInfo(Filter[] filters,String sortBy,boolean ascending,int startIndex, int displayCount) throws PayRollException, SQLException{ 
 return objPayRollDetailsManager.getAllPayRollIdsInfo(filters,sortBy,ascending,startIndex,displayCount); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap getCurrentPayrollCycle() throws SQLException, PayRollException{ 
 return objPayRollDetailsManager.getCurrentPayrollCycle(); 
}
}