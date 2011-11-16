/*
 * Created on Dec 10, 2005
*/
package com.datrics.scheduler.initializer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import com.datrics.scheduler.valueobjects.DailyDetails;
import com.savantit.prodacs.infra.dbtools.DBConnection;
import com.savantit.prodacs.infra.dbtools.DBConnectionFactory;
import com.savantit.prodacs.infra.dbtools.SQLMaster;
import com.savantit.prodacs.infra.util.BuildConfig;


/**
 * @author sduraisamy
 */
public class FetchTasks 
{
    public Object[] fetch() 
    {
        Vector objVec = new Vector();
        DBConnection db = null;
        try
        {
	        Hashtable ht = new Hashtable();
	        db = DBConnectionFactory.getConnection();
	        if(BuildConfig.DMODE)
	        {
	            System.out.println("Fetch job Starts");
	        }
	        PreparedStatement ps =  db.executeStatement(SQLMaster.GET_ALL_DETAIL_FROM_JOB_MASTER_AND_SCHEDULER,ht);
	        ResultSet rs = ps.executeQuery();
	        while(rs.next())
	        {
	            String jbType = rs.getString("SCHDLR_TYPE");
	            String jbClass = rs.getString("JB_CLASS");
	            Date startDate = rs.getDate("JB_START_DATE");
	            
	            Date endDate = rs.getDate("JB_END_DATE");
	            ht.put("JB_ID",rs.getString("JB_ID"));
	            if(jbType.equalsIgnoreCase("D"))
	            {
	                PreparedStatement innerPs  = db.executeStatement(SQLMaster.GET_DETAIL_FROM_DAILY,ht);
	                ResultSet innerRs = innerPs.executeQuery();
	                if(innerRs.next())
	                {
	                    DailyDetails objDailyDetails = new DailyDetails();
	                    objDailyDetails.setJobClass(jbClass);
	                    objDailyDetails.setNoOfDaysInterval(innerRs.getInt("EVERY_PER_DAY"));
	                    objDailyDetails.setOnceHr(innerRs.getInt("OCCUR_ONCE_AT_HR"));
	                    objDailyDetails.setOnceMin(innerRs.getInt("OCCUR_ONCE_AT_MIN"));
	                    objDailyDetails.setOnceSecs(innerRs.getInt("OCCUR_ONCE_AT_SEC"));
	                    objDailyDetails.setRepeatedHr(innerRs.getInt("OCCUR_REPEAT_HR"));
	
	                    objDailyDetails.setRepeatedMin(innerRs.getInt("OCCUR_REPEAT_MIN"));
	                    
	                    objDailyDetails.setRepeatedSecs(innerRs.getInt("OCCUR_REPEAT_SEC"));
	                    
	                    objDailyDetails.setStartTimeHr(innerRs.getInt("START_AT_HR"));
	                    objDailyDetails.setStartTimeMin(innerRs.getInt("START_AT_MIN"));
	                    objDailyDetails.setStartTimeSec(innerRs.getInt("START_AT_SEC"));
	                    
	                    objDailyDetails.setEndTimeHr(innerRs.getInt("END_AT_HR"));
	                    objDailyDetails.setEndTimeMin(innerRs.getInt("END_AT_MIN"));
	                    objDailyDetails.setEndTimeSec(innerRs.getInt("END_AT_SEC"));
	                    objDailyDetails.setSchedulerId(innerRs.getString("SCHDLR_ID"));
	                    objDailyDetails.setJobStartDate(startDate);
	                    objDailyDetails.setJobEndDate(endDate);
	                    objVec.add(objDailyDetails);
	                }
	                innerRs.close();
	                innerPs.close();
	                
	            }
	            else if(jbType.equalsIgnoreCase("W"))
	            {

	                PreparedStatement innerPs  = db.executeStatement(SQLMaster.GET_DETAIL_FROM_WEEKLY,ht);
	                ResultSet innerRs = innerPs.executeQuery();
	                if(innerRs.next())
	                {
	                    DailyDetails objDailyDetails = new DailyDetails();
	                    objDailyDetails.setJobClass(jbClass);
	                    System.out.println("jbclass:"+jbClass);
	                    objDailyDetails.setNoOfDaysInterval(innerRs.getInt("EVERY_PER_DAY"));
	                    objDailyDetails.setOnceHr(innerRs.getInt("OCCUR_ONCE_AT_HR"));
	                    objDailyDetails.setOnceMin(innerRs.getInt("OCCUR_ONCE_AT_MIN"));
	                    objDailyDetails.setOnceSecs(innerRs.getInt("OCCUR_ONCE_AT_SEC"));
	                    objDailyDetails.setRepeatedHr(innerRs.getInt("OCCUR_REPEAT_HR"));
	
	                    objDailyDetails.setRepeatedMin(innerRs.getInt("OCCUR_REPEAT_MIN"));
	                    
	                    objDailyDetails.setRepeatedSecs(innerRs.getInt("OCCUR_REPEAT_SEC"));
	                    
	                    objDailyDetails.setStartTimeHr(innerRs.getInt("START_AT_HR"));
	                    objDailyDetails.setStartTimeMin(innerRs.getInt("START_AT_MIN"));
	                    objDailyDetails.setStartTimeSec(innerRs.getInt("START_AT_SEC"));
	                    
	                    objDailyDetails.setEndTimeHr(innerRs.getInt("END_AT_HR"));
	                    objDailyDetails.setEndTimeMin(innerRs.getInt("END_AT_MIN"));
	                    objDailyDetails.setEndTimeSec(innerRs.getInt("END_AT_SEC"));
	                    
	                    objDailyDetails.setJobStartDate(startDate);
	                    objDailyDetails.setJobEndDate(endDate);
	                        
	                    objVec.add(objDailyDetails);
	                }
	                innerRs.close();
	                innerPs.close();
	            
	                
	            }
	        }
	        rs.close();
	        ps.close();
	        if(BuildConfig.DMODE)
	        {
	            System.out.println("Fetch job Ends");
	        }
	    }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
        	db.closeConnection();
        }
        return objVec.toArray();
    }
    
    public static void main(String[] agrs)
    {
        try
        {
            Object[] obj = new FetchTasks().fetch();
            for(int i=0;i<obj.length;i++)
            {
                DailyDetails objdailydetails = (DailyDetails)obj[i];
                System.out.println(objdailydetails.getJobClass());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
