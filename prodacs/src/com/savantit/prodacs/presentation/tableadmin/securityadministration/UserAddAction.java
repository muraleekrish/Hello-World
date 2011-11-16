/*
 * Created on Jan 6, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.securityadministration;

import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.rmi.PortableRemoteObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.savantit.prodacs.businessimplementation.employee.EmployeeDetails;
import com.savantit.prodacs.businessimplementation.securityadmin.ContactDetails;
import com.savantit.prodacs.businessimplementation.securityadmin.SecAdminUserDetails;
import com.savantit.prodacs.businessimplementation.securityadmin.SecurityAdminException;
import com.savantit.prodacs.facade.SessionSecurityAdminManager;
import com.savantit.prodacs.facade.SessionSecurityAdminManagerHome;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.util.EJBLocator;

/**
 * @author vkrishnamoorthy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class UserAddAction extends Action 
{
	public ActionForward execute (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		String action = "failure";
		int yr = 0;
		int month = 0;
		int day = 0;
		
		if (form instanceof UserAddForm)
		{
			UserAddForm frm = (UserAddForm) form;
			EJBLocator obj = new EJBLocator(); /* Instantiating object for EJBLocator */
			ActionErrors errors = new ActionErrors();
			SecAdminUserDetails secAdminDets = new SecAdminUserDetails();
			ContactDetails cntctDets = new ContactDetails();
			EmployeeDetails objEmpDets = new EmployeeDetails();
			try
			{
				/* JNDI Name and Environment Setting*/
				obj.setJndiName("SessionSecurityAdminManager");
				obj.setEnvironment();
				
				/* Creation of Home and Remote Objects */
				SessionSecurityAdminManagerHome secAdminHomeObj = (SessionSecurityAdminManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionSecurityAdminManagerHome.class);
				SessionSecurityAdminManager secAdminObj = (SessionSecurityAdminManager)PortableRemoteObject.narrow(secAdminHomeObj.create(),SessionSecurityAdminManager.class);
				if (frm.getFormAction().equalsIgnoreCase("empDets"))
				{
					objEmpDets = secAdminObj.getEmployeeDetails(Integer.parseInt(frm.getEmpName()));
					frm.setDateOfBirth(objEmpDets.getEmp_Dob().toString().substring(0,10));
					frm.setAddress1(objEmpDets.getEmp_Cntct_Addr1());
					frm.setAddress2(objEmpDets.getEmp_Cntct_Addr2());
					frm.setCity(objEmpDets.getEmp_Cntct_City());
					frm.setState(objEmpDets.getEmp_Cntct_State());
					frm.setPinCode(objEmpDets.getEmp_Cntct_Pcode());
					frm.setFormAction("add");
				}
				else if (frm.getFormAction().equalsIgnoreCase("add"))
				{
					secAdminDets.setUser_Name(frm.getUserId().trim());
					secAdminDets.setUser_Password(frm.getUserPwd().trim());
					secAdminDets.setUser_Desc(frm.getDescription().trim());
					
					cntctDets.setCntct_Title(frm.getTitle().trim());
					cntctDets.setCntct_Fname(frm.getFirstName().trim());
					cntctDets.setCntct_Lname(frm.getLastName().trim());
					if (!frm.getDateOfBirth().equalsIgnoreCase(""))
					{
						StringTokenizer st = new StringTokenizer(frm.getDateOfBirth(),"-");
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
						cntctDets.setCntct_Dob(ge.getTime());
					}
					if (!frm.getCustName().equalsIgnoreCase(""))
					{
						cntctDets.setCntct_Company(Integer.parseInt(frm.getCustName().trim()));
					}
					cntctDets.setCntct_Address1(frm.getAddress1().trim());
					cntctDets.setCntct_Address2(frm.getAddress2().trim());
					cntctDets.setCntct_Position(frm.getPosition().trim());
					cntctDets.setCntct_City(frm.getCity().trim());
					cntctDets.setCntct_State(frm.getState().trim());
					cntctDets.setCntct_Pincode(frm.getPinCode().trim());
					cntctDets.setCntct_Country(frm.getCountry().trim());
					cntctDets.setCntct_Email(frm.getMailId().trim());
					cntctDets.setCntct_HPhone(frm.getHousePhone().trim());
					cntctDets.setCntct_WPhone(frm.getWorkPhone().trim());
					cntctDets.setCntct_Extension(frm.getExtension().trim());
					cntctDets.setCntct_Mobile(frm.getMobile().trim());
					cntctDets.setCntct_Fax(frm.getFax().trim());
					
					Vector vec_selectedGroups = new Vector();
					if (frm.getGroupNames() != null)
					{
						String[] selectedGroups = frm.getGroupNames();
						for(int i = 0; i < selectedGroups.length; i++)
						{
							if (BuildConfig.DMODE)
								System.out.println("RESOURCE ID :"+selectedGroups[i]);
							vec_selectedGroups.add(selectedGroups[i]);
						}
					}
					secAdminDets.setUser_Rolls(vec_selectedGroups);
					secAdminDets.setObj_Contact_Details(cntctDets);
					boolean added = secAdminObj.addNewUser(secAdminDets);
					
					if (added == true)
					{
						ActionMessages message = new ActionMessages();
						message.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.added","User",frm.getUserId()));
						saveMessages(request,message);
						action="success";
					}
					else
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general"));
						if(!errors.isEmpty())
							saveErrors(request,errors);
						action="failure";				  			
					}
				}
			}
			catch (SecurityAdminException se)
			{
				if (BuildConfig.DMODE)
					se.printStackTrace();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.name",se.getExceptionMessage()));
				if(!errors.isEmpty())
					saveErrors(request,errors);
				action = "failure";
			}
			catch (Exception e)
			{
				if (BuildConfig.DMODE)
					e.printStackTrace();
			}
		}
		if (BuildConfig.DMODE)
			System.out.println("UserAdd Action :"+action);
		return mapping.findForward(action);
	}
}


/***
$Log: UserAddAction.java,v $
Revision 1.7  2005/08/17 09:20:05  vkrishnamoorthy
Field name changed.

Revision 1.6  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.5  2005/07/18 13:47:47  vkrishnamoorthy
User Company modified.

Revision 1.4  2005/04/06 05:02:27  vkrishnamoorthy
Modified for date.

Revision 1.3  2005/04/05 12:25:45  vkrishnamoorthy
Unwanted Println's removed.

Revision 1.2  2005/03/31 07:51:10  vkrishnamoorthy
Fax Field added.

Revision 1.1  2005/03/31 06:09:46  vkrishnamoorthy
User Module completed.

Revision 1.3  2005/02/11 11:21:49  sponnusamy
Warnings removed

Revision 1.2  2005/02/03 05:15:01  vkrishnamoorthy
Log added.

***/