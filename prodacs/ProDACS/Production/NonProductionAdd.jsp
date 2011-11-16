<%@ page language = "java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri = "/WEB-INF/struts-logic.tld" prefix = "logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<jsp:useBean id="frm" class="com.savantit.prodacs.presentation.tableadmin.production.NonProductionAddForm" />
<jsp:setProperty name="frm" property="*" /> 
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.GregorianCalendar"%>
<%@ page import="java.util.StringTokenizer"%>
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Map"%>

<%@ page import="com.savantit.prodacs.facade.SessionNonProductionDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionNonProductionDetailsManager"%>
<%@ page import="com.savantit.prodacs.businessimplementation.production.NonProductionDetails"%>
<%@ page import="com.savantit.prodacs.businessimplementation.production.EmployeeDtyOtDetails"%>
<%@ page import="com.savantit.prodacs.businessimplementation.securityadmin.UserAuthDetails"%>
<useradmin:userrights resource="1018"/>
<%
	if(frm.getFormAction().equalsIgnoreCase("addNew"))
		frm.setProDate("");
	if (BuildConfig.DMODE)
		System.out.println("Non Production Add Starts");
	Vector vec_Success = new Vector();
	Vector vec_MachineFail = new Vector();
	Vector vec_EmpFail = new Vector();
	Vector vec_DutyFail = new Vector();
	String prodList = "";
	
	HashMap hm_Result = new HashMap();
	HashMap empDetails = new HashMap(); /* For Employee Details and Type Details */
	UserAuthDetails userDetails = (UserAuthDetails)session.getAttribute("##userRights##");
	String userName = userDetails.getUserId();
	try
	{
		EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
		
		/* 	Setting the JNDI name and Environment 	*/
	   	obj.setJndiName("SessionNonProductionDetailsManagerBean");
   		obj.setEnvironment();

		/* 	Creating the Home and Remote Objects 	*/
		SessionNonProductionDetailsManagerHome nonProHomeObj = (SessionNonProductionDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionNonProductionDetailsManagerHome.class);
		SessionNonProductionDetailsManager nonProductionObj = (SessionNonProductionDetailsManager)PortableRemoteObject.narrow(nonProHomeObj.create(),SessionNonProductionDetailsManager.class);

		HashMap dateShift = new HashMap();
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
		
			dateShift = nonProductionObj.getShiftDefnNameList(gc.getTime());
			if (BuildConfig.DMODE)
				System.out.println("Shifts for Date(HM) :"+dateShift);
			pageContext.setAttribute("dateShift",dateShift);
		}
		else
		{
			pageContext.setAttribute("dateShift",dateShift);
		}
		
		HashMap shiftMachs = new HashMap();
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
		
			shiftMachs = nonProductionObj.getAllMachines(ge.getTime(),Integer.parseInt(frm.getProShift()));
			if (BuildConfig.DMODE)
				System.out.println("Machines(HM):"+shiftMachs);
			pageContext.setAttribute("shiftMachs",shiftMachs);
		}
		else
		{
			pageContext.setAttribute("shiftMachs",shiftMachs);
		}
		
		/* For Reason */
		HashMap idleBrkDwnRsn = nonProductionObj.getAllIdlBrkDwnRsns();
		pageContext.setAttribute("idleBrkDwnRsn",idleBrkDwnRsn);
		
		/* Loading EmployeeType and Employees */
		empDetails = nonProductionObj.getAllEmployeesByTypes();
		
		/* Finalizing the minimum required Quantities */
		HashMap minRqdEmp = nonProductionObj.minReqdEmployees();
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
	}catch (Exception e)
	{
		if (BuildConfig.DMODE)
			System.out.println("Problem in Production Add.");
	}
%>

<html:html>
<head>
<title><bean:message key="prodacs.production.header"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script src='<bean:message key="context"/>/library/jscripts.js'></script>
<script src='<bean:message key="context"/>/library/calendar_previous_submit.js'></script>
<script src='<bean:message key="context"/>/library/prototype.js'></script>
<script>
var oPopup;
	function init()
	 {
		oPopup = window.createPopup();
	 }

	 function dateSubmit()
	 {
		 var temp = document.forms[0];
		 var proDateValue = temp.proDate.value;
		 for(var i = 1; i < temp.proMachine.length; i++)
		 {
			 while (temp.proMachine.options[i] != null)
				temp.proMachine.options[i] = null;
		 }
		 if(proDateValue != "0")
		{
			var url = '/ProDACS/servlet/nprodservlet';
			var value = 1;
			var param = 'nonproDate='+proDateValue+'&value='+value;
			if (window.XMLHttpRequest) // Non-IE browsers
			{
				req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: showResponse});
			}
			else if (window.ActiveXObject) // IE
			{
				req = new ActiveXObject("Microsoft.XMLHTTP");
				if (req)
				{
					req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: showResponse});
				}
			}
		}
		else
		{
			alert("Production Date cannot be Empty!");
			return false;
		}

		 /*temp.formAction.value = "dateSubmit";
		 if (temp.proDate.value != '<%= request.getParameter("proDate") %>') // To clear the values while changing the shift 
		 {
			if (temp.proShift.value != "0" && '<%= request.getParameter("formAction") %>' == "dateSubmit")
			{
				 temp.proShift.value = "0";
			}
		 }
		 if (temp.proDate.value != '<%= request.getParameter("proDate") %>')
		 {
			 temp.hidNonProdDetSel.value = "";
		 }
		 loadNonProdDet();
		 temp.submit();*/
	 }

	 function showResponse(req)
	 {
	 	//Processing the returned Text and replacing the existing <select> with response
		document.getElementById('shiftName').innerHTML = req.responseText;
	 }
	 
	 function test()
	 {
		 var temp = document.forms[0];
		 for (var i = 1; i < temp.proMachine.length; i++)
			 alert(temp.proMachine.options[i].size);
	 }

	 function loadMachines()
	 {
		var temp = document.forms[0];
		var proDateValue = temp.proDate.value;
		var sftIndex = temp.proShift.selectedIndex;

		if((proDateValue != "") && (sftIndex != "0"))
		{
			var url = '/ProDACS/servlet/nprodservlet';
			var value = 2;
			var param = 'nonproDate='+proDateValue+'&shiftId='+sftIndex+'&value='+value;
			if (window.XMLHttpRequest) // Non-IE browsers
			{
				req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: showResponse1});
			}
			else if (window.ActiveXObject) // IE
			{
				req = new ActiveXObject("Microsoft.XMLHTTP");
				if (req)
				{
					req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: showResponse1});
				}
			}
		}
		else
		{
		alert("Production Shift cannot be Empty!");
		return false;
		}

		 /*if('<%= request.getParameter("formAction") %>' == "add" && temp.proShift.value != "0") // To clear the values while changing the shift while modify
		 {
			temp.proMachine.value = 0;
			temp.idleBrkDwnRsn.value = "0"
			temp.proTotalHours.value = "";
			tblNonProdRsn.style.display = 'none';
			tblEmployeeDetails.style.display = 'none';
			return false;
		 }
		 else if (temp.proShift.value != "0")
		 {
			 temp.formAction.value = "dateSubmit";
			 loadNonProdDet();
			 temp.submit();
		 }*/
	 }

	 function showResponse1(req)
	 {
		//Processing the returned Text and replacing the existing <select> with response
		document.getElementById('machNames').innerHTML = req.responseText;
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

	function listItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/Production/NonProductionList.jsp';
		document.forms[0].submit();
	}

	function addItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/Production/NonProductionAdd.jsp?formAction=addNew';
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

	function transEmpDet(val)
	{
		var temp = document.forms[0];
		var ind = parseInt(val);
		var obj = document.getElementById("tblEmpDetails");
		var obj1 = document.getElementById("tblEmpDetMstr");

		/* Duty + OT Hrs can't exceed Total Hours */
		if (temp.proTotalHours.value != "")
		{
			var empDtyHrs = obj1.children(0).children(ind).children(2).children(0).value;
			var empOtHrs = obj1.children(0).children(ind).children(3).children(0).value;
			if(empOtHrs == "")
			{
				empOtHrs = 0;
			}
			var empHrs = parseInt(empDtyHrs)+parseInt(empOtHrs);
			if (empHrs > temp.proTotalHours.value)
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

		/* Validations before shifting Employee Details */
		if (temp.proEmployeeName[ind-1].options[temp.proEmployeeName[ind-1].selectedIndex].value == "0")
		{
			alert ("Select Employee Name to Proceed!");
			return false;
		}
		if (((parseFloat(obj1.children(0).children(ind).children(2).children(0).value) + parseFloat(obj1.children(0).children(ind).children(3).children(0).value)) == 0) || (obj1.children(0).children(ind).children(2).children(0).value == "")&&
			(obj1.children(0).children(ind).children(3).children(0).value == ""))
		{
			if (('<%= request.getParameter("DT") %>' == 0) || ('<%= request.getParameter("OT") %>' == 0))
			{
				alert ("Duty + OT Hours can't be Zero!");
				return false;
			}
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
					alert ("Sorry! The Employee "+name+" details already Added!");

					/* Reseting the Fields */
					obj1.children(0).children(ind).children(2).children(0).value = "";
					obj1.children(0).children(ind).children(3).children(0).value = "";
					temp.proEmployeeName[ind-1].selectedIndex = "0";

					return false;
				}

				if (temp.proEmployeeName[ind-1].options[temp.proEmployeeName[ind-1].selectedIndex].value == "0")
				{
					alert ("Select a Employee Name!");
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
		var obj = document.getElementById("tblEmpDetails");

		if (temp.idleBrkDwnRsn.value == "0")
		{
			alert ("Non-Production Reason is Mandatory!");
			return false;
		}

		if (temp.proTotalHours.value == "") /* Total hrs can't be empty */
		{
			alert ("Total Hours is Mandatory!");
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
			empDet = empDet + obj.children(i).children(0).children(1).children(0).value; // EmpTyp name 
			empDet = empDet + "-" + obj.children(i).children(0).children(2).children(0).value; // Emp Name 
			empDet = empDet + "-" + obj.children(i).children(0).children(3).children(0).value; // Duty Hrs 
			empDet = empDet + "-" + obj.children(i).children(0).children(4).children(0).value; // OT Hrs 
			empDet = empDet + "-" + obj.children(i).children(0).children(5).children(0).value; // Emp Typ Id 
			empDet = empDet + "-" + obj.children(i).children(0).children(6).children(0).value; // Emp Name Id 
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

		tblNonProdRsn.style.display = 'none';
		tblEmployeeDetails.style.display = 'none';

		
		/* Move the values to Buffer */
		var obj = document.getElementById('tblBufferList');
		tblBuffer.style.display = 'block';
		if ((obj.children.length == 1) && (obj.children(0).children(0).children(2).children(0).value == ""))
		{
			obj.children(0).children(0).children(1).children(0).value = obj.children.length;
			obj.children(0).children(0).children(2).children(0).value = temp.proDate.value;
			obj.children(0).children(0).children(3).children(0).value = temp.proShift.options[temp.proShift.selectedIndex].text;
			obj.children(0).children(0).children(4).children(0).value = temp.proMachine.value;
			obj.children(0).children(0).children(5).children(0).value = (temp.nonProIdleRewrk[0].checked)?("Idle"):("Break Down");
			obj.children(0).children(0).children(6).children(0).value = temp.idleBrkDwnRsn.options[temp.idleBrkDwnRsn.selectedIndex].text;
			obj.children(0).children(0).children(7).children(0).value = temp.proTotalHours.value;
			obj.children(0).children(0).children(8).children(0).value = temp.proShift.value;
			obj.children(0).children(0).children(9).children(0).value = temp.idleBrkDwnRsn.value;
			obj.children(0).children(0).children(10).children(0).value = empDet;
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
			obj.children(len-1).children(0).children(7).children(0).value = "";
			obj.children(len-1).children(0).children(8).children(0).value = "";
			obj.children(len-1).children(0).children(9).children(0).value = "";
			obj.children(len-1).children(0).children(10).children(0).value = "";

			obj.children(len-1).children(0).children(1).children(0).value = obj.children.length;
			obj.children(len-1).children(0).children(2).children(0).value = temp.proDate.value;
			obj.children(len-1).children(0).children(3).children(0).value = temp.proShift.options[temp.proShift.selectedIndex].text;
			obj.children(len-1).children(0).children(4).children(0).value = temp.proMachine.value;
			obj.children(len-1).children(0).children(5).children(0).value = (temp.nonProIdleRewrk[0].checked)?("Idle"):("Break Down");
			obj.children(len-1).children(0).children(6).children(0).value = temp.idleBrkDwnRsn.options[temp.idleBrkDwnRsn.selectedIndex].text;
			obj.children(len-1).children(0).children(7).children(0).value = temp.proTotalHours.value;
			obj.children(len-1).children(0).children(8).children(0).value = temp.proShift.value;
			obj.children(len-1).children(0).children(9).children(0).value = temp.idleBrkDwnRsn.value;
			obj.children(len-1).children(0).children(10).children(0).value = empDet;
		}
		
		/* Clear All Values */
		//temp.proDate.value = "";
		//temp.proShift.selectedIndex = 0;
		//temp.proMachine.selectedIndex = 0;
		temp.nonProIdleRewrk[0].checked = true;
		temp.idleBrkDwnRsn.selectedIndex = 0;
		temp.proTotalHours.value = "";
		
		var obj = document.getElementById("tblEmpDetails");
		if (obj.children.length > 1)
		{
			for (var i = 0; i < obj.children.length; i++)
			{
				if (obj.children.length != 1)
				{
					obj.removeChild(obj.children(i));
					i = 0;
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
		loadNonProdDet();
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
				if ((temp.ckdCkBx[0].checked)&&(temp.ckdCkBx(0).checked))
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

				temp.ckdCkBx.checked = false;
				temp.ckAll.checked = false;
		}
	}

	function modifyRow()
	{
		var temp = document.forms[0];
		var obj = document.getElementById('tblBufferList');
		tblNonProdRsn.style.display = 'block';
		tblEmployeeDetails.style.display = 'block';

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
		delBufferRow();
		var modDet = (temp.hidNonProdDetSel.value).split("#");
		temp.proDate.value = modDet[0]; // Date

		for (var j = 0; j < temp.proShift.length; j++) // Shift
		{
			if (temp.proShift.options[j].value == modDet[6])
			{
				temp.proShift.selectedIndex = j;
			}
		}
		
		for (var j = 0; j < temp.proMachine.length; j++) // Machine
		{
			if (temp.proMachine.options[j].value == modDet[2])
			{
				temp.proMachine.selectedIndex = j;
			}
		}

		if (modDet[3] == "Idle") // Idle / BreakDown
			temp.nonProIdleRewrk[0].checked = true;
		else
			temp.nonProIdleRewrk[1].checked = true;

		for (var j = 0; j < temp.idleBrkDwnRsn.length; j++) // Reason Id
		{
			if (temp.idleBrkDwnRsn.options[j].value == modDet[7])
			{
				temp.idleBrkDwnRsn.selectedIndex = j;
			}
		}
	
		temp.proTotalHours.value = modDet[5]; // Tot Hrs

		var empDet = modDet[8].split('^');
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
	}
	
	function delBufferRow()
	{
		var temp = document.forms[0];
		var obj = document.getElementById('tblBufferList');
		for (var j = 0; j < obj.children.length; j++)
		{
			if (obj.children.length > 1)
			{
				if (obj.children(j).children(0).children(0).children(0).checked)  /* Backup for the checked entry alone during modification */
				{
					temp.hidNonProdDetSel.value = obj.children(j).children(0).children(2).children(0).value+"#"+ // Date
													  obj.children(j).children(0).children(3).children(0).value+"#"+ // Shift Name
													  obj.children(j).children(0).children(4).children(0).value+"#"+ // Machine Name
													  obj.children(j).children(0).children(5).children(0).value+"#"+ // Idle/BreakDown
													  obj.children(j).children(0).children(6).children(0).value+"#"+ // Reason
													  obj.children(j).children(0).children(7).children(0).value+"#"+ // Total Hours
													  obj.children(j).children(0).children(8).children(0).value+"#"+ // Shift Id
													  obj.children(j).children(0).children(9).children(0).value+"#"+ // Reason Id
													  obj.children(j).children(0).children(10).children(0).value; // Emp Details
						obj.removeChild(obj.children(j));
				}
			}
			else if ((obj.children.length == 1) && (obj.children(0).children(0).children(0).children(0).checked))
			{
				temp.hidNonProdDetSel.value = obj.children(0).children(0).children(2).children(0).value+"#"+ // Date
											  obj.children(0).children(0).children(3).children(0).value+"#"+ // Shift Name
											  obj.children(0).children(0).children(4).children(0).value+"#"+ // Machine Name
											  obj.children(0).children(0).children(5).children(0).value+"#"+ // Idle/BreakDown
											  obj.children(0).children(0).children(6).children(0).value+"#"+ // Reason
											  obj.children(0).children(0).children(7).children(0).value+"#"+ // Total Hours
											  obj.children(0).children(0).children(8).children(0).value+"#"+ // Shift Id
											  obj.children(0).children(0).children(9).children(0).value+"#"+ // Reason Id
											  obj.children(0).children(0).children(10).children(0).value; // Emp Details

				obj.children(0).children(0).children(0).children(0).checked = false;
				obj.children(0).children(0).children(1).children(0).value = "";
				obj.children(0).children(0).children(2).children(0).value = "";
				obj.children(0).children(0).children(3).children(0).value = "";
				obj.children(0).children(0).children(4).children(0).value = "";
				obj.children(0).children(0).children(5).children(0).value = "";
				obj.children(0).children(0).children(6).children(0).value = "";
				obj.children(0).children(0).children(7).children(0).value = "";
				obj.children(0).children(0).children(8).children(0).value = "";
				obj.children(0).children(0).children(9).children(0).value = "";
				obj.children(0).children(0).children(10).children(0).value = "";
			}
		}
	}

	function chkAllTblBffer()
	{
		var temp = document.forms[0];
		if (temp.chkAllProdBufHead.checked)
		{
			if (temp.chkProdList.length == undefined)
				temp.chkProdList.checked = true;
			for (var i = 0; i < temp.chkProdList.length; i++)
			{
				temp.chkProdList[i].checked = true;
			}
		}
		else
		{
			if (temp.chkProdList.length == undefined)
				temp.chkProdList.checked = false;
			for (var i = 0; i < temp.chkProdList.length; i++)
			{
				temp.chkProdList[i].checked = false;
			}
		}
	}

	function submitItem()
	{
		var obj = document.getElementById('tblBufferList');
		var temp = document.forms[0];
		var nonProdList = "";
		for (var j = 0; j < obj.children.length; j++)
		{
				if (j != 0)
					nonProdList = nonProdList + "$";
				nonProdList = nonProdList + 
					obj.children(j).children(0).children(1).children(0).value + "#" +
					obj.children(j).children(0).children(2).children(0).value + "#" +
					obj.children(j).children(0).children(3).children(0).value + "#" +
					obj.children(j).children(0).children(4).children(0).value + "#" +
					obj.children(j).children(0).children(5).children(0).value + "#" +
					obj.children(j).children(0).children(6).children(0).value + "#" +
					obj.children(j).children(0).children(7).children(0).value + "#" +
					obj.children(j).children(0).children(8).children(0).value + "#" +
					obj.children(j).children(0).children(9).children(0).value + "#" +
					obj.children(j).children(0).children(10).children(0).value;
		}
		//alert (nonProdList);
		temp.hidNonProdDet.value = nonProdList;
		temp.formAction.value = "add";
		temp.submit();
	}

	function chkPostRule()
	{
		var temp = document.forms[0];
		if (temp.proDate.value == "")
		{
			alert ("Non-Production Date is Mandatory!");
			return false;
		}

		if (temp.proShift.value == "0")
		{
			alert ("Non-Production Shift is Mandatory!");
			return false;
		}

		if (temp.proMachine.value == "0")
		{
			alert ("Non-Production MachineCode is Mandatory!");
			return false;
		}
		temp.formAction.value = "post";
		loadNonProdDet();
		temp.submit();
	}

	function showProdDet()  /* Hiding and showing the appropriate tables as per formAction & showCount values */
	{
		var temp = document.forms[0];
		temp.hidUserId.value = '<%= userName %>';
		if (temp.showCount.value == "1")
		{
			tblNonProdRsn.style.display = 'block';
			tblEmployeeDetails.style.display = 'block';
		}

		if (temp.showCount.value == "")
		{
			tblNonProdRsn.style.display = 'none';
			tblEmployeeDetails.style.display = 'none';
		}
		else if ((temp.showCount.value == "2") && (temp.formAction.value == "add"))
		{
			tblNonProdRsn.style.display = 'none';
			tblEmployeeDetails.style.display = 'none';
		}
		else if (temp.proDate.value != '<%= request.getParameter("proDate") %>' && temp.showCount.value != "2")
		{
			 temp.proShift.value = "0";
		}
	}

	function loadNonProdDet()
	{
		var temp = document.forms[0];
		var obj = document.getElementById('tblBufferList');
		var nonProdList = "";
		document.forms[0].hidNonProdDet.value = "";
		if (temp.formAction.value == "post")
		{
			temp.idleBrkDwnRsn.value = "0"
			temp.proTotalHours.value = "";
		}
		if ((obj.children(0).children(0).children(2).children(0).value == "") && (obj.children.length == 1))
			return false;
		for (var j = 0; j < obj.children.length; j++)
		{
			if (j != 0)
			{
				nonProdList = nonProdList + "$";
			}
			nonProdList = nonProdList + 
				obj.children(j).children(0).children(1).children(0).value + "#" +
				obj.children(j).children(0).children(2).children(0).value + "#" +
				obj.children(j).children(0).children(3).children(0).value + "#" +
				obj.children(j).children(0).children(4).children(0).value + "#" +
				obj.children(j).children(0).children(5).children(0).value + "#" +
				obj.children(j).children(0).children(6).children(0).value + "#" +
				obj.children(j).children(0).children(7).children(0).value + "#" +
				obj.children(j).children(0).children(8).children(0).value + "#" +
				obj.children(j).children(0).children(9).children(0).value + "#" +
				obj.children(j).children(0).children(10).children(0).value;
		}
		//alert (nonProdList);
		document.forms[0].hidNonProdDet.value = nonProdList;
	}

	function loadNonProdList()
	{
		var obj = document.getElementById('tblBufferList');
		var temp = document.forms[0];
		tblBuffer.style.display = 'none';
		tblNonProdRsn.style.display = 'none';
		tblEmployeeDetails.style.display = 'none';

		if (temp.formAction.value == "add")
		{
		<%
			if (frm.getFormAction().equalsIgnoreCase("add"))
			{
				hm_Result = (HashMap) request.getAttribute("nonprodresult");
				
				/* For Success */
				vec_Success = (Vector) hm_Result.get("Success");
				
				/* For Machine Failure */
				vec_MachineFail = (Vector) hm_Result.get("MachineFailure");
				for (int i = 0; i < vec_MachineFail.size(); i++)
				{
					NonProductionDetails objNonProductionDetails = (NonProductionDetails) vec_MachineFail.get(i);
					String empDet = "";
					if (i != 0)
					{
						prodList = prodList + "$";
					}
					GregorianCalendar gc = new GregorianCalendar();
					gc.setTime(objNonProductionDetails.getNonProdnCrntDate());
					int day = gc.get(GregorianCalendar.DATE);
					int month = gc.get(GregorianCalendar.MONTH);
					int year = gc.get(GregorianCalendar.YEAR);
					if (BuildConfig.DMODE)
						System.out.println("Date: "+year+"-"+month+"-"+day);
					prodList = prodList + (i+1) +"#"+ 
						(year+"-"+(month+1)+"-"+day) +"#"+ 
						objNonProductionDetails.getShiftName() +"#"+
						objNonProductionDetails.getMcCode() +"#"+
						objNonProductionDetails.getIdlOrBkDown() +"#"+
						objNonProductionDetails.getRsn() +"#"+
						objNonProductionDetails.getNprodTotHrs() +"#"+
						objNonProductionDetails.getShiftId() +"#"+
						objNonProductionDetails.getRsnId();
					Vector vec_EmpDet = objNonProductionDetails.getNonprodnEmpHrsDetails();
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
					if (BuildConfig.DMODE)
						System.out.println("ProdList: "+ prodList);
				}

								
				/* For Employee DutyFailure */
				vec_DutyFail = (Vector) hm_Result.get("DutyFailure");
				for (int i = 0; i < vec_DutyFail.size(); i++)
				{
					HashMap hm_DutyFail = (HashMap) vec_DutyFail.get(i);
					Set set = hm_DutyFail.entrySet();
					Iterator it = set.iterator();
					while(it.hasNext())
					{
						Map.Entry me = (Map.Entry) it.next();

						NonProductionDetails objNonProductionDetails = (NonProductionDetails) me.getValue();
						String empDet = "";
						if (i != 0)
						{
							prodList = prodList + "$";
						}
						GregorianCalendar gc = new GregorianCalendar();
						gc.setTime(objNonProductionDetails.getNonProdnCrntDate());
						int day = gc.get(GregorianCalendar.DATE);
						int month = gc.get(GregorianCalendar.MONTH);
						int year = gc.get(GregorianCalendar.YEAR);
						if (BuildConfig.DMODE)
							System.out.println("Date: "+year+"-"+month+"-"+day);
						prodList = prodList + (i+1) +"#"+ 
							(year+"-"+(month+1)+"-"+day) +"#"+ 
							objNonProductionDetails.getShiftName() +"#"+
							objNonProductionDetails.getMcCode() +"#"+
							objNonProductionDetails.getIdlOrBkDown() +"#"+
							objNonProductionDetails.getRsn() +"#"+
							objNonProductionDetails.getNprodTotHrs() +"#"+
							objNonProductionDetails.getShiftId() +"#"+
							objNonProductionDetails.getRsnId();
						Vector vec_EmpDet = objNonProductionDetails.getNonprodnEmpHrsDetails();
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
						if (BuildConfig.DMODE)
							System.out.println("ProdList: "+ prodList);
					}
				}
				
				/* For Employee Failure */
				vec_EmpFail = (Vector) hm_Result.get("EmpFailure");
				for (int i = 0; i < vec_EmpFail.size(); i++)
				{
					HashMap hm_EmpFail = (HashMap) vec_EmpFail.get(i);
					Set set = hm_EmpFail.entrySet();
					Iterator it = set.iterator();
					while(it.hasNext())
					{
						Map.Entry me = (Map.Entry) it.next();

						NonProductionDetails objNonProductionDetails = (NonProductionDetails) me.getValue();
						String empDet = "";
						if (prodList != "")
						{
							prodList = prodList + "$";
						}
						GregorianCalendar gc = new GregorianCalendar();
						gc.setTime(objNonProductionDetails.getNonProdnCrntDate());
						int day = gc.get(GregorianCalendar.DATE);
						int month = gc.get(GregorianCalendar.MONTH);
						int year = gc.get(GregorianCalendar.YEAR);
						prodList = prodList + (i+1) +"#"+ 
							(year+"-"+(month+1)+"-"+day) +"#"+ 
							objNonProductionDetails.getShiftName() +"#"+
							objNonProductionDetails.getMcCode() +"#"+
							objNonProductionDetails.getIdlOrBkDown() +"#"+
							objNonProductionDetails.getRsn() +"#"+
							objNonProductionDetails.getNprodTotHrs() +"#"+
							objNonProductionDetails.getShiftId() +"#"+
							objNonProductionDetails.getRsnId();
						Vector vec_EmpDet = objNonProductionDetails.getNonprodnEmpHrsDetails();
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
			temp.hidNonProdDet.value = '<%= prodList %>';		
		}
		if (temp.showCount.value != "")
		{
			if (temp.proMachine.value != "0")
			{
				tblNonProdRsn.style.display = 'block';
				tblEmployeeDetails.style.display = 'block';
			}
		}

		if (temp.hidNonProdDet.value != "" && temp.hidNonProdDetSel.value == "") /* Checking and loading the buffer list only the unchecked entries */
		{
			tblBuffer.style.display = 'block';
			var prodDet = (temp.hidNonProdDet.value).split('$');
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
					obj.children(0).children(0).children(7).children(0).value = prodPrnlDet[6];
					obj.children(0).children(0).children(8).children(0).value = prodPrnlDet[7];
					obj.children(0).children(0).children(9).children(0).value = prodPrnlDet[8];
					obj.children(0).children(0).children(10).children(0).value = prodPrnlDet[9];
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
					obj.children(len-1).children(0).children(7).children(0).value = "";
					obj.children(len-1).children(0).children(8).children(0).value = "";
					obj.children(len-1).children(0).children(9).children(0).value = "";
					obj.children(len-1).children(0).children(10).children(0).value = "";

					obj.children(len-1).children(0).children(1).children(0).value = i+1;
					obj.children(len-1).children(0).children(2).children(0).value = prodPrnlDet[1];
					obj.children(len-1).children(0).children(3).children(0).value = prodPrnlDet[2];
					obj.children(len-1).children(0).children(4).children(0).value = prodPrnlDet[3];
					obj.children(len-1).children(0).children(5).children(0).value = prodPrnlDet[4];
					obj.children(len-1).children(0).children(6).children(0).value = prodPrnlDet[5];
					obj.children(len-1).children(0).children(7).children(0).value = prodPrnlDet[6];
					obj.children(len-1).children(0).children(8).children(0).value = prodPrnlDet[7];
					obj.children(len-1).children(0).children(9).children(0).value = prodPrnlDet[8];
					obj.children(len-1).children(0).children(10).children(0).value = prodPrnlDet[9];
				}
			}
		}
		else if (obj.children.length == 1 && temp.hidNonProdDetSel.value != "")
		{
			delBufferRow();
			return false;
		}
		temp.showCount.value = "2";
	}
</script>
</head>

<body onLoad="init(); loadNonProdList(); showProdDet();">
<html:form action="frmNonProdAdd">
<html:hidden property="formAction"/>
<html:hidden property="showCount"/>
<html:hidden property="minQty" value="<%= frm.getMinQty() %>"/>
<html:hidden property="hidNonProdDet" value="<%= frm.getHidNonProdDet() %>"/> <!-- Holding the Employee Details -->
<html:hidden property="hidUserId"/> <!-- Holding the User Name -->
	<table width="100%" cellspacing="0" cellpadding="10">
    <tr> 
	<td>
		<table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
	    <tr>
			<td><bean:message key="prodacs.nonproduction.header"/></td>
	    </tr>
        </table>
        <br> 
        <table width="100" cellspacing="0" cellpadding="0" align="right">
	    <tr> 
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New NonProduction Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1018" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='List NonProduction Info'; return true"  onMouseOut="window.status=''; return true" resourceId="18" text="[ List ]" classId="TopLnk" onClick="javaScript:listItem();"/>
	    </tr>
        </table>
        <br>
        <table>
		<tr> 
			<td colspan='2'> <font size="1px" face="Verdana">
				<html:errors/></font>
  		    </td>
		</tr>
		<tr>
		</td>
<%		
		if (frm.getFormAction().equalsIgnoreCase("add"))
		{
			hm_Result = (HashMap) request.getAttribute("nonprodresult");
			if (BuildConfig.DMODE)
				System.out.println("Result: "+ hm_Result);
			
			/* For Success */
			vec_Success = (Vector) hm_Result.get("Success");
			if (BuildConfig.DMODE)
				System.out.println("vec_Success: "+ vec_Success.size());
			if (vec_Success.size() == 1)
			{
				out.println("<tr><td colspan='2'><font size='2px' face='Verdana' color='#3323C7'><img src='/ProDACS/images/active.gif' border='0'> "+vec_Success.size()+" Non-Production Entry Added. </font></td></tr>");
				out.println("<tr>&nbsp;&nbsp;&nbsp;</tr>");
				frm.setShowCount("");
			}
			else if (vec_Success.size() > 1)
			{
				out.println("<tr><td colspan='2'> <font size='2px' face='Verdana' color='#3323C7'><img src='/ProDACS/images/active.gif' border='0'> "+vec_Success.size()+" Non-Production Entries are Added. </font></td></tr>");
				out.println("<tr>&nbsp;&nbsp;&nbsp;</tr>");
			}
			
			/* For Machine Failure */
			vec_MachineFail = (Vector) hm_Result.get("MachineFailure");
			if (BuildConfig.DMODE)
				System.out.println("Machine Failure: "+ vec_MachineFail.size());
			
			/* For Employee Failure */
			vec_EmpFail = (Vector) hm_Result.get("EmpFailure");
			if (BuildConfig.DMODE)
				System.out.println("Employee Failure: "+ vec_EmpFail.size());
			
			/* For Employee Failure */
			vec_DutyFail = (Vector) hm_Result.get("DutyFailure");
			if (BuildConfig.DMODE)
				System.out.println("Duty Failure: "+ vec_DutyFail.size());
			
			if (vec_MachineFail.size() == 1)
			{
				out.println("<tr><td colspan='2'><font size='2px' face='Verdana' color='#3323C7'><img src='/ProDACS/images/inactive.gif' border='0'> "+vec_MachineFail.size()+" Non-Production Entry Not Added due to Machine Hours Invalid. </font></td></tr>"); 
			}
			else if (vec_MachineFail.size() > 1)
			{
				out.println("<tr><td colspan='2'> <font size='2px' face='Verdana' color='#3323C7'><img src='/ProDACS/images/inactive.gif' border='0'> "+vec_MachineFail.size()+" Non-Production Entries are Not Added due to Machine Hours Invalid. </font></td></tr>"); 
			}
			if (vec_EmpFail.size() == 1)
			{
				out.println("<tr><td colspan='2'> <font size='2px' face='Verdana' color='#3323C7'><img src='/ProDACS/images/inactive.gif' border='0'> "+vec_EmpFail.size()+" Non-Production Entry Not Added due to Employee Hours Invalid. </font></td></tr>");
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
				out.println("<tr><td colspan='2'> <font size='2px' face='Verdana' color='#3323C7'><img src='/ProDACS/images/inactive.gif' border='0'> "+vec_EmpFail.size()+" Non-Production Entries are Not Added due to Employee Hours Invalid. </font></td></tr>");
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
			if (vec_DutyFail.size() == 1)
			{
				out.println("<tr><td colspan='2'> <font size='2px' face='Verdana' color='#3323C7'><img src='/ProDACS/images/inactive.gif' border='0'> "+vec_DutyFail.size()+" Non-Production Entry not made. Employee Duty Hours not allowed for multiple shifts. </font></td></tr>");
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
				out.println("<tr><td colspan='2'> <font size='2px' face='Verdana' color='#3323C7'><img src='/ProDACS/images/inactive.gif' border='0'> "+vec_DutyFail.size()+" Non-Production Entries not made. Employee Duty Hours not allowed for multiple shifts. </font></td></tr>");
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
		}
%>
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
                  <td width="150" class="FieldTitle"><html:text property="proDate" styleClass="TextBox" size="12" readonly="true"/> 
                    <img src='<bean:message key="context"/>/images/calendar.gif' width="16" height="15" align="absmiddle" onClick='show_calendar("proDate",NonProductionAdd.proDate.value,event.clientX,event.clientY,false); clearAll();' onMouseOver="this.style.cursor='hand'"></td>
                    
                  <td width="50" class="FieldTitle"><bean:message key="prodacs.production.shift"/><span class="mandatory">*</span></td>
                  <td width="1" class="FieldTitle">:</td>
                  <td width="150" class="FieldTitle">
				  <div id="shiftName">
					  <html:select property="proShift" styleClass="Combo" onchange="loadMachines();">
						  <html:option value="0">-- Select Shift --</html:option>
						  <html:options collection="dateShift" property="key" labelProperty="value"/>
						</html:select>
				  </div>
				  </td>
                    
                  <td width="50" class="FieldTitle"><bean:message key="prodacs.production.machine"/><span class="mandatory">*</span></td>
                  <td width="1" class="FieldTitle">:</td>
                  <td class="FieldTitle">
				  <div id="machNames">
					<html:select property="proMachine" styleClass="Combo">
                      <html:option value="0">-- Select Machine --</html:option>
                      <html:options collection="shiftMachs" property="key" labelProperty="key"/>
                    </html:select>
				  </div>
				  </td>
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
			<table width="100%" cellspacing="0" cellpadding="0" id="tblNonProdRsn" style="display:none">
			 <tr>
				  <td width="50" class="FieldTitle"><html:radio property="nonProIdleRewrk" value="0"/>&nbsp;<bean:message key="prodacs.nonproduction.idle"/></td>
				  <td width="1"></td>
				  <td width="150" class="FieldTitle"><html:radio property="nonProIdleRewrk" value="1"/>&nbsp;<bean:message key="prodacs.nonproduction.breakdown"/></td>

				  <td width="50" class="FieldTitle"><bean:message key="prodacs.workorder.reason"/><span class="mandatory">*</span></td>
                  <td width="1" class="FieldTitle">:</td>
                  <td width="150" class="FieldTitle"><html:select property="idleBrkDwnRsn" styleClass="Combo">
                      <html:option value="0">-- Select Reason --</html:option>
                      <html:options collection="idleBrkDwnRsn" property="key" labelProperty="value"/>
                    </html:select></td>

                  <td width="70" class="FieldTitle"><bean:message key="prodacs.production.tothrs"/><span class="mandatory">*</span></td>
                  <td width="1" class="FieldTitle">:</td>
                  <td class="FieldTitle"><html:text property="proTotalHours" onkeypress="return isNumber(this);" onkeyup="return isNumber(this);" maxlength="6" styleClass="TextBox" size="12"/></td>
			 </tr>
		    </table>
		</td>
		</tr>
		</table>
		<table width="100%" cellspacing="0" cellpadding="0" id="tblEmployeeDetails" style="display:none">
		<tr>
		<td>
			<table width="100%" cellspacing="0" cellpadding="5" >
				<tr> 
				<td class="TopLnk">
				[ <a href="#" onClick="delRow(document.forms[0]);"><bean:message key="prodacs.common.delete"/></a> ]</td>
				</tr>
			</table>
			<table width="100%" cellspacing="0" cellpadding="0" >
			<tr>
			<td width="50%" valign="top" class="ViewData" id="tblEmpMstr">
				<table width="100%" cellspacing="0" cellpadding="3" id="tblEmpDetMstr">
                      <tr>
                        <td width="75" class="Header"><bean:message key="prodacs.production.emptype"/></td>
                        <td class="Header"><bean:message key="prodacs.production.empname"/></td>
                        <td width="57" class="Header"><bean:message key="prodacs.production.dutyhrs"/></td>
                        <td width="50" class="Header"><bean:message key="prodacs.production.othrs"/></td>
                        <td width="5" class="Header">&nbsp;</td>
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
						if (BuildConfig.DMODE)
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
	                <td class="TableItems"><html:text property="proDutyHrs" onkeypress="return isNumber(this);" onkeyup="return isNumber(this);" maxlength="6" size="8" styleClass="TextBox"/></td>
	                <%
	                	 }
	                	 else
	                	 {
	                %>
	                <td class="TableItems"><html:text property="proDutyHrs" onkeypress="return isNumber(this);" onkeyup="return isNumber(this);" maxlength="6" size="8" styleClass="disabledText" value="0" readonly="true"/></td>
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
	                <td class="TableItems"><html:text property="proDutyHrs" onkeypress="return isNumber(this);" onkeyup="return isNumber(this);" maxlength="6" size="8" styleClass="TextBox"/></td>
	                <%
	                	 }
	                	 else
	                	 {
	                %>
	                <td class="TableItems"><html:text property="proDutyHrs" onkeypress="return isNumber(this);" onkeyup="return isNumber(this);" maxlength="6" size="8" styleClass="disabledText" value="0" readonly="true"/></td>
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
			i++;
		}
%>                       
                  </table></td>

                  <td valign="top" class="ViewData">
				  <div style="height:80;overflow:auto;">
				  <table width="100%" cellspacing="0" cellpadding="0">
				  <tr> 
				    <td width="25" class="Header"><input type="checkbox" name="ckAll" onclick="headCkAll(this);"></td>
					<td width="100" class="Header"><bean:message key="prodacs.production.emptype"/></td>
					<td class="Header"><bean:message key="prodacs.production.empname"/></td>
					<td width="60" class="Header"><bean:message key="prodacs.production.dutyhrs"/></td>
					<td width="50" class="Header"><bean:message key="prodacs.production.othrs"/></td>
				  </tr>
				  </table>
				  <table width="100%" cellspacing="0" cellpadding="0" id="tblEmpDetails">
				  <tr> 
    			    <td width="22" class="TableItems"><input type="checkbox" name="ckdCkBx"></td>
					<td width="97" class="TableItems"><input type="text" class="TextBoxFull" readonly name="empTypName"></td>
					<td class="TableItems"><input type="text" class="TextBoxFull" readonly name="empName"></td>
					<td width="57" class="TableItems"><input type="text" class="TextBoxFull" readonly name="empDutyHrs"></td>
					<td width="47" class="TableItems"><input type="text" class="TextBoxFull" readonly name="empOTHrs"></td>
					<td><input type="hidden" name="hidEmpTypId"></td>
					<td><input type="hidden" name="hidEmpId"></td>
				  </tr>
                  </table>
				  </div>
				  </td>
				<br>
				<table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
				<tr>
					<td>
						<html:button property="proNextBtn4" styleClass="Button" onclick="enterProDet();">Add</html:button>
					</td>
				</tr>
				</table>
		</tr>
		</td>
		</table>

		<!-- Buffering the Production Values -->
		<table width="100%" cellpadding="0" cellspacing="0" id="tblBuffer" style="display:none" >
        <tr>
		<td>
			<fieldset id="FieldSet"><legend class="FieldTitle"> Current Non-Production Entries </legend>
				<table width="100%" cellspacing="0" cellpadding="5">
				<tr> 
               		<td class="TopLnk">
               		[ <a href="#" onClick="modifyRow(document.forms[0]);"><bean:message key="prodacs.common.modify"/></a> ] [ <a href="#" onClick="delBufferRow(document.forms[0]);"><bean:message key="prodacs.common.delete"/></a> ]</td>
				</tr>
				</table>
            
				<table width="100%" cellspacing="0" cellpadding="0">
					<tr> 
						<td width="25" class="Header"><input name="chkAllProdBufHead" type="checkbox" id="CheckAllDymSub" onClick="chkAllTblBffer();"/></td>
						<td width="50" class="Header"><bean:message key="prodacs.common.sno"/></td>
						<td width="100" class="Header"><bean:message key="prodacs.job.date"/></td>
						<td width="100" class="Header"><bean:message key="prodacs.production.shift"/></td>
						<td width="100" class="Header"><bean:message key="prodacs.production.machine"/></td>
						<td class="Header"><bean:message key="prodacs.nonproduction.idlebreakdown"/></td>
						<td width="100" class="Header"><bean:message key="prodacs.production.reason"/></td>
						<td width="93" class="Header"><bean:message key="prodacs.production.tothrs"/></td>
					</tr>
				</table>

				<table width="100%" cellspacing="0" cellpadding="0" id="tblBufferList">
					<tr> 
					  <td width="22" class="TableItems"><input type="checkbox" name="chkProdList"/></td>
					  <td width="47" class="TableItems"><input readonly name="nprodSno" class="TextBoxFull" /></td>
					  <td width="97" class="TableItems"><input readonly name="nprodDate" class="TextBoxFull" /></td>
					  <td width="97" class="TableItems"><input readonly name="nprodShift" class="TextBoxFull" /></td>
					  <td width="97" class="TableItems"><input readonly name="nprodMachine" class="TextBoxFull" /></td>
					  <td class="TableItems"><input readonly name="nprodIdleBrkDwn" class="TextBoxFull" /></td>
					  <td width="97" class="TableItems"><input readonly name="nprodReason" class="TextBoxFull" /></td>
					  <td width="90" class="TableItems"><input readonly name="prodTotHrs" class="TextBoxFull" /></td>
					  <td><input type="hidden" name="nprodShiftId"/></td>
					  <td><input type="hidden" name="nprodRsnId"/></td>
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

	</tr>
	</table>
</tr>
</table>
<input type="hidden" name="hidNonProdDetSel"/>
</html:form>
</body>
</html:html>
