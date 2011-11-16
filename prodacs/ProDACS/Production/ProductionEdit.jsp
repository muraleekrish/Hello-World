<%@ page language = "java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri = "/WEB-INF/struts-logic.tld" prefix = "logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<jsp:useBean id="frm" class="com.savantit.prodacs.presentation.tableadmin.production.ProductionEditForm" />
<jsp:setProperty name="frm" property="*" />

<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.StringTokenizer"%>
<%@ page import="java.util.GregorianCalendar"%>

<%@ page import="com.savantit.prodacs.facade.SessionProductionDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionProductionDetailsManager"%>
<%@ page import="com.savantit.prodacs.businessimplementation.production.ProductionDetails" %>
<%@ page import="com.savantit.prodacs.businessimplementation.production.ProductionJobDetails" %>
<%@ page import="com.savantit.prodacs.businessimplementation.production.EmployeeDtyOtDetails" %>
<%@ page import="com.savantit.prodacs.businessimplementation.securityadmin.UserAuthDetails"%>

<useradmin:userrights resource="1017"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("Production Edit.jsp Starts.");
	HashMap empDetails = new HashMap(); /* For Employee Details and Type Details */
	ProductionDetails objProductionDetails = new ProductionDetails();
	ProductionJobDetails objProductionJobDetails = new ProductionJobDetails();
	UserAuthDetails userDetails = (UserAuthDetails)session.getAttribute("##userRights##");
	String userName = userDetails.getUserId();
	try
	{
		EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
		
		/* 	Setting the JNDI name and Environment 	*/
	   	obj.setJndiName("SessionProductionDetailsManagerBean");
   		obj.setEnvironment();

		/* 	Creating the Home and Remote Objects 	*/
		SessionProductionDetailsManagerHome proHomeObj = (SessionProductionDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionProductionDetailsManagerHome.class);
		SessionProductionDetailsManager productionObj = (SessionProductionDetailsManager)PortableRemoteObject.narrow(proHomeObj.create(),SessionProductionDetailsManager.class);

		/* Object to Production Detals */
		objProductionDetails = productionObj.getProductionDetails(frm.getId());
		
		if (frm.getFormAction().equalsIgnoreCase("modify"))
		{
			frm.setHidWrkOrdId(objProductionDetails.getWoId()+"");
			frm.setHidWrkOrdJobId(objProductionDetails.getWoJbId()+"");
		}
		HashMap shiftDefnNameList = new HashMap();
		HashMap machineDet =  new HashMap();
		if (!frm.getLoadCount().equals("1"))
		{
		/* For Shift */
		shiftDefnNameList = productionObj.getShiftDefnNameList();
		pageContext.setAttribute("shiftDefnNameList",shiftDefnNameList);
		}
		if (frm.getLoadCount().equals("1"))
		{
			pageContext.setAttribute("machineDet",machineDet);
		}
		else
		{
		/* For Machine */
		machineDet = productionObj.getAllProdMachines();
		pageContext.setAttribute("machineDet",machineDet);
		}
		/* For Loading WorkOrder */
		HashMap workOrderDet = productionObj.getWorkOrderList();
		pageContext.setAttribute("workOrderDet",workOrderDet);
		
		/* Loading Job Details by giving WorkOrder Id */
		Vector vecJobDet = new Vector();
		if (BuildConfig.DMODE)
			System.out.println("Wo Id: "+frm.getHidWrkOrdId());
		if (frm.getHidWrkOrdId() != "0")
		{
			vecJobDet = productionObj.getProdnJobDetailsForUpdateByWorkOrder(Integer.parseInt(frm.getHidWrkOrdId()),frm.getId(),0,0,0,0);
			for (int i = 0; i < vecJobDet.size(); i++)
			{
				objProductionJobDetails = (ProductionJobDetails) vecJobDet.get(i);
			}
			pageContext.setAttribute("vecJobDet",vecJobDet);
		}
		else
		{
			pageContext.setAttribute("vecJobDet",vecJobDet);
		}
		
		/* Loading Job Operation Details by giving WorkOrderJobId */
		Vector vecJobOpnDet = new Vector();
		if (frm.getHidWrkOrdJobId() != "0")
		{
			vecJobOpnDet = productionObj.getProdnJobOperationDetailsForUpdate(Integer.parseInt(frm.getHidWrkOrdJobId()),frm.getId(),0);
			pageContext.setAttribute("vecJobOpnDet",vecJobOpnDet);
		}
		else
		{
			pageContext.setAttribute("vecJobOpnDet",vecJobOpnDet);
		}
		
		/* Loading EmployeeType and Employees */
		empDetails = productionObj.getAllEmployeesByTypes();
		
		/* Loading JobDetails for Checked JobName */
	 	objProductionJobDetails = (ProductionJobDetails)objProductionDetails.getObjProductionJobDetails();

		/* Finalizing the minimum required Quantities */
		HashMap minRqdEmp = productionObj.minReqdEmployees();
		Set set = minRqdEmp.entrySet();
		Iterator i = set.iterator();
		int c = 0; 
		String minDet = "";
		while (i.hasNext())
		{
			Map.Entry me = (Map.Entry) i.next();
			Vector vec = new Vector();
			if (c != 0)
			{
				minDet = minDet + "^";
			}
			if (c == 0)
			{
				minDet = me.getKey()+"";
				vec = (Vector)me.getValue();
				for (int k = 0; k < vec.size(); k++)
				{
					minDet = minDet + "-" + vec.get(k);
				}
			}
			else
			{
				minDet = minDet + "" + me.getKey();
				vec = (Vector)me.getValue();
				for (int k = 0; k < vec.size(); k++)
				{
					minDet = minDet + "-" + vec.get(k);
				}
			}
			c++;
		}
		frm.setMinQty(minDet);

		if (frm.getLoadCount().equals("1"))
		{
			if (!frm.getProDate().trim().equalsIgnoreCase(""))
			{
				StringTokenizer stDate = new StringTokenizer(frm.getProDate().trim(),"-");
				int year = 0;
				int mon = 0;
				int date = 0;
				if(stDate.hasMoreTokens())
				{
					year = Integer.parseInt(stDate.nextToken().trim());
				}
				if(stDate.hasMoreTokens())
				{
					mon = Integer.parseInt(stDate.nextToken().trim());
				}
				if(stDate.hasMoreTokens())
				{		
					date = Integer.parseInt(stDate.nextToken().trim());
				}
				GregorianCalendar gc = new GregorianCalendar(year,mon-1,date);
			
				shiftDefnNameList = productionObj.getShiftDefnNameList(gc.getTime());
				if (BuildConfig.DMODE)
					System.out.println("Shifts for Date(HM) :"+shiftDefnNameList);
				pageContext.setAttribute("shiftDefnNameList",shiftDefnNameList);
			}
			else
			{
				pageContext.setAttribute("shiftDefnNameList",shiftDefnNameList);
			}
		}
		if (frm.getLoadCount().equals("2") && frm.getFormAction().equals("modify1"))
		{
			if ((frm.getHidDate().equalsIgnoreCase(frm.getHidDate1())) && (frm.getHidSft().trim().equalsIgnoreCase(frm.getHidSft1())))
			{
				machineDet = productionObj.getAllProdMachines();
				if (BuildConfig.DMODE)
					System.out.println("Machines(HM):"+machineDet);
				pageContext.setAttribute("machineDet",machineDet);
			}
			else
			{
				StringTokenizer st = new StringTokenizer(frm.getProDate().trim(),"-");
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

				machineDet = productionObj.getAllProdMachines(ge.getTime(),Integer.parseInt(frm.getProShift()));
				pageContext.setAttribute("machineDet",machineDet);
			}
		}		

		/* For Loading Emp Details */
			Vector vec_empDet = objProductionDetails.getProdnEmpHrsDetails();
			String emp_details = "";
			for (int b = 0; b < vec_empDet.size(); b++)
			{
				EmployeeDtyOtDetails objEmpDtyOtDet = new EmployeeDtyOtDetails();
				objEmpDtyOtDet = (EmployeeDtyOtDetails) vec_empDet.get(b);
				
				if (b != 0)
				{
					emp_details = emp_details + "^";
				}
				emp_details = emp_details + "" + objEmpDtyOtDet.getEmpType();
				emp_details = emp_details + "-" + objEmpDtyOtDet.getEmpName();
				emp_details = emp_details + "-" + objEmpDtyOtDet.getDutyHrs();
				emp_details = emp_details + "-" + objEmpDtyOtDet.getOtHrs();
				emp_details = emp_details + "-" + objEmpDtyOtDet.getEmpTypdId();
				emp_details = emp_details + "-" + objEmpDtyOtDet.getEmpId();
			}
			
			frm.setHidAllEmpDet(emp_details);
	}
	catch (Exception e)
	{
		if (BuildConfig.DMODE)
			System.out.println("Problem in Production Edit.Jsp");
	}
%>

<html:html>
<head>
<title><bean:message key="prodacs.production.header"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script src='<bean:message key="context"/>/library/jscripts.js'></script>
<script src='<bean:message key="context"/>/library/calendar_previous_submit.js'></script>
<script>
	var oPopup;
	var flg = 0;
	var temFlg = true;

	function init()
	 {
		oPopup = window.createPopup();
	 }

	 function dateSubmit()
	 {
		 var temp = document.forms[0];
		 temp.formAction.value = "dateSubmit";
		 temp.loadCount.value = "1";
		 //alert(temp.formAction.value);
		 temp.submit();
	 }
	 
	 function loadMachines()
	 {
		 var temp = document.forms[0];
		 if (temp.proShift.value != "0" && temp.formAction.value == "modify")
		 {
			 temp.showCount.value = "1";
			 temp.loadCount.value = "2";
			 temp.formAction.value = "modify1";
			 temp.submit();
		 }
		 else if (temp.proShift.value != "0")
		 {
			 temp.formAction.value = "modify1";
			 temp.loadCount.value = "2";
	 		 if (temp.loadCount.value == 2)
			 {
				temp.hidDate1.value = temp.proDate.value;
				temp.hidSft1.value = temp.proShift.value;
			 }
			 temp.submit();
		 }
	 }

	 function clearAll()
	 {
		 var temp = document.forms[0];
		 temp.formAction.value = "";
		 temp.proShift.options[temp.proShift.selectedIndex].value = 0;
		 temp.proMachine.options[temp.proMachine.selectedIndex].value = 0;
	 }
	 
	function listItem()
	{
		var temp = document.forms[0];
		if ((temp.fromDate.value != "" ) || (temp.tDate.value != "") || (temp.woNo.value != "") || (temp.jbName.value != "") || (temp.jbCombo.value != "") || (temp.mcCde.value != "") || (temp.sftName.value != "") || (temp.viewAll.value != "") || (temp.postedView.value != "") || (temp.woSelect.value != "") || (temp.maxItem.value != "") || (temp.empName.value != "") || (temp.empNameCombo.value !="") || (temp.pageNo.value !=""))
		{
			temp.action = '<bean:message key="context"/>/Production/ProductionList.jsp?formAction=afterModify';
			temp.submit();
		}
		else
		{
			temp.action = '<bean:message key="context"/>/Production/ProductionList.jsp';
			temp.submit();
		}
	}

	function addItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/Production/ProductionAdd.jsp';
		document.forms[0].submit();
	}

	/* Numbers and Period are Allowed */
	function isNumber()
	{
		if (((event.keyCode > 47) && (event.keyCode < 58)) || (event.keyCode == 46))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/* Numbers only Allowed */
	function isNumber1()
	{
		if ((event.keyCode > 47) && (event.keyCode < 58))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/* Check or Uncheck all the Records */
	function chkAll()
	{
		var temp = document.forms[0];
		var obj = document.getElementById("tblJobOperDet");
		if (obj.children(0).children.length > 2)
		{
			if (temp.checkbox2.checked)		
			{
				for (var i = 0; i < temp.CheckValue.length; i++)
				{
					temp.CheckValue[i].checked = true;
				}
			}
			else
			{
				for (var i = 0; i < temp.CheckValue.length; i++)
				{
					temp.CheckValue[i].checked = false;
				}
			}
		}
		else if (obj.children(0).children.length == 2)
		{
			if (temp.checkbox2.checked)
			{
				temp.CheckValue.checked = true;
			}
			else
			{
				temp.CheckValue.checked = false;
			}
		}
	}

	/* To check all the Checkbox in Employee Details */
	function headCkAll() 
	{
		var temp = document.forms[0];
		var obj = document.getElementById("tblEmpDetails");
		if (obj.children.length > 1)
		{
			if (temp.ckAll.checked)
			{
				for (var i = 0; i < temp.ckdCkBx.length; i++)
				{
					temp.ckdCkBx[i].checked = true;
				}
			}
			else
			{
				for (var i = 0; i < temp.ckdCkBx.length; i++)
				{
					temp.ckdCkBx[i].checked = false;
				}
			}
		}
		else if (temp.ckAll.checked)
		{
			temp.ckdCkBx.checked = true;
		}
		else
		{
			temp.ckdCkBx.checked = false;
		}
	}

	/* This is for showing the Date, Shift, Machine Name and indicate the invalid Things */
	function hideNshowDateShiftMachine()
	{
		var temp = document.forms[0];
		var obj = document.getElementById("tblProdShft");

		/* For Displaying the Production Date else indimate a Alert */
		if (temp.proDate.value != "")
		{
			obj.children(2).innerText = temp.proDate.value;
		}
		else
		{
			alert ("Please Select a Production Date!");
			prodShftRow.style.display='none'; 
			prodInfo.style.display='block';
			return false;
		}

		/* For Displaying the Shift Name else indimate a Alert */
		if (temp.proShift.options[temp.proShift.selectedIndex].value != "0")
		{
			obj.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;
		}
		else
		{
			alert ("Please Select a Shift Name for Production!");
			prodShftRow.style.display='none'; 
			prodInfo.style.display='block';
			return false;
		}

		/* For Displaying the Machine name else indicate a Alert */
		if (temp.proMachine.options[temp.proMachine.selectedIndex].value != "0")
		{
			obj.children(8).innerText = temp.proMachine.options[temp.proMachine.selectedIndex].text;
		}
		else
		{
			alert ("Please Select a Machine Name for Production! ");
			prodShftRow.style.display='none';
			prodInfo.style.display='block';
			return false;
		}
		
		/* All the Details are correct then Show the Next Job Details */
		prodShftRow.style.display='block';
		prodInfo.style.display='block';
		temp.showCount.value = "1";
		temp.formAction.value = "post";
		temp.submit();
	}

	/* Loading Job Details by WorkOrderId */
	function loadJobDetByWOId(code)
	{
		var temp = document.forms[0];
		if (code != "0")
		{
			temp.hidWrkOrdId.value = code;
			temp.proTotQtySnos.value = "";
			temp.formAction.value = "getJobDetails";
			temp.showCount.value = "2";
			temp.submit();
		}
		else
		{
			alert ("Select a WorkOrder to View its Jobs!");
			return false;
		}
	}
	
	/* Showing the tables according to Entries in the Production Add Screen by Onload event*/
	function showJobDet()
	{
		var temp = document.forms[0];
		//alert (temp.formAction.value);
		//alert ("showCount :"+temp.showCount.value);
		//alert(temp.loadCount.value);
		temp.hidUserId.value = '<%= userName %>';
		if (temp.loadCount.value == "")
		{
			temp.hidDate.value = temp.proDate.value;
			temp.hidSft.value = temp.proShift.value;
		}

		var obj = document.getElementById("tblWorkOrdInfo");
		for (var i = 1; i < obj.children(0).children.length; i++)
		{
			if (obj.children(0).children(i).children(0).children(0).checked)
			{
				temp.hidChkdJobName.value = obj.children(0).children(i).children(1).children(0).value;
			}
		}

		if ((temp.showCount.value == "") && (temp.formAction.value != "modify"))
		{
			prodInfo.style.display='block';
			prodShftRow.style.display='none';
			prodImgRow.style.display = 'none';
			wrkOrder.style.display='none';
			tblJobQty.style.display = 'none';
			tblEmp.style.display = 'none';
			tblEmpDets.style.display = 'none';
		}
		else if (temp.showCount.value == "1")
		{
			prodInfo.style.display='block';
			prodShftRow.style.display='block';
			prodImgRow.style.display = 'block';
			wrkOrder.style.display='none';
			tblJobQty.style.display = 'none';
			tblEmp.style.display = 'none';
			tblEmpDets.style.display = 'none';

			/* Validation for updating the Date, Shift and Machine Names */
			var obj = document.getElementById("tblProdShft");
			obj.children(2).innerText = temp.proDate.value;
			obj.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;
			obj.children(8).innerText = temp.proMachine.options[temp.proMachine.selectedIndex].text;

			if (temp.formAction.value == "post")
			{
				temp.proWorkOrderHash.value = "0";
				tblWorkOrdInfo.style.display = 'none';
			}

			/* Select the Checked Radio Button */
			if (temp.rdWrkOrd.length != undefined)
			{
				temp.rdWrkOrd[temp.hidChkdJobId.value].checked = true;
			}
			else
			{
				temp.rdWrkOrd.checked = true;
			}
			if ((temp.proNmlorRwk[0].checked) || (temp.proNmlorRwk[1].checked))
			{
				return false;
			}
			else
			{
				temp.proNmlorRwk[0].checked = true;
			}
		}
		else if (temp.showCount.value == "2")
		{
			prodShftRow.style.display='block';
			prodInfo.style.display = 'block';
			wrkOrder.style.display='none';
			tblJobQty.style.display = 'none';
			tblEmp.style.display = 'none';
			tblEmpDets.style.display = 'none';
			
			/* Validation for updating Date, Shift, Machine Name, WorkOrderNo and Job name */
			var obj = document.getElementById("tblProdShft");
			obj.children(2).innerText = temp.proDate.value;
			obj.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;
			obj.children(8).innerText = temp.proMachine.options[temp.proMachine.selectedIndex].text;

			var obj1 = document.getElementById("tblProdShft2");
			obj1.children(2).innerText = temp.proDate.value;
			obj1.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;
			obj1.children(8).innerText = temp.proMachine.options[temp.proMachine.selectedIndex].text;
			
			var obj2 = document.getElementById("tblProdWrkOrd");
			obj2.children(2).innerText = temp.proWorkOrderHash.options[temp.proWorkOrderHash.selectedIndex].text;
			obj2.children(5).innerText = temp.hidChkdJobName.value;
			//alert("radio value :"+obj2.children(5).innerText);
			//temp.proNmlorRwk[0].checked = true; /* Normal Radio is Checked by Default */

			/* Select the Checked Radio Button */
			if (temp.rdWrkOrd.length != undefined)
			{
				temp.rdWrkOrd[temp.hidChkdJobId.value].checked = true;
			}
			else
			{
				temp.rdWrkOrd.checked = true;
			}
			if ((temp.proNmlorRwk[0].checked) || (temp.proNmlorRwk[1].checked))
			{
				return false;
			}
			else
			{
				temp.proNmlorRwk[0].checked = true;
			}
		}
		else if (temp.showCount.value == "3")
		{
			prodShftRow.style.display='block';
			prodInfo.style.display = 'none';
			tblJobQty.style.display = 'none';
			wrkOrder.style.display = 'block';
			tblEmp.style.display = 'none';
			tblEmpDets.style.display = 'none';
						
			/* 1 st Row */
			var obj = document.getElementById("tblProdShft"); 
			obj.children(2).innerText = temp.proDate.value;
			obj.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;
			obj.children(8).innerText = temp.proMachine.options[temp.proMachine.selectedIndex].text;

			/* 2 nd Row */
			var obj = document.getElementById("tblProdShft2"); 
			obj.children(2).innerText = temp.proDate.value;
			obj.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;
			obj.children(8).innerText = temp.proMachine.options[temp.proMachine.selectedIndex].text;

			var obj2 = document.getElementById("tblProdWrkOrd");
			obj2.children(2).innerText = temp.proWorkOrderHash.options[temp.proWorkOrderHash.selectedIndex].text;
			obj2.children(5).innerText = temp.hidChkdJobName.value;
			//alert("radio value :"+obj2.children(5).innerText);

			/* 3 d Row */
			var obj1 = document.getElementById("tblProdWrkOrd2");
			obj1.children(2).innerText = temp.proWorkOrderHash.options[temp.proWorkOrderHash.selectedIndex].text;
			obj1.children(5).innerText = temp.hidChkdJobName.value;
			obj1.children(8).innerText = (temp.proNmlorRwk[0].checked)?"Normal":"Rework";

			var obj2 = document.getElementById("tblProdJobOty");
			obj2.children(2).innerText = temp.proTotQtySnos.value;
			obj2.children(5).innerText = temp.proStartOperation.value;
			obj2.children(8).innerText = temp.proEndOperation.value;
			
			var chkdQty = (temp.proTotQtySnos.value).split(",");
			var obj = document.getElementById("tblJobOperDet");
			for (var i = 1; i < obj.children(0).children.length; i++)
			{
				for (var j = 0; j < chkdQty.length; j++)
				{
					//alert(obj.children(0).children(i).children(1).children(0).value +"=="+ chkdQty[j]);
					if (obj.children(0).children(i).children(1).children(0).value == chkdQty[j])
					{
						obj.children(0).children(i).children(0).children(0).checked = true;
					}
				}
			}
			
			/*if (temp.proIncentive.checked) // Checking the Incentive 
				temp.checkIncentive.checked = true;
			else
				temp.checkIncentive.checked = false;*/
				
			/* Select the Checked Radio Button */
			if (temp.rdWrkOrd.length != undefined)
			{
				temp.rdWrkOrd[temp.hidChkdJobId.value].checked = true;
			}
			else
			{
				temp.rdWrkOrd.checked = true;
			}
			temp.proStartOperation.value = "";
			temp.proEndOperation.value = "";
		}
		else if (temp.showCount.value == "4")
		{
			prodInfo.style.display='none';
			prodShftRow.style.display='none';
			wrkOrder.style.display='block';
			jobQty.style.display = 'block';
			tblJobQty.style.display = 'block';
			tblEmp.style.display = 'block';
			tblEmpDets.style.display = 'block';
						
			/* 1 st Row */
			var obj = document.getElementById("tblProdShft"); 
			obj.children(2).innerText = temp.proDate.value;
			obj.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;
			obj.children(8).innerText = temp.proMachine.options[temp.proMachine.selectedIndex].text;

			/* 2 nd Row */
			var obj = document.getElementById("tblProdShft2"); 
			obj.children(2).innerText = temp.proDate.value;
			obj.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;
			obj.children(8).innerText = temp.proMachine.options[temp.proMachine.selectedIndex].text;

			var obj2 = document.getElementById("tblProdWrkOrd");
			obj2.children(2).innerText = temp.proWorkOrderHash.options[temp.proWorkOrderHash.selectedIndex].text;
			obj2.children(5).innerText = temp.hidChkdJobName.value;
			//alert("radio value :"+obj2.children(5).innerText);

			/* 3 d Row */
			var obj1 = document.getElementById("tblProdWrkOrd2");
			obj1.children(2).innerText = temp.proWorkOrderHash.options[temp.proWorkOrderHash.selectedIndex].text;
			obj1.children(5).innerText = temp.hidChkdJobName.value;
			obj1.children(8).innerText = (temp.proNmlorRwk[0].checked)?"Normal":"Rework";

			var obj2 = document.getElementById("tblProdJobOty");
			obj2.children(2).innerText = temp.proTotQtySnos.value;
			obj2.children(5).innerText = temp.proStartOperation.value;
			obj2.children(8).innerText = temp.proEndOperation.value;
			
			var chkdQty = (temp.proTotQtySnos.value).split(",");
			var obj = document.getElementById("tblJobOperDet");
			for (var i = 1; i < obj.children(0).children.length; i++)
			{
				for (var j = 0; j < chkdQty.length; j++)
				{
					//alert(obj.children(0).children(i).children(1).children(0).value +"=="+ chkdQty[j]);
					if (obj.children(0).children(i).children(1).children(0).value == chkdQty[j])
					{
						obj.children(0).children(i).children(0).children(0).checked = true;
					}
				}
			}
			
				
			/* Select the Checked Radio Button */
			if (temp.rdWrkOrd.length != undefined)
			{
				temp.rdWrkOrd[temp.hidChkdJobId.value].checked = true;
			}
			else
			{
				temp.rdWrkOrd.checked = true;
			}
			
			temp.proTotalHours.value = "";
		}
		else if (temp.showCount.value == "5")
		{
			prodInfo.style.display='block';
			prodShftRow.style.display='block';
			wrkOrder.style.display='block';
		}
	}
	
	/* Showing JobOperDetails by hiding other 2 tables */
	function hideNshowJobOpnDet()
	{
		var obj = document.getElementById("tblWorkOrdInfo");
		var temp = document.forms[0];
		var flag = 0;

		if (obj.children(0).children.length == 1) /* No Jobs for that WorkOrder */
		{
			alert ("Sorry! This WorkOrder had no Jobs. Select different WorkOrder!");
			return false;
		}
		
		if (temp.rdWrkOrd.length != undefined)
		{
			if (temp.rdWrkOrd.length > 1) /* To get the WorkOrderJobId for selected Job */ 
			{
				if (temp.rdWrkOrd.length != 1)
				{
					for (var i = 0; i < temp.rdWrkOrd.length; i++)
					{
						if (temp.rdWrkOrd[i].checked == true)
						{
							flag = 1; /* One of the job must be selected */
							temp.hidWrkOrdJobId.value = obj.children(0).children(i+1).children(0).children(0).value;
							//alert (obj.children(0).children(i+1).children(1).innerHTML);
							temp.hidChkdJobName.value = obj.children(0).children(i+1).children(1).children(0).value;
							temp.hidChkdJobId.value = i;
						}
					}
				}
			}
		}
		else /* Only one is there */
		{
			if (temp.rdWrkOrd.checked)
			{
				flag = 1;
				temp.hidWrkOrdJobId.value = obj.children(0).children(1).children(0).children(0).value;
				temp.hidChkdJobName.value = obj.children(0).children(1).children(1).children(0).value;
				temp.hidChkdJobId.value = 0;
			}
		}
		
		if (flag != 1) /* Not even a single job is selected */
		{
			alert ("U must Select a Job!");
			return false;
		}
		temp.showCount.value = "3"; /* Increment the count value */
		temp.formAction.value = "load";
		temp.submit();
	}

	/* Hiding the JobDetails, JobOperation Details and show the Employee Details */
	function hideNshowEmpDetails(flag)
	{
		var temp = document.forms[0];

		if (temp.proStartOperation.value == "") /* Start Operation Cannot be Null */
		{
			alert ("Production Start Operation Cannot be Null!");
			return false;
		}
		if (temp.proEndOperation.value == "") /* End Operation cannot be Null */
		{
			alert ("Production End Operation Cannot be Null!");
			return false;
		}

		var start = parseInt(temp.proStartOperation.value);
		var end = parseInt(temp.proEndOperation.value);
		var srtString = "";
		if (start > end)
		{
			alert ("Start Operation can't be morethan End Operation!");
			temp.proStartOperation.value = "";
			temp.proEndOperation.value = "";
			return false;
		}

		if (start == end)
		{
			srtString = start;
		}
		else
		{
			for (var i = start; i <= end; i++)
			{
				if (i == start)
				{
					srtString = start;
				}
				else
				{
					srtString = srtString + "," + i;
				}
			}
		}
		
		var obj = document.getElementById("tblJobOperDet"); /* Picking that JobOperationDetails Table */
		var count = 0;
		temp.proTotQtySnos.value = "";
		temp.hidWrkOrdJbStIds.value = "";
		var i = 1;
		var k = 0;
		var totQty = "";
		var woJbStatIds = "";
		while (i < obj.children(0).children.length)
		{
			if (obj.children(0).children(i).children(0).children(0).checked)
			{
				if ((obj.children(0).children(i).children(2).children(0).value.search(srtString)) == -1)
				{
					alert ("Check Start/End Operations!");
					return false;
				}
				count++;
				var rewkOpns = (obj.children(0).children(i).children(3).children(0).value).split(',');
				if (k == 0)
				{
					totQty = obj.children(0).children(i).children(1).children(0).value;
					woJbStatIds = obj.children(0).children(i).children(0).children(0).value;
				}
				else
				{
					totQty = totQty + "," + obj.children(0).children(i).children(1).children(0).value;
					woJbStatIds = woJbStatIds + "-" + obj.children(0).children(i).children(0).children(0).value;
				}
				k++;

				for (var x = start; x <= end; x++) /* Rework Operation can enter only when the worktype is Rework */
				{
					for (var y = 0; y < rewkOpns.length; y++)
					{
						if ((x == rewkOpns[y]) && (temp.proNmlorRwk[0].checked))
						{
							alert ("Sorry! Rework Operations can enter only when the WorkType is Rework!");
							return false;
						}
					}
				}

			}
			i++;
		}
		temp.proTotQtySnos.value = totQty;

		if (count == 0)/* Verify if minimum one is checked */
		{
			alert ("Check One or more Job Quantities to Proceed!");
			return false;
		}
		
		/* Call the special fn. if the operation is ReWork */
		if (temp.proNmlorRwk[1].checked)
		{
			temp.hidWrkOrdJbStIds.value = woJbStatIds;
		}
		
		temp.hidIsRadl.value = obj.children(0).children(1).children(0).children(0).value;
		temp.showCount.value = "4";
		temp.formAction.value = "isRadl";
		flg = 1;
		temFlg = true;
		temp.submit();
	}

	function transEmpDet(val)
	{
		var temp = document.forms[0];
		var ind = parseInt(val);
		var obj = document.getElementById("tblEmpDetails");
		var obj1 = document.getElementById("tblEmpDetMstr");

		/* Duty + OT hrs can't be Zero */
		if((obj1.children(0).children(ind).children(2).children(0).value == "") && (obj1.children(0).children(ind).children(3).children(0).value == "") 
			|| (parseFloat(obj1.children(0).children(ind).children(2).children(0).value)+parseFloat(obj1.children(0).children(ind).children(3).children(0).value) == 0)
			|| (obj1.children(0).children(ind).children(2).children(0).value+obj1.children(0).children(ind).children(3).children(0).value == "")
			|| (obj1.children(0).children(ind).children(2).children(0).value+obj1.children(0).children(ind).children(3).children(0).value == 0))
		{
			alert ("Duty + OT Hours can't be Zero!");
			return false;
		}		

		/* Validations before shifting Employee Details */
		if (temp.proEmployeeName[ind-1].options[temp.proEmployeeName[ind-1].selectedIndex].value == "0")
		{
			alert ("Select Employee Name to Proceed!");
			return false;
		}
		

		/* Duty + OT Hrs can't exceed Total Hours */
		if (temp.proTotalHours.value != "")
		{
			var dty = (obj1.children(0).children(ind).children(2).children(0).value == "")?(0):(parseFloat(obj1.children(0).children(ind).children(2).children(0).value));
			var ot = (obj1.children(0).children(ind).children(3).children(0).value == "")?(0):(parseFloat(obj1.children(0).children(ind).children(3).children(0).value));
			if ((dty+ot) > parseFloat(temp.proTotalHours.value))
			{

				alert ("Duty Hrs + OT Hrs shouldn't be morethan Total Hours!");
				obj1.children(0).children(ind).children(2).children(0).value = "";
				obj1.children(0).children(ind).children(3).children(0).value = "";

				return false;
			}
		}
		else
		{
			alert ("Enter the Total Hours!");
			return false;
		}
		
		if ((obj1.children(0).children(ind).children(2).children(0).value > 999) || (obj1.children(0).children(ind).children(3).children(0).value > 999))
		{
			alert("Employee Hours is Invalid!");
			obj1.children(0).children(ind).children(2).children(0).value = "";
			obj1.children(0).children(ind).children(3).children(0).value = "";
			obj1.children(0).children(ind).children(2).children(0).focus();
			return false;
		}

		if ((obj.children.length == "1") && (obj.children(0).children(0).children(1).children(0).value == ""))
		{
			/* Shifting the Employee Details from Master */
			obj.children(0).children(0).children(1).children(0).value = obj1.children(0).children(ind).children(0).children(0).value;
			obj.children(0).children(0).children(2).children(0).value = temp.proEmployeeName[ind-1].options[temp.proEmployeeName[ind-1].selectedIndex].text;
			obj.children(0).children(0).children(3).children(0).value = (obj1.children(0).children(ind).children(2).children(0).value == "")?(0):(obj1.children(0).children(ind).children(2).children(0).value);
			obj.children(0).children(0).children(4).children(0).value = (obj1.children(0).children(ind).children(3).children(0).value == "")?(0):(obj1.children(0).children(ind).children(3).children(0).value);
			obj.children(0).children(0).children(5).children(0).value = obj1.children(0).children(ind).children(5).children(0).value;
			obj.children(0).children(0).children(6).children(0).value = temp.proEmployeeName[ind-1].options[temp.proEmployeeName[ind-1].selectedIndex].value;
			//alert (obj.children(0).children(0).children(4).children(0).value+"&"+obj.children(0).children(0).children(5).children(0).value);

			/* Reseting the Fields */
			obj1.children(0).children(ind).children(2).children(0).value = "";
			obj1.children(0).children(ind).children(3).children(0).value = "";
			temp.proEmployeeName[ind-1].selectedIndex = "0";
		}
		else
		{
			/* Validate wheather this is already added or not */
			for (var i = 0; i < obj.children.length; i++)
			{
				if (obj.children(i).children(0).children(6).children(0).value == temp.proEmployeeName[ind-1].options[temp.proEmployeeName[ind-1].selectedIndex].value)
				{
					var name = temp.proEmployeeName[ind-1].options[temp.proEmployeeName[ind-1].selectedIndex].text;
					alert ("Sorry! The Employee "+name+"'s details already Added!");

					/* Reseting the Fields */
					obj1.children(0).children(ind).children(2).children(0).value = "";
					obj1.children(0).children(ind).children(3).children(0).value = "";
					temp.proEmployeeName[ind-1].selectedIndex = "0";

					return false;
				}
			}

			/* Incrementing a Row */
			var newNode = obj.children(0).cloneNode(true); 
			obj.appendChild(newNode);
			var len = obj.children.length;
			
			/* Reset the Previous values */
			obj.children(len-1).children(0).children(1).children(0).value = "";
			obj.children(len-1).children(0).children(2).children(0).value = "";
			obj.children(len-1).children(0).children(3).children(0).value = "";
			obj.children(len-1).children(0).children(4).children(0).value = "";
			obj.children(len-1).children(0).children(5).children(0).value = "";
			obj.children(len-1).children(0).children(6).children(0).value = "";

			/* Updating the Employee Details from master */
			obj.children(len-1).children(0).children(1).children(0).value = obj1.children(0).children(ind).children(0).children(0).value;
			obj.children(len-1).children(0).children(2).children(0).value = temp.proEmployeeName[ind-1].options[temp.proEmployeeName[ind-1].selectedIndex].text;
			obj.children(len-1).children(0).children(3).children(0).value = (obj1.children(0).children(ind).children(2).children(0).value == "")?(0):(obj1.children(0).children(ind).children(2).children(0).value);
			obj.children(len-1).children(0).children(4).children(0).value = (obj1.children(0).children(ind).children(3).children(0).value == "")?(0):(obj1.children(0).children(ind).children(3).children(0).value);
			obj.children(len-1).children(0).children(5).children(0).value = obj1.children(0).children(ind).children(5).children(0).value;
			obj.children(len-1).children(0).children(6).children(0).value = temp.proEmployeeName[ind-1].options[temp.proEmployeeName[ind-1].selectedIndex].value;

			/* Reseting the Fields */
			obj1.children(0).children(ind).children(2).children(0).value = "";
			obj1.children(0).children(ind).children(3).children(0).value = "";
			temp.proEmployeeName[ind-1].selectedIndex = "0";
		}
	}

	function enterProDet()
	{
		var temp = document.forms[0];
		temFlg = false;
		/*hideNshowEmpDetails(temFlg);
		if (flg == 0)
			return false;*/
		flg = 0;

		var start = parseInt(temp.proStartOperation.value);
		var end = parseInt(temp.proEndOperation.value);
		var srtString = "";
		if (start > end)
		{
			alert ("Start Operation can't be morethan End Operation!");
			temp.proStartOperation.value = "";
			temp.proEndOperation.value = "";
			return false;
		}

		if (start == end)
		{
			srtString = start;
		}
		else
		{
			for (var i = start; i <= end; i++)
			{
				if (i == start)
				{
					srtString = start;
				}
				else
				{
					srtString = srtString + "," + i;
				}
			}
		}
		
		var obj = document.getElementById("tblJobOperDet"); /* Picking that JobOperationDetails Table */
		var count = 0;
		temp.proTotQtySnos.value = "";
		temp.hidWrkOrdJbStIds.value = "";
		var i = 1;
		var k = 0;
		var totQty = "";
		var woJbStatIds = "";
		while (i < obj.children(0).children.length)
		{
			if (obj.children(0).children(i).children(0).children(0).checked)
			{
				if ((obj.children(0).children(i).children(2).children(0).value.search(srtString)) == -1)
				{
					alert ("Check Start/End Operations!");
					return false;
				}
				count++;
				var rewkOpns = (obj.children(0).children(i).children(3).children(0).value).split(',');
				if (k == 0)
				{
					totQty = obj.children(0).children(i).children(1).children(0).value;
					woJbStatIds = obj.children(0).children(i).children(0).children(0).value;
				}
				else
				{
					totQty = totQty + "," + obj.children(0).children(i).children(1).children(0).value;
					woJbStatIds = woJbStatIds + "-" + obj.children(0).children(i).children(0).children(0).value;
				}
				k++;

				for (var x = start; x <= end; x++) /* Rework Operation can enter only when the worktype is Rework */
				{
					for (var y = 0; y < rewkOpns.length; y++)
					{
						if ((x == rewkOpns[y]) && (temp.proNmlorRwk[0].checked))
						{
							alert ("Sorry! Rework Operations can enter only when the WorkType is Rework!");
							return false;
						}
					}
				}

			}
			i++;
		}
		temp.proTotQtySnos.value = totQty;
		//alert ("Tot Qty: "+temp.proTotQtySnos.value);

		if (count == 0)/* Verify if minimum one is checked */
		{
			alert ("Check One or more Job Quantities to Proceed!");
			return false;
		}
		
		/* Call the special fn. if the operation is ReWork */
		if (temp.proNmlorRwk[1].checked)
		{
			temp.hidWrkOrdJbStIds.value = woJbStatIds;
		}



		var obj = document.getElementById("tblEmpDetails");

		if (temp.proTotalHours.value == "") /* Total hrs can't be empty */
		{
			alert ("Sorry! U must enter Total Hours!");
			return false;
		}
		else
		{
			var q = 0;
			var totHours = temp.proTotalHours.value;
			while (q < obj.children.length)
			{
				var dtynOtHrs = parseInt(obj.children(q).children(0).children(3).children(0).value) + parseInt(obj.children(q).children(0).children(4).children(0).value); 

				if (parseInt(totHours) < dtynOtHrs)
				{
					alert("Duty + OT Hours cannot be greater than Total Hours!");
					return false;
				}
				q++;
			}
		}
		
		if ((temp.proTotalHours.value != "") && (temp.proTotalHours.value > 999)) /* Total hrs can't be greater than 1000 */
		{
			alert("Total Hrs is Invalid!");
			temp.proTotalHours.value = "";
			temp.proTotalHours.focus();
			return false;
		}

		/* Remove the Empty values in Employee Details */
		if ((obj.children(0).children(0).children(1).children(0).value == "") &&
			(obj.children(0).children(0).children(2).children(0).value == "") &&
			(obj.children(0).children(0).children(3).children(0).value == ""))
		{
			alert ("Employee Details can't be Empty!");
			return false;
		}
		
		var empDet = "";
		var i = 0;
		while (i < obj.children.length)
		{
			if (i != 0)
			{
				empDet = empDet + "^";
			}
			if (i == 0)
			{
				empDet = obj.children(0).children(0).children(1).children(0).value; // Emp Typ name 
				empDet = empDet + "-" + obj.children(0).children(0).children(5).children(0).value; // Emp Typ Id 
				empDet = empDet + "-" + obj.children(0).children(0).children(2).children(0).value; // Emp Name 
				empDet = empDet + "-" + obj.children(0).children(0).children(6).children(0).value; // Emp Name Id 
				empDet = empDet + "-" + obj.children(0).children(0).children(3).children(0).value; // Duty Hrs 
				empDet = empDet + "-" + obj.children(0).children(0).children(4).children(0).value; // OT Hrs 
			}
			else
			{
				empDet = empDet + "" + obj.children(i).children(0).children(1).children(0).value; // EmpTyp name 
				empDet = empDet + "-" + obj.children(i).children(0).children(5).children(0).value; // Emp Typ Id 
				empDet = empDet + "-" + obj.children(i).children(0).children(2).children(0).value; // Emp Name 
				empDet = empDet + "-" + obj.children(i).children(0).children(6).children(0).value; // Emp Name Id 
				empDet = empDet + "-" + obj.children(i).children(0).children(3).children(0).value; // Duty Hrs 
				empDet = empDet + "-" + obj.children(i).children(0).children(4).children(0).value; // OT Hrs 
			}
			i++;
		}
		//alert ("empDet: "+empDet);
		if (empDet == "")
		{
			alert ("Sorry! U must select Employee's to proceed!");
			return false;
		}

		/* For Checking the Minimum required Quantity of Employee's */
		var arTotEmp = (temp.minQty.value).split("^");
		//alert('<%= frm.getMinQty() %>');
		//alert(temp.minQty.value);
		for (var j = 0; j < arTotEmp.length; j++)
		{
			var arPrnlEmpDet = arTotEmp[j].split("-");
			var count = 0;
			var hlp = "";
			var i = 0;
			while (i < obj.children.length)
			{
				if (obj.children(i).children(0).children(5).children(0).value == arPrnlEmpDet[0])
				{
					count++;
				}
				i++;
			}
			if (arPrnlEmpDet[1] > count)
			{
				alert ("Atleast "+arPrnlEmpDet[1]+" "+arPrnlEmpDet[2]+" Required.");
				//temFlg = false;
				return false;
			}
		}
//		if (temFlg)
//		{
		temp.hidAllEmpDet.value = empDet;
		temp.formAction.value = "update";
		temp.submit();
//		}
	}

	/* Deleting the Checked row in Employee Details */
	function delRow()
	{
		var temp = document.forms[0];
		var obj = document.getElementById("tblEmpDetails");
		if (obj.children.length > 1)
		{
			for (var i = 0 ; i < obj.children.length; i++)
			{
				if (temp.ckdCkBx[i].checked)
				{
					obj.removeChild(obj.children(i));
					i = 0;
				}
			}
			if (obj.children.length == 1)
			{
				if (temp.ckdCkBx[0].checked)
				{
					obj.children(0).children(0).children(1).children(0).value = "";
					obj.children(0).children(0).children(2).children(0).value = "";
					obj.children(0).children(0).children(3).children(0).value = "";
					obj.children(0).children(0).children(4).children(0).value = "";
					obj.children(0).children(0).children(5).children(0).value = "";
					obj.children(0).children(0).children(6).children(0).value = "";
					temp.ckdCkBx.checked = false;
					temp.ckAll.checked = false;
				}
			}

		}
		else if (obj.children.length == 1)
		{
				obj.children(0).children(0).children(1).children(0).value = "";
				obj.children(0).children(0).children(2).children(0).value = "";
				obj.children(0).children(0).children(3).children(0).value = "";
				obj.children(0).children(0).children(4).children(0).value = "";
				obj.children(0).children(0).children(5).children(0).value = "";
				obj.children(0).children(0).children(6).children(0).value = "";

				temp.ckdCkBx[0].checked = false;
				temp.ckAll.checked = false;
		}
	}
	
	function modOperation()
	{
		var temp = document.forms[0];
		//		alert ("Action: "+temp.formAction.value);

		if (temp.formAction.value == "modify")
		{
			var totalQty = '<%= objProductionDetails.getProdQtySnos() %>'; /* Splitting the Total Qty's */
			var arTotalQty = totalQty.split(",");

			var obj = document.getElementById("tblJobOperDet");
			for (var i = 1; i < obj.children(0).children.length; i++)
			{
				for (var j = 0; j < arTotalQty.length; j++)
				{
					if (obj.children(0).children(i).children(1).children(0).value == arTotalQty[j])
					{
						obj.children(0).children(i).children(0).children(0).checked = true;
					}
				}
			}
			
			var chkdWrkOrdJobId = '<%= objProductionDetails.getWoJbId() %>'; /* To check the Selected Job Name */
			var obj = document.getElementById("tblWorkOrdInfo");
			for(i=1; i<obj.children(0).children.length;i++)
			{
				if(chkdWrkOrdJobId == obj.children(0).children(i).children(0).children(0).value)
				{
					obj.children(0).children(i).children(0).children(0).checked = true;
				}
			}

			var nmlRwk = '<%= objProductionDetails.getProdWorkType() %>'; /* For Normal Or Rework */
			if (nmlRwk == "N")
				temp.proNmlorRwk[0].checked = true;
			else
				temp.proNmlorRwk[1].checked = true;
			/*if ('<%= frm.getProIncentive() %>' == 'true') // For Incentive Flag 
				temp.proIncentive.checked = true;
			else
				temp.proIncentive.checked = false;*/
			jobQty.style.display='block'; 

			/* Show all the table's Which are associated with Production */
			prodInfo.style.display='block';			
			prodShftRow.style.display='block';

			/* Validation for updating the Date, Shift and Machine Names */
			var obj = document.getElementById("tblProdShft");
			obj.children(2).innerText = temp.proDate.value;
			obj.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;
			obj.children(8).innerText = temp.proMachine.options[temp.proMachine.selectedIndex].text;

			wrkOrder.style.display='block';
			
			/* Validation for updating Date, Shift, Machine Name, WorkOrderNo and Job name */
			var obj = document.getElementById("tblProdShft");
			obj.children(2).innerText = temp.proDate.value;
			obj.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;
			obj.children(8).innerText = temp.proMachine.options[temp.proMachine.selectedIndex].text;

			var obj1 = document.getElementById("tblProdShft2");
			obj1.children(2).innerText = temp.proDate.value;
			obj1.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;
			obj1.children(8).innerText = temp.proMachine.options[temp.proMachine.selectedIndex].text;
			
			var obj2 = document.getElementById("tblProdWrkOrd");
			obj2.children(2).innerText = temp.proWorkOrderHash.options[temp.proWorkOrderHash.selectedIndex].text;
			obj2.children(5).innerText = '<%= objProductionJobDetails.getJobName() %>';

			var obj = document.getElementById("tblProdShft3");
			obj.children(2).innerText = temp.proDate.value;
			obj.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;
			obj.children(8).innerText = temp.proMachine.options[temp.proMachine.selectedIndex].text;

			var obj1 = document.getElementById("tblProdWrkOrd2");
			obj1.children(2).innerText = temp.proWorkOrderHash.options[temp.proWorkOrderHash.selectedIndex].text;
			obj1.children(5).innerText = '<%= objProductionJobDetails.getJobName() %>';
			obj1.children(8).innerText = (temp.proNmlorRwk[0].checked)?"Normal":"Rework";

			var obj2 = document.getElementById("tblProdJobOty");
			obj2.children(2).innerText = '<%= objProductionDetails.getProdQtySnos() %>';
			obj2.children(5).innerText = temp.proStartOperation.value;
			obj2.children(8).innerText = temp.proEndOperation.value;
			
			
			/*if (temp.proIncentive.checked)  // Checking the Incentive
				temp.checkIncentive.checked = true;
			else
				temp.checkIncentive.checked = false;*/
			
		}
		else
		{
			return false;
		}
	}
	
	/* This Fn. is called by Body Onload. This executes only when the modification is performed. */
	function modEmpDetails()
	{
		var temp = document.forms[0];
		if (temp.formAction.value != "modify") /* If not Modification, Exit*/
			return false;
		
		var obj = document.getElementById("tblEmpDetails");
		var arEmpDet = (temp.hidAllEmpDet.value).split("^");
		for (var i = 0; i < arEmpDet.length; i++)
		{
			var arEmpPrnlDet = arEmpDet[i].split("-");
			if ((obj.children.length == 1) && (obj.children(0).children(0).children(1).children(0).value == ""))
			{
				obj.children(0).children(0).children(1).children(0).value = arEmpPrnlDet[0];
				obj.children(0).children(0).children(2).children(0).value = arEmpPrnlDet[1];
				obj.children(0).children(0).children(3).children(0).value = arEmpPrnlDet[2];
				obj.children(0).children(0).children(4).children(0).value = arEmpPrnlDet[3];
				obj.children(0).children(0).children(5).children(0).value = arEmpPrnlDet[4];
				obj.children(0).children(0).children(6).children(0).value = arEmpPrnlDet[5];
			}
			else
			{
				/* Incrementing a Row */
				var newNode = obj.children(0).cloneNode(true);
				obj.appendChild(newNode);
				var len = obj.children.length;

				obj.children(len-1).children(0).children(1).children(0).value = arEmpPrnlDet[0];
				obj.children(len-1).children(0).children(2).children(0).value = arEmpPrnlDet[1];
				obj.children(len-1).children(0).children(3).children(0).value = arEmpPrnlDet[2];
				obj.children(len-1).children(0).children(4).children(0).value = arEmpPrnlDet[3];
				obj.children(len-1).children(0).children(5).children(0).value = arEmpPrnlDet[4];
				obj.children(len-1).children(0).children(6).children(0).value = arEmpPrnlDet[5];
			}
		}
		temp.hidAllEmpDet.value = "";
	}
	
	function loadToHidden()
	{
		var temp = document.forms[0];
		temp.ids.value = document.forms[0].id.value;
		// Changing formAction value to reload the page as it was...
		if (temp.formAction.value == "viewOpns")
		{
			temp.formAction.value = "modify";
		}
		temp.fromDate.value = '<%= request.getParameter("proFromDate") %>';
		temp.tDate.value = '<%= request.getParameter("proToDate") %>';
		temp.woNo.value = '<%= request.getParameter("proWorkOrder") %>';
		temp.jbName.value = '<%= request.getParameter("proJobName") %>';
		temp.jbCombo.value = '<%= request.getParameter("jobSelect") %>';
		temp.mcCde.value = '<%= request.getParameter("proMachineCode") %>';
		temp.sftName.value = '<%= request.getParameter("proShiftName") %>';
		temp.viewAll.value = '<%= request.getParameter("listValidEntries") %>';
		temp.postedView.value = '<%= request.getParameter("postedView") %>';
		temp.woSelect.value = '<%= request.getParameter("woSelect") %>';
		temp.maxItem.value = '<%= request.getParameter("maxItems") %>';
		temp.empName.value = '<%= request.getParameter("proEmplName") %>';
		temp.empNameCombo.value = '<%= request.getParameter("emplSelect") %>';
		temp.pageNo.value = '<%= request.getParameter("page") %>';
		temp.sortField.value = '<%= request.getParameter("sortField") %>';
		temp.sortOrder.value = '<%= request.getParameter("sortOrder") %>';
	}
	
	function viewOpns()
	{
		temp = document.forms[0];
		var obj = document.getElementById('tblWorkOrdInfo');
		for (var i = 1; i < obj.children(0).children.length; i++)
		{
			if (obj.children(0).children(i).children(0).children(0).checked)
			{
				temp.hidWoJbId.value = obj.children(0).children(i).children(0).children(0).value;
				temp.formAction.value = "viewOpns";
				temp.submit();
			}
		}
	}
	
	function operationView()
	{
		if (document.forms[0].formAction.value == "viewOpns")
		{
			var features = "toolbars=no,status=no,resizable=no,scrollbars=yes,height=550px,width=600px,top=90px,left=350px";
			window.open('<bean:message key="context"/>/Production/ViewOperationsEdit.jsp?formAction=viewOpns',"ViewOperationsEdit",features);
		}
	}
	
</script>
</head>

<body onLoad="init(); operationView(); loadToHidden(); showJobDet(); modOperation(); modEmpDetails();  ">
<html:form action="frmProdEdit">
<html:hidden property="formAction"/>
<html:hidden property="id"/>
<html:hidden property="ids"/>
<html:hidden property="modJobDet"/> <!-- Holding the JobDetails by EJB -->
<html:hidden property="minQty" value="<%= frm.getMinQty() %>"/> <!--  Holding the Minimum required no. of emp's -->
<html:hidden property="hidWrkOrdId" /><!-- value="<%= frm.getHidWrkOrdId() %>" Handling the WorkOrder Id for Job Details -->
<html:hidden property="hidWrkOrdJobId" /><!-- value="<%= frm.getHidWrkOrdJobId() %>" Handling the WorkOrder Job Id for Job Operation Details -->
<html:hidden property="showCount" /><!-- value="<%= frm.getShowCount() %>" Showing the appropriate Table by Count -->
<html:hidden property="hidChkdJobName" /><!-- value="<%= frm.getHidChkdJobName() %>" Holding the Selected Job name -->
<html:hidden property="hidChkdJobId" /> <!-- value="<%= frm.getHidChkdJobId() %>"Holding the Selected Job Id Index -->
<html:hidden property="proTotQtySnos" /> <!-- Holding the Total Qty Sno's -->
<html:hidden property="hidAllEmpDet" value="<%= frm.getHidAllEmpDet() %>" /> <!-- Holding the Employee Details -->
<html:hidden property="hidIsRadl" />
<html:hidden property="hidWrkOrdJbStIds" /> <!-- Holding the WoJbStatusIds for the operations are rework or not -->
<html:hidden property="hidUserId" /> <!-- Holding the User Name -->
<html:hidden property="hidWoJbId" value="<%= frm.getHidWoJbId() %>"/> <!-- Holding the WorkOrder Job Id -->
<html:hidden property="grpId"/> <!-- Holding the Opn.Group Id -->
<html:hidden property="sno"/> <!-- Holding the Opn.SNo -->
<html:hidden property="name"/> <!-- Holding the Opn.Name -->
<html:hidden property="stdHrs"/> <!-- Holding the Opn. StdHrs -->
<html:hidden property="gpCde"/> <!-- Holding the Group Code -->
<html:hidden property="loadCount"/> <!-- Holding the LoadCount value for Selection of Machines -->
<html:hidden property="hidSft"/> <!-- Holding the Shift Value -->
<html:hidden property="hidDate"/> <!-- Holding the Date value -->
<html:hidden property="hidSft1"/> <!-- Holding the Shift Value -->
<html:hidden property="hidDate1"/> <!-- Holding the Date value -->
<input type="hidden" name="frmDate"/>
<input type="hidden" name="toDate"/>
<input type="hidden" name="viewId"/>
<input type="hidden" name="fromDate"/>
<input type="hidden" name="tDate"/>
<input type="hidden" name="jbName"/>
<input type="hidden" name="jbCombo"/>
<input type="hidden" name="viewAll"/>
<input type="hidden" name="woNo"/>
<input type="hidden" name="woSelect"/>
<input type="hidden" name="mcCde"/>
<input type="hidden" name="sftName"/>
<input type="hidden" name="postedView"/>
<input type="hidden" name="maxItem"/>
<input type="hidden" name="empName"/>
<input type="hidden" name="empNameCombo"/>
<input type="hidden" name="pageNo"/>
<input type="hidden" name="sortField"/>
<input type="hidden" name="sortOrder"/>

	<table width="100%" cellspacing="0" cellpadding="10">
    	<tr> 
      <td><table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
	          <tr>
	             <td><bean:message key="prodacs.production.header"/></td>
	          </tr>
          </table>
          <br> 
          <table width="100" cellspacing="0" cellpadding="0" align="right">
	    <tr> 
	 	  <menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New Production Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1017" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
	 	  <menuconfig:userRights url="#" id="#" onMouseOver="window.status='List Production Info'; return true"  onMouseOut="window.status=''; return true" resourceId="17" text="[ List ]" classId="TopLnk" onClick="javaScript:listItem();"/>
	    </tr>
          </table>
          <br>
    		<font class="message">
		<html:messages id="messageid" message="true">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="messageid" />
		</html:messages>
		</font>
		<table>
		<tr> 
			<td colspan='2'> <font size="1px" face="Verdana"><html:errors/></font></td>
		</tr>
		</table>
		<table width="100%" cellpadding="0" cellspacing="0">
 		<tr id="prodInfo"> 
            <td>
			<table width="100%" cellspacing="0" cellpadding="0">
                	<tr> 
                  <td width="50" class="FieldTitle"><bean:message key="prodacs.job.date"/><span class="mandatory">*</span></td>
                  <td width="1" class="FieldTitle">:</td>
                  <td width="150" class="FieldTitle"><html:text property="proDate" styleClass="TextBox" readonly="true" size="12"/>
                    <img src='<bean:message key="context"/>/images/calendar.gif' width="16" height="15" align="absmiddle" onClick='show_calendar("proDate",ProductionEdit.proDate.value,event.clientX,event.clientY,false);clearAll();' onMouseOver="this.style.cursor='hand'"></td>
                    
                  <td width="50" class="FieldTitle"><bean:message key="prodacs.production.shift"/><span class="mandatory">*</span></td>
                  <td width="1" class="FieldTitle">:</td>
                  <td width="100" class="FieldTitle"><html:select property="proShift" styleClass="Combo" onchange="loadMachines();">
                      <html:option value="0">-- Select Shift --</html:option>
                      <html:options collection="shiftDefnNameList" property="key" labelProperty="value"/>
                    </html:select></td>
                    
                  <td width="65" class="FieldTitle"><bean:message key="prodacs.production.machine"/><span class="mandatory">*</span></td>
                  <td width="1" class="FieldTitle">:</td>
                  <td class="FieldTitle"><html:select property="proMachine" styleClass="Combo">
                      <html:option value="0">-- Select Machine --</html:option>
                      <html:options collection="machineDet" property="key" labelProperty="key"/>
                    </html:select></td>
                	</tr>
              	</table>
              <br>
              	<table width="100%" cellpadding="0" cellspacing="0" id="BtnBgLft">
                	<tr>
                  <td><html:button styleClass="Button" property="proNextBtn1" onclick="hideNshowDateShiftMachine(); ">Next</html:button></td>
                	</tr>
              	</table>
            </td>
          	</tr>
          	<tr id="prodImgRow"> 
              <td style="border-top:solid 3px #CCCCCC; text-align:right"><A HREF="javascript:showHide(document.getElementById('prodInfo'), document.getElementById('prodLayImg1'))"><img src='<bean:message key="context"/>/images/pal_hide.gif' id="prodLayImg1" border="0"></A></td>
			</tr>
			<tr id="prodShftRow" style="display:none">
			  <td>
				<table width="100%" cellpadding="0" cellspacing="0">
                	<tr id="tblProdShft"> 
                  <td width="90" class="FieldTitle"><bean:message key="prodacs.job.date"/></td>
                  <td width="1" class="FieldTitle">:</td>
                  <td width="150" class="ViewData">&nbsp;</td>
                  <td width="50" class="FieldTitle"><bean:message key="prodacs.production.shift"/></td>
                  <td width="1" class="FieldTitle">:</td>
                  <td width="100" class="ViewData">&nbsp;</td>
                  <td width="65" class="FieldTitle"><bean:message key="prodacs.production.machine"/></td>
                  <td width="1" class="FieldTitle">:</td>
                  <td class="ViewData">&nbsp;</td>
                	</tr>
                	<tr> 
                  <td width="90" class="FieldTitle"><bean:message key="prodacs.workorder.workorder"/></td>
                  <td width="1" class="FieldTitle">:</td>
                  <td colspan="7" class="ViewData"><html:select property="proWorkOrderHash" styleClass="Combo" onchange="loadJobDetByWOId(this.options[this.selectedIndex].value);">
                      <html:option value="0">-- Select WorkOrder --</html:option>
                      <html:options collection="workOrderDet" property="key" labelProperty="value"/>
                    </html:select></td>
                	</tr>
              	</table>
              <br>
		    <table width="100%" cellpadding="0" cellspacing="0" id="tblWorkOrdInfo">
                <tr> 
                  <td width="25" class="Header">&nbsp;</td>
                  <td class="Header"><bean:message key="prodacs.job.jobname"/></td>
                  <td width="75" class="Header"><bean:message key="prodacs.production.totalqty"/></td>
                  <td width="85" class="Header"><bean:message key="prodacs.production.pendingqtyseriel"/></td>
                  <td width="120" class="Header"><bean:message key="prodacs.production.postedqtyseriel"/></td>
                  <td width="120" class="Header"><bean:message key="prodacs.production.unpostedqtyseriel"/></td>
                  <td width="120" class="Header"><bean:message key="prodacs.production.lastproductiondateshift"/></td>
                </tr>
                <logic:iterate id="bt1" name="vecJobDet" indexId="count">
		    <tr>
			<%
				if(count.intValue()%2 == 0)
				{
			%>		
					<td class="TableItems"><input type=radio name="rdWrkOrd" value="<bean:write name='bt1' property='woJbId'/>"/></td>
					<td class="TableItems" title="<bean:write name='bt1' property='jobName'/>/<bean:write name='bt1' property='dwgNo'/>/<bean:write name='bt1' property='rvsnNo'/>/<bean:write name='bt1' property='matlType'/>"><input type="text" name="prodJbName" readonly class="TextBoxFull" value='<bean:write name="bt1" property="jobName"/>'/></td>
					<td class="TableItems"><input type="text" name="prodTotQty" readonly class="TextBoxFull" value='<bean:write name="bt1" property="totQty"/>'/></td>
					<td class="TableItems"><input type="text" name="prodPendQtySno" readonly class="TextBoxFull" value='<bean:write name="bt1" property="pendingQtySnos"/>'/></td>
					<td class="TableItems"><input type="text" name="prodPostQtySno" readonly class="TextBoxFull" value='<bean:write name="bt1" property="postedQtySnos"/>'/></td>
					<td class="TableItems"><input type="text" name="prodUnpostQty" readonly class="TextBoxFull" value='<bean:write name="bt1" property="unPostedQtySnos"/>'/></td>
					<td class="TableItems"><logic:notEmpty name="bt1" property="lastProdnDate">
												<bean:define id="latPrdDae" name="bt1" property="lastProdnDate"/>
												<input type="text" name="proLastPrdDate" readonly class="TextBoxFull" value='<%= latPrdDae.toString().substring(0,10) %>'/>
											</logic:notEmpty>&nbsp;</td>
			<%
				}
				else
				{
			%>
					<td class="TableItems2"><input type=radio name="rdWrkOrd" value="<bean:write name='bt1' property='woJbId'/>"/></td>
					<td class="TableItems2" title="<bean:write name='bt1' property='jobName'/>/<bean:write name='bt1' property='dwgNo'/>/<bean:write name='bt1' property='rvsnNo'/>/<bean:write name='bt1' property='matlType'/>"><input type="text" name="prodJbName" readonly class="TextBoxFull" value='<bean:write name="bt1" property="jobName"/>'/></td>
					<td class="TableItems2"><input type="text" name="prodTotQty" readonly class="TextBoxFull" value='<bean:write name="bt1" property="totQty"/>'/></td>
					<td class="TableItems2"><input type="text" name="prodPendQtySno" readonly class="TextBoxFull" value='<bean:write name="bt1" property="pendingQtySnos"/>'/></td>
					<td class="TableItems2"><input type="text" name="prodPostQtySno" readonly class="TextBoxFull" value='<bean:write name="bt1" property="postedQtySnos"/>'/></td>
					<td class="TableItems2"><input type="text" name="prodUnpostQty" readonly class="TextBoxFull" value='<bean:write name="bt1" property="unPostedQtySnos"/>'/></td>
					<td class="TableItems2"><logic:notEmpty name="bt1" property="lastProdnDate">
												<bean:define id="latPrdDae" name="bt1" property="lastProdnDate"/>
												<input type="text" name="proLastPrdDate" readonly class="TextBoxFull" value='<%= latPrdDae.toString().substring(0,10) %>'/>
											</logic:notEmpty>&nbsp;</td>
			<%
				}
			%>
			</tr>
   	 		</logic:iterate>
                  </table>
              <br><table width="100%" cellpadding="0" cellspacing="0" id="BtnBgLft">
                	<tr> 
                  <td><html:button styleClass="Button" property="proNextBtn2" onclick="hideNshowJobOpnDet();">Next</html:button></td>
                	</tr>
              	</table>
            </td>
          	</tr>
          	<tr> 
              <td style="border-top:solid 3px #CCCCCC; text-align:right"><A HREF="javascript:showHide(document.getElementById('prodShftRow'), document.getElementById('prodLayImg2'))"><img src='<bean:message key="context"/>/images/pal_hide.gif' id="prodLayImg2" border="0"></A></td>
			</tr>
			<tr id="wrkOrder" style="display:none">
			  <td>
				<table width="100%" cellpadding="0" cellspacing="0">
                	<tr id="tblProdShft2"> 
	                  <td width="90" class="FieldTitle"><bean:message key="prodacs.job.date"/></td>
	                  <td width="1" class="FieldTitle">:</td>
	                  <td width="150" class="ViewData">&nbsp;</td>
	                  <td width="90" class="FieldTitle"><bean:message key="prodacs.production.shift"/></td>
	                  <td width="1" class="FieldTitle">:</td>
	                  <td width="150" class="ViewData">&nbsp;</td>
	                  <td width="90" class="FieldTitle"><bean:message key="prodacs.production.machine"/></td>
	                  <td width="1" class="FieldTitle">:</td>
	                  <td class="ViewData">&nbsp;</td>
                	</tr>
                	<tr id="tblProdWrkOrd"> 
	                  <td width="90" class="FieldTitle"><bean:message key="prodacs.workorder.workorder"/></td>
	                  <td width="1" class="FieldTitle">:</td>
	                  <td class="ViewData">&nbsp;</td>
	                  <td class="FieldTitle"><bean:message key="prodacs.job.jobname"/></td>
	                  <td class="FieldTitle">:</td>
	                  <td class="ViewData">&nbsp;</td>
			</tr>
			<tr> 
                  	<td colspan="9" class="FieldTitle">
				<html:radio property="proNmlorRwk" value="normal"/><bean:message key="prodacs.production.normal"/>
                    	<html:radio property="proNmlorRwk" value="rework"/><bean:message key="prodacs.production.rework"/>
                    	<!--html:checkbox property="proIncentive"/><bean:message key="prodacs.production.incentive"/></td>-->
			</tr>
            	</table>
              	<br>
	            <table width="200" cellspacing="0" cellpadding="0" align="right" id="viewOpns">
	            <tr> 
					<td class="TopLnk">[ <a href="#" onmouseover="window.status='View Operations Info'; return true" onmouseout="window.status=''; return true" onClick="viewOpns();">View Operations</a> ]</td>
				</tr>
				</table>
				<br>
              	<table width="100%" cellspacing="0" cellpadding="0" id="tblJobOperDet">
                	<tr> 
	                  <td width="25" class="Header"><input type="checkbox" name="checkbox2" value="checkbox" onclick="chkAll(this);"></td>
	                  <td width="100" class="Header"><bean:message key="prodacs.workorder.jobqtysno"/></td>
	                  <td width="110" class="Header"><bean:message key="prodacs.production.pendingoperationsno"/></td>
					  <td width="110" class="Header"><bean:message key="prodacs.workorder.reworkoperationsno"/></td>
	                  <td width="130" class="Header"><bean:message key="prodacs.production.postedoperationcompletion"/></td>
	                  <td class="Header"><bean:message key="prodacs.production.unpostedoperationcompletion"/></td>
	                  <td width="120" class="Header"><bean:message key="prodacs.production.lastproductiondateshift"/></td>
			</tr>
			<logic:iterate id="bt2" name="vecJobOpnDet" indexId="count">
		    	<tr>
			<%
				if(count.intValue()%2 == 0)
				{
			%>		
			      <td class="TableItems"><input type="checkbox" name="CheckValue" value='<bean:write name="bt2" property="jobStatId"/>' ></td>
		            <td class="TableItems"><input type="text" name="jobQutySno" class="TextBoxFull" readonly value='<bean:write name="bt2" property="jobQtySno"/>'></td>
		            <td class="TableItems"><input type="text" class="TextBoxFull" readonly name="pendOpnSno" value='<bean:write name="bt2" property="pendingOpnSnos"/>'></td>
			   <td class="TableItems"><input type="text" class="TextBoxFull" readonly name="rwkOpnSno" value='<bean:write name="bt2" property="rwkOpns"/>'></td>
		            <td class="TableItems"><bean:write name="bt2" property="postedOpnSnos"/>&nbsp;</td>
		            <td class="TableItems"><bean:write name="bt2" property="unPostedOpnSnos"/>&nbsp;</td>
		            <td class="TableItems"><logic:notEmpty name="bt2" property="lastProdDate">
												<bean:define id="lstPrdDte" name="bt2" property="lastProdDate"/>
												<%= lstPrdDte.toString().substring(0,10) %>
 										  </logic:notEmpty>&nbsp;</td>
			<%
				}
				else
				{
			%>
			   <td class="TableItems2"><input type="checkbox" name="CheckValue" value='<bean:write name="bt2" property="jobStatId"/>'></td>
		            <td class="TableItems2"><input type="text" name="jobQutySno" class="TextBoxFull" readonly value='<bean:write name="bt2" property="jobQtySno"/>'></td>
		            <td class="TableItems2"><input type="text" name="pendOpnSno" class="TextBoxFull" readonly value='<bean:write name="bt2" property="pendingOpnSnos"/>'></td>
					<td class="TableItems2"><input type="text" class="TextBoxFull" readonly name="rwkOpnSno" value='<bean:write name="bt2" property="rwkOpns"/>'></td>
		            <td class="TableItems2"><bean:write name="bt2" property="postedOpnSnos"/>&nbsp;</td>
		            <td class="TableItems2"><bean:write name="bt2" property="unPostedOpnSnos"/>&nbsp;</td>
		            <td class="TableItems2"><logic:notEmpty name="bt2" property="lastProdDate">
												 <bean:define id="lstPrdDte" name="bt2" property="lastProdDate"/>
												 <%= lstPrdDte.toString().substring(0,10) %>
											</logic:notEmpty>&nbsp;</td>
			<%
				}
			%>
			</tr>
   	 		</logic:iterate>
              	</table>
              	<br>
              	<table width="100%" cellspacing="0" cellpadding="0">
                	<tr> 
                  	<td class="FieldTitle"><bean:message key="prodacs.production.startoperation"/><span class="mandatory">*</span>
                    	<html:text property="proStartOperation" onkeypress="return isNumber1(this);" onkeyup="return isNumber1(this);" maxlength="10" styleClass="TextBox" size="15"/>
                    	<bean:message key="prodacs.production.endoperation"/><span class="mandatory">*</span>
                    	<html:text property="proEndOperation" onkeypress="return isNumber1(this);" onkeyup="return isNumber1(this);" maxlength="10" styleClass="TextBox" size="15"/></td>
                	</tr>
              	</table>
              	<br>
              	<table width="100%" cellpadding="0" cellspacing="0" id="BtnBgLft">
                	<tr> 
                  <td><html:button property="proNextBtn3" styleClass="Button" onclick="hideNshowEmpDetails();">Next</html:button></td>
                	</tr>
              	</table>
		</td>
			</tr>
          	<tr id="jobQty" style="display:none"> 
              <td style="border-top:solid 3px #CCCCCC; text-align:right"><A HREF="javascript:showHide(document.getElementById('wrkOrder'), document.getElementById('prodLayImg3'))"><img src='<bean:message key="context"/>/images/pal_hide.gif' id="prodLayImg3" border="0"></A></td>
			</tr>
			<tr>
			  <td>
				<table width="100%" cellpadding="0" cellspacing="0" id="tblJobQty">
                	<tr id="tblProdShft3" style="display:none"> 
	                  <td width="90" class="FieldTitle"><bean:message key="prodacs.job.date"/></td>
	                  <td width="1" class="FieldTitle">:</td>
	                  <td width="150" class="ViewData">&nbsp;</td>
	                  <td width="50" class="FieldTitle"><bean:message key="prodacs.production.shift"/></td>
	                  <td width="1" class="FieldTitle">:</td>
	                  <td width="100" class="ViewData">&nbsp;</td>
	                  <td width="65" class="FieldTitle"><bean:message key="prodacs.production.machine"/></td>
	                  <td width="1" class="FieldTitle">:</td>
	                  <td class="ViewData">&nbsp;</td>
                	</tr>
                	<tr id="tblProdWrkOrd2"> 
	                  <td class="FieldTitle"><bean:message key="prodacs.workorder.workorder"/></td>
	                  <td class="FieldTitle">:</td>
	                  <td class="ViewData">&nbsp;</td>
	                  <td class="FieldTitle"><bean:message key="prodacs.job.jobname"/></td>
	                  <td class="FieldTitle">:</td>
	                  <td class="ViewData">&nbsp;</td>
	                  <td class="FieldTitle"><bean:message key="prodacs.production.worktype"/></td>
	                  <td class="FieldTitle">:</td>
	                  <td class="ViewData">&nbsp;</td>
                	</tr>
                	<tr id="tblProdJobOty"> 
	                  <td class="FieldTitle"><bean:message key="prodacs.workorder.jobqtysno"/></td>
	                  <td class="FieldTitle">:</td>
	                  <td class="ViewData">&nbsp;</td>
	                  <td class="FieldTitle"><bean:message key="prodacs.production.startoperation"/></td>
	                  <td class="FieldTitle">:</td>
	                  <td class="ViewData">&nbsp;</td>
	                  <td class="FieldTitle"><bean:message key="prodacs.production.endoperation"/></td>
	                  <td class="FieldTitle">:</td>
	                  <td class="ViewData">&nbsp;</td>
                	</tr>
                	<tr id="tblProdIncent"> 
	                  <!--td class="FieldTitle"><bean:message key="prodacs.production.incentive"/></td>
	                  <td class="FieldTitle">:</td>
	                  <td class="ViewData"><input type="checkbox" name="checkIncentive" disabled></td-->
	                  <td class="FieldTitle"><bean:message key="prodacs.production.totalhours"/><span class="mandatory">*</span></td>
	                  <td class="FieldTitle">:</td>
	                  <td class="ViewData"><html:text property="proTotalHours" onkeypress="return isNumber(this);" onkeyup="return isNumber(this);" maxlength="6" styleClass="TextBox" size="12"/></td>
			</tr>
             	</table>
              	<br> 
			<table width="100%" cellspacing="0" cellpadding="5" id="tblEmp">
			<tr> 
				<td class="TopLnk"> [ <a href="#" onClick="delRow(document.forms[0]);"><bean:message key="prodacs.common.delete"/></a> ]</td>
			</tr>
			</table>
			<table width="100%" cellspacing="0" cellpadding="0" id="tblEmpDets">
                	<tr>
                  <td width="50%" valign="top" class="ViewData">
				<table width="100%" cellspacing="0" cellpadding="3" id="tblEmpDetMstr">
                      	<tr>
	                        <td width="75" class="Header"><bean:message key="prodacs.production.emptype"/></td>
	                        <td class="Header"><bean:message key="prodacs.production.empname"/></td>
	                        <td width="60" class="Header"><bean:message key="prodacs.production.dutyhrs"/></td>
	                        <td width="50" class="Header"><bean:message key="prodacs.production.othrs"/></td>
	                        <td width="20" class="Header">&nbsp;</td>
                      	</tr>
						<%
							Iterator it = empDetails.keySet().iterator();
							HashMap empType = new HashMap();
							HashMap empDet = new HashMap();
							HashMap empTypRule = new HashMap();
							Vector vec_EmpDets = new Vector();
							int i = 0;
							while (it.hasNext())
							{
								int empTypId = 0;
								String empTypName = "";
								empType = (HashMap) it.next();
								
								//empDet = (HashMap) empDetails.get(empType);
								vec_EmpDets = (Vector) empDetails.get(empType);
								for (int x = 0; x < vec_EmpDets.size(); x++)
								{
									if (x == 0)
										empDet = (HashMap) vec_EmpDets.get(x);
									else
										empTypRule = (HashMap) vec_EmpDets.get(x);
								}
								Iterator it1 = empType.keySet().iterator();
								while (it1.hasNext())
								{
									empTypId = ((Integer)it1.next()).intValue();
									empTypName = empType.get(new Integer(empTypId)).toString();
								}
								/*if (BuildConfig.DMODE)
									System.out.println("EmpTypId :"+empTypId);*/
								pageContext.setAttribute("empDet",empDet);
								pageContext.setAttribute("empTypRule",empTypRule);
								if ((i % 2) == 0)
								{
						%>                     
	      		<tr>
					<td class="TableItems"><input type="text" class="TextBoxFull" readonly name="empTyName" value="<%= empTypName %>"></td>
					<td class="TableItems"><html:select property="proEmployeeName" styleClass="ComboFull">
					<html:option value="0">-- Employee Name --</html:option>
					<html:options collection="empDet" property="key" labelProperty="value"/>
					  </html:select></td>
		                <%
		                 boolean dtHrs = false;
		                 boolean otHrs = false;
  	                 	 dtHrs = ((Boolean)empTypRule.get("DT")).booleanValue();
  	                 	 otHrs = ((Boolean)empTypRule.get("OT")).booleanValue();
	                 	
		                 if (dtHrs == true)
		                 {
		                %>
		                <td class="TableItems"><html:text property="proDutyHrs" onkeypress="return isNumber(this);" onkeyup="return isNumber(this);" maxlength="6" size="7" styleClass="TextBox"/></td>
		                <%
		                }
		                else
		                {
		                %>
		                <td class="TableItems"><html:text property="proDutyHrs" onkeypress="return isNumber(this);" onkeyup="return isNumber(this);" maxlength="6" size="7" styleClass="disabledText" value="0" readonly="true"/></td>
		                <%
		                }
		                if (otHrs == true)
		                {
		                %>
		                <td class="TableItems"><html:text property="proOTHrs" onkeypress="return isNumber(this);" onkeyup="return isNumber(this);" maxlength="6" size="7" styleClass="TextBox"/></td>
		                <%
		                }
		                else
		                {
		                %>
		                <td class="TableItems"><html:text property="proOTHrs" onkeypress="return isNumber(this);" onkeyup="return isNumber(this);" maxlength="6" size="7" styleClass="disabledText" value="0" readonly="true"/></td>
		                <%
		                }
		                %>
		                <td class="TableItems"><img src='<bean:message key="context"/>/images/add.gif' width="20" height="20" border="0" value="<%= i+1 %>" onMouseOver="this.style.cursor='hand'" onclick="transEmpDet(this.value);"></td>
					<td><input type="hidden" name="empTypId" value="<%= empTypId %>"></td>
				</tr>
<%
			}
			else
			{
%>
				<tr>
					<td class="TableItems2"><input type="text" class="TextBoxFull" readonly name="empTyName" value="<%= empTypName %>"></td>
					<td class="TableItems2"><html:select property="proEmployeeName" styleClass="ComboFull">
					    <html:option value="0">-- Employee Name --</html:option>
					    <html:options collection="empDet" property="key" labelProperty="value"/>
					  </html:select></td>
						<%
		                 boolean dtHrs = false;
		                 boolean otHrs = false;
  	                 	 dtHrs = ((Boolean)empTypRule.get("DT")).booleanValue();
  	                 	 otHrs = ((Boolean)empTypRule.get("OT")).booleanValue();

						if (dtHrs == true)
						{
						%>
						<td class="TableItems2"><html:text property="proDutyHrs" onkeypress="return isNumber(this);" onkeyup="return isNumber(this);" maxlength="6" size="7" styleClass="TextBox"/></td>
						<%
						}
						else
						{
						%>
		                <td class="TableItems2"><html:text property="proDutyHrs" onkeypress="return isNumber(this);" onkeyup="return isNumber(this);" maxlength="6" size="7" styleClass="disabledText" value="0" readonly="true"/></td>
						<%
						}
						if (otHrs == true)
						{
						%>
		                <td class="TableItems2"><html:text property="proOTHrs" onkeypress="return isNumber(this);" onkeyup="return isNumber(this);" maxlength="6" size="7" styleClass="TextBox"/></td>
						<%
						}
						else
						{
						%>
						<td class="TableItems2"><html:text property="proOTHrs" onkeypress="return isNumber(this);" onkeyup="return isNumber(this);" maxlength="6" size="7" styleClass="disabledText" value="0" readonly="true"/></td>
						<%
						}
						%>
		                <td class="TableItems2"><img src='<bean:message key="context"/>/images/add.gif' width="20" height="20" border="0" value="<%= i+1 %>" onMouseOver="this.style.cursor='hand'" onclick="transEmpDet(this.value);"></td>
					<td><input type="hidden" name="empTypId" value="<%= empTypId %>"></td>
				</tr>
<%			
			}
			i++;
		}
%>                       
                  	</table>
                  </td>
                  <td valign="top" class="ViewData">
				  <div style="height:80;overflow:auto;">
				  <table width="100%" cellspacing="0" cellpadding="3">
				  <tr> 
				    <td width="25" class="Header"><input type="checkbox" name="ckAll" onclick="headCkAll(this);"></td>
					<td width="75" class="Header"><bean:message key="prodacs.production.emptype"/></td>
					<td class="Header"><bean:message key="prodacs.production.empname"/></td>
					<td width="45" class="Header"><bean:message key="prodacs.production.dutyhrs"/></td>
					<td width="35" class="Header"><bean:message key="prodacs.production.othrs"/></td>
				  </tr>
				  </table>
				  <table width="100%" cellspacing="0" cellpadding="0" id="tblEmpDetails">
				  <tr> 
    			    		<td width="28" class="TableItems"><input type="checkbox" name="ckdCkBx"></td>
					<td width="78" class="TableItems"><input type="text" class="TextBoxFull" readonly name="empTypName"></td>
					<td class="TableItems"><input type="text" class="TextBoxFull" readonly name="empName"></td>
					<td width="48" class="TableItems"><input type="text" class="TextBoxFull" readonly name="empDutyHrs"></td>
					<td width="38" class="TableItems"><input type="text" class="TextBoxFull" readonly name="empOTHrs"></td>
					<td><input type="hidden" name="hidEmpTypId"></td>
					<td><input type="hidden" name="hidEmpId"></td>
				  </tr>
				  </table>
                    	</div>
				  <br>
				  <table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
		              <tr>
		              	<td><input type='button' class="Button" onclick="enterProDet();" value='Update'/></td>
		              </tr>
		              </table>
                  </td>
			</tr>
			</table>
		</td>
          	</tr>
        	</table>
      </td>
    	</tr>
  	</table>
</html:form>
</body>
</html:html>
