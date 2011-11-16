/*
 * Created on Nov 15, 2004
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
import com.savantit.prodacs.businessimplementation.job.OperationGroupDetails;
import com.savantit.prodacs.businessimplementation.machine.MachineDetailsManager;
import com.savantit.prodacs.businessimplementation.machine.MachineException;
import com.savantit.prodacs.infra.beans.Filter;
/**
 * @ejb.bean name="SessionJobDetailsManager"
 *	jndi-name="SessionJobDetailsManagerBean"
 *	type="Stateless" 
 * 
 *--
 * This is needed for JOnAS.
 * If you are not using JOnAS you can safely remove the tags below.
 * @jonas.bean ejb-name="SessionJobDetailsManager"
 *	jndi-name="SessionJobDetailsManagerBean"
 * 
 *--
 **/
public abstract class SessionJobDetailsManagerBean implements SessionBean 
{
	JobDetailsManager objJobDetailsManager=new JobDetailsManager();
	CustomerDetailsManager objCustomerDetailsMgr = new CustomerDetailsManager();
	MachineDetailsManager objMachineDetailsManager = new MachineDetailsManager();
	
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
	public boolean updateJobDetails(JobDetails objJobDetails) throws JobException, SQLException
	{ 
		return objJobDetailsManager.updateJobDetails(objJobDetails);
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public HashMap deleteJobDetails(Vector vec_JbIds) throws JobException, SQLException
	{ 
		return objJobDetailsManager.deleteJobDetails(vec_JbIds); 
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
	public HashMap getAllJobDetails(Filter[] filters,String sortBy,boolean ascending,int startIndex,int displayCount) throws SQLException, JobException
	{ 
		return objJobDetailsManager.getAllJobDetails(filters,sortBy,ascending,startIndex,displayCount); 	
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public HashMap makeJobValid(Vector jbIds) throws JobException,SQLException
	{ 
		return objJobDetailsManager.makeJobValid(jbIds); 
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public HashMap makeJobInValid(Vector jbIds) throws JobException,SQLException
	{ 
		return objJobDetailsManager.makeJobInValid(jbIds); 
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public boolean addOperationGroupDetails(OperationGroupDetails objOperationGroupDetails) throws JobException, SQLException
	{ 
		return objJobDetailsManager.addOperationGroupDetails(objOperationGroupDetails);
		
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	 **/
	public boolean updateOperationGroupDetails(OperationGroupDetails objOperationGroupDetails) throws JobException, SQLException
	{ 
		return objJobDetailsManager.updateOperationGroupDetails(objOperationGroupDetails);
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public HashMap deleteOperationGroupDetails(Vector opnGpIds) throws JobException, SQLException
	{ 
		return objJobDetailsManager.deleteOperationGroupDetails(opnGpIds); 
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	 **/
	public OperationGroupDetails getOperationGroupDetails(int opnGpId) throws JobException, SQLException
	{
		return objJobDetailsManager.getOperationGroupDetails(opnGpId); 
	}	
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public HashMap getAllOperationGroupDetails(Filter[] filters,String sortBy,boolean ascending,int startIndex,int displayCount) throws JobException, SQLException
	{ 
		return objJobDetailsManager.getAllOperationGroupDetails(filters,sortBy,ascending,startIndex,displayCount); 
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public HashMap makeOperationGroupValid(Vector opnGpIds) throws JobException, SQLException
	{ 
		return objJobDetailsManager.makeOperationGroupValid(opnGpIds); 
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public HashMap makeOperationGroupInValid(Vector opnGpIds) throws JobException, SQLException
	{ 
		return objJobDetailsManager.makeOperationGroupInValid(opnGpIds); 
	}
	
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public LinkedHashMap getAllCustomers() throws CustomerException,SQLException
	{ 
	 return objCustomerDetailsMgr.getAllCustomers(); 
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public LinkedHashMap getCustomerTypes() throws CustomerException, SQLException
	{ 
	 return objCustomerDetailsMgr.getCustomerTypes(); 
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
	public LinkedHashMap getAllJobs() throws JobException,SQLException
	{ 
	 return objJobDetailsManager.getAllJobs(); 
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
	public LinkedHashMap getAllMachines() throws MachineException, SQLException
	{ 
		return objMachineDetailsManager.getAllMachines(); 
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public LinkedHashMap getCustomerNameByType(int custTypId) throws CustomerException, SQLException
	{ 
		return objCustomerDetailsMgr.getCustomerNameByType(custTypId); 
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public LinkedHashMap getGeneralNameByCustomer(int custId) throws JobException,SQLException
	{ 
		return objJobDetailsManager.getGeneralNameByCustomer(custId); 
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public Vector getJobDetailsByGeneralNameWithCustomer(int custId,int gnrlId) throws JobException,SQLException{ 
	 return objJobDetailsManager.getJobDetailsByGeneralNameWithCustomer(custId,gnrlId); 
	}
}


/***
$Log: SessionJobDetailsManagerBean.java,v $
Revision 1.17  2005/09/10 13:18:42  kduraisamy
order by clause added.

Revision 1.16  2005/02/28 04:56:07  kduraisamy
LinkedHashMap is added for HashMap.

Revision 1.15  2005/02/16 11:46:28  kduraisamy
signature modified for addNewJbGnrlName().

Revision 1.14  2005/01/27 06:04:48  kduraisamy
jbId included instead of jobName.

Revision 1.13  2004/11/27 10:38:13  sduraisamy
Method signature for getJobDetailsByGeneralNameWithCustomer() added

Revision 1.12  2004/11/26 07:23:47  sduraisamy
wrong calling of method corrected

Revision 1.11  2004/11/25 07:49:18  sduraisamy
Method signature for getJobDetailsByJobName() added

Revision 1.10  2004/11/24 08:12:28  sduraisamy
Method signature for getJobByGeneralName() added

Revision 1.9  2004/11/24 07:52:05  sduraisamy
Method Signature for getGeneralNameByCustomer() added

Revision 1.8  2004/11/24 07:20:09  sduraisamy
Signature for getCustomerByType() added

Revision 1.7  2004/11/23 13:00:51  kduraisamy
Method signature for getAllMachines() added

Revision 1.6  2004/11/23 10:22:15  kduraisamy
Method signatures included for getAllJobs(),getAllJobGeneralName(),getAllOperatioGroupCodes()

Revision 1.5  2004/11/22 07:02:32  kduraisamy
Method Signatures for Delete,Make Valid and Make InValid Job included

Revision 1.4  2004/11/21 11:39:18  sduraisamy
Method Signatures created for Operation Group Delete,Make Valid and Make InValid methods

***/
                                                                                                                  