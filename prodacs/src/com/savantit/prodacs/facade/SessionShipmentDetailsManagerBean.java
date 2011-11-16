/*
 * Created on Jul 22, 2005
 *
 * ClassName	:  	SessionShipmentDetailsManagerBean.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savantit.prodacs.facade;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;


import com.savantit.prodacs.businessimplementation.customer.CustomerDetailsManager;
import com.savantit.prodacs.businessimplementation.customer.CustomerException;
import com.savantit.prodacs.businessimplementation.production.ProductionDetailsManager;
import com.savantit.prodacs.businessimplementation.production.ProductionException;
import com.savantit.prodacs.businessimplementation.production.ShipmentDetails;
import com.savantit.prodacs.businessimplementation.production.ShipmentDetailsManager;
import com.savantit.prodacs.businessimplementation.workcalendar.WorkCalendarDetailsManager;
import com.savantit.prodacs.businessimplementation.workcalendar.WorkCalendarException;
import com.savantit.prodacs.businessimplementation.workorder.WorkOrderDetailsManager;
import com.savantit.prodacs.businessimplementation.workorder.WorkOrderException;
import com.savantit.prodacs.infra.beans.Filter;

/**
 * <!-- begin-user-doc --> A generated session bean <!-- end-user-doc --> <!-- lomboz.beginDefinition --> <?xml version="1.0" encoding="UTF-8"?> <lomboz:EJB xmlns:j2ee="http://java.sun.com/xml/ns/j2ee" xmlns:lomboz="http://lomboz.objectlearn.com/xml/lomboz"> <lomboz:session> <lomboz:sessionEjb> <j2ee:display-name>SessionShipmentDetailsManager</j2ee:display-name> <j2ee:ejb-name>SessionShipmentDetailsManager</j2ee:ejb-name> <j2ee:ejb-class>com.savantit.prodacs.facade.SessionShipmentDetailsManagerBean</j2ee:ejb-class> <j2ee:session-type>Stateless</j2ee:session-type> <j2ee:transaction-type>Container</j2ee:transaction-type> </lomboz:sessionEjb> </lomboz:session> </lomboz:EJB> <!-- lomboz.endDefinition --> <!-- begin-xdoclet-definition --> 
 * @ejb.bean  name="SessionShipmentDetailsManager"	 jndi-name="SessionShipmentDetailsManager" type="Stateless"  transaction-type="Container" -- This is needed for JOnAS. If you are not using JOnAS you can safely remove the tags below.
 * @jonas.bean  ejb-name="SessionShipmentDetailsManager"  jndi-name="SessionShipmentDetailsManager" -- <!-- end-xdoclet-definition --> 
 * @generated
 */
public abstract class SessionShipmentDetailsManagerBean implements javax.ejb.SessionBean
{
    ProductionDetailsManager  objProductionDetailsManager = new ProductionDetailsManager();
    ShipmentDetailsManager objShipmentDetailsManager = new ShipmentDetailsManager();
    WorkCalendarDetailsManager objWorkCalendarDetailsManager = new WorkCalendarDetailsManager();
    WorkOrderDetailsManager objWorkOrderDetailsManager = new WorkOrderDetailsManager();
    CustomerDetailsManager objCustomerDetailsManager = new CustomerDetailsManager();
    
    
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap getAllShipmentDetails(Filter filters[],String sortBy,boolean ascending,int startIndex,int displayCount) throws ProductionException,SQLException
{ 
 return objShipmentDetailsManager.getAllShipmentDetails(filters,sortBy,ascending,startIndex,displayCount); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean addNewShipmentDetails(ShipmentDetails[] objShipmentDetailsList) throws ProductionException, SQLException{ 
 return objShipmentDetailsManager.addNewShipmentDetails(objShipmentDetailsList);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public LinkedHashMap getWorkOrderList() throws SQLException, WorkOrderException{ 
 return objWorkOrderDetailsManager.getWorkOrderList(); 
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
public LinkedHashMap getShiftDefnNameList() throws WorkCalendarException,SQLException
{ 
 return objWorkCalendarDetailsManager.getShiftDefnNameList(); 
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
public Vector getShipmentOperationDetails(int woJbId) throws ProductionException,SQLException{ 
 return objShipmentDetailsManager.getShipmentOperationDetails(woJbId); 
}
/**
 * @throws SQLException
 * @throws ProductionException
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap getAllJobStatusDetails(String userName,Date fromDate,Date toDate,Filter filters[],String sortBy,boolean ascending,int startIndex,int displayCount) throws ProductionException, SQLException{ 
 return objShipmentDetailsManager.getAllJobStatusDetails(userName,fromDate,toDate,filters,sortBy,ascending,startIndex,displayCount); 
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
public boolean isModifyForDespatch(int shipmentId) throws ProductionException, SQLException{ 
 return objShipmentDetailsManager.isModifyForDespatch(shipmentId);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public ShipmentDetails getShipmentDetails(int shipmentId) throws ProductionException,SQLException{ 
 return objShipmentDetailsManager.getShipmentDetails(shipmentId); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public Vector getShipmentOperationDetailsForUpdate(int woJbId,int shipmentId) throws ProductionException,SQLException{ 
 return objShipmentDetailsManager.getShipmentOperationDetailsForUpdate(woJbId,shipmentId); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean updateShipmentDetails(ShipmentDetails objShipmentDetails) throws ProductionException, SQLException{ 
 return objShipmentDetailsManager.updateShipmentDetails(objShipmentDetails);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap makeShipmentValid(Vector shipmentIds) throws ProductionException, SQLException{ 
 return objShipmentDetailsManager.makeShipmentValid(shipmentIds); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap makeShipmentInValid(Vector shipmentIds) throws ProductionException, SQLException{ 
 return objShipmentDetailsManager.makeShipmentInValid(shipmentIds); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap getAllJobStatusDetails(int custId,Date fromDate,Date toDate,Filter filters[],String sortBy,boolean ascending,int startIndex,int displayCount) throws ProductionException, SQLException{ 
 return objShipmentDetailsManager.getAllJobStatusDetails(custId,fromDate,toDate,filters,sortBy,ascending,startIndex,displayCount); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public LinkedHashMap getCustomerTypes() throws CustomerException, SQLException{ 
 return objCustomerDetailsManager.getCustomerTypes(); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public LinkedHashMap getCustomerNameByType(int custTypId) throws CustomerException, SQLException{ 
 return objCustomerDetailsManager.getCustomerNameByType(custTypId); 
}
}

/*
*$Log: SessionShipmentDetailsManagerBean.java,v $
*Revision 1.15  2005/09/10 13:18:42  kduraisamy
*order by clause added.
*
*Revision 1.14  2005/09/01 07:55:28  kduraisamy
*signature added for getcustomers.
*
*Revision 1.13  2005/09/01 07:39:25  kduraisamy
*job status for pmac committed.
*
*Revision 1.12  2005/07/25 06:50:24  kduraisamy
*signature changed.user name added as additional parameter.
*
*Revision 1.11  2005/07/23 15:11:35  kduraisamy
*signature added for make valid and invalid.
*
*Revision 1.10  2005/07/23 14:33:34  kduraisamy
*updateShipmentDetails() signature added.
*
*Revision 1.9  2005/07/23 13:22:46  kduraisamy
*signature added for getShipmentOperationDetailsForUpdate().
*
*Revision 1.8  2005/07/23 09:22:04  kduraisamy
*signature added for isModify() and getShipmentDetails().
*
*Revision 1.7  2005/07/23 08:58:33  kduraisamy
*signature changed for getProdJobDetailsForUpdateByworkorder().
*
*Revision 1.6  2005/07/23 08:51:14  kduraisamy
*signature changed for getProdJobDetailsByworkorder().
*
*Revision 1.5  2005/07/22 12:00:24  kduraisamy
*signature added for getAllJobStatusDetails().
*
*Revision 1.4  2005/07/22 11:10:17  kduraisamy
*signature added for getShipmentOpns().
*
*Revision 1.3  2005/07/22 09:09:50  kduraisamy
*signature added for getWoList().
*
*Revision 1.2  2005/07/22 07:36:05  kduraisamy
*signature added for addNewShipmentDetails().
*
*Revision 1.1  2005/07/22 07:29:21  kduraisamy
*initial commit.
*
*
*/