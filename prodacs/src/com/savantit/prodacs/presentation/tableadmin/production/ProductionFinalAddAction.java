/*
 * Created on Jul 15, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savantit.prodacs.presentation.tableadmin.production;

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

import com.savantit.prodacs.businessimplementation.production.EmployeeDtyOtDetails;
import com.savantit.prodacs.businessimplementation.production.FinalProductionDetails;
import com.savantit.prodacs.businessimplementation.production.ProductionJobDetails;
import com.savantit.prodacs.facade.SessionFinalProductionDetailsManager;
import com.savantit.prodacs.facade.SessionFinalProductionDetailsManagerHome;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.util.EJBLocator;

/**
 * @author vkrishnamoorthy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ProductionFinalAddAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		String action = "failure";
		String arEmpDetails[] = null;
		String arEmpPrsnlDet[] = null;
		
		/*Creating an instance of ProductionFinalAddForm.java*/
		if (form instanceof ProductionFinalAddForm)
		{
			ProductionFinalAddForm frm = (ProductionFinalAddForm) form;
			EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
			ActionErrors errors = new ActionErrors();

			try
			{
				/* 	Setting the JNDI name and Environment 	*/
			   	obj.setJndiName("SessionFinalProductionDetailsManager");
		   		obj.setEnvironment();

				/* 	Creating the Home and Remote Objects 	*/		   		
		   		SessionFinalProductionDetailsManagerHome objFinalProdHome = (SessionFinalProductionDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionFinalProductionDetailsManagerHome.class);
				SessionFinalProductionDetailsManager objProdFinal = (SessionFinalProductionDetailsManager)PortableRemoteObject.narrow(objFinalProdHome.create(),SessionFinalProductionDetailsManager.class);
				if (BuildConfig.DMODE)
				{
					System.out.println("FormAction :"+frm.getFormAction());
				}
				if (frm.getFormAction().equalsIgnoreCase("add"))
				{
					if (BuildConfig.DMODE)
					{
						System.out.println("Production Final Add Starts.");
						System.out.println("Details: "+ frm.getHidProdList());
					}

					/* All the Production Entries are Tokenized by $ */
					StringTokenizer stTotProdList = new StringTokenizer(frm.getHidProdList(),"$");
					String[] arTotProdList = new String[stTotProdList.countTokens()];
					int count = 0;
					while (stTotProdList.hasMoreTokens())
					{
						arTotProdList[count] = stTotProdList.nextToken();
						if (BuildConfig.DMODE)
						{
							System.out.println(count+". "+arTotProdList[count]);
						}
						count++;
					}
					if (BuildConfig.DMODE)
					{
						System.out.println("Length: "+arTotProdList.length);
					}

					/* Details object to ProductionDetails */
					FinalProductionDetails[] objFinalProductionDetailsList = null;
					Vector vec_ProdDetails = new Vector();
					for (int i = 0; i < arTotProdList.length; i++)
					{
						FinalProductionDetails objFinalProductionDetails = new FinalProductionDetails();
						ProductionJobDetails objProductionJobDetails = new ProductionJobDetails();

						/* Production Entries are Listed */
						StringTokenizer stProdList = new StringTokenizer(arTotProdList[i],"#");
						String[] arProdList = new String[stProdList.countTokens()];
						count = 0;
						while (stProdList.hasMoreTokens())
						{
							arProdList[count] = stProdList.nextToken();
							count++;
						}

						/* Production Current Date */
						StringTokenizer st = new StringTokenizer(arProdList[1].trim(),"-");
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
						objFinalProductionDetails.setFinalCrntDate(ge.getTime());
						objFinalProductionDetails.setShiftName(arProdList[2].trim());
						objFinalProductionDetails.setWoNo(arProdList[3]);

						objProductionJobDetails.setJobName(arProdList[4]);
						objFinalProductionDetails.setObjProductionJobDetails(objProductionJobDetails);

						objFinalProductionDetails.setWoJbId(Integer.parseInt(arProdList[10].trim()));
						objFinalProductionDetails.setFinalProdQtySnos(arProdList[11].trim());

						/* Calculate no. of Qty's */
						StringTokenizer stTotQty = new StringTokenizer(arProdList[10].trim(),",");
						objFinalProductionDetails.setFinalProdTotQty(stTotQty.countTokens());
						objFinalProductionDetails.setFinalProdStartOpn(Integer.parseInt(arProdList[5].trim()));
						objFinalProductionDetails.setFinalProdEndOpn(Integer.parseInt(arProdList[6].trim()));
						objFinalProductionDetails.setFinalProdTotHrs(Integer.parseInt(arProdList[7].trim()));
						objFinalProductionDetails.setShiftId(Integer.parseInt(arProdList[8].trim()));
						objFinalProductionDetails.setWoId(Integer.parseInt(arProdList[9].trim()));

						/* Splitting all Details of Employees */
						StringTokenizer stEmpDet = new StringTokenizer(arProdList[12],"^");
						arEmpDetails = new String[stEmpDet.countTokens()];
						int recCount = 0;
						while (stEmpDet.hasMoreTokens())
						{
							arEmpDetails[recCount] = stEmpDet.nextToken();
							recCount++;
						}

						Vector vec_empDutyOtDet = new Vector();
						for (int j = 0; j < arEmpDetails.length; j++)
						{
							/* Details object to EmployeeDtyOtDetails */
							EmployeeDtyOtDetails objEmployeeDtyOtDetails = new EmployeeDtyOtDetails();

							StringTokenizer stEmpPrsnlDet = new StringTokenizer(arEmpDetails[j],"-");
							
							while (stEmpPrsnlDet.hasMoreTokens())
							{
								objEmployeeDtyOtDetails.setEmpType(stEmpPrsnlDet.nextToken()); // Emp Typ Name
								objEmployeeDtyOtDetails.setEmpName(stEmpPrsnlDet.nextToken()); // Emp Name
								objEmployeeDtyOtDetails.setEmpTypdId(Integer.parseInt(stEmpPrsnlDet.nextToken().trim())); // EmpTyp ID
								objEmployeeDtyOtDetails.setEmpId(Integer.parseInt(stEmpPrsnlDet.nextToken().trim())); // Emp Id
	
								vec_empDutyOtDet.add(objEmployeeDtyOtDetails);
							}
							objFinalProductionDetails.setProdnEmpHrsDetails(vec_empDutyOtDet);
							if (BuildConfig.DMODE)
							{
								System.out.println("Emp.Size: "+ vec_empDutyOtDet.size());
							}
						}
						objFinalProductionDetails.setCreatedBy(frm.getHidUserId());
						vec_ProdDetails.add(objFinalProductionDetails);
					}
					objFinalProductionDetailsList = new FinalProductionDetails[vec_ProdDetails.size()];
					vec_ProdDetails.copyInto(objFinalProductionDetailsList);

					boolean added = objProdFinal.addNewFinalProductionDetails(objFinalProductionDetailsList);
					if (BuildConfig.DMODE)
					{
						System.out.println("Added :"+added);
					}

					if (added == true)
					{
						ActionMessages message = new ActionMessages();
						message.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.added","Final Production Entry",""));

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
			catch (Exception e)
			{
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general"));
				if(!errors.isEmpty())
					saveErrors(request,errors);
				action="failure";
				if (BuildConfig.DMODE)
				{
					System.out.println("Problem in ProductionFinal Add Action.");
					e.printStackTrace();
				}
			}
		}
		if (BuildConfig.DMODE)
		{
			System.out.println("ProductionFinal Add Action :"+action);
		}
		return mapping.findForward(action);
	}
}

/***
$Log: ProductionFinalAddAction.java,v $
Revision 1.3  2005/08/01 09:29:12  vkrishnamoorthy
Total Hours included.

Revision 1.2  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.1  2005/07/18 05:57:01  vkrishnamoorthy
Initial commit on ProDACS.

***/