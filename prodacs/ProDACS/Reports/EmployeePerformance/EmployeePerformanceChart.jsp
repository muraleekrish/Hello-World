<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<%@ page import="com.savantit.prodacs.facade.SessionEmpDetailsManager"%>
<%@ page import="com.savantit.prodacs.facade.SessionEmpDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<useradmin:userrights resource="58"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("Employee Performance Chart Starts.....");
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	HashMap employeeType = new HashMap();
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
		pageContext.setAttribute("employeeType",employeeType);
	}
	catch(Exception e)
	{
		if (BuildConfig.DMODE)
		e.printStackTrace();
	}
	
%>		

<html:html>
<head>
	<title>Employee Performance</title>
	<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
	<script src='<bean:message key="context"/>/library/jscripts.js'></script>
	<script src='<bean:message key="context"/>/library/calendar_search.js'></script>
	<script src='<bean:message key="context"/>/library/prototype.js'></script>
</head>

<script>
	var oPopup;
	function init()
	{
		oPopup = window.createPopup();
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
		temp.formAction.value = "showImage";
		//temp.submit();
		var startDate = temp.startDate.value;
		var endDate = temp.endDate.value;
		var employeeType = temp.employeeType.value;
		if(employeeType != "0")
		{
			var url = '/ProDACS/servlet/GraphServlet';
			var value = 2;
			var param = 'startDate='+startDate+'&endDate='+endDate+'&employeeType='+employeeType+'&value='+value;
			/*if (window.XMLHttpRequest) // Non-IE browsers
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
			}*/
		
		document.getElementById('barChart').children[0].src = url+"?"+param;
		chartTbl.style.display ='block';
		}
	}

	function showResponse(req)
	{
		var temp = document.forms[0];
		if(temp.formAction.value != "")
		{
			document.getElementById('barChart').innerHTML = req.responseText;
			chartTbl.style.display ='block';
		}
	}

	function closeReport()
	{
		var temp = document.forms[0];
		chartTbl.style.display = 'none';
		temp.employeeType.value = "0";
		temp.startDate.value = "";
		temp.endDate.value = "";
	}

	function showHide()
	{
		var temp = document.forms[0];
		if (temp.formAction.value == "showImage")
		{
			chartTbl.style.display = 'block';
		}
		else
		{
			chartTbl.style.display = 'none';
		}
	}

</script>

<body onload="init();showHide();">
<html:form action="/frmEmpPerformanceChart">
<html:hidden property="formAction"/>
<table width="100%" cellspacing="0" cellpadding="10">
<tr>
<td valign="top">
	<table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
	<tr>
         	<td>Employee Performance - Chart</td>
	</tr>
	</table>
	<br>
	<table width="100%" cellspacing="0" cellpadding="0">
	<tr> 
		<td width="80" class="FieldTitle"><bean:message key="prodacs.workorder.fromdate"/></td>
		<td width="1" class="FieldTitle">:</td>
		<td width="130" class="FieldTitle"><html:text property="startDate" styleClass="TextBox" readonly="true" size="11"/> 
		<img src='<bean:message key="context"/>/images/calendar.gif' name='calImg' align='absmiddle' onclick='show_calendar("startDate",EmployeePerformanceGraph.startDate.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"></td>
		
		<td width="80" class="FieldTitle"><bean:message key="prodacs.workorder.todate"/></td>
		<td width="1" class="FieldTitle">:</td>
		<td width="130" class="FieldTitle"><html:text property="endDate" styleClass="TextBox" readonly="true" size="11"/> 
		<img src='<bean:message key="context"/>/images/calendar.gif' name='calImg' align='absmiddle' onclick='show_calendar("endDate",EmployeePerformanceGraph.endDate.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"></td>

		<td width="100" class="FieldTitle"><bean:message key="prodacs.employee.employeetype"/></td>
    	<td width="1" class="FieldTitle">:</td>
    	<td width="150" class="FieldTitle">
		<html:select property="employeeType" styleClass="Combo">
        		<html:option value="0">-- Choose Type --</html:option>
				<html:options collection="employeeType" property="key" labelProperty="value"/>
      	</html:select>
      	</td>
	</tr>
	</table>
	<br>
	<table width="100%" cellspacing="0" cellpadding="0" id="BtnBg">
	<tr>
		<td><input name="GenRprt" type="button" class="Button" value="Generate Reports" onclick="generateReports();"></td>
	</tr>
	</table>
	<br>
	<table width="100%" cellspacing="0" cellpadding="0" id="chartTbl" style="display:none">
	<tr>
		<td class="TopLnk"><a href="javascript:closeReport()">Close</a></td>
	</tr>
	<tr>
		<td align="center">
			<div id="barChart"><img src="" border=0/></div>
		</td>
	</tr>
	</table>
</td>
</tr>
</table>
</html:form>
</body>
</html:html>
