<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<%@ page import="com.savantit.prodacs.facade.SessionCustomerDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionCustomerDetailsManager"%>
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<useradmin:userrights resource="40"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("WORererence Report Starts.....");
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	HashMap customerType = new HashMap();
	HashMap customerName = new HashMap();
	try
	{
		/* 	Setting the JNDI name and Environment 	*/
		obj.setJndiName("SessionCustomerDetailsManagerBean");
   		obj.setEnvironment();

		/* 	Creating the Home and Remote Objects 	*/
		SessionCustomerDetailsManagerHome custHomeObj = (SessionCustomerDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionCustomerDetailsManagerHome.class);
		SessionCustomerDetailsManager custObj = (SessionCustomerDetailsManager)PortableRemoteObject.narrow(custHomeObj.create(),SessionCustomerDetailsManager.class);
		
		/*	For Customer Type */
		customerType = custObj.getCustomerTypes();

		/* For Employee Name */
		if (request.getParameter("flag") != null)
		{
			if (BuildConfig.DMODE)
				System.out.println("CustTypId: "+ request.getParameter("flag"));
			customerName = custObj.getCustomerNameByType(Integer.parseInt(request.getParameter("flag").trim()));
		}
	}catch(Exception e)
	{
		if (BuildConfig.DMODE)
			e.printStackTrace();
	}
	
%>		

<html>
<head>
	<title>WO Reference</title>
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
		for (var i = 0; i < document.forms[0].customerType.length; i++)
		{
			if (document.forms[0].customerType.options[i].value == document.forms[0].flag.value)
			{
				document.forms[0].customerType.selectedIndex = i;
			}
		}
	}

	function loadEmpNames()
	{
		var temp = document.forms[0];
		temp.flag.value = temp.customerType.value;
		temp.action = '<bean:message key="context"/>/Reports/WOReference/WOReference.jsp'>
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
		if (temp.customerType.value == "0")
		{
			alert ("Customer Type Required!");
			return false;
		}
		if (temp.customerName.value == "0")
		{
			alert ("Customer Name Required!");
			return false;
		}
		temp.custTypName.value = temp.customerType.options[temp.customerType.selectedIndex].text;
		temp.custName.value = temp.customerName.options[temp.customerName.selectedIndex].text;
		temp.action = '<bean:message key="context"/>/Reports/WOReference/WOReferenceResult.jsp';
		temp.submit();
	}
</script>

<body onload="init()">
<form name="frmWOReference" >
<table width="100%" cellspacing="0" cellpadding="10">
<tr>
<td valign="top">
	<input type="hidden" name="flag"/>
	<input type="hidden" name="custTypName"/>
	<input type="hidden" name="custName"/>
	<table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
	<tr>
         	<td><bean:message key="prodacs.reports.WOReference.title"/></td>
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
	            	<td width="100" class="FieldTitle"><bean:message key="prodacs.customer.customertype"/></td>
	            	<td width="1" class="FieldTitle">:</td>
	            	<td width="150" class="FieldTitle"><select name="customerType" class="Combo" onchange="loadEmpNames();">
	                		<option value="0">-- Choose Type --</option>
<%
	Set set = customerType.entrySet();
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
	              	<td width="100" class="FieldTitle"><bean:message key="prodacs.customer.customername"/></td>
	            	<td width="1" class="FieldTitle">:</td>
	            	<td class="FieldTitle">
	            	<select name="customerName" class="Combo">
	                		<option value="0">-- Choose Type --</option>
<%
	Set set1 = customerName.entrySet();
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
				<img src='<bean:message key="context"/>/images/calendar.gif' name='calImg' align='absmiddle' onclick='show_calendar("startDate",frmWOReference.startDate.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"></td>
				
				<td class="FieldTitle"><bean:message key="prodacs.workorder.todate"/></td>
				<td class="FieldTitle">:</td>
				<td class="FieldTitle"><input name="endDate" type="text" class="TextBox" readonly size="11"> 
				<img src='<bean:message key="context"/>/images/calendar.gif' name='calImg' align='absmiddle' onclick='show_calendar("endDate",frmWOReference.endDate.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"></td>
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
