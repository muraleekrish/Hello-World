/*
 * Created on Dec 2, 2005
 *
 * ClassName	:  	ProductionPlanningManager.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savantit.prodacs.businessimplementation.productionplanning;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.log4j.Logger;
import com.savantit.prodacs.infra.dbtools.DBConnection;
import com.savantit.prodacs.infra.dbtools.DBConnectionFactory;
import com.savantit.prodacs.infra.dbtools.SQLMaster;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.infra.util.LoggerOutput;
import com.savantit.prodacs.infra.util.ProdacsConfig;
/**
 * @author kduraisamy
 *
 */
public class ProductionPlanningManager
{
    static Logger logger = Logger.getLogger(ProductionPlanningManager.class);
    
    public ProductionPlanningManager()
    {
        logger.addAppender(LoggerOutput.getAppender());
        logger.setLevel(ProdacsConfig.LOGGER_LEVEL);
    }
    
    public ProductionPlanningDetails[] getMachineWiseRecapDetails() throws SQLException, ProductionPlanningException
    {
        DBConnection con = null;
        int opnGpId = 0;
        String opnGpCode = "";
        Hashtable ht = new Hashtable();
        int openJobQuantities = 0;
        int activeJobQuantities = 0;
        double pendingHrs = 0;
        String opnGpMachines = "";
        ProductionPlanningDetails[] objProductionPlanningDetailsList = null;
        Vector vecResult = new Vector();
        try
        {
            con = DBConnectionFactory.getConnection();
            PreparedStatement psValidMcRelatedGps = con.executeStatement(SQLMaster.VALID_MCRELATED_OPERATION_GROUP_SELECT_SQL_QUERY);
            ResultSet rsValidMcRelatedGps = psValidMcRelatedGps.executeQuery();
            
            while(rsValidMcRelatedGps.next())
            {
                ProductionPlanningDetails objProductionPlanningDetails = new ProductionPlanningDetails();
                opnGpId = rsValidMcRelatedGps.getInt("OPN_GP_ID");
                opnGpCode = rsValidMcRelatedGps.getString("OPN_GP_CODE");
                ht.put("OPN_GP_ID",new Integer(opnGpId));
                
                PreparedStatement psMcSelect = con.executeStatement(SQLMaster.OPN_GP_MC_SELECT_SQL_QUERY,ht);
                ResultSet rsMcSelect = psMcSelect.executeQuery();
                opnGpMachines = "";
                while(rsMcSelect.next())
                {
                    if(opnGpMachines.equals(""))
                        opnGpMachines = rsMcSelect.getString("MC_CDE");
                    else
                        opnGpMachines = opnGpMachines+","+rsMcSelect.getString("MC_CDE");
                }
                
                PreparedStatement psJobQuantities = con.executeStatement(SQLMaster.OPEN_JOB_QUANTITIES_SELECT_SQL_QUERY,ht);
                ResultSet rsJobQuantities = psJobQuantities.executeQuery();
                if(rsJobQuantities.next())
                {
                    openJobQuantities = rsJobQuantities.getInt("COUNT");
                }
                
                psJobQuantities = con.executeStatement(SQLMaster.ACTIVE_JOB_QUANTITIES_SELECT_SQL_QUERY,ht);
                rsJobQuantities = psJobQuantities.executeQuery();
                if(rsJobQuantities.next())
                {
                    activeJobQuantities = rsJobQuantities.getInt("COUNT");
                }
                
                PreparedStatement psPendingHrs = con.executeStatement(SQLMaster.PENDING_OPN_HRS_SELECT_SQL_QUERY,ht);
                ResultSet rsPendingHrs = psPendingHrs.executeQuery();
                
                if(rsPendingHrs.next())
                {
                    pendingHrs = rsPendingHrs.getDouble("SUMSTDHRS");
                }
                         
                
               /* if(BuildConfig.DMODE)
                {
                    System.out.println("OPN GP CODE:"+opnGpCode);
                    System.out.println("OPN GP MACHINES:"+opnGpMachines);
                    
                    System.out.println("Opn Jb Qtys:"+openJobQuantities);
                    System.out.println("Pending Jb Qtys:"+pendingJobQuantities);
                    System.out.println("SUM Pending HRS:"+pendingHrs);
                }*/
                          
                
                objProductionPlanningDetails.setOpnGpId(opnGpId);
                objProductionPlanningDetails.setOpnGpCode(opnGpCode);
                objProductionPlanningDetails.setOpnGpMachines(opnGpMachines);
                objProductionPlanningDetails.setOpenJobQtys(openJobQuantities);
                objProductionPlanningDetails.setActiveJobQtys(activeJobQuantities);
                objProductionPlanningDetails.setPendingStdHrs(pendingHrs);
                vecResult.add(objProductionPlanningDetails);
                
                                
            }
            
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
                System.out.println("GENERAL EXCEPTION");
            }
            
            logger.error("GENERAL SQL ERROR",sqle);
            
            
            throw new ProductionPlanningException("PMEC000","GENERAL SQL ERROR",sqle.toString());
        }
        
        finally
        {
            try
            {
                if(con != null)
                    if(con.getConnection() != null )
                        if(!con.getConnection().isClosed())
                        {
                            con.closeConnection();
                        }
            }
            catch(SQLException clse)
            {
                
                logger.error("CONNECTION CLOSING EXCEPTION", clse);
                throw new SQLException("CONNECTION CLOSING EXCEPTION" + clse.toString());
            }
        }
        objProductionPlanningDetailsList = new ProductionPlanningDetails[vecResult.size()];
        vecResult.copyInto(objProductionPlanningDetailsList);
        return objProductionPlanningDetailsList;
        
    }
    
    public static void main(String args[]) throws ProductionPlanningException, SQLException
    {
        ProductionPlanningManager objProductionPlanningManager = new ProductionPlanningManager();
        ProductionPlanningDetails[] objProductionPlanningDetailsList = null;
        ProductionPlanningDetails objProductionPlanningDetails = null; 
        objProductionPlanningDetailsList = objProductionPlanningManager.getMachineWiseRecapDetails();
        for(int i = 0; i<objProductionPlanningDetailsList.length;i++)
        {
             objProductionPlanningDetails = objProductionPlanningDetailsList[i];
             System.out.println("OPN GP CODE:"+objProductionPlanningDetails.getOpnGpCode());
             System.out.println("OPN GP MACHINES:"+objProductionPlanningDetails.getOpnGpMachines());
             System.out.println("Opn Jb Qtys:"+objProductionPlanningDetails.getOpenJobQtys());
             System.out.println("Active Jb Qtys:"+objProductionPlanningDetails.getActiveJobQtys());
             System.out.println("Pending HRS:"+objProductionPlanningDetails.getPendingStdHrs());
         
        }
        
    }

}

/*
*$Log: ProductionPlanningManager.java,v $
*Revision 1.2  2005/12/05 05:41:25  kduraisamy
*production planning screen added.
*
*Revision 1.1  2005/12/05 05:31:05  kduraisamy
*initial commit.
*
*
*/