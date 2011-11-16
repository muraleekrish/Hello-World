/*
 * Created on Jul 20, 2005
 *
 * ClassName	:  	SessionDespatchClearanceDetailsManagerBean.java
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


import com.savantit.prodacs.businessimplementation.production.DespatchClearanceDetails;
import com.savantit.prodacs.businessimplementation.production.DespatchClearanceDetailsManager;
import com.savantit.prodacs.businessimplementation.production.ProductionDetailsManager;
import com.savantit.prodacs.businessimplementation.production.ProductionException;
import com.savantit.prodacs.businessimplementation.workcalendar.WorkCalendarDetailsManager;
import com.savantit.prodacs.businessimplementation.workcalendar.WorkCalendarException;
import com.savantit.prodacs.businessimplementation.workorder.WorkOrderDetailsManager;
import com.savantit.prodacs.businessimplementation.workorder.WorkOrderException;
import com.savantit.prodacs.infra.beans.Filter;

/**
 * <!-- begin-user-doc --> A generated session bean <!-- end-user-doc --> <!-- lomboz.beginDefinition --> <?xml version="1.0" encoding="UTF-8"?> <lomboz:EJB xmlns:j2ee="http://java.sun.com/xml/ns/j2ee" xmlns:lomboz="http://lomboz.objectlearn.com/xml/lomboz"> <lomboz:session> <lomboz:sessionEjb> <j2ee:display-name>SessionDespatchClearanceDetailsManager</j2ee:display-name> <j2ee:ejb-name>SessionDespatchClearanceDetailsManager</j2ee:ejb-name> <j2ee:ejb-class>com.savantit.prodacs.facade.SessionDespatchClearanceDetailsManagerBean</j2ee:ejb-class> <j2ee:session-type>Stateless</j2ee:session-type> <j2ee:transaction-type>Container</j2ee:transaction-type> </lomboz:sessionEjb> </lomboz:session> </lomboz:EJB> <!-- lomboz.endDefinition --> <!-- begin-xdoclet-definition --> 
 * @ejb.bean  name="SessionDespatchClearanceDetailsManager"	 jndi-name="SessionDespatchClearanceDetailsManager" type="Stateless"  transaction-type="Container" -- This is needed for JOnAS. If you are not using JOnAS you can safely remove the tags below.
 * @jonas.bean  ejb-name="SessionDespatchClearanceDetailsManager"  jndi-name="SessionDespatchClearanceDetailsManager" -- <!-- end-xdoclet-definition --> 
 * @generated
 */
public abstract class SessionDespatchClearanceDetailsManagerBean implements javax.ejb.SessionBean
{

    	DespatchClearanceDetailsManager objDespatchClearanceDetailsManager = new DespatchClearanceDetailsManager();
    	WorkOrderDetailsManager objWorkOrderDetailsManager = new WorkOrderDetailsManager();
    	WorkCalendarDetailsManager  objWorkCalendarDetailsManager = new WorkCalendarDetailsManager();
    	ProductionDetailsManager objProductionDetailsManager = new ProductionDetailsManager();
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean addNewDespatchClearanceDetails(DespatchClearanceDetails[] objDespatchClearanceDetailsList) throws ProductionException, SQLException{ 
 return objDespatchClearanceDetailsManager.addNewDespatchClearanceDetails(objDespatchClearanceDetailsList);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean updateDespatchClearanceDetails(DespatchClearanceDetails objDespatchClearanceDetails) throws ProductionException, SQLException{ 
 return objDespatchClearanceDetailsManager.updateDespatchClearanceDetails(objDespatchClearanceDetails);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap getAllDespatchClearanceDetails(Filter filters[],String sortBy,boolean ascending,int startIndex,int displayCount) throws ProductionException,SQLException{ 
 return objDespatchClearanceDetailsManager.getAllDespatchClearanceDetails(filters,sortBy,ascending,startIndex,displayCount); 
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
public DespatchClearanceDetails getDespatchClearanceDetails(int despatchId) throws ProductionException,SQLException{ 
 return objDespatchClearanceDetailsManager.getDespatchClearanceDetails(despatchId); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public Vector viewUnPostedDespatchDetails(Date fromDate,Date toDate) throws SQLException, ProductionException{ 
 return objDespatchClearanceDetailsManager.viewUnPostedDespatchDetails(fromDate,toDate); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap postDespatchDetails(Vector vec_ProdIds) throws ProductionException, SQLException{ 
 return objDespatchClearanceDetailsManager.postDespatchDetails(vec_ProdIds); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public Vector getDespatchOperationDetails(int woJbId) throws ProductionException,SQLException{ 
 return objDespatchClearanceDetailsManager.getDespatchOperationDetails(woJbId); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public Vector getDespatchOperationDetailsForUpdate(int woJbId,int despatchId) throws ProductionException,SQLException{ 
 return objDespatchClearanceDetailsManager.getDespatchOperationDetailsForUpdate(woJbId,despatchId); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean isModifyForDespatch(int despatchId) throws ProductionException, SQLException{ 
 return objDespatchClearanceDetailsManager.isModifyForDespatch(despatchId);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap makeDespatchValid(Vector despatchIds) throws ProductionException, SQLException{ 
 return objDespatchClearanceDetailsManager.makeDespatchValid(despatchIds); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap makeDespatchInValid(Vector despatchIds) throws ProductionException, SQLException{ 
 return objDespatchClearanceDetailsManager.makeDespatchInValid(despatchIds); 
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
public Vector getProdnJobDetailsByWorkOrder(int woId) throws SQLException,ProductionException{ 
	return objProductionDetailsManager.getProdnJobDetailsByWorkOrder(woId); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public LinkedHashMap getShiftDefnNameList() throws WorkCalendarException,SQLException{ 
 return objWorkCalendarDetailsManager.getShiftDefnNameList(); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public Vector getProdnJobDetailsForUpdateByWorkOrder(int woId,int prodId,int radlId,int finalProdId,int despatchId,int shipmentId) throws SQLException,ProductionException{ 
 return objProductionDetailsManager.getProdnJobDetailsForUpdateByWorkOrder(woId,prodId,radlId,finalProdId,despatchId,shipmentId); 
}
}

/*
*$Log: SessionDespatchClearanceDetailsManagerBean.java,v $
*Revision 1.8  2005/09/10 13:18:42  kduraisamy
*order by clause added.
*
*Revision 1.7  2005/07/23 08:56:42  kduraisamy
*signature changed for getProdJobDetailsForUpdateByworkorder().
*
*Revision 1.6  2005/07/22 09:08:23  kduraisamy
*signature added for getShiftDefnNameList().
*
*Revision 1.5  2005/07/21 12:32:31  sponnusamy
*Session bean added.
*
*Revision 1.4  2005/07/21 07:12:23  kduraisamy
*signature added for getShiftNameList().
*
*Revision 1.3  2005/07/20 20:08:17  kduraisamy
*make valid and invalid signatures added.
*
*Revision 1.2  2005/07/20 12:24:42  kduraisamy
*signature added for getWoList().
*
*Revision 1.1  2005/07/20 12:07:44  kduraisamy
*initial commit.
*
*
*/