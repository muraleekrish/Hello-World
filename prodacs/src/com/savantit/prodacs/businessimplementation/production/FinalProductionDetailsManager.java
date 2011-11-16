/*
 * Created on Jul 15, 2005
 *
 * ClassName	:  	FinalProductionDetailsManager.java
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
public class FinalProductionDetailsManager
{
    static Logger logger = Logger.getLogger(FinalProductionDetailsManager.class);
    public FinalProductionDetailsManager()
    {
        logger.addAppender(LoggerOutput.getAppender());
        logger.setLevel(ProdacsConfig.LOGGER_LEVEL);
    }
    
    public boolean addNewFinalProductionDetails(FinalProductionDetails[] objFinalProductionDetailsList) throws ProductionException, SQLException
    {
        
        int result = 0;
        int finalProdId = 0;
        int empId = 0;
        boolean addRESULT = false;
        PreparedStatement ps = null;
        int startOpn = 0;
        int endOpn = 0;
        Hashtable ht_FinalProdn_Add = new Hashtable();
        Hashtable ht_EmpHrs_Add = new Hashtable();
        Vector vec_EmpHrs = new Vector();
        DBConnection con = null;
        
        logger.info("FINAL PRODUCTION ADD STARTS");
        
        if(BuildConfig.DMODE)
            System.out.println("ADD FINAL PRODUCTION DETAILS");
        
        if(objFinalProductionDetailsList.length == 0)
        {
            logger.error("FINAL PRODUCTION DETAILS OBJ NULL");
            throw new ProductionException("PMEC001","FINAL PRODUCTION DETAILS OBJ NULL","");
        }
        
        con = DBConnectionFactory.getConnection();
        con.startTransaction();
        try
        {
            
            for(int i = 0;i<objFinalProductionDetailsList.length;i++)
            {
                startOpn = objFinalProductionDetailsList[i].getFinalProdStartOpn();
                endOpn = objFinalProductionDetailsList[i].getFinalProdEndOpn();
                ht_FinalProdn_Add.put("FPROD_CRNT_DATE",objFinalProductionDetailsList[i].getFinalCrntDate());
                ht_FinalProdn_Add.put("SHIFT_ID",new Integer(objFinalProductionDetailsList[i].getShiftId()));
                ht_FinalProdn_Add.put("WOJB_ID",new Integer(objFinalProductionDetailsList[i].getWoJbId()));
                ht_FinalProdn_Add.put("FPROD_QTY_SNOS",objFinalProductionDetailsList[i].getFinalProdQtySnos());
                ht_FinalProdn_Add.put("FPROD_TOT_QTY",new Integer(objFinalProductionDetailsList[i].getFinalProdTotQty()));
                ht_FinalProdn_Add.put("FPROD_START_OPN",new Integer(startOpn));
                ht_FinalProdn_Add.put("FPROD_END_OPN",new Integer(endOpn));
                ht_FinalProdn_Add.put("FPROD_TOT_HRS",new Float(objFinalProductionDetailsList[i].getFinalProdTotHrs()));
                ht_FinalProdn_Add.put("FPROD_CREATEDBY",objFinalProductionDetailsList[i].getCreatedBy());
                //prodQtySnos = objFinalProductionDetailsList[i].getProdQtySnos();
                result = con.executeUpdateStatement(SQLMaster.FINAL_PRODN_DETAILS_ADD_SQL_QUERY,ht_FinalProdn_Add);
                if(result > 0)
                {
                    //to set WO_ISPROD_ENTERED to 1..
                    
                    con.executeUpdateStatement(SQLMaster.WORKORDER_JB_TABLE_UPDATE_SQL_QUERY,ht_FinalProdn_Add);
                    int j = 0;
                    ps = con.executeStatement(SQLMaster.FPROD_ID_SELECT_SQL_QUERY);
                    ResultSet rs_ProdId_Get = ps.executeQuery();
                    if(rs_ProdId_Get.next())
                    {
                        finalProdId = rs_ProdId_Get.getInt(1);
                        if(BuildConfig.DMODE)
                            System.out.println("FPROD ID :"+finalProdId);
                    }
                    rs_ProdId_Get.close();
                    ps.close();
                    vec_EmpHrs = objFinalProductionDetailsList[i].getProdnEmpHrsDetails();
                    
                    
                    for(j = 0;j<vec_EmpHrs.size();j++)
                    {
                        
                        EmployeeDtyOtDetails objEmployeeDtyOtDetails = new EmployeeDtyOtDetails();
                        objEmployeeDtyOtDetails = (EmployeeDtyOtDetails)vec_EmpHrs.elementAt(j);
                        empId = objEmployeeDtyOtDetails.getEmpId();
                        
                        ht_EmpHrs_Add.put("FPROD_ID",new Integer(finalProdId));
                        ht_EmpHrs_Add.put("EMP_ID",new Integer(empId));
                        ht_EmpHrs_Add.put("FPROD_MODIFYCOUNT",new Integer(0));
                        result = con.executeUpdateStatement(SQLMaster.FINAL_PRODN_EMPHRS_ADD_SQL_QUERY,ht_EmpHrs_Add);
                        if(result>0)
                        {
                            if(BuildConfig.DMODE)
                                System.out.println("Employee Hrs Details Added");
                            
                        }
                        
                        
                    }
                }
                
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
                if(sqle.toString().indexOf("PK_FPRODID")>=0)
                {
                    throw new ProductionException("PMEC002","FINAL PRODUCTION ID IS UNIQUE.UNIQUE CONSTRAINT VIOLATED",sqle.toString());
                }
                else if (sqle.toString().indexOf("FK_FPROD_SHIFTID") >= 0)
                {
                    throw new ProductionException("PMEC007","PARENT KEY SHIFT ID NOT FOUND",sqle.toString());
                }
                else if (sqle.toString().indexOf("FK_FPROD_WOJBID") >= 0)
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
        
        
        
        logger.info("FINAL PRODUCTION ADD ENDS");
        
        
        return addRESULT;
    }
    
    public HashMap getAllFinalProductionDetails(Filter filters[],String sortBy,boolean ascending,int startIndex,int displayCount) throws ProductionException,SQLException
    {
        HashMap hm_Result = new HashMap();
        Vector vec_Prod_Det = new Vector();
        DBConnection con=null;
        
        logger.info("GET ALL FINAL PRODUCTION DETAILS FILTER STARTS");
        
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
            tot_Rec_Cnt = con.getRowCountWithComplexFilters(filters, SQLMaster.GET_ALL_FINAL_PRODUCTION_DETAILS_FILTER_QUERY);
            
            //Finding end index for the query
            int eIndex = startIndex + displayCount;
            
            
            // filters,tablename,startindex,endindex,sorting column name and sortorder are passed to the function and  query will be received
            String Query = con.buildDynamicQuery(filters,startIndex,eIndex,sortBy,ascending,SQLMaster.GET_ALL_FINAL_PRODUCTION_DETAILS_FILTER_QUERY);
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
                FinalProductionDetails objFinalProductionDetails = new FinalProductionDetails();
                objFinalProductionDetails.setFinalProdId(rs_Prodn_Details_Get.getInt("FPROD_ID"));
                objFinalProductionDetails.setFinalCrntDate(rs_Prodn_Details_Get.getTimestamp("FPROD_CRNT_DATE"));
                objFinalProductionDetails.setShiftId(rs_Prodn_Details_Get.getInt("SHIFT_ID"));
                objFinalProductionDetails.setShiftName(rs_Prodn_Details_Get.getString("SHIFT_NAME"));
                objFinalProductionDetails.setWoNo(rs_Prodn_Details_Get.getString("WO_NO"));
                //objFinalProductionDetails.setProdQtySnos(rs_Prodn_Details_Get.getString("PROD_QTY_SNOS"));
                //objFinalProductionDetails.setProdTotQty(rs_Prodn_Details_Get.getInt("PROD_TOT_QTY"));
                //objFinalProductionDetails.setProdStartOpn(rs_Prodn_Details_Get.getInt("PROD_START_OPN"));
                //objFinalProductionDetails.setProdEndOpn(rs_Prodn_Details_Get.getInt("PROD_END_OPN"));
                ProductionJobDetails objProductionJobDetails = new ProductionJobDetails();
                objProductionJobDetails.setJobName(rs_Prodn_Details_Get.getString("JB_NAME"));
                //objProductionJobDetails.setDwgNo(rs_Prodn_Details_Get.getString("JB_DWG_NO"));
                //objProductionJobDetails.setRvsnNo(rs_Prodn_Details_Get.getString("JB_RVSN_NO"));
                //objProductionJobDetails.setMatlType(rs_Prodn_Details_Get.getString("JB_MATL_TYP"));
                
                objFinalProductionDetails.setObjProductionJobDetails(objProductionJobDetails);
                
                objFinalProductionDetails.setFinalProdDateStamp(rs_Prodn_Details_Get.getTimestamp("FPROD_DATESTAMP"));
                objFinalProductionDetails.setFinalProdIsValid(rs_Prodn_Details_Get.getInt("FPROD_ISVALID"));
                vec_Prod_Det.addElement(objFinalProductionDetails);
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
        
        logger.info("GET ALL FINAL PRODUCTION DETAILS FILTER ENDS");
        
        return hm_Result;
    }
    
    
    public boolean updateFinalProductionDetails(FinalProductionDetails objFinalProductionDetails) throws ProductionException, SQLException
    {
        boolean updateRESULT = false;
        int finalProdId = 0;
        DBConnection con=null;
        Hashtable ht_EmpHrs_Del = new Hashtable();
        Hashtable ht_Prod_Update = new Hashtable();
        Hashtable ht_EmpHrs_Add = new Hashtable();
        Vector vec_EmpHrs = new Vector();
        int result;
        int empId = 0;
        
        //delete FPROD_EMP table.
        finalProdId = objFinalProductionDetails.getFinalProdId();
        
        con = DBConnectionFactory.getConnection();
        con.startTransaction();
        ht_EmpHrs_Del.put("FPROD_ID",new Integer(finalProdId));
        try
        {
            //to store Emp Hours entries into Log..
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
            //Updation of F_PROD table.
            
            ht_Prod_Update.put("FPROD_ID",new Integer(finalProdId));
            ht_Prod_Update.put("FPROD_CRNT_DATE",objFinalProductionDetails.getFinalCrntDate());
            ht_Prod_Update.put("SHIFT_ID",new Integer(objFinalProductionDetails.getShiftId()));
            ht_Prod_Update.put("WOJB_ID",new Integer(objFinalProductionDetails.getWoJbId()));
            ht_Prod_Update.put("FPROD_QTY_SNOS",objFinalProductionDetails.getFinalProdQtySnos());
            ht_Prod_Update.put("FPROD_TOT_QTY",new Integer(objFinalProductionDetails.getFinalProdTotQty()));
            ht_Prod_Update.put("FPROD_START_OPN",new Integer(objFinalProductionDetails.getFinalProdStartOpn()));
            ht_Prod_Update.put("FPROD_END_OPN",new Integer(objFinalProductionDetails.getFinalProdEndOpn()));
            ht_Prod_Update.put("FPROD_TOT_HRS",new Float(objFinalProductionDetails.getFinalProdTotHrs()));
            ht_Prod_Update.put("FPROD_POST_FLG","0");
            ht_Prod_Update.put("FPROD_CREATEDBY",objFinalProductionDetails.getModifiedBy());
            
            // to store F_Prod Entries to Log..
            
            result = con.executeUpdateStatement(SQLMaster.FINAL_PRODN_DETAILS_LOG_ADD_SQL_QUERY,ht_Prod_Update);
            
            if(result > 0)
            {
                con.executeUpdateStatement(SQLMaster.FINAL_PRODN_DETAILS_UPDATE_SQL_QUERY,ht_Prod_Update);
                PreparedStatement ps_ModifyCnt_Sel = con.executeStatement(SQLMaster.FINAL_PROD_MODIFY_COUNT_SELECT,ht_Prod_Update);
                ResultSet rs_ModifyCnt_Sel = ps_ModifyCnt_Sel.executeQuery();
                int modifyCount = 0;
                if(rs_ModifyCnt_Sel.next())
                {
                    modifyCount = rs_ModifyCnt_Sel.getInt("FPROD_MODIFYCOUNT");
                }
                ht_Prod_Update.put("FPROD_MODIFYCOUNT",new Integer(modifyCount));
                
                vec_EmpHrs = objFinalProductionDetails.getProdnEmpHrsDetails();
                ht_EmpHrs_Add.put("FPROD_MODIFYCOUNT",new Integer(modifyCount));
                
                for(int i = 0;i<vec_EmpHrs.size();i++)
                {
                    EmployeeDtyOtDetails objEmployeeDtyOtDetails = new EmployeeDtyOtDetails();
                    objEmployeeDtyOtDetails = (EmployeeDtyOtDetails)vec_EmpHrs.elementAt(i);
                    empId = objEmployeeDtyOtDetails.getEmpId();
                    ht_EmpHrs_Add.put("FPROD_ID",new Integer(finalProdId));
                    ht_EmpHrs_Add.put("EMP_ID",new Integer(empId));
                    result = con.executeUpdateStatement(SQLMaster.FINAL_PRODN_EMPHRS_ADD_SQL_QUERY,ht_EmpHrs_Add);
                    if(result>0)
                    {
                        if(BuildConfig.DMODE)
                            System.out.println("Employee Hrs Details Added");
                        
                        
                    }
                    
                }
                
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
                if(sqle.toString().indexOf("PK_FPRODID")>=0)
                {
                    throw new ProductionException("PMEC002","FINAL PRODUCTION ID IS UNIQUE.UNIQUE CONSTRAINT VIOLATED",sqle.toString());
                }
                else if (sqle.toString().indexOf("FK_FPROD_SHIFTID") >= 0)
                {
                    throw new ProductionException("PMEC007","PARENT KEY SHIFT ID NOT FOUND",sqle.toString());
                }
                else if (sqle.toString().indexOf("FK_FPROD_WOJBID") >= 0)
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
        
        
        logger.info("PRODUCTION UPDATE ENDS");
        
        return updateRESULT;
    }
    
    public FinalProductionDetails getFinalProductionDetails(int finalProdId) throws ProductionException,SQLException
    {
        FinalProductionDetails objFinalProductionDetails = new FinalProductionDetails();
        ProductionJobDetails objProductionJobDetails = new ProductionJobDetails();
        Hashtable ht_ProdDet_Get = new Hashtable();
        Vector vec_Emp_Det = new Vector();
        DBConnection con = null;
        
        logger.info("GET FINAL PRODUCTION DETAILS STARTS");
        
        con = DBConnectionFactory.getConnection();
        
        ht_ProdDet_Get.put("FPROD_ID", new Integer(finalProdId));
        try
        {
            PreparedStatement ps_ProdDet_Get = con.executeStatement(SQLMaster.GET_FINAL_PROD_DETAILS_SQL_QUERY,ht_ProdDet_Get);
            ResultSet rs_ProdDet_Get = ps_ProdDet_Get.executeQuery();
            
            if(rs_ProdDet_Get.next())
            {
                //set Production Details //
                objFinalProductionDetails.setFinalProdId(finalProdId);
                objFinalProductionDetails.setFinalCrntDate(rs_ProdDet_Get.getTimestamp("FPROD_CRNT_DATE"));
                objFinalProductionDetails.setShiftId(rs_ProdDet_Get.getInt("SHIFT_ID"));
                objFinalProductionDetails.setShiftName(rs_ProdDet_Get.getString("SHIFT_NAME"));
                objFinalProductionDetails.setWoJbId(rs_ProdDet_Get.getInt("WOJB_ID"));
                objFinalProductionDetails.setWoNo(rs_ProdDet_Get.getString("WO_NO"));
                objFinalProductionDetails.setWoId(rs_ProdDet_Get.getInt("WO_ID"));
                objFinalProductionDetails.setFinalProdQtySnos(rs_ProdDet_Get.getString("FPROD_QTY_SNOS"));
                objFinalProductionDetails.setFinalProdTotQty(rs_ProdDet_Get.getInt("FPROD_TOT_QTY"));
                objFinalProductionDetails.setFinalProdStartOpn(rs_ProdDet_Get.getInt("FPROD_START_OPN"));
                objFinalProductionDetails.setFinalProdEndOpn(rs_ProdDet_Get.getInt("FPROD_END_OPN"));
                objFinalProductionDetails.setFinalProdTotHrs(rs_ProdDet_Get.getFloat("FPROD_TOT_HRS"));
                objFinalProductionDetails.setFinalProdPostFlg(rs_ProdDet_Get.getString("FPROD_POST_FLG").equals("1")?true:false);
                objFinalProductionDetails.setModifyCount(rs_ProdDet_Get.getInt("FPROD_MODIFYCOUNT"));
                objFinalProductionDetails.setCreatedBy(rs_ProdDet_Get.getString("FPROD_CREATEDBY"));
                objFinalProductionDetails.setFinalProdDateStamp(rs_ProdDet_Get.getTimestamp("FPROD_DATESTAMP"));
                objFinalProductionDetails.setFinalProdIsValid(rs_ProdDet_Get.getInt("FPROD_ISVALID"));
                
                ///set Job Details - starts///
                //objProductionJobDetails.setJobId(rs_ProdDet_Get.getInt("JB_ID"));
                objProductionJobDetails.setJobName(rs_ProdDet_Get.getString("JB_NAME"));
                objProductionJobDetails.setDwgNo(rs_ProdDet_Get.getString("JB_DWG_NO"));
                objProductionJobDetails.setRvsnNo(rs_ProdDet_Get.getString("JB_RVSN_NO"));
                objProductionJobDetails.setMatlType(rs_ProdDet_Get.getString("JB_MATL_TYP"));
                
                objFinalProductionDetails.setObjProductionJobDetails(objProductionJobDetails);
                //set Job Details - Ends///
                
                //get All Employee Details of the Above Production Record
                PreparedStatement ps_ProdEmp_Det = con.executeStatement(SQLMaster.GET_FINAL_PROD_EMP_DETAILS_SQL_QUERY,ht_ProdDet_Get);
                ResultSet rs_ProdEmp_Det = ps_ProdEmp_Det.executeQuery();
                
                while(rs_ProdEmp_Det.next())
                {
                    EmployeeDtyOtDetails objEmployeeDtyOtDetails = new EmployeeDtyOtDetails();
                    objEmployeeDtyOtDetails.setEmpId(rs_ProdEmp_Det.getInt("EMP_ID"));
                    objEmployeeDtyOtDetails.setEmpName(rs_ProdEmp_Det.getString("EMP_NAME"));
                    objEmployeeDtyOtDetails.setEmpCode(rs_ProdEmp_Det.getString("EMP_CDE"));
                    objEmployeeDtyOtDetails.setEmpTypdId(rs_ProdEmp_Det.getInt("EMP_TYP_ID"));
                    objEmployeeDtyOtDetails.setEmpType(rs_ProdEmp_Det.getString("EMP_TYP_NAME"));
                    
                    //Add Each Employee Details into Vector
                    vec_Emp_Det.addElement(objEmployeeDtyOtDetails);
                    
                    
                }
                //set Employee Details Vector into Prod Object
                objFinalProductionDetails.setProdnEmpHrsDetails(vec_Emp_Det);
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
        
        logger.info("GET FINAL PRODUCTION DETAILS ENDS");
        
        return objFinalProductionDetails;
    }
    
    public Vector viewUnPostedFinalProductionDetails(Date fromDate,Date toDate) throws SQLException, ProductionException
    {
        Vector vec_Result = new Vector();
        DBConnection con = null;
        Hashtable ht_UnPostedJbDet_Get = new Hashtable();
        Hashtable ht_UnPostedDet_Get = new Hashtable();
        int finalProdId = 0;
        int woJbId = 0;
        
        
        logger.info("GET UNPOSTED FINAL PRODN DETAILS STARTS");
        
        
        con = DBConnectionFactory.getConnection();
        ht_UnPostedDet_Get.put("FROM_DATE",fromDate);
        ht_UnPostedDet_Get.put("TO_DATE",toDate);
        try
        {
            PreparedStatement ps_UnpostedProdId_Get = con.executeStatement(SQLMaster.UNPOSTED_FINAL_PROD_ID_SELECT_SQL_QUERY,ht_UnPostedDet_Get);
            ResultSet rs_UnpostedProdId_Get = ps_UnpostedProdId_Get.executeQuery();
            while(rs_UnpostedProdId_Get.next())
            {
                UnPostedProductionDetails objUnPostedProductionDetails = new UnPostedProductionDetails();
                finalProdId = rs_UnpostedProdId_Get.getInt("FPROD_ID");
                objUnPostedProductionDetails.setProdId(finalProdId);
                objUnPostedProductionDetails.setProdCrntDate(rs_UnpostedProdId_Get.getTimestamp("FPROD_CRNT_DATE"));
                objUnPostedProductionDetails.setShiftName(rs_UnpostedProdId_Get.getString("SHIFT_NAME"));
                objUnPostedProductionDetails.setProdStartOpn(rs_UnpostedProdId_Get.getInt("FPROD_START_OPN"));
                objUnPostedProductionDetails.setProdEndOpn(rs_UnpostedProdId_Get.getInt("FPROD_END_OPN"));
                objUnPostedProductionDetails.setProdTotHrs(rs_UnpostedProdId_Get.getFloat("FPROD_TOT_HRS"));
                objUnPostedProductionDetails.setJobQtySnos(rs_UnpostedProdId_Get.getString("FPROD_QTY_SNOS"));
                objUnPostedProductionDetails.setJobQty(rs_UnpostedProdId_Get.getInt("FPROD_TOT_QTY"));
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
        
        
        logger.info("GET UNPOSTED FINAL PRODN DETAILS ENDS");
        
        
        return vec_Result;
    }
    public HashMap postFinalProductionDetails(Vector vec_ProdIds) throws ProductionException, SQLException
    {
        
        int finalProdId = 0;
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
        
        
        logger.info("POST FINAL PRODUCTION DETAILS STARTS");
        
        if(vec_ProdIds == null)
        {
            throw new ProductionException("PMEC005","PRODUCTION IDS VECTOR OBJECT IS NULL","");
        }
        try
        {
            con = DBConnectionFactory.getConnection();
            con.startTransaction();
            for(int i = 0;i<vec_ProdIds.size();i++)
            {
                finalProdId = ((Integer)vec_ProdIds.elementAt(i)).intValue();
                ht_PostProdn.put("FPROD_ID",new Integer(finalProdId));
                int result = con.executeUpdateStatement(SQLMaster.FINAL_PRODN_DETAILS_POST_SQL_QUERY,ht_PostProdn);
                if(result>=1)
                {
                    hm_Result.put(vec_ProdIds.get(i),new Integer(0));
                    logger.info(vec_ProdIds.get(i)+" : FINAL PRODUCTION RECORD POSTED");
                    
                    PreparedStatement ps_ProdnDet = con.executeStatement(SQLMaster.FINAL_PRODN_DETAILS_SELECT_SQL_QUERY,ht_PostProdn);
                    ResultSet rs_ProdnDet = ps_ProdnDet.executeQuery();
                    if(rs_ProdnDet.next())
                    {
                        woJbId = rs_ProdnDet.getInt("WOJB_ID");
                        qtySnos = rs_ProdnDet.getString("FPROD_QTY_SNOS");
                        //totQty = rs_ProdnDet.getInt("PROD_TOT_QTY");
                        startOpn = rs_ProdnDet.getInt("FPROD_START_OPN");
                        endOpn = rs_ProdnDet.getInt("FPROD_END_OPN");
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
        
        logger.info("POST FINAL PRODUCTION DETAILS ENDS");
        
        return hm_Result;
        
    }
    
    
    public Vector getFinalProdnJobOperationDetails(int woJbId) throws ProductionException,SQLException
    {
        Hashtable ht_ProdnJbOpn_Get = new Hashtable();
        Hashtable ht_JbStatSno_Get = new Hashtable();
        Hashtable ht_JbPstdStatSno_Get = new Hashtable();
        ResultSet rs_ProdnJbOpn_Get = null;
        Vector vec_Result = new Vector();
        DBConnection con=null;
        SortString ss = new SortString();
        
        logger.info("GET FINAL PRODUCTION JOB OPERATION DETAILS STARTS");
        
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
                
                ps_Prod_Det = con.executeStatement(SQLMaster.GET_FINAL_PRODDETAILS_SQL_QUERY,ht_ProdnJbOpn_Get);
                rs_Prod_Det = ps_Prod_Det.executeQuery();
                String totOpns = "";
                while(rs_Prod_Det.next())
                {
                    String qtySnos = "";
                    qtySnos = rs_Prod_Det.getString("FPROD_QTY_SNOS");
                    StringTokenizer st = new StringTokenizer(qtySnos.trim(),",");
                    while(st.hasMoreTokens())
                    {
                        int sno = Integer.parseInt(st.nextToken().trim());
                        if(sno == jbQtySno)
                        {
                            int stOpn = rs_Prod_Det.getInt("FPROD_START_OPN");
                            int endOpn = rs_Prod_Det.getInt("FPROD_END_OPN");
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
                PreparedStatement ps_PendStat_OpnSnos = con.executeStatement(SQLMaster.GET_PENDING_FINAL_PRODN_JOBSTAT_OPERATION_SNOS_SQL_QUERY,ht_JbStatSno_Get);
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
                
                PreparedStatement ps_PostedStat_OpnSnos = con.executeStatement(SQLMaster.GET_POSTED_FINAL_PRODN_JOBSTAT_OPERATION_SNOS_SQL_QUERY,ht_JbPstdStatSno_Get);
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
            logger.info("GET PRODUCTION JOB OPERATION DETAILS ENDS");
        }
        return vec_Result;
    }
    
    public Vector getFinalProdnJobOperationDetailsForUpdate(int woJbId,int fprodId) throws ProductionException,SQLException
    {
        Hashtable ht_ProdnJbOpn_Get = new Hashtable();
        Hashtable ht_JbStatSno_Get = new Hashtable();
        Hashtable ht_JbPstdStatSno_Get = new Hashtable();
        ResultSet rs_ProdnJbOpn_Get = null;
        Vector vec_Result = new Vector();
        DBConnection con=null;
        SortString ss = new SortString();
        
        logger.info("GET FINAL PRODUCTION JOB OPERATION DETAILS STARTS");
        
        con = DBConnectionFactory.getConnection();
        ht_ProdnJbOpn_Get.put("WOJB_ID",new Integer(woJbId));
        ht_ProdnJbOpn_Get.put("FPROD_ID",new Integer(fprodId));
        
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
                
                ps_Prod_Det = con.executeStatement(SQLMaster.GET_FINAL_PRODDETAILS_FOR_UPDATE_SQL_QUERY,ht_ProdnJbOpn_Get);
                rs_Prod_Det = ps_Prod_Det.executeQuery();
                String totOpns = "";
                while(rs_Prod_Det.next())
                {
                    String qtySnos = "";
                    qtySnos = rs_Prod_Det.getString("FPROD_QTY_SNOS");
                    StringTokenizer st = new StringTokenizer(qtySnos.trim(),",");
                    while(st.hasMoreTokens())
                    {
                        int sno = Integer.parseInt(st.nextToken().trim());
                        if(sno == jbQtySno)
                        {
                            int stOpn = rs_Prod_Det.getInt("FPROD_START_OPN");
                            int endOpn = rs_Prod_Det.getInt("FPROD_END_OPN");
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
                PreparedStatement ps_PendStat_OpnSnos = con.executeStatement(SQLMaster.GET_PENDING_FINAL_PRODN_JOBSTAT_OPERATION_SNOS_SQL_QUERY,ht_JbStatSno_Get);
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
                PreparedStatement ps_PostedStat_OpnSnos = con.executeStatement(SQLMaster.GET_POSTED_FINAL_PRODN_JOBSTAT_OPERATION_SNOS_SQL_QUERY,ht_JbPstdStatSno_Get);
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
        
        logger.info("GET FINAL PRODUCTION JOB OPERATION DETAILS ENDS");
        
        return vec_Result;
    }
    
    public HashMap getAllWorkOrderJobStatus(Filter filters[],String sortBy,boolean ascending,int startIndex,int displayCount) throws ProductionException,SQLException
    {
        HashMap hm_Result = new HashMap();
        Hashtable ht = new Hashtable();
        Vector vec_Prod_Det = new Vector();
        DBConnection con=null;
        
        logger.info("GET ALL WORK ORDER JOB QTY STATUS FILTER STARTS");
        
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
            tot_Rec_Cnt = con.getRowCountWithComplexFilters(filters, SQLMaster.GET_ALL_WO_JB_STATUS_DETAILS_FILTER_QUERY);
            
            //Finding end index for the query
            int eIndex = startIndex + displayCount;
            
            
            // filters,tablename,startindex,endindex,sorting column name and sortorder are passed to the function and  query will be received
            String Query = con.buildDynamicQuery(filters,startIndex,eIndex,sortBy,ascending,SQLMaster.GET_ALL_WO_JB_STATUS_DETAILS_FILTER_QUERY);
            if(BuildConfig.DMODE)
            {
                // total records and query
                System.out.println("TOTAL NUMBER OF RECORDS : " + tot_Rec_Cnt);
                System.out.println("QUERY : " + Query);
            }
            
            logger.info("Total Records : " + tot_Rec_Cnt);
            logger.info("Query : " + Query);
            
            
            ResultSet rs_Prodn_Details_Get =  con.executeRownumStatement(Query);
            String jbQtyStatus = "";
            int woJbStatId = 0;
            while(rs_Prodn_Details_Get.next())
            {
                WorkOrderJobStatusDetails objWorkOrderJobStatusDetails = new WorkOrderJobStatusDetails();
                objWorkOrderJobStatusDetails.setWorkOrderNo(rs_Prodn_Details_Get.getString("WO_NO"));
                objWorkOrderJobStatusDetails.setWoDate(rs_Prodn_Details_Get.getTimestamp("WO_DATE"));
                objWorkOrderJobStatusDetails.setJobName(rs_Prodn_Details_Get.getString("JB_NAME"));
                objWorkOrderJobStatusDetails.setJobDrwgNo(rs_Prodn_Details_Get.getString("JB_DWG_NO"));
                objWorkOrderJobStatusDetails.setJobRvsnNo(rs_Prodn_Details_Get.getString("JB_RVSN_NO"));
                objWorkOrderJobStatusDetails.setJobMatlType(rs_Prodn_Details_Get.getString("JB_MATL_TYP"));
                objWorkOrderJobStatusDetails.setJobQtySno(rs_Prodn_Details_Get.getInt("WOJBSTAT_SNO"));
                woJbStatId = rs_Prodn_Details_Get.getInt("WOJBSTAT_ID");
                objWorkOrderJobStatusDetails.setWoJbStatId(woJbStatId);
                jbQtyStatus = rs_Prodn_Details_Get.getString("WOJBSTAT_SNO_STAT");
                if(jbQtyStatus.equalsIgnoreCase("O"))
                {
                    objWorkOrderJobStatusDetails.setOrderTaken(true);
                }
                else
                {
                    if(jbQtyStatus.equalsIgnoreCase("C"))
                    {
                        objWorkOrderJobStatusDetails.setOrderTaken(true);
                        objWorkOrderJobStatusDetails.setWorkInProcess(true);
                        objWorkOrderJobStatusDetails.setDespatchClearance(true);
                        objWorkOrderJobStatusDetails.setShipment(true);
                    }
                    else if(jbQtyStatus.equalsIgnoreCase("A"))
                    {
                        ht.put("WOJBSTAT_ID",new Integer(woJbStatId));
                        PreparedStatement ps = con.executeStatement(SQLMaster.CHECK_DESPATCH_CLEARANCE_OPN_SQL_QUERY,ht);
                        ResultSet rs = ps.executeQuery();
                        if(rs.next())
                        {
                            int count = rs.getInt("COUNT");
                            if(count > 0)
                            {
                                objWorkOrderJobStatusDetails.setOrderTaken(true);
                                objWorkOrderJobStatusDetails.setWorkInProcess(true);
                            }
                            else
                            {
                                objWorkOrderJobStatusDetails.setOrderTaken(true);
                                objWorkOrderJobStatusDetails.setWorkInProcess(true);
                                objWorkOrderJobStatusDetails.setDespatchClearance(true);
                            }
                        }
                        rs.close();
                        ps.close();
                        
                    }
                }
                vec_Prod_Det.addElement(objWorkOrderJobStatusDetails);    
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
        
        logger.info("GET ALL FINAL PRODUCTION DETAILS FILTER ENDS");
        
        return hm_Result;
    }
    public boolean isModifyForFprod(int fProdId) throws ProductionException, SQLException
    {
        boolean retVal = false;
        DBConnection con = null;
        Hashtable ht_Prod = new Hashtable();
        PreparedStatement ps = null;
        int cnt = 0;
        try
        {
            con = DBConnectionFactory.getConnection();
            ht_Prod.put("FPROD_ID",new Integer(fProdId));
            ps = con.executeStatement(SQLMaster.CHECK_FPROD_FOR_MODIFY_SQL_QUERY,ht_Prod);
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
    
    public HashMap makeFinalProductionValid(Vector finalProdIds) throws ProductionException, SQLException
    {
        int result = 0;
        DBConnection con = null;
        HashMap hm_Result = new HashMap();
        
        logger.info("MAKE FINAL PRODUCTION VALID STARTS");
        
        if(finalProdIds == null)
        {
            logger.error("FINAL PRODUCTION VECTOR OBJECT IS NULL");
            throw new ProductionException("PREC00","FINAL PRODUCTION VECTOR OBJECT IS NULL","");
        }
        try
        {
            con = DBConnectionFactory.getConnection();
            con.startTransaction();
            for(int i=0;i<finalProdIds.size();i++)
            {
                Hashtable ht_Prod_Id = new Hashtable();
                
                
                ht_Prod_Id.put("FPROD_ID",(Integer)finalProdIds.get(i));
                
                result = con.executeUpdateStatement(SQLMaster.FPROD_MAKE_VALID_SQL_QUERY,ht_Prod_Id);
                
                if(result>=1)
                {
                    con.commitTransaction();
                    hm_Result.put(finalProdIds.get(i),new Integer(0));
                    logger.info(finalProdIds.get(i)+" : FINAL PRODUCTION RECORD VALIDATED");
                    
                }
                else
                {
                    con.rollBackTransaction();
                    hm_Result.put(finalProdIds.get(i),new Integer(1));
                    logger.info(finalProdIds.get(i)+" : FINAL PRODUCTION RECORD NOT VALIDATED");
                    
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
        
        logger.info("MAKE FINAL PRODUCTION VALID ENDS");
        
        return hm_Result;
        
    }
    
    public HashMap makeFinalProductionInValid(Vector finalProdIds) throws ProductionException, SQLException
    {
        int result = 0;
        DBConnection con = null;
        HashMap hm_Result = new HashMap();
        
        logger.info("MAKE FINAL PRODUCTION INVALID STARTS");
        
        if(finalProdIds == null)
        {
            logger.error("FINAL PRODUCTION VECTOR OBJECT IS NULL");
            throw new ProductionException("PREC00","FINAL PRODUCTION VECTOR OBJECT IS NULL","");
        }
        try
        {
            con = DBConnectionFactory.getConnection();
            con.startTransaction();
            for(int i=0;i<finalProdIds.size();i++)
            {
                Hashtable ht_Prod_Id = new Hashtable();
                
                ht_Prod_Id.put("FPROD_ID",(Integer)finalProdIds.get(i));
                
                result = con.executeUpdateStatement(SQLMaster.FPROD_MAKE_INVALID_SQL_QUERY,ht_Prod_Id);
                
                if(result>=1)
                {
                    con.commitTransaction();
                    hm_Result.put(finalProdIds.get(i),new Integer(0));
                    logger.info(finalProdIds.get(i)+" : FINAL PRODUCTION RECORD INVALIDATED");
                    
                }
                else
                {
                    con.rollBackTransaction();
                    hm_Result.put(finalProdIds.get(i),new Integer(1));
                    logger.info(finalProdIds.get(i)+" : FINAL PRODUCTION RECORD NOT INVALIDATED");
                    
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
        
        logger.info("MAKE FINAL PRODUCTION INVALID ENDS");
        
        return hm_Result;
        
    }
    
    
    public static void main(String args[]) throws ProductionException, SQLException
    {
        FinalProductionDetailsManager obj = new FinalProductionDetailsManager();
        //Vector v = obj.viewUnPostedFinalProductionDetails(new Date("01-jul-05"),new Date("20-jul-05"));
        //System.out.println("count :"+v.size());
        
        Vector v = obj.getFinalProdnJobOperationDetailsForUpdate(782,23);
        
        System.out.println(v.size());
        for (int i = 0;i < v.size();i++)
        {
            ProductionJobQtyDetails  objProductionJobQtyDetails = (ProductionJobQtyDetails)v.get(i);
            System.out.println("hai"+objProductionJobQtyDetails.getPendingOpnSnos());
            
        }
    }
    
}

/*
*$Log: FinalProductionDetailsManager.java,v $
*Revision 1.20  2005/08/05 06:47:04  kduraisamy
*EMP_CDE ADDED FOR F PROD VIEW.
*
*Revision 1.19  2005/08/01 04:38:05  kduraisamy
*wo order modification changed.
*
*Revision 1.18  2005/07/30 14:18:03  kduraisamy
*FPRO_TOT_HRS ADDED.
*
*Revision 1.17  2005/07/26 15:57:37  kduraisamy
*try is taken out of for loop in addNewDetails().
*
*Revision 1.16  2005/07/22 06:03:04  kduraisamy
*class cast exception in make valid invalid corrected.
*
*Revision 1.15  2005/07/22 05:27:23  vkrishnamoorthy
*Indentation.
*
*Revision 1.14  2005/07/20 20:15:13  kduraisamy
*indentation.
*
*Revision 1.13  2005/07/20 20:08:43  kduraisamy
*make valid invalid methods added.
*
*Revision 1.12  2005/07/20 12:25:59  kduraisamy
*prod_id is changed to fprod_id.
*
*Revision 1.11  2005/07/20 09:22:21  kduraisamy
*getProdnJobOperationDetails() name changed as getFinalProdn*().
*
*Revision 1.10  2005/07/20 07:43:34  kduraisamy
*indentation.
*
*Revision 1.9  2005/07/20 06:37:04  kduraisamy
*method isModifyForFprod() added.
*
*Revision 1.8  2005/07/18 10:20:34  kduraisamy
*VALID FIELD ADDED.
*
*Revision 1.7  2005/07/18 10:07:25  kduraisamy
*JB_DRWG NO IS CHANGED TO JB_DWG_NO.
*
*Revision 1.6  2005/07/18 09:06:22  kduraisamy
*field woJbstatId added in value object.
*
*Revision 1.5  2005/07/18 05:09:42  kduraisamy
*FPROD_ID SELECT QUERY ADDED.
*
*Revision 1.4  2005/07/16 10:28:47  kduraisamy
*work Orderjobstatus filter method query added.
*
*Revision 1.3  2005/07/15 20:02:10  kduraisamy
*indentation.
*
*Revision 1.2  2005/07/15 17:00:17  kduraisamy
*getProdnJobDetailsByWorkOrder() and getProdnJobDetailsForUpdateByWorkOrder() changed because of FINAL PRODUCTION.
*
*Revision 1.1  2005/07/15 07:40:37  kduraisamy
*initial commit.
*
*
*/