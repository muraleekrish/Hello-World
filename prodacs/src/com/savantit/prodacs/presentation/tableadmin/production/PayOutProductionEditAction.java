/*
 * Created on Dec 31, 2004
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
import com.savantit.prodacs.businessimplementation.production.PopDetails;
import com.savantit.prodacs.businessimplementation.production.ProductionException;
import com.savantit.prodacs.facade.SessionPopDetailsManager;
import com.savantit.prodacs.facade.SessionPopDetailsManagerHome;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.util.EJBLocator;

/**
 * @author vkrishnamoorthy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PayOutProductionEditAction extends Action 
{
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		String action="failure";
		if (form instanceof PayOutProductionEditForm)
		{
			/* Creating a instance to POP Edit forms */
			PayOutProductionEditForm frm=(PayOutProductionEditForm) form;
			
			String[] arEmpDet;
			String[] arEmpPrnlDet;
			EJBLocator obj = new EJBLocator();
			ActionErrors errors = new ActionErrors();
			try
			{
				/* 	Setting the JNDI name and Environment 	*/
			   	obj.setJndiName("SessionPopDetailsManagerBean");
		   		obj.setEnvironment();

				/* 	Creating the Home and Remote Objects 	*/
				SessionPopDetailsManagerHome popHomeObj = (SessionPopDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionPopDetailsManagerHome.class);
				SessionPopDetailsManager popObj = (SessionPopDetailsManager)PortableRemoteObject.narrow(popHomeObj.create(),SessionPopDetailsManager.class);

				if (frm.getFormAction().equalsIgnoreCase("view"))
				{
					action = "view";
				}
				else if (frm.getFormAction().equalsIgnoreCase("popview"))
				{
					action = "popview";
					if (BuildConfig.DMODE)
		            {
						System.out.println("-->"+action);
		            }
				}
				else if (frm.getFormAction().equalsIgnoreCase("searchPop"))
				{
					action = "searchPop";
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
					Vector vecPop = new Vector();
					vecPop.add(new Integer(frm.getId()));
					HashMap postProdDet = new HashMap();
					try
					{
						postProdDet = popObj.postPopDetails(vecPop);	
					}catch (ProductionException e)
					{
						if (BuildConfig.DMODE)
						{
							System.out.println("Problem in POP Posting.");
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
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.posted", Integer.toString(posted),"Production Indirect Entry"));
					else if(posted > 1)					
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.posted", Integer.toString(posted),"Production Indirect Entries"));					
					if(notPosted == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notPosted),"Production Indirect Entry"));						
					}
					else if(notPosted > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notPosted),"Production Indirect Entries"));
					}
					
					if(problm == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(problm),"Production Indirect Entry"));						
					}
					else if(problm > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(problm),"Production Indirect Entries"));
					}
					if(postProdDet.size() < vecPop.size())
					{
						int diff = vecPop.size() - postProdDet.size();
						if(diff==1)
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general"));
						else
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general"));
					}									
					if(!errors.isEmpty())
						saveErrors(request,errors);					
					saveMessages(request,messages);		

					action = "searchPop";
				}
				else if (frm.getFormAction().equalsIgnoreCase("modify"))
				{
					try
					{
						popObj.isModifyForPop(frm.getId());
						if (BuildConfig.DMODE)
			            {
							System.out.println("Posting Succeed.");
			            }
					}
					catch (ProductionException e)
					{
						//e.printStackTrace();
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.name",e.getExceptionMessage()));
						if(!errors.isEmpty())
							saveErrors(request,errors);
						if (BuildConfig.DMODE)
			            {
							System.out.println("Posting Failure.");
			            }
						action = "success";
					}
					if (BuildConfig.DMODE)
		            {
						System.out.println("Modification Starts...");
		            }
						
						/* Creating object to Details object */
						PopDetails objPopDetails = new PopDetails();
						
						objPopDetails = popObj.getPopDetails(frm.getId());
						frm.setPopFromDate(objPopDetails.getPopCrntDate().toString().substring(0,10));
						frm.setPopReason(objPopDetails.getPopRsn());
						//frm.setMachineCode(objPopDetails.getMcCode());
						frm.setMachineShift(objPopDetails.getShiftId()+"");
						
						Vector vec_empDet = objPopDetails.getPopEmpHrsDetails();
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
						if (BuildConfig.DMODE)
			            {
							System.out.println("EmpDet: "+empDet);
			            }
						frm.setHidEmpDetails(empDet);
				}
				else if (frm.getFormAction().equalsIgnoreCase("update"))
				{
					if (BuildConfig.DMODE)
		            {
						System.out.println("POP Update Starts...");
		            }
					
					/* Object to POPDetails */
					PopDetails objPopDetails = new PopDetails();
					
					/* Date Conversion for POP Current Date */
					StringTokenizer st = new StringTokenizer(frm.getPopFromDate(),"-");
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
					
					objPopDetails.setPopId(frm.getId());
					objPopDetails.setPopCrntDate(ge.getTime());
					//objPopDetails.setMcCode(frm.getMachineCode());
					objPopDetails.setShiftId(Integer.parseInt(frm.getMachineShift()));
					objPopDetails.setPopRsn(frm.getPopReason());
					if (BuildConfig.DMODE)
		            {					
						System.out.println("PopId: "+objPopDetails.getPopId());
						System.out.println("Shift: "+objPopDetails.getShiftId());
						System.out.println("Date: "+objPopDetails.getPopCrntDate());
						System.out.println("Rsn: "+objPopDetails.getPopRsn());
						System.out.println("EmpDet: "+frm.getHidEmpDet());
		            }
					StringTokenizer stEmpDet = new StringTokenizer(frm.getHidEmpDet(),"^");
					arEmpDet = new String[stEmpDet.countTokens()];
					int i = 0;
					while (stEmpDet.hasMoreTokens())
					{
						arEmpDet[i] = stEmpDet.nextToken();
						i++;
					}
					if (BuildConfig.DMODE)
		            {
						System.out.println("ArEmpDet: "+arEmpDet.length);
		            }
					Vector vec_empDet = new Vector();
					for (i = 0; i < arEmpDet.length; i++)
					{
						/* Object to Employee Duty OT Details */
						EmployeeDtyOtDetails objEmployeeDtyOtDetails = new EmployeeDtyOtDetails();
						
						StringTokenizer stEmpPrnlDet = new StringTokenizer(arEmpDet[i],"-");
						arEmpPrnlDet = new String[stEmpPrnlDet.countTokens()];
						int count = 0; 
						while (stEmpPrnlDet.hasMoreTokens())
						{
							arEmpPrnlDet[count] = stEmpPrnlDet.nextToken();
							if (BuildConfig.DMODE)
				            {
								System.out.println(count+1 +". "+arEmpPrnlDet[count]);
				            }
							count++;
						}
						
						objEmployeeDtyOtDetails.setEmpId(Integer.parseInt(arEmpPrnlDet[5]));
						objEmployeeDtyOtDetails.setEmpTypdId(Integer.parseInt(arEmpPrnlDet[4]));
						objEmployeeDtyOtDetails.setEmpName(arEmpPrnlDet[1]);
						objEmployeeDtyOtDetails.setDutyHrs(Float.parseFloat(arEmpPrnlDet[2]));
						objEmployeeDtyOtDetails.setOtHrs(Float.parseFloat(arEmpPrnlDet[3]));
						if (BuildConfig.DMODE)
			            {
							System.out.println("... "+arEmpPrnlDet[0]+" "+arEmpPrnlDet[1]+" "+arEmpPrnlDet[2]+" "+arEmpPrnlDet[3]+" "+arEmpPrnlDet[4]);
			            }
						vec_empDet.add(objEmployeeDtyOtDetails);
					}
					objPopDetails.setModifiedBy(frm.getHidUserId());
					objPopDetails.setPopEmpHrsDetails(vec_empDet);
					
					boolean updated = popObj.updatePopDetails(objPopDetails);
					
					if (updated == true)
					{
						ActionMessages message = new ActionMessages();
						message.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.update","POP"));
						
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
					System.out.println("Problem in POP Edit Action.");
	            }
			}
		}
		if (BuildConfig.DMODE)
        {
			System.out.println("POP Edit Action :"+action);
        }
		return mapping.findForward(action);
	}
}

/***
$Log: PayOutProductionEditAction.java,v $
Revision 1.15  2005/12/28 11:15:20  vkrishnamoorthy
Error message caught and thrown appropriately for Employee and Duty failures.

Revision 1.14  2005/09/27 10:18:10  vkrishnamoorthy
EmpTypId sent along with employee details.

Revision 1.13  2005/08/25 14:00:38  vkrishnamoorthy
Modified for posting details during view.

Revision 1.12  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.11  2005/06/16 06:32:35  vkrishnamoorthy
Machine Code removed.

Revision 1.10  2005/05/30 13:23:26  vkrishnamoorthy
Modified as per log entries.

Revision 1.9  2005/05/16 09:18:38  vkrishnamoorthy
Modified as per isModify() method.

Revision 1.8  2005/05/16 07:22:54  vkrishnamoorthy
isModify() method called for validation.

Revision 1.7  2005/03/12 06:35:20  sponnusamy
According PostProduction Actions Changed.

Revision 1.6  2005/03/11 13:11:45  sponnusamy
Pop Production Completed.

Revision 1.5  2005/03/09 07:28:47  vkrishnamoorthy
Modified as per PostProduction entry viewing.

Revision 1.4  2005/02/03 05:09:15  vkrishnamoorthy
Log added.

***/