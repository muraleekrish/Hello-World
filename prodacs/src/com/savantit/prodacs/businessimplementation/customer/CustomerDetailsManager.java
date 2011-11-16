package com.savantit.prodacs.businessimplementation.customer;
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
import com.savantit.prodacs.infra.dbtools.DBNull;
import com.savantit.prodacs.infra.dbtools.SQLMaster;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.infra.util.LoggerOutput;
import com.savantit.prodacs.infra.util.ProdacsConfig;



/**
 * @author kduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
/**
 * @author kduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CustomerDetailsManager
{
    DBConnection con=null;
    static Logger logger = Logger.getLogger(CustomerDetailsManager.class);
    public CustomerDetailsManager()
    {
        logger.addAppender(LoggerOutput.getAppender());
        logger.setLevel(ProdacsConfig.LOGGER_LEVEL);
    }
    /**
     * @param objCustomerDetails
     * @return
     * @throws SQLException
     */
    public boolean addCustomerDetails(CustomerDetails objCustomerDetails) throws SQLException,CustomerException
    {
        int result=0;
        boolean addRESULT=false;
        String st = "";
        int x=0;
        Hashtable ht_Cust_Add=new Hashtable();
        DBNull dbNull=new DBNull();
        Object objDBNull = dbNull;
        
        
        logger.info("CUSTOMER ADD STARTS");
        
        if(BuildConfig.DMODE)
        {
            System.out.println("ADD CUSTOMER");
        }    
        
        if(objCustomerDetails == null)
        {
            
            logger.error("CUSTOMER DETAILS OBJ NULL");
            throw new CustomerException("CMEC001","CUSTOMER DETAILS OBJ NULL","");
        }
        if(objCustomerDetails.getCust_Name().trim().length() == 0 )
        {
            
            logger.error("CUSTOMER NAME IS NULL");
            throw new CustomerException("CMEC002","CUSTOMER NAME REQUIRED","");
        }
        con = DBConnectionFactory.getConnection();
        ht_Cust_Add.put("CST_NAME",objCustomerDetails.getCust_Name());
        ht_Cust_Add.put("CST_TYP_ID",(x=objCustomerDetails.getCust_Typ_Id())!=0 ? new Integer(x):objDBNull);
        ht_Cust_Add.put("CST_INSRVCE",(st=objCustomerDetails.getCust_Insrvce())!=null?st:"");
        ht_Cust_Add.put("CST_ADDR1",(st=objCustomerDetails.getCust_Addr1())!=null ? st :"");
        ht_Cust_Add.put("CST_ADDR2",(st=objCustomerDetails.getCust_Addr2())!=null ? st :"");
        ht_Cust_Add.put("CST_CITY",(st=objCustomerDetails.getCust_City())!=null ? st :"");
        ht_Cust_Add.put("CST_STATE",(st=objCustomerDetails.getCust_State())!=null ? st :"");
        ht_Cust_Add.put("CST_PCODE",(st=objCustomerDetails.getCust_Pcode())!=null ? st :"");
        ht_Cust_Add.put("CST_COUNTRY",(st=objCustomerDetails.getCust_Country())!=null ? st :"");
        ht_Cust_Add.put("CST_PHONE1",(st=objCustomerDetails.getCust_Phone1())!=null ? st :"");
        ht_Cust_Add.put("CST_EXTENSION1",(st=objCustomerDetails.getCust_Extension1())!=null ? st :"");
        ht_Cust_Add.put("CST_PHONE2",(st=objCustomerDetails.getCust_Phone2())!=null ? st :"");
        ht_Cust_Add.put("CST_EXTENSION2",(st=objCustomerDetails.getCust_Extension2())!=null ? st :"");
        ht_Cust_Add.put("CST_PHONE3",(st=objCustomerDetails.getCust_Phone3())!=null ? st :"");
        ht_Cust_Add.put("CST_EXTENSION3",(st=objCustomerDetails.getCust_Extension3())!=null ? st :"");
        ht_Cust_Add.put("CST_FAX",(st=objCustomerDetails.getCust_Fax())!=null ? st :"");
        ht_Cust_Add.put("CST_CNTCT_FNAME",(st=objCustomerDetails.getCust_Cntct_Fname())!=null ? st :"");
        ht_Cust_Add.put("CST_CNTCT_LNAME",(st=objCustomerDetails.getCust_Cntct_Lname())!=null ? st :"");
        ht_Cust_Add.put("CST_CNTCT_DESIGNATION",(st = objCustomerDetails.getCust_Cntct_Designation())!=null ? st :"");
        ht_Cust_Add.put("CST_MOBILE",(st=objCustomerDetails.getCust_Mobile())!=null ? st :"");
        ht_Cust_Add.put("CST_EMAIL",(st=objCustomerDetails.getCust_Email())!=null ? st :"");
        ht_Cust_Add.put("CST_WEBSITE",(st=objCustomerDetails.getCust_Website())!=null ? st :"");
               
        
        //String PrepareStatement_Query=con.getProductQuery(SQLMaster.CUSTOMER_INSERT_SQL_QUERY);
        try
        {
            result = con.executeUpdateStatement(SQLMaster.CUSTOMER_INSERT_SQL_QUERY,ht_Cust_Add);
            if(BuildConfig.DMODE)
            {
                System.out.println("CUSTOMER" + (result != 0? " " : " NOT ") + "ADDED");
            }
            if(result>0)
            {
                addRESULT = true;
            }
        }
        catch(SQLException sqle)
        {
            
            logger.error("SQL EXCEPTION", sqle);
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
                
            }
            if(BuildConfig.DBASE==BuildConfig.ORACLE)
            {
                if(sqle.toString().indexOf("PK_CUSTID")>=0)
                {
                    throw new CustomerException("CMEC003","CUSTOMER ID IS UNIQUE.UNIQUE CONSTRAINT VIOLATED",sqle.toString());
                }
                else if (sqle.toString().indexOf("FK_CUSTTYPID") >= 0)
                {
                    throw new CustomerException("CMEC004","PARENT KEY CUSTOMER TYPE NOT FOUND",sqle.toString());
                }
                else if (sqle.toString().indexOf("UK_CUSTNAME") >= 0)
                {
                    throw new CustomerException("CMEC005","CUSTOMER NAME ALREADY EXISTS",sqle.toString());
                }
                else
                {
                    throw new CustomerException("CMEC000","GENERAL SQL ERROR",sqle.toString());
                }
            }
            if(BuildConfig.DBASE==BuildConfig.MYSQL)
            {
                if(sqle.toString().indexOf("Duplicate entry")>=0)
                {
                    throw new CustomerException("CMEC003","CUSTOMER ID OR CUSTOMER NAME ALREADY EXISTS",sqle.toString());
                }
                else if(sqle.toString().indexOf("foreign key constraint")>=0)
                {
                    throw new CustomerException("CMEC003","PARENT KEY CUSTOMER TYPE NOT FOUND",sqle.toString());
                }
                else
                {
                    throw new CustomerException("CMEC000","GENERAL SQL ERROR",sqle.toString());
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
        
        //ps_Cust_Add.close();
        
        
        logger.info("CUSTOMER ADD ENDS");
        
        return addRESULT;
    }
    
    /**
     * @param objCustomerDetails
     * @return
     * @throws SQLException
     */
    public boolean updateCustomerDetails(CustomerDetails objCustomerDetails) throws SQLException, CustomerException
    {
        int result=0;
        boolean updateRESULT=false;
        String st="";
        int x=0;
        Vector vec_Obj=new Vector();
        Hashtable ht_Cust_Update=new Hashtable();
        DBNull dbNull=new DBNull();
        Object objDBNull = dbNull;
        
        
        logger.info("CUSTOMER UPDATE STARTS");
        
        
        if(objCustomerDetails == null)
        {
            
            logger.error("CUSTOMER DETAILS OBJECT NULL");
            throw new CustomerException("CMEC006","CUSTOMER DETAILS OBJECT NULL","");
        }
        if(BuildConfig.DMODE)
        {
            System.out.println("UPDATION OF CUSTOMER");
        }    
        
        
        con = DBConnectionFactory.getConnection();
        con.startTransaction();
        ht_Cust_Update.put("CST_ID",new Integer(objCustomerDetails.getCust_Id()));
        ht_Cust_Update.put("CST_NAME",objCustomerDetails.getCust_Name());
        ht_Cust_Update.put("CST_TYP_ID",(x=objCustomerDetails.getCust_Typ_Id())!=0 ? new Integer(x):objDBNull);
        ht_Cust_Update.put("CST_INSRVCE",(st=objCustomerDetails.getCust_Insrvce())!=null?st:"");
        ht_Cust_Update.put("CST_ADDR1",(st=objCustomerDetails.getCust_Addr1())!=null ? st :"");
        ht_Cust_Update.put("CST_ADDR2",(st=objCustomerDetails.getCust_Addr2())!=null ? st :"");
        ht_Cust_Update.put("CST_CITY",(st=objCustomerDetails.getCust_City())!=null ? st :"");
        ht_Cust_Update.put("CST_STATE",(st=objCustomerDetails.getCust_State())!=null ? st :"");
        ht_Cust_Update.put("CST_PCODE",(st=objCustomerDetails.getCust_Pcode())!=null ? st :"");
        ht_Cust_Update.put("CST_COUNTRY",(st=objCustomerDetails.getCust_Country())!=null ? st :"");
        ht_Cust_Update.put("CST_PHONE1",(st=objCustomerDetails.getCust_Phone1())!=null ? st :"");
        ht_Cust_Update.put("CST_EXTENSION1",(st=objCustomerDetails.getCust_Extension1())!=null ? st :"");
        ht_Cust_Update.put("CST_PHONE2",(st=objCustomerDetails.getCust_Phone2())!=null ? st :"");
        ht_Cust_Update.put("CST_EXTENSION2",(st=objCustomerDetails.getCust_Extension2())!=null ? st :"");
        ht_Cust_Update.put("CST_PHONE3",(st=objCustomerDetails.getCust_Phone3())!=null ? st :"");
        ht_Cust_Update.put("CST_EXTENSION3",(st=objCustomerDetails.getCust_Extension3())!=null ? st :"");
        ht_Cust_Update.put("CST_FAX",(st=objCustomerDetails.getCust_Fax())!=null ? st :"");
        ht_Cust_Update.put("CST_CNTCT_FNAME",(st=objCustomerDetails.getCust_Cntct_Fname())!=null ? st :"");
        ht_Cust_Update.put("CST_CNTCT_LNAME",(st=objCustomerDetails.getCust_Cntct_Lname())!=null ? st :"");
        ht_Cust_Update.put("CST_CNTCT_DESIGNATION",(st = objCustomerDetails.getCust_Cntct_Designation())!=null ? st :"");
        ht_Cust_Update.put("CST_MOBILE",(st=objCustomerDetails.getCust_Mobile())!=null ? st :"");
        ht_Cust_Update.put("CST_EMAIL",(st=objCustomerDetails.getCust_Email())!=null ? st :"");
        ht_Cust_Update.put("CST_WEBSITE",(st=objCustomerDetails.getCust_Website())!=null ? st :"");
        
        
        try
        {
            result=con.executeUpdateStatement(SQLMaster.CUSTOMER_UPDATE_SQL_QUERY,ht_Cust_Update);
            if(BuildConfig.DMODE)
            {
                System.out.println("CUSTOMER" + (result != 0? " " : " NOT ") + "UPDATED");
            }    
            if(result>=1)
            {
                
                logger.debug("Record Updated : " + vec_Obj);
                updateRESULT=true;
                con.commitTransaction();
            }
            else
            {
                
                logger.debug("Record Not Updated : " + vec_Obj);
                con.rollBackTransaction();
            }
        }
        catch(SQLException sqle)
        {
            
            logger.error("SQL EXCEPTION", sqle);
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
                
            }
            if(BuildConfig.DBASE==BuildConfig.ORACLE)
            {
                if(sqle.toString().indexOf("PK_CUSTID")>=0)
                {
                    throw new CustomerException("CMEC003","CUSTOMER ID IS UNIQUE.UNIQUE CONSTRAINT VIOLATED",sqle.toString());
                }
                else if (sqle.toString().indexOf("FK_CUSTTYPID") >= 0)
                {
                    throw new CustomerException("CMEC004","PARENT KEY CUSTOMER TYPE NOT FOUND",sqle.toString());
                }
                else if (sqle.toString().indexOf("UK_CUSTNAME") >= 0)
                {
                    throw new CustomerException("CMEC005","CUSTOMER NAME ALREADY EXISTS",sqle.toString());
                }
                else
                {
                    throw new CustomerException("CMEC000","GENERAL SQL ERROR",sqle.toString());
                }
            }
            if(BuildConfig.DBASE==BuildConfig.MYSQL)
            {
                if(sqle.toString().indexOf("Duplicate entry")>=0)
                {
                    throw new CustomerException("CMEC003","CUSTOMER ID OR CUSTOMER NAME ALREADY EXISTS",sqle.toString());
                }
                else if(sqle.toString().indexOf("foreign key constraint")>=0)
                {
                    throw new CustomerException("CMEC003","PARENT KEY CUSTOMER TYPE NOT FOUND",sqle.toString());
                }
                else
                {
                    throw new CustomerException("CMEC000","GENERAL SQL ERROR",sqle.toString());
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
        
        
        
        logger.info("CUSTOMER UPDATE ENDS");
        
        
        return updateRESULT;
        
    }
    /**
     * @param customerId
     * @return
     * @throws SQLException
     */
    public CustomerDetails getCustomerDetails(int customerId) throws SQLException, CustomerException
    {
        String st="";
        Hashtable ht_Cust_Details=new Hashtable();
        ResultSet rs_Cust_Details;
        CustomerDetails objCustomerDetails=new CustomerDetails();
        
        
        logger.info("CUSTOMER GET STARTS");
        
        if(customerId<=0)
        {
            
            logger.error("CUSTOMER GET ID NULL");
            throw new CustomerException("CMEC008","CUSTOMER GET ID NULL","");
        }
        if(BuildConfig.DMODE)
        {
            System.out.println("GET CUSTOMER DETAILS");
        }    
        try
        {
            con = DBConnectionFactory.getConnection();
            PreparedStatement ps;
            ht_Cust_Details.put("CST_ID",new Integer(customerId));
            try
            {
                ps = con.executeStatement(SQLMaster.CUSTOMER_DETAILS_SELECT_SQL_QUERY,ht_Cust_Details);
                rs_Cust_Details = ps.executeQuery(); 
            }
            catch(SQLException sqle)
            {
                
                logger.error("SQL EXCEPTION", sqle);
                if(BuildConfig.DMODE)
                {
                    sqle.printStackTrace();
                    
                }
                throw new CustomerException("CMEC000","GENERAL SQL ERROR",sqle.toString());
            }
            if(rs_Cust_Details.next())
            {
                objCustomerDetails.setCust_Id(rs_Cust_Details.getInt("CUST_ID"));
                objCustomerDetails.setCust_Name(rs_Cust_Details.getString("CUST_NAME"));
                objCustomerDetails.setCust_Typ_Id(rs_Cust_Details.getInt("CUST_TYP_ID"));
                objCustomerDetails.setCust_Insrvce((st=rs_Cust_Details.getString("CUST_INSRVCE"))==null? "":st);
                objCustomerDetails.setCust_Addr1((st=rs_Cust_Details.getString("CUST_ADDR1"))==null ? "" :st);
                objCustomerDetails.setCust_Addr2((st=rs_Cust_Details.getString("CUST_ADDR2"))==null ? "" :st);
                objCustomerDetails.setCust_City((st=rs_Cust_Details.getString("CUST_CITY"))==null ? "" :st);
                objCustomerDetails.setCust_State((st=rs_Cust_Details.getString("CUST_STATE"))==null ? "" :st);
                objCustomerDetails.setCust_Pcode((st=rs_Cust_Details.getString("CUST_PCODE"))==null ? "" :st);
                objCustomerDetails.setCust_Country((st=rs_Cust_Details.getString("CUST_COUNTRY"))==null ? "" :st);
                objCustomerDetails.setCust_Cntct_Fname((st=rs_Cust_Details.getString("CUST_CNTCT_FNAME"))==null ? "" :st);
                objCustomerDetails.setCust_Cntct_Lname((st=rs_Cust_Details.getString("CUST_CNTCT_LNAME"))==null ? "" :st);
                objCustomerDetails.setCust_Cntct_Designation((st=rs_Cust_Details.getString("CUST_CNTCT_DESIGNATION"))==null ? "" :st);
                objCustomerDetails.setCust_Phone1((st=rs_Cust_Details.getString("CUST_PHONE1"))==null ? "" :st);
                objCustomerDetails.setCust_Extension1((st=rs_Cust_Details.getString("CUST_EXTENSION1"))==null ? "" :st);
                objCustomerDetails.setCust_Phone2((st=rs_Cust_Details.getString("CUST_PHONE2"))==null ? "" :st);
                objCustomerDetails.setCust_Extension2((st=rs_Cust_Details.getString("CUST_EXTENSION2"))==null ? "" :st);
                objCustomerDetails.setCust_Phone3((st=rs_Cust_Details.getString("CUST_PHONE3"))==null ? "" :st);
                objCustomerDetails.setCust_Extension3((st=rs_Cust_Details.getString("CUST_EXTENSION3"))==null ? "" :st);
                objCustomerDetails.setCust_Mobile((st=rs_Cust_Details.getString("CUST_MOBILE"))==null ? "" :st);
                objCustomerDetails.setCust_Email((st=rs_Cust_Details.getString("CUST_EMAIL"))==null ? "" :st);
                objCustomerDetails.setCust_Website((st=rs_Cust_Details.getString("CUST_WEBSITE"))==null ? "" :st);
                objCustomerDetails.setCust_Fax((st=rs_Cust_Details.getString("CUST_FAX"))==null ? "" :st);
                objCustomerDetails.setCust_Typ_Name((st=rs_Cust_Details.getString("CUST_TYP_NAME"))==null ? "" :st);
                objCustomerDetails.setCust_Datestamp(rs_Cust_Details.getTimestamp("CUST_DATESTAMP"));
                objCustomerDetails.setCust_Isvalid(rs_Cust_Details.getInt("CUST_ISVALID"));
                objCustomerDetails.setCust_LOPD(rs_Cust_Details.getTimestamp("CUST_LOPD"));
                objCustomerDetails.setCust_LODD(rs_Cust_Details.getTimestamp("CUST_LODD"));
                objCustomerDetails.setCust_LWOR((st=rs_Cust_Details.getString("CUST_LWOR"))==null ? "" :st);
                objCustomerDetails.setCust_YTD_OrdrVal(rs_Cust_Details.getInt("CUST_YTD_ORDRVAL"));
                objCustomerDetails.setCust_YTD_TotOrdrs(rs_Cust_Details.getInt("CUST_YTD_TOTORDRS"));
                
                
                logger.info("CUSTOMER ID SELECTED : " + objCustomerDetails.getCust_Id());
                
                
            }
            else
            {
                
                logger.error("CUSTOMER NOT FOUND");
                throw new CustomerException("CMEC009","CUSTOMER NOT FOUND","");
            }
            rs_Cust_Details.close();
            ps.close();
        }
        catch(SQLException sqle)
        {
            
            logger.error("SQL ERROR", sqle);
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
                
            }
            throw new CustomerException("CMEC000","GENERAL SQL ERROR",sqle.toString());
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
        
        logger.info("CUSTOMER GET ENDS");
        
        return objCustomerDetails;
    }
    
    public HashMap getCustomers(Filter filters[],String sortBy,boolean ascending, int startIndex, int displayCount) throws SQLException, CustomerException
    {
        String st="";
        if(BuildConfig.DMODE)
        {
            
            logger.info("CUSTOMER DETAILS GET FILTER SEARCH STARTS");
        }
        if(BuildConfig.DMODE)
        {
            System.out.println("GET CUSTOMER - SEARCH");
        }    
        
        HashMap hm_Cust_Details = new HashMap();
        int totalRecordCount = 0;
        
        //Define the table name to pass the function getRowCountWithFilters() in DBConnection
        
        try
        {
            con = DBConnectionFactory.getConnection();
            
            //Finding the end index for the query
            int eIndex = startIndex + displayCount;
            
            totalRecordCount =  con.getRowCountWithComplexFilters(filters,SQLMaster.CUSTOMER_FILTER_SELECT_SQL_QUERY);
            if(BuildConfig.DMODE)
            {
                System.out.println("total record count : " + totalRecordCount);
            }     
            
            
            //Passing the filters,tablename,startindex,endindex,sorting column name and sorting order to the function and get the query based on the arguments
            String Query = con.buildDynamicQuery(filters,startIndex,eIndex,sortBy,ascending,SQLMaster.CUSTOMER_FILTER_SELECT_SQL_QUERY);
            
            //Displaying the total records and query
            if(BuildConfig.DMODE)
            {
                System.out.println("Total records : " + totalRecordCount);
                System.out.println("Query : " + Query);
            }      
            
            logger.info("records : " + totalRecordCount);
            logger.info("Query : " + Query);
            
            ResultSet rs_Cust_Details = null;
            try
            {
                rs_Cust_Details = con.executeRownumStatement(Query);
            }
            catch(Exception sqle)
            {
                
                
                logger.error("GENERAL ERROR", sqle);
                if(BuildConfig.DMODE)
                {
                    sqle.printStackTrace();
                    
                }
                throw new CustomerException("CMEC000","GENERAL SQL ERROR",sqle.toString());
            }
            
            //Making the vector object to store the result set values
            Vector vec_Cust_Details = new Vector();
            
            while(rs_Cust_Details.next())
            {
                //Creating a Customer Details object to store the resultset Values
                CustomerDetails objCustomerDetails = new CustomerDetails();
                //Storing the ResultSet values into Customer Detais object
                objCustomerDetails.setCust_Id(rs_Cust_Details.getInt("CUST_ID"));
                objCustomerDetails.setCust_Name(rs_Cust_Details.getString("CUST_NAME"));
                objCustomerDetails.setCust_Typ_Name(rs_Cust_Details.getString("CUST_TYP_NAME"));
                objCustomerDetails.setCust_Typ_Id(rs_Cust_Details.getInt("CUST_TYP_ID"));
                objCustomerDetails.setCust_Insrvce((st=rs_Cust_Details.getString("CUST_INSRVCE"))==null? "":st);
                objCustomerDetails.setCust_Datestamp(rs_Cust_Details.getTimestamp("CUST_DATESTAMP"));
                objCustomerDetails.setCust_Isvalid(rs_Cust_Details.getInt("CUST_ISVALID"));
                
                //objCustomerDetails.setCust_Addr1((st=rs_Cust_Details.getString("CUST_ADDR1"))==null ? "" :st);
                //objCustomerDetails.setCust_Addr2((st=rs_Cust_Details.getString("CUST_ADDR2"))==null ? "" :st);
                //objCustomerDetails.setCust_City((st=rs_Cust_Details.getString("CUST_CITY"))==null ? "" :st);
                //objCustomerDetails.setCust_State((st=rs_Cust_Details.getString("CUST_STATE"))==null ? "" :st);
                //objCustomerDetails.setCust_Pcode((st=rs_Cust_Details.getString("CUST_PCODE"))==null ? "" :st);
                //objCustomerDetails.setCust_Country((st=rs_Cust_Details.getString("CUST_COUNTRY"))==null ? "" :st);
                //objCustomerDetails.setCust_Cntct_Fname((st=rs_Cust_Details.getString("CUST_CNTCT_FNAME"))==null ? "" :st);
                //objCustomerDetails.setCust_Cntct_Lname((st=rs_Cust_Details.getString("CUST_CNTCT_LNAME"))==null ? "" :st);
                //objCustomerDetails.setCust_Phone1((st=rs_Cust_Details.getString("CUST_PHONE1"))==null ? "" :st);
                //objCustomerDetails.setCust_Phone2((st=rs_Cust_Details.getString("CUST_PHONE2"))==null ? "" :st);
                //objCustomerDetails.setCust_Fax((st=rs_Cust_Details.getString("CUST_FAX"))==null ? "" :st);
                //Adding the Customer details object to the vector object
                vec_Cust_Details.addElement(objCustomerDetails);
                
                
                logger.info("CUSTOMER SELECTED ID : " + objCustomerDetails.getCust_Id());
                
            }
            
            //adding the total record count to the hashmap
            hm_Cust_Details.put("TotalRecordCount",new Integer(totalRecordCount));
            //adding the vector object to the hash table
            hm_Cust_Details.put("CustomerDetails",vec_Cust_Details);
            rs_Cust_Details.getStatement().close();
            rs_Cust_Details.close();
        }
        catch(SQLException sqle)
        {
            
            logger.error("SQL ERROR", sqle);
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
                
            }
            throw new CustomerException("CMEC000","GENERAL SQL ERROR",sqle.toString());
            
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
                
                logger.error("CONNECTION ERROR", clse);
                
                throw new SQLException("CONNECTION CLOSING EXCEPTION" + clse.toString());
            }
        }
        
        logger.info("CUSTOMER GET FILTER SEARCH ENDS");
        return hm_Cust_Details;
    }
    
    
    public HashMap getAllCustomerTypeDetails(Filter[] filters,String sortBy,boolean ascending,int startIndex,int displayCount) throws SQLException, CustomerException
    {
        
        logger.info("GET ALL CUSTOMER TYPE DETAILS STARTS");
        
        if((filters == null)||( sortBy == null))
        {
            
            logger.error("FILTER VALUES ARE NULL");
            throw new CustomerException("CMEC022","FILTER VALUES ARE NULL","");
        }
        if(BuildConfig.DMODE)
        {
            System.out.println("GET ALL CUSTOMER TYPE DETAILS STARTS");
        }    
        
        con = DBConnectionFactory.getConnection();
        HashMap hash_Result = new HashMap();
        
        int tot_Rec_Cnt = 0;
        
        try
        {
            //	filters and tableName are passed to the function and receives Total Number of Records
            tot_Rec_Cnt = con.getRowCountWithComplexFilters(filters,SQLMaster.GET_ALL_CUSTOMER_TYPE_DETAILS_QUERY);
            
            //Finding the end index for the query
            int eIndex = startIndex + displayCount;
            
            
            // filters,tablename,startindex,endindex,sorting column name and sortorder are passed to the function and  query will be received
            String Query = con.buildDynamicQuery(filters,startIndex,eIndex,sortBy,ascending,SQLMaster.GET_ALL_CUSTOMER_TYPE_DETAILS_QUERY);
            
            // total records and query
            if(BuildConfig.DMODE)
            {
                System.out.println("TOTAL NUMBER OF RECORDS : " + tot_Rec_Cnt);
                System.out.println("QUERY : " + Query);
            }
            
            logger.info("Total Records : " + tot_Rec_Cnt);
            logger.info("Query : " + Query);
            
            ResultSet rs  = null;
            
            try
            {
                
                rs = con.executeRownumStatement(Query);
            }
            catch(Exception sqle)
            {
                if(BuildConfig.DMODE)
                {
                    sqle.printStackTrace();
                }
                
                logger.error("SQL ERROR", sqle);
                
                throw new CustomerException("CMEC000","GENERAL SQL ERROR",sqle.toString());
            }
            
            // vector object to store the result set values
            Vector vec_All_Cust_Typ_Det = new Vector();
            
            
            
            while(rs.next())
            {
                CustomerTypDetails objCustomerTypDetails = new CustomerTypDetails();
                objCustomerTypDetails.setCust_Typ_Id(rs.getInt("CUST_TYP_ID"));
                objCustomerTypDetails.setCust_Typ_Name(rs.getString("CUST_TYP_NAME"));
                objCustomerTypDetails.setCust_Typ_Desc(rs.getString("CUST_TYP_DESC"));
                objCustomerTypDetails.setCust_Typ_DateStamp(rs.getTimestamp("CUST_TYP_DATESTAMP"));
                objCustomerTypDetails.setCust_Typ_IsValid(rs.getInt("CUST_TYP_ISVALID"));
                vec_All_Cust_Typ_Det.addElement(objCustomerTypDetails);
            }
            hash_Result.put("TotalRecordCount",new Integer(tot_Rec_Cnt));
            hash_Result.put("CustomerTypeDetails", vec_All_Cust_Typ_Det);
            rs.getStatement().close();
            rs.close();
        }
        catch(SQLException e)
        {
            if(BuildConfig.DMODE)
            {
                e.printStackTrace();
            }
            
            logger.error("GENERAL SQL ERROR");
            
            throw new CustomerException("EMEC000","GENERAL SQL ERROR",e.toString());
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
                
                logger.error("CONNECTION ERROR", ex);
            }
        }
        
        
        logger.info("GET All CUSTOMER TYPE DETAILS END");
        
        return hash_Result;
        
    }
    
    
    
    public LinkedHashMap getAllCustomers() throws SQLException, CustomerException
    {
        
        logger.info("CUSTOMER GET ALL STARTS");
        if(BuildConfig.DMODE)
        {
            System.out.println("GET CUSTOMER DETAILS");
        }      
        LinkedHashMap hm_Cust_Id_Name = new LinkedHashMap();
        
        try
        {
            
            con = DBConnectionFactory.getConnection();
            //Executing the query with preparedstatement object and getting the resultset object
            ResultSet rs_Cust_Id_Name = null;
            PreparedStatement ps = null;
            try
            {
                ps = con.executeStatement(SQLMaster.CUSTOMER_ID_AND_NAME_SELECT_SQL_QUERY);
                rs_Cust_Id_Name = ps.executeQuery();
            }
            catch(SQLException sqle)
            {
                
                logger.error("GENERAL ERROR", sqle);
                if(BuildConfig.DMODE)
                {
                    sqle.printStackTrace();
                    
                }
                throw new CustomerException("CMEC000","GENERAL SQL ERROR",sqle.toString());
            }
            
            while(rs_Cust_Id_Name.next())
            {
                hm_Cust_Id_Name.put(new Integer(rs_Cust_Id_Name.getInt("CUST_ID")),rs_Cust_Id_Name.getString("CUST_NAME"));
            }
            rs_Cust_Id_Name.close();
            ps.close();
        }
        catch(SQLException sqle)
        {
            
            logger.error("SQL ERROR", sqle);
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
                
            }
            throw new CustomerException("CMEC000","GENERAL SQL ERROR",sqle.toString());
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
                
                logger.error("CONNECTION CLOSING ERROR", clse);
                
                throw new SQLException("CONNECTION CLOSING EXCEPTION" + clse.toString());
            }
        }
        
        
        logger.info("CUSTOMER GET ALL ENDS");
        
        return hm_Cust_Id_Name;
    }
    
    public LinkedHashMap getCustomerNameByType(int custTypId) throws CustomerException, SQLException
    {
        
        logger.info("CUSTOMER NAME GET BY ITS TYPE STARTS");
        if(BuildConfig.DMODE)
        {
            System.out.println("GET CUSTOMER NAME BY ITS TYPE STARTS");
        }      
        LinkedHashMap hm_CustName = new LinkedHashMap();
        Hashtable ht_CustName = new Hashtable();
        
        try
        {
            
            con = DBConnectionFactory.getConnection();
            ht_CustName.put("CST_TYP_ID",new Integer(custTypId));
            ResultSet rs_CustName = null;
            PreparedStatement ps = null;
            try
            {
                ps = con.executeStatement(SQLMaster.CUSTOMER_NAME_BY_TYPE_SELECT_SQL_QUERY,ht_CustName);
                rs_CustName = ps.executeQuery();
            }
            catch(SQLException sqle)
            {
                
                logger.error("GENERAL ERROR", sqle);
                if(BuildConfig.DMODE)
                {
                    sqle.printStackTrace();
                    
                }
                throw new CustomerException("CMEC000","GENERAL SQL ERROR",sqle.toString());
            }
            
            while(rs_CustName.next())
            {
                hm_CustName.put(new Integer(rs_CustName.getInt("CUST_ID")),rs_CustName.getString("CUST_NAME"));
            }
            rs_CustName.close();
            ps.close();
        }
        catch(SQLException sqle)
        {
            
            logger.error("SQL ERROR", sqle);
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
                
            }
            throw new CustomerException("CMEC000","GENERAL SQL ERROR",sqle.toString());
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
                
                logger.error("CONNECTION CLOSING ERROR", clse);
                
                throw new SQLException("CONNECTION CLOSING EXCEPTION" + clse.toString());
            }
        }
        
        
        logger.info("CUSTOMER GET BY ITS TYPE ENDS");
        
        return hm_CustName;
    }
    
    //	GET CUSTOMER TYPE DETAILS
    public CustomerTypDetails getCustomerTypeDetails(int custTypId) throws SQLException, CustomerException
    {
        CustomerTypDetails objCustomerTypDetails = new CustomerTypDetails();
        Hashtable ht_Get_Cust_Typ = new Hashtable();
        ResultSet rs_Get_Cust_Typ_Det = null;
        PreparedStatement ps = null;
        
        logger.info("GET CUSTOMER TYPE DETAIL OF " + custTypId + " STARTED");
        
        if(custTypId <= 0)
        {
            
            logger.error("CUSTOMER TYPE ID IS NULL");
            throw new  CustomerException("EMEC017","CUSTOMER TYPE ID IS NULL","");
        }
        
        con = DBConnectionFactory.getConnection();
        
        ht_Get_Cust_Typ.put("CUST_TYP_ID",new Integer(custTypId));
        
        try
        {
            ps = con.executeStatement(SQLMaster.GET_CUSTOMER_TYPE_DETAILS_QUERY,ht_Get_Cust_Typ);
            rs_Get_Cust_Typ_Det = ps.executeQuery();
            if(rs_Get_Cust_Typ_Det.next())
            {
                objCustomerTypDetails.setCust_Typ_Id(rs_Get_Cust_Typ_Det.getInt("CUST_TYP_ID"));
                objCustomerTypDetails.setCust_Typ_Name(rs_Get_Cust_Typ_Det.getString("CUST_TYP_NAME"));
                objCustomerTypDetails.setCust_Typ_DateStamp(rs_Get_Cust_Typ_Det.getTimestamp("CUST_TYP_DATESTAMP"));
                objCustomerTypDetails.setCust_Typ_IsValid(rs_Get_Cust_Typ_Det.getInt("CUST_TYP_ISVALID"));
                objCustomerTypDetails.setCust_Typ_Desc(rs_Get_Cust_Typ_Det.getString("CUST_TYP_DESC"));
                
                
                logger.info("CUSTOMER TYPE DETAILS SELECTED");
            }
            else
            {
                
                
                logger.error("CUSTOMER TYPE NOT FOUND");
                throw new CustomerException("EMEC018","CUSTOMER TYPE NOT FOUND","");
            }
        }
        catch(SQLException ex)
        {
            
            if(BuildConfig.DMODE)
            {
                ex.printStackTrace();
                System.out.println("EXCEPTION WHILE EXECUTING CUSTOMER TYPE QUERY");
            }
            
            logger.error("EXCEPTION WHILE EXECUTING CUSTOMER TYPE QUERY",ex);
            
            throw new CustomerException("EMEC000","GENERAL SQL ERROR",ex.toString());
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
                
                logger.error("SQL ERROR", ex);
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }
        
        rs_Get_Cust_Typ_Det.close();
        ps.close();
        logger.info("CUSTOMER TYPE DETAILS SELECTION ENDS");
        return objCustomerTypDetails;
        
    }
    
    
    
    public LinkedHashMap getCustomerTypes() throws CustomerException, SQLException
    {
        
        logger.info("CUSTOMER TYPE GET ALL STARTS");
        if(BuildConfig.DMODE)
        {
            System.out.println("GET CUSTOMER TYPE DETAILS");
        }    
        LinkedHashMap hm_Cust_Typ_Id_Name = new LinkedHashMap();
        try
        {
            
            con = DBConnectionFactory.getConnection();
            //Executing the query with preparedstatement object and getting the resultset object
            ResultSet rs_Cust_Typ_Id_Name = null;
            PreparedStatement ps = null;
            try
            {
                ps = con.executeStatement(SQLMaster.CUSTOMER_TYP_ID_AND_NAME_SELECT_SQL_QUERY);
                rs_Cust_Typ_Id_Name = ps.executeQuery();
            }
            catch(SQLException sqle)
            {
                
                logger.error("SQL ERROR", sqle);
                
                if(BuildConfig.DMODE)
                {
                    sqle.printStackTrace();
                }
                throw new CustomerException("CMEC000","GENERAL SQL ERROR",sqle.toString());
            }
            
            while(rs_Cust_Typ_Id_Name.next())
            {
                hm_Cust_Typ_Id_Name.put(new Integer(rs_Cust_Typ_Id_Name.getInt("CUST_TYP_ID")),rs_Cust_Typ_Id_Name.getString("CUST_TYP_NAME"));
                
            }
            rs_Cust_Typ_Id_Name.close();
            ps.close();
        }
        catch(SQLException sqle)
        {
            
            logger.error("SQL ERROR", sqle);
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
                
            }
            throw new CustomerException("CMEC000","GENERAL SQL ERROR",sqle.toString());
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
                
                logger.error("CONNECTION CLOSING ERROR", clse);
                
                throw new SQLException("CONNECTION CLOSING EXCEPTION" + clse.toString());
            }
        }
        
        
        logger.info("CUSTOMER TYPE GET ALL ENDS");
        
        return hm_Cust_Typ_Id_Name;
    }
    
    
    
    
    
    public HashMap makeCustomerValid(Vector ids) throws CustomerException, SQLException
    {
        
        logger.info("CUSTOMER MAKE VALID STARTS");
        
        HashMap hm_Cust_MakeValid = new HashMap();
        
        if(ids == null)
        {
            
            logger.error("VECTOR NULL IN MAKE INVALID");
            throw new CustomerException("","VECTOR NULL IN MAKE INVALID","");
        }
        if(BuildConfig.DMODE)
            System.out.println("MAKE VALID - CUSTOMER");
        int result = 0;
        
        try
        {
            con = DBConnectionFactory.getConnection();
            //Creating DBConnection Object
            con.startTransaction();
            
            //Creating the hashtable object
            Hashtable ht_Cust_MakeValid = new Hashtable();
            
            
            //			Get the prepareStatement skeleton query
            
            for(int i=0;i<ids.size();i++)
            {
                ht_Cust_MakeValid.put("CST_ID",(Integer)ids.elementAt(i));
                //Store the details from hash table to vector as objects
                result = con.executeUpdateStatement(SQLMaster.CUSTOMER_MAKE_VALID,ht_Cust_MakeValid);
                if(result >= 1)
                {
                    con.commitTransaction();
                    hm_Cust_MakeValid.put(ids.get(i), new Integer(0));
                    
                    logger.info("Record made valid : " + ids.get(i));
                }
                else
                {
                    con.rollBackTransaction();
                    hm_Cust_MakeValid.put(ids.get(i), new Integer(1));
                    
                    logger.info("Record not made valid : "+ids.get(i));
                }
                
            }
        }
        catch(SQLException e)
        {
            
            logger.error("SQL ERROR", e);
            if(BuildConfig.DMODE)
            {
                e.printStackTrace();
            }
            throw new CustomerException("CMEC000","GENERAL SQL ERROR",e.toString());
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
                
                logger.error("CONNECTION CLOSING ERROR", clse);
                throw new SQLException("CONNECTION CLOSING EXCEPTION"+clse.toString());
            }
        }
        
        logger.info("CUSTOMER MAKE VALID ENDS");
        
        return hm_Cust_MakeValid;
    }
    public HashMap makeCustomerTypValid(Vector ids) throws CustomerException, SQLException
    {
        
        logger.info("CUSTOMER TYPE MAKE VALID STARTS");
        
        HashMap hm_Cust_MakeValid = new HashMap();
        
        if(ids == null)
        {
            
            logger.error("VECTOR NULL IN MAKE INVALID");
            throw new CustomerException("","VECTOR NULL IN MAKE INVALID","");
        }
        if(BuildConfig.DMODE)
            System.out.println("MAKE VALID - CUSTOMER TYPE");
        int result = 0;
        
        try
        {
            con = DBConnectionFactory.getConnection();
            con.startTransaction();
            
            //Creating the hashtable object
            Hashtable ht_Cust_MakeValid = new Hashtable();
            
            
            //			Get the prepareStatement skeleton query
            
            for(int i=0;i<ids.size();i++)
            {
                ht_Cust_MakeValid.put("CST_TYP_ID",(Integer)ids.elementAt(i));
                //Store the details from hash table to vector as objects
                result = con.executeUpdateStatement(SQLMaster.CUSTOMER_TYPE_MAKE_VALID,ht_Cust_MakeValid);
                if(result >= 1)
                {
                    con.commitTransaction();
                    hm_Cust_MakeValid.put(ids.get(i), new Integer(0));
                    
                    logger.info("Record made valid : " + ids.get(i));
                }
                else
                {
                    con.rollBackTransaction();
                    hm_Cust_MakeValid.put(ids.get(i), new Integer(1));
                    
                    logger.info("Record not made valid : "+ids.get(i));
                }
                
            }
        }
        catch(SQLException e)
        {
            
            logger.error("SQL ERROR", e);
            if(BuildConfig.DMODE)
            {
                e.printStackTrace();
                
            }
            throw new CustomerException("CMEC000","GENERAL SQL ERROR",e.toString());
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
                
                logger.error("CONNECTION CLOSING ERROR", clse);
                throw new SQLException("CONNECTION CLOSING EXCEPTION"+clse.toString());
            }
        }
        
        logger.info("CUSTOMER TYPE MAKE VALID ENDS");
        
        return hm_Cust_MakeValid;
    }
    
    public HashMap makeCustomerTypInValid(Vector ids) throws CustomerException, SQLException
    {
        
        logger.info("CUSTOMER TYPE MAKE INVALID STARTS");
        
        
        HashMap hm_Cust_MakeValid = new HashMap();
        
        if(ids == null)
        {
            
            logger.error("VECTOR NULL IN MAKE INVALID");
            throw new CustomerException("","VECTOR NULL IN MAKE INVALID","");
        }
        
        if(BuildConfig.DMODE)
            System.out.println("MAKE INVALID - CUSTOMER TYPE");
        int result = 0;
        
        try
        {
            con = DBConnectionFactory.getConnection();
            con.startTransaction();
            
            //Creating the hashtable object
            Hashtable ht_Cust_MakeValid = new Hashtable();
            
            
            //Get the prepareStatement skeleton query
            
            for(int i=0;i<ids.size();i++)
            {
                ht_Cust_MakeValid.put("CST_TYP_ID",(Integer)ids.elementAt(i));
                //Store the details from hash table to vector as objects
                result = con.executeUpdateStatement(SQLMaster.CUSTOMER_TYPE_MAKE_INVALID,ht_Cust_MakeValid);
                if(result >= 1)
                {
                    con.commitTransaction();
                    hm_Cust_MakeValid.put(ids.get(i), new Integer(0));
                    
                    logger.info("Record made valid : " + ids.get(i));
                }
                else
                {
                    con.rollBackTransaction();
                    hm_Cust_MakeValid.put(ids.get(i), new Integer(1));
                    
                    logger.info("Record not made valid : "+ids.get(i));
                }
                
            }
        }
        catch(SQLException e)
        {
            
            logger.error("SQL ERROR", e);
            if(BuildConfig.DMODE)
            {
                e.printStackTrace();
                
            }
            throw new CustomerException("CMEC000","GENERAL SQL ERROR",e.toString());
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
                
                logger.error("CONNECTION CLOSING ERROR", clse);
                throw new SQLException("CONNECTION CLOSING EXCEPTION"+clse.toString());
            }
        }
        
        logger.info("CUSTOMER TYPE MAKE VALID ENDS");
        
        return hm_Cust_MakeValid;
    }
    
    /**
     * @param ids
     * @return
     */
    public HashMap makeCustomerInValid(Vector ids) throws CustomerException, SQLException
    {
        
        logger.info("CUSTOMER MAKE INVALID STARTS");
        
        HashMap hm_Cust_MakeInValid = new HashMap();
        if(ids == null)
        {
            
            logger.error("VECTOR NULL IN MAKE INVALID");
            throw new CustomerException("","VECTOR OBJECT NULL","");
        }
        if(BuildConfig.DMODE)
            System.out.println("MAKE INVALID - CUSTOMER");
        int result = 0;
        try
        {
            con = DBConnectionFactory.getConnection();
            con.startTransaction();
            //Creating the hashtable object
            Hashtable ht_Cust_MakeInvalid = new Hashtable();
            //Get the prepareStatement skeleton query
            for(int i=0;i<ids.size();i++)
            {
                ht_Cust_MakeInvalid.clear();
                ht_Cust_MakeInvalid.put("CST_ID",(Integer)ids.elementAt(i));
                //Store the details from hash table to vector as objects
                result = con.executeUpdateStatement(SQLMaster.CUSTOMER_MAKE_INVALID,ht_Cust_MakeInvalid);
                
                
                if(result >= 1)
                {
                    con.commitTransaction();
                    hm_Cust_MakeInValid.put(ids.get(i), new Integer(0));
                    
                    logger.info("Record made invalid : " + ids.get(i));
                }
                else
                {
                    con.rollBackTransaction();
                    hm_Cust_MakeInValid.put(ids.get(i), new Integer(1));
                    
                    logger.info("Record not made invalid : "+ids.get(i));
                }
            }
        }
        catch(SQLException sqle)
        {
            
            logger.error("SQL ERROR", sqle);
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
                
            }
            throw new CustomerException("CMEC000","GENERAL SQL ERROR",sqle.toString());
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
                
                logger.error("CONNECTION CLOSING ERROR", clse);
                throw new SQLException("CONNECTION CLOSING EXCEPTION"+clse.toString());
            }
        }
        
        logger.info("CUSTOMER-MAKE INVALID ENDS");
        
        return hm_Cust_MakeInValid;
    }
    public int addCustomerTypeDetails(CustomerTypDetails objCustomerTypDetails) throws CustomerException, SQLException
    {
        int result=0;
        String st = "";
        int custTypId = 0;
        
        Hashtable ht_Cust_Typ_Add=new Hashtable();
        
        
        logger.info("CUSTOMER TYPE ADD STARTS");
        
        if(BuildConfig.DMODE)
            System.out.println("ADD CUSTOMER TYPE");
        
        if(objCustomerTypDetails == null)
        {
            
            logger.error("CUSTOMER TYPE DETAILS OBJ NULL");
            throw new CustomerException("CMEC010","CUSTOMER TYPE DETAILS OBJ NULL","");
        }
        if(objCustomerTypDetails.getCust_Typ_Name().trim().length() == 0 )
        {
            
            logger.error("CUSTOMER TYPE NAME IS NULL");
            throw new CustomerException("CMEC011","CUSTOMER TYPE NAME REQUIRED","");
        }
        con = DBConnectionFactory.getConnection();
        ht_Cust_Typ_Add.put("CUST_TYP_NAME",objCustomerTypDetails.getCust_Typ_Name());
        ht_Cust_Typ_Add.put("CUST_TYP_DESC",(st=objCustomerTypDetails.getCust_Typ_Desc())!=null ? st :"");
        try
        {
            result= con.executeUpdateStatement(SQLMaster.CUSTOMER_TYP_INSERT_SQL_QUERY,ht_Cust_Typ_Add);
            if(BuildConfig.DMODE)
                System.out.println("CUSTOMER TYPE" + (result != 0? " " : " NOT ") + "ADDED");
            PreparedStatement ps = con.executeStatement(SQLMaster.CUST_TYP_ID_SELECT_SQL_QUERY); 
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                custTypId = rs.getInt(1);
            }
            ps.close();
            rs.close();
        }
        catch(SQLException sqle)
        {
            
            logger.error("SQL EXCEPTION", sqle);
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
                
            }
            
            if(BuildConfig.DBASE==BuildConfig.ORACLE)
            {
                if (sqle.toString().indexOf("UK_CUSTTYPNAME") >= 0)
                {
                    throw new CustomerException("CMEC005","CUSTOMER TYPE NAME ALREADY EXISTS",sqle.toString());
                }
                else
                {
                    throw new CustomerException("CMEC000","GENERAL SQL ERROR",sqle.toString());
                }
            }
            if(BuildConfig.DBASE==BuildConfig.MYSQL)
            {
                if(sqle.toString().indexOf("Duplicate entry")>=0)
                {
                    throw new CustomerException("CMEC005","CUSTOMER TYPE NAME ALREADY EXISTS",sqle.toString());
                }
                else
                {
                    throw new CustomerException("CMEC000","GENERAL SQL ERROR",sqle.toString());
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
        
        
        logger.info("CUSTOMER TYPE ADD ENDS");
        
        
        return custTypId;
    }
    
    
    public boolean updateCustomerTypeDetails(CustomerTypDetails custTypDetObj) throws SQLException, CustomerException
    {
        Hashtable ht_Cust_Typ_Update = new Hashtable();
        boolean result = false;
        DBConnection con = null;
        int res = 0;
        
        logger.info("UPDATE CUSTOMER TYPE DETAILS STARTED");
        
        if(custTypDetObj == null)
        {
            
            logger.error("CUSTOMER TYPE DETAILS OBJECT IS NULL");
            throw new CustomerException("CMEC014","CUSTOMER TYPE DETAILS OBJECT IS NULL","");
        }
        
        
        if(custTypDetObj.getCust_Typ_Name().trim().length()== 0)
        {
            
            logger.error("CUSTOMER TYPE NAME IS REQUIRED");
            throw new CustomerException("CMEC015","CUSTOMER TYPE NAME IS REQUIRED","");
        }
        con = DBConnectionFactory.getConnection();
        ht_Cust_Typ_Update.put("CUST_TYP_ID",new Integer(custTypDetObj.getCust_Typ_Id()));
        ht_Cust_Typ_Update.put("CUST_TYP_NAME",custTypDetObj.getCust_Typ_Name());
        ht_Cust_Typ_Update.put("CUST_TYP_DESC",custTypDetObj.getCust_Typ_Desc() == null ? "" : custTypDetObj.getCust_Typ_Desc());
        
        //execute the query
        try
        {
            res = con.executeUpdateStatement(SQLMaster.CUSTOMER_TYPE_DETAILS_UPDATE_QUERY,ht_Cust_Typ_Update);
            if(BuildConfig.DMODE)
                System.out.println("res :"+res);
            if(res>=1)
            {
                result = true;
            }
            
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
                System.out.println("EXCEPTION WHILE UPDATING CUSTOMER TYPE DETAILS");
            }
            
            logger.error("EXCEPTION WHILE UPDATING CUSTOMER TYPE DETAILS",sqle);
            if(BuildConfig.DBASE==BuildConfig.ORACLE)
            {
                if (sqle.toString().indexOf("UK_CUSTTYPNAME") >= 0)
                {
                    throw new CustomerException("CMEC005","CUSTOMER TYPE NAME ALREADY EXISTS",sqle.toString());
                }
                else
                {
                    throw new CustomerException("CMEC000","GENERAL SQL ERROR",sqle.toString());
                }
            }
            if(BuildConfig.DBASE==BuildConfig.MYSQL)
            {
                if(sqle.toString().indexOf("Duplicate entry")>=0)
                {
                    throw new CustomerException("CMEC005","CUSTOMER TYPE NAME ALREADY EXISTS",sqle.toString());
                }
                else
                {
                    throw new CustomerException("CMEC000","GENERAL SQL ERROR",sqle.toString());
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
            catch(SQLException ex)
            {
                
                logger.error("GENERAL EXCEPTION WHILE CLOSING CONNECTION");
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }
        
        logger.info("UPDATE CUSTOMER TYPE DETAILS STARTED");
        
        return result;
        
    }
    
}
/***
$Log: CustomerDetailsManager.java,v $
Revision 1.54  2005/09/15 07:32:42  kduraisamy
rs.getStatement().close() added in Filters.

Revision 1.53  2005/09/10 12:31:56  kduraisamy
order by clause added.

Revision 1.52  2005/08/17 08:37:27  vkrishnamoorthy
EMP_MSTR STRUCTURE CHANGED.

Revision 1.51  2005/08/17 07:06:07  kduraisamy
EMP_MSTR AND CUST_MSTR TABLE ALTERED.

Revision 1.50  2005/07/12 11:36:32  kduraisamy
imports organized

Revision 1.49  2005/07/12 05:11:11  kduraisamy
connection properly closed in finally.

Revision 1.48  2005/06/29 11:31:35  kduraisamy
if(con != null) added.

Revision 1.47  2005/05/18 09:50:34  kduraisamy
commitTransaction() and rollbackTransaction() added.

Revision 1.46  2005/05/16 18:34:19  kduraisamy
specific throws addded for mysql.

Revision 1.45  2005/05/16 15:42:43  kduraisamy
specific throws addded for mysql.

Revision 1.44  2005/05/16 09:38:02  kduraisamy
description added for customer type list..

Revision 1.43  2005/04/19 13:13:13  kduraisamy
resultset properly closed.

Revision 1.42  2005/04/18 12:27:23  kduraisamy
executeStatement() return type changed.

Revision 1.41  2005/04/07 11:38:11  kduraisamy
error is changed to info.

Revision 1.40  2005/04/07 09:19:49  kduraisamy
MODE IS CHANGED TO DMODE.

Revision 1.39  2005/04/07 07:35:23  kduraisamy
if(BuildConfig.DMODE) ADDED. AND LEVEL INCLUDED PROPERLY.

Revision 1.38  2005/04/05 10:54:48  kduraisamy
unwanted con.commitTransaction() removed.

Revision 1.37  2005/03/30 08:22:12  kduraisamy
excecuteRownumStatement() called for filters.

Revision 1.36  2005/03/26 09:21:16  kduraisamy
if(BuildConfig.MYSQL) removed.

Revision 1.35  2005/03/26 09:20:53  kduraisamy
if(BuildConfig.MYSQL) removed.

Revision 1.34  2005/03/18 06:49:48  smurugesan
CON.COMMITTRANSACTION REMOVED.

Revision 1.33  2005/03/10 07:26:36  kduraisamy
CHECKING BEFORE THE FILTER METHODS(buildDynamicQuery(),etc) added.

Revision 1.32  2005/03/06 08:40:41  kduraisamy
make valid,invalid for custtyp added.

Revision 1.31  2005/03/06 07:43:23  kduraisamy
Indentation.

Revision 1.30  2005/03/06 07:37:49  kduraisamy
CUST TYP DETAILS UPDATE METHOD ADDED.

Revision 1.29  2005/03/06 06:23:10  kduraisamy
Indentation.

Revision 1.28  2005/03/05 11:01:13  kduraisamy
getCustomerTypDetails() added.

Revision 1.27  2005/03/05 06:33:20  kduraisamy
cust typ details filter added.

Revision 1.26  2005/03/04 11:57:38  vkrishnamoorthy
Indentation.

Revision 1.25  2005/03/04 08:15:22  kduraisamy
DBConnection Changes made.

Revision 1.24  2005/02/16 07:53:49  kduraisamy
method signature modified.

Revision 1.23  2005/02/14 11:10:05  kduraisamy
unwanted cast removed.

Revision 1.22  2005/02/11 06:54:32  kduraisamy
unused variables removed.

Revision 1.21  2005/01/29 10:23:19  kduraisamy
logger is taken out of debug.mode

Revision 1.20  2005/01/27 14:44:17  kduraisamy
if(BuildConfig.DMODE)
{
}
properly inclued inside the catch block.

Revision 1.19  2004/11/24 07:17:11  kduraisamy
getCustomerNameByitstype ()  added

Revision 1.18  2004/11/19 14:24:55  kduraisamy
Vector Object is type cast into Integer.

Revision 1.17  2004/11/19 14:08:48  kduraisamy
rs.close,ps.close removed

Revision 1.16  2004/11/19 09:47:58  kduraisamy
finally included

Revision 1.15  2004/11/17 04:53:50  kduraisamy
addCustomerDetails modified

Revision 1.14  2004/11/16 15:24:47  kduraisamy
additional fields are added in to entity object

Revision 1.13  2004/11/16 13:35:57  kduraisamy
error corrected in addCustomerDetails()

Revision 1.12  2004/11/16 07:56:44  kduraisamy
customer type id added to entity object

Revision 1.11  2004/11/16 07:13:20  kduraisamy
customer type name added to entity object

Revision 1.10  2004/11/16 06:48:20  kduraisamy
customer type name added to entity object

Revision 1.9  2004/11/15 13:59:32  kduraisamy
datestamp and isvalid added

Revision 1.8  2004/11/15 08:40:13  kduraisamy
getter,setter added for datestamp and isvalid

Revision 1.7  2004/11/09 07:41:36  kduraisamy
Entity Object CustomerTypDetails added. addCustomerTypeDetails method added in to CustomerDetailsManager.

Revision 1.6  2004/11/09 04:52:36  kduraisamy
Log added

***/