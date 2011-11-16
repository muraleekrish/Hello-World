/*
 * Created on Jul 15, 2005
 *
 * ClassName	:  	SessionFinalProductionDetailsManagerBean.java
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
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;

import com.savantit.prodacs.businessimplementation.employee.EmployeeDetailsManager;
import com.savantit.prodacs.businessimplementation.employee.EmployeeException;
import com.savantit.prodacs.businessimplementation.job.OperationDetails;
import com.savantit.prodacs.businessimplementation.production.FinalProductionDetails;
import com.savantit.prodacs.businessimplementation.production.FinalProductionDetailsManager;
import com.savantit.prodacs.businessimplementation.production.ProductionAccountingDateDetails;
import com.savantit.prodacs.businessimplementation.production.ProductionDetailsManager;
import com.savantit.prodacs.businessimplementation.production.ProductionException;
import com.savantit.prodacs.businessimplementation.workcalendar.WorkCalendarDetailsManager;
import com.savantit.prodacs.businessimplementation.workcalendar.WorkCalendarException;
import com.savantit.prodacs.businessimplementation.workorder.WorkOrderDetailsManager;
import com.savantit.prodacs.businessimplementation.workorder.WorkOrderException;
import com.savantit.prodacs.infra.beans.Filter;



/**
 * <!-- begin-user-doc --> A generated session bean <!-- end-user-doc --> <!-- lomboz.beginDefinition --> <?xml version="1.0" encoding="UTF-8"?> <lomboz:EJB xmlns:j2ee="http://java.sun.com/xml/ns/j2ee" xmlns:lomboz="http://lomboz.objectlearn.com/xml/lomboz"> <lomboz:session> <lomboz:sessionEjb> <j2ee:display-name>SessionFinalProductionDetailsManager</j2ee:display-name> <j2ee:ejb-name>SessionFinalProductionDetailsManager</j2ee:ejb-name> <j2ee:ejb-class>com.savantit.prodacs.facade.SessionFinalProductionDetailsManagerBean</j2ee:ejb-class> <j2ee:session-type>Stateless</j2ee:session-type> <j2ee:transaction-type>Container</j2ee:transaction-type> </lomboz:sessionEjb> </lomboz:session> </lomboz:EJB> <!-- lomboz.endDefinition --> <!-- begin-xdoclet-definition --> 
 * @ejb.bean  name="SessionFinalProductionDetailsManager"	 jndi-name="SessionFinalProductionDetailsManager" type="Stateless"  transaction-type="Container" -- This is needed for JOnAS. If you are not using JOnAS you can safely remove the tags below.
 * @jonas.bean  ejb-name="SessionFinalProductionDetailsManager"  jndi-name="SessionFinalProductionDetailsManager" -- <!-- end-xdoclet-definition --> 
 * @generated
 */
public abstract class SessionFinalProductionDetailsManagerBean implements javax.ejb.SessionBean
{
    FinalProductionDetailsManager objFinalProductionDetailsManager = new FinalProductionDetailsManager();
    ProductionDetailsManager objProductionDetailsManager = new ProductionDetailsManager();
    WorkOrderDetailsManager objWorkOrderDetailsManager = new WorkOrderDetailsManager();
    WorkCalendarDetailsManager objWorkCalendarDetailsManager = new WorkCalendarDetailsManager();
    EmployeeDetailsManager objEmployeeDetailsManager = new EmployeeDetailsManager();
    /**
     * @ejb.interface-method
     *	view-type="remote" 
     **/
    public boolean addNewFinalProductionDetails(FinalProductionDetails[] objFinalProductionDetailsList) throws ProductionException, SQLException{ 
        return objFinalProductionDetailsManager.addNewFinalProductionDetails(objFinalProductionDetailsList);
    }
    /**
     * @ejb.interface-method
     *	view-type="remote" 
     **/
    public HashMap getAllFinalProductionDetails(Filter filters[],String sortBy,boolean ascending,int startIndex,int displayCount) throws ProductionException,SQLException{ 
        return objFinalProductionDetailsManager.getAllFinalProductionDetails(filters,sortBy,ascending,startIndex,displayCount); 
    }
    /**
     * @ejb.interface-method
     *	view-type="remote" 
     **/
    public boolean updateFinalProductionDetails(FinalProductionDetails objFinalProductionDetails) throws ProductionException, SQLException{ 
        return objFinalProductionDetailsManager.updateFinalProductionDetails(objFinalProductionDetails);
    }
    /**
     * @ejb.interface-method
     *	view-type="remote" 
     **/
    public FinalProductionDetails getFinalProductionDetails(int finalProdId) throws ProductionException,SQLException{ 
        return objFinalProductionDetailsManager.getFinalProductionDetails(finalProdId); 
    }
    
    /**
     * @ejb.interface-method
     *	view-type="remote" 
     **/
    public LinkedHashMap getWorkOrderList() throws SQLException, WorkOrderException
    { 
        return objWorkOrderDetailsManager.getWorkOrderList(); 
    }
    
    /**
     * @ejb.interface-method
     *	view-type="remote" 
     **/
    public Vector viewUnPostedFinalProductionDetails(Date fromDate,Date toDate) throws SQLException, ProductionException{ 
        return objFinalProductionDetailsManager.viewUnPostedFinalProductionDetails(fromDate,toDate); 
    }
    /**
     * @ejb.interface-method
     *	view-type="remote" 
     **/
    public HashMap postFinalProductionDetails(Vector vec_ProdIds) throws ProductionException, SQLException{ 
        return objFinalProductionDetailsManager.postFinalProductionDetails(vec_ProdIds); 
    }
    /**
     * @ejb.interface-method
     *	view-type="remote" 
     **/
    public Vector getFinalProdnJobOperationDetails(int woJbId) throws ProductionException,SQLException{ 
        return objFinalProductionDetailsManager.getFinalProdnJobOperationDetails(woJbId); 
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
    public Vector getProdnJobDetailsForUpdateByWorkOrder(int woId,int prodId,int radlId,int finalProdId,int despatchId,int shipmentId) throws SQLException,ProductionException
    { 
        return objProductionDetailsManager.getProdnJobDetailsForUpdateByWorkOrder(woId,prodId,radlId,finalProdId,despatchId,shipmentId); 
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
    public LinkedHashMap getAllEmployeesByTypes() throws EmployeeException, SQLException, ProductionException
    { 
        return objProductionDetailsManager.getAllEmployeesByTypes(); 
    }
    /**
     * @ejb.interface-method
     *	view-type="remote" 
     **/
    public OperationDetails[] viewOperations(int woJbId) throws SQLException, ProductionException{ 
        return objProductionDetailsManager.viewOperations(woJbId); 
    }
    /**
     * @throws ProductionException
     * @throws ParseException
     * @throws SQLException
     * @ejb.interface-method
     *	view-type="remote" 
     **/
    public ProductionAccountingDateDetails isPosted(String mcCde,Date prodDate,int shiftId) throws ProductionException, ParseException, SQLException{ 
        return objProductionDetailsManager.isPosted(mcCde,prodDate,shiftId);
    }
    
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap getAllWorkOrderJobStatus(Filter filters[],String sortBy,boolean ascending,int startIndex,int displayCount) throws ProductionException,SQLException{ 
    return objFinalProductionDetailsManager.getAllWorkOrderJobStatus(filters,sortBy,ascending,startIndex,displayCount); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean isModifyForFprod(int fProdId) throws ProductionException, SQLException{ 
 return objFinalProductionDetailsManager.isModifyForFprod(fProdId);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public Vector getFinalProdnJobOperationDetailsForUpdate(int woJbId,int fprodId) throws ProductionException,SQLException{ 
 return objFinalProductionDetailsManager.getFinalProdnJobOperationDetailsForUpdate(woJbId,fprodId); 
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
public HashMap makeFinalProductionValid(Vector finalProdIds) throws ProductionException, SQLException{ 
 return objFinalProductionDetailsManager.makeFinalProductionValid(finalProdIds); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap makeFinalProductionInValid(Vector finalProdIds) throws ProductionException, SQLException{ 
 return objFinalProductionDetailsManager.makeFinalProductionInValid(finalProdIds); 
}
}

/*
*$Log: SessionFinalProductionDetailsManagerBean.java,v $
*Revision 1.12  2005/09/10 13:18:42  kduraisamy
*order by clause added.
*
*Revision 1.11  2005/07/23 08:51:14  kduraisamy
*signature changed for getProdJobDetailsByworkorder().
*
*Revision 1.10  2005/07/20 20:01:18  kduraisamy
*signature added for make valid and invalid methods.
*
*Revision 1.9  2005/07/20 14:36:54  kduraisamy
*getShiftDefnNameList() signature added.
*
*Revision 1.8  2005/07/20 09:29:52  kduraisamy
*signature added for getFinalProdnJobOperationDetailsForUpdate().
*
*Revision 1.7  2005/07/20 06:36:43  kduraisamy
*signature added for ismodify().
*
*Revision 1.6  2005/07/16 12:14:27  kduraisamy
*signature added for getAllWorkorderjobStatus() filter method.
*
*Revision 1.5  2005/07/15 19:37:41  kduraisamy
*signature added for isPosted().
*
*Revision 1.4  2005/07/15 19:23:44  kduraisamy
*signature added for getShiftDefnNameList().
*
*Revision 1.3  2005/07/15 17:00:17  kduraisamy
*getProdnJobDetailsByWorkOrder() and getProdnJobDetailsForUpdateByWorkOrder() changed because of FINAL PRODUCTION.
*
*Revision 1.2  2005/07/15 10:58:43  kduraisamy
*signature added for getWoList().
*
*Revision 1.1  2005/07/15 07:40:37  kduraisamy
*initial commit.
*
*
*/