
<%@ page language = "java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri = "/WEB-INF/struts-logic.tld" prefix = "logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<jsp:useBean id="frm" class="com.savantit.prodacs.presentation.tableadmin.production.RadialProductionEditForm" />
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
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<%@ page import="com.savantit.prodacs.facade.SessionRadlProductionDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionRadlProductionDetailsManager"%>
<%@ page import="com.savantit.prodacs.businessimplementation.production.RadialProductionDetails"%>
<%@ page import="com.savantit.prodacs.businessimplementation.production.ProductionJobDetails" %>
<%@ page import="com.savantit.prodacs.businessimplementation.production.EmployeeDtyOtDetails" %>
<%@ page import="com.savantit.prodacs.businessimplementation.securityadmin.UserAuthDetails"%>
<useradmin:userrights resource="1019"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("Radial Production Edit Form");
	HashMap empDetails = new HashMap(); /* For Employee Details and Type Details */
	RadialProductionDetails objRadialProductionDetails = new RadialProductionDetails();
	ProductionJobDetails objProductionJobDetails = new ProductionJobDetails();
	UserAuthDetails userDetails = (UserAuthDetails)session.getAttribute("##userRights##");
	String userName = userDetails.getUserId();
	try
	{
		EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
		
		/* 	Setting the JNDI name and Environment 	*/
	   	obj.setJndiName("SessionRadlProductionDetailsManagerBean");
   		obj.setEnvironment();

		/* 	Creating the Home and Remote Objects 	*/
		SessionRadlProductionDetailsManagerHome rdlProHomeObj = (SessionRadlProductionDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionRadlProductionDetailsManagerHome.class);
		SessionRadlProductionDetailsManager rdlProductionObj = (SessionRadlProductionDetailsManager)PortableRemoteObject.narrow(rdlProHomeObj.create(),SessionRadlProductionDetailsManager.class);

		/* Object to Radial Production Details */
		objRadialProductionDetails = rdlProductionObj.getRadlProductionDetails(frm.getId());
		
		/* Object to Radial Production JobDetails */
		objProductionJobDetails = (ProductionJobDetails) objRadialProductionDetails.getObjProductionJobDetails();
		
		if (frm.getFormAction().equalsIgnoreCase("modify"))
		{
			if (BuildConfig.DMODE)
				System.out.println("Modify Inside");
			frm.setHidWrkOrdId(objRadialProductionDetails.getWoId()+"");
			frm.setHidWrkOrdJobId(objRadialProductionDetails.getWoJbId()+"");
			//frm.setProIncentive(objRadialProductionDetails.isRadlIncntvFlg()+"");
			//System.out.println("Incentive: "+ frm.getProIncentive());
		}
		
		/* For Shift */
		HashMap shiftDefnNameList = rdlProductionObj.getShiftDefnNameList();
		pageContext.setAttribute("shiftDefnNameList",shiftDefnNameList);
		
		/* For Machine */
		HashMap machineDet = rdlProductionObj.getAllRadialMachines();
		pageContext.setAttribute("machineDet",machineDet);
		
		
		//HashMap dateShift = new HashMap();
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
		
			shiftDefnNameList = rdlProductionObj.getShiftDefnNameList(gc.getTime());
			if (BuildConfig.DMODE)
				System.out.println("Shifts for Date(HM) :"+shiftDefnNameList);
			pageContext.setAttribute("shiftDefnNameList",shiftDefnNameList);
		}
		else
		{
			pageContext.setAttribute("shiftDefnNameList",shiftDefnNameList);
		}
		
		//HashMap shiftMachs = new HashMap();
		if ((!frm.getProShift().equalsIgnoreCase("0")) && (!frm.getProDate().trim().equalsIgnoreCase("")))
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
		
			machineDet = rdlProductionObj.getAllRadlMachines(ge.getTime(),Integer.parseInt(frm.getProShift()));
			if (BuildConfig.DMODE)
				System.out.println("Machines(HM):"+machineDet);
			pageContext.setAttribute("machineDet",machineDet);
		}
		else
		{
			pageContext.setAttribute("machineDet",machineDet);
		}
		
		/* For Loading WorkOrder */
		HashMap workOrderDet = rdlProductionObj.getWorkOrderList();
		if (BuildConfig.DMODE)
			System.out.println("WO ID:"+frm.getHidWrkOrdId());
		pageContext.setAttribute("workOrderDet",workOrderDet);
		
		/* Loading Job Details by giving WorkOrder Id */
		Vector vecJobDet = new Vector();
		if (frm.getHidWrkOrdId() != "0")
		{
			vecJobDet = rdlProductionObj.getProdnJobDetailsForUpdateByWorkOrder(Integer.parseInt(frm.getHidWrkOrdId()),0,frm.getId(),0,0,0);
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
			vecJobOpnDet = rdlProductionObj.getProdnJobOperationDetailsForUpdate(Integer.parseInt(frm.getHidWrkOrdJobId()),0,frm.getId());
			pageContext.setAttribute("vecJobOpnDet",vecJobOpnDet);
		}
		else
		{
			pageContext.setAttribute("vecJobOpnDet",vecJobOpnDet);
		}
		
		/* Loading EmployeeType and Employees */
		empDetails = rdlProductionObj.getAllEmployeesByTypes();
		
		/* Finalizing the minimum required Quantities */
		HashMap minRqdEmp = rdlProductionObj.minReqdEmployees();
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
		if (BuildConfig.DMODE)
			System.out.println("Min Qty: "+ minDet);
		
		/* For Material Type */
		HashMap rdlMatlType = rdlProductionObj.getAllMatlTypes();
		pageContext.setAttribute("rdlMatlType",rdlMatlType);
		
		/* For Loading Emp Details */
		Vector vec_empDet = objRadialProductionDetails.getRadlEmpHrsDetails();
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
		if (BuildConfig.DMODE)
			System.out.println("empDetails: "+emp_details);

	}catch (Exception e)
	{
		if (BuildConfig.DMODE)
		{
			e.printStackTrace();
			System.out.println("Problem in Production Edit.");
		}
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
	function init()
	 {
		oPopup = window.createPopup();
	 }

	 function dateSubmit()
	 {
		 var temp = document.forms[0];
		 temp.formAction.value = "dateSubmit";
		 //alert(temp.formAction.value);
		 temp.submit();
	 }
	 
	 function loadMachines()
	 {
		 var temp = document.forms[0];
		 if (temp.proShift.value != "0")
		 {
			 temp.formAction.value = "dateSubmit";
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
</script>
<script language="Javascript" type="text/Javascript">

	var flg = 0;
	function listItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/Production/RadialProductionList.jsp';
		document.forms[0].submit();
	}

	function addItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/Production/RadialProductionAdd.jsp';
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
		//prodShftRow.style.display='block';
		//prodInfo.style.display='none';
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
		temp.hidUserId.value = '<%= userName %>';
		//alert ("Show Count:-"+temp.showCount.value+"-");
		/* Setting the Chkd Job Name */
		var obj = document.getElementById("tblWorkOrdInfo");
		for (var i = 1; i < obj.children(0).children.length; i++)
		{
			if (obj.children(0).children(i).children(0).children(0).checked)
			{
				temp.hidChkdJobName.value = obj.children(0).children(i).children(1).children(0).value;
			}
		}

		if (temp.showCount.value == "")
		{
			prodInfo.style.display='block';
			prodShftRow.style.display='none';
			
			/*var incnt = '<%= frm.getProIncentive() %>';
			if (incnt == 'true')
			{
				temp.proIncentive.checked = true;
			}
			else
			{
				temp.proIncentive.checked = false;
			}*/
		}
		else if (temp.showCount.value == "1")
		{
			prodInfo.style.display='block';
			prodShftRow.style.display='block';

			/* Validation for updating the Date, Shift and Machine Names */
			var obj = document.getElementById("tblProdShft");
			obj.children(2).innerText = temp.proDate.value;
			obj.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;
			obj.children(8).innerText = temp.proMachine.options[temp.proMachine.selectedIndex].text;
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
			obj2.children(5).innerText = temp.hidChkdJobName.value;
			
			temp.proNmlorRwk[0].checked = true; /* Normal Radio is Checked by Default */

			/* Select the Checked Radio Button */
			if (temp.rdWrkOrd.length != undefined)
			{
				temp.rdWrkOrd[temp.hidChkdJobId.value].checked = true;
			}
			else
			{
				temp.rdWrkOrd.checked = true;
			}
		}
		else if (temp.showCount.value == "3")
		{
			jobQty.style.display = 'block';
						
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
			//alert (chkdQty);
			var obj = document.getElementById("tblJobOperDet");
			for (var i = 1; i < obj.children(0).children.length; i++)
			{
				for (var j = 0; j < chkdQty.length; j++)
				{
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
							temp.hidChkdJobName.value = obj.children(0).children(i+1).children(1).innerText;
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
				temp.hidChkdJobName.value = obj.children(0).children(1).children(1).innerText;
				temp.hidChkdJobId.value = 0;
			}
		}
		
		if (flag != 1) /* Not even a single job is selected */
		{
			alert ("U must Select a Job!");
			return false;
		}

		temp.showCount.value = "2"; /* Increment the count value */
		//prodShftRow.style.display='none';
		//wrkOrder.style.display='block';
		temp.submit();
	}

	/* Hiding the JobDetails, JobOperation Details and show the Employee Details */
	function hideNshowEmpDetails()
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
		
		/* Loading the Checked Job Name */
		var obj = document.getElementById("tblWorkOrdInfo");
		for (var i = 1; i < obj.children(0).children.length; i++)
		{
			if (obj.children(0).children(i).children(0).children(0).checked)
			{
				temp.hidChkdJobName.value = obj.children(0).children(i).children(1).children(0).value;
			}
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

				for (var x = start; x <= end; x++) /* Rework Operations can enter only when the WorkType is Rework */
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
		//alert ("totQty: "+ temp.proTotQtySnos.value);

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
		temp.formAction.value = "isRadl";
		flg = 1;
		temp.submit();

		/*temp.showCount.value = "3";
		showJobDet();
		flg = 1;
		wrkOrder.style.display='none';
		jobQty.style.display='block'; 
		self.scrollTo(0, 255);*/
	}

	function transEmpDet(val)
	{
		var temp = document.forms[0];
		var ind = parseInt(val);
		var obj = document.getElementById("tblEmpDetails");
		var obj1 = document.getElementById("tblEmpDetMstr");

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

		/* Total Hours can't be Empty & lessthen duty+ot Hrs*/
		if (temp.proTotalHours.value != "")
		{
			if ((parseInt(obj1.children(0).children(ind).children(2).children(0).value) + parseInt(obj1.children(0).children(ind).children(3).children(0).value)) > temp.proTotalHours.value)
			{

				alert ("Duty Hrs + OT Hrs can't be morethan Total Hours!");
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

		hideNshowEmpDetails(); /**/
		if (flg == 0)
			return false;
		flg = 0;
		var obj = document.getElementById("tblEmpDetails");
		if (temp.proTotalHours.value == "") /* Total hrs can't be empty */
		{
			alert ("U must enter Total Hours!");
			return false;
		}
		
		if (temp.materialType.value == "0") /* Material Type can't be empty */
		{
			alert ("U must choose a MaterialType!");
			return false;
		}
		
		if (temp.rdlDiameter.value == "") /* Radial Diameter can't be empty */
		{
			alert ("U must enter Diameter!");
			return false;
		}

		if (temp.rdlLength.value == "") /* Radial Length can't be empty */
		{
			alert ("U must enter Length!");
			return false;
		}

		if (temp.rdlHoles.value == "") /* No. of Holes can't be empty */
		{
			alert ("U must enter No. of Holes!");
			return false;
		}

		if (temp.rdlPreDiameter.value == "") /* Pre-Diameter can't be empty */
		{
			alert ("U must enter PreDiameter!");
			return false;
		}

		if (temp.rdlDiameter.value <= temp.rdlPreDiameter.value)
		{
			alert ("Diameter value must be higher than PreDiameter value!");
			return false;
		}

		if (temp.rdlCompleteStatus.value == "0") /* Complete Status can't be empty */
		{
			alert ("U must Choose the Complete Status!");
			return false;
		}
		
		/* Remove the Empty values in Employee Details */
		if ((obj.children(0).children(0).children(1).children(0).value == "") &&
			(obj.children(0).children(0).children(2).children(0).value == "") &&
			(obj.children(0).children(0).children(3).children(0).value == ""))
		{
			alert ("Employee Details cannot be Empty!");
			return false;
		}
		
		if ((temp.proTotalHours.value != "") && (temp.proTotalHours.value > 999)) /* Total hrs can't be greater than 1000 */
		{
			alert("Total Hrs is Invalid!");
			temp.proTotalHours.value = "";
			temp.proTotalHours.focus();
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
				return false;
			}
		}

		temp.hidAllEmpDet.value = empDet;
		temp.formAction.value = "update";
		temp.showCount.value = "";
		temp.submit();
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
	
	function loadToHidden()
	{
		document.forms[0].ids.value = document.forms[0].id.value;
		// Changing formAction value to reload the page as it was...
		if (document.forms[0].formAction.value == "viewOpns")
			document.forms[0].formAction.value = "modify";
	}
	
	function modOperation()
	{
		var temp = document.forms[0];
		//alert ("Mod Operation Action: "+temp.formAction.value);

		if ((temp.formAction.value == "modify") || (temp.formAction.value == "update"))
		{
			var totalQty = '<%= objRadialProductionDetails.getRadlQtySnos() %>'; /* Splitting the Total Qty's */
			var arTotalQty = totalQty.split(",");

			var obj = document.getElementById("tblJobOperDet");
			for (var i = 1; i < obj.children(0).children.length; i++)
			{
				for (var j = 0; j < arTotalQty.length; j++)
				{
					if (obj.children(0).children(i).children(1).children(0).value == parseInt(arTotalQty[j]))
					{
						obj.children(0).children(i).children(0).children(0).checked = true; 
					}
				}
			}
			
			var chkdWrkOrdJobId = '<%= objRadialProductionDetails.getWoJbId() %>'; /* To check the Selected Job Name */
			var obj = document.getElementById("tblWorkOrdInfo");
			for(i=1; i<obj.children(0).children.length;i++)
			{
				if(chkdWrkOrdJobId == obj.children(0).children(i).children(0).children(0).value)
				{
					obj.children(0).children(i).children(0).children(0).checked = true;
				}
			}

			var nmlRwk = '<%= objRadialProductionDetails.getRadlWorkType() %>'; /* For Normal Or Rework */
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
			obj2.children(2).innerText = '<%= objRadialProductionDetails.getRadlQtySnos() %>';
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

		if (temp.hidAllEmpDet.value == "") /* If EmpDetails is empty, then exit */
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

	function loadEmp()
	{
		temp = document.forms[0];
		//alert(temp.hidAllEmpDet.value);
		if (temp.hidAllEmpDet.value != "")
		{
			obj = document.getElementById('tblEmpDetails');
			var empDet = (temp.hidAllEmpDet.value).split('^');
			for(var i = 0; i < empDet.length; i++)
			{
				//alert(empDet[i]);
				var empTblDet = empDet[i].split('-');
				//alert(obj.children(0).children(0).children(5).innerHTML);
				if((obj.children.length == 1) && (obj.children(0).children(0).children(5).children(0).value == ""))
				{
					obj.children(0).children(0).children(1).children(0).value = empTblDet[0]; // Emp Typ name 
					obj.children(0).children(0).children(2).children(0).value = empTblDet[1]; // Emp Name  
					obj.children(0).children(0).children(3).children(0).value = empTblDet[2]; // Duty Hrs 
					obj.children(0).children(0).children(4).children(0).value = empTblDet[3]; // OT Hrs 
					obj.children(0).children(0).children(5).children(0).value = empTblDet[4]; // Emp Typ Id
					obj.children(0).children(0).children(6).children(0).value = empTblDet[5]; // Emp Name Id 
				}
				else
				{
					var newNode = obj.children(0).cloneNode(true); 
					obj.appendChild(newNode);
					var len = obj.children.length;
					obj.children(len-1).children(0).children(1).children(0).value = ""; // Emp Typ name 
					obj.children(len-1).children(0).children(2).children(0).value = ""; // Emp Name
					obj.children(len-1).children(0).children(3).children(0).value = ""; // Duty Hrs 
					obj.children(len-1).children(0).children(4).children(0).value = ""; // OT Hrs 
					obj.children(len-1).children(0).children(5).children(0).value = ""; // Emp Typ Id 
					obj.children(len-1).children(0).children(6).children(0).value = ""; // Emp Name Id

					obj.children(len-1).children(0).children(1).children(0).value = empTblDet[0]; // Emp Typ name 
					obj.children(len-1).children(0).children(2).children(0).value = empTblDet[1]; // Emp Name
					obj.children(len-1).children(0).children(3).children(0).value = empTblDet[2]; // Duty Hrs 
					obj.children(len-1).children(0).children(4).children(0).value = empTblDet[3]; // OT Hrs 
					obj.children(len-1).children(0).children(5).children(0).value = empTblDet[4]; // Emp Typ Id 
					obj.children(len-1).children(0).children(6).children(0).value = empTblDet[5]; // Emp Name Id
				}
			}
		}
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
			var features = "toolbars=no,status=no,resizable=no,scrollbars=yes,height=550px,width=550px,top=90px,left=350px";
			window.open('<bean:message key="context"/>/Production/ViewOperationsRadlEdit.jsp?formAction=viewOpns',"ViewOperationsEdit",features);
		}
	}
	
</script>
</head>

<body onLoad="init(); operationView(); loadToHidden(); showJobDet(); modOperation(); modEmpDetails();loadEmp();">
<html:form action="frmRdlProdEdit">
<html:hidden property="formAction"/>
<html:hidden property="id"/>
<html:hidden property="ids"/>
<html:hidden property="minQty" value="<%= frm.getMinQty() %>"/> <!-- Holding the Minimum required no. of emp's -->
<html:hidden property="hidWrkOrdId" value="<%= frm.getHidWrkOrdId() %>"/><!-- Handling the WorkOrder Id for Job Details -->
<html:hidden property="hidWrkOrdJobId" value="<%= frm.getHidWrkOrdJobId() %>"/><!-- Handling the WorkOrder Job Id for Job Operation Details -->
<html:hidden property="showCount"/><!-- Showing the appropriate Table by Count -->
<html:hidden property="hidChkdJobName"/> <!-- value="<%= frm.getHidChkdJobName() %>" Holding the Selected Job name -->
<html:hidden property="hidChkdJobId" /> <!--value="<%= frm.getHidChkdJobId() %>" Holding the Selected Job Id Index -->
<html:hidden property="proTotQtySnos" /> <!-- value="<%= frm.getProTotQtySnos() %>"Holding the Total Qty Sno's -->
<html:hidden property="hidAllEmpDet" value="<%= frm.getHidAllEmpDet() %>"/> <!-- Holding the Employee Details -->
<html:hidden property="hidIsRadl"/>
<html:hidden property="hidWrkOrdJbStIds"/> <!-- Holding the WoJobStatusIds for the operation is Rework Or Not -->
<html:hidden property="hidUserId"/> <!-- Holding the User Name -->
<html:hidden property="hidWoJbId" value="<%= frm.getHidWoJbId() %>"/> <!-- Holding the WorkOrder Job Id -->
<html:hidden property="grpId"/> <!-- Holding the Opn.Group Id -->
<html:hidden property="sno"/> <!-- Holding the Opn.SNo -->
<html:hidden property="name"/> <!-- Holding the Opn.Name -->
<html:hidden property="stdHrs"/> <!-- Holding the Opn. StdHrs -->
<html:hidden property="gpCde"/> <!-- Holding the Group Code -->

	<table width="100%" cellspacing="0" cellpadding="10">
    	<tr> 
      <td><table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
	          <tr>
	            <td><bean:message key="prodacs.radialproduction.radialproduction"/></td>
	          </tr>
          </table>
          <br> 
          <table width="100" cellspacing="0" cellpadding="0" align="right">
	    <tr> 
	 	  <menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New RadialProduction Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1019" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
	 	  <menuconfig:userRights url="#" id="#" onMouseOver="window.status='List RadialProduction Info'; return true"  onMouseOut="window.status=''; return true" resourceId="19" text="[ List ]" classId="TopLnk" onClick="javaScript:listItem();"/>
	    </tr>
          </table>
          <br>
         <table>
		  <tr> 
			<td colspan='2'> <font size="1px" face="Verdana">
				<html:errors/></font>
		   </td>
		  </tr> 
		 </table> 
          
		<table width="100%" cellpadding="0" cellspacing="0">
 		<tr id="prodInfo"> 
            <td>
		<table width="100%" cellspacing="0" cellpadding="0">
                <tr> 
                  <td width="50" class="FieldTitle"><bean:message key="prodacs.job.date"/><span class="mandatory">*</span></td>
                  <td width="1" class="FieldTitle">:</td>
                  <td width="150" class="FieldTitle"><html:text property="proDate" styleClass="TextBox" size="12"/> 
                    <img src='<bean:message key="context"/>/images/calendar.gif' width="16" height="15" align="absmiddle" onClick='show_calendar("proDate",RadialProductionEdit.proDate.value,event.clientX,event.clientY,false);clearAll();' onMouseOver="this.style.cursor='hand'"></td>
                    
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
              </table></td>
            <td valign="top">&nbsp;</td>
          </tr>
          <tr id="prodShftRow" style="display:none"> 
            <td style="border-top:dashed 1px #666666">
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
              <br> <table width="100%" cellpadding="0" cellspacing="0" id="tblWorkOrdInfo">
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
				      <td  class="TableItems"><input type=radio name="rdWrkOrd" value="<bean:write name='bt1' property='woJbId'/>"></td>
			            <td class="TableItems" title="<bean:write name='bt1' property='jobName'/>/<bean:write name='bt1' property='dwgNo'/>/<bean:write name='bt1' property='rvsnNo'/>/<bean:write name='bt1' property='matlType'/>"><input type="text" name="prodJbName" readonly class="TextBoxFull" value='<bean:write name="bt1" property="jobName"/>'></td>
			            <td class="TableItems"><bean:write name="bt1" property="totQty"/>&nbsp;</td>
			            <td class="TableItems"><bean:write name="bt1" property="pendingQtySnos"/>&nbsp;</td>
			            <td class="TableItems"><bean:write name="bt1" property="postedQtySnos"/>&nbsp;</td>
			            <td class="TableItems"><bean:write name="bt1" property="unPostedQtySnos"/>&nbsp;</td>
			            <td class="TableItems"><logic:notEmpty name="bt1" property="lastProdnDate"><bean:define id="lstPrdDte" name="bt1" property="lastProdnDate"/><%= lstPrdDte.toString().substring(0,10) %></logic:notEmpty>&nbsp;</td>
			<%
				}
				else
				{
			%>
				      <td  class="TableItems2"><input type=radio name="rdWrkOrd" value="<bean:write name='bt1' property='woJbId'/>"></td>
			            <td class="TableItems2" title="<bean:write name='bt1' property='jobName'/>/<bean:write name='bt1' property='dwgNo'/>/<bean:write name='bt1' property='rvsnNo'/>/<bean:write name='bt1' property='matlType'/>"><input type="text" name="prodJbName" readonly class="TextBoxFull" value='<bean:write name="bt1" property="jobName"/>'></td>
			            <td class="TableItems2"><bean:write name="bt1" property="totQty"/>&nbsp;</td>
			            <td class="TableItems2"><bean:write name="bt1" property="pendingQtySnos"/>&nbsp;</td>
			            <td class="TableItems2"><bean:write name="bt1" property="postedQtySnos"/>&nbsp;</td>
			            <td class="TableItems2"><bean:write name="bt1" property="unPostedQtySnos"/>&nbsp;</td>
			            <td class="TableItems2"><logic:notEmpty name="bt1" property="lastProdnDate"><bean:define id="lstPrdDte1" name="bt1" property="lastProdnDate"/><%= lstPrdDte1.toString().substring(0,10) %></logic:notEmpty>&nbsp;</td>
			<%
				}
			%>
			</tr>
   	 		</logic:iterate>
              </table>
              <br> <table width="100%" cellpadding="0" cellspacing="0" id="BtnBgLft">
                <tr> 
                  <td><html:button styleClass="Button" property="proNextBtn2" onclick="hideNshowJobOpnDet();">Next</html:button> 
                  </td>
                </tr>
              </table></td>
            <td width="20" valign="top"> <table width="100%" cellpadding="0" cellspacing="0" id="ShowHide">
                <tr> 
                  <td onClick="showHide(document.getElementById('prodInfo'), document.getElementById('prodShftImg'))"><img src='<bean:message key="context"/>/images/hide.gif' width="9" height="7" id="prodShftImg"></td><!--showHide(document.getElementById('tblProdShft'), document.getElementById('tblProdShft2'), document.getElementById('prodShftImg'), document.getElementById('prodShft2Img'))-->
                </tr>
              </table></td>
          </tr>
          <tr id="wrkOrder" style="display:none"> 
            <td style="border-top:dashed 1px #666666"> 
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
			  		<html:radio property="proNmlorRwk" value="normal"/>
                    <bean:message key="prodacs.production.normal"/>
                    <html:radio property="proNmlorRwk" value="rework"/>
                    <bean:message key="prodacs.production.rework"/>
                    <!--html:checkbox property="proIncentive" value="1"/>
                    <bean:message key="prodacs.production.incentive"/></td>-->
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
			            <td class="TableItems"><logic:notEmpty name="bt2" property="lastProdDate"><bean:define id="lstProdDte" name="bt2" property="lastProdDate"/><%= lstProdDte.toString().substring(0,10) %></logic:notEmpty>&nbsp;</td>
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
			            <td class="TableItems2"><logic:notEmpty name="bt2" property="lastProdDate"><bean:define id="lstProdDte1" name="bt2" property="lastProdDate"/><%= lstProdDte1.toString().substring(0,10) %></logic:notEmpty>&nbsp;</td>
			<%
				}
			%>
			</tr>
   	 		</logic:iterate>
              </table>
              <br> <table width="100%" cellspacing="0" cellpadding="0">
                <tr> 
                  <td class="FieldTitle"><bean:message key="prodacs.production.startoperation"/><span class="mandatory">*</span>
                    <html:text property="proStartOperation" onkeypress="return isNumber1(this);" onkeyup="return isNumber1(this);" maxlength="10" styleClass="TextBox" size="15"/>
                    <bean:message key="prodacs.production.endoperation"/><span class="mandatory">*</span>
                    <html:text property="proEndOperation" onkeypress="return isNumber1(this);" onkeyup="return isNumber1(this);" maxlength="10" styleClass="TextBox" size="15"/></td>
                </tr>
              </table>
              <br> <table width="100%" cellpadding="0" cellspacing="0" id="BtnBgLft">
                <tr> 
                  <td><html:button property="proNextBtn3" styleClass="Button" onclick="hideNshowEmpDetails();">Next</html:button> 
                  </td>
                </tr>
              </table></td>
            <td valign="top"><table width="100%" cellpadding="0" cellspacing="0" id="ShowHide">
                <tr> 
                  <td onClick="showHide(document.getElementById('prodInfo'), document.getElementById('prodShft2Img'))"><img src='<bean:message key="context"/>/images/hide.gif' width="9" height="7" id="prodShft2Img"></td>
                </tr>
              </table>
              <table width="100%" cellpadding="0" cellspacing="0" id="ShowHide">
                <tr> 
                  <td onClick="showHide(document.getElementById('prodShftRow'), document.getElementById('prodShftRowImg'))"><img src='<bean:message key="context"/>/images/hide.gif' width="9" height="7" id="prodShftRowImg"></td>
                </tr>
              </table></td>
          </tr>
          <tr id="jobQty" style="display:none"> 
            <td style="border-top:dashed 1px #666666">
			<table width="100%" cellpadding="0" cellspacing="0">
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
                  <td class="FieldTitle"><bean:message key="prodacs.job.materialtype"/><span class="mandatory">*</span></td>
					<td class="FieldTitle">:</td>
					<td colspan="4" class="FieldTitle"><html:select property="materialType" styleClass="Combo">
						<html:option value="0">-- Material Type --</html:option>
						<html:options collection="rdlMatlType" property="key" labelProperty="value"/>
						</html:select></td>
					</tr>
		<tr> 
			<td class="FieldTitle"><bean:message key="prodacs.radialproduction.diameters"/><span class="mandatory">*</span></td>
			<td class="FieldTitle">:</td>
			<td class="FieldTitle"><html:text property="rdlDiameter" styleClass="TextBox" size="12" onkeypress="return isNumber(this);" onkeyup="return isNumber(this);" maxlength="9"/></td>
			<td class="FieldTitle"><bean:message key="prodacs.radialproduction.length"/><span class="mandatory">*</span></td>
			<td class="FieldTitle">:</td>
			<td class="FieldTitle"><html:text property="rdlLength" styleClass="TextBox" size="12" onkeypress="return isNumber(this);" onkeyup="return isNumber(this);" maxlength="9"/></td>
			<td class="FieldTitle"><bean:message key="prodacs.radialproduction.noofholes"/><span class="mandatory">*</span></td>
			<td class="FieldTitle">:</td>
			<td class="FieldTitle"><html:text property="rdlHoles" styleClass="TextBox" size="12" onkeypress="return isNumber1(this);" onkeyup="return isNumber1(this);" maxlength="10"/></td>
		</tr>
		<tr> 
			
			<td class="FieldTitle"><bean:message key="prodacs.radialproduction.prediameter"/><span class="mandatory">*</span></td>
			<td class="FieldTitle">:</td>
			<td class="FieldTitle"><html:text property="rdlPreDiameter" styleClass="TextBox" size="12" onkeypress="return isNumber(this);" onkeyup="return isNumber(this);" maxlength="9"/></td>
			<td class="FieldTitle"><bean:message key="prodacs.radialproduction.completestatus"/><span class="mandatory">*</span></td>
			<td class="FieldTitle">:</td>
			<td class="FieldTitle"><html:select property="rdlCompleteStatus" styleClass="Combo">
			<html:option value="0">-- Status --</html:option>
			<html:option value="1">Yes</html:option>
			<html:option value="2">No</html:option>
			</html:select></td>
		</tr>
            </table>
              <br> 
				<table width="100%" cellspacing="0" cellpadding="5">
				   <tr> 
					<td class="TopLnk">
					[ <a href="#" onClick="delRow(document.forms[0]);"><bean:message key="prodacs.common.delete"/></a> ]</td>
				   </tr>
				</table>

			  <table width="100%" cellspacing="0" cellpadding="0">
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
					System.out.println("EmpTypId :"+empTypId);
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
                  </table></td>

                  <td valign="top" class="ViewData">
				  <div style="height:80;overflow:auto;">
				  <table width="100%" cellspacing="0" cellpadding="3">
				  <tr> 
				    <td width="25" class="Header"><input type="checkbox" name="ckAll" onclick="headCkAll(this);"></td>
					<td width="75" class="Header"><bean:message key="prodacs.production.emptype"/></td>
					<td class="Header"><bean:message key="prodacs.production.empname"/></td>
					<td width="60" class="Header"><bean:message key="prodacs.production.dutyhrs"/></td>
					<td width="50" class="Header"><bean:message key="prodacs.production.othrs"/></td>
				  </tr>
				  </table>
				  <table width="100%" cellspacing="0" cellpadding="0" id="tblEmpDetails">
				  <tr> 
    			    <td width="28" class="TableItems"><input type="checkbox" name="ckdCkBx"></td>
					<td width="78" class="TableItems"><input type="text" class="TextBoxFull" readonly name="empTypName"></td>
					<td class="TableItems"><input type="text" class="TextBoxFull" readonly name="empName"></td>
					<td width="63" class="TableItems"><input type="text" class="TextBoxFull" readonly name="empDutyHrs"></td>
					<td width="53" class="TableItems"><input type="text" class="TextBoxFull" readonly name="empOTHrs"></td>
					<td><input type="hidden" name="hidEmpTypId"></td>
					<td><input type="hidden" name="hidEmpId"></td>
				  </tr>
                  </table>
                    </div></td>
				  <br><table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
	            <tr><td><html:button property="proNextBtn4" styleClass="Button" onclick="enterProDet();">Update</html:button></td></tr></table>

				 <td valign="top"><table width="100%" cellpadding="0" cellspacing="0" id="ShowHide">
                <tr> 
                  <td onClick="showHide(document.getElementById('prodInfo'), document.getElementById('prodShft3Img'))"><img src='<bean:message key="context"/>/images/hide.gif' name="prodShft3Img" width="9" height="7" id="prodShft3Img"></td>
                </tr>
              </table>
              <table width="100%" cellpadding="0" cellspacing="0" id="ShowHide">
                <tr> 
                  <td onClick="showHide(document.getElementById('prodShftRow'), document.getElementById('prodShftRow2Img'))"><img src='<bean:message key="context"/>/images/hide.gif' width="9" height="7" id="prodShftRow2Img"></td>
                </tr>
              </table>
              <table width="100%" cellpadding="0" cellspacing="0" id="ShowHide">
                <tr>
                  <td onClick="showHide(document.getElementById('wrkOrder'), document.getElementById('prodShftRow3Img'))"><img src='<bean:message key="context"/>/images/hide.gif' width="9" height="7" id="prodShftRow3Img"></td>
                </tr>
              </table></td>

                </tr>

              </table></td>
          </tr>
        </table></td>
    </tr>
  </table>

</html:form>
</body>
</html:html>
