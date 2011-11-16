/*
 * Created on Jul 20, 2005
 *
 * ClassName	:  	DespatchClearanceDetailsManager.java
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
public class DespatchClearanceDetailsManager
{
    static Logger logger = Logger.getLogger(DespatchClearanceDetailsManager.class);
    public DespatchClearanceDetailsManager()
    {
        logger.addAppender(LoggerOutput.getAppender());
        logger.setLevel(ProdacsConfig.LOGGER_LEVEL);
    }
    public boolean addNewDespatchClearanceDetails(DespatchClearanceDetails[] objDespatchClearanceDetailsList) throws ProductionException, SQLException
    {
        
        boolean addRESULT = false;
        int startOpn = 0;
        int endOpn = 0;
        Hashtable ht_Despatch_Add = new Hashtable();
        DBConnection con = null;
        
        logger.info("DESPATCH CLEARANCE ADD STARTS");
        
        if(BuildConfig.DMODE)
            System.out.println("DESPATCH CLEARANCE ADD STARTS");
        
        if(objDespatchClearanceDetailsList.length == 0)
        {
            logger.error("DESPATCH CLEARANCE DETAILS OBJ NULL");
            throw new ProductionException("PMEC001","DESPATCH CLEARANCE DETAILS OBJ NULL","");
        }
        
        con = DBConnectionFactory.getConnection();
        con.startTransaction();
        try
        {
            
            for(int i = 0;i<objDespatchClearanceDetailsList.length;i++)
            {
                startOpn = objDespatchClearanceDetailsList[i].getDespatchStartOpn();
                endOpn = objDespatchClearanceDetailsList[i].getDespatchEndOpn();
                ht_Despatch_Add.put("DESPATCH_CRNT_DATE",objDespatchClearanceDetailsList[i].getDespatchCrntDate());
                ht_Despatch_Add.put("SHIFT_ID",new Integer(objDespatchClearanceDetailsList[i].getShiftId()));
                ht_Despatch_Add.put("WOJB_ID",new Integer(objDespatchClearanceDetailsList[i].getWoJbId()));
                ht_Despatch_Add.put("DESPATCH_QTY_SNOS",objDespatchClearanceDetailsList[i].getDespatchQtySnos());
                ht_Despatch_Add.put("DESPATCH_TOT_QTY",new Integer(objDespatchClearanceDetailsList[i].getDespatchTotQty()));
                ht_Despatch_Add.put("DESPATCH_START_OPN",new Integer(startOpn));
                ht_Despatch_Add.put("DESPATCH_END_OPN",new Integer(endOpn));
                ht_Despatch_Add.put("DESPATCH_CREATEDBY",objDespatchClearanceDetailsList[i].getCreatedBy());
                //prodQtySnos = objDespatchClearanceDetailsList[i].getProdQtySnos();
                con.executeUpdateStatement(SQLMaster.DESPATCH_DETAILS_ADD_SQL_QUERY,ht_Despatch_Add);
                /*if(result > 0)
                 */
                /*   {
                 
                 //to set WO_ISPROD_ENTERED to 1..
                  
                  con.executeUpdateStatement(SQLMaster.WORKORDER_TABLE_UPDATE_SQL_QUERY,ht_Despatch_Add);
                  int j = 0;
                  ps = con.executeStatement(SQLMaster.DESPATCH_ID_SELECT_SQL_QUERY);
                  ResultSet rs_ProdId_Get = ps.executeQuery();
                  if(rs_ProdId_Get.next())
                  {
                  despatchId = rs_ProdId_Get.getInt(1);
                  if(BuildConfig.DMODE)
                  System.out.println("DESPATCH ID :"+despatchId);
                  }
                  rs_ProdId_Get.close();
                  ps.close();
                  */   
                
                /*vec_EmpHrs = objDespatchClearanceDetailsList[i].getDespatchEmpHrsDetails();
                 
                 
                 for(j = 0;j<vec_EmpHrs.size();j++)
                 {
                 
                 EmployeeDtyOtDetails objEmployeeDtyOtDetails = new EmployeeDtyOtDetails();
                 objEmployeeDtyOtDetails = (EmployeeDtyOtDetails)vec_EmpHrs.elementAt(j);
                 empId = objEmployeeDtyOtDetails.getEmpId();
                 
                 ht_EmpHrs_Add.put("DESPATCH_ID",new Integer(despatchId));
                 ht_EmpHrs_Add.put("EMP_ID",new Integer(empId));
                 ht_EmpHrs_Add.put("DESPATCH_MODIFYCOUNT",new Integer(0));
                 result = con.executeUpdateStatement(SQLMaster.FINAL_PRODN_EMPHRS_ADD_SQL_QUERY,ht_EmpHrs_Add);
                 if(result>0)
                 {
                 if(BuildConfig.DMODE)
                 System.out.println("Employee Hrs Details Added");
                 
                 }
                 
                 
                 }
                 }*/
            }
            addRESULT = true;
            con.commitTransaction();
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
                if(sqle.toString().indexOf("PK_DESPATCHID")>=0)
                {
                    throw new ProductionException("PMEC002","DESPATCH CLEARANCE ID IS UNIQUE.UNIQUE CONSTRAINT VIOLATED",sqle.toString());
                }
                else if (sqle.toString().indexOf("FK_DESPATCH_SHIFTID") >= 0)
                {
                    throw new ProductionException("PMEC007","PARENT KEY SHIFT ID NOT FOUND",sqle.toString());
                }
                else if (sqle.toString().indexOf("FK_DESPATCH_WOJBID") >= 0)
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
                    throw new ProductionException("PMEC002","DESPATCH ID IS UNIQUE.UNIQUE CONSTRAINT VIOLATED",sqle.toString());
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
        logger.info("DESPATCH CLEARANCE ADD ENDS");
        
        
        
        return addRESULT;
    }
    
    public boolean updateDespatchClearanceDetails(DespatchClearanceDetails objDespatchClearanceDetails) throws ProductionException, SQLException
    {
        boolean updateRESULT = false;
        int despatchId = 0;
        DBConnection con=null;
        Hashtable ht_Despatch_Update = new Hashtable();
        int result;
        
        
        //delete PROD_EMP table.
        despatchId = objDespatchClearanceDetails.getDespatchId();
        
        con = DBConnectionFactory.getConnection();
        con.startTransaction();
        //ht_EmpHrs_Del.put("DESPATCH_ID",new Integer(finalProdId));
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
            
            //Updation of DESPATCH table.
            
            ht_Despatch_Update.put("DESPATCH_ID",new Integer(despatchId));
            ht_Despatch_Update.put("DESPATCH_CRNT_DATE",objDespatchClearanceDetails.getDespatchCrntDate());
            ht_Despatch_Update.put("SHIFT_ID",new Integer(objDespatchClearanceDetails.getShiftId()));
            ht_Despatch_Update.put("WOJB_ID",new Integer(objDespatchClearanceDetails.getWoJbId()));
            ht_Despatch_Update.put("DESPATCH_QTY_SNOS",objDespatchClearanceDetails.getDespatchQtySnos());
            ht_Despatch_Update.put("DESPATCH_TOT_QTY",new Integer(objDespatchClearanceDetails.getDespatchTotQty()));
            ht_Despatch_Update.put("DESPATCH_START_OPN",new Integer(objDespatchClearanceDetails.getDespatchStartOpn()));
            ht_Despatch_Update.put("DESPATCH_END_OPN",new Integer(objDespatchClearanceDetails.getDespatchEndOpn()));
            ht_Despatch_Update.put("DESPATCH_POST_FLG","0");
            ht_Despatch_Update.put("DESPATCH_CREATEDBY",objDespatchClearanceDetails.getModifiedBy());
            
            // to store Prod Entries to Log..
            
            result = con.executeUpdateStatement(SQLMaster.DESPATCH_DETAILS_LOG_ADD_SQL_QUERY,ht_Despatch_Update);
            
            if(result > 0)
            {
                con.executeUpdateStatement(SQLMaster.DESPATCH_DETAILS_UPDATE_SQL_QUERY,ht_Despatch_Update);
                /*PreparedStatement ps_ModifyCnt_Sel = con.executeStatement(SQLMaster.FINAL_PROD_MODIFY_COUNT_SELECT,ht_Despatch_Update);
                 ResultSet rs_ModifyCnt_Sel = ps_ModifyCnt_Sel.executeQuery();
                 int modifyCount = 0;
                 if(rs_ModifyCnt_Sel.next())
                 {
                 modifyCount = rs_ModifyCnt_Sel.getInt("DESPATCH_MODIFYCOUNT");
                 }
                 ht_Despatch_Update.put("DESPATCH_MODIFYCOUNT",new Integer(modifyCount));
                 */
                /*vec_EmpHrs = objDespatchClearanceDetails.getProdnEmpHrsDetails();
                 ht_EmpHrs_Add.put("DESPATCH_MODIFYCOUNT",new Integer(modifyCount));
                 
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
                if(sqle.toString().indexOf("PK_DESPATCHID")>=0)
                {
                    throw new ProductionException("PMEC002","FINAL PRODUCTION ID IS UNIQUE.UNIQUE CONSTRAINT VIOLATED",sqle.toString());
                }
                else if (sqle.toString().indexOf("FK_DESPATCH_SHIFTID") >= 0)
                {
                    throw new ProductionException("PMEC007","PARENT KEY SHIFT ID NOT FOUND",sqle.toString());
                }
                else if (sqle.toString().indexOf("FK_DESPATCH_WOJBID") >= 0)
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
                    throw new ProductionException("PMEC002","FINAL PRODUCTION ID IS UNIQUE.UNIQUE CONSTRAINT VIOLATED",sqle.toString());
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
        
        
        logger.info("DESPATCH CLEARANCE UPDATE ENDS");
        
        return updateRESULT;
    }
    
    public HashMap getAllDespatchClearanceDetails(Filter filters[],String sortBy,boolean ascending,int startIndex,int displayCount) throws ProductionException,SQLException
    {
        HashMap hm_Result = new HashMap();
        Vector vec_Prod_Det = new Vector();
        DBConnection con=null;
        
        logger.info("GET ALL DESPATCH DETAILS FILTER STARTS");
        
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
            tot_Rec_Cnt = con.getRowCountWithComplexFilters(filters, SQLMaster.GET_ALL_DESPATCH_DETAILS_FILTER_QUERY);
            
            //Finding end index for the query
            int eIndex = startIndex + displayCount;
            
            
            // filters,tablename,startindex,endindex,sorting column name and sortorder are passed to the function and  query will be received
            String Query = con.buildDynamicQuery(filters,startIndex,eIndex,sortBy,ascending,SQLMaster.GET_ALL_DESPATCH_DETAILS_FILTER_QUERY);
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
                DespatchClearanceDetails  objDespatchClearanceDetails = new DespatchClearanceDetails();
                objDespatchClearanceDetails.setDespatchId(rs_Prodn_Details_Get.getInt("DESPATCH_ID"));
                objDespatchClearanceDetails.setDespatchCrntDate(rs_Prodn_Details_Get.getTimestamp("DESPATCH_CRNT_DATE"));
                objDespatchClearanceDetails.setShiftId(rs_Prodn_Details_Get.getInt("SHIFT_ID"));
                objDespatchClearanceDetails.setShiftName(rs_Prodn_Details_Get.getString("SHIFT_NAME"));
                objDespatchClearanceDetails.setWoNo(rs_Prodn_Details_Get.getString("WO_NO"));
                //objDespatchClearanceDetails.setProdQtySnos(rs_Prodn_Details_Get.getString("PROD_QTY_SNOS"));
                //objDespatchClearanceDetails.setProdTotQty(rs_Prodn_Details_Get.getInt("PROD_TOT_QTY"));
                //objDespatchClearanceDetails.setProdStartOpn(rs_Prodn_Details_Get.getInt("PROD_START_OPN"));
                //objDespatchClearanceDetails.setProdEndOpn(rs_Prodn_Details_Get.getInt("PROD_END_OPN"));
                ProductionJobDetails objProductionJobDetails = new ProductionJobDetails();
                objProductionJobDetails.setJobName(rs_Prodn_Details_Get.getString("JB_NAME"));
                //objProductionJobDetails.setDwgNo(rs_Prodn_Details_Get.getString("JB_DWG_NO"));
                //objProductionJobDetails.setRvsnNo(rs_Prodn_Details_Get.getString("JB_RVSN_NO"));
                //objProductionJobDetails.setMatlType(rs_Prodn_Details_Get.getString("JB_MATL_TYP"));
                
                objDespatchClearanceDetails.setObjProductionJobDetails(objProductionJobDetails);
                
                objDespatchClearanceDetails.setDespatchDateStamp(rs_Prodn_Details_Get.getTimestamp("DESPATCH_DATESTAMP"));
                objDespatchClearanceDetails.setDespatchIsValid(rs_Prodn_Details_Get.getInt("DESPATCH_ISVALID"));
                vec_Prod_Det.addElement(objDespatchClearanceDetails);
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
        
        logger.info("GET ALL DESPATCH DETAILS FILTER ENDS");
        
        return hm_Result;
    }
    
    public DespatchClearanceDetails getDespatchClearanceDetails(int despatchId) throws ProductionException,SQLException
    {
        DespatchClearanceDetails objDespatchClearanceDetails = new DespatchClearanceDetails();
        Hashtable ht_ProdDet_Get = new Hashtable();
        ProductionJobDetails objProductionJobDetails = new ProductionJobDetails();
        DBConnection con = null;
        
        logger.info("GET DESPATCH DETAILS STARTS");
        
        con = DBConnectionFactory.getConnection();
        
        ht_ProdDet_Get.put("DESPATCH_ID", new Integer(despatchId));
        try
        {
            PreparedStatement ps_ProdDet_Get = con.executeStatement(SQLMaster.GET_DESPATCH_DETAILS_SQL_QUERY,ht_ProdDet_Get);
            ResultSet rs_ProdDet_Get = ps_ProdDet_Get.executeQuery();
            
            if(rs_ProdDet_Get.next())
            {
                //set Production Details //
                objDespatchClearanceDetails.setDespatchId(despatchId);
                objDespatchClearanceDetails.setDespatchCrntDate(rs_ProdDet_Get.getTimestamp("DESPATCH_CRNT_DATE"));
                objDespatchClearanceDetails.setShiftId(rs_ProdDet_Get.getInt("SHIFT_ID"));
                objDespatchClearanceDetails.setShiftName(rs_ProdDet_Get.getString("SHIFT_NAME"));
                objDespatchClearanceDetails.setWoJbId(rs_ProdDet_Get.getInt("WOJB_ID"));
                objDespatchClearanceDetails.setWoNo(rs_ProdDet_Get.getString("WO_NO"));
                objDespatchClearanceDetails.setWoId(rs_ProdDet_Get.getInt("WO_ID"));
                objDespatchClearanceDetails.setDespatchQtySnos(rs_ProdDet_Get.getString("DESPATCH_QTY_SNOS"));
                objDespatchClearanceDetails.setDespatchTotQty(rs_ProdDet_Get.getInt("DESPATCH_TOT_QTY"));
                objDespatchClearanceDetails.setDespatchStartOpn(rs_ProdDet_Get.getInt("DESPATCH_START_OPN"));
                objDespatchClearanceDetails.setDespatchEndOpn(rs_ProdDet_Get.getInt("DESPATCH_END_OPN"));
                objDespatchClearanceDetails.setDespatchPostFlg(rs_ProdDet_Get.getString("DESPATCH_POST_FLG").equals("1")?true:false);
                objDespatchClearanceDetails.setModifyCount(rs_ProdDet_Get.getInt("DESPATCH_MODIFYCOUNT"));
                objDespatchClearanceDetails.setCreatedBy(rs_ProdDet_Get.getString("DESPATCH_CREATEDBY"));
                objDespatchClearanceDetails.setDespatchDateStamp(rs_ProdDet_Get.getTimestamp("DESPATCH_DATESTAMP"));
                objDespatchClearanceDetails.setDespatchIsValid(rs_ProdDet_Get.getInt("DESPATCH_ISVALID"));
                
                ///set Job Details - starts///
                //objProductionJobDetails.setJobId(rs_ProdDet_Get.getInt("JB_ID"));
                objProductionJobDetails.setJobName(rs_ProdDet_Get.getString("JB_NAME"));
                objProductionJobDetails.setDwgNo(rs_ProdDet_Get.getString("JB_DWG_NO"));
                objProductionJobDetails.setRvsnNo(rs_ProdDet_Get.getString("JB_RVSN_NO"));
                objProductionJobDetails.setMatlType(rs_ProdDet_Get.getString("JB_MATL_TYP"));
                
                objDespatchClearanceDetails.setObjProductionJobDetails(objProductionJobDetails);
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
        
        logger.info("GET DESPATCH DETAILS ENDS");
        
        return objDespatchClearanceDetails;
    }
    
    public Vector viewUnPostedDespatchDetails(Date fromDate,Date toDate) throws SQLException, ProductionException
    {
        Vector vec_Result = new Vector();
        DBConnection con = null;
        Hashtable ht_UnPostedJbDet_Get = new Hashtable();
        Hashtable ht_UnPostedDet_Get = new Hashtable();
        int despatchId = 0;
        int woJbId = 0;
        
        
        logger.info("GET UNPOSTED DESPATCH DETAILS STARTS");
        
        
        con = DBConnectionFactory.getConnection();
        ht_UnPostedDet_Get.put("FROM_DATE",fromDate);
        ht_UnPostedDet_Get.put("TO_DATE",toDate);
        try
        {
            PreparedStatement ps_UnpostedProdId_Get = con.executeStatement(SQLMaster.UNPOSTED_DESPATCH_ID_SELECT_SQL_QUERY,ht_UnPostedDet_Get);
            ResultSet rs_UnpostedProdId_Get = ps_UnpostedProdId_Get.executeQuery();
            while(rs_UnpostedProdId_Get.next())
            {
                UnPostedProductionDetails objUnPostedProductionDetails = new UnPostedProductionDetails();
                despatchId = rs_UnpostedProdId_Get.getInt("DESPATCH_ID");
                objUnPostedProductionDetails.setProdId(despatchId);
                objUnPostedProductionDetails.setProdCrntDate(rs_UnpostedProdId_Get.getTimestamp("DESPATCH_CRNT_DATE"));
                objUnPostedProductionDetails.setShiftName(rs_UnpostedProdId_Get.getString("SHIFT_NAME"));
                objUnPostedProductionDetails.setProdStartOpn(rs_UnpostedProdId_Get.getInt("DESPATCH_START_OPN"));
                objUnPostedProductionDetails.setProdEndOpn(rs_UnpostedProdId_Get.getInt("DESPATCH_END_OPN"));
                objUnPostedProductionDetails.setJobQtySnos(rs_UnpostedProdId_Get.getString("DESPATCH_QTY_SNOS"));
                objUnPostedProductionDetails.setJobQty(rs_UnpostedProdId_Get.getInt("DESPATCH_TOT_QTY"));
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
        
        
        logger.info("GET UNPOSTED DESPATCH DETAILS ENDS");
        
        
        return vec_Result;
    }
    public HashMap postDespatchDetails(Vector vec_ProdIds) throws ProductionException, SQLException
    {
        
        int despatchId = 0;
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
        
        
        logger.info("POST DESPATCH DETAILS STARTS");
        
        if(vec_ProdIds == null)
        {
            throw new ProductionException("PMEC005","DESPATCH IDS VECTOR OBJECT IS NULL","");
        }
        try
        {
            con = DBConnectionFactory.getConnection();
            con.startTransaction();
            for(int i = 0;i<vec_ProdIds.size();i++)
            {
                despatchId = ((Integer)vec_ProdIds.elementAt(i)).intValue();
                ht_PostProdn.put("DESPATCH_ID",new Integer(despatchId));
                int result = con.executeUpdateStatement(SQLMaster.DESPATCH_DETAILS_POST_SQL_QUERY,ht_PostProdn);
                if(result>=1)
                {
                    hm_Result.put(vec_ProdIds.get(i),new Integer(0));
                    logger.info(vec_ProdIds.get(i)+" : DESPATCH RECORD POSTED");
                    
                    PreparedStatement ps_ProdnDet = con.executeStatement(SQLMaster.DESPATCH_DETAILS_SELECT_SQL_QUERY,ht_PostProdn);
                    ResultSet rs_ProdnDet = ps_ProdnDet.executeQuery();
                    if(rs_ProdnDet.next())
                    {
                        woJbId = rs_ProdnDet.getInt("WOJB_ID");
                        qtySnos = rs_ProdnDet.getString("DESPATCH_QTY_SNOS");
                        //totQty = rs_ProdnDet.getInt("PROD_TOT_QTY");
                        startOpn = rs_ProdnDet.getInt("DESPATCH_START_OPN");
                        endOpn = rs_ProdnDet.getInt("DESPATCH_END_OPN");
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
        
        logger.info("POST DESPATCH DETAILS ENDS");
        
        return hm_Result;
        
    }
    
    public Vector getDespatchOperationDetails(int woJbId) throws ProductionException,SQLException
    {
        Hashtable ht_ProdnJbOpn_Get = new Hashtable();
        Hashtable ht_JbStatSno_Get = new Hashtable();
        Hashtable ht_JbPstdStatSno_Get = new Hashtable();
        ResultSet rs_ProdnJbOpn_Get = null;
        Vector vec_Result = new Vector();
        DBConnection con=null;
        SortString ss = new SortString();
        
        logger.info("GET DESPATCH JOB OPERATION DETAILS STARTS");
        
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
                
                ps_Prod_Det = con.executeStatement(SQLMaster.GET_DESPATCH_CLEARANCE_DETAILS_SQL_QUERY,ht_ProdnJbOpn_Get);
                rs_Prod_Det = ps_Prod_Det.executeQuery();
                String totOpns = "";
                while(rs_Prod_Det.next())
                {
                    String qtySnos = "";
                    qtySnos = rs_Prod_Det.getString("DESPATCH_QTY_SNOS");
                    StringTokenizer st = new StringTokenizer(qtySnos.trim(),",");
                    while(st.hasMoreTokens())
                    {
                        int sno = Integer.parseInt(st.nextToken().trim());
                        if(sno == jbQtySno)
                        {
                            int stOpn = rs_Prod_Det.getInt("DESPATCH_START_OPN");
                            int endOpn = rs_Prod_Det.getInt("DESPATCH_END_OPN");
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
                PreparedStatement ps_PendStat_OpnSnos = con.executeStatement(SQLMaster.GET_PENDING_DESPATCH_OPERATION_SNOS_SQL_QUERY,ht_JbStatSno_Get);
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
                
                PreparedStatement ps_PostedStat_OpnSnos = con.executeStatement(SQLMaster.GET_POSTED_DESPATCH_OPERATION_SNOS_SQL_QUERY,ht_JbPstdStatSno_Get);
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
            logger.info("GET DESPATCH JOB OPERATION DETAILS ENDS");
        }
        return vec_Result;
    }
    
    public Vector getDespatchOperationDetailsForUpdate(int woJbId,int despatchId) throws ProductionException,SQLException
    {
        Hashtable ht_ProdnJbOpn_Get = new Hashtable();
        Hashtable ht_JbStatSno_Get = new Hashtable();
        Hashtable ht_JbPstdStatSno_Get = new Hashtable();
        ResultSet rs_ProdnJbOpn_Get = null;
        Vector vec_Result = new Vector();
        DBConnection con=null;
        SortString ss = new SortString();
        
        logger.info("GET DESPATCH OPERATION DETAILS STARTS");
        
        con = DBConnectionFactory.getConnection();
        ht_ProdnJbOpn_Get.put("WOJB_ID",new Integer(woJbId));
        ht_ProdnJbOpn_Get.put("DESPATCH_ID",new Integer(despatchId));
        
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
                
                ps_Prod_Det = con.executeStatement(SQLMaster.GET_DESPATCH_DETAILS_FOR_UPDATE_SQL_QUERY,ht_ProdnJbOpn_Get);
                rs_Prod_Det = ps_Prod_Det.executeQuery();
                String totOpns = "";
                while(rs_Prod_Det.next())
                {
                    String qtySnos = "";
                    qtySnos = rs_Prod_Det.getString("DESPATCH_QTY_SNOS");
                    StringTokenizer st = new StringTokenizer(qtySnos.trim(),",");
                    while(st.hasMoreTokens())
                    {
                        int sno = Integer.parseInt(st.nextToken().trim());
                        if(sno == jbQtySno)
                        {
                            int stOpn = rs_Prod_Det.getInt("DESPATCH_START_OPN");
                            int endOpn = rs_Prod_Det.getInt("DESPATCH_END_OPN");
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
                PreparedStatement ps_PendStat_OpnSnos = con.executeStatement(SQLMaster.GET_PENDING_DESPATCH_OPERATION_SNOS_SQL_QUERY,ht_JbStatSno_Get);
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
                PreparedStatement ps_PostedStat_OpnSnos = con.executeStatement(SQLMaster.GET_POSTED_DESPATCH_OPERATION_SNOS_SQL_QUERY,ht_JbPstdStatSno_Get);
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
        
        logger.info("GET DESPATCH OPERATION DETAILS ENDS");
        
        return vec_Result;
    }
    
    public boolean isModifyForDespatch(int despatchId) throws ProductionException, SQLException
    {
        boolean retVal = false;
        DBConnection con = null;
        Hashtable ht_Prod = new Hashtable();
        PreparedStatement ps = null;
        int cnt = 0;
        try
        {
            con = DBConnectionFactory.getConnection();
            ht_Prod.put("DESPATCH_ID",new Integer(despatchId));
            ps = con.executeStatement(SQLMaster.CHECK_DESPATCH_FOR_MODIFY_SQL_QUERY,ht_Prod);
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
    public HashMap makeDespatchValid(Vector despatchIds) throws ProductionException, SQLException
    {
        int result = 0;
        DBConnection con = null;
        HashMap hm_Result = new HashMap();
        
        logger.info("MAKE DESPATCH VALID STARTS");
        
        if(despatchIds == null)
        {
            logger.error("DESPATCH VECTOR OBJECT IS NULL");
            throw new ProductionException("PREC00","DESPATCH VECTOR OBJECT IS NULL","");
        }
        try
        {
            con = DBConnectionFactory.getConnection();
            con.startTransaction();
            for(int i=0;i<despatchIds.size();i++)
            {
                Hashtable ht_Prod_Id = new Hashtable();
                
                
                ht_Prod_Id.put("DESPATCH_ID",(Integer)despatchIds.get(i));
                
                result = con.executeUpdateStatement(SQLMaster.DESPATCH_MAKE_VALID_SQL_QUERY,ht_Prod_Id);
                
                if(result>=1)
                {
                    con.commitTransaction();
                    hm_Result.put(despatchIds.get(i),new Integer(0));
                    logger.info(despatchIds.get(i)+" : DESPATCH RECORD VALIDATED");
                    
                }
                else
                {
                    con.rollBackTransaction();
                    hm_Result.put(despatchIds.get(i),new Integer(1));
                    logger.info(despatchIds.get(i)+" : DESPATCH RECORD NOT VALIDATED");
                    
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
        
        logger.info("MAKE DESPATCH VALID ENDS");
        
        return hm_Result;
        
    }
    public HashMap makeDespatchInValid(Vector despatchIds) throws ProductionException, SQLException
    {
        int result = 0;
        DBConnection con = null;
        HashMap hm_Result = new HashMap();
        
        logger.info("MAKE DESPATCH INVALID STARTS");
        
        if(despatchIds == null)
        {
            logger.error("DESPATCH VECTOR OBJECT IS NULL");
            throw new ProductionException("PREC00","DESPATCH VECTOR OBJECT IS NULL","");
        }
        try
        {
            con = DBConnectionFactory.getConnection();
            con.startTransaction();
            for(int i=0;i<despatchIds.size();i++)
            {
                Hashtable ht_Prod_Id = new Hashtable();
                
                
                ht_Prod_Id.put("DESPATCH_ID",(Integer)despatchIds.get(i));
                
                result = con.executeUpdateStatement(SQLMaster.DESPATCH_MAKE_INVALID_SQL_QUERY,ht_Prod_Id);
                
                if(result>=1)
                {
                    con.commitTransaction();
                    hm_Result.put(despatchIds.get(i),new Integer(0));
                    logger.info(despatchIds.get(i)+" : DESPATCH RECORD INVALIDATED");
                    
                }
                else
                {
                    con.rollBackTransaction();
                    hm_Result.put(despatchIds.get(i),new Integer(1));
                    logger.info(despatchIds.get(i)+" : DESPATCH RECORD NOT INVALIDATED");
                    
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
        
        logger.info("MAKE DESPATCH INVALID ENDS");
        
        return hm_Result;
        
        
        
    }
    public static void main(String args[]) throws ProductionException, SQLException
    {
        DespatchClearanceDetailsManager  objDespatchClearanceDetailsManager = new DespatchClearanceDetailsManager();
        Vector v = new Vector();
        v.add(new Integer(4));
        objDespatchClearanceDetailsManager.postDespatchDetails(v);
    }
    
    
    
    
}

/*
*$Log: DespatchClearanceDetailsManager.java,v $
*Revision 1.8  2005/07/27 07:02:16  kduraisamy
*IF DEBUG.DMODE ADDED.
*
*Revision 1.7  2005/07/26 15:57:37  kduraisamy
*try is taken out of for loop in addNewDetails().
*
*Revision 1.6  2005/07/23 08:50:27  kduraisamy
*indentation.
*
*Revision 1.5  2005/07/20 20:16:35  kduraisamy
*indentation.
*
*Revision 1.4  2005/07/20 20:15:13  kduraisamy
*indentation.
*
*Revision 1.3  2005/07/20 20:08:55  kduraisamy
*make valid invalid methods added.
*
*Revision 1.2  2005/07/20 12:25:01  kduraisamy
*filter method completed.
*
*Revision 1.1  2005/07/20 11:56:10  kduraisamy
*INITIAL COMMIT.
*
*
*/