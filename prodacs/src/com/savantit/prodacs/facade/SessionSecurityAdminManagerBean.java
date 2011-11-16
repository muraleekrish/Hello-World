/*
 * Created on Mar 23, 2005
 *
 * ClassName	:  	SessionSecurityAdminManagerBean.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savantit.prodacs.facade;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.savantit.prodacs.businessimplementation.customer.CustomerDetailsManager;
import com.savantit.prodacs.businessimplementation.customer.CustomerException;
import com.savantit.prodacs.businessimplementation.employee.EmployeeDetails;
import com.savantit.prodacs.businessimplementation.employee.EmployeeDetailsManager;
import com.savantit.prodacs.businessimplementation.employee.EmployeeException;
import com.savantit.prodacs.businessimplementation.securityadmin.ContactDetails;
import com.savantit.prodacs.businessimplementation.securityadmin.SecAdminGroupDetails;
import com.savantit.prodacs.businessimplementation.securityadmin.SecAdminUserDetails;
import com.savantit.prodacs.businessimplementation.securityadmin.SecurityAdminException;
import com.savantit.prodacs.businessimplementation.securityadmin.SecurityAdminManager;
import com.savantit.prodacs.businessimplementation.securityadmin.SystemInfoDetails;
import com.savantit.prodacs.businessimplementation.securityadmin.UserAuthDetails;
import com.savantit.prodacs.infra.beans.Filter;
import com.savantit.prodacs.infra.dbtools.DBConnection;



/**
 * <!-- begin-user-doc --> A generated session bean <!-- end-user-doc --> <!-- lomboz.beginDefinition --> <?xml version="1.0" encoding="UTF-8"?> <lomboz:EJB xmlns:j2ee="http://java.sun.com/xml/ns/j2ee" xmlns:lomboz="http://lomboz.objectlearn.com/xml/lomboz"> <lomboz:session> <lomboz:sessionEjb> <j2ee:display-name>SessionSecurityAdminManager</j2ee:display-name> <j2ee:ejb-name>SessionSecurityAdminManager</j2ee:ejb-name> <j2ee:ejb-class>com.savantit.prodacs.facade.SessionSecurityAdminManagerBean</j2ee:ejb-class> <j2ee:session-type>Stateless</j2ee:session-type> <j2ee:transaction-type>Container</j2ee:transaction-type> </lomboz:sessionEjb> </lomboz:session> </lomboz:EJB> <!-- lomboz.endDefinition --> <!-- begin-xdoclet-definition --> 
 * @ejb.bean  name="SessionSecurityAdminManager"	 jndi-name="SessionSecurityAdminManager" type="Stateless"  transaction-type="Container" -- This is needed for JOnAS. If you are not using JOnAS you can safely remove the tags below.
 * @jonas.bean  ejb-name="SessionSecurityAdminManager"  jndi-name="SessionSecurityAdminManager" -- <!-- end-xdoclet-definition --> 
 * @generated
 */
public abstract class SessionSecurityAdminManagerBean implements javax.ejb.SessionBean
{
    SecurityAdminManager objSecurityAdminManager = new SecurityAdminManager();
    EmployeeDetailsManager objEmployeeDetailsManager = new EmployeeDetailsManager();
    CustomerDetailsManager objCustomerDetailsManager = new CustomerDetailsManager();
    
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean addNewGroup(SecAdminGroupDetails grpObj) throws SQLException, SecurityAdminException{ 
 return objSecurityAdminManager.addNewGroup(grpObj);
}
/**
 * @throws SQLException
 * @throws SecurityAdminException
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean updateGroup(SecAdminGroupDetails grpObj) throws SQLException, SecurityAdminException{ 
 return objSecurityAdminManager.updateGroup(grpObj);
}
/**
 * @throws SQLException
 * @throws SecurityAdminException
 * @ejb.interface-method
 *	view-type="remote" 
**/
public LinkedHashMap getAllGroups() throws SQLException, SecurityAdminException{ 
 return objSecurityAdminManager.getAllGroups(); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public SecAdminGroupDetails getGroupDetails(int groupId) throws SQLException, SecurityAdminException{ 
 return objSecurityAdminManager.getGroupDetails(groupId); 
}
/**
 * @throws SQLException
 * @throws SecurityAdminException
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap getAllGroupsDetails(Filter[] filters, String sortBy, boolean sortOrder, int stIndex, int displayCount) throws SQLException, SecurityAdminException{ 
 return objSecurityAdminManager.getAllGroupsDetails(filters,sortBy,sortOrder,stIndex,displayCount); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap makeGroupsValid(Vector groupIds) throws SecurityAdminException{ 
 return objSecurityAdminManager.makeGroupsValid(groupIds); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap makeGroupsInValid(Vector groupIds) throws SecurityAdminException{ 
 return objSecurityAdminManager.makeGroupsInValid(groupIds); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap deleteGroups(Vector groupIds) throws SecurityAdminException{ 
 return objSecurityAdminManager.deleteGroups(groupIds); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public int addNewContact(ContactDetails obj_Contact_Details, DBConnection objDBConnection) throws SecurityAdminException, SQLException{ 
 return objSecurityAdminManager.addNewContact(obj_Contact_Details,objDBConnection); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public int updateContact(ContactDetails obj_Contact_Details, DBConnection objDBConnection)throws SQLException, SecurityAdminException{ 
 return objSecurityAdminManager.updateContact(obj_Contact_Details,objDBConnection); 
}
/**
 * @throws SQLException
 * @throws SecurityAdminException
 * @throws BadPaddingException
 * @throws IllegalBlockSizeException
 * @throws IllegalStateException
 * @throws NoSuchPaddingException
 * @throws NoSuchAlgorithmException
 * @throws InvalidKeyException
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean addNewUser(SecAdminUserDetails usrObj) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalStateException, IllegalBlockSizeException, BadPaddingException, SQLException, SecurityAdminException{ 
 return objSecurityAdminManager.addNewUser(usrObj);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap getAllUsersDetails(Filter[] filters, String sortBy, boolean sortOrder, int stIndex, int displayCount) throws SQLException, SecurityAdminException{ 
 return objSecurityAdminManager.getAllUsersDetails(filters,sortBy,sortOrder,stIndex,displayCount); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public LinkedHashMap getAllEmployeeTypes() throws SQLException,EmployeeException{ 
 return objEmployeeDetailsManager.getAllEmployeeTypes(); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public LinkedHashMap getAllEmployeesByType(int empTypeId) throws EmployeeException, SQLException{ 
 return objEmployeeDetailsManager.getAllEmployeesByType(empTypeId); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public EmployeeDetails getEmployeeDetails(int empId) throws SQLException,EmployeeException{ 
 return objEmployeeDetailsManager.getEmployeeDetails(empId); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap deleteUsers(Vector userIds) throws SecurityAdminException{ 
 return objSecurityAdminManager.deleteUsers(userIds); 
}
/**
 * @throws SQLException
 * @throws SecurityAdminException
 * @throws BadPaddingException
 * @throws IllegalBlockSizeException
 * @throws IllegalStateException
 * @throws NoSuchPaddingException
 * @throws NoSuchAlgorithmException
 * @throws InvalidKeyException
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean updateUser(SecAdminUserDetails usrObj) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalStateException, IllegalBlockSizeException, BadPaddingException, SQLException, SecurityAdminException{ 
 return objSecurityAdminManager.updateUser(usrObj);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap makeUsersValid(Vector userIds) throws SecurityAdminException{ 
 return objSecurityAdminManager.makeUsersValid(userIds); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap makeUsersInValid(Vector userIds) throws SecurityAdminException{ 
 return objSecurityAdminManager.makeUsersInValid(userIds); 
}
/**
 * @throws SecurityAdminException
 * @throws SQLException
 * @throws IOException
 * @throws BadPaddingException
 * @throws IllegalBlockSizeException
 * @throws IllegalStateException
 * @throws NoSuchPaddingException
 * @throws NoSuchAlgorithmException
 * @throws InvalidKeyException
 * @ejb.interface-method
 *	view-type="remote" 
**/
public SecAdminUserDetails getUserDetails(String userId) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalStateException, IllegalBlockSizeException, BadPaddingException, SecurityAdminException, SQLException, IOException{ 
 return objSecurityAdminManager.getUserDetails(userId); 
}
/**
 * @throws IOException
 * @throws NoSuchAlgorithmException
 * @throws NoSuchPaddingException
 * @throws IllegalStateException
 * @throws IllegalBlockSizeException
 * @throws BadPaddingException
 * @throws SecurityAdminException
 * @throws SQLException
 * @throws IOException
 * @throws InvalidKeyException
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean checkUserAuthentication(String userId, String password) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalStateException, IllegalBlockSizeException, BadPaddingException, SecurityAdminException, SQLException, IOException{ 
 return objSecurityAdminManager.checkUserAuthentication(userId,password);
}
/**
 * @throws SQLException
 * @throws SecurityAdminException
 * @throws IOException
 * @throws BadPaddingException
 * @throws IllegalBlockSizeException
 * @throws IllegalStateException
 * @throws NoSuchPaddingException
 * @throws NoSuchAlgorithmException
 * @throws InvalidKeyException
 * @ejb.interface-method
 *	view-type="remote" 
**/
public UserAuthDetails getUserResources(String userId) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalStateException, IllegalBlockSizeException, BadPaddingException, SQLException, SecurityAdminException, IOException{ 
 return objSecurityAdminManager.getUserResources(userId); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public LinkedHashMap getAllCustomers() throws SQLException, CustomerException{ 
 return objCustomerDetailsManager.getAllCustomers(); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public Vector getAllUsers() throws SecurityAdminException, SQLException{ 
 return objSecurityAdminManager.getAllUsers(); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public Vector getAllUsers(Filter[] filters) throws SecurityAdminException{ 
 return objSecurityAdminManager.getAllUsers(filters); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean checkMasterUserAuthentication(String userId, String password) throws SecurityAdminException, SQLException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalStateException, IllegalBlockSizeException, BadPaddingException, IOException{ 
 return objSecurityAdminManager.checkMasterUserAuthentication(userId,password);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean addSystemInfo(SystemInfoDetails objSystemInfoDetails) throws SecurityAdminException, SQLException{ 
 return objSecurityAdminManager.addSystemInfo(objSystemInfoDetails);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean checkUserAuthentication(String userId, String password,SystemInfoDetails objSystemInfoDetails) throws SecurityAdminException, SQLException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalStateException, IllegalBlockSizeException, BadPaddingException, IOException{ 
 return objSecurityAdminManager.checkUserAuthentication(userId,password,objSystemInfoDetails);
}
}

/*
*$Log: SessionSecurityAdminManagerBean.java,v $
*Revision 1.16  2005/12/07 05:55:54  kduraisamy
*Super user security added.
*
*Revision 1.15  2005/11/26 11:42:16  kduraisamy
*signature added for getAllUsers(Filter filter).
*
*Revision 1.14  2005/11/25 08:30:28  kduraisamy
*getAllUsers() signature added.
*
*Revision 1.13  2005/09/10 13:18:42  kduraisamy
*order by clause added.
*
*Revision 1.12  2005/07/18 11:59:38  kduraisamy
*signature addded for getAllUsers().
*
*Revision 1.11  2005/05/16 15:41:50  kduraisamy
*specific throws addded for mysql.
*
*Revision 1.10  2005/04/11 12:32:52  kduraisamy
*password encryption added.
*
*Revision 1.9  2005/04/07 08:26:20  kduraisamy
*throws added.
*
*Revision 1.8  2005/03/31 11:06:48  kduraisamy
*signature added for getUserResources().
*
*Revision 1.7  2005/03/31 08:56:38  kduraisamy
*signature added for userAuthentication.
*
*Revision 1.6  2005/03/30 06:42:12  kduraisamy
*getUserDetails() signature added.
*
*Revision 1.5  2005/03/30 05:16:39  kduraisamy
*signature added for make uesr valid and invalid.
*
*Revision 1.4  2005/03/29 08:53:35  kduraisamy
*signature added for Delete Users().
*
*Revision 1.3  2005/03/29 06:40:32  kduraisamy
*employee methods signature added.
*
*Revision 1.2  2005/03/28 07:39:25  kduraisamy
*addNewUser() signature added.
*
*Revision 1.1  2005/03/23 06:35:48  kduraisamy
*initial commit.
*
*
*/