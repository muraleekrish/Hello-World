/*
 * Created on Jul 21, 2005
 *
 * ClassName	:  	ShipmentDetailsManager.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savantit.prodacs.businessimplementation.production;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.savantit.prodacs.infra.beans.Filter;
import com.savantit.prodacs.infra.dbtools.DBConnection;
import com.savantit.prodacs.infra.dbtools.DBConnectionFactory;
import com.savantit.prodacs.infra.dbtools.SQLMaster;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.infra.util.LoggerOutput;
import com.savantit.prodacs.infra.util.ProdacsConfig;
import com.savantit.prodacs.infra.util.SortString;

/**
 * @author kduraisamy
 *
 */
public class ShipmentDetailsManager
{
    static Logger logger = Logger.getLogger(ShipmentDetailsManager.class);
    public ShipmentDetailsManager()
    {
        logger.addAppender(LoggerOutput.getAppender());
        logger.setLevel(ProdacsConfig.LOGGER_LEVEL);
    }
    public boolean addNewShipmentDetails(ShipmentDetails[] objShipmentDetailsList) throws ProductionException, SQLException
    {
        
        boolean addRESULT = false;
        int startOpn = 0;
        int endOpn = 0;
        Hashtable ht_Shipment_Add = new Hashtable();
        DBConnection con = null;
        
        logger.info("SHIPMENT ADD STARTS");
        
        if(BuildConfig.DMODE)
            System.out.println("SHIPMENT ADD STARTS");
        
        if(objShipmentDetailsList.length == 0)
        {
            logger.error("SHIPMENT DETAILS OBJ NULL");
            throw new ProductionException("PMEC001","SHIPMENT DETAILS OBJ NULL","");
        }
        
        con = DBConnectionFactory.getConnection();
        con.startTransaction();
        try
        {
            
            for(int i = 0;i<objShipmentDetailsList.length;i++)
            {
                startOpn = objShipmentDetailsList[i].getShipmentStartOpn();
                endOpn = objShipmentDetailsList[i].getShipmentEndOpn();
                ht_Shipment_Add.put("SHIPMENT_CRNT_DATE",objShipmentDetailsList[i].getShipmentCrntDate());
                ht_Shipment_Add.put("SHIFT_ID",new Integer(objShipmentDetailsList[i].getShiftId()));
                ht_Shipment_Add.put("WOJB_ID",new Integer(objShipmentDetailsList[i].getWoJbId()));
                ht_Shipment_Add.put("SHIPMENT_QTY_SNOS",objShipmentDetailsList[i].getShipmentQtySnos());
                ht_Shipment_Add.put("SHIPMENT_TOT_QTY",new Integer(objShipmentDetailsList[i].getShipmentTotQty()));
                ht_Shipment_Add.put("SHIPMENT_START_OPN",new Integer(startOpn));
                ht_Shipment_Add.put("SHIPMENT_END_OPN",new Integer(endOpn));
                ht_Shipment_Add.put("SHIPMENT_DCNO",objShipmentDetailsList[i].getDeliveryChallanNo());
                ht_Shipment_Add.put("SHIPMENT_CREATEDBY",objShipmentDetailsList[i].getCreatedBy());
                //prodQtySnos = objShipmentDetailsList[i].getProdQtySnos();
                con.executeUpdateStatement(SQLMaster.SHIPMENT_DETAILS_ADD_SQL_QUERY,ht_Shipment_Add);
                /*if(result > 0)
                 */
                /*   {
                 
                 //to set WO_ISPROD_ENTERED to 1..
                  
                  con.executeUpdateStatement(SQLMaster.WORKORDER_TABLE_UPDATE_SQL_QUERY,ht_Shipment_Add);
                  int j = 0;
                  ps = con.executeStatement(SQLMaster.SHIPMENT_ID_SELECT_SQL_QUERY);
                  ResultSet rs_ProdId_Get = ps.executeQuery();
                  if(rs_ProdId_Get.next())
                  {
                  shipmentId = rs_ProdId_Get.getInt(1);
                  if(BuildConfig.DMODE)
                  System.out.println("SHIPMENT ID :"+shipmentId);
                  }
                  rs_ProdId_Get.close();
                  ps.close();
                  */   
                
                /*vec_EmpHrs = objShipmentDetailsList[i].getDespatchEmpHrsDetails();
                 
                 
                 for(j = 0;j<vec_EmpHrs.size();j++)
                 {
                 
                 EmployeeDtyOtDetails objEmployeeDtyOtDetails = new EmployeeDtyOtDetails();
                 objEmployeeDtyOtDetails = (EmployeeDtyOtDetails)vec_EmpHrs.elementAt(j);
                 empId = objEmployeeDtyOtDetails.getEmpId();
                 
                 ht_EmpHrs_Add.put("SHIPMENT_ID",new Integer(shipmentId));
                 ht_EmpHrs_Add.put("EMP_ID",new Integer(empId));
                 ht_EmpHrs_Add.put("SHIPMENT_MODIFYCOUNT",new Integer(0));
                 result = con.executeUpdateStatement(SQLMaster.FINAL_PRODN_EMPHRS_ADD_SQL_QUERY,ht_EmpHrs_Add);
                 if(result>0)
                 {
                 if(BuildConfig.DMODE)
                 System.out.println("Employee Hrs Details Added");
                 
                 }
                 
                 
                 }
                 }*/
            }
            con.commitTransaction();
            addRESULT = true;
            
            
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }
            con.rollBackTransaction();
            if(BuildConfig.DBASE==BuildConfig.ORACLE)
            {
                if(sqle.toString().indexOf("PK_SHIPMENTID")>=0)
                {
                    throw new ProductionException("PMEC002","SHIPMENT ID IS UNIQUE.UNIQUE CONSTRAINT VIOLATED",sqle.toString());
                }
                else if (sqle.toString().indexOf("FK_SHIPMENT_SHIFTID") >= 0)
                {
                    throw new ProductionException("PMEC007","PARENT KEY SHIFT ID NOT FOUND",sqle.toString());
                }
                else if (sqle.toString().indexOf("FK_SHIPMENT_WOJBID") >= 0)
                {
                    throw new ProductionException("PMEC008","PARENT KEY WORK ORDER JOB ID NOT FOUND",sqle.toString());
                }
                else
                {
                    throw new ProductionException("PMEC000","GENERAL SQL ERROR",sqle.toString());
                }
            }
            if(BuildConfig.DBASE==BuildConfig.MYSQL)
            {
                if(sqle.toString().indexOf("Duplicate entry")>=0)
                {
                    throw new ProductionException("PMEC002","SHIPMENT ID IS UNIQUE.UNIQUE CONSTRAINT VIOLATED",sqle.toString());
                }
                else if (sqle.toString().indexOf("foreign key constraint") >= 0)
                {
                    throw new ProductionException("PMEC004","SHIFT ID OR WORKORDER JOB ID NOT FOUND",sqle.toString());
                }
                else
                {
                    throw new ProductionException("PMEC000","GENERAL SQL ERROR",sqle.toString());
                }
            }
            
            
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
        
        
        
        
        logger.info("SHIPMENT ADD ENDS");
        
        return addRESULT;
    }
    
    public boolean updateShipmentDetails(ShipmentDetails objShipmentDetails) throws ProductionException, SQLException
    {
        boolean updateRESULT = false;
        int shipmentId = 0;
        DBConnection con=null;
        Hashtable ht_Shipment_Update = new Hashtable();
        int result;
        
        
        logger.info("PRODUCTION UPDATE STARTS");
        shipmentId = objShipmentDetails.getShipmentId();
        
        con = DBConnectionFactory.getConnection();
        con.startTransaction();
        //ht_EmpHrs_Del.put("SHIPMENT_ID",new Integer(finalProdId));
        try
        {
            /*//to store Emp Hours entries into Log..
             result = con.executeUpdateStatement(SQLMaster.LOG_FINAL_PRODN_EMPHRS_ADD_SQL_QUERY,ht_EmpHrs_Del);
             if(result >0)
             {
             result = con.executeUpdateStatement(SQLMaster.FINAL_PRODN_EMP_HRS_DELETE_SQL_QUERY,ht_EmpHrs_Del);
             if(result>0)
             {
             if(BuildConfig.DMODE)
             System.out.println("PROD EMP HRS DELETED");
             }
             }
             */
            
            //Updation of SHIPMENT table.
            
            ht_Shipment_Update.put("SHIPMENT_ID",new Integer(shipmentId));
            ht_Shipment_Update.put("SHIPMENT_CRNT_DATE",objShipmentDetails.getShipmentCrntDate());
            ht_Shipment_Update.put("SHIFT_ID",new Integer(objShipmentDetails.getShiftId()));
            ht_Shipment_Update.put("WOJB_ID",new Integer(objShipmentDetails.getWoJbId()));
            ht_Shipment_Update.put("SHIPMENT_QTY_SNOS",objShipmentDetails.getShipmentQtySnos());
            ht_Shipment_Update.put("SHIPMENT_TOT_QTY",new Integer(objShipmentDetails.getShipmentTotQty()));
            ht_Shipment_Update.put("SHIPMENT_START_OPN",new Integer(objShipmentDetails.getShipmentStartOpn()));
            ht_Shipment_Update.put("SHIPMENT_END_OPN",new Integer(objShipmentDetails.getShipmentEndOpn()));
            ht_Shipment_Update.put("SHIPMENT_DCNO",new Integer(objShipmentDetails.getDeliveryChallanNo()));
            ht_Shipment_Update.put("SHIPMENT_POST_FLG","0");
            ht_Shipment_Update.put("SHIPMENT_CREATEDBY",objShipmentDetails.getModifiedBy());
            
            // to store Prod Entries to Log..
            
            result = con.executeUpdateStatement(SQLMaster.SHIPMENT_DETAILS_LOG_ADD_SQL_QUERY,ht_Shipment_Update);
            
            if(result > 0)
            {
                con.executeUpdateStatement(SQLMaster.SHIPMENT_DETAILS_UPDATE_SQL_QUERY,ht_Shipment_Update);
                /*PreparedStatement ps_ModifyCnt_Sel = con.executeStatement(SQLMaster.FINAL_PROD_MODIFY_COUNT_SELECT,ht_Shipment_Update);
                 ResultSet rs_ModifyCnt_Sel = ps_ModifyCnt_Sel.executeQuery();
                 int modifyCount = 0;
                 if(rs_ModifyCnt_Sel.next())
                 {
                 modifyCount = rs_ModifyCnt_Sel.getInt("SHIPMENT_MODIFYCOUNT");
                 }
                 ht_Shipment_Update.put("SHIPMENT_MODIFYCOUNT",new Integer(modifyCount));
                 */
                /*vec_EmpHrs = objShipmentDetails.getProdnEmpHrsDetails();
                 ht_EmpHrs_Add.put("SHIPMENT_MODIFYCOUNT",new Integer(modifyCount));
                 
                 for(int i = 0;i<vec_EmpHrs.size();i++)
                 {
                 EmployeeDtyOtDetails objEmployeeDtyOtDetails = new EmployeeDtyOtDetails();
                 objEmployeeDtyOtDetails = (EmployeeDtyOtDetails)vec_EmpHrs.elementAt(i);
                 empId = objEmployeeDtyOtDetails.getEmpId();
                 ht_EmpHrs_Add.put("PROD_ID",new Integer(finalProdId));
                 ht_EmpHrs_Add.put("EMP_ID",new Integer(empId));
                 result = con.executeUpdateStatement(SQLMaster.FINAL_PRODN_EMPHRS_ADD_SQL_QUERY,ht_EmpHrs_Add);
                 if(result>0)
                 {
                 if(BuildConfig.DMODE)
                 System.out.println("Employee Hrs Details Added");
                 
                 
                 }
                 
                 }
                 */        
                updateRESULT = true;
                con.commitTransaction();
            }
        }
        
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }
            con.rollBackTransaction();
            if(BuildConfig.DBASE==BuildConfig.ORACLE)
            {
                if(sqle.toString().indexOf("PK_SHIPMENTID")>=0)
                {
                    throw new ProductionException("PMEC002","SHIPMENT ID IS UNIQUE.UNIQUE CONSTRAINT VIOLATED",sqle.toString());
                }
                else if (sqle.toString().indexOf("FK_SHIPMENT_SHIFTID") >= 0)
                {
                    throw new ProductionException("PMEC007","PARENT KEY SHIFT ID NOT FOUND",sqle.toString());
                }
                else if (sqle.toString().indexOf("FK_SHIPMENT_WOJBID") >= 0)
                {
                    throw new ProductionException("PMEC008","PARENT KEY WORK ORDER JOB ID NOT FOUND",sqle.toString());
                }
                else
                {
                    throw new ProductionException("PMEC000","GENERAL SQL ERROR",sqle.toString());
                }
            }
            if(BuildConfig.DBASE==BuildConfig.MYSQL)
            {
                if(sqle.toString().indexOf("Duplicate entry")>=0)
                {
                    throw new ProductionException("PMEC002","SHIPMENT ID IS UNIQUE.UNIQUE CONSTRAINT VIOLATED",sqle.toString());
                }
                else if (sqle.toString().indexOf("foreign key constraint") >= 0)
                {
                    throw new ProductionException("PMEC004","PARENT KEY SHIFT ID OR WORKORDER JOB ID NOT FOUND",sqle.toString());
                }
                else
                {
                    throw new ProductionException("PMEC000","GENERAL SQL ERROR",sqle.toString());
                }
            }
            
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
        
        
        logger.info("PRODUCTION UPDATE ENDS");
        
        return updateRESULT;
    }
    
    public HashMap getAllShipmentDetails(Filter filters[],String sortBy,boolean ascending,int startIndex,int displayCount) throws ProductionException,SQLException
    {
        HashMap hm_Result = new HashMap();
        Vector vec_Prod_Det = new Vector();
        DBConnection con=null;
        
        logger.info("GET ALL SHIPMENT DETAILS FILTER STARTS");
        
        if((filters == null)||( sortBy == null))
        {
            logger.error("FILTER VALUES ARE NULL");
            
            throw new ProductionException("PMEC006","FILTER VALUES ARE NULL","");
        }
        
        con = DBConnectionFactory.getConnection();
        
        int tot_Rec_Cnt = 0;
        
        
        try
        {
            
            //filters and tableName are passed to the function and receives Total Record Count
            tot_Rec_Cnt = con.getRowCountWithComplexFilters(filters, SQLMaster.GET_ALL_SHIPMENT_DETAILS_FILTER_QUERY);
            
            //Finding end index for the query
            int eIndex = startIndex + displayCount;
            
            
            // filters,tablename,startindex,endindex,sorting column name and sortorder are passed to the function and  query will be received
            String Query = con.buildDynamicQuery(filters,startIndex,eIndex,sortBy,ascending,SQLMaster.GET_ALL_SHIPMENT_DETAILS_FILTER_QUERY);
            if(BuildConfig.DMODE)
            {
                // total records and query
                System.out.println("TOTAL NUMBER OF RECORDS : " + tot_Rec_Cnt);
                System.out.println("QUERY : " + Query);
            }
            
            logger.info("Total Records : " + tot_Rec_Cnt);
            logger.info("Query : " + Query);
            
            
            ResultSet rs_Prodn_Details_Get =  con.executeRownumStatement(Query);
            while(rs_Prodn_Details_Get.next())
            {
                ShipmentDetails  objShipmentDetails = new ShipmentDetails();
                objShipmentDetails.setShipmentId(rs_Prodn_Details_Get.getInt("SHIPMENT_ID"));
                objShipmentDetails.setShipmentCrntDate(rs_Prodn_Details_Get.getTimestamp("SHIPMENT_CRNT_DATE"));
                objShipmentDetails.setShiftId(rs_Prodn_Details_Get.getInt("SHIFT_ID"));
                objShipmentDetails.setShiftName(rs_Prodn_Details_Get.getString("SHIFT_NAME"));
                objShipmentDetails.setWoNo(rs_Prodn_Details_Get.getString("WO_NO"));
                //objShipmentDetails.setProdQtySnos(rs_Prodn_Details_Get.getString("PROD_QTY_SNOS"));
                //objShipmentDetails.setProdTotQty(rs_Prodn_Details_Get.getInt("PROD_TOT_QTY"));
                //objShipmentDetails.setProdStartOpn(rs_Prodn_Details_Get.getInt("PROD_START_OPN"));
                //objShipmentDetails.setProdEndOpn(rs_Prodn_Details_Get.getInt("PROD_END_OPN"));
                ProductionJobDetails objProductionJobDetails = new ProductionJobDetails();
                objProductionJobDetails.setJobName(rs_Prodn_Details_Get.getString("JB_NAME"));
                //objProductionJobDetails.setDwgNo(rs_Prodn_Details_Get.getString("JB_DWG_NO"));
                //objProductionJobDetails.setRvsnNo(rs_Prodn_Details_Get.getString("JB_RVSN_NO"));
                //objProductionJobDetails.setMatlType(rs_Prodn_Details_Get.getString("JB_MATL_TYP"));
                
                objShipmentDetails.setObjProductionJobDetails(objProductionJobDetails);
                
                objShipmentDetails.setShipmentDateStamp(rs_Prodn_Details_Get.getTimestamp("SHIPMENT_DATESTAMP"));
                objShipmentDetails.setShipmentIsValid(rs_Prodn_Details_Get.getInt("SHIPMENT_ISVALID"));
                vec_Prod_Det.addElement(objShipmentDetails);
            }
            hm_Result.put("TotalRecordCount",new Integer(tot_Rec_Cnt));
            hm_Result.put("ProductionDetails",vec_Prod_Det);
            rs_Prodn_Details_Get.close();
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }
            logger.info("GENERAL EXCEPTION");
            throw new ProductionException("PMEC000","GENERAL SQL ERROR",sqle.toString());
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
            catch(SQLException ex)
            {
                if(BuildConfig.DMODE)
                {
                    logger.error("EXCEPTION WHILE CLOSING CONNECTION");
                }
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }
        
        logger.info("GET ALL SHIPMENT DETAILS FILTER ENDS");
        
        return hm_Result;
    }
    
    public ShipmentDetails getShipmentDetails(int shipmentId) throws ProductionException,SQLException
    {
        ShipmentDetails objShipmentDetails = new ShipmentDetails();
        Hashtable ht_ProdDet_Get = new Hashtable();
        ProductionJobDetails objProductionJobDetails = new ProductionJobDetails();
        DBConnection con = null;
        
        logger.info("GET SHIPMENT DETAILS STARTS");
        
        con = DBConnectionFactory.getConnection();
        
        ht_ProdDet_Get.put("SHIPMENT_ID", new Integer(shipmentId));
        try
        {
            PreparedStatement ps_ProdDet_Get = con.executeStatement(SQLMaster.GET_SHIPMENT_DETAILS_SQL_QUERY,ht_ProdDet_Get);
            ResultSet rs_ProdDet_Get = ps_ProdDet_Get.executeQuery();
            
            if(rs_ProdDet_Get.next())
            {
                //set Production Details //
                objShipmentDetails.setShipmentId(shipmentId);
                objShipmentDetails.setShipmentCrntDate(rs_ProdDet_Get.getTimestamp("SHIPMENT_CRNT_DATE"));
                objShipmentDetails.setShiftId(rs_ProdDet_Get.getInt("SHIFT_ID"));
                objShipmentDetails.setShiftName(rs_ProdDet_Get.getString("SHIFT_NAME"));
                objShipmentDetails.setWoJbId(rs_ProdDet_Get.getInt("WOJB_ID"));
                objShipmentDetails.setWoNo(rs_ProdDet_Get.getString("WO_NO"));
                objShipmentDetails.setWoId(rs_ProdDet_Get.getInt("WO_ID"));
                objShipmentDetails.setShipmentQtySnos(rs_ProdDet_Get.getString("SHIPMENT_QTY_SNOS"));
                objShipmentDetails.setShipmentTotQty(rs_ProdDet_Get.getInt("SHIPMENT_TOT_QTY"));
                objShipmentDetails.setShipmentStartOpn(rs_ProdDet_Get.getInt("SHIPMENT_START_OPN"));
                objShipmentDetails.setShipmentEndOpn(rs_ProdDet_Get.getInt("SHIPMENT_END_OPN"));
                objShipmentDetails.setDeliveryChallanNo(rs_ProdDet_Get.getString("SHIPMENT_DCNO"));
                objShipmentDetails.setShipmentPostFlg(rs_ProdDet_Get.getString("SHIPMENT_POST_FLG").equals("1")?true:false);
                objShipmentDetails.setModifyCount(rs_ProdDet_Get.getInt("SHIPMENT_MODIFYCOUNT"));
                objShipmentDetails.setCreatedBy(rs_ProdDet_Get.getString("SHIPMENT_CREATEDBY"));
                objShipmentDetails.setShipmentDateStamp(rs_ProdDet_Get.getTimestamp("SHIPMENT_DATESTAMP"));
                objShipmentDetails.setShipmentIsValid(rs_ProdDet_Get.getInt("SHIPMENT_ISVALID"));
                
                ///set Job Details - starts///
                //objProductionJobDetails.setJobId(rs_ProdDet_Get.getInt("JB_ID"));
                objProductionJobDetails.setJobName(rs_ProdDet_Get.getString("JB_NAME"));
                objProductionJobDetails.setDwgNo(rs_ProdDet_Get.getString("JB_DWG_NO"));
                objProductionJobDetails.setRvsnNo(rs_ProdDet_Get.getString("JB_RVSN_NO"));
                objProductionJobDetails.setMatlType(rs_ProdDet_Get.getString("JB_MATL_TYP"));
                
                objShipmentDetails.setObjProductionJobDetails(objProductionJobDetails);
                //set Job Details - Ends///
            }
            else
            {
                throw new ProductionException("PMEC000","RECORD NOT FOUND","");
            }
            rs_ProdDet_Get.close();
            ps_ProdDet_Get.close();
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }
            throw new ProductionException("PMEC000","GENERAL SQL ERROR",sqle.toString());
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
        
        logger.info("GET SHIPMENT DETAILS ENDS");
        
        return objShipmentDetails;
    }
    
    public Vector viewUnPostedShipmentDetails(Date fromDate,Date toDate) throws SQLException, ProductionException
    {
        Vector vec_Result = new Vector();
        DBConnection con = null;
        Hashtable ht_UnPostedJbDet_Get = new Hashtable();
        Hashtable ht_UnPostedDet_Get = new Hashtable();
        int shipmentId = 0;
        int woJbId = 0;
        
        
        logger.info("GET UNPOSTED SHIPMENT DETAILS STARTS");
        
        
        con = DBConnectionFactory.getConnection();
        ht_UnPostedDet_Get.put("FROM_DATE",fromDate);
        ht_UnPostedDet_Get.put("TO_DATE",toDate);
        try
        {
            PreparedStatement ps_UnpostedProdId_Get = con.executeStatement(SQLMaster.UNPOSTED_SHIPMENT_ID_SELECT_SQL_QUERY,ht_UnPostedDet_Get);
            ResultSet rs_UnpostedProdId_Get = ps_UnpostedProdId_Get.executeQuery();
            while(rs_UnpostedProdId_Get.next())
            {
                UnPostedProductionDetails objUnPostedProductionDetails = new UnPostedProductionDetails();
                shipmentId = rs_UnpostedProdId_Get.getInt("SHIPMENT_ID");
                objUnPostedProductionDetails.setProdId(shipmentId);
                objUnPostedProductionDetails.setProdCrntDate(rs_UnpostedProdId_Get.getTimestamp("SHIPMENT_CRNT_DATE"));
                objUnPostedProductionDetails.setShiftName(rs_UnpostedProdId_Get.getString("SHIFT_NAME"));
                objUnPostedProductionDetails.setProdStartOpn(rs_UnpostedProdId_Get.getInt("SHIPMENT_START_OPN"));
                objUnPostedProductionDetails.setProdEndOpn(rs_UnpostedProdId_Get.getInt("SHIPMENT_END_OPN"));
                objUnPostedProductionDetails.setCompanyDCNo(rs_UnpostedProdId_Get.getString("SHIPMENT_DCNO"));
                objUnPostedProductionDetails.setJobQtySnos(rs_UnpostedProdId_Get.getString("SHIPMENT_QTY_SNOS"));
                objUnPostedProductionDetails.setJobQty(rs_UnpostedProdId_Get.getInt("SHIPMENT_TOT_QTY"));
                woJbId = rs_UnpostedProdId_Get.getInt("WOJB_ID");
                ht_UnPostedJbDet_Get.put("WOJB_ID",new Integer(woJbId));
                
                PreparedStatement ps_UnPostedJbDet_Get = con.executeStatement(SQLMaster.UNPOSTED_JOB_DETAILS_GET_SQL_QUERY,ht_UnPostedJbDet_Get);
                ResultSet rs_UnPostedJbDet_Get =  ps_UnPostedJbDet_Get.executeQuery();
                if(rs_UnPostedJbDet_Get.next())
                {
                    objUnPostedProductionDetails.setJobName(rs_UnPostedJbDet_Get.getString("JB_NAME"));
                    objUnPostedProductionDetails.setJobDrwgNo(rs_UnPostedJbDet_Get.getString("JB_DWG_NO"));
                    objUnPostedProductionDetails.setJobRvsnNo(rs_UnPostedJbDet_Get.getString("JB_RVSN_NO"));
                    objUnPostedProductionDetails.setJobMatlType(rs_UnPostedJbDet_Get.getString("JB_MATL_TYP"));
                    objUnPostedProductionDetails.setWoNo(rs_UnPostedJbDet_Get.getString("WO_NO"));
                    
                }
                rs_UnPostedJbDet_Get.close();
                ps_UnPostedJbDet_Get.close();
                vec_Result.addElement(objUnPostedProductionDetails);
            }
            rs_UnpostedProdId_Get.close();
            ps_UnpostedProdId_Get.close();
            
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }
            throw new ProductionException("PMEC000","GENERAL SQL ERROR",sqle.toString());
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
        
        
        logger.info("GET UNPOSTED SHIPMENT DETAILS ENDS");
        
        
        return vec_Result;
    }
    public HashMap postShipmentDetails(Vector vec_ProdIds) throws ProductionException, SQLException
    {
        
        int shipmentId = 0;
        Hashtable ht_PostProdn = new Hashtable();
        Hashtable ht_woJbStatId_Get = new Hashtable();
        Hashtable ht_OpnsStatus_Change = new Hashtable();
        Hashtable ht_Status_Change = new Hashtable();
        HashMap hm_Result = new HashMap();
        DBConnection con = null;
        int woJbStatId = 0;
        
        String qtySnos = "";
        int startOpn = 0;
        int endOpn = 0;
        int woJbId = 0;
        int woId = 0;
        
        
        logger.info("POST SHIPMENT DETAILS STARTS");
        
        if(vec_ProdIds == null)
        {
            throw new ProductionException("PMEC005","SHIPMENT IDS VECTOR OBJECT IS NULL","");
        }
        try
        {
            con = DBConnectionFactory.getConnection();
            con.startTransaction();
            for(int i = 0;i<vec_ProdIds.size();i++)
            {
                shipmentId = ((Integer)vec_ProdIds.elementAt(i)).intValue();
                ht_PostProdn.put("SHIPMENT_ID",new Integer(shipmentId));
                int result = con.executeUpdateStatement(SQLMaster.SHIPMENT_DETAILS_POST_SQL_QUERY,ht_PostProdn);
                if(result>=1)
                {
                    hm_Result.put(vec_ProdIds.get(i),new Integer(0));
                    logger.info(vec_ProdIds.get(i)+" : SHIPMENT RECORD POSTED");
                    
                    PreparedStatement ps_ProdnDet = con.executeStatement(SQLMaster.SHIPMENT_DETAILS_SELECT_SQL_QUERY,ht_PostProdn);
                    ResultSet rs_ProdnDet = ps_ProdnDet.executeQuery();
                    if(rs_ProdnDet.next())
                    {
                        woJbId = rs_ProdnDet.getInt("WOJB_ID");
                        qtySnos = rs_ProdnDet.getString("SHIPMENT_QTY_SNOS");
                        //totQty = rs_ProdnDet.getInt("PROD_TOT_QTY");
                        startOpn = rs_ProdnDet.getInt("SHIPMENT_START_OPN");
                        endOpn = rs_ProdnDet.getInt("SHIPMENT_END_OPN");
                        //production may be for more than one job quantity...so loop comes
                        StringTokenizer stn = new StringTokenizer(qtySnos.trim(),",");
                        
                        while(stn.hasMoreTokens())
                        {
                            int j = Integer.parseInt(stn.nextToken().trim());
                            ht_woJbStatId_Get.put("WOJB_ID",new Integer(woJbId));
                            ht_woJbStatId_Get.put("WOJBSTAT_SNO",new Integer(j));
                            
                            PreparedStatement ps_woJbStatId = con.executeStatement(SQLMaster.PRODN_WOJBSTATID_GET_SQL_QUERY,ht_woJbStatId_Get);
                            ResultSet rs_woJbStatId = ps_woJbStatId.executeQuery();
                            if(rs_woJbStatId.next())
                            {
                                woJbStatId = rs_woJbStatId.getInt("WOJBSTAT_ID");
                            }
                            rs_woJbStatId.close();
                            ps_woJbStatId.close();
                            for(int k =startOpn;k<=endOpn;k++)
                            {
                                ht_OpnsStatus_Change.put("WOJBSTAT_ID",new Integer(woJbStatId));
                                ht_OpnsStatus_Change.put("WOJBOPN_OPN_SNO",new Integer(k));
                                
                                result = con.executeUpdateStatement(SQLMaster.PRODN_OPN_STATUS_CHANGE_SQL_QUERY,ht_OpnsStatus_Change);
                            }
                            
                            //status checking for remaining operations of particular quantity//
                            PreparedStatement ps_OpnsStatus_Check = con.executeStatement(SQLMaster.PRODN_OPN_STATUS_CHECK_SQL_QUERY,ht_OpnsStatus_Change);
                            ResultSet rs_OpnsStatus_Check = ps_OpnsStatus_Check.executeQuery();
                            
                            //if some value comes,all the operations for the particular quantity is not completed.
                            if(rs_OpnsStatus_Check.next())
                            {
                                
                                //to get woJbId and  woId for that particular quantity
                                PreparedStatement ps_Ids_Get = con.executeStatement(SQLMaster.PRODN_WOJBID_WOID_SELECT_SQL_QUERY,ht_OpnsStatus_Change);
                                ResultSet rs_Ids_Get = ps_Ids_Get.executeQuery();
                                
                                if(rs_Ids_Get.next())
                                {
                                    woJbId = rs_Ids_Get.getInt("WOJB_ID");
                                    woId = rs_Ids_Get.getInt("WO_ID");
                                }
                                ht_Status_Change.put("WOJBSTAT_ID",new Integer(woJbStatId));
                                ht_Status_Change.put("WOJB_ID",new Integer(woJbId));
                                ht_Status_Change.put("WO_ID",new Integer(woId));
                                
                                //to make particular quantity sno status to 'A'
                                result = con.executeUpdateStatement(SQLMaster.PRODN_WOJBSTATSNO_STATUS_CHANGETOA_SQL_QUERY,ht_Status_Change);
                                
                                //to make particular job status to 'A'
                                result = con.executeUpdateStatement(SQLMaster.PRODN_WOJB_STATUS_CHANGETOA_SQL_QUERY,ht_Status_Change);
                                
                                //to make particular work order status to 'A'
                                result = con.executeUpdateStatement(SQLMaster.PRODN_WO_STATUS_CHANGETOA_SQL_QUERY,ht_Status_Change);
                            }
                            //all the operations for the particular quantity are completed..so we have to check another quantities of that job..
                            else
                            {
                                //to get woJbId and  woId for that particular quantity
                                PreparedStatement ps_Ids_Get = con.executeStatement(SQLMaster.PRODN_WOJBID_WOID_SELECT_SQL_QUERY,ht_OpnsStatus_Change);
                                ResultSet rs_Ids_Get = ps_Ids_Get.executeQuery();
                                if(rs_Ids_Get.next())
                                {
                                    woJbId = rs_Ids_Get.getInt("WOJB_ID");
                                    woId = rs_Ids_Get.getInt("WO_ID");
                                }
                                ht_Status_Change.put("WOJBSTAT_ID",new Integer(woJbStatId));
                                ht_Status_Change.put("WOJB_ID",new Integer(woJbId));
                                ht_Status_Change.put("WO_ID",new Integer(woId));
                                
                                //to make particular quantity sno status to 'C'
                                result = con.executeUpdateStatement(SQLMaster.PRODN_WOJBSTATSNO_STATUS_CHANGETOC_SQL_QUERY,ht_Status_Change);
                                
                                //status checking for remaining job quantities of particular job//
                                
                                PreparedStatement ps_WoJobQtysStatus_Check = con.executeStatement(SQLMaster.PRODN_WOJBSTATSNO_STATUS_CHECK_SQL_QUERY,ht_Status_Change);
                                ResultSet rs_WoJobQtysStatus_Check = ps_WoJobQtysStatus_Check.executeQuery();
                                
                                if(rs_WoJobQtysStatus_Check.next())
                                {
                                    //to make particular job status to 'A'
                                    result = con.executeUpdateStatement(SQLMaster.PRODN_WOJB_STATUS_CHANGETOA_SQL_QUERY,ht_Status_Change);
                                    
                                    //to make particular work order status to 'A'
                                    result = con.executeUpdateStatement(SQLMaster.PRODN_WO_STATUS_CHANGETOA_SQL_QUERY,ht_Status_Change);
                                    
                                }
                                else
                                {
                                    //to make particular job status to 'C'
                                    result = con.executeUpdateStatement(SQLMaster.PRODN_WOJB_STATUS_CHANGETOC_SQL_QUERY,ht_Status_Change);
                                    
                                    //status checking for remaining jobs of particular work order//
                                    PreparedStatement ps_WoJobStatus_Check = con.executeStatement(SQLMaster.PRODN_JOB_STATUS_CHECK_SQL_QUERY,ht_Status_Change);
                                    ResultSet rs_WoJobStatus_Check = ps_WoJobStatus_Check.executeQuery();
                                    if(rs_WoJobStatus_Check.next())
                                    {
                                        //to make particular work order status to 'A'
                                        result = con.executeUpdateStatement(SQLMaster.PRODN_WO_STATUS_CHANGETOA_SQL_QUERY,ht_Status_Change);
                                    }
                                    else
                                    {
                                        //to make particular work order status to 'C'
                                        result = con.executeUpdateStatement(SQLMaster.PRODN_WO_STATUS_CHANGETOC_SQL_QUERY,ht_Status_Change);
                                    }
                                    rs_WoJobStatus_Check.close();
                                    ps_WoJobStatus_Check.close();
                                }
                                rs_WoJobQtysStatus_Check.close();
                                ps_WoJobQtysStatus_Check.close();
                                
                            }
                            rs_OpnsStatus_Check.close();
                            ps_OpnsStatus_Check.close();
                        }
                        
                        
                    }
                    rs_ProdnDet.close();
                    ps_ProdnDet.close();
                    
                    con.commitTransaction();
                }
                else
                {
                    con.rollBackTransaction();
                    hm_Result.put(vec_ProdIds.get(i),new Integer(1));
                    logger.info(vec_ProdIds.get(i)+" : PRODUCTION RECORD NOT POSTED");
                }
            }
        }
        catch (SQLException e)
        {
            con.rollBackTransaction();
            if(BuildConfig.DMODE)
            {
                e.printStackTrace();
            }
            throw new ProductionException("PMEC000","GENERAL SQL ERROR",e.toString());
            
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
        
        logger.info("POST SHIPMENT DETAILS ENDS");
        
        return hm_Result;
        
    }
    
    public Vector getShipmentOperationDetails(int woJbId) throws ProductionException,SQLException
    {
        Hashtable ht_ProdnJbOpn_Get = new Hashtable();
        Hashtable ht_JbStatSno_Get = new Hashtable();
        Hashtable ht_JbPstdStatSno_Get = new Hashtable();
        ResultSet rs_ProdnJbOpn_Get = null;
        Vector vec_Result = new Vector();
        DBConnection con=null;
        SortString ss = new SortString();
        
        logger.info("GET SHIPMENT JOB OPERATION DETAILS STARTS");
        
        con = DBConnectionFactory.getConnection();
        ht_ProdnJbOpn_Get.put("WOJB_ID",new Integer(woJbId));
        
        try
        {
            PreparedStatement ps_ProdnJbOpn_Get = con.executeStatement(SQLMaster.GET_PRODUCTION_JOBSTATSNO_SQL_QUERY,ht_ProdnJbOpn_Get);
            rs_ProdnJbOpn_Get = ps_ProdnJbOpn_Get.executeQuery();
            PreparedStatement ps_Prod_Det = null;
            ResultSet rs_Prod_Det = null;
            while(rs_ProdnJbOpn_Get.next())
            {
                ProductionJobQtyDetails objProductionJobQtyDetails = new ProductionJobQtyDetails();
                objProductionJobQtyDetails.setJobStatId(rs_ProdnJbOpn_Get.getInt("WOJBSTAT_ID"));
                objProductionJobQtyDetails.setJobQtySno(rs_ProdnJbOpn_Get.getInt("WOJBSTAT_SNO"));
                int jbQtySno = objProductionJobQtyDetails.getJobQtySno();
                
                ht_JbStatSno_Get.put("WOJBSTAT_ID",new Integer(objProductionJobQtyDetails.getJobStatId()));
                
                ps_Prod_Det = con.executeStatement(SQLMaster.GET_SHIPMENT_CLEARANCE_DETAILS_SQL_QUERY,ht_ProdnJbOpn_Get);
                rs_Prod_Det = ps_Prod_Det.executeQuery();
                String totOpns = "";
                while(rs_Prod_Det.next())
                {
                    String qtySnos = "";
                    qtySnos = rs_Prod_Det.getString("SHIPMENT_QTY_SNOS");
                    StringTokenizer st = new StringTokenizer(qtySnos.trim(),",");
                    while(st.hasMoreTokens())
                    {
                        int sno = Integer.parseInt(st.nextToken().trim());
                        if(sno == jbQtySno)
                        {
                            int stOpn = rs_Prod_Det.getInt("SHIPMENT_START_OPN");
                            int endOpn = rs_Prod_Det.getInt("SHIPMENT_END_OPN");
                            for(int j = stOpn ;j<=endOpn ; j++)
                            {
                                if(totOpns.equals(""))
                                {
                                    totOpns = j+"";
                                }
                                else
                                {
                                    totOpns = totOpns +","+j;
                                }
                            }
                        }
                        
                    }
                }
                if(BuildConfig.DMODE)
                    System.out.println("totOpns"+totOpns);
                totOpns = ss.sortString(totOpns);
                PreparedStatement ps_PendStat_OpnSnos = con.executeStatement(SQLMaster.GET_PENDING_SHIPMENT_OPERATION_SNOS_SQL_QUERY,ht_JbStatSno_Get);
                ResultSet rs_PendStat_OpnSnos = ps_PendStat_OpnSnos.executeQuery();
                String pndOpn_Snos = "";
                String UnpostedOpn_Snos = "";
                
                
                while(rs_PendStat_OpnSnos.next())
                {
                    
                    int woJbOpnSno = rs_PendStat_OpnSnos.getInt("WOJBOPN_OPN_SNO");
                    int fl = 0;
                    StringTokenizer st = new StringTokenizer(totOpns,",");
                    while(st.hasMoreTokens())
                    {
                        
                        if(woJbOpnSno==Integer.parseInt(st.nextToken().trim()))
                        {
                            fl = 1;
                            if(UnpostedOpn_Snos.equals(""))
                            {
                                UnpostedOpn_Snos = woJbOpnSno+"";
                            }
                            else
                            {
                                UnpostedOpn_Snos = UnpostedOpn_Snos + ","+woJbOpnSno;
                            }
                        }
                    }
                    if(fl==0)
                    {
                        if(pndOpn_Snos.equals(""))
                        {
                            pndOpn_Snos = woJbOpnSno+"";
                            
                        }
                        else
                        {
                            pndOpn_Snos = pndOpn_Snos + ","+woJbOpnSno;
                        }
                    }
                    
                }
                
                objProductionJobQtyDetails.setPendingOpnSnos(ss.sortString(pndOpn_Snos));
                objProductionJobQtyDetails.setUnPostedOpnSnos(ss.sortString(UnpostedOpn_Snos));
                
                ht_JbPstdStatSno_Get.put("WOJBSTAT_ID",new Integer(objProductionJobQtyDetails.getJobStatId()));
                
                PreparedStatement ps_PostedStat_OpnSnos = con.executeStatement(SQLMaster.GET_POSTED_SHIPMENT_OPERATION_SNOS_SQL_QUERY,ht_JbPstdStatSno_Get);
                ResultSet rs_PostedStat_OpnSnos = ps_PostedStat_OpnSnos.executeQuery();
                
                String postedOpn_Snos = "";
                int i1 =0;
                
                while(rs_PostedStat_OpnSnos.next())
                {
                    if(i1==0)
                    {
                        postedOpn_Snos = rs_PostedStat_OpnSnos.getInt("WOJBOPN_OPN_SNO")+"";
                        i1++;
                    }
                    else
                    {
                        postedOpn_Snos = postedOpn_Snos + ","+rs_PostedStat_OpnSnos.getInt("WOJBOPN_OPN_SNO");
                    }
                }
                rs_PostedStat_OpnSnos.close();
                ps_PostedStat_OpnSnos.close();
                objProductionJobQtyDetails.setPostedOpnSnos(ss.sortString(postedOpn_Snos));
                
                /* //to get Last Production Date for a particular quantity...
                 Date lastProdDate = null;
                 Date tempDate = new Date();
                 
                 PreparedStatement ps_GetProdDet = con.executeStatement(SQLMaster.GET_LAST_PRODN_DATE_SQL_QUERY,ht_ProdnJbOpn_Get);
                 ResultSet rs_GetProdDet = ps_GetProdDet.executeQuery();
                 
                 while(rs_GetProdDet.next())
                 {
                 String prodQtySnos = rs_GetProdDet.getString("PROD_QTY_SNOS");
                 StringTokenizer st = new StringTokenizer(prodQtySnos,",");
                 while(st.hasMoreTokens())
                 {
                 int prodQtySno = Integer.parseInt(st.nextToken().trim());
                 if(prodQtySno==jbQtySno)
                 {
                 tempDate = rs_GetProdDet.getTimestamp("PROD_CRNT_DATE");
                 
                 if(lastProdDate == null)
                 {
                 lastProdDate = tempDate;
                 
                 }
                 else
                 {
                 if(tempDate.after(lastProdDate))
                 lastProdDate = tempDate;
                 }
                 break;
                 }
                 }
                 }
                 rs_GetProdDet.close();
                 ps_GetProdDet.close();
                 
                 if(BuildConfig.DMODE)
                 System.out.println("lpdate :"+lastProdDate);
                 objProductionJobQtyDetails.setLastProdDate(lastProdDate);
                 */
                //if pending Opn Snos is " " , no need to set into Vector..
                if(!objProductionJobQtyDetails.getPendingOpnSnos().equals(""))
                    vec_Result.addElement(objProductionJobQtyDetails);
                
                
            }
            rs_ProdnJbOpn_Get.close();
            ps_ProdnJbOpn_Get.close();
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }
            throw new ProductionException("PMEC000","GENERAL SQL ERROR",sqle.toString());
        }
        if(BuildConfig.DMODE)
        {
            logger.info("GET SHIPMENT JOB OPERATION DETAILS ENDS");
        }
        return vec_Result;
    }
    
    public Vector getShipmentOperationDetailsForUpdate(int woJbId,int shipmentId) throws ProductionException,SQLException
    {
        Hashtable ht_ProdnJbOpn_Get = new Hashtable();
        Hashtable ht_JbStatSno_Get = new Hashtable();
        Hashtable ht_JbPstdStatSno_Get = new Hashtable();
        ResultSet rs_ProdnJbOpn_Get = null;
        Vector vec_Result = new Vector();
        DBConnection con=null;
        SortString ss = new SortString();
        
        logger.info("GET SHIPMENT JOB OPERATION DETAILS STARTS");
        
        con = DBConnectionFactory.getConnection();
        ht_ProdnJbOpn_Get.put("WOJB_ID",new Integer(woJbId));
        ht_ProdnJbOpn_Get.put("SHIPMENT_ID",new Integer(shipmentId));
        
        try
        {
            PreparedStatement ps_ProdnJbOpn_Get = con.executeStatement(SQLMaster.GET_PRODUCTION_JOBSTATSNO_SQL_QUERY,ht_ProdnJbOpn_Get);
            rs_ProdnJbOpn_Get = ps_ProdnJbOpn_Get.executeQuery();
            ResultSet rs_Prod_Det = null;
            PreparedStatement ps_Prod_Det = null;
            while(rs_ProdnJbOpn_Get.next())
            {
                ProductionJobQtyDetails objProductionJobQtyDetails = new ProductionJobQtyDetails();
                objProductionJobQtyDetails.setJobStatId(rs_ProdnJbOpn_Get.getInt("WOJBSTAT_ID"));
                objProductionJobQtyDetails.setJobQtySno(rs_ProdnJbOpn_Get.getInt("WOJBSTAT_SNO"));
                int jbQtySno = objProductionJobQtyDetails.getJobQtySno();
                
                ht_JbStatSno_Get.put("WOJBSTAT_ID",new Integer(objProductionJobQtyDetails.getJobStatId()));
                
                ps_Prod_Det = con.executeStatement(SQLMaster.GET_SHIPMENT_DETAILS_FOR_UPDATE_SQL_QUERY,ht_ProdnJbOpn_Get);
                rs_Prod_Det = ps_Prod_Det.executeQuery();
                String totOpns = "";
                while(rs_Prod_Det.next())
                {
                    String qtySnos = "";
                    qtySnos = rs_Prod_Det.getString("SHIPMENT_QTY_SNOS");
                    StringTokenizer st = new StringTokenizer(qtySnos.trim(),",");
                    while(st.hasMoreTokens())
                    {
                        int sno = Integer.parseInt(st.nextToken().trim());
                        if(sno == jbQtySno)
                        {
                            int stOpn = rs_Prod_Det.getInt("SHIPMENT_START_OPN");
                            int endOpn = rs_Prod_Det.getInt("SHIPMENT_END_OPN");
                            for(int j = stOpn ;j<=endOpn ; j++)
                            {
                                if(totOpns.equals(""))
                                {
                                    totOpns = j+"";
                                }
                                else
                                {
                                    totOpns = totOpns +","+j;
                                }
                            }
                        }
                        
                    }
                }
                if(BuildConfig.DMODE)
                    System.out.println("totOpns"+totOpns);
                totOpns = ss.sortString(totOpns);
                PreparedStatement ps_PendStat_OpnSnos = con.executeStatement(SQLMaster.GET_PENDING_SHIPMENT_OPERATION_SNOS_SQL_QUERY,ht_JbStatSno_Get);
                ResultSet rs_PendStat_OpnSnos = ps_PendStat_OpnSnos.executeQuery();
                String pndOpn_Snos = "";
                String UnpostedOpn_Snos = "";
                
                
                while(rs_PendStat_OpnSnos.next())
                {
                    
                    int woJbOpnSno = rs_PendStat_OpnSnos.getInt("WOJBOPN_OPN_SNO");
                    int fl = 0;
                    StringTokenizer st = new StringTokenizer(totOpns,",");
                    while(st.hasMoreTokens())
                    {
                        
                        if(woJbOpnSno==Integer.parseInt(st.nextToken().trim()))
                        {
                            fl = 1;
                            if(UnpostedOpn_Snos.equals(""))
                            {
                                UnpostedOpn_Snos = woJbOpnSno+"";
                                
                            }
                            else
                            {
                                UnpostedOpn_Snos = UnpostedOpn_Snos + ","+woJbOpnSno;
                            }
                        }
                    }
                    if(fl==0)
                    {
                        if(pndOpn_Snos.equals(""))
                        {
                            pndOpn_Snos = woJbOpnSno+"";
                            
                        }
                        else
                        {
                            pndOpn_Snos = pndOpn_Snos + ","+woJbOpnSno;
                        }
                    }
                    
                }
                
                
                
                
                objProductionJobQtyDetails.setPendingOpnSnos(ss.sortString(pndOpn_Snos));
                objProductionJobQtyDetails.setUnPostedOpnSnos(ss.sortString(UnpostedOpn_Snos));
                
                ht_JbPstdStatSno_Get.put("WOJBSTAT_ID",new Integer(objProductionJobQtyDetails.getJobStatId()));
                PreparedStatement ps_PostedStat_OpnSnos = con.executeStatement(SQLMaster.GET_POSTED_SHIPMENT_OPERATION_SNOS_SQL_QUERY,ht_JbPstdStatSno_Get);
                ResultSet rs_PostedStat_OpnSnos = ps_PostedStat_OpnSnos.executeQuery();
                
                String postedOpn_Snos = "";
                int i1 =0;
                
                while(rs_PostedStat_OpnSnos.next())
                {
                    if(i1==0)
                    {
                        postedOpn_Snos = rs_PostedStat_OpnSnos.getInt("WOJBOPN_OPN_SNO")+"";
                        i1++;
                    }
                    else
                    {
                        postedOpn_Snos = postedOpn_Snos + ","+rs_PostedStat_OpnSnos.getInt("WOJBOPN_OPN_SNO");
                    }
                }
                
                objProductionJobQtyDetails.setPostedOpnSnos(ss.sortString(postedOpn_Snos));
                
                /* //to get Last Production Date for a particular quantity...
                 Date lastProdDate = null;
                 Date tempDate = new Date();
                 PreparedStatement ps_GetProdDet = con.executeStatement(SQLMaster.GET_LAST_PRODN_DATE_FOR_UPDATE_SQL_QUERY,ht_ProdnJbOpn_Get);
                 ResultSet rs_GetProdDet = ps_GetProdDet.executeQuery();
                 
                 while(rs_GetProdDet.next())
                 {
                 String prodQtySnos = rs_GetProdDet.getString("PROD_QTY_SNOS");
                 StringTokenizer st = new StringTokenizer(prodQtySnos,",");
                 while(st.hasMoreTokens())
                 {
                 
                 int prodQtySno = Integer.parseInt(st.nextToken().trim());
                 if(prodQtySno==jbQtySno)
                 {
                 tempDate = rs_GetProdDet.getTimestamp("PROD_CRNT_DATE");
                 
                 if(lastProdDate == null)
                 {
                 lastProdDate = tempDate;
                 
                 }
                 else
                 {
                 if(tempDate.after(lastProdDate))
                 lastProdDate = tempDate;
                 }
                 break;
                 }
                 }
                 }
                 
                 
                 
                 objProductionJobQtyDetails.setLastProdDate(lastProdDate);
                 */
                //if pending Opn Snos is " " , no need to set into Vector..
                if(!objProductionJobQtyDetails.getPendingOpnSnos().equals(""))
                    vec_Result.addElement(objProductionJobQtyDetails);
                
            }
            rs_ProdnJbOpn_Get.close();
            ps_ProdnJbOpn_Get.close();
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }
            throw new ProductionException("PMEC000","GENERAL SQL ERROR",sqle.toString());
        }
        
        logger.info("GET SHIPMENT JOB OPERATION DETAILS ENDS");
        
        return vec_Result;
    }
    
    public boolean isModifyForDespatch(int shipmentId) throws ProductionException, SQLException
    {
        boolean retVal = false;
        DBConnection con = null;
        Hashtable ht_Prod = new Hashtable();
        PreparedStatement ps = null;
        int cnt = 0;
        try
        {
            con = DBConnectionFactory.getConnection();
            ht_Prod.put("SHIPMENT_ID",new Integer(shipmentId));
            ps = con.executeStatement(SQLMaster.CHECK_SHIPMENT_FOR_MODIFY_SQL_QUERY,ht_Prod);
            ResultSet rs_Prod = ps.executeQuery();
            if(rs_Prod.next())
            {
                cnt = rs_Prod.getInt(1);
                if(cnt == 0)
                {
                    throw new ProductionException("","POSTED ENTRY CANNOT BE MODIFIED","");
                }
                else
                {
                    retVal = true;
                }
            }
            rs_Prod.close();
            ps.close();
            
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }
            throw new ProductionException("NPMEC000","GENERAL SQL ERROR",sqle.toString());
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
            catch(SQLException ex)
            {
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }
        
        return retVal;
    }
    public HashMap makeShipmentValid(Vector shipmentIds) throws ProductionException, SQLException
    {
        int result = 0;
        DBConnection con = null;
        HashMap hm_Result = new HashMap();
        
        logger.info("MAKE SHIPMENT VALID STARTS");
        
        if(shipmentIds == null)
        {
            logger.error("SHIPMENT VECTOR OBJECT IS NULL");
            throw new ProductionException("PREC00","SHIPMENT VECTOR OBJECT IS NULL","");
        }
        try
        {
            con = DBConnectionFactory.getConnection();
            con.startTransaction();
            for(int i=0;i<shipmentIds.size();i++)
            {
                Hashtable ht_Prod_Id = new Hashtable();
                
                
                ht_Prod_Id.put("SHIPMENT_ID",(Integer)shipmentIds.get(i));
                
                result = con.executeUpdateStatement(SQLMaster.SHIPMENT_MAKE_VALID_SQL_QUERY,ht_Prod_Id);
                
                if(result>=1)
                {
                    con.commitTransaction();
                    hm_Result.put(shipmentIds.get(i),new Integer(0));
                    logger.info(shipmentIds.get(i)+" : SHIPMENT RECORD VALIDATED");
                    
                }
                else
                {
                    con.rollBackTransaction();
                    hm_Result.put(shipmentIds.get(i),new Integer(1));
                    logger.info(shipmentIds.get(i)+" : SHIPMENT RECORD NOT VALIDATED");
                    
                }
                
                
            }//for loop end
            
        }//first try end
        
        catch(SQLException e)
        {
            if(BuildConfig.DMODE)
            {
                e.printStackTrace();
                System.out.println("GENERAL EXCEPTION");
            }
            logger.error("GENERAL ERROR",e);
            throw new ProductionException("PREC000","GENERAL SQL ERROR",e.toString());
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
            catch(SQLException ex)
            {
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }
        
        logger.info("MAKE SHIPMENT VALID ENDS");
        
        return hm_Result;
        
    }
    public HashMap makeShipmentInValid(Vector shipmentIds) throws ProductionException, SQLException
    {
        int result = 0;
        DBConnection con = null;
        HashMap hm_Result = new HashMap();
        
        logger.info("MAKE SHIPMENT INVALID STARTS");
        
        if(shipmentIds == null)
        {
            logger.error("SHIPMENT VECTOR OBJECT IS NULL");
            throw new ProductionException("PREC00","SHIPMENT VECTOR OBJECT IS NULL","");
        }
        try
        {
            con = DBConnectionFactory.getConnection();
            con.startTransaction();
            for(int i=0;i<shipmentIds.size();i++)
            {
                Hashtable ht_Prod_Id = new Hashtable();
                
                
                ht_Prod_Id.put("SHIPMENT_ID",(Integer)shipmentIds.get(i));
                
                result = con.executeUpdateStatement(SQLMaster.SHIPMENT_MAKE_INVALID_SQL_QUERY,ht_Prod_Id);
                
                if(result>=1)
                {
                    con.commitTransaction();
                    hm_Result.put(shipmentIds.get(i),new Integer(0));
                    logger.info(shipmentIds.get(i)+" : SHIPMENT RECORD INVALIDATED");
                    
                }
                else
                {
                    con.rollBackTransaction();
                    hm_Result.put(shipmentIds.get(i),new Integer(1));
                    logger.info(shipmentIds.get(i)+" : SHIPMENT RECORD NOT INVALIDATED");
                    
                }
                
                
            }//for loop end
            
        }//first try end
        
        catch(SQLException e)
        {
            if(BuildConfig.DMODE)
            {
                e.printStackTrace();
                System.out.println("GENERAL EXCEPTION");
            }
            logger.error("GENERAL ERROR",e);
            throw new ProductionException("PREC000","GENERAL SQL ERROR",e.toString());
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
            catch(SQLException ex)
            {
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }
        
        logger.info("MAKE SHIPMENT INVALID ENDS");
        
        return hm_Result;
        
    }
    
    
    public HashMap getAllJobStatusDetails(int custId,Date fromDate,Date toDate,Filter filters[],String sortBy,boolean ascending,int startIndex,int displayCount) throws ProductionException, SQLException
    {
        HashMap hm_Result = new HashMap();
        DBConnection con = null;
        Hashtable ht = new Hashtable();
        int tot_Rec_Cnt = 0;
        String jbName =  "";
        String jbDwgNo = "";
        int jbId = 0;
        int jbQty = 0;
        int desPatchQty = 0;
        int balanceForward = 0;
        int readyToDespatch = 0;
        int notTakenUp = 0;
        int workInProcess = 0;
        logger.info("GET ALL JOB STATUS DETAILS FILTER STARTS");
        
        if((filters == null)||( sortBy == null))
        {
            logger.error("FILTER VALUES ARE NULL");
            
            throw new ProductionException("PMEC006","FILTER VALUES ARE NULL","");
        }
        
        
        con = DBConnectionFactory.getConnection();
        
        ht.put("FROM_DATE",fromDate);
        ht.put("TO_DATE",toDate);
        ht.put("CUST_ID",new Integer(custId));
        //Finding end index for the query
        
        //filters and tableName are passed to the function and receives Total Record Count
        tot_Rec_Cnt = con.getRowCountWithComplexFiltersWithHashtable(filters, SQLMaster.GET_JOB_STATUS_DETAILS_FOR_CUSTOMER_SQL_QUERY,ht);
        
        if(BuildConfig.DMODE)
        System.out.println("TotRecCount:"+tot_Rec_Cnt);
        int eIndex = startIndex + displayCount;
        
        //filters,tablename,startindex,endindex,sorting column name and sortorder are passed to the function and  query will be received
        String Query = con.buildDynamicQuery(filters,startIndex,eIndex,sortBy,ascending,SQLMaster.GET_JOB_STATUS_DETAILS_FOR_CUSTOMER_SQL_QUERY);
        
        PreparedStatement ps = con.executeStatement(Query,ht);
        ResultSet rs = ps.executeQuery();
        Vector vec_Result = new Vector();
        while(rs.next())
        {
            jbId = rs.getInt("JB_ID");
            jbName = rs.getString("JB_NAME");
            jbDwgNo = rs.getString("JB_DWG_NO");
            if(BuildConfig.DMODE)
            {
                System.out.println("JB-NAME:"+jbName);
                System.out.println("JB-ID:"+jbId);
                System.out.println("JB-DWGNO:"+jbDwgNo);
            }
            
            CustomerViewJobDetails  objCustomerViewJobDetails = new CustomerViewJobDetails();
            objCustomerViewJobDetails.setJobName(jbName);
            objCustomerViewJobDetails.setDwgNo(jbDwgNo);
            
            //to fetch JobReceipt details..this includes Order details and balance forward Details..
            JobReceiptDetails  objJobReceiptDetails = new JobReceiptDetails();
            
            //to fetch all the work order details..
            HashMap hm = new HashMap();
            Vector vecOrderDet = new Vector();
            hm = this.fetchOrderDetails(custId,jbId,fromDate,toDate,con);
            
            vecOrderDet = (Vector)hm.get("ORDER_DETAILS");
            objJobReceiptDetails.setVecOrderDetails(vecOrderDet);
            
            jbQty = ((Integer)hm.get("JB_QTY")).intValue();
            
            //to fetch balance forward Details..
            balanceForward = this.fetchBalanceForwardDetails(custId,jbId,fromDate,toDate,con);
            objJobReceiptDetails.setBalanceForward(balanceForward);
            
            objJobReceiptDetails.setTotalQty(jbQty + balanceForward);
            
            objCustomerViewJobDetails.setObjJobReceiptDetails(objJobReceiptDetails);
            
            //to fetch JobStatus details..this includes despatch details and work in process and ready to despatch Details..
            JobStatusDetails objJobStatusDetails = new JobStatusDetails();
            
            
            //to fetch all the despatched details..
            Vector vecDespatchDet = new Vector();
            HashMap hm_Despatch = new HashMap();
            
            hm_Despatch = this.fetchDespatchDetails(custId,jbId,fromDate,toDate,con);
            
            vecDespatchDet = (Vector)hm_Despatch.get("DESPATCH_DETAILS");
            
            desPatchQty = ((Integer)hm_Despatch.get("JB_QTY")).intValue();
            
            objJobStatusDetails.setVecDespatchDetails(vecDespatchDet);
            
            //to fetch not Taken Up..
            notTakenUp = this.fetchNotTakenUp(custId,jbId,con);
            objJobStatusDetails.setNotTakenUp(notTakenUp);
            
            //to fetch Ready to Despatch...
            
            readyToDespatch = this.fetchReadyToDespatchDetails(custId,jbId,con);
            objJobStatusDetails.setReadyToDespatch(readyToDespatch);
            
            //          //to fetch Workin Process...
            workInProcess = this.fetchWorkInProcessDetails(custId,jbId,con)-readyToDespatch;
            objJobStatusDetails.setWorkInProcess(workInProcess);
            
            
            
            objJobStatusDetails.setTotQty(desPatchQty+notTakenUp+workInProcess+readyToDespatch);
            
            objCustomerViewJobDetails.setObjJobStatusDetails(objJobStatusDetails);
            
            vec_Result.add(objCustomerViewJobDetails);
            
        }
        logger.info("GET ALL SHIPMENT DETAILS FILTER ENDS");
        
        hm_Result.put("JobStatusDetails",vec_Result);
        hm_Result.put("TotalRecordCount",new Integer(tot_Rec_Cnt));
        
        return hm_Result;
    }
    public CustomerMailJobDetails[] getAllJobStatusDetails(int custId,Date fromDate,Date toDate) throws ProductionException, SQLException
    {
        DBConnection con = null;
        Hashtable ht = new Hashtable();
        String jbName =  "";
        String jbDwgNo = "";
        int jbId = 0;
        int jbQty = 0;
        int desPatchQty = 0;
        int balanceForward = 0;
        int readyToDespatch = 0;
        int notTakenUp = 0;
        int workInProcess = 0;
        logger.info("GET ALL JOB STATUS DETAILS FILTER STARTS");

        con = DBConnectionFactory.getConnection();
        
        ht.put("FROM_DATE",fromDate);
        ht.put("TO_DATE",toDate);
        ht.put("CUST_ID",new Integer(custId));
        
        PreparedStatement ps = con.executeStatement(SQLMaster.GET_JOB_STATUS_DETAILS_FOR_SENDING_MAIL_TO_CUSTOMER_SQL_QUERY,ht);
        ResultSet rs = ps.executeQuery();
        Vector vec_Result = new Vector();
        
        CustomerMailJobDetails[] objCustomerMailJobDetailsList = null;
        
        while(rs.next())
        {
            jbId = rs.getInt("JB_ID");
            jbName = rs.getString("JB_NAME");
            jbDwgNo = rs.getString("JB_DWG_NO");
            if(BuildConfig.DMODE)
            {
                System.out.println("JB-NAME:"+jbName);
                System.out.println("JB-ID:"+jbId);
                System.out.println("JB-DWGNO:"+jbDwgNo);
            }
            
            CustomerMailJobDetails  objCustomerMailJobDetails = new CustomerMailJobDetails();
            objCustomerMailJobDetails.setJobName(jbName);
            objCustomerMailJobDetails.setDwgNo(jbDwgNo);
            
            //to fetch JobReceipt details..this includes Order details and balance forward Details..
            
            
            //to fetch all the work order details..
            HashMap hm = new HashMap();
            Vector vecOrderDet = new Vector();
            hm = this.fetchOrderDetails(custId,jbId,fromDate,toDate,con);
            
            vecOrderDet = (Vector)hm.get("ORDER_DETAILS");
            
                       
            jbQty = ((Integer)hm.get("JB_QTY")).intValue();
            
            //to fetch balance forward Details..
            balanceForward = this.fetchBalanceForwardDetails(custId,jbId,fromDate,toDate,con);
            objCustomerMailJobDetails.setBalanceForward(balanceForward);
            
            objCustomerMailJobDetails.setTotalQty(jbQty + balanceForward);
            
            objCustomerMailJobDetails.setVecCustomerOrderDetails(vecOrderDet);
            
            
            
            //to fetch all the despatched details..
            Vector vecDespatchDet = new Vector();
            HashMap hm_Despatch = new HashMap();
            
            hm_Despatch = this.fetchDespatchDetails(custId,jbId,fromDate,toDate,con);
            
            vecDespatchDet = (Vector)hm_Despatch.get("DESPATCH_DETAILS");
            
            desPatchQty = ((Integer)hm_Despatch.get("JB_QTY")).intValue();
            
            
            //to fetch not Taken Up..
            notTakenUp = this.fetchNotTakenUp(custId,jbId,con);
            objCustomerMailJobDetails.setNotTakenUp(notTakenUp);
            
            //to fetch Ready to Despatch...
            
            readyToDespatch = this.fetchReadyToDespatchDetails(custId,jbId,con);
            objCustomerMailJobDetails.setReadyToDespatch(readyToDespatch);
            
            //          //to fetch Workin Process...
            workInProcess = this.fetchWorkInProcessDetails(custId,jbId,con)-readyToDespatch;
            objCustomerMailJobDetails.setWorkInProcess(workInProcess);
            
            
            
            objCustomerMailJobDetails.setTotStatusQty(desPatchQty+notTakenUp+workInProcess+readyToDespatch);
            
            objCustomerMailJobDetails.setVecDespatchDetails(vecDespatchDet);
            
            vec_Result.add(objCustomerMailJobDetails);
            
        }
        logger.info("GET ALL SHIPMENT DETAILS FILTER ENDS");
        
        objCustomerMailJobDetailsList = new CustomerMailJobDetails[vec_Result.size()];
        vec_Result.copyInto(objCustomerMailJobDetailsList);
       
        return objCustomerMailJobDetailsList;
    }
    
    private HashMap fetchOrderDetails(int custId,int jbId,Date fromDate,Date toDate,DBConnection con) throws SQLException
    {
        
        Vector vec = new Vector();
        HashMap hm = new HashMap();
        Hashtable ht = new Hashtable();
        int jbQty = 0;
        int jbQtyTot = 0;
        ht.put("CUST_ID",new Integer(custId));
        ht.put("JB_ID",new Integer(jbId));
        ht.put("FROM_DATE",fromDate);
        ht.put("TO_DATE",toDate);
        
        PreparedStatement ps = con.executeStatement(SQLMaster.GET_CUSTOMER_ORDER_SQL_QUERY,ht);
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            CustomerOrderDetails objCustomerOrderDetails = new CustomerOrderDetails();
            objCustomerOrderDetails.setWoDate(rs.getTimestamp("WODATE"));
            objCustomerOrderDetails.setCustDCNo(rs.getString("CUSTDCNO"));
            jbQty = rs.getInt("JBQTY");
            objCustomerOrderDetails.setJbQty(jbQty);
            jbQtyTot = jbQtyTot + jbQty;
            vec.add(objCustomerOrderDetails);
        }
        hm.put("JB_QTY",new Integer(jbQtyTot));
        hm.put("ORDER_DETAILS",vec);
        return hm;
    }
    private int fetchBalanceForwardDetails(int custId,int jbId,Date fromDate,Date toDate,DBConnection con) throws SQLException
    {
        
        int balanceForward = 0;
        Hashtable ht = new Hashtable();
        ht.put("CUST_ID",new Integer(custId));
        ht.put("JB_ID",new Integer(jbId));
        ht.put("FROM_DATE",fromDate);
        ht.put("TO_DATE",toDate);
        PreparedStatement ps = con.executeStatement(SQLMaster.SELECT_BALANCE_FORWARD_DETAILS_FOR_CUSTOMER_SQL_QUERY,ht);
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            balanceForward = rs.getInt("JBQTY");
        }
        return balanceForward;
    }
    
    private HashMap fetchDespatchDetails(int custId,int jbId,Date fromDate,Date toDate,DBConnection con) throws SQLException
    {
        
        Vector vec = new Vector();
        HashMap hm = new HashMap();
        Hashtable ht = new Hashtable();
        int jbQty = 0;
        int jbQtyTot = 0;
        ht.put("CUST_ID",new Integer(custId));
        ht.put("JB_ID",new Integer(jbId));
        ht.put("FROM_DATE",fromDate);
        ht.put("TO_DATE",toDate);
        
        
        PreparedStatement ps = con.executeStatement(SQLMaster.DESPATCHED_DETAILS_FOR_CUSTOMER_SELECT_SQL_QUERY,ht);
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            DespatchDetails objDespatchDetails = new DespatchDetails();
            objDespatchDetails.setDespatchDate(rs.getTimestamp("DESPATCHEDDATE"));
            objDespatchDetails.setCompanyDCNo(rs.getString("CUSTDCNO"));
            jbQty = rs.getInt("JBQTY");
            objDespatchDetails.setJbQty(jbQty);
            jbQtyTot = jbQtyTot + jbQty;
            vec.add(objDespatchDetails);
        }
        hm.put("DESPATCH_DETAILS",vec);
        hm.put("JB_QTY",new Integer(jbQtyTot));
        return hm;
    }
    
    private int fetchNotTakenUp(int custId,int jbId,DBConnection con) throws SQLException
    {
        int notTakenUp = 0;
        Hashtable ht = new Hashtable();
        ht.put("CUST_ID",new Integer(custId));
        ht.put("JB_ID",new Integer(jbId));
        
        PreparedStatement ps = con.executeStatement(SQLMaster.FETCH_NOT_TAKEN_UP_FOR_CUSTOMER_SQL_QUERY,ht);
        ResultSet rs = ps.executeQuery();
        if(rs.next())
        {
            notTakenUp = rs.getInt("JBQTY");
        }
        return notTakenUp;
    }
    
    
    private int fetchWorkInProcessDetails(int custId,int jbId,DBConnection con) throws SQLException
    {
        int workInProcess = 0;
        Hashtable ht = new Hashtable();
        ht.put("CUST_ID",new Integer(custId));
        ht.put("JB_ID",new Integer(jbId));
        
        PreparedStatement ps = con.executeStatement(SQLMaster.FETCH_WORK_IN_PROCESS_FOR_CUSTOMER_SQL_QUERY,ht);
        ResultSet rs = ps.executeQuery();
        if(rs.next())
        {
            workInProcess = rs.getInt("JBQTY");
        }
        
        
        
        return workInProcess;
    }
    private int fetchReadyToDespatchDetails(int custId,int jbId,DBConnection con) throws SQLException
    {
        int readyToDespatch = 0;
        Hashtable ht = new Hashtable();
        ht.put("CUST_ID",new Integer(custId));
        ht.put("JB_ID",new Integer(jbId));
        PreparedStatement ps = con.executeStatement(SQLMaster.FETCH_READY_TO_DESPATCH_FOR_CUSTOMER_SQL_QUERY,ht);
        ResultSet rs = ps.executeQuery();
        if(rs.next())
        {
            readyToDespatch = rs.getInt("JBQTY");
        }
        
        /*PreparedStatement psWojbIdSelect = con.executeStatement(SQLMaster.WOJOBID_GET_SQL_QUERY,ht);
         ResultSet rsWojbIdSelect = psWojbIdSelect.executeQuery();
         while(rsWojbIdSelect.next())
         {
         woJbId = rsWojbIdSelect.getInt("WOJB_ID");
         ht.put("WOJB_ID",new Integer(woJbId));	
         PreparedStatement psWoJbStatIdSelect = con.executeStatement(SQLMaster.WOJBSTATID_GET_SQL_QUERY,ht);
         ResultSet rsWoJbStatIdSelect = psWoJbStatIdSelect.executeQuery();
         while(rsWoJbStatIdSelect.next())
         {
         woJbStatId = rsWoJbStatIdSelect.getInt("WOJBSTAT_ID");
         ht.put("WOJBSTAT_ID",new Integer(woJbStatId));
         PreparedStatement ps = con.executeStatement(SQLMaster.FETCH_READY_TO_DESPATCH_SQL_QUERY,ht);
         ResultSet rs = ps.executeQuery();
         if(rs.next())
         {
         if(rs.getInt(1) >=1 )
         {
         readyToDespatch++;
         }
         }
         
         }
         
         }*/
        return readyToDespatch;
    }
    
    public HashMap getAllJobStatusDetails(String userName,Date fromDate,Date toDate,Filter filters[],String sortBy,boolean ascending,int startIndex,int displayCount) throws ProductionException, SQLException
    {
        HashMap hm_Result = new HashMap();
        DBConnection con = null;
        Hashtable ht = new Hashtable();
        int tot_Rec_Cnt = 0;
        String jbName =  "";
        String jbDwgNo = "";
        int jbId = 0;
        int jbQty = 0;
        int desPatchQty = 0;
        int balanceForward = 0;
        int readyToDespatch = 0;
        int notTakenUp = 0;
        int workInProcess = 0;
        logger.info("GET ALL JOB STATUS DETAILS FILTER STARTS");
        
        if((filters == null)||( sortBy == null))
        {
            logger.error("FILTER VALUES ARE NULL");
            
            throw new ProductionException("PMEC006","FILTER VALUES ARE NULL","");
        }
        
        
        con = DBConnectionFactory.getConnection();
        
        ht.put("FROM_DATE",fromDate);
        ht.put("TO_DATE",toDate);
        //Finding end index for the query
        
        //filters and tableName are passed to the function and receives Total Record Count
        tot_Rec_Cnt = con.getRowCountWithComplexFiltersWithHashtable(filters, SQLMaster.GET_ALL_JOB_STATUS_DETAILS_SQL_QUERY,ht);
        if(BuildConfig.DMODE)
        System.out.println("TotRecCount:"+tot_Rec_Cnt);
        int eIndex = startIndex + displayCount;
        
        //filters,tablename,startindex,endindex,sorting column name and sortorder are passed to the function and  query will be received
        String Query = con.buildDynamicQuery(filters,startIndex,eIndex,sortBy,ascending,SQLMaster.GET_ALL_JOB_STATUS_DETAILS_SQL_QUERY);
        
        PreparedStatement ps = con.executeStatement(Query,ht);
        ResultSet rs = ps.executeQuery();
        Vector vec_Result = new Vector();
        while(rs.next())
        {
            jbId = rs.getInt("JB_ID");
            jbName = rs.getString("JB_NAME");
            jbDwgNo = rs.getString("JB_DWG_NO");
            if(BuildConfig.DMODE)
            {
                System.out.println("JB-NAME:"+jbName);
                System.out.println("JB-ID:"+jbId);
                System.out.println("JB-DWGNO:"+jbDwgNo);
            }
            
            CustomerViewJobDetails  objCustomerViewJobDetails = new CustomerViewJobDetails();
            objCustomerViewJobDetails.setJobName(jbName);
            objCustomerViewJobDetails.setDwgNo(jbDwgNo);
            
            //to fetch JobReceipt details..this includes Order details and balance forward Details..
            JobReceiptDetails  objJobReceiptDetails = new JobReceiptDetails();
            
            //to fetch all the work order details..
            HashMap hm = new HashMap();
            Vector vecOrderDet = new Vector();
            hm = this.fetchOrderDetails(userName,jbId,fromDate,toDate,con);
            
            vecOrderDet = (Vector)hm.get("ORDER_DETAILS");
            objJobReceiptDetails.setVecOrderDetails(vecOrderDet);
            
            jbQty = ((Integer)hm.get("JB_QTY")).intValue();
            
            //to fetch balance forward Details..
            balanceForward = this.fetchBalanceForwardDetails(userName,jbId,fromDate,toDate,con);
            objJobReceiptDetails.setBalanceForward(balanceForward);
            
            objJobReceiptDetails.setTotalQty(jbQty + balanceForward);
            
            objCustomerViewJobDetails.setObjJobReceiptDetails(objJobReceiptDetails);
            
            //to fetch JobStatus details..this includes despatch details and work in process and ready to despatch Details..
            JobStatusDetails objJobStatusDetails = new JobStatusDetails();
            
            
            //to fetch all the despatched details..
            Vector vecDespatchDet = new Vector();
            HashMap hm_Despatch = new HashMap();
            
            hm_Despatch = this.fetchDespatchDetails(userName,jbId,fromDate,toDate,con);
            
            vecDespatchDet = (Vector)hm_Despatch.get("DESPATCH_DETAILS");
            
            desPatchQty = ((Integer)hm_Despatch.get("JB_QTY")).intValue();
            
            objJobStatusDetails.setVecDespatchDetails(vecDespatchDet);
            
            //to fetch not Taken Up..
            notTakenUp = this.fetchNotTakenUp(userName,jbId,con);
            objJobStatusDetails.setNotTakenUp(notTakenUp);
            
            //to fetch Ready to Despatch...
            
            readyToDespatch = this.fetchReadyToDespatchDetails(userName,jbId,con);
            objJobStatusDetails.setReadyToDespatch(readyToDespatch);
            
            //          //to fetch Workin Process...
            workInProcess = this.fetchWorkInProcessDetails(userName,jbId,con)-readyToDespatch;
            objJobStatusDetails.setWorkInProcess(workInProcess);
            
            
            
            objJobStatusDetails.setTotQty(desPatchQty+notTakenUp+workInProcess+readyToDespatch);
            
            objCustomerViewJobDetails.setObjJobStatusDetails(objJobStatusDetails);
            
            vec_Result.add(objCustomerViewJobDetails);
            
        }
        logger.info("GET ALL SHIPMENT DETAILS FILTER ENDS");
        
        hm_Result.put("JobStatusDetails",vec_Result);
        hm_Result.put("TotalRecordCount",new Integer(tot_Rec_Cnt));
        
        return hm_Result;
    }
    private HashMap fetchOrderDetails(String userName,int jbId,Date fromDate,Date toDate,DBConnection con) throws SQLException
    {
        
        Vector vec = new Vector();
        HashMap hm = new HashMap();
        Hashtable ht = new Hashtable();
        int jbQty = 0;
        int jbQtyTot = 0;
        ht.put("USER_NAME",userName);
        ht.put("JB_ID",new Integer(jbId));
        ht.put("FROM_DATE",fromDate);
        ht.put("TO_DATE",toDate);
        
        PreparedStatement ps = con.executeStatement(SQLMaster.CUSTOMER_ORDER_SELECT_SQL_QUERY,ht);
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            CustomerOrderDetails objCustomerOrderDetails = new CustomerOrderDetails();
            objCustomerOrderDetails.setWoDate(rs.getTimestamp("WODATE"));
            objCustomerOrderDetails.setCustDCNo(rs.getString("CUSTDCNO"));
            jbQty = rs.getInt("JBQTY");
            objCustomerOrderDetails.setJbQty(jbQty);
            jbQtyTot = jbQtyTot + jbQty;
            vec.add(objCustomerOrderDetails);
        }
        hm.put("JB_QTY",new Integer(jbQtyTot));
        hm.put("ORDER_DETAILS",vec);
        return hm;
    }
    private int fetchBalanceForwardDetails(String userName,int jbId,Date fromDate,Date toDate,DBConnection con) throws SQLException
    {
        
        int balanceForward = 0;
        Hashtable ht = new Hashtable();
        ht.put("USER_NAME",userName);
        ht.put("JB_ID",new Integer(jbId));
        ht.put("FROM_DATE",fromDate);
        ht.put("TO_DATE",toDate);
        PreparedStatement ps = con.executeStatement(SQLMaster.SELECT_BALANCE_FORWARD_DETAILS_SQL_QUERY,ht);
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            balanceForward = rs.getInt("JBQTY");
        }
        return balanceForward;
    }
    
    private HashMap fetchDespatchDetails(String userName,int jbId,Date fromDate,Date toDate,DBConnection con) throws SQLException
    {
        
        Vector vec = new Vector();
        HashMap hm = new HashMap();
        Hashtable ht = new Hashtable();
        int jbQty = 0;
        int jbQtyTot = 0;
        ht.put("USER_NAME",userName);
        ht.put("JB_ID",new Integer(jbId));
        ht.put("FROM_DATE",fromDate);
        ht.put("TO_DATE",toDate);
        
        
        PreparedStatement ps = con.executeStatement(SQLMaster.DESPATCHED_DETAILS_SELECT_SQL_QUERY,ht);
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            DespatchDetails objDespatchDetails = new DespatchDetails();
            objDespatchDetails.setDespatchDate(rs.getTimestamp("DESPATCHEDDATE"));
            objDespatchDetails.setCompanyDCNo(rs.getString("CUSTDCNO"));
            jbQty = rs.getInt("JBQTY");
            objDespatchDetails.setJbQty(jbQty);
            jbQtyTot = jbQtyTot + jbQty;
            vec.add(objDespatchDetails);
        }
        hm.put("DESPATCH_DETAILS",vec);
        hm.put("JB_QTY",new Integer(jbQtyTot));
        return hm;
    }
    
    private int fetchNotTakenUp(String userName,int jbId,DBConnection con) throws SQLException
    {
        int notTakenUp = 0;
        Hashtable ht = new Hashtable();
        ht.put("USER_NAME",userName);
        ht.put("JB_ID",new Integer(jbId));
        
        PreparedStatement ps = con.executeStatement(SQLMaster.FETCH_NOT_TAKEN_UP_SQL_QUERY,ht);
        ResultSet rs = ps.executeQuery();
        if(rs.next())
        {
            notTakenUp = rs.getInt("JBQTY");
        }
        return notTakenUp;
    }
    
    
    private int fetchWorkInProcessDetails(String userName,int jbId,DBConnection con) throws SQLException
    {
        int workInProcess = 0;
        Hashtable ht = new Hashtable();
        ht.put("USER_NAME",userName);
        ht.put("JB_ID",new Integer(jbId));
        
        PreparedStatement ps = con.executeStatement(SQLMaster.FETCH_WORK_IN_PROCESS_SQL_QUERY,ht);
        ResultSet rs = ps.executeQuery();
        if(rs.next())
        {
            workInProcess = rs.getInt("JBQTY");
        }
        
        
        
        return workInProcess;
    }
    private int fetchReadyToDespatchDetails(String userName,int jbId,DBConnection con) throws SQLException
    {
        int readyToDespatch = 0;
        Hashtable ht = new Hashtable();
        ht.put("USER_NAME",userName);
        ht.put("JB_ID",new Integer(jbId));
        PreparedStatement ps = con.executeStatement(SQLMaster.FETCH_READY_TO_DESPATCH_SQL_QUERY,ht);
        ResultSet rs = ps.executeQuery();
        if(rs.next())
        {
            readyToDespatch = rs.getInt("JBQTY");
        }
        
        /*PreparedStatement psWojbIdSelect = con.executeStatement(SQLMaster.WOJOBID_GET_SQL_QUERY,ht);
         ResultSet rsWojbIdSelect = psWojbIdSelect.executeQuery();
         while(rsWojbIdSelect.next())
         {
         woJbId = rsWojbIdSelect.getInt("WOJB_ID");
         ht.put("WOJB_ID",new Integer(woJbId));	
         PreparedStatement psWoJbStatIdSelect = con.executeStatement(SQLMaster.WOJBSTATID_GET_SQL_QUERY,ht);
         ResultSet rsWoJbStatIdSelect = psWoJbStatIdSelect.executeQuery();
         while(rsWoJbStatIdSelect.next())
         {
         woJbStatId = rsWoJbStatIdSelect.getInt("WOJBSTAT_ID");
         ht.put("WOJBSTAT_ID",new Integer(woJbStatId));
         PreparedStatement ps = con.executeStatement(SQLMaster.FETCH_READY_TO_DESPATCH_SQL_QUERY,ht);
         ResultSet rs = ps.executeQuery();
         if(rs.next())
         {
         if(rs.getInt(1) >=1 )
         {
         readyToDespatch++;
         }
         }
         
         }
         
         }*/
        return readyToDespatch;
    }
    
    
    public static void main(String args[]) throws ProductionException, SQLException
    {
        ShipmentDetailsManager objShipmentDetailsManager = new ShipmentDetailsManager();
        Vector vec = new Vector();
        vec.add(new Integer(21));
        vec.add(new Integer(22));
        vec.add(new Integer(23));
        objShipmentDetailsManager.postShipmentDetails(vec);
        
        
        /*
         HashMap hmp = new HashMap();
         ShipmentDetailsManager objShipmentDetailsManager = new ShipmentDetailsManager();
         
         Filter filobj[] = new Filter[2];
         filobj[0] = new Filter();
         filobj[0].setFieldName("USER_NAME");
         filobj[0].setFieldValue("admin");
         filobj[0].setSpecialFunction("Starts With");
         
         filobj[1] = new Filter();
         filobj[1].setFieldName("JB_NAME");
         filobj[1].setFieldValue("20");
         filobj[1].setSpecialFunction("Starts With");
         
         
         int startIndex = 1;
         int displayCount = 4;
         boolean ascending = true;
         String sortBy = "JB_NAME";
         Date stDate = null;
         Date enDate = null;
         stDate = new Date("18-JUL-05");
         enDate = new Date("24-JUL-05");
         hmp = objShipmentDetailsManager.getAllJobStatusDetails(stDate,enDate,filobj,sortBy,ascending,startIndex,displayCount);
         System.out.println("HASHMAP RESULT OF FILTER"+hmp);
         System.out.println("Main Ends");
         Vector v = (Vector)hmp.get("JobStatusDetails");
         
         for(int i = 0;i<v.size();i++)
         {
         CustomerViewJobDetails objCustomerViewJobDetails = new CustomerViewJobDetails();
         objCustomerViewJobDetails = (CustomerViewJobDetails)v.get(i);
         objCustomerViewJobDetails
         }
         
         
         
         */}
    
}

/*
*$Log: ShipmentDetailsManager.java,v $
*Revision 1.26  2005/12/21 09:39:36  kduraisamy
*signature added for getAlljobStatus for sending mail report to customer.
*
*Revision 1.25  2005/12/20 20:55:14  kduraisamy
*signature added for getAlljobStatus for sending mail report to customer.
*
*Revision 1.24  2005/12/20 17:59:03  kduraisamy
*signature added for getAlljobStatus for sending mail report to customer.
*
*Revision 1.23  2005/09/16 11:41:27  kduraisamy
*BuildConfig.DMODE. added.
*
*Revision 1.22  2005/09/01 07:36:16  kduraisamy
*job status for pmac committed.
*
*Revision 1.21  2005/08/09 08:49:26  kduraisamy
*BUILDCONFIG.DMODE() ADDED.
*
*Revision 1.20  2005/07/27 07:02:16  kduraisamy
*IF DEBUG.DMODE ADDED.
*
*Revision 1.19  2005/07/26 15:57:37  kduraisamy
*try is taken out of for loop in addNewDetails().
*
*Revision 1.18  2005/07/26 08:36:20  kduraisamy
*fetchDespatchDetails() query changed.
*
*Revision 1.17  2005/07/25 21:09:22  kduraisamy
*not Taken up added for total.
*
*Revision 1.16  2005/07/25 08:38:48  kduraisamy
*fetchNotTakenUp() added.
*
*Revision 1.15  2005/07/23 15:25:16  kduraisamy
*customer login screen.cust dc no set in fetchOrderDetails().
*
*Revision 1.14  2005/07/23 15:11:05  kduraisamy
*make valid and invalid methods added.
*
*Revision 1.13  2005/07/23 14:31:57  kduraisamy
*updateShipmentDetails() error corrected.
*
*Revision 1.12  2005/07/23 14:13:07  kduraisamy
*totQty for customer login finished.
*
*Revision 1.11  2005/07/23 13:23:03  kduraisamy
*getShipmentOperationDetailsForUpdate(). added.
*
*Revision 1.10  2005/07/23 10:19:28  kduraisamy
*getAllJobStatusDetails() method error corrected.
*
*Revision 1.9  2005/07/23 09:22:47  kduraisamy
* isModify() and getShipmentDetails().
*
*Revision 1.8  2005/07/23 08:50:27  kduraisamy
*indentation.
*
*Revision 1.7  2005/07/23 06:46:36  kduraisamy
*getAllShipmentDetails() error corrected.
*
*Revision 1.6  2005/07/22 12:49:15  kduraisamy
*HARD CODED FOR CUSTOMER VIEW ADDED.
*
*Revision 1.5  2005/07/22 11:58:22  kduraisamy
*getAllJobStatusDetails().
*
*Revision 1.4  2005/07/22 11:16:46  kduraisamy
*getshipmentOpnDetails() added.
*
*Revision 1.3  2005/07/22 10:45:06  kduraisamy
*initial commit.
*
*Revision 1.2  2005/07/22 07:36:57  kduraisamy
*addNewShipmentDetails() added.
*
*Revision 1.1  2005/07/22 07:07:34  kduraisamy
*INITIAL COMMIT.
*
*
*/