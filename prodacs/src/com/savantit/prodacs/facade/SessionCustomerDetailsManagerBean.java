/*
 * Created on Nov 15, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.facade;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;

import javax.ejb.SessionBean;

import com.savantit.prodacs.businessimplementation.customer.CustomerDetails;
import com.savantit.prodacs.businessimplementation.customer.CustomerDetailsManager;
import com.savantit.prodacs.businessimplementation.customer.CustomerException;
import com.savantit.prodacs.businessimplementation.customer.CustomerTypDetails;
import com.savantit.prodacs.infra.beans.Filter;
/**
 * @ejb.bean name="SessionCustomerDetailsManager"
 *	jndi-name="SessionCustomerDetailsManagerBean"
 *	type="Stateless" 
 * 
 *--
 * This is needed for JOnAS.
 * If you are not using JOnAS you can safely remove the tags below.
 * @jonas.bean ejb-name="SessionCustomerDetailsManager"
 *	jndi-name="SessionCustomerDetailsManagerBean"
 * 
 *--
 **/
public abstract class SessionCustomerDetailsManagerBean implements SessionBean 
{
	CustomerDetailsManager objCustomerDetailsManager = new CustomerDetailsManager();
	
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
   public boolean addCustomerDetails(CustomerDetails objCustomerDetails)throws RemoteException, SQLException, CustomerException
   {
   	return objCustomerDetailsManager.addCustomerDetails(objCustomerDetails);
   }
   /**
    * @ejb.interface-method
    *	view-type="remote" 
   **/
   public boolean updateCustomerDetails(CustomerDetails CustomerDetailsObj) throws RemoteException,SQLException, CustomerException
   {
   		 		return objCustomerDetailsManager.updateCustomerDetails(CustomerDetailsObj);
   }
   /**
    * @ejb.interface-method
    *	view-type="remote" 
   **/
   public CustomerDetails getCustomerDetails(int customerId) throws SQLException, CustomerException
   {
   				return objCustomerDetailsManager.getCustomerDetails(customerId);
   }
   /**
    * @ejb.interface-method
    *	view-type="remote" 
   **/
   public LinkedHashMap getAllCustomers() throws SQLException, CustomerException
   {
   				return objCustomerDetailsManager.getAllCustomers();
   }
 
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public LinkedHashMap getCustomerTypes() throws CustomerException,SQLException{ 
 return objCustomerDetailsManager.getCustomerTypes(); 
}

/**
 * @ejb.interface-method
 *	view-type="remote" 
**/

public HashMap getCustomers(Filter filters[],String sortBy,boolean ascending, int startIndex, int displayCount) throws SQLException, CustomerException
{
	return objCustomerDetailsManager.getCustomers(filters,sortBy,ascending,startIndex,displayCount);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public int addCustomerTypDetails(CustomerTypDetails objCustomerTypDetails) throws CustomerException, SQLException
{
	return objCustomerDetailsManager.addCustomerTypeDetails(objCustomerTypDetails);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap makeCustomerValid(Vector ids) throws CustomerException, SQLException{ 
 return objCustomerDetailsManager.makeCustomerValid(ids); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap makeCustomerInValid(Vector ids) throws CustomerException, SQLException{ 
 return objCustomerDetailsManager.makeCustomerInValid(ids); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public LinkedHashMap getCustomerNameByType(int custTypId) throws CustomerException, SQLException{ 
 return objCustomerDetailsManager.getCustomerNameByType(custTypId); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap getAllCustomerTypeDetails(Filter[] filters,String sortBy,boolean ascending,int startIndex,int displayCount) throws SQLException, CustomerException{ 
 return objCustomerDetailsManager.getAllCustomerTypeDetails(filters,sortBy,ascending,startIndex,displayCount); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public CustomerTypDetails getCustomerTypeDetails(int custTypId) throws SQLException, CustomerException{ 
 return objCustomerDetailsManager.getCustomerTypeDetails(custTypId); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean updateCustomerTypeDetails(CustomerTypDetails custTypDetObj) throws SQLException, CustomerException{ 
 return objCustomerDetailsManager.updateCustomerTypeDetails(custTypDetObj);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap makeCustomerTypInValid(Vector ids) throws CustomerException, SQLException{ 
 return objCustomerDetailsManager.makeCustomerTypInValid(ids); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap makeCustomerTypValid(Vector ids) throws CustomerException, SQLException{ 
 return objCustomerDetailsManager.makeCustomerTypValid(ids); 
}

}
/***
$Log: SessionCustomerDetailsManagerBean.java,v $
Revision 1.10  2005/09/10 13:18:42  kduraisamy
order by clause added.

Revision 1.9  2005/03/06 08:40:41  kduraisamy
make valid,invalid for custtyp added.

Revision 1.8  2005/03/06 07:39:05  kduraisamy
signature added for update customer typ details().

Revision 1.7  2005/03/05 11:00:39  kduraisamy
signature added for getCustomerTypDetails().

Revision 1.6  2005/03/05 08:38:26  kduraisamy
signature added for filter custTypeDetails().

Revision 1.5  2005/02/18 05:59:28  kduraisamy
signature added for getCustomerByType(0.

Revision 1.4  2005/02/16 07:52:43  kduraisamy
signature modified for addCustomerTypeDetails().

Revision 1.3  2005/01/27 14:53:40  kduraisamy
exceptions included.

Revision 1.2  2004/11/16 10:13:51  kduraisamy
signatures added for make valid and make invalid

Revision 1.1  2004/11/15 06:34:41  kduraisamy
Name is changed

Revision 1.4  2004/11/09 07:42:30  kduraisamy
Signature added for addCustomerTypeDetails method

Revision 1.3  2004/11/09 04:51:33  kduraisamy
Log added

***/