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
import com.savantit.prodacs.businessimplementation.production.MachineNonAccountedDetails;
import com.savantit.prodacs.businessimplementation.production.ProductionAccountingDateDetails;
import com.savantit.prodacs.businessimplementation.production.ProductionDetails;
import com.savantit.prodacs.businessimplementation.production.ProductionDetailsManager;
import com.savantit.prodacs.businessimplementation.production.ProductionException;
import com.savantit.prodacs.businessimplementation.reports.ReportsDetailsManager;
import com.savantit.prodacs.businessimplementation.reports.ReportsException;
import com.savantit.prodacs.businessimplementation.workcalendar.WorkCalendarDetailsManager;
import com.savantit.prodacs.businessimplementation.workcalendar.WorkCalendarException;
import com.savantit.prodacs.businessimplementation.workorder.WorkOrderDetailsManager;
import com.savantit.prodacs.businessimplementation.workorder.WorkOrderException;
import com.savantit.prodacs.infra.beans.Filter;
/**
 * @ejb.bean name="SessionProductionDetailsManager"
 *	jndi-name="SessionProductionDetailsManagerBean"
 *	type="Stateless" 
 * 
 *--
 * This is needed for JOnAS.
 * If you are not using JOnAS you can safely remove the tags below.
 * @jonas.bean ejb-name="SessionProductionDetailsManager"
 *	jndi-name="SessionProductionDetailsManagerBean"
 * 
 *--
 **/
public abstract class SessionProductionDetailsManagerBean implements SessionBean
{
	ProductionDetailsManager objProductionDetailsManager = new ProductionDetailsManager();
	MachineDetailsManager objMachineDetailsManager = new MachineDetailsManager();
	WorkOrderDetailsManager objWorkOrderDetailsManager = new WorkOrderDetailsManager();
	JobDetailsManager objJobDetailsManager = new JobDetailsManager();
	WorkCalendarDetailsManager objWorkCalendarDetailsManager = new WorkCalendarDetailsManager();
	EmployeeDetailsManager objEmployeeDetailsManager = new EmployeeDetailsManager();
	ReportsDetailsManager objReportsDetailsManager = new ReportsDetailsManager();
	
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap addNewProductionDetails(ProductionDetails[] objProductionDetails) throws ProductionException, SQLException
{ 
	return objProductionDetailsManager.addNewProductionDetails(objProductionDetails);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public Vector viewUnPostedProductionDetails(Date fromDate,Date toDate) throws SQLException, ProductionException
{ 
	return objProductionDetailsManager.viewUnPostedProductionDetails(fromDate,toDate); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public Vector getProdnJobDetailsByWorkOrder(int woId) throws SQLException,ProductionException
{ 
	return objProductionDetailsManager.getProdnJobDetailsByWorkOrder(woId); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public Vector getProdnJobOperationDetails(int woJbId) throws ProductionException,SQLException
{ 
 return objProductionDetailsManager.getProdnJobOperationDetails(woJbId); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public ProductionDetails getProductionDetails(int prodId) throws ProductionException,SQLException
{ 
	return objProductionDetailsManager.getProductionDetails(prodId); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap getAllProductionDetails(Filter filters[],String sortBy,boolean ascending,int startIndex,int displayCount) throws ProductionException,SQLException
{ 
	return objProductionDetailsManager.getAllProductionDetails(filters,sortBy,ascending,startIndex,displayCount); 
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
public LinkedHashMap getWorkOrderList() throws SQLException, WorkOrderException
{ 
	return objWorkOrderDetailsManager.getWorkOrderList(); 
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
public LinkedHashMap getAllEmployeeTypes() throws SQLException,EmployeeException
{ 
	return objEmployeeDetailsManager.getAllEmployeeTypes(); 
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
public float getStandardHrs(int woJbId,int startOpn,int endOpn) throws ProductionException, SQLException
{ 
	return objProductionDetailsManager.getStandardHrs(woJbId,startOpn,endOpn); 
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
public Vector getProdnJobDetailsForUpdateByWorkOrder(int woId,int prodId,int radlId,int finalProdId,int despatchId,int shipmentId) throws SQLException,ProductionException{ 
 return objProductionDetailsManager.getProdnJobDetailsForUpdateByWorkOrder(woId,prodId,radlId,finalProdId,despatchId,shipmentId); 
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
public boolean isModify(int prodId) throws ProductionException, SQLException{ 
 return objProductionDetailsManager.isModify(prodId);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean updateProductionDetails(ProductionDetails objProductionDetails) throws ProductionException, SQLException{ 
 return objProductionDetailsManager.updateProductionDetails(objProductionDetails);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap makeProductionValid(Vector prodIds) throws ProductionException, SQLException{ 
 return objProductionDetailsManager.makeProductionValid(prodIds); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap makeProductionInValid(Vector prodIds) throws ProductionException, SQLException{ 
 return objProductionDetailsManager.makeProductionInValid(prodIds); 
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
public LinkedHashMap getAllProdMachines() throws MachineException, SQLException{ 
 return objMachineDetailsManager.getAllProdMachines(); 
}
/**
 * @throws SQLException
 * @throws SQLException
 * @throws ProductionException
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
public LinkedHashMap getAllProdMachines(Date crntDate,int shiftId) throws MachineException, SQLException{ 
 return objMachineDetailsManager.getAllProdMachines(crntDate,shiftId); 
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
public LinkedHashMap currentPostingRule() throws ProductionException, SQLException{ 
 return objProductionDetailsManager.currentPostingRule(); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean changePostingRule(int ruleId) throws ProductionException, SQLException{ 
 return objProductionDetailsManager.changePostingRule(ruleId);
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
public HashMap postProductionDetails(Vector vec_ProdIds) throws ProductionException, SQLException{ 
 return objProductionDetailsManager.postProductionDetails(vec_ProdIds); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public MachineNonAccountedDetails[] checkMachineAccounting(Date fromDate,Date toDate,String mcCde) throws ProductionException, SQLException{ 
 return objProductionDetailsManager.checkMachineAccounting(fromDate,toDate,mcCde); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public double fetchProductionEnteredHrs(Date fromDate,Date toDate,String mcCde) throws ProductionException, SQLException{ 
 return objProductionDetailsManager.fetchProductionEnteredHrs(fromDate,toDate,mcCde); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public double fetchNonProductionEnteredHrs(Date fromDate,Date toDate,String mcCde) throws ProductionException, SQLException{ 
    return objProductionDetailsManager.fetchNonProductionEnteredHrs(fromDate,toDate,mcCde); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public double calculateMachineAvailHrs(Date fromDate,Date toDate) throws ReportsException, SQLException{ 
 return objReportsDetailsManager.calculateMachineAvailHrs(fromDate,toDate); 
}
}
/***
$Log: SessionProductionDetailsManagerBean.java,v $
Revision 1.38  2005/11/10 07:04:33  kduraisamy
ProductionAccounting Added.

Revision 1.37  2005/11/07 13:54:41  kduraisamy
ProductionAccounting Added.

Revision 1.36  2005/09/10 13:18:42  kduraisamy
order by clause added.

Revision 1.35  2005/08/25 12:35:30  kduraisamy
signature added for posting().

Revision 1.34  2005/07/23 08:51:14  kduraisamy
signature changed for getProdJobDetailsByworkorder().

Revision 1.33  2005/07/15 17:00:17  kduraisamy
getProdnJobDetailsByWorkOrder() and getProdnJobDetailsForUpdateByWorkOrder() changed because of FINAL PRODUCTION.

Revision 1.32  2005/07/11 10:41:11  kduraisamy
signature changed.

Revision 1.31  2005/06/30 07:09:49  kduraisamy
viewOperationDetails() query added.

Revision 1.30  2005/05/31 06:50:52  kduraisamy
signature changed.

Revision 1.29  2005/05/27 12:41:27  kduraisamy
signature changed.HashMap is changed to LinkedHashmap.

Revision 1.28  2005/05/19 14:30:06  kduraisamy
simpleDateFormat added.

Revision 1.27  2005/05/19 09:52:15  kduraisamy
return type is changed to Vector.

Revision 1.26  2005/05/12 13:57:40  kduraisamy
HashMap is changed to LinkedHashMap().

Revision 1.25  2005/05/12 07:58:11  kduraisamy
signatures added for currentPostingRule().

Revision 1.24  2005/05/05 04:58:10  vkrishnamoorthy
signature linkedHashMap added .

Revision 1.23  2005/05/04 12:22:32  kduraisamy
signature added for getShiftDefnNameList().

Revision 1.22  2005/04/27 07:20:27  kduraisamy
signature added for getAllProdMachines(Date,shiftId).

Revision 1.21  2005/03/03 09:50:22  kduraisamy
addNewProductionDetails() modified.

Revision 1.20  2005/02/28 04:56:06  kduraisamy
LinkedHashMap is added for HashMap.

Revision 1.19  2005/02/03 09:27:23  sponnusamy
signature adde for isReworkOpns()

Revision 1.18  2005/02/02 06:38:57  kduraisamy
signature modified for getJob  for update().

Revision 1.17  2005/02/02 06:37:04  kduraisamy
signature modified for getOperation for update().

Revision 1.16  2005/01/31 05:36:56  kduraisamy
signature added for getAllProdMachines().

Revision 1.15  2005/01/25 07:39:32  kduraisamy
signature added for isRadlOperations().

Revision 1.14  2005/01/18 12:50:50  kduraisamy
signature added for make valid invalid methods..

Revision 1.13  2005/01/17 15:13:04  kduraisamy
signature added for updateProductionDetails()

Revision 1.12  2005/01/17 09:15:13  kduraisamy
signature added for isModify().

Revision 1.11  2005/01/13 12:10:29  kduraisamy
signature added for new get methods for update.

Revision 1.10  2005/01/11 07:32:41  kduraisamy
signature for postProdnDetails() removed.

Revision 1.9  2005/01/07 06:23:04  kduraisamy
signature added for isPosted().

Revision 1.8  2005/01/05 07:57:26  kduraisamy
signature added for minReqdEmployees().

Revision 1.7  2004/12/30 07:28:35  kduraisamy
signature changed for getStandardHrs().

Revision 1.6  2004/12/30 07:14:21  kduraisamy
signature added for getStandardHrs().

Revision 1.5  2004/12/27 09:43:32  sduraisamy
getProductionJobOperationDetails() argument modified

Revision 1.4  2004/12/27 09:26:11  kduraisamy
signature added for getAllEmployeesByTypes()..

Revision 1.3  2004/12/23 11:58:37  sduraisamy
Session created for getShiftDefnNsameList() and getAllEmployees() and getAllEmployeeTypes()

Revision 1.2  2004/12/23 07:39:18  sduraisamy
SessionBean for getAllMachines(),getAllJobs() and getWorkOrderList() included

Revision 1.1  2004/12/22 11:26:26  sduraisamy
SessionBean Created for ProductionDetailsManager

***/