/*
 * Created on Nov 24, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.businessimplementation.workcalendar;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
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
public class WorkCalendarDetailsManager
{
    DBConnection con = null;
    static Logger logger = Logger.getLogger(WorkCalendarDetailsManager.class);
    public WorkCalendarDetailsManager()
    {
        logger.addAppender(LoggerOutput.getAppender());
        logger.setLevel(ProdacsConfig.LOGGER_LEVEL);
    }
    public boolean addShiftDefinition(ShiftDefnDetails objShiftDefnDetails) throws WorkCalendarException, SQLException
    {

        int result = 0;
        boolean addRESULT = false;
        String st = "";
        Vector vec_Obj = new Vector();
        Hashtable ht_ShiftDefn_Add = new Hashtable();


        logger.info("SHIFT DEFINITION ADD STARTS");

        if(objShiftDefnDetails == null)
        {

            logger.error("SHIFT DETAILS OBJECT IS NULL");
            throw new WorkCalendarException("WCMEC001","SHIFT DETAILS OBJECT IS NULL","");
        }
        if(objShiftDefnDetails.getShiftName().trim().length() == 0)
        {

            logger.error("SHIFT NAME IS REQUIRED");
            throw new WorkCalendarException("WCMEC002","SHIFT NAME IS REQUIRED","");

        }
        if(BuildConfig.DMODE)
            System.out.println("ADD SHIFT DEFINITION");
        con = DBConnectionFactory.getConnection();
        ht_ShiftDefn_Add.put("SHIFT_NAME",objShiftDefnDetails.getShiftName());
        ht_ShiftDefn_Add.put("SHIFT_DESC",(st=objShiftDefnDetails.getShiftDesc())==null?"":st );
        try
        {
            result=con.executeUpdateStatement(SQLMaster.SHIFT_DEFN_ADD_SQL_QUERY,ht_ShiftDefn_Add);
            if(BuildConfig.DMODE)
                System.out.println("SHIFT DEFINTITON" + (result != 0? " " : " NOT ") + "ADDED");
            if(result != 0)
            {

                logger.debug("Record Added : " + vec_Obj);

                addRESULT = true;
            }
            else
            {

                logger.debug("Record not Added : " + vec_Obj);

            }

        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }

            logger.error("SQL EXCEPTION", sqle);
            if(sqle.toString().indexOf("UK_SHIFTNAME")>=0 || sqle.toString().indexOf("Duplicate entry")>=0 || sqle.toString().indexOf("PK_SHIFTID")>=0)
            {
                throw new WorkCalendarException("WCMEC003","SHIFT NAME OR SHIFT ID ALREADY EXISTS",sqle.toString());
            }

            else
            {
                throw new WorkCalendarException("WCMEC000","GENERAL SQL ERROR",sqle.toString());
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


        logger.info("SHIFT DEFINITION ADD ENDS");

        return addRESULT;
    }

    public boolean updateShiftDefinition(ShiftDefnDetails objShiftDefnDetails) throws WorkCalendarException, SQLException
    {
        DBConnection con = null;
        boolean result = false;
        Hashtable ht_ShiftDefn_Update = new Hashtable();
        Vector vec_ShiftDefn_Update = new Vector();
        //ResultSet rs = null;
        DBNull dbNull = new DBNull();
        Object objDBNull = dbNull;
        int res = 0;


        logger.info("UPDATE SHIFT DEFN DETAILS STARTED");

        if(objShiftDefnDetails == null)
        {

            logger.error("SHIFT DEFN DETAILS OBJECT IS NULL");
            throw new WorkCalendarException("WCMEC004","SHIFT DEFN DETAILS OBJECT IS NULL","");
        }


        con = DBConnectionFactory.getConnection();
        //update Employee Details
        ht_ShiftDefn_Update.put("SHIFT_ID",new Integer(objShiftDefnDetails.getShiftId()));
        ht_ShiftDefn_Update.put("SHIFT_DESC",objShiftDefnDetails.getShiftDesc()==null ? objDBNull : objShiftDefnDetails.getShiftDesc());
        //Store the details from Hashtable to Vector

        try
        {
            //execute the preparedstatement
            res = con.executeUpdateStatement(SQLMaster.SHIFT_DEFN_DETAILS_UPDATE_QUERY,ht_ShiftDefn_Update);
            if(res>=1)
            {

                if(BuildConfig.DMODE)
                {
                    System.out.println("SHIFT DEFN RECORD UPDATED");
                }

                logger.debug("SHIFT DEFN RECORD UPDATED: " + vec_ShiftDefn_Update);
                result = true;
            }

            else
            {

                result = false;
                if(BuildConfig.DMODE)
                {
                    System.out.println("SHIFT DEFN RECORD NOT UPDATED");
                }

                logger.debug("SHIFT DEFN RECORD NOT UPDATED" + vec_ShiftDefn_Update);

            }

        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
                System.out.println("EXCEPTION WHILE UPDATING SHIFT DEFINITION");
            }

            logger.error("SQLERROR",sqle);
            result = false;
            if(sqle.toString().indexOf("UK_SHIFTNAME")>=0 || sqle.toString().indexOf("Duplicate entry")>=0 || sqle.toString().indexOf("PK_SHIFTID")>=0)
            {
                throw new WorkCalendarException("WCMEC003","SHIFT NAME OR SHIFT ID ALREADY EXISTS",sqle.toString());
            }

            else
            {
                throw new WorkCalendarException("WCMEC000","GENERAL SQL ERROR",sqle.toString());
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

                logger.error("CONNECTION ERROR : EXCEPTION WHILE CLOSING CONNECTION", ex);
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }

        return result;
    }
    public HashMap deleteShiftDefinition(Vector shiftIds) throws WorkCalendarException, SQLException
    {
        HashMap hm_Result = new HashMap();
        Hashtable ht_ShiftDefn_Del = new Hashtable();
        DBConnection con = null;
        int res = 0;


        logger.info("DELETE SHIFT DEFINITION DETAILS STARTED");

        if(shiftIds == null)
        {

            logger.info("SHIFT VECTOR OBJECT IS NULL");
            throw new WorkCalendarException("WCMEC006","SHIFT VECTOR OBJECT IS NULL","");
        }
        try
        {
            con = DBConnectionFactory.getConnection();
            for(int i=0;i<shiftIds.size();i++)
            {
                ht_ShiftDefn_Del.put("SHIFT_ID",(Integer)shiftIds.get(i));

                res = con.executeUpdateStatement(SQLMaster.SHIFT_DEFN_DETAILS_DELETE_QUERY,ht_ShiftDefn_Del);
                if(res>=1)
                {
                    hm_Result.put(shiftIds.get(i),new Integer(0));

                    logger.info("RECORD DELETED");
                }
                else
                {

                    hm_Result.put(shiftIds.get(i),new Integer(1));

                    logger.info("RECORD NOT DELETED");
                }
            }

        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }
            if(BuildConfig.DBASE==BuildConfig.ORACLE)
            {
            if(sqle.toString().indexOf("FK_SHIFTID1")>=0)
            {
                throw new WorkCalendarException("WCMEC008","CHILD RECORD FOUND IN CUSTOM_AVBLTY",sqle.toString());
            }
            else if(sqle.toString().indexOf("FK_SHIFTID")>=0)
            {
                throw new WorkCalendarException("WCMEC007","CHILD RECORD FOUND IN BASE_CAL_VAR",sqle.toString());
            }
            else
            {
                throw new WorkCalendarException("WCMEC000","GENERAL SQL ERROR",sqle.toString());
            }
            }
            if(BuildConfig.DBASE==BuildConfig.MYSQL)
            {
            if(sqle.toString().indexOf("foreign key constraint")>=0)
            {
                throw new WorkCalendarException("WCMEC008","CHILD RECORD FOUND IN CUSTOM_AVBLTY OR BASE_CAL_VAR",sqle.toString());
            }
            else
            {
                throw new WorkCalendarException("WCMEC000","GENERAL SQL ERROR",sqle.toString());
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


        logger.info("DELETE SHIFT DEFINITION DETAILS ENDS");

        return hm_Result;
    }
    public ShiftDefnDetails getShiftDefinition(int shiftId) throws WorkCalendarException, SQLException
    {
        ShiftDefnDetails objShiftDefnDetails = new ShiftDefnDetails();
        DBConnection con = null;
        Hashtable ht_ShiftDefn_Get = new Hashtable();
        ResultSet rs_ShiftDefn_Get = null;
        PreparedStatement ps_ShiftDefn_Get = null;
        String st="";


        logger.info("GET SHIFT DEFN DETAILS OF " + shiftId + " STARTED");

        try
        {
            con = DBConnectionFactory.getConnection();
            //put SHIFT_ID into Hashtable
            ht_ShiftDefn_Get.put("SHIFT_ID",new Integer(shiftId));

            //execute the query
            try
            {

                ps_ShiftDefn_Get = con.executeStatement(SQLMaster.GET_SHIFT_DEFN_DETAILS_QUERY,ht_ShiftDefn_Get);
                rs_ShiftDefn_Get = ps_ShiftDefn_Get.executeQuery();

            }
            catch(SQLException ex)
            {
                if(BuildConfig.DMODE)
                {
                    System.out.println("EXCEPTION WHILE SELECTING SHIFT DEFN DETAILS");
                }


                logger.error("SQL ERROR", ex);

                throw new WorkCalendarException("WCMEC000","GENERAL SQL ERROR",ex.toString());
            }
            if(rs_ShiftDefn_Get.next())
            {

                objShiftDefnDetails.setShiftId(rs_ShiftDefn_Get.getInt("SHIFT_ID"));
                objShiftDefnDetails.setShiftName(rs_ShiftDefn_Get.getString("SHIFT_NAME"));
                objShiftDefnDetails.setShiftDesc((st = rs_ShiftDefn_Get.getString("SHIFT_DESC"))==null ? "":st);
                objShiftDefnDetails.setShiftDateStamp(rs_ShiftDefn_Get.getTimestamp("SHIFT_DATESTAMP"));
                objShiftDefnDetails.setShiftIsValid(rs_ShiftDefn_Get.getInt("SHIFT_ISVALID"));


                logger.debug("GET SHIFT DEFN DETAILS FINISHED");

            }
            else
            {
                if(BuildConfig.DMODE)
                    System.out.println("SHIFT DEFN RECORD NOT FOUND");

                logger.error("SHIFT DEFN RECORD NOT FOUND");
                throw new WorkCalendarException("WCMEC009","SHIFT DEFN RECORD NOT FOUND","");

            }
            rs_ShiftDefn_Get.close();
            ps_ShiftDefn_Get.close();

        }
        catch(SQLException e)
        {
            if(BuildConfig.DMODE)
            {
                System.out.println("GENERAL EXCEPTION");
            }


            logger.error("GENERAL SQL ERROR");

            throw new WorkCalendarException("WCMEC000","GENERAL SQL ERROR",e.toString());
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


        logger.info("GET SHIFT DEFN DETAILS OF " + shiftId + " ENDS");

        return objShiftDefnDetails;
    }
    public LinkedHashMap getShiftDefnNameList() throws WorkCalendarException,SQLException
    {
        DBConnection con = null;
        LinkedHashMap hm_Result = new LinkedHashMap();
        ResultSet  rs = null;


        logger.info("GET SHIFT NAME LIST STARTS");

        try
        {
            con = DBConnectionFactory.getConnection();
            Statement st = con.getConnection().createStatement();
            String query = SQLMaster.GET_SHIFT_NAME_LIST;
            try
            {
                rs = st.executeQuery(query);
                while(rs.next())
                {
                    hm_Result.put(new Integer(rs.getInt("SHIFT_ID")),rs.getString("SHIFT_NAME"));
                }
                rs.close();
            }
            catch(SQLException e)
            {
                if(BuildConfig.DMODE)
                {
                    e.printStackTrace();
                    System.out.println("EXCEPTION WHILE SELECTING SHIFT NAME LIST");
                }


                logger.error("GENERAL SQL ERROR");

                throw new WorkCalendarException("WCMEC000","GENERAL SQL ERROR",e.toString());
            }

        }
        catch(SQLException e)
        {
            if(BuildConfig.DMODE)
            {
                e.printStackTrace();
                System.out.println("GENERAL EXCEPTION");
            }

            logger.error("GENERAL SQL ERROR");

            throw new WorkCalendarException("WCMEC000","GENERAL SQL ERROR",e.toString());
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


        logger.info("GET SHIFT NAME LIST ENDS");

        return hm_Result;
    }
    public LinkedHashMap getShiftDefnNameList(Date crntDate) throws WorkCalendarException,SQLException
    {
        DBConnection con = null;
        LinkedHashMap hm_Result = new LinkedHashMap();
        ResultSet  rs = null;
        Hashtable ht = new Hashtable();

        logger.info("GET SHIFT NAME LIST STARTS");

        try
        {
            con = DBConnectionFactory.getConnection();
            ht.put("PROD_DATE",crntDate);

            String query = SQLMaster.GET_SHIFT_NAME_LIST_WITH_CUSTOM_AVBLTY;
            PreparedStatement ps = null;

            ps = con.executeStatement(query,ht);
            rs = ps.executeQuery();
            while(rs.next())
            {
                   hm_Result.put(new Integer(rs.getInt("SHIFT_ID")),rs.getString("SHIFT_NAME"));
            }
            rs.close();
            ps.close();

        }
        catch(SQLException e)
        {
            if(BuildConfig.DMODE)
            {
                e.printStackTrace();
                System.out.println("GENERAL EXCEPTION");
            }

            logger.error("GENERAL SQL ERROR");

            throw new WorkCalendarException("WCMEC000","GENERAL SQL ERROR",e.toString());
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


        logger.info("GET SHIFT NAME LIST ENDS");

        return hm_Result;
    }

    public HashMap getAllShiftDefnDetails(Filter[] filters,String sortBy,boolean ascending,int startIndex,int displayCount) throws WorkCalendarException, SQLException
    {
        HashMap hm_Result = new HashMap();
        int eIndex = 0;
        ResultSet rs_ShiftDefnDet_Get = null;
        int tot_Rec_Cnt;
        DBConnection con = DBConnectionFactory.getConnection();

        eIndex = startIndex + displayCount;
        if(filters == null)
        {

            logger.error( "FILTER VALUES ARE NULL");
            throw new WorkCalendarException("WCMEC010","FILTER VALUES ARE NULL","");
        }


        logger.info("SHIFT DEFINTION FILTER SEARCH STARTS");


        String x =  SQLMaster.SHIFT_DEFINITION_DETAILS_FILTER_QUERY;

        tot_Rec_Cnt = con.getRowCountWithComplexFilters(filters,x);


        String Query = con.buildDynamicQuery(filters,startIndex,eIndex,sortBy,ascending,SQLMaster.SHIFT_DEFINITION_DETAILS_FILTER_QUERY);


        try
        {
            rs_ShiftDefnDet_Get = con.executeRownumStatement(Query);

            Vector vec_ShiftDefnDet_Get = new Vector();

            while(rs_ShiftDefnDet_Get.next())
            {
                ShiftDetails objShiftDetails = new ShiftDetails();
                objShiftDetails.setShiftId(rs_ShiftDefnDet_Get.getInt("SHIFT_ID"));
                objShiftDetails.setShiftName(rs_ShiftDefnDet_Get.getString("SHIFT_NAME"));
                objShiftDetails.setShiftDesc(rs_ShiftDefnDet_Get.getString("SHIFT_DESC"));
                objShiftDetails.setShiftIsValid(rs_ShiftDefnDet_Get.getInt("SHIFT_ISVALID"));
                objShiftDetails.setShiftDateStamp(rs_ShiftDefnDet_Get.getTimestamp("SHIFT_DATESTAMP"));

                vec_ShiftDefnDet_Get.addElement(objShiftDetails);
            }
            hm_Result.put("TotalRecordCount",new Integer(tot_Rec_Cnt));
            hm_Result.put("ShiftDefnDetails",vec_ShiftDefnDet_Get);
            rs_ShiftDefnDet_Get.getStatement().close();
            rs_ShiftDefnDet_Get.close();
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }


            logger.error("EXCEPTION WHILE SELECTING SHIFT DEFINITION DETAILS");

            throw new WorkCalendarException("WCMEC000","GENERAL SQL ERROR",sqle.toString());
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


        logger.info("SHIFT DEFINITION FILTER SEARCH ENDS");

        return 	hm_Result;
    }


    public HashMap makeShiftDefinitionValid(Vector shiftIds) throws WorkCalendarException, SQLException
    {
        HashMap hm_Result = new HashMap();
        Hashtable ht_ShiftDefn_Valid = new Hashtable();
        DBConnection con = null;
        int res = 0;


        logger.info("MAKE SHIFT DEFINITION VALID STARTS");

        if(shiftIds == null)
        {

            logger.info("SHIFT VECTOR OBJECT IS NULL");
            throw new WorkCalendarException("WCMEC011","SHIFT VECTOR OBJECT IS NULL","");
        }
        try
        {
            con = DBConnectionFactory.getConnection();
            for(int i=0;i<shiftIds.size();i++)
            {
                ht_ShiftDefn_Valid.put("SHIFT_ID",(Integer)shiftIds.get(i));

                res = con.executeUpdateStatement(SQLMaster.SHIFT_DEFN_MAKE_VALID_QUERY,ht_ShiftDefn_Valid);
                if(res>=1)
                {
                    hm_Result.put(shiftIds.get(i),new Integer(0));


                    logger.info("RECORD VALIDATED");

                }
                else
                {

                    hm_Result.put(shiftIds.get(i),new Integer(1));


                    logger.info("RECORD NOT VALIDATED");

                }


            }

        }
        catch(SQLException ex)
        {
            if(BuildConfig.DMODE)
            {
                ex.printStackTrace();
            }

            logger.error("GENERAL SQL ERROR");
            throw new WorkCalendarException("WCMEC000","GENERAL SQL ERROR",ex.toString());
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


        logger.info("MAKE SHIFT DEFINITION VALID ENDS");

        return hm_Result;
    }
    public HashMap makeShiftDefinitionInValid(Vector shiftIds) throws WorkCalendarException, SQLException
    {
        HashMap hm_Result = new HashMap();
        Hashtable ht_ShiftDefn_InValid = new Hashtable();
        DBConnection con = null;
        int res = 0;


        logger.info("MAKE SHIFT DEFINITION INVALID STARTED");

        if(shiftIds == null)
        {

            logger.info("SHIFT VECTOR OBJECT IS NULL");
            throw new WorkCalendarException("WCMEC012","SHIFT VECTOR OBJECT IS NULL","");
        }
        try
        {
            con = DBConnectionFactory.getConnection();
            for(int i=0;i<shiftIds.size();i++)
            {
                ht_ShiftDefn_InValid.put("SHIFT_ID",(Integer)shiftIds.get(i));

                res = con.executeUpdateStatement(SQLMaster.SHIFT_DEFN_MAKE_INVALID_QUERY,ht_ShiftDefn_InValid);
                if(res>=1)
                {
                    hm_Result.put(shiftIds.get(i),new Integer(0));


                    logger.info("RECORD INVALIDATED");

                }
                else
                {

                    hm_Result.put(shiftIds.get(i),new Integer(1));


                    logger.info("RECORD NOT INVALIDATED");

                }


            }

        }
        catch(SQLException ex)
        {
            if(BuildConfig.DMODE)
            {
                ex.printStackTrace();
            }

            logger.error("GENERAL SQL ERROR");
            throw new WorkCalendarException("WCMEC000","GENERAL SQL ERROR",ex.toString());
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


        logger.info("MAKE SHIFT DEFINITION INVALID ENDS");

        return hm_Result;
    }

    public boolean addBaseCalendar(BaseCalendarDetails objBaseCalendarDetails) throws WorkCalendarException, SQLException
    {
        boolean addRESULT = false;
        int result = 0;
        int baseCalId=0;
        int st = 0;
        Vector vec_Obj = new Vector();
        Hashtable ht_BaseCal_Add = new Hashtable();
        Hashtable ht_ShiftDet_Add = new Hashtable();
        DBNull dbNull = new DBNull();
        Object objDBNull = dbNull;



        logger.info("BASE CALENDAR ADD STARTS");

        if(objBaseCalendarDetails == null)
        {

            logger.error("BASECALENDAR DETAILS OBJECT IS NULL");
            throw new WorkCalendarException("WCMEC013","BASECALENDAR DETAILS OBJECT IS NULL","");

        }
        if(objBaseCalendarDetails.getBcName().trim().length() == 0)
        {

            logger.error("BASECALENDAR NAME IS REQUIRED");
            throw new WorkCalendarException("WCMEC014","BASECALENDAR NAME IS REQUIRED","");

        }
        if(BuildConfig.DMODE)
        System.out.println("ADD BASE CALENDAR");
        try
        {
            con = DBConnectionFactory.getConnection();
            con.startTransaction();
            ht_BaseCal_Add.put("BASE_CAL_NAME",objBaseCalendarDetails.getBcName());
            ht_BaseCal_Add.put("BASE_CAL_STARTDAY",new Integer(objBaseCalendarDetails.getBcStartDay()));
            ht_BaseCal_Add.put("BASE_CAL_ENDDAY",new Integer(objBaseCalendarDetails.getBcEndDay()));
            result = con.executeUpdateStatement(SQLMaster.BASE_CAL_ADD_SQL_QUERY,ht_BaseCal_Add);
            if(result>0)
            {
                if(BuildConfig.DMODE)
                    System.out.println("BASE CALENDAR NAME" + (result != 0? " " : " NOT ") + "ADDED");
                PreparedStatement ps = con.executeStatement(SQLMaster.BASE_CAL_ID_SELECT_SQL_QUERY);
                ResultSet rs_BaseCal_Id_Get = ps.executeQuery();
                if(rs_BaseCal_Id_Get.next())
                {
                    baseCalId = rs_BaseCal_Id_Get.getInt(1);
                    if(BuildConfig.DMODE)
                        System.out.println("The id is"+baseCalId);
                }
                rs_BaseCal_Id_Get.close();
                ps.close();
                //Iterator itr = hm_MC_Codes.keySet().iterator();
                Vector vec_ShiftDetails = objBaseCalendarDetails.getVec_ShiftDetails();

                for ( int i = 0;i<vec_ShiftDetails.size();i++)
                {
                    ShiftDetails objShiftDetails = new ShiftDetails();
                    objShiftDetails = (ShiftDetails)vec_ShiftDetails.get(i);
                    ht_ShiftDet_Add.put("BASE_CAL_ID",new Integer(baseCalId));
                    if(BuildConfig.DMODE)
                        System.out.println(ht_ShiftDet_Add);
                    ht_ShiftDet_Add.put("SHIFT_ID",new Integer(objShiftDetails.getShiftId()));
                    ht_ShiftDet_Add.put("BASE_CAL_VAR_DAY",new Integer(objShiftDetails.getDay()));
                    ht_ShiftDet_Add.put("BASE_CAL_VAR_STARTTIME",objShiftDetails.getStartTime());
                    ht_ShiftDet_Add.put("BASE_CAL_VAR_STARTTIME_DAY",objShiftDetails.getStartTimeDay());
                    ht_ShiftDet_Add.put("BASE_CAL_VAR_ENDTIME",objShiftDetails.getEndTime());
                    ht_ShiftDet_Add.put("BASE_CAL_VAR_ENDTIME_DAY",objShiftDetails.getEndTimeDay());
                    ht_ShiftDet_Add.put("BASE_CAL_VAR_PREDSRSHIFT",(st =objShiftDetails.getPredsrShiftId())!= 0 ? new Integer(st) : objDBNull);
                    if(BuildConfig.DMODE)
                        System.out.println(ht_ShiftDet_Add);
                    result = con.executeUpdateStatement(SQLMaster.BASE_CAL_VAR_ADD_SQL_QUERY,ht_ShiftDet_Add);
                }
                if(result>0)
                {
                    if(BuildConfig.DMODE)
                        System.out.println("SHIFT DETAILS FOR BASECALENDAR" + (result != 0? " " : " NOT ") + "ADDED");

                    logger.debug("Record Added : " + vec_Obj);
                    con.commitTransaction();
                    addRESULT = true;
                }
                else
                {

                    logger.debug("Record not Added : " + vec_Obj);
                    con.rollBackTransaction();
                }


            }
            else
            {

                logger.debug("Record not Added : " + vec_Obj);

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
            if(BuildConfig.DBASE == BuildConfig.ORACLE)
            {
            if(sqle.toString().indexOf("FK_SHIFTID")>=0)
            {
                throw new WorkCalendarException("WCMEC15","SHIFT RECORD NOT FOUND",sqle.toString());
            }
            else if(sqle.toString().indexOf("FK_BASECALID")>=0)
            {
                throw new WorkCalendarException("WCMEC016","BASE CAL RECORD NOT FOUND",sqle.toString());
            }
            else if(sqle.toString().indexOf("FK_BASECALVARPREDSRSHIFT")>=0)
            {
                throw new WorkCalendarException("WCMEC017","BASE_CAL_VAR_PREDSRSHIFT RECORD NOT FOUND",sqle.toString());
            }
            else
            {
                throw new WorkCalendarException("WCMEC000","GENERAL SQL ERROR",sqle.toString());
            }
            }
            if(BuildConfig.DBASE == BuildConfig.MYSQL)
            {
            if(sqle.toString().indexOf("foreign key constraint")>=0)
            {
                throw new WorkCalendarException("WCMEC15","RECORD NOT FOUND",sqle.toString());
            }
            else
            {
                throw new WorkCalendarException("WCMEC000","GENERAL SQL ERROR",sqle.toString());
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


        logger.info("BASE CALENDAR ADD ENDS");

        return addRESULT;
    }
    public boolean updateBaseCalendar(BaseCalendarDetails objBaseCalendarDetails) throws WorkCalendarException, SQLException
    {
        int st = 0;
        int result = 0;
        boolean updateRESULT = false;
        Vector vec_Obj = new Vector();


        Hashtable ht_BaseCal_Update = new Hashtable();
        Hashtable ht_ShiftDet_Update = new Hashtable();
        DBNull dbNull = new DBNull();
        Object objDBNull = dbNull;


        logger.info("BASE CALENDAR UPDATE STARTS");
        if(BuildConfig.DMODE)
            System.out.println("UPDATE BASE CALENDAR");
        con = DBConnectionFactory.getConnection();
        con.startTransaction();

        ht_BaseCal_Update.put("BASE_CAL_ID",new Integer(objBaseCalendarDetails.getBcId()));
        ht_BaseCal_Update.put("BASE_CAL_STARTDAY",new Integer(objBaseCalendarDetails.getBcStartDay()));
        ht_BaseCal_Update.put("BASE_CAL_ENDDAY",new Integer(objBaseCalendarDetails.getBcEndDay()));

        try
        {
            result = con.executeUpdateStatement(SQLMaster.BASE_CAL_UPDATE_SQL_QUERY,ht_BaseCal_Update);

            if(result>0)
            {
                if(BuildConfig.DMODE)
                    System.out.println("BASE CALENDAR" + (result != 0? " " : " NOT ") + "UPDATED");

                result = con.executeUpdateStatement(SQLMaster.BASE_CAL_VAR_DELETE_QUERY,ht_BaseCal_Update);

                if(BuildConfig.DMODE)
                    System.out.println("BASE CALENDAR SHIFT" + (result != 0? " " : " NOT ") + "DELETED");

                if(result>0)
                {
                    Vector vec_ShiftDetails = objBaseCalendarDetails.getVec_ShiftDetails();

                    for (int i = 0;i<vec_ShiftDetails.size();i++)
                    {
                        ShiftDetails objShiftDetails = new ShiftDetails();
                        objShiftDetails = (ShiftDetails)vec_ShiftDetails.get(i);
                        ht_ShiftDet_Update.put("BASE_CAL_ID",new Integer(objBaseCalendarDetails.getBcId()));

                        ht_ShiftDet_Update.put("SHIFT_ID",new Integer(objShiftDetails.getShiftId()));
                        ht_ShiftDet_Update.put("BASE_CAL_VAR_DAY",new Integer(objShiftDetails.getDay()));
                        ht_ShiftDet_Update.put("BASE_CAL_VAR_STARTTIME",objShiftDetails.getStartTime());
                        ht_ShiftDet_Update.put("BASE_CAL_VAR_STARTTIME_DAY",objShiftDetails.getStartTimeDay());
                        ht_ShiftDet_Update.put("BASE_CAL_VAR_ENDTIME_DAY",objShiftDetails.getEndTimeDay());
                        ht_ShiftDet_Update.put("BASE_CAL_VAR_ENDTIME",objShiftDetails.getEndTime());
                        ht_ShiftDet_Update.put("BASE_CAL_VAR_PREDSRSHIFT",(st =objShiftDetails.getPredsrShiftId())!= 0 ? new Integer(st) : objDBNull);
                        if(BuildConfig.DMODE)
                        {
                            System.out.println(ht_ShiftDet_Update);
                            System.out.println("Value of "+i);
                        }
                        result = con.executeUpdateStatement(SQLMaster.BASE_CAL_VAR_ADD_SQL_QUERY,ht_ShiftDet_Update);
                    }
                    if(result>0)
                    {
                        if(BuildConfig.DMODE)
                            System.out.println("SHIFT DETAILS FOR BASECALENDAR" + (result != 0? " " : " NOT ") + "UPDATED");

                        logger.debug("Record Updated : " + vec_Obj);
                        con.commitTransaction();
                        updateRESULT = true;
                    }
                    else
                    {

                        logger.debug("Record not Updated : " + vec_Obj);
                        con.rollBackTransaction();
                    }

                }
                else
                {

                    logger.debug("RECORD NOT FOUND TO DELETE");
                    throw new WorkCalendarException("WCMEC018","RECORD NOT FOUND TO DELETE","");
                }

            }
            else
            {

                logger.debug("Record not Updated : " + vec_Obj);

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
            if(BuildConfig.DBASE == BuildConfig.ORACLE)
            {
            if(sqle.toString().indexOf("FK_SHIFTID")>=0)
            {
                throw new WorkCalendarException("WCMEC15","SHIFT RECORD NOT FOUND",sqle.toString());
            }
            else if(sqle.toString().indexOf("FK_BASECALID")>=0)
            {
                throw new WorkCalendarException("WCMEC016","BASE CAL RECORD NOT FOUND",sqle.toString());
            }
            else if(sqle.toString().indexOf("FK_BASECALVARPREDSRSHIFT")>=0)
            {
                throw new WorkCalendarException("WCMEC017","BASE_CAL_VAR_PREDSRSHIFT RECORD NOT FOUND",sqle.toString());
            }
            else
            {
                throw new WorkCalendarException("WCMEC000","GENERAL SQL ERROR",sqle.toString());
            }
            }
            if(BuildConfig.DBASE == BuildConfig.MYSQL)
            {
            if(sqle.toString().indexOf("foreign key constraint")>=0)
            {
                throw new WorkCalendarException("WCMEC15","RECORD NOT FOUND",sqle.toString());
            }
            else
            {
                throw new WorkCalendarException("WCMEC000","GENERAL SQL ERROR",sqle.toString());
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


        logger.info("BASECALENDAR UPDATE ENDS");

        return updateRESULT;
    }
    public HashMap deleteBaseCalendar(Vector baseCalIds) throws WorkCalendarException, SQLException
    {
        DBConnection con = null;
        HashMap hm_Result = new HashMap();
        Hashtable ht_baseCalId = new Hashtable();


        logger.info("DELETE BASECALENDAR NAME STARTS");

        try
        {
            con = DBConnectionFactory.getConnection();
            con.startTransaction();
            int result = 0;
            for(int i=0;i<baseCalIds.size();i++)
            {
                ht_baseCalId.put("BASE_CAL_ID",(Integer)baseCalIds.get(i));
                result = con.executeUpdateStatement(SQLMaster.BASE_CAL_VAR_DELETE_QUERY,ht_baseCalId);
                result = con.executeUpdateStatement(SQLMaster.BASE_CAL_DELETE_QUERY,ht_baseCalId);
                if(result > 0)
                {
                    con.commitTransaction();
                    hm_Result.put(baseCalIds.get(i),new Integer(0));

                    logger.debug("Record Deleted");
                }
                else
                {
                    con.rollBackTransaction();
                    hm_Result.put(baseCalIds.get(i),new Integer(1));

                    logger.debug("Record Not Found To Delete");
                }



            }//for loop
        }//first try
        catch(SQLException sqle)
        {

            con.rollBackTransaction();

            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }


            logger.error("EXCEPTION WHILE DELETING BASECALENDAR RECORDS");

            if(BuildConfig.DBASE == BuildConfig.ORACLE)
            {
            if(sqle.toString().indexOf("FK_BASECALID")>=0)
            {
                throw new WorkCalendarException("WCMEC022","CHILD RECORD FOUND IN BASE_CAL_VAR",sqle.toString());
            }
            else if(sqle.toString().indexOf("FK_BASECALID1")>=0)
            {
                throw new WorkCalendarException("WCMEC023","CHILD RECORD FOUND IN AVBLTY_MSTR",sqle.toString());

            }
            else
            {
                throw new WorkCalendarException("WCMEC000","GENERAL SQL ERROR",sqle.toString());
            }
            }
            if(BuildConfig.DBASE == BuildConfig.MYSQL)
            {
            if(sqle.toString().indexOf("foreign key constraint")>=0)
            {
                throw new WorkCalendarException("WCMEC022","CHILD RECORD FOUND IN BASE_CAL_VAR OR IN AVBLTY_MSTR",sqle.toString());
            }
            else
            {
                throw new WorkCalendarException("WCMEC000","GENERAL SQL ERROR",sqle.toString());
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


        logger.info("DELETE BASECALENDAR ENDS");


        return hm_Result;

    }

    public BaseCalendarDetails getBaseCalendarDetails(int baseCalId) throws WorkCalendarException, SQLException
    {
        DBConnection con = null;
        Hashtable ht_BaseCal_Det_Get = new Hashtable();
        ResultSet rs_BaseCal_Det_Get = null;
        ResultSet rs_BaseCal_ShiftDet_Get = null;
        Vector vec = new Vector();
        BaseCalendarDetails objBaseCalendarDetails = new BaseCalendarDetails();



        logger.info("GET BASE CALENDAR DETAILS STARTS");


        con = DBConnectionFactory.getConnection();

        ht_BaseCal_Det_Get.put("BAS_CAL_ID",new Integer(baseCalId));
        try
        {
            PreparedStatement ps_BaseCal_Det_Get = con.executeStatement(SQLMaster.GET_BASE_CAL_DETAILS_QUERY,ht_BaseCal_Det_Get);
            rs_BaseCal_Det_Get = ps_BaseCal_Det_Get.executeQuery();

            if(rs_BaseCal_Det_Get.next())
            {
                objBaseCalendarDetails.setBcId(baseCalId);
                objBaseCalendarDetails.setBcName(rs_BaseCal_Det_Get.getString("BASE_CAL_NAME"));
                objBaseCalendarDetails.setBcStartDay(rs_BaseCal_Det_Get.getInt("BASE_CAL_STARTDAY"));
                objBaseCalendarDetails.setBcEndDay(rs_BaseCal_Det_Get.getInt("BASE_CAL_ENDDAY"));
                objBaseCalendarDetails.setBcDateStamp(rs_BaseCal_Det_Get.getTimestamp("BASE_CAL_DATESTAMP"));
                objBaseCalendarDetails.setBcIsValid(rs_BaseCal_Det_Get.getInt("BASE_CAL_ISVALID"));
                PreparedStatement ps_BaseCal_ShiftDet_Get = con.executeStatement(SQLMaster.GET_BASE_CAL_SHIFT_DETAILS,ht_BaseCal_Det_Get);
                rs_BaseCal_ShiftDet_Get = ps_BaseCal_ShiftDet_Get.executeQuery();
                ResultSet rs = null;
                PreparedStatement ps = null;
                while(rs_BaseCal_ShiftDet_Get.next())
                {
                    ShiftDetails objShiftDetails = new ShiftDetails();
                    int sh = 0;
                    objShiftDetails.setShiftId(rs_BaseCal_ShiftDet_Get.getInt("SHIFT_ID"));
                    objShiftDetails.setShiftName(rs_BaseCal_ShiftDet_Get.getString("SHIFT_NAME"));
                    objShiftDetails.setDay(rs_BaseCal_ShiftDet_Get.getInt("BASE_CAL_VAR_DAY"));
                    objShiftDetails.setStartTime(rs_BaseCal_ShiftDet_Get.getString("BASE_CAL_VAR_STARTTIME"));
                    objShiftDetails.setStartTimeDay(rs_BaseCal_ShiftDet_Get.getString("BASE_CAL_VAR_STARTTIME_DAY"));
                    objShiftDetails.setEndTime(rs_BaseCal_ShiftDet_Get.getString("BASE_CAL_VAR_ENDTIME"));
                    objShiftDetails.setEndTimeDay(rs_BaseCal_ShiftDet_Get.getString("BASE_CAL_VAR_ENDTIME_DAY"));
                    objShiftDetails.setPredsrShiftId((sh = rs_BaseCal_ShiftDet_Get.getInt("BASE_CAL_VAR_PREDSRSHIFT"))!=0 ? sh : 0);

                    //to get Predecessor Shift Name//
                    int prdcsr_id = objShiftDetails.getPredsrShiftId();
                    Hashtable ht_Prdcsr_Id = new Hashtable();
                    ht_Prdcsr_Id.put("BASE_CAL_VAR_PREDSRSHIFT",new Integer(prdcsr_id));
                    ps = con.executeStatement(SQLMaster.GET_PRDCSR_SHIFT_NAME,ht_Prdcsr_Id);
                    rs = ps.executeQuery();
                    String Prdcsr_Name = "";
                    if(rs.next())
                    {
                        Prdcsr_Name = rs.getString("SHIFT_NAME");
                    }
                    objShiftDetails.setPredsrShiftName(Prdcsr_Name);
                    //END-to get Predecessor Shift Name//

                    //set all shift details in vector//
                    vec.addElement(objShiftDetails);

                }
                rs_BaseCal_ShiftDet_Get.close();
                ps_BaseCal_ShiftDet_Get.close();
                objBaseCalendarDetails.setVec_ShiftDetails(vec);

            }
            else
            {

                logger.info("BASECALENDAR RECORD NOT FOUND");

                if(BuildConfig.DMODE)
                    System.out.println("BASECALENDAR NOT FOUND");
                throw new WorkCalendarException("WCMEC024","BASECALENDAR RECORD NOT FOUND","");
            }
            rs_BaseCal_Det_Get.close();
            ps_BaseCal_Det_Get.close();
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
                System.out.println("EXCEPTION WHILE GETTING BASECALENDAR DETAILS");
            }


            logger.error("GENERAL SQL ERROR");

            throw new WorkCalendarException("WCMEC000","GENERAL SQL ERROR",sqle.toString());
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


        logger.info("GET BASECALENDAR DETAILS OF " + baseCalId + " ENDS");

        return objBaseCalendarDetails;

    }
    public HashMap getBaseCalendarNameList() throws WorkCalendarException, SQLException
    {
        DBConnection con = null;
        HashMap hm_Result = new HashMap();
        ResultSet  rs = null;


        logger.info("GET BASECALENDAR NAME LIST STARTS");

        try
        {
            con = DBConnectionFactory.getConnection();
            Statement st = con.getConnection().createStatement();
            String query = SQLMaster.GET_BASE_CAL_NAME_LIST;
            try
            {
                rs = st.executeQuery(query);
                while(rs.next())
                {
                    hm_Result.put(new Integer(rs.getInt("BASE_CAL_ID")),rs.getString("BASE_CAL_NAME"));
                }
                rs.close();

            }
            catch(SQLException e)
            {
                if(BuildConfig.DMODE)
                {
                    e.printStackTrace();
                    System.out.println("EXCEPTION WHILE SELECTING BASE CALENDAR NAME LIST");
                }

                logger.error("GENERAL SQL ERROR");

                throw new WorkCalendarException("WCMEC000","GENERAL SQL ERROR",e.toString());
            }

        }
        catch(SQLException e)
        {
            if(BuildConfig.DMODE)
            {
                e.printStackTrace();
                System.out.println("GENERAL EXCEPTION");
            }

            logger.error("GENERAL SQL ERROR");

            throw new WorkCalendarException("WCMEC000","GENERAL SQL ERROR",e.toString());
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


        logger.info("GET BASECALENDAR NAME LIST ENDS");


        return hm_Result;
    }
    public HashMap getAllBaseCalendarDetails(Filter[] filters,String sortBy,boolean ascending,int startIndex,int displayCount) throws WorkCalendarException, SQLException
    {
        HashMap hm_Result = new HashMap();
        int eIndex = 0;
        ResultSet rs_BaseCalDet_Get = null;
        Vector vec_BaseCalDet_Get = new Vector();
        int tot_Rec_Cnt;
        DBConnection con = DBConnectionFactory.getConnection();

        eIndex = startIndex + displayCount;


        logger.info("BASE CALENDAR FILTER SEARCH STARTS");

        if(filters == null)
            throw new WorkCalendarException("WCMEC025","FILTER VALUES ARE NULL","");


        tot_Rec_Cnt = con.getRowCountWithComplexFilters(filters,SQLMaster.BASE_CALENDAR_DETAILS_FILTER_QUERY);



        String Query = con.buildDynamicQuery(filters,startIndex,eIndex,sortBy,ascending,SQLMaster.BASE_CALENDAR_DETAILS_FILTER_QUERY);


        try
        {
            rs_BaseCalDet_Get = con.executeRownumStatement(Query);

            while(rs_BaseCalDet_Get.next())
            {
                BaseCalendarDetails objBaseCalendarDetails = new BaseCalendarDetails();
                objBaseCalendarDetails.setBcId(rs_BaseCalDet_Get.getInt("BASE_CAL_ID"));
                objBaseCalendarDetails.setBcName(rs_BaseCalDet_Get.getString("BASE_CAL_NAME"));
                objBaseCalendarDetails.setBcStartDay(rs_BaseCalDet_Get.getInt("BASE_CAL_STARTDAY"));
                objBaseCalendarDetails.setBcEndDay(rs_BaseCalDet_Get.getInt("BASE_CAL_ENDDAY"));
                objBaseCalendarDetails.setBcIsValid(rs_BaseCalDet_Get.getInt("BASE_CAL_ISVALID"));
                objBaseCalendarDetails.setBcDateStamp(rs_BaseCalDet_Get.getTimestamp("BASE_CAL_DATESTAMP"));

                vec_BaseCalDet_Get.addElement(objBaseCalendarDetails);
            }
            hm_Result.put("TotalRecordCount",new Integer(tot_Rec_Cnt));
            hm_Result.put("BaseCalendarDetails",vec_BaseCalDet_Get);
            rs_BaseCalDet_Get.getStatement().close();
            rs_BaseCalDet_Get.close();
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }

            logger.error("EXCEPTION WHILE SELECTING BASE CALENDAR DETAILS");

            throw new WorkCalendarException("WCMEC000","GENERAL SQL ERROR",sqle.toString());
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


        logger.info("BASE CALENDAR FILTER SEARCH ENDS");

        return 	hm_Result;
    }


    public HashMap makeBaseCalendarValid(Vector baseCalIds) throws WorkCalendarException, SQLException
    {
        DBConnection con = null;
        HashMap hm_Result = new HashMap();
        Hashtable ht_baseCalId = new Hashtable();
        int result = 0;


        logger.info("BASECALENDAR MAKE VALID STARTS");

        if(baseCalIds == null)
            throw new WorkCalendarException("WCMEC026","VECTOR OBJECT IS NULL","");
        try
        {
            con = DBConnectionFactory.getConnection();
            for(int i=0;i<baseCalIds.size();i++)
            {
                ht_baseCalId.put("BASE_CAL_ID",(Integer)baseCalIds.get(i));
                result = con.executeUpdateStatement(SQLMaster.BASE_CAL_MAKE_VALID_QUERY,ht_baseCalId);
                if(result > 0)
                {
                    hm_Result.put(baseCalIds.get(i),new Integer(0));

                    logger.debug("RECORD VALIDATED");
                }
                else
                {
                    hm_Result.put(baseCalIds.get(i),new Integer(1));

                    logger.debug("RECORD NOT VALIDATED");
                }

            }
        }
        catch(SQLException ex)
        {
            if(BuildConfig.DMODE)
            {
                ex.printStackTrace();
            }

            logger.error("GENERAL EXCEPTION");

            throw new WorkCalendarException("WCMEC000","GENERAL SQL ERROR",ex.toString());

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


        logger.info("BASECALENDAR MAKE VALID ENDS");

        return hm_Result;
    }
    public HashMap makeBaseCalendarInValid(Vector baseCalIds) throws WorkCalendarException, SQLException
    {
        DBConnection con = null;
        HashMap hm_Result = new HashMap();
        Hashtable ht_baseCalId = new Hashtable();
        int result = 0;


        logger.info("BASECALENDAR MAKE INVALID STARTS");

        if(baseCalIds == null)
            throw new WorkCalendarException("WCMEC027","VECTOR OBJECT IS NULL","");

        try
        {
            con = DBConnectionFactory.getConnection();
            for(int i=0;i<baseCalIds.size();i++)
            {
                ht_baseCalId.put("BASE_CAL_ID",(Integer)baseCalIds.get(i));
                result = con.executeUpdateStatement(SQLMaster.BASE_CAL_MAKE_INVALID_QUERY,ht_baseCalId);
                if(result > 0)
                {
                    hm_Result.put(baseCalIds.get(i),new Integer(0));

                    logger.debug("RECORD INVALIDATED");
                }
                else
                {
                    hm_Result.put(baseCalIds.get(i),new Integer(1));

                    logger.debug("RECORD NOT INVALIDATED");
                }

            }
        }
        catch(SQLException ex)
        {
            if(BuildConfig.DMODE)
            {
                ex.printStackTrace();
            }

            logger.error("GENERAL EXCEPTION");

            throw new WorkCalendarException("WCMEC000","GENERAL SQL ERROR",ex.toString());

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


        logger.info("BASECALENDAR MAKE INVALID ENDS");

        return hm_Result;
    }

    public boolean addAvailabilityCalendar(AvailabilityDetails objAvailabilityDetails) throws WorkCalendarException, SQLException
    {

        boolean addRESULT = false;
        int result;
        Hashtable ht_AvbltyCal_Add = new Hashtable();



        logger.info("AVAILABILITY CALENDAR ADD STARTS");

        if(objAvailabilityDetails ==  null)
            throw new WorkCalendarException("WCMEC028","AVAILABILITY OBJECT IS NULL","");

        if(BuildConfig.DMODE)
            System.out.println("ADD AVAILABILITY CALENDAR");
        con = DBConnectionFactory.getConnection();
        con.startTransaction();

        ht_AvbltyCal_Add.put("BASE_CAL_ID",new Integer(objAvailabilityDetails.getBaseCalId()));
        ht_AvbltyCal_Add.put("AVBLTY_NAME",objAvailabilityDetails.getAvailabilityName());
        ht_AvbltyCal_Add.put("AVBLTY_FROMDATE",objAvailabilityDetails.getFromDate());
        ht_AvbltyCal_Add.put("AVBLTY_TODATE",objAvailabilityDetails.getToDate());
        try
        {
            result = con.executeUpdateStatement(SQLMaster.AVBLTY_CAL_ADD_SQL_QUERY,ht_AvbltyCal_Add);
            if(result>0)
            {

                if(BuildConfig.DMODE)
                    System.out.println("AVAILABILITY CALENDAR NAME" + (result != 0? " " : " NOT ") + "ADDED");
                PreparedStatement ps = con.executeStatement(SQLMaster.LAST_AVBLTY_ID_SELECT_SQL_QUERY);
                ResultSet rs = ps.executeQuery();

                if(rs.next())
                objAvailabilityDetails.setAvailabilityId(rs.getInt(1));

                rs.close();
                ps.close();

                if(addCustomAvbltyDetails(objAvailabilityDetails,con))
                {
                    if(addCustomNonAvbltyDetails(objAvailabilityDetails,con))
                    {
                        addRESULT = true;
                        con.commitTransaction();
                    }
                    else
                    {
                        con.rollBackTransaction();
                    }
                }
                else
                {
                    con.rollBackTransaction();
                }

            }
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }

            logger.error("SQL EXCEPTION", sqle);

            if(BuildConfig.DBASE==BuildConfig.ORACLE)
            {
            if(sqle.toString().indexOf("PK_AVBLTYID")>=0)
            {
                throw new WorkCalendarException("WCMEC029","AVAILABILITY ID ALREADY EXISTS",sqle.toString());
            }
            else if(sqle.toString().indexOf("FK_BASECALID1")>=0)
            {
                throw new WorkCalendarException("WCMEC030","BASE_CAL RECORD FOUND",sqle.toString());
            }
            else
            {
                throw new WorkCalendarException("WCMEC000","GENERAL SQL ERROR",sqle.toString());
            }
            }
            if(BuildConfig.DBASE==BuildConfig.MYSQL)
            {
            if(sqle.toString().indexOf("Duplicate entry")>=0)
            {
                throw new WorkCalendarException("WCMEC029","AVAILABILITY ID ALREADY EXISTS",sqle.toString());
            }
            else if(sqle.toString().indexOf("foreign key constraint")>=0)
            {
                throw new WorkCalendarException("WCMEC030","BASE_CAL RECORD FOUND",sqle.toString());
            }
            else
            {
                throw new WorkCalendarException("WCMEC000","GENERAL SQL ERROR",sqle.toString());
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


        logger.info("AVAILABILITY CALENDAR ADD ENDS");


        return addRESULT;

    }
    public boolean updateAvailabilityCalendar(AvailabilityDetails objAvailabilityDetails) throws WorkCalendarException, SQLException
    {

        boolean updateRESULT = false;

        int result;
        Hashtable ht_AvbltyCal_Update = new Hashtable();



        logger.info("AVAILABILITY CALENDAR UPDATE STARTS");

        if(objAvailabilityDetails ==  null)
            throw new WorkCalendarException("WCMEC028","AVAILABILITY OBJECT IS NULL","");

        if(BuildConfig.DMODE)
            System.out.println("UPDATE AVAILABILITY CALENDAR");
        con = DBConnectionFactory.getConnection();
        con.startTransaction();

        ht_AvbltyCal_Update.put("AVBLTY_ID",new Integer(objAvailabilityDetails.getAvailabilityId()));
        ht_AvbltyCal_Update.put("BASE_CAL_ID",new Integer(objAvailabilityDetails.getBaseCalId()));
        ht_AvbltyCal_Update.put("AVBLTY_NAME",objAvailabilityDetails.getAvailabilityName());
        ht_AvbltyCal_Update.put("AVBLTY_FROMDATE",objAvailabilityDetails.getFromDate());
        ht_AvbltyCal_Update.put("AVBLTY_TODATE",objAvailabilityDetails.getToDate());
        try
        {
            int res = con.executeUpdateStatement(SQLMaster.CUSTOM_AVBLTY_DELETE_QUERY,ht_AvbltyCal_Update);
            if(res>0)
            {
                if(BuildConfig.DMODE)
                    System.out.println("CUSTOM AVBLTY DELETED");
                logger.info("CUSTOM AVBLTY DELETED");
            }
            res = con.executeUpdateStatement(SQLMaster.CUSTOM_NON_AVBLTY_DELETE_QUERY,ht_AvbltyCal_Update);
            if(res>0)
            {
                if(BuildConfig.DMODE)
                    System.out.println("CUSTOM NON AVBLTY DELETED");
                logger.info("CUSTOM NON AVBLTY DELETED");
            }

            result = con.executeUpdateStatement(SQLMaster.AVBLTY_CAL_UPDATE_SQL_QUERY,ht_AvbltyCal_Update);
            if(result>0)
            {

                if(BuildConfig.DMODE)
                    System.out.println("AVAILABILITY CALENDAR NAME" + (result != 0? " " : " NOT ") + "UPDATED");
                if(addCustomAvbltyDetails(objAvailabilityDetails,con))
                {
                    if(addCustomNonAvbltyDetails(objAvailabilityDetails,con))
                    {
                        updateRESULT = true;
                        con.commitTransaction();
                    }
                    else
                    {
                        con.rollBackTransaction();
                    }
                }
                else
                {
                    con.rollBackTransaction();
                }

            }
            else
            {
                con.rollBackTransaction();
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
            if(sqle.toString().indexOf("PK_AVBLTYID")>=0)
            {
                throw new WorkCalendarException("WCMEC029","AVAILABILITY ID ALREADY EXISTS",sqle.toString());
            }
            else if(sqle.toString().indexOf("FK_BASECALID1")>=0)
            {
                throw new WorkCalendarException("WCMEC030","BASE_CAL RECORD FOUND",sqle.toString());
            }
            else
            {
                throw new WorkCalendarException("WCMEC000","GENERAL SQL ERROR",sqle.toString());
            }
            }
            if(BuildConfig.DBASE==BuildConfig.MYSQL)
            {
            if(sqle.toString().indexOf("Duplicate entry")>=0)
            {
                throw new WorkCalendarException("WCMEC029","AVAILABILITY ID ALREADY EXISTS",sqle.toString());
            }
            else if(sqle.toString().indexOf("foreign key constraint")>=0)
            {
                throw new WorkCalendarException("WCMEC030","BASE_CAL RECORD FOUND",sqle.toString());
            }
            else
            {
                throw new WorkCalendarException("WCMEC000","GENERAL SQL ERROR",sqle.toString());
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


        logger.info("AVAILABILITY CALENDAR UPDATE ENDS");


        return updateRESULT;

    }
    public boolean addCustomAvbltyDetails(AvailabilityDetails objAvailabilityDetails,DBConnection con) throws WorkCalendarException,SQLException
    {
        boolean addRESULT = false;
        int result = 0;
        int st = 0;
        boolean flg = false;
        Hashtable ht_CustomDet_Add = new Hashtable();
        CustomAvbltyDetails objCustomDetails = new CustomAvbltyDetails();
        Vector vec_CustomDet = objAvailabilityDetails.getCustomAvbltyDetails();
        Vector vec_ShiftDet = new Vector();
        DBNull dbNull = new DBNull();
        Object objDBNull = dbNull;


        logger.info("CUSTOM AVAILABILITY ADD STARTS");

        if(objAvailabilityDetails == null)
            throw new WorkCalendarException("WCMEC031","AVAILABILITY CUSTOM DETAILS OBJECT IS NULL","");

        if(BuildConfig.DMODE)
            System.out.println("ADD CUSTOM AVAILABILITY");
        try
        {
            for ( int i = 0 ;i<vec_CustomDet.size();i++)
            {
                objCustomDetails = (CustomAvbltyDetails)vec_CustomDet.get(i);
                ht_CustomDet_Add.put("AVBLTY_ID",new Integer(objAvailabilityDetails.getAvailabilityId()));
                ht_CustomDet_Add.put("CUSTOM_AVBLTY_FORDATE",objCustomDetails.getForDate());
                vec_ShiftDet = objCustomDetails.getVec_ShiftDet();

                for(int j = 0;j<vec_ShiftDet.size();j++)
                {

                    ShiftDetails objShiftDetails = new ShiftDetails();
                    objShiftDetails = (ShiftDetails)vec_ShiftDet.get(j);

                   ht_CustomDet_Add.put("SHIFT_ID",new Integer(objShiftDetails.getShiftId()));
                   ht_CustomDet_Add.put("CUSTOM_AVBLTY_STARTTIME",objShiftDetails.getStartTime());
                   ht_CustomDet_Add.put("CUSTOM_AVBLTY_STARTDAY",objShiftDetails.getStartTimeDay());
                   ht_CustomDet_Add.put("CUSTOM_AVBLTY_ENDTIME",objShiftDetails.getEndTime());
                   ht_CustomDet_Add.put("CUSTOM_AVBLTY_ENDDAY",objShiftDetails.getEndTimeDay());
                   ht_CustomDet_Add.put("CUSTOM_AVBLTY_PREDSRSHIFT",(st =objShiftDetails.getPredsrShiftId())!= 0 ? new Integer(st) : objDBNull);
                if(BuildConfig.DMODE)
                System.out.println("Shift Id :"+objShiftDetails.getPredsrShiftId());
                result = con.executeUpdateStatement(SQLMaster.CUSTOM_AVBLTY_ADD_SQL_QUERY,ht_CustomDet_Add);
                if(result<=0)
                {

                    addRESULT = false;
                    if(BuildConfig.DMODE)
                        System.out.println("CUSTOM AVAILABILITY " + (result != 0? " " : " NOT ") + "ADDED");
                    flg = true;
                    break;

                }
            }
            }
            if(!flg)
            {
                addRESULT = true;
            }
        }

        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }

            logger.error("SQL EXCEPTION", sqle);

            if(BuildConfig.DBASE==BuildConfig.ORACLE)
            {
            if(sqle.toString().indexOf("UK_AVBSFTFORDATE")>=0)
            {
                throw new WorkCalendarException("WCMEC032","CUSTOM AVAILABILITY ALREADY EXISTS",sqle.toString());
            }
            else if(sqle.toString().indexOf("FK_AVBLTYID")>=0)
            {
                throw new WorkCalendarException("WCMEC033","AVAILABILITY RECORD NOT FOUND",sqle.toString());
            }
            else if(sqle.toString().indexOf("FK_SHIFTID1")>=0)
            {
                throw new WorkCalendarException("WCMEC034","SHIFT RECORD NOT FOUND",sqle.toString());
            }
            else
            {
                throw new WorkCalendarException("WCMEC000","GENERAL SQL ERROR",sqle.toString());
            }
            }
            if(BuildConfig.DBASE==BuildConfig.MYSQL)
            {
            if(sqle.toString().indexOf("Duplicate entry")>=0)
            {
                throw new WorkCalendarException("WCMEC032","CUSTOM AVAILABILITY ALREADY EXISTS",sqle.toString());
            }
            else if(sqle.toString().indexOf("foreign key constraint")>=0)
            {
                throw new WorkCalendarException("WCMEC033","AVAILABILITY RECORD OR SHIFT RECORD NOT FOUND",sqle.toString());
            }
            else
            {
                throw new WorkCalendarException("WCMEC000","GENERAL SQL ERROR",sqle.toString());
            }
            }

        }

        logger.info("CUSTOM AVAILABILITY ADD ENDS");


        return addRESULT;

    }

    public boolean addCustomNonAvbltyDetails(AvailabilityDetails objAvailabilityDetails,DBConnection con) throws WorkCalendarException, SQLException
    {
        boolean addRESULT = false;
        boolean flg = false;
        int result = 0;
        Hashtable ht_CustomDet_Add = new Hashtable();
        CustomNonAvbltyDetails objCustomDetails = new CustomNonAvbltyDetails();
        Vector vec_CustomDet = objAvailabilityDetails.getNonAvbltyDetails();


        logger.info("CUSTOM NON AVAILABILITY ADD STARTS");

        if(objAvailabilityDetails == null)
            throw new WorkCalendarException("WCMEC035","AVAILABILITY CUSTOM DETAILS OBJECT IS NULL","");

        if(BuildConfig.DMODE)
            System.out.println("ADD CUSTOM NON AVAILABILITY");
        try
        {
            for ( int i = 0 ;i<vec_CustomDet.size();i++)
            {
                objCustomDetails = (CustomNonAvbltyDetails)vec_CustomDet.get(i);
                ht_CustomDet_Add.put("AVBLTY_ID",new Integer(objAvailabilityDetails.getAvailabilityId()));
                ht_CustomDet_Add.put("CUSTOM_N_AVBLTY_FORDATE",objCustomDetails.getForDate());
                ht_CustomDet_Add.put("CUSTOM_N_AVBLTY_RSN",objCustomDetails.getNonAvbltyReason());
                result = con.executeUpdateStatement(SQLMaster.CUSTOM_NON_AVBLTY_ADD_SQL_QUERY,ht_CustomDet_Add);
                if(result<=0)
                {
                    addRESULT = false;
                    if(BuildConfig.DMODE)
                        System.out.println("CUSTOM NON AVAILABILITY " + (result != 0? " " : " NOT ") + "ADDED");
                    flg = true;
                }
            }
            if(!flg)
            {
                addRESULT = true;
            }
        }

        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }

            logger.error("SQL EXCEPTION", sqle);

            if(BuildConfig.DBASE==BuildConfig.ORACLE)
            {
            if(sqle.toString().indexOf("UK_AVBFORDATE")>=0)
            {
                throw new WorkCalendarException("WCMEC036","NON CUSTOM AVAILABILITY ALREADY EXISTS",sqle.toString());
            }
            else
                if(sqle.toString().indexOf("FK_AVBLTYID1")>=0)
                {
                    throw new WorkCalendarException("WCMEC037","AVAILABILITY RECORD NOT FOUND",sqle.toString());
                }
                else
                {
                    throw new WorkCalendarException("WCMEC000","GENERAL SQL ERROR",sqle.toString());
                }
            }
            if(BuildConfig.DBASE==BuildConfig.MYSQL)
            {
            if(sqle.toString().indexOf("Duplicate entry")>=0)
            {
                throw new WorkCalendarException("WCMEC036","NON CUSTOM AVAILABILITY ALREADY EXISTS",sqle.toString());
            }
            else
                if(sqle.toString().indexOf("foreign key constraint")>=0)
                {
                    throw new WorkCalendarException("WCMEC037","AVAILABILITY RECORD NOT FOUND",sqle.toString());
                }
                else
                {
                    throw new WorkCalendarException("WCMEC000","GENERAL SQL ERROR",sqle.toString());
                }
            }

        }


        logger.info("CUSTOM NON AVAILABILITY ADD ENDS");

        return addRESULT;

    }
    /*public boolean updateAvailabilityCalendar(AvailabilityDetails objAvailabilityDetails) throws WorkCalendarException,SQLException
     {
     boolean result = false;
     //AvailabilityDetails objAvailabilityDetails = new AvailabilityDetails();
      ShiftDetails objShiftDetails = new ShiftDetails();
      Hashtable ht_AvbCal_Update = new Hashtable();
      DBConnection con = null;
      int result_Avb = 0;
      int result_CusAvb = 0;
      int result_NonCusAvb = 0;
      try
      {
      con = DBConnectionFactory.getConnection();

      ht_AvbCal_Update.put("AVBLTY_ID",new Integer(objAvailabilityDetails.getAvailabilityId()));
      Vector vec_AvbCal_Update = con.getProductVector(SQLMaster.UPDATE_AVAILABILTIY_CALENDAR_QUERY,ht_AvbCal_Update);
      String query = con.getProductQuery(SQLMaster.UPDATE_AVAILABILTIY_CALENDAR_QUERY);
      PreparedStatement ps_AvbCal_Update = con.executeRawQuery(query,vec_AvbCal_Update);

      try
      {
      result_Avb = ps_AvbCal_Update.executeUpdate();

      if(result_Avb > 0)
      {

      Vector vec_CusAvb_Update = con.getProductVector(SQLMaster.UPDATE_CUSTOM_AVAILABILTIY_QUERY,ht_AvbCal_Update);
      String query_CusAvb = con.getProductQuery(SQLMaster.UPDATE_CUSTOM_AVAILABILTIY_QUERY);
      PreparedStatement ps_CusAvb_Update = con.executeRawQuery(query_CusAvb,vec_CusAvb_Update);

      result_CusAvb = ps_CusAvb_Update.executeUpdate();

      Vector vec_NonCusAvb_Update = con.getProductVector(SQLMaster.UPDATE_CUSTOM_AVAILABILTIY_QUERY,ht_AvbCal_Update);
      String query_NonCusAvb = con.getProductQuery(SQLMaster.UPDATE_CUSTOM_AVAILABILTIY_QUERY);
      PreparedStatement ps_NonCusAvb_Update = con.executeRawQuery(query_NonCusAvb,vec_NonCusAvb_Update);

      result_CusAvb = ps_NonCusAvb_Update.executeUpdate();
      if(BuildConfig.DMODE)
      {

      logger.debug("RECORDS UPDATED");
      }

      }
      else
      }
      catch(SQLException sqle)
      {
      sqle.printStackTrace();
      }


      }


      }*/
    public HashMap deleteAvailablityCalendar(Vector avlbltyIds) throws  WorkCalendarException, SQLException
    {
        DBConnection con = null;
        HashMap hm_Result = new HashMap();
        Hashtable ht_AvlbltyId = new Hashtable();


        logger.info("DELETE AVAILABITY CALENDAR STARTS");

        try
        {
            con = DBConnectionFactory.getConnection();
            con.startTransaction();
            int cus_Avlblty_Del = 0;
            int cus_NonAvlblty_Del = 0;
            int avlblty_Del = 0;

            for(int i=0;i<avlbltyIds.size();i++)
            {
                ht_AvlbltyId.put("AVBLTY_ID",(Integer)avlbltyIds.get(i));

                //custom non-availability delete starts

                logger.info("CUSTOM NON-AVAILABILITY DELETE STARTS");

                cus_NonAvlblty_Del = con.executeUpdateStatement(SQLMaster.CUSTOM_NON_AVBLTY_DELETE_QUERY,ht_AvlbltyId);
                if(cus_NonAvlblty_Del > 0)
                {



                    logger.debug("CUSTOM NON-AVAILABILITY DELETED");

                    if(BuildConfig.DMODE)
                        System.out.println("CUSTOM NON-AVAILABILITY DELETED");
                }
                else
                {


                    logger.debug("CUSTOM NON-AVAILABILITY NOT DELETED");

                    if(BuildConfig.DMODE)
                        System.out.println("CUSTOM NON-AVAILABILITY NOT DELETED");
                }


                logger.info("CUSTOM NON-AVAILABILITY DELETE ENDS");
                //custom non-availabilty delete ends

                //custom availability delete starts

                logger.info("CUSTOM AVAILABILITY DELETE STARTS");

                cus_Avlblty_Del = con.executeUpdateStatement(SQLMaster.CUSTOM_AVBLTY_DELETE_QUERY,ht_AvlbltyId);
                if(cus_Avlblty_Del > 0)
                {

                    logger.debug("CUSTOM AVAILABILITY DELETED");

                    if(BuildConfig.DMODE)
                        System.out.println("CUSTOM AVAILABILITY DELETED");
                }
                else
                {


                    logger.debug("CUSTOM AVAILABILITY NOT DELETED");

                    if(BuildConfig.DMODE)
                        System.out.println("CUSTOM AVAILABILITY NOT DELETED");
                }


                logger.info("CUSTOM AVAILABILITY DELETE ENDS");
                //custom availability delete ends

                //availability delete starts

                logger.info("AVAILABILITY DELETE STARTS");

                avlblty_Del = con.executeUpdateStatement(SQLMaster.AVBLTY_DELETE_QUERY,ht_AvlbltyId);
                if(avlblty_Del > 0)
                {
                    con.commitTransaction();
                    hm_Result.put(avlbltyIds.get(i),new Integer(0));


                    logger.debug("Record Deleted");

                    if(BuildConfig.DMODE)
                        System.out.println("AVAILABILITY RECORD DELETED");
                }
                else
                {
                    con.rollBackTransaction();
                    hm_Result.put(avlbltyIds.get(i),new Integer(1));


                    logger.debug("Record Not Found To Delete");

                    if(BuildConfig.DMODE)
                        System.out.println("AVAILABILITY RECORD DELETED");
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

            logger.error("EXCEPTION WHILE DELETING AVAILABILITY RECORDS");

            if(BuildConfig.DBASE==BuildConfig.ORACLE)
            {
            if(sqle.toString().indexOf("FK_AVBLTYID1")>=0)
            {
                throw new WorkCalendarException("WCEC0","CHILD RECORD FOUND",sqle.toString());
            }
            else if(sqle.toString().indexOf("FK_AVBLTYID")>=0)
            {
                throw new WorkCalendarException("WCEC0","CHILD RECORD FOUND",sqle.toString());
            }
            else
                throw new WorkCalendarException("WCEC0","GENERAL SQL ERROR",sqle.toString());
            }
            if(BuildConfig.DBASE==BuildConfig.MYSQL)
            {
            if(sqle.toString().indexOf("foreign key constraint")>=0)
            {
                throw new WorkCalendarException("WCEC0","CHILD RECORD FOUND",sqle.toString());
            }
            else
                throw new WorkCalendarException("WCEC0","GENERAL SQL ERROR",sqle.toString());
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


        logger.info("DELETE AVAILABILITY CALENDAR ENDS");

        return hm_Result;
    }
    public AvailabilityDetails getAvailabilityDetails(int avbltyId) throws  WorkCalendarException, SQLException
    {
        ResultSet rs = null;
        ResultSet rs_Cus = null;
        ResultSet rs_NonCus = null;
        DBConnection con = null;
        int sh = 0;
        Vector vec_cusAvbltyDet = new Vector();

        Vector vec_cusNonAvbltyDet  = new Vector();
        Hashtable ht_AvbltyDet_Get = new Hashtable();
        AvailabilityDetails objAvailabilityDetails = new AvailabilityDetails();


        logger.info("GET AVAILABILTY DETAILS STARTS");

        con = DBConnectionFactory.getConnection();
        ht_AvbltyDet_Get.put("AVBLTY_ID",new Integer(avbltyId));
        //get Availability Details
        try
        {
            PreparedStatement ps = con.executeStatement(SQLMaster.GET_AVAILABILITY_DETAILS,ht_AvbltyDet_Get);
            rs = ps.executeQuery();
            if(rs.next())
            {
            	objAvailabilityDetails.setAvailabilityId(rs.getInt("AVBLTY_ID"));
                objAvailabilityDetails.setAvailabilityName(rs.getString("AVBLTY_NAME"));
                objAvailabilityDetails.setBaseCalId(rs.getInt("BASE_CAL_ID"));
                objAvailabilityDetails.setBaseCalName(rs.getString("BASE_CAL_NAME"));
                objAvailabilityDetails.setFromDate(rs.getTimestamp("AVBLTY_FROMDATE"));
                objAvailabilityDetails.setToDate(rs.getTimestamp("AVBLTY_TODATE"));
                objAvailabilityDetails.setAvailabltyDateStamp(rs.getTimestamp("AVBLTY_DATESTAMP"));
                objAvailabilityDetails.setAvailabiltyIsValid(rs.getInt("AVBLTY_ISVALID"));
                //objAvailabilityDetails.setHasCustom(rs.getString());

                //get Custom Availability Details
                PreparedStatement ps_Cus = con.executeStatement(SQLMaster.GET_CUSTOM_AVAILABILITY_DATE,ht_AvbltyDet_Get);
                PreparedStatement ps_ShiftDet = null;
                rs_Cus = ps_Cus.executeQuery();

                while(rs_Cus.next())
                {


                    CustomAvbltyDetails objCustomDetails = new CustomAvbltyDetails();
                    Vector vec_cusAvbltyShiftDet  = new Vector();
                    objCustomDetails.setForDate(rs_Cus.getTimestamp("CUSTOM_AVBLTY_FORDATE"));
                    ht_AvbltyDet_Get.put("CUSTOM_AVBLTY_FORDATE",objCustomDetails.getForDate());
                    ps_ShiftDet = con.executeStatement(SQLMaster.GET_CUSTOM_AVAILABILITY_SHIFT_DETAILS,ht_AvbltyDet_Get);
                    ResultSet rs_ShiftDet = ps_ShiftDet.executeQuery();
                    while(rs_ShiftDet.next())
                    {
                        ShiftDetails objShiftDetails = new ShiftDetails();
                        objShiftDetails.setShiftId(rs_ShiftDet.getInt("SHIFT_ID"));
                        objShiftDetails.setShiftName(rs_ShiftDet.getString("SHIFT_NAME"));
	                    objShiftDetails.setStartTime(rs_ShiftDet.getString("CUSTOM_AVBLTY_STARTTIME"));
	                    objShiftDetails.setStartTimeDay(rs_ShiftDet.getString("CUSTOM_AVBLTY_STARTDAY"));
	                    objShiftDetails.setEndTime(rs_ShiftDet.getString("CUSTOM_AVBLTY_ENDTIME"));
	                    objShiftDetails.setEndTimeDay(rs_ShiftDet.getString("CUSTOM_AVBLTY_ENDDAY"));
	                    objShiftDetails.setPredsrShiftId((sh = rs_ShiftDet.getInt("CUSTOM_AVBLTY_PREDSRSHIFT"))!=0 ? sh : 0);
	                    vec_cusAvbltyShiftDet.addElement(objShiftDetails);
                    }
                    objCustomDetails.setVec_ShiftDet(vec_cusAvbltyShiftDet);
                    vec_cusAvbltyDet.add(objCustomDetails);
                    rs_ShiftDet.close();
                    ps_ShiftDet.close();


                }

                ps_Cus.close();
                rs_Cus.close();

                //get Non-Custom Availability Details
                PreparedStatement ps_NonCus = con.executeStatement(SQLMaster.GET_NONCUSTOM_AVAILABILITY_DETAILS,ht_AvbltyDet_Get);
                rs_NonCus = ps_NonCus.executeQuery();
                while(rs_NonCus.next())
                {
                 	CustomNonAvbltyDetails objCustomDetails1 = new CustomNonAvbltyDetails();
                    objCustomDetails1.setForDate(rs_NonCus.getTimestamp("CUSTOM_N_AVBLTY_FORDATE"));
                    objCustomDetails1.setNonAvbltyReason(rs_NonCus.getString("N_AVBLTY_RSN"));
                    vec_cusNonAvbltyDet.addElement(objCustomDetails1);
                }
                rs_NonCus.close();
                ps_NonCus.close();

                objAvailabilityDetails.setCustomAvbltyDetails(vec_cusAvbltyDet);
                objAvailabilityDetails.setNonAvbltyDetails(vec_cusNonAvbltyDet);
            }
            else
            {

                logger.info("AVAILABILITY RECORD NOT FOUND");
                throw new WorkCalendarException("WCMEC0","AVAILABILITY RECORD NOT FOUND","");
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

            logger.error("EXCEPTION WHILE SELECTING AVAILABILITY DETAILS");

            throw new WorkCalendarException("WCMEC000","GENERAL SQL ERROR",sqle.toString());
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


        logger.info("GET AVAILABILITY DETAILS ENDS");

        return objAvailabilityDetails;
    }

    public HashMap getAvailabilityNameList() throws  WorkCalendarException, SQLException
    {
        DBConnection con = null;
        HashMap hm_Result = new HashMap();
        ResultSet  rs = null;


        logger.info("GET AVAILLABILTY NAME LIST STARTS");

        try
        {
            con = DBConnectionFactory.getConnection();
            Statement st = con.getConnection().createStatement();
            String query = SQLMaster.GET_AVAILABILITY_NAME_LIST;
            try
            {
                rs = st.executeQuery(query);
                while(rs.next())
                {
                    hm_Result.put(new Integer(rs.getInt("AVBLTY_ID")),rs.getString("AVBLTY_NAME"));
                }
                rs.close();
            }
            catch(SQLException e)
            {
                if(BuildConfig.DMODE)
                {
                    e.printStackTrace();
                    System.out.println("EXCEPTION WHILE SELECTING AVAILABLITY NAME LIST");
                }

                logger.error("GENERAL SQL ERROR");
                throw new WorkCalendarException("WMEC000","GENERAL SQL ERROR",e.toString());
            }

        }
        catch(SQLException e)
        {
            if(BuildConfig.DMODE)
            {
                e.printStackTrace();
                System.out.println("GENERAL EXCEPTION");
            }

            logger.error("GENERAL SQL ERROR");

            throw new WorkCalendarException("WMEC000","GENERAL SQL ERROR",e.toString());
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


        logger.info("GET AVAILABILITY NAME LIST ENDS");


        return hm_Result;
    }
    public HashMap getAllAvailabilityDetails(Filter[] filters,String sortBy,boolean ascending,int startIndex,int displayCount) throws WorkCalendarException,SQLException
    {
        HashMap hm_Result = new HashMap();
        DBConnection con = null;
        Vector vec_AvbltyDet_Get = new Vector();


        logger.info("AVAILABILITY SEARCH FILTER STARTS");

        try
        {
            con = DBConnectionFactory.getConnection();

            ResultSet rs_AvbltyDet_Get = null;
            int eIndex = startIndex + displayCount;
            int tot_Rec_Cnt = 0;



            tot_Rec_Cnt = con.getRowCountWithComplexFilters(filters,SQLMaster.AVAILABILITY_DETAILS_FILTER_QUERY);



            String Query = con.buildDynamicQuery(filters,startIndex,eIndex,sortBy,ascending,SQLMaster.AVAILABILITY_DETAILS_FILTER_QUERY);
            try
            {
                rs_AvbltyDet_Get = con.executeRownumStatement(Query);
                while(rs_AvbltyDet_Get.next())
                {
                    AvailabilityDetails objAvailabilityDetails = new AvailabilityDetails();
                    objAvailabilityDetails.setAvailabilityId(rs_AvbltyDet_Get.getInt("AVBLTY_ID"));
                    objAvailabilityDetails.setAvailabilityName(rs_AvbltyDet_Get.getString("AVBLTY_NAME"));
                    objAvailabilityDetails.setFromDate(rs_AvbltyDet_Get.getTimestamp("AVBLTY_FROMDATE"));
                    objAvailabilityDetails.setToDate(rs_AvbltyDet_Get.getTimestamp("AVBLTY_TODATE"));
                    objAvailabilityDetails.setBaseCalId(rs_AvbltyDet_Get.getInt("BASE_CAL_ID"));
                    objAvailabilityDetails.setAvailabltyCurrent(rs_AvbltyDet_Get.getString("AVBLTY_CURRENT").equals("1"));
                    objAvailabilityDetails.setAvailabltyDateStamp(rs_AvbltyDet_Get.getTimestamp("AVBLTY_DATESTAMP"));
                    objAvailabilityDetails.setAvailabiltyIsValid(rs_AvbltyDet_Get.getInt("AVBLTY_ISVALID"));

                    vec_AvbltyDet_Get.addElement(objAvailabilityDetails);

                }
                hm_Result.put("TotalRecordCount",new Integer(tot_Rec_Cnt));
                hm_Result.put("AvailablityDetails",vec_AvbltyDet_Get);
                rs_AvbltyDet_Get.getStatement().close();
                rs_AvbltyDet_Get.close();
            }
            catch(SQLException e)
            {
                if(BuildConfig.DMODE)
                {
                    e.printStackTrace();
                    System.out.println("EXCEPTION WHILE SELECTING AVAILABILITY DETAILS");
                }

                logger.error("GENERAL SQL ERROR");

                throw new WorkCalendarException("WMEC000","GENERAL SQL ERROR",e.toString());
            }
        }
        catch(SQLException e)
        {
            if(BuildConfig.DMODE)
            {
                e.printStackTrace();
                System.out.println("GENERAL EXCEPTION");
            }

            logger.error("GENERAL SQL ERROR");

            throw new WorkCalendarException("WMEC000","GENERAL SQL ERROR",e.toString());
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


        logger.info("AVAILABILITY SEARCH FILTER ENDS");

        return hm_Result;
    }
    public boolean chooseAvbltyName(int avbltyId) throws WorkCalendarException, SQLException
    {
        DBConnection con = null;
        Hashtable ht_AvlbltyId = new Hashtable();
        int result = 0;
        if(BuildConfig.DMODE)
        {
            System.out.println("CHOOSE AVAILABITY NAME STARTS");
        }
        logger.info("CHOOSE AVAILABITY NAME STARTS");

        boolean chooseRESULT = false;
        try
        {
            con = DBConnectionFactory.getConnection();
            con.startTransaction();
            ht_AvlbltyId.put("AVBLTY_ID",new Integer(avbltyId));
            result = con.executeUpdateStatement(SQLMaster.UPDATE_AVBLTY_NAME,ht_AvlbltyId);
            if(result>0)
            {
                if(BuildConfig.DMODE)
                {
                    System.out.println("ALL AVBLTY ENTRIES SET TO 0(zero)");
                }
            }
            result = con.executeUpdateStatement(SQLMaster.CHOOSE_AVBLTY_NAME,ht_AvlbltyId);
            if(result>0)
            {
                chooseRESULT = true;
                con.commitTransaction();
            }
            else
            {
                con.rollBackTransaction();
            }
        }
        catch(SQLException ex)
        {
            con.rollBackTransaction();
            if(BuildConfig.DMODE)
            {
                ex.printStackTrace();
            }

            logger.error("GENERAL SQL ERROR");

            throw new WorkCalendarException("WCMEC000","GENERAL SQL ERROR",ex.toString());

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

        if(BuildConfig.DMODE)
        {
            System.out.println("CHOOSE AVAILABITY NAME ENDS");
        }

        logger.info("CHOOSE AVAILABITY NAME ENDS");

        return chooseRESULT;

    }
    public HashMap makeAvailablityCalendarValid(Vector avlbltyIds) throws  WorkCalendarException, SQLException
    {
        DBConnection con = null;
        HashMap hm_Result = new HashMap();
        Hashtable ht_AvlbltyId = new Hashtable();


        logger.info("AVAILABITY CALENDAR MAKE VALID STARTS");

        try
        {
            con = DBConnectionFactory.getConnection();
            int result = 0;

            for(int i=0;i<avlbltyIds.size();i++)
            {

                ht_AvlbltyId.put("AVBLTY_ID",(Integer)avlbltyIds.get(i));

                result = con.executeUpdateStatement(SQLMaster.AVBLTY_MAKEVALID_QUERY,ht_AvlbltyId);
                if(result > 0)
                {
                    hm_Result.put(avlbltyIds.get(i),new Integer(0));

                    logger.debug("RECORD VALIDATED");

                }
                else
                {
                    hm_Result.put(avlbltyIds.get(i),new Integer(1));

                    logger.debug("RECORD NOT VALIDATED");

                }
            }
        }
        catch(SQLException ex)
        {
            if(BuildConfig.DMODE)
            {
                ex.printStackTrace();
            }

            logger.error("GENERAL SQL ERROR");

            throw new WorkCalendarException("WCMEC000","GENERAL SQL ERROR",ex.toString());

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


        logger.info("AVAILABITY CALENDAR MAKE VALID ENDS");

        return hm_Result;
    }
    public HashMap makeAvailablityCalendarInValid(Vector avlbltyIds) throws  WorkCalendarException, SQLException
    {
        DBConnection con = null;
        HashMap hm_Result = new HashMap();
        Hashtable ht_AvlbltyId = new Hashtable();


        logger.info("AVAILABITY CALENDAR MAKE INVALID STARTS");

        try
        {
            con = DBConnectionFactory.getConnection();
            int result = 0;

            for(int i=0;i<avlbltyIds.size();i++)
            {
                ht_AvlbltyId.put("AVBLTY_ID",(Integer)avlbltyIds.get(i));

                result = con.executeUpdateStatement(SQLMaster.AVBLTY_MAKEINVALID_QUERY,ht_AvlbltyId);
                if(result > 0)
                {
                    hm_Result.put(avlbltyIds.get(i),new Integer(0));


                    logger.debug("RECORD INVALIDATED");

                }
                else
                {
                    hm_Result.put(avlbltyIds.get(i),new Integer(1));


                    logger.debug("RECORD NOT INVALIDATED");

                }
            }
        }
        catch(SQLException ex)
        {

            if(BuildConfig.DMODE)
            {
                ex.printStackTrace();
            }

            logger.error("GENERAL SQL ERROR");
            throw new WorkCalendarException("WCMEC000","GENERAL SQL ERROR",ex.toString());

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


        logger.info("AVAILABITY CALENDAR MAKE VALID ENDS");

        return hm_Result;
    }

    public static void main(String args[]) throws  WorkCalendarException, SQLException
    {
        //ShiftDefnDetails ob = new ShiftDefnDetails();
        WorkCalendarDetailsManager obj1 = new WorkCalendarDetailsManager();
        AvailabilityDetails obj = new AvailabilityDetails();
        obj.setAvailabilityId(36);
        obj.setAvailabilityName("current");
        obj.setFromDate(new Date("01-jan-05"));
        obj.setToDate(new Date("01-jan-06"));
        obj.setBaseCalId(163);
        System.out.println(obj1.updateAvailabilityCalendar(obj));

        //BaseCalendarDetails obj=new BaseCalendarDetails();
        /*obj.setBcName("BCNAME1");
         obj.setBcStartDay(2);
         obj.setBcEndDay(7);
         ShiftDetails s = new ShiftDetails();
         ShiftDetails s1 = new ShiftDetails();
         s.setShiftId(1);
         s.setDay(2);
         s.setStartTime("1.30");
         s.setEndTime("4.50");
         s1.setShiftId(2);
         s1.setDay(3);
         s.setStartTime("11.30");
         s.setEndTime("8.50");
         Vector vec = new Vector();
         vec.addElement(s);
         vec.addElement(s1);
         obj.setVec_ShiftDetails(vec);

         boolean result = obj1.addBaseCalendar(obj);*/
        /*obj.setBcName("baseCalendar1");
         obj.setBcStartDay(1);
         obj.setBcEndDay(3);
         ShiftDetails s = new ShiftDetails();
         s.setDay(1);
         s.setShiftId(1);
         s.setStartTime("9.30");
         s.setEndTime("12.30");

         Vector v = new Vector();
         v.addElement(s);
         ShiftDetails s1 = new ShiftDetails();
         s1.setDay(1);
         s1.setShiftId(2);
         s1.setStartTime("12.30");
         s1.setEndTime("04.30");
         v.addElement(s1);
         ShiftDetails s2 = new ShiftDetails();
         s2.setDay(2);
         s2.setShiftId(1);
         s2.setStartTime("9.30");
         s2.setEndTime("12.30");
         v.addElement(s2);
         obj.setVec_ShiftDetails(v);*/

        //ob.setShiftName("THIRD");
        //ob.setShiftDesc("THIRDDESC");
        //System.out.println("The result is "+obj.addShiftDefinition(ob));
        //ShiftDefnDetails ob1 = new ShiftDefnDetails();
        //ob1.setShiftId(3);
        //ob1.setShiftDesc("seconddesc");

        //boolean result = obj1.addBaseCalendar(obj);

        //ob1 = obj.getShiftDefinition(3);
        //System.out.println("shift_Id:"+ob1.getShiftId());
        //System.out.println("shift_Name:"+ob1.getShiftName());
        //System.out.println("shift_Desc:"+ob1.getShiftDesc());
        //System.out.println("shift_Date:"+ob1.getShiftDateStamp());
        //System.out.println("shift_IsValid:"+ob1.getShiftIsValid());

        /*obj = obj1.getBaseCalendarDetails(22);
         System.out.println(obj.getBcName());
         System.out.println(obj.getBcId());
         System.out.println(obj.getBcIsValid());
         System.out.println(obj.getBcStartDay());
         Vector vec = obj.getVec_ShiftDetails();
         for(int i=0;i<vec.size();i++)
         {
         ShiftDetails objShiftDetails = new ShiftDetails();
         objShiftDetails = (ShiftDetails)vec.get(i);
         System.out.println(objShiftDetails.getShiftName());
         System.out.println(objShiftDetails.getEndTime());
         System.out.println(objShiftDetails.getPredsrShiftId());

         }*/
        /*HashMap hm = obj1.getBaseCalendarNameList();
         System.out.println("HASHMAP RESULT :"+hm);*/
        /*HashMap hm = obj1.getAvailabilityNameList();
         System.out.println("HASHMAP RESULT :"+hm);*/
        //updateBaseCalendarDetails()//
        /*BaseCalendarDetails objBCDetails = new BaseCalendarDetails();
         objBCDetails.setBcId(19);
         objBCDetails.setBcStartDay(1);
         objBCDetails.setBcEndDay(6);
         ShiftDetails objShiftDetails = new ShiftDetails();
         objShiftDetails.setShiftId(2);
         objShiftDetails.setStartTime("1.30");
         objShiftDetails.setEndTime("07.30");
         objShiftDetails.setDay(2);
         objShiftDetails.setPredsrShiftId(1);

         ShiftDetails objShiftDetails1 = new ShiftDetails();
         objShiftDetails1.setShiftId(1);
         objShiftDetails1.setStartTime("2.30");
         objShiftDetails1.setEndTime("05.45");
         objShiftDetails1.setDay(4);
         //objShiftDetails1.setPredsrShiftId();

          Vector vec = new Vector();
          vec.addElement(objShiftDetails);
          vec.addElement(objShiftDetails1);
          objBCDetails.setVec_ShiftDetails(vec);

          boolean result = obj1.updateBaseCalendar(objBCDetails);
          System.out.println("RESULT :"+result);*/

        /*Vector vec = new Vector();
         vec.addElement(new Integer(19));
         //vec.addElement();
          HashMap hm = obj1.deleteBaseCalendar(vec);
          System.out.println("HashMap Result :"+hm);*/

        ///shift filter method///
        /*Filter filter[] = new Filter[1];
         filter[0] = new Filter();
         filter[0].setFieldName("SHIFT_ISVALID");
         filter[0].setFieldValue("1");
         filter[0].setSpecialFunction("");
         String sortBy = "SHIFT_NAME";;
         boolean ascending = true;
         int startIndex = 1;
         int displayCount = 15;
         HashMap hm_Result = obj1.getAllShiftDefnDetails(filter,sortBy,new Boolean(ascending).booleanValue(),startIndex,displayCount);
         System.out.println("HASHMAP RESULT :"+hm_Result);*/

        ///basecalendar filter method///
        /*Filter filter[] = new Filter[1];
         filter[0] = new Filter();
         filter[0].setFieldName("BASE_CAL_ISVALID");
         filter[0].setFieldValue("1");
         filter[0].setSpecialFunction("");
         String sortBy = "BASE_CAL_NAME";;
         boolean ascending = true;
         int startIndex = 1;
         int displayCount = 15;
         HashMap hm_Result = obj1.getAllBaseCalendarDetails(filter,sortBy,new Boolean(ascending).booleanValue(),startIndex,displayCount);
         System.out.println("HASHMAP RESULT :"+hm_Result);*/

        //filter method for availability ///
        /*Filter filter[] = new Filter[1];
         filter[0] = new Filter();
         filter[0].setFieldName("AVBLTY_ISVALID");
         filter[0].setFieldValue("1");
         filter[0].setSpecialFunction("");
         String sortBy = "AVBLTY_NAME";;
         boolean ascending = true;
         int startIndex = 1;
         int displayCount = 15;
         HashMap hm_Result = obj1.getAllAvailabilityDetails(filter,sortBy,new Boolean(ascending).booleanValue(),startIndex,displayCount);
         System.out.println("HASHMAP RESULT :"+hm_Result);*/
        //getshiftnamelist()//
        /*HashMap hm = obj1.getShiftNameList();
         System.out.println("HashMap :"+hm);*/
        /*AvailabilityDetails  objAvbDet = obj1.getAvailabilityDetails(1);
         System.out.println("avbname :"+objAvbDet.getAvailabilityName());
         System.out.println("avbfromdate :"+objAvbDet.getFromDate());
         Vector vec = objAvbDet.getCustomDetails();
         for(int i=0;i<vec.size();i++)
         {
         CustomDetails objCustom = (CustomDetails)vec.get(i);
         System.out.println("SHIFT NAME :"+objCustom.getShiftName());
         System.out.println("custom non available for date :"+objCustom.getForDate());
         System.out.println("custom non available reason :"+objCustom.getNonAvailReason());
         }*/
        Vector vec = new Vector();
        vec.addElement(new Integer(21));
        obj1.deleteShiftDefinition(vec);
    }
}
/***
 $Log: WorkCalendarDetailsManager.java,v $
 Revision 1.68  2005/09/15 07:36:09  kduraisamy
 rs.getStatement().close() added in Filters.

 Revision 1.67  2005/07/14 11:27:57  kduraisamy
 indentation.

 Revision 1.66  2005/06/29 12:43:58  smurugesan
 if con ! = null added in finally

 Revision 1.65  2005/05/27 09:45:21  kduraisamy
 signature changed.HashMap is changed to LinkedHashmap.

 Revision 1.64  2005/05/27 08:49:51  kduraisamy
 buildconfig.DMODE ADDED.

 Revision 1.63  2005/05/18 08:35:18  kduraisamy
 commitTransaction() and rollbackTransaction() added.

 Revision 1.62  2005/05/17 06:48:32  kduraisamy
 commit added.

 Revision 1.61  2005/05/16 18:36:07  kduraisamy
 specific throws addded for mysql.

 Revision 1.60  2005/05/05 12:17:04  kduraisamy
 old avblty id is set AVBLTY_CURRENT = '0'.

 Revision 1.59  2005/05/05 04:58:22  vkrishnamoorthy
 signature linkedHashMap added .

 Revision 1.58  2005/05/05 04:42:15  vkrishnamoorthy
 error corrected.

 Revision 1.57  2005/05/02 05:09:04  kduraisamy
 shift id included in getAvailbilityDetails().

 Revision 1.56  2005/04/30 11:41:05  vkrishnamoorthy
 vector Creation position corrected.

 Revision 1.55  2005/04/30 08:39:49  vkrishnamoorthy
 error  corrected.

 Revision 1.54  2005/04/30 07:47:25  vkrishnamoorthy
 null field validation added before set into Hashtable.

 Revision 1.53  2005/04/30 06:21:27  kduraisamy
 unnecessary customavblty and non avblty sql queries removed.

 Revision 1.52  2005/04/29 10:58:40  kduraisamy
 custom avbltyDetails object structure changed.

 Revision 1.51  2005/04/28 14:21:56  kduraisamy
 predsr shift for custom avblty.

 Revision 1.50  2005/04/28 10:01:23  kduraisamy
 CUSTOM AVBLTY STARTDAY ,ENDDAY ADDED. IN GET METHOD.

 Revision 1.49  2005/04/28 09:35:35  kduraisamy
 CUSTOM AVBLTY STARTDAY ,ENDDAY ADDED. IN GET METHOD.

 Revision 1.48  2005/04/28 09:31:17  kduraisamy
 CUSTOM AVBLTY STARTDAY ,ENDDAY ADDED. IN GET METHOD.

 Revision 1.47  2005/04/28 09:14:24  kduraisamy
 CUSTOM AVBLTY STARTDAY ,ENDDAY ADDED.

 Revision 1.46  2005/04/27 15:47:55  kduraisamy
 SHIFT NAME LIST LOADED ACCORDING TO CUSTOM AVAILABILITY.

 Revision 1.45  2005/04/27 06:05:57  kduraisamy
 updateAvailabilityCalendar() added.

 Revision 1.44  2005/04/26 11:52:17  kduraisamy
 CUSTOM_N_AVBLITY_FORDATE CHANGED TO CUSTOM_N_AVBLTY_FORDATE

 Revision 1.43  2005/04/26 10:08:21  kduraisamy
 availability current added.

 Revision 1.42  2005/04/22 06:17:10  smurugesan
 GetAvbltyCalendarDetails() error corrected.

 Revision 1.41  2005/04/20 10:10:10  kduraisamy
 avbltyDetails() add method changed.

 Revision 1.40  2005/04/19 13:13:33  kduraisamy
 resultset properly closed.

 Revision 1.39  2005/04/18 12:28:44  kduraisamy
 executeStatement() return type changed.

 Revision 1.38  2005/04/07 11:58:30  kduraisamy
 BuildConfig.DMODE ADDED befor SYSTEM.OUT.PRINTLN().

 Revision 1.37  2005/04/07 09:20:56  kduraisamy
 MODE IS CHANGED TO DMODE.

 Revision 1.36  2005/04/07 07:36:56  kduraisamy
 if(BuildConfig.DMODE) ADDED. AND LEVEL INCLUDED PROPERLY.

 Revision 1.35  2005/03/31 05:35:14  kduraisamy
 logger.error is changed to logger.info.

 Revision 1.34  2005/03/30 08:28:14  kduraisamy
 excecuteRownumStatement() called for filters.

 Revision 1.33  2005/03/26 10:09:26  smurugesan
 ROWNUM RESET QUERY REMOVED.

 Revision 1.32  2005/03/24 06:25:18  smurugesan
 unwanted startTransaction() removed.

 Revision 1.31  2005/03/19 14:34:03  vkrishnamoorthy
 hashtable entry in updateBaseCalendarDetails() added.

 Revision 1.30  2005/03/19 06:04:25  kduraisamy
 field base_cal_var_starttime_day,endtime_day added.

 Revision 1.29  2005/03/11 06:20:56  kduraisamy
 RESET ROWNUM QUERY ADDED BEFORE FILTER METHODS..

 Revision 1.28  2005/03/10 07:28:44  kduraisamy
 getDate is changed to getTimestamp().

 Revision 1.27  2005/03/04 12:44:59  kduraisamy
 Indentation.

 Revision 1.26  2005/03/04 10:19:04  vkrishnamoorthy
 DBConnection changes made.

 Revision 1.25  2005/02/14 11:10:05  kduraisamy
 unwanted cast removed.

 Revision 1.24  2005/02/11 06:55:57  kduraisamy
 unused variables removed.

 Revision 1.23  2005/01/27 14:48:12  kduraisamy
 if(BuildConfig.DMODE)
 {
 }
 properly inclued inside the catch block.

 Revision 1.22  2004/12/25 11:11:45  sduraisamy
 Exception messages modified

 Revision 1.21  2004/12/07 05:39:22  sduraisamy
 PrdcsrShiftName set in getBaseCalendarDetails()

 Revision 1.20  2004/12/07 05:09:27  kduraisamy
 setter name changed from start,end date to from,to date respectively.

 Revision 1.19  2004/12/06 17:01:16  kduraisamy
 allowing null into hashtable restricted.

 Revision 1.18  2004/12/06 15:26:00  sduraisamy
 Exception included for addCustomAvaialbility and Noncustom Availability

 Revision 1.17  2004/12/06 14:52:44  kduraisamy
 new Float(starttime) is changed to just starttime.

 Revision 1.16  2004/12/04 06:39:45  sduraisamy
 else part included for getAvailabilityDetails()

 Revision 1.15  2004/12/03 13:30:38  kduraisamy
 WCMEC003 Exception Message Modified in addShiftDefinition

 Revision 1.14  2004/12/03 11:52:11  sduraisamy
 Exception modified

 Revision 1.13  2004/12/03 07:50:51  sduraisamy
 Exception Codes Changed

 Revision 1.12  2004/12/03 05:32:25  sduraisamy
 Exception Codes Changed

 Revision 1.11  2004/12/02 07:14:54  kduraisamy
 addAvailability() added

 Revision 1.10  2004/12/02 05:47:52  sduraisamy
 getAvailabilityDetails(int) modified to set basecalname and shiftname

 Revision 1.9  2004/12/01 14:31:32  sduraisamy
 getAllShiftDefnDetails(),getAllBaseCalendarDetails(),getAllAvailablityDetails() and getAvailablityDetails(int) added

 Revision 1.8  2004/11/30 10:12:07  sduraisamy
 shiftIds instead of vec_ShiftIds in makeShiftDefnValid and InValid

 Revision 1.7  2004/11/30 10:09:08  sduraisamy
 deleteBaseCalendar(),makeBaseCalendarValid() and makeBaseCalendarInValid() added

 Revision 1.6  2004/11/29 15:09:02  sduraisamy
 updateBaseCalendar() added

 Revision 1.5  2004/11/29 11:59:30  sduraisamy
 getAvailabilityNameList() added

 Revision 1.4  2004/11/29 11:50:27  sduraisamy
 getBaseCalendarDetails() and getBaseCalendarNameList() added

 Revision 1.3  2004/11/29 09:52:50  kduraisamy
 addBaseCalendar() added

 Revision 1.1  2004/11/24 15:44:49  kduraisamy
 initial commit

 ***/


