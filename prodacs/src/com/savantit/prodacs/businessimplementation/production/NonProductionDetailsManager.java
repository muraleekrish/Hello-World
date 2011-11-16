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
public class NonProductionDetailsManager
{
    DBConnection con=null;
    static Logger logger = Logger.getLogger(NonProductionDetailsManager.class);
    public NonProductionDetailsManager()
    {
        logger.addAppender(LoggerOutput.getAppender());
        logger.setLevel(ProdacsConfig.LOGGER_LEVEL);
    }


    public HashMap addNewNonProductionDetails(NonProductionDetails[] objNonProductionDetailsList) throws ProductionException, SQLException
    {
        int nonProdId = 0;
        float otHrs = 0;
        float otSlryHrs = 0;
        float dtyHrs = 0;
        float dtySlryHrs = 0;
        int empId = 0;
        int empTypId = 0;
        //String empType = "";
        Vector vec_Success = new Vector();
        Vector vec_EmpFail = new Vector();
        Vector vec_MachineFail = new Vector();
        Hashtable ht_NonProdn_Add = new Hashtable();
        Hashtable ht_EmpHrs_Add = new Hashtable();
        Hashtable ht_ProdAccntng_Add = new Hashtable();
        HashMap hm_Result = new HashMap();
        Vector vec_EmpHrs = new Vector();
        Vector vecDutyFail = new Vector();
        PreparedStatement ps = null;
        PreparedStatement psEmpTypCheck = null;
        ResultSet rsEmpTypCheck = null;
        Hashtable ht = new Hashtable();
        ProductionDetailsManager objProductionDetailsManager = new ProductionDetailsManager();
        int result = 0;

        logger.info("NON-PRODUCTION ADD STARTS");

        if(BuildConfig.DMODE)
            System.out.println("ADD NON-PRODUCTION DETAILS");
        if(objNonProductionDetailsList.length == 0)
        {

            logger.error("NON-PRODUCTION DETAILS OBJ NULL");
            throw new ProductionException("NPMEC001","NON-PRODUCTION DETAILS OBJ NULL","");
        }
        try
        {
            con = DBConnectionFactory.getConnection();
            con.startTransaction();
            for(int i = 0;i<objNonProductionDetailsList.length;i++)
            {

                ht_NonProdn_Add.put("MC_CDE",objNonProductionDetailsList[i].getMcCode());
                ht_NonProdn_Add.put("NPROD_CRNT_DATE",objNonProductionDetailsList[i].getNonProdnCrntDate());
                ht_NonProdn_Add.put("SHIFT_ID",new Integer(objNonProductionDetailsList[i].getShiftId()));
                ht_NonProdn_Add.put("NPROD_IDL_BRKDWN",objNonProductionDetailsList[i].getIdlOrBkDown());
                ht_NonProdn_Add.put("RSN_ID",new Integer(objNonProductionDetailsList[i].getRsnId()));
                ht_NonProdn_Add.put("NPROD_TOT_HRS",new Float(objNonProductionDetailsList[i].getNprodTotHrs()));
                ht_NonProdn_Add.put("NPROD_CREATEDBY",objNonProductionDetailsList[i].getCreatedBy());
                result = con.executeUpdateStatement(SQLMaster.NONPRODN_DETAILS_ADD_SQL_QUERY,ht_NonProdn_Add);
                if(result > 0)
                {
                    ps = con.executeStatement(SQLMaster.NONPROD_ID_SELECT_SQL_QUERY);
                    ResultSet rs_NonProdId_Get = ps.executeQuery();
                    if(rs_NonProdId_Get.next())
                    {
                        nonProdId = rs_NonProdId_Get.getInt(1);
                    }
                    
                    rs_NonProdId_Get.close();
                    ps.close();
                    
                    vec_EmpHrs = objNonProductionDetailsList[i].getNonprodnEmpHrsDetails();
                    //totHrs = objNonProductionDetails.getNprodTotHrs();
                    boolean flag = true;
                    for(int j = 0;j<vec_EmpHrs.size();j++)
                    {
                        otHrs = 0;
                        otSlryHrs = 0;
                        dtyHrs = 0;
                        dtySlryHrs = 0;
                        EmployeeDtyOtDetails objEmployeeDtyOtDetails = new EmployeeDtyOtDetails();
                        objEmployeeDtyOtDetails = (EmployeeDtyOtDetails)vec_EmpHrs.elementAt(j);

                        dtyHrs = objEmployeeDtyOtDetails.getDutyHrs();
                        otHrs = objEmployeeDtyOtDetails.getOtHrs();

                        empId = objEmployeeDtyOtDetails.getEmpId();
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
                        
                        rsEmpTypCheck.close();
                        psEmpTypCheck.close();
                        
                        if(val == 0)
                            if(dtyHrs > 0)
                            if(!objProductionDetailsManager.checkDutyEntry(objNonProductionDetailsList[i].getNonProdnCrntDate(), objNonProductionDetailsList[i].getShiftId(), empId, con))
                            {
                                con.rollBackTransaction();
                                flag = false;
                                HashMap hm = new HashMap();
                                hm.put(objEmployeeDtyOtDetails.getEmpName(), objNonProductionDetailsList[i]);
                                vecDutyFail.addElement(hm);
                                break;
                            }
                            
                        
                        if(val == 0)
                            check = objProductionDetailsManager.checkAvailableEmpHrs(empId,objNonProductionDetailsList[i].getNonProdnCrntDate(),objNonProductionDetailsList[i].getShiftId(),0,dtyHrs+otHrs,0,0,con);
                        if(check==0)
                        {

                            ht_EmpHrs_Add.put("NPROD_ID",new Integer(nonProdId));
                            ht_EmpHrs_Add.put("EMP_ID",new Integer(empId));
                            ht_EmpHrs_Add.put("DT_HRS",new Float(dtyHrs));
                            ht_EmpHrs_Add.put("OT_HRS",new Float(otHrs));
                            ht_EmpHrs_Add.put("DT_SLRY_HRS",new Float(dtySlryHrs));
                            ht_EmpHrs_Add.put("OT_SLRY_HRS",new Float(otSlryHrs));
                            ht_EmpHrs_Add.put("NPROD_MODIFYCOUNT",new Integer(0));

                            result = con.executeUpdateStatement(SQLMaster.NPRODN_EMPHRS_ADD_SQL_QUERY,ht_EmpHrs_Add);
                            if(result>0)
                            {
                                if(BuildConfig.DMODE)
                                    System.out.println("Employee Hrs Details Added Non-Production");
                            }
                        }
                        else
                        {
                            con.rollBackTransaction();
                            flag = false;
                            HashMap hm = new HashMap();
                            hm.put(objEmployeeDtyOtDetails.getEmpName(), objNonProductionDetailsList[i]);
                            vec_EmpFail.addElement(hm);
                            break;

                        }

                    }
                    if(flag)
                    {
                        ht_ProdAccntng_Add.put("MC_CDE",objNonProductionDetailsList[i].getMcCode());
                        ht_ProdAccntng_Add.put("PROD_DATE",objNonProductionDetailsList[i].getNonProdnCrntDate());
                        ht_ProdAccntng_Add.put("SHIFT_ID",new Integer(objNonProductionDetailsList[i].getShiftId()));
                        int check = objProductionDetailsManager.checkHrsAccounted(objNonProductionDetailsList[i].getMcCode(),objNonProductionDetailsList[i].getNonProdnCrntDate(),objNonProductionDetailsList[i].getShiftId(),0,objNonProductionDetailsList[i].getNprodTotHrs(),con);
                        if(check==0)
                        {
                            ht_ProdAccntng_Add.put("NPROD_HRS",new Float(objNonProductionDetailsList[i].getNprodTotHrs()));
                            ht_ProdAccntng_Add.put("ISHRS_ACCOUNTED","1");
                        }
                        else if(check ==1)
                        {
                            ht_ProdAccntng_Add.put("NPROD_HRS",new Float(objNonProductionDetailsList[i].getNprodTotHrs()));
                            ht_ProdAccntng_Add.put("ISHRS_ACCOUNTED","0");
                        }
                        else
                        {
                            con.rollBackTransaction();
                            vec_MachineFail.addElement(objNonProductionDetailsList[i]);
                            continue;
                        }
                        //////
                        result = con.executeUpdateStatement(SQLMaster.PRODN_ACCNTNG_UPDATE_BY_NPRODHRS_SQL_QUERY,ht_ProdAccntng_Add);

                        if(result<=0)
                        {
                            result = con.executeUpdateStatement(SQLMaster.PRODN_ACCNTNG_NPRODHRS_ADD_SQL_QUERY,ht_ProdAccntng_Add);
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
                    //////
                }


                con.commitTransaction();
                vec_Success.addElement(new Integer(nonProdId));
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
                if(sqle.toString().indexOf("PK_NPRODID")>=0)
                {
                    throw new ProductionException("NPMEC002","NON-PRODUCTION ID IS UNIQUE.UNIQUE CONSTRAINT VIOLATED",sqle.toString());
                }
                else if (sqle.toString().indexOf("FK_NPROD_MCCDE") >= 0)
                {
                    throw new ProductionException("NPMEC003","PARENT KEY MACHINE CODE NOT FOUND",sqle.toString());
                }
                else if (sqle.toString().indexOf("FK_NPROD_SHIFTID") >= 0)
                {
                    throw new ProductionException("NPMEC004","PARENT KEY SHIFT ID NOT FOUND",sqle.toString());
                }
                else if (sqle.toString().indexOf("FK_NPROD_RSNID") >= 0)
                {
                    throw new ProductionException("NPMEC005","PARENT KEY REASON ID NOT FOUND",sqle.toString());
                }
                else
                {
                    throw new ProductionException("NPMEC000","GENERAL SQL ERROR",sqle.toString());
                }
            }
            if(BuildConfig.DBASE==BuildConfig.MYSQL)
            {
                if(sqle.toString().indexOf("Duplicate entry")>=0)
                {
                    throw new ProductionException("NPMEC002","NON-PRODUCTION ID IS UNIQUE.UNIQUE CONSTRAINT VIOLATED",sqle.toString());
                }
                else if (sqle.toString().indexOf("foreign key constraint") >= 0)
                {
                    throw new ProductionException("NPMEC003","PARENT KEY MACHINE CODE OR SHIFT ID OR REASON ID NOT FOUND",sqle.toString());
                }
                else
                {
                    throw new ProductionException("NPMEC000","GENERAL SQL ERROR",sqle.toString());
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



        logger.info("NON-PRODUCTION ADD ENDS");


        hm_Result.put("Success",vec_Success);
        hm_Result.put("EmpFailure",vec_EmpFail);
        hm_Result.put("MachineFailure",vec_MachineFail);
        hm_Result.put("DutyFailure",vecDutyFail);
        return hm_Result;
    }
    public boolean updateNonProductionDetails(NonProductionDetails objNonProductionDetails) throws ProductionException, SQLException
    {
        boolean updateRESULT = false;
        int nprodId = 0;
        Hashtable ht_EmpHrs_Del = new Hashtable();
        Hashtable ht_MacHrs_Reduce = new Hashtable();
        Hashtable ht_Prod_Update = new Hashtable();
        Hashtable ht_EmpHrs_Update = new Hashtable();
        Hashtable ht_ProdAccntng_Update = new Hashtable();

        ProductionDetailsManager objProductionDetailsManager = new ProductionDetailsManager();
        Vector vec_EmpHrs = new Vector();
        int result;
        float otHrs = 0;
        float otSlryHrs = 0;
        float dtyHrs = 0;
        float dtySlryHrs = 0;
        int empId = 0;
        int empTypId = 0;
        PreparedStatement psEmpTypCheck = null;
        ResultSet rsEmpTypCheck = null;
        Hashtable ht = new Hashtable();
        //String empType = "";


        //delete PROD_EMP table.
        nprodId = objNonProductionDetails.getNonProdId();
        con = DBConnectionFactory.getConnection();
        con.startTransaction();
        ht_EmpHrs_Del.put("NPROD_ID",new Integer(nprodId));
        try
        {

            //to store Emp Hours entries into Log..
            result = con.executeUpdateStatement(SQLMaster.LOG_NPRODN_EMPHRS_ADD_SQL_QUERY,ht_EmpHrs_Del);
            if(result>0)
            {
                result = con.executeUpdateStatement(SQLMaster.NPRODN_EMP_HRS_DELETE_SQL_QUERY,ht_EmpHrs_Del);
                if(BuildConfig.DMODE)
                if(result>0)
                {
                    System.out.println("NPROD EMP HRS DELETED");
                }
            }
            
            //to select PREVIOUS PROD HRS...FOR
            PreparedStatement ps = con.executeStatement(SQLMaster.NPRODN_TOT_HRS_SELECT_SQL_QUERY,ht_EmpHrs_Del);
            ResultSet rs = ps.executeQuery();
            
            float prodTotHrs = 0;
            if(rs.next())
            {
                prodTotHrs = rs.getFloat("NPROD_TOT_HRS");
                if(BuildConfig.DMODE)
                    System.out.println("prodTotHrs :"+prodTotHrs);
            }
            rs.close();
            ps.close();
           
            //subtract Machine Hrs in PROD_ACCNTNG table.
            //ht_MacHrs_Reduce.put("MC_CDE",objNonProductionDetails.getMcCode());
            //ht_MacHrs_Reduce.put("PROD_DATE",objNonProductionDetails.getNonProdnCrntDate());
            //ht_MacHrs_Reduce.put("SHIFT_ID",new Integer(objNonProductionDetails.getShiftId()));
            ht_MacHrs_Reduce.put("NPROD_ID",new Integer(nprodId));
            ht_MacHrs_Reduce.put("NPROD_HRS",new Float(prodTotHrs));
            ht_MacHrs_Reduce.put("ISHRS_ACCOUNTED","0");
            //ht_MacHrs_Reduce.put("IS_COMPLETED","0");
            result = con.executeUpdateStatement(SQLMaster.NPRODN_MC_HRS_REDUCE_SQL_QUERY,ht_MacHrs_Reduce);
            if(result>0)
            {
                if(BuildConfig.DMODE)
                    System.out.println("NPROD MC HRS SUBTRACTED");
            }
            //Updation of PROD table.

            ht_Prod_Update.put("NPROD_ID",new Integer(nprodId));
            ht_Prod_Update.put("MC_CDE",objNonProductionDetails.getMcCode());
            ht_Prod_Update.put("NPROD_CRNT_DATE",objNonProductionDetails.getNonProdnCrntDate());
            ht_Prod_Update.put("SHIFT_ID",new Integer(objNonProductionDetails.getShiftId()));
            ht_Prod_Update.put("NPROD_IDL_BRKDWN",objNonProductionDetails.getIdlOrBkDown());
            ht_Prod_Update.put("RSN_ID",new Integer(objNonProductionDetails.getRsnId()));
            ht_Prod_Update.put("NPROD_TOT_HRS",new Float(objNonProductionDetails.getNprodTotHrs()));
            ht_Prod_Update.put("NPROD_CREATEDBY",objNonProductionDetails.getModifiedBy());
            result = con.executeUpdateStatement(SQLMaster.NPRODN_DETAILS_UPDATE_SQL_QUERY,ht_Prod_Update);
            if(result > 0)
            {

                //to select current modify count..

                PreparedStatement ps_ModifyCnt_Sel = con.executeStatement(SQLMaster.NPROD_MODIFY_COUNT_SELECT,ht_Prod_Update);
                ResultSet rs_ModifyCnt_Sel = ps_ModifyCnt_Sel.executeQuery();
                int modifyCount = 0;
                if(rs_ModifyCnt_Sel.next())
                {
                    modifyCount = rs_ModifyCnt_Sel.getInt("NPROD_MODIFYCOUNT");
                }
                ht_Prod_Update.put("NPROD_MODIFYCOUNT",new Integer(modifyCount));

                ht_EmpHrs_Update.put("NPROD_MODIFYCOUNT",new Integer(modifyCount));

                //to store Prod Entries to Log..
                con.executeUpdateStatement(SQLMaster.NPRODN_DETAILS_LOG_ADD_SQL_QUERY,ht_Prod_Update);


                vec_EmpHrs = objNonProductionDetails.getNonprodnEmpHrsDetails();

                //totHrs = objNonProductionDetails.getNprodTotHrs();

                for(int i = 0;i<vec_EmpHrs.size();i++)
                {
                    otHrs = 0;
                    otSlryHrs = 0;
                    dtyHrs = 0;
                    dtySlryHrs = 0;

                    EmployeeDtyOtDetails objEmployeeDtyOtDetails = new EmployeeDtyOtDetails();
                    objEmployeeDtyOtDetails = (EmployeeDtyOtDetails)vec_EmpHrs.elementAt(i);


                    dtyHrs = objEmployeeDtyOtDetails.getDutyHrs();
                    otHrs = objEmployeeDtyOtDetails.getOtHrs();


                    empId = objEmployeeDtyOtDetails.getEmpId();
                    /*
                    empType = objEmployeeDtyOtDetails.getEmpType();
                    if(!empType.equalsIgnoreCase("supervisor"))
                    {

                        dtySlryHrs = dtyHrs;
                        otSlryHrs = otHrs * 2;
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
                    if(BuildConfig.DMODE)
                    {
                        System.out.println("EmpTypeId:"+empTypId);
                        System.out.println("EmpId:"+empId);
                        System.out.println("Empval:"+val);
                    }
                   
                    if(val == 0)
                        if(dtyHrs >0)
                        if(!objProductionDetailsManager.checkDutyEntry(objNonProductionDetails.getNonProdnCrntDate(), objNonProductionDetails.getShiftId(), empId, con))
                        {
                            con.rollBackTransaction();
                            throw new ProductionException("WOEC00","EMPLOYEE DUTY NOT POSSIBLE FOR MULTIPLE SHIFTS : "+objEmployeeDtyOtDetails.getEmpName(),"");
                        }
                    
                    if(val == 0)
                    check = objProductionDetailsManager.checkAvailableEmpHrs(empId,objNonProductionDetails.getNonProdnCrntDate(),objNonProductionDetails.getShiftId(),0,dtyHrs+otHrs,0,0,con);
                    if(check==0)
                    {
                        ht_EmpHrs_Update.put("NPROD_ID",new Integer(nprodId));
                        ht_EmpHrs_Update.put("EMP_ID",new Integer(empId));
                        ht_EmpHrs_Update.put("DT_HRS",new Float(dtyHrs));
                        ht_EmpHrs_Update.put("OT_HRS",new Float(otHrs));
                        ht_EmpHrs_Update.put("DT_SLRY_HRS",new Float(dtySlryHrs));
                        ht_EmpHrs_Update.put("OT_SLRY_HRS",new Float(otSlryHrs));


                        result = con.executeUpdateStatement(SQLMaster.NPRODN_EMPHRS_ADD_SQL_QUERY,ht_EmpHrs_Update);
                        if(result>0)
                        {
                            if(BuildConfig.DMODE)
                                System.out.println("Employee Hrs Details Updateed");


                        }

                    }
                    else
                    {
                        con.rollBackTransaction();
                        throw new ProductionException("NPMEC006","EMPLOYEE HRS IS INVALID : "+objEmployeeDtyOtDetails.getEmpName(),"");
                    }


                }
                ht_ProdAccntng_Update.put("MC_CDE",objNonProductionDetails.getMcCode());
                ht_ProdAccntng_Update.put("PROD_DATE",objNonProductionDetails.getNonProdnCrntDate());
                ht_ProdAccntng_Update.put("SHIFT_ID",new Integer(objNonProductionDetails.getShiftId()));
                int check = objProductionDetailsManager.checkHrsAccounted(objNonProductionDetails.getMcCode(),objNonProductionDetails.getNonProdnCrntDate(),objNonProductionDetails.getShiftId(),0,objNonProductionDetails.getNprodTotHrs(),con);
                if(check==0)
                {
                    ht_ProdAccntng_Update.put("NPROD_HRS",new Float(objNonProductionDetails.getNprodTotHrs()));
                    ht_ProdAccntng_Update.put("ISHRS_ACCOUNTED","1");
                }
                else if(check ==1)
                {
                    ht_ProdAccntng_Update.put("NPROD_HRS",new Float(objNonProductionDetails.getNprodTotHrs()));
                    ht_ProdAccntng_Update.put("ISHRS_ACCOUNTED","0");
                }
                else
                {
                    con.rollBackTransaction();
                    throw new ProductionException("NPMEC007","NON-PROD HRS IS INVALID","");
                }
                result = con.executeUpdateStatement(SQLMaster.PRODN_ACCNTNG_UPDATE_BY_NPRODHRS_SQL_QUERY,ht_ProdAccntng_Update);

                if(result<=0)
                {
                    result = con.executeUpdateStatement(SQLMaster.PRODN_ACCNTNG_NPRODHRS_ADD_SQL_QUERY,ht_ProdAccntng_Update);
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
                ////////
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
                if(sqle.toString().indexOf("PK_NPRODID")>=0)
                {
                    throw new ProductionException("NPMEC002","NON-PRODUCTION ID IS UNIQUE.UNIQUE CONSTRAINT VIOLATED",sqle.toString());
                }
                else if (sqle.toString().indexOf("FK_NPROD_MCCDE") >= 0)
                {
                    throw new ProductionException("NPMEC003","PARENT KEY MACHINE CODE NOT FOUND",sqle.toString());
                }
                else if (sqle.toString().indexOf("FK_NPROD_SHIFTID") >= 0)
                {
                    throw new ProductionException("NPMEC004","PARENT KEY SHIFT ID NOT FOUND",sqle.toString());
                }
                else if (sqle.toString().indexOf("FK_NPROD_RSNID") >= 0)
                {
                    throw new ProductionException("NPMEC005","PARENT KEY REASON ID NOT FOUND",sqle.toString());
                }
                else
                {
                    throw new ProductionException("NPMEC000","GENERAL SQL ERROR",sqle.toString());
                }
            }
            if(BuildConfig.DBASE==BuildConfig.MYSQL)
            {
                if(sqle.toString().indexOf("Duplicate entry")>=0)
                {
                    throw new ProductionException("NPMEC002","NON-PRODUCTION ID IS UNIQUE.UNIQUE CONSTRAINT VIOLATED",sqle.toString());
                }
                else if (sqle.toString().indexOf("foreign key constraint") >= 0)
                {
                    throw new ProductionException("NPMEC003","PARENT KEY MACHINE CODE OR SHIFT ID OR REASON ID NOT FOUND",sqle.toString());
                }
                else
                {
                    throw new ProductionException("NPMEC000","GENERAL SQL ERROR",sqle.toString());
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



        logger.info("NON-PRODUCTION UPDATE ENDS");

        return updateRESULT;
    }


    public boolean isModifyForNprod(int nprodId) throws ProductionException, SQLException
    {
        boolean retVal = false;
        DBConnection con = null;
        Hashtable ht_Prod = new Hashtable();
        PreparedStatement ps = null;
        int cnt = 0;
        try
        {
            con = DBConnectionFactory.getConnection();
            ht_Prod.put("NPROD_ID",new Integer(nprodId));
            ps = con.executeStatement(SQLMaster.CHECK_NPROD_FOR_MODIFY_SQL_QUERY,ht_Prod);
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

    public Vector viewUnPostedNonProductionDetails(Date fromDate,Date toDate) throws ProductionException,SQLException
    {
        Vector vec_Result = new Vector();
        Hashtable ht_UnPostedNonProd_Get = new Hashtable();
        PreparedStatement ps = null;
        //Hashtable ht_UnPostedNonProd_Get = new Hashtable();


        logger.info("GET UNPOSTED NOPRODUCTION DETAILS STARTS");


        con = DBConnectionFactory.getConnection();
        ht_UnPostedNonProd_Get.put("FROM_DATE",fromDate);
        ht_UnPostedNonProd_Get.put("TO_DATE",toDate);
        try
        {
            ps = con.executeStatement(SQLMaster.UNPOSTED_NONPROD_DETAILS_SELECT_SQL_QUERY,ht_UnPostedNonProd_Get);
            ResultSet rs_UnPostedNonProd_Get = ps.executeQuery();
            while(rs_UnPostedNonProd_Get.next())
            {
                NonProductionDetails objNonProductionDetails = new NonProductionDetails();
                objNonProductionDetails.setNonProdId(rs_UnPostedNonProd_Get.getInt("NPROD_ID"));
                objNonProductionDetails.setNonProdnCrntDate(rs_UnPostedNonProd_Get.getTimestamp("NPROD_CRNT_DATE"));
                objNonProductionDetails.setMcCode(rs_UnPostedNonProd_Get.getString("MC_CDE"));
                objNonProductionDetails.setShiftId(rs_UnPostedNonProd_Get.getInt("SHIFT_ID"));
                objNonProductionDetails.setShiftName(rs_UnPostedNonProd_Get.getString("SHIFT_NAME"));
                objNonProductionDetails.setNprodTotHrs(rs_UnPostedNonProd_Get.getFloat("NPROD_TOT_HRS"));
                objNonProductionDetails.setIdlOrBkDown(rs_UnPostedNonProd_Get.getString("NPROD_IDL_BRKDWN"));
                objNonProductionDetails.setIdlOrBrkDwnRsn(rs_UnPostedNonProd_Get.getString("RSN"));

                System.out.println(objNonProductionDetails.getNonProdId());
                vec_Result.addElement(objNonProductionDetails);
            }
            rs_UnPostedNonProd_Get.close();
            ps.close();
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }
            throw new ProductionException("PREC00","GENERAL SQL ERROR",sqle.toString());
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


        logger.info("GET UNPOSTED NONPROUCTION DETAILS ENDS");


        return vec_Result;
    }

    public HashMap postNonProductionDetails(Vector vec_NonProd_Ids) throws ProductionException, SQLException
    {

        int result = 0;
        HashMap hm_Result = new HashMap();
        Hashtable ht_PostNonProdn = new Hashtable();

        logger.info("POST NON-PRODUCTION DETAILS STARTS");

        try
        {
            con = DBConnectionFactory.getConnection();
            con.startTransaction();

            for(int i=0;i<vec_NonProd_Ids.size();i++)
            {
                ht_PostNonProdn.put("NPROD_ID",vec_NonProd_Ids.get(i));
                result = con.executeUpdateStatement(SQLMaster.POST_NONPRODN_DETAILS_SQL_QUERY,ht_PostNonProdn);
                if(result>=1)
                {
                    con.commitTransaction();
                    hm_Result.put(vec_NonProd_Ids.get(i),new Integer(0));
                    logger.info(vec_NonProd_Ids.get(i)+" : NON PRODUCTION RECORD POSTED");

                }
                else
                {
                    con.rollBackTransaction();
                    hm_Result.put(vec_NonProd_Ids.get(i),new Integer(1));
                    logger.info(vec_NonProd_Ids.get(i)+" : NON PRODUCTION RECORD NOT POSTED");

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


        logger.info("POST NON-PROUCTION DETAILS ENDS");

        return hm_Result;
    }
    public NonProductionDetails getNonProductionDetails(int nprodId) throws ProductionException,SQLException
    {
        NonProductionDetails objNonProductionDetails = new NonProductionDetails();
        Hashtable ht_NProdDet_Get = new Hashtable();
        Vector vec_Emp_Det = new Vector();
        PreparedStatement ps_NProdDet_Get = null;
        PreparedStatement ps_NProdEmp_Det = null;

        logger.info("GET PRODUCTION DETAILS STARTS");

        con = DBConnectionFactory.getConnection();

        ht_NProdDet_Get.put("NPROD_ID", new Integer(nprodId));
        try
        {
            ps_NProdDet_Get = con.executeStatement(SQLMaster.GET_NPROD_DETAILS_SQL_QUERY,ht_NProdDet_Get);
            ResultSet rs_NProdDet_Get = ps_NProdDet_Get.executeQuery();

            if(rs_NProdDet_Get.next())
            {
                //set NonProduction Details //
                objNonProductionDetails.setNonProdId(nprodId);
                objNonProductionDetails.setMcCode(rs_NProdDet_Get.getString("MC_CDE"));
                objNonProductionDetails.setNonProdnCrntDate(rs_NProdDet_Get.getTimestamp("NPROD_CRNT_DATE"));
                objNonProductionDetails.setShiftId(rs_NProdDet_Get.getInt("SHIFT_ID"));
                objNonProductionDetails.setShiftName(rs_NProdDet_Get.getString("SHIFT_NAME"));
                objNonProductionDetails.setNprodTotHrs(rs_NProdDet_Get.getFloat("NPROD_TOT_HRS"));
                objNonProductionDetails.setIdlOrBkDown(rs_NProdDet_Get.getString("NPROD_IDL_BRKDWN"));
                objNonProductionDetails.setRsnId(rs_NProdDet_Get.getInt("RSN_ID"));
                objNonProductionDetails.setIdlOrBrkDwnRsn(rs_NProdDet_Get.getString("RSN"));
                objNonProductionDetails.setNprodUpdatePyrl(rs_NProdDet_Get.getString("NPROD_UPDATE_PYRL").equals("1"));
                objNonProductionDetails.setNprodPostFlg(rs_NProdDet_Get.getString("NPROD_POST_FLG").equals("1"));
                objNonProductionDetails.setDeleted(rs_NProdDet_Get.getString("IS_DLTD").equals("1"));
                objNonProductionDetails.setModifyCount(rs_NProdDet_Get.getInt("NPROD_MODIFYCOUNT"));
                if(objNonProductionDetails.getModifyCount()==0)
                objNonProductionDetails.setCreatedBy(rs_NProdDet_Get.getString("NPROD_CREATEDBY"));
                else
                objNonProductionDetails.setModifiedBy(rs_NProdDet_Get.getString("NPROD_CREATEDBY"));	
                
                objNonProductionDetails.setNprodDateStamp(rs_NProdDet_Get.getTimestamp("NPROD_DATESTAMP"));
                objNonProductionDetails.setNprodIsValid(rs_NProdDet_Get.getInt("NPROD_ISVALID"));

                //get All Employee Details of the Above Production Record
                ps_NProdEmp_Det = con.executeStatement(SQLMaster.GET_NPROD_EMP_DETAILS_SQL_QUERY,ht_NProdDet_Get);
                ResultSet rs_NProdEmp_Det = ps_NProdEmp_Det.executeQuery();

                while(rs_NProdEmp_Det.next())
                {
                    EmployeeDtyOtDetails objEmployeeDtyOtDetails = new EmployeeDtyOtDetails();

                    objEmployeeDtyOtDetails.setEmpId(rs_NProdEmp_Det.getInt("EMP_ID"));
                    objEmployeeDtyOtDetails.setEmpName(rs_NProdEmp_Det.getString("EMP_NAME"));
                    objEmployeeDtyOtDetails.setEmpCode(rs_NProdEmp_Det.getString("EMP_CDE"));
                    objEmployeeDtyOtDetails.setEmpType(rs_NProdEmp_Det.getString("EMP_TYP_NAME"));
                    objEmployeeDtyOtDetails.setEmpTypdId(rs_NProdEmp_Det.getInt("EMP_TYP_ID"));
                    objEmployeeDtyOtDetails.setDutyHrs(rs_NProdEmp_Det.getFloat("DT_HRS"));
                    objEmployeeDtyOtDetails.setOtHrs(rs_NProdEmp_Det.getFloat("OT_HRS"));
                    objEmployeeDtyOtDetails.setDutySlryHrs(rs_NProdEmp_Det.getFloat("DT_SLRY_HRS"));
                    objEmployeeDtyOtDetails.setOtSlryHrs(rs_NProdEmp_Det.getFloat("OT_SLRY_HRS"));

                    //Add Each Employee Details into Vector
                    vec_Emp_Det.addElement(objEmployeeDtyOtDetails);


                }
                rs_NProdEmp_Det.close();
                ps_NProdEmp_Det.close();
                //set Employee Details Vector into Prod Object
                objNonProductionDetails.setNonprodnEmpHrsDetails(vec_Emp_Det);
            }
            else
            {
                throw new ProductionException("PREC00","RECORD NOT FOUND","");
            }
            rs_NProdDet_Get.close();
            ps_NProdDet_Get.close();
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }
            throw new ProductionException("PREC00","GENERAL SQL ERROR",sqle.toString());
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
        logger.info("GET PRODUCTION DETAILS ENDS");

        return objNonProductionDetails;
    }
    public HashMap getAllNonProductionDetails(Filter filters[],String sortBy,boolean ascending,int startIndex,int displayCount) throws ProductionException, SQLException
    {

        HashMap hm_Result = new HashMap();
        Vector vec_NProd_Det = new Vector();

        logger.info("GET ALL NON-PRODUCTION DETAILS FILTER STARTS");

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
            tot_Rec_Cnt = con.getRowCountWithComplexFilters(filters, SQLMaster.GET_ALL_NON_PRODUCTION_DETAILS_FILTER_QUERY);

            //Finding end index for the query
            int eIndex = startIndex + displayCount;


            // filters,tablename,startindex,endindex,sorting column name and sortorder are passed to the function and  query will be received
            String Query = con.buildDynamicQuery(filters,startIndex,eIndex,sortBy,ascending,SQLMaster.GET_ALL_NON_PRODUCTION_DETAILS_FILTER_QUERY);

            if(BuildConfig.DMODE)
            {
                // total records and query
                System.out.println("TOTAL NUMBER OF RECORDS : " + tot_Rec_Cnt);
                System.out.println("QUERY : " + Query);
            }
            logger.info("Total Records : " + tot_Rec_Cnt);
            logger.info("Query : " + Query);


            ResultSet rs_NProdn_Details_Get =  con.executeRownumStatement(Query);
            double totNprodHrs = 0;
            while(rs_NProdn_Details_Get.next())
            {
                NonProductionDetails objNonProductionDetails = new NonProductionDetails();
                objNonProductionDetails.setNonProdId(rs_NProdn_Details_Get.getInt("NPROD_ID"));
                objNonProductionDetails.setMcCode(rs_NProdn_Details_Get.getString("MC_CDE"));
                objNonProductionDetails.setNonProdnCrntDate(rs_NProdn_Details_Get.getTimestamp("NPROD_CRNT_DATE"));
                objNonProductionDetails.setShiftId(rs_NProdn_Details_Get.getInt("SHIFT_ID"));
                objNonProductionDetails.setShiftName(rs_NProdn_Details_Get.getString("SHIFT_NAME"));
                objNonProductionDetails.setNprodTotHrs(rs_NProdn_Details_Get.getFloat("NPROD_TOT_HRS"));
                objNonProductionDetails.setIdlOrBkDown(rs_NProdn_Details_Get.getString("NPROD_IDL_BRKDWN"));
                //objNonProductionDetails.setIdlOrBrkDwnRsn(rs_NProdn_Details_Get.getString("RSN"));
                //objNonProductionDetails.setNprodTotHrs(rs_NProdn_Details_Get.getFloat("NPROD_TOT_HRS"));
                objNonProductionDetails.setNprodDateStamp(rs_NProdn_Details_Get.getTimestamp("NPROD_DATESTAMP"));
                objNonProductionDetails.setNprodIsValid(rs_NProdn_Details_Get.getInt("NPROD_ISVALID"));
                totNprodHrs = totNprodHrs + objNonProductionDetails.getNprodTotHrs();
                vec_NProd_Det.addElement(objNonProductionDetails);

            }
            hm_Result.put("TotalNProdHrs",new Double(totNprodHrs));
            hm_Result.put("TotalRecordCount",new Integer(tot_Rec_Cnt));
            hm_Result.put("NonProductionDetails",vec_NProd_Det);
            rs_NProdn_Details_Get.getStatement().close();
            rs_NProdn_Details_Get.close();
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

        logger.info("GET ALL NON-PRODUCTION DETAILS FILTER ENDS");


        return hm_Result;
    }
    public LinkedHashMap getAllIdlBrkDwnRsns() throws SQLException, ProductionException
    {
        DBConnection con = null;
        int reasonId = 0;
        String reason = "";
        LinkedHashMap hm_IdlBkdwnRsn = new LinkedHashMap();
        PreparedStatement ps = null;

        logger.info("GET ALL IDL/BREAKDOWN REASON GETS STARTED");

        try
        {
            con = DBConnectionFactory.getConnection();
            {
                ps = con.executeStatement(SQLMaster.ALL_IDLBRKDWN_REASON_SELECT_SQL_QUERY);
                ResultSet rs_IdlBrkdwnRsn_Get = ps.executeQuery();
                while(rs_IdlBrkdwnRsn_Get.next())
                {
                    reasonId = rs_IdlBrkdwnRsn_Get.getInt("RSN_ID");
                    reason = rs_IdlBrkdwnRsn_Get.getString("RSN");
                    hm_IdlBkdwnRsn.put(new Integer(reasonId),reason);
                }
                rs_IdlBrkdwnRsn_Get.close();
                ps.close();

            }

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


        logger.info("GET ALL IDL/BREAKDOWN REASON GETS ENDS");

        return hm_IdlBkdwnRsn;
    }
    public HashMap makeNonProductionValid(Vector nprodIds) throws ProductionException, SQLException
    {
        int result = 0;
        DBConnection con = null;
        HashMap hm_Result = new HashMap();
        if(BuildConfig.DMODE)
        {

            logger.info("MAKE NON PRODUCTION VALID STARTS");
        }
        if(nprodIds == null)
        {
            logger.error("NON PRODUCTION VECTOR OBJECT IS NULL");
            throw new ProductionException("PREC00","NON PRODUCTION VECTOR OBJECT IS NULL","");
        }

        try
        {
            con = DBConnectionFactory.getConnection();
            con.startTransaction();
            for(int i=0;i<nprodIds.size();i++)
            {
                Hashtable ht_Prod_Id = new Hashtable();


                ht_Prod_Id.put("NPROD_ID",(Integer)nprodIds.get(i));

                result = con.executeUpdateStatement(SQLMaster.NPROD_MAKE_VALID_SQL_QUERY,ht_Prod_Id);

                if(result>=1)
                {
                    con.commitTransaction();
                    hm_Result.put(nprodIds.get(i),new Integer(0));

                    logger.info(nprodIds.get(i)+" : NON PRODUCTION RECORD VALIDATED");

                }
                else
                {
                    con.rollBackTransaction();
                    hm_Result.put(nprodIds.get(i),new Integer(1));

                    logger.info(nprodIds.get(i)+" : NON PRODUCTION RECORD NOT VALIDATED");

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

        logger.info("MAKE NON PRODUCTION VALID ENDS");

        return hm_Result;

    }

    public HashMap makeNonProductionInValid(Vector nprodIds) throws ProductionException, SQLException
    {
        int result = 0;
        DBConnection con = null;
        HashMap hm_Result = new HashMap();


        logger.info("MAKE NON PRODUCTION INVALID STARTS");

        if(nprodIds == null)
        {

            logger.error("NON PRODUCTION VECTOR OBJECT IS NULL");
            throw new ProductionException("PREC00","NON PRODUCTION VECTOR OBJECT IS NULL","");
        }

        try
        {
            con = DBConnectionFactory.getConnection();
            con.startTransaction();
            for(int i=0;i<nprodIds.size();i++)
            {
                Hashtable ht_Prod_Id = new Hashtable();


                ht_Prod_Id.put("NPROD_ID",(Integer)nprodIds.get(i));
                result = con.executeUpdateStatement(SQLMaster.NPROD_MAKE_INVALID_SQL_QUERY,ht_Prod_Id);

                if(result>=1)
                {
                    con.commitTransaction();
                    hm_Result.put(nprodIds.get(i),new Integer(0));
                    logger.info(nprodIds.get(i)+" : NON PRODUCTION RECORD VALIDATED");

                }
                else
                {
                    con.rollBackTransaction();
                    hm_Result.put(nprodIds.get(i),new Integer(1));
                    logger.info(nprodIds.get(i)+" : NON PRODUCTION RECORD NOT VALIDATED");

                }
            }//for loop end

        }//first try end

        catch(SQLException e)
        {
            if(BuildConfig.DMODE)
            {
                e.printStackTrace();
                System.out.println("GENERAL EXCEPTION");
                logger.error("GENERAL ERROR",e);
            }
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

        logger.info("MAKE NON PRODUCTION INVALID ENDS");

        return hm_Result;

    }

    public static void main(String args[]) throws ProductionException,SQLException
    {
        NonProductionDetailsManager objNPMgr = new NonProductionDetailsManager();
        NonProductionDetails objNPDetails = new NonProductionDetails();
        EmployeeDtyOtDetails objEmpDetails = new EmployeeDtyOtDetails();
        Vector vec = new Vector();

        ////////////////////////////addNewNonProductionDetails()//////////////////
        /*objNPDetails.setMcCode("123");
         objNPDetails.setNonProdnCrntDate(new Date("21-DEC-04"));
         objNPDetails.setShiftId(62);
         objNPDetails.setRsnId(1);
         objNPDetails.setIdlOrBkDown("Idle");
         objNPDetails.setNprodTotHrs(0);
         objEmpDetails.setEmpId(181);
         objEmpDetails.setDutyHrs(0);
         objEmpDetails.setOtHrs(0);
         vec.addElement(objEmpDetails);*/
        //objNPDetails.setNonProdnEmpHrsDetails(vec);

        //HashMap h = new HashMap();
        //System.out.println((h = objNPMgr.getAllIdlBrkDwnRsns()).size());

        /////////////////////////updateNonProductionDetails()////////////////////
        objNPDetails.setNonProdId(19);
        objNPDetails.setShiftId(42);
        //objNPDetails.setNonProdnCrntDate(new Date("25-NOV-04"));
        objNPDetails.setMcCode("mc100");
        objNPDetails.setNprodTotHrs(1);
        //objNPDetails.setNprodUpdatePyrl(true);
        //objNPDetails.setNprodPostFlg(true);
        //objNPDetails.setDeleted(true);
        objNPDetails.setIdlOrBkDown("BRKDOWN");
        objNPDetails.setRsnId(1);
        objEmpDetails.setEmpId(184);
        objEmpDetails.setDutyHrs(1);
        objEmpDetails.setOtHrs(0);
        //objNPDetails.setNprodDateStamp(new Date("25-NOV-04"));
        vec.addElement(objEmpDetails);
        objNPDetails.setNonprodnEmpHrsDetails(vec);

        System.out.println(objNPMgr.isModifyForNprod(41));

        //System.out.println(objNPMgr.updateNonProductionDetails(objNPDetails));
        //////////////////////////getAllNonProductionDetails()//////////////////
        /*Filter[] filters = new Filter[2];
         filters[0] = new Filter();
         filters[0].setFieldName("SHIFT_NAME");
         filters[0].setFieldValue("a");
         filters[0].setSpecialFunction("AnyWhere");


         filters[1] = new Filter();
         filters[1].setFieldName("NPROD_CRNT_DATE");
         filters[1].setFieldValue("01-NOV-2004$31-DEC-2004");
         filters[1].setSpecialFunction("DateBetween");

         boolean ascending = true;
         int startIndex = 0;
         String sortBy = "MC_CDE";
         int displayCount = 10;
         HashMap hm = objNPMgr.getAllNonProductionDetails(filters,sortBy,ascending,startIndex,displayCount);
         System.out.println("Result :"+hm);*/

        ///////////////////////////getNonProductionDetails()///////////////

        /*objNPDetails = objNPMgr.getNonProductionDetails(19);
         System.out.println("RSN."+objNPDetails.getIdlOrBrkDwnRsn());
         System.out.println("DATE."+objNPDetails.getNonProdnCrntDate());
         System.out.println("TOTAL HRS."+objNPDetails.getNprodTotHrs());
         System.out.println("VALID."+objNPDetails.getNProdIsValid());
         vec = objNPDetails.getNonProdnEmpHrsDetails();
         for(int i=0;i<vec.size();i++)
         {
         objEmpDetails = (EmployeeDtyOtDetails)vec.get(i);
         System.out.println("EMP.DTY.SLRY.HRS"+objEmpDetails.getDutySlryHrs());

         }*/

        //////////////////////postNonProduction()/////////////////
        /*vec.addElement(new Integer(14));
         vec.addElement(new Integer(18));
         System.out.println(objNPMgr.postNonProductionDetails(vec));*/

        /////////////////////viewUnpostedProductionDetails()//////////
        /*vec = objNPMgr.viewUnPostedNonProductionDetails(new Date("01-NOV-04"),new Date("31-DEC-04"));
         System.out.println("VECTOR VALUES :"+vec);
         for(int i=0;i<vec.size();i++)
         {
         objNPDetails = (NonProductionDetails)vec.get(i);
         System.out.println("RSN :"+objNPDetails.getIdlOrBrkDwnRsn());
         System.out.println("id " +objNPDetails.getNonProdId());
         System.out.println("valid ;"+objNPDetails.getNProdIsValid());
         System.out.println("tothrs  :"+objNPDetails.getNprodTotHrs());
         }*/


    }

}

/***
$Log: NonProductionDetailsManager.java,v $
Revision 1.67  2006/01/12 12:13:56  kduraisamy
BuildConfig.DMODE included.

Revision 1.66  2005/12/28 09:27:34  kduraisamy
in emp Hrs invalid  emp code included.

Revision 1.65  2005/12/01 07:23:23  kduraisamy
BuildConfig.DMODE ADDED.

Revision 1.64  2005/10/06 10:31:12  vkrishnamoorthy
Modified By in View, error corrected.

Revision 1.63  2005/10/04 15:15:48  kduraisamy
prodTotHrs selection added for updation of nprod.

Revision 1.62  2005/10/04 06:41:38  kduraisamy
NprodTotHrs added in NProd Filter.

Revision 1.61  2005/10/04 06:02:47  kduraisamy
NprodTotHrs added in NProd Filter.

Revision 1.60  2005/09/30 11:50:06  kduraisamy
startTransaction() added in create pyrl interface().

Revision 1.59  2005/09/30 11:02:30  vkrishnamoorthy
Update payroll set to 0 in update.

Revision 1.58  2005/09/26 14:46:03  kduraisamy
duty 8 hrs validation added.

Revision 1.57  2005/09/26 11:09:03  kduraisamy
duty 8 hrs validation added.

Revision 1.56  2005/09/26 07:31:22  kduraisamy
duty 8 hrs validation added.

Revision 1.55  2005/09/26 07:22:29  kduraisamy
duty 8 hrs validation added.

Revision 1.54  2005/09/15 07:36:09  kduraisamy
rs.getStatement().close() added in Filters.

Revision 1.53  2005/09/10 13:46:46  kduraisamy
order by clause added.

Revision 1.52  2005/09/10 13:18:42  kduraisamy
order by clause added.

Revision 1.51  2005/09/07 14:43:05  kduraisamy
result Set properly closed.

Revision 1.50  2005/08/23 09:24:49  kduraisamy
CHECK EMP TYPE SQL QUERY ADDED.

Revision 1.49  2005/08/08 07:18:05  kduraisamy
modify count field added.

Revision 1.48  2005/08/04 12:00:10  kduraisamy
EMP_CDE ADDED FOR PROD VIEW.

Revision 1.47  2005/07/12 11:37:26  kduraisamy
imports organized

Revision 1.46  2005/07/04 09:48:24  kduraisamy
payroll calculate Sal Hrs() method changed for radl,nprod,pop.

Revision 1.45  2005/07/04 09:46:51  kduraisamy
payroll calculate Sal Hrs() method changed for radl,nprod,pop.

Revision 1.44  2005/06/29 12:43:58  smurugesan
if con ! = null added in finally

Revision 1.43  2005/06/01 14:37:15  kduraisamy
log adding changed.

Revision 1.42  2005/05/30 11:45:49  kduraisamy
PROD_DUMMYCOUNT ADDED.

Revision 1.41  2005/05/30 11:43:18  kduraisamy
NPROD_DUMMYCOUNT ADDED.

Revision 1.40  2005/05/27 05:43:30  kduraisamy
con != null checking added.

Revision 1.39  2005/05/16 18:35:05  kduraisamy
specific throws addded for mysql.

Revision 1.38  2005/05/16 15:42:43  kduraisamy
specific throws addded for mysql.

Revision 1.37  2005/04/19 13:13:33  kduraisamy
resultset properly closed.

Revision 1.36  2005/04/18 12:28:06  kduraisamy
executeStatement() return type changed.

Revision 1.35  2005/04/07 09:20:19  kduraisamy
MODE IS CHANGED TO DMODE.

Revision 1.34  2005/04/07 07:36:10  kduraisamy
if(BuildConfig.DMODE) ADDED. AND LEVEL INCLUDED PROPERLY.

Revision 1.33  2005/03/30 08:23:40  kduraisamy
excecuteRownumStatement() called for filters.

Revision 1.32  2005/03/26 11:35:09  smurugesan
ERROR CODES ARE ALTERED.

Revision 1.31  2005/03/26 10:09:00  smurugesan
ROWNUM RESET QUERY REMOVED.

Revision 1.30  2005/03/11 06:20:16  kduraisamy
RESET ROWNUM QUERY ADDED BEFORE FILTER METHODS..

Revision 1.29  2005/03/10 07:27:36  kduraisamy
CHECKING BEFORE THE FILTER METHODS(buildDynamicQuery(),etc) added.

getDate is changed to getTimestamp().

Revision 1.28  2005/03/09 07:19:16  kduraisamy
indentation.

Revision 1.27  2005/03/05 09:52:22  kduraisamy
signature changed for addProductionDetails().

Revision 1.26  2005/03/04 12:33:57  kduraisamy
DBConnection Changes made.

Revision 1.25  2005/02/15 09:15:47  smurugesan
connection object added as a checkempHrs()'s parameter.

Revision 1.24  2005/02/14 11:10:04  kduraisamy
unwanted cast removed.

Revision 1.23  2005/02/11 06:55:15  kduraisamy
unused variables removed.

Revision 1.22  2005/02/08 10:00:24  sponnusamy
Depricated methods removed in main.

Revision 1.21  2005/02/08 06:00:34  kduraisamy
DBConnection con object passed to checkHrsAccounted().

Revision 1.20  2005/02/01 17:16:42  kduraisamy
posting method return type changed to HashMap.

Revision 1.19  2005/01/28 14:55:51  kduraisamy
isModify() method added.

Revision 1.18  2005/01/27 14:46:35  kduraisamy
if(BuildConfig.DMODE)
{
}
properly inclued inside the catch block.

Revision 1.16  2005/01/17 06:12:40  kduraisamy
make valid invalid added.

Revision 1.15  2005/01/06 09:14:06  kduraisamy
QUERY SEPARATED FOR PROD_ACCNTNG ADD AND UPDATE

Revision 1.14  2005/01/05 12:08:42  kduraisamy
main changed.

Revision 1.13  2005/01/05 06:35:54  kduraisamy
updateNonProductionDetails() modified.

Revision 1.12  2005/01/04 13:15:14  kduraisamy
empTypId added in EmpDetails value object.

Revision 1.11  2005/01/03 10:46:04  kduraisamy
method getAllBrkDwnRsns() added.

Revision 1.10  2004/12/30 05:42:30  kduraisamy
con.rollbackTransaction() included before throw exception.

Revision 1.9  2004/12/25 11:10:50  sduraisamy
Exception messages included

Revision 1.8  2004/12/23 11:31:39  sduraisamy
class name modified from ProductionDetailsManager to NonProductionDetailsManager in Logger

Revision 1.7  2004/12/23 10:11:32  sduraisamy
variable name started in uppercase modified in entityobject

Revision 1.6  2004/12/23 06:16:38  sduraisamy
add method modified to separate Queries

Revision 1.5  2004/12/22 09:50:45  kduraisamy
addRESULT = true added.

Revision 1.4  2004/12/21 05:04:43  sduraisamy
main included

Revision 1.3  2004/12/20 13:50:34  sduraisamy
Log added

***/