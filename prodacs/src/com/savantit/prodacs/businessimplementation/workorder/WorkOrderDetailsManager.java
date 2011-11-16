/*
 * Created on Dec 2, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.businessimplementation.workorder;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.StringTokenizer;
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
import com.savantit.prodacs.infra.util.SortString;

/**
 * @author kduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class WorkOrderDetailsManager
{
    DBConnection con=null;
    DBNull dbNull=new DBNull();
    Object objDBNull = dbNull;
    static Logger logger = Logger.getLogger(WorkOrderDetailsManager.class);

    public WorkOrderDetailsManager()
    {
        logger.addAppender(LoggerOutput.getAppender());
        logger.setLevel(ProdacsConfig.LOGGER_LEVEL);
    }

    public boolean addNewWorkOrder(WorkOrderDetails objWorkOrderDetails) throws WorkOrderException, SQLException
    {
        int result = 0;
        boolean addRESULT = false;
        String st = "";
        Hashtable ht_WorkOrder_Add = new Hashtable();
        Hashtable ht_WOJob_Add = new Hashtable();
        Hashtable ht_WOJobStat_Add = new Hashtable();
        Hashtable ht_WOJobOpn_Add = new Hashtable();
        Hashtable ht_WOJobHstry_Add = new Hashtable();
        Hashtable ht_CustMstr_Update = new Hashtable();
        int workOrder_Id = 0;
        int workOrderJob_Id = 0;
        int workOrderJobStat_Id = 0;



        logger.info("WORKORDER CREATION STARTS");

        if(objWorkOrderDetails == null)
        {

            logger.error("WORK ORDER DETAILS OBJ NULL");
            throw new WorkOrderException("WOEC001","WORK ORDER DETAILS OBJ NULL","");
        }


        con = DBConnectionFactory.getConnection();

        con.startTransaction();

        ht_WorkOrder_Add.put("WO_NO",(st=objWorkOrderDetails.getWorkOrderNo())!= null ? st:"");
        ht_WorkOrder_Add.put("WO_DATE",objWorkOrderDetails.getWoCreatedDate());
        ht_WorkOrder_Add.put("WO_ESTMD_DATE",objWorkOrderDetails.getWoEstmdCompleteDate());
        ht_WorkOrder_Add.put("WO_CNTCT_PERSON",(st=objWorkOrderDetails.getContactPerson())!=null? st :"");
        try
        {


            result = con.executeUpdateStatement(SQLMaster.WORKORDER_ADD_SQL_QUERY,ht_WorkOrder_Add);

            if(BuildConfig.DMODE)
                System.out.println("WORK ORDER HEADER DETAILS" + (result != 0? " " : " NOT ") + "ADDED");

            if(result > 0)
            {

                PreparedStatement ps_WOId_Get = con.executeStatement(SQLMaster.WORKORDER_ID_SELECT_SQL_QUERY);
                ResultSet rs_WOId_Get = ps_WOId_Get.executeQuery();
                if(rs_WOId_Get.next())
                {
                    workOrder_Id = rs_WOId_Get.getInt(1);

                }
                rs_WOId_Get.close();
                ps_WOId_Get.close();
                //Vector vec_Job_Add = objJobDetails.getVec_OpnGpDetails();
                Vector vec_WOJob_Add = objWorkOrderDetails.getWOJobDetails();

                for(int i = 0 ; i<vec_WOJob_Add.size();i++)
                {
                    WOJobDetails objWOJobDetails = new WOJobDetails();
                    objWOJobDetails = (WOJobDetails)vec_WOJob_Add.elementAt(i);

                    //OperationDetails objOperationDetails = new OperationDetails();
                    //objOperationDetails =(OperationDetails)vec_JobOpn_Add.elementAt(i);
                    ht_WOJob_Add.put("WO_ID",new Integer(workOrder_Id));
                    ht_WOJob_Add.put("JB_ID",new Integer(objWOJobDetails.getJobId()));
                    ht_WOJob_Add.put("WOJB_SNO",new Integer(objWOJobDetails.getJobSerialNo()));
                    ht_WOJob_Add.put("WOJB_START_SNO",new Integer(objWOJobDetails.getJobQtyStartSno()));
                    ht_WOJob_Add.put("WOJB_QTY",new Integer(objWOJobDetails.getJobQty()));
                    ht_WOJob_Add.put("WOJB_DCNO",objWOJobDetails.getWoDCNo());
                    ht_WOJob_Add.put("WOJB_DCDATE",objWOJobDetails.getWoDCDate());
                    
                    
                    result = con.executeUpdateStatement(SQLMaster.WORKORDER_JOB_ADD_SQL_QUERY,ht_WOJob_Add);

                    if(BuildConfig.DMODE)
                        System.out.println("WORK ORDER JOB HEADER DETAILS" + (result != 0? " " : " NOT ") + "ADDED");
                    if(result > 0)
                    {
                        PreparedStatement ps_WOJobId_Get = con.executeStatement(SQLMaster.WORKORDERJOB_ID_SELECT_SQL_QUERY);
                        ResultSet rs_WOJobId_Get = ps_WOJobId_Get.executeQuery();
                        if(rs_WOJobId_Get.next())
                        {
                            workOrderJob_Id = rs_WOJobId_Get.getInt(1);
                        }
                        rs_WOJobId_Get.close();
                        ps_WOJobId_Get.close();

                        Vector vec_WOJobStat_Add = objWOJobDetails.getWOJobOpnDetails();
                        ht_WOJobHstry_Add.put("WOJB_ID",new Integer(workOrderJob_Id));
                        ht_WOJobHstry_Add.put("WO_NO",objWorkOrderDetails.getWorkOrderNo());
                        ht_WOJobHstry_Add.put("JB_NAME",objWOJobDetails.getJobName());
                        ht_WOJobHstry_Add.put("CUST_ID",new Integer(objWorkOrderDetails.getCustId()));
                        ht_WOJobHstry_Add.put("JB_DWG_NO",objWOJobDetails.getJobDrwgNo());
                        ht_WOJobHstry_Add.put("JB_RVSN_NO",objWOJobDetails.getJobRvsnNo());
                        ht_WOJobHstry_Add.put("JB_MATL_TYP",objWOJobDetails.getJobMatlType());
                        ht_WOJobHstry_Add.put("WOJB_QTY",new Integer(objWOJobDetails.getJobQty()));
                        ht_WOJobHstry_Add.put("WO_DATE",objWorkOrderDetails.getWoCreatedDate());
                        result = con.executeUpdateStatement(SQLMaster.WORKORDER_JOBHISTRY_ADD_SQL_QUERY,ht_WOJobHstry_Add);
                        if(BuildConfig.DMODE)
                        System.out.println("WORK ORDER JOB HISTRY DETAILS" + (result != 0? " " : " NOT ") + "ADDED");

                        for(int j = 0 ; j<objWOJobDetails.getJobQty();j++)
                        {

                            ht_WOJobStat_Add.put("WOJB_ID",new Integer(workOrderJob_Id));
                            ht_WOJobStat_Add.put("WOJBSTAT_SNO",new Integer(objWOJobDetails.getJobQtyStartSno()+j));
                            result = con.executeUpdateStatement(SQLMaster.WORKORDER_JOBSTAT_ADD_SQL_QUERY,ht_WOJobStat_Add);

                            if(BuildConfig.DMODE)
                                System.out.println("WORK ORDER JOBSTAT DETAILS" + (result != 0? " " : " NOT ") + "ADDED");
                            if(result > 0)
                            {
                                PreparedStatement ps_WOJobStatId_Get = con.executeStatement(SQLMaster.WORKORDERJOBSTAT_ID_SELECT_SQL_QUERY);
                                ResultSet rs_WOJobStatId_Get = ps_WOJobStatId_Get.executeQuery();
                                if(rs_WOJobStatId_Get.next())
                                {
                                    workOrderJobStat_Id = rs_WOJobStatId_Get.getInt(1);
                                }
                                rs_WOJobStatId_Get.close();
                                ps_WOJobStatId_Get.close();
                                for(int k = 0 ; k<vec_WOJobStat_Add.size();k++)
                                {
                                    WOJobOpnDetails objWOJobOpnDetails = new WOJobOpnDetails();
                                    objWOJobOpnDetails = (WOJobOpnDetails)vec_WOJobStat_Add.elementAt(k);
                                    ht_WOJobOpn_Add.put("WOJBSTAT_ID",new Integer(workOrderJobStat_Id));
                                    ht_WOJobOpn_Add.put("OPN_GP_ID",new Integer(objWOJobOpnDetails.getOpnGpId()));
                                    ht_WOJobOpn_Add.put("WOJBOPN_OPN_SNO",new Integer(objWOJobOpnDetails.getOpnSerialNo()));
                                    ht_WOJobOpn_Add.put("WOJBOPN_OPN_NAME",objWOJobOpnDetails.getOpnName());
                                    ht_WOJobOpn_Add.put("WOJBOPN_OPN_STDHRS",new Float(objWOJobOpnDetails.getOpnStdHrs()));
                                    ht_WOJobOpn_Add.put("WOJBOPN_INCENTIVE",objWOJobOpnDetails.isOpnIncentive()?"1":"0");
                                    result = con.executeUpdateStatement(SQLMaster.WORKORDER_JOBOPN_ADD_SQL_QUERY,ht_WOJobOpn_Add);
                                    if(BuildConfig.DMODE)
                                        System.out.println("WORK ORDER JOBOPN DETAILS" + (result != 0? " " : " NOT ") + "ADDED");

                                }


                            }

                        }



                    }

                }


                //to update the Cust_Mstr Table..ie, to store the Last Order Placed Date and Last work order reference in Cust_Mstr.
                ht_CustMstr_Update.put("CUST_ID",new Integer(objWorkOrderDetails.getCustId()));
                ht_CustMstr_Update.put("CUST_LOPD",objWorkOrderDetails.getWoCreatedDate());
                ht_CustMstr_Update.put("CUST_LWOR",objWorkOrderDetails.getWorkOrderNo());

                result = con.executeUpdateStatement(SQLMaster.CUST_MSTR_UPDATE_FROM_WORKORDER,ht_CustMstr_Update);
                if(result>0)
                {
                    if(BuildConfig.DMODE)
                    System.out.println("Customer Master Table Updated from Work order Add");
                    logger.info("Customer Master Table Updated from Work order Add");
                }
                else
                {
                    if(BuildConfig.DMODE)
                    System.out.println("Customer Master Table not Updated from Work order Add");
                    logger.info("Customer Master Table not Updated from Work order Add");
                }


            }
            con.commitTransaction();
            addRESULT = true;

        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
            
            logger.error("SQL EXCEPTION", sqle);

            con.rollBackTransaction();
            if(BuildConfig.DBASE==BuildConfig.ORACLE)
            {
            if(sqle.toString().indexOf("PK_WOID")>=0)
            {
                throw new WorkOrderException("WOEC002","WO ID ALREADY EXISTS",sqle.toString());
            }
            else if(sqle.toString().indexOf("UK_WONO") >=0)
            {
                throw new WorkOrderException("WOEC003","WORK ORDER NO ALREADY EXISTS",sqle.toString());
            }
            else if(sqle.toString().indexOf("UK_WOJBHEADER") >=0)
            {
                throw new WorkOrderException("WOEC004","WORK ORDER JOB ALREADY EXISTS",sqle.toString());
            }
            else if(sqle.toString().indexOf("PK_WOJBID")>=0)
            {
                throw new WorkOrderException("WOEC005","WORK ORDER JOBID ALREADY EXISTS",sqle.toString());
            }
            else if (sqle.toString().indexOf("FK_WOID") >= 0)
            {
                throw new WorkOrderException("WOEC006","PARENT RECORD - WORK ORDER NOT FOUND",sqle.toString());
            }
            else if (sqle.toString().indexOf("FK_JBID") >= 0)
            {
                throw new WorkOrderException("WOEC007","PARENT RECORD - JOB NOT FOUND",sqle.toString());
            }
            else if(sqle.toString().indexOf("PK_WOJBSTATID")>=0)
            {
                throw new WorkOrderException("WOEC008","WORK ORDER JOBSTATID ALREADY EXISTS",sqle.toString());
            }
            else if (sqle.toString().indexOf("FK_WOJBID") >= 0)
            {
                throw new WorkOrderException("WOEC009","PARENT RECORD -  WORK ORDER JOB ID NOT FOUND",sqle.toString());
            }
            else if(sqle.toString().indexOf("PK_WOJBOPNID")>=0)
            {
                throw new WorkOrderException("WOEC010","WORK ORDER JOBOPNID ALREADY EXITS",sqle.toString());
            }
            else if (sqle.toString().indexOf("FK_WOJBSTATID") >= 0)
            {
                throw new WorkOrderException("WOEC011","PARENT RECORD - WORK ORDER WOJBSTATID NOT FOUND",sqle.toString());
            }
            else if(sqle.toString().indexOf("PK_WOJBID1")>=0)
            {
                throw new WorkOrderException("WOEC012","WORK ORDER JOB ID ALREADY EXISTS",sqle.toString());
            }
            else if (sqle.toString().indexOf("FK_CUSTID") >= 0)
            {
                throw new WorkOrderException("WOEC013","PARENT RECORD -  CUSTOMER ID NOT FOUND",sqle.toString());
            }
            else
            {
                throw new WorkOrderException("WOEC000","GENERAL SQL ERROR",sqle.toString());
            }
            }
            if(BuildConfig.DBASE==BuildConfig.MYSQL)
            {
                if(sqle.toString().indexOf("Duplicate entry")>=0)
                {
                    throw new WorkOrderException("WOEC002","RECORD ALREADY EXISTS",sqle.toString());
                }
                else if(sqle.toString().indexOf("foreign key constraint") >=0)
                {
                    throw new WorkOrderException("WOEC003","RECORD IN USE",sqle.toString());
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


        logger.info("WORK ORDER CREATION ENDS");

        return addRESULT;
    }
    public boolean updateWorkOrder(WorkOrderDetails objWorkOrderDetails) throws WorkOrderException, SQLException
    {
        boolean updateRESULT = false;
        int result = 0;
        Hashtable ht_WoJbId_Get = new Hashtable();
        Hashtable ht_WoJbStatId_Get = new Hashtable();
        Hashtable ht_WoJbOpn_Del = new Hashtable();
        Hashtable ht_WoJbStat_Del = new Hashtable();
        Hashtable ht_WoJbHeader_Del = new Hashtable();
        String st = "";
        Hashtable ht_WorkOrder_Add = new Hashtable();
        Hashtable ht_WOJob_Add = new Hashtable();
        Hashtable ht_WOJobStat_Add = new Hashtable();
        Hashtable ht_WOJobOpn_Add = new Hashtable();
        Hashtable ht_WOJobHstry_Add = new Hashtable();
        Hashtable ht_CustMstr_Update = new Hashtable();
        int workOrderJob_Id = 0;
        int workOrderJobStat_Id = 0;

        int jb_statId = 0;


        logger.info("WORKORDER UPDATE STARTS");

        if(objWorkOrderDetails == null)
        {

            logger.error("WORK ORDER DETAILS OBJ NULL");
            throw new WorkOrderException("WOEC014","WORK ORDER DETAILS OBJ NULL","");
        }


        con = DBConnectionFactory.getConnection();
        con.startTransaction();
        ht_WoJbId_Get.put("WO_ID",new Integer(objWorkOrderDetails.getWorkOrderId()));
        try
        {
            PreparedStatement ps_WoJbId_Get = con.executeStatement(SQLMaster.WORKORDER_JOBID_GET_SQL_QUERY,ht_WoJbId_Get);
            ResultSet rs_WoJbId_Get = ps_WoJbId_Get.executeQuery();
            Vector vec_Job_Ids = new Vector();
            while(rs_WoJbId_Get.next())
            {
                vec_Job_Ids.addElement(new Integer(rs_WoJbId_Get.getInt("WOJB_ID")));
            }
            rs_WoJbId_Get.close();
            ps_WoJbId_Get.close();
            Vector vec_JobStat_Ids = new Vector();
            Vector vec_JobStat_TotIds = new Vector();
            ResultSet rs_WoJbStatId_Get = null;
            PreparedStatement ps_WoJbStatId_Get = null;
            for(int i = 0;i<vec_Job_Ids.size();i++)
            {
                ht_WoJbStatId_Get.put("WOJB_ID",(Integer)vec_Job_Ids.elementAt(i));
                ps_WoJbStatId_Get = con.executeStatement(SQLMaster.WORKORDER_JOBSTATID_GET_SQL_QUERY,ht_WoJbStatId_Get);
                rs_WoJbStatId_Get = ps_WoJbStatId_Get.executeQuery();
                while(rs_WoJbStatId_Get.next())
                {
                    vec_JobStat_Ids.addElement(new Integer(rs_WoJbStatId_Get.getInt("WOJBSTAT_ID")));
                }
                vec_JobStat_TotIds.addElement(vec_JobStat_Ids);
                rs_WoJbStatId_Get.close();
                ps_WoJbStatId_Get.close();

            }
            //deletion of work order tables starts
            for(int j = 0;j<vec_JobStat_TotIds.size();j++)
            {
                vec_JobStat_Ids = (Vector)vec_JobStat_TotIds.elementAt(j);
                for(int k = 0;k<vec_JobStat_Ids.size();k++)
                {
                    jb_statId = ((Integer)vec_JobStat_Ids.elementAt(k)).intValue();
                    ht_WoJbOpn_Del.put("WOJBSTAT_ID",new Integer(jb_statId));
                    result = con.executeUpdateStatement(SQLMaster.WORKORDER_JOBOPN_DEL_SQL_QUERY,ht_WoJbOpn_Del);
                    if(BuildConfig.DMODE)
                    {
                        if(result > 0)
                            System.out.println("Opns Deleted");
                        else
                            System.out.println("Opns Not Deleted");
                    }
                }
            }
            for(int l = 0;l<vec_Job_Ids.size();l++)
            {
                ht_WoJbStat_Del.put("WOJB_ID",(Integer)vec_Job_Ids.elementAt(l));
                ht_WoJbHeader_Del.put("WOJB_ID",(Integer)vec_Job_Ids.elementAt(l));
                result = con.executeUpdateStatement(SQLMaster.WORKORDER_JOBSTAT_DEL_SQL_QUERY,ht_WoJbStat_Del);
                if(BuildConfig.DMODE)
                {
                    if(result > 0)
                        System.out.println("Jb Stat Deleted");
                    else
                        System.out.println("Jb Stat Not Deleted");
                }
                result = con.executeUpdateStatement(SQLMaster.WORKORDER_JOBHSTRY_DEL_SQL_QUERY,ht_WoJbStat_Del);
                if(BuildConfig.DMODE)
                {
                    if(result > 0)
                        System.out.println("Jb Hstry Deleted");
                    else
                        System.out.println("Jb Hstry Not Deleted");
                }
                result = con.executeUpdateStatement(SQLMaster.WORKORDER_JOBHEADER_DEL_SQL_QUERY,ht_WoJbHeader_Del);
                if(BuildConfig.DMODE)
                {
                    if(result > 0)
                        System.out.println("Jb Header Deleted");
                    else
                        System.out.println("Jb Header Not Deleted");
                }

            }

            //ht_WoJbHeader_Del.put("WO_ID",new Integer(objWorkOrderDetails.getWorkOrderId()));
            
            //result = con.executeUpdateStatement(SQLMaster.WORKORDER_HEADER_DEL_SQL_QUERY,ht_WoJbHeader_Del);
            //if(BuildConfig.DMODE)
            //{
              //  if(result > 0)
                //    System.out.println("Wo Header Deleted");
                //else
                  //  System.out.println("Wo Header Not Deleted");
            //}
            ht_WorkOrder_Add.put("WO_ID",new Integer(objWorkOrderDetails.getWorkOrderId()));
            ht_WorkOrder_Add.put("WO_NO",(st=objWorkOrderDetails.getWorkOrderNo())!= null ? st:"");
            ht_WorkOrder_Add.put("WO_DATE",objWorkOrderDetails.getWoCreatedDate());
            ht_WorkOrder_Add.put("WO_ESTMD_DATE",objWorkOrderDetails.getWoEstmdCompleteDate());
            ht_WorkOrder_Add.put("WO_CNTCT_PERSON",(st=objWorkOrderDetails.getContactPerson())!=null? st :"");

            result = con.executeUpdateStatement(SQLMaster.WORKORDER_UPDATE_SQL_QUERY,ht_WorkOrder_Add);



            if(BuildConfig.DMODE)
                System.out.println("WORK ORDER HEADER DETAILS" + (result != 0? " " : " NOT ") + "UPDATED");
            if(result > 0)
            {
                /*PreparedStatement ps_WOId_Get = con.executeStatement(SQLMaster.WORKORDER_ID_SELECT_SQL_QUERY);
                ResultSet rs_WOId_Get = ps_WOId_Get.executeQuery();
                if(rs_WOId_Get.next())
                {
                    workOrder_Id = rs_WOId_Get.getInt(1);

                }*/

                //Vector vec_Job_Add = objJobDetails.getVec_OpnGpDetails();
                Vector vec_WOJob_Add = objWorkOrderDetails.getWOJobDetails();

                for(int i = 0 ; i<vec_WOJob_Add.size();i++)
                {
                    WOJobDetails objWOJobDetails = new WOJobDetails();
                    objWOJobDetails = (WOJobDetails)vec_WOJob_Add.elementAt(i);
                    if(!objWOJobDetails.isProdEntered())
                    {
                        //OperationDetails objOperationDetails = new OperationDetails();
                        //objOperationDetails =(OperationDetails)vec_JobOpn_Add.elementAt(i);
                        ht_WOJob_Add.put("WO_ID",new Integer(objWorkOrderDetails.getWorkOrderId()));
                        ht_WOJob_Add.put("JB_ID",new Integer(objWOJobDetails.getJobId()));
                        if(BuildConfig.DMODE)
                            System.out.println("->"+objWOJobDetails.getJobSerialNo());
                        ht_WOJob_Add.put("WOJB_SNO",new Integer(objWOJobDetails.getJobSerialNo()));
                        ht_WOJob_Add.put("WOJB_START_SNO",new Integer(objWOJobDetails.getJobQtyStartSno()));
                        ht_WOJob_Add.put("WOJB_QTY",new Integer(objWOJobDetails.getJobQty()));
                        ht_WOJob_Add.put("WOJB_DCNO",objWOJobDetails.getWoDCNo());
                        ht_WOJob_Add.put("WOJB_DCDATE",objWOJobDetails.getWoDCDate());
                        
                        result = con.executeUpdateStatement(SQLMaster.WORKORDER_JOB_ADD_SQL_QUERY,ht_WOJob_Add);
                        
                        if(BuildConfig.DMODE)
                            System.out.println("WORK ORDER JOB HEADER DETAILS" + (result != 0? " " : " NOT ") + "ADDED");
                        if(result > 0)
                        {
                            PreparedStatement ps_WOJobId_Get = con.executeStatement(SQLMaster.WORKORDERJOB_ID_SELECT_SQL_QUERY);
                            ResultSet rs_WOJobId_Get = ps_WOJobId_Get.executeQuery();
                            if(rs_WOJobId_Get.next())
                            {
                                workOrderJob_Id = rs_WOJobId_Get.getInt(1);
                            }
                            rs_WOJobId_Get.close();
                            ps_WOJobId_Get.close();
                            
                            Vector vec_WOJobStat_Add = objWOJobDetails.getWOJobOpnDetails();
                            ht_WOJobHstry_Add.put("WOJB_ID",new Integer(workOrderJob_Id));
                            ht_WOJobHstry_Add.put("WO_NO",objWorkOrderDetails.getWorkOrderNo());
                            ht_WOJobHstry_Add.put("JB_NAME",objWOJobDetails.getJobName());
                            ht_WOJobHstry_Add.put("CUST_ID",new Integer(objWorkOrderDetails.getCustId()));
                            ht_WOJobHstry_Add.put("JB_DWG_NO",objWOJobDetails.getJobDrwgNo());
                            ht_WOJobHstry_Add.put("JB_RVSN_NO",objWOJobDetails.getJobRvsnNo());
                            ht_WOJobHstry_Add.put("JB_MATL_TYP",objWOJobDetails.getJobMatlType());
                            ht_WOJobHstry_Add.put("WOJB_QTY",new Integer(objWOJobDetails.getJobQty()));
                            ht_WOJobHstry_Add.put("WO_DATE",objWorkOrderDetails.getWoCreatedDate());
                            result = con.executeUpdateStatement(SQLMaster.WORKORDER_JOBHISTRY_ADD_SQL_QUERY,ht_WOJobHstry_Add);
                            if(BuildConfig.DMODE)
                                System.out.println("WORK ORDER JOB HISTRY DETAILS" + (result != 0? " " : " NOT ") + "ADDED");
                            
                            for(int j = 0 ; j<objWOJobDetails.getJobQty();j++)
                            {
                                
                                ht_WOJobStat_Add.put("WOJB_ID",new Integer(workOrderJob_Id));
                                ht_WOJobStat_Add.put("WOJBSTAT_SNO",new Integer(objWOJobDetails.getJobQtyStartSno()+j));
                                result = con.executeUpdateStatement(SQLMaster.WORKORDER_JOBSTAT_ADD_SQL_QUERY,ht_WOJobStat_Add);
                                if(BuildConfig.DMODE)
                                    System.out.println("WORK ORDER JOBSTAT DETAILS" + (result != 0? " " : " NOT ") + "ADDED");
                                if(result > 0)
                                {
                                    PreparedStatement ps_WOJobStatId_Get = con.executeStatement(SQLMaster.WORKORDERJOBSTAT_ID_SELECT_SQL_QUERY);
                                    ResultSet rs_WOJobStatId_Get = ps_WOJobStatId_Get.executeQuery();
                                    if(rs_WOJobStatId_Get.next())
                                    {
                                        workOrderJobStat_Id = rs_WOJobStatId_Get.getInt(1);
                                    }
                                    for(int k = 0 ; k<vec_WOJobStat_Add.size();k++)
                                    {
                                        WOJobOpnDetails objWOJobOpnDetails = new WOJobOpnDetails();
                                        objWOJobOpnDetails = (WOJobOpnDetails)vec_WOJobStat_Add.elementAt(k);
                                        ht_WOJobOpn_Add.put("WOJBSTAT_ID",new Integer(workOrderJobStat_Id));
                                        ht_WOJobOpn_Add.put("OPN_GP_ID",new Integer(objWOJobOpnDetails.getOpnGpId()));
                                        ht_WOJobOpn_Add.put("WOJBOPN_OPN_SNO",new Integer(objWOJobOpnDetails.getOpnSerialNo()));
                                        ht_WOJobOpn_Add.put("WOJBOPN_OPN_NAME",objWOJobOpnDetails.getOpnName());
                                        ht_WOJobOpn_Add.put("WOJBOPN_OPN_STDHRS",new Float(objWOJobOpnDetails.getOpnStdHrs()));
                                        ht_WOJobOpn_Add.put("WOJBOPN_INCENTIVE",objWOJobOpnDetails.isOpnIncentive()?"1":"0");
                                        result = con.executeUpdateStatement(SQLMaster.WORKORDER_JOBOPN_ADD_SQL_QUERY,ht_WOJobOpn_Add);
                                        if(BuildConfig.DMODE)
                                            System.out.println("WORK ORDER JOBOPN DETAILS" + (result != 0? " " : " NOT ") + "ADDED");
                                        
                                    }
                                    rs_WOJobStatId_Get.close();
                                    ps_WOJobStatId_Get.close();
                                    
                                    
                                }
                                
                            }
                                                        
                        }
                    }

                }
                
                //to update the Cust_Mstr Table..ie, to store the Last Order Placed Date and Last work order reference in Cust_Mstr.
                ht_CustMstr_Update.put("CUST_ID",new Integer(objWorkOrderDetails.getCustId()));
                ht_CustMstr_Update.put("CUST_LOPD",objWorkOrderDetails.getWoCreatedDate());
                ht_CustMstr_Update.put("CUST_LWOR",objWorkOrderDetails.getWorkOrderNo());

                result = con.executeUpdateStatement(SQLMaster.CUST_MSTR_UPDATE_FROM_WORKORDER,ht_CustMstr_Update);
                if(result>0)
                {
                    if(BuildConfig.DMODE)
                    System.out.println("Customer Master Table Updated from Work order Add");
                    logger.info("Customer Master Table Updated from Work order Add");
                }
                else
                {
                    if(BuildConfig.DMODE)
                    System.out.println("Customer Master Table not Updated from Work order Add");
                    logger.info("Customer Master Table not Updated from Work order Add");
                }

            }

            rs_WoJbId_Get.close();


            con.commitTransaction();
            updateRESULT = true;


        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
            logger.error("SQL EXCEPTION", sqle);

            con.rollBackTransaction();
            if(BuildConfig.DBASE==BuildConfig.ORACLE)
            {
            if(sqle.toString().indexOf("PK_WOID")>=0)
            {
                throw new WorkOrderException("WOEC002","WO ID ALREADY EXISTS",sqle.toString());
            }
            else if(sqle.toString().indexOf("UK_WONO") >=0)
            {
                throw new WorkOrderException("WOEC003","WORK ORDER NO ALREADY EXISTS",sqle.toString());
            }
            else if(sqle.toString().indexOf("UK_WOJBHEADER") >=0)
            {
                throw new WorkOrderException("WOEC004","WORK ORDER JOB ALREADY EXISTS",sqle.toString());
            }
            else if(sqle.toString().indexOf("PK_WOJBID")>=0)
            {
                throw new WorkOrderException("WOEC005","WORK ORDER JOBID ALREADY EXISTS",sqle.toString());
            }
            else if (sqle.toString().indexOf("FK_WOID") >= 0)
            {
                throw new WorkOrderException("WOEC006","PARENT RECORD - WORK ORDER NOT FOUND",sqle.toString());
            }
            else if (sqle.toString().indexOf("FK_JBID") >= 0)
            {
                throw new WorkOrderException("WOEC007","PARENT RECORD - JOB NOT FOUND",sqle.toString());
            }
            else if(sqle.toString().indexOf("PK_WOJBSTATID")>=0)
            {
                throw new WorkOrderException("WOEC008","WORK ORDER JOBSTATID ALREADY EXISTS",sqle.toString());
            }
            else if (sqle.toString().indexOf("FK_WOJBID") >= 0)
            {
                throw new WorkOrderException("WOEC009","PARENT RECORD -  WORK ORDER JOB ID NOT FOUND",sqle.toString());
            }
            else if(sqle.toString().indexOf("PK_WOJBOPNID")>=0)
            {
                throw new WorkOrderException("WOEC010","WORK ORDER JOBOPNID ALREADY EXITS",sqle.toString());
            }
            else if (sqle.toString().indexOf("FK_WOJBSTATID") >= 0)
            {
                throw new WorkOrderException("WOEC011","PARENT RECORD - WORK ORDER WOJBSTATID NOT FOUND",sqle.toString());
            }
            else if(sqle.toString().indexOf("PK_WOJBID1")>=0)
            {
                throw new WorkOrderException("WOEC012","WORK ORDER JOB ID ALREADY EXISTS",sqle.toString());
            }
            else if (sqle.toString().indexOf("FK_CUSTID") >= 0)
            {
                throw new WorkOrderException("WOEC013","PARENT RECORD -  CUSTOMER ID NOT FOUND",sqle.toString());
            }
            else
            {
                throw new WorkOrderException("WOEC000","GENERAL SQL ERROR",sqle.toString());
            }
            }
            if(BuildConfig.DBASE==BuildConfig.MYSQL)
            {
                if(sqle.toString().indexOf("Duplicate entry")>=0)
                {
                    throw new WorkOrderException("WOEC002","RECORD ALREADY EXISTS",sqle.toString());
                }
                else if(sqle.toString().indexOf("foreign key constraint") >=0)
                {
                    throw new WorkOrderException("WOEC003","RECORD IN USE",sqle.toString());
                }
                else
                {
                    throw new WorkOrderException("WOEC000","GENERAL SQL ERROR",sqle.toString());
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


        logger.info("WORKORDER UPDATE ENDS");

        return updateRESULT;
    }
    public LinkedHashMap getWorkOrderList() throws SQLException, WorkOrderException
	{
    	LinkedHashMap hm_Result = new LinkedHashMap();
    	ResultSet rs_WO_Get = null;
    	
    	
    	logger.info("GET WORK ORDER LIST STARTS");
    	
    	try
		{
    		con = DBConnectionFactory.getConnection();
    		
    		Statement ps_WO_Get = con.getConnection().createStatement();
    		try
			{
    			rs_WO_Get = ps_WO_Get.executeQuery(SQLMaster.GET_WORKORDER_LIST);
    			while(rs_WO_Get.next())
    			{
    				hm_Result.put(new Integer(rs_WO_Get.getInt("WO_ID")),rs_WO_Get.getString("WO_NO"));
    			}
    			rs_WO_Get.close();
    			ps_WO_Get.close();
			}
    		catch(SQLException sqle)
			{
    			sqle.printStackTrace();
    			logger.error("EXCEPTION WHILE SELECTING WORK ORDER LIST");
    			throw new WorkOrderException("WOEC000","GENERAL SQL ERROR",sqle.toString());
			}
		}
    	catch(SQLException e)
		{
    		e.printStackTrace();
    		logger.error("GENERAL SQL ERROR",e);
    		throw new WorkOrderException("WOCEC000","GENERAL SQL ERROR",e.toString());
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
    	
    	
    	logger.info("GET WORK ORDER LIST ENDS");
    	
    	return hm_Result;
    	
	}
    public WorkOrderDetails getWorkOrderDetails(int WOId) throws WorkOrderException, SQLException
    {
        WorkOrderDetails objWorkOrderDetails = new WorkOrderDetails();
        Hashtable ht_WODet_Get = new Hashtable();
        ResultSet rs_WODet_Get = null;
        PreparedStatement ps_WODet_Get = null;
        ResultSet rs_WO_JbDet_Get = null;
        PreparedStatement ps_WO_JbDet_Get = null;
        ResultSet rs_WO_JbOpn_Det_Get = null;
        PreparedStatement ps_WO_JbOpn_Det_Get = null;


        logger.info("GET WORK ORDER DETAILS STARTS");

        con = DBConnectionFactory.getConnection();
        ht_WODet_Get.put("WO_ID",new Integer(WOId));
        try
        {
            ps_WODet_Get = con.executeStatement(SQLMaster.GET_WORKORDER_DETAILS_QUERY,ht_WODet_Get);
            rs_WODet_Get = ps_WODet_Get.executeQuery();
            if(rs_WODet_Get.next())
            {

                objWorkOrderDetails.setWorkOrderId(WOId);
                objWorkOrderDetails.setWorkOrderNo(rs_WODet_Get.getString("WO_NO"));
                objWorkOrderDetails.setWoCreatedDate(rs_WODet_Get.getTimestamp("WO_DATE"));
                objWorkOrderDetails.setCustId(rs_WODet_Get.getInt("CUST_ID"));
                objWorkOrderDetails.setCustName(rs_WODet_Get.getString("CUST_NAME"));
                objWorkOrderDetails.setCustTypeId(rs_WODet_Get.getInt("CUST_TYP_ID"));
                objWorkOrderDetails.setCustTypName(rs_WODet_Get.getString("CUST_TYP_NAME"));
                objWorkOrderDetails.setContactPerson(rs_WODet_Get.getString("WO_CNTCT_PERSON"));
                objWorkOrderDetails.setWorkOrderStatus(rs_WODet_Get.getString("WO_STAT"));
                objWorkOrderDetails.setWoEstmdCompleteDate(rs_WODet_Get.getTimestamp("WO_ESTMD_DATE"));
                objWorkOrderDetails.setWorkOrderIsValid(rs_WODet_Get.getInt("WO_ISVALID"));
                objWorkOrderDetails.setWorkOrderDateStamp(rs_WODet_Get.getTimestamp("WO_DATESTAMP"));
            }
            else
            {
                if(BuildConfig.DMODE)
                {
                    System.out.println("RECORD NOT FOUND");
                }
                throw new WorkOrderException("WOEC027","RECORD NOT FOUND","");
            }
            rs_WODet_Get.close();
            ps_WODet_Get.close();
            ////////////JOB DETAILS //////////
            Vector vec_WO_Jb_Det = new Vector();
            ps_WO_JbDet_Get = con.executeStatement(SQLMaster.GET_WORKORDER_JOB_DETAILS_QUERY,ht_WODet_Get);
            rs_WO_JbDet_Get = ps_WO_JbDet_Get.executeQuery();
            
            while(rs_WO_JbDet_Get.next())
            {
                WOJobDetails objWOJobDetails = new WOJobDetails();
                objWOJobDetails.setGeneralId(rs_WO_JbDet_Get.getInt("GNRL_ID"));
                objWOJobDetails.setGeneralName(rs_WO_JbDet_Get.getString("GNRL_NAME"));
                objWOJobDetails.setJobId(rs_WO_JbDet_Get.getInt("JB_ID"));
                objWOJobDetails.setJobName(rs_WO_JbDet_Get.getString("JB_NAME"));
                objWOJobDetails.setJobDrwgNo(rs_WO_JbDet_Get.getString("JB_DRWG_NO"));
                objWOJobDetails.setJobRvsnNo(rs_WO_JbDet_Get.getString("JB_RVSN_NO"));
                objWOJobDetails.setJobMatlType(rs_WO_JbDet_Get.getString("JB_MATL_TYP"));
                objWOJobDetails.setJobStatus(rs_WO_JbDet_Get.getString("WOJB_STAT"));
                objWOJobDetails.setJobQty(rs_WO_JbDet_Get.getInt("WOJB_QTY"));
                objWOJobDetails.setWoDCNo(rs_WO_JbDet_Get.getString("WOJB_DCNO"));
                objWOJobDetails.setWoDCDate(rs_WO_JbDet_Get.getTimestamp("WOJB_DCDATE"));
                objWOJobDetails.setWoJbStatus(rs_WO_JbDet_Get.getString("WOJB_STAT"));
                objWOJobDetails.setProdEntered(rs_WO_JbDet_Get.getString("WOJB_ISPROD_ENTERED").equalsIgnoreCase("1"));
                objWOJobDetails.setJobQtyStartSno(rs_WO_JbDet_Get.getInt("WOJB_START_SNO"));
            
                //////////////// JOB OPERATION DETAILS ///////////////
                Vector vec_JbOpnDet = new Vector();
                Hashtable ht_JbOpn_Det = new Hashtable();
                ht_JbOpn_Det.put("WO_ID",new Integer(WOId));
                ht_JbOpn_Det.put("JB_ID",new Integer(objWOJobDetails.getJobId()));
                ps_WO_JbOpn_Det_Get = con.executeStatement(SQLMaster.GET_WORKORDER_JOB_OPERATION_DETAILS_QUERY,ht_JbOpn_Det);
                rs_WO_JbOpn_Det_Get = ps_WO_JbOpn_Det_Get.executeQuery();

                while (rs_WO_JbOpn_Det_Get.next())
                {
                    WOJobOpnDetails objWOJobOpnDetails = new WOJobOpnDetails();
                    objWOJobOpnDetails.setOpnGpId(rs_WO_JbOpn_Det_Get.getInt("OPN_GP_ID"));
                    objWOJobOpnDetails.setOpnGpCode(rs_WO_JbOpn_Det_Get.getString("OPN_GP_CODE"));
                    objWOJobOpnDetails.setOpnName(rs_WO_JbOpn_Det_Get.getString("WOJBOPN_OPN_NAME"));
                    objWOJobOpnDetails.setOpnSerialNo(rs_WO_JbOpn_Det_Get.getInt("WOJBOPN_OPN_SNO"));
                    objWOJobOpnDetails.setOpnStdHrs(rs_WO_JbOpn_Det_Get.getFloat("WOJBOPN_OPN_STDHRS"));
                    objWOJobOpnDetails.setOpnIncentive(rs_WO_JbOpn_Det_Get.getString("WOJBOPN_INCENTIVE").equals("1"));
                    vec_JbOpnDet.addElement(objWOJobOpnDetails);
                }
                rs_WO_JbOpn_Det_Get.close();
                ps_WO_JbOpn_Det_Get.close();
                objWOJobDetails.setWOJobOpnDetails(vec_JbOpnDet);
                vec_WO_Jb_Det.addElement(objWOJobDetails);
            }

            rs_WO_JbDet_Get.close();
            ps_WO_JbDet_Get.close();

            objWorkOrderDetails.setWOJobDetails(vec_WO_Jb_Det);
        }
        catch(SQLException sqle)
		{
        	sqle.printStackTrace();
        	throw new WorkOrderException("WOEC000","GENERAL SQL ERROR",sqle.toString());
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


        logger.info("GET WORK ORDER DETAILS ENDS");

        return objWorkOrderDetails;
    }
    private WorkOrderDetails getWorkOrderDet(int WOId) throws SQLException, WorkOrderException
    {

        WorkOrderDetails objWorkOrderDetails = new WorkOrderDetails();
        Hashtable ht_WODet_Get = new Hashtable();
        ResultSet rs_WODet_Get = null;
        PreparedStatement ps_WODet_Get = null;
        ResultSet rs_WO_JbDet_Get = null;
        PreparedStatement ps_WO_JbDet_Get = null;
       
        logger.info("GET WORK ORDER DETAILS STARTS");

        con = DBConnectionFactory.getConnection();
        ht_WODet_Get.put("WO_ID",new Integer(WOId));
        try
        {
            ps_WODet_Get = con.executeStatement(SQLMaster.GET_WORKORDER_DETAILS_QUERY,ht_WODet_Get);
            rs_WODet_Get = ps_WODet_Get.executeQuery();
            if(rs_WODet_Get.next())
            {

                objWorkOrderDetails.setWorkOrderId(WOId);
                objWorkOrderDetails.setWorkOrderNo(rs_WODet_Get.getString("WO_NO"));
                objWorkOrderDetails.setWoCreatedDate(rs_WODet_Get.getTimestamp("WO_DATE"));
                objWorkOrderDetails.setCustId(rs_WODet_Get.getInt("CUST_ID"));
                objWorkOrderDetails.setCustName(rs_WODet_Get.getString("CUST_NAME"));
                objWorkOrderDetails.setCustTypeId(rs_WODet_Get.getInt("CUST_TYP_ID"));
                objWorkOrderDetails.setCustTypName(rs_WODet_Get.getString("CUST_TYP_NAME"));
                objWorkOrderDetails.setContactPerson(rs_WODet_Get.getString("WO_CNTCT_PERSON"));
                objWorkOrderDetails.setWorkOrderStatus(rs_WODet_Get.getString("WO_STAT"));
                objWorkOrderDetails.setWoEstmdCompleteDate(rs_WODet_Get.getTimestamp("WO_ESTMD_DATE"));
                objWorkOrderDetails.setWorkOrderIsValid(rs_WODet_Get.getInt("WO_ISVALID"));
                objWorkOrderDetails.setWorkOrderDateStamp(rs_WODet_Get.getTimestamp("WO_DATESTAMP"));
            }
            else
            {
                if(BuildConfig.DMODE)
                {
                    System.out.println("RECORD NOT FOUND");
                }
                throw new WorkOrderException("WOEC027","RECORD NOT FOUND","");
            }
            rs_WODet_Get.close();
            ps_WODet_Get.close();
            ////////////JOB DETAILS //////////
            Vector vec_WO_Jb_Det = new Vector();
            ps_WO_JbDet_Get = con.executeStatement(SQLMaster.GET_WORKORDER_JOB_DETAILS_QUERY,ht_WODet_Get);
            rs_WO_JbDet_Get = ps_WO_JbDet_Get.executeQuery();
            
            while(rs_WO_JbDet_Get.next())
            {
                WOJobDetails objWOJobDetails = new WOJobDetails();
                objWOJobDetails.setGeneralId(rs_WO_JbDet_Get.getInt("GNRL_ID"));
                objWOJobDetails.setGeneralName(rs_WO_JbDet_Get.getString("GNRL_NAME"));
                objWOJobDetails.setJobId(rs_WO_JbDet_Get.getInt("JB_ID"));
                objWOJobDetails.setJobName(rs_WO_JbDet_Get.getString("JB_NAME"));
                objWOJobDetails.setJobDrwgNo(rs_WO_JbDet_Get.getString("JB_DRWG_NO"));
                objWOJobDetails.setJobRvsnNo(rs_WO_JbDet_Get.getString("JB_RVSN_NO"));
                objWOJobDetails.setJobMatlType(rs_WO_JbDet_Get.getString("JB_MATL_TYP"));
                objWOJobDetails.setJobStatus(rs_WO_JbDet_Get.getString("WOJB_STAT"));
                objWOJobDetails.setJobQty(rs_WO_JbDet_Get.getInt("WOJB_QTY"));
                objWOJobDetails.setWoDCNo(rs_WO_JbDet_Get.getString("WOJB_DCNO"));
                objWOJobDetails.setWoDCDate(rs_WO_JbDet_Get.getTimestamp("WOJB_DCDATE"));
                objWOJobDetails.setWoJbStatus(rs_WO_JbDet_Get.getString("WOJB_STAT"));
                objWOJobDetails.setProdEntered(rs_WO_JbDet_Get.getString("WOJB_ISPROD_ENTERED").equalsIgnoreCase("1"));
                objWOJobDetails.setJobQtyStartSno(rs_WO_JbDet_Get.getInt("WOJB_START_SNO"));
                vec_WO_Jb_Det.addElement(objWOJobDetails);
            }

            rs_WO_JbDet_Get.close();
            ps_WO_JbDet_Get.close();

            objWorkOrderDetails.setWOJobDetails(vec_WO_Jb_Det);
        }
        catch(SQLException sqle)
		{
        	sqle.printStackTrace();
        	throw new WorkOrderException("WOEC000","GENERAL SQL ERROR",sqle.toString());
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


        logger.info("GET WORK ORDER DETAILS ENDS");

        return objWorkOrderDetails;
    
    }
    public HashMap getAllWorkOrderDetails(Filter[] filters,String sortBy,boolean ascending,int startIndex,int displayCount) throws WorkOrderException,SQLException
    {
        HashMap hm_Result = new HashMap();
        ResultSet rs_WO_Filter = null;
        Vector vec_WO_Filter = new Vector();


        logger.info("WORK ORDER FILTER STARTS");

        if(filters == null)
            throw new WorkOrderException("WOEC028","FILTER VALUES ARE NULL","");
        try
        {
            con = DBConnectionFactory.getConnection();
            int eIndex = startIndex + displayCount;

            int tot_Rec_Cnt = con.getRowCountWithComplexFilters(filters,SQLMaster.WORKORDER_FILTER_QUERY);
            String query = con.buildDynamicQuery(filters,startIndex,eIndex,sortBy,ascending,SQLMaster.WORKORDER_FILTER_QUERY);
            try
            {
                
                rs_WO_Filter = con.executeRownumStatement(query);
                int woId = 0;
                WorkOrderDetails objWorkOrderDetails = null;
                
                while(rs_WO_Filter.next())
                {
                    woId = rs_WO_Filter.getInt("WO_ID");
                    objWorkOrderDetails = this.getWorkOrderDet(woId);
                    vec_WO_Filter.addElement(objWorkOrderDetails);
                }
                hm_Result.put("TotalRecordCount",new Integer(tot_Rec_Cnt));
                hm_Result.put("WorkOrderDetails",vec_WO_Filter);
               
                rs_WO_Filter.getStatement().close();
                rs_WO_Filter.close();
            }
            catch(SQLException sqle)
			{
            	sqle.printStackTrace();
            	logger.error("EXCEPTION WHILE SELECTING WORK ORDER DETAILS");
            	throw new WorkOrderException("WOEC000","GENERAL SQL ERROR",sqle.toString());
			}
        }
        catch (SQLException e)
		{
        	e.printStackTrace();
        	logger.error("GENERAL SQL ERROR", e);
        	throw new WorkOrderException("WOEC000","GENERAL SQL ERROR",e.toString());
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


        logger.info("WORK ORDER FILTER ENDS");

        return hm_Result;
    }

    
    public Vector getJobNameByWorkOrder(int WOId) throws SQLException, WorkOrderException
    {
        Vector vec_Result = new Vector();
        ResultSet rs_JbName = null;
        PreparedStatement ps_JbName = null;
        Hashtable ht_JbName_Get = new Hashtable();


        logger.info("GET JOB NAME BY WORK ORDER STARTS");

        try
        {
            con = DBConnectionFactory.getConnection();
            ht_JbName_Get.put("WO_ID",new Integer(WOId));
            ps_JbName = con.executeStatement(SQLMaster.GET_JOBNAME_BY_WORKORDER,ht_JbName_Get);
            rs_JbName = ps_JbName.executeQuery();
            int i=0;
            while(rs_JbName.next())
            {
                i=1;
                WOJobDetails objWOJobDetails = new WOJobDetails();
                objWOJobDetails.setJobId(rs_JbName.getInt("JB_ID"));
                objWOJobDetails.setJobName(rs_JbName.getString("JB_NAME"));
                objWOJobDetails.setJobDrwgNo(rs_JbName.getString("JB_DWG_NO"));
                objWOJobDetails.setJobRvsnNo(rs_JbName.getString("JB_RVSN_NO"));
                objWOJobDetails.setJobMatlType(rs_JbName.getString("JB_MATL_TYP"));
                vec_Result.addElement(objWOJobDetails);
            }
            rs_JbName.close();
            ps_JbName.close();
            if(i==0)
                throw new WorkOrderException("WOEC029","RECORD NOT FOUND","");
        }

        catch(SQLException sqle)
		{
        	sqle.printStackTrace();
        	logger.error("EXCEPTION WHILE SELECTING JOB NAME BY WORK OREDER");
        	throw new WorkOrderException("WOEC000","GENERAL SQL ERROR",sqle.toString());
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


        logger.info("GET JOB NAME BY WORK ORDER ENDS");

        return vec_Result;

    }
    public HashMap makeWorkOrderValid(Vector workOrderIds) throws SQLException, WorkOrderException
    {
        HashMap hm_Result = new HashMap();
        int res = 0;


        logger.info("MAKE WORK ORDER VALID STARTS");


        if(workOrderIds == null)
        {

            logger.error("WORK ORDER IDS VECTOR OBJECT IS NULL");
            throw new WorkOrderException("WOEC030","WORK ORDER IDS VECTOR OBJECT IS NULL","");
        }
        try
        {
            con =  DBConnectionFactory.getConnection();
            con.startTransaction();
            if(BuildConfig.DMODE)
                System.out.println("Vector size "+workOrderIds.size());
            for(int i = 0;i<workOrderIds.size();i++)
            {
                Hashtable ht_Mk_WO_Valid = new Hashtable();

                ht_Mk_WO_Valid.put("WO_ID",(Integer)workOrderIds.get(i));
                res = con.executeUpdateStatement(SQLMaster.WORKORDER_MKVALID_QUERY,ht_Mk_WO_Valid);
                if(res>=1)
                {
                    hm_Result.put(workOrderIds.get(i),new Integer(0));

                    logger.info("WORK ORDER VALIDATED");
                    if(BuildConfig.DMODE)
                        System.out.println("WORK ORDER VALIDATED");
                    con.commitTransaction();
                }
                else
                {
                    hm_Result.put(workOrderIds.get(i),new Integer(1));
                    con.rollBackTransaction();


                    logger.debug("WORK ORDER NOT VALIDATED");


                }

            }
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
            if(BuildConfig.DMODE)
            {
                System.out.println("GENERAL EXCEPTION");
            }

            logger.error("GENERAL SQL ERROR",sqle);

            throw new WorkOrderException("WOEC000","GENERAL SQL ERROR",sqle.toString());
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


        logger.info("MAKE WORK ORDER VALID ENDS");

        return hm_Result;

    }
    public HashMap makeWorkOrderInValid(Vector workOrderIds) throws SQLException, WorkOrderException
    {
        HashMap hm_Result = new HashMap();
        int res = 0;


        logger.info("MAKE WORK ORDER INVALID STARTS");


        if(workOrderIds == null)
        {

            logger.error("WORK ORDER IDS VECTOR OBJECT IS NULL");
            throw new WorkOrderException("WOEC031","WORK ORDER IDS VECTOR OBJECT IS NULL","");
        }
        try
        {
            con =  DBConnectionFactory.getConnection();
            con.startTransaction();
            if(BuildConfig.DMODE)
                System.out.println("Vector size "+workOrderIds.size());
            for(int i = 0;i<workOrderIds.size();i++)
            {
                Hashtable ht_Mk_WO_InValid = new Hashtable();

                ht_Mk_WO_InValid.put("WO_ID",(Integer)workOrderIds.get(i));
                res = con.executeUpdateStatement(SQLMaster.WORKORDER_MKINVALID_QUERY,ht_Mk_WO_InValid);
                if(res>=1)
                {
                    hm_Result.put(workOrderIds.get(i),new Integer(0));

                    logger.info("WORK ORDER INVALIDATED");
                    if(BuildConfig.DMODE)
                        System.out.println("WORK ORDER INVALIDATED");
                    con.commitTransaction();
                }

                else
                {
                    hm_Result.put(workOrderIds.get(i),new Integer(1));
                    con.rollBackTransaction();


                    logger.debug("WORK ORDER NOT INVALIDATED");


                }

            }

        }
        catch(SQLException e)
        {
            e.printStackTrace();
            if(BuildConfig.DMODE)
            {
                System.out.println("GENERAL EXCEPTION");
            }

            logger.error("GENERAL SQL ERROR",e);

            throw new WorkOrderException("WOEC000","GENERAL SQL ERROR",e.toString());
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


        logger.info("MAKE WORK ORDER INVALID ENDS");

        return hm_Result;

    }





    public boolean addNewDummyWorkOrder(DummyWorkOrderDetails objDummyWorkOrderDetails) throws WorkOrderException, SQLException
    {
        boolean addRESULT = false;
        DBConnection con = null;
        int workOrder_Id = 0;
        int workOrderJob_Id = 0;
        int workOrderJobStat_Id = 0;
        int x = 0;
        String st = "";
        Hashtable ht_Job_Add = new Hashtable();
        Hashtable ht_JobOpn_Add = new Hashtable();
        Hashtable ht_WOJob_Add = new Hashtable();
        Hashtable ht_WOJobHstry_Add = new Hashtable();
        Hashtable ht_WOJobStat_Add = new Hashtable();
        Hashtable ht_WOJobOpn_Add = new Hashtable();
        int result = 0;
        int job_Id = 0;


        logger.info("ADD NEW DUMMY WORK ORDER STARTS");

        if(objDummyWorkOrderDetails.getDmyWoNo().length() == 0)
        {

            logger.error("DUMMY WORK ORDER NUMBER IS REQUIRED");
            throw new WorkOrderException("","DUMMY WORK ORDER NUMBER IS REQUIRED","");
        }
        con = DBConnectionFactory.getConnection();
        con.startTransaction();


        ht_Job_Add.put("JB_NAME",objDummyWorkOrderDetails.getDmyWoJbName());
        //System.out.println("CST_ID :"+objJobDetails.getCust_Id());
        ht_Job_Add.put("CST_ID",(x=objDummyWorkOrderDetails.getCustId())!=0 ? new Integer(x):objDBNull);
        ht_Job_Add.put("GNRL_ID",(x=objDummyWorkOrderDetails.getDmyWoGnrlId())!=0 ? new Integer(x):objDBNull);
        ht_Job_Add.put("JB_DRWG_NO",(st=objDummyWorkOrderDetails.getDmyWoJbDrwgNo())==null ?"": st);
        ht_Job_Add.put("JB_RVSN_NO",(st=objDummyWorkOrderDetails.getDmyWoJbRvsnNo())==null ?"": st);
        ht_Job_Add.put("JB_MATL_TYP",(st=objDummyWorkOrderDetails.getDmyWoJbMatlTyp())==null ? "": st);
        ht_Job_Add.put("JB_INCNTV_FLG","0");
        //ht_Job_Add.put("JB_CREATED_DATE",(objDummyWorkOrderDetails.getDmyWoDate())!=null ? objDummyWorkOrderDetails.getDmyWoDate():objDBNull);
        ht_Job_Add.put("JB_TYP","D");

        try
        {
            result = con.executeUpdateStatement(SQLMaster.DUMMY_JOB_ADD_SQL_QUERY,ht_Job_Add);
            if(BuildConfig.DMODE)
                System.out.println("JOB DETAILS" + (result != 0? " " : " NOT ") + "ADDED");
            if(result>0)
            {
                PreparedStatement ps_JbId_Get = con.executeStatement(SQLMaster.JOB_ID_SELECT_SQL_QUERY);
                ResultSet rs_JbId_Get = ps_JbId_Get.executeQuery();
                if(rs_JbId_Get.next())
                {
                    job_Id = rs_JbId_Get.getInt(1);
                }
                rs_JbId_Get.close();
                ps_JbId_Get.close();
                int startOpn = objDummyWorkOrderDetails.getDmyStartOpn();
                int endOpn = objDummyWorkOrderDetails.getDmyEndOpn();
                for(int i = startOpn;i<=endOpn;i++)
                {
                    ht_JobOpn_Add.put("OPN_GP_ID",new Integer(objDummyWorkOrderDetails.getDmyOpnGpId()));
                    ht_JobOpn_Add.put("JB_ID",new Integer(job_Id));
                    ht_JobOpn_Add.put("JBOPN_NAME",objDummyWorkOrderDetails.getDmyOpnName()+i);
                    ht_JobOpn_Add.put("JBOPN_SNO",new Integer(i));
                    ht_JobOpn_Add.put("JBOPN_STDHRS",new Float(0));
                    ht_JobOpn_Add.put("JBOPN_INCENTIVE","0");
                    result=con.executeUpdateStatement(SQLMaster.JOBOPN_ADD_SQL_QUERY,ht_JobOpn_Add);

                }

                Hashtable ht_WorkOrder_Add = new Hashtable();
                ht_WorkOrder_Add.put("WO_NO",(st=objDummyWorkOrderDetails.getDmyWoNo())!= null ? st:"");
                //ht_WorkOrder_Add.put("WO_DATE",(objDummyWorkOrderDetails.getDmyWoDate())!=null ? objDummyWorkOrderDetails.getDmyWoDate(): objDBNull);
                result = con.executeUpdateStatement(SQLMaster.DUMMY_WORKORDER_ADD_SQL_QUERY,ht_WorkOrder_Add);

                if(BuildConfig.DMODE)
                    System.out.println("WORK ORDER HEADER DETAILS" + (result != 0? " " : " NOT ") + "ADDED");
                if(result > 0)
                {
                    PreparedStatement ps_WOId_Get = con.executeStatement(SQLMaster.WORKORDER_ID_SELECT_SQL_QUERY);
                    ResultSet rs_WOId_Get = ps_WOId_Get.executeQuery();
                    if(rs_WOId_Get.next())
                    {
                        workOrder_Id = rs_WOId_Get.getInt(1);
                    }
                    rs_WOId_Get.close();
                    ps_WOId_Get.close();

                    ht_WOJob_Add.put("WO_ID",new Integer(workOrder_Id));
                    ht_WOJob_Add.put("JB_ID",new Integer(job_Id));
                    ht_WOJob_Add.put("WOJB_SNO",new Integer(1));
                    ht_WOJob_Add.put("WOJB_START_SNO",new Integer(objDummyWorkOrderDetails.getStartSno()));
                    ht_WOJob_Add.put("WOJB_QTY",new Integer(objDummyWorkOrderDetails.getJbQty()));
                    ht_WOJob_Add.put("WOJB_DCNO",objDummyWorkOrderDetails.getWoDCNo());
                    ht_WOJob_Add.put("WOJB_DCDATE",objDummyWorkOrderDetails.getWoDCDate());
                    
                    result = con.executeUpdateStatement(SQLMaster.WORKORDER_JOB_ADD_SQL_QUERY,ht_WOJob_Add);
                    if(BuildConfig.DMODE)
                        System.out.println("WORK ORDER JOB HEADER DETAILS" + (result != 0? " " : " NOT ") + "ADDED");
                    if(result > 0)
                    {
                        PreparedStatement ps_WOJobId_Get = con.executeStatement(SQLMaster.WORKORDERJOB_ID_SELECT_SQL_QUERY);
                        ResultSet rs_WOJobId_Get = ps_WOJobId_Get.executeQuery();
                        if(rs_WOJobId_Get.next())
                        {
                            workOrderJob_Id = rs_WOJobId_Get.getInt(1);
                        }

                        rs_WOJobId_Get.close();
                        ps_WOJobId_Get.close();

                        ht_WOJobHstry_Add.put("WOJB_ID",new Integer(workOrderJob_Id));
                        ht_WOJobHstry_Add.put("WO_NO",objDummyWorkOrderDetails.getDmyWoNo());
                        ht_WOJobHstry_Add.put("JB_NAME",objDummyWorkOrderDetails.getDmyWoJbName());
                        ht_WOJobHstry_Add.put("CUST_ID",new Integer(objDummyWorkOrderDetails.getCustId()));
                        ht_WOJobHstry_Add.put("JB_DWG_NO",objDummyWorkOrderDetails.getDmyWoJbDrwgNo());
                        ht_WOJobHstry_Add.put("JB_RVSN_NO",objDummyWorkOrderDetails.getDmyWoJbRvsnNo());
                        ht_WOJobHstry_Add.put("JB_MATL_TYP",objDummyWorkOrderDetails.getDmyWoJbMatlTyp());
                        ht_WOJobHstry_Add.put("WOJB_QTY",new Integer(objDummyWorkOrderDetails.getJbQty()));
                        ht_WOJobHstry_Add.put("WO_DATE",new Date());

                        result = con.executeUpdateStatement(SQLMaster.WORKORDER_JOBHISTRY_ADD_SQL_QUERY,ht_WOJobHstry_Add);

                        if(BuildConfig.DMODE)
                            System.out.println("WORK ORDER JOB HISTRY DETAILS" + (result != 0? " " : " NOT ") + "ADDED");

                        for(int j = 0 ; j<objDummyWorkOrderDetails.getJbQty();j++)
                        {

                            ht_WOJobStat_Add.put("WOJB_ID",new Integer(workOrderJob_Id));
                            ht_WOJobStat_Add.put("WOJBSTAT_SNO",new Integer(objDummyWorkOrderDetails.getStartSno()+j));
                            result = con.executeUpdateStatement(SQLMaster.WORKORDER_JOBSTAT_ADD_SQL_QUERY,ht_WOJobStat_Add);
                            if(BuildConfig.DMODE)
                                System.out.println("WORK ORDER JOBSTAT DETAILS" + (result != 0? " " : " NOT ") + "ADDED");
                            if(result > 0)
                            {
                                PreparedStatement ps_WOJobStatId_Get = con.executeStatement(SQLMaster.WORKORDERJOBSTAT_ID_SELECT_SQL_QUERY);
                                ResultSet rs_WOJobStatId_Get = ps_WOJobStatId_Get.executeQuery();
                                if(rs_WOJobStatId_Get.next())
                                {
                                    workOrderJobStat_Id = rs_WOJobStatId_Get.getInt(1);
                                }
                                rs_WOJobStatId_Get.close();
                                ps_WOJobStatId_Get.close();
                                for(int k = startOpn ; k<=endOpn;k++)
                                {
                                    ht_WOJobOpn_Add.put("WOJBSTAT_ID",new Integer(workOrderJobStat_Id));
                                    ht_WOJobOpn_Add.put("OPN_GP_ID",new Integer(objDummyWorkOrderDetails.getDmyOpnGpId()));
                                    ht_WOJobOpn_Add.put("WOJBOPN_OPN_SNO",new Integer(k));
                                    ht_WOJobOpn_Add.put("WOJBOPN_OPN_NAME",objDummyWorkOrderDetails.getDmyOpnName()+k);
                                    ht_WOJobOpn_Add.put("WOJBOPN_OPN_STDHRS",new Float(0));
                                    ht_WOJobOpn_Add.put("WOJBOPN_INCENTIVE","0");
                                    result = con.executeUpdateStatement(SQLMaster.WORKORDER_JOBOPN_ADD_SQL_QUERY,ht_WOJobOpn_Add);
                                    if(BuildConfig.DMODE)
                                        System.out.println("WORK ORDER JOBOPN DETAILS" + (result != 0? " " : " NOT ") + "ADDED");

                                }
                            }


                        }

                    }
                }


            }

            con.commitTransaction();
            addRESULT = true;

        }
        catch(SQLException sqle)
		{
        	sqle.printStackTrace();
        	logger.error("EXCEPTION WHILE ADDING DUMMY WORK ORDEER");
        	if(BuildConfig.DBASE==BuildConfig.ORACLE)
        	{
        		if(sqle.toString().indexOf("UK_WODMY_NO")>=0)
        		{
        			con.rollBackTransaction();
        			throw new WorkOrderException("WOEC","DUMMY WORK ORDER ALREADY EXISTS",sqle.toString());
        		}
        		else
        		{
        			con.rollBackTransaction();
        			throw new WorkOrderException("WOEC000","GENERAL SQL ERROR",sqle.toString());
        		}
        	}
        	if(BuildConfig.DBASE==BuildConfig.MYSQL)
        	{
        		if(sqle.toString().indexOf("Duplicate entry")>=0)
        		{
        			con.rollBackTransaction();
        			throw new WorkOrderException("WOEC","DUMMY WORK ORDER ALREADY EXISTS",sqle.toString());
        		}
        		else
        		{
        			con.rollBackTransaction();
        			throw new WorkOrderException("WOEC000","GENERAL SQL ERROR",sqle.toString());
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


        logger.info("ADD NEW DUMMY WORK ORDER ENDS");

        return addRESULT;

    }
    public WorkOrderDetails getDummyWorkOrderDetails(int DWOId) throws WorkOrderException, SQLException
    {
        WorkOrderDetails objWorkOrderDetails = new WorkOrderDetails();
        Hashtable ht_WODet_Get = new Hashtable();
        ResultSet rs_WODet_Get = null;
        ResultSet rs_WO_JbDet_Get = null;
        ResultSet rs_WO_JbOpn_Det_Get = null;


        logger.info("GET WORK ORDER DETAILS STARTS");

        con = DBConnectionFactory.getConnection();
        ht_WODet_Get.put("WO_ID",new Integer(DWOId));
        try
        {
            PreparedStatement ps_WODet_Get = con.executeStatement(SQLMaster.GET_WORKORDER_DETAILS_QUERY,ht_WODet_Get);
            rs_WODet_Get = ps_WODet_Get.executeQuery();
            if(rs_WODet_Get.next())
            {

                objWorkOrderDetails.setWorkOrderId(DWOId);
                objWorkOrderDetails.setWorkOrderNo(rs_WODet_Get.getString("WO_NO"));
                objWorkOrderDetails.setCustId(rs_WODet_Get.getInt("CUST_ID"));
                objWorkOrderDetails.setCustName(rs_WODet_Get.getString("CUST_NAME"));
                objWorkOrderDetails.setCustTypeId(rs_WODet_Get.getInt("CUST_TYP_ID"));
                objWorkOrderDetails.setCustTypName(rs_WODet_Get.getString("CUST_TYP_NAME"));
                objWorkOrderDetails.setContactPerson(rs_WODet_Get.getString("WO_CNTCT_PERSON"));
                objWorkOrderDetails.setWoCreatedDate(rs_WODet_Get.getTimestamp("WO_DATE"));
                objWorkOrderDetails.setWoEstmdCompleteDate(rs_WODet_Get.getTimestamp("WO_ESTMD_DATE"));
                objWorkOrderDetails.setWorkOrderIsValid(rs_WODet_Get.getInt("WO_ISVALID"));
                objWorkOrderDetails.setWorkOrderDateStamp(rs_WODet_Get.getTimestamp("WO_DATESTAMP"));
            }
            else
            {
                if(BuildConfig.DMODE)
                {
                    System.out.println("RECORD NOT FOUND");
                }
                throw new WorkOrderException("WOEC027","RECORD NOT FOUND","");
            }
            rs_WODet_Get.close();
            ps_WODet_Get.close();
            ////////////JOB DETAILS //////////
            Vector vec_WO_Jb_Det = new Vector();
            PreparedStatement ps_WO_JbDet_Get = con.executeStatement(SQLMaster.GET_WORKORDER_JOB_DETAILS_QUERY,ht_WODet_Get);
            rs_WO_JbDet_Get = ps_WO_JbDet_Get.executeQuery();
            while(rs_WO_JbDet_Get.next())
            {
                WOJobDetails objWOJobDetails = new WOJobDetails();
                objWOJobDetails.setGeneralId(rs_WO_JbDet_Get.getInt("GNRL_ID"));
                objWOJobDetails.setGeneralName(rs_WO_JbDet_Get.getString("GNRL_NAME"));
                objWOJobDetails.setJobId(rs_WO_JbDet_Get.getInt("JB_ID"));
                objWOJobDetails.setJobName(rs_WO_JbDet_Get.getString("JB_NAME"));
                objWOJobDetails.setJobDrwgNo(rs_WO_JbDet_Get.getString("JB_DRWG_NO"));
                objWOJobDetails.setJobRvsnNo(rs_WO_JbDet_Get.getString("JB_RVSN_NO"));
                objWOJobDetails.setJobMatlType(rs_WO_JbDet_Get.getString("JB_MATL_TYP"));
                objWOJobDetails.setJobStatus(rs_WO_JbDet_Get.getString("WOJB_STAT"));
                objWOJobDetails.setJobQty(rs_WO_JbDet_Get.getInt("WOJB_QTY"));
                objWOJobDetails.setWoDCNo(rs_WO_JbDet_Get.getString("WOJB_DCNO"));
                objWOJobDetails.setWoDCDate(rs_WO_JbDet_Get.getTimestamp("WOJB_DCDATE"));
                objWOJobDetails.setJobQtyStartSno(rs_WO_JbDet_Get.getInt("WOJB_START_SNO"));
                //////////////// JOB OPERATION DETAILS ///////////////
                Vector vec_JbOpnDet = new Vector();
                Hashtable ht_JbOpn_Det = new Hashtable();
                ht_JbOpn_Det.put("WO_ID",new Integer(DWOId));
                ht_JbOpn_Det.put("JB_ID",new Integer(objWOJobDetails.getJobId()));
                PreparedStatement ps_WO_JbOpn_Det_Get = con.executeStatement(SQLMaster.GET_WORKORDER_JOB_OPERATION_DETAILS_QUERY,ht_JbOpn_Det);
                rs_WO_JbOpn_Det_Get = ps_WO_JbOpn_Det_Get.executeQuery();
                while (rs_WO_JbOpn_Det_Get.next())
                {
                    WOJobOpnDetails objWOJobOpnDetails = new WOJobOpnDetails();
                    objWOJobOpnDetails.setOpnGpId(rs_WO_JbOpn_Det_Get.getInt("OPN_GP_ID"));
                    objWOJobOpnDetails.setOpnGpCode(rs_WO_JbOpn_Det_Get.getString("OPN_GP_CODE"));
                    objWOJobOpnDetails.setOpnName(rs_WO_JbOpn_Det_Get.getString("WOJBOPN_OPN_NAME"));
                    objWOJobOpnDetails.setOpnSerialNo(rs_WO_JbOpn_Det_Get.getInt("WOJBOPN_OPN_SNO"));
                    objWOJobOpnDetails.setOpnStdHrs(rs_WO_JbOpn_Det_Get.getFloat("WOJBOPN_OPN_STDHRS"));

                    vec_JbOpnDet.addElement(objWOJobOpnDetails);
                }
                rs_WO_JbOpn_Det_Get.close();
                ps_WO_JbOpn_Det_Get.close();

                objWOJobDetails.setWOJobOpnDetails(vec_JbOpnDet);
                vec_WO_Jb_Det.addElement(objWOJobDetails);
            }
            rs_WO_JbDet_Get.close();
            ps_WO_JbDet_Get.close();

            objWorkOrderDetails.setWOJobDetails(vec_WO_Jb_Det);
        }
        catch(SQLException sqle)
		{
        	sqle.printStackTrace();
        	throw new WorkOrderException("WOEC000","GENERAL SQL ERROR",sqle.toString());
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


        logger.info("GET WORK ORDER DETAILS ENDS");

        return objWorkOrderDetails;

    }

    public HashMap getAllDummyWorkOrderDetails(Filter[] filters,String sortBy,boolean ascending,int startIndex,int displayCount) throws WorkOrderException,SQLException
    {
        HashMap hm_Result = new HashMap();
        ResultSet rs_WO_Filter = null;
        Vector vec_WO_Filter = new Vector();


        logger.info("DUMMY WORK ORDER FILTER STARTS");

        if(filters == null)
            throw new WorkOrderException("WOEC028","FILTER VALUES ARE NULL","");
        try
        {
            con = DBConnectionFactory.getConnection();
            int eIndex = startIndex + displayCount;

            int tot_Rec_Cnt = con.getRowCountWithComplexFilters(filters,SQLMaster.DUMMY_WORKORDER_FILTER_QUERY);
            String query = con.buildDynamicQuery(filters,startIndex,eIndex,sortBy,ascending,SQLMaster.DUMMY_WORKORDER_FILTER_QUERY);

            try
            {
                rs_WO_Filter = con.executeRownumStatement(query);
                while(rs_WO_Filter.next())
                {
                    WorkOrderDetails objWorkOrderDetails = new WorkOrderDetails();
                    WOJobDetails objWOJobDetails = new WOJobDetails();
                    Vector vec_WOJB_Det = new Vector();
                    objWorkOrderDetails.setWorkOrderId(rs_WO_Filter.getInt("WO_ID"));
                    objWorkOrderDetails.setWorkOrderNo(rs_WO_Filter.getString("WO_NO"));
                    objWorkOrderDetails.setCustId(rs_WO_Filter.getInt("CUST_ID"));
                    objWorkOrderDetails.setCustName(rs_WO_Filter.getString("CUST_NAME"));
                    objWorkOrderDetails.setWoCreatedDate(rs_WO_Filter.getTimestamp("WO_DATE"));
                    objWorkOrderDetails.setWorkOrderStatus(rs_WO_Filter.getString("WO_STAT"));
                    objWorkOrderDetails.setWorkOrderIsValid(rs_WO_Filter.getInt("WO_ISVALID"));
                    objWorkOrderDetails.setWorkOrderDateStamp(rs_WO_Filter.getTimestamp("WO_DATESTAMP"));
                    //objWOJobDetails.setJobId(rs_WO_Filter.getInt("JB_ID"));
                    //objWOJobDetails.setJobName(rs_WO_Filter.getString("JB_NAME"));
                    //objWOJobDetails.setJobDrwgNo(rs_WO_Filter.getString("JB_DRWG_NO"));
                    //objWOJobDetails.setJobRvsnNo(rs_WO_Filter.getString("JB_RVSN_NO"));
                    //objWOJobDetails.setJobMatlType(rs_WO_Filter.getString("JB_MATL_TYP"));
                    //objWOJobDetails.setJobStatus(rs_WO_Filter.getString("WOJB_STAT"));


                    vec_WOJB_Det.addElement(objWOJobDetails);
                    objWorkOrderDetails.setWOJobDetails(vec_WOJB_Det);

                    vec_WO_Filter.addElement(objWorkOrderDetails);
                }
                hm_Result.put("TotalRecordCount",new Integer(tot_Rec_Cnt));
                hm_Result.put("DummyWorkOrderDetails",vec_WO_Filter);
                rs_WO_Filter.getStatement().close();
                rs_WO_Filter.close();
            }
            catch(SQLException sqle)
			{
            	sqle.printStackTrace();
            	logger.error("EXCEPTION WHILE SELECTING WORK ORDER DETAILS");
            	throw new WorkOrderException("WOEC000","GENERAL SQL ERROR",sqle.toString());
			}
        }
        catch (SQLException e)
		{
        	e.printStackTrace();
        	logger.error("GENERAL SQL ERROR", e);
        	throw new WorkOrderException("WOEC000","GENERAL SQL ERROR",e.toString());
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


        logger.info("WORK ORDER FILTER ENDS");

        return hm_Result;
    }


    public boolean addNewPreCloseLog(PreCloseDetails objPreCloseDetails) throws SQLException, WorkOrderException
    {
        boolean addRESULT = false;
        Vector vec_JobQty_Det = new Vector();
        Hashtable ht_PrClose_Add = new Hashtable();
        Hashtable ht_PreClose = new Hashtable();
        Hashtable ht_Status_Change = new Hashtable();
        SortString ss = new SortString();
        int woJbId = 0;
        int woId = 0;
        int woJbStatId = 0;


        logger.info("ADD NEW PRECLOSE STARTS");

        if(objPreCloseDetails == null)
            throw new WorkOrderException("WOEC032","PRECLOSE DETAILS OBJECT IS NULL","");
        try
        {
            con = DBConnectionFactory.getConnection();
            vec_JobQty_Det = (Vector)objPreCloseDetails.getJobQtyDetails();
            for(int i = 0;i<vec_JobQty_Det.size();i++)
            {
                Vector vec_JobOpn_Det = new Vector();
                JobQtyDetails objJobQtyDetails = new JobQtyDetails();
                objJobQtyDetails = (JobQtyDetails)vec_JobQty_Det.elementAt(i);
                ht_PrClose_Add.put("WOJBSTAT_ID",new Integer(objJobQtyDetails.getWoJbStatId()));
                vec_JobOpn_Det = objJobQtyDetails.getWOJobOpnDetails();
                String opnSnos = "";
                woJbStatId = objJobQtyDetails.getWoJbStatId();

                for( int j = 0;j<vec_JobOpn_Det.size();j++)
                {
                    WOJobOpnDetails objWOJobOpnDetails  = new WOJobOpnDetails();
                    objWOJobOpnDetails = (WOJobOpnDetails)vec_JobOpn_Det.elementAt(j);
                    if(j!=0)
                        opnSnos = opnSnos+","+objWOJobOpnDetails.getOpnSerialNo();
                    else
                        opnSnos = objWOJobOpnDetails.getOpnSerialNo()+"";
                    ht_PreClose.put("WOJBSTAT_ID",new Integer(objJobQtyDetails.getWoJbStatId()));
                    ht_PreClose.put("WOJBOPN_OPN_SNO",new Integer(objWOJobOpnDetails.getOpnSerialNo()));
                    con.executeUpdateStatement(SQLMaster.PRECLOSE_SQL_QUERY,ht_PreClose);
                }

                //status checking for remaining operations of particular quantity//
                PreparedStatement ps_OpnsStatus_Check = con.executeStatement(SQLMaster.PRODN_OPN_STATUS_CHECK_SQL_QUERY,ht_PrClose_Add);
                ResultSet rs_OpnsStatus_Check = ps_OpnsStatus_Check.executeQuery();

                //if some value comes,all the operations for the particular quantity is not completed.
                if(rs_OpnsStatus_Check.next())
                {

                    //to get woJbId and  woId for that particular quantity
                    PreparedStatement ps_Ids_Get = con.executeStatement(SQLMaster.PRODN_WOJBID_WOID_SELECT_SQL_QUERY,ht_PrClose_Add);
                    ResultSet rs_Ids_Get = ps_Ids_Get.executeQuery();

                    if(rs_Ids_Get.next())
                    {
                        woJbId = rs_Ids_Get.getInt("WOJB_ID");
                        woId = rs_Ids_Get.getInt("WO_ID");
                    }
                    rs_Ids_Get.close();
                    ps_Ids_Get.close();

                    ht_Status_Change.put("WOJBSTAT_ID",new Integer(woJbStatId));
                    ht_Status_Change.put("WOJB_ID",new Integer(woJbId));
                    ht_Status_Change.put("WO_ID",new Integer(woId));

                    //to make particular quantity sno status to 'A'
                    con.executeUpdateStatement(SQLMaster.PRODN_WOJBSTATSNO_STATUS_CHANGETOA_SQL_QUERY,ht_Status_Change);

                    //to make particular job status to 'A'
                    con.executeUpdateStatement(SQLMaster.PRODN_WOJB_STATUS_CHANGETOA_SQL_QUERY,ht_Status_Change);

                    //to make particular work order status to 'A'
                    con.executeUpdateStatement(SQLMaster.PRODN_WO_STATUS_CHANGETOA_SQL_QUERY,ht_Status_Change);
                }
                //all the operations for the particular quantity are completed..so we have to check another quantities of that job..
                else
                {
                    //to get woJbId and  woId for that particular quantity
                    PreparedStatement ps_Ids_Get = con.executeStatement(SQLMaster.PRODN_WOJBID_WOID_SELECT_SQL_QUERY,ht_PrClose_Add);
                    ResultSet rs_Ids_Get = ps_Ids_Get.executeQuery();
                    if(rs_Ids_Get.next())
                    {
                        woJbId = rs_Ids_Get.getInt("WOJB_ID");
                        woId = rs_Ids_Get.getInt("WO_ID");
                    }
                    rs_Ids_Get.close();
                    ps_Ids_Get.close();

                    ht_Status_Change.put("WOJBSTAT_ID",new Integer(woJbStatId));
                    ht_Status_Change.put("WOJB_ID",new Integer(woJbId));
                    ht_Status_Change.put("WO_ID",new Integer(woId));

                    //to make particular quantity sno status to 'C'
                    con.executeUpdateStatement(SQLMaster.PRODN_WOJBSTATSNO_STATUS_CHANGETOC_SQL_QUERY,ht_Status_Change);

                    //status checking for remaining job quantities of particular job//
                    PreparedStatement ps_WoJobQtysStatus_Check = con.executeStatement(SQLMaster.PRODN_WOJBSTATSNO_STATUS_CHECK_SQL_QUERY,ht_Status_Change);
                    ResultSet rs_WoJobQtysStatus_Check = ps_WoJobQtysStatus_Check.executeQuery();

                    if(rs_WoJobQtysStatus_Check.next())
                    {
                        //to make particular job status to 'A'
                        con.executeUpdateStatement(SQLMaster.PRODN_WOJB_STATUS_CHANGETOA_SQL_QUERY,ht_Status_Change);

                        //to make particular work order status to 'A'
                        con.executeUpdateStatement(SQLMaster.PRODN_WO_STATUS_CHANGETOA_SQL_QUERY,ht_Status_Change);

                    }
                    else
                    {
                        //to make particular job status to 'C'
                        con.executeUpdateStatement(SQLMaster.PRODN_WOJB_STATUS_CHANGETOC_SQL_QUERY,ht_Status_Change);

                        //status checking for remaining jobs of particular work order//
                        PreparedStatement ps_WoJobStatus_Check = con.executeStatement(SQLMaster.PRODN_JOB_STATUS_CHECK_SQL_QUERY,ht_Status_Change);
                        ResultSet rs_WoJobStatus_Check = ps_WoJobStatus_Check.executeQuery();
                        if(rs_WoJobStatus_Check.next())
                        {
                            //to make particular work order status to 'A'
                            con.executeUpdateStatement(SQLMaster.PRODN_WO_STATUS_CHANGETOA_SQL_QUERY,ht_Status_Change);
                        }
                        else
                        {
                            //to make particular work order status to 'C'
                            con.executeUpdateStatement(SQLMaster.PRODN_WO_STATUS_CHANGETOC_SQL_QUERY,ht_Status_Change);
                        }
                        rs_WoJobStatus_Check.close();
                        ps_WoJobStatus_Check.close();

                    }
                    rs_WoJobQtysStatus_Check.close();
                    ps_WoJobQtysStatus_Check.close();

                }
                rs_OpnsStatus_Check.close();
                ps_OpnsStatus_Check.close();
                ht_PrClose_Add.put("PCLOG_OPN_LIST",ss.sortString(opnSnos));
                ht_PrClose_Add.put("PCLOG_PCRESN",objPreCloseDetails.getPreCloseReason());
                try
                {
                    int result = con.executeUpdateStatement(SQLMaster.PRECLOSE_LOG_ADD_SQL_QUERY,ht_PrClose_Add);
                    if(result>0)
                    {
                        addRESULT = true;
                        if(BuildConfig.DMODE)
                            System.out.println("PRECLOSE LOG INFORMATION ADDED");
                    }
                    else
                    {
                        if(BuildConfig.DMODE)
                            System.out.println("PRECLOSE LOG INFORMATION NOT ADDED");
                    }

                }
                catch(SQLException sqle)
                {
                    sqle.printStackTrace();
                    logger.error("SQL EXCEPTION", sqle);

                    if (sqle.toString().indexOf("FK_PRCLOG_WOJBSTATID") >= 0 || sqle.toString().indexOf("foreign key constraint") >= 0)
                    {
                        throw new WorkOrderException("WOEC033","PARENT RECORD - WOJBSTAT NOT FOUND",sqle.toString());
                    }
                    else
                    {
                        throw new WorkOrderException("WOEC000","GENERAL SQL ERROR",sqle.toString());
                    }

                }
            }

        }
        catch(SQLException sqle)
		{
        	sqle.printStackTrace();
        	logger.error("GENERAL SQL ERROR");
        	throw new WorkOrderException("WOEC000","GENERAL SQL ERROR",sqle.toString());
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


        logger.info("ADD NEW PRECLOSE ENDS");

        return addRESULT;
    }


    public PreCloseDetails getForAddNewPreCloseLog(PreCloseDetails objPreCloseDetails) throws SQLException, WorkOrderException
    {

        Hashtable ht_WoJbId_Get = new Hashtable();
        Hashtable ht_WoJbStatId_Get = new Hashtable();
        Hashtable ht_WoJbStatSno_Get = new Hashtable();
        Hashtable ht_ProdDet_Get = new Hashtable();
        int woJbId = 0;
        int i = 0;
        SortString objSortString = new SortString();


        logger.info("GET FOR ADD NEW PRECLOSE STARTS");

        con = DBConnectionFactory.getConnection();
        ht_WoJbId_Get.put("WO_ID",new Integer(objPreCloseDetails.getWorkOrderId()));
        ht_WoJbId_Get.put("JB_ID",new Integer(objPreCloseDetails.getJbId()));
        try
        {
            PreparedStatement ps_WoJbId_Get = con.executeStatement(SQLMaster.PRECLOSE_WORKORDER_JOBID_GET_SQL_QUERY,ht_WoJbId_Get);
            ResultSet rs_WoJbId_Get = ps_WoJbId_Get.executeQuery();
            Vector vec_JobQty_Det = new Vector();
            if(rs_WoJbId_Get.next())
            {

                Vector vec_JobStat_Ids = new Vector();
                woJbId = rs_WoJbId_Get.getInt("WOJB_ID");
                ht_WoJbStatId_Get.put("WOJB_ID",new Integer(woJbId));
                PreparedStatement ps_WoJbStatId_Get = con.executeStatement(SQLMaster.PRECLOSE_WORKORDER_JOBSTATID_GET_SQL_QUERY,ht_WoJbStatId_Get);
                ResultSet rs_WoJbStatId_Get = ps_WoJbStatId_Get.executeQuery();
                while(rs_WoJbStatId_Get.next())
                {
                    vec_JobStat_Ids.addElement(new Integer(rs_WoJbStatId_Get.getInt("WOJBSTAT_ID")));
                }
                rs_WoJbStatId_Get.close();
                ps_WoJbStatId_Get.close();

                PreparedStatement ps_WoJbStatSno_Get = null;
                ResultSet rs_WoJbStatSno_Get = null;
                ResultSet rs_ProdDet_Get = null;
                PreparedStatement ps_ProdDet_Get = null;
                for(int x = 0 ;x<vec_JobStat_Ids.size();x++)
                {

                    JobQtyDetails objJobQtyDetails = new JobQtyDetails();
                    ht_WoJbStatSno_Get.put("WOJBSTAT_ID",(Integer)vec_JobStat_Ids.elementAt(x));
                    ps_WoJbStatSno_Get = con.executeStatement(SQLMaster.PRECLOSE_WORKORDER_JOBSTATSNO_GET_SQL_QUERY,ht_WoJbStatSno_Get);
                    rs_WoJbStatSno_Get = ps_WoJbStatSno_Get.executeQuery();
                    if(rs_WoJbStatSno_Get.next())
                    {
                        objJobQtyDetails.setJobQtySno(rs_WoJbStatSno_Get.getInt("WOJBSTAT_SNO"));
                        objJobQtyDetails.setWoJbStatId(((Integer)vec_JobStat_Ids.elementAt(x)).intValue());
                    }
                    rs_WoJbStatSno_Get.close();
                    ps_WoJbStatSno_Get.close();
                    //to fetch PRODUCTION ENTERED OPERATIONS..from PROD table

                    ht_ProdDet_Get.put("WOJB_ID",new Integer(woJbId));
                    ps_ProdDet_Get = con.executeStatement(SQLMaster.PRODUCTION_ENTERED_OPNS_GET_SQL_QUERY,ht_ProdDet_Get);
                    rs_ProdDet_Get = ps_ProdDet_Get.executeQuery();
                    String prodEnteredOpns = "";
                    int woJbStatSno = 0;

                    String prodQtySnos = "";
                    int startOpn = 0;
                    int endOpn = 0;
                    woJbStatSno = objJobQtyDetails.getJobQtySno();
                    int flag = 0;
                    while(rs_ProdDet_Get.next())
                    {
                        prodQtySnos = rs_ProdDet_Get.getString("PROD_QTY_SNOS");
                        startOpn = rs_ProdDet_Get.getInt("PROD_START_OPN");
                        endOpn = rs_ProdDet_Get.getInt("PROD_END_OPN");
                        StringTokenizer st = new StringTokenizer(prodQtySnos,",");
                        while(st.hasMoreTokens())
                            //for(i = prodQtyStSno;i<(prodQtyStSno+prodTotQty);i++)
                        {
                            i = Integer.parseInt(st.nextToken());
                            if(i==woJbStatSno)
                            {
                                for(int j = startOpn;j<=endOpn;j++)
                                {
                                    if(flag==0)
                                        prodEnteredOpns = j+"";
                                    else
                                        prodEnteredOpns = prodEnteredOpns+","+j;
                                    flag = 1;
                                }
                                break;
                            }
                        }
                    }
                    rs_ProdDet_Get.close();
                    ps_ProdDet_Get.close();
                    //to take all Operations from RADL table for this particular Quantity...
                    PreparedStatement ps_RadlProd_Det = con.executeStatement(SQLMaster.GET_RADL_PRODDETAILS_SQL_QUERY,ht_ProdDet_Get);
                    ResultSet rs_RadlProd_Det = ps_RadlProd_Det.executeQuery();
                    while(rs_RadlProd_Det.next())
                    {
                        String qtySnos = "";
                        qtySnos = rs_RadlProd_Det.getString("RADL_QTY_SNOS");
                        StringTokenizer st = new StringTokenizer(qtySnos.trim(),",");
                        while(st.hasMoreTokens())
                        {
                            int sno = Integer.parseInt(st.nextToken().trim());
                            if(sno == woJbStatSno)
                            {
                                startOpn = rs_RadlProd_Det.getInt("RADL_START_OPN");
                                endOpn = rs_RadlProd_Det.getInt("RADL_END_OPN");
                                for(int j = startOpn ;j<=endOpn ; j++)
                                {
                                    if(prodEnteredOpns.equals(""))
                                    {
                                        prodEnteredOpns = j+"";
                                    }
                                    else
                                    {
                                        prodEnteredOpns = prodEnteredOpns +","+j;
                                    }

                                }

                            }
                        }

                    }
                    rs_RadlProd_Det.close();
                    ps_RadlProd_Det.close();




                    //to fetch OPEN OPERATIONS..from WO_JB_OPN table
                    PreparedStatement ps_WoJbStatOpns_Get = con.executeStatement(SQLMaster.PRECLOSE_WORKORDER_OPEN_JOBOPNS_GET_SQL_QUERY,ht_WoJbStatSno_Get);
                    ResultSet rs_WoJbStatOpns_Get = ps_WoJbStatOpns_Get.executeQuery();
                    Vector vec_Opn_Det = new Vector();
                    int woJbOpnSno = 0;
                    int fl = 0;
                    String opns = "";
                    int array[];
                    array = new int[prodEnteredOpns.length()];
                    StringTokenizer st = new StringTokenizer(prodEnteredOpns,",");
                    int index = 0;
                    while(st.hasMoreTokens())
                    {
                        array[index] = Integer.parseInt(st.nextToken());
                        index++;
                    }
                    while(rs_WoJbStatOpns_Get.next())
                    {
                        woJbOpnSno = rs_WoJbStatOpns_Get.getInt("WOJBOPN_OPN_SNO");
                        int flagz = 0;
                        for(int n = 0;n<array.length;n++)
                        {
                            if(woJbOpnSno==array[n])
                            {
                                flagz = 1;
                            }
                        }
                        if(flagz!=1)
                        {
                            WOJobOpnDetails objWOJobOpnDetails = new WOJobOpnDetails();

                            objWOJobOpnDetails.setOpnGpId(rs_WoJbStatOpns_Get.getInt("OPN_GP_ID"));
                            objWOJobOpnDetails.setWoJbOpnId(rs_WoJbStatOpns_Get.getInt("WOJBOPN_ID"));

                            objWOJobOpnDetails.setOpnSerialNo(woJbOpnSno);
                            objWOJobOpnDetails.setOpnName(rs_WoJbStatOpns_Get.getString("WOJBOPN_OPN_NAME"));
                            objWOJobOpnDetails.setOpnStdHrs(rs_WoJbStatOpns_Get.getFloat("WOJBOPN_OPN_STDHRS"));
                            vec_Opn_Det.addElement(objWOJobOpnDetails);
                            if(fl==0)
                            {
                                opns =woJbOpnSno+"";
                                fl = 1;
                            }
                            else
                            {
                                opns = opns +","+woJbOpnSno;

                            }
                            if(BuildConfig.DMODE)
                            System.out.println(opns);
                        }
                    }
                    rs_WoJbStatOpns_Get.close();
                    ps_WoJbStatOpns_Get.close();

                    objJobQtyDetails.setWOJobOpenOpnString(objSortString.sortString(opns));
                    objJobQtyDetails.setWOJobOpenOpnDetails(vec_Opn_Det);

                    //to fetch COMPLETED OPERATIONS..from WO_JB_OPN table
                    ps_WoJbStatOpns_Get = con.executeStatement(SQLMaster.PRECLOSE_WORKORDER_CMPLTD_JOBOPNS_GET_SQL_QUERY,ht_WoJbStatSno_Get);
                    rs_WoJbStatOpns_Get = ps_WoJbStatOpns_Get.executeQuery();
                    opns = "";
                    fl = 0;
                    while(rs_WoJbStatOpns_Get.next())
                    {
                        if(fl==0)
                        {
                            opns =rs_WoJbStatOpns_Get.getInt("WOJBOPN_OPN_SNO")+"";
                            fl=1;
                        }
                        else
                        {
                            opns = opns +","+rs_WoJbStatOpns_Get.getInt("WOJBOPN_OPN_SNO");

                        }
                    }
                    rs_WoJbStatOpns_Get.close();
                    ps_WoJbStatOpns_Get.close();
                    objJobQtyDetails.setWOJobCmpltdOpnString(objSortString.sortString(opns));

                    //						//to fetch REWORK OPERATIONS..from WO_JB_OPN table
                    ps_WoJbStatOpns_Get = con.executeStatement(SQLMaster.PRECLOSE_WORKORDER_REWORK_JOBOPNS_GET_SQL_QUERY,ht_WoJbStatSno_Get);
                    rs_WoJbStatOpns_Get = ps_WoJbStatOpns_Get.executeQuery();
                    fl = 0;
                    opns = "";
                    while(rs_WoJbStatOpns_Get.next())
                    {
                        if(fl==0)
                        {
                            opns =rs_WoJbStatOpns_Get.getInt("WOJBOPN_OPN_SNO")+"";
                            fl=1;
                        }
                        else
                        {
                            opns = opns +","+rs_WoJbStatOpns_Get.getInt("WOJBOPN_OPN_SNO");

                        }
                    }
                    rs_WoJbStatOpns_Get.close();
                    ps_WoJbStatOpns_Get.close();

                    objJobQtyDetails.setWOJobReworkOpnString(objSortString.sortString(opns));
                    objJobQtyDetails.setWOJobProductionEnteredOpnString(objSortString.sortString(prodEnteredOpns));
                    vec_JobQty_Det.addElement(objJobQtyDetails);
                }


            }
            else
            {
                if(BuildConfig.DMODE)
                    System.out.println("RECORD NOT FOUND");
                throw new WorkOrderException("WOEC034","RECORD NOT FOUND","");
            }
            rs_WoJbId_Get.close();
            ps_WoJbId_Get.close();
            objPreCloseDetails.setJobQtyDetails(vec_JobQty_Det);
        }
        catch(SQLException sqle)
		{
        	sqle.printStackTrace();
        	logger.error("EXCEPTION WHILE GET FOR ADD NEW PRECLOSE LOG");
        	throw new WorkOrderException("WOEC000","GENERAL SQL ERROR",sqle.toString());
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



        logger.info("GET FOR ADD NEW PRECLOSE ENDS");

        return objPreCloseDetails;
    }

    public HashMap workOrderJobCloseView(Filter[] filter,String sortBy,boolean ascending,int startIndex,int displayCount) throws WorkOrderException, SQLException
    {


        HashMap hm_Result = new HashMap();
        ResultSet rs_WoClose_Filter = null;
        Vector vec_WoClose_Filter = new Vector();


        logger.info("WORK ORDER JOB CLOSE VIEW STARTS");


        if(filter == null)
            throw new WorkOrderException("WOEC037","FILTER VALUES ARE NULL","");

        try
        {
            con = DBConnectionFactory.getConnection();
            int eIndex = startIndex + displayCount;
            int tot_Rec_Cnt = con.getRowCountWithComplexFilters(filter,SQLMaster.GET_WORKORDER_COMPLETED_JOBDETAILS_FILTER_QUERY);
            String query = con.buildDynamicQuery(filter,startIndex,eIndex,sortBy,ascending,SQLMaster.GET_WORKORDER_COMPLETED_JOBDETAILS_FILTER_QUERY);

            rs_WoClose_Filter = con.executeRownumStatement(query);

            while(rs_WoClose_Filter.next())
            {
                WorkOrderCloseDetails objWorkOrderCloseDetails = new WorkOrderCloseDetails();
                objWorkOrderCloseDetails.setWorkOrderJbId(rs_WoClose_Filter.getInt("WOJB_ID"));
                objWorkOrderCloseDetails.setWorkOrderNo(rs_WoClose_Filter.getString("WO_NO"));
                objWorkOrderCloseDetails.setWorkOrderDate(rs_WoClose_Filter.getTimestamp("WO_DATE"));
                objWorkOrderCloseDetails.setCustName(rs_WoClose_Filter.getString("CUST_NAME"));
                objWorkOrderCloseDetails.setJobName(rs_WoClose_Filter.getString("JB_NAME"));
                objWorkOrderCloseDetails.setDrwgNo(rs_WoClose_Filter.getString("JB_DWG_NO"));
                objWorkOrderCloseDetails.setRvsnNo(rs_WoClose_Filter.getString("JB_RVSN_NO"));
                objWorkOrderCloseDetails.setMatlType(rs_WoClose_Filter.getString("JB_MATL_TYP"));
                vec_WoClose_Filter.addElement(objWorkOrderCloseDetails);
            }

            hm_Result.put("TotalRecordCount",new Integer(tot_Rec_Cnt));
            hm_Result.put("WorkOrderCloseDetails",vec_WoClose_Filter);
            rs_WoClose_Filter.getStatement().close();
            rs_WoClose_Filter.close();

        }
        catch(SQLException sqle)
		{
        	sqle.printStackTrace();
        	logger.error("GENERAL SQL ERROR");
        	throw new WorkOrderException("WOEC000","GENERAL SQL ERROR",sqle.toString());
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


        logger.info("WORK ORDER JOB CLOSE VIEW ENDS");

        return hm_Result;

    }

    public HashMap closeWorkOrder(Vector vec_woJbIds) throws SQLException, WorkOrderException
    {

        Hashtable ht_Job_Close = new Hashtable();
        Hashtable ht_WO_Stat_Check = new Hashtable();
        HashMap hm_Result = new HashMap();

        int woJbId;


        logger.info("WORK ORDER JOB CLOSE STARTS");

        if(vec_woJbIds == null)
        {
            throw new WorkOrderException("WOEC035","VECTOR OBJECT IS NULL","");
        }
        try
        {
            con = DBConnectionFactory.getConnection();
            con.startTransaction();
            ResultSet rs_Wo_Id = null;
            PreparedStatement ps_Wo_Id = null;
            ResultSet rs_WO_Stat_Check = null;
            PreparedStatement ps_WO_Stat_Check = null;

            for(int i = 0;i<vec_woJbIds.size();i++)
            {
                woJbId = ((Integer)vec_woJbIds.elementAt(i)).intValue();
                ht_Job_Close.put("WOJB_ID",new Integer(woJbId));
                int res = con.executeUpdateStatement(SQLMaster.WORKORDER_COMPLETED_JOB_CLOSE_QUERY,ht_Job_Close);
                if(res > 0)
                {
                    hm_Result.put(vec_woJbIds.get(i),new Integer(0));
                    logger.info(vec_woJbIds.get(i)+" : WORK ORDER JOB CLOSED");
                    ps_Wo_Id = con.executeStatement(SQLMaster.WORKORDER_ID_SELECT_QUERY,ht_Job_Close);
                    rs_Wo_Id = ps_Wo_Id.executeQuery();
                    int workOrderId = 0;
                    if(rs_Wo_Id.next())
                    {
                        workOrderId = rs_Wo_Id.getInt("WO_ID");
                    }
                    ht_WO_Stat_Check.put("WO_ID",new Integer(workOrderId));
                    ps_WO_Stat_Check = con.executeStatement(SQLMaster.WORKORDER_STAT_CHECK_SQL_QUERY,ht_WO_Stat_Check);
                    rs_WO_Stat_Check = ps_WO_Stat_Check.executeQuery();
                    if(!rs_WO_Stat_Check.next())
                    {
                        int resultx = con.executeUpdateStatement(SQLMaster.WORKORDER_CLOSE_SQL_QUERY,ht_WO_Stat_Check);
                        if(BuildConfig.DMODE)
                        {
                            if(resultx>0)
                            {
                                System.out.println("WORK ORDER IS CLOSED");
                            }
                        }
                        /****moving of Closed Work Order Details.****/
                        //to move WO_HEADER..
                        /*		ht_CloseWorkOrder.put("WO_ID",new Integer(workOrderId));


                         vec_Obj=con.getProductVector(SQLMaster.CLOSE_WORKORDER_MOVE_SQL_QUERY,ht_CloseWorkOrder);
                         String PrepareStatement_Query=con.getProductQuery(SQLMaster.CLOSE_WORKORDER_MOVE_SQL_QUERY);

                         PreparedStatement ps_CloseWorkOrder_Add=con.executeRawQuery(PrepareStatement_Query,vec_Obj);
                         result = ps_CloseWorkOrder_Add.executeUpdate();
                         System.out.println("WORK ORDER HEADER DETAILS" + (result != 0? " " : " NOT ") + "MOVED");
                         if(result > 0)
                         {

                         vec_Obj = con.getProductVector(SQLMaster.WORKORDER_JOBID_GET_SQL_QUERY,ht_CloseWorkOrder);
                         PrepareStatement_Query = con.getProductQuery(SQLMaster.WORKORDER_JOBID_GET_SQL_QUERY);
                         PreparedStatement ps_WoJbId_Get = con.executeRawQuery(PrepareStatement_Query,vec_Obj);
                         ResultSet rs_WoJbId_Get = ps_WoJbId_Get.executeQuery();

                         while(rs_WoJbId_Get.next())
                         {

                         //to move WO_JB_HEADER..
                          ht_CloseWoJbHeader.put("WOJB_ID",new Integer(rs_WoJbId_Get.getInt("WOJB_ID")));
                          vec_Obj=con.getProductVector(SQLMaster.CLOSE_WORKORDER_JOB_MOVE_SQL_QUERY,ht_CloseWoJbHeader);
                          PrepareStatement_Query=con.getProductQuery(SQLMaster.CLOSE_WORKORDER_JOB_MOVE_SQL_QUERY);

                          PreparedStatement ps_CloseWorkOrderJbHeader = con.executeRawQuery(PrepareStatement_Query,vec_Obj);
                          result = ps_CloseWorkOrderJbHeader.executeUpdate();
                          System.out.println("WORK ORDER JOB HEADER DETAILS" + (result != 0? " " : " NOT ") + "MOVED");

                          vec_Obj = con.getProductVector(SQLMaster.WORKORDER_JOBSTATID_GET_SQL_QUERY,ht_CloseWoJbHeader);
                          PrepareStatement_Query = con.getProductQuery(SQLMaster.WORKORDER_JOBSTATID_GET_SQL_QUERY);
                          PreparedStatement ps_WoJbStatId_Get = con.executeRawQuery(PrepareStatement_Query,vec_Obj);
                          ResultSet rs_WoJbStatId_Get = ps_WoJbStatId_Get.executeQuery();
                          while(rs_WoJbStatId_Get.next())
                          {
                          //to move WO_JB_STAT..
                           ht_CloseWoJbStat.put("WOJBSTAT_ID",new Integer(rs_WoJbStatId_Get.getInt("WOJBSTAT_ID")));
                           vec_Obj=con.getProductVector(SQLMaster.CLOSE_WORKORDER_JOB_STAT_MOVE_SQL_QUERY,ht_CloseWoJbStat);
                           PrepareStatement_Query=con.getProductQuery(SQLMaster.CLOSE_WORKORDER_JOB_STAT_MOVE_SQL_QUERY);

                           PreparedStatement ps_CloseWoJbStat = con.executeRawQuery(PrepareStatement_Query,vec_Obj);
                           result = ps_CloseWoJbStat.executeUpdate();
                           System.out.println("WORK ORDER JOB STAT DETAILS" + (result != 0? " " : " NOT ") + "MOVED");

                           //to move WO_JB_OPN..
                            vec_Obj=con.getProductVector(SQLMaster.CLOSE_WORKORDER_JOB_OPN_MOVE_SQL_QUERY,ht_CloseWoJbStat);
                            PrepareStatement_Query=con.getProductQuery(SQLMaster.CLOSE_WORKORDER_JOB_OPN_MOVE_SQL_QUERY);

                            PreparedStatement ps_CloseWoJbOpn = con.executeRawQuery(PrepareStatement_Query,vec_Obj);
                            result = ps_CloseWoJbOpn.executeUpdate();


                            }
                            }


                            }






                            Deletion of Work Order tables..starts

                            vec_Obj = con.getProductVector(SQLMaster.WORKORDER_JOBID_GET_SQL_QUERY,ht_CloseWorkOrder);
                            PrepareStatement_Query = con.getProductQuery(SQLMaster.WORKORDER_JOBID_GET_SQL_QUERY);
                            PreparedStatement ps_WoJbId_Get = con.executeRawQuery(PrepareStatement_Query,vec_Obj);
                            ResultSet rs_WoJbId_Get = ps_WoJbId_Get.executeQuery();
                            Vector vec_Job_Ids = new Vector();
                            while(rs_WoJbId_Get.next())
                            {
                            vec_Job_Ids.addElement(new Integer(rs_WoJbId_Get.getInt("WOJB_ID")));
                            }

                            Vector vec_JobStat_Ids = new Vector();
                            Vector vec_JobStat_TotIds = new Vector();
                            for(i = 0;i<vec_Job_Ids.size();i++)
                            {
                            ht_WoJbStatId_Get.put("WOJB_ID",(Integer)vec_Job_Ids.elementAt(i));
                            vec_Obj = con.getProductVector(SQLMaster.WORKORDER_JOBSTATID_GET_SQL_QUERY,ht_WoJbStatId_Get);
                            PrepareStatement_Query = con.getProductQuery(SQLMaster.WORKORDER_JOBSTATID_GET_SQL_QUERY);
                            PreparedStatement ps_WoJbStatId_Get = con.executeRawQuery(PrepareStatement_Query,vec_Obj);
                            ResultSet rs_WoJbStatId_Get = ps_WoJbStatId_Get.executeQuery();
                            while(rs_WoJbStatId_Get.next())
                            {
                            vec_JobStat_Ids.addElement(new Integer(rs_WoJbStatId_Get.getInt("WOJBSTAT_ID")));
                            }
                            vec_JobStat_TotIds.addElement(vec_JobStat_Ids);
                            rs_WoJbStatId_Get.close();
                            ps_WoJbStatId_Get.close();
                            }
                            //deletion of work order tables starts
                             for(int j = 0;j<vec_JobStat_TotIds.size();j++)
                             {
                             vec_JobStat_Ids = (Vector)vec_JobStat_TotIds.elementAt(j);
                             for(int k = 0;k<vec_JobStat_Ids.size();k++)
                             {
                             jbStatId = ((Integer)vec_JobStat_Ids.elementAt(k)).intValue();
                             ht_WoJbOpn_Del.put("WOJBSTAT_ID",new Integer(jbStatId));
                             vec_Obj = con.getProductVector(SQLMaster.WORKORDER_JOBOPN_DEL_SQL_QUERY,ht_WoJbOpn_Del);
                             PrepareStatement_Query = con.getProductQuery(SQLMaster.WORKORDER_JOBOPN_DEL_SQL_QUERY);
                             PreparedStatement ps_WoJbOpn_Del = con.executeRawQuery(PrepareStatement_Query,vec_Obj);
                             result = ps_WoJbOpn_Del.executeUpdate();
                             if(result > 0)
                             System.out.println("Opns Deleted");
                             else
                             System.out.println("Opns Not Deleted");
                             ps_WoJbOpn_Del.close();
                             }
                             }
                             for(int l = 0;l<vec_Job_Ids.size();l++)
                             {
                             ht_WoJbStat_Del.put("WOJB_ID",(Integer)vec_Job_Ids.elementAt(l));
                             vec_Obj = con.getProductVector(SQLMaster.WORKORDER_JOBSTAT_DEL_SQL_QUERY,ht_WoJbStat_Del);
                             PrepareStatement_Query = con.getProductQuery(SQLMaster.WORKORDER_JOBSTAT_DEL_SQL_QUERY);
                             PreparedStatement ps_WoJbStat_Del = con.executeRawQuery(PrepareStatement_Query,vec_Obj);
                             result = ps_WoJbStat_Del.executeUpdate();
                             if(result > 0)
                             System.out.println("Jb Stat Deleted");
                             else
                             System.out.println("Jb Stat Not Deleted");
                             //vec_Obj = con.getProductVector(SQLMaster.WORKORDER_JOBHSTRY_DEL_SQL_QUERY,ht_WoJbStat_Del);
                              //PrepareStatement_Query = con.getProductQuery(SQLMaster.WORKORDER_JOBHSTRY_DEL_SQL_QUERY);
                               //PreparedStatement ps_WoJbHstry_Del = con.executeRawQuery(PrepareStatement_Query,vec_Obj);
                                //result = ps_WoJbHstry_Del.executeUpdate();
                                 //if(result > 0)
                                  //	System.out.println("Jb Hstry Deleted");
                                   //else
                                    //	System.out.println("Jb Hstry Not Deleted");
                                     //ps_WoJbHstry_Del.close();
                                      ps_WoJbStat_Del.close();

                                      }

                                      ht_WoJbHeader_Del.put("WO_ID",new Integer(workOrderId));
                                      vec_Obj = con.getProductVector(SQLMaster.WORKORDER_JOBHEADER_DEL_SQL_QUERY,ht_WoJbHeader_Del);
                                      PrepareStatement_Query = con.getProductQuery(SQLMaster.WORKORDER_JOBHEADER_DEL_SQL_QUERY);
                                      PreparedStatement ps_WoJbHeader_Del = con.executeRawQuery(PrepareStatement_Query,vec_Obj);
                                      result = ps_WoJbHeader_Del.executeUpdate();
                                      if(result > 0)
                                      System.out.println("Jb Header Deleted");
                                      else
                                      System.out.println("Jb Header Not Deleted");

                                      vec_Obj = con.getProductVector(SQLMaster.WORKORDER_HEADER_DEL_SQL_QUERY,ht_WoJbHeader_Del);
                                      PrepareStatement_Query = con.getProductQuery(SQLMaster.WORKORDER_HEADER_DEL_SQL_QUERY);
                                      PreparedStatement ps_WoHeader_Del = con.executeRawQuery(PrepareStatement_Query,vec_Obj);
                                      result = ps_WoHeader_Del.executeUpdate();
                                      if(result > 0)
                                      System.out.println("Wo Header Deleted");
                                      else
                                      System.out.println("Wo Header Not Deleted");


                                      }*/

                    }
                    con.commitTransaction();
                }
                else
                {
                    con.rollBackTransaction();
                    hm_Result.put(vec_woJbIds.get(i),new Integer(1));
                    logger.info(vec_woJbIds.get(i)+" : WORK ORDER JOB NOT CLOSED");
                }



            }
        }
        catch(SQLException sqle)
		{
        	con.rollBackTransaction();
        	sqle.printStackTrace();
        	logger.error("GENERAL SQL ERROR");
        	throw new WorkOrderException("WOEC000","GENERAL SQL ERROR",sqle.toString());
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



        logger.info("WORK ORDER JOB CLOSE ENDS");


        return hm_Result;
    }

    public PreCloseDetails getPreCloseDetails(int prclLogId) throws WorkOrderException,SQLException
    {
        PreCloseDetails objPreCloseDetails = new PreCloseDetails();
        Hashtable ht_PreClose_Get = new Hashtable();
        Hashtable ht_WOJbOpn = new Hashtable();
        ResultSet rs_PreClose_JobDet_Get = null;
        ResultSet rs_WoJbStat_Sno_Get = null;
        PreparedStatement ps_WoJbStat_Sno_Get = null;
        ResultSet rs_PreClose_Get = null;
        int WOJbStatId = 0;


        logger.info("GET PRECLOSE DETAILS STARTS");

        con = DBConnectionFactory.getConnection();

        ht_PreClose_Get.put("PRCL_LOG_ID",new Integer(prclLogId));

        try
        {
            PreparedStatement ps_PreClose_JobDet_Get = con.executeStatement(SQLMaster.GET_PRECLOSE_JOB_DETAILS_QUERY,ht_PreClose_Get);
            rs_PreClose_JobDet_Get = ps_PreClose_JobDet_Get.executeQuery();

            //set WorkOrder Job Details//
            if(rs_PreClose_JobDet_Get.next())
            {
                //	objPreCloseDetails.setWorkOrderJbStatId(WOJbStatId);
                objPreCloseDetails.setWorkOrderId(rs_PreClose_JobDet_Get.getInt("WO_ID"));
                objPreCloseDetails.setWorkOrderNo(rs_PreClose_JobDet_Get.getString("WO_NO"));
                objPreCloseDetails.setWorkOrderDate(rs_PreClose_JobDet_Get.getTimestamp("WO_DATE"));
                objPreCloseDetails.setJbId(rs_PreClose_JobDet_Get.getInt("JB_ID"));
                objPreCloseDetails.setJbName(rs_PreClose_JobDet_Get.getString("JB_NAME"));
                objPreCloseDetails.setJbDwgNo(rs_PreClose_JobDet_Get.getString("JB_DWG_NO"));
                objPreCloseDetails.setJbRvsnNo(rs_PreClose_JobDet_Get.getString("JB_RVSN_NO"));
                objPreCloseDetails.setJbMatlTyp(rs_PreClose_JobDet_Get.getString("JB_MATL_TYP"));

                rs_PreClose_JobDet_Get.close();

                /*//set Job Stat Sno starts//
                 vec_Obj = con.getProductVector(SQLMaster.GET_WORKORDER_JBSTATSNO_QUERY,ht_PreClose_Get);
                 PrepareStatement_Query = con.getProductQuery(SQLMaster.GET_WORKORDER_JBSTATSNO_QUERY);
                 PreparedStatement ps_WOJbStat_Sno_Get = con.executeRawQuery(PrepareStatement_Query,vec_Obj);

                 rs_WoJbStat_Sno_Get = ps_WOJbStat_Sno_Get.executeQuery();

                 JobQtyDetails objJobQtyDetails = new JobQtyDetails();
                 if(rs_WoJbStat_Sno_Get.next())
                 {
                 objJobQtyDetails.setJobQtySno(rs_WoJbStat_Sno_Get.getInt("WOJBSTAT_SNO"));
                 //objJobQtyDetails.setWoJbStatId(WOJbStatId);

                  }
                  rs_WoJbStat_Sno_Get.close();
                  ps_WOJbStat_Sno_Get.close();
                  //set Job Stat Sno ends//
                   */

                //set PreClose Reason starts//

                JobQtyDetails objJobQtyDetails = new JobQtyDetails();
                PreparedStatement ps_PreClose_Get = con.executeStatement(SQLMaster.GET_PRECLOSE_LOG_DETAILS_QUERY,ht_PreClose_Get);
                rs_PreClose_Get = ps_PreClose_Get.executeQuery();

                if(rs_PreClose_Get.next())
                {
                    WOJbStatId = rs_PreClose_Get.getInt("WOJBSTAT_ID");
                    objPreCloseDetails.setPreCloseReason(rs_PreClose_Get.getString("PCLOG_PCRESN"));
                    //set PreClose Reason ends//

                    //String Tokenizer for PreClose Operation List//
                    String pcLogOpnList = rs_PreClose_Get.getString("PCLOG_OPN_LIST");
                    StringTokenizer st = new StringTokenizer(pcLogOpnList,",");
                    int sno =0 ;
                    Vector vec_WOJbOpnDet = new Vector();
                    ResultSet rs_PreClose_Opn_Get = null;
                    PreparedStatement ps_PreClose_Opn_Get = null;
                    while(st.hasMoreTokens())
                    {
                        sno=Integer.parseInt(st.nextToken());
                        //Put WOJBOPN_OPN_SNO and WOJBSTAT_ID into hashtable to get its Job Operation Details//
                        ht_WOJbOpn.put("WOJBSTAT_ID",new Integer(WOJbStatId));
                        ht_WOJbOpn.put("WOJBOPN_OPN_SNO",new Integer(sno));
                        ps_PreClose_Opn_Get = con.executeStatement(SQLMaster.GET_PRECLOSE_WORKORDER_JOBOPERATION_QUERY,ht_WOJbOpn);
                        rs_PreClose_Opn_Get = ps_PreClose_Opn_Get.executeQuery();

                        while(rs_PreClose_Opn_Get.next())
                        {

                            WOJobOpnDetails objWOJbOpnDetails = new WOJobOpnDetails();

                            //set Work Order Job Operation Details for the above WOJBOPN_OPN_SNO and WOJBSTAT_ID//
                            objWOJbOpnDetails.setOpnGpId(rs_PreClose_Opn_Get.getInt("OPN_GP_ID"));
                            objWOJbOpnDetails.setOpnGpCode(rs_PreClose_Opn_Get.getString("OPN_GP_CODE"));
                            objWOJbOpnDetails.setOpnName(rs_PreClose_Opn_Get.getString("WOJBOPN_OPN_NAME"));
                            objWOJbOpnDetails.setOpnSerialNo(rs_PreClose_Opn_Get.getInt("WOJBOPN_OPN_SNO"));
                            objWOJbOpnDetails.setOpnStdHrs(rs_PreClose_Opn_Get.getFloat("WOJBOPN_OPN_STDHRS"));

                            vec_WOJbOpnDet.addElement(objWOJbOpnDetails);

                        }

                        rs_PreClose_Opn_Get.close();
                        ps_PreClose_Opn_Get.close();


                    }
                    //set Job Stat Sno starts//
                    ps_WoJbStat_Sno_Get = con.executeStatement(SQLMaster.GET_WORKORDER_JBSTATSNO_QUERY,ht_WOJbOpn);
                    rs_WoJbStat_Sno_Get = ps_WoJbStat_Sno_Get.executeQuery();

                    //JobQtyDetails objJobQtyDetails = new JobQtyDetails();
                    if(rs_WoJbStat_Sno_Get.next())
                    {
                        objJobQtyDetails.setJobQtySno(rs_WoJbStat_Sno_Get.getInt("WOJBSTAT_SNO"));
                        //objJobQtyDetails.setWoJbStatId(WOJbStatId);

                    }
                    rs_WoJbStat_Sno_Get.close();
                    ps_WoJbStat_Sno_Get.close();

                    //set Job Stat Sno ends//
                    objJobQtyDetails.setWOJobOpnDetails(vec_WOJbOpnDet);
                    Vector vec = new Vector();
                    vec.addElement(objJobQtyDetails);
                    objPreCloseDetails.setJobQtyDetails(vec);

                }
                rs_PreClose_Get.close();
                ps_PreClose_Get.close();
            }
            else
                throw new WorkOrderException("WOEC036","RECORD NOT FOUND","");

            rs_PreClose_JobDet_Get.close();
            ps_PreClose_JobDet_Get.close();

        }
        catch(SQLException sqle)
		{
        	sqle.printStackTrace();
        	logger.error("EXCEPTION WHILE SELECTING PRECLOSE WORKORDER DETAILS");
        	throw new WorkOrderException("WOEC000","GENERAL SQL ERROR",sqle.toString());
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

        logger.info("GET WORK ORDER DETAILS ENDS");

        return objPreCloseDetails;
    }
    public  HashMap getAllPreCloseDetails(Filter[] filter,String sortBy,boolean ascending,int startIndex,int displayCount) throws WorkOrderException,SQLException
    {

        HashMap hm_Result = new HashMap();
        ResultSet rs_WOPreClose_Filter = null;
        Vector vec_PreClose_Filter = new Vector();


        logger.info("PRECLOSE WORK ORDER FILTER DETAILS STARTS");

        if(filter == null)
            throw new WorkOrderException("WOEC037","FILTER VALUES ARE NULL","");

        try
        {
            con = DBConnectionFactory.getConnection();
            int eIndex = startIndex + displayCount;
            int tot_Rec_Cnt = con.getRowCountWithComplexFilters(filter,SQLMaster.PRECLOSE_WORKORDER_FILTER_QUERY);
            String query = con.buildDynamicQuery(filter,startIndex,eIndex,sortBy,ascending,SQLMaster.PRECLOSE_WORKORDER_FILTER_QUERY);


            rs_WOPreClose_Filter = con.executeRownumStatement(query);
            while(rs_WOPreClose_Filter.next())
            {
                PreCloseDetails objPreCloseDetails = new PreCloseDetails();

                objPreCloseDetails.setWorkOrderNo(rs_WOPreClose_Filter.getString("WO_NO"));
                objPreCloseDetails.setWorkOrderDate(rs_WOPreClose_Filter.getTimestamp("WO_DATE"));
                objPreCloseDetails.setPreCloseReason(rs_WOPreClose_Filter.getString("PCLOG_PCRESN"));
                objPreCloseDetails.setWorkOrderJbStatId(rs_WOPreClose_Filter.getInt("WOJBSTAT_ID"));
                objPreCloseDetails.setPrecloseLogId(rs_WOPreClose_Filter.getInt("PRCL_LOG_ID"));
                objPreCloseDetails.setWorkOrderJbStatSno(rs_WOPreClose_Filter.getInt("WOJBSTAT_SNO"));
                objPreCloseDetails.setJbId(rs_WOPreClose_Filter.getInt("JB_ID"));
                objPreCloseDetails.setJbName(rs_WOPreClose_Filter.getString("JB_NAME"));
                objPreCloseDetails.setJbDwgNo(rs_WOPreClose_Filter.getString("JB_DWG_NO"));
                objPreCloseDetails.setJbRvsnNo(rs_WOPreClose_Filter.getString("JB_RVSN_NO"));
                objPreCloseDetails.setJbMatlTyp(rs_WOPreClose_Filter.getString("JB_MATL_TYP"));
                objPreCloseDetails.setJbSno(rs_WOPreClose_Filter.getInt("WOJB_SNO"));
                objPreCloseDetails.setPreCloseLogDate(rs_WOPreClose_Filter.getTimestamp("PCLOG_LOGDATE"));
                objPreCloseDetails.setPreCloseDateStamp(rs_WOPreClose_Filter.getTimestamp("PCLOG_DATESTAMP"));
                objPreCloseDetails.setPreCloseIsValid(rs_WOPreClose_Filter.getInt("PCLOG_ISVALID"));

                vec_PreClose_Filter.addElement(objPreCloseDetails);
            }
            hm_Result.put("TotalRecordCount",new Integer(tot_Rec_Cnt));
            hm_Result.put("PreCloseDetails",vec_PreClose_Filter);
            rs_WOPreClose_Filter.getStatement().close();
            rs_WOPreClose_Filter.close();
        }
        catch(SQLException ex)
		{
        	ex.printStackTrace();
        	logger.error("GENERAL EXCEPTION");
        	throw new WorkOrderException("WOEC000","GENERAL SQL ERROR",ex.toString());
        	
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


        logger.info("PRECLOSE WORK ORDER FILTER DETAILS ENDS");

        return hm_Result;
    }
    public HashMap makePreCloseLogValid(Vector woJbStatIds) throws WorkOrderException,SQLException
    {
        HashMap hm_Result = new HashMap();
        int res = 0;


        logger.info("MAKE PRECLOSE VALID STARTS");

        if(woJbStatIds == null)
            throw new WorkOrderException("WOEC038","VECTOR OBJECT IS NULL","");

        try
        {
            con = DBConnectionFactory.getConnection();

            for(int i=0;i<woJbStatIds.size();i++)
            {
                Hashtable ht_Pcl_Ids = new Hashtable();
                ht_Pcl_Ids.put("WOJBSTAT_ID",(Integer)woJbStatIds.get(i));
                res = con.executeUpdateStatement(SQLMaster.PRECLOSE_MAKE_VALID_QUERY,ht_Pcl_Ids);
                if(res > 0)
                {
                    hm_Result.put(woJbStatIds.get(i),new Integer(0));


                    logger.info("PRECLOSE RECORD VALIDATED");

                }
                else
                {
                    hm_Result.put(woJbStatIds.get(i),new Integer(1));


                    logger.info("PRECLOSE RECORD NOT VALIDATED");

                }
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            if(BuildConfig.DMODE)
            {
                System.out.println("GENERAL EXCEPTION");
            }

            logger.error("GENERAL SQL ERROR",e);

            throw new WorkOrderException("WOEC000","GENERAL SQL ERROR",e.toString());
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


        logger.info("MAKE PRECLOSE LOG VALID ENDS");


        return hm_Result;

    }
    public HashMap makePreCloseLogInValid(Vector woJbStatIds) throws WorkOrderException,SQLException
    {
        HashMap hm_Result = new HashMap();
        int res = 0;


        logger.info("MAKE PRECLOSE INVALID STARTS");

        if(woJbStatIds == null)
            throw new WorkOrderException("WOEC039","VECTOR OBJECT IS NULL","");

        try
        {
            con = DBConnectionFactory.getConnection();

            for(int i=0;i<woJbStatIds.size();i++)
            {
                Hashtable ht_Pcl_Ids = new Hashtable();
                ht_Pcl_Ids.put("WOJBSTAT_ID",(Integer)woJbStatIds.get(i));
                res = con.executeUpdateStatement(SQLMaster.PRECLOSE_MAKE_INVALID_QUERY,ht_Pcl_Ids);
                if(res > 0)
                {
                    hm_Result.put(woJbStatIds.get(i),new Integer(0));


                    logger.info("PRECLOSE RECORD INVALIDATED");

                }
                else
                {
                    hm_Result.put(woJbStatIds.get(i),new Integer(1));


                    logger.info("PRECLOSE RECORD NOT INVALIDATED");

                }

            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            if(BuildConfig.DMODE)
            {
                System.out.println("GENERAL EXCEPTION");
            }

            logger.error("GENERAL SQL ERROR",e);
            throw new WorkOrderException("WOEC000","GENERAL SQL ERROR",e.toString());
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


        logger.info("MAKE PRECLOSE LOG INVALID ENDS");


        return hm_Result;

    }

    /*public boolean isWoModify(int woId) throws WorkOrderException, SQLException
    {
        boolean retVal = false;
        DBConnection con = null;
        Hashtable ht_Wo = new Hashtable();
        int cnt = 0;
        try
        {
            con = DBConnectionFactory.getConnection();
            ht_Wo.put("WO_ID",new Integer(woId));
            PreparedStatement ps = con.executeStatement(SQLMaster.CHECK_WORKORDER_HEADER_FOR_MODIFY_SQL_QUERY,ht_Wo);
            ResultSet rs_Wo = ps.executeQuery();
            if(rs_Wo.next())
            {
                cnt = rs_Wo.getInt(1);
                if(cnt==0)
                {

                    throw new WorkOrderException("","WORK ORDER CANNOT BE MODIFIED. PRODUCTION ENTRY MADE","");
                }
                else
                {
                    retVal = true;
                }
            }
            rs_Wo.close();
            ps.close();

        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }
            throw new WorkOrderException("PMEC000","GENERAL SQL ERROR",sqle.toString());
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
*/
    public static void main(String args[]) throws WorkOrderException,SQLException
    {
        //WorkOrderDetailsManager objWorkOrderDetailsManager = new WorkOrderDetailsManager();
        /*WorkOrderDetails objWODetails =  new WorkOrderDetails();
         DummyWorkOrderDetails objDWODetails =  new DummyWorkOrderDetails();
         WOJobOpnDetails objWOJobOpnDetails = new WOJobOpnDetails();
         WOJobDetails objWOJobDetails = new WOJobDetails();

         Vector v = new Vector();
         WorkOrderDetails objWorkOrderDetails = new WorkOrderDetails();
         WOJobOpnDetails objWOJobOpnDetails1 = new WOJobOpnDetails();
         WOJobOpnDetails objWOJobOpnDetails2 = new WOJobOpnDetails();

         /////closeWorkOrder()/////
          //v.addElement(new Integer(210));
           //objWorkOrderDetailsManager.closeWorkOrder(v);

            /////addNewWorkOrder()/////
             objWOJobOpnDetails.setOpnGpId(44);
             objWOJobOpnDetails.setOpnName("opnname10");
             objWOJobOpnDetails.setOpnSerialNo(1);
             objWOJobOpnDetails.setOpnStdHrs(2.5f);
             v.addElement(objWOJobOpnDetails);

             objWOJobOpnDetails1.setOpnGpId(13024);
             objWOJobOpnDetails1.setOpnName("opnname20");
             objWOJobOpnDetails1.setOpnSerialNo(2);
             objWOJobOpnDetails1.setOpnStdHrs(3.5f);
             v.addElement(objWOJobOpnDetails1);


             objWOJobOpnDetails2.setOpnGpId(13025);
             objWOJobOpnDetails2.setOpnName("opnname30");
             objWOJobOpnDetails2.setOpnSerialNo(3);
             objWOJobOpnDetails2.setOpnStdHrs(4.5f);
             v.addElement(objWOJobOpnDetails2);

             Vector v2 = new Vector();
             WOJobOpnDetails objWOJobOpnDetails3 = new WOJobOpnDetails();

             objWOJobOpnDetails3.setOpnGpId(13026);
             objWOJobOpnDetails3.setOpnName("opnname40");
             objWOJobOpnDetails3.setOpnSerialNo(1);
             objWOJobOpnDetails3.setOpnStdHrs(5.5f);
             v2.addElement(objWOJobOpnDetails3);

             WOJobOpnDetails objWOJobOpnDetails4 = new WOJobOpnDetails();

             objWOJobOpnDetails4.setOpnGpId(13027);
             objWOJobOpnDetails4.setOpnName("opnname50");
             objWOJobOpnDetails4.setOpnSerialNo(2);
             objWOJobOpnDetails4.setOpnStdHrs(6.5f);
             v2.addElement(objWOJobOpnDetails4);

             WOJobOpnDetails objWOJobOpnDetails5 = new WOJobOpnDetails();

             objWOJobOpnDetails5.setOpnGpId(42);
             objWOJobOpnDetails5.setOpnName("opnname60");
             objWOJobOpnDetails5.setOpnSerialNo(3);
             objWOJobOpnDetails5.setOpnStdHrs(7.5f);
             v2.addElement(objWOJobOpnDetails5);

             WOJobDetails objWOJobDetails1 = new WOJobDetails();
             Vector v1 = new Vector();
             objWOJobDetails.setWOJobOpnDetails(v);
             objWOJobDetails.setJobDrwgNo("dwgno");
             objWOJobDetails.setJobId(13023);
             objWOJobDetails.setJobSerialNo(1);
             objWOJobDetails.setJobMatlType("matltype");
             objWOJobDetails.setJobName("jobname");
             objWOJobDetails.setJobQty(3);
             objWOJobDetails.setJobQtyStartSno(1);
             objWOJobDetails.setJobRvsnNo("rvsno");
             v1.addElement(objWOJobDetails);

             objWOJobDetails1.setWOJobOpnDetails(v2);
             objWOJobDetails1.setJobDrwgNo("dwgno1");
             objWOJobDetails1.setJobId(13024);
             objWOJobDetails1.setJobSerialNo(2);
             objWOJobDetails1.setJobMatlType("matltype1");
             objWOJobDetails1.setJobName("jobname1");
             objWOJobDetails1.setJobQty(4);
             objWOJobDetails1.setJobQtyStartSno(1);
             objWOJobDetails1.setJobRvsnNo("rvsn1");
             v1.addElement(objWOJobDetails1);

             objWorkOrderDetails.setWOJobDetails(v1);
             objWorkOrderDetails.setCustId(13022);
             objWorkOrderDetails.setWoCreatedDate(new Date("04-NOV-04"));
             objWorkOrderDetails.setContactPerson("bhaskar");
             objWorkOrderDetails.setWoEstmdCompleteDate(new Date("04-NOV-04"));
             objWorkOrderDetails.setWorkOrderNo("150");
             //objWorkOrderDetails.setWorkOrderId(121);
              WorkOrderDetailsManager mgr = new WorkOrderDetailsManager();
              PreCloseDetails objPreCloseDetails = new PreCloseDetails();

              //System.out.println(mgr.addNewWorkOrder(objWorkOrderDetails));
               objPreCloseDetails.setWorkOrderId(180);
               objPreCloseDetails.setJbId(13023);
               System.out.println("hai");
               System.out.println(mgr.getWorkOrderDetails(198));

               //Vector vecs = new Vector();
                //vecs.addElement(new Integer(216));
                 //mgr.closeWorkOrder(vecs);
                  //Object obj = vecs.elementAt(0);
                   //Integer x = new Integer(obj.toString());
                    //System.out.println(x.intValue());

                     /////updateWorkOrder()/////
                      //mgr.updateWorkOrder(objWorkOrderDetails);


                       /////getForAddNewPreCloseLog()/////
                        objPreCloseDetails = mgr.getForAddNewPreCloseLog(objPreCloseDetails);

                        JobQtyDetails objJobQtyDetails = new JobQtyDetails();
                        Vector vt = new Vector();
                        vt = objPreCloseDetails.getJobQtyDetails();
                        System.out.println(vt.size());
                        objJobQtyDetails = (JobQtyDetails)vt.elementAt(1);
                        System.out.println(objJobQtyDetails.getWOJobOpenOpnString());
                        System.out.println(objJobQtyDetails.getWOJobProductionEnteredOpnString());
                        System.out.println(objJobQtyDetails.getWOJobCmpltdOpnString());

                        WorkOrderCloseDetails objWorkOrderCloseDetails = new WorkOrderCloseDetails();
                        Vector v7= new Vector();
                        v7.addElement(new Integer(63));
                        v7.addElement(new Integer(64));
                        PreCloseDetails objPreCloseDetails = new PreCloseDetails();
                        objPreCloseDetails.setJbId(13024);
                        objPreCloseDetails.setWorkOrderId(67);*/
        //		System.out.println(mgr.isWoModify(196));
        //		//objPreCloseDetails = mgr.getForAddNewPreCloseLog(objPreCloseDetails);
        //		JobQtyDetails objJobQtyDetails = new JobQtyDetails();
        //		Vector v11 = objPreCloseDetails.getJobQtyDetails();
        //		Vector v12 = new Vector();
        //		for(int k = 0;k<v11.size();k++)
        //		{
        //			objJobQtyDetails = (JobQtyDetails)v11.elementAt(k);
        //			System.out.println("The Job Qty Is "+objJobQtyDetails.getJobQtySno());
        //			v12 =objJobQtyDetails.getWOJobOpenOpnDetails();
        //			objWOJobOpnDetails = new WOJobOpnDetails();
        //			for(int l = 0;l<v12.size();l++)
        //			{
        //				objWOJobOpnDetails = (WOJobOpnDetails)v12.elementAt(l);
        //				//System.out.println(objWOJobOpnDetails.getOpnGpId());
        //				System.out.println("The Opn Name Is "+objWOJobOpnDetails.getOpnName());
        //				System.out.println("The Opn Sno Is "+objWOJobOpnDetails.getOpnSerialNo());
        //				System.out.println("The Opn Std Hrs Is "+objWOJobOpnDetails.getOpnStdHrs());
        //
        //
        //			}
        //		}
        //System.out.println(mgr.closeWorkOrder(v7));

        //////workOrderJobCloseView()//////

        //v7 = mgr.workOrderJobCloseView();
        /*for(int j=0;j<v7.size();j++)
         {
         objWorkOrderCloseDetails = (WorkOrderCloseDetails)v7.elementAt(j);
         System.out.println(objWorkOrderCloseDetails.getWorkOrderId());
         System.out.println(objWorkOrderCloseDetails.getWorkOrderNo());
         System.out.println(objWorkOrderCloseDetails.getWorkOrderDate());
         System.out.println(objWorkOrderCloseDetails.getCustName());
         System.out.println(objWorkOrderCloseDetails.getJobName());
         System.out.println(objWorkOrderCloseDetails.getDrwgNo());
         System.out.println(objWorkOrderCloseDetails.getRvsnNo());
         System.out.println(objWorkOrderCloseDetails.getMatlType());


         }

         //objWorkOrderDetails.setWorkOrderId(44);
          //System.out.println(mgr.updateWorkOrder(objWorkOrderDetails));
           //Vector v10 = new Vector();
            //v10.addElement(new Integer(61));
             //mgr.makeWorkOrderValid(v10);*/

        //getAllWorkOrderDetails()//(Filter)

        /*System.out.println("WORK ORDER FILTER STARTS");
         Filter[] filters = new Filter[1];
         filters[0] = new Filter();
         filters[0].setFieldName("CUST_NAME");
         filters[0].setFieldValue("13");
         filters[0].setSpecialFunction("Starts With");
         String sortBy = "WO_NO";

         filters[0] = new Filter();
         filters[0].setFieldName("WO_DATE");
         filters[0].setFieldValue("04-nov-2004$04-nov-2004");
         filters[0].setSpecialFunction("DateBetween");

         boolean ascending = true;
         int startIndex = 0;
         int displayCount = 10;
         HashMap hm = objWorkOrderDetailsManager.getAllWorkOrderDetails(filters,sortBy,ascending,startIndex,displayCount);
         System.out.println("HashMap Result :" + hm);*/


        //getJobNameByWorkOrder(()//

        /*Vector vec = objWorkOrderDetailsManager.getJobNameByWorkOrder(62);
         for(int i=0;i<vec.size();i++)
         {
         //System.out.println("JBNAME :"+vec.get(i));
          WOJobDetails objWOJobDetails1 = new WOJobDetails();
          objWOJobDetails1 = (WOJobDetails)vec.get(i);
          System.out.println(objWOJobDetails1.getJobDrwgNo());
          System.out.println(objWOJobDetails1.getJobId());
          System.out.println(objWOJobDetails1.getJobMatlType());
          System.out.println(objWOJobDetails1.getJobRvsnNo());
          System.out.println(objWOJobDetails1.getJobName());
          }*/
        // getWorkOrderList()//

        /*HashMap hm =objWorkOrderDetailsManager.getWorkOrderList();
         System.out.println("Result :"+hm);*/


        //getWorkOrderDetails()//

        /*objWODetails = objWorkOrderDetailsManager.getWorkOrderDetails(62);
         System.out.println("1."+objWODetails.getWoEstmdCompleteDate());
         System.out.println("1."+objWODetails.getCustName());
         System.out.println("1."+objWODetails.getCustTypName());
         System.out.println("1."+objWODetails.getWorkOrderNo());
         Vector vec = objWODetails.getWOJobDetails();
         for(int i=0;i<vec.size();i++)
         {
         WOJobDetails objWOJobDetails=(WOJobDetails)vec.get(i);
         System.out.println("2."+objWOJobDetails.getGeneralName());
         System.out.println("2."+objWOJobDetails.getJobName());
         System.out.println("2."+objWOJobDetails.getJobStatus());
         System.out.println("2."+objWOJobDetails.getJobRvsnNo());

         Vector vec1 = objWOJobDetails.getWOJobOpnDetails();
         for(int i1=0;i1<vec1.size();i1++)
         {
         WOJobOpnDetails objWOJobOpnDetails = (WOJobOpnDetails)vec1.get(i1);
         System.out.println("3."+objWOJobOpnDetails.getOpnGpCode());
         System.out.println("3."+objWOJobOpnDetails.getOpnName());
         System.out.println("3."+objWOJobOpnDetails.getOpnSerialNo());
         System.out.println("3."+objWOJobOpnDetails.getOpnStdHrs());

         }
         }*/

        //getPreCloseDetails()//

        /*PreCloseDetails objPreCloseDetails = objWorkOrderDetailsManager.getPreCloseDetails(1510);
         System.out.println("1."+objPreCloseDetails.getPreCloseReason());
         System.out.println("2."+objPreCloseDetails.getJbName());
         System.out.println("3."+objPreCloseDetails.getWorkOrderNo());
         Vector vec = objPreCloseDetails.getJobQtyDetails();

         for(int i=0;i<vec.size();i++)
         {
         System.out.println("4.vector values"+vec.get(i));
         JobQtyDetails objJobQtyDetails = (JobQtyDetails)vec.get(i);
         System.out.println("Value :"+i+" "+objJobQtyDetails.getJobQtySno());
         System.out.println("OPERATION VECTOR :"+objJobQtyDetails.getWOJobOpnDetails());

         }*/
        //getAllPreCloseDetails()[Filter]//
        //Filter[] filters = new Filter[1];
        /*filters[0] = new Filter();
         filters[0].setFieldName("JB_NAME");
         filters[0].setFieldValue("jobname");
         filters[0].setSpecialFunction("AnyWhere");*/


        /*filters[0] = new Filter();
         filters[0].setFieldName("PCLOG_LOGDATE");
         filters[0].setFieldValue("12-DEC-2004$12-DEC-2004");
         filters[0].setSpecialFunction("DateBetween");

         boolean ascending = true;
         int startIndex = 0;
         String sortBy = "WO_NO";
         int displayCount = 10;

         HashMap hm =objWorkOrderDetailsManager.getAllPreCloseDetails(filters,sortBy,ascending,startIndex,displayCount);
         System.out.println("Result :"+hm);
         v =  (Vector)hm.get("PreCloseDetails");
         PreCloseDetails obj = new PreCloseDetails();


         for (int i = 0; i < v.size(); i++)
         {
         obj = (PreCloseDetails)v.get(i);
         System.out.println(obj.getPreCloseDateStamp());
         System.out.println(obj.getPreCloseIsValid());
         System.out.println(obj.getPreCloseReason());
         System.out.println(obj.getWorkOrderJbStatId());
         System.out.println(obj.getPreCloseLogDate());
         System.out.println(obj.getWorkOrderJbStatSno());
         System.out.println(obj.getJbSno());
         }*/
        //System.out.println("HashMap :"+objPreCloseDetails.getPreCloseReason());


        //addNewDummyWorkOrder()//
        /*objDWODetails.setCustId(13022);
         objDWODetails.setDWOGnrlName("13020JBGNRLNAME2");
         objDWODetails.setDWOJbDrwgNo("DRWGNO4");
         objDWODetails.setDWOJbMatlTyp("MATLTYP3");
         objDWODetails.setDWOJbName("13020JBNAME3");
         objDWODetails.setDWOJbRvsnNo("RVSN3");
         objDWODetails.setDWONo("DWONO1");
         boolean result = objWorkOrderDetailsManager.addNewDummyWorkOrder(objDWODetails);
         System.out.println("RESULT :"+result);*/


        //updateDummyWorkOrder()//
        /*objDWODetails.setDWOId(21);
         //objDWODetails.setCustId(13022);
          objDWODetails.setDWOGnrlName("13020JBGNRLNAME");
          objDWODetails.setDWOJbDrwgNo("DRWGNO");
          objDWODetails.setDWOJbMatlTyp("MATLTYP");
          objDWODetails.setDWOJbName("13020JBNAME");
          objDWODetails.setDWOJbRvsnNo("RVSN");
          //objDWODetails.setDWONo("DWONO");
           boolean result = objWorkOrderDetailsManager.updateDummyWorkOrder(objDWODetails);
           System.out.println("RESULT :"+result);*/

        //getDummyWorkOrderDetails()//
        /*objDWODetails = objWorkOrderDetailsManager.getDummyWorkOrderDetails(21);
         System.out.println(objDWODetails.getDWOGnrlName());
         System.out.println(objDWODetails.getCustName());*/

        //deleteDummyWorkOrder()//
        /*Vector vec = new Vector();
         vec.addElement(new Integer(21));
         HashMap hm = objWorkOrderDetailsManager.deleteDummyWorkOrder(vec);*/

        //getAllDummyWorkOrderDetails()//

        /*Filter[] filters = new Filter[2];
         filters[0] = new Filter();
         filters[0].setFieldName("CUST_NAME");
         filters[0].setFieldValue("13");
         filters[0].setSpecialFunction("Starts With");

         filters[1] = new Filter();
         filters[1].setFieldName("WODMY_DATE");
         filters[1].setFieldValue("04-11-2004$20-12-2004");
         filters[1].setSpecialFunction("DateBetween");

         String sortBy = "WODMY_NO";
         boolean ascending = true;
         int startIndex = 0;
         int displayCount = 10;
         HashMap hm = objWorkOrderDetailsManager.getAllDummyWorkOrderDetails(filters,sortBy,ascending,startIndex,displayCount);
         System.out.println("HashMap Result :"+hm);*/
        //objWorkOrderDetailsManager.getPreCloseDetails(100);
        DummyWorkOrderDetails  objDummyWorkOrderDetails = new DummyWorkOrderDetails();
        objDummyWorkOrderDetails.setDmyWoNo("DUMH01");
        objDummyWorkOrderDetails.setCustId(254);
        objDummyWorkOrderDetails.setDmyWoGnrlId(2);
        objDummyWorkOrderDetails.setDmyWoJbName("dummyob");
        objDummyWorkOrderDetails.setDmyWoJbDrwgNo("dummyDrwngNo");
        objDummyWorkOrderDetails.setDmyWoJbRvsnNo("dmyro");
        objDummyWorkOrderDetails.setDmyWoJbMatlTyp("dmyMatlTyp");
        objDummyWorkOrderDetails.setStartSno(1);
        //objDummyWorkOrderDetails.setDmyWoDate(new Date("04-nov-04"));
        objDummyWorkOrderDetails.setJbQty(2);
        objDummyWorkOrderDetails.setDmyStartOpn(1);
        objDummyWorkOrderDetails.setDmyEndOpn(10);
        objDummyWorkOrderDetails.setDmyOpnName("Drilling");
        objDummyWorkOrderDetails.setDmyOpnGpId(121);
        //objWorkOrderDetailsManager.addNewDummyWorkOrder(objDummyWorkOrderDetails);
        //objWorkOrderDetailsManager.test();


    }
}
/***
$Log: WorkOrderDetailsManager.java,v $
Revision 1.136  2009/03/23 11:15:43  ppalaniappan
BuildConfig.DMODE is remove at printstacktrace.

Revision 1.135  2009/03/23 11:00:03  ppalaniappan
AddNewWorkOrder comment is removed by fetching old version.

Revision 1.132  2005/12/29 12:16:33  kduraisamy
to give tag build.I have reverted the version.

Revision 1.130  2005/12/08 09:08:38  kduraisamy
ORDER BY CLAUSE ADDED.

Revision 1.129  2005/09/26 06:25:08  kduraisamy
getWorkOrderDet() added for workorder list.

Revision 1.128  2005/09/21 07:25:20  vkrishnamoorthy
WOList submenu added.

Revision 1.127  2005/09/21 07:14:49  kduraisamy
Wo List sub menu added.

Revision 1.126  2005/09/20 12:41:30  kduraisamy
Wo List sub menu added.

Revision 1.125  2005/09/20 10:09:10  vkrishnamoorthy
WOList submenu added.

Revision 1.124  2005/09/20 09:10:26  kduraisamy
Wo List sub menu added.

Revision 1.123  2005/09/20 07:38:40  kduraisamy
Wo List sub menu added.

Revision 1.122  2005/09/15 07:36:10  kduraisamy
rs.getStatement().close() added in Filters.

Revision 1.121  2005/09/10 13:18:42  kduraisamy
order by clause added.

Revision 1.120  2005/08/20 07:22:25  kduraisamy
dummy wo date current date added.

Revision 1.119  2005/08/13 09:01:47  vkrishnamoorthy
WOJBOPN_INCENTIVE ADDED IN DUMMY WO ADD.

Revision 1.118  2005/08/13 06:11:27  kduraisamy
isWoModify().commented.

Revision 1.117  2005/08/09 10:02:05  kduraisamy
statuses added in wo view.

Revision 1.116  2005/08/09 05:28:58  vkrishnamoorthy
WO_DATE included.

Revision 1.115  2005/08/09 04:54:31  kduraisamy
statuses added in wo view.

Revision 1.114  2005/08/09 04:46:52  kduraisamy
statuses added in wo view.

Revision 1.113  2005/08/03 05:23:13  vkrishnamoorthy
WOJB_ID set into hashTable.

Revision 1.112  2005/08/02 07:19:38  kduraisamy
WOJBOPN_INCENTIVE ADDED.

Revision 1.111  2005/08/01 04:35:40  kduraisamy
wo order modification changed.

Revision 1.110  2005/07/25 06:36:57  kduraisamy
WOJB DCDATE ADDED.

Revision 1.109  2005/07/23 11:55:14  vkrishnamoorthy
WoDCNo error corrected.

Revision 1.108  2005/07/23 11:15:11  kduraisamy
CUSTOMER WORK ORDER DC NO ADDED.

Revision 1.107  2005/07/20 09:29:04  kduraisamy
jbOpnIncentive removed in get.

Revision 1.106  2005/07/15 07:40:37  kduraisamy
initial commit.

Revision 1.105  2005/07/12 11:48:09  kduraisamy
imports organized

Revision 1.104  2005/07/07 06:56:38  kduraisamy
JBOPN_INCENTIVE ADDED IN DUMMYWORKORDER.

Revision 1.103  2005/06/29 12:43:58  smurugesan
if con ! = null added in finally

Revision 1.102  2005/06/10 12:39:19  vkrishnamoorthy
resultSet properly closed.

Revision 1.101  2005/06/10 11:43:01  kduraisamy
resultSet properly Closed.

Revision 1.100  2005/05/27 08:48:39  kduraisamy
con != null checking added.

Revision 1.99  2005/05/18 08:37:43  kduraisamy
commitTransaction() and rollbackTransaction() added.

Revision 1.98  2005/05/16 18:36:25  kduraisamy
specific throws addded for mysql.

Revision 1.97  2005/05/06 09:23:32  kduraisamy
if(con!=null) added in finally..

Revision 1.96  2005/04/19 13:13:33  kduraisamy
resultset properly closed.

Revision 1.95  2005/04/18 12:28:53  kduraisamy
executeStatement() return type changed.

Revision 1.94  2005/04/13 10:01:28  vkrishnamoorthy
trim() added.

Revision 1.93  2005/04/09 05:50:35  kduraisamy
BuildConfig.DMODE ADDED befor SYSTEM.OUT.PRINTLN().

Revision 1.92  2005/04/08 10:08:10  kduraisamy
CUST_MSTR UPDATE FROM WORK ORDER ADD. INCLUDED.LAST ORDER PLACED DATE AND LAST WORK ORDER REFERENCE.

Revision 1.91  2005/04/08 07:30:51  kduraisamy
BuildConfig.DMODE ADDED befor SYSTEM.OUT.PRINTLN().

Revision 1.90  2005/04/07 09:21:02  kduraisamy
MODE IS CHANGED TO DMODE.

Revision 1.89  2005/04/07 07:37:05  kduraisamy
if(BuildConfig.DMODE) ADDED. AND LEVEL INCLUDED PROPERLY.

Revision 1.88  2005/03/30 08:31:19  kduraisamy
excecuteRownumStatement() called for filters.

Revision 1.87  2005/03/10 07:28:44  kduraisamy
getDate is changed to getTimestamp().

Revision 1.86  2005/03/04 07:02:22  kduraisamy
DBConnection Changes made.

Revision 1.85  2005/02/28 08:58:32  kduraisamy
printstacktrace() added in addNewWorkOrder().

Revision 1.84  2005/02/23 11:00:20  kduraisamy
WOJBOPN_OPN_GP_ID IS CHANGED TO OPN_GP_ID.

Revision 1.83  2005/02/15 06:56:08  kduraisamy
con.rollbackTransaction() added in addNewDummyWo().

Revision 1.82  2005/02/14 11:10:05  kduraisamy
unwanted cast removed.

Revision 1.81  2005/02/14 05:31:13  kduraisamy
commented code removed.

Revision 1.80  2005/02/11 18:45:26  kduraisamy
addPreCloseLogDetails() modified.

Revision 1.79  2005/02/11 06:55:57  kduraisamy
unused variables removed.

Revision 1.78  2005/02/08 10:02:16  sponnusamy
Depricated methods removed in main.

Revision 1.77  2005/02/08 09:02:04  kduraisamy
unwanted exception throw removed.

Revision 1.76  2005/02/08 05:28:16  kduraisamy
addRESULT = true .included in addDmyWo().

Revision 1.75  2005/02/07 12:12:51  sponnusamy
AddRESULT is made true.

Revision 1.74  2005/02/07 09:54:51  kduraisamy
SYSTIMESTAMP INCLUDED IN WO_DATE OF WO_JB_HSTRY.

Revision 1.73  2005/02/07 09:34:28  kduraisamy
SYSTIMESTAMP ADDED FOR JB_CREATED_DATE FOR DUMMY WORK ORDER.

Revision 1.72  2005/02/07 09:18:15  kduraisamy
SYSTIMESTAMP ADDED FOR WO_DATE.

Revision 1.71  2005/02/07 09:06:51  sponnusamy
Hashmap Key added for dummy workorder
filter.

Revision 1.70  2005/02/07 07:01:04  kduraisamy
FILTER METHOD ADDED FOR DUMMY WO.

Revision 1.69  2005/02/07 06:21:42  kduraisamy
addNewDummyWo() modified.

Revision 1.68  2005/02/02 14:47:44  kduraisamy
WOJBSTAT SNO ADDED IN OBJECT.

Revision 1.67  2005/02/02 14:38:54  kduraisamy
FIELD PRECLOSELOGID ADDED IN VALUE OBJECT.

Revision 1.66  2005/02/02 13:56:40  kduraisamy
getPrecloseDetails() modified.

Revision 1.65  2005/02/02 06:52:53  kduraisamy
RETURN TYPE CHANGED TO HASHMAP.

Revision 1.64  2005/01/31 15:12:07  kduraisamy
prodEntered Opns is taken from radl.

Revision 1.63  2005/01/29 06:24:18  kduraisamy
SortString() method used.

Revision 1.62  2005/01/27 14:48:27  kduraisamy
if(BuildConfig.DMODE)
{
}
properly inclued inside the catch block.

Revision 1.61  2005/01/20 06:43:17  kduraisamy
getWorkOrderDetails() modified.jobQty and jobQtyStartSno set.

Revision 1.60  2005/01/17 15:10:10  kduraisamy
EXCEPTIO THROW ADDED IN ISWOMODIFY() .

Revision 1.59  2005/01/17 10:06:38  kduraisamy
isWoModify() added.

Revision 1.58  2005/01/13 07:01:35  kduraisamy
method getForAddNewPreCloseLog is taken from previous version because of Status 'U'.

Revision 1.57  2005/01/10 12:43:00  kduraisamy
getForAddNewPrecloseLog() errors corrected.

Revision 1.56  2005/01/10 12:23:52  kduraisamy
getForNewPrecloseLogDetails() modified.

Revision 1.55  2004/12/31 17:33:43  kduraisamy
throw Exception added for Record not found situation in closeWorkOrder().

Revision 1.54  2004/12/31 10:08:35  kduraisamy
moving of wo tables to closed wo tables commented.

Revision 1.53  2004/12/29 11:46:37  kduraisamy
moving of wo tables to closed wo tables performed..

Revision 1.51  2004/12/23 04:44:32  sduraisamy
getWoOpenOpnDetails changed to getWoOpnDetails in getPreCloseDetails(int)

Revision 1.50  2004/12/22 10:14:58  kduraisamy
RECORD NOT FOUND error throws added.

Revision 1.49  2004/12/22 08:03:15  kduraisamy
gpCde changed to gpId.

Revision 1.48  2004/12/22 06:39:41  sponnusamy
nextElement is changed as nextToken.

Revision 1.47  2004/12/22 06:19:39  sduraisamy
getForAddNewPreCloseLog() modified to find incompleted operations

Revision 1.46  2004/12/21 12:52:36  kduraisamy
setOpnGpId included instead of setOpnGpCde()

Revision 1.45  2004/12/21 07:25:46  sduraisamy
setWOjobOpenOpn() modified to setWOJobOpn() in getPreCloseDetails(int)

Revision 1.44  2004/12/20 05:36:55  kduraisamy
new field WOJBOPN_ID is taken from result set.

Revision 1.43  2004/12/18 10:40:43  kduraisamy
additional field added in JobQtyDetails and new field is used.

Revision 1.42  2004/12/18 06:46:30  kduraisamy
error corrected in getJobNameByWO().

Revision 1.41  2004/12/17 13:30:46  kduraisamy
getForAddNewPreCloseLog() modified.

Revision 1.40  2004/12/17 11:28:54  sduraisamy
getJobNameByWorkOrder() changed to  include DwgNo,RvsnNo,MatlTyp

Revision 1.39  2004/12/17 10:59:56  kduraisamy
operations taken from Radl table also.code added for that.

Revision 1.38  2004/12/17 10:41:37  kduraisamy
getForPreCloseDetails() changed.

Revision 1.37  2004/12/17 09:14:01  sduraisamy
PreCLose Filter Method Modified to include PreCloseLogDate

Revision 1.36  2004/12/16 10:39:32  sduraisamy
DummyWorkOrder Filter Method modified to include Date and IsValid

Revision 1.35  2004/12/16 09:47:23  kduraisamy
unwanted comment removed.

Revision 1.34  2004/12/16 09:45:18  kduraisamy
main changed.

Revision 1.33  2004/12/16 09:13:18  sduraisamy
WorkOrder Filter modified to remove Job Details

Revision 1.32  2004/12/16 04:39:40  kduraisamy
unwanted comment removed.

Revision 1.31  2004/12/15 11:21:35  kduraisamy
format organised.

Revision 1.30  2004/12/15 10:28:44  sduraisamy
Exceptions included for getDummyWorkOrder()

Revision 1.29  2004/12/14 14:13:40  kduraisamy
main changed.

Revision 1.28  2004/12/14 13:54:40  sduraisamy
Values assigned for hm_Result in deleteDummyWorkOrder()

Revision 1.27  2004/12/14 10:58:14  sduraisamy
ResultSets and PreparedStatements are closed in add and update WorkOrder Methods

Revision 1.26  2004/12/14 04:16:48  sduraisamy
throw new WorkOrderException() included for getJobNameByWorkOrder()

Revision 1.25  2004/12/10 11:48:25  kduraisamy
unwanted println removed..

Revision 1.24  2004/12/10 11:43:23  kduraisamy
con.commitTransaction()'s place changed..

Revision 1.23  2004/12/10 10:10:43  kduraisamy
unwanted println removed..

Revision 1.22  2004/12/10 10:02:34  kduraisamy
updateWorkOrderDetails() modified.

Revision 1.21  2004/12/10 06:59:03  kduraisamy
try catch added and con.startTransaction(),con.commitTransaction() added.

Revision 1.20  2004/12/09 13:52:53  kduraisamy
logger.info("WORKORDER UPDATE STARTS") added.

Revision 1.19  2004/12/09 07:43:36  kduraisamy
OPN_GP_CDE is changed to OPN_GP_ID

Revision 1.18  2004/12/09 07:07:24  sduraisamy
getPreCloseDetails() , getAllPreCloseDetails(),makePreCloseValid() and makePreCloseInValid() added

Revision 1.17  2004/12/09 05:54:46  kduraisamy
Log added.

***/