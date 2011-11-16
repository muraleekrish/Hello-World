/*
 * Created on Jun 13, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
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

import com.savantit.prodacs.businessimplementation.securityadmin.ContactDetails;
import com.savantit.prodacs.businessimplementation.securityadmin.SecAdminGroupDetails;
import com.savantit.prodacs.businessimplementation.securityadmin.SecAdminUserDetails;
import com.savantit.prodacs.businessimplementation.securityadmin.SecurityAdminException;
import com.savantit.prodacs.facade.SessionSecurityAdminManager;
import com.savantit.prodacs.facade.SessionSecurityAdminManagerHome;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.util.EJBLocator;

/**
 * @author vkrishnamoorthy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UserPreferencesAction extends Action 
{
	public ActionForward execute (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		String action = "failure";
		if (form instanceof UserPreferencesForm)
		{
			UserPreferencesForm frm = (UserPreferencesForm) form;
			EJBLocator obj = new EJBLocator(); /* Instantiating object for EJBLocator */
			ActionErrors errors = new ActionErrors();
			SecAdminUserDetails secAdminDets = new SecAdminUserDetails();
			ContactDetails cntctDets = new ContactDetails();
			try
			{
				/* JNDI Name and Environment Setting*/
				obj.setJndiName("SessionSecurityAdminManager");
				obj.setEnvironment();
				
				/* Creation of Home and Remote Objects */
				SessionSecurityAdminManagerHome secAdminHomeObj = (SessionSecurityAdminManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionSecurityAdminManagerHome.class);
				SessionSecurityAdminManager secAdminObj = (SessionSecurityAdminManager)PortableRemoteObject.narrow(secAdminHomeObj.create(),SessionSecurityAdminManager.class);
				if (BuildConfig.DMODE)
					System.out.println("frm.getFormAction() :"+frm.getFormAction());
				if (frm.getFormAction().equalsIgnoreCase("modify"))
				{
					if (BuildConfig.DMODE)
						System.out.println("Modification Starts");
					
					secAdminDets = secAdminObj.getUserDetails(frm.getId().trim());
					cntctDets = secAdminDets.getObj_Contact_Details();
					frm.setUserId(secAdminDets.getUser_Name().trim());
					frm.setUserPwd(secAdminDets.getUser_Password().trim());
					frm.setConfirmPwd(secAdminDets.getUser_Password().trim());
					frm.setDescription(secAdminDets.getUser_Desc().trim());
					frm.setCntctId(cntctDets.getCntct_Id()+"");
					if (BuildConfig.DMODE)
						System.out.println("CNTCT_ID :"+"-"+frm.getCntctId()+"-"+frm.getCntctId().trim()+"-");					
					frm.setTitle(cntctDets.getCntct_Title().trim());
					frm.setFirstName(cntctDets.getCntct_Fname().trim());
					frm.setLastName(cntctDets.getCntct_Lname().trim());
					if (cntctDets.getCntct_Dob() == null)
					{
						frm.setDateOfBirth("");
					}
					else
					{
						frm.setDateOfBirth(cntctDets.getCntct_Dob().toString().substring(0,10));
					}
					frm.setCustName(cntctDets.getCntct_Company()+"");
					frm.setAddress1(cntctDets.getCntct_Address1().trim());
					frm.setAddress2(cntctDets.getCntct_Address2().trim());
					frm.setPosition(cntctDets.getCntct_Position().trim());
					frm.setCity(cntctDets.getCntct_City().trim());
					frm.setState(cntctDets.getCntct_State().trim());
					frm.setPinCode(cntctDets.getCntct_Pincode().trim());
					frm.setCountry(cntctDets.getCntct_Country().trim());
					frm.setMailId(cntctDets.getCntct_Email().trim());
					frm.setHousePhone(cntctDets.getCntct_HPhone().trim());
					frm.setWorkPhone(cntctDets.getCntct_WPhone().trim());
					frm.setExtension(cntctDets.getCntct_Extension().trim());
					frm.setMobile(cntctDets.getCntct_Mobile().trim());
					frm.setFax(cntctDets.getCntct_Fax().trim());

					Vector vec_selectedGroups = secAdminDets.getUser_Rolls();
					String groupNames = "";
					for (int i = 0; i < vec_selectedGroups.size(); i++)
					{
						SecAdminGroupDetails grpDetailObj = new SecAdminGroupDetails();
						grpDetailObj = (SecAdminGroupDetails) vec_selectedGroups.get(i);

						if (i == 0)
						{
							groupNames = groupNames + grpDetailObj.getGroup_Id()+"";
						}
						else
						{
							groupNames = groupNames + "^" + grpDetailObj.getGroup_Id()+"";
						}
						frm.setHidGroupDetails(groupNames.trim());
						if (BuildConfig.DMODE)
						{
							System.out.println("GroupNames :"+frm.getHidGroupDetails());
							System.out.println("Modification Ends");
						}
					}
				}
				else if (frm.getFormAction().equalsIgnoreCase("update"))
				{
					if (BuildConfig.DMODE)
					{
						System.out.println("Updation Starts");
					}
					try
					{
						secAdminDets.setUser_Name(frm.getUserId().trim());
						secAdminDets.setUser_Password(frm.getUserPwd().trim());
						secAdminDets.setUser_Desc(frm.getDescription().trim());
						if (BuildConfig.DMODE)
							System.out.println("CNTCT_ID :"+"-"+frm.getCntctId()+"-"+frm.getCntctId().trim()+"-");
						cntctDets.setCntct_Id(Integer.parseInt(frm.getCntctId().trim()));
						cntctDets.setCntct_Title(frm.getTitle().trim());
						cntctDets.setCntct_Fname(frm.getFirstName().trim());
						cntctDets.setCntct_Lname(frm.getLastName().trim());
						int yr = 0;
						int month = 0;
						int day = 0;
						if (!frm.getDateOfBirth().equalsIgnoreCase(""))
						{
							String dob = frm.getDateOfBirth().toString().substring(0,10);
							if (BuildConfig.DMODE)
								System.out.println("DOB :"+dob);
							StringTokenizer st = new StringTokenizer(dob,"-");
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
						if (BuildConfig.DMODE)
							System.out.println("-->"+frm.getCustName());
						cntctDets.setCntct_Company(Integer.parseInt(frm.getCustName().trim()));
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
						
						if (BuildConfig.DMODE)
							System.out.println("frm.getHidGroupDetails() :"+frm.getHidGroupDetails());
						Vector vec_selectedGroups = new Vector();
						if (frm.getHidGroupDetails() != null)
						{
							StringTokenizer stGrps = new StringTokenizer(frm.getHidGroupDetails(),"^");
							String[] arTotGrps = new String[stGrps.countTokens()];
							int count = 0;
							while (stGrps.hasMoreTokens())
							{
								arTotGrps[count] = stGrps.nextToken();
								if (BuildConfig.DMODE)
									System.out.println(count+". "+arTotGrps[count]);
								vec_selectedGroups.add(arTotGrps[count]);
								count++;
							}
						}
						secAdminDets.setUser_Rolls(vec_selectedGroups);
						
						secAdminDets.setObj_Contact_Details(cntctDets);
						boolean updated = secAdminObj.updateUser(secAdminDets);
						
						if (updated == true)
						{
							ActionMessages message = new ActionMessages();
							message.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.updated","User",frm.getUserId()));
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
					catch (SecurityAdminException se)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.name",se.getExceptionMessage()));
						if(!errors.isEmpty())
							saveErrors(request,errors);
						action = "failure";
						
					}
				}
			}
			catch(Exception e)
			{
				if (BuildConfig.DMODE)
					e.printStackTrace();
			}
		}
		if (BuildConfig.DMODE)
			System.out.println("User Preferences Action :"+action);
		return mapping.findForward(action);
	}
}


/***
$Log: UserPreferencesAction.java,v $
Revision 1.3  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.2  2005/07/18 18:56:11  vkrishnamoorthy
Modified as per CustomerName.

Revision 1.1  2005/06/14 13:09:32  vkrishnamoorthy
Initial commit on ProDACS.

***/