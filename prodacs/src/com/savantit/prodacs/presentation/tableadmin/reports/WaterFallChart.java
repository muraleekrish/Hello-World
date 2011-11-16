/*
 * Created on Jan 6, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savantit.prodacs.presentation.tableadmin.reports;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.savantit.prodacs.businessimplementation.reports.ReportsGraphDetailsManager;
import com.savantit.prodacs.infra.util.BuildConfig;

/**
 * @author vkrishnamoorthy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class WaterFallChart extends HttpServlet 
{
	Properties props = new Properties();
	private ServletContext context;

	public void init(ServletConfig config) throws ServletException 
	{
        this.context = config.getServletContext();
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		ReportsGraphDetailsManager objReportsGraphDetailsManager = new ReportsGraphDetailsManager();
		String fileName = "";
		String graphUrl = "";
		String resp = "";
		if (BuildConfig.DMODE)
			System.out.println("QueryString Value in WaterFallServlet :"+request.getQueryString());
		int param = Integer.parseInt((request.getParameter("value")));
		switch (param)
		{
			case 1:
			{
				if ((!request.getParameter("startDate").equalsIgnoreCase("") && (!request.getParameter("endDate").equalsIgnoreCase("")) && (!request.getParameter("machineCode").equalsIgnoreCase(""))))
				{
					/* For Start Date */
					StringTokenizer st = new StringTokenizer(request.getParameter("startDate"),"-");
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

					/* To End Date */
					StringTokenizer st1 = new StringTokenizer(request.getParameter("endDate"),"-");
					yr = 0;
					month = 0;
					day = 0;
					if(st1.hasMoreTokens())
					{
						yr = Integer.parseInt(st1.nextToken().trim());
					}
					if(st1.hasMoreTokens())
					{
						month = Integer.parseInt(st1.nextToken().trim());
					}
					if(st1.hasMoreTokens())
					{		
						day = Integer.parseInt(st1.nextToken().trim());
					}
					GregorianCalendar ge1 = new GregorianCalendar(yr,month-1,day);
					String machCode = request.getParameter("machineCode");
					PrintWriter out = new PrintWriter(System.out);
					fileName = objReportsGraphDetailsManager.generateWaterfallChartForIdleBrkDwn(ge.getTime(),ge1.getTime(),machCode,request.getSession(),out);
					if (BuildConfig.DMODE)
						System.out.println("FileName in WaterFall Servlet:"+fileName);
				}
				break;
			}
		}
		OutputStream out = response.getOutputStream();
		FileInputStream fis = new FileInputStream(System.getProperty("catalina.home")+"/temp/"+fileName);
		byte img[] = new byte[1024];
		int len=0;
		while((len=fis.read(img))>0)
			out.write(img,0,len);
		out.flush();
		
	}
}

/***
$Log: WaterFallChart.java,v $
Revision 1.1  2006/01/06 07:45:29  vkrishnamoorthy
Initial commit on ProDACS.

***/