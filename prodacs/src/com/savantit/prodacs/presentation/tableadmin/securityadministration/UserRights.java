/*
 * Created on Mar 22, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savantit.prodacs.presentation.tableadmin.securityadministration;

import java.util.Vector;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.savantit.prodacs.businessimplementation.securityadmin.UserAuthDetails;
import com.savantit.prodacs.infra.util.BuildConfig;



/**
 * @author vkrishnamoorthy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UserRights extends TagSupport
{
	protected String resource = "";
	
	
	/**
	 * @return Returns the resource.
	 */
	public String getResource() {
		return resource;
	}
	/**
	 * @param resource The resource to set.
	 */
	public void setResource(String resource) {
		this.resource = resource;
	}
	
	public int doStartTag() throws JspException
	{
		try
		{
			boolean result = false;
			int resourceId = Integer.parseInt(this.getResource());
			UserAuthDetails usrAuthObj = (UserAuthDetails)pageContext.getSession().getAttribute("##userRights##");
			Vector vecUserResource = new Vector();
			if(usrAuthObj != null)
			{
				vecUserResource = usrAuthObj.getVecUserAuth();
			}
			else
			{
				pageContext.getServletContext().getRequestDispatcher("/SecurityAdministration/AuthErrorPage.jsp").forward(pageContext.getRequest(),pageContext.getResponse());
				result = false;
			}
			
			if(vecUserResource.contains((new Integer(resourceId))))
			{
				result = true;
			}
			if(resourceId<1000)
			{
				if(vecUserResource.contains((new Integer(resourceId+1000))))
				{
					result = true;
				}
			}
			if(!result)
			{
				pageContext.getServletContext().getRequestDispatcher("/SecurityAdministration/AuthErrorPage.jsp").forward(pageContext.getRequest(),pageContext.getResponse());
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
		return SKIP_BODY;
	}
	
	public int doEndTag()
	{
		return EVAL_PAGE;
	}
	
	
}



/***
$Log: UserRights.java,v $
Revision 1.4  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.3  2005/06/27 08:31:26  vkrishnamoorthy
Page redirection error printing statement modified.

Revision 1.2  2005/04/05 12:25:45  vkrishnamoorthy
Unwanted Println's removed.

Revision 1.1  2005/04/04 09:24:13  vkrishnamoorthy
Initial Commit on Prodacs.

***/