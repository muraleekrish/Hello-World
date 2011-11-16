/*
 * Created on Jan 7, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.datrics.scheduler.initializer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.datrics.scheduler.valueobjects.JobDetails;
import com.savantit.prodacs.infra.dbtools.DBConnection;
import com.savantit.prodacs.infra.dbtools.DBConnectionFactory;
import com.savantit.prodacs.infra.dbtools.SQLMaster;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.infra.util.LoggerOutput;
import com.savantit.prodacs.infra.util.ProdacsConfig;

/**
 * @author vkrishnamoorthy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MailScheduler
{
    DBConnection con=null;
    static Logger logger = Logger.getLogger(MailScheduler.class);
    
    public MailScheduler()
    {
        logger.addAppender(LoggerOutput.getAppender());
        logger.setLevel(ProdacsConfig.LOGGER_LEVEL);
    }
    
    public JobDetails[] getAllScheduleDetails() throws SQLException,MailSchedulerException
	{
    	logger.info("Mail Scheduler Starts...");

    	if(BuildConfig.DMODE)
        {
            System.out.println("GET ALL MAIL SCHEDULER DETAILS STARTS");
        }
        JobDetails[] objJobDetailsList = null;
        try
		{
            con = DBConnectionFactory.getConnection();
            ResultSet rs_Mail_Scheduler = null;
            PreparedStatement ps = null;
            try
            {
                ps = con.executeStatement(SQLMaster.GET_ALL_MAIL_SCHEDULE_SQL_QUERY);
                rs_Mail_Scheduler  = ps.executeQuery();
                Vector vec_JobDetails = new Vector();
                while(rs_Mail_Scheduler.next())
                {
                	JobDetails objJobDetails = new JobDetails();
                	objJobDetails.setJbName(rs_Mail_Scheduler.getString("JB_NAME"));
                	objJobDetails.setJbType(rs_Mail_Scheduler.getString("SCHDLR_TYPE"));
                	objJobDetails.setJbStartDate(rs_Mail_Scheduler.getTimestamp("JB_START_DATE"));
                	objJobDetails.setJbEndDate(rs_Mail_Scheduler.getTimestamp("JB_END_DATE"));
                	objJobDetails.setNextRun(rs_Mail_Scheduler.getTimestamp("JB_NEXT_RUN"));
                	objJobDetails.setLastRun(rs_Mail_Scheduler.getTimestamp("JB_LAST_RUN"));
                	objJobDetails.setLastRunStat(rs_Mail_Scheduler.getString("LAST_RUN_STAT").equalsIgnoreCase("success")?true:false);
                	vec_JobDetails.addElement(objJobDetails);
                }
                objJobDetailsList = new JobDetails[vec_JobDetails.size()];
                vec_JobDetails.copyInto(objJobDetailsList);
            }
            catch(SQLException sqle)
            {
                logger.error("GENERAL ERROR", sqle);
                if(BuildConfig.DMODE)
                {
                    sqle.printStackTrace();
                }
                throw new MailSchedulerException("CMEC000","GENERAL SQL ERROR",sqle.toString());
            }
            rs_Mail_Scheduler.close();
            ps.close();
        }
        catch(SQLException sqle)
        {
            logger.error("SQL ERROR", sqle);
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }
            throw new MailSchedulerException("CMEC000","GENERAL SQL ERROR",sqle.toString());
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
        logger.info("MAIL SCHEDULER GET ALL ENDS");
        
        return objJobDetailsList;
	}
    
    public boolean updateJbDetails(JobDetails objJobDetails) 
    { 
        // to store the next schedule time..
        DBConnection con = DBConnectionFactory.getConnection();
        Hashtable ht = new Hashtable();
        boolean updateResult = false;
        ht.put("JB_LAST_RUN",objJobDetails.getLastRun());
        ht.put("LAST_RUN_STAT",(objJobDetails.isLastRunStat())?"SUCCESS":"FAILURE");
        ht.put("JB_NEXT_RUN",objJobDetails.getNextRun());
        try
        {
            int i = con.executeUpdateStatement(SQLMaster.UPDATE_SCHDLR_JB_DETAILS,ht);
            if(i>0)
            {
                updateResult = true;
            }
        }
        catch (SQLException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
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
                
                try
                {
                    throw new SQLException("CONNECTION CLOSING EXCEPTION" + clse.toString());
                }
                catch (SQLException e2)
                {
                    // TODO Auto-generated catch block
                    e2.printStackTrace();
                }
            }
        }
        return updateResult;
    }
}


/***
$Log: MailScheduler.java,v $
Revision 1.5  2006/01/10 10:31:51  vkrishnamoorthy
Last Run Status modified.

Revision 1.3  2006/01/10 07:23:46  vkrishnamoorthy
getAllScheduleDetails() method modified.

Revision 1.1  2006/01/07 05:55:09  vkrishnamoorthy
Initial commit on ProDACS.

***/