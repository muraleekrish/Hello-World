/*
 * Created on Jan 4, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.production;

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

import com.savantit.prodacs.businessimplementation.production.EmployeeDtyOtDetails;
import com.savantit.prodacs.businessimplementation.production.NonProductionDetails;
import com.savantit.prodacs.businessimplementation.production.ProductionException;
import com.savantit.prodacs.facade.SessionNonProductionDetailsManager;
import com.savantit.prodacs.facade.SessionNonProductionDetailsManagerHome;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.util.EJBLocator;

/**
 * @author sponnusamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class NonProductionEditAction extends Action 
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		String action = "failure";
		String[] arEmpDetails;
		String[] arEmpPrsnlDet;
		
		/*Creating an instance of ProductionEditForm.java*/
		if (form instanceof NonProductionEditForm)
		{
			NonProductionEditForm frm = (NonProductionEditForm) form;
			
			EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
			ActionErrors errors = new ActionErrors();

			try
			{
				/* 	Setting the JNDI name and Environment 	*/
			   	obj.setJndiName("SessionNonProductionDetailsManagerBean");
		   		obj.setEnvironment();
		
				/* 	Creating the Home and Remote Objects 	*/
				SessionNonProductionDetailsManagerHome nonProHomeObj = (SessionNonProductionDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionNonProductionDetailsManagerHome.class);
				SessionNonProductionDetailsManager nonProductionObj = (SessionNonProductionDetailsManager)PortableRemoteObject.narrow(nonProHomeObj.create(),SessionNonProductionDetailsManager.class);
				
				if (frm.getFormAction().equalsIgnoreCase("view"))
				{
					action = "view";
				}
				else if (frm.getFormAction().equalsIgnoreCase("nonprodnview"))
				{
					action = "nonprodnview";
					if (BuildConfig.DMODE)
					{
						System.out.println("-->"+action);
					}
				}
				else if (frm.getFormAction().equalsIgnoreCase("searchNonProd"))
				{
					action = "searchNonProd";
					if (BuildConfig.DMODE)
					{
						System.out.println("-->"+action);
					}
				}
				else if (frm.getFormAction().equalsIgnoreCase("posting"))
				{
					ActionMessages messages = new ActionMessages();

					if (BuildConfig.DMODE)
						System.out.println("Prod Id :"+frm.getId());
					Vector vecNonProd = new Vector();
					vecNonProd.add(new Integer(frm.getId()));
					HashMap postProdDet = new HashMap();
					try
					{
						postProdDet = nonProductionObj.postNonProductionDetails(vecNonProd);	
					}catch (ProductionException e)
					{
						if (BuildConfig.DMODE)
						{
							System.out.println("Problem in NonProduction Posting.");
						}
					}
					
					int posted = 0;
					int notPosted = 0;
					int problm = 0;
					for (Iterator it=postProdDet.entrySet().iterator(); it.hasNext(); ) 
					{ 
						Map.Entry entry = (Map.Entry)it.next(); 
						int value = ((Integer)entry.getValue()).intValue();
						if(value == 0)
						{
							posted++;
						}
						else if(value == 1)
						{
							notPosted++;
						}
						else if(value == 2)
						{
							problm++;
						}
					}
					if(posted <= 1)
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.posted", Integer.toString(posted),"Non Production Entry"));
					else if(posted > 1)					
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.posted", Integer.toString(posted),"Non Production Entries"));					
					if(notPosted == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notPosted),"Non Production Entry"));						
					}
					else if(notPosted > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notPosted),"Non Production Entries"));
					}
					
					if(problm == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(problm),"Non Production Entry"));						
					}
					else if(problm > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(problm),"Non Production Entries"));
					}
					if(postProdDet.size() < vecNonProd.size())
					{
						int diff = vecNonProd.size() - postProdDet.size();
						if(diff==1)
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general"));
						else
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general"));
					}									
					if(!errors.isEmpty())
						saveErrors(request,errors);					
					saveMessages(request,messages);		

					action = "searchNonProd";
				}
				else if (frm.getFormAction().equalsIgnoreCase("modify"))
				{
					if (BuildConfig.DMODE)
					{
						System.out.println("Modify Starts...");
					}
					try
					{
						nonProductionObj.isModifyForNprod(frm.getId());
						if (BuildConfig.DMODE)
						{
							System.out.println("Posting Succeed.");
						}
					}
					catch (ProductionException e)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.name",e.getExceptionMessage()));
						if(!errors.isEmpty())
							saveErrors(request,errors);
						if (BuildConfig.DMODE)
						{
							e.printStackTrace();
							System.out.println("Posting Failure.");
						}
						action = "success";
					}
					
						/* Object to NonProductionDetails */
						NonProductionDetails objNonProductionDetails = new NonProductionDetails();
						
						objNonProductionDetails = nonProductionObj.getNonProductionDetails(frm.getId());
						
						frm.setProDate(objNonProductionDetails.getNonProdnCrntDate().toString().substring(0,10));
						frm.setProShift(objNonProductionDetails.getShiftId()+"");
						frm.setProMachine(objNonProductionDetails.getMcCode());
						frm.setNonProIdleRewrk((objNonProductionDetails.getIdlOrBkDown().equalsIgnoreCase("Idle"))?"0":"1");
						frm.setIdleBrkDwnRsn(objNonProductionDetails.getRsnId()+"");
						frm.setProTotalHours(objNonProductionDetails.getNprodTotHrs()+"");
						if (BuildConfig.DMODE)
						{
							System.out.println("1. "+objNonProductionDetails.getNonProdnCrntDate());
							System.out.println("2. "+objNonProductionDetails.getShiftId());
							System.out.println("3. "+objNonProductionDetails.getMcCode());
							System.out.println("4. "+objNonProductionDetails.getIdlOrBkDown());
							System.out.println("5. "+objNonProductionDetails.getIdlOrBrkDwnRsn());
							System.out.println("6. "+objNonProductionDetails.getNprodTotHrs());
						}
						Vector vec_empDet = objNonProductionDetails.getNonprodnEmpHrsDetails();
						String empDet = "";
						for (int i = 0 ; i < vec_empDet.size(); i++)
						{
							EmployeeDtyOtDetails objEmpDet = new EmployeeDtyOtDetails();
							objEmpDet = (EmployeeDtyOtDetails) vec_empDet.get(i);
							
							if (i != 0)
							{
								empDet = empDet + "^";
							}
							if (i == 0)
							{
								empDet = empDet + "" + objEmpDet.getEmpType();
								empDet = empDet + "-" + objEmpDet.getEmpName();
								empDet = empDet + "-" + objEmpDet.getDutyHrs();
								empDet = empDet + "-" + objEmpDet.getOtHrs();
								empDet = empDet + "-" + objEmpDet.getEmpTypdId();
								empDet = empDet + "-" + objEmpDet.getEmpId();
							}
							else
							{
								empDet = empDet + "" + objEmpDet.getEmpType();
								empDet = empDet + "-" + objEmpDet.getEmpName();
								empDet = empDet + "-" + objEmpDet.getDutyHrs();
								empDet = empDet + "-" + objEmpDet.getOtHrs();
								empDet = empDet + "-" + objEmpDet.getEmpTypdId();
								empDet = empDet + "-" + objEmpDet.getEmpId();
							}
						}
						frm.setHidEmpDet(empDet);
						if (BuildConfig.DMODE)
						{
							System.out.println("EmpDet1: "+frm.getHidEmpDet());
						}
				}
				else if (frm.getFormAction().equalsIgnoreCase("update"))
				{
					if (BuildConfig.DMODE)
					{
						System.out.println("Update......");
					}

					/* Object to Non-ProductionDetails */
					NonProductionDetails objNonProductionDetails = new NonProductionDetails();
					
					objNonProductionDetails.setNonProdId(frm.getId());
					if (BuildConfig.DMODE)
					{
						System.out.println("ProId: "+objNonProductionDetails.getNonProdId());
					}
						
					/* Date Conversion for Non-Production Current Date */
					StringTokenizer st = new StringTokenizer(frm.getProDate(),"-");
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
					objNonProductionDetails.setNonProdnCrntDate(ge.getTime());
					if (BuildConfig.DMODE)
					{
						System.out.println("NonPro Date: "+ objNonProductionDetails.getNonProdnCrntDate());
					}
					objNonProductionDetails.setMcCode(frm.getProMachine());
					objNonProductionDetails.setShiftId(Integer.parseInt(frm.getProShift()));
					objNonProductionDetails.setRsnId(Integer.parseInt(frm.getIdleBrkDwnRsn()));
					objNonProductionDetails.setNprodTotHrs(Float.parseFloat(frm.getProTotalHours()));
					objNonProductionDetails.setIdlOrBkDown((frm.getNonProIdleRewrk().equalsIgnoreCase("0"))?"Idle":"BreakDown");
					if (BuildConfig.DMODE)
					{
						System.out.println("Idle/BreakDown: "+objNonProductionDetails.getIdlOrBkDown());
					}
						
					/* Splitting all Details of Employees */
					StringTokenizer stEmpDet = new StringTokenizer(frm.getHidAllEmpDet(),"^");
					arEmpDetails = new String[stEmpDet.countTokens()];
					int recCount = 0;
					while (stEmpDet.hasMoreTokens())
					{
						arEmpDetails[recCount] = stEmpDet.nextToken();
						recCount++;
					}

					Vector vec_empDutyOtDet = new Vector();
					for (int i = 0; i < arEmpDetails.length; i++)
					{
						EmployeeDtyOtDetails objEmployeeDtyOtDetails = new EmployeeDtyOtDetails();
						StringTokenizer stEmpPrsnlDet = new StringTokenizer(arEmpDetails[i],"-");
						arEmpPrsnlDet = new String[stEmpPrsnlDet.countTokens()];
						recCount = 0;
						while (stEmpPrsnlDet.hasMoreTokens())
						{
							arEmpPrsnlDet[recCount] = stEmpPrsnlDet.nextToken();
							System.out.println("arEmpPrsnlDet["+recCount+"] :"+arEmpPrsnlDet[recCount]);
							recCount++;
						}
						if (BuildConfig.DMODE)
						{
							System.out.println("Emp-Rec-Fiels: "+arEmpPrsnlDet.length);
						}
						objEmployeeDtyOtDetails.setEmpType(arEmpPrsnlDet[0]);
						objEmployeeDtyOtDetails.setEmpTypdId(Integer.parseInt(arEmpPrsnlDet[1]));
						objEmployeeDtyOtDetails.setEmpName(arEmpPrsnlDet[2]);
						objEmployeeDtyOtDetails.setEmpId(Integer.parseInt(arEmpPrsnlDet[3]));
						objEmployeeDtyOtDetails.setDutyHrs(Float.parseFloat(arEmpPrsnlDet[4]));
						objEmployeeDtyOtDetails.setOtHrs(Float.parseFloat(arEmpPrsnlDet[5]));
						if (BuildConfig.DMODE)
						{
							System.out.println("--> "+arEmpPrsnlDet[0]+"&"+arEmpPrsnlDet[3]+"&"+arEmpPrsnlDet[4]+"&"+arEmpPrsnlDet[5]);
						}
	
						vec_empDutyOtDet.add(objEmployeeDtyOtDetails);	
					}
					objNonProductionDetails.setModifiedBy(frm.getHidUserId());
					objNonProductionDetails.setNonprodnEmpHrsDetails(vec_empDutyOtDet);
					if (BuildConfig.DMODE)
					{
						System.out.println("Before Update.");
					}
					boolean update = nonProductionObj.updateNonProductionDetails(objNonProductionDetails);
					if (BuildConfig.DMODE)
					{
						System.out.println("After Update.");
					}
					if (update == true)
					{
						if (BuildConfig.DMODE)
						{
							System.out.println("Excep: 1 update True");
						}
						ActionMessages message = new ActionMessages();
						message.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.update","Non-Production"));
						
						saveMessages(request,message);
						action="success";
					}
					else
					{
						if (BuildConfig.DMODE)
						{
							System.out.println("Excep: 2 Update False");
						}
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general"));
						if(!errors.isEmpty())
							saveErrors(request,errors);
						action="failure";				  			
					}
				}
				else if (frm.getFormAction().equalsIgnoreCase("makeInvalid"))
				{
					if (BuildConfig.DMODE)
					{
						System.out.println("Make InValid");
					}
					
					String ids[] = frm.getIds();
					Vector v = new Vector();
					for (int i = 0; i < ids.length; i++)
					{
						v.add(new Integer(ids[i]));
						if (BuildConfig.DMODE)
						{
							System.out.println("Invalid Ids: "+ ids[i]);
						}
					}
					HashMap hm = new HashMap();
					try
					{
						hm = nonProductionObj.makeNonProductionInValid(v);
					}catch (Exception e)
					{
						if (BuildConfig.DMODE)
						{
							System.out.println("Problem in MakeInvalid");
						}
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
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.invalid", Integer.toString(success),"Non Production Entry"));
					else if(success>1)					
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.messages.invalid", Integer.toString(success),"Non Production Entries"));					
					if(notFound == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notFound),"Non Production Entry"));						
					}
					else if(notFound > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notFound),"Non Production Entries"));
					}
					
					if(inUse == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(inUse),"Non Production Entry"));						
					}
					else if(inUse > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(inUse),"Non Production Entries"));
					}
					if(locked == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.locked", Integer.toString(locked),"Non Production Entry"));						
					}
					else if(locked > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.locked", Integer.toString(locked),"Non Production Entries"));
					}
					if(general == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general", Integer.toString(general),"Non Production Entry"));						
					}
					else if(general > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.general", Integer.toString(general),"Non Production Entries"));
					}
					if(hm.size()<v.size())
					{
						int diff = v.size()-hm.size();
						if(diff==1)
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general", Integer.toString(diff),"Non Production Entry"));
						else
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general", Integer.toString(diff),"Non Production Entries"));
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
					}
					
					String ids[] = frm.getIds();
					Vector v = new Vector();
					for (int i = 0; i < ids.length; i++)
					{
						v.add(new Integer(ids[i]));
						if (BuildConfig.DMODE)
						{
							System.out.println("Valid Ids: "+ ids[i]);
						}
					}
					HashMap hm = new HashMap();
					try
					{
						hm = nonProductionObj.makeNonProductionValid(v);
					}catch (Exception e)
					{
						if (BuildConfig.DMODE)
						{
							System.out.println("Problem in MakeValid");
						}
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
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.valid", Integer.toString(success),"Non Production Entry"));
					else if(success>1)					
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.messages.valid", Integer.toString(success),"Non Production Entries"));					
					if(notFound == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notFound),"Non Production Entry"));						
					}
					else if(notFound > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notFound),"Non Production Entries"));
					}
					
					if(inUse == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(inUse),"Non Production Entry"));						
					}
					else if(inUse > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(inUse),"Non Production Entries"));
					}
					if(locked == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.locked", Integer.toString(locked),"Non Production Entry"));						
					}
					else if(locked > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.locked", Integer.toString(locked),"Non Production Entries"));
					}
					if(general == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general", Integer.toString(general),"Non Production Entry"));						
					}
					else if(general > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.general", Integer.toString(general),"Non Production Entries"));
					}
					if(hm.size()<v.size())
					{
						int diff = v.size()-hm.size();
						if(diff==1)
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general", Integer.toString(diff),"Non Production Entry"));
						else
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general", Integer.toString(diff),"Non Production Entries"));
					}									
					if(!errors.isEmpty())
						saveErrors(request,errors);					
					saveMessages(request,messages);		
					action = "success";
				}
			}
			catch (ProductionException e)
			{
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.name",e.getExceptionMessage()));
				if(!errors.isEmpty())
					saveErrors(request,errors);
				action="failure";				  			
			}
			catch (Exception e)
			{
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general"));
				if(!errors.isEmpty())
					saveErrors(request,errors);
				action="failure";
				if (BuildConfig.DMODE)
				{
					e.printStackTrace();
					System.out.println("Problem in NonProduction Edit Action.");
				}

			}
		}
		if (BuildConfig.DMODE)
		{
			System.out.println("Non Production Edit Action :"+action);
		}
		return mapping.findForward(action);
	}

}

/***
$Log: NonProductionEditAction.java,v $
Revision 1.16  2005/12/28 10:37:58  vkrishnamoorthy
Error message caught and thrown appropriately for Employee and Duty failures.

Revision 1.15  2005/09/28 11:43:43  vkrishnamoorthy
Error corrected for OT hrs validation.

Revision 1.14  2005/09/26 14:35:05  vkrishnamoorthy
EmpTypId sent along with employee details.

Revision 1.13  2005/08/29 12:14:43  vkrishnamoorthy
Ids for MakeValid and MakeInValid changed to Integer type.

Revision 1.12  2005/08/25 14:00:38  vkrishnamoorthy
Modified for posting details during view.

Revision 1.11  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.10  2005/05/30 12:34:25  vkrishnamoorthy
Modified as per log entries.

Revision 1.9  2005/05/16 09:08:21  vkrishnamoorthy
Modified as per isModify() method.

Revision 1.8  2005/05/16 07:22:54  vkrishnamoorthy
isModify() method called for validation.

Revision 1.7  2005/03/12 06:35:20  sponnusamy
According PostProduction Actions Changed.

Revision 1.6  2005/03/11 13:14:42  sponnusamy
NonProduction Errors completed.

Revision 1.5  2005/03/09 07:28:47  vkrishnamoorthy
Modified as per PostProduction entry viewing.

Revision 1.4  2005/02/11 11:14:17  sponnusamy
Warnings removed

Revision 1.3  2005/02/03 05:09:15  vkrishnamoorthy
Log added.

***/