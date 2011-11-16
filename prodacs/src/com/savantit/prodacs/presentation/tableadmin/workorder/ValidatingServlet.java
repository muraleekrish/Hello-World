package com.savantit.prodacs.presentation.tableadmin.workorder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.rmi.PortableRemoteObject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.savantit.prodacs.facade.SessionWorkOrderDetailsManager;
import com.savantit.prodacs.facade.SessionWorkOrderDetailsManagerHome;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.util.EJBLocator;

/**
 * @author vkrishnamoorthy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ValidatingServlet extends HttpServlet
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
				obj.setJndiName("SessionWorkOrderDetailsManagerBean");
				obj.setEnvironment();
				
				SessionWorkOrderDetailsManagerHome objWoOrderHome = (SessionWorkOrderDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionWorkOrderDetailsManagerHome.class);
				SessionWorkOrderDetailsManager objWoOrder = (SessionWorkOrderDetailsManager)PortableRemoteObject.narrow(objWoOrderHome.create(),SessionWorkOrderDetailsManager.class);
				if (BuildConfig.DMODE)
					System.out.println("QueryString Value in Servlet :"+request.getQueryString());
				int param = Integer.parseInt((request.getParameter("value")));
				switch (param)
				{
					case 1:
					{
						if (!request.getParameter("custTypeName").equalsIgnoreCase(""))
						{
							HashMap custNames = objWoOrder.getCustomerNameByType(Integer.parseInt(request.getParameter("custTypeName")));
							select += "<select name='woCustomerName' id='woCustomerName' class='Combo' onchange='loadGeneralName();'>";
							select += "<option value='0'>-- Customer Name --</option>";
							Set set = custNames.entrySet();
						    Iterator it = set.iterator();
						    while (it.hasNext())
						    {
						      Map.Entry me = (Map.Entry) it.next();
						      select += "<option value=\""+(me.getKey())+"\">";
						      select += me.getValue();
						      select += "</option>";
						    }
						    select += "</select>";
						}
						else
						{
							select += "<select name='woCustomerName' id='woCustomerName' class='Combo' onchange='loadGeneralName();'>";
							select += "<option value='0'>-- Customer Name --</option>";
						    select += "</select>";							
						}
						break;
					}
					case 2:
					{
						if (!request.getParameter("custName").equalsIgnoreCase(""))
						{
							HashMap genNames = objWoOrder.getGeneralNameByCustomer(Integer.parseInt(request.getParameter("custName")));
							select += "<select name='woGenName' id='woGenName' class='Combo' onchange='loadJobNames();'>";
							select += "<option value='0'>-- General Name --</option>";
							Set set = genNames.entrySet();
						    Iterator it = set.iterator();
						    while (it.hasNext())
						    {
						      Map.Entry me = (Map.Entry) it.next();
						      select += "<option value=\""+(me.getKey())+"\">";
						      select += me.getValue();
						      select += "</option>";
						    }
						    select += "</select>";
						}
						else
						{
							select += "<select name='woGenName' id='woGenName' class='Combo' onchange='loadJobNames();'>";
							select += "<option value='0'>-- General Name --</option>";
						    select += "</select>";							
						}
						break;
					}
					case 3:
					{
						if (!request.getParameter("genName").equalsIgnoreCase(""))
						{
							Vector jobNames = objWoOrder.getJobByGeneralName(Integer.parseInt(request.getParameter("genName")),Integer.parseInt(request.getParameter("custName")));
							select += "<select name='woJobName' id='woJobName' class='Combo' onchange='loadDrawingNo();'>";
							select += "<option value='-1'>-- Job Name --</option>";
							for (int i = 0; i < jobNames.size(); i++)
							{
								select += "<option value=\""+jobNames.get(i) +"\">";
								select += jobNames.get(i);
								select += "</option>";
						    }
						    select += "</select>";
						}
						else
						{
							select += "<select name='woJobName' id='woJobName' class='Combo' onchange='loadDrawingNo();'>";
							select += "<option value='-1'>-- Job Name --</option>";
						    select += "</select>";							
						}
						break;
					}
					case 4:
					{
						if (!request.getParameter("jbName").equalsIgnoreCase(""))
						{
							Vector dwgNo = objWoOrder.getDrawingNoByJobName(Integer.parseInt(request.getParameter("genName")),Integer.parseInt(request.getParameter("custName")),request.getParameter("jbName"));
							select += "<select name='woDrawingHash' id='woDrawingHash' class='ComboFull' onchange='loadRevisionNo();'>";
							select += "<option value='-1'>-- Drawing No --</option>";
							for (int i = 0; i < dwgNo.size(); i++)
							{
								select += "<option value=\""+dwgNo.get(i) +"\">";
								select += dwgNo.get(i);
								select += "</option>";
						    }
						    select += "</select>";
						}
						else
						{
							select += "<select name='woDrawingHash' id='woDrawingHash' class='ComboFull' onchange='loadRevisionNo();'>";
							select += "<option value='-1'>-- Drawing No --</option>";
						    select += "</select>";							
						}
						break;
					}
					case 5:
					{
						if (!request.getParameter("dwgNo").equalsIgnoreCase(""))
						{
							Vector revNo = objWoOrder.getRvsnNoByJobNameAndDrawingNo(Integer.parseInt(request.getParameter("genName")),Integer.parseInt(request.getParameter("custName")),request.getParameter("jbName"),request.getParameter("dwgNo"));
							select += "<select name='woRevisionHash' id='woRevisionHash' class='ComboFull' onchange='loadMaterialType();'>";
							select += "<option value='-1'>-- Revision No --</option>";
							for (int i = 0; i < revNo.size(); i++)
							{
								select += "<option value=\""+revNo.get(i) +"\">";
								select += revNo.get(i);
								select += "</option>";
						    }
						    select += "</select>";
						}
						else
						{
							select += "<select name='woRevisionHash' id='woRevisionHash' class='ComboFull' onchange='loadMaterialType();'>";
							select += "<option value='-1'>-- Revision No --</option>";
						    select += "</select>";							
						}
						break;
					}
					case 6:
					{
						if (!request.getParameter("rvsnNo").equalsIgnoreCase(""))
						{
							Vector matlTypes = objWoOrder.getMatlTypByJobNameDrawingNoAndRvsnNo(Integer.parseInt(request.getParameter("custName")),Integer.parseInt(request.getParameter("genName")),request.getParameter("jbName"),request.getParameter("dwgNo"),request.getParameter("rvsnNo"));
							select += "<select name='woMaterialType' id='woMaterialType' class='ComboFull' onchange='loadJobId();'>";
							select += "<option value='-1'>-- Material Type --</option>";
							for (int i = 0; i < matlTypes.size(); i++)
							{
								select += "<option value=\""+matlTypes.get(i) +"\">";
								select += matlTypes.get(i);
								select += "</option>";
						    }
						    select += "</select>";
						}
						else
						{
							select += "<select name='woMaterialType' id='woMaterialType' class='ComboFull' onchange='loadJobId();'>";
							select += "<option value='-1'>-- Material Type --</option>";
						    select += "</select>";							
						}
						break;
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			response.setContentType("*/*");
			response.setHeader("Cache-Control", "no-cache");
		    response.getWriter().write(select);
		}
	}


/***
$Log: ValidatingServlet.java,v $
Revision 1.1  2005/11/18 12:32:36  vkrishnamoorthy
Initial commit on ProDACS for implementing AJAX.

***/