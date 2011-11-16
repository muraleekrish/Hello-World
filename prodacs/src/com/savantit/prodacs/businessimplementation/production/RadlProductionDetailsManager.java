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
import java.util.LinkedHashMap;
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

/**
 * @author kduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class RadlProductionDetailsManager
{
    DBConnection con=null;
    static Logger logger = Logger.getLogger(RadlProductionDetailsManager.class);
    public RadlProductionDetailsManager()
    {
        logger.addAppender(LoggerOutput.getAppender());
        logger.setLevel(ProdacsConfig.LOGGER_LEVEL);
    }


    public HashMap addNewRadialProductionDetails(RadialProductionDetails[] objRadialProductionDetailsList) throws ProductionException, SQLException
    {
        //boolean incntvFlg = false;
        float totHrs = 0;
        float otHrs = 0;
        float otSlryHrs = 0;
        float dtyHrs = 0;
        float dtySlryHrs = 0;
        float incntvSlryHrs = 0;
        float stdHrs = 0;
        int totQty = 0;
        int radlProdId = 0;
        int empId = 0;
        int empTypId = 0;
        int startOpn = 0;
        int endOpn = 0;
        //String empType = "";
        Vector vec_EmpHrs = new Vector();
        Vector vec_EmpFail = new Vector();
        Vector vec_MachineFail = new Vector();
        Vector vecDutyFail = new Vector();
        Hashtable ht_RadlProdn_Add = new Hashtable();
        Hashtable ht_EmpHrs_Add = new Hashtable();
        Hashtable ht_ProdAccntng_Add = new Hashtable();
        HashMap hm_Result = new HashMap();
        Hashtable ht = new Hashtable();
        PreparedStatement psEmpTypCheck = null;
        ResultSet rsEmpTypCheck = null;
        Vector vec_Success = new Vector();

        ProductionDetailsManager objProductionDetailsManager = new ProductionDetailsManager();
        int result = 0;


        logger.info("RADL-PRODUCTION ADD STARTS");

        if(BuildConfig.DMODE)
            System.out.println("ADD RADL-PRODUCTION DETAILS");
        if(objRadialProductionDetailsList.length == 0)
        {

            logger.error("RADL-PRODUCTION DETAILS OBJ NULL");
            throw new ProductionException("RPMEC001","RADL-PRODUCTION DETAILS OBJ NULL","");
        }

        try
        {

            con = DBConnectionFactory.getConnection();
            con.startTransaction();

            for( int i = 0;i<objRadialProductionDetailsList.length;i++)
            {
                startOpn = objRadialProductionDetailsList[i].getRadlStartOpn();
                endOpn = objRadialProductionDetailsList[i].getRadlEndOpn();
                totHrs = objRadialProductionDetailsList[i].getRadlTotHrs();

                totQty =  objRadialProductionDetailsList[i].getRadlTotQty();
                stdHrs  = objProductionDetailsManager.getStandardHrs(objRadialProductionDetailsList[i].getWoJbId(),startOpn,endOpn);

                //to find total standard hrs...

                stdHrs = totQty * stdHrs;


                ht_RadlProdn_Add.put("MC_CDE",objRadialProductionDetailsList[i].getMcCode());
                ht_RadlProdn_Add.put("RADL_CRNT_DATE",objRadialProductionDetailsList[i].getRadlCrntDate());
                ht_RadlProdn_Add.put("SHIFT_ID",new Integer(objRadialProductionDetailsList[i].getShiftId()));
                ht_RadlProdn_Add.put("RADL_WORK_TYP",objRadialProductionDetailsList[i].getRadlWorkType());
                ht_RadlProdn_Add.put("RADL_INCNTV_FLG",(objProductionDetailsManager.checkIncentive(objRadialProductionDetailsList[i].getWoJbId(),startOpn,endOpn))?"1":"0");
                ht_RadlProdn_Add.put("WOJB_ID",new Integer(objRadialProductionDetailsList[i].getWoJbId()));
                ht_RadlProdn_Add.put("RADL_QTY_SNOS",objRadialProductionDetailsList[i].getRadlQtySnos());
                ht_RadlProdn_Add.put("RADL_TOT_QTY",new Integer(objRadialProductionDetailsList[i].getRadlTotQty()));
                ht_RadlProdn_Add.put("RADL_START_OPN",new Integer(startOpn));
                ht_RadlProdn_Add.put("RADL_END_OPN",new Integer(endOpn));
                ht_RadlProdn_Add.put("RADL_STD_HRS",new Float(stdHrs));
                ht_RadlProdn_Add.put("RADL_TOT_HRS",new Float(totHrs));
                ht_RadlProdn_Add.put("RADL_MATL_TYP_ID",new Integer(objRadialProductionDetailsList[i].getRadlMatlTypeId()));
                ht_RadlProdn_Add.put("RADL_DMTR",new Float(objRadialProductionDetailsList[i].getRadlDiameter()));
                ht_RadlProdn_Add.put("RADL_LENGTH",new Float(objRadialProductionDetailsList[i].getRadlLength()));
                ht_RadlProdn_Add.put("RADL_NO_OF_HOLES",new Integer(objRadialProductionDetailsList[i].getRadlNoOfHoles()));
                ht_RadlProdn_Add.put("RADL_PRDMTR",new Float(objRadialProductionDetailsList[i].getRadlPreDiameter()));
                ht_RadlProdn_Add.put("RADL_COMPLETION_FLG",objRadialProductionDetailsList[i].isRadlCompletionFlg()?"1":"0");
                ht_RadlProdn_Add.put("RADL_CREATEDBY",objRadialProductionDetailsList[i].getCreatedBy());

                try
                {
                    result = con.executeUpdateStatement(SQLMaster.RADL_PRODN_DETAILS_ADD_SQL_QUERY,ht_RadlProdn_Add);
                    if(result > 0)
                    {

                        //to set WO_ISPROD_ENTERED to 1..

                        con.executeUpdateStatement(SQLMaster.WORKORDER_JB_TABLE_UPDATE_SQL_QUERY,ht_RadlProdn_Add);

                        PreparedStatement ps = con.executeStatement(SQLMaster.RADL_ID_SELECT_SQL_QUERY);
                        ResultSet rs_RadlProdId_Get = ps.executeQuery();
                        if(rs_RadlProdId_Get.next())
                        {
                            radlProdId = rs_RadlProdId_Get.getInt(1);
                            if(BuildConfig.DMODE)
                                System.out.println("RADL PROD ID :"+radlProdId);
                        }
                        rs_RadlProdId_Get.close();
                        ps.close();
                        vec_EmpHrs = objRadialProductionDetailsList[i].getRadlEmpHrsDetails();
                        //incntvFlg = objRadialProductionDetailsList[i].isRadlIncntvFlg();


                        //String prodWorkType = objRadialProductionDetailsList[i].getRadlWorkType();
                        boolean flag = true;
                        for(int j = 0;j<vec_EmpHrs.size();j++)
                        {
                            otHrs = 0;
                            otSlryHrs = 0;
                            dtyHrs = 0;
                            dtySlryHrs = 0;
                            incntvSlryHrs = 0;
                            EmployeeDtyOtDetails objEmployeeDtyOtDetails = new EmployeeDtyOtDetails();
                            objEmployeeDtyOtDetails = (EmployeeDtyOtDetails)vec_EmpHrs.elementAt(j);
                            dtyHrs = objEmployeeDtyOtDetails.getDutyHrs();
                            otHrs = objEmployeeDtyOtDetails.getOtHrs();
                            empId = objEmployeeDtyOtDetails.getEmpId();
                            
                            /*empType = objEmployeeDtyOtDetails.getEmpType();
                            if(!empType.equalsIgnoreCase("supervisor"))
                            {
                                if((incntvFlg==true)&&!(prodWorkType.equalsIgnoreCase("r")))
                                {

                                    if((dtyHrs+otHrs)==totHrs)
                                    {
                                        if(dtyHrs+otHrs <= stdHrs)
                                        {
                                            incntvSlryHrs = stdHrs;
                                            otSlryHrs = otHrs;
                                        }
                                        else
                                        {
                                            dtySlryHrs = dtyHrs;
                                            otSlryHrs = otHrs * 2;
                                        }
                                    }
                                    else
                                    {
                                        dtySlryHrs = dtyHrs;
                                        otSlryHrs = otHrs * 2;
                                    }
                                }
                                else
                                {
                                    dtySlryHrs = dtyHrs;
                                    otSlryHrs = otHrs * 2;

                                }
                            }
                            else
                            {
                                dtySlryHrs = dtyHrs;
                            }
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
                                if(!objProductionDetailsManager.checkDutyEntry(objRadialProductionDetailsList[i].getRadlCrntDate(), objRadialProductionDetailsList[i].getShiftId(), empId, con))
                                {
                                    con.rollBackTransaction();
                                    flag = false;
                                    HashMap hm = new HashMap();
                                    hm.put(objEmployeeDtyOtDetails.getEmpName(), objRadialProductionDetailsList[i]);
                                    vecDutyFail.addElement(hm);
                                    break;
                                }
                               
                            if(val == 0)
                              check = objProductionDetailsManager.checkAvailableEmpHrs(empId,objRadialProductionDetailsList[i].getRadlCrntDate(),objRadialProductionDetailsList[i].getShiftId(),0,0,dtyHrs+otHrs,0,con);
                            if(check==0)
                            {
                                ht_EmpHrs_Add.put("RADL_ID",new Integer(radlProdId));
                                ht_EmpHrs_Add.put("EMP_ID",new Integer(empId));
                                ht_EmpHrs_Add.put("DT_HRS",new Float(dtyHrs));
                                ht_EmpHrs_Add.put("OT_HRS",new Float(otHrs));
                                ht_EmpHrs_Add.put("DT_SLRY_HRS",new Float(dtySlryHrs));
                                ht_EmpHrs_Add.put("OT_SLRY_HRS",new Float(otSlryHrs));
                                ht_EmpHrs_Add.put("INCNTV_SLRY_HRS",new Float(incntvSlryHrs));
                                ht_EmpHrs_Add.put("RADL_MODIFYCOUNT",new Integer(0));
                                result = con.executeUpdateStatement(SQLMaster.RADL_PRODN_EMPHRS_ADD_SQL_QUERY,ht_EmpHrs_Add);
                                if(result>0)
                                {
                                    if(BuildConfig.DMODE)
                                        System.out.println("Employee Hrs Details Added");
                                }

                            }
                            else
                            {
                                flag = false;
                                con.rollBackTransaction();
                                HashMap hm = new HashMap();
                                hm.put(objEmployeeDtyOtDetails.getEmpName(), objRadialProductionDetailsList[i]);
                                vec_EmpFail.addElement(hm);
                                break;
                            }
                        }
                        if(flag)
                        {
                            ht_ProdAccntng_Add.put("MC_CDE",objRadialProductionDetailsList[i].getMcCode());
                            ht_ProdAccntng_Add.put("PROD_DATE",objRadialProductionDetailsList[i].getRadlCrntDate());
                            ht_ProdAccntng_Add.put("SHIFT_ID",new Integer(objRadialProductionDetailsList[i].getShiftId()));
                            int check = objProductionDetailsManager.checkHrsAccounted(objRadialProductionDetailsList[i].getMcCode(),objRadialProductionDetailsList[i].getRadlCrntDate(),objRadialProductionDetailsList[i].getShiftId(),objRadialProductionDetailsList[i].getRadlTotHrs(),0,con);
                            if(check==0)
                            {
                                ht_ProdAccntng_Add.put("PROD_HRS",new Float(objRadialProductionDetailsList[i].getRadlTotHrs()));
                                ht_ProdAccntng_Add.put("ISHRS_ACCOUNTED","1");
                            }
                            else if(check ==1)
                            {
                                ht_ProdAccntng_Add.put("PROD_HRS",new Float(objRadialProductionDetailsList[i].getRadlTotHrs()));
                                ht_ProdAccntng_Add.put("ISHRS_ACCOUNTED","0");
                            }
                            else
                            {
                                con.rollBackTransaction();
                                vec_MachineFail.addElement(objRadialProductionDetailsList[i]);
                                continue;

                            }
                            result = con.executeUpdateStatement(SQLMaster.PRODN_ACCNTNG_UPDATE_BY_PRODHRS_SQL_QUERY,ht_ProdAccntng_Add);

                            if(result<=0)
                            {
                                result = con.executeUpdateStatement(SQLMaster.PRODN_ACCNTNG_PRODHRS_ADD_SQL_QUERY,ht_ProdAccntng_Add);
                                if(result>0)
                                {
                                    if(BuildConfig.DMODE)
                                        System.out.println("Production Accounting Added");
                                }

                            }
                            else
                            {
                                if(BuildConfig.DMODE)
                                    System.out.println("Production Accounting Updated");
                            }
                        }
                        else
                        {
                            continue;
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
                    if(sqle.toString().indexOf("PK_RADLID")>=0)
                    {
                        throw new ProductionException("RPMEC002","RADIAL PRODUCTION ID IS UNIQUE.UNIQUE CONSTRAINT VIOLATED",sqle.toString());
                    }
                    else if (sqle.toString().indexOf("FK_RADL_MCCDE") >= 0)
                    {
                        throw new ProductionException("RPMEC003","PARENT KEY MACHINE CODE NOT FOUND",sqle.toString());
                    }
                    else if (sqle.toString().indexOf("FK_RADL_SHIFTID") >= 0)
                    {
                        throw new ProductionException("RPMEC004","PARENT KEY SHIFT ID NOT FOUND",sqle.toString());
                    }
                    else if (sqle.toString().indexOf("FK_RADL_WOJBID") >= 0)
                    {
                        throw new ProductionException("RPMEC005","PARENT KEY WORK ORDER JOB ID NOT FOUND",sqle.toString());
                    }
                    else if (sqle.toString().indexOf("FK_RADLMATLTYPID") >= 0)
                    {
                        throw new ProductionException("RPMEC006","PARENT KEY RADIAL MATERIAL TYPE ID NOT FOUND",sqle.toString());
                    }
                    else
                    {
                        throw new ProductionException("RPMEC000","GENERAL SQL ERROR",sqle.toString());
                    }
                    }
                    if(BuildConfig.DBASE==BuildConfig.MYSQL)
                    {
                    if(sqle.toString().indexOf("Duplicate entry")>=0)
                    {
                        throw new ProductionException("RPMEC002","RADIAL PRODUCTION ID IS UNIQUE.UNIQUE CONSTRAINT VIOLATED",sqle.toString());
                    }
                    else if (sqle.toString().indexOf("foreign key constraint") >= 0)
                    {
                        throw new ProductionException("RPMEC003","PARENT KEY MACHINE CODE OR SHIFT ID OR WORKORDER JOB ID OR RADIAL MATL TYPE ID NOT FOUND",sqle.toString());
                    }
                    else
                    {
                        throw new ProductionException("RPMEC000","GENERAL SQL ERROR",sqle.toString());
                    }
                    }
                }
                con.commitTransaction();
                vec_Success.addElement(new Integer(radlProdId));

            }
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();

                logger.error("GENERAL SQL ERROR",sqle);
                System.out.println("GENERAL EXCEPTION");
            }

            throw new ProductionException("RPMEC000","GENERAL SQL ERROR",sqle.toString());
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

        logger.info("RADL-PRODUCTION ADD ENDS");


        hm_Result.put("Success",vec_Success);
        hm_Result.put("EmpFailure",vec_EmpFail);
        hm_Result.put("MachineFailure",vec_MachineFail);
        hm_Result.put("DutyFailure",vecDutyFail);

        return hm_Result;


    }
    public boolean updateRadlProductionDetails(RadialProductionDetails objRadialProductionDetails) throws ProductionException, SQLException
    {
        boolean updateRESULT = false;
        //boolean incntvFlg = false;
        int radlId = 0;
        DBConnection con=null;
        Hashtable ht_EmpHrs_Del = new Hashtable();
        Hashtable ht_MacHrs_Reduce = new Hashtable();
        Hashtable ht_RadlProdn_Update = new Hashtable();
        Hashtable ht_EmpHrs_Add = new Hashtable();
        Hashtable ht_ProdAccntng_Add = new Hashtable();
        Vector vec_EmpHrs = new Vector();
        ProductionDetailsManager objProductionDetailsManager = new ProductionDetailsManager();
        int result;
        float otHrs = 0;
        float otSlryHrs = 0;
        float dtyHrs = 0;
        float dtySlryHrs = 0;
        float incntvSlryHrs = 0;
        int totQty = 0;

        float stdHrs = 0;
        //float totHrs = 0;
        int empId = 0;
        int empTypId = 0;
        PreparedStatement psEmpTypCheck = null;
        ResultSet rsEmpTypCheck = null;
        Hashtable ht = new Hashtable();
        //String empType = "";



        //delete PROD_EMP table.
        radlId = objRadialProductionDetails.getRadlId();
        con = DBConnectionFactory.getConnection();
        con.startTransaction();
        ht_EmpHrs_Del.put("RADL_ID",new Integer(radlId));
        try
        {
//      	//    to store Emp Hours entries into Log..
            result = con.executeUpdateStatement(SQLMaster.LOG_RADL_EMPHRS_ADD_SQL_QUERY,ht_EmpHrs_Del);
            if(result>0)
            {


            result = con.executeUpdateStatement(SQLMaster.RADL_PRODN_EMP_HRS_DELETE_SQL_QUERY,ht_EmpHrs_Del);
            if(result>0)
            {
                if(BuildConfig.DMODE)
                    System.out.println("RADIAL PROD EMP HRS DELETED");
            }
            }
            //to select PREVIOUS PROD HRS...FOR
            PreparedStatement ps = con.executeStatement(SQLMaster.RADL_PRODN_TOT_HRS_SELECT_SQL_QUERY,ht_EmpHrs_Del);
            ResultSet rs = ps.executeQuery();

            float radlTotHrs = 0;
            if(rs.next())
            {
                radlTotHrs = rs.getFloat("RADL_TOT_HRS");

            }

            rs.close();
            ps.close();

            //subtract Machine Hrs in PROD_ACCNTNG table.
            ht_MacHrs_Reduce.put("MC_CDE",objRadialProductionDetails.getMcCode());
            ht_MacHrs_Reduce.put("PROD_DATE",objRadialProductionDetails.getRadlCrntDate());
            ht_MacHrs_Reduce.put("SHIFT_ID",new Integer(objRadialProductionDetails.getShiftId()));
            ht_MacHrs_Reduce.put("PROD_HRS",new Float(radlTotHrs));
            ht_MacHrs_Reduce.put("ISHRS_ACCOUNTED","0");
            ht_MacHrs_Reduce.put("RADL_ID",new Integer(radlId));
            result = con.executeUpdateStatement(SQLMaster.RADL_PRODN_MC_HRS_REDUCE_SQL_QUERY,ht_MacHrs_Reduce);
            if(result>0)
            {
                if(BuildConfig.DMODE)
                    System.out.println("RADIAL PROD MC HRS SUBTRACTED");
            }
            //Updation of RADL table.


            ht_RadlProdn_Update.put("RADL_ID",new Integer(radlId));
            ht_RadlProdn_Update.put("MC_CDE",objRadialProductionDetails.getMcCode());
            ht_RadlProdn_Update.put("RADL_CRNT_DATE",objRadialProductionDetails.getRadlCrntDate());
            ht_RadlProdn_Update.put("SHIFT_ID",new Integer(objRadialProductionDetails.getShiftId()));
            ht_RadlProdn_Update.put("RADL_WORK_TYP",objRadialProductionDetails.getRadlWorkType());
            ht_RadlProdn_Update.put("RADL_INCNTV_FLG",(objProductionDetailsManager.checkIncentive(objRadialProductionDetails.getWoJbId(),objRadialProductionDetails.getRadlStartOpn(),objRadialProductionDetails.getRadlEndOpn()))?"1":"0");
            ht_RadlProdn_Update.put("WOJB_ID",new Integer(objRadialProductionDetails.getWoJbId()));
            ht_RadlProdn_Update.put("RADL_QTY_SNOS",objRadialProductionDetails.getRadlQtySnos());
            ht_RadlProdn_Update.put("RADL_TOT_QTY",new Integer(objRadialProductionDetails.getRadlTotQty()));
            ht_RadlProdn_Update.put("RADL_START_OPN",new Integer(objRadialProductionDetails.getRadlStartOpn()));
            ht_RadlProdn_Update.put("RADL_END_OPN",new Integer(objRadialProductionDetails.getRadlEndOpn()));
            ht_RadlProdn_Update.put("RADL_STD_HRS",new Float(objRadialProductionDetails.getRadlStdHrs()));
            ht_RadlProdn_Update.put("RADL_TOT_HRS",new Float(objRadialProductionDetails.getRadlTotHrs()));
            ht_RadlProdn_Update.put("RADL_MATL_TYP_ID",new Integer(objRadialProductionDetails.getRadlMatlTypeId()));
            ht_RadlProdn_Update.put("RADL_DMTR",new Float(objRadialProductionDetails.getRadlDiameter()));
            ht_RadlProdn_Update.put("RADL_LENGTH",new Float(objRadialProductionDetails.getRadlLength()));
            ht_RadlProdn_Update.put("RADL_NO_OF_HOLES",new Integer(objRadialProductionDetails.getRadlNoOfHoles()));
            ht_RadlProdn_Update.put("RADL_PRDMTR",new Float(objRadialProductionDetails.getRadlPreDiameter()));
            ht_RadlProdn_Update.put("RADL_COMPLETION_FLG",objRadialProductionDetails.isRadlCompletionFlg()?"1":"0");
            ht_RadlProdn_Update.put("RADL_UPDATE_PYRL","0");
            ht_RadlProdn_Update.put("RADL_UPDATE_WO","0");
            ht_RadlProdn_Update.put("RADL_POST_FLG","0");
            ht_RadlProdn_Update.put("IS_DLTD","0");
            ht_RadlProdn_Update.put("RADL_CREATEDBY",objRadialProductionDetails.getModifiedBy());

            // to store Prod Entries to Log..
            con.executeUpdateStatement(SQLMaster.RADL_DETAILS_LOG_ADD_SQL_QUERY,ht_RadlProdn_Update);

            result = con.executeUpdateStatement(SQLMaster.RADL_PRODN_DETAILS_UPDATE_SQL_QUERY,ht_RadlProdn_Update);
            if(result > 0)
            {
                //to select current modify count..

                PreparedStatement ps_ModifyCnt_Sel = con.executeStatement(SQLMaster.RADL_MODIFY_COUNT_SELECT,ht_RadlProdn_Update);
                ResultSet rs_ModifyCnt_Sel = ps_ModifyCnt_Sel.executeQuery();
                int modifyCount = 0;
                if(rs_ModifyCnt_Sel.next())
                {
                    modifyCount = rs_ModifyCnt_Sel.getInt("RADL_MODIFYCOUNT");
                }
                ht_RadlProdn_Update.put("RADL_MODIFYCOUNT",new Integer(modifyCount));

                ht_EmpHrs_Add.put("RADL_MODIFYCOUNT",new Integer(modifyCount));



                totQty =  objRadialProductionDetails.getRadlTotQty();
                stdHrs  = objProductionDetailsManager.getStandardHrs(objRadialProductionDetails.getWoJbId(),objRadialProductionDetails.getRadlStartOpn(),objRadialProductionDetails.getRadlEndOpn());

                //to find total standard hrs...

                stdHrs = totQty * stdHrs;

                vec_EmpHrs = objRadialProductionDetails.getRadlEmpHrsDetails();
                //incntvFlg = objRadialProductionDetails.isRadlIncntvFlg();
                //String prodWorkType = objRadialProductionDetails.getRadlWorkType();
                //totHrs = objRadialProductionDetails.getRadlTotHrs();
                for(int i = 0;i<vec_EmpHrs.size();i++)
                {
                    otHrs = 0;
                    otSlryHrs = 0;
                    dtyHrs = 0;
                    dtySlryHrs = 0;
                    incntvSlryHrs = 0;
                    EmployeeDtyOtDetails objEmployeeDtyOtDetails = new EmployeeDtyOtDetails();
                    objEmployeeDtyOtDetails = (EmployeeDtyOtDetails)vec_EmpHrs.elementAt(i);


                    dtyHrs = objEmployeeDtyOtDetails.getDutyHrs();
                    otHrs = objEmployeeDtyOtDetails.getOtHrs();

                    empId = objEmployeeDtyOtDetails.getEmpId();
                    
                    /*empType = objEmployeeDtyOtDetails.getEmpType();
                    if(!empType.equalsIgnoreCase("supervisor"))
                    {
                        if((incntvFlg==true)&&!(prodWorkType.equalsIgnoreCase("r")))
                        {

                            if((dtyHrs+otHrs)==totHrs)
                            {
                                if(dtyHrs+otHrs <= stdHrs)
                                {
                                    incntvSlryHrs = stdHrs;
                                    otSlryHrs = otHrs;
                                }
                                else
                                {
                                    dtySlryHrs = dtyHrs;
                                    otSlryHrs = otHrs * 2;
                                }
                            }
                            else
                            {
                                dtySlryHrs = dtyHrs;
                                otSlryHrs = otHrs * 2;
                            }

                            /*if(dtyHrs==totHrs)
                             {
                             if(dtyHrs <= stdHrs)
                             {
                             incntvSlryHrs = stdHrs;
                             }
                             else
                             {
                             dtySlryHrs = dtyHrs;
                             }
                             }
                             else
                             {
                             dtySlryHrs = dtyHrs;
                             }

                             if(otHrs==totHrs)
                             {
                             if(otHrs <= stdHrs)
                             {
                             incntvSlryHrs = stdHrs;
                             otSlryHrs = otHrs;
                             }
                             else
                             {
                             otSlryHrs = otHrs * 2;
                             }
                             }
                             else
                             {
                             otSlryHrs = otHrs * 2;
                             }


                        }
                        else
                        {
                            dtySlryHrs = dtyHrs;
                            otSlryHrs = otHrs * 2;

                        }
                    }
                    else
                    {
                        dtySlryHrs = dtyHrs;
                    }
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
                        if(!objProductionDetailsManager.checkDutyEntry(objRadialProductionDetails.getRadlCrntDate(), objRadialProductionDetails.getShiftId(), empId, con))
                        {
                            con.rollBackTransaction();
                            throw new ProductionException("WOEC00","EMPLOYEE DUTY NOT POSSIBLE FOR MULTIPLE SHIFTS : "+objEmployeeDtyOtDetails.getEmpName(),"");
                        }
                    
                    if(val == 0)
                      check = objProductionDetailsManager.checkAvailableEmpHrs(empId,objRadialProductionDetails.getRadlCrntDate(),objRadialProductionDetails.getShiftId(),0,0,dtyHrs+otHrs,0,con);
                    if(check==0)
                    {
                        ht_EmpHrs_Add.put("RADL_ID",new Integer(radlId));
                        ht_EmpHrs_Add.put("EMP_ID",new Integer(empId));
                        ht_EmpHrs_Add.put("DT_HRS",new Float(dtyHrs));
                        ht_EmpHrs_Add.put("OT_HRS",new Float(otHrs));
                        ht_EmpHrs_Add.put("DT_SLRY_HRS",new Float(dtySlryHrs));
                        ht_EmpHrs_Add.put("OT_SLRY_HRS",new Float(otSlryHrs));
                        ht_EmpHrs_Add.put("INCNTV_SLRY_HRS",new Float(incntvSlryHrs));

                        result = con.executeUpdateStatement(SQLMaster.RADL_PRODN_EMPHRS_ADD_SQL_QUERY,ht_EmpHrs_Add);
                        if(result>0)
                        {
                            if(BuildConfig.DMODE)
                                System.out.println("Employee Hrs Details Added");


                        }

                    }
                    else
                    {
                        con.rollBackTransaction();
                        throw new ProductionException("WOEC00","EMPLOYEE HRS IS INVALID : "+objEmployeeDtyOtDetails.getEmpName() ,"");
                    }
                }

                ht_ProdAccntng_Add.put("MC_CDE",objRadialProductionDetails.getMcCode());
                ht_ProdAccntng_Add.put("PROD_DATE",objRadialProductionDetails.getRadlCrntDate());
                ht_ProdAccntng_Add.put("SHIFT_ID",new Integer(objRadialProductionDetails.getShiftId()));
                int check = objProductionDetailsManager.checkHrsAccounted(objRadialProductionDetails.getMcCode(),objRadialProductionDetails.getRadlCrntDate(),objRadialProductionDetails.getShiftId(),objRadialProductionDetails.getRadlTotHrs(),0,con);
                if(check==0)
                {
                    ht_ProdAccntng_Add.put("PROD_HRS",new Float(objRadialProductionDetails.getRadlTotHrs()));
                    ht_ProdAccntng_Add.put("ISHRS_ACCOUNTED","1");
                }
                else if(check ==1)
                {
                    ht_ProdAccntng_Add.put("PROD_HRS",new Float(objRadialProductionDetails.getRadlTotHrs()));
                    ht_ProdAccntng_Add.put("ISHRS_ACCOUNTED","0");
                }
                else
                {

                    con.rollBackTransaction();
                    throw new ProductionException("WOEC00","RADIAL PROD HRS IS INVALID","");
                }



                result = con.executeUpdateStatement(SQLMaster.PRODN_ACCNTNG_UPDATE_BY_PRODHRS_SQL_QUERY,ht_ProdAccntng_Add);

                if(result<=0)
                {
                    result = con.executeUpdateStatement(SQLMaster.PRODN_ACCNTNG_PRODHRS_ADD_SQL_QUERY,ht_ProdAccntng_Add);
                    if(result>0)
                    {
                        if(BuildConfig.DMODE)
                            System.out.println("Production Accounting Added");
                    }

                }
                else
                {
                    if(BuildConfig.DMODE)
                        System.out.println("Production Accounting Updated");
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
            if(sqle.toString().indexOf("PK_RADLID")>=0)
            {
                throw new ProductionException("RPMEC002","RADIAL PRODUCTION ID IS UNIQUE.UNIQUE CONSTRAINT VIOLATED",sqle.toString());
            }
            else if (sqle.toString().indexOf("FK_RADL_MCCDE") >= 0)
            {
                throw new ProductionException("RPMEC003","PARENT KEY MACHINE CODE NOT FOUND",sqle.toString());
            }
            else if (sqle.toString().indexOf("FK_RADL_SHIFTID") >= 0)
            {
                throw new ProductionException("RPMEC004","PARENT KEY SHIFT ID NOT FOUND",sqle.toString());
            }
            else if (sqle.toString().indexOf("FK_RADL_WOJBID") >= 0)
            {
                throw new ProductionException("RPMEC005","PARENT KEY WORK ORDER JOB ID NOT FOUND",sqle.toString());
            }
            else if (sqle.toString().indexOf("FK_RADLMATLTYPID") >= 0)
            {
                throw new ProductionException("RPMEC006","PARENT KEY RADIAL MATERIAL TYPE ID NOT FOUND",sqle.toString());
            }
            else
            {
                throw new ProductionException("RPMEC000","GENERAL SQL ERROR",sqle.toString());
            }
            }
            if(BuildConfig.DBASE==BuildConfig.MYSQL)
            {
            if(sqle.toString().indexOf("Duplicate entry")>=0)
            {
                throw new ProductionException("RPMEC002","RADIAL PRODUCTION ID IS UNIQUE.UNIQUE CONSTRAINT VIOLATED",sqle.toString());
            }
            else if (sqle.toString().indexOf("foreign key constraint") >= 0)
            {
                throw new ProductionException("RPMEC003","PARENT KEY MACHINE CODE OR SHIFT ID OR WORKORDER JOB ID OR RADIAL MATL TYPE ID NOT FOUND",sqle.toString());
            }
            else
            {
                throw new ProductionException("RPMEC000","GENERAL SQL ERROR",sqle.toString());
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

    public boolean isModifyForRadl(int radlId) throws ProductionException, SQLException
    {
        boolean retVal = false;
        DBConnection con = null;
        Hashtable ht_Prod = new Hashtable();
        int cnt = 0;
        try
        {
            con = DBConnectionFactory.getConnection();
            ht_Prod.put("RADL_ID",new Integer(radlId));
            PreparedStatement ps = con.executeStatement(SQLMaster.CHECK_RADL_FOR_MODIFY_SQL_QUERY,ht_Prod);
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
            throw new ProductionException("RPMEC000","GENERAL SQL ERROR",sqle.toString());
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

    public Vector viewUnPostedRadlProductionDetails(Date fromDate,Date toDate) throws SQLException, ProductionException
    {
        Vector vec_Result = new Vector();
        Hashtable ht_UnPostedJbDet_Get = new Hashtable();
        Hashtable ht_UnPostedDet_Get = new Hashtable();
        int radlProdId = 0;
        int woJbId = 0;


        logger.info("GET UNPOSTED RADL PRODN DETAILS STARTS");


        con = DBConnectionFactory.getConnection();
        ht_UnPostedDet_Get.put("FROM_DATE",fromDate);
        ht_UnPostedDet_Get.put("TO_DATE",toDate);
        try
        {
            PreparedStatement ps_UnpostedRadlProdId_Get = con.executeStatement(SQLMaster.UNPOSTED_RADLPROD_ID_SELECT_SQL_QUERY,ht_UnPostedDet_Get);
            ResultSet rs_UnpostedRadlProdId_Get = ps_UnpostedRadlProdId_Get.executeQuery();
            while(rs_UnpostedRadlProdId_Get.next())
            {
                UnPostedRadlProductionDetails objUnPostedRadlProductionDetails = new UnPostedRadlProductionDetails();
                radlProdId = rs_UnpostedRadlProdId_Get.getInt("RADL_ID");
                objUnPostedRadlProductionDetails.setRadlProdId(radlProdId);
                objUnPostedRadlProductionDetails.setMcCode(rs_UnpostedRadlProdId_Get.getString("MC_CDE"));
                objUnPostedRadlProductionDetails.setRadlProdCrntDate(rs_UnpostedRadlProdId_Get.getTimestamp("RADL_CRNT_DATE"));
                objUnPostedRadlProductionDetails.setShiftName(rs_UnpostedRadlProdId_Get.getString("SHIFT_NAME"));
                objUnPostedRadlProductionDetails.setRadlProdWorkType(rs_UnpostedRadlProdId_Get.getString("RADL_WORK_TYP"));
                objUnPostedRadlProductionDetails.setRadlProdIncntvFlag(rs_UnpostedRadlProdId_Get.getString("RADL_INCNTV_FLG").equals("1")?true:false);
                objUnPostedRadlProductionDetails.setRadlProdStartOpn(rs_UnpostedRadlProdId_Get.getInt("RADL_START_OPN"));
                objUnPostedRadlProductionDetails.setRadlProdEndOpn(rs_UnpostedRadlProdId_Get.getInt("RADL_END_OPN"));
                objUnPostedRadlProductionDetails.setRadlProdStdHrs(rs_UnpostedRadlProdId_Get.getFloat("RADL_STD_HRS"));
                objUnPostedRadlProductionDetails.setRadlProdTotHrs(rs_UnpostedRadlProdId_Get.getFloat("RADL_TOT_HRS"));
                objUnPostedRadlProductionDetails.setRadlQtySnos(rs_UnpostedRadlProdId_Get.getString("RADL_QTY_SNOS"));
                objUnPostedRadlProductionDetails.setJobQty(rs_UnpostedRadlProdId_Get.getInt("RADL_TOT_QTY"));
                objUnPostedRadlProductionDetails.setRadlMatlTypeId(rs_UnpostedRadlProdId_Get.getInt("RADL_MATL_TYP_ID"));
                objUnPostedRadlProductionDetails.setRadlDmtr(rs_UnpostedRadlProdId_Get.getFloat("RADL_DMTR"));
                objUnPostedRadlProductionDetails.setRadlLength(rs_UnpostedRadlProdId_Get.getFloat("RADL_LENGTH"));
                objUnPostedRadlProductionDetails.setRadlNoOfHoles(rs_UnpostedRadlProdId_Get.getInt("RADL_NO_OF_HOLES"));
                objUnPostedRadlProductionDetails.setRadlPreDmtr(rs_UnpostedRadlProdId_Get.getFloat("RADL_PRDMTR"));
                woJbId = rs_UnpostedRadlProdId_Get.getInt("WOJB_ID");
                ht_UnPostedJbDet_Get.put("WOJB_ID",new Integer(woJbId));

                PreparedStatement ps_UnPostedJbDet_Get = con.executeStatement(SQLMaster.UNPOSTED_JOB_DETAILS_GET_SQL_QUERY,ht_UnPostedJbDet_Get);
                ResultSet rs_UnPostedJbDet_Get = ps_UnPostedJbDet_Get.executeQuery();
                if(rs_UnPostedJbDet_Get.next())
                {
                    objUnPostedRadlProductionDetails.setJobName(rs_UnPostedJbDet_Get.getString("JB_NAME"));
                    objUnPostedRadlProductionDetails.setJobDrwgNo(rs_UnPostedJbDet_Get.getString("JB_DWG_NO"));
                    objUnPostedRadlProductionDetails.setJobRvsnNo(rs_UnPostedJbDet_Get.getString("JB_RVSN_NO"));
                    objUnPostedRadlProductionDetails.setJobMatlType(rs_UnPostedJbDet_Get.getString("JB_MATL_TYP"));
                    objUnPostedRadlProductionDetails.setWoNo(rs_UnPostedJbDet_Get.getString("WO_NO"));

                }
                rs_UnPostedJbDet_Get.close();
                ps_UnPostedJbDet_Get.close();
                vec_Result.addElement(objUnPostedRadlProductionDetails);
            }
            rs_UnpostedRadlProdId_Get.close();
            ps_UnpostedRadlProdId_Get.close();
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }
            throw new ProductionException("WOEC00","GENERAL SQL ERROR",sqle.toString());
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


        logger.info("GET UNPOSTED RADL PRODN DETAILS ENDS");


        return vec_Result;
    }
    public RadialProductionDetails getRadlProductionDetails(int radlId) throws ProductionException,SQLException
    {
        RadialProductionDetails objRadialProductionDetails = new RadialProductionDetails();
        ProductionJobDetails objProductionJobDetails = new ProductionJobDetails();
        Hashtable ht_ProdDet_Get = new Hashtable();
        Vector vec_Emp_Det = new Vector();
        DBConnection con = null;

        logger.info("GET RADL PRODUCTION DETAILS STARTS");

        con = DBConnectionFactory.getConnection();

        ht_ProdDet_Get.put("RADL_ID", new Integer(radlId));
        try
        {
            PreparedStatement ps_ProdDet_Get = con.executeStatement(SQLMaster.GET_RADL_PROD_DETAILS_SQL_QUERY,ht_ProdDet_Get);
            ResultSet rs_ProdDet_Get = ps_ProdDet_Get.executeQuery();

            if(rs_ProdDet_Get.next())
            {
                //set Production Details //
                objRadialProductionDetails.setRadlId(radlId);
                objRadialProductionDetails.setMcCode(rs_ProdDet_Get.getString("MC_CDE"));
                objRadialProductionDetails.setRadlCrntDate(rs_ProdDet_Get.getTimestamp("RADL_CRNT_DATE"));
                objRadialProductionDetails.setShiftId(rs_ProdDet_Get.getInt("SHIFT_ID"));
                objRadialProductionDetails.setShiftName(rs_ProdDet_Get.getString("SHIFT_NAME"));
                objRadialProductionDetails.setRadlWorkType(rs_ProdDet_Get.getString("RADL_WORK_TYP"));
                objRadialProductionDetails.setRadlIncntvFlg(rs_ProdDet_Get.getString("RADL_INCNTV_FLG").equals("1"));
                objRadialProductionDetails.setWoJbId(rs_ProdDet_Get.getInt("WOJB_ID"));
                objRadialProductionDetails.setWoNo(rs_ProdDet_Get.getString("WO_NO"));
                objRadialProductionDetails.setWoId(rs_ProdDet_Get.getInt("WO_ID"));
                objRadialProductionDetails.setRadlQtySnos(rs_ProdDet_Get.getString("RADL_QTY_SNOS"));
                objRadialProductionDetails.setRadlTotQty(rs_ProdDet_Get.getInt("RADL_TOT_QTY"));
                objRadialProductionDetails.setRadlStartOpn(rs_ProdDet_Get.getInt("RADL_START_OPN"));
                objRadialProductionDetails.setRadlEndOpn(rs_ProdDet_Get.getInt("RADL_END_OPN"));
                objRadialProductionDetails.setRadlStdHrs(rs_ProdDet_Get.getFloat("RADL_STD_HRS"));
                objRadialProductionDetails.setRadlTotHrs(rs_ProdDet_Get.getFloat("RADL_TOT_HRS"));
                objRadialProductionDetails.setRadlUpdatePyrl(rs_ProdDet_Get.getString("RADL_UPDATE_PYRL").equals("1"));
                objRadialProductionDetails.setRadlUpdateWo(rs_ProdDet_Get.getString("RADL_UPDATE_WO").equals("1")?true:false);
                objRadialProductionDetails.setRadlPostFlg(rs_ProdDet_Get.getString("RADL_POST_FLG").equals("1")?true:false);
                objRadialProductionDetails.setDeleted(rs_ProdDet_Get.getString("IS_DLTD").equals("1")?true:false);
                objRadialProductionDetails.setModifyCount(rs_ProdDet_Get.getInt("RADL_MODIFYCOUNT"));
                if(objRadialProductionDetails.getModifyCount() == 0)
                objRadialProductionDetails.setCreatedBy(rs_ProdDet_Get.getString("RADL_CREATEDBY"));
                else
                objRadialProductionDetails.setModifiedBy(rs_ProdDet_Get.getString("RADL_CREATEDBY"));	
                objRadialProductionDetails.setRadlDateStamp(rs_ProdDet_Get.getTimestamp("RADL_DATESTAMP"));
                objRadialProductionDetails.setRadlIsValid(rs_ProdDet_Get.getInt("RADL_ISVALID"));
                objRadialProductionDetails.setRadlDiameter(rs_ProdDet_Get.getDouble("RADL_DMTR"));
                objRadialProductionDetails.setRadlLength(rs_ProdDet_Get.getDouble("RADL_LENGTH"));
                objRadialProductionDetails.setRadlPreDiameter(rs_ProdDet_Get.getDouble("RADL_PRDMTR"));
                objRadialProductionDetails.setRadlCompletionFlg(rs_ProdDet_Get.getString("RADL_COMPLETION_FLG").equals("1")?true:false);
                objRadialProductionDetails.setRadlMatlTypeId(rs_ProdDet_Get.getInt("RADL_MATL_TYP_ID"));
                objRadialProductionDetails.setRadlNoOfHoles(rs_ProdDet_Get.getInt("RADL_NO_OF_HOLES"));
                ///set Job Details - starts///
                //objProductionJobDetails.setJobId(rs_ProdDet_Get.getInt("JB_ID"));
                objProductionJobDetails.setJobName(rs_ProdDet_Get.getString("JB_NAME"));
                objProductionJobDetails.setDwgNo(rs_ProdDet_Get.getString("JB_DWG_NO"));
                objProductionJobDetails.setRvsnNo(rs_ProdDet_Get.getString("JB_RVSN_NO"));
                objProductionJobDetails.setMatlType(rs_ProdDet_Get.getString("JB_MATL_TYP"));

                objRadialProductionDetails.setObjProductionJobDetails(objProductionJobDetails);
                //set Job Details - Ends///

                //get All Employee Details of the Above Production Record
                PreparedStatement ps_ProdEmp_Det = con.executeStatement(SQLMaster.GET_RADL_PROD_EMP_DETAILS_SQL_QUERY,ht_ProdDet_Get);
                ResultSet rs_ProdEmp_Det = ps_ProdEmp_Det.executeQuery();

                while(rs_ProdEmp_Det.next())
                {
                    EmployeeDtyOtDetails objEmployeeDtyOtDetails = new EmployeeDtyOtDetails();

                    objEmployeeDtyOtDetails.setEmpId(rs_ProdEmp_Det.getInt("EMP_ID"));
                    objEmployeeDtyOtDetails.setEmpName(rs_ProdEmp_Det.getString("EMP_NAME"));
                    objEmployeeDtyOtDetails.setEmpCode(rs_ProdEmp_Det.getString("EMP_CDE"));
                    objEmployeeDtyOtDetails.setEmpTypdId(rs_ProdEmp_Det.getInt("EMP_TYP_ID"));
                    objEmployeeDtyOtDetails.setEmpType(rs_ProdEmp_Det.getString("EMP_TYP_NAME"));
                    objEmployeeDtyOtDetails.setDutyHrs(rs_ProdEmp_Det.getFloat("DT_HRS"));
                    objEmployeeDtyOtDetails.setOtHrs(rs_ProdEmp_Det.getFloat("OT_HRS"));
                    objEmployeeDtyOtDetails.setDutySlryHrs(rs_ProdEmp_Det.getFloat("DT_SLRY_HRS"));
                    objEmployeeDtyOtDetails.setOtSlryHrs(rs_ProdEmp_Det.getFloat("OT_SLRY_HRS"));
                    objEmployeeDtyOtDetails.setIncntvSlryHrs(rs_ProdEmp_Det.getFloat("INCNTV_SLRY_HRS"));

                    //Add Each Employee Details into Vector
                    vec_Emp_Det.addElement(objEmployeeDtyOtDetails);


                }
                rs_ProdEmp_Det.close();
                ps_ProdEmp_Det.close();
                //set Employee Details Vector into Prod Object
                objRadialProductionDetails.setRadlEmpHrsDetails(vec_Emp_Det);
            }
            else
            {
                throw new ProductionException("RPMEC007","RECORD NOT FOUND","");
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
            throw new ProductionException("RPMEC000","GENERAL SQL ERROR",sqle.toString());
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

        logger.info("GET RADIAL PRODUCTION DETAILS ENDS");

        return objRadialProductionDetails;
    }

    public HashMap postRadlProductionDetails(Vector vec_RadlProdIds) throws ProductionException, SQLException
    {

        int RadlProdId = 0;
        Hashtable ht_PostProdn = new Hashtable();
        Hashtable ht_woJbStatId_Get = new Hashtable();
        Hashtable ht_OpnsStatus_Change = new Hashtable();
        Hashtable ht_Status_Change = new Hashtable();
        HashMap hm_Result = new HashMap();
        int woJbStatId = 0;

        String qtySnos = "";

        int startOpn = 0;
        int endOpn = 0;
        int woJbId = 0;
        int woId = 0;
        String complteFlg = "";
        String workType = "";


        if(vec_RadlProdIds == null)
        {
            throw new ProductionException("RPMEC008","PRODUCTION IDs VECTOR OBJECT IS NULL","");

        }

        try
        {

            con = DBConnectionFactory.getConnection();
            con.startTransaction();
            for(int i = 0;i<vec_RadlProdIds.size();i++)
            {
                RadlProdId = ((Integer)vec_RadlProdIds.elementAt(i)).intValue();
                ht_PostProdn.put("RADL_ID",new Integer(RadlProdId));
                int result = con.executeUpdateStatement(SQLMaster.RADL_PRODN_DETAILS_POST_SQL_QUERY,ht_PostProdn);
                if(result>=1)
                {
                    hm_Result.put(vec_RadlProdIds.get(i),new Integer(0));
                    logger.info(vec_RadlProdIds.get(i)+" : RADL PRODUCTION RECORD POSTED");

                    PreparedStatement ps_RadlProdnDet = con.executeStatement(SQLMaster.RADL_PRODN_DETAILS_SELECT_SQL_QUERY,ht_PostProdn);
                    ResultSet rs_RadlProdnDet = ps_RadlProdnDet.executeQuery();
                    if(rs_RadlProdnDet.next())
                    {
                        woJbId = rs_RadlProdnDet.getInt("WOJB_ID");
                        qtySnos = rs_RadlProdnDet.getString("RADL_QTY_SNOS");
                        //totQty = rs_RadlProdnDet.getInt("RADL_TOT_QTY");
                        startOpn = rs_RadlProdnDet.getInt("RADL_START_OPN");
                        endOpn = rs_RadlProdnDet.getInt("RADL_END_OPN");
                        complteFlg = rs_RadlProdnDet.getString("RADL_COMPLETION_FLG");
                        workType = rs_RadlProdnDet.getString("RADL_WORK_TYP");



                        if(complteFlg.equals("1"))
                        {
                            //production may be for more than one job quantity...so loop comes
                            int j = 0;
                            StringTokenizer stn = new StringTokenizer(qtySnos,",");
                            if(workType.equalsIgnoreCase("n"))
                            {
                                ResultSet rs_woJbStatId = null;
                                PreparedStatement ps_woJbStatId = null;
                                while(stn.hasMoreTokens())
                                {
                                    j = Integer.parseInt(stn.nextToken().trim());
                                    ht_woJbStatId_Get.put("WOJB_ID",new Integer(woJbId));
                                    ht_woJbStatId_Get.put("WOJBSTAT_SNO",new Integer(j));

                                    ps_woJbStatId = con.executeStatement(SQLMaster.PRODN_WOJBSTATID_GET_SQL_QUERY,ht_woJbStatId_Get);
                                    rs_woJbStatId = ps_woJbStatId.executeQuery();
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
                                        rs_Ids_Get.close();
                                        ps_Ids_Get.close();
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
                                        rs_Ids_Get.close();
                                        ps_Ids_Get.close();
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
                                                //	to make particular work order status to 'C'
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
                            else
                            {

                                while(stn.hasMoreTokens())
                                {
                                    j = Integer.parseInt(stn.nextToken().trim());
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
                                        result = con.executeUpdateStatement(SQLMaster.PRODN_OPN_STATUS_CHANGE_FOR_REWORK_SQL_QUERY,ht_OpnsStatus_Change);
                                    }

                                    //status checking for remaining operations of particular quantity//
                                    PreparedStatement ps_OpnsStatus_Check = con.executeStatement(SQLMaster.PRODN_OPN_STATUS_CHECK_FOR_REWORK_SQL_QUERY,ht_OpnsStatus_Change);
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
                                        ps_OpnsStatus_Check = con.executeStatement(SQLMaster.PRODN_OPN_STATUS_CHECK_F_FOR_REWORK_SQL_QUERY,ht_OpnsStatus_Change);
                                        rs_OpnsStatus_Check = ps_OpnsStatus_Check.executeQuery();
                                        if(rs_OpnsStatus_Check.next())
                                        {
                                            //to make particular quantity sno status to 'C'
                                            result = con.executeUpdateStatement(SQLMaster.PRODN_WOJBSTATSNO_STATUS_CHANGETOC_SQL_QUERY,ht_Status_Change);

                                        }
                                        else
                                        {
                                            //to make particular quantity sno status to 'F'
                                            result = con.executeUpdateStatement(SQLMaster.PRODN_WOJBSTATSNO_STATUS_CHANGETOC_SQL_QUERY,ht_Status_Change);
                                        }
                                        //status checking for remaining job quantities of particular job//
                                        PreparedStatement ps_WoJobQtysStatus_Check = con.executeStatement(SQLMaster.PRODN_WOJBSTATSNO_STATUS_CHECK_SQL_QUERY,ht_Status_Change);
                                        ResultSet rs_WoJobQtysStatus_Check = ps_WoJobQtysStatus_Check.executeQuery();

                                        if(rs_WoJobQtysStatus_Check.next())
                                        {
                                            //
                                            //to make particular job status to 'A'
                                            result = con.executeUpdateStatement(SQLMaster.PRODN_WOJB_STATUS_CHANGETOA_SQL_QUERY,ht_Status_Change);

                                            //to make particular work order status to 'A'
                                            result = con.executeUpdateStatement(SQLMaster.PRODN_WO_STATUS_CHANGETOA_SQL_QUERY,ht_Status_Change);

                                        }
                                        else
                                        {
                                            ps_WoJobQtysStatus_Check = con.executeStatement(SQLMaster.PRODN_WOJBSTATSNO_STATUS_F_CHECK_SQL_QUERY,ht_Status_Change);
                                            rs_WoJobQtysStatus_Check = ps_WoJobQtysStatus_Check.executeQuery();
                                            if(rs_WoJobQtysStatus_Check.next())
                                            {
                                                //to make particular job status to 'C'
                                                result = con.executeUpdateStatement(SQLMaster.PRODN_WOJB_STATUS_CHANGETOC_SQL_QUERY,ht_Status_Change);
                                            }
                                            else
                                            {
                                                //to make particular job status to 'F'
                                                result = con.executeUpdateStatement(SQLMaster.PRODN_WOJB_STATUS_CHANGETOF_SQL_QUERY,ht_Status_Change);
                                            }
                                            rs_WoJobQtysStatus_Check.close();
                                            ps_WoJobQtysStatus_Check.close();
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

                        }
                        else
                        {
                            int j = 0;
                            StringTokenizer stn = new StringTokenizer(qtySnos,",");
                            while(stn.hasMoreTokens())
                            {
                                j = Integer.parseInt(stn.nextToken().trim());
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

                                    result = con.executeUpdateStatement(SQLMaster.OPN_ITRN_CHANGE_SQL_QUERY,ht_OpnsStatus_Change);
                                }
                            }
                        }
                    }
                    rs_RadlProdnDet.close();
                    con.commitTransaction();
                }
                else
                {
                    hm_Result.put(vec_RadlProdIds.get(i),new Integer(1));
                    logger.info(vec_RadlProdIds.get(i)+" : RADL PRODUCTION RECORD NOT POSTED");
                    con.rollBackTransaction();
                }


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

            throw new ProductionException("RPMEC000","GENERAL SQL ERROR",sqle.toString());
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

        return hm_Result;

    }

    public HashMap getAllRadialProductionDetails(Filter filters[],String sortBy,boolean ascending,int startIndex,int displayCount) throws ProductionException, SQLException
    {
        HashMap hm_Result = new HashMap();
        Vector vec_RadlProd_Det = new Vector();

        logger.info("GET ALL RADILA PRODUCTION DETAILS FILTER STARTS");

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
            tot_Rec_Cnt = con.getRowCountWithComplexFilters(filters,SQLMaster.GET_ALL_RADIAL_PRODUCTION_DETAILS_FILTER_QUERY);

            //Finding end index for the query
            int eIndex = startIndex + displayCount;


            // filters,tablename,startindex,endindex,sorting column name and sortorder are passed to the function and  query will be received
            String Query = con.buildDynamicQuery(filters,startIndex,eIndex,sortBy,ascending,SQLMaster.GET_ALL_RADIAL_PRODUCTION_DETAILS_FILTER_QUERY);
            if(BuildConfig.DMODE)
            {
                // total records and query
                System.out.println("TOTAL NUMBER OF RECORDS : " + tot_Rec_Cnt);
                System.out.println("QUERY : " + Query);
            }

            logger.info("Total Records : " + tot_Rec_Cnt);
            logger.info("Query : " + Query);


            ResultSet rs_RadlProdn_Details_Get =  con.executeRownumStatement(Query);
            while(rs_RadlProdn_Details_Get.next())
            {
                RadialProductionDetails objRadialProductionDetails = new RadialProductionDetails();
                objRadialProductionDetails.setRadlId(rs_RadlProdn_Details_Get.getInt("RADL_ID"));
                objRadialProductionDetails.setMcCode(rs_RadlProdn_Details_Get.getString("MC_CDE"));
                objRadialProductionDetails.setRadlCrntDate(rs_RadlProdn_Details_Get.getTimestamp("RADL_CRNT_DATE"));
                objRadialProductionDetails.setShiftId(rs_RadlProdn_Details_Get.getInt("SHIFT_ID"));
                objRadialProductionDetails.setShiftName(rs_RadlProdn_Details_Get.getString("SHIFT_NAME"));
                objRadialProductionDetails.setWoNo(rs_RadlProdn_Details_Get.getString("WO_NO"));
                objRadialProductionDetails.setRadlDateStamp(rs_RadlProdn_Details_Get.getTimestamp("RADL_DATESTAMP"));
                objRadialProductionDetails.setRadlIsValid(rs_RadlProdn_Details_Get.getInt("RADL_ISVALID"));

                /*objRadialProductionDetails.setRadlWorkType(rs_RadlProdn_Details_Get.getString("RADL_WORK_TYP"));
                 objRadialProductionDetails.setRadlIncntvFlg(rs_RadlProdn_Details_Get.getString("RADL_INCNTV_FLG").equals("1")?true:false);
                 objRadialProductionDetails.setWoJbId(rs_RadlProdn_Details_Get.getInt("WOJB_ID"));
                 objRadialProductionDetails.setRadlQtySnos(rs_RadlProdn_Details_Get.getString("RADL_QTY_SNOS"));
                 objRadialProductionDetails.setRadlTotQty(rs_RadlProdn_Details_Get.getInt("RADL_TOT_QTY"));
                 objRadialProductionDetails.setRadlStartOpn(rs_RadlProdn_Details_Get.getInt("RADL_START_OPN"));
                 objRadialProductionDetails.setRadlEndOpn(rs_RadlProdn_Details_Get.getInt("RADL_END_OPN"));
                 objRadialProductionDetails.setRadlStdHrs(rs_RadlProdn_Details_Get.getFloat("RADL_STD_HRS"));
                 objRadialProductionDetails.setRadlTotHrs(rs_RadlProdn_Details_Get.getFloat("RADL_TOT_HRS"));
                 objRadialProductionDetails.setRadlUpdatePyrl(rs_RadlProdn_Details_Get.getString("RADL_UPDATE_PYRL").equals("1")?true:false);
                 objRadialProductionDetails.setRadlUpdateWo(rs_RadlProdn_Details_Get.getString("RADL_UPDATE_WO").equals("1")?true:false);
                 objRadialProductionDetails.setRadlPostFlg(rs_RadlProdn_Details_Get.getString("RADL_POST_FLG").equals("1")?true:false);
                 objRadialProductionDetails.setRadlMatlTypeId(rs_RadlProdn_Details_Get.getInt("RADL_MATL_TYP_ID"));
                 objRadialProductionDetails.setRadlDiameter(rs_RadlProdn_Details_Get.getFloat("RADL_DMTR"));
                 objRadialProductionDetails.setRadlLength(rs_RadlProdn_Details_Get.getFloat("RADL_LENGTH"));
                 objRadialProductionDetails.setRadlNoOfHoles(rs_RadlProdn_Details_Get.getInt("RADL_NO_OF_HOLES"));
                 objRadialProductionDetails.setRadlPreDiameter(rs_RadlProdn_Details_Get.getFloat("RADL_PRDMTR"));
                 objRadialProductionDetails.setRadlCompletionFlg(rs_RadlProdn_Details_Get.getString("RADL_COMPLETION_FLG").equals("1")?true:false);
                 objRadialProductionDetails.setDeleted(rs_RadlProdn_Details_Get.getString("IS_DLTD").equals("1")?true:false);*/

                //to set Job Details of Radial Production
                ProductionJobDetails objProductionJobDetails = new ProductionJobDetails();
                objProductionJobDetails.setJobName(rs_RadlProdn_Details_Get.getString("JB_NAME"));
                /*objProductionJobDetails.setDwgNo(rs_RadlProdn_Details_Get.getString("JB_DWG_NO"));
                 objProductionJobDetails.setRvsnNo(rs_RadlProdn_Details_Get.getString("JB_RVSN_NO"));
                 objProductionJobDetails.setMatlType(rs_RadlProdn_Details_Get.getString("JB_MATL_TYP"));*/

                objRadialProductionDetails.setObjProductionJobDetails(objProductionJobDetails);

                vec_RadlProd_Det.addElement(objRadialProductionDetails);

            }
            hm_Result.put("TotalRecordCount",new Integer(tot_Rec_Cnt));
            hm_Result.put("RadialProductionDetails",vec_RadlProd_Det);
            rs_RadlProdn_Details_Get.getStatement().close();
            rs_RadlProdn_Details_Get.close();

        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
                logger.info("GENERAL EXCEPTION");
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
            catch(SQLException ex)
            {
                if(BuildConfig.DMODE)
                {
                    logger.error("EXCEPTION WHILE CLOSING CONNECTION");
                }
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }

        logger.info("GET ALL RADIAL PRODUCTION DETAILS FILTER ENDS");

        return hm_Result;

    }
    public LinkedHashMap getAllMatlTypes() throws ProductionException, SQLException
    {
        LinkedHashMap hm_Result = new LinkedHashMap();
        ResultSet rs_MatlTyp_Get = null;
        PreparedStatement ps = null;


        logger.info("GET RADL MATL TYPE LIST STARTS");

        con = DBConnectionFactory.getConnection();
        try
        {
            ps = con.executeStatement(SQLMaster.GET_RADL_MATL_TYPE_LIST);
            rs_MatlTyp_Get = ps.executeQuery();
            while(rs_MatlTyp_Get.next())
            {
                hm_Result.put(new Integer(rs_MatlTyp_Get.getInt("RADL_MATL_TYP_ID")),rs_MatlTyp_Get.getString("RADL_MATL_TYP"));
            }
            rs_MatlTyp_Get.close();
            ps.close();
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }
            logger.error("EXCEPTION WHILE SELECTING WORK ORDER LIST");
            throw new ProductionException("WOEC000","GENERAL SQL ERROR",sqle.toString());
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


        logger.info("GET RADL MATL TYPE LIST ENDS");


        return hm_Result;

    }

    public HashMap makeRadlProductionValid(Vector radlIds) throws ProductionException, SQLException
    {
        int result = 0;
        DBConnection con = null;
        HashMap hm_Result = new HashMap();

        logger.info("MAKE RADIAL PRODUCTION VALID STARTS");

        if(radlIds == null)
        {
            logger.error("RADIAL PRODUCTION VECTOR OBJECT IS NULL");
            throw new ProductionException("PREC00","RADIAL PRODUCTION VECTOR OBJECT IS NULL","");
        }

        try
        {
            con = DBConnectionFactory.getConnection();
            con.startTransaction();
            for(int i=0;i<radlIds.size();i++)
            {
                Hashtable ht_Prod_Id = new Hashtable();


                ht_Prod_Id.put("RADL_ID",(Integer)radlIds.get(i));
                result = con.executeUpdateStatement(SQLMaster.RADL_MAKE_VALID_SQL_QUERY,ht_Prod_Id);

                if(result>=1)
                {
                    con.commitTransaction();
                    hm_Result.put(radlIds.get(i),new Integer(0));
                    logger.info(radlIds.get(i)+" : RADIAL PRODUCTION RECORD VALIDATED");

                }
                else
                {
                    con.rollBackTransaction();
                    hm_Result.put(radlIds.get(i),new Integer(1));
                    logger.info(radlIds.get(i)+" : RADIAL PRODUCTION RECORD NOT VALIDATED");

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

        logger.info("MAKE RADIAL PRODUCTION VALID ENDS");

        return hm_Result;

    }
    public HashMap makeRadlProductionInValid(Vector radlIds) throws ProductionException, SQLException
    {
        int result = 0;
        DBConnection con = null;
        HashMap hm_Result = new HashMap();

        logger.info("MAKE RADIAL PRODUCTION INVALID STARTS");

        if(radlIds == null)
        {
            logger.error("RADIAL PRODUCTION VECTOR OBJECT IS NULL");
            throw new ProductionException("PREC00","RADIAL PRODUCTION VECTOR OBJECT IS NULL","");
        }

        try
        {
            con = DBConnectionFactory.getConnection();
            con.startTransaction();
            for(int i=0;i<radlIds.size();i++)
            {
                Hashtable ht_Prod_Id = new Hashtable();
                ht_Prod_Id.put("RADL_ID",(Integer)radlIds.get(i));
                result = con.executeUpdateStatement(SQLMaster.RADL_MAKE_INVALID_SQL_QUERY,ht_Prod_Id);
                if(result>=1)
                {
                    con.commitTransaction();
                    hm_Result.put(radlIds.get(i),new Integer(0));
                    logger.info(radlIds.get(i)+" : RADIAL PRODUCTION RECORD VALIDATED");

                }
                else
                {
                    con.rollBackTransaction();
                    hm_Result.put(radlIds.get(i),new Integer(1));
                    logger.info(radlIds.get(i)+" : RADIAL PRODUCTION RECORD NOT VALIDATED");

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

        logger.info("MAKE RADIAL PRODUCTION INVALID ENDS");

        return hm_Result;

    }

    public static void main(String args[]) throws ProductionException,SQLException
    {
        RadlProductionDetailsManager objRProdDetails = new RadlProductionDetailsManager();
        Filter[] filters = new Filter[2];
        filters[0] = new Filter();
        filters[0].setFieldName("JB_NAME");
        filters[0].setFieldValue("j");
        filters[0].setSpecialFunction("AnyWhere");


        filters[1] = new Filter();
        filters[1].setFieldName("RADL_CRNT_DATE");
        filters[1].setFieldValue("01-NOV-2004$31-DEC-2004");
        filters[1].setSpecialFunction("DateBetween");

        //boolean ascending = true;
        //int startIndex = 0;
        //String sortBy = "MC_CDE";
        //int displayCount = 10;
        //HashMap hm = objRProdDetails.getAllRadialProductionDetails(filters,sortBy,ascending,startIndex,displayCount);
        HashMap hm = objRProdDetails.getAllMatlTypes();
        System.out.println("Result :"+hm);
        //	System.out.println(objRProdDetails.getRadlProductionDetails(21));
        System.out.println(objRProdDetails.isModifyForRadl(43));


    }

}

/***
 $Log: RadlProductionDetailsManager.java,v $
 Revision 1.69  2005/12/29 07:11:59  kduraisamy
 radl production update query(mc hrs reduce query) modified.

 Revision 1.68  2005/12/28 09:47:35  kduraisamy
 in emp Hrs invalid  emp code included.

 Revision 1.67  2005/10/06 10:31:12  vkrishnamoorthy
 Modified By in View, error corrected.

 Revision 1.66  2005/09/30 11:02:30  vkrishnamoorthy
 Update payroll set to 0 in update.

 Revision 1.65  2005/09/27 05:50:22  kduraisamy
 duty 8 hrs validation added.

 Revision 1.64  2005/09/15 07:36:09  kduraisamy
 rs.getStatement().close() added in Filters.

 Revision 1.63  2005/09/10 13:18:42  kduraisamy
 order by clause added.

 Revision 1.62  2005/08/23 09:24:49  kduraisamy
 CHECK EMP TYPE SQL QUERY ADDED.

 Revision 1.61  2005/08/08 06:48:32  kduraisamy
 modify count field added.

 Revision 1.60  2005/08/04 12:00:10  kduraisamy
 EMP_CDE ADDED FOR PROD VIEW.

 Revision 1.59  2005/08/01 04:38:05  kduraisamy
 wo order modification changed.

 Revision 1.58  2005/07/12 11:37:26  kduraisamy
 imports organized

 Revision 1.57  2005/07/06 10:30:42  vkrishnamoorthy
 prod incentive flg is varchar.

 Revision 1.56  2005/07/06 06:39:59  kduraisamy
 checkincentive() added.

 Revision 1.55  2005/07/04 09:48:53  kduraisamy
 payroll calculate Sal Hrs() method changed for radl,nprod,pop.

 Revision 1.54  2005/06/29 12:43:58  smurugesan
 if con ! = null added in finally

 Revision 1.53  2005/06/01 14:37:15  kduraisamy
 log adding changed.

 Revision 1.52  2005/05/30 13:10:22  kduraisamy
 POP AND RADL_DUMMYCOUNT ADDED.

 Revision 1.51  2005/05/16 18:35:05  kduraisamy
 specific throws addded for mysql.

 Revision 1.50  2005/05/16 15:42:43  kduraisamy
 specific throws addded for mysql.

 Revision 1.49  2005/04/22 10:10:51  kduraisamy
 logger class changed.

 Revision 1.48  2005/04/19 13:13:33  kduraisamy
 resultset properly closed.

 Revision 1.47  2005/04/18 12:28:06  kduraisamy
 executeStatement() return type changed.

 Revision 1.46  2005/04/07 09:20:19  kduraisamy
 MODE IS CHANGED TO DMODE.

 Revision 1.45  2005/04/07 07:36:10  kduraisamy
 if(BuildConfig.DMODE) ADDED. AND LEVEL INCLUDED PROPERLY.

 Revision 1.44  2005/03/30 08:23:40  kduraisamy
 excecuteRownumStatement() called for filters.

 Revision 1.43  2005/03/26 11:35:09  smurugesan
 ERROR CODES ARE ALTERED.

 Revision 1.42  2005/03/26 10:09:00  smurugesan
 ROWNUM RESET QUERY REMOVED.

 Revision 1.41  2005/03/11 06:20:16  kduraisamy
 RESET ROWNUM QUERY ADDED BEFORE FILTER METHODS..

 Revision 1.40  2005/03/10 07:27:36  kduraisamy
 CHECKING BEFORE THE FILTER METHODS(buildDynamicQuery(),etc) added.

 getDate is changed to getTimestamp().

 Revision 1.39  2005/03/09 11:31:25  kduraisamy
 indentation.

 Revision 1.38  2005/03/09 07:26:33  kduraisamy
 indentation.

 Revision 1.37  2005/03/05 09:52:22  kduraisamy
 signature changed for addProductionDetails().

 Revision 1.36  2005/03/04 12:42:27  kduraisamy
 Indentation.

 Revision 1.35  2005/03/04 11:54:53  vkrishnamoorthy
 DBConnection changes made.

 Revision 1.34  2005/02/15 09:15:47  smurugesan
 connection object added as a checkempHrs()'s parameter.

 Revision 1.33  2005/02/15 06:27:17  kduraisamy
 RADL UPDATE MODIFIED.

 Revision 1.32  2005/02/14 11:10:04  kduraisamy
 unwanted cast removed.

 Revision 1.31  2005/02/11 06:55:15  kduraisamy
 unused variables removed.

 Revision 1.30  2005/02/08 06:00:34  kduraisamy
 DBConnection con object passed to checkHrsAccounted().

 Revision 1.29  2005/02/04 19:07:19  kduraisamy
 rework entered operations get code included.

 Revision 1.28  2005/02/03 06:43:06  sponnusamy
 .trim() included.

 Revision 1.27  2005/02/01 17:47:43  smurugesan
 Exception throws for Object null.in Post productionDetails.

 Revision 1.26  2005/02/01 17:16:42  kduraisamy
 posting method return type changed to HashMap.

 Revision 1.25  2005/01/31 10:37:21  kduraisamy
 posting modified.iteration no increment added.

 Revision 1.24  2005/01/30 10:27:45  kduraisamy
 incntv calculation algo changed.

 Revision 1.23  2005/01/29 10:22:42  kduraisamy
 completion flag included before operations status change.

 Revision 1.22  2005/01/28 14:55:51  kduraisamy
 isModify() method added.

 Revision 1.21  2005/01/27 14:47:10  kduraisamy
 if(BuildConfig.DMODE)
 {
 }
 properly inclued inside the catch block.

 Revision 1.20  2005/01/25 07:49:57  kduraisamy
 filter setters modified.

 Revision 1.18  2005/01/20 10:19:44  kduraisamy
 method getRadlProductionDetails() added.

 Revision 1.17  2005/01/18 06:41:31  kduraisamy
 update added.

 Revision 1.16  2005/01/17 06:32:46  kduraisamy
 con.rollBackTransaction() added before throw ProductionException().

 Revision 1.15  2005/01/17 06:12:40  kduraisamy
 make valid invalid added.

 Revision 1.14  2005/01/12 06:59:49  kduraisamy
 postRESULT = true added.

 Revision 1.13  2005/01/12 06:54:18  kduraisamy
 SHIFT NAME INCLUDED IN VIEW UNPOSTED DETAILS METHOD.

 Revision 1.12  2005/01/12 05:01:11  kduraisamy
 prod is changed to radl.

 Revision 1.11  2005/01/10 08:51:39  kduraisamy
 wo_no included in RadialProductionDetails.

 Revision 1.10  2005/01/10 07:07:18  kduraisamy
 RADL END HRS IS CHANGED TO RADL_TOT_HRS.

 Revision 1.9  2005/01/08 13:07:52  kduraisamy
 Radl Id get sql query name changed.

 Revision 1.8  2005/01/08 11:42:51  kduraisamy
 getAllRadlMatlTypes() added.

 Revision 1.7  2005/01/06 13:00:17  kduraisamy
 radl_matl_typ changed to radl_matl_typ_id

 Revision 1.6  2005/01/06 09:43:52  kduraisamy
 QUERY SEPARATED FOR PROD_ACCNTNG ADD AND UPDATE

 Revision 1.5  2005/01/05 12:10:15  kduraisamy
 empHrs Calculation algorithm modified.

 Revision 1.4  2004/12/30 05:44:18  kduraisamy
 radl_qty_startsno changed to radl_qty_snos.and iterator included instead of for loop..

 Revision 1.3  2004/12/23 10:15:08  sduraisamy
 filter method included

 ***/
