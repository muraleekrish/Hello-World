<%@ page language = "java" session="true"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri = "/WEB-INF/struts-logic.tld" prefix = "logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<%@ page import="com.savantit.prodacs.facade.SessionSchedulerManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionSchedulerManager"%>
<%@ page import="com.datrics.scheduler.valueobjects.JobDetails"%>

<% 
	if (BuildConfig.DMODE)
		System.out.println("Mail Scheduler Starts...");
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	JobDetails[] objJobDetails = null;
	try
	{
		/* 	Setting the JNDI name and Environment 	*/
		obj.setJndiName("SessionSchedulerManager");
		obj.setEnvironment();

		/* 	Creating the Home and Remote Objects 	*/
		SessionSchedulerManagerHome objSchedulerHome = (SessionSchedulerManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionSchedulerManagerHome.class);
		SessionSchedulerManager objScheduler = (SessionSchedulerManager)PortableRemoteObject.narrow(objSchedulerHome.create(),SessionSchedulerManager.class);
		
		objJobDetails = objScheduler.getAllScheduleDetails();
		pageContext.setAttribute("objJobDetails",objJobDetails);
		System.out.println("JobDetls Len :"+objJobDetails.length);
		for (int i = 0; i < objJobDetails.length; i++)
		{
			System.out.println("Job Name :"+objJobDetails[i].getJbName());
			System.out.println("St. Date :"+objJobDetails[i].getJbStartDate());
			System.out.println("End Date :"+objJobDetails[i].getJbEndDate());
			System.out.println("Job Type :"+objJobDetails[i].getJbType());
			System.out.println("Last Run :"+objJobDetails[i].getLastRun());
			System.out.println("Next Run :"+objJobDetails[i].getNextRun());
			System.out.println("Run Stat :"+objJobDetails[i].isLastRunStat());
		}
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}
%>

<html:html>
<head>
<title>Mail Scheduler</title>
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script src='<bean:message key="context"/>/library/jscripts.js'></script>
<script>
</script>
</script>
</head>

<body>
<html:form action="frmMailScheduler">
<html:hidden property="formAction"/>
<table width="100%" height="100%" cellpadding="10" cellspacing="0">
<tr>
	<td valign="top">
	<table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
    <tr>
    	<td>Mail Scheduler</td>
	</tr>
    </table>
    <br>
    <table width="100" cellspacing="0" cellpadding="0" border="0" align="right">
    <tr>
		<!-- <menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New Customer Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1002" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/> -->
	</tr>
    </table>
    <br>
    <font class="message"><html:messages id="messageid" message="true">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="messageid" /></html:messages></font>
	<table>
	<tr> 
		<td colspan='2'> <font size="1px" face="Verdana"><html:errors/></font></td>
	</tr>
	</table>
    <table width="100%" cellpadding="3" cellspacing="0" id="scheduleInfoList">
    <tr>
		<td width="25" class="Header"><input name="CheckAll" type="checkbox" id="CheckAll" value="checkbox" onClick="checkAll(document.MailScheduler)"></td>
    	<td class="Header"><bean:message key="prodacs.job.jobname"/></td>
        <td width="70" class="Header" nowrap>Job Type</td>
        <td width="100" class="Header" nowrap>Job Start Date</td>
        <td width="100" class="Header" nowrap>Job End Date</td>
        <td width="130" class="Header" nowrap>Job Last Run</td>
    	<td width="100" class="Header" nowrap>Last Run Status</td>
    	<td width="100" class="Header" nowrap>Job Next Run</td>
	</tr>
	<logic:iterate id="bt1" name="objJobDetails" indexId="count">
    <tr>
	<%
		if (count.intValue() % 2 == 0)
		{
	%>
			<td class="TableItems"><input name="CheckValue" type="checkbox" id="CheckValue" value='' onClick="isSimilar(this)"></td>
	    	<td class="TableItems"><input type="text" name="jobName" class="TextBoxFull" readonly maxlength="20" size="15" value='<bean:write name="bt1" property="jbName"/>'/></td>
	        <td class="TableItems">
				<logic:equal name="bt1" property="jbType" value="D">Daily</logic:equal>
				<logic:equal name="bt1" property="jbType" value="W">Weekly</logic:equal>
				<logic:equal name="bt1" property="jbType" value="M">Monthly</logic:equal>
			</td>
	        <td class="TableItems"><bean:define id="stDate1" name="bt1" property="jbStartDate"/><%= stDate1.toString().substring(0,10) %></td>
	        <td class="TableItems"><bean:define id="edDate1" name="bt1" property="jbEndDate"/><%= edDate1.toString().substring(0,10) %></td>
	        <td class="TableItems"><input type="text" name="jobLastRun" class="TextBoxFull" readonly value='<bean:write name="bt1" property="lastRun"/>'/></td>
	        <td class="TableItems">
				<logic:equal name="bt1" property="lastRunStat" value="false">Failure</logic:equal>
				<logic:equal name="bt1" property="lastRunStat" value="true">Success</logic:equal>
			</td>
	        <td class="TableItems"><input type="text" name="jobNextRun" class="TextBoxFull" readonly value='<bean:write name="bt1" property="nextRun"/>'/></td>
	<%
		}
		else
		{
	%>
			<td class="TableItems2"><input name="CheckValue" type="checkbox" id="CheckValue" value='' onClick="isSimilar(this)"></td>
	    	<td class="TableItems2"><input type="text" name="jobName" class="TextBoxFull" readonly value='<bean:write name="bt1" property="jbName"/>'/></td>
	        <td class="TableItems2">
				<logic:equal name="bt1" property="jbType" value="D">Daily</logic:equal>
				<logic:equal name="bt1" property="jbType" value="W">Weekly</logic:equal>
				<logic:equal name="bt1" property="jbType" value="M">Monthly</logic:equal>
			</td>
	        <td class="TableItems2"><bean:define id="stDate2" name="bt1" property="jbStartDate"/><%= stDate2.toString().substring(0,10) %></td>
	        <td class="TableItems2"><bean:define id="edDate2" name="bt1" property="jbEndDate"/><%= edDate2.toString().substring(0,10) %></td></td>
	        <td class="TableItems2"><input type="text" name="jobLastRun" class="TextBoxFull" readonly value='<bean:write name="bt1" property="lastRun"/>'/></td>
	        <td class="TableItems2">
				<logic:equal name="bt1" property="lastRunStat" value="false">Failure</logic:equal>
				<logic:equal name="bt1" property="lastRunStat" value="true">Success</logic:equal>
			</td>
	        <td class="TableItems2"><input type="text" name="jobNextRun" class="TextBoxFull" readonly value='<bean:write name="bt1" property="nextRun"/>'/></td>
	<%
		}
	%>
	</tr>
	</logic:iterate>
    </table>
    </td>
</tr>
</table>
</html:form>
</body>
</html:html>
