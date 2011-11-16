/*
 * Created on Nov 3, 2004
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

import com.savantit.prodacs.businessimplementation.customer.CustomerException;
import com.savantit.prodacs.businessimplementation.machine.MachineDetails;
import com.savantit.prodacs.businessimplementation.machine.MachineDetailsManager;
import com.savantit.prodacs.businessimplementation.machine.MachineException;
import com.savantit.prodacs.businessimplementation.machine.MachineTypeDetails;
import com.savantit.prodacs.infra.beans.Filter;
/**
 * @ejb.bean name="SessionMachineDetailsManager"
 *	jndi-name="SessionMachineDetailsManagerBean"
 *	type="Stateless" 
 * 
 *--
 * This is needed for JOnAS.
 * If you are not using JOnAS you can safely remove the tags below.
 * @jonas.bean ejb-name="SessionMachineDetailsManager"
 *	jndi-name="SessionMachineDetailsManagerBean"
 * 
 *--
 **/
public abstract class SessionMachineDetailsManagerBean implements SessionBean
{
	MachineDetailsManager objMachDetMgr = new MachineDetailsManager();
	
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public int addMachineTypeDetails(MachineTypeDetails objMachTypDet) throws SQLException, MachineException{ 
 return objMachDetMgr.addMachineTypeDetails(objMachTypDet) ;
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public LinkedHashMap getAllMachineTypes() throws SQLException, MachineException{ 
 return objMachDetMgr.getAllMachineTypes(); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean addMachineDetails(MachineDetails objMachineDetails) throws SQLException, MachineException{ 
 return objMachDetMgr.addMachineDetails(objMachineDetails);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean updateMachineDetails(MachineDetails objMachineDetails) throws SQLException, MachineException{ 
 return objMachDetMgr.updateMachineDetails(objMachineDetails);
 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public MachineDetails getMachineDetails(String machineCode) throws SQLException, MachineException{ 
 return objMachDetMgr.getMachineDetails(machineCode);
}

/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public LinkedHashMap getAllMachines() throws MachineException, SQLException{ 
 return objMachDetMgr.getAllMachines(); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap makeMachinesValid(Vector machineCodes) throws SQLException, MachineException{ 
 return objMachDetMgr.makeMachinesValid(machineCodes); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap makeMachinesInValid(Vector machineCodes) throws SQLException, MachineException{ 
 return objMachDetMgr.makeMachinesInValid(machineCodes); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap getAllMachineDetails(Filter[] filters,String sortBy,boolean ascending,int startIndex,int displayCount) throws SQLException, MachineException{ 
 return objMachDetMgr.getAllMachineDetails(filters,sortBy,ascending,startIndex,displayCount); 
}

/**
 * @throws SQLException
 * @throws MachineException
 * @throws CustomerException
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap getAllMachineTypeDetails(Filter[] filters,String sortBy,boolean ascending,int startIndex,int displayCount) throws SQLException, CustomerException, MachineException{ 
 return objMachDetMgr.getAllMachineTypeDetails(filters,sortBy,ascending,startIndex,displayCount); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public MachineTypeDetails getMachineTypeDetails(int machineTypId) throws SQLException, MachineException{ 
 return objMachDetMgr.getMachineTypeDetails(machineTypId); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean updateMachineTypeDetails(MachineTypeDetails machineTypDetObj) throws SQLException, MachineException{ 
 return objMachDetMgr.updateMachineTypeDetails(machineTypDetObj);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap makeMachineTypInValid(Vector ids) throws MachineException, SQLException{ 
 return objMachDetMgr.makeMachineTypInValid(ids); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap makeMachineTypValid(Vector ids) throws MachineException, SQLException{ 
 return objMachDetMgr.makeMachineTypValid(ids); 
}
}

/***
$Log: SessionMachineDetailsManagerBean.java,v $
Revision 1.9  2005/09/10 13:18:42  kduraisamy
order by clause added.

Revision 1.8  2005/03/08 05:50:54  kduraisamy
signature added for make valid and invalid.

Revision 1.7  2005/03/08 05:35:53  kduraisamy
signature added for updateMachineTypeDetails().

Revision 1.6  2005/03/08 05:18:02  kduraisamy
signature added for getMachineTypeDetails().

Revision 1.5  2005/03/06 09:08:38  kduraisamy
Machine type filter signature added.

Revision 1.4  2005/02/28 04:56:06  kduraisamy
LinkedHashMap is added for HashMap.

Revision 1.3  2005/02/17 06:05:39  kduraisamy
signature modified for addNewMachineTypeDetails().

Revision 1.2  2004/11/06 07:41:44  sduraisamy
Log inclusion in Employee and Machine Session Bean

***/