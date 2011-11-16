/*
 * Created on Jan 12, 2005
 */
package com.savantit.prodacs.facade;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;

import javax.ejb.SessionBean;

import com.savantit.prodacs.businessimplementation.job.ReworkDetailsManager;
import com.savantit.prodacs.businessimplementation.job.ReworkException;
import com.savantit.prodacs.businessimplementation.production.ProductionException;
import com.savantit.prodacs.businessimplementation.reworklog.ReworkLogDetails;
import com.savantit.prodacs.businessimplementation.reworklog.ReworkLogDetailsManager;
import com.savantit.prodacs.businessimplementation.workorder.WorkOrderDetailsManager;
import com.savantit.prodacs.businessimplementation.workorder.WorkOrderException;
import com.savantit.prodacs.infra.beans.Filter;
/**
 * @ejb.bean name="SessionReworkLogDetailsManager"
 *	jndi-name="SessionReworkLogDetailsManagerBean"
 *	type="Stateless" 
 * 
 *--
 * This is needed for JOnAS.
 * If you are not using JOnAS you can safely remove the tags below.
 * @jonas.bean ejb-name="SessionReworkLogDetailsManager"
 *	jndi-name="SessionReworkLogDetailsManagerBean"
 * 
 *--
 **/
public abstract class SessionReworkLogDetailsManagerBean implements SessionBean
{
	ReworkLogDetailsManager objReworkLogDetailsManager = new ReworkLogDetailsManager();
	ReworkDetailsManager objReworkDetailsManager = new ReworkDetailsManager();
	WorkOrderDetailsManager objWorkOrderDetailsManager = new WorkOrderDetailsManager();
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	 **/
	public HashMap getAllReworkLogDetails(Filter[] filters,String sortBy,boolean ascending,int startIndex,int displayCount) throws ProductionException, SQLException
	{ 
		return objReworkLogDetailsManager.getAllReworkLogDetails(filters,sortBy,ascending,startIndex,displayCount); 
	}
/**
 * @throws SQLException
 * @throws ProductionException
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean addReworkLogDetails(ReworkLogDetails objReworkLogDetails) throws ProductionException, SQLException{ 
 return objReworkLogDetailsManager.addReworkLogDetails(objReworkLogDetails);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public Vector getRwkJobOperationDetails(int woId,int jbId) throws ProductionException,SQLException{ 
 return objReworkLogDetailsManager.getRwkJobOperationDetails(woId,jbId); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public Vector getJobNameByWorkOrder(int WOId) throws SQLException, WorkOrderException{ 
 return objWorkOrderDetailsManager.getJobNameByWorkOrder(WOId); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public LinkedHashMap getAllReworkReasonsByCategory(int categryId) throws ReworkException, SQLException{ 
 return objReworkDetailsManager.getAllReworkReasonsByCategory(categryId); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public LinkedHashMap getAllReworkCategories() throws ReworkException, SQLException{ 
 return objReworkDetailsManager.getAllReworkCategories(); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public LinkedHashMap getWorkOrderList() throws SQLException, WorkOrderException{ 
 return objWorkOrderDetailsManager.getWorkOrderList(); 
}
/**
 * @throws ProductionException
 * @throws SQLException
 * @ejb.interface-method
 *	view-type="remote" 
**/
public ReworkLogDetails getReworkLogDetails(int rwkLogId) throws ProductionException, SQLException{ 
 return objReworkLogDetailsManager.getReworkLogDetails(rwkLogId); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap makeReworkLogValid(Vector reworkLogIds) throws SQLException,ProductionException{ 
 return objReworkLogDetailsManager.makeReworkLogValid(reworkLogIds); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap makeReworkLogInValid(Vector reworkLogIds) throws SQLException,ProductionException{ 
 return objReworkLogDetailsManager.makeReworkLogInValid(reworkLogIds); 
}
}
/**
 $Log: SessionReworkLogDetailsManagerBean.java,v $
 Revision 1.8  2005/09/10 13:18:42  kduraisamy
 order by clause added.

 Revision 1.7  2005/04/07 08:25:31  kduraisamy
 throws added.

 Revision 1.6  2005/02/11 05:15:47  kduraisamy
 signature added for make valid ,invalid.

 Revision 1.5  2005/02/04 11:46:03  kduraisamy
 signature added for getReworkLogDetails().

 Revision 1.4  2005/02/01 09:06:09  kduraisamy
 signature added for getWoList(),getAllReworkCategories().

 Revision 1.3  2005/02/01 08:51:09  kduraisamy
 signature for getAllReworkReasonbycategory() added.

 Revision 1.2  2005/02/01 07:44:24  kduraisamy
 signature added for getJbNamebyWorkOrder().

 Revision 1.1  2005/01/12 11:07:03  sduraisamy
 SessionBean for ReworkLogDetails Committed

 */

