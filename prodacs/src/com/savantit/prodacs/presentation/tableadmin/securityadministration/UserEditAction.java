/*
 * Created on Jan 6, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.securityadministration;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class UserEditAction extends Action 
{
	public ActionForward execute (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		String action = "failure";
		int yr = 0;
		int month = 0;
		int day = 0;
		if (form instanceof UserEditForm)
		{
			UserEditForm frm = (UserEditForm) form;
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
				
				if (frm.getFormAction().equalsIgnoreCase("view"))
				{
					action = "view";
				}
				else if (frm.getFormAction().equalsIgnoreCase("modify"))
				{
					if (BuildConfig.DMODE)
						System.out.println("Modification Starts");
					try
					{
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
						if (BuildConfig.DMODE)
							System.out.println(grpDetailObj.getGroup_Id());
						if (i == 0)
						{
							groupNames = groupNames + grpDetailObj.getGroup_Id()+"";
						}
						else
						{
							groupNames = groupNames + "^" + grpDetailObj.getGroup_Id()+"";
						}
					}
					if (BuildConfig.DMODE)
						System.out.println("GroupNames :"+groupNames);
					frm.setHidGroupDetails(groupNames.trim());
					if (BuildConfig.DMODE)
						System.out.println("Modification Ends");
					}
					catch(SecurityAdminException se)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.name",se.getExceptionMessage()));
						if(!errors.isEmpty())
							saveErrors(request,errors);
						action = "failure";
					}
				}
				else if (frm.getFormAction().equalsIgnoreCase("update"))
				{
					if (BuildConfig.DMODE)
						System.out.println("Updation Starts");
					
					secAdminDets.setUser_Name(frm.getUserId().trim());
					secAdminDets.setUser_Password(frm.getUserPwd().trim());
					secAdminDets.setUser_Desc(frm.getDescription().trim());
					if (BuildConfig.DMODE)
						System.out.println("CNTCT_ID :"+"-"+frm.getCntctId()+"-"+frm.getCntctId().trim()+"-");
					cntctDets.setCntct_Id(Integer.parseInt(frm.getCntctId().trim()));
					cntctDets.setCntct_Title(frm.getTitle().trim());
					cntctDets.setCntct_Fname(frm.getFirstName().trim());
					cntctDets.setCntct_Lname(frm.getLastName().trim());
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
				else if (frm.getFormAction().equalsIgnoreCase("delete"))
				{
					if (BuildConfig.DMODE)
						System.out.println("Delete");
					
					String ids[] = frm.getIds();
					Vector delList = new Vector();
					for (int i = 0; i < ids.length; i++)
					{
						delList.add(ids[i]);
						if (BuildConfig.DMODE)
							System.out.println("Del List: "+ ids[i]);
					}
					HashMap hm = new HashMap();
					try
					{
						hm = secAdminObj.deleteUsers(delList);
						if (BuildConfig.DMODE)
							System.out.println("Delete(HM): "+hm);
					}catch (Exception e)
					{
						if (BuildConfig.DMODE)
							System.out.println("Problem while Deleting the User");
					}
					
					int success = 0;
					int notFound = 0;
					int inUse = 0;
					int locked = 0;
					int general = 0;
					for (Iterator it=hm.entrySet().iterator(); it.hasNext(); ) 
					{ 
						Map.Entry entry = (Map.Entry)it.next(); 
						int value = ((Integer)entry.getValue()).intValue();
						if(value == 0)
						{
							success++;
						}
						else if(value == 1)
						{
							notFound++;
						}
						else if(value == 2)
						{
							inUse++;
						}
						else if(value == 3)
						{
							locked++;
						}						
						else if(value == 4)
						{
							general++;
						}												
					}
					ActionMessages messages = new ActionMessages();
					if(success<=1)
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.deleted", Integer.toString(success),"User"));
					else if(success>1)					
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.messages.deleted", Integer.toString(success),"Users"));					
					if(notFound == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notFound),"User"));						
					}
					else if(notFound > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notFound),"Users"));
					}
					
					if(inUse == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(inUse),"User"));						
					}
					else if(inUse > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(inUse),"Users"));
					}
					if(locked == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.locked", Integer.toString(locked),"User"));						
					}
					else if(locked > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.locked", Integer.toString(locked),"Users"));
					}
					if(general == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general", Integer.toString(general),"User"));						
					}
					else if(general > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.general", Integer.toString(general),"Users"));
					}
					if(hm.size() < delList.size())
					{
						int diff = delList.size()-hm.size();
						if(diff==1)
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.delete.general", Integer.toString(diff),"User"));
						else
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.delete.general", Integer.toString(diff),"Users"));
					}									
					if(!errors.isEmpty())
						saveErrors(request,errors);					
					saveMessages(request,messages);		
					action = "success";
				}
				else if (frm.getFormAction().equalsIgnoreCase("makeValid"))
				{
					if (BuildConfig.DMODE)
					{
						System.out.println("Make Valid");
						System.out.println("ID's ->"+frm.getIds());
					}
					String ids[] = frm.getIds();
					Vector v = new Vector();
					for (int i = 0; i < ids.length; i++)
					{
						v.add(ids[i]);
					}
					HashMap hm = new HashMap();
					try
					{
						hm = secAdminObj.makeUsersValid(v);
					}catch (Exception e)
					{
						if (BuildConfig.DMODE)
							System.out.println("Problem in MakeValid");
					}
					
					int success = 0;
					int notFound = 0;
					int inUse = 0;
					int locked = 0;
					int general = 0;
					for (Iterator it=hm.entrySet().iterator(); it.hasNext(); ) 
					{ 
						Map.Entry entry = (Map.Entry)it.next(); 
						int value = ((Integer)entry.getValue()).intValue();
						if(value == 0)
						{
							success++;
						}
						else if(value == 1)
						{
							notFound++;
						}
						else if(value == 2)
						{
							inUse++;
						}
						else if(value == 3)
						{
							locked++;
						}						
						else if(value == 4)
						{
							general++;
						}												
					}
					ActionMessages messages = new ActionMessages();
					if(success<=1)
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.valid", Integer.toString(success),"User"));
					else if(success>1)					
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.messages.valid", Integer.toString(success),"User"));					
					if(notFound == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notFound),"User"));						
					}
					else if(notFound > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notFound),"User"));
					}
					
					if(inUse == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(inUse),"User"));						
					}
					else if(inUse > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(inUse),"User"));
					}
					if(locked == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.locked", Integer.toString(locked),"User"));						
					}
					else if(locked > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.locked", Integer.toString(locked),"User"));
					}
					if(general == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general", Integer.toString(general),"User"));						
					}
					else if(general > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.general", Integer.toString(general),"User"));
					}
					if(hm.size()<v.size())
					{
						int diff = v.size()-hm.size();
						if(diff==1)
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general", Integer.toString(diff),"User"));
						else
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general", Integer.toString(diff),"User"));
					}									
					if(!errors.isEmpty())
						saveErrors(request,errors);					
					saveMessages(request,messages);		
					action = "success";

				}else if (frm.getFormAction().equalsIgnoreCase("makeInvalid"))
				{
					if (BuildConfig.DMODE)
					{
						System.out.println("Make InValid");
						System.out.println("ID's ->"+frm.getIds());
					}
					String ids[] = frm.getIds();
					Vector v = new Vector();
					for (int i = 0; i < ids.length; i++)
					{
						v.add(ids[i]);
						if (BuildConfig.DMODE)
							System.out.println("**> "+ids[i]);
					}
					HashMap hm = new HashMap();
					try
					{
						
						hm = secAdminObj.makeUsersInValid(v);
						if (BuildConfig.DMODE)
							System.out.println("Invalid(HM): "+ hm.size());
					}catch (Exception e)
					{
						if (BuildConfig.DMODE)
							System.out.println("Problem in MakeInvalid");
					}
					
					int success = 0;
					int notFound = 0;
					int inUse = 0;
					int locked = 0;
					int general = 0;
					for (Iterator it=hm.entrySet().iterator(); it.hasNext(); ) 
					{ 
						Map.Entry entry = (Map.Entry)it.next(); 
						int value = ((Integer)entry.getValue()).intValue();
						if(value == 0)
						{
							success++;
						}
						else if(value == 1)
						{
							notFound++;
						}
						else if(value == 2)
						{
							inUse++;
						}
						else if(value == 3)
						{
							locked++;
						}						
						else if(value == 4)
						{
							general++;
						}												
					}
					ActionMessages messages = new ActionMessages();
					if(success<=1)
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.invalid", Integer.toString(success),"User"));
					else if(success>1)					
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.messages.invalid", Integer.toString(success),"User"));					
					if(notFound == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notFound),"User"));						
					}
					else if(notFound > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notFound),"User"));
					}
					
					if(inUse == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(inUse),"User"));						
					}
					else if(inUse > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(inUse),"User"));
					}
					if(locked == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.locked", Integer.toString(locked),"User"));						
					}
					else if(locked > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.locked", Integer.toString(locked),"User"));
					}
					if(general == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general", Integer.toString(general),"User"));						
					}
					else if(general > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.general", Integer.toString(general),"User"));
					}
					if(hm.size()<v.size())
					{
						int diff = v.size()-hm.size();
						if(diff==1)
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general", Integer.toString(diff),"User"));
						else
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general", Integer.toString(diff),"User"));
					}									
					if(!errors.isEmpty())
						saveErrors(request,errors);					
					saveMessages(request,messages);		
					action = "success";
				}
			}
			catch (SecurityAdminException se)
			{
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
			System.out.println("UserEdit Action :"+action);
		return mapping.findForward(action);
	}
}


/***
$Log: UserEditAction.java,v $
Revision 1.14  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.13  2005/07/18 17:45:54  vkrishnamoorthy
Modified as per Company Names.

Revision 1.12  2005/06/16 12:04:51  vkrishnamoorthy
Exception caught for multiple instances user deletion.

Revision 1.11  2005/05/28 03:54:11  vkrishnamoorthy
Error messages thrown appropriately.

Revision 1.10  2005/05/16 06:21:17  vkrishnamoorthy
Update message modified.

Revision 1.9  2005/04/12 11:48:00  vkrishnamoorthy
Modified for displaying group names.

Revision 1.8  2005/04/06 11:44:06  vkrishnamoorthy
Delete option added.

Revision 1.7  2005/04/06 05:02:27  vkrishnamoorthy
Modified for date.

Revision 1.6  2005/04/05 12:25:45  vkrishnamoorthy
Unwanted Println's removed.

Revision 1.5  2005/04/05 12:14:29  vkrishnamoorthy
ContactId is set.

Revision 1.4  2005/04/04 09:23:35  vkrishnamoorthy
Fax Field added.

Revision 1.3  2005/03/31 07:51:10  vkrishnamoorthy
Fax Field added.

Revision 1.2  2005/03/31 06:52:41  vkrishnamoorthy
Invalid id passing value changed.

Revision 1.1  2005/03/31 06:09:46  vkrishnamoorthy
User Module completed.

Revision 1.3  2005/02/11 11:21:49  sponnusamy
Warnings removed

Revision 1.2  2005/02/03 05:15:01  vkrishnamoorthy
Log added.

***/