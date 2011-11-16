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
import com.savantit.prodacs.businessimplementation.production.NonProductionDetails;
import com.savantit.prodacs.businessimplementation.production.NonProductionDetailsManager;
import com.savantit.prodacs.businessimplementation.production.ProductionAccountingDateDetails;
import com.savantit.prodacs.businessimplementation.production.ProductionDetailsManager;
import com.savantit.prodacs.businessimplementation.production.ProductionException;
import com.savantit.prodacs.businessimplementation.workcalendar.WorkCalendarDetailsManager;
import com.savantit.prodacs.businessimplementation.workcalendar.WorkCalendarException;
import com.savantit.prodacs.infra.beans.Filter;
/**
 * @ejb.bean name="SessionNonProductionDetailsManager"
 *	jndi-name="SessionNonProductionDetailsManagerBean"
 *	type="Stateless" 
 * 
 *--
 * This is needed for JOnAS.
 * If you are not using JOnAS you can safely remove the tags below.
 * @jonas.bean ejb-name="SessionNonProductionDetailsManager"
 *	jndi-name="SessionNonProductionDetailsManagerBean"
 * 
 *--
 **/
public abstract class SessionNonProductionDetailsManagerBean implements	SessionBean
{
	NonProductionDetailsManager objNonProductionDetailsManager = new NonProductionDetailsManager();
	ProductionDetailsManager objProductionDetailsManager = new ProductionDetailsManager();
	MachineDetailsManager objMachineDetailsManager = new MachineDetailsManager();
	WorkCalendarDetailsManager objWorkCalendarDetailsManager = new WorkCalendarDetailsManager();
	EmployeeDetailsManager objEmployeeDetailsManager = new EmployeeDetailsManager();
	
/**
 * @throws ProductionException
 * @throws SQLException
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap addNewNonProductionDetails(NonProductionDetails[] objNonProductionDetails) throws ProductionException, SQLException
{ 
	return objNonProductionDetailsManager.addNewNonProductionDetails(objNonProductionDetails);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public Vector viewUnPostedNonProductionDetails(Date fromDate,Date toDate) throws ProductionException,SQLException
{ 
	return objNonProductionDetailsManager.viewUnPostedNonProductionDetails(fromDate,toDate); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public NonProductionDetails getNonProductionDetails(int nprodId) throws ProductionException,SQLException
{ 
	return objNonProductionDetailsManager.getNonProductionDetails(nprodId); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap getAllNonProductionDetails(Filter filters[],String sortBy,boolean ascending,int startIndex,int displayCount) throws ProductionException, SQLException
{ 
	return objNonProductionDetailsManager.getAllNonProductionDetails(filters,sortBy,ascending,startIndex,displayCount); 
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
public LinkedHashMap getShiftDefnNameList() throws WorkCalendarException,SQLException
{ 
	return objWorkCalendarDetailsManager.getShiftDefnNameList(); 
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
public LinkedHashMap getAllEmployeesByTypes() throws EmployeeException, SQLException, ProductionException
{ 
	return objProductionDetailsManager.getAllEmployeesByTypes(); 
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
public LinkedHashMap getAllIdlBrkDwnRsns() throws SQLException, ProductionException
{ 
	return objNonProductionDetailsManager.getAllIdlBrkDwnRsns(); 
}
/**
 * @throws ProductionException
 * @throws SQLException
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean updateNonProductionDetails(NonProductionDetails objNonProductionDetails) throws ProductionException, SQLException
{ 
	return objNonProductionDetailsManager.updateNonProductionDetails(objNonProductionDetails);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap minReqdEmployees() throws SQLException, ProductionException
{ 
	return objProductionDetailsManager.minReqdEmployees(); 
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
public HashMap makeNonProductionValid(Vector nprodIds) throws ProductionException, SQLException{ 
 return objNonProductionDetailsManager.makeNonProductionValid(nprodIds); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap makeNonProductionInValid(Vector nprodIds) throws ProductionException, SQLException{ 
 return objNonProductionDetailsManager.makeNonProductionInValid(nprodIds); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean isModifyForNprod(int nprodId) throws ProductionException, SQLException{ 
 return objNonProductionDetailsManager.isModifyForNprod(nprodId);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public LinkedHashMap getAllMachines(Date crntDate,int shiftId) throws MachineException, SQLException{ 
 return objMachineDetailsManager.getAllMachines(crntDate,shiftId); 
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
public HashMap postNonProductionDetails(Vector vec_NonProd_Ids) throws ProductionException, SQLException{ 
 return objNonProductionDetailsManager.postNonProductionDetails(vec_NonProd_Ids); 
}
}
/***
$Log: SessionNonProductionDetailsManagerBean.java,v $
Revision 1.23  2005/09/10 13:18:42  kduraisamy
order by clause added.

Revision 1.22  2005/08/25 12:35:30  kduraisamy
signature added for posting().

Revision 1.21  2005/07/11 10:40:59  kduraisamy
signature changed.

Revision 1.20  2005/05/31 06:50:52  kduraisamy
signature changed.

Revision 1.19  2005/05/27 12:41:27  kduraisamy
signature changed.HashMap is changed to LinkedHashmap.

Revision 1.18  2005/05/19 14:30:06  kduraisamy
simpleDateFormat added.

Revision 1.17  2005/05/19 09:52:15  kduraisamy
return type is changed to Vector.

Revision 1.16  2005/05/14 07:18:02  kduraisamy
signature added for getShiftNameList().

Revision 1.15  2005/05/14 07:00:13  kduraisamy
signature added for getAllMachines(date,shift);

Revision 1.14  2005/03/05 09:39:49  kduraisamy
signature changed for addProductionDetails().

Revision 1.13  2005/03/04 12:55:41  kduraisamy
throws SQLException() added .

Revision 1.12  2005/02/28 04:56:06  kduraisamy
LinkedHashMap is added for HashMap.

Revision 1.11  2005/01/28 14:54:56  kduraisamy
isModify() signature added.

Revision 1.10  2005/01/18 12:52:42  kduraisamy
signature added for make valid invalid methods..

Revision 1.9  2005/01/11 07:32:41  kduraisamy
signature for postProdnDetails() removed.

Revision 1.8  2005/01/08 09:46:05  kduraisamy
signature added for isPosted().

Revision 1.7  2005/01/05 10:16:03  kduraisamy
signature added for minReqdEmployees().

Revision 1.6  2005/01/05 05:52:22  kduraisamy
signature added for updateNonProductionDetails().

Revision 1.5  2005/01/03 10:45:06  kduraisamy
signature added for getAllIdlBrkDwnRsns().

Revision 1.4  2005/01/03 10:15:02  kduraisamy
signature added for getAllEmployeesByTypes().

Revision 1.3  2004/12/23 11:58:37  sduraisamy
Session created for getShiftDefnNsameList() and getAllEmployees() and getAllEmployeeTypes()

Revision 1.2  2004/12/23 07:39:18  sduraisamy
SessionBean for getAllMachines(),getAllJobs() and getWorkOrderList() included

Revision 1.1  2004/12/22 11:52:52  sduraisamy
SessionBean Created for NonProductionDetailsManager

***/