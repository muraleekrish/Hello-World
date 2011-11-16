<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri = "/WEB-INF/struts-logic.tld" prefix = "logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<bean:parameter id="id" name="id" value="0"/>
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.Vector" %>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<%@ page import="com.savantit.prodacs.facade.SessionNonProductionDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionNonProductionDetailsManager"%>
<%@ page import="com.savantit.prodacs.businessimplementation.production.NonProductionDetails"%>
<%@ page import="com.savantit.prodacs.businessimplementation.production.ProductionJobDetails"%>
<useradmin:userrights resource="18"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("Production View Starts.");
	NonProductionDetails objNonProdDetails = new NonProductionDetails();
	ProductionJobDetails objProdJobDet = new ProductionJobDetails();
	Vector vec_empDet = new Vector();
	try
	{
		EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
		
		/* 	Setting the JNDI name and Environment 	*/
	   	obj.setJndiName("SessionNonProductionDetailsManagerBean");
   		obj.setEnvironment();

		/* 	Creating the Home and Remote Objects 	*/
		SessionNonProductionDetailsManagerHome proHomeObj = (SessionNonProductionDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionNonProductionDetailsManagerHome.class);
		SessionNonProductionDetailsManager nonProductionObj = (SessionNonProductionDetailsManager)PortableRemoteObject.narrow(proHomeObj.create(),SessionNonProductionDetailsManager.class);
		
		objNonProdDetails = nonProductionObj.getNonProductionDetails(Integer.parseInt(id)); /* Non-Production Details */

		vec_empDet = (Vector) objNonProdDetails.getNonprodnEmpHrsDetails(); /* Employee Duty & OT Details */
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
<title><bean:message key="prodacs.production.header"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script>
	var isValid = <%= objNonProdDetails.getNprodIsValid() %>;
	function scrollhori()
	{
        header.scrollRight = table.scrollRight;
        header.scrollLeft = table.scrollLeft;
	}
	
	function listItem()
	{
		temp = document.forms[0];
		if (temp.prodListBtn.value == "Return to PostProduction")
		{
			temp.action = '<bean:message key="context"/>/frmPostProd.do?formAction=searchNonProd&id='+temp.viewId.value+'&frmDate='+temp.frmDate.value+'&toDate='+temp.toDate.value;
			temp.submit();
		}
		else
		{
			if ((temp.fromDate.value != "" ) || (temp.proToDate.value != "") || (temp.viewAll.value != "") || (temp.mcCde.value != "") || (temp.sftName.value != "") || (temp.postedView.value != "") || (temp.empName.value != "") || (temp.empNameCombo.value != ""))
			{
				temp.action = '<bean:message key="context"/>/frmNonProdList.do?formAction=afterView';
				temp.submit();
			}
			else
			{
				temp.action = '<bean:message key="context"/>/Production/NonProductionList.jsp';
				temp.submit();
			}
		}
	}
	
	function addItem()
	{
		temp = document.forms[0];	
		temp.action = '<bean:message key="context"/>/Production/NonProductionAdd.jsp';
		temp.submit();
	}
	
	function editItem()
	{
		if (isValid == 0)
		{
			alert("Invalid Item Cannot be Modified! ");
		}
		else
		{
			document.forms[0].action = '<bean:message key="context"/>/frmNonProdEdit.do?formAction=modify&id='+<%= objNonProdDetails.getNonProdId() %>;
			document.forms[0].submit();
		}	
	}
	
	function loadToHidden()
	{
		temp = document.forms[0];
		temp.ids.value = temp.id.value;

		temp.fromDate.value = '<%= request.getParameter("nonProFromDate") %>';
		temp.proToDate.value = '<%= request.getParameter("nonProToDate") %>';
		temp.viewAll.value = '<%= request.getParameter("nonProdValid") %>';
		temp.mcCde.value = '<%= request.getParameter("nonProMachineCode") %>';
		temp.sftName.value = '<%= request.getParameter("nonProShiftName") %>';
		temp.postedView.value = '<%= request.getParameter("postedView") %>';
		temp.empName.value = '<%= request.getParameter("nonProdEmplName") %>';
		temp.empNameCombo.value = '<%= request.getParameter("emplSelect") %>';
	}
	
	function actionChecking()
	{
		temp = document.forms[0];
		temp.frmDate.value = '<%= request.getParameter("fDate") %>';
		temp.toDate.value = '<%= request.getParameter("tDate") %>';
		temp.viewId.value = '<%= request.getParameter("id") %>';
		if (temp.formAction.value == "nonprodnview")
		{
			tblLink.style.display = 'none';
			showWhenProdView.style.display= 'none';
			hideNshow.style.display = 'block';
			temp.prodListBtn.value = "Return to PostProduction";
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
<html:form action="frmNonProdEdit">
<html:hidden property="id"/>
<html:hidden property="ids"/>
<html:hidden property="formAction"/>
<input type="hidden" name="frmDate"/>
<input type="hidden" name="toDate"/>
<input type="hidden" name="viewId"/>
<input type="hidden" name="fromDate"/>
<input type="hidden" name="proToDate"/>
<input type="hidden" name="viewAll"/>
<input type="hidden" name="mcCde"/>
<input type="hidden" name="sftName"/>
<input type="hidden" name="postedView"/>
<input type="hidden" name="empName"/>
<input type="hidden" name="empNameCombo"/>

<table width="100%" cellspacing="0" cellpadding="10">
<tr>
<td>
	<table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
	<tr>
		<td><bean:message key="prodacs.production.nonproductionview"/></td>
	</tr>
	</table>
	<br>
	<table width="100" cellspacing="0" cellpadding="0" id="tblLink" align="right">
  	<tr> 
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New NonProduction Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1018" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Modify NonProduction Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1018" text="[ Modify ]" classId="TopLnk" onClick="javaScript:editItem();"/>
	</tr>
	</table>
	<br>
	<table width="100%" cellspacing="0" cellpadding="0">
	<tr> 
		<td width="100" class="FieldTitle"><bean:message key="prodacs.job.date"/></td>
		<td width="1" class="FieldTitle">:</td>
		<td class="ViewData"><%= objNonProdDetails.getNonProdnCrntDate().toString().substring(0,10) %></td>

		<td width="100" class="FieldTitle"><bean:message key="prodacs.machine.machinecode"/></td>
		<td width="1" class="FieldTitle">:</td>
		<td class="ViewData"><%= objNonProdDetails.getMcCode() %></td>

		<td width="100" class="FieldTitle"><bean:message key="prodacs.production.machineshift"/></td>
		<td width="1" class="FieldTitle">:</td>
		<td class="ViewData"><%= objNonProdDetails.getShiftName() %></td>

		<td width="100" class="FieldTitle"></td>
		<td class="FieldTitle"></td>
		<td class="ViewData"></td>
	</tr>
	</table>
	<fieldset id="FieldSet"><legend class="FieldTitle"><bean:message key="prodacs.nonproduction.nonproductiondetails"/></legend>
	  	<table width="100%" cellpadding="0" cellspacing="0">
		<tr>
			  <td width="85" class="FieldTitle"><bean:message key="prodacs.nonproduction.idlebreakdown"/></td>
			  <td width="1" class="FieldTitle">:</td>
			  <td width="150" class="ViewData"><%= objNonProdDetails.getIdlOrBkDown() %></td>

			  <td width="100" class="FieldTitle"><bean:message key="prodacs.workorder.reason"/></td>
			  <td width="1" class="FieldTitle">:</td>
			  <td class="ViewData"><%= objNonProdDetails.getIdlOrBrkDwnRsn() %></td>

			  <td class="FieldTitle"><bean:message key="prodacs.production.tothrs"/></td>
			  <td class="FieldTitle">:</td>
			  <td class="ViewData"><%= objNonProdDetails.getNprodTotHrs() %></td>
		</tr>
		<tr>
			<%
			if (objNonProdDetails.getModifyCount() == 0)
			{
			%>
			  <td width="100" class="FieldTitle"><bean:message key="prodacs.production.createdby"/></td>
			  <td width="1" class="FieldTitle">:</td>
			  <td class="ViewData"><%= objNonProdDetails.getCreatedBy() %></td>
			<%
			}
			else
			{
			%>
			  <td class="FieldTitle"><bean:message key="prodacs.production.modifiedby"/></td>
			  <td class="FieldTitle">:</td>
			  <td class="ViewData"><%= objNonProdDetails.getModifiedBy() %></td>
			<%
			}
			%>
		</tr>
		</table>
		<br>
	  	<div id="header" style="width:716; overflow:scroll; scrollbar-3dlight-color:#FFFFFF; scrollbar-arrow-color:#FFFFFF; scrollbar-base-color:#FFFFFF; scrollbar-darkshadow-color:#FFFFFF; scrollbar-face-color:#FFFFFF; scrollbar-highlight-color:#FFFFFF; scrollbar-shadow-color:#FFFFFF; scrollbar-track-color:#FFFFFF;">
		  	<table width="980" cellpadding="3" cellspacing="0">
			<tr> 
				<td width="150" class="Header"><bean:message key="prodacs.employee.employeetype"/></td>
				<td class="Header"><bean:message key="prodacs.employee.employeename"/></td>
				<td width="120" class="Header"><bean:message key="prodacs.employee.employeecode"/></td>
				<td width="75" class="Header"><bean:message key="prodacs.production.dutyhrs"/></td>
				<td width="120" class="Header"><bean:message key="prodacs.production.overtimehrs"/></td>
			</tr>
			</table>
		</div>
		<div id="table" style="Position:absolute; top:196; height:100; width:700; overflow:auto;" onscroll="scrollhori()">
		 	<table width="980" cellpadding="3" cellspacing="0">
		 	<logic:iterate id="bt1" name="vec_empDet" indexId="count">
		    	<tr>
			<%
				if(count.intValue()%2 == 0) 
				{
			%>		
			            <td width="150" class="TableItems"><bean:write name="bt1" property="empType"/>&nbsp;</td>
			            <td class="TableItems"><bean:write name="bt1" property="empName"/>&nbsp;</td>
						<td width="120" class="TableItems"><bean:write name="bt1" property="empCode"/>&nbsp;</td>
			            <td width="75" class="TableItems"><bean:write name="bt1" property="dutyHrs"/>&nbsp;</td>
			            <td width="120" class="TableItems"><bean:write name="bt1" property="otHrs"/>&nbsp;</td>
			<%
				}
				else
				{
			%>
			            <td width="150" class="TableItems2"><bean:write name="bt1" property="empType"/>&nbsp;</td>
			            <td class="TableItems2"><bean:write name="bt1" property="empName"/>&nbsp;</td>
						<td width="120" class="TableItems2"><bean:write name="bt1" property="empCode"/>&nbsp;</td>
			            <td width="75" class="TableItems2"><bean:write name="bt1" property="dutyHrs"/>&nbsp;</td>
			            <td width="120" class="TableItems2"><bean:write name="bt1" property="otHrs"/>&nbsp;</td>
			<%
				}
			%>
			</tr>
   	 		</logic:iterate>
			</table>
		</div><br><br><br><br><br><br>
	</fieldset>
	<br>
	<table width="100%" cellpadding="0" cellspacing="0" id="showWhenProdView">
	<tr>
		<td>
			<table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
			<tr>
				<td>
				<html:button property="btnProdList" styleClass="Button" value="Non-Production List" onclick="javaScript:listItem();"/>&nbsp;
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
					<table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
					<tr>
						<td>
						<html:button property="prodListBtn" styleClass="Button" value="Non-Production List" onclick="javaScript:listItem();"/>&nbsp;
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
