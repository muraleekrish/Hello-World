/*
 * Created on Apr 1, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savantit.prodacs.presentation.tableadmin.securityadministration;

import java.util.Vector;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.savantit.prodacs.businessimplementation.securityadmin.SecurityAdminManager;
import com.savantit.prodacs.businessimplementation.securityadmin.UserAuthDetails;
import com.savantit.prodacs.infra.util.BuildConfig;


/**
 * @author vkrishnamoorthy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DefaultResource extends BodyTagSupport 
{   
	public int doStartTag()
	{   
		JspWriter out = pageContext.getOut();
		SecurityAdminManager objSecurityAdminManager = new SecurityAdminManager();
		try
		{
		Vector userRights = new Vector();
		UserAuthDetails userDetails = objSecurityAdminManager.getUserResources(id);
		
		String id= userDetails.getUserId();
		if (BuildConfig.DMODE)
			System.out.println("user details : " + userDetails);
		userRights = userDetails.getVecUserAuth();
		if (BuildConfig.DMODE)
			System.out.println("user rights size : " + userRights.size());
		String sesState[]= new String[userRights.size()];
		userRights.copyInto(sesState);
		
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return(SKIP_BODY);        
	}
	
	public int doEndTag()
	{        
		return(EVAL_PAGE); 
	}
}


/***
$Log: DefaultResource.java,v $
Revision 1.2  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.1  2005/04/04 09:20:06  vkrishnamoorthy
Initial Commit on Prodacs.

***/