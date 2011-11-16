/*
 * Created on Dec 22, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.facade;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;

import javax.ejb.SessionBean;

import com.savantit.prodacs.businessimplementation.employee.EmployeeDetailsManager;
import com.savantit.prodacs.businessimplementation.employee.EmployeeException;
import com.savantit.prodacs.businessimplementation.machine.MachineDetailsManager;
import com.savantit.prodacs.businessimplementation.machine.MachineException;
import com.savantit.prodacs.businessimplementation.production.PopDetails;
import com.savantit.prodacs.businessimplementation.production.PopDetailsManager;
import com.savantit.prodacs.businessimplementation.production.ProductionAccountingDateDetails;
import com.savantit.prodacs.businessimplementation.production.ProductionDetailsManager;
import com.savantit.prodacs.businessimplementation.production.ProductionException;
import com.savantit.prodacs.businessimplementation.workcalendar.WorkCalendarDetailsManager;
import com.savantit.prodacs.businessimplementation.workcalendar.WorkCalendarException;
import com.savantit.prodacs.infra.beans.Filter;
/**
 * @ejb.bean name="SessionPopDetailsManager"
 *	jndi-name="SessionPopDetailsManagerBean"
 *	type="Stateless" 
 * 
 *--
 * This is needed for JOnAS.
 * If you are not using JOnAS you can safely remove the tags below.
 * @jonas.bean ejb-name="SessionPopDetailsManager"
 *	jndi-name="SessionPopDetailsManagerBean"
 * 
 *--
 **/
public abstract class SessionPopDetailsManagerBean implements SessionBean 
{
	PopDetailsManager objPopDetailsManager = new PopDetailsManager();
	ProductionDetailsManager objProductionDetailsManager = new ProductionDetailsManager();
	MachineDetailsManager objMachineDetailsManager = new MachineDetailsManager();
	WorkCalendarDetailsManager objWorkCalendarDetailsManager = new WorkCalendarDetailsManager();
	EmployeeDetailsManager objEmployeeDetailsManager = new EmployeeDetailsManager();
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap addNewPopDetails(PopDetails[] objPopDetails) throws ProductionException, SQLException
{ 
	return objPopDetailsManager.addNewPopDetails(objPopDetails);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public PopDetails getPopDetails(int popId) throws ProductionException,SQLException
{ 
	return objPopDetailsManager.getPopDetails(popId); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap getAllPopDetails(Filter[] filters,String sortBy,boolean ascending,int startIndex,int displayCount) throws ProductionException,SQLException
{ 
	return objPopDetailsManager.getAllPopDetails(filters,sortBy,ascending,startIndex,displayCount); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public Vector viewUnPostedPopProductionDetails(Date fromDate,Date toDate) throws ProductionException,SQLException
{ 
 return objPopDetailsManager.viewUnPostedPopProductionDetails(fromDate,toDate); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap makePopValid(Vector popIds) throws ProductionException, SQLException
{ 
 return objPopDetailsManager.makePopValid(popIds); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap makePopInValid(Vector popIds) throws ProductionException, SQLException
{ 
 return objPopDetailsManager.makePopInValid(popIds); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public LinkedHashMap getShiftDefnNameList() throws WorkCalendarException,SQLException
{ 
	return objWorkCalendarDetailsManager.getShiftDefnNameList(); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public LinkedHashMap getAllMachines() throws MachineException, SQLException
{ 
 return objMachineDetailsManager.getAllMachines(); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public LinkedHashMap getAllEmployees() throws EmployeeException, SQLException
{ 
	return objEmployeeDetailsManager.getAllEmployees(); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public LinkedHashMap getAllEmployeeTypes() throws SQLException,EmployeeException
{ 
	return objEmployeeDetailsManager.getAllEmployeeTypes(); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public LinkedHashMap getAllEmployeesByType(int empTypeId) throws EmployeeException, SQLException
{ 
	return objEmployeeDetailsManager.getAllEmployeesByType(empTypeId); 
}
/**
 * @throws ProductionException
 * @throws ParseException
 * @throws SQLException
 * @ejb.interface-method
 *	view-type="remote" 
**/
public ProductionAccountingDateDetails isPosted(String mcCde,Date prodDate,int shiftId) throws ProductionException, ParseException, SQLException{ 
 return objProductionDetailsManager.isPosted(mcCde,prodDate,shiftId);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean updatePopDetails(PopDetails objPopDetails)throws ProductionException,SQLException{ 
 return objPopDetailsManager.updatePopDetails(objPopDetails);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean isModifyForPop(int popId) throws ProductionException, SQLException{ 
 return objPopDetailsManager.isModifyForPop(popId);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public LinkedHashMap getShiftDefnNameList(Date crntDate) throws WorkCalendarException,SQLException{ 
 return objWorkCalendarDetailsManager.getShiftDefnNameList(crntDate); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public LinkedHashMap getAllEmployeesByTypes(int empTypId) throws EmployeeException, SQLException, ProductionException{ 
 return objProductionDetailsManager.getAllEmployeesByTypes(empTypId); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap postPopDetails(Vector vec_Pop_Ids) throws ProductionException, SQLException{ 
 return objPopDetailsManager.postPopDetails(vec_Pop_Ids); 
}
}
/***
$Log: SessionPopDetailsManagerBean.java,v $
Revision 1.17  2005/09/10 13:18:42  kduraisamy
order by clause added.

Revision 1.16  2005/08/25 12:35:30  kduraisamy
signature added for posting().

Revision 1.15  2005/07/11 10:41:11  kduraisamy
signature changed.

Revision 1.14  2005/06/22 05:23:14  kduraisamy
signature added for getAllEmployeesByType(empTypId).

Revision 1.13  2005/05/31 06:50:52  kduraisamy
signature changed.

Revision 1.12  2005/05/27 12:41:27  kduraisamy
signature changed.HashMap is changed to LinkedHashmap.

Revision 1.11  2005/05/19 14:30:06  kduraisamy
simpleDateFormat added.

Revision 1.10  2005/05/19 09:52:15  kduraisamy
return type is changed to Vector.

Revision 1.9  2005/05/14 07:18:02  kduraisamy
signature added for getShiftNameList().

Revision 1.8  2005/03/05 09:39:49  kduraisamy
signature changed for addProductionDetails().

Revision 1.7  2005/02/28 04:56:07  kduraisamy
LinkedHashMap is added for HashMap.

Revision 1.6  2005/01/28 14:54:56  kduraisamy
isModify() signature added.

Revision 1.5  2005/01/11 07:32:41  kduraisamy
signature for postProdnDetails() removed.

Revision 1.4  2005/01/08 09:46:05  kduraisamy
signature added for isPosted().

Revision 1.3  2005/01/06 13:00:46  kduraisamy
signature added for getAllEmployeesByType() .

Revision 1.2  2004/12/23 11:58:37  sduraisamy
Session created for getShiftDefnNsameList() and getAllEmployees() and getAllEmployeeTypes()

Revision 1.1  2004/12/22 12:01:19  sduraisamy
SessionBean Created for PopDetailsManager

***/