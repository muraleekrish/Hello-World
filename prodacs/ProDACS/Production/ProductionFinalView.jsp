<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri = "/WEB-INF/struts-logic.tld" prefix = "logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<bean:parameter id="id" name="id" value="0"/>
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.Vector" %>

<%@ page import="com.savantit.prodacs.facade.SessionFinalProductionDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionFinalProductionDetailsManager"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<%@ page import="com.savantit.prodacs.businessimplementation.production.FinalProductionDetails"%>
<%@ page import="com.savantit.prodacs.businessimplementation.production.ProductionJobDetails"%>
<%@ page import="com.savantit.prodacs.businessimplementation.production.ProductionJobQtyDetails"%>

<useradmin:userrights resource="49"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("Production Final View Starts.");
	FinalProductionDetails objFinalProductionDetails = new FinalProductionDetails();
	ProductionJobDetails objProductionJobDetails = new ProductionJobDetails();
	ProductionJobQtyDetails objProdJobQtyDet = new ProductionJobQtyDetails();
	Vector vec_empDet = new Vector();
	try
	{
		EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator

		/* 	Setting the JNDI name and Environment 	*/
	   	obj.setJndiName("SessionFinalProductionDetailsManager");
   		obj.setEnvironment();

		/* 	Creating the Home and Remote Objects 	*/
		SessionFinalProductionDetailsManagerHome objFinalProdHome = (SessionFinalProductionDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionFinalProductionDetailsManagerHome.class);
		SessionFinalProductionDetailsManager objProdFinal = (SessionFinalProductionDetailsManager)PortableRemoteObject.narrow(objFinalProdHome.create(),SessionFinalProductionDetailsManager.class);

		objFinalProductionDetails = objProdFinal.getFinalProductionDetails(Integer.parseInt(id)); /* Production Final Details */
		objProductionJobDetails = objFinalProductionDetails.getObjProductionJobDetails(); /* Production Final Job Details */
		vec_empDet = (Vector) objFinalProductionDetails.getProdnEmpHrsDetails(); /* Employee Duty & OT Details */
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
	var isValid = <%= objFinalProductionDetails.getFinalProdIsValid() %>;

	function scrollhori()
	{
        header.scrollRight = table.scrollRight;
        header.scrollLeft = table.scrollLeft;
	}

	function listItem()
	{
		temp = document.forms[0];
		if (temp.btnProdList.value == "Return to PostProduction")
		{
			temp.action = '<bean:message key="context"/>/frmPostProdQC.do?formAction=searchProd&frmDate='+temp.frmDate.value+'&toDate='+temp.toDate.value;
			temp.submit();
		}
		else
		{
			if ((temp.fromDate.value != "" ) || (temp.prodToDate.value != "") || (temp.woNo.value != "") || (temp.jbName.value != "") || (temp.jbCombo.value != "") || (temp.viewAll.value != "") || (temp.postedView.value != ""))
			{
				temp.action = '<bean:message key="context"/>/frmProdFinalList.do?formAction=afterView&fromDate='+temp.fromDate.value+'&prodToDate='+temp.prodToDate.value+'&jbName='+temp.jbName.value+'&jbCombo='+temp.jbCombo.value+'&viewAll='+temp.viewAll.value+'&postedView='+temp.postedView.value;
				temp.submit();
			}
			else
			{
				temp.action = '<bean:message key="context"/>/Production/ProductionFinal.jsp';
				temp.submit();
			}
		}
	}

	function addItem()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/Production/ProductionFinalAdd.jsp';
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
			document.forms[0].action = '<bean:message key="context"/>/frmProdFinalEdit.do?formAction=modify&id=<%= objFinalProductionDetails.getFinalProdId() %>';
			document.forms[0].submit();
		}
	}

	function makeInvalidItem()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/frmProdFinalEdit.do?formAction=makeInvalid';
		temp.submit();
	}

	function loadToHidden()
	{
		temp = document.forms[0];
		temp.ids.value = temp.id.value;

		temp.fromDate.value = '<%= session.getAttribute("proFromDate") %>';
		temp.prodToDate.value = '<%= session.getAttribute("proToDate") %>';
		temp.woNo.value = '<%= session.getAttribute("proWorkOrder") %>';
		temp.jbName.value = '<%= session.getAttribute("proJobName") %>';
		temp.jbCombo.value = '<%= session.getAttribute("jobSelect") %>';
		temp.viewAll.value = '<%= session.getAttribute("listValidEntries") %>';
		temp.postedView.value = '<%= session.getAttribute("postedView") %>';
	}

	function actionChecking()
	{
		temp = document.forms[0];
		temp.frmDate.value = '<%= request.getParameter("fDate") %>';
		temp.toDate.value = '<%= request.getParameter("tDate") %>';
		if (temp.formAction.value == "fprodnview")
		{
			tblLink.style.display = 'none';
			temp.btnProdList.value = "Return to PostProduction";
		}
	}
</script>
</head>

<body onLoad="loadToHidden();actionChecking();">
<html:form action="frmProdFinalEdit">
<html:hidden property="id"/>
<html:hidden property="ids"/>
<html:hidden property="formAction"/>
<input type="hidden" name="frmDate"/>
<input type="hidden" name="toDate"/>
<input type="hidden" name="fromDate"/>
<input type="hidden" name="prodToDate"/>
<input type="hidden" name="jbName"/>
<input type="hidden" name="jbCombo"/>
<input type="hidden" name="viewAll"/>
<input type="hidden" name="woNo"/>
<input type="hidden" name="postedView"/>

<table width="100%" cellspacing="0" cellpadding="10">
<tr>
<td>
	<table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
	<tr>
		<td><bean:message key="prodacs.productionfinal.header"/></td>
	</tr>
	</table>
	<br>
	<table width="100" cellspacing="0" cellpadding="0" id="tblLink" align="right">
  	<tr>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New Production Final Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1017" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Modify Production Final Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1017" text="[ Modify ]" classId="TopLnk" onClick="javaScript:editItem();"/>
 		<menuconfig:userRights url="#" id="mkInValid" onMouseOver="window.status='Make Production Final Info InValid'; return true"  onMouseOut="window.status=''; return true" resourceId="1017" text="[ MakeInValid ]" classId="TopLnk" onClick="javaScript:makeInvalidItem();"/>
	</tr>
	</table>
	<br>
	<table width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td width="100" class="FieldTitle"><bean:message key="prodacs.job.date"/></td>
		<td width="1" class="FieldTitle">:</td>
		<td class="ViewData"><%= objFinalProductionDetails.getFinalCrntDate().toString().substring(0,10) %></td>

		<td width="100" class="FieldTitle"><bean:message key="prodacs.production.machineshift"/></td>
		<td width="1" class="FieldTitle">:</td>
		<td class="ViewData"><%= objFinalProductionDetails.getShiftName() %></td>

		<td width="100" class="FieldTitle"></td>
		<td class="FieldTitle"></td>
		<td class="ViewData"></td>
	</tr>
	</table>
	<fieldset id="FieldSet"><legend class="FieldTitle"><bean:message key="prodacs.production.productiondetails"/></legend>
	  	<table width="100%" cellpadding="0" cellspacing="0">
		<tr>
			  <td width="85" class="FieldTitle"><bean:message key="prodacs.workorder.workorder"/></td>
			  <td width="1" class="FieldTitle">:</td>
			  <td width="150" class="ViewData"><%= objFinalProductionDetails.getWoNo() %></td>

			  <td width="100" class="FieldTitle"><bean:message key="prodacs.production.totalqtysno"/></td>
			  <td width="1" class="FieldTitle">:</td>
			  <td class="ViewData"><%= objFinalProductionDetails.getFinalProdQtySnos() %></td>

			  <td class="FieldTitle"><bean:message key="prodacs.job.jobname"/></td>
			  <td class="FieldTitle">:</td>
			  <td colspan="4" class="ViewData"><%= objProductionJobDetails.getJobName() %></td>
		</tr>
		<tr>
			  <td class="FieldTitle"><bean:message key="prodacs.production.startoperation"/></td>
			  <td class="FieldTitle">:</td>
			  <td class="ViewData"><%= objFinalProductionDetails.getFinalProdStartOpn() %></td>

			  <td class="FieldTitle"><bean:message key="prodacs.production.endoperation"/></td>
			  <td class="FieldTitle">:</td>
			  <td class="ViewData"><%= objFinalProductionDetails.getFinalProdEndOpn() %></td>

			  <td class="FieldTitle"><bean:message key="prodacs.production.totalhours"/></td>
			  <td class="FieldTitle">:</td>
			  <td class="ViewData"><%= objFinalProductionDetails.getFinalProdTotHrs() %></td>
		</tr>
		<tr>
		<%
			if (BuildConfig.DMODE)
				System.out.println(objFinalProductionDetails.getModifyCount());
			if (objFinalProductionDetails.getModifyCount() == 0)
			{
		%>
			<td class="FieldTitle"><bean:message key="prodacs.production.createdby"/></td>
			<td class="FieldTitle">:</td>
			<td class="ViewData"><%= objFinalProductionDetails.getCreatedBy() %></td>
		<%
			}
			else
			{
		%>
			<td class="FieldTitle"><bean:message key="prodacs.production.modifiedby"/></td>
			<td class="FieldTitle">:</td>
			<td class="ViewData"><%= objFinalProductionDetails.getModifiedBy() %></td>
		<%
			}
		%>
		</tr>
		</table>
		<br>
	  	<div id="header" style="width:716; overflow:scroll; scrollbar-3dlight-color:#FFFFFF; scrollbar-arrow-color:#FFFFFF; scrollbar-base-color:#FFFFFF; scrollbar-darkshadow-color:#FFFFFF; scrollbar-face-color:#FFFFFF; scrollbar-highlight-color:#FFFFFF; scrollbar-shadow-color:#FFFFFF; scrollbar-track-color:#FFFFFF;">
		  	<table width="700" cellpadding="3" cellspacing="0">
			<tr>
				<td width="200" class="Header"><bean:message key="prodacs.employee.employeetype"/></td>
				<td class="Header"><bean:message key="prodacs.employee.employeename"/></td>
			</tr>
			</table>
		</div>
		<div id="table" style="Position:absolute; top:215; height:100; width:716; overflow:auto;" onscroll="scrollhori()">
		 	<table width="700" cellpadding="3" cellspacing="0">
		 	<logic:iterate id="bt1" name="vec_empDet" indexId="count">
		    	<tr>
			<%
				if(count.intValue()%2 == 0)
				{
			%>
			            <td width="200" class="TableItems"><bean:write name="bt1" property="empType"/>&nbsp;</td>
			            <td class="TableItems"><bean:write name="bt1" property="empName"/>&nbsp;</td>
			<%
				}
				else
				{
			%>
			            <td width="150" class="TableItems2"><bean:write name="bt1" property="empType"/>&nbsp;</td>
			            <td class="TableItems2"><bean:write name="bt1" property="empName"/>&nbsp;</td>
			<%
				}
			%>
			</tr>
   	 		</logic:iterate>
			</table>
		</div><br><br><br><br><br><br>
	</fieldset>
	<br>
	<table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
	<tr>
		<td>
		<html:button property="btnProdList" styleClass="Button" value="Production Final List" onclick="javaScript:listItem();"/>
		</td>
	</tr>
	</table>
</td>
</tr>
</table>
</html:form>
</body>
</html:html>
