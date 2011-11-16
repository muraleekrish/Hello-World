/*
 * Created on Dec 7, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.facade;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;

import javax.ejb.SessionBean;

import com.savantit.prodacs.businessimplementation.customer.CustomerDetailsManager;
import com.savantit.prodacs.businessimplementation.customer.CustomerException;
import com.savantit.prodacs.businessimplementation.job.JobDetails;
import com.savantit.prodacs.businessimplementation.job.JobDetailsManager;
import com.savantit.prodacs.businessimplementation.job.JobException;
import com.savantit.prodacs.businessimplementation.workorder.DummyWorkOrderDetails;
import com.savantit.prodacs.businessimplementation.workorder.PreCloseDetails;
import com.savantit.prodacs.businessimplementation.workorder.WorkOrderDetails;
import com.savantit.prodacs.businessimplementation.workorder.WorkOrderDetailsManager;
import com.savantit.prodacs.businessimplementation.workorder.WorkOrderException;
import com.savantit.prodacs.infra.beans.Filter;
/**
 * @ejb.bean name="SessionWorkOrderDetailsManager"
 *	jndi-name="SessionWorkOrderDetailsManagerBean"
 *	type="Stateless" 
 * 
 *--
 * This is needed for JOnAS.
 * If you are not using JOnAS you can safely remove the tags below.
 * @jonas.bean ejb-name="SessionWorkOrderDetailsManager"
 *	jndi-name="SessionWorkOrderDetailsManagerBean"
 * 
 *--
 **/
public abstract class SessionWorkOrderDetailsManagerBean implements SessionBean 
{

	WorkOrderDetailsManager objWorkOrderDetailsManager = new WorkOrderDetailsManager();
	CustomerDetailsManager objCustomerDetailsManager = new CustomerDetailsManager();
	JobDetailsManager objJobDetailsManager = new JobDetailsManager();
	
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public int addJobGeneralName(String jb_Gnrl_Name) throws JobException, SQLException
	{
		return objJobDetailsManager.addJobGeneralName(jb_Gnrl_Name);
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	 **/
	public boolean addJobDetails(JobDetails objJobDetails) throws JobException, SQLException 
	{
		return objJobDetailsManager.addJobDetails(objJobDetails);
		
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public LinkedHashMap getAllJobGeneralName() throws JobException,SQLException
	{ 
	 return objJobDetailsManager.getAllJobGeneralName(); 
	}
	
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public boolean addNewWorkOrder(WorkOrderDetails objWorkOrderDetails) throws WorkOrderException, SQLException
	{ 
		return objWorkOrderDetailsManager.addNewWorkOrder(objWorkOrderDetails);
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public boolean updateWorkOrder(WorkOrderDetails objWorkOrderDetails) throws WorkOrderException, SQLException
	{ 
		return objWorkOrderDetailsManager.updateWorkOrder(objWorkOrderDetails);
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public HashMap makeWorkOrderValid(Vector workOrderIds) throws SQLException, WorkOrderException
	{ 
		return objWorkOrderDetailsManager.makeWorkOrderValid(workOrderIds); 
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public HashMap makeWorkOrderInValid(Vector workOrderIds) throws SQLException, WorkOrderException
	{ 
		return objWorkOrderDetailsManager.makeWorkOrderInValid(workOrderIds); 
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public WorkOrderDetails getWorkOrderDetails(int WOId) throws WorkOrderException, SQLException
	{ 
		return objWorkOrderDetailsManager.getWorkOrderDetails(WOId); 
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public HashMap getAllWorkOrderDetails(Filter[] filters,String sortBy,boolean ascending,int startIndex,int displayCount) throws WorkOrderException,SQLException
	{ 
		return objWorkOrderDetailsManager.getAllWorkOrderDetails(filters,sortBy,ascending,startIndex,displayCount); 
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public boolean addNewDummyWorkOrder(DummyWorkOrderDetails objDummyWorkOrderDetails) throws WorkOrderException, SQLException
	{ 
		return objWorkOrderDetailsManager.addNewDummyWorkOrder(objDummyWorkOrderDetails);
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public HashMap workOrderJobCloseView(Filter[] filter,String sortBy,boolean ascending,int startIndex,int displayCount) throws WorkOrderException, SQLException
	{ 
		return objWorkOrderDetailsManager.workOrderJobCloseView(filter,sortBy,ascending,startIndex,displayCount); 
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public LinkedHashMap getCustomerNameByType(int custTypId) throws CustomerException, SQLException
	{ 
		return objCustomerDetailsManager.getCustomerNameByType(custTypId); 
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public LinkedHashMap getCustomerTypes() throws CustomerException, SQLException
	{ 
		return objCustomerDetailsManager.getCustomerTypes(); 
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public Vector getJobByGeneralName(int gnrlId,int custId) throws JobException,SQLException
	{ 
		return objJobDetailsManager.getJobByGeneralName(gnrlId,custId); 
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public Vector getDrawingNoByJobName(int gnrlId,int custId,String jbName) throws JobException,SQLException
	{ 
		return objJobDetailsManager.getDrawingNoByJobName(gnrlId,custId,jbName); 
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public Vector getRvsnNoByJobNameAndDrawingNo(int gnrlId,int custId,String jbName,String drwgNo) throws JobException,SQLException
	{ 
		return objJobDetailsManager.getRvsnNoByJobNameAndDrawingNo(gnrlId,custId,jbName,drwgNo); 
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public Vector getMatlTypByJobNameDrawingNoAndRvsnNo(int custId,int gnrlId,String jbName,String drwgNo,String rvsnNo) throws JobException,SQLException
	{
		return objJobDetailsManager.getMatlTypByJobNameDrawingNoAndRvsnNo(custId,gnrlId,jbName,drwgNo,rvsnNo); 
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public LinkedHashMap getAllOperationGroupCodes() throws JobException,SQLException
	{ 
		return objJobDetailsManager.getAllOperationGroupCodes(); 
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public JobDetails getJobDetails(int jobId) throws JobException, SQLException
	{ 
		return objJobDetailsManager.getJobDetails(jobId); 
	}

	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public PreCloseDetails getPreCloseDetails(int WOJbStatId) throws WorkOrderException,SQLException
	{ 
		return objWorkOrderDetailsManager.getPreCloseDetails(WOJbStatId); 
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public  HashMap getAllPreCloseDetails(Filter[] filter,String sortBy,boolean ascending,int startIndex,int displayCount) throws WorkOrderException,SQLException
	{ 
		return objWorkOrderDetailsManager.getAllPreCloseDetails(filter,sortBy,ascending,startIndex,displayCount); 
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public HashMap makePreCloseLogValid(Vector woJbStatIds) throws WorkOrderException,SQLException
	{ 
		return objWorkOrderDetailsManager.makePreCloseLogValid(woJbStatIds); 
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public HashMap makePreCloseLogInValid(Vector woJbStatIds) throws WorkOrderException,SQLException
	{ 
		return objWorkOrderDetailsManager.makePreCloseLogInValid(woJbStatIds); 
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public boolean addNewPreCloseLog(PreCloseDetails objPreCloseDetails) throws SQLException, WorkOrderException
	{
		return objWorkOrderDetailsManager.addNewPreCloseLog(objPreCloseDetails);
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public PreCloseDetails getForAddNewPreCloseLog(PreCloseDetails objPreCloseDetails) throws SQLException, WorkOrderException
	{ 
		return objWorkOrderDetailsManager.getForAddNewPreCloseLog(objPreCloseDetails); 
	}

	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public HashMap closeWorkOrder(Vector vec_woJbIds) throws SQLException, WorkOrderException
	{ 
		return objWorkOrderDetailsManager.closeWorkOrder(vec_woJbIds);
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
	public Vector getJobNameByWorkOrder(int WOId) throws SQLException, WorkOrderException
	{ 
	 return objWorkOrderDetailsManager.getJobNameByWorkOrder(WOId) ; 
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**//*
	public boolean isWoModify(int woId) throws WorkOrderException, SQLException{ 
	 return objWorkOrderDetailsManager.isWoModify(woId);
	}*/
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap getAllDummyWorkOrderDetails(Filter[] filters,String sortBy,boolean ascending,int startIndex,int displayCount) throws WorkOrderException,SQLException{ 
 return objWorkOrderDetailsManager.getAllDummyWorkOrderDetails(filters,sortBy,ascending,startIndex,displayCount); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public WorkOrderDetails getDummyWorkOrderDetails(int DWOId) throws WorkOrderException, SQLException{ 
 return objWorkOrderDetailsManager.getDummyWorkOrderDetails(DWOId); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public LinkedHashMap getGeneralNameByCustomer(int custId) throws JobException,SQLException{ 
 return objJobDetailsManager.getGeneralNameByCustomer(custId); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public int getJobId(int gnrlId,int custId,String jbName,String drwgNo,String rvsnNo,String matlTyp) throws JobException, SQLException{ 
 return objJobDetailsManager.getJobId(gnrlId,custId,jbName,drwgNo,rvsnNo,matlTyp); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public int addJobDetailsPartial(JobDetails objJobDetails) throws JobException, SQLException{ 
 return objJobDetailsManager.addJobDetailsPartial(objJobDetails); 
}
}
/***
$Log: SessionWorkOrderDetailsManagerBean.java,v $
Revision 1.24  2006/01/23 04:58:33  kduraisamy
addJobDetailsPartial() method included.

Revision 1.23  2006/01/18 12:20:46  kduraisamy
signature added for wo separation.

Revision 1.22  2005/09/10 13:18:42  kduraisamy
order by clause added.

Revision 1.21  2005/08/13 06:11:27  kduraisamy
isWoModify().commented.

Revision 1.20  2005/03/03 10:44:02  kduraisamy
woJobCloseView() signature modified.

Revision 1.19  2005/02/19 10:10:53  kduraisamy
signature added for getJobId().

Revision 1.18  2005/02/18 10:49:47  kduraisamy
signature changed for getJobByGnrlName().

Revision 1.17  2005/02/18 10:31:05  kduraisamy
UNWANTED SIGNATURE REMOVED.

Revision 1.16  2005/02/18 10:29:54  kduraisamy
SIGNATURE ADDED FOR GETGNRLNAMEBYCUST().

Revision 1.15  2005/02/08 09:54:23  kduraisamy
commented code removed.

Revision 1.14  2005/02/07 09:16:06  kduraisamy
SIGNATURE ADDED FOR GETDMYWODETAILS().

Revision 1.13  2005/02/07 07:22:56  kduraisamy
signature added for getAllDummyOrderDeta().

Revision 1.12  2005/02/07 06:24:57  kduraisamy
modified.

Revision 1.11  2005/02/02 06:52:53  kduraisamy
RETURN TYPE CHANGED TO HASHMAP.

Revision 1.10  2005/01/27 06:04:55  kduraisamy
jbId included instead of jobName.

Revision 1.9  2005/01/17 10:06:25  kduraisamy
signature added for isWoModify().

Revision 1.8  2004/12/17 11:26:01  sduraisamy
SessionBean for getJobNameByWorkOrder() included

Revision 1.7  2004/12/17 10:50:44  sduraisamy
SessionBean created for getWorkOrdeList()

Revision 1.6  2004/12/16 11:35:17  sduraisamy
Session Bean created for Preclose methods

Revision 1.5  2004/12/08 15:03:34  sduraisamy
Session Bean for getPreCloseDetails(),getAllPreCloseDetails(),makePreCloseValid() and makePreCloseInValid() created

Revision 1.4  2004/12/08 09:42:32  sduraisamy
getAllOperationGroupCodes() and getJobDetails() included

Revision 1.3  2004/12/08 05:04:20  sduraisamy
SessionBean included for getDrawingNoByJobName(),getRvsnNoByJobNameAndDrwgNo() and getMatlTypByJobNameDrawingNoAndRvsnNo() included

***/