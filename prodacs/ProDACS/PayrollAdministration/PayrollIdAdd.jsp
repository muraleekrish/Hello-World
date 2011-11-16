<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Map"%>
<%@ page import="com.savantit.prodacs.facade.SessionPayrollDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionPayrollDetailsManager"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<useradmin:userrights resource="1041"/>

<% 
	if(BuildConfig.DMODE)
		System.out.println("Payroll Id Update");
	EJBLocator obj = new EJBLocator(); /* Create an Object for the EJBLocator */
	HashMap currentCycleType = new HashMap();
	try
	{
		/* Setting JNDI Name and Environment */
		obj.setJndiName("SessionPayrollDetailsManagerBean");
		obj.setEnvironment();
	
		/* Object creation for Home and Remote Interfaces */
		SessionPayrollDetailsManagerHome objPayrollDetailsHome = (SessionPayrollDetailsManagerHome) PortableRemoteObject.narrow(obj.getHome(),SessionPayrollDetailsManagerHome.class);
		SessionPayrollDetailsManager objPayrollDetails = (SessionPayrollDetailsManager) PortableRemoteObject.narrow(objPayrollDetailsHome.create(),SessionPayrollDetailsManager.class);
		
		currentCycleType = objPayrollDetails.getCurrentPayrollCycle();
		pageContext.setAttribute("currentCycleType",currentCycleType);
		if(BuildConfig.DMODE)
			System.out.println("CurrentCycle Type (HM):"+currentCycleType);
	}
	catch(Exception e)
	{
		if(BuildConfig.DMODE)
		{
			System.out.println("Error in PayrollIdUpdate.jsp");
			e.printStackTrace();
		}
	}
%>
<html:html>
<head>
<title><bean:message key="prodacs.common.header"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script language="Javascript" type="text/Javascript">
	function addItem()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/PayrollAdministration/PayrollIdAdd.jsp';
		temp.submit();
	}
	
	function listItem()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/PayrollAdministration/PayrollId.jsp';
		temp.submit();
	}
	
	function addId()
	{
		temp = document.forms[0];
		temp.formAction.value = "addId";
		temp.submit();
	}
</script>
</head>
<body>
<html:form action="frmPayrollIdAdd">
<html:hidden property="formAction"/>
<table width="100%" cellspacing="0" cellpadding="10">
<tr>
<td>
	<table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
      <tr>
      	<td><bean:message key="prodacs.payroll.payrollidadd.header"/></td>
	</tr>
      </table>
      <br>
      <table width="100" cellspacing="0" cellpadding="0" align="right">
      <tr> 
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add PayrollId Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1014" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='List PayrollId Info'; return true"  onMouseOut="window.status=''; return true" resourceId="14" text="[ List ]" classId="TopLnk" onClick="javaScript:listItem();"/>
	</tr>
      </table>

		<font class="message">
		<html:messages id="messageid" message="true">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="messageid" />
		</html:messages>
		</font>
      <table>
		<tr><td colspan='2'> <font size="1px" face="Verdana"><html:errors/></font></td></tr>
	</table>
    <br>
      <table width="100%" cellspacing="0" cellpadding="0">
	    <%
	    	Set set = currentCycleType.entrySet();
	    	Iterator it = set.iterator();
	    	while(it.hasNext())
	    	{
	    		Map.Entry me = (Map.Entry)it.next();
	    	%>
          <tr> 
            <td width="150" class="FieldTitle"><bean:message key="prodacs.payroll.payrollcycletype"/></td>
            <td width="1" class="FieldTitle">:</td>
            <td class="ViewData"><%= me.getKey() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.payroll.payrollday"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= me.getValue() %></td>
          </tr>
	    <%
	    	}
	    %>
        </table><br>
	<table width="100%" cellspacing="0" cellpadding="0" id="BtnBg">
	<tr>
	<td>
		<html:button property="updateId" styleClass="button" value="Add PayrollId" onclick="javaScript:addId();"/>
	</td>
	</tr>
	</table>     
    
</html:form>
</body>
</html:html>