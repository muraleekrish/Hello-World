/*
 * Created on Nov 15, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savantit.prodacs.presentation.tableadmin.production;

import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.rmi.PortableRemoteObject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.savantit.prodacs.businessimplementation.production.ProductionJobDetails;
import com.savantit.prodacs.facade.SessionProductionDetailsManager;
import com.savantit.prodacs.facade.SessionProductionDetailsManagerHome;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.util.EJBLocator;

/**
 * @author vkrishnamoorthy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ProductionServlet extends HttpServlet
{
	private ServletContext context;

	public void init(ServletConfig config) throws ServletException 
	{
        this.context = config.getServletContext();
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		EJBLocator obj = new EJBLocator();
		String select = "";
		try
		{
			/* 	Setting the JNDI name and Environment 	*/
		   	obj.setJndiName("SessionProductionDetailsManagerBean");
	   		obj.setEnvironment();

			/* 	Creating the Home and Remote Objects 	*/
			SessionProductionDetailsManagerHome proHomeObj = (SessionProductionDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionProductionDetailsManagerHome.class);
			SessionProductionDetailsManager productionObj = (SessionProductionDetailsManager)PortableRemoteObject.narrow(proHomeObj.create(),SessionProductionDetailsManager.class);
			
			if (BuildConfig.DMODE)
			{
				System.out.println("QueryString Value in Servlet :"+request.getQueryString());
				System.out.println("Value :"+request.getParameter("value"));
			}
			int param = Integer.parseInt((request.getParameter("value")));
			switch (param)
			{
				case 1:
				{
					if (!request.getParameter("proDate").equalsIgnoreCase(""))
					{
						StringTokenizer st = new StringTokenizer(request.getParameter("proDate"),"-");
						int yr = 0;
						int month = 0;
						int day = 0;
						if(st.hasMoreTokens())
						{
							yr = Integer.parseInt(st.nextToken().trim());
						}
						if(st.hasMoreTokens())
						{
							month = Integer.parseInt(st.nextToken().trim());
						}
						if(st.hasMoreTokens())
						{		
							day = Integer.parseInt(st.nextToken().trim());
						}
						GregorianCalendar ge = new GregorianCalendar(yr,month-1,day);

						HashMap shiftNames = productionObj.getShiftDefnNameList(ge.getTime());
						select += "<select name='proShift' id='proShift' class='Combo' onchange='loadMachines();'>";
						select += "<option value='0'>-- Select Shift --</option>";
						Set set = shiftNames.entrySet();
					    Iterator it = set.iterator();
					    while (it.hasNext())
					    {
					      Map.Entry me = (Map.Entry) it.next();
					      select += "<option value=\""+(me.getKey())+"\">";
					      select += me.getValue();
					      select += "</option>";
					    }
					    select += "</select>";
					    if (BuildConfig.DMODE)
					    	System.out.println("Select tag :"+select);
					}
					else
					{
						select += "<select name='proShift' id='proShift' class='Combo' onchange='loadMachines();'>";
						select += "<option value='0'>-- Select Shift --</option>";
					    select += "</select>";							
					}
					break;
					
				}
				case 2:
				{
					if (!(request.getParameter("proDate").equalsIgnoreCase("") || (request.getParameter("shiftId").equalsIgnoreCase("0"))))
					{
						StringTokenizer st = new StringTokenizer(request.getParameter("proDate"),"-");
						int yr = 0;
						int month = 0;
						int day = 0;
						if(st.hasMoreTokens())
						{
							yr = Integer.parseInt(st.nextToken().trim());
						}
						if(st.hasMoreTokens())
						{
							month = Integer.parseInt(st.nextToken().trim());
						}
						if(st.hasMoreTokens())
						{		
							day = Integer.parseInt(st.nextToken().trim());
						}
						GregorianCalendar ge = new GregorianCalendar(yr,month-1,day);

						HashMap machNames = productionObj.getAllProdMachines(ge.getTime(),Integer.parseInt(request.getParameter("shiftId")));
						select += "<select name='proMachine' id='proMachine' class='ComboFull'>";
						select += "<option value='0'>-- Select Machine --</option>";
						Set set = machNames.entrySet();
					    Iterator it = set.iterator();
					    while (it.hasNext())
					    {
					      Map.Entry me = (Map.Entry) it.next();
					      select += "<option value=\""+(me.getKey())+"\">";
					      select += me.getKey();
					      select += "</option>";
					    }
					    select += "</select>";
					    if (BuildConfig.DMODE)
					    	System.out.println("Select tag :"+select);
					}
					else
					{
						select += "<select name='proMachine' id='proMachine' class='Combo'>";
						select += "<option value='0'>-- Select Machine --</option>";
					    select += "</select>";							
					}
					break;
				}
				case 3:
				{
					if (!request.getParameter("wrkOrdId").equalsIgnoreCase(""))
					{
						Vector vecJobDet = productionObj.getProdnJobDetailsByWorkOrder(Integer.parseInt(request.getParameter("wrkOrdId")));
						for (int i = 0; i < vecJobDet.size(); i++)
						{
							ProductionJobDetails objProductionJobDetails = null;
							objProductionJobDetails = (ProductionJobDetails)vecJobDet.get(i);
							if (i != 0)
							{
								select += "^";
							}
							select += objProductionJobDetails.getWoJbId()+"~"+
									  objProductionJobDetails.getJobName()+"~"+
									  objProductionJobDetails.getTotQty()+"~"+
									  objProductionJobDetails.getPendingQtySnos()+"~"+
									  objProductionJobDetails.getPostedQtySnos()+"~"+
									  objProductionJobDetails.getUnPostedQtySnos()+"~";
							String date = ((objProductionJobDetails.getLastProdnDate()!= null) ? (objProductionJobDetails.getLastProdnDate().toString().substring(0,10)):"");
							select += date;
						}
					}
					break;
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		response.setContentType("*/*");
		response.setHeader("Cache-Control", "no-cache");
	    response.getWriter().write(select);
	}
}


/***
$Log: ProductionServlet.java,v $
Revision 1.2  2005/11/25 03:57:51  vkrishnamoorthy
Umwanted println() removed.

Revision 1.1  2005/11/21 12:07:49  vkrishnamoorthy
Initial commit on ProDACS for implementing AJAX.

***/