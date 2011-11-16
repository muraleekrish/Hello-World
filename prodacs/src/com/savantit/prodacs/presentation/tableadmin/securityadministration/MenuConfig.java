/*
 * Created on Apr 1, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savantit.prodacs.presentation.tableadmin.securityadministration;

import java.util.Vector;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.savantit.prodacs.businessimplementation.securityadmin.UserAuthDetails;
import com.savantit.prodacs.infra.util.BuildConfig;

/**
 * @author vkrishnamoorthy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MenuConfig extends TagSupport 
{
	String resourceId = "";
	String url = "";
	String text = "";
	String onMouseOver = "";
	String onMouseOut = "";
	String classId = "";
	String onClick = "";
	String id = "";
	String style = "";
	
	
	
	/**
	 * @param style The style to set.
	 */
	public void setStyle(String style) {
		this.style = style;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @param onClick The onClick to set.
	 */
	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}
	/**
	 * @param classId The classId to set.
	 */
	public void setClassId(String classId) {
		this.classId = classId;
	}
	/**
	 * @param onMouseOut The onMouseOut to set.
	 */
	public void setOnMouseOut(String onMouseOut) {
		this.onMouseOut = onMouseOut;
	}
	/**
	 * @param onMouseOver The onMouseOver to set.
	 */
	public void setOnMouseOver(String onMouseOver) {
		this.onMouseOver = onMouseOver;
	}
	/**
	 * @param text The text to set.
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * @param url The url to set.
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @param resourceId The resourceId to set.
	 */
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
	    public int doStartTag()
	    {
			Vector userRights = new Vector();
			UserAuthDetails userDetails = (UserAuthDetails)pageContext.getSession().getAttribute("##userRights##");
			try
			{
				if (userDetails != null)
				{
					userRights = userDetails.getVecUserAuth();
					try
					{
						JspWriter out = pageContext.getOut();
						if ((userRights.contains(new Integer(resourceId)) || (userRights.contains(new Integer(Integer.parseInt(resourceId)+1000)))))
						{
							  out.println("<td class=\""+classId+"\"><a href=\""+url+"\" onMouseOver=\""+onMouseOver+"\" onMouseOut=\""+onMouseOut+"\" onClick=\""+onClick+"\" id=\""+id+"\" style=\""+style+"\">"+text+"</a></td>");
						}
					}
					catch(Exception e)
					{
						if (BuildConfig.DMODE)
						{
							System.out.println("Session Timed Out! Relogin Again!");
							e.printStackTrace();
						}
							
					}
				}
				else
				{
					pageContext.getServletContext().getRequestDispatcher("/SecurityAdministration/AuthErrorPage.jsp").forward(pageContext.getRequest(),pageContext.getResponse());
				}
			}
			catch (Exception ex)
			{
				if (BuildConfig.DMODE)
				{
					System.out.println("Session Timed Out! Relogin Again!");
					ex.printStackTrace();
				}
			}
			return(SKIP_BODY);
		}
}



/***
$Log: MenuConfig.java,v $
Revision 1.7  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.6  2005/06/27 08:31:26  vkrishnamoorthy
Page redirection error printing statement modified.

Revision 1.5  2005/06/15 08:21:49  vkrishnamoorthy
Page redirected to AuthError page on Session time out.

Revision 1.4  2005/06/13 10:52:11  vkrishnamoorthy
Timeout error corrected.

Revision 1.3  2005/04/05 12:25:45  vkrishnamoorthy
Unwanted Println's removed.

Revision 1.2  2005/04/05 11:55:43  kduraisamy
Condition for Read&Write permission changed.

Revision 1.1  2005/04/04 09:22:29  vkrishnamoorthy
Initial Commit on Prodacs.

***/