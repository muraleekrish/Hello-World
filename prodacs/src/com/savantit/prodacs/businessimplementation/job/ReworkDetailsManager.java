/*
 * Created on Nov 18, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.businessimplementation.job;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.savantit.prodacs.infra.beans.Filter;
import com.savantit.prodacs.infra.dbtools.DBConnection;
import com.savantit.prodacs.infra.dbtools.DBConnectionFactory;
import com.savantit.prodacs.infra.dbtools.SQLMaster;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.infra.util.LoggerOutput;
import com.savantit.prodacs.infra.util.ProdacsConfig;

/**
 * @author sduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ReworkDetailsManager
{
    static Logger logger = Logger.getLogger(ReworkDetailsManager.class);
    
    public ReworkDetailsManager()
    {
        logger.addAppender(LoggerOutput.getAppender());
        logger.setLevel(ProdacsConfig.LOGGER_LEVEL);
    }
    public boolean addNewReworkCategory(ReworkDetails objReworkDetails) throws ReworkException, SQLException
    {
        DBConnection con = null;
        boolean result = false;
        int res = 0;
        
        
        logger.info("ADD NEW REWORK CATEGORY STARTS");
        
        if(objReworkDetails.getRwk_Category().equals(""))
        {
            
            logger.error("REWORK DETAILS OBJECT IS NULL ");
            throw new ReworkException("","REWORK DETAILS OBJECT IS NULL ","");
        }
        
        if(objReworkDetails.getRwk_Category().equals(""))
        {
            
            logger.error("REWORK CATEGORY IS NULL ");
            throw new ReworkException("","REWORK CATEGORY IS NULL ","");
        }
        
        con = DBConnectionFactory.getConnection();
        con.startTransaction();
        
        Hashtable ht_Rwk_Catgry_Add = new Hashtable();
        ht_Rwk_Catgry_Add.put("RWK_CATGRY",objReworkDetails.getRwk_Category());
        try
        {
            res = con.executeUpdateStatement(SQLMaster.REWORK_CATEGORY_ADD_QUERY,ht_Rwk_Catgry_Add);
            if(res>=1)
            {
                result = true;
                con.commitTransaction();
                logger.info("REWORK CATEGORY ADDED");
            }
            else
            {
                result = false;
                con.rollBackTransaction();
                logger.info("REWORK CATEGORY NOT ADDED");
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            if(BuildConfig.DBASE==BuildConfig.ORACLE)
            {
                if(ex.toString().indexOf("UK_RWKCATGRY")>=0)
                {
                    throw new ReworkException("","REWORK CATEGORY ALREADY EXISTS",ex.toString());
                }
                else
                {
                    
                    logger.error("GENERAL SQL ERROR");
                    throw new ReworkException("RWKEC000","GENERAL SQL ERROR",ex.toString());
                }
            }
            if(BuildConfig.DBASE==BuildConfig.MYSQL)
            {
                if(ex.toString().indexOf("Duplicate entry")>=0)
                {
                    throw new ReworkException("","REWORK CATEGORY ALREADY EXISTS",ex.toString());
                }
                else
                {
                    
                    logger.error("GENERAL SQL ERROR");
                    throw new ReworkException("RWKEC000","GENERAL SQL ERROR",ex.toString());
                }
            }
        }
        finally
        {
            try
            {
                if(!con.getConnection().isClosed())
                {
                    con.closeConnection();
                }
            }
            catch(SQLException ex)
            {
                
                logger.error("EXCEPTION WHILE CLOSING CONNECTION", ex);
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }
        
        
        logger.info("ADD NEW REWORK CATEGORY ENDS");
        
        
        return result;
    }
    
    public LinkedHashMap getAllReworkCategories() throws ReworkException, SQLException
    {
        DBConnection con = null;
        LinkedHashMap hm_Result = new LinkedHashMap();
        ResultSet rs_Get_All_Rwk_Cat = null;
        PreparedStatement ps = null;
        
        logger.info("GET ALL REWORK CATEGORIES STARTED");
        
        
        try
        {
            con = DBConnectionFactory.getConnection();
            
            ps = con.executeStatement(SQLMaster.GET_ALL_REWORK_CATEGORY_QUERY);
            rs_Get_All_Rwk_Cat = ps.executeQuery();
            while(rs_Get_All_Rwk_Cat.next())
            {
                hm_Result.put(new Integer(rs_Get_All_Rwk_Cat.getInt("RWK_CATEGORY_ID")),rs_Get_All_Rwk_Cat.getString("RWK_CATEGORY"));
            }
            rs_Get_All_Rwk_Cat.close();
            ps.close();
        }
        catch(SQLException e)
        {
            
            logger.error("GENERAL EXCEPTION",e);
            throw new ReworkException("RWKEC000","GENERAL SQL ERROR",e.toString());
        }
        finally
        {
            try
            {
                if(!con.getConnection().isClosed())
                {
                    con.closeConnection();
                }
            }
            catch(SQLException ex)
            {
                
                logger.error("EXCEPTION WHILE CLOSING CONNECTION", ex);
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }
        
        logger.info("GET ALL REWORK CATEGORIES ENDS");
        
        return hm_Result;
    }
    
    public LinkedHashMap getAllReworkReasonsByCategory(int categryId) throws ReworkException, SQLException
    {
        DBConnection con = null;
        LinkedHashMap hm_Result = new LinkedHashMap();
        ResultSet rs_Get_All_Rwk_Rsn = null;
        PreparedStatement ps = null;
        Hashtable ht_RwkCatgryId = new Hashtable();
        
        
        logger.info("GET ALL REWORK REASONS STARTED");
        
        
        con = DBConnectionFactory.getConnection();
        ht_RwkCatgryId.put("RWK_CATEGORY_ID",new Integer(categryId));
        try
        {
            ps = con.executeStatement(SQLMaster.GET_ALL_REWORK_CATEGORY_REASON,ht_RwkCatgryId);
            rs_Get_All_Rwk_Rsn  = ps.executeQuery();
            while(rs_Get_All_Rwk_Rsn.next())
            {
                hm_Result.put(new Integer(rs_Get_All_Rwk_Rsn.getInt("RWK_ID")),rs_Get_All_Rwk_Rsn.getString("RWK_RSN"));
            }
            rs_Get_All_Rwk_Rsn.close();
            ps.close();
        }
        
        catch(SQLException ex)
        {
            
            logger.error("SQL ERROR",ex);
            throw new ReworkException("RWKEC000","GENERAL SQL ERROR",ex.toString());
        }
        
        finally
        {
            try
            {
                if(!con.getConnection().isClosed())
                {
                    con.closeConnection();
                }
            }
            catch(SQLException ex)
            {
                
                logger.error("EXCEPTION WHILE CLOSING CONNECTION", ex);
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }
        
        
        logger.info("GET ALL REWORK CATEGORIES ENDS");
        
        return hm_Result;
    }
    
    
    public boolean addNewReworkReason(ReworkDetails objReworkDetails) throws ReworkException, SQLException
    {
        
        DBConnection con = DBConnectionFactory.getConnection();
        boolean result = false;
        int res =0 ;
        Hashtable ht_Rwk_Rsn_Add = new Hashtable();
        
        
        logger.info("ADD NEW REWORK REASON STARTS");
        
        if(objReworkDetails == null)
        {
            
            logger.error("REWORK DETAILS OBJECT IS NULL ");
            throw new ReworkException("","REWORK DETAILS OBJECT IS NULL ","");
        }
        
        if(objReworkDetails.getRwk_Rsn().equals(""))
        {
            
            logger.error("REWORK REASON IS NULL ");
            throw new ReworkException("","REWORK REASON IS NULL ","");
        }
        
        con = DBConnectionFactory.getConnection();
        String st ="";
        ht_Rwk_Rsn_Add.put("RWK_CATGRY_ID",new Integer(objReworkDetails.getRwk_Category_Id()));
        ht_Rwk_Rsn_Add.put("RWK_REASON",(st = objReworkDetails.getRwk_Rsn()) == null ? "" : st);
        try
        {
            res = con.executeUpdateStatement(SQLMaster.REWORK_REASON_ADD_QUERY,ht_Rwk_Rsn_Add);
            if(res>=1)
            {
                result = true;
                
                logger.info("NEW REWORK REASON ADDED");
            }
            else
            {
                result = false;
                
                logger.error("NEW REWORK REASON NOT ADDED");
                
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            
            if(BuildConfig.DBASE==BuildConfig.ORACLE)
            {
                if(ex.toString().indexOf("PRODACS.FK_RWKCATGRYID")>=0)
                {
                    throw new ReworkException("","Parent Key Not Found - [RWK_CATEGORY_ID]",ex.toString());
                }
                else if(ex.toString().indexOf("PRODACS.UK_CATEGORYRSN")>=0)
                {
                    throw new ReworkException("","REASON ALREADY EXISTS FOR SELECTED CATEGORY",ex.toString());
                }
                else
                {
                    
                    logger.error("GENERAL SQL ERROR");
                    throw new ReworkException("RWKEC000","GENERAL SQL ERROR",ex.toString());
                }
            }
            if(BuildConfig.DBASE==BuildConfig.MYSQL)
            {
                if(ex.toString().indexOf("Duplicate entry")>=0)
                {
                    throw new ReworkException("","REASON ALREADY EXISTS FOR SELECTED CATEGORY",ex.toString());
                }
                if(ex.toString().indexOf("foreign key constraint")>=0)
                {
                    throw new ReworkException("","Parent Key Not Found - [RWK_CATEGORY_ID]",ex.toString());
                }
                else
                {
                    logger.error("GENERAL SQL ERROR");
                    throw new ReworkException("RWKEC000","GENERAL SQL ERROR",ex.toString());
                }
            }
            
            
        }
        finally
        {
            try
            {
                if(!con.getConnection().isClosed())
                {
                    con.closeConnection();
                }
            }
            catch(SQLException ex)
            {
                
                logger.error("EXCEPTION WHILE CLOSING CONNECTION", ex);
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }
        
        
        logger.info("ADD NEW REWORK REASON ENDS");
        
        return result;
    }
    
    public boolean updateReworkReason(ReworkDetails objReworkDetails) throws SQLException,ReworkException
    {
        DBConnection con = null;
        boolean result = false;
        int res =0 ;
        Hashtable ht_Rwk_Rsn_Update = new Hashtable();
        
        
        logger.info("UPDATE REWORK REASON STARTS");
        
        if(objReworkDetails == null)
        {
            
            logger.error("REWORK DETAILS OBJECT IS NULL ");
            throw new ReworkException("","REWORK DETAILS OBJECT IS NULL ","");
        }
        
        if(objReworkDetails.getRwk_Rsn().equals(""))
        {
            
            logger.error("REWORK REASON IS NULL ");
            throw new ReworkException("","REWORK REASON IS NULL ","");
        }
        
        try
        {
            con = DBConnectionFactory.getConnection();
            con.startTransaction();
            String st ="";
            
            ht_Rwk_Rsn_Update.put("RWK_ID",new Integer(objReworkDetails.getRwk_Id()));
            ht_Rwk_Rsn_Update.put("RWK_REASON",(st = objReworkDetails.getRwk_Rsn()) == null ? "" : st);
            try
            {
                res = con.executeUpdateStatement(SQLMaster.REWORK_REASON_UPDATE_QUERY,ht_Rwk_Rsn_Update);
                if(res>=1)
                {
                    result = true;
                    con.commitTransaction();
                    
                    logger.info("REWORK REASON UPDATED");
                }
                else
                {
                    result = false;
                    con.rollBackTransaction();
                    logger.error("REWORK REASON NOT UPDATED");
                    
                }
            }
            catch(SQLException ex)
            {
                con.rollBackTransaction();
                logger.error("GENERAL EXCEPTION",ex);
                if(BuildConfig.DMODE)
                    System.out.println("GENERAL EXCEPTION");
                if(BuildConfig.DBASE==BuildConfig.ORACLE)
                {
                    if(ex.toString().indexOf("PRODACS.FK_RWKCATGRYID")>=0)
                    {
                        throw new ReworkException("","Parent Key Not Found - [RWK_CATEGORY_ID]",ex.toString());
                    }
                    else if(ex.toString().indexOf("PRODACS.UK_CATEGORYRSN")>=0)
                    {
                        throw new ReworkException("","REASON ALREADY EXISTS FOR SELECTED CATEGORY",ex.toString());
                    }
                    else
                    {
                        
                        logger.error("GENERAL SQL ERROR");
                        throw new ReworkException("RWKEC000","GENERAL SQL ERROR",ex.toString());
                    }
                }
                if(BuildConfig.DBASE==BuildConfig.MYSQL)
                {
                    if(ex.toString().indexOf("Duplicate entry")>=0)
                    {
                        throw new ReworkException("","REASON ALREADY EXISTS FOR SELECTED CATEGORY",ex.toString());
                    }
                    if(ex.toString().indexOf("foreign key constraint")>=0)
                    {
                        throw new ReworkException("","Parent Key Not Found - [RWK_CATEGORY_ID]",ex.toString());
                    }
                    else
                    {
                        logger.error("GENERAL SQL ERROR");
                        throw new ReworkException("RWKEC000","GENERAL SQL ERROR",ex.toString());
                    }
                }
                
            }
            
        }
        finally
        {
            try
            {
                if(!con.getConnection().isClosed())
                {
                    con.closeConnection();
                }
            }
            catch(SQLException ex)
            {
                
                logger.error("CONNECTION ERROR : EXCEPTION WHILE CLOSING CONNECTION", ex);
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }
        
        
        logger.info("UPDATE REWORK REASON ENDS");
        
        return result;
    }
    
    public ReworkDetails getReworkDetails(int rwkId) throws SQLException,ReworkException
    {
        ReworkDetails obj_Rwk_Det = new ReworkDetails();
        DBConnection con = null;
        Hashtable ht_Rwk_Det = new Hashtable();
        ResultSet rs_Rwk_Det = null;
        PreparedStatement ps = null;
        
        logger.info("GET REWORK DETAILS STARTED");
        
        
        try
        {
            con = DBConnectionFactory.getConnection();
            
            //put RWK_ID into Hashtable
            ht_Rwk_Det.put("REWORK_ID",new Integer(rwkId));
            try
            {
                ps = con.executeStatement(SQLMaster.GET_REWORK_DETAILS_QUERY,ht_Rwk_Det);
                rs_Rwk_Det = ps.executeQuery();
                if(rs_Rwk_Det.next())
                {
                    obj_Rwk_Det.setRwk_Category_Id(rs_Rwk_Det.getInt("RWK_CATEGORY_ID"));
                    obj_Rwk_Det.setRwk_Category(rs_Rwk_Det.getString("RWK_CATEGORY"));
                    obj_Rwk_Det.setRwk_Id(rs_Rwk_Det.getInt("RWK_ID"));
                    obj_Rwk_Det.setRwk_Rsn(rs_Rwk_Det.getString("RWK_RSN"));
                    obj_Rwk_Det.setRwk_IsValid(rs_Rwk_Det.getInt("RWK_ISVALID"));
                    obj_Rwk_Det.setRwk_DateStamp(rs_Rwk_Det.getTimestamp("RWK_DATESTAMP"));
                    
                    
                    logger.info("GET REWORK DETAILS ENDS");
                    
                }
                else
                {
                    if(BuildConfig.DMODE)
                        System.out.println("REWORK REASON RECORD NOT FOUND");
                    
                    logger.error("REWORK REASON  RECORD NOT FOUND");
                    throw new ReworkException("","REWORK REASON RECORD NOT FOUND","");
                }
                rs_Rwk_Det.close();
                ps.close();
            }
            catch(SQLException ex)
            {
                
                logger.error("SQL ERROR",ex);
                throw new SQLException(ex.toString());
            }
        }
        catch(SQLException e)
        {
            
            logger.error("GENERAL SQL ERROR",e);
            if(BuildConfig.DMODE)
                System.out.println("GENERAL EXCEPTION");
            throw new ReworkException("RWKEC000","GENERAL SQL ERROR",e.toString());
        }
        finally
        {
            try
            {
                if(!con.getConnection().isClosed())
                {
                    con.closeConnection();
                }
            }
            catch(SQLException ex)
            {
                
                logger.error("SQL ERROR", ex);
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }
        
        return obj_Rwk_Det;
    }
    public HashMap makeReworkReasonValid(Vector rwkRsnIds) throws SQLException,ReworkException
    {
        DBConnection con = null;
        HashMap hm_Result = new HashMap();
        int res = 0;
        
        
        logger.info("MAKE REWORK REASON VALID STARTS");
        
        if(rwkRsnIds == null)
        {
            
            logger.error("VECTOR OBJECT IS NULL");
            throw new ReworkException("","VECTOR OBJECT IS NULL","");
        }
        
        try
        {
            con = DBConnectionFactory.getConnection();
            con.startTransaction();
            for(int i=0;i<rwkRsnIds.size();i++)
            {
                Hashtable ht_Mk_RwkRsn_Valid = new Hashtable();
                ht_Mk_RwkRsn_Valid .put("RWRK_ID",(Integer)rwkRsnIds.get(i));
                res = con.executeUpdateStatement(SQLMaster.RWKRSN_MAKE_VALID_QUERY,ht_Mk_RwkRsn_Valid);
                if(res>0)
                {
                    hm_Result.put(rwkRsnIds.get(i),new Integer(0));
                    
                    logger.info("RECORD VALIDATED");
                    con.commitTransaction();
                }
                else
                {
                    hm_Result.put(rwkRsnIds.get(i),new Integer(1));
                    
                    logger.info("RECORD NOT VALIDATED");
                }
                
            }
        }
        catch(SQLException e)
        {
            
            logger.error("GENERAL SQL ERROR",e);
            System.out.println("GENERAL EXCEPTION");
            throw new ReworkException("RWKEC000","GENERAL SQL ERROR",e.toString());
        }
        
        finally
        {
            try
            {
                if(!con.getConnection().isClosed())
                {
                    con.closeConnection();
                }
            }
            catch(SQLException ex)
            {
                
                logger.error("EXCEPTION WHILE CLOSING CONNECTION");
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }
        
        logger.info("MAKE REWORK REASON VALID ENDS");
        
        return hm_Result;
    }
    
    public HashMap makeReworkReasonInValid(Vector rwkRsnIds) throws SQLException,ReworkException
    {
        DBConnection con = null;
        HashMap hm_Result = new HashMap();
        int res = 0;
        
        
        logger.info("MAKE REWORK REASON INVALID STARTS");
        
        if(rwkRsnIds == null)
        {
            
            logger.error("VECTOR OBJECT IS NULL");
            throw new ReworkException("","VECTOR OBJECT IS NULL","");
        }
        
        try
        {
            con = DBConnectionFactory.getConnection();
            con.startTransaction();
            for(int i=0;i<rwkRsnIds.size();i++)
            {
                Hashtable ht_Mk_RwkRsn_InValid = new Hashtable();
                ht_Mk_RwkRsn_InValid .put("RWRK_ID",(Integer)rwkRsnIds.get(i));
                res = con.executeUpdateStatement(SQLMaster.RWKRSN_MAKE_INVALID_QUERY,ht_Mk_RwkRsn_InValid);
                if(res>0)
                {
                    hm_Result.put(rwkRsnIds.get(i),new Integer(0));
                    
                    logger.info("RECORD INVALIDATED");
                    con.commitTransaction();
                }
                else
                {
                    hm_Result.put(rwkRsnIds.get(i),new Integer(1));
                    
                    logger.info("RECORD NOT INVALIDATED");
                }
                
            }
        }
        catch(SQLException e)
        {
            
            logger.error("GENERAL SQL ERROR",e);
            System.out.println("GENERAL EXCEPTION");
            throw new ReworkException("RWKEC000","GENERAL SQL ERROR",e.toString());
        }
        
        finally
        {
            try
            {
                if(!con.getConnection().isClosed())
                {
                    con.closeConnection();
                }
            }
            catch(SQLException ex)
            {
                
                logger.error("EXCEPTION WHILE CLOSING CONNECTION");
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }
        
        
        logger.info("MAKE REWORK REASON INVALID ENDS");
        
        return hm_Result;
    }
    
    public HashMap getAllReworkDetails(Filter[] filters,String sortBy,boolean ascending,int startIndex,int displayCount) throws ReworkException,SQLException
    {
        DBConnection con = null;
        HashMap hm_Result = new HashMap();
        int tot_Rec_Cnt = 0;
        
        
        logger.info("REWORK DETAILS FILTER STARTS");
        
        if(filters == null || sortBy == null)
        {
            
            logger.error("FILTER VALUES ARE NULL");
            throw new ReworkException("","FILTER VALUES ARE NULL","");
        }
        try
        {
            con = DBConnectionFactory.getConnection();
            //Finding the end index for the query
            int eIndex = startIndex+displayCount;
            
            
            
            // filters and tableName are passed to the function and receives Total Number of Records
            tot_Rec_Cnt = con.getRowCountWithComplexFilters(filters,SQLMaster.GET_ALL_REWORK_DETAILS_QUERY);
            
            
            
            // filters,tablename,startindex,endindex,sorting column name and sortorder are passed to the function and  query will be received
            String Query = con.buildDynamicQuery(filters,startIndex,eIndex,sortBy,ascending,SQLMaster.GET_ALL_REWORK_DETAILS_QUERY);
            
            
            logger.info("Records : " + tot_Rec_Cnt);
            logger.info("Query   : " + Query);
            
            
            
            
            ResultSet rs  = null;
            
            try
            {
                rs = con.executeRownumStatement(Query);
            }
            catch(Exception sqle)
            {
                
                logger.error("SQL ERROR", sqle);
                
                throw new ReworkException("RWKEC000","GENERAL SQL ERROR",sqle.toString());
            }
            
            // vector object to store the result set values
            Vector vec_All_Rwk_Det = new Vector();
            while(rs.next())
            {
                
                ReworkDetails objRwkDet = new ReworkDetails();
                
                objRwkDet.setRwk_Category_Id(rs.getInt("RWK_CATEGORY_ID"));
                objRwkDet.setRwk_Category(rs.getString("RWK_CATEGORY"));
                objRwkDet.setRwk_Id(rs.getInt("RWK_ID"));
                objRwkDet.setRwk_Rsn(rs.getString("RWK_RSN"));
                objRwkDet.setRwk_DateStamp(rs.getTimestamp("RWK_DATESTAMP"));
                objRwkDet.setRwk_IsValid(rs.getInt("RWK_ISVALID"));
                
                vec_All_Rwk_Det.addElement(objRwkDet);
            }
            hm_Result.put("TotalRecordCount",new Integer(tot_Rec_Cnt));
            hm_Result.put("ReworkDetails", vec_All_Rwk_Det);
            if(BuildConfig.DMODE)
                System.out.println("HashResult : " + hm_Result);
            rs.getStatement().close();
            rs.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            
            logger.error("GENERAL SQL ERROR",e);
            
            throw new ReworkException("RWKEC000","GENERAL SQL ERROR",e.toString());
        }
        
        finally
        {
            try
            {
                if(con!=null)
                {
                    if(!con.getConnection().isClosed())
                        con.closeConnection();
                }
            }
            catch(SQLException ex)
            {
                
                logger.error("CONNECTION ERROR", ex);
            }
        }
        
        
        logger.info("REWORK DETAILS FILTER ENDS");
        
        return hm_Result;
    }
    
    public static void main(String args[]) throws ReworkException, SQLException{
        
        ReworkDetailsManager objRwkDetMgr = new ReworkDetailsManager();
        ReworkDetails objRwkDetails = new ReworkDetails();
        
        
        //addNewReworkCategory()//
        /*objRwkDetails.setRwk_Category("REWORKCATEGORY1");
         result = objRwkDetMgr.addNewReworkCategory(objRwkDetails);
         System.out.println("Result of Category Add :"+result);*/
        
        
        //getAllReworkCategories()//
        /*HashMap hm_Result = new HashMap();
         hm_Result = objRwkDetMgr.getAllReworkCategories();
         System.out.println("Hash Map Result :"+hm_Result);*/
        
        
        //addNewReworkReason()//
        /*objRwkDetails.setRwk_Category_Id(21);
         objRwkDetails.setRwk_Rsn("REWORK REASON1");
         result = objRwkDetMgr.addNewReworkReason(objRwkDetails);
         System.out.println("Result of Reason Add:"+result);*/
        
        
        //updateReworkReason()//
        objRwkDetails.setRwk_Id(21);
        objRwkDetails.setRwk_Rsn("SECOND REWORK REASON");
        //result = objRwkDetMgr.updateReworkReason(objRwkDetails);
        HashMap hm = new HashMap();
        hm = objRwkDetMgr.getAllReworkReasonsByCategory(41);
        System.out.println("Result of Reason Update:"+hm);
        
        
        //makeReworkReasonValid() and makeReworkReasonInValid()//
        /*HashMap hm_Result = new HashMap();
         Vector vec_RwkRsn = new Vector();
         vec_RwkRsn.addElement(new Integer(21));
         vec_RwkRsn.addElement(new Integer(26));
         vec_RwkRsn.addElement(new Integer(27));
         vec_RwkRsn.addElement(new Integer(2112));
         
         hm_Result = objRwkDetMgr.makeReworkReasonValid(vec_RwkRsn);
         //hm_Result = objRwkDetMgr.makeReworkReasonInValid(vec_RwkRsn);
          System.out.println("HashMap Result :"+hm_Result);*/
        
        
        //getReworkDetails()//
        
        /*objRwkDetails = objRwkDetMgr.getReworkDetails(27);
         System.out.println(objRwkDetails.getRwk_Category());
         System.out.println(objRwkDetails.getRwk_Category_Id());
         System.out.println(objRwkDetails.getRwk_Id());
         System.out.println(objRwkDetails.getRwk_Rsn());
         System.out.println(objRwkDetails.getRwk_IsValid());*/
        
        //getAllReworkDetails()//
        /*HashMap hmp = new HashMap();
         Filter filobj[] = new Filter[1];
         
         filobj[0] = new Filter();
         filobj[0].setFieldName("RWK_CATEGORY");
         filobj[0].setFieldValue("REWORKCATEGORY1");
         filobj[0].setSpecialFunction("");
         
         //filobj[0].setFieldName("EMP_ISVALID");
          //filobj[0].setFieldValue("1");
           //filobj[0].setSpecialFunction("");
            int startIndex = 1;
            int displayCount = 4;
            boolean ascending = true;
            String sortBy = "RWK_CATEGORY";
            HashMap hm_Result = objRwkDetMgr.getAllReworkDetails(filobj,sortBy,new Boolean(ascending).booleanValue(),startIndex,displayCount);
            System.out.println("Hash Map Result :"+hm_Result);
            
            Vector v =  (Vector)hm_Result.get("ReworkDetails");
            ReworkDetails obj = null;
            for (int i = 0; i < v.size(); i++)
            {
            obj = (ReworkDetails)v.get(i);
            System.out.println(obj.getRwk_Category());
            System.out.println(obj.getRwk_Rsn());
            System.out.println(obj.getRwk_IsValid());
            System.out.println(obj.getRwk_Id());
            }*/
        
    }
    
}
/***
$Log: ReworkDetailsManager.java,v $
Revision 1.29  2005/12/29 06:31:05  kduraisamy
con.startTransaction included.

Revision 1.28  2005/09/15 07:36:10  kduraisamy
rs.getStatement().close() added in Filters.

Revision 1.27  2005/09/10 13:18:20  kduraisamy
order by clause added.

Revision 1.26  2005/07/12 11:37:01  kduraisamy
imports organized

Revision 1.25  2005/07/06 14:50:27  kduraisamy
con.startTransaction() added.

Revision 1.24  2005/05/18 09:57:04  kduraisamy
indentation.

Revision 1.23  2005/05/18 08:39:15  kduraisamy
commitTransaction() and rollbackTransaction() added.

Revision 1.22  2005/05/16 18:34:40  kduraisamy
specific throws addded for mysql.

Revision 1.21  2005/05/16 15:42:43  kduraisamy
specific throws addded for mysql.

Revision 1.20  2005/04/19 13:13:34  kduraisamy
resultset properly closed.

Revision 1.19  2005/04/18 12:27:42  kduraisamy
executeStatement() return type changed.

Revision 1.18  2005/04/07 09:20:04  kduraisamy
MODE IS CHANGED TO DMODE.

Revision 1.17  2005/04/07 07:35:39  kduraisamy
if(BuildConfig.DMODE) ADDED. AND LEVEL INCLUDED PROPERLY.

Revision 1.16  2005/03/30 08:22:42  kduraisamy
excecuteRownumStatement() called for filters.

Revision 1.15  2005/03/26 10:08:21  smurugesan
ROWNUM RESET QUERY REMOVED.

Revision 1.14  2005/03/11 07:44:50  kduraisamy
RESET ROWNUM QUERY ADDED BEFORE FILTER METHODS..

Revision 1.13  2005/03/10 07:27:36  kduraisamy
CHECKING BEFORE THE FILTER METHODS(buildDynamicQuery(),etc) added.

getDate is changed to getTimestamp().

Revision 1.12  2005/03/04 08:15:22  kduraisamy
DBConnection Changes made.

Revision 1.11  2005/02/15 07:22:38  kduraisamy
UNIQUE CONSTRAINT ADDED FOR CATEGORY AND REASON.

Revision 1.10  2005/02/14 11:10:05  kduraisamy
unwanted cast removed.

Revision 1.9  2005/02/11 06:54:48  kduraisamy
unused variables removed.

Revision 1.8  2005/02/01 08:48:26  kduraisamy
getAllReworkReasonbycategory() added.

Revision 1.7  2004/12/07 06:25:02  kduraisamy
unwanted objDBNull removed.

Revision 1.6  2004/12/07 05:08:06  kduraisamy
== "" is changed to .equals("").

Revision 1.5  2004/12/01 14:20:09  sduraisamy
Format Changed

Revision 1.4  2004/11/23 12:31:01  sduraisamy
JobDetails ,OperationGroupDetails ,OperationDetails Entity Objects modified and errors Corrected

Revision 1.3  2004/11/22 04:38:30  sduraisamy
getAllReworkCategories() errors corrected

Revision 1.2  2004/11/21 11:44:42  sduraisamy
finally included for Methods

Revision 1.1  2004/11/20 10:34:31  sduraisamy
Initial commit of ReworkDetails,ReworkDetailsManager,ReworkException

***/