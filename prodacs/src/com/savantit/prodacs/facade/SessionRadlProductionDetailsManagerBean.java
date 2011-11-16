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
import com.savantit.prodacs.businessimplementation.job.JobDetailsManager;
import com.savantit.prodacs.businessimplementation.job.JobException;
import com.savantit.prodacs.businessimplementation.job.OperationDetails;
import com.savantit.prodacs.businessimplementation.machine.MachineDetailsManager;
import com.savantit.prodacs.businessimplementation.machine.MachineException;
import com.savantit.prodacs.businessimplementation.production.ProductionAccountingDateDetails;
import com.savantit.prodacs.businessimplementation.production.ProductionDetailsManager;
import com.savantit.prodacs.businessimplementation.production.ProductionException;
import com.savantit.prodacs.businessimplementation.production.RadialProductionDetails;
import com.savantit.prodacs.businessimplementation.production.RadlProductionDetailsManager;
import com.savantit.prodacs.businessimplementation.workcalendar.WorkCalendarDetailsManager;
import com.savantit.prodacs.businessimplementation.workcalendar.WorkCalendarException;
import com.savantit.prodacs.businessimplementation.workorder.WorkOrderDetailsManager;
import com.savantit.prodacs.businessimplementation.workorder.WorkOrderException;
import com.savantit.prodacs.infra.beans.Filter;
/**
 * @ejb.bean name="SessionRadlProductionDetailsManager"
 *	jndi-name="SessionRadlProductionDetailsManagerBean"
 *	type="Stateless" 
 * 
 *--
 * This is needed for JOnAS.
 * If you are not using JOnAS you can safely remove the tags below.
 * @jonas.bean ejb-name="SessionRadlProductionDetailsManager"
 *	jndi-name="SessionRadlProductionDetailsManagerBean"
 * 
 *--
 **/
public abstract class SessionRadlProductionDetailsManagerBean implements SessionBean {
	
	RadlProductionDetailsManager objRadlProductionDetailsManager = new RadlProductionDetailsManager();
	ProductionDetailsManager objProductionDetailsManager = new ProductionDetailsManager();
	MachineDetailsManager objMachineDetailsManager = new MachineDetailsManager();
	WorkOrderDetailsManager objWorkOrderDetailsManager = new WorkOrderDetailsManager();
	JobDetailsManager objJobDetailsManager = new JobDetailsManager();
	WorkCalendarDetailsManager objWorkCalendarDetailsManager = new WorkCalendarDetailsManager();
	EmployeeDetailsManager objEmployeeDetailsManager = new EmployeeDetailsManager();
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
public LinkedHashMap getAllJobs() throws JobException,SQLException
{ 
	return objJobDetailsManager.getAllJobs(); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public LinkedHashMap getWorkOrderList() throws SQLException, WorkOrderException
{ 
	return objWorkOrderDetailsManager.getWorkOrderList(); 
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
public HashMap getAllRadialProductionDetails(Filter filters[],String sortBy,boolean ascending,int startIndex,int displayCount) throws ProductionException, SQLException
{ 
	return objRadlProductionDetailsManager.getAllRadialProductionDetails(filters,sortBy,ascending,startIndex,displayCount); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public Vector getProdnJobDetailsByWorkOrder(int woId) throws SQLException,ProductionException{ 
 return objProductionDetailsManager.getProdnJobDetailsByWorkOrder(woId); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public Vector getProdnJobOperationDetails(int woJbId) throws ProductionException,SQLException{ 
 return objProductionDetailsManager.getProdnJobOperationDetails(woJbId); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public LinkedHashMap getAllEmployeesByTypes() throws EmployeeException, SQLException, ProductionException{ 
 return objProductionDetailsManager.getAllEmployeesByTypes(); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap minReqdEmployees() throws SQLException, ProductionException{ 
 return objProductionDetailsManager.minReqdEmployees(); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap addNewRadialProductionDetails(RadialProductionDetails[] objRadialProductionDetails) throws ProductionException, SQLException{ 
 return objRadlProductionDetailsManager.addNewRadialProductionDetails(objRadialProductionDetails);
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
public LinkedHashMap getAllMatlTypes() throws ProductionException, SQLException{ 
 return objRadlProductionDetailsManager.getAllMatlTypes(); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public Vector viewUnPostedRadlProductionDetails(Date fromDate,Date toDate) throws SQLException, ProductionException{ 
 return objRadlProductionDetailsManager.viewUnPostedRadlProductionDetails(fromDate,toDate); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public RadialProductionDetails getRadlProductionDetails(int radlId) throws ProductionException,SQLException{ 
 return objRadlProductionDetailsManager.getRadlProductionDetails(radlId); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public LinkedHashMap getAllRadialMachines() throws MachineException, SQLException{ 
 return objMachineDetailsManager.getAllRadialMachines(); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean isRadlOperation(int woJbStatId,int startOpn,int endOpn,int value) throws ProductionException, SQLException{ 
 return objProductionDetailsManager.isRadlOperation(woJbStatId,startOpn,endOpn,value);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean isModifyForRadl(int radlId) throws ProductionException, SQLException{ 
 return objRadlProductionDetailsManager.isModifyForRadl(radlId);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean updateRadlProductionDetails(RadialProductionDetails objRadialProductionDetails) throws ProductionException, SQLException{ 
 return objRadlProductionDetailsManager.updateRadlProductionDetails(objRadialProductionDetails);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap makeRadlProductionValid(Vector radlIds) throws ProductionException, SQLException{ 
 return objRadlProductionDetailsManager.makeRadlProductionValid(radlIds); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap makeRadlProductionInValid(Vector radlIds) throws ProductionException, SQLException{ 
 return objRadlProductionDetailsManager.makeRadlProductionInValid(radlIds); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public Vector getProdnJobOperationDetailsForUpdate(int woJbId,int prodId,int radlId) throws ProductionException,SQLException{ 
 return objProductionDetailsManager.getProdnJobOperationDetailsForUpdate(woJbId,prodId,radlId); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public Vector getProdnJobDetailsForUpdateByWorkOrder(int woId,int prodId,int radlId,int finalProdId,int despatchId,int shipmentId) throws SQLException,ProductionException{ 
 return objProductionDetailsManager.getProdnJobDetailsForUpdateByWorkOrder(woId,prodId,radlId,finalProdId,despatchId,shipmentId); 
}
/**
 * @throws ProductionException
 * @throws SQLException
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean isReworkOperations(int woJbStatId[],int startOpn,int endOpn) throws ProductionException, SQLException{ 
 return objProductionDetailsManager.isReworkOperations(woJbStatId,startOpn,endOpn);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public LinkedHashMap getAllRadlMachines(Date crntDate,int shiftId) throws MachineException, SQLException{ 
 return objMachineDetailsManager.getAllRadlMachines(crntDate,shiftId); 
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
public OperationDetails[] viewOperations(int woJbId) throws SQLException, ProductionException{ 
 return objProductionDetailsManager.viewOperations(woJbId); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap postRadlProductionDetails(Vector vec_RadlProdIds) throws ProductionException, SQLException{ 
 return objRadlProductionDetailsManager.postRadlProductionDetails(vec_RadlProdIds); 
}
}
/***
$Log: SessionRadlProductionDetailsManagerBean.java,v $
Revision 1.29  2005/09/10 13:18:42  kduraisamy
order by clause added.

Revision 1.28  2005/08/25 12:35:30  kduraisamy
signature added for posting().

Revision 1.27  2005/07/23 08:51:14  kduraisamy
signature changed for getProdJobDetailsByworkorder().

Revision 1.26  2005/07/15 17:00:17  kduraisamy
getProdnJobDetailsByWorkOrder() and getProdnJobDetailsForUpdateByWorkOrder() changed because of FINAL PRODUCTION.

Revision 1.25  2005/07/11 10:41:11  kduraisamy
signature changed.

Revision 1.24  2005/06/30 07:09:49  kduraisamy
viewOperationDetails() query added.

Revision 1.23  2005/05/31 06:50:52  kduraisamy
signature changed.

Revision 1.22  2005/05/27 12:41:27  kduraisamy
signature changed.HashMap is changed to LinkedHashmap.

Revision 1.21  2005/05/19 14:30:06  kduraisamy
simpleDateFormat added.

Revision 1.20  2005/05/19 09:52:15  kduraisamy
return type is changed to Vector.

Revision 1.19  2005/05/05 06:21:06  kduraisamy
signature added for getShiftDefnNameList(Date).

Revision 1.18  2005/05/05 06:19:33  kduraisamy
signature added for getAllRadlMachines(Date,shift).

Revision 1.17  2005/03/05 09:39:49  kduraisamy
signature changed for addProductionDetails().

Revision 1.16  2005/03/02 07:08:25  kduraisamy
signature changed for getAllRadlMachines().

Revision 1.15  2005/02/03 09:27:23  sponnusamy
signature adde for isReworkOpns()

Revision 1.14  2005/02/02 09:03:32  kduraisamy
signature added for getJobDetailsForUpdate().

Revision 1.13  2005/01/31 08:42:31  kduraisamy
signature added for make valid,invalid.

Revision 1.12  2005/01/30 10:29:20  kduraisamy
signature added for updateRadlProduction().

Revision 1.11  2005/01/28 14:54:56  kduraisamy
isModify() signature added.

Revision 1.10  2005/01/28 12:10:24  kduraisamy
signature added for isModify().

Revision 1.9  2005/01/25 07:39:32  kduraisamy
signature added for isRadlOperations().

Revision 1.8  2005/01/25 07:00:20  kduraisamy
signature added for getAllRadlMachines().

Revision 1.7  2005/01/20 10:19:11  kduraisamy
signature added for radl get method.

Revision 1.6  2005/01/11 11:35:54  kduraisamy
signature added for viewUnposted().

Revision 1.5  2005/01/08 11:43:28  kduraisamy
signature added for getAllMatlTypes().

Revision 1.4  2005/01/08 09:46:05  kduraisamy
signature added for isPosted().

Revision 1.3  2005/01/07 09:37:24  kduraisamy
signature added for addNewRadlProductionDetails().

Revision 1.2  2005/01/06 08:01:45  kduraisamy
signature added for getJobDetails,minReqdQtyEmployee(),..

Revision 1.1  2004/12/23 11:58:37  sduraisamy
Session created for getShiftDefnNsameList() and getAllEmployees() and getAllEmployeeTypes()

***/