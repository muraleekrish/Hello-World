<%@ page language = "java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri = "/WEB-INF/struts-logic.tld" prefix = "logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<jsp:useBean id="frm" class="com.savantit.prodacs.presentation.tableadmin.production.NonProductionEditForm" />
<jsp:setProperty name="frm" property="*" /> 
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.GregorianCalendar"%>
<%@ page import="java.util.StringTokenizer"%>
<%@ page import="com.savantit.prodacs.businessimplementation.securityadmin.UserAuthDetails"%>

<%@ page import="com.savantit.prodacs.facade.SessionNonProductionDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionNonProductionDetailsManager"%>
<useradmin:userrights resource="1018"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("Production Edit Form");
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
		
		/* For Shift */
		HashMap shiftDefnNameList = nonProductionObj.getShiftDefnNameList();
		pageContext.setAttribute("shiftDefnNameList",shiftDefnNameList);
		
		/* For Machine */
		HashMap machineDet = nonProductionObj.getAllMachines();
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
		
			shiftDefnNameList = nonProductionObj.getShiftDefnNameList(gc.getTime());
			if (BuildConfig.DMODE)
				System.out.println("Shifts for Date(HM) :"+shiftDefnNameList);
			pageContext.setAttribute("shiftDefnNameList",shiftDefnNameList);
		}
		else
		{
			pageContext.setAttribute("shiftDefnNameList",shiftDefnNameList);
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
		
			machineDet = nonProductionObj.getAllMachines(ge.getTime(),Integer.parseInt(frm.getProShift()));
			if (BuildConfig.DMODE)
				System.out.println("Machines(HM):"+machineDet);
			pageContext.setAttribute("machineDet",machineDet);
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

	function listItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/Production/NonProductionList.jsp';
		document.forms[0].submit();
	}

	function addItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/Production/NonProductionAdd.jsp';
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

		/*if (obj1.children(0).children(ind).children(0).children(0).value == "Supervisor")
		{
			if (obj1.children(0).children(ind).children(3).children(0).value != "")
			{
				alert("OT Hours cannot be entered for Supervisor!");
				obj1.children(0).children(ind).children(2).children(0).value = "";
				obj1.children(0).children(ind).children(3).children(0).value = "";
				obj1.children(0).children(ind).children(2).children(0).focus();
				return false;
			}
		}*/

		//alert (obj1.children(0).children(parseInt(val)).innerHTML);
		//alert (obj1.children(0).innerHTML);
		//alert (obj.children.length);
		
		if ((obj.children.length == "1") && (obj.children(0).children(0).children(1).children(0).value == ""))
		{
			/* Validations before shifting Employee Details */
			if (temp.proEmployeeName[ind-1].options[temp.proEmployeeName[ind-1].selectedIndex].value == "0")
			{
				alert ("Select Employee Name to Proceed!");
				return false;
			}
			if (obj1.children(0).children(ind).children(2).children(0).value == "")
			{
				alert ("Select Duty Hours to Proceed!");
				return false;
			}
			if (obj1.children(0).children(ind).children(3).children(0).value == "")
			{
				alert ("Select OT Hours to Proceed!");
				return false;
			}
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

			/* Shifting the Employee Details from Master */
			obj.children(0).children(0).children(1).children(0).value = obj1.children(0).children(ind).children(0).children(0).value;
			obj.children(0).children(0).children(2).children(0).value = temp.proEmployeeName[ind-1].options[temp.proEmployeeName[ind-1].selectedIndex].text;
			obj.children(0).children(0).children(3).children(0).value = obj1.children(0).children(ind).children(2).children(0).value;
			obj.children(0).children(0).children(4).children(0).value = obj1.children(0).children(ind).children(3).children(0).value;
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
			/* Duty + OT Hrs can't exceed Total Hours */
			if (temp.proTotalHours.value != "")
			{
				if ((parseFloat(obj1.children(0).children(ind).children(2).children(0).value) + parseFloat(obj1.children(0).children(ind).children(3).children(0).value)) > temp.proTotalHours.value)
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
			obj.children(len-1).children(0).children(3).children(0).value = obj1.children(0).children(ind).children(2).children(0).value;
			obj.children(len-1).children(0).children(4).children(0).value = obj1.children(0).children(ind).children(3).children(0).value;
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

		if (temp.proTotalHours.value == "") /* Total hrs can't be empty */
		{
			alert ("Sorry! U must enter Total Hours!");
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
				empDet = empDet + "-" + (obj.children(0).children(0).children(3).children(0).value != ""?parseFloat(obj.children(0).children(0).children(3).children(0).value):"0"); // Duty Hrs 
				empDet = empDet + "-" + (obj.children(0).children(0).children(4).children(0).value != ""?parseFloat(obj.children(0).children(0).children(4).children(0).value):"0"); // OT Hrs 
			}
			else
			{
				empDet = empDet + "" + obj.children(i).children(0).children(1).children(0).value; // EmpTyp name 
				empDet = empDet + "-" + obj.children(i).children(0).children(5).children(0).value; // Emp Typ Id 
				empDet = empDet + "-" + obj.children(i).children(0).children(2).children(0).value; // Emp Name 
				empDet = empDet + "-" + obj.children(i).children(0).children(6).children(0).value; // Emp Name Id 
				empDet = empDet + "-" + (obj.children(i).children(0).children(3).children(0).value != ""?parseFloat(obj.children(i).children(0).children(3).children(0).value):"0"); // Duty Hrs 
				empDet = empDet + "-" + (obj.children(i).children(0).children(4).children(0).value != ""?parseFloat(obj.children(i).children(0).children(4).children(0).value):"0"); // OT Hrs 
			}
			i++;
		}

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
			if ((obj.children.length == 1) && (temp.ckdCkBx(0).checked))
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

				temp.ckdCkBx.checked = false;
				temp.ckAll.checked = false;
		}
	}

	function loadEmpDet()
	{
		var temp = document.forms[0];
		var obj = document.getElementById("tblEmpDetails");
		var empDet = temp.hidEmpDet.value;
		temp.hidUserId.value = '<%= userName %>';
		if (empDet == "")
		{
			return false;
		}
		var arEmpDet = empDet.split("^");
		for (var i = 0; i < arEmpDet.length; i++)
		{
			var arEmpPerDet = arEmpDet[i].split("-");
			
			if (i == 0)
			{
				obj.children(0).children(0).children(1).children(0).value = arEmpPerDet[0];
				obj.children(0).children(0).children(2).children(0).value = arEmpPerDet[1];
				obj.children(0).children(0).children(3).children(0).value = arEmpPerDet[2];
				obj.children(0).children(0).children(4).children(0).value = arEmpPerDet[3];
				obj.children(0).children(0).children(5).children(0).value = arEmpPerDet[4];
				obj.children(0).children(0).children(6).children(0).value = arEmpPerDet[5];
			}
			else
			{
				/* Incrementing a Row */
				var newNode = obj.children(0).cloneNode(true); 
				obj.appendChild(newNode);
				var len = obj.children.length;

				obj.children(len-1).children(0).children(1).children(0).value = arEmpPerDet[0];
				obj.children(len-1).children(0).children(2).children(0).value = arEmpPerDet[1];
				obj.children(len-1).children(0).children(3).children(0).value = arEmpPerDet[2];
				obj.children(len-1).children(0).children(4).children(0).value = arEmpPerDet[3];
				obj.children(len-1).children(0).children(5).children(0).value = arEmpPerDet[4];
				obj.children(len-1).children(0).children(6).children(0).value = arEmpPerDet[5];
			}
		}
	}

	function loadToHidden()
	{
		document.forms[0].ids.value = document.forms[0].id.value;
	}

</script>
</head>

<body onLoad="init(); loadToHidden(); loadEmpDet();">
<html:form action="frmNonProdEdit">
<html:hidden property="formAction"/>
<html:hidden property="id"/>
<html:hidden property="ids"/>
<html:hidden property="minQty" value="<%= frm.getMinQty() %>"/> <!-- Holding the Minimum required no. of emp's-->
<html:hidden property="hidAllEmpDet" value="<%= frm.getHidAllEmpDet() %>"/> <!-- Holding the Employee Details -->
<html:hidden property="hidEmpDet"/> <!-- Fetch Employee Details By EJB -->
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
	</table> 
		<table width="100%" cellpadding="0" cellspacing="0">
 		<tr id="prodInfo">
        <td>
			<table width="100%" cellspacing="0" cellpadding="0">
                <tr> 
                  <td width="50" class="FieldTitle"><bean:message key="prodacs.job.date"/><span class="mandatory">*</span></td>
                  <td width="1" class="FieldTitle">:</td>
                  <td width="150" class="FieldTitle"><html:text property="proDate" styleClass="TextBox" size="12"/> 
                    <img src='<bean:message key="context"/>/images/calendar.gif' width="16" height="15" align="absmiddle" onClick='show_calendar("proDate",NonProductionEdit.proDate.value,event.clientX,event.clientY,false);clearAll();' onMouseOver="this.style.cursor='hand'"></td>
                    
                  <td width="50" class="FieldTitle"><bean:message key="prodacs.production.shift"/><span class="mandatory">*</span></td>
                  <td width="1" class="FieldTitle">:</td>
                  <td width="50" class="FieldTitle"><html:select property="proShift" styleClass="Combo" onchange="loadMachines();">
                      <html:option value="0">-- Select Shift --</html:option>
                      <html:options collection="shiftDefnNameList" property="key" labelProperty="value"/>
                    </html:select></td>
                    
                  <td width="50" class="FieldTitle"><bean:message key="prodacs.production.machine"/><span class="mandatory">*</span></td>
                  <td width="1" class="FieldTitle">:</td>
                  <td class="FieldTitle"><html:select property="proMachine" styleClass="Combo">
                      <html:option value="0">-- Select Machine --</html:option>
                      <html:options collection="machineDet" property="key" labelProperty="key"/>
                    </html:select></td>
                </tr>
			    <tr>
				  <td width="70" class="FieldTitle"><html:radio property="nonProIdleRewrk" value="0"/>&nbsp;<bean:message key="prodacs.nonproduction.idle"/></td>
				  <td width="5"></td>
				  <td width="120" class="FieldTitle"><html:radio property="nonProIdleRewrk" value="1"/>&nbsp;<bean:message key="prodacs.nonproduction.breakdown"/></td>

				  <td width="65" class="FieldTitle"><bean:message key="prodacs.workorder.reason"/><span class="mandatory">*</span></td>
                  <td width="1" class="FieldTitle">:</td>
                  <td class="FieldTitle"><html:select property="idleBrkDwnRsn" styleClass="Combo">
                      <html:option value="0">-- Select Reason --</html:option>
                      <html:options collection="idleBrkDwnRsn" property="key" labelProperty="value"/>
                    </html:select></td>

                  <td width="80" class="FieldTitle"><bean:message key="prodacs.production.tothrs"/><span class="mandatory">*</span></td>
                  <td width="1" class="FieldTitle">:</td>
                  <td width="50" class="FieldTitle"><html:text property="proTotalHours" onkeypress="return isNumber(this);" onkeyup="return isNumber(this);" maxlength="6" styleClass="TextBox" size="12"/></td>
			    </tr>
		    </table><br>
		</td>
		</tr>
		</table>

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
		<table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
		<tr>
			<td>
				<html:button property="proNextBtn4" styleClass="Button" onclick="enterProDet();">Update</html:button>
			</td>
		</tr>
		</table>

	</tr>
	</table>
</tr>
</table>
</html:form>
</body>
</html:html>
