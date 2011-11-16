<%@ page language = "java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<bean:parameter id="id" name="id" value="0"/>
<%@ page import = "com.savantit.prodacs.util.EJBLocator"%>
<%@ page import = "javax.rmi.PortableRemoteObject" %>
<%@ page import = "java.util.Vector" %>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<%@ page import="com.savantit.prodacs.facade.SessionWorkOrderDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionWorkOrderDetailsManager"%>
<%@ page import="com.savantit.prodacs.businessimplementation.workorder.PreCloseDetails"%>
<%@ page import="com.savantit.prodacs.businessimplementation.workorder.JobQtyDetails"%>
<%@ page import="com.savantit.prodacs.businessimplementation.workorder.WOJobOpnDetails"%>
<useradmin:userrights resource="16"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("PreCloseLog View");
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	PreCloseDetails objPreCloseDetails = null;
	Vector vec_WOJobDet = new Vector();
	try
   {
		/* 	Setting the JNDI name and Environment 	*/
	   	obj.setJndiName("SessionWorkOrderDetailsManagerBean");
   		obj.setEnvironment();

		/* 	Creating the Home and Remote Objects 	*/
		SessionWorkOrderDetailsManagerHome woHomeObj = (SessionWorkOrderDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionWorkOrderDetailsManagerHome.class);
		SessionWorkOrderDetailsManager workOrderObj = (SessionWorkOrderDetailsManager)PortableRemoteObject.narrow(woHomeObj.create(),SessionWorkOrderDetailsManager.class);

		objPreCloseDetails = workOrderObj.getPreCloseDetails(Integer.parseInt(id));

	}catch (Exception e)
	{
		if (BuildConfig.DMODE)
			e.printStackTrace();
	}
%>
<html:html>
<head>
<title>Prodacs</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script language="Javascript" type="text/Javascript">
	function listItem()
	{
		temp = document.forms[0];
		if ((temp.frmDate.value != "" ) || (temp.toDate.value != "") || (temp.jbName.value != "") || (temp.jbSrchTab.value != "") || (temp.woNo.value != "") || (temp.woNoCombo.value != ""))
		{
			temp.action = '<bean:message key="context"/>/frmPreClsList.do?formAction=afterView';
			temp.submit();
		}
		else
		{
			temp.action = '<bean:message key="context"/>/WorkOrder/PreCloseLogList.jsp';
			temp.submit();
		}
	}

	function addItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/WorkOrder/PreCloseLogAdd.jsp';
		document.forms[0].submit();
	}

	function loadToHidden()
	{
		temp = document.forms[0];
		temp.frmDate.value = '<%= request.getParameter("preCloseLogFromDate") %>';
		temp.toDate.value = '<%= request.getParameter("preCloseLogToDate") %>';
		temp.jbName.value = '<%= request.getParameter("jobNameText") %>';
		temp.jbSrchTab.value = '<%= request.getParameter("preCloseLogJobName") %>';
		temp.woNo.value = '<%= request.getParameter("woNoText") %>';
		temp.woNoCombo.value = '<%= request.getParameter("preCloseLogWOHash") %>';

		temp.ids.value = temp.id.value;
	}

</script>
</head>

<body onload="loadToHidden();">
<html:form action="frmPreClsLogEdit">
<html:hidden property="id"/>
<html:hidden property="ids"/>
<html:hidden property="formAction"/>
<input type="hidden" name="frmDate"/>
<input type="hidden" name="toDate"/>
<input type="hidden" name="jbName"/>
<input type="hidden" name="jbSrchTab"/>
<input type="hidden" name="woNo"/>
<input type="hidden" name="woNoCombo"/>

  <table width="100%" cellspacing="0" cellpadding="10">
    <tr>
      <td><table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
          <tr>
            <td><bean:message key="prodacs.workorder.precloselog"/></td>
          </tr>
        </table>
        <br>
        <table width="100" cellspacing="0" cellpadding="0" align="right">
        <tr> 
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New PreCloseLog Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1016" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='List PreCloseLog Info'; return true"  onMouseOut="window.status=''; return true" resourceId="16" text="[ List ]" classId="TopLnk" onClick="javaScript:listItem();"/>
        </tr>
        </table>
        <br>
        <table width="100%" cellspacing="0" cellpadding="0">
          <tr> 
            <td width="100" class="FieldTitle"><bean:message key="prodacs.workorder.workorder"/></td>
            <td width="1" class="FieldTitle">:</td>
            <td width="150" class="ViewData"><%= objPreCloseDetails.getWorkOrderNo() %></td>
            <td width="75" class="FieldTitle"><bean:message key="prodacs.job.jobname"/></td>
            <td width="1" class="FieldTitle">:</td>
            <td class="ViewData"><%= objPreCloseDetails.getJbName() %></td>
          </tr>
          <tr>
            <td class="FieldTitle"><bean:message key="prodacs.job.drawing"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objPreCloseDetails.getJbDwgNo() %></td>
            <td class="FieldTitle"><bean:message key="prodacs.job.revision"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objPreCloseDetails.getJbRvsnNo() %></td>
          </tr>
          <tr>
            <td class="FieldTitle"><bean:message key="prodacs.job.materialtype"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objPreCloseDetails.getJbMatlTyp() %></td>
            <td class="FieldTitle"><bean:message key="prodacs.workorder.reason"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objPreCloseDetails.getPreCloseReason() %></td>
          </tr>
        </table>
        <br>
        <table width="100%" cellspacing="0" cellpadding="3">
          <tr> 
            <td width="100" class="Header"><bean:message key="prodacs.workorder.jobqtysno"/></td>
            <td width="200" class="Header"><bean:message key="prodacs.workorder.preclosedoperationsno"/></td>
            <td class="Header"><bean:message key="prodacs.workorder.preclosedoperationname"/></td>
          </tr>
<%
	Vector vec_preClsDet = new Vector();
	vec_preClsDet = objPreCloseDetails.getJobQtyDetails();
	if (BuildConfig.DMODE)
		System.out.println("vec_preClsDet: "+vec_preClsDet.size());
	for (int i = 0; i < vec_preClsDet.size(); i++)
	{
		JobQtyDetails objJobQtyDetails = new JobQtyDetails();
		objJobQtyDetails = (JobQtyDetails) vec_preClsDet.get(i);
		Vector vec_opnDet = new Vector();
		vec_opnDet = objJobQtyDetails.getWOJobOpnDetails();
		if (BuildConfig.DMODE)
			System.out.println("vec_opnDet: "+vec_opnDet);
		for (int j = 0; j < vec_opnDet.size(); j++)
		{
			WOJobOpnDetails objWOJobOpnDet = new WOJobOpnDetails();
			objWOJobOpnDet = (WOJobOpnDetails) vec_opnDet.get(j);
%>          
          <tr> 
            <td class="TableItems"><%= objJobQtyDetails.getJobQtySno() %></td>
            <td class="TableItems"><%= objWOJobOpnDet.getOpnSerialNo() %></td>
            <td class="TableItems"><%= objWOJobOpnDet.getOpnName() %></td>
          </tr>
<%
		}
	}
%>          
        </table>
        <br>
        <table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
          <tr>
            <td><html:button property="retPreCloseLogList" styleClass="Button" onclick="javascript:listItem()">Return to Pre-Close Log List</html:button></td>
          </tr>
        </table></td>
    </tr>
  </table>
</html:form>
</body>
</html:html>
