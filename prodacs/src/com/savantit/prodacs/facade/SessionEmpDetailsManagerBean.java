/*
 * Created on Oct 27, 2004
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

import com.savantit.prodacs.businessimplementation.employee.EmployeeDetails;
import com.savantit.prodacs.businessimplementation.employee.EmployeeDetailsManager;
import com.savantit.prodacs.businessimplementation.employee.EmployeeException;
import com.savantit.prodacs.businessimplementation.employee.EmployeeStatusDetails;
import com.savantit.prodacs.businessimplementation.employee.EmployeeTypeDetails;
import com.savantit.prodacs.infra.beans.Filter;
/**
 * @ejb.bean name="SessionEmpDetailsManager"
 *	jndi-name="SessionEmpDetailsManagerBean"
 *	type="Stateless" 
 * 
 *--
 * This is needed for JOnAS.
 * If you are not using JOnAS you can safely remove the tags below.
 * @jonas.bean ejb-name="SessionEmpDetailsManager"
 *	jndi-name="SessionEmpDetailsManagerBean"
 * 
 *--
 **/
public abstract class SessionEmpDetailsManagerBean implements SessionBean
{
	EmployeeDetailsManager empDetMgrObj = new EmployeeDetailsManager();
	
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean addEmployee(EmployeeDetails objEmpDet) throws SQLException, EmployeeException
{ 
 return empDetMgrObj.addEmployeeDetails(objEmpDet);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean updateEmployee(EmployeeDetails objEmpDet) throws SQLException, EmployeeException
{ 
 return empDetMgrObj.updateEmployeeDetails(objEmpDet);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
/*public LinkedHashMap delEmployee(Vector empIds) throws Exception
{ 
 return empDetMgrObj.deleteEmployee(empIds); 
}*/

/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public LinkedHashMap getAllEmployees() throws Exception
{ 
	 return empDetMgrObj.getAllEmployees(); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public EmployeeDetails getEmployeeDetails(int empId) throws SQLException, EmployeeException { 
 return empDetMgrObj.getEmployeeDetails(empId); 
}

/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap getAllEmployeeDetails(Filter[] filters,String sortBy,boolean ascending,int startIndex,int displayCount) throws SQLException, EmployeeException{ 
 return empDetMgrObj.getAllEmployeeDetails(filters,sortBy,ascending,startIndex,displayCount); 
}

/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap makeEmployeesValid(Vector empIds) throws SQLException,EmployeeException{ 
 return empDetMgrObj.makeEmployeesValid(empIds); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap makeEmployeesInValid(Vector empIds) throws SQLException, EmployeeException{ 
 return empDetMgrObj.makeEmployeesInValid(empIds); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean addEmployeeTypeDetails(EmployeeTypeDetails empTypDetObj) throws SQLException,EmployeeException{ 
 return empDetMgrObj.addEmployeeTypeDetails(empTypDetObj);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean updateEmployeeTypeDetails(EmployeeTypeDetails empTypDetObj) throws SQLException, EmployeeException{ 
 return empDetMgrObj.updateEmployeeTypeDetails(empTypDetObj);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public LinkedHashMap getAllEmployeeTypes() throws SQLException, EmployeeException{ 
 return empDetMgrObj.getAllEmployeeTypes(); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public EmployeeTypeDetails getEmployeeTypeDetails(int empTypId) throws SQLException, EmployeeException{ 
 return empDetMgrObj.getEmployeeTypeDetails(empTypId); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap makeEmployeeTypesValid(Vector empTypIds) throws SQLException, EmployeeException{ 
 return empDetMgrObj.makeEmployeeTypesValid(empTypIds); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap makeEmployeeTypesInValid(Vector empTypIds) throws SQLException, EmployeeException{ 
 return empDetMgrObj.makeEmployeeTypesInValid(empTypIds); 
}

/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap getAllEmployeeTypeDetails(Filter[] filters,String sortBy,boolean ascending,int startIndex,int displayCount) throws SQLException, EmployeeException{ 
 return empDetMgrObj.getAllEmployeeTypeDetails(filters,sortBy,ascending,startIndex,displayCount); 
}

/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public int addNewEmployeeStatus(EmployeeStatusDetails objEmpStat) throws SQLException, EmployeeException
{
 return empDetMgrObj.addNewEmployeeStatus(objEmpStat);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public LinkedHashMap getAllEmployeeStatus() throws SQLException,EmployeeException{ 
 return empDetMgrObj.getAllEmployeeStatus(); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public LinkedHashMap getAllEmployeesByType(int empTypeId) throws EmployeeException, SQLException{ 
 return empDetMgrObj.getAllEmployeesByType(empTypeId); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
    public EmployeeTypeDetails[] getEmployeeTypeWithTeam() throws EmployeeException, SQLException{ 
 return empDetMgrObj.getEmployeeTypeWithTeam(); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean updateEmployeeTypeDetailsWithTeam(EmployeeTypeDetails[] objEmployeeTypeDetailsList) throws SQLException, EmployeeException{ 
 return empDetMgrObj.updateEmployeeTypeDetailsWithTeam(objEmployeeTypeDetailsList);
}
}

/***
$Log: SessionEmpDetailsManagerBean.java,v $
Revision 1.9  2005/09/16 11:40:53  kduraisamy
ejbCreate() removed from session bean.

Revision 1.8  2005/09/10 13:18:42  kduraisamy
order by clause added.

Revision 1.7  2005/05/26 07:13:05  kduraisamy
team add and update changed.

Revision 1.6  2005/02/16 12:26:13  kduraisamy
signature added for getAllEmployeesByType().

Revision 1.5  2005/02/16 10:14:11  kduraisamy
signature modified for addNewEmpStat().

Revision 1.4  2004/11/17 14:45:14  sduraisamy
add and get All Employee Status methods included

Revision 1.3  2004/11/10 09:15:26  sduraisamy
addEmployeeTypeDetails() name modified in SessionEmpDetailsManagerBean

Revision 1.2  2004/11/06 07:41:44  sduraisamy
Log inclusion in Employee and Machine Session Bean

***/