/*
 * Created on Dec 5, 2005
 *
 * ClassName	:  	SessionProductionPlanningManagerBean.java
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


import com.savantit.prodacs.businessimplementation.productionplanning.ProductionPlanningDetails;
import com.savantit.prodacs.businessimplementation.productionplanning.ProductionPlanningException;
import com.savantit.prodacs.businessimplementation.productionplanning.ProductionPlanningManager;

/**
 * <!-- begin-user-doc --> A generated session bean <!-- end-user-doc --> <!-- lomboz.beginDefinition --> <?xml version="1.0" encoding="UTF-8"?> <lomboz:EJB xmlns:j2ee="http://java.sun.com/xml/ns/j2ee" xmlns:lomboz="http://lomboz.objectlearn.com/xml/lomboz"> <lomboz:session> <lomboz:sessionEjb> <j2ee:display-name>SessionProductionPlanningManager</j2ee:display-name> <j2ee:ejb-name>SessionProductionPlanningManager</j2ee:ejb-name> <j2ee:ejb-class>com.savantit.prodacs.facade.SessionProductionPlanningManagerBean</j2ee:ejb-class> <j2ee:session-type>Stateless</j2ee:session-type> <j2ee:transaction-type>Container</j2ee:transaction-type> </lomboz:sessionEjb> </lomboz:session> </lomboz:EJB> <!-- lomboz.endDefinition --> <!-- begin-xdoclet-definition --> 
 * @ejb.bean  name="SessionProductionPlanningManager"	 jndi-name="SessionProductionPlanningManager" type="Stateless"  transaction-type="Container" -- This is needed for JOnAS. If you are not using JOnAS you can safely remove the tags below.
 * @jonas.bean  ejb-name="SessionProductionPlanningManager"  jndi-name="SessionProductionPlanningManager" -- <!-- end-xdoclet-definition --> 
 * @generated
 */
public abstract class SessionProductionPlanningManagerBean implements javax.ejb.SessionBean
{

    ProductionPlanningManager objProductionPlanningManager = new ProductionPlanningManager();
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public ProductionPlanningDetails[] getMachineWiseRecapDetails() throws SQLException, ProductionPlanningException{ 
 return objProductionPlanningManager.getMachineWiseRecapDetails(); 
}
}

/*
*$Log: SessionProductionPlanningManagerBean.java,v $
*Revision 1.2  2005/12/07 05:57:18  kduraisamy
*Super user security added.
*
*Revision 1.1  2005/12/05 05:47:47  kduraisamy
*production planning screen added.
*
*
*/