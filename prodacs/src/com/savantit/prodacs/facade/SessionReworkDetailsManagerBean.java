/*
 * Created on Nov 20, 2004
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

import com.savantit.prodacs.businessimplementation.job.ReworkDetails;
import com.savantit.prodacs.businessimplementation.job.ReworkDetailsManager;
import com.savantit.prodacs.businessimplementation.job.ReworkException;
import com.savantit.prodacs.infra.beans.Filter;
/**
 * @ejb.bean name="SessionReworkDetailsManager"
 *	jndi-name="SessionReworkDetailsManagerBean"
 *	type="Stateless" 
 * 
 *--
 * This is needed for JOnAS.
 * If you are not using JOnAS you can safely remove the tags below.
 * @jonas.bean ejb-name="SessionReworkDetailsManager"
 *	jndi-name="SessionReworkDetailsManagerBean"
 * 
 *--
 **/
public abstract class SessionReworkDetailsManagerBean implements SessionBean
{
	ReworkDetailsManager objRwkDetMgr = new ReworkDetailsManager();
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean addNewReworkCategory(ReworkDetails objReworkDetails) throws ReworkException,SQLException{ 
 return objRwkDetMgr.addNewReworkCategory(objReworkDetails);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public LinkedHashMap getAllReworkCategories() throws ReworkException, SQLException{ 
 return objRwkDetMgr.getAllReworkCategories(); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean addNewReworkReason(ReworkDetails objReworkDetails) throws ReworkException,SQLException{ 
 return objRwkDetMgr.addNewReworkReason(objReworkDetails);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean updateReworkReason(ReworkDetails objReworkDetails) throws ReworkException,SQLException{ 
 return objRwkDetMgr.updateReworkReason(objReworkDetails);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public ReworkDetails getReworkDetails(int rwkId) throws ReworkException,SQLException{ 
 return objRwkDetMgr.getReworkDetails(rwkId); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap makeReworkReasonValid(Vector rwkRsnIds) throws ReworkException,SQLException{ 
 return objRwkDetMgr.makeReworkReasonValid(rwkRsnIds); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap makeReworkReasonInValid(Vector rwkRsnIds) throws ReworkException,SQLException{ 
 return objRwkDetMgr.makeReworkReasonInValid(rwkRsnIds); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap getAllReworkDetails(Filter[] filters,String sortBy,boolean ascending,int startIndex,int displayCount) throws ReworkException,SQLException{ 
 return objRwkDetMgr.getAllReworkDetails(filters,sortBy,ascending,startIndex,displayCount); 
}
}
/***
$Log: SessionReworkDetailsManagerBean.java,v $
Revision 1.2  2005/09/10 13:18:42  kduraisamy
order by clause added.

Revision 1.1  2004/11/20 10:35:18  sduraisamy
Initial commit of SessionReworkDetailsManagerBean

***/