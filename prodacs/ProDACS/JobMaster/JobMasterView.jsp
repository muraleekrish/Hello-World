<%@ page language = "java" %>

<%@ taglib uri = "/WEB-INF/struts-html.tld" prefix = "html" %>
<%@ taglib uri = "/WEB-INF/struts-bean.tld" prefix = "bean" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>
<bean:parameter id="id" name="id" value="0"/>

<%@ page import = "com.savantit.prodacs.util.EJBLocator"%>
<%@ page import = "javax.rmi.PortableRemoteObject" %>
<%@ page import = "java.util.Vector" %>
<%@ page import = "java.io.InputStream" %>
<%@ page import="com.savantit.prodacs.facade.SessionJobDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionJobDetailsManager"%>
<%@ page import="com.savantit.prodacs.businessimplementation.job.JobDetails"%>
<%@ page import="com.savantit.prodacs.businessimplementation.job.OperationDetails"%>
<%@ page import="com.savantit.prodacs.util.CastorXML"%>
<%@ page import="com.savantit.prodacs.presentation.tableadmin.jobmaster.StandardHours"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<useradmin:userrights resource="11"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("Job Master View");
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	JobDetails objJobDetails = null;
	OperationDetails objOperDet = null;
    StandardHours objStdHours = new StandardHours();
	try
	{
    InputStream it = getClass().getClassLoader().getResourceAsStream("jobconfig.xml");
    objStdHours = (StandardHours)CastorXML.fromXML(it,objStdHours.getClass());
	try
	{
		/* 	Setting the JNDI name and Environment 	*/
	   obj.setJndiName("SessionJobDetailsManagerBean");
   	   obj.setEnvironment();

		/* 	Creating the Home and Remote Objects 	*/
		SessionJobDetailsManagerHome jobHomeObj = (SessionJobDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionJobDetailsManagerHome.class);
		SessionJobDetailsManager jobObj = (SessionJobDetailsManager)PortableRemoteObject.narrow(jobHomeObj.create(),SessionJobDetailsManager.class);

		objJobDetails = jobObj.getJobDetails(Integer.parseInt(id));
		if (BuildConfig.DMODE)
			System.out.println("objJobDetails..getJob_Id(): "+objJobDetails.getJob_Id());
 	}
 	catch (Exception e)
 	{
 		if (BuildConfig.DMODE)
 			e.printStackTrace();
 	}
	}catch (Exception ex)
	{
		if (BuildConfig.DMODE)
			ex.printStackTrace();
	}
%>

<html:html>
<head>
<title><bean:message key="prodacs.common.header"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script src='<bean:message key="context"/>/library/jscripts.js'></script>
<script language="Javascript" type="text/Javascript">
	var isValid = <%= objJobDetails.getJob_IsValid() %>;

	function chkMkValid()
	{
		temp = document.forms[0];
		temp.custName.value = '<%= request.getParameter("txtCustomerName") %>';
		temp.custSrchTab.value = '<%= request.getParameter("searchCustomerName") %>';
		temp.jbName.value = '<%= request.getParameter("txtJobName") %>';
		temp.jbSrchTab.value = '<%= request.getParameter("searchJobName") %>';
		temp.viewAll.value = '<%= request.getParameter("validEntry") %>';
		temp.dwgNo.value = '<%= request.getParameter("drawingNo") %>';
		temp.dwgSrchTab.value = '<%= request.getParameter("drawingSearchTab") %>';
		temp.matlType.value = '<%= request.getParameter("txtMaterial") %>';
		temp.matlSrchTab.value = '<%= request.getParameter("searchMaterial") %>';
		temp.maxItem.value = '<%= request.getParameter("maxItems") %>';
		temp.pageNo.value = '<%= request.getParameter("page") %>';

		if (document.getElementById('mkValid') != null)
		{
			if (isValid == 0)
			{
				mkValid.style.display = 'block';
				mkInValid.style.display = 'none';
			}
			else
			{
				mkValid.style.display = 'none';
				mkInValid.style.display = 'block';
			}
		}
	}
	
	function listItem()
	{
		temp = document.forms[0];
		if ((temp.custName.value != "" ) || (temp.custSrchTab.value != "") || (temp.jbName.value != "") || (temp.jbSrchTab.value != "") || (temp.viewAll.value != "") || (temp.dwgNo.value != "") || (temp.dwgSrchTab.value != "") || (temp.matlType.value != "") || (temp.matlSrchTab.value != "") || (temp.maxItem.value != "") || (temp.pageNo.value != ""))
		{
			temp.action = '<bean:message key="context"/>/frmJobList.do?formAction=afterView';
			temp.submit();
		}
		else
		{
			if (temp.maxItem.value == null)
			{
				temp.maxItems.value = "15";
				temp.action = '<bean:message key="context"/>/JobMaster/JobMasterList.jsp?maxItem='+temp.maxItem.value;
				temp.submit();
			}
		}
	}
	
	function addItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/JobMaster/JobMasterAdd.jsp';
		document.forms[0].submit();
	}

	function editItem()
	{
		if (isValid == 0)
		{
			alert("Invalid Item Cannot be Modified! ");
		}
		else
		{
			document.forms[0].action = '<bean:message key="context"/>/jobMasterEdit.do?formAction=modify&id='+<%= objJobDetails.getJob_Id() %>;
			document.forms[0].submit();
		}	
	}
	
	function loadToHidden()
	{
		document.forms[0].ids.value = document.forms[0].id.value;
	}
	
	function makeInvalidItem()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/jobMasterEdit.do?formAction=makeInvalid';
		temp.submit();
	}

	function makeValid()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/jobMasterEdit.do?formAction=makeValid';
		temp.submit();
	}
	
	function deleteItem()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/jobMasterEdit.do?formAction=delete';
		temp.submit();
				
	}
</script>
</head>

<body onload = "loadToHidden();chkMkValid();">
<html:form action="jobMasterEdit">
<html:hidden property="id"/>
<html:hidden property="ids"/>
<html:hidden property="formAction"/>
<input type="hidden" name="custName"/>
<input type="hidden" name="custSrchTab"/>
<input type="hidden" name="jbName"/>
<input type="hidden" name="jbSrchTab"/>
<input type="hidden" name="viewAll"/>
<input type="hidden" name="dwgNo"/>
<input type="hidden" name="dwgSrchTab"/>
<input type="hidden" name="matlType"/>
<input type="hidden" name="matlSrchTab"/>
<input type="hidden" name="maxItem"/>
<input type="hidden" name="pageNo"/>

  <table width="100%" cellspacing="0" cellpadding="10">
    <tr>
      <td><table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
          <tr>
            <td><bean:message key="prodacs.job.header"/></td>
          </tr>
        </table>
        <br> <table width="100" cellspacing="0" cellpadding="0" align="right">
          <tr> 
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New Job Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1011" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Modify Job Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1011" text="[ Modify ]" classId="TopLnk" onClick="javaScript:editItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Delete Job Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1011" text="[ Delete ]" classId="TopLnk" onClick="javaScript:deleteItem();"/>
 		<menuconfig:userRights url="#" id="mkInValid" onMouseOver="window.status='Make Job Info InValid'; return true"  onMouseOut="window.status=''; return true" resourceId="1011" text="[ MakeInValid ]" classId="TopLnk" onClick="javaScript:makeInvalidItem();"/>
 		<menuconfig:userRights url="#" id="mkValid" onMouseOver="window.status='Make Job Info Valid'; return true"  onMouseOut="window.status=''; return true" resourceId="1011" text="[ MakeValid ]" classId="TopLnk" style="display:none" onClick="javaScript:makeValid();"/>
          </tr>
        </table>
        <br>
        <table width="100%" cellspacing="0" cellpadding="0">
          <tr> 
            <td width="120" class="FieldTitle"><bean:message key="prodacs.job.date"/></td>
            <td width="1" class="FieldTitle">:</td>
            <td width="150" class="ViewData"><%= objJobDetails.getJob_Created_Date().toString().substring(0,10) %></td>
            <td width="110" class="FieldTitle"><bean:message key="prodacs.job.jobname"/></td>
            <td width="1" class="FieldTitle">:</td>
            <td colspan="4" class="ViewData"><%= objJobDetails.getJob_Name() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.customertype"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objJobDetails.getCust_Typ_Name() %></td>
            <td class="FieldTitle"><bean:message key="prodacs.customer.customername"/></td>
            <td class="FieldTitle">:</td>
            <td colspan="4" class="ViewData"><%= objJobDetails.getCust_Name() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.job.generalname"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objJobDetails.getGnrl_Name() %></td>
            <td class="FieldTitle"><bean:message key="prodacs.job.drawing"/></td>
            <td class="FieldTitle">:</td>
            <td width="140" class="ViewData"><%= objJobDetails.getJob_Dwg_No() %></td>
            <td width="80" class="FieldTitle"><bean:message key="prodacs.job.revision"/></td>
            <td width="1" class="FieldTitle">:</td>
            <td class="ViewData"><%= objJobDetails.getJob_Rvsn_No() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.job.materialtype"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objJobDetails.getJob_Matl_Type() %></td>
			<%
				if (objStdHours.isStdhrs())
				{
					if (objStdHours.isJoblevelstdhrs())
					{
						if (objStdHours.isIncentive())
						{
							if (objStdHours.isJoblevelincentive())
							{
								if (objJobDetails.getJob_Incntv_Flag().equalsIgnoreCase("1"))
								{
			%>            
								<td class="FieldTitle"><bean:message key="prodacs.job.incentive"/></td>
								<td class="FieldTitle">:</td>
								<td class="FieldTitle">
								<img src='<bean:message key="context"/>/images/active.gif'>
			<%
								}
								else
								{
			%>            
								<img src='<bean:message key="context"/>/images/inactive.gif'>
			<%
								}
							}
						}
					}
				}
				if (objStdHours.isStdhrs())
				{
					if (objStdHours.isJoblevelstdhrs())
					{
			%>
						<td class="FieldTitle" nowrap><bean:message key="prodacs.job.standardhrs"/></td>
						<td class="FieldTitle">:</td>
						<td class="ViewData"><%= objJobDetails.getJob_stdHrs() %></td>
			<%
					}
				}
			%>

            </td>
          </tr>
        </table>
        <br>
		<%
			if (objStdHours.isOpnlevelstdhrs())
			{
		%>
				<table width="100%" cellspacing="0" cellpadding="3">
				  <tr> 
					  <td width="160" class="Header"><bean:message key="prodacs.job.operationgroupname"/></td>
					  <td width="110" class="Header"><bean:message key="prodacs.job.operationsno"/></td>
					  <td width="225" class="Header"><bean:message key="prodacs.job.operationname"/></td>
					  <td width="100" class="Header"><bean:message key="prodacs.job.standardhrs"/></td>
					  <td class="Header"><bean:message key="prodacs.job.incentive"/></td>
				  </tr>
				  <%
				  Vector vec_JobDet = objJobDetails.getVec_OpnDetails();
				  for (int i = 0; i < vec_JobDet.size(); i++)
				  {
					objOperDet = (OperationDetails) vec_JobDet.get(i);
				  %>          
				  <tr> 
					<td class="TableItems"><%= objOperDet.getOpnGpCode() %></td>
					<td class="TableItems"><%= objOperDet.getOpnSerialNo() %></td>
					<td class="TableItems"><%= objOperDet.getOpnName() %></td>
					<td class="TableItems"><%= objOperDet.getOpnStdHrs() %></td>
					<%
					  if (objOperDet.isOpnIncentive() == true)
					  {
					%>
					<td align="center" class="TableItems"><img src='<bean:message key="context"/>/images/active.gif'></td>
					<%
					  }
					  else
					  {
					%>
					<td align="center" class="TableItems"><img src='<bean:message key="context"/>/images/inactive.gif'></td>
					<%
					  }
					%>
				  </tr>
				  <%
					 }
				  %>          
		        </table>
		<%
			}
			else
			{
		%>
				<table width="100%" cellspacing="0" cellpadding="3">
				  <tr> 
					  <td width="250" class="Header"><bean:message key="prodacs.job.operationgroupname"/></td>
					  <td width="110" class="Header"><bean:message key="prodacs.job.operationsno"/></td>
					  <td class="Header"><bean:message key="prodacs.job.operationname"/></td>
				  </tr>
				  <%
				  Vector vec_JobDet = objJobDetails.getVec_OpnDetails();
				  for (int i = 0; i < vec_JobDet.size(); i++)
				  {
					objOperDet = (OperationDetails) vec_JobDet.get(i);
				  %>          
				  <tr> 
					<td class="TableItems"><%= objOperDet.getOpnGpCode() %></td>
					<td class="TableItems"><%= objOperDet.getOpnSerialNo() %></td>
					<td class="TableItems"><%= objOperDet.getOpnName() %></td>
				  </tr>
				  <%
					 }
				  %>          
		        </table>
		<%
			}
		%>
        <br>
        <table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
          <tr>
            <td><html:button property="retJobList" styleClass="Button" onclick="javascript:listItem()" value = "Return to Job List" /></td>
          </tr>
        </table></td>
    </tr>
  </table>
</html:form>
</body>
</html:html>
