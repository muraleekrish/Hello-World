/*
 * Created on Dec 16, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.businessimplementation.production;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
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
 * @author kduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PopDetailsManager
{
    DBConnection con = null;
    static Logger logger = Logger.getLogger(PopDetailsManager.class);

    public PopDetailsManager()
    {
        logger.addAppender(LoggerOutput.getAppender());
        logger.setLevel(ProdacsConfig.LOGGER_LEVEL);
    }
    public HashMap addNewPopDetails(PopDetails[] objPopDetailsList) throws ProductionException, SQLException
    {
        ProductionDetailsManager objProductionDetailsManager = new ProductionDetailsManager();
        Hashtable ht_Pop_Add = new Hashtable();
        Hashtable ht_EmpHrs_Add = new Hashtable();
        PreparedStatement ps = null;
        ResultSet rs_Pop_Id = null;
        HashMap hm_Result = new HashMap();
        Vector vec_Success = new Vector();
        Vector vec_EmpFail = new Vector();
        Vector vecDutyFail = new Vector();
        float dtyHrs = 0;
        float otHrs = 0;
        float dtySlryHrs = 0;
        float otSlryHrs = 0;
        int empId = 0;
        int empTypId = 0;
        PreparedStatement psEmpTypCheck = null;
        ResultSet rsEmpTypCheck = null;
        Hashtable ht = new Hashtable();
        //String empType = "";
        int result = 0;
        int popId = 0;
        if(objPopDetailsList.length == 0)
        {

            logger.error("PAY OUTSIDE PRODUCTION DETAILS OBJ NULL");
            throw new ProductionException("POPMEC001","PAY OUTSIDE PRODUCTION DETAILS OBJ NULL","");
        }

        try
        {
            con = DBConnectionFactory.getConnection();
            con.startTransaction();

            for(int i = 0;i<objPopDetailsList.length;i++)
            {
                //ht_Pop_Add.put("MC_CDE",objPopDetailsList[i].getMcCode());
                ht_Pop_Add.put("POP_CRNT_DATE",objPopDetailsList[i].getPopCrntDate());
                ht_Pop_Add.put("SHIFT_ID",new Integer(objPopDetailsList[i].getShiftId()));
                ht_Pop_Add.put("POP_RSN",objPopDetailsList[i].getPopRsn());
                ht_Pop_Add.put("POP_UPDATE_PYRL",objPopDetailsList[i].isPopUpdatePyrl()?"1":"0");
                ht_Pop_Add.put("POP_POST_FLG",objPopDetailsList[i].isPopPostFlg()?"1":"0");
                ht_Pop_Add.put("IS_DLTD",objPopDetailsList[i].isPopIsDeleted()?"1":"0");
                ht_Pop_Add.put("POP_CREATEDBY",objPopDetailsList[i].getCreatedBy());
                result = con.executeUpdateStatement(SQLMaster.POP_DETAILS_ADD_SQL_QUERY,ht_Pop_Add);
                if(result>0)
                {
                    ps = con.executeStatement(SQLMaster.POPID_SELECT_SQL_QUERY);
                    rs_Pop_Id = ps.executeQuery();
                    if(rs_Pop_Id.next())
                    {
                        popId = rs_Pop_Id.getInt(1);
                    }
                    Vector vec_EmpHrs = objPopDetailsList[i].getPopEmpHrsDetails();
                    //totHrs = objPopDetails.getPopTotHrs();
                    boolean flag = true;
                    for(int j=0;j<vec_EmpHrs.size();j++)
                    {
                        ////start//

                        otHrs = 0;
                        otSlryHrs = 0;
                        dtyHrs = 0;
                        dtySlryHrs = 0;
                        EmployeeDtyOtDetails objEmployeeDtyOtDetails = new EmployeeDtyOtDetails();
                        objEmployeeDtyOtDetails = (EmployeeDtyOtDetails)vec_EmpHrs.get(j);

                        empId = objEmployeeDtyOtDetails.getEmpId();
                        //empType = objEmployeeDtyOtDetails.getEmpType();
                        dtyHrs = objEmployeeDtyOtDetails.getDutyHrs();
                        otHrs = objEmployeeDtyOtDetails.getOtHrs();


                       /* if(!empType.equalsIgnoreCase("supervisor"))
                        {

                            dtySlryHrs = dtyHrs;
                            otSlryHrs = otHrs * 2;
                        }
                        else
                        {
                            dtySlryHrs = dtyHrs;
                        }
                        if(BuildConfig.DMODE)
                        {
	                        System.out.println("DTYSLRYHRS :"+dtySlryHrs);
	                        System.out.println("OTSLRYHRS :"+otSlryHrs);
	                        System.out.println("EMPID :"+empId);
	                        System.out.println(objPopDetailsList[i].getPopCrntDate());
                        }*/
                        empTypId = objEmployeeDtyOtDetails.getEmpTypdId();
                        ht.put("EMP_TYP_ID",new Integer(empTypId));
                        psEmpTypCheck = con.executeStatement(SQLMaster.CHECK_EMP_TYPE_SQL_QUERY,ht);
                        rsEmpTypCheck = psEmpTypCheck.executeQuery();
                        int check = 0;
                        int val = 0;
                        if(rsEmpTypCheck.next())
                        {
                            val = rsEmpTypCheck.getInt(1);
                        }
                        if(BuildConfig.DMODE)
                        {
                            System.out.println("empTypId"+empTypId);
                            System.out.println("val"+val);
                            System.out.println("DtyHrs"+dtyHrs);
                        }
                        
                        if(val==0)
                            if(dtyHrs > 0)
                                if(!objProductionDetailsManager.checkDutyEntry(objPopDetailsList[i].getPopCrntDate(), objPopDetailsList[i].getShiftId(), empId, con))
                                {
                                    con.rollBackTransaction();
                                    flag = false;
                                    HashMap hm = new HashMap();
                                    hm.put(objEmployeeDtyOtDetails.getEmpName(), objPopDetailsList[i]);
                                    vecDutyFail.addElement(hm);
                                    break;
                                }
                        if(val == 0)
                         check = objProductionDetailsManager.checkAvailableEmpHrs(empId,objPopDetailsList[i].getPopCrntDate(),objPopDetailsList[i].getShiftId(),0,0,0,dtyHrs+otHrs,con);
                        if(check==0)
                        {

                            ht_EmpHrs_Add.put("POP_ID",new Integer(popId));
                            ht_EmpHrs_Add.put("EMP_ID",new Integer(empId));
                            ht_EmpHrs_Add.put("DT_HRS",new Float(dtyHrs));
                            ht_EmpHrs_Add.put("OT_HRS",new Float(otHrs));
                            ht_EmpHrs_Add.put("DT_SLRY_HRS",new Float(dtySlryHrs));
                            ht_EmpHrs_Add.put("OT_SLRY_HRS",new Float(otSlryHrs));
                            ht_EmpHrs_Add.put("POP_MODIFYCOUNT",new Integer(0));

                            result = con.executeUpdateStatement(SQLMaster.POP_EMPHRS_ADD_SQL_QUERY,ht_EmpHrs_Add);
                            if(result>0)
                            {
                                if(BuildConfig.DMODE)
                                    System.out.println("Employee Hrs Details Added For Pay-Outside-Production");
                                //con.commitTransaction();

                            }
                        }
                        else
                        {
                            flag = false;
                            con.rollBackTransaction();
                            HashMap hm = new HashMap();
                            hm.put(objEmployeeDtyOtDetails.getEmpName(), objPopDetailsList[i]);
                            vec_EmpFail.addElement(hm);
                            break;
                        }

                    }
                    if(!flag)
                    {
                        continue;
                    }
                    rs_Pop_Id.close();
                    ps.close();

                }
                con.commitTransaction();
                vec_Success.addElement(new Integer(popId));
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
                if(sqle.toString().indexOf("PK_POPID")>=0)
                {
                    throw new ProductionException("POPMEC002","PAY OUTSIDE PRODUCTION ID IS UNIQUE.UNIQUE CONSTRAINT VIOLATED",sqle.toString());
                }
                else if (sqle.toString().indexOf("FK_POP_MCCDE") >= 0)
                {
                    throw new ProductionException("POPMEC003","PARENT KEY MACHINE CODE NOT FOUND",sqle.toString());
                }
                else if (sqle.toString().indexOf("FK_POP_SHIFTID") >= 0)
                {
                    throw new ProductionException("POPMEC004","PARENT KEY SHIFT ID NOT FOUND",sqle.toString());
                }
                else
                {
                    throw new ProductionException("POPMEC000","GENERAL SQL ERROR",sqle.toString());
                }
            }
            if(BuildConfig.DBASE==BuildConfig.MYSQL)
            {
                if(sqle.toString().indexOf("unique")>=0)
                {
                    throw new ProductionException("POPMEC002","PAY OUTSIDE PRODUCTION ID IS UNIQUE.UNIQUE CONSTRAINT VIOLATED",sqle.toString());
                }
                else if (sqle.toString().indexOf("foreign key constraint") >= 0)
                {
                    throw new ProductionException("POPMEC003","PARENT KEY MACHINE CODE OR SHIFT ID NOT FOUND",sqle.toString());
                }
                else
                {
                    throw new ProductionException("POPMEC000","GENERAL SQL ERROR",sqle.toString());
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

        logger.info("POP ADD ENDS");

        hm_Result.put("Success",vec_Success);
        hm_Result.put("EmpFailure",vec_EmpFail);
        hm_Result.put("DutyFailure",vecDutyFail);

        return hm_Result;
    }
    public boolean updatePopDetails(PopDetails objPopDetails)throws ProductionException,SQLException
    {
        boolean updateRESULT = false;
        ProductionDetailsManager objProductionDetailsManager = new ProductionDetailsManager();
        Hashtable ht_Pop_Update = new Hashtable();
        Hashtable ht_EmpHrs_Update = new Hashtable();
        Hashtable ht_EmpHrs_Del = new Hashtable();
        float dtyHrs = 0;
        float otHrs = 0;
        float dtySlryHrs = 0;
        float otSlryHrs = 0;
        int empId = 0;
        int empTypId = 0;
        PreparedStatement psEmpTypCheck = null;
        ResultSet rsEmpTypCheck = null;
        Hashtable ht = new Hashtable();
        //String empType = "";
        int result = 0;
        con = DBConnectionFactory.getConnection();
        con.startTransaction();
        //

        ht_EmpHrs_Del.put("POP_ID",new Integer(objPopDetails.getPopId()));
        try
        {
            // to store Emp Hours entries into Log..
            result = con.executeUpdateStatement(SQLMaster.LOG_POP_EMPHRS_ADD_SQL_QUERY,ht_EmpHrs_Del);
            if(result>0)
            {

                result = con.executeUpdateStatement(SQLMaster.POP_EMP_HRS_DELETE_SQL_QUERY,ht_EmpHrs_Del);
                if(result>0)
                {
                    if(BuildConfig.DMODE)
                        System.out.println("POP EMP HRS DELETED");
                }
            }

            ht_Pop_Update.put("POP_ID",new Integer(objPopDetails.getPopId()));
            //ht_Pop_Update.put("MC_CDE",objPopDetails.getMcCode());
            ht_Pop_Update.put("POP_CRNT_DATE",objPopDetails.getPopCrntDate());
            ht_Pop_Update.put("SHIFT_ID",new Integer(objPopDetails.getShiftId()));
            ht_Pop_Update.put("POP_RSN",objPopDetails.getPopRsn());
            ht_Pop_Update.put("POP_UPDATE_PYRL",objPopDetails.isPopUpdatePyrl()?"1":"0");
            ht_Pop_Update.put("POP_POST_FLG",objPopDetails.isPopPostFlg()?"1":"0");
            ht_Pop_Update.put("IS_DLTD",objPopDetails.isPopIsDeleted()?"1":"0");
            ht_Pop_Update.put("POP_CREATEDBY",objPopDetails.getModifiedBy());

            //to store Prod Entries to Log..
            con.executeUpdateStatement(SQLMaster.POP_DETAILS_LOG_ADD_SQL_QUERY,ht_Pop_Update);

            result = con.executeUpdateStatement(SQLMaster.POP_DETAILS_UPDATE_SQL_QUERY,ht_Pop_Update);
            if(result>0)
            {
                //   to select current modify count..

                PreparedStatement ps_ModifyCnt_Sel = con.executeStatement(SQLMaster.POP_MODIFY_COUNT_SELECT,ht_Pop_Update);
                ResultSet rs_ModifyCnt_Sel = ps_ModifyCnt_Sel.executeQuery();
                int modifyCount = 0;
                if(rs_ModifyCnt_Sel.next())
                {
                    modifyCount = rs_ModifyCnt_Sel.getInt("POP_MODIFYCOUNT");
                }
                ht_Pop_Update.put("POP_MODIFYCOUNT",new Integer(modifyCount));

                ht_EmpHrs_Update.put("POP_MODIFYCOUNT",new Integer(modifyCount));



                Vector vec_EmpHrs = objPopDetails.getPopEmpHrsDetails();
                //totHrs = objPopDetails.getPopTotHrs();
                for(int i=0;i<vec_EmpHrs.size();i++)
                {
                    ////start//

                    otHrs = 0;
                    otSlryHrs = 0;
                    dtyHrs = 0;
                    dtySlryHrs = 0;
                    EmployeeDtyOtDetails objEmployeeDtyOtDetails = new EmployeeDtyOtDetails();
                    objEmployeeDtyOtDetails = (EmployeeDtyOtDetails)vec_EmpHrs.get(i);

                    empId = objEmployeeDtyOtDetails.getEmpId();
                    //empType = objEmployeeDtyOtDetails.getEmpType();
                    dtyHrs = objEmployeeDtyOtDetails.getDutyHrs();
                    otHrs = objEmployeeDtyOtDetails.getOtHrs();


                   /* if(!empType.equalsIgnoreCase("supervisor"))
                    {

                        dtySlryHrs = dtyHrs;
                        otSlryHrs = otHrs * 2;
                    }
                    else
                    {
                        dtySlryHrs = dtyHrs;
                    }
                    System.out.println("DTYSLRYHRS :"+dtySlryHrs);
                    System.out.println("OTSLRYHRS :"+otSlryHrs);
                    System.out.println("EMPID :"+empId);
                    System.out.println(objPopDetails.getPopCrntDate());
                    
                    */
                    empTypId = objEmployeeDtyOtDetails.getEmpTypdId();
                    ht.put("EMP_TYP_ID",new Integer(empTypId));
                    psEmpTypCheck = con.executeStatement(SQLMaster.CHECK_EMP_TYPE_SQL_QUERY,ht);
                    rsEmpTypCheck = psEmpTypCheck.executeQuery();
                    int check = 0;
                    int val = 0;
                    if(rsEmpTypCheck.next())
                    {
                        val = rsEmpTypCheck.getInt(1);
                    }
                    if(val == 0)
                        if(dtyHrs >0)
                        if(!objProductionDetailsManager.checkDutyEntry(objPopDetails.getPopCrntDate(), objPopDetails.getShiftId(), empId, con))
                        {
                            con.rollBackTransaction();
                            throw new ProductionException("WOEC00","EMPLOYEE DUTY NOT POSSIBLE FOR MULTIPLE SHIFTS : "+objEmployeeDtyOtDetails.getEmpName(),"");
                        }
                    
                    
                    if(val == 0)
                     check = objProductionDetailsManager.checkAvailableEmpHrs(empId,objPopDetails.getPopCrntDate(),objPopDetails.getShiftId(),0,0,0,dtyHrs+otHrs,con);
                    if(check==0)
                    {

                        ht_EmpHrs_Update.put("POP_ID",new Integer(objPopDetails.getPopId()));
                        ht_EmpHrs_Update.put("EMP_ID",new Integer(empId));
                        ht_EmpHrs_Update.put("DT_HRS",new Float(dtyHrs));
                        ht_EmpHrs_Update.put("OT_HRS",new Float(otHrs));
                        ht_EmpHrs_Update.put("DT_SLRY_HRS",new Float(dtySlryHrs));
                        ht_EmpHrs_Update.put("OT_SLRY_HRS",new Float(otSlryHrs));


                        result = con.executeUpdateStatement(SQLMaster.POP_EMPHRS_ADD_SQL_QUERY,ht_EmpHrs_Update);
                        if(result>0)
                        {
                            if(BuildConfig.DMODE)
                                System.out.println("Employee Hrs Details updated For Pay-Outside-Production");


                        }
                    }
                    else
                    {
                        con.rollBackTransaction();
                        throw new ProductionException("POPMEC005","EMPLOYEE HRS GREATER THAN AVAILABLE SHIFT HRS : "+objEmployeeDtyOtDetails.getEmpName(),"");
                    }

                }

            }
            con.commitTransaction();
            updateRESULT = true;

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
                if(sqle.toString().indexOf("PK_POPID")>=0)
                {
                    throw new ProductionException("POPMEC002","PAY OUTSIDE PRODUCTION ID IS UNIQUE.UNIQUE CONSTRAINT VIOLATED",sqle.toString());
                }
                else if (sqle.toString().indexOf("FK_POP_MCCDE") >= 0)
                {
                    throw new ProductionException("POPMEC003","PARENT KEY MACHINE CODE NOT FOUND",sqle.toString());
                }
                else if (sqle.toString().indexOf("FK_POP_SHIFTID") >= 0)
                {
                    throw new ProductionException("POPMEC004","PARENT KEY SHIFT ID NOT FOUND",sqle.toString());
                }
                else
                {
                    throw new ProductionException("POPMEC000","GENERAL SQL ERROR",sqle.toString());
                }
            }
            if(BuildConfig.DBASE==BuildConfig.MYSQL)
            {
                if(sqle.toString().indexOf("Duplicate entry")>=0)
                {
                    throw new ProductionException("POPMEC002","PAY OUTSIDE PRODUCTION ID IS UNIQUE.UNIQUE CONSTRAINT VIOLATED",sqle.toString());
                }
                else if (sqle.toString().indexOf("foreign key constraint") >= 0)
                {
                    throw new ProductionException("POPMEC003","PARENT KEY MACHINE CODE OR SHIFT ID NOT FOUND",sqle.toString());
                }
                else
                {
                    throw new ProductionException("POPMEC000","GENERAL SQL ERROR",sqle.toString());
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

        logger.info("POP ADD ENDS");

        return updateRESULT;
    }
    public boolean isModifyForPop(int popId) throws ProductionException, SQLException
    {
        boolean retVal = false;
        DBConnection con = null;
        Hashtable ht_Prod = new Hashtable();
        PreparedStatement ps = null;
        int cnt = 0;
        try
        {
            con = DBConnectionFactory.getConnection();
            ht_Prod.put("POP_ID",new Integer(popId));
            ps = con.executeStatement(SQLMaster.CHECK_POP_FOR_MODIFY_SQL_QUERY,ht_Prod);
            ResultSet rs_Prod = ps.executeQuery();
            if(rs_Prod.next())
            {
                cnt = rs_Prod.getInt(1);
                if(cnt==0)
                {
                    throw new ProductionException("","POSTED PRODUCTION CANNOT BE MODIFIED","");
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
            throw new ProductionException("POPMEC000","GENERAL SQL ERROR",sqle.toString());
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

    public PopDetails getPopDetails(int popId) throws ProductionException,SQLException
    {
        PopDetails objPopDetails = new PopDetails();
        Hashtable ht_PopDet_Get = new Hashtable();
        Vector vec_Emp_Det = new Vector();
        PreparedStatement ps_PopDet_Get = null;
        PreparedStatement ps_PopEmp_Det = null;
        if(BuildConfig.DMODE)
        {
            logger.info("GET POP DETAILS STARTS");
        }
        con = DBConnectionFactory.getConnection();
        ht_PopDet_Get.put("POP_ID", new Integer(popId));
        try
        {
            ps_PopDet_Get = con.executeStatement(SQLMaster.GET_POP_DETAILS_SQL_QUERY,ht_PopDet_Get);
            ResultSet rs_PopDet_Get = ps_PopDet_Get.executeQuery();

            if(rs_PopDet_Get.next())
            {
                //set Pop Details //
                objPopDetails.setPopId(popId);
                //objPopDetails.setMcCode(rs_PopDet_Get.getString("MC_CDE"));
                objPopDetails.setPopCrntDate(rs_PopDet_Get.getTimestamp("POP_CRNT_DATE"));
                objPopDetails.setShiftId(rs_PopDet_Get.getInt("SHIFT_ID"));
                objPopDetails.setShiftName(rs_PopDet_Get.getString("SHIFT_NAME"));
                objPopDetails.setPopRsn(rs_PopDet_Get.getString("POP_RSN"));
                objPopDetails.setPopUpdatePyrl(rs_PopDet_Get.getString("POP_UPDATE_PYRL").equals("1"));
                objPopDetails.setPopPostFlg(rs_PopDet_Get.getString("POP_POST_FLG").equals("1"));
                objPopDetails.setPopIsDeleted(rs_PopDet_Get.getString("IS_DLTD").equals("1"));
                objPopDetails.setModifyCount(rs_PopDet_Get.getInt("POP_MODIFYCOUNT"));
                if(objPopDetails.getModifyCount()==0)
                objPopDetails.setCreatedBy(rs_PopDet_Get.getString("POP_CREATEDBY"));
                else
                objPopDetails.setModifiedBy(rs_PopDet_Get.getString("POP_CREATEDBY"));
                objPopDetails.setPopDateStamp(rs_PopDet_Get.getTimestamp("POP_DATESTAMP"));
                objPopDetails.setPopIsValid(rs_PopDet_Get.getInt("POP_ISVALID"));

                //get All Employee Details of the Above Pop Record
                ps_PopEmp_Det = con.executeStatement(SQLMaster.GET_POP_EMP_DETAILS_SQL_QUERY,ht_PopDet_Get);
                ResultSet rs_PopEmp_Det = ps_PopEmp_Det.executeQuery();

                while(rs_PopEmp_Det.next())
                {
                    EmployeeDtyOtDetails objEmployeeDtyOtDetails = new EmployeeDtyOtDetails();

                    objEmployeeDtyOtDetails.setEmpId(rs_PopEmp_Det.getInt("EMP_ID"));
                    objEmployeeDtyOtDetails.setEmpName(rs_PopEmp_Det.getString("EMP_NAME"));
                    objEmployeeDtyOtDetails.setEmpCode(rs_PopEmp_Det.getString("EMP_CDE"));
                    objEmployeeDtyOtDetails.setEmpTypdId(rs_PopEmp_Det.getInt("EMP_TYP_ID"));
                    objEmployeeDtyOtDetails.setEmpType(rs_PopEmp_Det.getString("EMP_TYP_NAME"));
                    objEmployeeDtyOtDetails.setDutyHrs(rs_PopEmp_Det.getFloat("DT_HRS"));
                    objEmployeeDtyOtDetails.setOtHrs(rs_PopEmp_Det.getFloat("OT_HRS"));
                    objEmployeeDtyOtDetails.setDutySlryHrs(rs_PopEmp_Det.getFloat("DT_SLRY_HRS"));
                    objEmployeeDtyOtDetails.setOtSlryHrs(rs_PopEmp_Det.getFloat("OT_SLRY_HRS"));

                    //Add Each Employee Details into Vector
                    vec_Emp_Det.addElement(objEmployeeDtyOtDetails);


                }
                rs_PopEmp_Det.close();
                ps_PopEmp_Det.close();
                //set Employee Details Vector into Pop Object
                objPopDetails.setPopEmpHrsDetails(vec_Emp_Det);
            }
            else
            {
                throw new ProductionException("POPMEC000","RECORD NOT FOUND","");
            }
            rs_PopDet_Get.close();
            ps_PopDet_Get.close();
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
                logger.info("EXCEPTION WHILE SELECTING PAY OUTSIDE PRODUCTION DETAILS");
            }
            throw new ProductionException("POPMEC000","GENERAL SQL ERROR",sqle.toString());
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

        logger.info("GET POP DETAILS ENDS");

        return objPopDetails;

    }
    public HashMap getAllPopDetails(Filter[] filters,String sortBy,boolean ascending,int startIndex,int displayCount) throws ProductionException,SQLException
    {
        HashMap hm_Result = new HashMap();
        Vector Vec_Pop_Det = new Vector();

        logger.info("GET ALL POP DETAILS FILTER STARTS");

        if((filters == null)||( sortBy == null))
        {
            logger.error("FILTER VALUES ARE NULL");

            throw new ProductionException("PREC022","FILTER VALUES ARE NULL","");
        }

        con = DBConnectionFactory.getConnection();

        int tot_Rec_Cnt = 0;

        try
        {

            //filters and tableName are passed to the function and receives Total Record Count
            tot_Rec_Cnt = con.getRowCountWithComplexFilters(filters, SQLMaster.GET_ALL_POP_DETAILS_FILTER_QUERY);

            //Finding end index for the query
            int eIndex = startIndex + displayCount;

            // filters,tablename,startindex,endindex,sorting column name and sortorder are passed to the function and  query will be received
            String Query = con.buildDynamicQuery(filters,startIndex,eIndex,sortBy,ascending,SQLMaster.GET_ALL_POP_DETAILS_FILTER_QUERY);

            if(BuildConfig.DMODE)
            {
                // total records and query
                System.out.println("TOTAL NUMBER OF RECORDS : " + tot_Rec_Cnt);
                System.out.println("QUERY : " + Query);
            }

            logger.info("Total Records : " + tot_Rec_Cnt);
            logger.info("Query : " + Query);


            ResultSet rs_PopDetails_Get =  con.executeRownumStatement(Query);
            while(rs_PopDetails_Get.next())
            {
                PopDetails objPopDetails = new PopDetails();

                objPopDetails.setPopId(rs_PopDetails_Get.getInt("POP_ID"));
                //objPopDetails.setMcCode(rs_PopDetails_Get.getString("MC_CDE"));
                objPopDetails.setPopCrntDate(rs_PopDetails_Get.getTimestamp("POP_CRNT_DATE"));
                objPopDetails.setShiftId(rs_PopDetails_Get.getInt("SHIFT_ID"));
                objPopDetails.setShiftName(rs_PopDetails_Get.getString("SHIFT_NAME"));
                objPopDetails.setPopRsn(rs_PopDetails_Get.getString("POP_RSN"));
                objPopDetails.setPopIsValid(rs_PopDetails_Get.getInt("POP_ISVALID"));
                objPopDetails.setPopDateStamp(rs_PopDetails_Get.getTimestamp("POP_DATESTAMP"));

                Vec_Pop_Det.addElement(objPopDetails);
            }
            hm_Result.put("TotalRecordCount",new Integer(tot_Rec_Cnt));
            hm_Result.put("PopDetails",Vec_Pop_Det);
            rs_PopDetails_Get.getStatement().close();
            rs_PopDetails_Get.close();
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }
            logger.info("GENERAL EXCEPTION");
            throw new ProductionException("PREC000","GENERAL SQL ERROR",sqle.toString());
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

        logger.info("GET ALL POP DETAILS FILTER ENDS");


        return hm_Result;
    }

    public Vector viewUnPostedPopProductionDetails(Date fromDate,Date toDate) throws ProductionException,SQLException
    {
        Vector vec_Result = new Vector();
        Vector vec_Emp = new Vector();
        Hashtable ht_UnPostedPop_Get = new Hashtable();
        Hashtable ht_UnPosted_PopEmp = new Hashtable();
        PreparedStatement ps_UnPostedPop_Get = null;
        PreparedStatement ps_UnPosted_PopEmp = null;
        int popId = 0;

        logger.info("GET UNPOSTED PAYOUTSIDE-PRODUCTION DETAILS STARTS");


        con = DBConnectionFactory.getConnection();
        ht_UnPostedPop_Get.put("FROM_DATE",fromDate);
        ht_UnPostedPop_Get.put("TO_DATE",toDate);
        try
        {
            ps_UnPostedPop_Get = con.executeStatement(SQLMaster.UNPOSTED_POP_DETAILS_SELECT_SQL_QUERY,ht_UnPostedPop_Get);
            ResultSet rs_UnPostedPop_Get = ps_UnPostedPop_Get.executeQuery();
            while(rs_UnPostedPop_Get.next())
            {
                PopDetails objPopDetails = new PopDetails();
                popId = rs_UnPostedPop_Get.getInt("POP_ID");
                objPopDetails.setPopId(popId);
                objPopDetails.setPopCrntDate(rs_UnPostedPop_Get.getTimestamp("POP_CRNT_DATE"));
                //objPopDetails.setMcCode(rs_UnPostedPop_Get.getString("MC_CDE"));
                objPopDetails.setShiftId(rs_UnPostedPop_Get.getInt("SHIFT_ID"));
                objPopDetails.setShiftName(rs_UnPostedPop_Get.getString("SHIFT_NAME"));
                objPopDetails.setPopRsn(rs_UnPostedPop_Get.getString("POP_RSN"));

                ht_UnPosted_PopEmp.put("POP_ID",new Integer(popId));
                ps_UnPosted_PopEmp = con.executeStatement(SQLMaster.UNPOSTED_POP_EMP_DETAILS_SELECT_SQL_QUERY,ht_UnPosted_PopEmp);
                ResultSet rs_UnPosted_PopEmp =  ps_UnPosted_PopEmp.executeQuery();
                while(rs_UnPosted_PopEmp.next())
                {

                    EmployeeDtyOtDetails objEmployeeDtyOtDetails = new EmployeeDtyOtDetails();

                    objEmployeeDtyOtDetails.setEmpId(rs_UnPosted_PopEmp.getInt("EMP_ID"));
                    objEmployeeDtyOtDetails.setEmpName(rs_UnPosted_PopEmp.getString("EMP_NAME"));
                    objEmployeeDtyOtDetails.setEmpType(rs_UnPosted_PopEmp.getString("EMP_TYP_NAME"));
                    objEmployeeDtyOtDetails.setDutyHrs(rs_UnPosted_PopEmp.getFloat("DT_HRS"));
                    objEmployeeDtyOtDetails.setOtHrs(rs_UnPosted_PopEmp.getFloat("OT_HRS"));

                    vec_Emp.addElement(objEmployeeDtyOtDetails);
                }
                rs_UnPosted_PopEmp.close();
                ps_UnPosted_PopEmp.close();
                objPopDetails.setPopEmpHrsDetails(vec_Emp);

                vec_Result.addElement(objPopDetails);
            }
            rs_UnPostedPop_Get.close();
            ps_UnPostedPop_Get.close();
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }
            throw new ProductionException("PREC000","GENERAL SQL ERROR",sqle.toString());
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

        logger.info("GET UNPOSTED PAYOUTSIDE-PROUCTION DETAILS ENDS");


        return vec_Result;
    }

    public HashMap postPopDetails(Vector vec_Pop_Ids) throws ProductionException, SQLException
    {

        int result = 0;
        Hashtable ht_PostPop = new Hashtable();
        HashMap hm_Result = new HashMap();

        logger.info("POST PAYOUTSIDE-PRODUCTION DETAILS STARTS");

        if(vec_Pop_Ids == null)
        {
            throw new ProductionException("POPMEC006","PRODUCTION IDs VECTOR OBJECT IS NULL","");

        }
        try
        {
            con = DBConnectionFactory.getConnection();
            con.startTransaction();
            for(int i=0;i<vec_Pop_Ids.size();i++)
            {
                ht_PostPop.put("POP_ID",vec_Pop_Ids.get(i));
                result = con.executeUpdateStatement(SQLMaster.POST_POP_DETAILS_SQL_QUERY,ht_PostPop);
                if(result>=1)
                {
                    con.commitTransaction();
                    hm_Result.put(vec_Pop_Ids.get(i),new Integer(0));
                    logger.info(vec_Pop_Ids.get(i)+" : PAY OUTSIDE PRODUCTION RECORD POSTED");

                }
                else
                {
                    con.rollBackTransaction();
                    hm_Result.put(vec_Pop_Ids.get(i),new Integer(1));
                    logger.info(vec_Pop_Ids.get(i)+" : PAY OUTSIDE PRODUCTION RECORD NOT POSTED");

                }

            }


        }
        catch(SQLException ex)
        {

            if(BuildConfig.DMODE)
            {
                ex.printStackTrace();
                System.out.println("GENERAL EXCEPTION");
            }
            logger.error("GENERAL SQL ERROR",ex);
            throw new ProductionException("PREC000","GENERAL SQL ERROR",ex.toString());
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

        logger.info("POST PAYOUTSIDE-PROUCTION DETAILS ENDS");


        return hm_Result;
    }

    public HashMap makePopValid(Vector popIds) throws ProductionException, SQLException
    {
        int result = 0;
        HashMap hm_Result = new HashMap();

        logger.info("MAKE POP VALID STARTS");

        if(popIds == null)
        {
            logger.error("POP VECTOR OBJECT IS NULL");
            throw new ProductionException("PREC00","POP VECTOR OBJECT IS NULL","");
        }

        try
        {
            con = DBConnectionFactory.getConnection();
            con.startTransaction();
            for(int i=0;i<popIds.size();i++)
            {
                Hashtable ht_Pop_Id = new Hashtable();

                //put the EMP_ID into Hashtable
                ht_Pop_Id.put("POP_ID",(Integer)popIds.get(i));

                //Store the Details from Hashtable to Vector
                result = con.executeUpdateStatement(SQLMaster.POP_MAKE_VALID_SQL_QUERY,ht_Pop_Id);

                if(result>=1)
                {
                    con.commitTransaction();
                    hm_Result.put(popIds.get(i),new Integer(0));
                    logger.info(popIds.get(i)+" : POP RECORD VALIDATED");

                }
                else
                {
                    con.rollBackTransaction();
                    hm_Result.put(popIds.get(i),new Integer(1));
                    logger.info(popIds.get(i)+" : POP RECORD NOT VALIDATED");

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

        logger.info("MAKE POP VALID ENDS");

        return hm_Result;

    }
    public HashMap makePopInValid(Vector popIds) throws ProductionException, SQLException
    {
        int result = 0;
        HashMap hm_Result = new HashMap();

        logger.info("MAKE POP INVALID STARTS");

        if(popIds == null)
        {
            logger.error("POP VECTOR OBJECT IS NULL");
            throw new ProductionException("PREC019","POP VECTOR OBJECT IS NULL","");
        }
        try
        {
            con = DBConnectionFactory.getConnection();
            con.startTransaction();
            for(int i=0;i<popIds.size();i++)
            {
                Hashtable ht_Pop_Id = new Hashtable();
                //put the EMP_ID into Hashtable
                ht_Pop_Id.put("POP_ID",(Integer)popIds.get(i));

                result = con.executeUpdateStatement(SQLMaster.POP_MAKE_INVALID_SQL_QUERY,ht_Pop_Id);

                if(result>=1)
                {
                    con.commitTransaction();
                    hm_Result.put(popIds.get(i),new Integer(0));
                    logger.info(popIds.get(i)+" : POP RECORD INVALIDATED");

                }
                else
                {
                    con.rollBackTransaction();
                    hm_Result.put(popIds.get(i),new Integer(1));
                    logger.info(popIds.get(i)+" : POP RECORD NOT INVALIDATED");
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
            throw new ProductionException("POPMEC000","GENERAL SQL ERROR",e.toString());
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

        logger.info("MAKE POP INVALID ENDS");

        return hm_Result;

    }

    public static void main(String args[]) throws ProductionException, SQLException
    {

        /*PopDetailsManager objPopDetailsManager = new PopDetailsManager();
         EmployeeDtyOtDetails objEmpDtyOtDetails = new EmployeeDtyOtDetails();
         EmployeeDtyOtDetails objEmpDtyOtDetails1 = new EmployeeDtyOtDetails();
         PopDetails objPopDetails = new PopDetails();
         Vector vec = new Vector();
         HashMap hm = new HashMap();
         */
        //////1.addNewPopDetails()//////

        /*objPopDetails.setMcCode("mc102");
         objPopDetails.setPopCrntDate(new Date("12-DEC-04"));
         objPopDetails.setPopRsn("REASON");
         objPopDetails.setShiftId(42);
         objPopDetails.setPopUpdatePyrl(true);
         objPopDetails.setPopPostFlg(false);
         objPopDetails.setPopIsDeleted(false);

         objEmpDtyOtDetails.setEmpId(181);
         objEmpDtyOtDetails.setEmpType("supervisor");
         objEmpDtyOtDetails.setOtHrs(0);
         objEmpDtyOtDetails.setDutyHrs(1.0f);

         vec.addElement(objEmpDtyOtDetails);

         objEmpDtyOtDetails1.setEmpId(101);
         objEmpDtyOtDetails1.setEmpType("test");
         objEmpDtyOtDetails1.setDutyHrs(0.5f);
         objEmpDtyOtDetails1.setOtHrs(0.5f);

         vec.addElement(objEmpDtyOtDetails1);

         objPopDetails.setPopEmpHrsDetails(vec);
         System.out.println(objPopDetailsManager.addNewPopDetails(objPopDetails));*/
        /*catch(ProductionException e)
         {
         e.printStackTrace();
         System.out.println(e.getExceptionMessage());
         }
         catch(Exception e)
         {
         e.printStackTrace();
         }*/

        /////////2.getPopDetails()///////
        /*objPopDetails = objPopDetailsManager.getPopDetails(31);
         objPopDetails.getMcCode();
         vec = objPopDetails.getPopEmpHrsDetails();
         for(int i=0;i<vec.size();i++)
         {
         objEmpDtyOtDetails = (EmployeeDtyOtDetails)vec.get(i);
         System.out.println(objEmpDtyOtDetails.getEmpName());
         System.out.println(objEmpDtyOtDetails.getEmpId());
         System.out.println(objEmpDtyOtDetails.getEmpType());
         System.out.println(objEmpDtyOtDetails.getOtHrs());
         System.out.println(objEmpDtyOtDetails.getOtSlryHrs());
         System.out.println(objEmpDtyOtDetails.getDutyHrs());
         System.out.println(objEmpDtyOtDetails.getDutySlryHrs());

         }*/


        ////////3.getAllPopDetails()//////
        /*Filter[] filters = new Filter[2];
         filters[0] = new Filter();
         filters[0].setFieldName("SHIFT_NAME");
         filters[0].setFieldValue("a");
         filters[0].setSpecialFunction("AnyWhere");


         filters[1] = new Filter();
         filters[1].setFieldName("POP_CRNT_DATE");
         filters[1].setFieldValue("01-NOV-2004$31-DEC-2004");
         filters[1].setSpecialFunction("DateBetween");

         boolean ascending = true;
         int startIndex = 0;
         String sortBy = "MC_CDE";
         int displayCount = 10;
         hm = objPopDetailsManager.getAllPopDetails(filters,sortBy,ascending,startIndex,displayCount);
         System.out.println("HashMap Result :"+hm);*/

        /////////// 4.viewUnPostPopDetails()//
        /*vec = objPopDetailsManager.viewUnPostedPopProductionDetails(new Date("01-NOV-04"),new Date("31-DEC-04"));

         for(int i=0;i<vec.size();i++)
         {
         PopDetails objPopDetails1 = new PopDetails();
         objPopDetails1 = (PopDetails)vec.get(i);
         System.out.println(objPopDetails1.getPopId());
         System.out.println(objPopDetails1.getShiftName());
         System.out.println(objPopDetails1.getMcCode());
         System.out.println(objPopDetails1.getPopCrntDate());
         System.out.println(objPopDetails1.getPopRsn());
         Vector vec1 = new Vector();
         vec1 = objPopDetails1.getPopEmpHrsDetails();
         for(int j=0;j<vec1.size();j++)
         {
         EmployeeDtyOtDetails objEmpDtyOtDetails2 = new EmployeeDtyOtDetails();
         objEmpDtyOtDetails2 = (EmployeeDtyOtDetails)vec1.get(j);
         System.out.println(objEmpDtyOtDetails2.getEmpId());
         System.out.println(objEmpDtyOtDetails2.getEmpName());
         System.out.println(objEmpDtyOtDetails2.getEmpType());
         System.out.println(objEmpDtyOtDetails2.getDutyHrs());
         System.out.println(objEmpDtyOtDetails2.getOtHrs());

         }
         }*/

        ///////////5.postPopDetails()////////
        /*vec.addElement(new Integer(41));
         System.out.println("Result :"+objPopDetailsManager.postPopDetails(vec));*/

        ///////////6.makePopValid()//////////
        /*vec.addElement(new Integer(41));
         hm = objPopDetailsManager.makePopInValid(vec);*/

        ///////////7.makePopInValid()///////
        /*vec.addElement(new Integer(41));
         hm = objPopDetailsManager.makePopValid(vec);*/
        ///////////////updatePopDetails()/////////////
        /*objPopDetails.setPopId(41);
         objPopDetails.setMcCode("mc100");
         objPopDetails.setPopCrntDate(new Date("24-DEC-04"));
         objPopDetails.setPopRsn("TEST");
         objPopDetails.setShiftId(42);
         objPopDetails.setPopUpdatePyrl(false);
         objPopDetails.setPopPostFlg(false);
         objPopDetails.setPopIsDeleted(false);
         objEmpDtyOtDetails1.setEmpId(184);
         objEmpDtyOtDetails1.setDutyHrs(0);
         objEmpDtyOtDetails1.setOtHrs(0);

         vec.addElement(objEmpDtyOtDetails1);
         objPopDetails.setPopEmpHrsDetails(vec);
         //objPopDetails

          System.out.println(objPopDetailsManager.updatePopDetails(objPopDetails));*/
        System.out.println("End of main");
    }
}
/***
 $Log: PopDetailsManager.java,v $
 Revision 1.48  2006/01/12 12:13:56  kduraisamy
 BuildConfig.DMODE included.

 Revision 1.47  2005/12/28 09:47:35  kduraisamy
 in emp Hrs invalid  emp code included.

 Revision 1.46  2005/10/06 10:31:12  vkrishnamoorthy
 Modified By in View, error corrected.

 Revision 1.45  2005/09/30 11:50:06  kduraisamy
 startTransaction() added in create pyrl interface().

 Revision 1.44  2005/09/27 11:35:44  vkrishnamoorthy
 EmpTypId sent along with employee details.

 Revision 1.43  2005/09/27 10:06:12  kduraisamy
 EMP_TYP_ID INCLUDED IN SELECT POP EMP DETAILS.

 Revision 1.42  2005/09/27 05:50:22  kduraisamy
 duty 8 hrs validation added.

 Revision 1.41  2005/09/15 07:36:09  kduraisamy
 rs.getStatement().close() added in Filters.

 Revision 1.40  2005/08/23 09:24:49  kduraisamy
 CHECK EMP TYPE SQL QUERY ADDED.

 Revision 1.39  2005/08/08 07:18:05  kduraisamy
 modify count field added.

 Revision 1.38  2005/08/04 12:00:10  kduraisamy
 EMP_CDE ADDED FOR PROD VIEW.

 Revision 1.37  2005/07/04 09:47:49  kduraisamy
 payroll calculate Sal Hrs() method changed for radl,nprod,pop.

 Revision 1.36  2005/06/29 12:43:58  smurugesan
 if con ! = null added in finally

 Revision 1.35  2005/06/16 05:02:01  kduraisamy
 mc_code removed.

 Revision 1.34  2005/06/13 13:43:14  kduraisamy
 BuildConfig.DMODE added.

 Revision 1.33  2005/06/01 14:37:15  kduraisamy
 log adding changed.

 Revision 1.32  2005/05/30 13:10:22  kduraisamy
 POP AND RADL_DUMMYCOUNT ADDED.

 Revision 1.31  2005/05/16 18:35:05  kduraisamy
 specific throws addded for mysql.

 Revision 1.30  2005/05/16 15:42:43  kduraisamy
 specific throws addded for mysql.

 Revision 1.29  2005/04/19 13:13:33  kduraisamy
 resultset properly closed.

 Revision 1.28  2005/04/18 12:28:06  kduraisamy
 executeStatement() return type changed.

 Revision 1.27  2005/04/07 09:20:19  kduraisamy
 MODE IS CHANGED TO DMODE.

 Revision 1.26  2005/04/07 07:36:10  kduraisamy
 if(BuildConfig.DMODE) ADDED. AND LEVEL INCLUDED PROPERLY.

 Revision 1.25  2005/03/30 08:23:40  kduraisamy
 excecuteRownumStatement() called for filters.

 Revision 1.24  2005/03/26 11:35:09  smurugesan
 ERROR CODES ARE ALTERED.

 Revision 1.23  2005/03/26 10:09:00  smurugesan
 ROWNUM RESET QUERY REMOVED.

 Revision 1.22  2005/03/21 06:52:55  smurugesan
 con.start Transaction() added

 Revision 1.21  2005/03/11 06:20:16  kduraisamy
 RESET ROWNUM QUERY ADDED BEFORE FILTER METHODS..

 Revision 1.20  2005/03/10 07:27:36  kduraisamy
 CHECKING BEFORE THE FILTER METHODS(buildDynamicQuery(),etc) added.

 getDate is changed to getTimestamp().

 Revision 1.19  2005/03/09 07:26:33  kduraisamy
 indentation.

 Revision 1.18  2005/03/05 09:52:22  kduraisamy
 signature changed for addProductionDetails().

 Revision 1.17  2005/03/04 12:33:57  kduraisamy
 DBConnection Changes made.

 Revision 1.16  2005/02/15 09:15:47  smurugesan
 connection object added as a checkempHrs()'s parameter.

 Revision 1.15  2005/02/14 11:10:04  kduraisamy
 unwanted cast removed.

 Revision 1.14  2005/02/11 06:55:15  kduraisamy
 unused variables removed.

 Revision 1.13  2005/02/02 05:30:31  kduraisamy
 logger message modified for postProduction().

 Revision 1.12  2005/02/01 17:47:43  smurugesan
 Exception throws for Object null.in Post productionDetails.

 Revision 1.11  2005/02/01 17:16:42  kduraisamy
 posting method return type changed to HashMap.

 Revision 1.10  2005/01/30 14:13:14  sponnusamy
 commit transaction() is taken out of the for loop.

 Revision 1.9  2005/01/28 14:55:51  kduraisamy
 isModify() method added.

 Revision 1.8  2005/01/27 14:46:45  kduraisamy
 if(BuildConfig.DMODE)
 {
 }
 properly inclued inside the catch block.

 Revision 1.7  2005/01/21 10:30:05  kduraisamy
 EXCEPTION THROWS ADDED.

 Revision 1.6  2005/01/17 06:12:40  kduraisamy
 make valid invalid added.

 Revision 1.5  2005/01/10 06:39:36  kduraisamy
 POP RESN ADDED SETTER METHOD.

 Revision 1.4  2005/01/08 06:22:25  kduraisamy
 getAllPopDetails() method modified.

 Revision 1.3  2004/12/25 11:11:02  sduraisamy
 Exception messages included

 Revision 1.2  2004/12/20 13:55:26  sduraisamy
 Initial Commit with add,view,filter,post,valid and Invalid methods

 ***/
