/*
 * Created on Jan 11, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.facade;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.ejb.SessionBean;

import com.savantit.prodacs.businessimplementation.production.DespatchClearanceDetailsManager;
import com.savantit.prodacs.businessimplementation.production.FinalProductionDetailsManager;
import com.savantit.prodacs.businessimplementation.production.NonProductionDetailsManager;
import com.savantit.prodacs.businessimplementation.production.PopDetailsManager;
import com.savantit.prodacs.businessimplementation.production.ProductionDetailsManager;
import com.savantit.prodacs.businessimplementation.production.ProductionException;
import com.savantit.prodacs.businessimplementation.production.RadlProductionDetailsManager;
import com.savantit.prodacs.businessimplementation.production.ShipmentDetailsManager;
/**
 * @ejb.bean name="SessionPostingDetails"
 *	jndi-name="SessionPostingDetailsBean"
 *	type="Stateless" 
 * 
 *--
 * This is needed for JOnAS.
 * If you are not using JOnAS you can safely remove the tags below.
 * @jonas.bean ejb-name="SessionPostingDetails"
 *	jndi-name="SessionPostingDetailsBean"
 * 
 *--
 **/
public abstract class SessionPostingDetailsBean implements SessionBean 
{
	ProductionDetailsManager objProductionDetailsManager = new ProductionDetailsManager();
	FinalProductionDetailsManager  objFinalProductionDetailsManager = new FinalProductionDetailsManager();
	NonProductionDetailsManager objNonProductionDetailsManager = new NonProductionDetailsManager();
	RadlProductionDetailsManager objRadialProductionDetailsManager = new RadlProductionDetailsManager();
	PopDetailsManager objPopDetailsManager = new PopDetailsManager();
	DespatchClearanceDetailsManager objDespatchClearanceDetailsManager = new DespatchClearanceDetailsManager();
	ShipmentDetailsManager objShipmentDetailsManager = new ShipmentDetailsManager();
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap postProductionDetails(Vector vec_ProdIds) throws ProductionException, SQLException
{ 
	return objProductionDetailsManager.postProductionDetails(vec_ProdIds);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap postNonProductionDetails(Vector vec_NonProd_Ids) throws ProductionException, SQLException{ 
 return objNonProductionDetailsManager.postNonProductionDetails(vec_NonProd_Ids);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap postRadlProductionDetails(Vector vec_RadlProdIds) throws ProductionException, SQLException{ 
 return objRadialProductionDetailsManager.postRadlProductionDetails(vec_RadlProdIds);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap postPopDetails(Vector vec_Pop_Ids) throws ProductionException, SQLException{ 
 return objPopDetailsManager.postPopDetails(vec_Pop_Ids);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public Vector viewUnPostedProductionDetails(Date fromDate,Date toDate) throws SQLException, ProductionException{ 
 return objProductionDetailsManager.viewUnPostedProductionDetails(fromDate,toDate); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public Vector viewUnPostedNonProductionDetails(Date fromDate,Date toDate) throws ProductionException,SQLException{ 
 return objNonProductionDetailsManager.viewUnPostedNonProductionDetails(fromDate,toDate); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public Vector viewUnPostedRadlProductionDetails(Date fromDate,Date toDate) throws SQLException, ProductionException{ 
 return objRadialProductionDetailsManager.viewUnPostedRadlProductionDetails(fromDate,toDate); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public Vector viewUnPostedPopProductionDetails(Date fromDate,Date toDate) throws ProductionException,SQLException{ 
 return objPopDetailsManager.viewUnPostedPopProductionDetails(fromDate,toDate); 
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
public Vector viewUnPostedFinalProductionDetails(Date fromDate,Date toDate) throws SQLException, ProductionException{ 
 return objFinalProductionDetailsManager.viewUnPostedFinalProductionDetails(fromDate,toDate); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public Vector viewUnPostedShipmentDetails(Date fromDate,Date toDate) throws SQLException, ProductionException{ 
 return objShipmentDetailsManager.viewUnPostedShipmentDetails(fromDate,toDate); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap postShipmentDetails(Vector vec_ProdIds) throws ProductionException, SQLException{ 
 return objShipmentDetailsManager.postShipmentDetails(vec_ProdIds); 
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
}