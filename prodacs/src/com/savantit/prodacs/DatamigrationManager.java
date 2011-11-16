/*
 * Created on Aug 3, 2005
 *
 * ClassName	:  	DatamigrationManager.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savantit.prodacs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import com.savantit.prodacs.infra.dbtools.DBConnection;
import com.savantit.prodacs.infra.dbtools.DBConnectionFactory;

/**
 * @author kduraisamy
 *
 */
public class DatamigrationManager
{
    public static void main(String args[]) throws SQLException
    {
        DBConnection con = DBConnectionFactory.getConnection();
        PreparedStatement ps = con.executeStatement("select * from movedb.Workorder_Extension we,WO_JB_HEADER WJH where we.wo_id = WJH.WOJB_ID");
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            int wo_jbId = rs.getInt("WO_ID");
            System.out.println("WOJB_ID :"+wo_jbId);
            int jobsno = rs.getInt("job_serialno");
            String opn1 = rs.getString("operation1");
            String opn2 = rs.getString("operation2");
            String opn3 = rs.getString("operation3");
            String opn4 = rs.getString("operation4");
            String opn5 = rs.getString("operation5");
            String opn6 = rs.getString("operation6");
            String opn7 = rs.getString("operation7");
            String opn8 = rs.getString("operation8");
            String opn9 = rs.getString("operation9");
            String opn10 = rs.getString("operation10");
            String opn11 = rs.getString("operation11");
            String opn12 = rs.getString("operation12");
            String opn13 = rs.getString("operation13");
            String opn14 = rs.getString("operation14");
            String opn15 = rs.getString("operation15");
            String opn16 = rs.getString("operation16");
            String opn17 = rs.getString("operation17");
            String opn18 = rs.getString("operation18");
            String opn19 = rs.getString("operation19");
            String opn20 = rs.getString("operation20");
            String opn21 = rs.getString("operation21");
            String opn22 = rs.getString("operation22");
            String opn23 = rs.getString("operation23");
            String opn24 = rs.getString("operation24");
            String opn25 = rs.getString("operation25");
            String opn26 = rs.getString("operation26");
            String opn27 = rs.getString("operation27");
            String opn28 = rs.getString("operation28");
            String opn29 = rs.getString("operation29");
            String opn30 = rs.getString("operation30");
            String opn31 = rs.getString("operation31");
            String opn32 = rs.getString("operation32");
            String opn33= rs.getString("operation33");
            String opn34 = rs.getString("operation34");
            String opn35 = rs.getString("operation35");
            String opn36 = rs.getString("operation36");
            String opn37 = rs.getString("operation37");
            String opn38 = rs.getString("operation38");
            String opn39 = rs.getString("operation39");
            String opn40 = rs.getString("operation40");
            String opn41 = rs.getString("operation41");
            String opn42 = rs.getString("operation42");
            String opn43 = rs.getString("operation43");
            String opn44 = rs.getString("operation44");
            String opn45 = rs.getString("operation45");
            String opn46 = rs.getString("operation46");
            String opn47 = rs.getString("operation47");
            String opn48 = rs.getString("operation48");
            String opn49 = rs.getString("operation49");
            String opn50 = rs.getString("operation50");
            
            //to find wojbstat_id
            PreparedStatement ps1 = con.executeStatement("select WOJBSTAT_ID FROM WO_JB_STAT WJS where WJS.WOJB_ID ="+wo_jbId+" and WOJBSTAT_SNO ="+jobsno);
            ResultSet rs1 = ps1.executeQuery();
            int wojbstat_id = 0;
            if(rs1.next())
            {
                wojbstat_id = rs1.getInt("WOJBSTAT_ID");
            }
            System.out.println("WOJBSTAT_ID :"+wojbstat_id);
            if(opn1 !=null && (opn1.equalsIgnoreCase("D") || opn1.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn1+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 1");
            if(opn2 !=null && (opn2.equalsIgnoreCase("D") || opn2.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn2+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 2");
            if(opn3 !=null && (opn3.equalsIgnoreCase("D") || opn3.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn3+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 3");
            if(opn4 !=null && (opn4.equalsIgnoreCase("D") | opn4.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn4+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 4");
            if(opn5 !=null && (opn5.equalsIgnoreCase("D") || opn5.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn5+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 5");
            if(opn6 !=null && (opn6.equalsIgnoreCase("D") || opn6.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn6+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 6");
            if(opn7 !=null && (opn7.equalsIgnoreCase("D") || opn7.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn7+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 7");
            if(opn8 !=null && (opn8.equalsIgnoreCase("D") || opn8.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn8+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 8");
            if(opn9 !=null && (opn9.equalsIgnoreCase("D") || opn9.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn9+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 9");
            if(opn10 !=null && (opn10.equalsIgnoreCase("D") || opn10.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn10+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 10");
            if(opn11 !=null && (opn11.equalsIgnoreCase("D") || opn11.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn11+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 11");
            if(opn12 !=null && (opn12.equalsIgnoreCase("D") || opn12.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn12+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 12");
            if(opn13 !=null && (opn13.equalsIgnoreCase("D") || opn13.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn13+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 13");
            if(opn14 !=null && (opn14.equalsIgnoreCase("D") || opn14.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn14+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 14");
            if(opn15 !=null && (opn15.equalsIgnoreCase("D") || opn15.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn15+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 15");
            if(opn16 !=null && (opn16.equalsIgnoreCase("D") || opn16.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn16+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 16");
            if(opn17 !=null && (opn17.equalsIgnoreCase("D") || opn17.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn17+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 17");
            if(opn18 !=null && (opn18.equalsIgnoreCase("D") || opn18.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn18+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 18");
            if(opn19 !=null && (opn19.equalsIgnoreCase("D") || opn19.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn19+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 19");
            if(opn20 !=null && (opn20.equalsIgnoreCase("D") || opn20.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn20+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 20");
            if(opn21 !=null && (opn21.equalsIgnoreCase("D") || opn21.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn21+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 21");
            if(opn22 !=null && (opn22.equalsIgnoreCase("D") || opn22.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn22+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 22");
            if(opn23 !=null && (opn23.equalsIgnoreCase("D") || opn23.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn23+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 23");
            if(opn24 !=null && (opn24.equalsIgnoreCase("D") || opn24.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn24+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 24");
            if(opn25 !=null && (opn25.equalsIgnoreCase("D") || opn25.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn25+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 25");
            if(opn26 !=null && (opn26.equalsIgnoreCase("D") || opn26.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn26+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 26");
            if(opn27 !=null && (opn27.equalsIgnoreCase("D") || opn27.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn27+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 27");
            if(opn28 !=null && (opn28.equalsIgnoreCase("D") || opn28.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn28+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 28");
            if(opn29 !=null && (opn29.equalsIgnoreCase("D") || opn29.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn29+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 29");
            if(opn30 !=null && (opn30.equalsIgnoreCase("D") || opn30.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn30+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 30");
            if(opn31 !=null && (opn31.equalsIgnoreCase("D") || opn31.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn31+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 31");
            if(opn32 !=null && (opn32.equalsIgnoreCase("D") || opn32.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn32+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 32");
            if(opn33 !=null && (opn33.equalsIgnoreCase("D") || opn33.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn33+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 33");
            if(opn34 !=null && (opn34.equalsIgnoreCase("D") || opn34.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn34+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 34");
            if(opn35 !=null && (opn35.equalsIgnoreCase("D") || opn35.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn35+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 35");
            if(opn36 !=null && (opn36.equalsIgnoreCase("D") || opn36.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn36+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 36");
            if(opn37 !=null && (opn37.equalsIgnoreCase("D") || opn37.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn37+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 37");
            if(opn38 !=null && (opn38.equalsIgnoreCase("D") || opn38.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn38+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 38");
            if(opn39 !=null && (opn39.equalsIgnoreCase("D") || opn39.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn39+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 39");
            if(opn40 !=null && (opn40.equalsIgnoreCase("D") || opn40.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn40+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 40");
            if(opn41 !=null && (opn41.equalsIgnoreCase("D") || opn41.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn41+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 41");
            if(opn42 !=null && (opn42.equalsIgnoreCase("D") || opn42.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn42+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 42");
            if(opn43 !=null && (opn43.equalsIgnoreCase("D") || opn43.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn43+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 43");
            if(opn44 !=null && (opn44.equalsIgnoreCase("D") || opn44.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn44+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 44");
            if(opn45 !=null && (opn45.equalsIgnoreCase("D") || opn45.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn45+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 45");
            if(opn46 !=null && (opn46.equalsIgnoreCase("D") || opn46.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn46+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 46");
            if(opn47 !=null && (opn47.equalsIgnoreCase("D") || opn47.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn47+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 47");
            if(opn48 !=null && (opn48.equalsIgnoreCase("D") || opn48.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn48+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 48");
            if(opn49 !=null && (opn49.equalsIgnoreCase("D") || opn49.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn49+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 49");
            if(opn50 !=null && (opn50.equalsIgnoreCase("D") || opn50.equalsIgnoreCase("U")))
            con.executeUpdateStatement("update WO_JB_OPN set WOJBOPN_STAT ='"+opn50+"' where WOJBSTAT_ID ="+ wojbstat_id +" and WOJBOPN_OPN_SNO = 50");
        }
    }
}

/*
*$Log: DatamigrationManager.java,v $
*Revision 1.1  2005/08/23 09:24:49  kduraisamy
*CHECK EMP TYPE SQL QUERY ADDED.
*
*
*/