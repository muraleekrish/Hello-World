<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<jsp:useBean id="frm" class="com.savantit.prodacs.presentation.tableadmin.production.PayOutProductionAddForm" />
<jsp:setProperty name="frm" property="*" /> 
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.GregorianCalendar"%>
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.StringTokenizer"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<%@ page import="com.savantit.prodacs.facade.SessionPopDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionPopDetailsManager"%>
<%@ page import="com.savantit.prodacs.businessimplementation.production.PopDetails"%>
<%@ page import="com.savantit.prodacs.businessimplementation.production.EmployeeDtyOtDetails"%>
<%@ page import="com.savantit.prodacs.businessimplementation.securityadmin.UserAuthDetails"%>
<useradmin:userrights resource="1020"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("PayOut Production Add");
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	Vector vec_Success = new Vector();
	Vector vec_EmpFail = new Vector();
	Vector vec_DutyFail = new Vector();
	String prodList = "";
	
	HashMap hm_Result = new HashMap();
	HashMap popEmpList = new HashMap();
	HashMap dtOtEmpList = new HashMap();
	UserAuthDetails userDetails = (UserAuthDetails)session.getAttribute("##userRights##");
	String userName = userDetails.getUserId();
	try
	{
		if (BuildConfig.DMODE)
			System.out.println("POP Add Starts.");
		
		/* 	Setting the JNDI name and Environment 	*/
	   	obj.setJndiName("SessionPopDetailsManagerBean");
   		obj.setEnvironment();

		/* 	Creating the Home and Remote Objects 	*/
		SessionPopDetailsManagerHome popHomeObj = (SessionPopDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionPopDetailsManagerHome.class);
		SessionPopDetailsManager popObj = (SessionPopDetailsManager)PortableRemoteObject.narrow(popHomeObj.create(),SessionPopDetailsManager.class);
		
		/* For Shift List 
		HashMap popShiftList = popObj.getShiftDefnNameList();
		pageContext.setAttribute("popShiftList",popShiftList);*/

		HashMap dateShift = new HashMap();
		if (!frm.getPopFromDate().trim().equalsIgnoreCase(""))
		{
			StringTokenizer stDate = new StringTokenizer(frm.getPopFromDate().trim(),"-");
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
		
			dateShift = popObj.getShiftDefnNameList(gc.getTime());
			if (BuildConfig.DMODE)
				System.out.println("Shifts for Date(HM) :"+dateShift);
			pageContext.setAttribute("dateShift",dateShift);
		}
		else
		{
			pageContext.setAttribute("dateShift",dateShift);
		}
		
		/* For Employee Type */
		HashMap popEmpType = popObj.getAllEmployeeTypes();
		pageContext.setAttribute("popEmpType",popEmpType);
		
		/* Loading Employee's List */
		if (!frm.getHidEmpTypId().equalsIgnoreCase(""))
		{
			popEmpList = popObj.getAllEmployeesByType(Integer.parseInt(frm.getHidEmpTypId()));
			dtOtEmpList = popObj.getAllEmployeesByTypes(Integer.parseInt(frm.getHidEmpTypId()));
			if (BuildConfig.DMODE)
				System.out.println("dtOtEmpList [HM]:"+dtOtEmpList);
			pageContext.setAttribute("popEmpList",popEmpList);
			pageContext.setAttribute("dtOtEmpList",dtOtEmpList);
		}
		else
		{
			pageContext.setAttribute("popEmpList",popEmpList);
			pageContext.setAttribute("dtOtEmpList",dtOtEmpList);
		}
	}catch (Exception e)
	{
		if (BuildConfig.DMODE)
		{
			e.printStackTrace();
			System.out.println("Problem in POP Add.jsp");
		}
	}
%>
<html:html>
<head>
<title><bean:message key="prodacs.production.payoutsideproduction"/></title>
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
	 
</script>

<script>
function chkAll(frmPage) // Check All Script for Dynamic Table List
	{
		table = dvPayOutProd.children[0].children[0];
		if(frmPage.CheckAll.checked == true)
			{
//				alert(table.innerHTML);
				for(i=0;i<table.children.length;i++)
					{
						table.children[i].children[0].children[0].checked=true;
						if(table.children[i].children[0].className == "InValid")
							{
								table.children[i].children[0].children[0].checked=false;
							}
					}
			}
		else
			{
				for(i=0;i<table.children.length;i++)
				{
					table.children[i].children[0].children[0].checked=false;
				}
			}
	}
</script>
<script>

	function listItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/Production/PayOutProductionList.jsp';
		document.forms[0].submit();
	}
		
	function addItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/Production/PayOutProductionAdd.jsp';
		document.forms[0].submit();
	}
		
	/* Loading the Emp Details when the page is submitted. */
	function loadEmpList()
	{
		var temp = document.forms[0];
		temp.hidEmpTypId.value = temp.employeeType.options[temp.employeeType.selectedIndex].value;
		temp.formAction.value = "load";
		loadEmpDet(); // Load the Employee Details to a variable.
		loadPopDet();
		temp.submit();
	}

	/* The POP Frame show only when the Shift is selected */
	function showPop()
	{
		var temp = document.forms[0];
		temp.hidUserId.value = '<%= userName %>';
		if (temp.showCount.value != "")
		{
			if (temp.machineShift.value == "0")
			{
				tblPayOutProdList.style.display='none';
			}
			else
			{
				tblPayOutProdList.style.display='block';
			}
		}
	}

	/* Transfering the Emp Details from the Master to Temporary Table */
	function transEmpDet()
	{
		var temp = document.forms[0];
		var obj = document.getElementById("tblEmpDetails");

		/* Emp Type Name can't be Empty */
		if (temp.employeeType.options[temp.employeeType.selectedIndex].value == "0")
		{
			alert ("Employee Type can't be Empty!");
			return false;
		}

		/* Emp Name can't be Empty */
		if (temp.employeeName.options[temp.employeeName.selectedIndex].value == "0")
		{
			alert ("Employee Name can't be Empty!");
			return false;
		}

		/* Duty Hrs can't be Empty */
		if (((parseFloat(temp.dutyHrs.value) + parseFloat(temp.otHrs.value)) == 0) || ((temp.dutyHrs.value + temp.otHrs.value) == "") || ((temp.dutyHrs.value + temp.otHrs.value) == 0) ||
			((temp.dutyHrs.value == "") && (temp.otHrs.value == "")))
		{
			alert ("Duty + OT Hrs can't be Zero!");
			return false;
		}

		/* Employee name reduntancy not allowed */
		for (var i = 0; i < obj.children.length; i++)
		{
			if (obj.children(i).children(0).children(6).children(0).value == temp.employeeName.value)
			{
				alert ("The Employee '"+temp.employeeName.options[temp.employeeName.selectedIndex].text+"' details already shifted.");
				return false;
			}
		}

		if ((obj.children.length == "1") && (obj.children(0).children(0).children(1).children(0).value == ""))
		{
			obj.children(0).children(0).children(1).children(0).value = temp.employeeType.options[temp.employeeType.selectedIndex].text; // Emp Typ Name
			obj.children(0).children(0).children(2).children(0).value = temp.employeeName.options[temp.employeeName.selectedIndex].text; // Emp Name 
			obj.children(0).children(0).children(3).children(0).value = (temp.dutyHrs.value == "")?(0):(temp.dutyHrs.value); // Duty Hours 
			obj.children(0).children(0).children(4).children(0).value = (temp.otHrs.value == "")?(0):(temp.otHrs.value); // OT Hrs
			obj.children(0).children(0).children(5).children(0).value = temp.employeeType.options[temp.employeeType.selectedIndex].value; // Emp Typ Id
			obj.children(0).children(0).children(6).children(0).value = temp.employeeName.options[temp.employeeName.selectedIndex].value; // Emp Id
		}
		else
		{
			var newNode = obj.children(0).cloneNode(true);
			obj.appendChild(newNode);
			var len = obj.children.length;

			obj.children(len-1).children(0).children(1).children(0).value = temp.employeeType.options[temp.employeeType.selectedIndex].text;
			obj.children(len-1).children(0).children(2).children(0).value = temp.employeeName.options[temp.employeeName.selectedIndex].text;
			obj.children(len-1).children(0).children(3).children(0).value = (temp.dutyHrs.value == "")?(0):(temp.dutyHrs.value); // Duty Hours 
			obj.children(len-1).children(0).children(4).children(0).value = (temp.otHrs.value == "")?(0):(temp.otHrs.value); // OT Hrs
			obj.children(len-1).children(0).children(5).children(0).value = temp.employeeType.options[temp.employeeType.selectedIndex].value;
			obj.children(len-1).children(0).children(6).children(0).value = temp.employeeName.options[temp.employeeName.selectedIndex].value;
		}
		
		/* Clear the Fields */
		temp.dutyHrs.value = "";
		temp.otHrs.value = "";
		temp.employeeName.selectedIndex = 0;
	}

	/* To Perform the deletion of a Row in Emp Table */
	function delRow()
	{
		var temp = document.forms[0];
		var obj = document.getElementById("tblEmpDetails");
		if (obj.children.length > 1)
		{
			for (var i = 0 ; i < obj.children.length; i++)
			{
				if (temp.chkBx[i].checked)
				{
					obj.removeChild(obj.children(i));
					i = 0;
				}
			}
			if (obj.children.length == 1)
			{
				if (temp.chkBx[0].checked)
				{
					obj.children(0).children(0).children(1).children(0).value = "";
					obj.children(0).children(0).children(2).children(0).value = "";
					obj.children(0).children(0).children(3).children(0).value = "";
					obj.children(0).children(0).children(4).children(0).value = "";
					obj.children(0).children(0).children(5).children(0).value = "";
					obj.children(0).children(0).children(6).children(0).value = "";

					temp.chkBx[0].checked = false;
					temp.checkAll.checked = false;
				}
			}

		}
		else if (obj.children.length == 1)
		{
			if (temp.chkBx.checked)
			{
				obj.children(0).children(0).children(1).children(0).value = "";
				obj.children(0).children(0).children(2).children(0).value = "";
				obj.children(0).children(0).children(3).children(0).value = "";
				obj.children(0).children(0).children(4).children(0).value = "";
				obj.children(0).children(0).children(5).children(0).value = "";
				obj.children(0).children(0).children(6).children(0).value = "";

				temp.chkBx.checked = false;
				temp.checkAll.checked = false;
			}
		}

		loadEmpDet();
	}

	/* To check all the Checkbox in Employee Details */
	function chkAll() 
	{
		var temp = document.forms[0];
		var obj = document.getElementById("tblEmpDetails");
		if (obj.children.length > 1)
		{
			if (temp.checkAll.checked)
			{
				for (var i = 0; i < temp.chkBx.length; i++)
				{
					temp.chkBx[i].checked = true;
				}
			}
			else
			{
				for (var i = 0; i < temp.chkBx.length; i++)
				{
					temp.chkBx[i].checked = false;
				}
			}
		}
		else if (temp.checkAll.checked)
		{
			temp.chkBx.checked = true;
		}
		else
		{
			temp.chkBx.checked = false;
		}
	}

	/* Assing the table values into a variable */
	function loadEmpDet()
	{
		var temp = document.forms[0];
		var obj = document.getElementById("tblEmpDetails");

		var empDet = "";
		temp.hidEmpDet.value = "";
		if ((obj.children(0).children(0).children(1).children(0).value == "") &&
			(obj.children(0).children(0).children(2).children(0).value == ""))
		{
			return false;
		}
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
				empDet = empDet + "-" + obj.children(0).children(0).children(2).children(0).value; // Emp Name 

				if (obj.children(0).children(0).children(3).children(0).value == "")
				{	t1 = 0;	}
				else {	t1 = obj.children(0).children(0).children(3).children(0).value;	}

				empDet = empDet + "-" + t1; // Duty Hrs 

				if (obj.children(0).children(0).children(4).children(0).value == "")
				{	t2 = 0;	}
				else {	t2 = obj.children(0).children(0).children(4).children(0).value;	}

				empDet = empDet + "-" + t2; // OT Hrs 
				empDet = empDet + "-" + obj.children(0).children(0).children(5).children(0).value; // Emp Typ Id 
				empDet = empDet + "-" + obj.children(0).children(0).children(6).children(0).value; // Emp Name Id 
			}
			else
			{
				empDet = empDet + "" + obj.children(i).children(0).children(1).children(0).value; // EmpTyp name 
				empDet = empDet + "-" + obj.children(i).children(0).children(2).children(0).value; // Emp Name 

				if (obj.children(i).children(0).children(3).children(0).value == "")
				{	t1 = 0;	}
				else {	t1 = obj.children(i).children(0).children(3).children(0).value;	}

				empDet = empDet + "-" + t1; // Duty Hrs 

				if (obj.children(i).children(0).children(4).children(0).value == "")
				{	t2 = 0;	}
				else {	t2 = obj.children(i).children(0).children(4).children(0).value;	}

				empDet = empDet + "-" + t2; // OT Hrs 

				empDet = empDet + "-" + obj.children(i).children(0).children(5).children(0).value; // Emp Typ Id 
				empDet = empDet + "-" + obj.children(i).children(0).children(6).children(0).value; // Emp Id 
			}
			i++;
		}
		temp.hidEmpDet.value = empDet;
		//alert ("EmpDet: "+temp.hidEmpDet.value);
	}
	
	/* Write the values once again in to the table */
	function loadEmpDetails()
	{
		var temp = document.forms[0];
		var obj = document.getElementById("tblEmpDetails");

		if (temp.hidEmpDet.value == "")
		{
			//tblPayOutProdList.style.display = 'none';
			return false;
		}
		var arEmpDet = (temp.hidEmpDet.value).split("^");

		for (var i = 0; i < arEmpDet.length; i++)
		{
			var arEmpPrnlDet = arEmpDet[i].split("-");
			if (i == 0)
			{
				obj.children(i).children(0).children(1).children(0).value = arEmpPrnlDet[0]; // Type name
				obj.children(i).children(0).children(2).children(0).value = arEmpPrnlDet[1]; // Emp Name
				obj.children(i).children(0).children(3).children(0).value = arEmpPrnlDet[2]; // Duty
				obj.children(i).children(0).children(4).children(0).value = arEmpPrnlDet[3]; // OT
				obj.children(i).children(0).children(5).children(0).value = arEmpPrnlDet[4]; // Type Id
				obj.children(i).children(0).children(6).children(0).value = arEmpPrnlDet[5]; // Emp Id
			}
			else
			{
				/* Inserting a new Row */
				var newNode = obj.children(0).cloneNode(true);
				obj.appendChild(newNode);
				var len = obj.children.length;
				
				/* Removing the Previous values */
				obj.children(len-1).children(0).children(1).children(0).value = ""; // Type name
				obj.children(len-1).children(0).children(2).children(0).value = ""; // Emp Name
				obj.children(len-1).children(0).children(3).children(0).value = ""; // Duty
				obj.children(len-1).children(0).children(4).children(0).value = ""; // OT
				obj.children(len-1).children(0).children(5).children(0).value = ""; // Type Id
				obj.children(len-1).children(0).children(6).children(0).value = ""; // Emp Id

				/* Enter the New values */
				obj.children(len-1).children(0).children(1).children(0).value = arEmpPrnlDet[0]; // Type name
				obj.children(len-1).children(0).children(2).children(0).value = arEmpPrnlDet[1]; // Emp Name
				obj.children(len-1).children(0).children(3).children(0).value = arEmpPrnlDet[2]; // Duty
				obj.children(len-1).children(0).children(4).children(0).value = arEmpPrnlDet[3]; // OT
				obj.children(len-1).children(0).children(5).children(0).value = arEmpPrnlDet[4]; // Type Id
				obj.children(len-1).children(0).children(6).children(0).value = arEmpPrnlDet[5]; // Emp Id
			}
		}
	}

	/* Performing the add operations and check the mandatory values */
	function addPoPDet()
	{
		var temp = document.forms[0];
		loadEmpDet(); // To hold the Emp Details in a variable.
		//alert (temp.hidEmpDet.value);
		if (temp.popFromDate.value == "") /* Pop Date can't be empty */
		{
			alert ("POP Date is Mandatory!");
			return false;
		}
		
		/* Reason can't be empty */
		if (temp.popReason.value == "")  
		{
			alert ("POP Reason is Mandatory!");
			return false;
		}

		if (temp.machineShift.value == "0")
		{
			alert ("POP Shift is Mandatory!");
			return false;
		}

		if (temp.hidEmpDet.value == "")
		{
			alert ("No Records found in Pay Outside Production Employee Details!");
			return false;
		}

		/* Move the values to Buffer */
		var obj = document.getElementById('tblBufferList');
		tblBuffer.style.display = 'block';
		if ((obj.children.length == 1) && (obj.children(0).children(0).children(2).children(0).value == ""))
		{
			obj.children(0).children(0).children(1).children(0).value = obj.children.length;
			obj.children(0).children(0).children(2).children(0).value = temp.popFromDate.value;
			obj.children(0).children(0).children(3).children(0).value = temp.machineShift.options[temp.machineShift.selectedIndex].text;
			obj.children(0).children(0).children(4).children(0).value = temp.popReason.value;
			obj.children(0).children(0).children(5).children(0).value = temp.machineShift.value;
			obj.children(0).children(0).children(6).children(0).value = temp.hidEmpDet.value;
		}
		else
		{
			var newNode = obj.children(0).cloneNode(true);
			obj.appendChild(newNode);
			var len = obj.children.length;

			obj.children(len-1).children(0).children(1).children(0).value = "";
			obj.children(len-1).children(0).children(2).children(0).value = "";
			obj.children(len-1).children(0).children(3).children(0).value = "";
			obj.children(len-1).children(0).children(4).children(0).value = "";
			obj.children(len-1).children(0).children(5).children(0).value = "";
			obj.children(len-1).children(0).children(6).children(0).value = "";

			obj.children(len-1).children(0).children(1).children(0).value = obj.children.length;
			obj.children(len-1).children(0).children(2).children(0).value = temp.popFromDate.value;
			obj.children(len-1).children(0).children(3).children(0).value = temp.machineShift.options[temp.machineShift.selectedIndex].text;
			obj.children(len-1).children(0).children(4).children(0).value = temp.popReason.value;
			obj.children(len-1).children(0).children(5).children(0).value = temp.machineShift.value;
			obj.children(len-1).children(0).children(6).children(0).value = temp.hidEmpDet.value;
		}
		
		flag = 1;
		//temp.popFromDate.value = "";
		temp.popReason.value = "";
		//temp.machineShift.selectedIndex = 0;
		temp.employeeType.selectedIndex = 0;
		temp.employeeName.selectedIndex = 0;
		
		var obj = document.getElementById('tblEmpDetails'); // To clear Emp Details
		temp.hidEmpDet.value = "";
		if (obj.children.length > 1)
		{
			for (var k = 0 ; k < obj.children.length; k++)
			{
				obj.removeChild(obj.children(k));
				k = 0;
			}
			if (obj.children.length == 1)
			{
				obj.children(0).children(0).children(1).children(0).value = "";
				obj.children(0).children(0).children(2).children(0).value = "";
				obj.children(0).children(0).children(3).children(0).value = "";
				obj.children(0).children(0).children(4).children(0).value = "";
				obj.children(0).children(0).children(5).children(0).value = "";
				obj.children(0).children(0).children(6).children(0).value = "";
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
		}
		tblPayOutProdList.style.display = 'none';
		loadPopDet();
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

	function chkPostRule()
	{
		var temp = document.forms[0];
		if (temp.popFromDate.value == "")
		{
			alert ("POP Date is Mandatory!");
			return false;
		}
		
		if (temp.popReason.value == "")
		{
			alert ("POP Reason is Mandatory!");
			return false;
		}

		if (temp.machineShift.value == "0")
		{
			alert ("POP Shift is Mandatory!");
			return false;
		}

		temp.formAction.value = "post";
		loadPopDet();
		temp.submit();
	}

	function delBufferRow()
	{
		var temp = document.forms[0];
		var obj = document.getElementById('tblBufferList');
		var count = 0;
		for (var i = 0; i < obj.children.length; i++)
		{
			if (obj.children(i).children(0).children(0).children(0).checked)
				count++;
		}
		if (count == 0)
		{
			alert ("Select One or More Production Entries to Delete!");
			return false;
		}
		
		if (obj.children.length > 1)
		{
			for (var i = 0 ; i < obj.children.length; i++)
			{
				if (obj.children(i).children(0).children(0).children(0).checked)
				{
					obj.removeChild(obj.children(i));
					i = 0;
				}
			}
			if (obj.children.length == 1)
			{
				if (obj.children(0).children(0).children(0).children(0).checked)
				{
					obj.children(0).children(0).children(1).children(0).value = "";
					obj.children(0).children(0).children(2).children(0).value = "";
					obj.children(0).children(0).children(3).children(0).value = "";
					obj.children(0).children(0).children(4).children(0).value = "";
					obj.children(0).children(0).children(5).children(0).value = "";
					obj.children(0).children(0).children(6).children(0).value = "";

					obj.children(0).children(0).children(0).children(0).checked = false;
				}
			}
		}
		else if (obj.children(0).children(0).children(0).children(0).checked)
		{
					obj.children(0).children(0).children(1).children(0).value = "";
					obj.children(0).children(0).children(2).children(0).value = "";
					obj.children(0).children(0).children(3).children(0).value = "";
					obj.children(0).children(0).children(4).children(0).value = "";
					obj.children(0).children(0).children(5).children(0).value = "";
					obj.children(0).children(0).children(6).children(0).value = "";

					obj.children(0).children(0).children(0).children(0).checked = false;
		}
		for (var i = 0; i < obj.children.length; i++)
		{
			obj.children(i).children(0).children(1).children(0).value = i+1;
		}
	}

	function chkAllTblBffer()
	{
		var temp = document.forms[0];
		if (temp.chkAllProdBufHead.checked)
		{
			for (var i = 0; i < temp.chkProdList.length; i++)
			{
				temp.chkProdList[i].checked = true;
			}
			if (temp.chkProdList.length == undefined)
				temp.chkProdList.checked = true;
		}
		else
		{
			for (var i = 0; i < temp.chkProdList.length; i++)
			{
				temp.chkProdList[i].checked = false;
			}
			if (temp.chkProdList.length == undefined)
				temp.chkProdList.checked = false;
		}
	}

	function modifyRow()
	{
		var temp = document.forms[0];
		var obj = document.getElementById('tblBufferList');

		var count = 0;
		for (var i = 0; i < obj.children.length; i++)
		{
			if (obj.children(i).children(0).children(0).children(0).checked)
				count++;
		}
		if (count > 1)
		{
			alert ("Select One Item to Modify!");
			return false;
		}
		else if (count == 0)
		{
			alert ("Select a Item to Modify!");
			return false;
		}
		
		tblPayOutProdList.style.display = 'block';

		for (var i = 0; i < obj.children.length; i++)
		{
			if (obj.children(i).children(0).children(0).children(0).checked)
			{
				temp.popFromDate.value = obj.children(i).children(0).children(2).children(0).value; // Date
				temp.popReason.value = obj.children(i).children(0).children(4).children(0).value; // Reason

				for (var j = 0; j < temp.machineShift.length; j++) // Shift
				{
					if (temp.machineShift.options[j].value == obj.children(i).children(0).children(5).children(0).value)
					{
						temp.machineShift.selectedIndex = j;
					}
				}
				
				/*for (var j = 0; j < temp.machineCode.length; j++) // Machine
				{
					if (temp.machineCode.options[j].value == obj.children(i).children(0).children(4).children(0).value)
					{
						temp.machineCode.selectedIndex = j;
					}
				}*/

				var empDet = (obj.children(i).children(0).children(6).children(0).value).split('^');
				var obj = document.getElementById('tblEmpDetails'); // Emp Details
				if (obj.children.length > 1)
				{
					for (var k = 0 ; k < obj.children.length; k++)
					{
						obj.removeChild(obj.children(i));
						k = 0;
					}
					if (obj.children.length == 1)
					{
						obj.children(0).children(0).children(1).children(0).value = "";
						obj.children(0).children(0).children(2).children(0).value = "";
						obj.children(0).children(0).children(3).children(0).value = "";
						obj.children(0).children(0).children(4).children(0).value = "";
						obj.children(0).children(0).children(5).children(0).value = "";
						obj.children(0).children(0).children(6).children(0).value = "";
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
				}

				for (var j = 0; j < empDet.length; j++)
				{
					var empPrnlDet = empDet[j].split('-');
					if ((obj.children.length == 1) && (obj.children(0).children(0).children(1).children(0).value == ""))
					{
						obj.children(0).children(0).children(1).children(0).value = empPrnlDet[0];
						obj.children(0).children(0).children(2).children(0).value = empPrnlDet[1];
						obj.children(0).children(0).children(3).children(0).value = empPrnlDet[2];
						obj.children(0).children(0).children(4).children(0).value = empPrnlDet[3];
						obj.children(0).children(0).children(5).children(0).value = empPrnlDet[4];
						obj.children(0).children(0).children(6).children(0).value = empPrnlDet[5];
					}
					else
					{
						var newNode = obj.children(0).cloneNode(true);
						obj.appendChild(newNode);
						var len = obj.children.length;

						obj.children(len-1).children(0).children(1).children(0).value = empPrnlDet[0];
						obj.children(len-1).children(0).children(2).children(0).value = empPrnlDet[1];
						obj.children(len-1).children(0).children(3).children(0).value = empPrnlDet[2];
						obj.children(len-1).children(0).children(4).children(0).value = empPrnlDet[3];
						obj.children(len-1).children(0).children(5).children(0).value = empPrnlDet[4];
						obj.children(len-1).children(0).children(6).children(0).value = empPrnlDet[5];
					}
				}
				
				/* Removing the particular child */
				var obj = document.getElementById('tblBufferList');
				for (var j = 0; j < obj.children.length; j++)
				{
					if (obj.children.length > 1)
					{
						if (obj.children(j).children(0).children(0).children(0).checked)
						{
							obj.removeChild(obj.children(j));
						}
					}
					else if ((obj.children.length == 1) && (obj.children(0).children(0).children(0).children(0).checked))
					{
						obj.children(0).children(0).children(0).children(0).checked = false;
						obj.children(0).children(0).children(1).children(0).value = "";
						obj.children(0).children(0).children(2).children(0).value = "";
						obj.children(0).children(0).children(3).children(0).value = "";
						obj.children(0).children(0).children(4).children(0).value = "";
						obj.children(0).children(0).children(5).children(0).value = "";
						obj.children(0).children(0).children(6).children(0).value = "";
					}
				}
			}
		}
		tblBuffer.style.display = 'none';
	}

	function loadPopDet()
	{
		var obj = document.getElementById('tblBufferList');
		var popDet = "";
		document.forms[0].hidPopDet.value = "";
		if ((obj.children(0).children(0).children(2).children(0).value == "") && (obj.children.length == 1))
			return false;
		for (var j = 0; j < obj.children.length; j++)
		{
			if (j != 0)
			{
				popDet = popDet + "$";
			}
			popDet = popDet + 
				obj.children(j).children(0).children(1).children(0).value + "#" +
				obj.children(j).children(0).children(2).children(0).value + "#" +
				obj.children(j).children(0).children(3).children(0).value + "#" +
				obj.children(j).children(0).children(4).children(0).value + "#" +
				obj.children(j).children(0).children(5).children(0).value + "#" +
				obj.children(j).children(0).children(6).children(0).value;
		}
		document.forms[0].hidPopDet.value = popDet;
	}

	function replacePopDet()
	{
		var obj = document.getElementById('tblBufferList');
		var temp = document.forms[0];
		tblBuffer.style.display = 'none';
		//tblPayOutProdList.style.display = 'none';
		
		if (temp.formAction.value == "add")
		{
<%
			if (frm.getFormAction().equalsIgnoreCase("add"))
			{
				hm_Result = (HashMap) request.getAttribute("popResult");
				
				/* For Success */
				vec_Success = (Vector) hm_Result.get("Success");
				
				/* For Employee Failure */
				vec_EmpFail = (Vector) hm_Result.get("EmpFailure");
				for (int i = 0; i < vec_EmpFail.size(); i++)
				{
					HashMap hm_DutyFail = (HashMap) vec_DutyFail.get(i);
					Set set = hm_DutyFail.entrySet();
					Iterator it = set.iterator();
					while(it.hasNext())
					{
						Map.Entry me = (Map.Entry) it.next();

						PopDetails objPopDetails = (PopDetails) me.getValue();
						String empDet = "";
						if (prodList != "")
						{
							prodList = prodList + "$";
						}
						GregorianCalendar gc = new GregorianCalendar();
						gc.setTime(objPopDetails.getPopCrntDate());
						int day = gc.get(GregorianCalendar.DATE);
						int month = gc.get(GregorianCalendar.MONTH);
						int year = gc.get(GregorianCalendar.YEAR);
						prodList = prodList + (i+1) +"#"+ 
							(year+"-"+(month+1)+"-"+day) +"#"+ 
							objPopDetails.getShiftName() +"#"+
							objPopDetails.getPopRsn() +"#"+
							objPopDetails.getShiftId();
							
						Vector vec_EmpDet = objPopDetails.getPopEmpHrsDetails();
						for (int j = 0; j < vec_EmpDet.size(); j++)
						{
							EmployeeDtyOtDetails objEmployeeDtyOtDetails = new EmployeeDtyOtDetails();
							objEmployeeDtyOtDetails = (EmployeeDtyOtDetails) vec_EmpDet.get(j);
							if (j != 0)
							{
								empDet = empDet + "^";
							}
							empDet = empDet + objEmployeeDtyOtDetails.getEmpType() +"-"+
								objEmployeeDtyOtDetails.getEmpName() +"-"+
								objEmployeeDtyOtDetails.getDutyHrs() +"-"+
								objEmployeeDtyOtDetails.getOtHrs() +"-"+
								objEmployeeDtyOtDetails.getEmpTypdId() +"-"+
								objEmployeeDtyOtDetails.getEmpId();
						}
						prodList = prodList + "#" + empDet;
					}
				}

				/* For Employee Duty Failure */
				vec_DutyFail = (Vector) hm_Result.get("DutyFailure");
				for (int i = 0; i < vec_DutyFail.size(); i++)
				{
					HashMap hm_DutyFail = (HashMap) vec_DutyFail.get(i);
					Set set = hm_DutyFail.entrySet();
					Iterator it = set.iterator();
					while(it.hasNext())
					{
						Map.Entry me = (Map.Entry) it.next();

						PopDetails objPopDetails = (PopDetails) me.getValue();
						String empDet = "";
						if (prodList != "")
						{
							prodList = prodList + "$";
						}
						GregorianCalendar gc = new GregorianCalendar();
						gc.setTime(objPopDetails.getPopCrntDate());
						int day = gc.get(GregorianCalendar.DATE);
						int month = gc.get(GregorianCalendar.MONTH);
						int year = gc.get(GregorianCalendar.YEAR);
						prodList = prodList + (i+1) +"#"+ 
							(year+"-"+(month+1)+"-"+day) +"#"+ 
							objPopDetails.getShiftName() +"#"+
							objPopDetails.getPopRsn() +"#"+
							objPopDetails.getShiftId();
							
						Vector vec_EmpDet = objPopDetails.getPopEmpHrsDetails();
						for (int j = 0; j < vec_EmpDet.size(); j++)
						{
							EmployeeDtyOtDetails objEmployeeDtyOtDetails = new EmployeeDtyOtDetails();
							objEmployeeDtyOtDetails = (EmployeeDtyOtDetails) vec_EmpDet.get(j);
							if (j != 0)
							{
								empDet = empDet + "^";
							}
							empDet = empDet + objEmployeeDtyOtDetails.getEmpType() +"-"+
								objEmployeeDtyOtDetails.getEmpName() +"-"+
								objEmployeeDtyOtDetails.getDutyHrs() +"-"+
								objEmployeeDtyOtDetails.getOtHrs() +"-"+
								objEmployeeDtyOtDetails.getEmpTypdId() +"-"+
								objEmployeeDtyOtDetails.getEmpId();
						}
						prodList = prodList + "#" + empDet;
					}
				}
			}
%>
			temp.hidPopDet.value = '<%= prodList %>';		
		}

		if (temp.hidPopDet.value != "")
		{
			tblBuffer.style.display = 'block';
			var prodDet = (temp.hidPopDet.value).split('$');
			for (var i = 0; i < prodDet.length; i++)
			{
				var prodPrnlDet = prodDet[i].split('#');
				if ((obj.children.length == 1) && (obj.children(0).children(0).children(2).children(0).value == ""))
				{
					obj.children(0).children(0).children(1).children(0).value = i+1;
					obj.children(0).children(0).children(2).children(0).value = prodPrnlDet[1];
					obj.children(0).children(0).children(3).children(0).value = prodPrnlDet[2];
					obj.children(0).children(0).children(4).children(0).value = prodPrnlDet[3];
					obj.children(0).children(0).children(5).children(0).value = prodPrnlDet[4];
					obj.children(0).children(0).children(6).children(0).value = prodPrnlDet[5];
				}
				else
				{
					var newNode = obj.children(0).cloneNode(true);
					obj.appendChild(newNode);
					var len = obj.children.length;

					obj.children(len-1).children(0).children(1).children(0).value = "";
					obj.children(len-1).children(0).children(2).children(0).value = "";
					obj.children(len-1).children(0).children(3).children(0).value = "";
					obj.children(len-1).children(0).children(4).children(0).value = "";
					obj.children(len-1).children(0).children(5).children(0).value = "";
					obj.children(len-1).children(0).children(6).children(0).value = "";

					obj.children(len-1).children(0).children(1).children(0).value = i+1;
					obj.children(len-1).children(0).children(2).children(0).value = prodPrnlDet[1];
					obj.children(len-1).children(0).children(3).children(0).value = prodPrnlDet[2];
					obj.children(len-1).children(0).children(4).children(0).value = prodPrnlDet[3];
					obj.children(len-1).children(0).children(5).children(0).value = prodPrnlDet[4];
					obj.children(len-1).children(0).children(6).children(0).value = prodPrnlDet[5];
				}
			}
		}
	}

	function submitItem()
	{
		var obj = document.getElementById('tblBufferList');
		var temp = document.forms[0];
		var popList = "";
		for (var j = 0; j < obj.children.length; j++)
		{
			if (j != 0)
			{
				popList = popList + "$";
			}
			popList = popList + 
				obj.children(j).children(0).children(1).children(0).value + "#" +
				obj.children(j).children(0).children(2).children(0).value + "#" +
				obj.children(j).children(0).children(3).children(0).value + "#" +
				obj.children(j).children(0).children(4).children(0).value + "#" +
				obj.children(j).children(0).children(5).children(0).value + "#" +
				obj.children(j).children(0).children(6).children(0).value;
		}
		temp.hidPopDet.value = popList;
		temp.formAction.value = "add";
		temp.submit();
	}

</script>
</head>

<body onload="init();showPop();loadEmpDetails(); replacePopDet();">
<html:form action="frmPayOutProdAdd">
<html:hidden property="formAction"/>
<html:hidden property="showCount"/>
<html:hidden property="hidPopDet"/>
<html:hidden property="hidEmpTypId" value="<%= frm.getHidEmpTypId() %>"/> <!-- Holding the EmpTypId -->
<html:hidden property="hidEmpDet" /> <!-- value="<%= frm.getHidEmpDet() %>" Holding the Emp Details -->
<html:hidden property="hidUserId"/> <!-- Holding the User Name -->

	<table width="100%" cellspacing="0" cellpadding="10">
		<tr>
			<td>
	<table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
		<tr>
			<td><bean:message key="prodacs.production.payoutsideproduction"/></td>
		</tr>
	</table>
	<br>
	<table width="100" cellspacing="0" cellpadding="0" align="right">
	<tr> 
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New PayOutProduction Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1020" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='List PayOutProduction Info'; return true"  onMouseOut="window.status=''; return true" resourceId="20" text="[ List ]" classId="TopLnk" onClick="javaScript:listItem();"/>
	</tr>
	</table>
	<br>
	<table>
	<tr> 
		<td colspan='2'> <font size="1px" face="Verdana">
		<html:errors/></font></td>
	</tr> 
<%		
		if (frm.getFormAction().equalsIgnoreCase("add"))
		{
			hm_Result = (HashMap) request.getAttribute("popResult");
			if (BuildConfig.DMODE)
				System.out.println("Result: "+ hm_Result);
			
			/* For Success */
			vec_Success = (Vector) hm_Result.get("Success");
			if (BuildConfig.DMODE)
				System.out.println("vec_Success: "+ vec_Success.size());
			if (vec_Success.size() == 1)
			{
				out.println("<tr><td colspan='2'><font size='2px' face='Verdana' color='#3323C7'><img src='/ProDACS/images/active.gif' border='0'> "+vec_Success.size()+" POP Entry Added. </font></td></tr>");
				out.println("<tr>&nbsp;&nbsp;&nbsp;</tr>");
				frm.setShowCount("");
			}
			else if (vec_Success.size() > 1)
			{
				out.println("<tr><td colspan='2'> <font size='2px' face='Verdana' color='#3323C7'><img src='/ProDACS/images/active.gif' border='0'> "+vec_Success.size()+" POP Entries are Added. </font></td></tr>");
				out.println("<tr>&nbsp;&nbsp;&nbsp;</tr>");
			}
			
			/* For Employee Failure */
			vec_EmpFail = (Vector) hm_Result.get("EmpFailure");
			if (BuildConfig.DMODE)
				System.out.println("Employee Failure: "+ vec_EmpFail.size());
			
			/* For Employee Duty Failure */
			vec_DutyFail = (Vector) hm_Result.get("DutyFailure");
			if (BuildConfig.DMODE)
				System.out.println("Employee Duty Failure: "+ vec_DutyFail.size());
			
			if (vec_DutyFail.size() == 1)
			{
				out.println("<tr><td colspan='2'> <font size='2px' face='Verdana' color='#3323C7'><img src='/ProDACS/images/inactive.gif' border='0'> "+vec_DutyFail.size()+" POP Entry Not Added. Employee Duty Hours not allowed for multiple shifts. </font></td></tr>");
				for (int x = 0; x < vec_DutyFail.size(); x++)
				{
					HashMap hm_DutyFail = (HashMap) vec_DutyFail.get(x);
					Set set = hm_DutyFail.entrySet();
					Iterator it = set.iterator();
					while(it.hasNext())
					{
						Map.Entry me = (Map.Entry) it.next();
						out.println("<tr><td colspan='2'><li><font size='1px' face='Verdana' color='#FF3300'>"+me.getKey()+"</font></li></td></tr>");
					}
				}
			}
			else if (vec_DutyFail.size() > 1)
			{
				out.println("<tr><td colspan='2'> <font size='2px' face='Verdana' color='#3323C7'><img src='/ProDACS/images/inactive.gif' border='0'> "+vec_DutyFail.size()+" POP Entries are Not Added. Employee Duty Hours not allowed for multiple shifts. </font></td></tr>");
				for (int x = 0; x < vec_DutyFail.size(); x++)
				{
					HashMap hm_DutyFail = (HashMap) vec_DutyFail.get(x);
					Set set = hm_DutyFail.entrySet();
					Iterator it = set.iterator();
					while(it.hasNext())
					{
						Map.Entry me = (Map.Entry) it.next();
						out.println("<tr><td colspan='2'><li><font size='1px' face='Verdana' color='#FF3300'>"+me.getKey()+"</font></li></td></tr>");
					}
				}
			}
			out.println("<tr>&nbsp;&nbsp;&nbsp;</tr>");
						
			if (vec_EmpFail.size() == 1)
			{
				out.println("<tr><td colspan='2'> <font size='2px' face='Verdana' color='#3323C7'><img src='/ProDACS/images/inactive.gif' border='0'> "+vec_EmpFail.size()+" POP Entry Not Added due to Employee Hours Invalid. </font></td></tr>");
				for (int x = 0; x < vec_EmpFail.size(); x++)
				{
					HashMap hm_EmpFail = (HashMap) vec_EmpFail.get(x);
					Set set = hm_EmpFail.entrySet();
					Iterator it = set.iterator();
					while(it.hasNext())
					{
						Map.Entry me = (Map.Entry) it.next();
						out.println("<tr><td colspan='2'><li><font size='1px' face='Verdana' color='#FF3300'>"+me.getKey()+"</font></li></td></tr>");
					}
				}
			}
			else if (vec_EmpFail.size() > 1)
			{
				out.println("<tr><td colspan='2'> <font size='1px' face='Verdana' color='#3323C7'><img src='/ProDACS/images/inactive.gif' border='0'> "+vec_EmpFail.size()+" POP Entries are Not Added due to Employee Hours Invalid. </font></td></tr>");
				for (int x = 0; x < vec_EmpFail.size(); x++)
				{
					HashMap hm_EmpFail = (HashMap) vec_EmpFail.get(x);
					Set set = hm_EmpFail.entrySet();
					Iterator it = set.iterator();
					while(it.hasNext())
					{
						Map.Entry me = (Map.Entry) it.next();
						out.println("<tr><td colspan='2'><li><font size='1px' face='Verdana' color='#FF3300'>"+me.getKey()+"</font></li></td></tr>");
					}
				}
			}
			out.println("<tr>&nbsp;&nbsp;&nbsp;</tr>");

		}
%>
	
	</table> 
	
	<table width="100%" cellspacing="0" cellpadding="0">
		<tr> 
			<td width="50" class="FieldTitle"><bean:message key="prodacs.production.date"/><span class="mandatory">*</span></td>
			<td width="1" class="FieldTitle">:</td>
			<td class="FieldTitle"><html:text property="popFromDate" styleClass="TextBox" size="12" readonly="true"/> 
			<img src='<bean:message key="context"/>/images/calendar.gif' width="16" height="15" align="absmiddle" onClick='show_calendar("popFromDate",PayOutProductionAdd.popFromDate.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"></td>
			
			<td width="100" >&nbsp;</td>

			<td class="FieldTitle"><bean:message key="prodacs.production.reason"/><span class="mandatory">*</span></td>
			<td class="FieldTitle">:</td>
			<td class="FieldTitle"><html:text property="popReason" styleClass="TextBox" size="25" maxlength="30"/></td>
			
		</tr> 
		<tr>
			<td class="FieldTitle"><bean:message key="prodacs.production.shift"/><span class="mandatory">*</span></td>
			<td class="FieldTitle">:</td>
			<td class="FieldTitle"><html:select property="machineShift" styleClass="Combo">
			<html:option value="0">--Choose Shift--</html:option>
			<html:options collection="dateShift" property="key" labelProperty="value"/>
			</html:select></td>
		</tr>

	</table>
	<br>
	<table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
	<tr>
		<td>
			<html:button property="proNextBtn4" styleClass="Button" onclick="chkPostRule();">Next</html:button>
		</td>
	</tr>
	</table>
	<br>
		<!-- 	<table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
				<tr>
				<td><html:button property="BtnBgNext" value="Next" styleClass="Button" onclick="tblPayOutProdList.style.display='block'"/></td>
				</tr>
			</table> -->	
	<table width="100%" cellpadding="0" cellspacing="0" id="tblPayOutProdList" style="display:none">
		<tr>
			<td class="FieldTitle">
			<fieldset id="FieldSet"><legend><bean:message key="prodacs.production.payoutsideproduction"/></legend>
	<br>
	<table width="100%" cellspacing="0" cellpadding="0">
		<tr> 
			<td width="200" class="FieldTitle"><bean:message key="prodacs.production.employeetype"/><span class="mandatory">*</span></td>
			<td width="1" class="FieldTitle">:</td>
			<td width="200" class="FieldTitle"><html:select property="employeeType" styleClass="Combo" onchange="loadEmpList();">
			<html:option value="0">-- Employee Type --</html:option>
			<html:options collection="popEmpType" property="key" labelProperty="value"/>
			</html:select></td>
			<td width="200" class="FieldTitle"><bean:message key="prodacs.production.employeename"/><span class="mandatory">*</span></td>
			<td width="1" class="FieldTitle">:</td>
			<td class="FieldTitle"><html:select property="employeeName" styleClass="Combo">
			<html:option value="0">-- Employee Name --</html:option>
			<html:options collection="popEmpList" property="key" labelProperty="value"/>
			</html:select></td>
		</tr>
		<%
			boolean dtHrs = false;
		    boolean otHrs = false;
			Set set = dtOtEmpList.entrySet();
			Iterator it = set.iterator();
			while (it.hasNext())
			{
				Map.Entry me = (Map.Entry) it.next();
				if (me.getKey().equals("DT"))
				{
					dtHrs = ((Boolean)me.getValue()).booleanValue();
				    if (dtHrs == true)
				    {
		%>
		<tr> 
			<td class="FieldTitle"><bean:message key="prodacs.production.dutyhrs"/><span class="mandatory">*</span></td>
			<td class="FieldTitle">:</td>
			<td class="FieldTitle"><html:text property="dutyHrs" styleClass="TextBox" size="10" onkeypress="return isNumber();" onkeyup="return isNumber();" maxlength="6"/></td>
		<%
					}
					else
					{
		%>
			<td class="FieldTitle"><bean:message key="prodacs.production.dutyhrs"/><span class="mandatory">*</span></td>
			<td class="FieldTitle">:</td>
			<td class="FieldTitle"><html:text property="dutyHrs" size="10" onkeypress="return isNumber();" onkeyup="return isNumber();" maxlength="6" styleClass="disabledText" value="0" readonly="true"/></td>
		<%
					}
				}
				if (me.getKey().equals("OT"))
				{
					otHrs = ((Boolean)me.getValue()).booleanValue();
					if (otHrs == true)
					{
		%>
			<td class="FieldTitle"><bean:message key="prodacs.production.othrs"/><span class="mandatory">*</span></td>
			<td class="FieldTitle">:</td>
			<td class="FieldTitle"><html:text property="otHrs" styleClass="TextBox" size="10"onkeypress="return isNumber();" onkeyup="return isNumber();" maxlength="6"/></td>
		<%
					}
					else
					{
		%>
			<td class="FieldTitle"><bean:message key="prodacs.production.othrs"/><span class="mandatory">*</span></td>
			<td class="FieldTitle">:</td>
			<td class="FieldTitle"><html:text property="otHrs" size="10"onkeypress="return isNumber();" onkeyup="return isNumber();" maxlength="6" styleClass="disabledText" value="0" readonly="true"/></td>
		<%
					}
				}
			}
		%>
		</tr>
	</table>
	<table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
		<tr>
			<td><html:button property="Button" styleClass="Button" value="Move" onclick="transEmpDet();"/></td>
		</tr>
	</table>
	<br>
	<div id="dvPayOutProdList">
	<table width="100%" cellspacing="0" cellpadding="5">
    <tr> 
		<td class="TopLnk">
		[ <a href="#" onClick="delRow(document.forms[0]);"><bean:message key="prodacs.common.delete"/></a> ]</td>
    </tr>
	</table>
	<div id="dvPayOutProd" style="overflow:auto; height:105">
	<table width="100%" cellpadding="0" cellspacing="0">
		<tr> 
			<td width="25" class="Header"><input type="checkbox"  name="checkAll" value="checkbox" onclick="chkAll(document.forms[0])"/></td>
			<td width="200" class="Header"><bean:message key="prodacs.production.emptype"/></td>
			<td class="Header"><bean:message key="prodacs.production.empname"/></td>
			<td width="150" class="Header"><bean:message key="prodacs.production.dutyhrs"/></td>
			<td width="150" class="Header"><bean:message key="prodacs.production.othrs"/></td>
		</tr>
	</table>
	
	<table width="100%" cellspacing="0" cellpadding="0" id="tblEmpDetails">
		<tr> 
			<td width="22" class="TableItems"><input type="checkbox" name="chkBx" value="checkbox"/></td>
			<td width="197" class="TableItems"><input type="text" class="TextBoxFull" readonly name="empTypName"/></td>
			<td class="TableItems"><input type="text" class="TextBoxFull" readonly name="empName"/></td>
			<td width="147" class="TableItems"><input type="text" class="TextBoxFull" readonly name="dutyHours"/></td>
			<td width="147" class="TableItems"><input type="text" class="TextBoxFull" readonly name="otHours"/></td>
			<td><input type="hidden" name="empTypId"/></td>
			<td><input type="hidden" name="empId"/></td>
		</tr>
	</table>
	</div>
	<table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
		<tr>
			<td><html:button property="save" styleClass="Button" value="Add" onclick="addPoPDet();"/></td>
		</tr>
	</table>
	</div>
	</fieldset>
	</td>
	</tr>
	</table>
			<!-- Buffering the Pop Values -->
		<table width="100%" cellpadding="0" cellspacing="0" id="tblBuffer" style="display:none"  -->
        <tr>
		<td>
			<fieldset id="FieldSet"><legend class="FieldTitle"> Current POP Entries </legend>
				<table width="100%" cellspacing="0" cellpadding="5">
				<tr> 
               		<td class="TopLnk">
               		[ <a href="#" onClick="modifyRow(document.forms[0]);"><bean:message key="prodacs.common.modify"/></a> ] [ <a href="#" onClick="delBufferRow(document.forms[0]);"><bean:message key="prodacs.common.delete"/></a> ]&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				</tr>
				</table>
            
				<table width="95%" cellspacing="0" cellpadding="0">
					<tr> 
						<td width="25" class="Header"><input name="chkAllProdBufHead" type="checkbox" id="CheckAllDymSub" onClick="chkAllTblBffer();"/></td>
						<td width="50" class="Header"><bean:message key="prodacs.common.sno"/></td>
						<td width="100" class="Header"><bean:message key="prodacs.job.date"/></td>
						<td width="150" class="Header"><bean:message key="prodacs.workcalendar.shiftdefinition.shiftname"/></td>
						<td class="Header"><bean:message key="prodacs.production.reason"/></td>
					</tr>
				</table>

				<table width="95%" cellspacing="0" cellpadding="0" id="tblBufferList">
					<tr> 
					  <td width="22" class="TableItems"><input type="checkbox" name="chkProdList"/></td>
					  <td width="47" class="TableItems"><input readonly name="nprodSno" class="TextBoxFull" /></td>
					  <td width="97" class="TableItems"><input readonly name="nprodDate" class="TextBoxFull" /></td>
					  <td width="147" class="TableItems"><input readonly name="nprodShift" class="TextBoxFull" /></td>
					  <td class="TableItems"><input readonly name="nprodReason" class="TextBoxFull" /></td>
					  <td><input type="hidden" name="nprodShiftId"/></td>
					  <td><input type="hidden" name="prodEmpDet"/></td>
					</tr>
				</table>
				<table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
                <tr>
                  <td><html:button property="addAll" styleClass="Button" onclick="javaScript:submitItem();">Add All</html:button></td>
                </tr>
				</table>

			</fieldset>
		</td>
        </tr>
        </table>
		<!-- End of Buffering Tables -->

	<br>
</td>
</tr>
</table>
</html:form>
</body>
</html:html>
