<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<%@ page import="com.savantit.prodacs.facade.SessionEmpDetailsManager"%>
<%@ page import="com.savantit.prodacs.facade.SessionEmpDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<useradmin:userrights resource="34"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("Employee Performance Report Starts.....");
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	HashMap employeeType = new HashMap();
	HashMap employeeName = new HashMap();
	try
	{
		/* 	Setting the JNDI name and Environment 	*/
		obj.setJndiName("SessionEmpDetailsManagerBean");
   		obj.setEnvironment();

		/* 	Creating the Home and Remote Objects 	*/
		SessionEmpDetailsManagerHome empHomeObj = (SessionEmpDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionEmpDetailsManagerHome.class);
		SessionEmpDetailsManager empObj = (SessionEmpDetailsManager)PortableRemoteObject.narrow(empHomeObj.create(),SessionEmpDetailsManager.class);
		
		/*	For Employee Type */
		employeeType = empObj.getAllEmployeeTypes();

		/* For Employee Name */
		if (request.getParameter("flag") != null)
		{
			if (BuildConfig.DMODE)
				System.out.println("EmpTypId: "+ request.getParameter("flag"));
			employeeName = empObj.getAllEmployeesByType(Integer.parseInt(request.getParameter("flag").trim()));
		}
	}catch(Exception e)
	{
		if (BuildConfig.DMODE)
		e.printStackTrace();
	}
	
%>		

<html>
<head>
	<title>Employee Performance</title>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
	<script src='<bean:message key="context"/>/library/jscripts.js'></script>
	<script src='<bean:message key="context"/>/library/calendar_search.js'></script>
</head>

<script>
	var oPopup;
	function init()
	{
		oPopup = window.createPopup();
		if ('<%= request.getParameter("mesg")%>' == "success")
		{
			mailSuccess.style.display = 'block';
		}
		else if ('<%= request.getParameter("mesg")%>' == "failure")
		{
			mailFailed.style.display = 'block';
		}
		
		/* If the Start Date and End Date entered, then, */
		var temp = document.forms[0];
		
		if ('<%= request.getParameter("startDate")%>' == 'null')
		{
			temp.startDate.value = "";
		}
		else
		{
			temp.startDate.value = '<%= request.getParameter("startDate")%>';
		}

		if ('<%= request.getParameter("endDate")%>' == 'null')
		{
			temp.endDate.value = "";
		}
		else
		{
			temp.endDate.value = '<%= request.getParameter("endDate")%>';
		}

		/* Check the Particular Employee Type */
		document.forms[0].flag.value = '<%= request.getParameter("flag") %>';
		for (var i = 0; i < document.forms[0].employeeType.length; i++)
		{
			if (document.forms[0].employeeType.options[i].value == document.forms[0].flag.value)
			{
				document.forms[0].employeeType.selectedIndex = i;
			}
		}
	}

	function loadEmpNames()
	{
		var temp = document.forms[0];
		temp.flag.value = temp.employeeType.value;
		temp.action = '<bean:message key="context"/>/Reports/EmployeePerformance/EmployeePerformance.jsp'>
		temp.submit();
	}

	function generateReports()
	{
		var temp = document.forms[0];
		if(temp.startDate.value == "")
		{
			alert("Start Date Required!");
			return false;
	    }
		if(temp.endDate.value == "")
		{
			alert("End Date Required!");
			return false;	     
		}
		if (temp.employeeType.value == "0")
		{
			alert ("Employee Type Required!");
			return false;
		}
		if (temp.employeeName.value == "0")
		{
			alert ("Employee Name Required!");
			return false;
		}
		temp.empTypName.value = temp.employeeType.options[temp.employeeType.selectedIndex].text;
		temp.empName.value = temp.employeeName.options[temp.employeeName.selectedIndex].text;
		temp.action = '<bean:message key="context"/>/Reports/EmployeePerformance/EmployeePerformanceResult.jsp';
		temp.submit();
	}
</script>

<body onload="init()">
<form name="frmEmployeePerformance" >
<table width="100%" cellspacing="0" cellpadding="10">
<tr>
<td valign="top">
	<input type="hidden" name="flag"/>
	<input type="hidden" name="empTypName"/>
	<input type="hidden" name="empName"/>
	<table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
	<tr>
         	<td><bean:message key="prodacs.reports.employeeperformance.title"/></td>
	</tr>
	</table>

	<table width="100%" cellpadding="0" cellspacing="0" id="mailSuccess" style="display:none">
	<tr><td>&nbsp;&nbsp;&nbsp;</td></tr>
	<tr>
         	<td class="FieldTitle"> Mail Sent Successfully!</td>
	</tr>
	</table>
	<table width="100%" cellpadding="0" cellspacing="0" id="mailFailed" style="display:none">
	<tr><td>&nbsp;&nbsp;&nbsp;</td></tr>
	<tr>
         	<td class="FieldTitle"> Mail Sending Failed!</td>
	</tr>
	</table>

	<br>
	<table width="100%" cellspacing="0" cellpadding="0">
	          <tr> 
	            	<td width="100" class="FieldTitle"><bean:message key="prodacs.employee.employeetype"/></td>
	            	<td width="1" class="FieldTitle">:</td>
	            	<td width="150" class="FieldTitle"><select name="employeeType" class="Combo" onchange="loadEmpNames();">
	                		<option value="0">-- Choose Type --</option>
<%
	Set set = employeeType.entrySet();
	Iterator i = set.iterator();
	while (i.hasNext())
	{
		Map.Entry me = (Map.Entry) i.next();
%>
				<option value='<%= me.getKey() %>'><%= me.getValue()%></option>
<%
	}
%>	                		
	              		</select>
	              	</td>
	              	<td width="100" class="FieldTitle"><bean:message key="prodacs.employee.employeename"/></td>
	            	<td width="1" class="FieldTitle">:</td>
	            	<td class="FieldTitle">
	            	<select name="employeeName" class="Combo">
	                		<option value="0">-- Choose Type --</option>
<%
	Set set1 = employeeName.entrySet();
	Iterator i1 = set1.iterator();
	while (i1.hasNext())
	{
		Map.Entry me1 = (Map.Entry) i1.next();
%>
				<option value='<%= me1.getKey() %>'><%= me1.getValue()%></option>
<%
	}
%>	                	
	              	</select>
	              	</td>
	          </tr>
			<tr> 
				<td class="FieldTitle"><bean:message key="prodacs.workorder.fromdate"/></td>
				<td class="FieldTitle">:</td>
				<td class="FieldTitle"><input name="startDate" type="text" class="TextBox" readonly size="11"> 
				<img src='<bean:message key="context"/>/images/calendar.gif' name='calImg' align='absmiddle' onclick='show_calendar("startDate",frmEmployeePerformance.startDate.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"></td>
				
				<td class="FieldTitle"><bean:message key="prodacs.workorder.todate"/></td>
				<td class="FieldTitle">:</td>
				<td class="FieldTitle"><input name="endDate" type="text" class="TextBox" readonly size="11"> 
				<img src='<bean:message key="context"/>/images/calendar.gif' name='calImg' align='absmiddle' onclick='show_calendar("endDate",frmEmployeePerformance.endDate.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"></td>
			</tr>
	</table>
	<br>
	<table width="100%" cellspacing="0" cellpadding="0" id="BtnBg">
		<tr>
			<td>
				<input name="GenRprt" type="button" class="Button" value="Generate Reports" onclick="generateReports();">
			</td>
		</tr>
	</table>
</td>
</tr>
</table>
</form>
</body>
</html>
