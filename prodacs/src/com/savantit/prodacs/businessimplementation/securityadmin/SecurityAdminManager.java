/*
 * Created on Feb 26, 2005
 */
package com.savantit.prodacs.businessimplementation.securityadmin;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Vector;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.savantit.prodacs.infra.beans.Filter;
import com.savantit.prodacs.infra.dbtools.DBConnection;
import com.savantit.prodacs.infra.dbtools.DBConnectionFactory;
import com.savantit.prodacs.infra.dbtools.DBNull;
import com.savantit.prodacs.infra.dbtools.SQLMaster;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.infra.util.LoggerOutput;
import com.savantit.prodacs.infra.util.ProdacsConfig;





/**
 * @author bkannusamy,kduraisamy
 */
public class SecurityAdminManager
{
    
    static Logger logger = Logger.getLogger(SecurityAdminManager.class);
    public SecurityAdminManager()
    {
        logger.addAppender(LoggerOutput.getAppender());
        logger.setLevel(ProdacsConfig.LOGGER_LEVEL);
    }
    
    DBNull dbNull = new DBNull();
    Object objNull = dbNull;
    
    /* TO ADD NEW CONTACT AND RETURN THE CONTACT TD */
    public int addNewContact(ContactDetails obj_Contact_Details, DBConnection objDBConnection) throws SecurityAdminException, SQLException
    {
        if(BuildConfig.DMODE)
            System.out.println("Add new Contact started...");
        logger.info("CONTACT ADD START");
        if(obj_Contact_Details == null || obj_Contact_Details.getCntct_Fname().trim().length() == 0 || obj_Contact_Details.getCntct_Lname().trim().length() == 0)
        {
            
            logger.error("CONTACT OBJECT IS NULL");
            throw new SecurityAdminException("EC","CONTACT OBJECT IS NULL","");
        }
        ResultSet rs_Contact_Id_Get = null;
        int contact_id=0;
        int id = 0;
        String st = "";
        try
        {
            Hashtable ht_Contact_Add = new Hashtable();
            ht_Contact_Add.put("CNTCT_LNAME", (st = obj_Contact_Details.getCntct_Lname())!=null ? st :"");
            ht_Contact_Add.put("CNTCT_FNAME", (st = obj_Contact_Details.getCntct_Fname())!=null ? st :"");
            ht_Contact_Add.put("CNTCT_TITLE", (st = obj_Contact_Details.getCntct_Title())!=null ? st :"");
            ht_Contact_Add.put("CNTCT_DOB", obj_Contact_Details.getCntct_Dob() == null ? objNull : obj_Contact_Details.getCntct_Dob());
            ht_Contact_Add.put("CNTCT_COMPANY", (id = obj_Contact_Details.getCntct_Company()) !=0 ? new Integer(id):objNull);
            ht_Contact_Add.put("CNTCT_POSITION",(st = obj_Contact_Details.getCntct_Position())!=null ? st : "");
            ht_Contact_Add.put("CNTCT_ADDR1", (st = obj_Contact_Details.getCntct_Address1())!=null ? st :"");
            ht_Contact_Add.put("CNTCT_ADDR2", (st = obj_Contact_Details.getCntct_Address2())!= null ? st:"");
            ht_Contact_Add.put("CNTCT_CITY",(st=obj_Contact_Details.getCntct_City())!=null ? st :"");
            ht_Contact_Add.put("CNTCT_STATE",(st=obj_Contact_Details.getCntct_State())!=null ? st :"");
            ht_Contact_Add.put("CNTCT_PCODE",(st=obj_Contact_Details.getCntct_Pincode())!=null ? st :"");
            ht_Contact_Add.put("CNTCT_COUNTRY",(st=obj_Contact_Details.getCntct_Country())!=null ? st :"");
            ht_Contact_Add.put("CNTCT_EMAIL", (st = obj_Contact_Details.getCntct_Email())!=null ? st :"");
            ht_Contact_Add.put("CNTCT_HPHONE", (st = obj_Contact_Details.getCntct_HPhone())!=null ? st : "");
            ht_Contact_Add.put("CNTCT_WPHONE", (st = obj_Contact_Details.getCntct_WPhone())!=null ? st :"");
            ht_Contact_Add.put("CNTCT_EXTENSION",(st = obj_Contact_Details.getCntct_Extension())!=null ? st :"");
            ht_Contact_Add.put("CNTCT_MOBILE", (st = obj_Contact_Details.getCntct_Mobile())!=null ? st : "");
            ht_Contact_Add.put("CNTCT_FAX", (st = obj_Contact_Details.getCntct_Fax())!=null ? st : "");
            
            int contact_Add_Result = 0;
            contact_Add_Result = objDBConnection.executeUpdateStatement(SQLMaster.ADD_CONTACT_SQL_QUERY,ht_Contact_Add);
            if(contact_Add_Result >= 1)
            {
                logger.debug("CONTACT RECORD ADDED:");
                logger.info("TRYING TO GET PRFLCONTACT_ID FROM PROFILE_CONTACT");
                PreparedStatement ps_Contact_Id_Get = objDBConnection.executeStatement(SQLMaster.CONTACT_GET_ID_QUERY);
                rs_Contact_Id_Get = ps_Contact_Id_Get.executeQuery();
                
                if(rs_Contact_Id_Get.next())
                {
                    contact_id = rs_Contact_Id_Get.getInt(1);
                }
                else
                {
                    logger.error("RECORD ID NOT GET");
                    throw new SecurityAdminException("EC","RECORD NOT ADDED","");
                }
                
                rs_Contact_Id_Get.close();
                ps_Contact_Id_Get.close();
                
                logger.info("CONTACT_ID : " + contact_id);
                
            }
            else
            {
                logger.debug("CONTACT RECORD NOT ADDED :");
            }
        }
        catch(SQLException e)
        {
            if(BuildConfig.DMODE)
            {
                e.printStackTrace();
            }
            logger.error("SQL ERROR", e);
            objDBConnection.rollBackTransaction();
            if(e.toString().indexOf("unique") >= 0 || e.toString().indexOf("Duplicate entry") >= 0)
                throw new SecurityAdminException("EC","UNIQUE CONSTRAINT VIOLATED",e.toString());
            else
                throw new SecurityAdminException("EC","GENERAL SQL ERROR",e.toString());
        }
        logger.info("CONTACT ADD END");
        if(BuildConfig.DMODE)
            System.out.println("Add new Contact Finished... Contact Id : " + contact_id);
        return contact_id;
    }
    
    /* TO UPDATE THE CONTACT DETAILS OF USER */
    public int updateContact(ContactDetails obj_Contact_Details, DBConnection objDBConnection)throws SQLException, SecurityAdminException
    {
        if(BuildConfig.DMODE)
            System.out.println("Update Contact Details started...");
        logger.info("CONTACT UPDATE START");
        if(obj_Contact_Details == null || obj_Contact_Details.getCntct_Fname().trim().length() == 0 || obj_Contact_Details.getCntct_Lname().trim().length() == 0)
        {
            logger.error("CONTACT DETAILS OBJ NULL");
            throw new SecurityAdminException("EC","CONTACT OBJECT IS NULL","");
        }
        int bool_Ret_Val = 0;
        int id = 0;
        String st = "";
        try
        {
            Hashtable ht_Contact_Update = new Hashtable();
            ht_Contact_Update.put("CNTCT_ID", new Integer(obj_Contact_Details.getCntct_Id()));
            ht_Contact_Update.put("CNTCT_LNAME", (st = obj_Contact_Details.getCntct_Lname())!=null ? st :"");
            ht_Contact_Update.put("CNTCT_FNAME", (st = obj_Contact_Details.getCntct_Fname())!=null ? st :"");
            ht_Contact_Update.put("CNTCT_TITLE", (st = obj_Contact_Details.getCntct_Title())!=null ? st :"");
            ht_Contact_Update.put("CNTCT_DOB", obj_Contact_Details.getCntct_Dob() == null ? objNull : obj_Contact_Details.getCntct_Dob());
            ht_Contact_Update.put("CNTCT_COMPANY",(id = obj_Contact_Details.getCntct_Company()) !=0 ? new Integer(id):objNull);
            ht_Contact_Update.put("CNTCT_POSITION",(st = obj_Contact_Details.getCntct_Position())!=null ? st : "");
            ht_Contact_Update.put("CNTCT_ADDR1", (st = obj_Contact_Details.getCntct_Address1())!=null ? st :"");
            ht_Contact_Update.put("CNTCT_ADDR2", (st = obj_Contact_Details.getCntct_Address2())!= null ? st:"");
            ht_Contact_Update.put("CNTCT_CITY",(st=obj_Contact_Details.getCntct_City())!=null ? st :"");
            ht_Contact_Update.put("CNTCT_STATE",(st=obj_Contact_Details.getCntct_State())!=null ? st :"");
            ht_Contact_Update.put("CNTCT_PCODE",(st=obj_Contact_Details.getCntct_Pincode())!=null ? st :"");
            ht_Contact_Update.put("CNTCT_COUNTRY",(st=obj_Contact_Details.getCntct_Country())!=null ? st :"");
            ht_Contact_Update.put("CNTCT_EMAIL", (st = obj_Contact_Details.getCntct_Email())!=null ? st :"");
            ht_Contact_Update.put("CNTCT_HPHONE", (st = obj_Contact_Details.getCntct_HPhone())!=null ? st : "");
            ht_Contact_Update.put("CNTCT_WPHONE", (st = obj_Contact_Details.getCntct_WPhone())!=null ? st :"");
            ht_Contact_Update.put("CNTCT_EXTENSION",(st = obj_Contact_Details.getCntct_Extension())!=null ? st :"");
            ht_Contact_Update.put("CNTCT_MOBILE", (st = obj_Contact_Details.getCntct_Mobile())!=null ? st : "");
            ht_Contact_Update.put("CNTCT_FAX", (st = obj_Contact_Details.getCntct_Fax())!=null ? st : "");
            
            if(BuildConfig.DMODE)
                System.out.println("Hash Values : " + ht_Contact_Update);
            int contact_Update_Result = 0;
            
            contact_Update_Result = objDBConnection.executeUpdateStatement(SQLMaster.CONTACT_UPDATE_SQL_QUERY,ht_Contact_Update);
            
            if(contact_Update_Result >= 1)
            {
                logger.debug("CONTACT RECORD UPDATED");
                bool_Ret_Val = contact_Update_Result;
            }
            else
            {
                if(BuildConfig.DMODE)
                    System.out.println("contact update result : " + contact_Update_Result);
                logger.debug("CONTACT RECORD NOT UPDATED");
                bool_Ret_Val = 0;
                throw new SecurityAdminException("EC","ContactNotFound","");
            }
            
        }
        
        catch(SQLException e)
        {
            if(BuildConfig.DMODE)
            {
                e.printStackTrace();
            }
            
            logger.error("GENERAL SQL ERROR", e);
            objDBConnection.rollBackTransaction();
            if(e.toString().indexOf("unique") >= 0 || e.toString().indexOf("Duplicate entry") >= 0)
                throw new SecurityAdminException("EC","UNIQUE CONSTRAINT VIOLATED",e.toString());
            else
                throw new SecurityAdminException("EC","GENERAL SQL ERROR",e.toString());
            
        }
        logger.info("CONTACT UPDATE END");
        
        if(BuildConfig.DMODE)
            System.out.println("Update Contact Details finished...");
        return bool_Ret_Val;
    }
    
    
    public boolean addNewUser(SecAdminUserDetails usrObj) throws SQLException, SecurityAdminException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalStateException, IllegalBlockSizeException, BadPaddingException
    {
        if(BuildConfig.DMODE)
            System.out.println("Add New User Starts ...");
        
        logger.info("ADD NEW USER STARTED");
        
        if(usrObj==null)
        {
            logger.error("USER OBJECT IS NULL");
            throw new SecurityAdminException("EC","USER OBJECT IS NULL","");
        }
        int contactId = 0;
        boolean boolResult = false;
        DBConnection objDBConnection = null;
        objDBConnection = DBConnectionFactory.getConnection();
        objDBConnection.startTransaction();
        
        
        logger.info("CONTACT ADD START");
        
        
        ContactDetails obj_Contact_Details = (ContactDetails)usrObj.getObj_Contact_Details();
        
        try
        {
            contactId = addNewContact(obj_Contact_Details, objDBConnection);
            /*}
             catch(SQLException e)
             {
             throw new SecurityAdminException("EC","PROBLEM IN CONTACT ADDS",e.toString());
             }
             */
            if(BuildConfig.DMODE)
                System.out.println("Contact Id : " + contactId);
            
            String sql_Query_User_Add = SQLMaster.USER_ADD_SQL_QUERY;
            Hashtable ht_User_Add = new Hashtable();
            ht_User_Add.put("USER_NAME", usrObj.getUser_Name());
            ht_User_Add.put("CNTCT_ID",new Integer(contactId));
            ht_User_Add.put("USER_DESC", usrObj.getUser_Desc());
            
            Key key = new SecretKeySpec((usrObj.getUser_Name()+"99999999").substring(0,8).toLowerCase().getBytes(),"DES");
            if(BuildConfig.DMODE)
                System.out.println("Key value :"+key);
            Cipher cipher = Cipher.getInstance("DES");
            
            byte[] data = usrObj.getUser_Password().getBytes();
            if(BuildConfig.DMODE)
                System.out.println("Original data : " + new String(data));
            
            cipher.init(Cipher.ENCRYPT_MODE, key);
            String result = new BASE64Encoder().encode(cipher.doFinal(data));
            if(BuildConfig.DMODE)
                System.out.println("Encrypted data: " + result);
            ht_User_Add.put("USER_PWD", result);
            int user_Add_Result = 0;
            
            user_Add_Result = objDBConnection.executeUpdateStatement(sql_Query_User_Add, ht_User_Add);
            if(user_Add_Result >= 1)
            {
                if(BuildConfig.DMODE)
                    System.out.println("User Group Add Started");
                /* start of USER GROUP ADD */
                logger.info("ADD USER GROUP BLOCK STARTED");
                
                Vector vecUsrGrpObj = usrObj.getUser_Rolls();
                
                if(BuildConfig.DMODE)
                    System.out.println("No of user Rolls : " + vecUsrGrpObj.size());
                for(int i=0;i<vecUsrGrpObj.size();i++)
                {
                    
                    logger.info("USER ROLE ADD STARTED");
                    
                    Hashtable htUsrGrpAdd = new Hashtable();
                    htUsrGrpAdd.put("USER_NAME", usrObj.getUser_Name());
                    htUsrGrpAdd.put("ROLE_ID",vecUsrGrpObj.get(i));
                    int usrGrpAddResult = 0;
                    try
                    {
                        
                        usrGrpAddResult = objDBConnection.executeUpdateStatement(SQLMaster.USER_GRP_ADD_SQL_QUERY,htUsrGrpAdd);
                        if(BuildConfig.DMODE)
                            System.out.println("Result :" + usrGrpAddResult);
                        if(usrGrpAddResult >= 1)
                        {
                            
                            logger.debug("USER GROUP ADDED");
                            
                        }
                        else
                        {
                            
                            logger.debug("USER GROUP NOT ADDED");
                            throw new SQLException("SQL ERROR");
                        }
                    }
                    catch(SQLException ex)
                    {
                        if(BuildConfig.DMODE)
                        {
                            ex.printStackTrace();
                        }
                        
                        logger.error("SQL ERROR", ex);
                        if(ex.toString().indexOf("unique") >= 0 || ex.toString().indexOf("Duplicate entry") >= 0)
                            throw new SecurityAdminException("EC","UNIQUE CONSTRAINT VIOLATED",ex.toString());
                        else if(ex.toString().indexOf("integrity") >= 0 || ex.toString().indexOf("foreign key constraint") >= 0 )
                            throw new SecurityAdminException("EC","PARENT KEY NOT FOUND",ex.toString());
                        else
                            throw new SecurityAdminException("EC","GENERAL SQL ERROR",ex.toString());
                    }
                }
                
                
                logger.info("USER GROUP ADD ENDED");
                
                if(BuildConfig.DMODE)
                    System.out.println("User Group Add Finished");
                /* end of USER GROUP ADD */
            }/* End of if(User_Add_Result >= 1)*/
            boolResult = true;
            objDBConnection.commitTransaction();
        }
        catch(SQLException ex)
        {
            objDBConnection.rollBackTransaction();
            if(BuildConfig.DMODE)
                ex.printStackTrace();
            logger.error("SQL ERROR", ex);
            boolResult = false;
            if(ex.toString().indexOf("unique") >= 0 && ex.toString().indexOf("CNTCTID")>=0)
                throw new SecurityAdminException("EC","UNIQUE CONSTRAINT VIOLATED",ex.toString());
            else if(ex.toString().indexOf("unique") >= 0 || ex.toString().indexOf("Duplicate entry") >= 0 )
                throw new SecurityAdminException("EC","UNIQUE CONSTRAINT VIOLATED",ex.toString());
            else if(ex.toString().indexOf("integrity") >= 0 || ex.toString().indexOf("foreign key constraint") >= 0 )
                throw new SecurityAdminException("EC","PARENT KEY NOT FOUND",ex.toString());
            else
                throw new SecurityAdminException("EC","GENERAL SQL ERROR",ex.toString());
        }
        
        /* end of ADD USER DETAILS */
        /* End of if(usrObj.getIsNewContactFlag()==0)*/
        
        /* USER ADD WITH NEW CONTACT DETAILS */
        
        finally
        {
            try
            {
                if(objDBConnection != null)
                    if(objDBConnection.getConnection() != null )
                        if(!objDBConnection.getConnection().isClosed())
                        {
                            objDBConnection.closeConnection();
                        }
                        
            }
            catch(SQLException clse)
            {
                
                logger.error("CONNECTION CLOSING EXCEPTION", clse);
                throw new SQLException("CONNECTION CLOSING EXCEPTION" + clse.toString());
            }
        }
        return boolResult;
    }
    
    /* to get users according to the input from filters */
    
    public Vector getAllUsers(Filter[] filters) throws SecurityAdminException
    {
        DBConnection objDBConnection = null;
        ResultSet rsAllUserFilter = null;
        Vector vecResult = new Vector();
        try
        {
            objDBConnection = DBConnectionFactory.getConnection();
            
            int totalRecordCount = 0;
            int stIndex = 0;
            totalRecordCount = objDBConnection.getRowCountWithComplexFilters(filters,SQLMaster.GET_USERS_NAME_FILTER_QUERY);
            System.out.println("tot count"+totalRecordCount);
            
            String filterQuery = objDBConnection.buildDynamicQuery(filters,stIndex,totalRecordCount+1,"USER_NAME",true,SQLMaster.GET_USERS_NAME_FILTER_QUERY);
            //if(BuildConfig.DMODE)
                System.out.println("Filter ->" + filterQuery);
            if(BuildConfig.DMODE)
            {
                
                logger.debug("TOTAL RECORDS : " + totalRecordCount);
            }
            
            
            //	System.out.println("Prepared stmt : " + psAllUserFilter);
            try
            {
                
                rsAllUserFilter = objDBConnection.executeRownumStatement(filterQuery);
               
                while(rsAllUserFilter.next())
                {
                   vecResult.addElement(rsAllUserFilter.getString("USER_NAME"));
                }
               
            }
            catch(Exception ex)
            {
                if(BuildConfig.DMODE)
                    ex.printStackTrace();
                
                logger.error("SQL ERROR", ex);
                throw new SecurityAdminException("","GENERAL SQL ERROR",ex.toString());
            }
            
            
            rsAllUserFilter.close();
        }
        catch(SQLException e)
        {
            if(BuildConfig.DMODE)
                e.printStackTrace();
            logger.error("GENERAL SQL ERROR");
            
            throw new SecurityAdminException("","GENERAL SQL ERROR",e.toString());
        }
        
        finally
        {
            try
            {if(objDBConnection != null)
                if(objDBConnection.getConnection() != null )
                    if(!objDBConnection.getConnection().isClosed())
                    {
                        objDBConnection.closeConnection();
                    }
            }
            catch(SQLException ex)
            {
                
                logger.error("CONNECTION ERROR", ex);
            }
        }
        System.out.println(vecResult.size());
        return vecResult;
    }
    
    /* TO GET ALL USERS ID AND NAME */
    public Vector getAllUsers() throws SecurityAdminException, SQLException
    {
        if(BuildConfig.DMODE)
            System.out.println("start of GET ALL USERS");
        
        
        logger.info("GET ALL USERS STARTED ");
        
        
        Vector vecAllUsers = new Vector();
        DBConnection objDBConnection = null;
        
        try
        {
            objDBConnection = DBConnectionFactory.getConnection();
            Statement stmt = objDBConnection.getConnection().createStatement();
            ResultSet rsGetAllUsers = null;
            
            try
            {
                rsGetAllUsers = stmt.executeQuery(SQLMaster.GET_ALL_USER_NAME_SQL_QUERY);
            }
            catch(SQLException e)
            {
                if(BuildConfig.DMODE)
                {
                    e.printStackTrace();
                }
                
                logger.error("SQL ERROR", e);
                
                throw new SQLException("SQL ERROR");
            }
            while(rsGetAllUsers.next())
            {
                vecAllUsers.add(rsGetAllUsers.getString("USER_NAME"));
            }
            rsGetAllUsers.close();
            stmt.close();
            
        }
        catch(SQLException e)
        {
            if(BuildConfig.DMODE)
            {
                e.printStackTrace();
            }
            
            logger.error("GENERAL SQL ERROR", e);
            
            throw new SecurityAdminException("EC","GENERAL SQL ERROR",e.toString());
        }
        finally
        {
            try
            {
                if(objDBConnection != null)
                    if(objDBConnection.getConnection() != null )
                        if(!objDBConnection.getConnection().isClosed())
                        {
                            objDBConnection.closeConnection();
                        }
            }
            catch(SQLException clse)
            {
                
                logger.error("CONNECTION CLOSING EXCEPTION", clse);
                throw new SQLException("CONNECTION CLOSING EXCEPTION" + clse.toString());
            }
        }
        
        if(BuildConfig.DMODE)
            System.out.println("end of GET ALL USERS");
        
        
        logger.info("GET ALL USERS ENDED");
        
        return vecAllUsers;
    }
    
    public SecAdminUserDetails getUserDetails(String userId) throws SecurityAdminException, SQLException, NoSuchAlgorithmException, NoSuchPaddingException, IOException, InvalidKeyException, IllegalStateException, IllegalBlockSizeException, BadPaddingException
    {
        if(BuildConfig.DMODE)
            System.out.println("start of GET USER DETAILs");
        
        
        logger.info("GET USER DETAIL OF " + userId + " STARTED");
        
        
        if(userId.equals(""))
        {
            logger.error("USER NAME IS NULL");
            throw new SecurityAdminException("EC","USER NAME IS NULL","");
        }
        
        SecAdminUserDetails usrDetailObj = new SecAdminUserDetails();
        Vector vecUserGroups = new Vector();
        
        DBConnection objDBConnection = null;
        ResultSet rs_UserDetailResult = null;
        ResultSet rs_GroupDetailResult = null;
        PreparedStatement ps_GroupDetailResult = null;
        PreparedStatement ps_UserDetailResult = null;
        try
        {
            objDBConnection = DBConnectionFactory.getConnection();
            Hashtable htUserDetail = new Hashtable();
            
            htUserDetail.put("USER_NAME", userId);
            
            
            logger.info("GET USER GROUP DETAIL STARED ");
            
            
            try
            {
                ps_GroupDetailResult = objDBConnection.executeStatement(SQLMaster.GET_USER_GROUP_ID_NAME,htUserDetail);
                rs_GroupDetailResult = ps_GroupDetailResult.executeQuery();
            }
            catch(SQLException ex)
            {
                if(BuildConfig.DMODE)
                {
                    ex.printStackTrace();
                }
                
                logger.error("SQL ERROR", ex);
                throw new SQLException("GENERAL EXCEPTION");
            }
            boolean flg = true;
            while(rs_GroupDetailResult.next())
            {
                SecAdminGroupDetails grpDetailObj = new SecAdminGroupDetails();
                String st="";
                grpDetailObj.setGroup_Id(rs_GroupDetailResult.getInt("ROLE_ID"));
                grpDetailObj.setGroup_Name((st=rs_GroupDetailResult.getString("ROLE_NAME"))!= null ? st : "");
                vecUserGroups.add(grpDetailObj);
                flg = false;
            }
            rs_GroupDetailResult.close();
            ps_GroupDetailResult.close();
            
            if(flg)
            {
                throw new SecurityAdminException("EC","USER NOT EXISTS","");
            }
            usrDetailObj.setUser_Rolls(vecUserGroups);
            
            
            logger.debug("GET USER GROUP DETAILS FINISHED");
            
            
            
            try
            {
                ps_UserDetailResult = objDBConnection.executeStatement(SQLMaster.GET_USER_DETAIL_SQL_QUERY,htUserDetail);
                rs_UserDetailResult = ps_UserDetailResult.executeQuery();
            }
            catch(SQLException ex)
            {
                if(BuildConfig.DMODE)
                {
                    ex.printStackTrace();
                }
                
                logger.error("SQL ERROR", ex);
                throw new SQLException("GENERAL EXCEPTION");
            }
            
            if(rs_UserDetailResult.next())
            {
                String st="";
                usrDetailObj.setUser_Name(userId);
                usrDetailObj.setContactId(rs_UserDetailResult.getInt("CNTCT_ID"));
                Key key = new SecretKeySpec((userId+"99999999").substring(0,8).toLowerCase().getBytes(),"DES");
                if(BuildConfig.DMODE)
                    System.out.println("Key value :"+key.hashCode());
                Cipher cipher = Cipher.getInstance("DES");
                byte[] result1 = new BASE64Decoder().decodeBuffer(rs_UserDetailResult.getString("USER_PWD"));
                cipher.init(Cipher.DECRYPT_MODE, key);
                byte[] original = cipher.doFinal(result1);
                if(BuildConfig.DMODE)
                    System.out.println("Decrypted data: " + new String(original));
                usrDetailObj.setUser_Password((st=new String(original))!=null ? st:"");
                usrDetailObj.setUser_Desc((st=rs_UserDetailResult.getString("USER_DESC"))!=null ? st:"");
                usrDetailObj.setUser_DateStamp(rs_UserDetailResult.getTimestamp("USER_DATESTAMP"));
                usrDetailObj.setUser_IsValid((rs_UserDetailResult.getInt("USER_ISVALID"))==1?true:false);
            }
            else
            {
                if(BuildConfig.DMODE)
                    System.out.println("User Not Found");
                throw new SecurityAdminException("EC","UserNotFound","");
            }
            rs_UserDetailResult.close();
            ps_UserDetailResult.close();
            usrDetailObj.setObj_Contact_Details(getContact(usrDetailObj.getContactId()));
            
        }
        catch(SQLException e)
        {
            if(BuildConfig.DMODE)
                e.printStackTrace();
            throw new SecurityAdminException("EC","GENERAL SQL ERROR",e.toString());
        }
        finally
        {
            try
            {if(objDBConnection != null)
                if(objDBConnection.getConnection() != null )
                    if(!objDBConnection.getConnection().isClosed())
                    {
                        objDBConnection.closeConnection();
                    }
            }
            catch(SQLException clse)
            {
                
                logger.error("CONNECTION CLOSING EXCEPTION", clse);
                throw new SQLException("CONNECTION CLOSING EXCEPTION" + clse.toString());
            }
        }
        
        
        logger.info("GET USER DETAIL OF " + userId + " ENDS");
        
        if(BuildConfig.DMODE)
            System.out.println("end of GET USER DETAILs");
        return usrDetailObj;
    }
    
    public ContactDetails getContact(int cntctId) throws SecurityAdminException, SQLException
    {
        ContactDetails objContactDetails = new ContactDetails();
        logger.info("GET  CONTACT DETAILS STARTED");
        DBConnection objDBConnection = null;
        Hashtable ht_ContactId = new Hashtable();
        try
        {
            objDBConnection = DBConnectionFactory.getConnection();
            ht_ContactId.put("CNTCT_ID",new Integer(cntctId));
            PreparedStatement ps = objDBConnection.executeStatement(SQLMaster.GET_CONTACT_DETAILS_SQL_QUERY,ht_ContactId);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                objContactDetails.setCntct_Fname(rs.getString("CNTCT_FNAME"));
                objContactDetails.setCntct_Lname(rs.getString("CNTCT_LNAME"));
                objContactDetails.setCntct_Title(rs.getString("CNTCT_TITLE"));
                objContactDetails.setCntct_Company(rs.getInt("CNTCT_COMPANY"));
                objContactDetails.setCompanyName(rs.getString("CUST_NAME"));
                objContactDetails.setCntct_Position(rs.getString("CNTCT_POSITION"));
                objContactDetails.setCntct_Dob(rs.getTimestamp("CNTCT_DOB"));
                objContactDetails.setCntct_Address1(rs.getString("CNTCT_ADDR1"));
                objContactDetails.setCntct_Address2(rs.getString("CNTCT_ADDR2"));
                objContactDetails.setCntct_City(rs.getString("CNTCT_CITY"));
                objContactDetails.setCntct_State(rs.getString("CNTCT_STATE"));
                objContactDetails.setCntct_Pincode(rs.getString("CNTCT_PCODE"));
                objContactDetails.setCntct_Country(rs.getString("CNTCT_COUNTRY"));
                objContactDetails.setCntct_Email(rs.getString("CNTCT_EMAIL"));
                objContactDetails.setCntct_HPhone(rs.getString("CNTCT_HPHONE"));
                objContactDetails.setCntct_WPhone(rs.getString("CNTCT_WPHONE"));
                objContactDetails.setCntct_Extension(rs.getString("CNTCT_EXTENSION"));
                objContactDetails.setCntct_Mobile(rs.getString("CNTCT_MOBILE"));
                objContactDetails.setCntct_Fax(rs.getString("CNTCT_FAX"));
                objContactDetails.setDatestamp(rs.getTimestamp("CNTCT_DATESTAMP"));
                objContactDetails.setIsValid(rs.getInt("CNTCT_ISVALID"));
                objContactDetails.setCntct_Id(cntctId);
                
            }
            rs.close();
            ps.close();
            
        }
        catch(SQLException e)
        {
            if(BuildConfig.DMODE)
                e.printStackTrace();
            throw new SecurityAdminException("EC","GENERAL SQL ERROR",e.toString());
        }
        finally
        {
            try
            {
                if(objDBConnection != null)
                if(objDBConnection.getConnection() != null )
                    if(!objDBConnection.getConnection().isClosed())
                    {
                        objDBConnection.closeConnection();
                    }
            }
            catch(SQLException clse)
            {
                
                logger.error("CONNECTION CLOSING EXCEPTION", clse);
                throw new SQLException("CONNECTION CLOSING EXCEPTION" + clse.toString());
            }
        }
        
        
        logger.info("GET  CONTACT DETAILS STARTED");
        
        
        return objContactDetails;
    }
    
    public HashMap makeUsersValid(Vector userIds) throws SecurityAdminException
    {
        
        logger.info("MAKE USER VALID STARTED");
        
        HashMap hashResult = new HashMap();
        DBConnection objDBConnection = null;
        
        if(userIds == null)
        {
            logger.error("USER VECTOR NULL");
            throw new SecurityAdminException("EC","USER VECTOR NULL","");
        }
        
        try
        {
            objDBConnection = DBConnectionFactory.getConnection();
            objDBConnection.startTransaction();
            for(int i=0;i<userIds.size();i++)
            {
                Hashtable htUserId = new Hashtable();
                htUserId.put("USER_NAME", userIds.get(i));
                int makeUserValidResult = objDBConnection.executeUpdateStatement(SQLMaster.MAKE_USER_VALID_SQL_QUERY,htUserId);
                if(makeUserValidResult >=1)
                {
                    hashResult.put(userIds.get(i),new Integer(0));
                    objDBConnection.commitTransaction();
                    logger.debug("USER VALIDATED VALUES");
                }
                else
                {
                    hashResult.put(userIds.get(i),new Integer(1));
                    objDBConnection.rollBackTransaction();
                    logger.debug("USER NOT VALIDATED VALUES");
                }
                
            } /* End of For loop */
        }
        catch(SQLException e)
        {
            if(BuildConfig.DMODE)
                e.printStackTrace();
            logger.error("GENERAL SQL ERROR");
            throw new SecurityAdminException("EC","GENERAL SQL ERROR","");
        }
        
        finally
        {
            try
            {
                if(objDBConnection != null)
                if(objDBConnection.getConnection() != null )
                    if(!objDBConnection.getConnection().isClosed())
                    {
                        objDBConnection.closeConnection();
                    }
            }
            catch(SQLException ex)
            {
                
                logger.error("CONNECTION ERROR", ex);
            }
        }
        
        
        logger.info("USER VALID END");
        
        return hashResult;
    }
    
    public HashMap makeUsersInValid(Vector userIds) throws SecurityAdminException
    {
        
        
        logger.info("MAKE USER INVALID STARTED");
        HashMap hashResult = new HashMap();
        DBConnection objDBConnection = null;
        
        if(userIds == null)
        {
            logger.error("USER VECTOR NULL");
            throw new SecurityAdminException("EC","USER VECTOR NULL","");
        }
        
        try
        {
            objDBConnection = DBConnectionFactory.getConnection();
            objDBConnection.startTransaction();
            for(int i=0;i<userIds.size();i++)
            {
                Hashtable htUserId = new Hashtable();
                htUserId.put("USER_NAME", userIds.get(i));
                int makeUserInValidResult = objDBConnection.executeUpdateStatement(SQLMaster.MAKE_USER_INVALID_SQL_QUERY,htUserId);
                if(makeUserInValidResult >=1)
                {
                    hashResult.put(userIds.get(i),new Integer(0));
                    objDBConnection.commitTransaction();
                    logger.debug("USER INVALIDATED VALUES");
                }
                else
                {
                    hashResult.put(userIds.get(i),new Integer(1));
                    objDBConnection.rollBackTransaction();
                    logger.debug("USER NOT INVALIDATED VALUES");
                }
            } /* End of For loop */
        }
        catch(SQLException e)
        {
            if(BuildConfig.DMODE)
            {
                e.printStackTrace();
            }
            logger.error("GENERAL SQL ERROR");
            throw new SecurityAdminException("EC","GENERAL SQL ERROR",e.toString());
        }
        
        finally
        {
            try
            {
                if(objDBConnection != null)
                if(objDBConnection.getConnection() != null )
                    if(!objDBConnection.getConnection().isClosed())
                    {
                        objDBConnection.closeConnection();
                    }
            }
            catch(SQLException ex)
            {
                
                logger.error("CONNECTION ERROR", ex);
            }
        }
        
        
        logger.info("USER VALID END");
        
        return hashResult;
    }
    
    
    
    /* TO GET ALL USER DETAILS USING FILTERS */
    public HashMap getAllUsersDetails(Filter[] filters, String sortBy, boolean sortOrder, int stIndex, int displayCount) throws SQLException, SecurityAdminException
    {
        if(BuildConfig.DMODE)
            System.out.println("Get All user details started...");
        logger.info("GET ALL USER DETAILS WITH FILTER STARTED");
        if((filters == null)||( sortBy == null))
        {
            logger.error("USER FILTER VALUES ARE NULL");
            throw new SecurityAdminException("EC","USER FILTER VALUES ARE NULL","");
        }
        HashMap hashAllUserFilterObj = new HashMap();
        Vector vecAllUserFilterObj = new Vector();
        int totalRecordCount = 0;
        DBConnection objDBConnection = null;
        ResultSet rsAllUserFilter = null;
        
        try
        {
            objDBConnection = DBConnectionFactory.getConnection();
            
            int endIndex = stIndex + displayCount;
            
            totalRecordCount = objDBConnection.getRowCountWithComplexFilters(filters,SQLMaster.GET_ALL_USER_FILTER_SQL_QUERY);
            
            String filterQuery = objDBConnection.buildDynamicQuery(filters,stIndex,endIndex,sortBy,sortOrder,SQLMaster.GET_ALL_USER_FILTER_SQL_QUERY);
            if(BuildConfig.DMODE)
                System.out.println("Filter Query : " + filterQuery);
            if(BuildConfig.DMODE)
            {
                
                logger.debug("TOTAL RECORDS : " + totalRecordCount);
            }
            
            
            //	System.out.println("Prepared stmt : " + psAllUserFilter);
            try
            {
                rsAllUserFilter = objDBConnection.executeRownumStatement(filterQuery);
            }
            catch(Exception ex)
            {
                if(BuildConfig.DMODE)
                    ex.printStackTrace();
                
                logger.error("SQL ERROR", ex);
                throw new SecurityAdminException("","GENERAL SQL ERROR",ex.toString());
            }
            
            while(rsAllUserFilter.next())
            {
                String st="";
                SecAdminUserDetails usrObj = new SecAdminUserDetails();
                ContactDetails conObj = new ContactDetails();
                usrObj.setUser_Name(rsAllUserFilter.getString("USER_NAME"));
                conObj.setCntct_Id(rsAllUserFilter.getInt("CNTCT_ID"));
                conObj.setCntct_Fname((st=rsAllUserFilter.getString("CNTCT_FNAME"))!=null ? st:"");
                conObj.setCntct_Lname((st=rsAllUserFilter.getString("CNTCT_LNAME"))!=null ? st:"");
                conObj.setCompanyName(rsAllUserFilter.getString("CUST_NAME"));
                usrObj.setUser_IsValid((rsAllUserFilter.getInt("USER_ISVALID")==1)?true:false);
                usrObj.setObj_Contact_Details(conObj);
                vecAllUserFilterObj.addElement(usrObj);
            }
            hashAllUserFilterObj.put("TotalRecordCount", new Integer(totalRecordCount));
            hashAllUserFilterObj.put("UserDetails", vecAllUserFilterObj);
            if(BuildConfig.DMODE)
                System.out.println("HashResult : " + hashAllUserFilterObj);
            rsAllUserFilter.getStatement().close();
            rsAllUserFilter.close();
        }
        catch(SQLException e)
        {
            if(BuildConfig.DMODE)
                e.printStackTrace();
            logger.error("GENERAL SQL ERROR");
            
            throw new SecurityAdminException("","GENERAL SQL ERROR",e.toString());
        }
        
        finally
        {
            try
            {if(objDBConnection != null)
                if(objDBConnection.getConnection() != null )
                    if(!objDBConnection.getConnection().isClosed())
                    {
                        objDBConnection.closeConnection();
                    }
            }
            catch(SQLException ex)
            {
                
                logger.error("CONNECTION ERROR", ex);
            }
        }
        
        
        logger.info("GET All USER DETAIL FILTER END");
        
        if(BuildConfig.DMODE)
            System.out.println("Get All Users with filter is finished");
        return hashAllUserFilterObj;
    }
    
    
    
    /* TO UPDATE THE USER DETAILS */
    public boolean updateUser(SecAdminUserDetails usrObj) throws SQLException, SecurityAdminException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalStateException, IllegalBlockSizeException, BadPaddingException
    {
        /* updating the user group details should be done */
        
        logger.info("USER UPDATE STARTED");
        
        if(usrObj==null)
        {
            logger.error("USER OBJECT IS NULL");
            throw new SecurityAdminException("EC","OBJECT IS NULL","");
        }
        
        boolean boolResult = false;
        DBConnection objDBConnection = null;
        
        try
        {
            objDBConnection = DBConnectionFactory.getConnection();
            objDBConnection.startTransaction();
            /* start of USER RECORD UPDATE */
            int contact_Update_Result = 0;
            contact_Update_Result = updateContact(usrObj.getObj_Contact_Details(),objDBConnection);
            if(BuildConfig.DMODE)
                System.out.println("contact_Update_Result : " + contact_Update_Result);
            if(contact_Update_Result==0)
            {
                boolResult = false;
                throw new SecurityAdminException("EC","SQL ERROR","");
            }
            Hashtable ht_User_Update = new Hashtable();
            Vector vec_User_Update = new Vector();
            logger.info("USER DETAILS UPDATE BLOCK STARTED");
            if(BuildConfig.DMODE)
                System.out.println("Before user Update ...");
            
            ht_User_Update.put("USER_DESC", usrObj.getUser_Desc());
            ht_User_Update.put("USER_NAME", usrObj.getUser_Name());
            if(BuildConfig.DMODE)
                System.out.println("HASH TABLE: "+ht_User_Update);
            
            Key key = new SecretKeySpec((usrObj.getUser_Name()+"99999999").substring(0,8).toLowerCase().getBytes(),"DES");
            if(BuildConfig.DMODE)
                System.out.println("Key value :"+key);
            Cipher cipher = Cipher.getInstance("DES");
            
            byte[] data = usrObj.getUser_Password().getBytes();
            if(BuildConfig.DMODE)
                System.out.println("Original data : " + new String(data));
            
            cipher.init(Cipher.ENCRYPT_MODE, key);
            String result = new BASE64Encoder().encode(cipher.doFinal(data));
            if(BuildConfig.DMODE)
                System.out.println("Encrypted data: " + result);
            ht_User_Update.put("USER_PWD", result);
            
            
            int userUpdateResult = 0;
            
            try
            {
                
                userUpdateResult = objDBConnection.executeUpdateStatement(SQLMaster.USER_UPDATE_SQL_QUERY,ht_User_Update);
                
            }
            catch(SQLException ex)
            {
                if(BuildConfig.DMODE)
                    ex.printStackTrace();
                
                logger.error("SQL ERROR : " + ex);
                boolResult = false;
                if(ex.toString().indexOf("unique") >= 0 || ex.toString().indexOf("Duplicate entry") >= 0)
                    throw new SecurityAdminException("EC","UNIQUE CONSTRAINT VIOLATED",ex.toString());
                else if(ex.toString().indexOf("integrity") >= 0 || ex.toString().indexOf("foreign key constraint") >= 0 )
                    throw new SecurityAdminException("EC","PARENT KEY NOT FOUND",ex.toString());
                else
                    throw new SecurityAdminException("EC","GENERAL SQL ERROR",ex.toString());
            }
            if(BuildConfig.DMODE)
                System.out.println("update Result : " + userUpdateResult);
            if(userUpdateResult>=1)
            {
                
                logger.debug("USER RECORD UPDATED : " + vec_User_Update);
                
                /* start of DELETE EXISTING USER GROUP RECORDS */
                
                Vector vecUsrGrpObj = usrObj.getUser_Rolls();
                if(BuildConfig.DMODE)
                    System.out.println("Delete Existing User Group Records Started...");
                if(BuildConfig.DMODE)
                {
                    
                    logger.info("USER GROUP DELETE EXISTING RECORDS");
                }
                
                Hashtable htUsrGrpDelete = new Hashtable();
                htUsrGrpDelete.put("USER_NAME", usrObj.getUser_Name());
                
                
                int usrGrpDeleteResult = 0;
                
                try
                {
                    
                    usrGrpDeleteResult = objDBConnection.executeUpdateStatement(SQLMaster.USER_GRP_DELETE_SQL_QUERY,htUsrGrpDelete);
                    if(BuildConfig.DMODE)
                    {
                        
                        logger.debug("USER GROUP DELETED");
                        logger.debug("NUMBER OF RECORDS DELETED : " + usrGrpDeleteResult);
                    }
                }
                catch(SQLException ex)
                {
                    if(BuildConfig.DMODE)
                    {
                        ex.printStackTrace();
                    }
                    logger.error("SQL ERROR", ex);
                    //objDBConnection.rollBackTransaction();
                    boolResult = false;
                    throw new SQLException(ex.toString());
                }
                if(BuildConfig.DMODE)
                    System.out.println("Delete Existing User Group Records Ended...");
                /* end of DELETE EXISTING USER GROUP RECORDS*/
                
                /* start of ADDING NEW USER GROUP RECORDS */
                if(BuildConfig.DMODE)
                    System.out.println("Adding New User Group Started...");
                for(int i=0;i<vecUsrGrpObj.size();i++)
                {
                    logger.info("USER GROUP UPDATE STARTED");
                    
                    
                    Hashtable htUsrGrpUpdate = new Hashtable();
                    htUsrGrpUpdate.put("USER_NAME", usrObj.getUser_Name());
                    htUsrGrpUpdate.put("ROLE_ID",vecUsrGrpObj.get(i));
                    
                    
                    int usrGrpUpdateResult = 0;
                    
                    try
                    {
                        
                        usrGrpUpdateResult = objDBConnection.executeUpdateStatement(SQLMaster.USER_GRP_ADD_SQL_QUERY,htUsrGrpUpdate);
                        
                        if(usrGrpUpdateResult >= 1)
                        {
                            logger.debug("USER GROUP ADDED");
                            
                        }
                        else
                        {
                            
                            logger.debug("USER GROUP NOT ADDED");
                            
                            boolResult = false;
                            throw new SQLException("SQL ERROR");
                        }
                    }
                    catch(SQLException ex)
                    {
                        objDBConnection.rollBackTransaction();
                        if(BuildConfig.DMODE)
                        {
                            ex.printStackTrace();
                        }
                        logger.error("SQL ERROR", ex);
                        boolResult = false;
                        if(ex.toString().indexOf("unique") >= 0 || ex.toString().indexOf("Duplicate entry") >= 0)
                            throw new SecurityAdminException("EC","UNIQUE CONSTRAINT VIOLATED",ex.toString());
                        else if(ex.toString().indexOf("integrity") >= 0 || ex.toString().indexOf("foreign key constraint") >= 0 )
                            throw new SecurityAdminException("EC","PARENT KEY NOT FOUND",ex.toString());
                        else
                            throw new SecurityAdminException("EC","GENERAL SQL ERROR",ex.toString());
                    }
                }
                
                logger.info("USER GROUP UPDATE ENDED");
                
                /* END OF ADDING NEW USER GROUP RECORDS */
                boolResult = true;
                objDBConnection.commitTransaction();
            }
            else
            {
                
                logger.error("USER RECORD NOT UPDATED");
                boolResult = false;
                objDBConnection.rollBackTransaction();
                throw new SecurityAdminException("EC","UserNotFound","");
            }
            
            /* end of USER RECORD UPDATE */
        }
        catch(SQLException e)
        {
            objDBConnection.rollBackTransaction();
            if(BuildConfig.DMODE)
                e.printStackTrace();
            
            logger.error("GENERAL SQL ERROR");
            boolResult = false;
            throw new SecurityAdminException("EC","GENERAL SQL ERROR",e.toString());
        }
        
        finally
        {
            try
            {
                if(objDBConnection != null)
                if(objDBConnection.getConnection() != null )
                    if(!objDBConnection.getConnection().isClosed())
                    {
                        objDBConnection.closeConnection();
                    }
            }
            catch(SQLException ex)
            {
                
                logger.error("CONNECTION ERROR", ex);
            }
            
        }
        return boolResult;
    }
    
    public HashMap deleteUsers(Vector userIds) throws SecurityAdminException
    {
        
        logger.info("USER DELETE STARTED");
        
        
        HashMap hashResult = new HashMap();
        DBConnection objDBConnection = null;
        
        if(userIds == null)
        {
            
            logger.error("USER VECTOR NULL");
            
            throw new SecurityAdminException("EC","USER VECTOR NULL","");
        }
        
        try
        {
            objDBConnection = DBConnectionFactory.getConnection();
            objDBConnection.startTransaction();
            for(int i=0;i<userIds.size();i++)
            {
                Hashtable htUserId = new Hashtable();
                htUserId.put("USER_NAME", userIds.get(i));
                int deleteUserGrpResult = objDBConnection.executeUpdateStatement(SQLMaster.USER_GRP_DELETE_SQL_QUERY, htUserId);
                if(deleteUserGrpResult >=1)
                {
                    
                    logger.debug("USER GROUP DELETED");
                    
                }
                else
                {
                    
                    logger.debug("USER GROUP NOT DELETED");
                    
                }
                int deleteUserResult = objDBConnection.executeUpdateStatement(SQLMaster.USER_DELETE_SQL_QUERY,htUserId);
                if(deleteUserResult >=1)
                {
                    hashResult.put(userIds.get(i),new Integer(0));
                    objDBConnection.commitTransaction();
                }
                else
                {
                    hashResult.put(userIds.get(i),new Integer(1));
                    objDBConnection.rollBackTransaction();
                }
                
            }
        }
        catch(SQLException e)
        {
            if(BuildConfig.DMODE)
            {
                e.printStackTrace();
            }
            
            if(e.toString().indexOf("integrity constraint") >= 0 || e.toString().indexOf("foreign key constraint") >= 0 )
            {
                
                logger.debug("CONSTRAINT VIOLATED - RECORD IN USE");
                throw new SecurityAdminException("EC","RECORD IN USE.CONSTRAINT VIOLATED",e.toString());
            }
            else
            {
                logger.error("GENERAL SQL ERROR");
                throw new SecurityAdminException("EC","GENERAL SQL ERROR",e.toString());
            }
            
            
        }
        
        finally
        {
            try
            {
                if(objDBConnection != null)
                    if(objDBConnection.getConnection() != null )
                        if(!objDBConnection.getConnection().isClosed())
                        {
                            objDBConnection.closeConnection();
                        }
            }
            catch(SQLException ex)
            {
                
                logger.error("CONNECTION ERROR", ex);
            }
        }
        
        logger.info("USER DELETE END");
        
        if(BuildConfig.DMODE)
            System.out.println("Delete Users Ended ");
        return hashResult;
    }
    
    
    
    ////////////////////////////////////////////////////GROUP///////////////////////////////////////////////
    
    
    /* TO ADD NEW GROUP WITH RESOURCES */
    public boolean addNewGroup(SecAdminGroupDetails grpObj) throws SQLException, SecurityAdminException
    {
        if(BuildConfig.DMODE)
            System.out.println("Add New Group Starts...");
        
        
        logger.info("ADD NEW GROUP STARTED");
        
        if(grpObj==null)
        {
            
            logger.error("GROUP OBJECT IS NULL");
            throw new SQLException("OBJECT IS NULL");
        }
        if(grpObj.getGroup_Name().length()<1)
        {
            
            logger.error("GROUP NAME IS NULL");
            throw new SQLException("GROUP NAME IS NULL");
        }
        
        boolean boolResult = false;
        DBConnection objDBConnection = null;
        objDBConnection = DBConnectionFactory.getConnection();
        objDBConnection.startTransaction();
        /* start OF ADD NEW GROUP DETAILS */
        
        logger.debug("GROUP ADD STARTs... ");
        
        
        Hashtable htGrpAdd = new Hashtable();
        htGrpAdd.put("ROLE_NAME", grpObj.getGroup_Name());
        htGrpAdd.put("ROLE_DESC", grpObj.getGroup_Desc());
        int grpAddResult = 0;
        
        try
        {
            grpAddResult = objDBConnection.executeUpdateStatement(SQLMaster.GRP_ADD_SQL_QUERY, htGrpAdd);
            /* CHECK WHETHER GROUP IS ADDED OR NOT */
            if(grpAddResult >= 1)
            {
                if(BuildConfig.DMODE)
                    System.out.println("Group Details added and to get the new group Id ");
                int newGrpId = 0;
                logger.debug("GROUP ADDED");
                /* start of GET THE NEW GROUP ID */
                
                logger.info(" GET NEW GROUP ID STARTED");
                
                PreparedStatement ps = objDBConnection.executeStatement(SQLMaster.GET_CURRENT_GRP_ID_SQL_QUERY);
                ResultSet rsGetNewGrpId = ps.executeQuery();
                
                if(rsGetNewGrpId.next())
                {
                    newGrpId = rsGetNewGrpId.getInt(1);
                }
                else
                {
                    
                    logger.error("RECORD ID NOT GET");
                    boolResult = false;
                    throw new SecurityAdminException("EC10-US02","RECORD NOT ADDED","");/*Exception should be verified*/
                }
                rsGetNewGrpId.close();
                ps.close();
                
                /* end of GET THE NEW GROUP ID */
                if(BuildConfig.DMODE)
                    System.out.println("New group Id " + newGrpId);
                /* start of ADD THE NEW GROUP RESOURCES */
                
                
                logger.info("NEW GROUP ID : " + newGrpId);
                logger.info("ADD NEW GROUP RESOURCES STARTED ");
                
                Vector vecSelectedResIds = grpObj.getVResources();
                for(int i=0;i<vecSelectedResIds.size();i++)
                {
                    
                    Hashtable htAddGrpRes = new Hashtable();
                    htAddGrpRes.put("ROLE_ID", new Integer(newGrpId));
                    htAddGrpRes.put("RESRC_ID", vecSelectedResIds.get(i));
                    
                    int grpResAddResult = objDBConnection.executeUpdateStatement(SQLMaster.ADD_GRP_RES_SQL_QUERY, htAddGrpRes);
                    if(grpResAddResult>=1)
                    {
                        
                        logger.debug("GROUP RESOURCE ADDED");
                    }
                    else
                    {
                        
                        logger.debug("GROUP RESOURCE NOT ADDED");
                        boolResult = false;
                        throw new SecurityAdminException("EC10-USO2","Group Already Exists","");
                    }
                }
                
                
                logger.info("GROUP RESOURCE ADD BLOCK COMPLETED");
                
                /* end of ADD THE NEW GROUP RESOURCES */
                if(BuildConfig.DMODE)
                    System.out.println("Add new group resource finished");
                
                objDBConnection.commitTransaction();
                logger.info("ADD GROUP ENDED");
                
            }
            
            /* end of ADD THE NEW GROUP */
            if(BuildConfig.DMODE)
                System.out.println("Add new group finished");
            
            logger.debug("ADD NEW GROUP ENDED");
            
            boolResult = true;
            
        }
        catch(SQLException ex)
        {
            
            if(BuildConfig.DMODE)
                ex.printStackTrace();
            
            logger.error("GROUP EXIST", ex);
            objDBConnection.rollBackTransaction();
            
            if(ex.toString().indexOf("unique") >= 0 || ex.toString().indexOf("Duplicate entry") >= 0)
                throw new SecurityAdminException("EC11-US02","UNIQUE CONSTRAINT VIOLATED", ex.toString());
            else if(ex.toString().indexOf("integrity") >= 0 || ex.toString().indexOf("foreign key constraint") >= 0)
                throw new SecurityAdminException("EC11-US02","PARENT KEY NOT FOUND", ex.toString());
            else
                throw new SecurityAdminException("","GENERAL SQL ERROR",ex.toString());
        }
        
        finally
        {
            if(BuildConfig.DMODE)
                System.out.println("Result : " + boolResult);
            try
            {
                if(objDBConnection != null)
                    if(objDBConnection.getConnection() != null)
                        if(!objDBConnection.getConnection().isClosed())
                            objDBConnection.closeConnection();
            }
            catch(SQLException ex)
            {
                
                logger.error("CONNECTION ERROR", ex);
            }
        }
        if(BuildConfig.DMODE)
            System.out.println("Add New Group Ends...");
        return boolResult;
    }
    
    /* TO UPDATE THE GROUP DETAILS WITH UPDATED GROUP RESOURCES */
    public boolean updateGroup(SecAdminGroupDetails grpObj) throws SQLException, SecurityAdminException
    {
        
        logger.info("GROUP UPDATE STARTED");
        
        if(grpObj==null)
        {
            
            logger.error("GROUP OBJECT IS NULL");
            throw new SQLException("OBJECT IS NULL");
        }
        boolean boolResult = false;
        DBConnection objDBConnection = null;
        
        try
        {
            objDBConnection = DBConnectionFactory.getConnection();
            objDBConnection.startTransaction();
            
            /* start of UPDATE THE GROUP RESOURCES */
            if(BuildConfig.DMODE)
                System.out.println("Deleting the existing Group resources");
            
            
            logger.info("GROUP RESOURCE DELETE OLD VALUES...");
            
            
            Hashtable htDeleteGrpRes = new Hashtable();
            htDeleteGrpRes.put("ROLE_ID", new Integer(grpObj.getGroup_Id()));
            
            
            objDBConnection.executeUpdateStatement(SQLMaster.DELETE_GRP_RES_SQL_QUERY, htDeleteGrpRes);
            //if(grpResDeleteResult>=1)
            //	{
            if(BuildConfig.DMODE)
                System.out.println("Old Values Deleted");
            
            logger.debug("GROUP RESOURCE DELETED");
            
            /* start of Adding the new Group Resources */
            Vector vecSelectedResIds = grpObj.getVResources();
            
            for(int i=0;i<vecSelectedResIds.size();i++)
            {
                
                Hashtable htAddGrpRes = new Hashtable();
                htAddGrpRes.put("ROLE_ID", new Integer(grpObj.getGroup_Id()));
                htAddGrpRes.put("RESRC_ID", vecSelectedResIds.get(i));
                if(BuildConfig.DMODE)
                    System.out.println("Hash Table values : " + htAddGrpRes);
                
                int grpResAddResult = objDBConnection.executeUpdateStatement(SQLMaster.ADD_GRP_RES_SQL_QUERY, htAddGrpRes);
                if(grpResAddResult>=1)
                {
                    logger.debug("GROUP RESOURCE ADDED");
                }
                else
                {
                    
                    logger.debug("GROUP RESOURCE NOT ADDED");
                    boolResult = false;
                    throw new SQLException("SQL ERROR");
                }
            }
            
            
            logger.info("GROUP RESOURCE ADD BLOCK COMPLETED");
            
            if(BuildConfig.DMODE)
                System.out.println("Group Resource updated completed");
            /* end of UPDATE THE NEW GROUP RESOURCES */
            
            /* start of UPDATE THE GROUP RESOURCES */
            Hashtable htGrpUpdate = new Hashtable();
            Vector vecGrpUpdate = new Vector();
            
            
            logger.info("GROUP DETAILS UPDATE BLOCK STARTED");
            
            if(BuildConfig.DMODE)
                System.out.println("Before group Update ...");
            
            htGrpUpdate.put("ROLE_DESC", grpObj.getGroup_Desc());
            htGrpUpdate.put("ROLE_ID", new Integer(grpObj.getGroup_Id()));
            htGrpUpdate.put("ROLE_NAME",grpObj.getGroup_Name());
            
            if(BuildConfig.DMODE)
                System.out.println("HASH TABLE: " + htGrpUpdate);
            
            int grpUpdateResult = 0;
            
            try
            {
                grpUpdateResult = objDBConnection.executeUpdateStatement(SQLMaster.GRP_UPDATE_SQL_QUERY, htGrpUpdate);
                
            }
            catch(SQLException ex)
            {
                if(BuildConfig.DMODE)
                {
                    System.out.println("CATCH DURING EXECUTION");
                    ex.printStackTrace();
                }
                
                
                logger.error("SQL ERROR : " + ex);
                boolResult = false;
                if(ex.toString().indexOf("unique") >= 0 || ex.toString().indexOf("Duplicate entry") >= 0)
                    throw new SecurityAdminException("EC11-US02","UNIQUE CONSTRAINT VIOLATED", ex.toString());
                else if(ex.toString().indexOf("integrity") >= 0 || ex.toString().indexOf("foreign key constraint") >= 0)
                    throw new SecurityAdminException("EC11-US02","PARENT KEY NOT FOUND", ex.toString());
                else
                    throw new SecurityAdminException("","GENERAL SQL ERROR",ex.toString());
            }
            
            if(grpUpdateResult>=1)
            {
                
                logger.debug("GROUP RECORD UPDATED : " + vecGrpUpdate);
                objDBConnection.commitTransaction();
                boolResult = true;
            }
            else
            {
                
                logger.error("GROUP RECORD NOT UPDATED");
                objDBConnection.rollBackTransaction();
                boolResult = false;
            }
            logger.info("GROUP RESOURCE UPDATE BLOCK STARTED...");
        }
        catch(SQLException e)
        {
            if(BuildConfig.DMODE)
                e.printStackTrace();
            
            logger.error("GENERAL SQL ERROR");
            objDBConnection.rollBackTransaction();
            throw new SecurityAdminException("EC","GENERAL SQL ERROR",e.toString());
        }
        
        finally
        {
            try
            {
                if(objDBConnection != null)
                    if(objDBConnection.getConnection() != null )
                        if(!objDBConnection.getConnection().isClosed())
                        {
                            objDBConnection.closeConnection();
                        }
            }
            catch(SQLException ex)
            {
                
                logger.error("CONNECTION ERROR", ex);
            }
        }
        return boolResult;
    }
    
    public LinkedHashMap getAllGroups() throws SQLException, SecurityAdminException
    {
        if(BuildConfig.DMODE)
            System.out.println("start of GET ALL GROUPS");
        if(BuildConfig.DMODE)
        {
            
            logger.info("GET ALL GROUPS STARTED...");
        }
        
        LinkedHashMap hashAllGroups = new LinkedHashMap();
        DBConnection objDBConnection = null;
        
        try
        {
            objDBConnection = DBConnectionFactory.getConnection();
            Statement stmt = objDBConnection.getConnection().createStatement();
            ResultSet rsGetAllGroups = null;
            
            try
            {
                rsGetAllGroups = stmt.executeQuery(SQLMaster.GET_ALL_GRP_ID_SQL_QUERY);
            }
            catch(SQLException e)
            {
                
                if(BuildConfig.DMODE)
                    e.printStackTrace();
                
                logger.error("SQL ERROR", e);
                
                throw new SecurityAdminException("","GENERAL SQL ERROR",e.toString());
            }
            while(rsGetAllGroups.next())
            {
                hashAllGroups.put(rsGetAllGroups.getString("ROLE_ID"),rsGetAllGroups.getString("ROLE_NAME"));
            }
            rsGetAllGroups.close();
            
            
        }
        catch(SQLException e)
        {
            
            if(BuildConfig.DMODE)
                e.printStackTrace();
            logger.error("GENERAL SQL ERROR", e);
            
            throw new SecurityAdminException("","GENERAL SQL ERROR",e.toString());
        }
        finally
        {
            try
            {
                if(objDBConnection != null)
                    if(objDBConnection.getConnection() != null )
                        if(!objDBConnection.getConnection().isClosed())
                        {
                            objDBConnection.closeConnection();
                        }
            }
            catch(SQLException clse)
            {
                
                logger.error("CONNECTION CLOSING EXCEPTION", clse);
                throw new SQLException("CONNECTION CLOSING EXCEPTION" + clse.toString());
            }
        }
        
        
        logger.info("GET ALL GROUPS ENDED...");
        
        if(BuildConfig.DMODE)
            System.out.println("end of GET ALL GROUPS");
        return hashAllGroups;
    }
    
    
    public SecAdminGroupDetails getGroupDetails(int groupId) throws SQLException, SecurityAdminException
    {
        if(BuildConfig.DMODE)
            System.out.println("start of GET GROUP DETAILs");
        
        
        logger.info("GET GROUP DETAIL OF " + groupId + " STARTED");
        
        
        if(groupId==0)
        {
            
            logger.error("GROUP ID IS NULL");
            
            throw new SecurityAdminException("EC01","GROUP NAME IS NULL","");
        }
        
        SecAdminGroupDetails grpDetailObj = new SecAdminGroupDetails();
        
        DBConnection objDBConnection = null;
        
        try
        {
            objDBConnection = DBConnectionFactory.getConnection();
            
            
            /* start of GET GROUP RESOURCE */
            ResultSet rsResDetail = null;
            Hashtable htGrpResDetail = new Hashtable();
            
            htGrpResDetail.put("ROLE_ID",new Integer(groupId));
            PreparedStatement psResDetail = objDBConnection.executeStatement(SQLMaster.GET_GRP_RES_SQL_QUERY,htGrpResDetail);
            rsResDetail = psResDetail.executeQuery();
            HashMap hashGrpRes = new HashMap();
            while(rsResDetail.next())
            {
                hashGrpRes.put(rsResDetail.getString("RESRC_ID"),rsResDetail.getString("RESRC_NAME"));
            }
            rsResDetail.close();
            psResDetail.close();
            
            grpDetailObj.setHmResources(hashGrpRes);
            if(BuildConfig.DMODE)
                System.out.println("hashGrpRes : " + hashGrpRes);
            /*end of GET GROUP RESOURCE */
            
            /*start of GET GROUP DETAILS */
            ResultSet rsGrpDetail = null;
            PreparedStatement psGrpDetail = null;
            Hashtable htGrpDetail = new Hashtable();
            
            htGrpDetail.put("ROLE_ID", new Integer(groupId));
            
            
            logger.info("GET GROUP DETAILS STARED");
            
            
            try
            {
                psGrpDetail = objDBConnection.executeStatement(SQLMaster.GET_GRP_DETAIL_SQL_QUERY,htGrpDetail);
                rsGrpDetail = psGrpDetail.executeQuery();
            }
            catch(SQLException ex)
            {
                
                logger.error("SQL ERROR", ex);
                throw new SQLException("SQL ERROR");
            }
            
            if(rsGrpDetail.next())
            {
                String st="";
                grpDetailObj.setGroup_Id(rsGrpDetail.getInt("ROLE_ID"));
                grpDetailObj.setGroup_Name((st=rsGrpDetail.getString("ROLE_NAME"))!= null ? st : "");
                grpDetailObj.setGroup_Desc((st=rsGrpDetail.getString("ROLE_DESC"))!=null ? st : "");
                grpDetailObj.setGroup_DateStamp(rsGrpDetail.getTimestamp("ROLE_DATESTAMP"));
                grpDetailObj.setGroup_IsValid(rsGrpDetail.getInt("ROLE_ISVALID"));
            }
            else
            {
                throw new SecurityAdminException("EC","Group Not Found","");
            }
            rsGrpDetail.close();
            psGrpDetail.close();
            /* end of GET GROUP DETAILS */
            
            
            logger.debug("GET GROUP DETAILS FINISHED");
            
            
        }
        catch(SQLException e)
        {
            
            if(BuildConfig.DMODE)
                e.printStackTrace();
            logger.error("GENERAL SQL ERROR");
            throw new SecurityAdminException("","GENERAL SQL ERROR",e.toString());
        }
        
        finally
        {
            try
            {
                if(objDBConnection != null)
                    if(objDBConnection.getConnection() != null )
                        if(!objDBConnection.getConnection().isClosed())
                        {
                            objDBConnection.closeConnection();
                        }
            }
            catch(SQLException clse)
            {
                
                logger.error("CONNECTION CLOSING EXCEPTION", clse);
                throw new SQLException("CONNECTION CLOSING EXCEPTION" + clse.toString());
            }
        }
        
        if(BuildConfig.DMODE)
            System.out.println("end of GET USER DETAILs");
        return grpDetailObj;
    }
    
    
    public HashMap getAllGroupsDetails(Filter[] filters, String sortBy, boolean sortOrder, int stIndex, int displayCount) throws SQLException, SecurityAdminException
    {
        if(BuildConfig.DMODE)
            System.out.println("Get All group details started...");
        
        
        logger.info("GET ALL GROUP DETAILS WITH FILTER STARTED");
        
        
        if((filters == null)||( sortBy == null))
        {
            
            logger.error("GROUP FILTER VALUES ARE NULL");
            throw new SQLException("GROUP FILTER VALUES ARE NULL");
        }
        
        HashMap hashAllGrpFilterObj = new HashMap();
        Vector vecAllGrpFilterObj = new Vector();
        int totalRecordCount = 0;
        DBConnection objDBConnection = null;
        ResultSet rsAllGrpFilter = null;
        
        try
        {
            
            objDBConnection = DBConnectionFactory.getConnection();
            
            totalRecordCount = objDBConnection.getRowCountWithComplexFilters(filters,SQLMaster.GET_ALL_GRP_FILTER_SQL_QUERY);
            int endIndex = stIndex + displayCount;
            
            
            // filters,tablename,startindex,endindex,sorting column name and sortorder are passed to the function and  query will be received
            String Query = objDBConnection.buildDynamicQuery(filters,stIndex,endIndex,sortBy,sortOrder,SQLMaster.GET_ALL_GRP_FILTER_SQL_QUERY);
            
            if(BuildConfig.DMODE)
            {
                // total records and query
                System.out.println("TOTAL NUMBER OF RECORDS : " + totalRecordCount);
                System.out.println("QUERY : " + Query);
            }
            
            
            if(BuildConfig.DMODE)
                System.out.println("Filter Query : " + Query);
            
            
            logger.debug("TOTAL RECORDS : " + totalRecordCount);
            
            
            
            rsAllGrpFilter = objDBConnection.executeRownumStatement(Query);
            
            while(rsAllGrpFilter.next())
            {
                String st="";
                SecAdminGroupDetails grpObj = new SecAdminGroupDetails();
                grpObj.setGroup_Id(rsAllGrpFilter.getInt("ROLE_ID"));
                grpObj.setGroup_Name(rsAllGrpFilter.getString("ROLE_NAME"));
                grpObj.setGroup_Desc((st=rsAllGrpFilter.getString("ROLE_DESC"))!=null ? st:"");
                grpObj.setGroup_IsValid(rsAllGrpFilter.getInt("ROLE_ISVALID"));
                
                if(BuildConfig.DMODE)
                {
                    
                    logger.debug("GROUP SELECTED ID : " + grpObj.getGroup_Id());
                }
                
                vecAllGrpFilterObj.addElement(grpObj);
            }
            rsAllGrpFilter.getStatement().close();
            rsAllGrpFilter.close();
            
            hashAllGrpFilterObj.put("TotalRecordCount", new Integer(totalRecordCount));
            hashAllGrpFilterObj.put("GroupDetails", vecAllGrpFilterObj);
            if(BuildConfig.DMODE)
                System.out.println("HashResult : " + hashAllGrpFilterObj);
        }
        catch(SQLException e)
        {
            if(BuildConfig.DMODE)
                e.printStackTrace();
            
            logger.error("GENERAL SQL ERROR");
            
            throw new SecurityAdminException("","GENERAL SQL ERROR",e.toString());
        }
        
        finally
        {
            try
            {
                if(objDBConnection != null)
                    if(objDBConnection.getConnection() != null )
                        if(!objDBConnection.getConnection().isClosed())
                        {
                            objDBConnection.closeConnection();
                        }
            }
            catch(SQLException ex)
            {
                
                logger.error("CONNECTION ERROR", ex);
            }
        }
        
        
        logger.info("GET All GROUP DETAIL FILTER END");
        
        if(BuildConfig.DMODE)
            System.out.println("Get All Groups with filter is finished");
        return hashAllGrpFilterObj;
    }
    
    public HashMap makeGroupsValid(Vector groupIds) throws SecurityAdminException
    {
        
        
        logger.info("MAKE GROUP VALID STARTED");
        
        
        HashMap hashResult = new HashMap();
        DBConnection objDBConnection = null;
        
        if(groupIds == null)
        {
            
            logger.error("GROUP VECTOR NULL");
            
            throw new SecurityAdminException("EC","GROUP VECTOR NULL","");
        }
        
        try
        {
            objDBConnection = DBConnectionFactory.getConnection();
            objDBConnection.startTransaction();
            for(int i=0;i<groupIds.size();i++)
            {
                Hashtable htGroupId = new Hashtable();
                htGroupId.put("ROLE_ID", groupIds.get(i));
                
                int makeGrpUsrValidResult = objDBConnection.executeUpdateStatement(SQLMaster.MAKE_GRP_USR_VALID_SQL_QUERY, htGroupId);
                
                if(BuildConfig.DMODE)
                    System.out.println("result : " + makeGrpUsrValidResult);
                
                try
                {
                    int makeGrpValidResult = objDBConnection.executeUpdateStatement(SQLMaster.MAKE_GRP_VALID_SQL_QUERY, htGroupId);
                    if(BuildConfig.DMODE)
                        System.out.println("result : " + makeGrpValidResult);
                    if(makeGrpValidResult >=1)
                    {
                        hashResult.put(groupIds.get(i),new Integer(0));
                        objDBConnection.commitTransaction();
                        
                        
                        logger.debug("GROUP VALIDATED");
                        
                    }
                    else
                    {
                        hashResult.put(groupIds.get(i),new Integer(1));
                        objDBConnection.rollBackTransaction();
                        
                        
                        logger.debug("GROUP NOT VALIDATED");
                        
                    }
                }
                catch(SQLException sqle)
                {
                    //sqle.printStackTrace();
                    objDBConnection.rollBackTransaction();
                    
                    if(sqle.toString().indexOf("integrity constraint") >= 0)
                    {
                        hashResult.put(groupIds.get(i), new Integer(2));
                        
                        logger.debug("CONTRAINT VIOLATED - RECORD IN USE");
                    }
                    else
                    {
                        hashResult.put(groupIds.get(i), new Integer(4));
                    }
                }
            } /* End of For loop */
        }
        catch(SQLException e)
        {
            if(BuildConfig.DMODE)
                e.printStackTrace();
            
            logger.error("GENERAL SQL ERROR");
            
            throw new SecurityAdminException("EC","GROUP VECTOR NULL","");
        }
        
        finally
        {
            try
            {
                if(objDBConnection != null)
                    if(objDBConnection.getConnection() != null )
                        if(!objDBConnection.getConnection().isClosed())
                        {
                            objDBConnection.closeConnection();
                        }
            }
            catch(SQLException ex)
            {
                
                logger.error("CONNECTION ERROR", ex);
            }
        }
        
        
        logger.info("GROUP VALID END");
        
        return hashResult;
    }
    
    public HashMap makeGroupsInValid(Vector groupIds) throws SecurityAdminException
    {
        if(BuildConfig.DMODE)
            System.out.println("Make Group InValid Started");
        
        
        
        logger.info("MAKE GROUP INVALID STARTED");
        
        
        HashMap hashResult = new HashMap();
        DBConnection objDBConnection = null;
        
        if(groupIds == null)
        {
            if(BuildConfig.DMODE)
                System.out.println("Group Ids is Null");
            
            logger.error("GROUP VECTOR NULL");
            
            throw new SecurityAdminException("EC","GROUP VECTOR NULL","");
        }
        
        try
        {
            objDBConnection = DBConnectionFactory.getConnection();
            objDBConnection.startTransaction();
            for(int i=0;i<groupIds.size();i++)
            {
                Hashtable htGroupId = new Hashtable();
                htGroupId.put("ROLE_ID", groupIds.get(i));
                
                int makeGrpInvalidResult = objDBConnection.executeUpdateStatement(SQLMaster.MAKE_GRP_INVALID_SQL_QUERY, htGroupId);
                if(BuildConfig.DMODE)
                    System.out.println("Result : " + makeGrpInvalidResult);
                if(makeGrpInvalidResult >=1)
                {
                    
                    logger.info("MAKE GROUP INVALID STARTED");
                    
                    
                    objDBConnection.executeUpdateStatement(SQLMaster.MAKE_GRP_USR_INVALID_SQL_QUERY, htGroupId);
                    
                    
                    hashResult.put(groupIds.get(i),new Integer(0));
                    objDBConnection.commitTransaction();
                    
                }
                else
                {
                    hashResult.put(groupIds.get(i),new Integer(1));
                    objDBConnection.rollBackTransaction();
                    
                    
                    logger.debug("GROUP NOT INVALIDATED");
                    
                }
                
            }
        }
        catch(SQLException e)
        {
            if(BuildConfig.DMODE)
                e.printStackTrace();
            logger.error("GENERAL SQL ERROR");
            
            throw new SecurityAdminException("EC","GENERAL SQL ERROR","");
        }
        
        finally
        {
            try
            {
                if(objDBConnection != null)
                    if(objDBConnection.getConnection() != null )
                        if(!objDBConnection.getConnection().isClosed())
                        {
                            objDBConnection.closeConnection();
                        }
            }
            catch(SQLException ex)
            {
                
                logger.error("CONNECTION ERROR", ex);
            }
        }
        
        
        logger.info("GROUP INVALID END");
        
        if(BuildConfig.DMODE)
            System.out.println("Make Group InValid Ended");
        return hashResult;
    }
    
    public HashMap deleteGroups(Vector groupIds) throws SecurityAdminException
    {
        
        
        logger.info("GROUP DELETE STARTED");
        
        
        HashMap hashResult = new HashMap();
        DBConnection objDBConnection = null;
        
        if(groupIds == null)
        {
            
            logger.error("GROUP VECTOR NULL");
            
            throw new SecurityAdminException("EC","GROUP VECTOR NULL","");
        }
        
        try
        {
            objDBConnection = DBConnectionFactory.getConnection();
            objDBConnection.startTransaction();
            if(BuildConfig.DMODE)
                System.out.println("Group Ids : " + groupIds);
            for(int i=0;i<groupIds.size();i++)
            {
                Hashtable htGrpId = new Hashtable();
                htGrpId.put("ROLE_ID", groupIds.get(i));
                
                if(BuildConfig.DMODE)
                    System.out.println("Hash Values : " + htGrpId);
                
                
                /* start of delete group resources */
                
                logger.info("DELETE GROUP RESOURCES STARTED");
                try
                {
                    
                    int deleteGrpResResult = objDBConnection.executeUpdateStatement(SQLMaster.DELETE_GRP_RES_SQL_QUERY, htGrpId);
                    
                    if(BuildConfig.DMODE)
                        System.out.println("deleteGrpResResult : " + deleteGrpResResult);
                    if(deleteGrpResResult >=1)
                    {
                        
                        logger.debug("GROUP RESOURCE DELETED");
                        
                    }
                    else
                    {
                        
                        logger.debug("GROUP RESOURCE NOT DELETED");
                        
                    }
                    
                    logger.info("DELETE GROUP RESOURCES ENDED");
                    /* end of delete Group Resource */
                    
                    /*start of delete User Group started */
                    
                    //logger.info("DELETE GROUP USERS STARTED");
                    
                    //int deleteGrpUsrResult = objDBConnection.executeUpdateStatement(SQLMaster.USER_GRP_USR_DELETE_SQL_QUERY, htGrpId);
                    
                    //if(BuildConfig.DMODE)
                    //  System.out.println("deleteGrpUsrResult : " + deleteGrpUsrResult);
                    
                    //if(deleteGrpUsrResult >=1)
                    //{
                    
                    //  logger.debug("GROUP USERS DELETED");
                    //}
                    //else
                    //{
                    
                    //  logger.debug("GROUP USERS NOT DELETED");
                    
                    //}
                    
                    // logger.info("DELETE GROUP USERS ENDED");
                    /*end of delete User Group started */
                    
                    /*start of delete Group started */
                    
                    logger.info("DELETE GROUP STARTED");
                    int deleteGrpResult = objDBConnection.executeUpdateStatement(SQLMaster.GRP_DELETE_SQL_QUERY, htGrpId);
                    
                    if(BuildConfig.DMODE)
                        System.out.println("Group Delete Result : " + deleteGrpResult);
                    if(deleteGrpResult >=1)
                    {
                        hashResult.put(groupIds.get(i),new Integer(0));
                        objDBConnection.commitTransaction();
                        logger.debug("GROUP DELETED");
                        
                    }
                    else
                    {
                        hashResult.put(groupIds.get(i),new Integer(1));
                        objDBConnection.rollBackTransaction();
                        logger.debug("GROUP NOT DELETED");
                    }
                    
                    logger.info("DELETE GROUP USERS STARTED");
                }
                catch(SQLException e)
                {
                    if(BuildConfig.DMODE)
                        e.printStackTrace();
                    objDBConnection.rollBackTransaction();
                    if(e.toString().indexOf("integrity constraint") >= 0 || e.toString().indexOf("foreign key constraint") >= 0)
                    {
                        logger.debug("CONTRAINT VIOLATED - RECORD IN USE");
                        hashResult.put(groupIds.get(i),new Integer(2));
                        //throw new SecurityAdminException("CONTRAINT VIOLATED - RECORD IN USE","EC","");
                    }
                    else
                    {
                        logger.error("GENERAL SQL ERROR");
                        hashResult.put(groupIds.get(i),new Integer(4));
                    }
                    //throw new SecurityAdminException("GENERAL SQL ERROR","EC","");
                    
                }
                
            }
        }
        
        catch(Exception e)
        {
            if(BuildConfig.DMODE)
                e.printStackTrace();
            objDBConnection.rollBackTransaction();
            logger.error("GENERAL SQL ERROR");
            throw new SecurityAdminException("EC","GENERAL SQL ERROR","");
        }
        
        finally
        {
            try
            {
                if(objDBConnection != null)
                    if(objDBConnection.getConnection() != null )
                        if(!objDBConnection.getConnection().isClosed())
                        {
                            objDBConnection.closeConnection();
                        }
            }
            catch(SQLException ex)
            {
                
                logger.error("CONNECTION ERROR", ex);
            }
        }
        
        
        logger.info("GROUP DELETE END");
        
        if(BuildConfig.DMODE)
            System.out.println("Delete Groups Ended ");
        return hashResult;
    }
    /* TO GET ALL THE RESOURCES FROM THE PROFILE_RESOURCE TABLE*/
    public HashMap getAllResources() throws SQLException, SecurityAdminException
    {
        if(BuildConfig.DMODE)
            System.out.println("Get All Resources started...");
        
        
        logger.info("GET ALL RESOURCESS STARTED");
        
        
        DBConnection objDBConnection = null;
        HashMap hashAllResources = new HashMap();
        try
        {
            objDBConnection = DBConnectionFactory.getConnection();
            ResultSet rsGetAllResources = null;
            PreparedStatement psGetAllResources = null;
            
            try
            {
                psGetAllResources = objDBConnection.executeStatement(SQLMaster.GET_ALL_RESOURCES_SQL_QUERY);
                rsGetAllResources = psGetAllResources.executeQuery();
            }
            catch(SQLException sqle)
            {
                if(BuildConfig.DMODE)
                    sqle.printStackTrace();
                logger.error("SQL ERROR");
                throw new SecurityAdminException("","GENERAL SQL ERROR",sqle.toString());
            }
            while(rsGetAllResources.next())
            {
                hashAllResources.put(new Integer(rsGetAllResources.getInt("RESRC_ID")),rsGetAllResources.getString("RESRC_NAME"));
            }
            rsGetAllResources.close();
            psGetAllResources.close();
            
        }
        catch(SQLException e)
        {
            if(BuildConfig.DMODE)
                e.printStackTrace();
            logger.error("GENERAL SQL ERROR");
            throw new SecurityAdminException("EC","GENERAL SQL ERROR",e.toString());
        }
        
        finally
        {
            try
            {
                if(objDBConnection != null)
                    if(objDBConnection.getConnection() != null )
                        if(!objDBConnection.getConnection().isClosed())
                        {
                            objDBConnection.closeConnection();
                        }
            }
            catch(SQLException clse)
            {
                
                logger.error("CONNECTION CLOSING EXCEPTION", clse);
                throw new SQLException("CONNECTION CLOSING EXCEPTION" + clse.toString());
            }
        }
        
        
        logger.info("GET ALL RESOURCES ENDED...");
        
        if(BuildConfig.DMODE)
            System.out.println("end of GET ALL RESOURCES");
        return hashAllResources;
    }
    
    public UserAuthDetails getUserResources(String userId) throws SQLException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalStateException, IllegalBlockSizeException, BadPaddingException, SecurityAdminException, IOException
    {
        if(BuildConfig.DMODE)
            System.out.println("Get User Resources started ...");
        
        DBConnection objDBConnection = null;
        UserAuthDetails usrAuthObj = new UserAuthDetails();
        SecAdminUserDetails usrObj = new SecAdminUserDetails();
        try
        {
            usrObj = getUserDetails(userId);
        }
        catch(SecurityAdminException e)
        {
            throw new SecurityAdminException("EC","UserNotFoundException",e.toString());
        }
        
        try
        {
            objDBConnection = DBConnectionFactory.getConnection();
            Vector vecUserResources = new Vector();
            ResultSet rsGetUserResources = null;
            PreparedStatement ps = null;
            Hashtable htGetUserResources = new Hashtable();
            
            htGetUserResources.put("USER_NAME", userId);
            
            if(BuildConfig.DMODE)
                System.out.println("Hash Values : " + htGetUserResources);
            
            try
            {
                
                ps = objDBConnection.executeStatement(SQLMaster.GET_USER_RESOURCES_SQL_QUERY, htGetUserResources);
                rsGetUserResources = ps.executeQuery();
                
            }
            catch(SQLException sqle)
            {
                if(BuildConfig.DMODE)
                    sqle.printStackTrace();
                
                logger.error("SQL ERROR");
                throw new SecurityAdminException("EC","GENERAL SQL ERROR",sqle.toString());
            }
            
            while(rsGetUserResources.next())
            {
                vecUserResources.add(new Integer(rsGetUserResources.getInt("RESRC_ID")));
            }
            rsGetUserResources.close();
            ps.close();
            
            usrAuthObj.setUserId(userId);
            usrAuthObj.setVecUserAuth(vecUserResources);
            usrAuthObj.setContactDetails(usrObj.getObj_Contact_Details());
        }
        catch(SQLException e)
        {
            if(BuildConfig.DMODE)
                e.printStackTrace();
            throw new SecurityAdminException("EC","GENERAL SQL ERROR",e.toString());
        }
        finally
        {
            try
            {
                if(objDBConnection != null)
                    if(objDBConnection.getConnection() != null )
                        if(!objDBConnection.getConnection().isClosed())
                        {
                            objDBConnection.closeConnection();
                        }
            }
            catch(SQLException clse)
            {
                
                logger.error("CONNECTION CLOSING EXCEPTION", clse);
                throw new SQLException("CONNECTION CLOSING EXCEPTION" + clse.toString());
            }
        }
        
        if(BuildConfig.DMODE)
            System.out.println("Get User Resources Ended ...");
        return usrAuthObj;
    }
    
    public boolean checkUserAuthentication(String userId, String password) throws SecurityAdminException, SQLException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalStateException, IllegalBlockSizeException, BadPaddingException, IOException
    {
        
        if(BuildConfig.DMODE)
            System.out.println("Check User Authentication started ...");
        boolean result = false;
        
        DBConnection objDBConnection = null;
        try
        {
            objDBConnection = DBConnectionFactory.getConnection();
            ResultSet rsGetUserPassword = null;
            PreparedStatement ps = null;
            
            logger.debug("USER AUTHENTICATION CHECK STARTED");
            
            Hashtable htGetUserPassword = new Hashtable();
            htGetUserPassword.put("USER_NAME", userId);
            
            if(BuildConfig.DMODE)
                System.out.println("Hash Values : " + htGetUserPassword);
            try
            {
                
                ps = objDBConnection.executeStatement(SQLMaster.GET_USER_PASSWORD_SQL_QUERY, htGetUserPassword);
                rsGetUserPassword = ps.executeQuery();
                
            }
            catch(SQLException sqle)
            {
                if(BuildConfig.DMODE)
                    sqle.printStackTrace();
                logger.error("SQL ERROR");
                throw new SecurityAdminException("EC","GENERAL SQL ERROR",sqle.toString());
            }
            Key key = new SecretKeySpec((userId+"99999999").substring(0,8).toLowerCase().getBytes(),"DES");
            if(BuildConfig.DMODE)
                System.out.println("Key value :"+key.hashCode());
            Cipher cipher = Cipher.getInstance("DES");
            if(rsGetUserPassword.next())
            {
                byte[] result1 = new BASE64Decoder().decodeBuffer(rsGetUserPassword.getString("USER_PWD"));
                cipher.init(Cipher.DECRYPT_MODE, key);
                byte[] original = cipher.doFinal(result1);
             /*   if(BuildConfig.DMODE)
                    System.out.println("Decrypted data: " + new String(original));*/
                
                if(password.equals(new String(original)))
                {
                    result = true;
                    if(BuildConfig.DMODE)
                        System.out.println("SUCCESS");
                }
                else
                    result = false;
            }
            else
            {
                if(BuildConfig.DMODE)
                    System.out.println("UserNotFound");
                throw new SecurityAdminException("EC","UserNotFound","");
            }
            rsGetUserPassword.close();
            ps.close();
        }
        catch(SQLException e)
        {
            if(BuildConfig.DMODE)
                e.printStackTrace();
            throw new SecurityAdminException("EC","GENERAL SQL ERROR",e.toString());
        }
        finally
        {
            try
            {
                if(objDBConnection != null)
                    if(objDBConnection.getConnection() != null)
                        if(!objDBConnection.getConnection().isClosed())
                            objDBConnection.closeConnection();
            }
            catch(SQLException clse)
            {
                
                logger.error("CONNECTION CLOSING EXCEPTION", clse);
                throw new SQLException("CONNECTION CLOSING EXCEPTION" + clse.toString());
            }
        }
        
        if(BuildConfig.DMODE)
            System.out.println("Check User Authentication Finished ...");
        return result;
    }
    
    //These methods are used for additional security(Super User).
    
    public boolean checkMasterUserAuthentication(String userId, String password) throws SecurityAdminException, SQLException
    {
        
        if(BuildConfig.DMODE)
            System.out.println("Check Master User Authentication started ...");
        boolean result = false;
        
        DBConnection objDBConnection = null;
        try
        {
            objDBConnection = DBConnectionFactory.getConnection();
            ResultSet rsUserPassword = null;
            PreparedStatement ps = null;
            
            logger.debug("MASTER USER AUTHENTICATION CHECK STARTED");
            
            Hashtable htUserPassword = new Hashtable();
            htUserPassword.put("USER_NAME", userId);
            htUserPassword.put("USER_PWD",password);
            
            
            if(BuildConfig.DMODE)
                System.out.println("Hash Values : " + htUserPassword);
            try
            {
                
                ps = objDBConnection.executeStatement(SQLMaster.GET_MASTER_USER_PASSWORD_SQL_QUERY, htUserPassword);
                rsUserPassword = ps.executeQuery();
                if(rsUserPassword.next())
                {
                    if(rsUserPassword.getInt(1)>0)
                    result = true;
                }
            }
            catch(SQLException sqle)
            {
                if(BuildConfig.DMODE)
                    sqle.printStackTrace();
                logger.error("SQL ERROR");
                throw new SecurityAdminException("EC","GENERAL SQL ERROR",sqle.toString());
            }
            rsUserPassword.close();
            ps.close();
        }
        catch(SQLException e)
        {
            if(BuildConfig.DMODE)
                e.printStackTrace();
            throw new SecurityAdminException("EC","GENERAL SQL ERROR",e.toString());
        }
        finally
        {
            try
            {
                if(objDBConnection != null)
                    if(objDBConnection.getConnection() != null)
                        if(!objDBConnection.getConnection().isClosed())
                            objDBConnection.closeConnection();
            }
            catch(SQLException clse)
            {
                
                logger.error("CONNECTION CLOSING EXCEPTION", clse);
                throw new SQLException("CONNECTION CLOSING EXCEPTION" + clse.toString());
            }
        }
        
        if(BuildConfig.DMODE)
            System.out.println("Check Master User Authentication Finished ...");
        return result;
    }
    
    public boolean addSystemInfo(SystemInfoDetails objSystemInfoDetails) throws SecurityAdminException, SQLException
    {
        boolean addRESULT = false;

        if(BuildConfig.DMODE)
            System.out.println("Add System Info Starts ...");
        
        logger.info("ADD SYSTEM INFO STARTED");
        
        if(objSystemInfoDetails==null)
        {
            logger.error("SYSTEM INFO OBJECT IS NULL");
            throw new SecurityAdminException("EC","SYSTEM INFO OBJECT IS NULL","");
        }
        DBConnection objDBConnection = null;
        try
        {
            objDBConnection = DBConnectionFactory.getConnection();
            String sql_Query_System_Info_Add = SQLMaster.SYSTEM_INFO_ADD_SQL_QUERY;
            Hashtable ht_SystemInfo_Add = new Hashtable();
            ht_SystemInfo_Add.put("CPU_ID", objSystemInfoDetails.getCpuId());
            ht_SystemInfo_Add.put("MAC_ADDR",objSystemInfoDetails.getMacAddr());
            ht_SystemInfo_Add.put("DRIVE_SNO",objSystemInfoDetails.getDriveSno());
            ht_SystemInfo_Add.put("SYS_USER_NAME",objSystemInfoDetails.getUserName());
            ht_SystemInfo_Add.put("SYS_DET",objSystemInfoDetails.getSystDetails());
            if(objDBConnection.executeUpdateStatement(sql_Query_System_Info_Add,ht_SystemInfo_Add)>=1)
            {
                addRESULT = true;
            }
            
            
            
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            logger.error("SQL ERROR", ex);
            throw new SecurityAdminException("EC","GENERAL SQL ERROR",ex.toString());
        }
        
           
        finally
        {
            try
            {
                if(objDBConnection != null)
                    if(objDBConnection.getConnection() != null )
                        if(!objDBConnection.getConnection().isClosed())
                        {
                            objDBConnection.closeConnection();
                        }
                        
            }
            catch(SQLException clse)
            {
                
                logger.error("CONNECTION CLOSING EXCEPTION", clse);
                throw new SQLException("CONNECTION CLOSING EXCEPTION" + clse.toString());
            }
        }
        if(BuildConfig.DMODE)
            System.out.println("System Info Added ...");
        
        logger.info("SYSTEM INFO ADDED");
       
        return addRESULT;
    
    }
    public boolean checkUserAuthentication(String userId, String password,SystemInfoDetails objSystemInfoDetails) throws SecurityAdminException, SQLException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalStateException, IllegalBlockSizeException, BadPaddingException, IOException
    {
        
        if(BuildConfig.DMODE)
            System.out.println("Check User Authentication started ...");
        boolean result = false;
        boolean systemInfoAvail = false;
        DBConnection objDBConnection = null;
        try
        {
            objDBConnection = DBConnectionFactory.getConnection();
            ResultSet rsGetUserPassword = null;
            ResultSet rsSystemInfo = null;
            PreparedStatement ps = null;
            
            logger.debug("USER AUTHENTICATION CHECK STARTED");
            
            Hashtable htGetUserPassword = new Hashtable();
            Hashtable htSystemInfo = new Hashtable();
            
            htSystemInfo.put("CPU_ID", objSystemInfoDetails.getCpuId());
            htSystemInfo.put("MAC_ADDR",objSystemInfoDetails.getMacAddr());
            htSystemInfo.put("DRIVE_SNO",objSystemInfoDetails.getDriveSno());
            ps = objDBConnection.executeStatement(SQLMaster.SYSTEM_INFO_CHECK_SQL_QUERY,htSystemInfo);
            rsSystemInfo = ps.executeQuery();
            if(rsSystemInfo.next())
            {
                if(rsSystemInfo.getInt(1)>=1)
                    systemInfoAvail = true;
            }
            rsSystemInfo.close();
            ps.close();
            
            if(systemInfoAvail)
            {
                htGetUserPassword.put("USER_NAME", userId);
                
                if(BuildConfig.DMODE)
                    System.out.println("Hash Values : " + htGetUserPassword);
                ps = objDBConnection.executeStatement(SQLMaster.GET_USER_PASSWORD_SQL_QUERY, htGetUserPassword);
                rsGetUserPassword = ps.executeQuery();
                
                Key key = new SecretKeySpec((userId+"99999999").substring(0,8).toLowerCase().getBytes(),"DES");
                if(BuildConfig.DMODE)
                    System.out.println("Key value :"+key.hashCode());
                Cipher cipher = Cipher.getInstance("DES");
                if(rsGetUserPassword.next())
                {
                    byte[] result1 = new BASE64Decoder().decodeBuffer(rsGetUserPassword.getString("USER_PWD"));
                    cipher.init(Cipher.DECRYPT_MODE, key);
                    byte[] original = cipher.doFinal(result1);
                    /*   if(BuildConfig.DMODE)
                     System.out.println("Decrypted data: " + new String(original));*/
                    
                    if(password.equals(new String(original)))
                    {
                        result = true;
                        if(BuildConfig.DMODE)
                            System.out.println("SUCCESS");
                    }
                    else
                        result = false;
                }
                else
                {
                    if(BuildConfig.DMODE)
                        System.out.println("UserNotFound");
                    throw new SecurityAdminException("EC001","UserNotFound","");
                }
                rsGetUserPassword.close();
                ps.close();
            }
            else
            {
                if(BuildConfig.DMODE)
                    System.out.println("System Info Not Available");
                throw new SecurityAdminException("EC002","System Info Not Available","");
                
            }
            
        }
        catch(SQLException e)
        {
            if(BuildConfig.DMODE)
                e.printStackTrace();
            throw new SecurityAdminException("EC","GENERAL SQL ERROR",e.toString());
        }
        finally
        {
            try
            {
                if(objDBConnection != null)
                    if(objDBConnection.getConnection() != null)
                        if(!objDBConnection.getConnection().isClosed())
                            objDBConnection.closeConnection();
            }
            catch(SQLException clse)
            {
                
                logger.error("CONNECTION CLOSING EXCEPTION", clse);
                throw new SQLException("CONNECTION CLOSING EXCEPTION" + clse.toString());
            }
        }
        
        if(BuildConfig.DMODE)
            System.out.println("Check User Authentication Finished ...");
        return result;
    }
    
    
    public static void main(String args[]) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalStateException, IllegalBlockSizeException, BadPaddingException, SecurityAdminException, SQLException, IOException
    {
        SecurityAdminManager man = new SecurityAdminManager();
        Filter[] filt = new Filter[1];
        filt[0]= new Filter();
        filt[0].setFieldName("USER_NAME");
        filt[0].setFieldValue("sav");
        filt[0].setSpecialFunction("Starts With");
        Vector v = man.getAllUsers(filt);
        for(int i = 0;i<v.size();i++)
        {
            System.out.println("user name:"+v.elementAt(i).toString());
        }
        //SecAdminUserDetails obj = new SecAdminUserDetails();
        /*ContactDetails o = new ContactDetails();
        o.setCntct_Fname("ravi");
        o.setCntct_Lname("kumar");
        obj.setObj_Contact_Details(o);
        obj.setUser_Name("admin");
        obj.setUser_Password("admin123");
        man.addNewUser(obj);
        //man.checkUserAuthentication("bass","bass");
        
        
        /*Key key = new SecretKeySpec("bhaskara".getBytes(),"DES");
         System.out.println("Key value :"+key);
         Cipher cipher = Cipher.getInstance("DES");
         
         byte[] data = "check".getBytes();
         System.out.println("Original data : " + new String(data));
         
         cipher.init(Cipher.ENCRYPT_MODE, key);
         byte[] result1 = cipher.doFinal(data);
         System.out.println("Encrypted data: " + new String(result1));
         
         Key key1 = new SecretKeySpec("bhaskara".getBytes(),"DES");
         cipher.init(Cipher.DECRYPT_MODE, key1);
         byte[] original = cipher.doFinal(result1);
         System.out.println("Decrypted data: " + new String(original));*/
        
        
    }
    
}

/***
 $Log: SecurityAdminManager.java,v $
 Revision 1.63  2005/12/15 07:08:44  kduraisamy
 SCHEDULER TABLES ADDED.

 Revision 1.62  2005/12/13 10:54:11  vkrishnamoorthy
 ERROR CODE ADDED.

 Revision 1.61  2005/12/08 13:03:22  vkrishnamoorthy
 Unwanted Exceptions removed.

 Revision 1.60  2005/12/07 05:55:24  kduraisamy
 Super user security added.

 Revision 1.59  2005/11/26 13:08:19  kduraisamy
 signature added for getAllUsers(Filter filter).

 Revision 1.58  2005/11/26 11:48:19  kduraisamy
 signature added for getAllUsers(Filter filter).

 Revision 1.57  2005/09/15 07:36:10  kduraisamy
 rs.getStatement().close() added in Filters.

 Revision 1.56  2005/09/10 13:18:42  kduraisamy
 order by clause added.

 Revision 1.55  2005/08/16 06:05:10  vkrishnamoorthy
 ROLE_NAME INTRODUCED IN UPDATE GROUP.

 Revision 1.54  2005/07/18 17:15:03  kduraisamy
 cntct_company added.

 Revision 1.53  2005/07/18 15:03:03  kduraisamy
 COMPANY NAME FIELD ADDED IN VALUE OBJECT.

 Revision 1.52  2005/07/18 12:03:18  kduraisamy
 customer load in user add added.

 Revision 1.51  2005/07/12 11:48:00  kduraisamy
 imports organized

 Revision 1.50  2005/07/12 06:58:13  kduraisamy
 connection closing properly handled.

 Revision 1.49  2005/06/29 12:43:58  smurugesan
 if con ! = null added in finally
 
 Revision 1.48  2005/06/16 13:10:10  kduraisamy
 constructor changed.
 
 Revision 1.47  2005/06/16 13:02:33  kduraisamy
 constructor changed.
 
 Revision 1.46  2005/06/16 11:42:33  kduraisamy
 Error code added.
 
 Revision 1.45  2005/05/28 04:46:10  kduraisamy
 delete error corrected.
 
 Revision 1.44  2005/05/28 04:40:00  kduraisamy
 delete error corrected.
 
 Revision 1.43  2005/05/27 15:18:12  kduraisamy
 error in user role delete corrected.
 
 Revision 1.42  2005/05/27 08:54:49  kduraisamy
 con != null checking added.
 
 Revision 1.41  2005/05/27 08:53:43  kduraisamy
 con != null checking added.
 
 Revision 1.40  2005/05/26 05:35:46  kduraisamy
 unused variables removed.
 
 Revision 1.39  2005/05/18 08:42:37  kduraisamy
 commitTransaction() and rollbackTransaction() added.
 
 Revision 1.38  2005/05/18 08:00:33  kduraisamy
 commitTransaction() and rollbackTransaction() added.
 
 Revision 1.37  2005/05/16 18:35:52  kduraisamy
 specific throws addded for mysql.
 
 Revision 1.36  2005/05/16 15:42:43  kduraisamy
 specific throws addded for mysql.
 
 Revision 1.35  2005/05/11 10:13:37  kduraisamy
 if(BuildConfig.DMODE) ADDED.
 
 Revision 1.34  2005/04/26 10:07:41  kduraisamy
 logger GENERAL SQL ERROR is put into Exceptions.
 
 Revision 1.33  2005/04/23 10:22:32  kduraisamy
 BuildConfig.DMODE added.
 
 Revision 1.32  2005/04/22 10:00:33  kduraisamy
 logger class changed.
 
 Revision 1.31  2005/04/19 13:13:34  kduraisamy
 resultset properly closed.
 
 Revision 1.30  2005/04/18 12:28:36  kduraisamy
 executeStatement() return type changed.
 
 Revision 1.29  2005/04/15 11:18:36  kduraisamy
 commitTransaction()  added.
 
 Revision 1.28  2005/04/15 10:13:59  kduraisamy
 commitTransaction()  added.
 
 Revision 1.27  2005/04/15 07:19:54  kduraisamy
 throws SecurityAdmin added.
 
 Revision 1.26  2005/04/15 07:15:00  kduraisamy
 printStackTrace() added.
 
 Revision 1.25  2005/04/13 12:23:56  kduraisamy
 throws added .
 
 Revision 1.23  2005/04/11 12:32:40  kduraisamy
 password encryption added.
 
 Revision 1.22  2005/04/08 09:20:29  kduraisamy
 finally removed in addNewContact() and updateContact().
 
 Revision 1.21  2005/04/07 12:26:28  kduraisamy
 BuildConfig.DMODE ADDED befor SYSTEM.OUT.PRINTLN().
 
 Revision 1.20  2005/04/07 11:52:12  kduraisamy
 BuildConfig.DMODE ADDED befor SYSTEM.OUT.PRINTLN().
 
 Revision 1.19  2005/04/07 09:20:49  kduraisamy
 MODE IS CHANGED TO DMODE.
 
 Revision 1.18  2005/04/07 07:38:57  kduraisamy
 INITIAL COMMIT.
 
 Revision 1.17  2005/04/06 11:41:46  vkrishnamoorthy
 error corrected.Hashtable is sent as parameter for deleting group.
 
 Revision 1.16  2005/04/05 11:23:10  kduraisamy
 contact id set in getContactDetails.
 
 Revision 1.15  2005/04/03 09:25:11  kduraisamy
 CNTCT_FAX ADDED.
 
 Revision 1.14  2005/03/31 08:52:10  kduraisamy
 CNTCT_FAX ADDED.
 
 Revision 1.13  2005/03/31 07:48:43  kduraisamy
 indentation.
 
 Revision 1.12  2005/03/30 09:32:49  vkrishnamoorthy
 errors corrected in getUserDetails().
 
 Revision 1.11  2005/03/30 08:25:46  kduraisamy
 excecuteRownumStatement() called for filters.
 
 Revision 1.10  2005/03/29 12:42:37  kduraisamy
 PASSWORD IS CHANGED TO PWD.
 
 Revision 1.9  2005/03/29 12:26:39  kduraisamy
 GET ALL RESOURCES () ADDED.
 
 Revision 1.8  2005/03/29 09:39:04  kduraisamy
 getDate is changed to getTimestamp
 
 Revision 1.7  2005/03/29 09:28:09  kduraisamy
 UPDATE USER() ADDED.
 
 Revision 1.6  2005/03/29 08:57:44  kduraisamy
 deleteUsers() added.
 
 Revision 1.5  2005/03/29 07:40:39  kduraisamy
 USER VALID INVALID ADDED.
 
 Revision 1.4  2005/03/29 05:55:48  kduraisamy
 USER_ID CHANGED AS USER_NAME
 
 Revision 1.3  2005/03/28 07:35:06  kduraisamy
 getAllUserDetails() filter added.
 
 Revision 1.2  2005/03/23 08:52:02  kduraisamy
 userAuthentication() added.
 
 Revision 1.1  2005/03/23 06:22:06  kduraisamy
 initial commit.
 
 
 */
