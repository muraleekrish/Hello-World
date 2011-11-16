/*
 * Created on Jan 3, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.production;

import java.util.GregorianCalendar;
import java.util.HashMap;
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

import com.savantit.prodacs.businessimplementation.production.EmployeeDtyOtDetails;
import com.savantit.prodacs.businessimplementation.production.NonProductionDetails;
import com.savantit.prodacs.businessimplementation.production.ProductionAccountingDateDetails;
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
public class NonProductionAddAction extends Action 
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		String action = "failure";
		String[] arEmpDetails;
		String[] arEmpPrsnlDet;
		String[] arNonProdDet;
		String[] arNonProdPrnlDet;
		if (form instanceof NonProductionAddForm)
		{
			NonProductionAddForm frm = (NonProductionAddForm) form;
			
			EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
			ActionErrors errors = new ActionErrors();

			try
			{
				if (BuildConfig.DMODE)
				{
					System.out.println("Non-ProductionAddAction Starts.");
				}
				
				/* 	Setting the JNDI name and Environment 	*/
			   	obj.setJndiName("SessionNonProductionDetailsManagerBean");
		   		obj.setEnvironment();
		
				/* 	Creating the Home and Remote Objects 	*/
				SessionNonProductionDetailsManagerHome nonProHomeObj = (SessionNonProductionDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionNonProductionDetailsManagerHome.class);
				SessionNonProductionDetailsManager nonProductionObj = (SessionNonProductionDetailsManager)PortableRemoteObject.narrow(nonProHomeObj.create(),SessionNonProductionDetailsManager.class);
				if (BuildConfig.DMODE)
				{
					System.out.println("frm.getFormAction(): "+ frm.getFormAction());
				}
				if (frm.getFormAction().equalsIgnoreCase("add"))
				{
					if (BuildConfig.DMODE)
					{
						System.out.println("Non Production Add");
						System.out.println("List: "+frm.getHidNonProdDet());
					}
					Vector vec_NonProdDet = new Vector();
					NonProductionDetails[] objNonProductionDetailsList = null;
					StringTokenizer stNonProdDet = new StringTokenizer(frm.getHidNonProdDet(),"$");
					arNonProdDet = new String[stNonProdDet.countTokens()];
					int count = 0;
					while (stNonProdDet.hasMoreTokens())
					{
						arNonProdDet[count] = stNonProdDet.nextToken();
						if (BuildConfig.DMODE)
						{
							System.out.println((count+1)+". "+ arNonProdDet[count]);
						}
						count++;
					}
					
					for (int i = 0; i < arNonProdDet.length; i++)
					{
						StringTokenizer stNonProdPrnlDet = new StringTokenizer(arNonProdDet[i],"#");
						arNonProdPrnlDet = new String[stNonProdPrnlDet.countTokens()];
						count = 0;
						while (stNonProdPrnlDet.hasMoreTokens())
						{
							arNonProdPrnlDet[count] = stNonProdPrnlDet.nextToken();
							if (BuildConfig.DMODE)
							{
								System.out.println(count+". "+ arNonProdPrnlDet[count]);
							}
							count++;
						}
						if (BuildConfig.DMODE)
						{
							System.out.println("Length1: "+arNonProdPrnlDet.length);
						}
						/* Object to Non-ProductionDetails */
						NonProductionDetails objNonProductionDetails = new NonProductionDetails();
						
						/* Date Conversion for Non-Production Current Date */ 
						StringTokenizer st = new StringTokenizer(arNonProdPrnlDet[1],"-");
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
						objNonProductionDetails.setShiftName(arNonProdPrnlDet[2]);
						objNonProductionDetails.setMcCode(arNonProdPrnlDet[3]);
						objNonProductionDetails.setIdlOrBkDown(arNonProdPrnlDet[4]);
						objNonProductionDetails.setRsn(arNonProdPrnlDet[5]);
						objNonProductionDetails.setNprodTotHrs(Float.parseFloat(arNonProdPrnlDet[6]));
						objNonProductionDetails.setShiftId(Integer.parseInt(arNonProdPrnlDet[7].trim()));
						objNonProductionDetails.setRsnId(Integer.parseInt(arNonProdPrnlDet[8].trim()));
						
						/* Employee Details */
						StringTokenizer stEmpDetails = new StringTokenizer(arNonProdPrnlDet[9],"^");
						arEmpDetails = new String[stEmpDetails.countTokens()];
						count = 0;
						while (stEmpDetails.hasMoreTokens())
						{
							arEmpDetails[count] = stEmpDetails.nextToken();
							if (BuildConfig.DMODE)
							{
								System.out.println(count+". "+arEmpDetails[count]);
							}
							count++;
						}
						if (BuildConfig.DMODE)
						{
							System.out.println("Length2: "+arEmpDetails.length);
						}
						Vector vec_empDutyOtDet = new Vector();
						for (int j = 0; j < arEmpDetails.length; j++)
						{
							StringTokenizer stEmpPrsnlDet = new StringTokenizer(arEmpDetails[j],"-");
							arEmpPrsnlDet = new String[stEmpPrsnlDet.countTokens()];
							count = 0;
							while (stEmpPrsnlDet.hasMoreTokens())
							{
								arEmpPrsnlDet[count] = stEmpPrsnlDet.nextToken();
								if (BuildConfig.DMODE)
								{
									System.out.println(count+". "+arEmpPrsnlDet[count]);
								}
								count++;
							}
							/*Object to Employee Duty Ot Details*/
							EmployeeDtyOtDetails objEmployeeDtyOtDetails = new EmployeeDtyOtDetails();
							
							objEmployeeDtyOtDetails.setEmpType(arEmpPrsnlDet[0]);
							objEmployeeDtyOtDetails.setEmpName(arEmpPrsnlDet[1]);
							objEmployeeDtyOtDetails.setDutyHrs(Float.parseFloat(arEmpPrsnlDet[2].trim()));
							objEmployeeDtyOtDetails.setOtHrs(Float.parseFloat(arEmpPrsnlDet[3].trim()));
							objEmployeeDtyOtDetails.setEmpTypdId(Integer.parseInt(arEmpPrsnlDet[4].trim()));
							objEmployeeDtyOtDetails.setEmpId(Integer.parseInt(arEmpPrsnlDet[5].trim()));

							vec_empDutyOtDet.add(objEmployeeDtyOtDetails);	
						}
						objNonProductionDetails.setCreatedBy(frm.getHidUserId());
						objNonProductionDetails.setNonprodnEmpHrsDetails(vec_empDutyOtDet);
						vec_NonProdDet.add(objNonProductionDetails);
						if (BuildConfig.DMODE)
						{
							System.out.println("Date: "+ objNonProductionDetails.getNonProdnCrntDate());
							System.out.println("SName: "+ objNonProductionDetails.getShiftName());
							System.out.println("Mcode: "+ objNonProductionDetails.getMcCode());
							System.out.println("Idle/BrkDwn: "+ objNonProductionDetails.getIdlOrBkDown());
							System.out.println("RsnName: "+ objNonProductionDetails.getRsn());
							System.out.println("TotHrs: "+ objNonProductionDetails.getNprodTotHrs());
							System.out.println("SId: "+ objNonProductionDetails.getShiftId());
							System.out.println("RId: "+ objNonProductionDetails.getRsnId());
							System.out.println("EmpSize: "+ vec_empDutyOtDet.size());
						}
					}
					
					objNonProductionDetailsList = new NonProductionDetails[vec_NonProdDet.size()];
					vec_NonProdDet.copyInto(objNonProductionDetailsList);
					
					HashMap hm_NonProdResult = nonProductionObj.addNewNonProductionDetails(objNonProductionDetailsList);
					request.setAttribute("nonprodresult",hm_NonProdResult);
				}
				else if (frm.getFormAction().equalsIgnoreCase("post"))
				{
					if (BuildConfig.DMODE)
					{
						System.out.println("Posting Starts...");
					}
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
					try
					{
						ProductionAccountingDateDetails prodAccntngDet = (ProductionAccountingDateDetails)nonProductionObj.isPosted(frm.getProMachine(),ge.getTime(),Integer.parseInt(frm.getProShift()));
						Vector prodAccntngDets = prodAccntngDet.getVecProductionAccountingMachineDetails();
						String date = prodAccntngDet.getProdDate();
						
						int postRsnSize = prodAccntngDets.size();
						if (postRsnSize == 0)
						{
							frm.setShowCount("1");
						}
						else
						{
							request.setAttribute("date",date);
							request.setAttribute("prodAccntngDets",prodAccntngDets);
							action = "nProdAccountings";
							frm.setFormAction("nProdAccountings");
							request.setAttribute("formAction",action);
						}
						if (BuildConfig.DMODE)
						{
							System.out.println("Posting Decision: "+postRsnSize);
						}
					}
					catch (ProductionException e)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.name",e.getExceptionMessage()));
						if (BuildConfig.DMODE)
						{
							System.out.println("ProdException");
						}
						if(!errors.isEmpty())
							saveErrors(request,errors);
						frm.setShowCount("");
						action = "failure";
					}
					catch(Exception e)
					{
						e.printStackTrace();
						action = "failure";
					}
				}
			}
			catch (Exception e)
			{
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general"));
				if(!errors.isEmpty())
					saveErrors(request,errors);
				action="failure";
				if (BuildConfig.DMODE)
				{
					System.out.println("Problem in Non-Production add Action.");
					e.printStackTrace();
				}
			}
			if (BuildConfig.DMODE)
			{
				System.out.println("Final Form Action: "+ frm.getFormAction()+" & Action: "+ action);
			}
		}
		return mapping.findForward(action);
	}
}

/***
$Log: NonProductionAddAction.java,v $
Revision 1.11  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.10  2005/05/31 14:07:52  vkrishnamoorthy
Modified as per Production Accounting Details.

Revision 1.9  2005/05/30 12:34:25  vkrishnamoorthy
Modified as per log entries.

Revision 1.8  2005/05/26 13:46:51  vkrishnamoorthy
printStackTrace() added.

Revision 1.7  2005/05/20 09:35:14  vkrishnamoorthy
Modified as per Production Accountings.

Revision 1.6  2005/03/09 11:01:33  sponnusamy
NonProduction Buffer Added

Revision 1.5  2005/02/11 11:14:17  sponnusamy
Warnings removed

Revision 1.4  2005/02/03 05:09:15  vkrishnamoorthy
Log added.

***/