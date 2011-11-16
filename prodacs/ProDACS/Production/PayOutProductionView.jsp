<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<bean:parameter id="id" name="id" value="0"/>
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.Vector" %>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<%@ page import="com.savantit.prodacs.facade.SessionPopDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionPopDetailsManager"%>
<%@ page import="com.savantit.prodacs.businessimplementation.production.PopDetails"%>
<useradmin:userrights resource="20"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("POP View Starts.");
	PopDetails objPopDetails = new PopDetails();
	Vector vec_empDet = new Vector();
	try
	{
		EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
		
		/* 	Setting the JNDI name and Environment 	*/
	   	obj.setJndiName("SessionPopDetailsManagerBean");
   		obj.setEnvironment();

		/* 	Creating the Home and Remote Objects 	*/
		SessionPopDetailsManagerHome popHomeObj = (SessionPopDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionPopDetailsManagerHome.class);
		SessionPopDetailsManager popObj = (SessionPopDetailsManager)PortableRemoteObject.narrow(popHomeObj.create(),SessionPopDetailsManager.class);
		
		objPopDetails = popObj.getPopDetails(Integer.parseInt(id)); /* POP Details */

		vec_empDet = (Vector) objPopDetails.getPopEmpHrsDetails(); /* Employee Duty & OT Details */
		pageContext.setAttribute("vec_empDet",vec_empDet);
	}catch (Exception e)
	{
		if (BuildConfig.DMODE)
		{
			e.printStackTrace();
			System.out.println("Problem in Production View.jsp.");
		}
	}
%>

<html:html>
<head>
<title><bean:message key="prodacs.common.header"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script src='<bean:message key="context"/>/library/jscripts.js'></script>
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
		temp = document.forms[0];
		if (temp.returnToPost.value == "Return to PostProduction")
		{
			temp.action = '<bean:message key="context"/>/frmPostProd.do?formAction=searchPop&id'+temp.viewId.value+'&frmDate='+temp.frmDate.value+'&toDate='+temp.toDate.value;
			temp.submit();
		}
		else
		{
			if ((temp.fromDate.value != "" ) || (temp.proToDate.value != "") || (temp.sftName.value != "") || (temp.postedView.value != ""))
			{
				temp.action = '<bean:message key="context"/>/frmPayOutProdList.do?formAction=afterView&fromDate='+temp.fromDate.value+'&proToDate='+temp.proToDate.value+'&sftName='+temp.sftName.value+'&postedView='+temp.postedView.value;
				temp.submit();
			}
			else
			{
				temp.action = '<bean:message key="context"/>/Production/PayOutProductionList.jsp';
				temp.submit();
			}
		}
	}

	function addItem()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/Production/PayOutProductionAdd.jsp';
		temp.submit();		
	}
	
	function editItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/frmPayOutProdEdit.do?formAction=modify&id='+<%= objPopDetails.getPopId() %>;
		document.forms[0].submit();
	}
	
	function loadToHidden()
	{
		temp = document.forms[0];
		temp.ids.value = temp.id.value;

		temp.fromDate.value = '<%= request.getParameter("proFromDate") %>';
		temp.proToDate.value = '<%= request.getParameter("proToDate") %>';
		temp.sftName.value = '<%= request.getParameter("popShiftName") %>';
		temp.postedView.value = '<%= request.getParameter("postedView") %>';
		temp.empName.value = '<%= request.getParameter("popEmplName") %>';
		temp.empNameCombo.value = '<%= request.getParameter("emplSelect") %>';
	}
	function actionChecking()
	{
		temp = document.forms[0];
		temp.frmDate.value = '<%= request.getParameter("fDate") %>';
		temp.toDate.value = '<%= request.getParameter("tDate") %>';
		temp.viewId.value = '<%= request.getParameter("id") %>';
		if (temp.formAction.value == "popview")
		{
			tblLink.style.display = 'none';
			showWhenProdView.style.display= 'none';
			hideNshow.style.display = 'block';
			temp.returnToPost.value = "Return to PostProduction";
		}
		else if (temp.formAction.value == 'view')
		{
			tblLink.style.display = 'block';
			hideNshow.style.display = 'none';
			showWhenProdView.style.display= 'block';
		}
	}

	function postProduction()
	{
		temp = document.forms[0];
		temp.formAction.value = "posting";
		temp.submit();
	}
	
</script>
</head>
<body onLoad="loadToHidden();actionChecking();">
<html:form action="frmPayOutProdEdit">
<html:hidden property="id"/>
<html:hidden property="ids"/>
<html:hidden property="formAction"/>
<input type="hidden" name="frmDate"/>
<input type="hidden" name="toDate"/>
<input type="hidden" name="viewId"/>
<input type="hidden" name="fromDate"/>
<input type="hidden" name="proToDate"/>
<input type="hidden" name="sftName"/>
<input type="hidden" name="postedView"/>
<input type="hidden" name="empName"/>
<input type="hidden" name="empNameCombo"/>

	<table width="100%" cellspacing="0" cellpadding="10">
		<tr>
			<td>
	<table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
		<tr>
			<td><bean:message key="prodacs.production.payoutsideproductionview"/></td>
		</tr>
	</table>
	<br> 
	<table width="100" cellspacing="0" cellpadding="0" id="tblLink" align="right">
	<tr> 
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New PayOutProduction Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1020" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='List PayOutProduction Info'; return true"  onMouseOut="window.status=''; return true" resourceId="20" text="[ List ]" classId="TopLnk" onClick="javaScript:listItem();"/>
	</tr>
	</table>
	<br>
	<table width="100%" cellspacing="0" cellpadding="0">
		<tr> 
			<td class="FieldTitle"><bean:message key="prodacs.production.date"/></td>
			<td class="FieldTitle">:</td>
			<td class="ViewData"><%= objPopDetails.getPopCrntDate().toString().substring(0,10) %></td>
			<td width="80" class="FieldTitle"><bean:message key="prodacs.production.reason"/></td>
			<td width="1" class="FieldTitle">:</td>
			<td width="200" class="ViewData" nowrap><%= objPopDetails.getPopRsn() %></td>
		</tr>
		<tr>
			<td width="80" class="FieldTitle"><bean:message key="prodacs.production.shift"/></td>
			<td width="1" class="FieldTitle">:</td>
			<td width="100" class="ViewData"><%= objPopDetails.getShiftName() %></td>
			<%
			if (objPopDetails.getModifyCount() == 0)
			{
			%>
			<td class="FieldTitle"><bean:message key="prodacs.production.createdby"/></td>
			<td class="FieldTitle">:</td>
			<td class="ViewData"><%= objPopDetails.getCreatedBy() %></td>
			<%
			}
			else
			{
			%>
			<td class="FieldTitle"><bean:message key="prodacs.production.modifiedby"/></td>
			<td class="FieldTitle">:</td>
			<td class="ViewData"><%= objPopDetails.getModifiedBy() %></td>
			<%
			}
			%>	
		</tr>
	</table>
	<table width="100%" cellpadding="0" cellspacing="0" id="tblPayOutProdList">
		<tr>
			<td class="FieldTitle">
			<fieldset id="FieldSet"><legend><bean:message key="prodacs.production.payoutsideproduction"/></legend>
	<br>
	<div id="dvPayOutProdList">
	<table width="100%" cellpadding="3" cellspacing="0">
		<tr> 
			<td width="125" class="Header"><bean:message key="prodacs.production.emptype"/></td>
			<td class="Header"><bean:message key="prodacs.production.empname"/></td>
			<td width="100" class="Header"><bean:message key="prodacs.employee.employeecode"/></td>
			<td width="70" class="Header"><bean:message key="prodacs.production.dutyhrs"/></td>
			<td width="70" class="Header"><bean:message key="prodacs.production.othrs"/></td>
		</tr>
	</table>
	<div id="dvPayOutProd" style="overflow:auto; height:105">
	<table width="100%" cellspacing="0" cellpadding="3">
 	<logic:iterate id="bt1" name="vec_empDet" indexId="count">
    	<tr>
	<%
		if(count.intValue()%2 == 0) 
		{
	%>		
	            <td width="125" class="TableItems"><bean:write name="bt1" property="empType"/>&nbsp;</td>
	            <td class="TableItems"><bean:write name="bt1" property="empName"/>&nbsp;</td>
				<td width="100" class="TableItems"><bean:write name="bt1" property="empCode"/>&nbsp;</td>
	            <td width="70" class="TableItems"><bean:write name="bt1" property="dutyHrs"/>&nbsp;</td>
	            <td width="70" class="TableItems"><bean:write name="bt1" property="otHrs"/>&nbsp;</td>
	<%
		}
		else
		{
	%>
	            <td width="125" class="TableItems"><bean:write name="bt1" property="empType"/>&nbsp;</td>
	            <td class="TableItems"><bean:write name="bt1" property="empName"/>&nbsp;</td>
				<td width="100" class="TableItems"><bean:write name="bt1" property="empCode"/>&nbsp;</td>
	            <td width="70" class="TableItems"><bean:write name="bt1" property="dutyHrs"/>&nbsp;</td>
	            <td width="70" class="TableItems"><bean:write name="bt1" property="otHrs"/>&nbsp;</td>
	<%
		}
	%>
	</tr>
	</logic:iterate>
	</table>
	</div>
	</div>
			</fieldset>
			</td>
		</tr>
	</table>
	<br>
	<table width="100%" cellpadding="0" cellspacing="0" id="showWhenProdView">
	<tr>
		<td>
			<table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
			<tr>
				<td>
				<html:button property="returntopay" styleClass="Button" value="InDirect Production List" onclick="javaScript:listItem();"/>&nbsp;
				</td>
			</tr>
			</table>
		</td>
	</tr>
	</table>
	<table width="100%" cellpadding="0" cellspacing="0" id="hideNshow" style="display:none">
	<tr>
		<td>
			<table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
				<tr>
					<td> 
					<html:button property="returnToPost" styleClass="Button" value="InDirect Production List" onclick="javaScript:listItem();"/>&nbsp;
					<html:button property="post" value="Post" styleClass="Button" onclick="postProduction();"/></td>
				</tr>
			</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	</table>
</html:form>
</body>
</html:html>
