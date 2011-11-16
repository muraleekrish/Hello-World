
/*
 * Created on Nov 2, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.businessimplementation.job;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
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
public class JobDetailsManager
{
    DBConnection con=null;
    static Logger logger = Logger.getLogger(JobDetailsManager.class);
    public JobDetailsManager()
    {
        logger.addAppender(LoggerOutput.getAppender());
        logger.setLevel(ProdacsConfig.LOGGER_LEVEL);
    }

    public int addJobGeneralName(String jb_Gnrl_Name) throws JobException, SQLException
    {
        int result = 0;
        int jbGnrlId = 0;
        Vector vec_Obj = new Vector();
        Hashtable ht_Job_Gnrl_Name = new Hashtable();


        logger.info("JOB GENERAL NAME ADD STARTS");
        if(BuildConfig.DMODE)
            System.out.println("ADD JOB GENERAL NAME");
        con = DBConnectionFactory.getConnection();
        ht_Job_Gnrl_Name.put("JB_GNRL_NAME",jb_Gnrl_Name);

        try
        {
            result = con.executeUpdateStatement(SQLMaster.JOB_GNRL_NAME_ADD_SQL_QUERY,ht_Job_Gnrl_Name);
            if(result != 0)
            {
                if(BuildConfig.DMODE)
                {

                    logger.debug("Record Added : " + vec_Obj);
                }


            }
            else
            {
                if(BuildConfig.DMODE)
                {

                    logger.debug("Record not Added : " + vec_Obj);
                }

            }
            if(BuildConfig.DMODE)
                System.out.println("JOB GENERAL NAME" + (result != 0? " " : " NOT ") + "ADDED");
            PreparedStatement ps = con.executeStatement(SQLMaster.JB_GNRL_ID_SELECT_SQL_QUERY);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                jbGnrlId = rs.getInt(1);
            }
            rs.close();
            ps.close();

        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();

                logger.error("SQL EXCEPTION", sqle);
            }
            if(BuildConfig.DBASE==BuildConfig.ORACLE)
            {
                if (sqle.toString().indexOf("UK_GNRLNAME") >= 0)
                {
                    throw new JobException("JMEC001","JB JNRL NAME IS UNIQUE.UNIQUE CONSTRAINT VIOLATED",sqle.toString());
                }
                else
                {
                    throw new JobException("JMEC000","GENERAL SQL ERROR",sqle.toString());
                }
            }
            if(BuildConfig.DBASE==BuildConfig.MYSQL)
            {
                if (sqle.toString().indexOf("Duplicate entry") >= 0)
                {
                    throw new JobException("JMEC001","JB JNRL NAME IS UNIQUE.UNIQUE CONSTRAINT VIOLATED",sqle.toString());
                }
                else
                {
                    throw new JobException("JMEC000","GENERAL SQL ERROR",sqle.toString());
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

        logger.info("JOB GNRL NAME ADD ENDS");

        return jbGnrlId;
    }
    public LinkedHashMap getAllJobGeneralName() throws JobException,SQLException
    {
        DBConnection con = null;
        LinkedHashMap hm_Get_All_JbGnrl_Name = new LinkedHashMap();
        ResultSet rs_Get_All_JbGnrl_Name = null;
        PreparedStatement ps = null;


        logger.info("GET ALL JOB GENERAL NAME STARTS");

        try
        {
            con = DBConnectionFactory.getConnection();
            ps = con.executeStatement(SQLMaster.GET_ALL_JOB_GNRL_NAME);
            rs_Get_All_JbGnrl_Name = ps.executeQuery();
            while(rs_Get_All_JbGnrl_Name.next())
            {
                hm_Get_All_JbGnrl_Name.put(new Integer(rs_Get_All_JbGnrl_Name.getInt("GNRL_ID")),rs_Get_All_JbGnrl_Name.getString("GNRL_NAME"));
            }
            rs_Get_All_JbGnrl_Name.close();
            ps.close();
        }
        catch(SQLException e)
        {

            if(BuildConfig.DMODE)
            {
                e.printStackTrace();
                System.out.println("GENERAL EXCEPTION");
            }

            logger.error("GENERAL SQL ERROR", e);
            throw new JobException("JMEC000","GENERAL SQL ERROR",e.toString());
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

                logger.error("EXCEPTION WHILE CLOSING CONNECTION", ex);
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }
        if(BuildConfig.DMODE)
            System.out.println("GET ALL JOB GENERAL NAME ENDED");

        logger.info("GET ALL JOB GENERAL NAME ENDED");

        return hm_Get_All_JbGnrl_Name;
    }

    public int addJobDetailsPartial(JobDetails objJobDetails) throws JobException, SQLException
    {
        

        int result = 0;
        String st = "";
        int x = 0;
        ResultSet rs_JbId_Get=null;
        Hashtable ht_Job_Add = new Hashtable();
        Hashtable ht_JobOpn_Add = new Hashtable();
        DBNull dbNull = new DBNull();
        Object objDBNull = dbNull;
        DBConnection con = null;
        int job_Id = 0;


        logger.info("JOB ADD STARTS");

        if(BuildConfig.DMODE)
            System.out.println("ADD JOB");

        if(objJobDetails == null || (objJobDetails.getJob_Name().trim().length() == 0 ))
        {

            logger.error("JOB DETAILS OBJ NULL");
            throw new JobException("JMEC002","JOB DETAILS OBJ NULL","");
        }

        con = DBConnectionFactory.getConnection();
        con.startTransaction();
        ht_Job_Add.put("JB_NAME",objJobDetails.getJob_Name());
        ht_Job_Add.put("CST_ID",(x=objJobDetails.getCust_Id())!=0 ? new Integer(x):objDBNull);
        ht_Job_Add.put("GNRL_ID",(x=objJobDetails.getJob_Gnrl_Id())!=0 ? new Integer(x):objDBNull);
        ht_Job_Add.put("JB_DRWG_NO",(st=objJobDetails.getJob_Dwg_No())==null ?"": st);
        ht_Job_Add.put("JB_RVSN_NO",(st=objJobDetails.getJob_Rvsn_No())==null ?"": st);
        ht_Job_Add.put("JB_MATL_TYP",(st=objJobDetails.getJob_Matl_Type())==null ? "": st);
        ht_Job_Add.put("JB_STDHRS",new Float(objJobDetails.getJob_stdHrs()));
        ht_Job_Add.put("JB_INCNTV_FLG",(st=objJobDetails.getJob_Incntv_Flag())==null ? "": st);
        ht_Job_Add.put("JB_CREATED_DATE",(objJobDetails.getJob_Created_Date())!=null ? objJobDetails.getJob_Created_Date():objDBNull);

        try
        {
            result = con.executeUpdateStatement(SQLMaster.JOB_ADD_SQL_QUERY,ht_Job_Add);
            if(BuildConfig.DMODE)
                System.out.println("JOB DETAILS" + (result != 0? " " : " NOT ") + "ADDED");
            if(result > 0)
            {
                PreparedStatement ps_JbId_Get = con.getConnection().prepareStatement(SQLMaster.JOB_ID_SELECT_SQL_QUERY);
                rs_JbId_Get = ps_JbId_Get.executeQuery();
                if(rs_JbId_Get.next())
                {
                    job_Id = rs_JbId_Get.getInt(1);
                }
                rs_JbId_Get.close();
                ps_JbId_Get.close();
    
                //Vector vec_Job_Add = objJobDetails.getVec_OpnGpDetails();
                Vector vec_JobOpn_Add = objJobDetails.getVec_OpnDetails();
                for(int i = 0 ; i<vec_JobOpn_Add.size();i++)
                {
                    OperationDetails objOperationDetails = new OperationDetails();
                    objOperationDetails =(OperationDetails)vec_JobOpn_Add.elementAt(i);
                    ht_JobOpn_Add.put("OPN_GP_ID",new Integer(objOperationDetails.getOpnGpId()));
                    ht_JobOpn_Add.put("JB_ID",new Integer(job_Id));
                    ht_JobOpn_Add.put("JBOPN_NAME",objOperationDetails.getOpnName());
                    ht_JobOpn_Add.put("JBOPN_SNO",new Integer(objOperationDetails.getOpnSerialNo()));
                    ht_JobOpn_Add.put("JBOPN_STDHRS",new Float(objOperationDetails.getOpnStdHrs()));
                    ht_JobOpn_Add.put("JBOPN_INCENTIVE",(objOperationDetails.isOpnIncentive())?"1":"0");
                    result=con.executeUpdateStatement(SQLMaster.JOBOPN_ADD_SQL_QUERY,ht_JobOpn_Add);
                }
                con.commitTransaction();
            }

        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();

                logger.error("SQL EXCEPTION", sqle);
            }
            con.rollBackTransaction();
            if(BuildConfig.DBASE==BuildConfig.ORACLE)
            {
                if(sqle.toString().indexOf("PK_JBID")>=0)
                {
                    throw new JobException("JMEC003","JOB ID IS UNIQUE.UNIQUE CONSTRAINT VIOLATED",sqle.toString());
                }

                else if (sqle.toString().indexOf("FK_JM_CUSTID") >= 0)
                {
                    throw new JobException("JMEC004","PARENT KEY CUSTOMER ID NOT FOUND",sqle.toString());
                }
                else if(sqle.toString().indexOf("FK_JM_GNRLID") >=0)
                {
                    throw new JobException("JMEC005","PARENT KEY GNRL ID NOT FOUND",sqle.toString());
                }
                else if(sqle.toString().indexOf("PK_JBOPNID") >=0)
                {
                    throw new JobException("JMEC006","JOBOPN ID IS UNIQUE.UNIQUE CONSTRAINT VALIDATED",sqle.toString());
                }
                else if(sqle.toString().indexOf("UK_JBOPNNAME") >=0)
                {
                    throw new JobException("JMEC007","JOB OPERATION NAME ALREADY EXISTS",sqle.toString());
                }
                else if (sqle.toString().indexOf("FK_JM_OPNGPID") >= 0)
                {
                    throw new JobException("JMEC008","PARENT KEY OPERATION GROUP ID NOT FOUND",sqle.toString());
                }
                else if (sqle.toString().indexOf(" FK_JM_JBID") >= 0)
                {
                    throw new JobException("JMEC009","PARENT KEY JOB ID NOT FOUND",sqle.toString());
                }
                else if(sqle.toString().indexOf("UK_JB_MSTR") >=0)
                {
                    throw new JobException("JMEC010","JOB ALREADY EXISTS",sqle.toString());
                }
                else
                {
                    throw new JobException("JMEC000","GENERAL SQL ERROR",sqle.toString());
                }
            }
            if(BuildConfig.DBASE==BuildConfig.MYSQL)
            {
                if(sqle.toString().indexOf("Duplicate entry")>=0)
                {
                    throw new JobException("JMEC003","JOB ID OR JOB OPERATION ID OR JOB OPERATION NAME OR JOB NAME UNIQUE.UNIQUE CONSTRAINT VIOLATED",sqle.toString());
                }
                else if (sqle.toString().indexOf("foreign key constraint") >= 0)
                {
                    throw new JobException("JMEC004","PARENT KEY CUSTOMER ID OR GNRL ID OR OPERATION GROUP ID OR JOB ID NOT FOUND",sqle.toString());
                }
                else
                {
                    throw new JobException("JMEC000","GENERAL SQL ERROR",sqle.toString());
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

        logger.info("JOB ADD ENDS");

        return job_Id;
    }
    
    public boolean addJobDetails(JobDetails objJobDetails) throws JobException, SQLException
    {
        int result = 0;
        boolean addRESULT = false;
        String st = "";
        int x = 0;
        ResultSet rs_JbId_Get=null;
        Hashtable ht_Job_Add = new Hashtable();
        Hashtable ht_JobOpn_Add = new Hashtable();
        DBNull dbNull = new DBNull();
        Object objDBNull = dbNull;
        DBConnection con = null;
        int job_Id = 0;


        logger.info("JOB ADD STARTS");

        if(BuildConfig.DMODE)
            System.out.println("ADD JOB");

        if(objJobDetails == null || (objJobDetails.getJob_Name().trim().length() == 0 ))
        {

            logger.error("JOB DETAILS OBJ NULL");
            throw new JobException("JMEC002","JOB DETAILS OBJ NULL","");
        }

        con = DBConnectionFactory.getConnection();
        con.startTransaction();
        ht_Job_Add.put("JB_NAME",objJobDetails.getJob_Name());
        ht_Job_Add.put("CST_ID",(x=objJobDetails.getCust_Id())!=0 ? new Integer(x):objDBNull);
        ht_Job_Add.put("GNRL_ID",(x=objJobDetails.getJob_Gnrl_Id())!=0 ? new Integer(x):objDBNull);
        ht_Job_Add.put("JB_DRWG_NO",(st=objJobDetails.getJob_Dwg_No())==null ?"": st);
        ht_Job_Add.put("JB_RVSN_NO",(st=objJobDetails.getJob_Rvsn_No())==null ?"": st);
        ht_Job_Add.put("JB_MATL_TYP",(st=objJobDetails.getJob_Matl_Type())==null ? "": st);
        ht_Job_Add.put("JB_STDHRS",new Float(objJobDetails.getJob_stdHrs()));
        ht_Job_Add.put("JB_INCNTV_FLG",(st=objJobDetails.getJob_Incntv_Flag())==null ? "": st);
        ht_Job_Add.put("JB_CREATED_DATE",(objJobDetails.getJob_Created_Date())!=null ? objJobDetails.getJob_Created_Date():objDBNull);

        try
        {
            result = con.executeUpdateStatement(SQLMaster.JOB_ADD_SQL_QUERY,ht_Job_Add);
            if(BuildConfig.DMODE)
                System.out.println("JOB DETAILS" + (result != 0? " " : " NOT ") + "ADDED");
            if(result > 0)
            {
                PreparedStatement ps_JbId_Get = con.getConnection().prepareStatement(SQLMaster.JOB_ID_SELECT_SQL_QUERY);
                rs_JbId_Get = ps_JbId_Get.executeQuery();
                if(rs_JbId_Get.next())
                {
                    job_Id = rs_JbId_Get.getInt(1);
                }
                //Vector vec_Job_Add = objJobDetails.getVec_OpnGpDetails();
                Vector vec_JobOpn_Add = objJobDetails.getVec_OpnDetails();
                for(int i = 0 ; i<vec_JobOpn_Add.size();i++)
                {
                    OperationDetails objOperationDetails = new OperationDetails();
                    objOperationDetails =(OperationDetails)vec_JobOpn_Add.elementAt(i);
                    ht_JobOpn_Add.put("OPN_GP_ID",new Integer(objOperationDetails.getOpnGpId()));
                    ht_JobOpn_Add.put("JB_ID",new Integer(job_Id));
                    ht_JobOpn_Add.put("JBOPN_NAME",objOperationDetails.getOpnName());
                    ht_JobOpn_Add.put("JBOPN_SNO",new Integer(objOperationDetails.getOpnSerialNo()));
                    ht_JobOpn_Add.put("JBOPN_STDHRS",new Float(objOperationDetails.getOpnStdHrs()));
                    ht_JobOpn_Add.put("JBOPN_INCENTIVE",(objOperationDetails.isOpnIncentive())?"1":"0");
                    result=con.executeUpdateStatement(SQLMaster.JOBOPN_ADD_SQL_QUERY,ht_JobOpn_Add);
                }
                rs_JbId_Get.close();
                ps_JbId_Get.close();

                addRESULT = true;
                con.commitTransaction();
            }

        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();

                logger.error("SQL EXCEPTION", sqle);
            }
            con.rollBackTransaction();
            if(BuildConfig.DBASE==BuildConfig.ORACLE)
            {
                if(sqle.toString().indexOf("PK_JBID")>=0)
                {
                    throw new JobException("JMEC003","JOB ID IS UNIQUE.UNIQUE CONSTRAINT VIOLATED",sqle.toString());
                }

                else if (sqle.toString().indexOf("FK_JM_CUSTID") >= 0)
                {
                    throw new JobException("JMEC004","PARENT KEY CUSTOMER ID NOT FOUND",sqle.toString());
                }
                else if(sqle.toString().indexOf("FK_JM_GNRLID") >=0)
                {
                    throw new JobException("JMEC005","PARENT KEY GNRL ID NOT FOUND",sqle.toString());
                }
                else if(sqle.toString().indexOf("PK_JBOPNID") >=0)
                {
                    throw new JobException("JMEC006","JOBOPN ID IS UNIQUE.UNIQUE CONSTRAINT VALIDATED",sqle.toString());
                }
                else if(sqle.toString().indexOf("UK_JBOPNNAME") >=0)
                {
                    throw new JobException("JMEC007","JOB OPERATION NAME ALREADY EXISTS",sqle.toString());
                }
                else if (sqle.toString().indexOf("FK_JM_OPNGPID") >= 0)
                {
                    throw new JobException("JMEC008","PARENT KEY OPERATION GROUP ID NOT FOUND",sqle.toString());
                }
                else if (sqle.toString().indexOf(" FK_JM_JBID") >= 0)
                {
                    throw new JobException("JMEC009","PARENT KEY JOB ID NOT FOUND",sqle.toString());
                }
                else if(sqle.toString().indexOf("UK_JB_MSTR") >=0)
                {
                    throw new JobException("JMEC010","JOB ALREADY EXISTS",sqle.toString());
                }
                else
                {
                    throw new JobException("JMEC000","GENERAL SQL ERROR",sqle.toString());
                }
            }
            if(BuildConfig.DBASE==BuildConfig.MYSQL)
            {
                if(sqle.toString().indexOf("Duplicate entry")>=0)
                {
                    throw new JobException("JMEC003","JOB ID OR JOB OPERATION ID OR JOB OPERATION NAME OR JOB NAME UNIQUE.UNIQUE CONSTRAINT VIOLATED",sqle.toString());
                }
                else if (sqle.toString().indexOf("foreign key constraint") >= 0)
                {
                    throw new JobException("JMEC004","PARENT KEY CUSTOMER ID OR GNRL ID OR OPERATION GROUP ID OR JOB ID NOT FOUND",sqle.toString());
                }
                else
                {
                    throw new JobException("JMEC000","GENERAL SQL ERROR",sqle.toString());
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

        logger.info("JOB ADD ENDS");

        return addRESULT;
    }

    public boolean updateJobDetails(JobDetails objJobDetails) throws JobException, SQLException
    {
        int result = 0;
        boolean updateRESULT = false;
        String st = "";
        int x = 0;
        int opn_GpId = 0;
        Hashtable ht_JobDet_Update = new Hashtable();
        Hashtable ht_OpnGpId_Get = new Hashtable();
        Hashtable ht_JobOpn_Update = new Hashtable();
        Hashtable ht_JobOpn_Delete = new Hashtable();
        DBNull dbNull=new DBNull();
        Object objDBNull = dbNull;

        logger.info("JOB DETAILS UPDATE STARTS");

        if(objJobDetails == null)
        {

            logger.error("JOB DETAILS OBJECT NULL");
            throw new JobException("JMEC011","JOB DETAILS OBJECT NULL","");
        }
        if(BuildConfig.DMODE)
            System.out.println("UPDATION OF JOB DETAILS");
        con = DBConnectionFactory.getConnection();
        con.startTransaction();
        ht_JobDet_Update.put("JB_ID",new Integer(objJobDetails.getJob_Id()));
        ht_JobDet_Update.put("JB_NAME",objJobDetails.getJob_Name());
        ht_JobDet_Update.put("CST_ID",(x=objJobDetails.getCust_Id())!=0 ? new Integer(x):objDBNull);
        ht_JobDet_Update.put("GNRL_ID",(x=objJobDetails.getJob_Gnrl_Id())!=0 ? new Integer(x):objDBNull);
        ht_JobDet_Update.put("JB_DRWG_NO",(st=objJobDetails.getJob_Dwg_No())!=null ? st :"");
        ht_JobDet_Update.put("JB_RVSN_NO",(st=objJobDetails.getJob_Rvsn_No())!=null ? st :"");
        ht_JobDet_Update.put("JB_MATL_TYP",(st=objJobDetails.getJob_Matl_Type())!=null ? st :"");
        ht_JobDet_Update.put("JB_STDHRS",new Float(objJobDetails.getJob_stdHrs()));
        ht_JobDet_Update.put("JB_INCNTV_FLG",(st=objJobDetails.getJob_Incntv_Flag())!=null ? st :"");
        ht_JobDet_Update.put("JB_CREATED_DATE",(objJobDetails.getJob_Created_Date())!=null ? objJobDetails.getJob_Created_Date():objDBNull);

        try
        {
            result = con.executeUpdateStatement(SQLMaster.JOB_UPDATE_SQL_QUERY,ht_JobDet_Update);
            if(BuildConfig.DMODE)
                System.out.println("JOB DETAILS" + (result != 0? " " : " NOT ") + "UPDATED");
            if(result > 0)
            {
                ht_JobOpn_Delete.put("JB_ID",new Integer(objJobDetails.getJob_Id()));

                result = con.executeUpdateStatement(SQLMaster.JOB_OPN_DELETE_QUERY,ht_JobOpn_Delete);
                Vector vec_JobOpn_Update = objJobDetails.getVec_OpnDetails();
                System.out.println(vec_JobOpn_Update.size());
                for(int i = 0 ; i < vec_JobOpn_Update.size() ; i++)
                {

                    OperationDetails objOperationDetails = new OperationDetails();
                    objOperationDetails = (OperationDetails)vec_JobOpn_Update.elementAt(i);

                    ht_OpnGpId_Get.put("OPN_GP_CODE",objOperationDetails.getOpnGpCode());

                    PreparedStatement ps = con.executeStatement(SQLMaster.OPNGP_ID_SELECT_SQL_QUERY,ht_OpnGpId_Get);
                    ResultSet rs_OpnGpId_Get = ps.executeQuery();
                    if(rs_OpnGpId_Get.next())
                    {
                        opn_GpId = rs_OpnGpId_Get.getInt(1);
                    }

                    ht_JobOpn_Update.put("OPN_GP_ID",new Integer(opn_GpId));
                    ht_JobOpn_Update.put("JB_ID",new Integer(objJobDetails.getJob_Id()));

                    ht_JobOpn_Update.put("JBOPN_NAME",objOperationDetails.getOpnName());
                    ht_JobOpn_Update.put("JBOPN_SNO",new Integer(objOperationDetails.getOpnSerialNo()));
                    ht_JobOpn_Update.put("JBOPN_STDHRS",new Float(objOperationDetails.getOpnStdHrs()));
                    ht_JobOpn_Update.put("JBOPN_INCENTIVE",(objOperationDetails.isOpnIncentive())?"1":"0");
                    result = con.executeUpdateStatement(SQLMaster.JOBOPN_ADD_SQL_QUERY,ht_JobOpn_Update);
                    ps.close();
                    rs_OpnGpId_Get.close();
                }

                con.commitTransaction();
                updateRESULT=true;

            }
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }

            logger.error("SQL EXCEPTION", sqle);

            con.rollBackTransaction();
            if(BuildConfig.DBASE==BuildConfig.ORACLE)
            {
                if(sqle.toString().indexOf("PK_JBID")>=0)
                {
                    throw new JobException("JMEC003","JOB ID IS UNIQUE.UNIQUE CONSTRAINT VIOLATED",sqle.toString());
                }

                else if (sqle.toString().indexOf("FK_JM_CUSTID") >= 0)
                {
                    throw new JobException("JMEC004","PARENT KEY CUSTOMER ID NOT FOUND",sqle.toString());
                }
                else if(sqle.toString().indexOf("FK_JM_GNRLID") >=0)
                {
                    throw new JobException("JMEC005","PARENT KEY GNRL ID NOT FOUND",sqle.toString());
                }
                else if(sqle.toString().indexOf("PK_JBOPNID") >=0)
                {
                    throw new JobException("JMEC006","JOBOPN ID IS UNIQUE.UNIQUE CONSTRAINT VALIDATED",sqle.toString());
                }
                else if(sqle.toString().indexOf("UK_JBOPNNAME") >=0)
                {
                    throw new JobException("JMEC007","JOB OPERATION NAME ALREADY EXISTS",sqle.toString());
                }
                else if (sqle.toString().indexOf("FK_JM_OPNGPID") >= 0)
                {
                    throw new JobException("JMEC008","PARENT KEY OPERATION GROUP ID NOT FOUND",sqle.toString());
                }
                else if (sqle.toString().indexOf(" FK_JM_JBID") >= 0)
                {
                    throw new JobException("JMEC009","PARENT KEY JOB ID NOT FOUND",sqle.toString());
                }
                else if(sqle.toString().indexOf("UK_JB_MSTR") >=0)
                {
                    throw new JobException("JMEC010","JOB ALREADY EXISTS",sqle.toString());
                }
                else
                {
                    throw new JobException("JMEC000","GENERAL SQL ERROR",sqle.toString());
                }
            }
            if(BuildConfig.DBASE==BuildConfig.MYSQL)
            {
                if(sqle.toString().indexOf("Duplicate entry")>=0)
                {
                    throw new JobException("JMEC003","JOB ID OR JOB OPERATION ID OR JOB OPERATION NAME OR JOB NAME UNIQUE.UNIQUE CONSTRAINT VIOLATED",sqle.toString());
                }
                else if (sqle.toString().indexOf("foreign key constraint") >= 0)
                {
                    throw new JobException("JMEC004","PARENT KEY CUSTOMER ID OR GNRL ID OR OPERATION GROUP ID OR JOB ID NOT FOUND",sqle.toString());
                }
                else
                {
                    throw new JobException("JMEC000","GENERAL SQL ERROR",sqle.toString());
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

        logger.info("JOB DETAILS UPDATE ENDS");


        return updateRESULT;

    }

    public HashMap deleteJobDetails(Vector vec_JbIds) throws JobException, SQLException
    {

        logger.info("JOB DELETE STARTS");

        Hashtable ht_JbDet_Delete = new Hashtable();
        HashMap resultHashMap = new HashMap();

        try
        {

            con = DBConnectionFactory.getConnection();
            con.startTransaction();
            for(int i=0 ; i<vec_JbIds.size() ; i++)
            {
                ht_JbDet_Delete.clear();
                ht_JbDet_Delete.put("JB_ID",vec_JbIds.elementAt(i));
                // Delete records in Table JB_OPN_MSTR
                int records = 0;
                records = con.executeUpdateStatement(SQLMaster.JOB_OPN_DELETE_QUERY,ht_JbDet_Delete);

                if(records!=0)
                {
                    records = con.executeUpdateStatement(SQLMaster.JOB_DETAILS_DELETE_SQL_QUERY,ht_JbDet_Delete);
                    if(records != 0)
                    {
                        con.commitTransaction();
                        resultHashMap.put(vec_JbIds.elementAt(i),new Integer(0));
                        logger.debug("Record deleted : " + vec_JbIds.get(i));
                    }
                    else
                    {
                        con.rollBackTransaction();
                        resultHashMap.put(vec_JbIds.elementAt(i),new Integer(1));
                        logger.debug("Record not deleted : " + vec_JbIds.get(i));
                    }
                }
                else
                {
                    con.rollBackTransaction();
                    resultHashMap.put(vec_JbIds.elementAt(i),new Integer(1));
                    logger.debug("Record not deleted : " + vec_JbIds.get(i));
                }
            }
        }
        catch(SQLException sqle)
        {
            con.rollBackTransaction();
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();

                logger.error("EXCEPTION WHILE DELETING RECORDS");
            }
            if(BuildConfig.DBASE==BuildConfig.ORACLE)
            {
                if(sqle.toString().indexOf("FK_JM_JBID") >= 0)
                {
                    logger.error("CONSTRAINT VIOLATED - IN USE", sqle);
                    throw new JobException("JMEC018","CHILD RECORD JB_ID FOUND.CONSTRAINT VIOLATED",sqle.toString());
                }
                else
                {
                    logger.error("GENERAL EXCEPTION", sqle);
                    throw new JobException("JMEC000","GENERAL SQL ERROR",sqle.toString());
                }
            }
            if(BuildConfig.DBASE==BuildConfig.MYSQL)
            {
                if(sqle.toString().indexOf("foreign key constraint") >= 0)
                {
                    logger.error("CONSTRAINT VIOLATED - IN USE", sqle);
                    throw new JobException("JMEC018","CHILD RECORD JB_ID FOUND.CONSTRAINT VIOLATED",sqle.toString());
                }
                else
                {
                    logger.error("GENERAL EXCEPTION", sqle);
                    throw new JobException("JMEC000","GENERAL SQL ERROR",sqle.toString());
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
			}catch(SQLException sqle)
            {

                logger.error("CONNECTION CLOSING ERROR", sqle);
                throw new SQLException("CONNECTION CLOSING EXCEPTION" + sqle.toString());
            }
        }


        logger.info("JOB DELETE ENDS");


        return resultHashMap;
    }

    public JobDetails getJobDetails(int jobId) throws JobException, SQLException
    {
        DBConnection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        PreparedStatement ps_JbOpnDet = null;
        ResultSet rs_JbOpnDet = null;
        Hashtable ht_JbDet = new Hashtable();
        JobDetails objJobDetails = new JobDetails();

        Vector vec = new Vector();

        logger.info("GET JOB DETAILS STARTS");

        con = DBConnectionFactory.getConnection();
        ht_JbDet.put("JOB_ID",new Integer(jobId));
        try
        {
            ps = con.executeStatement(SQLMaster.GET_JOB_DETAILS_QUERY,ht_JbDet);
            rs = ps.executeQuery();
            if(rs.next())
            {
                objJobDetails.setJob_Id(rs.getInt("JB_ID"));
                objJobDetails.setJob_Created_Date(rs.getTimestamp("JB_CREATED_DATE"));
                objJobDetails.setJob_Name(rs.getString("JB_NAME"));
                objJobDetails.setCust_Id(rs.getInt("CUST_ID"));
                objJobDetails.setCust_Name(rs.getString("CUST_NAME"));
                objJobDetails.setCust_Typ_Id(rs.getInt("CUST_TYP_ID"));
                objJobDetails.setCust_Typ_Name(rs.getString("CUST_TYP_NAME"));
                objJobDetails.setJob_Gnrl_Id(rs.getInt("GNRL_ID"));
                objJobDetails.setGnrl_Name(rs.getString("GNRL_NAME"));
                objJobDetails.setJob_Dwg_No(rs.getString("JB_DRWG_NO"));
                objJobDetails.setJob_Rvsn_No(rs.getString("JB_RVSN_NO"));
                objJobDetails.setJob_Matl_Type(rs.getString("JB_MATL_TYP"));
                objJobDetails.setJob_stdHrs(rs.getFloat("JB_STDHRS"));
                objJobDetails.setJob_Incntv_Flag(rs.getString("JB_INCNTV_FLG"));
                objJobDetails.setJob_DateStamp(rs.getTimestamp("JB_DATESTAMP"));
                objJobDetails.setJob_IsValid(rs.getInt("JB_ISVALID"));

                ps_JbOpnDet = con.executeStatement(SQLMaster.GET_JOBOPN_DETAILS_QUERY,ht_JbDet);
                rs_JbOpnDet = ps_JbOpnDet.executeQuery();
                while(rs_JbOpnDet.next())
                {
                    OperationDetails objOperationDetails = new OperationDetails();
                    objOperationDetails.setOpnGpId(rs_JbOpnDet.getInt("OPN_GP_ID"));
                    objOperationDetails.setOpnGpCode(rs_JbOpnDet.getString("OPN_GP_CODE"));
                    objOperationDetails.setOpnSerialNo(rs_JbOpnDet.getInt("JBOPN_SNO"));
                    objOperationDetails.setOpnName(rs_JbOpnDet.getString("JBOPN_NAME"));
                    objOperationDetails.setOpnStdHrs(rs_JbOpnDet.getFloat("JBOPN_STDHRS"));
                    objOperationDetails.setOpnIncentive(rs_JbOpnDet.getString("JBOPN_INCENTIVE").equals("1"));
                    vec.addElement(objOperationDetails);


                }
                objJobDetails.setVec_OpnDetails(vec);
            }
            else
            {
                throw new JobException("JMEC019","JOB NOT FOUND","");
            }
            rs.close();
            ps.close();
        }
        catch(SQLException e)
        {
            if(BuildConfig.DMODE)
            {
                e.printStackTrace();
            }


            logger.error("GENERAL SQL ERROR");

            throw new JobException("JME000","GENERAL SQL ERROR",e.toString());
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


        logger.info("GET JOB DETAILS ENDS");

        return objJobDetails;
    }

    public HashMap getAllJobDetails(Filter[] filters,String sortBy,boolean ascending,int startIndex,int displayCount) throws SQLException, JobException
    {
        HashMap hm_Result = new HashMap();
        int eIndex = 0;
        ResultSet rs_JbDet_Get = null;
        int tot_Rec_Cnt;

        eIndex = startIndex + displayCount;

        logger.info("JOB FILTER SEARCH STARTS");


        con = DBConnectionFactory.getConnection();

        tot_Rec_Cnt = con.getRowCountWithComplexFilters(filters,SQLMaster.JOB_FILTER_SQL_QUERY);

        String Query = con.buildDynamicQuery(filters,startIndex,eIndex,sortBy,ascending,SQLMaster.JOB_FILTER_SQL_QUERY);


        try
        {
            rs_JbDet_Get = con.executeRownumStatement(Query);

            Vector vec_JbDet_Get = new Vector();

            while(rs_JbDet_Get.next())
            {
                JobDetails objJobDetails = new JobDetails();
                objJobDetails.setCust_Id(rs_JbDet_Get.getInt("CUST_ID"));
                objJobDetails.setCust_Name(rs_JbDet_Get.getString("CUST_NAME"));
                objJobDetails.setJob_Gnrl_Id(rs_JbDet_Get.getInt("GNRL_ID"));
                objJobDetails.setGnrl_Name(rs_JbDet_Get.getString("GNRL_NAME"));
                objJobDetails.setJob_Id(rs_JbDet_Get.getInt("JB_ID"));
                objJobDetails.setJob_Name(rs_JbDet_Get.getString("JB_NAME"));
                objJobDetails.setJob_Dwg_No(rs_JbDet_Get.getString("JB_DRWG_NO"));
                objJobDetails.setJob_Rvsn_No(rs_JbDet_Get.getString("JB_RVSN_NO"));
                objJobDetails.setJob_Matl_Type(rs_JbDet_Get.getString("JB_MATL_TYP"));
                objJobDetails.setJob_DateStamp(rs_JbDet_Get.getTimestamp("JB_DATESTAMP"));
                objJobDetails.setJob_IsValid(rs_JbDet_Get.getInt("JB_ISVALID"));
                vec_JbDet_Get.addElement(objJobDetails);

            }
            hm_Result.put("TotalRecordCount",new Integer(tot_Rec_Cnt));
            hm_Result.put("JobDetails",vec_JbDet_Get);
            rs_JbDet_Get.getStatement().close();
            rs_JbDet_Get.close();
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }
            throw new JobException("JMEC000","GENERAL SQL ERROR",sqle.toString());
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

        logger.info("JOB FILTER SEARCH ENDS");

        return hm_Result;
    }
    public LinkedHashMap getAllJobs() throws JobException,SQLException
    {
        DBConnection con = null;
        LinkedHashMap hm_Get_All_Jb = new LinkedHashMap();
        ResultSet rs_Get_All_Jb = null;


        logger.info("GET ALL JOBS STARTS");

        try
        {
            con = DBConnectionFactory.getConnection();
            Statement stmt = con.getConnection().createStatement();
            try
            {
                rs_Get_All_Jb = stmt.executeQuery(SQLMaster.GET_ALL_JOBS);
                while(rs_Get_All_Jb.next())
                {
                    hm_Get_All_Jb.put(new Integer(rs_Get_All_Jb.getInt("JB_ID")),rs_Get_All_Jb.getString("JB_NAME"));
                }
                rs_Get_All_Jb.close();
            }
            catch(SQLException ex)
            {
                if(BuildConfig.DMODE)
                {
                    ex.printStackTrace();
                }


                logger.error("SQL ERROR", ex);

                throw new JobException("JMEC000","GENERAL SQL ERROR",ex.toString());
            }

        }
        catch(SQLException e)
        {

            if(BuildConfig.DMODE)
            {
                e.printStackTrace();
                System.out.println("GENERAL EXCEPTION");
            }

            logger.error("GENERAL SQL ERROR", e);

            throw new JobException("JMEC000","GENERAL SQL ERROR",e.toString());
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

                logger.error("EXCEPTION WHILE CLOSING CONNECTION", ex);
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }
        if(BuildConfig.DMODE)
            System.out.println("GET ALL JOBS ENDED");


        logger.info("GET ALL JOBS ENDED");

        return hm_Get_All_Jb;
    }
    public Vector getJobByGeneralName(int gnrlId,int custId) throws JobException,SQLException
    {
        DBConnection con = null;

        Vector vecResult = new Vector();
        Hashtable ht_Job_GnrlName = new Hashtable();
        ResultSet rs_Job_GnrlName = null;
        PreparedStatement ps = null;

        logger.info("GET JOB BY GENERAL NAME STARTS");

        con = DBConnectionFactory.getConnection();
        ht_Job_GnrlName.put("GNRL_ID",new Integer(gnrlId));
        ht_Job_GnrlName.put("CUST_ID",new Integer(custId));
        try
        {
            ps = con.executeStatement(SQLMaster.GET_JOB_BY_GENERALNAME,ht_Job_GnrlName);
            rs_Job_GnrlName = ps.executeQuery();
            while(rs_Job_GnrlName.next())
            {
                vecResult.addElement(rs_Job_GnrlName.getString("JB_NAME"));
            }
            rs_Job_GnrlName.close();
            ps.close();

        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }

            logger.error("EXCEPTION WHILE SELECTING JOBS BY GENERAL NAME");

            throw new JobException("JMEC000","GENERAL SQL ERROR",sqle.toString());
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

                logger.error("EXCEPTION WHILE CLOSING CONNECTION", ex);
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }

        logger.info("GET JOB BY GENERAL NAME ENDS");


        return vecResult;

    }

    public LinkedHashMap getGeneralNameByCustomer(int custId) throws JobException,SQLException
    {
        DBConnection con = null;
        LinkedHashMap hm_Result = new LinkedHashMap();
        Hashtable ht_GnrlName_Cus = new Hashtable();
        ResultSet rs_GnrlName_Cus = null;
        PreparedStatement ps = null;

        logger.info("GET GENERAL NAME BY CUSTOMER STARTS");

        con = DBConnectionFactory.getConnection();
        ht_GnrlName_Cus.put("CUST_ID",new Integer(custId));
        try
        {
            ps = con.executeStatement(SQLMaster.GET_GENERALNAME_BY_CUSTOMER,ht_GnrlName_Cus);
            rs_GnrlName_Cus = ps.executeQuery();
            while(rs_GnrlName_Cus.next())
            {
                hm_Result.put(new Integer(rs_GnrlName_Cus.getInt("GNRL_ID")),rs_GnrlName_Cus.getString("GNRL_NAME"));
            }
            rs_GnrlName_Cus.close();
            ps.close();
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }

            logger.error("EXCEPTION WHILE SELECTING GENERAL NAMES BY CUSTOMER");

            throw new JobException("JMEC000","GENERAL SQL ERROR",sqle.toString());
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

                logger.error("EXCEPTION WHILE CLOSING CONNECTION", ex);
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }

        logger.info("GET GENERAL NAME BY CUSTOMER ENDS");


        return hm_Result;
    }
    public Vector getJobDetailsByGeneralNameWithCustomer(int custId,int gnrlId) throws JobException,SQLException
    {
        DBConnection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        Hashtable ht_Jb_GnrlName = new Hashtable();

        Vector vec_Result = new Vector();


        logger.info("GET JOB DETAILS BY GENERAL NAME WITH CUSTOMER STARTS");

        con = DBConnectionFactory.getConnection();
        ht_Jb_GnrlName.put("GNRL_ID",new Integer(gnrlId));
        ht_Jb_GnrlName.put("CUST_ID",new Integer(custId));

        try
        {
            ps = con.executeStatement(SQLMaster.GET_JOB_DETAILS_BY_GENERALNAME_CUSTOMER,ht_Jb_GnrlName);

            rs = ps.executeQuery();
            while(rs.next())
            {
                JobDetails objJobDetails = new JobDetails();
                objJobDetails.setJob_Id(rs.getInt("JB_ID"));
                objJobDetails.setJob_Name(rs.getString("JB_NAME"));
                objJobDetails.setJob_Gnrl_Id(rs.getInt("CUST_ID"));
                objJobDetails.setCust_Id(rs.getInt("GNRL_ID"));
                objJobDetails.setGnrl_Name(rs.getString("GNRL_NAME"));
                objJobDetails.setJob_Dwg_No(rs.getString("JB_DRWG_NO"));
                objJobDetails.setJob_Rvsn_No(rs.getString("JB_RVSN_NO"));
                objJobDetails.setJob_Matl_Type(rs.getString("JB_MATL_TYP"));
                objJobDetails.setJob_Incntv_Flag(rs.getString("JB_INCNTV_FLG"));
                objJobDetails.setJob_Created_Date(rs.getTimestamp("JB_CREATED_DATE"));
                objJobDetails.setGnrl_DateStamp(rs.getTimestamp("JB_DATESTAMP"));
                objJobDetails.setJob_IsValid(rs.getInt("JB_ISVALID"));

                vec_Result.addElement(objJobDetails);
            }
            rs.close();
            ps.close();
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }

            logger.error("EXCEPTION WHILE SELECTING JOB DETAILS BY GENERALNAME");

            throw new JobException("JMEC000","GENERAL SQL ERROR",sqle.toString());
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

                logger.error("EXCEPTION WHILE CLOSING CONNECTION", ex);
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }


        logger.info("GET JOB DETAILS BY GENERAL NAME WITH CUSTOMER ENDS");

        return vec_Result;

    }
    public Vector getDrawingNoByJobName(int gnrlId,int custId,String jbName) throws JobException,SQLException
    {
        Vector vec_Result = new Vector();
        DBConnection con = null;
        Hashtable ht_jbName = new Hashtable();
        ResultSet rs = null;
        PreparedStatement ps = null;


        logger.info("GET DRAWING NUMBER BY JOB NAME STARTS");

        con = DBConnectionFactory.getConnection();
        ht_jbName.put("JB_NAME",jbName);
        ht_jbName.put("GNRL_ID",new Integer(gnrlId));
        ht_jbName.put("CUST_ID",new Integer(custId));
        try
        {
            ps = con.executeStatement(SQLMaster.GET_DRWGNO_BY_JOBNAME,ht_jbName);
            rs = ps.executeQuery();
            while(rs.next())
            {
                vec_Result.addElement(rs.getString("JB_DRWG_NO"));
            }
            rs.close();
            ps.close();
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }

            logger.error("EXCEPTION WHILE SELECTING JOB DRAWING NUMBER BY JOBNAME");

            throw new JobException("JMEC000","GENERAL SQL ERROR",sqle.toString());
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

                logger.error("EXCEPTION WHILE CLOSING CONNECTION", ex);
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }

        logger.info("GET DRAWING NUMBER BY JOB NAME ENDS");


        return vec_Result;
    }

    public Vector getRvsnNoByJobNameAndDrawingNo(int gnrlId,int custId,String jbName,String drwgNo) throws JobException,SQLException
    {
        Vector vec_Result = new Vector();
        DBConnection con = null;
        Hashtable ht_RvsnNo = new Hashtable();
        ResultSet rs = null;
        PreparedStatement ps = null;


        logger.info("GET REVISION NUMBER BY JOB NAME AND DRAWING NUMBER STARTS");

        con = DBConnectionFactory.getConnection();
        ht_RvsnNo.put("JB_NAME",jbName);
        ht_RvsnNo.put("JB_DRWG_NO",drwgNo);
        ht_RvsnNo.put("GNRL_ID",new Integer(gnrlId));
        ht_RvsnNo.put("CUST_ID",new Integer(custId));
        try
        {
            ps = con.executeStatement(SQLMaster.GET_RVSNNO_BY_JOBNAME_AND_DRWGNO,ht_RvsnNo);
            rs = ps.executeQuery();
            while(rs.next())
            {
                vec_Result.addElement(rs.getString("JB_RVSN_NO"));
            }
            rs.close();
            ps.close();
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }


            logger.error("EXCEPTION WHILE SELECTING REVISION NUMBER BY JOBNAME AND DRAWING NUMBER");

            throw new JobException("JMEC000","GENERAL SQL ERROR",sqle.toString());
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

                logger.error("EXCEPTION WHILE CLOSING CONNECTION", ex);
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }


        logger.info("GET REVISION NUMBER BY JOB NAME AND DRAWING NUMBER ENDS");


        return vec_Result;
    }

    public Vector getMatlTypByJobNameDrawingNoAndRvsnNo(int custId,int gnrlId,String jbName,String drwgNo,String rvsnNo) throws JobException,SQLException
    {
        Vector vec_Result = new Vector();
        DBConnection con = null;
        Hashtable ht_MatlTyp = new Hashtable();

        ResultSet rs = null;
        PreparedStatement ps = null;


        logger.info("GET MATERIAL TYPE BY JOB NAME,REVISION NUMBER AND DRAWING NUMBER STARTS");

        con = DBConnectionFactory.getConnection();
        ht_MatlTyp.put("GNRL_ID",new Integer(gnrlId));
        ht_MatlTyp.put("CUST_ID",new Integer(custId));
        ht_MatlTyp.put("JB_NAME",jbName);
        ht_MatlTyp.put("JB_DRWG_NO",drwgNo);
        ht_MatlTyp.put("JB_RVSN_NO",rvsnNo);
        try
        {
            ps = con.executeStatement(SQLMaster.GET_MATLTYP_BY_JOBNAME_DRWGNO_RVSNNO,ht_MatlTyp);
            rs = ps.executeQuery();
            while(rs.next())
            {
                vec_Result.addElement(rs.getString("JB_MATL_TYP"));
            }
            rs.close();
            ps.close();
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }

            logger.error("EXCEPTION WHILE SELECTING MATERIAL TYPE BY JOB NAME,REVISION NUMBER AND DRAWING NUMBER");

            throw new JobException("JMEC000","GENERAL SQL ERROR",sqle.toString());
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

                logger.error("EXCEPTION WHILE CLOSING CONNECTION", ex);
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }


        logger.info("GET MATERIAL TYPE BY JOB NAME,REVISION NUMBER AND DRAWING NUMBER ENDS");


        return vec_Result;
    }

    public int getJobId(int gnrlId,int custId,String jbName,String drwgNo,String rvsnNo,String matlTyp) throws JobException, SQLException
    {
        int jbId = 0;
        DBConnection con = null;
        Hashtable ht_JobId = new Hashtable();

        ResultSet rs = null;
        PreparedStatement ps = null;

        logger.info("GET JOB ID STARTS");

        con = DBConnectionFactory.getConnection();
        ht_JobId.put("JB_MATL_TYP",matlTyp);
        ht_JobId.put("GNRL_ID",new Integer(gnrlId));
        ht_JobId.put("CUST_ID",new Integer(custId));
        ht_JobId.put("JB_NAME",jbName);
        ht_JobId.put("JB_DRWG_NO",drwgNo);
        ht_JobId.put("JB_RVSN_NO",rvsnNo);

        try
        {

            ps = con.executeStatement(SQLMaster.GET_JOBID_SQL_QUERY,ht_JobId);
            rs = ps.executeQuery();
            if(rs.next())
            {
                jbId = rs.getInt("JB_ID");
            }
            rs.close();
            ps.close();
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }

            logger.error("EXCEPTION WHILE SELECTING MATERIAL TYPE BY JOB NAME,REVISION NUMBER AND DRAWING NUMBER");

            throw new JobException("JMEC000","GENERAL SQL ERROR",sqle.toString());
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

                logger.error("EXCEPTION WHILE CLOSING CONNECTION", ex);
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }

        logger.info("GET JOB ID ENDS");

        return jbId;

    }
    public HashMap makeJobValid(Vector jbIds) throws JobException,SQLException
    {
        DBConnection con = null;
        HashMap hm_Result = new HashMap();
        int res = 0;


        logger.info("MAKE JOB VALID STARTS");

        try
        {
            con = DBConnectionFactory.getConnection();
            con.startTransaction();
            for(int i=0;i<jbIds.size();i++)
            {
                Hashtable ht_Jb_Ids = new Hashtable();
                ht_Jb_Ids.put("JOB_ID",(Integer)jbIds.get(i));
                res = con.executeUpdateStatement(SQLMaster.JOB_MAKE_VALID_QUERY,ht_Jb_Ids);
                if(res > 0)
                {
                    hm_Result.put(jbIds.get(i),new Integer(0));

                    logger.info("JOB VALIDATED");
                    con.commitTransaction();
                }
                else
                {
                    hm_Result.put(jbIds.get(i),new Integer(1));

                    logger.info("JOB NOT VALIDATED");
                    con.rollBackTransaction();
                }
            }
        }
        catch(SQLException e)
        {
            if(BuildConfig.DMODE)
            {
                e.printStackTrace();
                System.out.println("GENERAL EXCEPTION");
            }

            logger.error("GENERAL SQL ERROR",e);
            throw new JobException("JMEC000","GENERAL SQL ERROR",e.toString());
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

                logger.error("EXCEPTION WHILE CLOSING CONNECTION");
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }

        logger.info("MAKE JOB VALID ENDS");


        return hm_Result;

    }
    public HashMap makeJobInValid(Vector jbIds) throws SQLException,JobException
    {
        DBConnection con = null;
        HashMap hm_Result = new HashMap();
        int res = 0;

        logger.info("MAKE JOB INVALID STARTS");

        try
        {
            con = DBConnectionFactory.getConnection();
            con.startTransaction();
            for(int i=0;i<jbIds.size();i++)
            {
                Hashtable ht_Jb_Ids = new Hashtable();
                ht_Jb_Ids.put("JOB_ID",(Integer)jbIds.get(i));
                res = con.executeUpdateStatement(SQLMaster.JOB_MAKE_INVALID_QUERY,ht_Jb_Ids);
                if(res > 0)
                {
                    hm_Result.put(jbIds.get(i),new Integer(0));

                    logger.info("JOB INVALIDATED");
                    con.commitTransaction();
                }
                else
                {
                    hm_Result.put(jbIds.get(i),new Integer(1));

                    logger.info("JOB NOT INVALIDATED");
                    con.rollBackTransaction();
                }

            }
        }
        catch(SQLException e)
        {
            if(BuildConfig.DMODE)
            {
                e.printStackTrace();
                System.out.println("GENERAL EXCEPTION");
            }

            logger.error("GENERAL SQL ERROR",e);

            throw new JobException("JMEC000","GENERAL SQL ERROR",e.toString());
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

                logger.error("EXCEPTION WHILE CLOSING CONNECTION");
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }


        logger.info("MAKE JOB INVALID STARTS");

        return hm_Result;
    }

    public boolean addOperationGroupDetails(OperationGroupDetails objOperationGroupDetails) throws JobException, SQLException
    {
        int result=0;
        int opn_Gp_Id=0;
        boolean addRESULT=false;
        PreparedStatement ps_Opn_Grp_Id_Add=null;
        Hashtable ht_Opn_Grp_Add=new Hashtable();
        Hashtable ht_Opn_Grp_Id_Add = new Hashtable();


        logger.info("OPERATION GROUP ADD STARTS");
        if(BuildConfig.DMODE)
            System.out.println("ADD OPERATION GROUP");

        if(objOperationGroupDetails == null || (objOperationGroupDetails.getOperationGroupCode().trim().length() == 0 ))
        {

            logger.error("OPERATION GROUP DETAILS OBJ NULL");
            throw new JobException("JMEC020","OPERATION GROUP DETAILS OBJ NULL","");
        }

        con = DBConnectionFactory.getConnection();
        con.startTransaction();
        HashMap hm_MC_Codes=objOperationGroupDetails.getHm_MachineDetails();
        ht_Opn_Grp_Add.put("OPN_GP_CDE",objOperationGroupDetails.getOperationGroupCode());
        ht_Opn_Grp_Add.put("OPN_GP_MCRELATED",(objOperationGroupDetails.isMachineRelated())?"1":"0");
        try
        {
            result = con.executeUpdateStatement(SQLMaster.OPERATION_GROUP_ADD_SQL_QUERY,ht_Opn_Grp_Add);
            if(BuildConfig.DMODE)
                System.out.println("OPERATION GROUP CODE" + (result != 0? " " : " NOT ") + "ADDED");
            if(objOperationGroupDetails.isMachineRelated())
            {
                ps_Opn_Grp_Id_Add = con.getConnection().prepareStatement(SQLMaster.OPERATION_GROUP_ID_SELECT_SQL_QUERY);
                ResultSet rs_Opn_Grp_Id=ps_Opn_Grp_Id_Add.executeQuery();
                if(rs_Opn_Grp_Id.next())
                {
                    opn_Gp_Id =rs_Opn_Grp_Id.getInt(1);
                }
                Iterator itr = hm_MC_Codes.keySet().iterator();
                while(itr.hasNext())
                {
                    ht_Opn_Grp_Id_Add.put("OPN_GP_ID",new Integer(opn_Gp_Id));
                    ht_Opn_Grp_Id_Add.put("MC_CDE",itr.next().toString());
                    result=con.executeUpdateStatement(SQLMaster.OPERATION_GROUP_ID_MACHINE_ADD_SQL_QUERY,ht_Opn_Grp_Id_Add);
                }
                if(BuildConfig.DMODE)
                    System.out.println("OPERATION GROUP ID AND MACHINE" + (result != 0? " " : " NOT ") + "ADDED");
                con.commitTransaction();
                addRESULT = true;
                rs_Opn_Grp_Id.close();
                ps_Opn_Grp_Id_Add.close();
            }
            else
            {
            	con.commitTransaction();
                addRESULT = true;
            }

        }
        catch(SQLException sqle)
        {
            con.rollBackTransaction();
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();

            }

            logger.error("SQL EXCEPTION", sqle);

            if(BuildConfig.DBASE==BuildConfig.ORACLE)
            {
                if (sqle.toString().indexOf("PK_OPNGPID") >= 0)
                {
                    throw new JobException("JMEC021","OPERATION GROUP ID IS UNIQUE.UNIQUE CONSTRAINT VIOLATED",sqle.toString());
                }
                else if (sqle.toString().indexOf("UK_OPNGPCODE") >= 0)
                {
                    throw new JobException("JMEC022","OPERATION GROUP CODE ALREADY EXISTS",sqle.toString());
                }
                else if (sqle.toString().indexOf("FK_JM_OPNGPID2") >= 0)
                {
                    throw new JobException("JMEC023","PARENT KEY OPERATION GROUP ID NOT FOUND",sqle.toString());
                }
                else if (sqle.toString().indexOf("FK_JM_MCCDE") >= 0)
                {
                    throw new JobException("JMEC024","PARENT KEY MACHINE CODE NOT FOUND",sqle.toString());
                }
                else
                {
                    throw new JobException("JMEC000","GENERAL SQL ERROR",sqle.toString());
                }
            }
            if(BuildConfig.DBASE==BuildConfig.MYSQL)
            {
                if (sqle.toString().indexOf("Duplicate entry") >= 0)
                {
                    throw new JobException("JMEC021","OPERATION GROUP ID OR OPERATION GROUP CODE UNIQUE.UNIQUE CONSTRAINT VIOLATED",sqle.toString());
                }
                if(sqle.toString().indexOf("foreign key constraint") >= 0)
                {
                    throw new JobException("JMEC023","PARENT KEY OPERATION GROUP ID OR MACHINE CODE NOT FOUND",sqle.toString());
                }
                else
                {
                    throw new JobException("JMEC000","GENERAL SQL ERROR",sqle.toString());
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

                logger.error("EXCEPTION WHILE CLOSING CONNECTION");
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }

        logger.info("OPERATION GROUP ADD ENDS");

        return addRESULT;
    }
    public boolean updateOperationGroupDetails(OperationGroupDetails objOperationGroupDetails) throws SQLException, JobException
    {
        int result=0;
        boolean updateRESULT=false;
        int opnGpId = 0;
        Vector vec_Obj = new Vector();
        Hashtable ht_OpnGpMachine_Delete = new Hashtable();
        Hashtable ht_OpnGpMachine_Update =  new Hashtable();
        
        
        logger.info("OPERATION GROUP DETAILS UPDATE STARTS");
        
        
        if(objOperationGroupDetails == null)
        {
            
            logger.error("OPERATION GROUP DETAILS OBJECT NULL");
            throw new JobException("JMEC025","OPERATION GROUP DETAILS OBJECT NULL","");
        }
        if(BuildConfig.DMODE)
            System.out.println("UPDATION OF OPERATION GROUP DETAILS");
        
        con = DBConnectionFactory.getConnection();
        con.startTransaction();
        try
        {
            HashMap hm_MC_Codes = objOperationGroupDetails.getHm_MachineDetails();
            opnGpId = objOperationGroupDetails.getOperationGroupId();
            ht_OpnGpMachine_Delete.put("OPN_GP_ID",new Integer(opnGpId));
            ht_OpnGpMachine_Delete.put("OPN_GP_MCRELATED",(objOperationGroupDetails.isMachineRelated())?"1":"0");
            
            //to update the OPN_GP_MSTR table..
            
            result = con.executeUpdateStatement(SQLMaster.OPERATION_GROUP_UPDATE_SQL_QUERY,ht_OpnGpMachine_Delete);
            if(result >= 1)
            {
                if(BuildConfig.DMODE)
                    System.out.println("OPERATION GROUP DETAILS " + (result != 0? " " : " NOT ") + "UPDATED");
                
                //to delete machines(if exists) corresponding to that group.
                result = con.executeUpdateStatement(SQLMaster.OPERATION_GROUP_MACHINE_DELETE_SQL_QUERY,ht_OpnGpMachine_Delete);
                if(result>=0)
                {
                    
                    logger.debug("Old Machines Deleted : " + vec_Obj);
                    if(objOperationGroupDetails.isMachineRelated())
                    {
                        Iterator itr = hm_MC_Codes.keySet().iterator();
                        while(itr.hasNext())
                        {
                            ht_OpnGpMachine_Update.put("OPN_GP_ID",new Integer(opnGpId));
                            ht_OpnGpMachine_Update.put("MC_CDE",itr.next().toString());
                            result = con.executeUpdateStatement(SQLMaster.OPERATION_GROUP_ID_MACHINE_ADD_SQL_QUERY,ht_OpnGpMachine_Update);
                            if(result>=1)
                            {
                                
                                logger.debug("Record Updated : " + vec_Obj);
                                con.commitTransaction();
                                if(BuildConfig.DMODE)
                                    System.out.println("OPERATION GROUP MACHINE " + (result != 0? " " : " NOT ") + "UPDATED");
                                updateRESULT=true;
                            }
                            else
                            {
                                
                                logger.debug("Record Not Updated : " + vec_Obj);
                                con.rollBackTransaction();
                            }
                        }
                    }
                    else
                    {
                        con.commitTransaction();
                        updateRESULT = true;
                    }
                    
                }
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
                if (sqle.toString().indexOf("PK_OPNGPID") >= 0)
                {
                    throw new JobException("JMEC021","OPERATION GROUP ID IS UNIQUE.UNIQUE CONSTRAINT VIOLATED",sqle.toString());
                }
                else if (sqle.toString().indexOf("UK_OPNGPCODE") >= 0)
                {
                    throw new JobException("JMEC022","OPERATION GROUP CODE ALREADY EXISTS",sqle.toString());
                }
                else if (sqle.toString().indexOf("FK_JM_OPNGPID2") >= 0)
                {
                    throw new JobException("JMEC023","PARENT KEY OPERATION GROUP ID NOT FOUND",sqle.toString());
                }
                else if (sqle.toString().indexOf("FK_JM_MCCDE") >= 0)
                {
                    throw new JobException("JMEC024","PARENT KEY MACHINE CODE NOT FOUND",sqle.toString());
                }
                else
                {
                    throw new JobException("JMEC000","GENERAL SQL ERROR",sqle.toString());
                }
            }
            if(BuildConfig.DBASE==BuildConfig.MYSQL)
            {
                if (sqle.toString().indexOf("Duplicate entry") >= 0)
                {
                    throw new JobException("JMEC021","OPERATION GROUP ID OR OPERATION GROUP CODE UNIQUE.UNIQUE CONSTRAINT VIOLATED",sqle.toString());
                }
                if(sqle.toString().indexOf("foreign key constraint") >= 0)
                {
                    throw new JobException("JMEC023","PARENT KEY OPERATION GROUP ID OR MACHINE CODE NOT FOUND",sqle.toString());
                }
                else
                {
                    throw new JobException("JMEC000","GENERAL SQL ERROR",sqle.toString());
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
        
        
        logger.info("OPERATION GROUP DETAILS UPDATE ENDS");
        
        
        return updateRESULT;
    }
    public HashMap deleteOperationGroupDetails(Vector opnGpIds) throws JobException, SQLException
    {
        DBConnection con = null;
        HashMap hm_Result = new HashMap();


        logger.info("OPERATION GROUP DELETION STARTS");

        if(opnGpIds == null)
        {

            logger.error("OPERATION GROUP ID IS MANDATORY");
            throw new JobException("JMEC030","OPERATION GROUP ID IS NULL","");
        }
        int res = 0;

        try
        {
            con = DBConnectionFactory.getConnection();
            con.startTransaction();
            for(int i=0;i<opnGpIds.size();i++)
            {
                Hashtable ht_OpnGpIds = new Hashtable();
                ht_OpnGpIds.put("OPN_GRP_ID",(Integer)opnGpIds.get(i));
                res = con.executeUpdateStatement(SQLMaster.OPERATION_GROUP_MACHINE_DELETE_QUERY,ht_OpnGpIds);
                res = con.executeUpdateStatement(SQLMaster.OPERATION_GROUP_DELETE_QUERY,ht_OpnGpIds);
                if(res > 0)
                {
                    hm_Result.put(opnGpIds.get(i),new Integer(0));
                    con.commitTransaction();

                    logger.info("RECORD DELETED");
                }
                else
                {
                    hm_Result.put(opnGpIds.get(i),new Integer(1));
                    con.rollBackTransaction();

                    logger.info("RECORD NOT DELETED");
                    if(BuildConfig.DMODE)
                        System.out.println("OPERATION GROUP RECORD NOT FOUND");
                }
            }
        }
        catch(SQLException sqle)
        {
            con.rollBackTransaction();
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }

            logger.error("EXCEPTION WHILE DELETING OPERATION GROUP RECORD");
            if(sqle.toString().indexOf("integrity")>=0 || sqle.toString().indexOf("foreign key constraint")>=0)
            {
                throw new JobException("JMEC031","CHILD RECORD FOUND",sqle.toString());
            }
            else
                throw new JobException("JMEC000","GENERAL SQL ERROR",sqle.toString());
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


        logger.info("OPERATION GROUP DELETION ENDS");

        return hm_Result;

    }

    public OperationGroupDetails getOperationGroupDetails(int opnGpId) throws SQLException,JobException
    {
        OperationGroupDetails  objOperationGroupDetails = new OperationGroupDetails();
        DBConnection con = null;
        Hashtable ht_OpnGpDet = new Hashtable();
        HashMap hm_MC_Names = new HashMap();
        ResultSet rs = null;
        ResultSet rs1 = null;
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;

        logger.info("GET OPERATION GROUP DETAILS STARTS");


        con = DBConnectionFactory.getConnection();
        ht_OpnGpDet.put("OPN_GP_ID",new Integer(opnGpId));
        try
        {
            ps = con.executeStatement(SQLMaster.GET_OPERATION_GROUP_DETAILS_QUERY,ht_OpnGpDet);
            rs = ps.executeQuery();
            if(rs.next())
            {
                objOperationGroupDetails.setOperationGroupId(rs.getInt("OPN_GP_ID"));
                objOperationGroupDetails.setOperationGroupCode(rs.getString("OPN_GP_CODE"));
                objOperationGroupDetails.setMachineRelated(rs.getString("OPN_GP_MCRELATED").equals("1"));
                objOperationGroupDetails.setOpnGpDateStamp(rs.getTimestamp("OPN_GP_DATESTAMP"));
                objOperationGroupDetails.setOpnGpIsValid(rs.getInt("OPN_GP_ISVALID"));
                if(objOperationGroupDetails.isMachineRelated())
                {
                    ps1 =  con.executeStatement(SQLMaster.GET_OPERATION_GROUP_MACHINE_DETAILS_QUERY,ht_OpnGpDet);
                    rs1 = ps1.executeQuery();
                    while(rs1.next())
                    {
                        hm_MC_Names.put(rs1.getString("MC_CDE"),rs1.getString("MC_NAME"));
                    }
                    objOperationGroupDetails.setHm_MachineDetails(hm_MC_Names);
                    rs1.close();
                    ps1.close();
                }
            }
            else
            {

                logger.error("OPERATION GROUP CODE NOT FOUND");
                throw new JobException("JME032","OPERATION GROUP CODE NOT FOUND","");
            }
            rs.close();
            ps.close();
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }

            logger.error("GENERAL SQL ERROR", sqle);

            throw new JobException("JME000","GENERAL SQL ERROR",sqle.toString());
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

        logger.info("GET OPERATION GROUP DETAILS ENDS");

        return objOperationGroupDetails;
    }

    public HashMap getAllOperationGroupDetails(Filter[] filters,String sortBy,boolean ascending,int startIndex,int displayCount) throws JobException, SQLException
    {
        DBConnection con = null;
        HashMap hm_Result = new HashMap();
        ResultSet rs_Get_All_OpnGp_Det = null;


        logger.info("GET ALL OPERATION GROUP FILTER STARTS");


        if((filters == null)||( sortBy == null))
        {

            logger.error("FILTER VALUES ARE NULL");

            throw new JobException("JMEC033","FILTER VALUES ARE NULL","");
        }

        try
        {
            con = DBConnectionFactory.getConnection();
            int eIndex = startIndex + displayCount;



            int tot_Rec_Cnt = con.getRowCountWithComplexFilters(filters,SQLMaster.GET_ALL_OPERATION_GROUP_DETAILS);

            String Query = con.buildDynamicQuery(filters,startIndex,eIndex,sortBy,ascending,SQLMaster.GET_ALL_OPERATION_GROUP_DETAILS);
            HashMap hm_GetAllOpnGpDet = new HashMap();
            Vector vec_Get_All_OpnGP_Det = new Vector();
            try
            {
                rs_Get_All_OpnGp_Det = con.executeRownumStatement(Query);
                while(rs_Get_All_OpnGp_Det.next())
                {
                    OperationGroupDetails objOperationGroupDetails = new OperationGroupDetails();
                    objOperationGroupDetails.setOperationGroupId(rs_Get_All_OpnGp_Det.getInt("OPN_GP_ID"));
                    objOperationGroupDetails.setOperationGroupCode(rs_Get_All_OpnGp_Det.getString("OPN_GP_CODE"));
                    objOperationGroupDetails.setHm_MachineDetails(hm_GetAllOpnGpDet);
                    objOperationGroupDetails.setMachineRelated(rs_Get_All_OpnGp_Det.getBoolean("OPN_GP_MCRELATED"));
                    objOperationGroupDetails.setOpnGpDateStamp(rs_Get_All_OpnGp_Det.getTimestamp("OPN_GP_DATESTAMP"));
                    objOperationGroupDetails.setOpnGpIsValid(rs_Get_All_OpnGp_Det.getInt("OPN_GP_ISVALID"));

                    vec_Get_All_OpnGP_Det.addElement(objOperationGroupDetails);
                }
                rs_Get_All_OpnGp_Det.getStatement().close();
                rs_Get_All_OpnGp_Det.close();
                hm_Result.put("TotalRecordCount",new Integer(tot_Rec_Cnt));
                hm_Result.put("OperationGroupDetails",vec_Get_All_OpnGP_Det);
            }
            catch(SQLException e)
            {
                if(BuildConfig.DMODE)
                {
                    e.printStackTrace();
                }

                logger.error("GENERAL SQL ERROR",e);

                throw new JobException("JMEC000","GENERAL SQL ERROR",e.toString());
            }
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }

            logger.error("GENERAL SQL ERROR",sqle);

            throw new JobException("JMEC000","GENERAL SQL ERROR",sqle.toString());
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


        logger.info("GET All OPERATION GROUP FILTER ENDS");

        if(BuildConfig.DMODE)
            System.out.println("GET All OPERATION GROUP FILTER ENDS");
        return hm_Result;

    }
    public LinkedHashMap getAllOperationGroupCodes() throws JobException,SQLException
    {
        DBConnection con = null;
        LinkedHashMap hm_Get_All_OpnGpCode = new LinkedHashMap();
        ResultSet rs_Get_All_OpnGpCode = null;


        logger.info("GET ALL OPERATION GROUP CODE STARTS");

        try
        {
            con = DBConnectionFactory.getConnection();
            Statement stmt = con.getConnection().createStatement();
            try
            {
                rs_Get_All_OpnGpCode = stmt.executeQuery(SQLMaster.GET_ALL_OPERATION_GROUP_CODE);
                while(rs_Get_All_OpnGpCode.next())
                {
                    hm_Get_All_OpnGpCode.put(new Integer(rs_Get_All_OpnGpCode.getInt("OPN_GP_ID")),rs_Get_All_OpnGpCode.getString("OPN_GP_CODE"));
                }
                rs_Get_All_OpnGpCode.close();
            }
            catch(SQLException ex)
            {
                if(BuildConfig.DMODE)
                {
                    ex.printStackTrace();
                }

                logger.error("SQL ERROR", ex);

                throw new JobException("JMEC000","GENERAL SQL ERROR",ex.toString());
            }

        }
        catch(SQLException e)
        {

            if(BuildConfig.DMODE)
            {
                e.printStackTrace();
                System.out.println("GENERAL EXCEPTION");
            }

            logger.error("GENERAL SQL ERROR", e);

            throw new JobException("JMEC000","GENERAL SQL ERROR",e.toString());
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

                logger.error("EXCEPTION WHILE CLOSING CONNECTION", ex);
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }
        if(BuildConfig.DMODE)
            System.out.println("GET ALL OPERATION GROUP CODE ENDED");


        logger.info("GET ALL OPERATION GROUP CODE ENDED");

        return hm_Get_All_OpnGpCode;
    }

    public HashMap makeOperationGroupValid(Vector opnGpIds) throws JobException, SQLException
    {
        DBConnection con = null;
        HashMap hm_Result = new HashMap();


        logger.info("MAKE OPERATION GROUP VALID STARTS");

        if(opnGpIds == null)
        {

            logger.error("OPERATION GROUP ID IS MANDATORY");
            throw new JobException("JMEC034","OPERATION GROUP ID IS NULL","");
        }
        int res = 0;
        try
        {
            con = DBConnectionFactory.getConnection();
            con.startTransaction();

            for(int i=0;i<opnGpIds.size();i++)
            {
                Hashtable ht_OpnGpIds = new Hashtable();
                ht_OpnGpIds.put("OPN_GRP_ID",(Integer)opnGpIds.get(i));
                res = con.executeUpdateStatement(SQLMaster.OPERATION_GROUP_MAKE_VALID_QUERY,ht_OpnGpIds);
                if(res>0)
                {
                    hm_Result.put(opnGpIds.get(i),new Integer(0));

                    logger.info("OPERATION GROUP VALIDATED");
                    con.commitTransaction();
                }
                else
                {
                    hm_Result.put(opnGpIds.get(i),new Integer(1));

                    logger.info("OPERATION GROUP NOT VALIDATED");
                    con.rollBackTransaction();
                }
            }



        }//first try end

        catch(SQLException e)
        {
            if(BuildConfig.DMODE)
            {
                e.printStackTrace();
                System.out.println("GENERAL EXCEPTION");
            }

            logger.error("GENERAL ERROR",e);

            throw new JobException("JMEC000","GENERAL SQL ERROR",e.toString());
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


        logger.info("MAKE OPERATION GROUP VALID ENDS");

        return hm_Result;
    }
    public HashMap makeOperationGroupInValid(Vector opnGpIds) throws JobException, SQLException
    {
        DBConnection con = null;
        HashMap hm_Result = new HashMap();


        logger.info("MAKE OPERATION GROUP INVALID STARTS");

        if(opnGpIds == null)
        {

            logger.error("OPERATION GROUP ID IS MANDATORY");
            throw new JobException("JMEC035","OPERATION GROUP ID IS NULL","");
        }
        int res = 0;
        try
        {
            con = DBConnectionFactory.getConnection();
            con.startTransaction();
            for(int i=0;i<opnGpIds.size();i++)
            {
                Hashtable ht_OpnGpIds = new Hashtable();
                ht_OpnGpIds.put("OPN_GRP_ID",(Integer)opnGpIds.get(i));
                res = con.executeUpdateStatement(SQLMaster.OPERATION_GROUP_MAKE_INVALID_QUERY,ht_OpnGpIds);
                if(res>0)
                {
                    hm_Result.put(opnGpIds.get(i),new Integer(0));

                    logger.info("OPERATION GROUP INVALIDATED");
                    con.commitTransaction();
                }
                else
                {
                    hm_Result.put(opnGpIds.get(i),new Integer(1));

                    logger.info("OPERATION GROUP NOT INVALIDATED");
                    con.rollBackTransaction();
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
            throw new JobException("JMEC000","GENERAL SQL ERROR",e.toString());
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


        logger.info("MAKE OPERATION GROUP INVALID ENDS");

        return hm_Result;
    }





    public static void main(String args[]) throws SQLException, JobException
    {
        /*JobDetails objJobDetails = new JobDetails();
         JobDetailsManager objJobDetMgr = new JobDetailsManager();

         OperationGroupDetails opGrpDetobj = new OperationGroupDetails();
         OperationDetails objOpnDet  = new OperationDetails();
         Vector v = new Vector();
         *///System.out.println(v = objJobDetMgr.getDrawingNoByJobName("12"));

        //Vector vec = new Vector();
        //vec.addElement(new Integer(17));
        //objJobDetMgr.deleteJobDetails(vec);
        /*objJobDetails.setJob_Name("JobName9");
         objJobDetails.setCust_Id(1101);
         objJobDetails.setJob_Gnrl_Id(13022);
         objJobDetails.setJob_Dwg_No("dwg16");
         objJobDetails.setJob_Rvsn_No("rvsn");
         objJobDetails.setJob_Matl_Type("matl16");
         objJobDetails.setJob_Incntv_Flag("1");
         objJobDetails.setJob_Created_Date(new Date("24-NOV-04"));

         Vector v = new Vector();
         objOpnDet.setOpnGpCode("as");
         objOpnDet.setOpnName("opnnamef");
         objOpnDet.setOpnSerialNo(1);
         objOpnDet.setOpnStdHrs(Float.parseFloat("22.10"));

         v.addElement(objOpnDet);*/

        /*OperationGroupDetails opGrpDetobj1 = new OperationGroupDetails();
         opGrpDetobj1.setOperationGroupName("42name");
         opGrpDetobj1.setOperationName("shaping2");
         opGrpDetobj1.setOperationSerialNo(2);
         opGrpDetobj1.setOperationStdHrs(8.5f);
         v.addElement(opGrpDetobj1);

         OperationGroupDetails opGrpDetobj2 = new OperationGroupDetails();
         opGrpDetobj2.setOperationGroupName("43name");
         opGrpDetobj2.setOperationName("shaping3");
         opGrpDetobj2.setOperationSerialNo(3);
         opGrpDetobj2.setOperationStdHrs(9.5f);
         v.addElement(opGrpDetobj2);*/

        /*objJobDetails.setVec_OpnDetails(v);
         //JobDetailsManager objJobDetMgr = new JobDetailsManager();
          System.out.println(objJobDetMgr.addJobDetails(objJobDetails));*/

        /*JobDetailsManager objJobDetMgr = new JobDetailsManager();
         HashMap hm = objJobDetMgr.getAllJobGeneralName();
         HashMap hm1 = new HashMap();
         HashMap hm2 = new HashMap();
         hm1 = objJobDetMgr.getAllJobs();
         hm2 = objJobDetMgr.getAllOperationGroupCodes();
         System.out.println("GET ALL JOB GENERAL NAME "+hm);
         System.out.println("GET ALL JOBs :"+hm1);
         System.out.println("GET ALL OPERATION GROUP CODES "+hm2);
         HashMap hmp = new HashMap();
         Filter filobj[] = new Filter[1];

         filobj[0] = new Filter();
         filobj[0].setFieldName("JB_NAME");
         filobj[0].setFieldValue("13100");
         filobj[0].setSpecialFunction("Starts With");

         //filobj[0].setFieldName("EMP_ISVALID");
          //filobj[0].setFieldValue("1");
           //filobj[0].setSpecialFunction("");
            int startIndex = 1;
            int displayCount = 4;
            boolean ascending = true;
            String sortBy = "CUST_NAME";
            HashMap hm_Result = objJobDetMgr.getAllJobDetails(filobj,sortBy,new Boolean(ascending).booleanValue(),startIndex,displayCount);
            System.out.println("Hash Map Result :"+hm_Result);

            Vector v =  (Vector)hm_Result.get("JobDetails");
            JobDetails obj = new JobDetails();
            for (int i = 0; i < v.size(); i++)
            {
            obj = (JobDetails)v.get(i);
            System.out.println(obj.getCust_Name());
            System.out.println(obj.getGnrl_Name());
            }
            OperationGroupDetails objOpnGrpDetails = new OperationGroupDetails();
            HashMap hm = new HashMap();
            hm.put("ads","dfe");
            hm.put("jh","6gt");
            objOpnGrpDetails.setOperationGroupCode("OpnGrpCd");
            objOpnGrpDetails.setHm_MachineDetails(hm);
            boolean result = objJobDetMgr.addOperationGroupDetails(objOpnGrpDetails);
            System.out.println("Result : "+result);
            Filter filobj[] = new Filter[1];

            filobj[0] = new Filter();
            filobj[0].setFieldName("MC_NAME");
            filobj[0].setFieldValue("1");
            filobj[0].setSpecialFunction("Starts With");

            //filobj[0].setFieldName("EMP_ISVALID");
             //filobj[0].setFieldValue("1");
              //filobj[0].setSpecialFunction("");
               int startIndex = 1;
               int displayCount = 10;
               boolean ascending = true;
               String sortBy = "OPN_GP_ID";
               HashMap hm_Result = objJobDetMgr.getAllOperationGroupDetails(filobj,sortBy,new Boolean(ascending).booleanValue(),startIndex,displayCount);
               System.out.println("Hash Map Result :"+hm_Result);

               Integer i2 = (Integer)hm_Result.get("TotalRecordCount");
               int i1= i2.intValue();
               Vector v =  (Vector)hm_Result.get("OperationGroupDetails");
               OperationGroupDetails obj = new OperationGroupDetails();

               System.out.println("SIZE :"+i1);
               for (int i = 0; i < v.size(); i++)
               {
               obj = (OperationGroupDetails)v.get(i);
               System.out.println(obj.getOperationGroupCode());

               }

               //getGeneralNameByCustomer()//
                HashMap hm = objJobDetMgr.getGeneralNameByCustomer(13040);
                System.out.println("HASHMAP RESULT :"+hm);
                HashMap hm1 = objJobDetMgr.getJobByGeneralName(1101);
                System.out.println("HASHMAP RESULT :"+hm1);
                OperationGroupDetails obj = objJobDetMgr.getOperationGroupDetails(13040);
                System.out.println("operation Group code :"+obj.getOperationGroupCode());
                HashMap hm = new HashMap();
                hm = obj.getHm_MachineDetails();
                Iterator itr =  hm.keySet().iterator();
                while(itr.hasNext())
                {
                String x = (String)itr.next();
                System.out.println("Mcode:"+x);
                System.out.println("Mcname:"+(String)hm.get(x));

                }

                Vector vec = objJobDetMgr.getJobDetailsByJobName("drilling");
                for(int i=0;i<vec.size();i++)
                {
                JobDetails ObjJobDetails = (JobDetails)vec.get(i);
                System.out.println(ObjJobDetails.getJob_Dwg_No());
                }
                Vector vec = objJobDetMgr.getJobDetailsByGeneralNameWithCustomer(13241,13240);

                for(int i=0;i<vec.size();i++)
                {
                JobDetails obj = (JobDetails)vec.get(i);
                System.out.println("DRGWNO :"+obj.getJob_Dwg_No());
                }*/
        /*Vector vec = objJobDetMgr.getDrawingNoByJobName("13020JBNAME");
         for(int i=0;i<vec.size();i++)
         {
         System.out.println("VECTOR VALUE :"+vec.get(i));
         }*/
        /*Vector vecR = objJobDetMgr.getRvsnNoByJobNameAndDrawingNo("13020JBNAME3","DRWGNO4");
         for(int i=0;i<vecR.size();i++)
         {

         System.out.println("VECTOR VALUE :"+vecR.get(i));
         }*/
        /*HashMap hm= objJobDetMgr.getMatlTypByJobNameDrawingNoAndRvsnNo("13020JBNAME3","DRWGNO4","RVSN3");
         System.out.println("HashMap :"+hm);	*/


    }
}
/***
$Log: JobDetailsManager.java,v $
Revision 1.88  2006/01/23 04:57:34  kduraisamy
addJobDetailsPartial() method included.

Revision 1.87  2005/12/15 10:47:45  vkrishnamoorthy
OPN_GP_MCRELATED field added in filter.

Revision 1.86  2005/09/23 09:22:42  vkrishnamoorthy
Start transaction added.

Revision 1.85  2005/09/15 07:36:10  kduraisamy
rs.getStatement().close() added in Filters.

Revision 1.84  2005/09/10 13:18:19  kduraisamy
order by clause added.

Revision 1.83  2005/07/14 12:45:48  kduraisamy
OPN GP MCRELATED FIELD ADDED.SO GET OPN GP DETAILS() MODIFIED.

Revision 1.82  2005/07/14 12:22:43  vkrishnamoorthy
Indentation.

Revision 1.81  2005/07/14 11:21:15  kduraisamy
OPN GP MCRELATED FIELD ADDED.

Revision 1.80  2005/07/12 11:37:01  kduraisamy
imports organized

Revision 1.79  2005/07/06 14:49:35  kduraisamy
con.startTransaction() added.

Revision 1.78  2005/07/05 06:14:47  kduraisamy
opnIncentive added.

Revision 1.77  2005/06/29 12:43:58  smurugesan
if con ! = null added in finally

Revision 1.76  2005/06/24 05:17:47  vkrishnamoorthy
Job StdHrs added.

Revision 1.75  2005/05/18 09:56:13  kduraisamy
indentation.

Revision 1.74  2005/05/18 08:19:59  kduraisamy
commitTransaction() and rollbackTransaction() added.

Revision 1.73  2005/05/16 18:34:40  kduraisamy
specific throws addded for mysql.

Revision 1.72  2005/05/16 15:42:43  kduraisamy
specific throws addded for mysql.

Revision 1.71  2005/05/16 12:19:11  kduraisamy
job delete error corrected...

Revision 1.70  2005/04/19 13:13:34  kduraisamy
resultset properly closed.

Revision 1.69  2005/04/19 06:00:13  kduraisamy
resultSet name corrected.

Revision 1.68  2005/04/18 12:27:42  kduraisamy
executeStatement() return type changed.

Revision 1.67  2005/04/07 11:57:49  kduraisamy
BuildConfig.DMODE ADDED befor SYSTEM.OUT.PRINTLN().

Revision 1.66  2005/04/07 09:20:04  kduraisamy
MODE IS CHANGED TO DMODE.

Revision 1.65  2005/04/07 07:35:38  kduraisamy
if(BuildConfig.DMODE) ADDED. AND LEVEL INCLUDED PROPERLY.

Revision 1.64  2005/03/30 08:22:42  kduraisamy
excecuteRownumStatement() called for filters.

Revision 1.63  2005/03/26 10:08:21  smurugesan
ROWNUM RESET QUERY REMOVED.

Revision 1.62  2005/03/11 07:45:32  kduraisamy
getInt(1) included for getInt(Id).

Revision 1.61  2005/03/10 07:27:36  kduraisamy
CHECKING BEFORE THE FILTER METHODS(buildDynamicQuery(),etc) added.

getDate is changed to getTimestamp().

Revision 1.60  2005/03/08 09:14:20  kduraisamy
hashtable added to excecuteStatement() in updateJobDetails().

Revision 1.59  2005/03/08 09:11:57  kduraisamy
finally inside the for loop removed.

Revision 1.57  2005/03/04 08:15:22  kduraisamy
DBConnection Changes made.

Revision 1.56  2005/02/21 05:18:08  kduraisamy
MATL_TYP IS CHANGED TO JB_MATL_TYP WHILE SETTING TO HASHTABLE.

Revision 1.55  2005/02/19 10:03:25  kduraisamy
for job uniqueness methods are changed.

Revision 1.54  2005/02/18 10:50:04  kduraisamy
signature changed for getJobByGnrlName().

Revision 1.53  2005/02/16 11:46:28  kduraisamy
signature modified for addNewJbGnrlName().

Revision 1.52  2005/02/14 11:10:05  kduraisamy
unwanted cast removed.

Revision 1.51  2005/02/11 06:54:48  kduraisamy
unused variables removed.

Revision 1.50  2005/02/08 09:53:55  kduraisamy
unwanted exception throw removed.

Revision 1.49  2005/02/01 16:10:52  kduraisamy
exception throw added.

Revision 1.48  2005/01/27 14:44:39  kduraisamy
if(BuildConfig.DMODE)
{
}
properly inclued inside the catch block.

Revision 1.47  2005/01/27 06:05:05  kduraisamy
jbId included instead of jobName.

Revision 1.46  2005/01/25 11:22:10  vkrishnamoorthy
unwanted checking removed in updation.

Revision 1.45  2005/01/22 11:38:17  kduraisamy
commitTransaction() added.

Revision 1.44  2004/12/08 04:37:16  sduraisamy
getJobByGeneralName() added

Revision 1.43  2004/12/07 05:07:46  kduraisamy
== "" is changed to .equals("").

Revision 1.42  2004/12/04 10:43:56  kduraisamy
unwanted imports removed

Revision 1.41  2004/12/04 10:36:01  kduraisamy
new String(string) is replaced with string.Error founded by findbugs corrected

Revision 1.39  2004/12/03 05:32:06  sduraisamy
Exception Codes Changed

Revision 1.38  2004/12/02 12:56:55  sduraisamy
JB_ID set in getJobDetails method

Revision 1.37  2004/12/02 05:46:12  sduraisamy
deleteJobDetails() modified .  if( records!=0) removed.

Revision 1.36  2004/12/01 14:40:49  sduraisamy
deleteOperationGroupDetails() modified.

Revision 1.35  2004/11/30 07:47:26  sduraisamy
con.rollbackTransaction() included in addOperationGroupDetails()

Revision 1.34  2004/11/27 11:13:51  sduraisamy
custId and gnrl_id set in getJobDetailsByGeneralNameWithCustomer()

Revision 1.33  2004/11/27 10:37:46  sduraisamy
getJobDetailsByGeneralNameWithCustomer() added

Revision 1.32  2004/11/27 07:02:42  sduraisamy
JB_ID set in GetJobDetailsByJobName() from resultset

Revision 1.31  2004/11/26 10:25:47  kduraisamy
Exception is changed to SQLException

Revision 1.30  2004/11/26 10:06:16  kduraisamy
checking

Revision 1.29  2004/11/26 09:52:15  kduraisamy
detailed exceptions added for addOperationGroupDetails()

Revision 1.28  2004/11/26 08:07:15  sduraisamy
OPN_GP_ISVALID set in OperationGroupDetails get method

Revision 1.26  2004/11/25 10:27:15  sduraisamy
JobException instead of SQLException in addOperationGroupDetails()

Revision 1.25  2004/11/25 10:14:13  sduraisamy
HashMap for Machine Details removed from OperationGroupDetails Filter Query

Revision 1.24  2004/11/25 08:05:11  sduraisamy
OperationGroupDetails instantiated inside while loop in OperationGroupDetails filter Query

Revision 1.23  2004/11/25 07:46:50  sduraisamy
getJobDetailsByJobName() added

Revision 1.22  2004/11/24 16:13:29  sduraisamy
Exception included in GetJobByGeneralName()

Revision 1.21  2004/11/24 09:11:34  sduraisamy
getOperationGroupDetails() Queries separated

Revision 1.20  2004/11/24 08:06:49  sduraisamy
getJobByGeneralName() added

Revision 1.19  2004/11/24 07:47:05  sduraisamy
getGeneralNameByCustomer() added

Revision 1.18  2004/11/23 15:16:52  kduraisamy
job details delete modified

Revision 1.17  2004/11/23 13:55:29  kduraisamy
eIndex value initialized

Revision 1.16  2004/11/23 12:57:51  kduraisamy
Get all jobs(),getallOpnGpCodes(),getAllJbGnrlName() added

Revision 1.14  2004/11/22 14:26:30  sduraisamy
OPN_GP_CODE instead of OPN_GP_NAME in operationGroupDetails filter method

Revision 1.13  2004/11/22 07:01:10  kduraisamy
Delete,Make Valid and Make InValid methods  for Job added

Revision 1.8  2004/11/17 12:58:22  sduraisamy
updateOperationGroupDetails() errors corrected

Revision 1.7  2004/11/17 12:12:01  sduraisamy
getoperation group details() added

Revision 1.6  2004/11/17 11:51:34  kduraisamy
update operation group details added

Revision 1.5  2004/11/16 11:23:29  kduraisamy
addJobDetails modified

Revision 1.4  2004/11/15 07:03:19  kduraisamy
initial commit

Revision 1.3  2004/11/09 04:58:20  kduraisamy
Log added.

***/