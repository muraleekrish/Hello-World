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
<%@ page import="com.savantit.prodacs.facade.SessionProductionDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionProductionDetailsManager"%>
<%@ page import="com.savantit.prodacs.businessimplementation.production.ProductionDetails"%>
<%@ page import="com.savantit.prodacs.businessimplementation.production.ProductionJobDetails"%>
<useradmin:userrights resource="17"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("Production View Starts.");
	ProductionDetails objProdDetails = new ProductionDetails();
	ProductionJobDetails objProdJobDet = new ProductionJobDetails();
	Vector vec_empDet = new Vector();
	try
	{
		EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator

		/* 	Setting the JNDI name and Environment 	*/
	   	obj.setJndiName("SessionProductionDetailsManagerBean");
   		obj.setEnvironment();

		/* 	Creating the Home and Remote Objects 	*/
		SessionProductionDetailsManagerHome proHomeObj = (SessionProductionDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionProductionDetailsManagerHome.class);
		SessionProductionDetailsManager productionObj = (SessionProductionDetailsManager)PortableRemoteObject.narrow(proHomeObj.create(),SessionProductionDetailsManager.class);

		objProdDetails = productionObj.getProductionDetails(Integer.parseInt(id)); /* Production Details */
		objProdJobDet = objProdDetails.getObjProductionJobDetails(); /* Production Job Details */
		vec_empDet = (Vector) objProdDetails.getProdnEmpHrsDetails(); /* Employee Duty & OT Details */
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
	var isValid = <%= objProdDetails.getProdIsValid() %>;

	function scrollhori()
	{
        header.scrollRight = table.scrollRight;
        header.scrollLeft = table.scrollLeft;
	}

	function loadToHidden()
	{
		temp = document.forms[0];
		temp.ids.value = temp.id.value;
		temp.fromDate.value = '<%= request.getParameter("proFromDate") %>';
		temp.tDate.value = '<%= request.getParameter("proToDate") %>';
		temp.woNo.value = '<%= request.getParameter("proWorkOrder") %>';
		temp.jbName.value = '<%= request.getParameter("proJobName") %>';
		temp.jbCombo.value = '<%= request.getParameter("jobSelect") %>';
		temp.mcCde.value = '<%= request.getParameter("proMachineCode") %>';
		temp.sftName.value = '<%= request.getParameter("proShiftName") %>';
		temp.viewAll.value = '<%= request.getParameter("listValidEntries") %>';
		temp.postedView.value = '<%= request.getParameter("postedView") %>';
		temp.woSelect.value = '<%= request.getParameter("woSelect") %>';
		temp.maxItem.value = '<%= request.getParameter("maxItems") %>';
		temp.empName.value = '<%= request.getParameter("proEmplName") %>';
		temp.empNameCombo.value = '<%= request.getParameter("emplSelect") %>';
		temp.pageNo.value = '<%= request.getParameter("page") %>';
		temp.sortField.value = '<%= request.getParameter("sortField") %>';
		temp.sortOrder.value = '<%= request.getParameter("sortOrder") %>';
	}

	function listItem()
	{
		temp = document.forms[0];
		if (temp.prodListBtn.value == "Return to PostProduction")
		{
			temp.action = '<bean:message key="context"/>/frmPostProd.do?formAction=searchProd&id='+temp.viewId.value+'&frmDate='+temp.frmDate.value+'&toDate='+temp.toDate.value;
			temp.submit();
		}
		else
		{
			if ((temp.fromDate.value != "" ) || (temp.tDate.value != "") || (temp.woNo.value != "") || (temp.jbName.value != "") || (temp.jbCombo.value != "") || (temp.mcCde.value != "") || (temp.sftName.value != "") || (temp.viewAll.value != "") || (temp.postedView.value != "") || (temp.woSelect.value != "") || (temp.maxItem.value != "") || (temp.empName.value != "") || (temp.empNameCombo.value !="") || (temp.pageNo.value !=""))
			{
				temp.action = '<bean:message key="context"/>/frmProdList.do?formAction=afterView';
				temp.submit();
			}
			else
			{
				if (temp.maxItem.value == null)
				{
					temp.maxItem.value = "15";
					temp.action = '<bean:message key="context"/>/Production/ProductionList.jsp?maxItem='+temp.maxItem.value;
					temp.submit();
				}
			}
		}
	}

	function addItem()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/Production/ProductionAdd.jsp';
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
			document.forms[0].action = '<bean:message key="context"/>/frmProdEdit.do?formAction=modify&id=<%= objProdDetails.getProdId() %>';
			document.forms[0].submit();
		}
	}

	function makeInvalidItem()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/frmProdEdit.do?formAction=makeInvalid';
		temp.submit();
	}

	function actionChecking()
	{
		temp = document.forms[0];
		temp.frmDate.value = '<%= request.getParameter("fDate") %>';
		temp.toDate.value = '<%= request.getParameter("tDate") %>';
		temp.viewId.value = '<%= request.getParameter("id") %>';
		if (temp.formAction.value == "prodnview")
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
<html:form action="frmProdEdit">
<html:hidden property="id"/>
<html:hidden property="ids"/>
<html:hidden property="formAction"/>
<input type="hidden" name="frmDate"/>
<input type="hidden" name="toDate"/>
<input type="hidden" name="viewId"/>
<input type="hidden" name="fromDate"/>
<input type="hidden" name="tDate"/>
<input type="hidden" name="jbName"/>
<input type="hidden" name="jbCombo"/>
<input type="hidden" name="viewAll"/>
<input type="hidden" name="woNo"/>
<input type="hidden" name="woSelect"/>
<input type="hidden" name="mcCde"/>
<input type="hidden" name="sftName"/>
<input type="hidden" name="postedView"/>
<input type="hidden" name="maxItem"/>
<input type="hidden" name="empName"/>
<input type="hidden" name="empNameCombo"/>
<input type="hidden" name="pageNo"/>
<input type="hidden" name="sortField"/>
<input type="hidden" name="sortOrder"/>

<table width="100%" cellspacing="0" cellpadding="10">
<tr>
<td>
	<table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
	<tr>
		<td><bean:message key="prodacs.production.productionview"/></td>
	</tr>
	</table>
	<br>
	<table width="100" cellspacing="0" cellpadding="0" id="tblLink" align="right">
  	<tr>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New Production Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1017" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Modify Production Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1017" text="[ Modify ]" classId="TopLnk" onClick="javaScript:editItem();"/>
 		<!--menuconfig:userRights url="#" id="mkInValid" onMouseOver="window.status='Make Production Info InValid'; return true"  onMouseOut="window.status=''; return true" resourceId="1017" text="[ MakeInValid ]" classId="TopLnk" onClick="javaScript:makeInvalidItem();"/-->
	</tr>
	</table>
	<br>
	<table width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td width="100" class="FieldTitle"><bean:message key="prodacs.job.date"/></td>
		<td width="1" class="FieldTitle">:</td>
		<td class="ViewData"><%= objProdDetails.getProdCrntDate().toString().substring(0,10) %></td>

		<td width="100" class="FieldTitle"><bean:message key="prodacs.machine.machinecode"/></td>
		<td width="1" class="FieldTitle">:</td>
		<td class="ViewData"><%= objProdDetails.getMcCode() %></td>

		<td width="100" class="FieldTitle"><bean:message key="prodacs.production.machineshift"/></td>
		<td width="1" class="FieldTitle">:</td>
		<td class="ViewData"><%= objProdDetails.getShiftName() %></td>

		<td width="100" class="FieldTitle"></td>
		<td class="FieldTitle"></td>
		<td class="ViewData"></td>
	</tr>
	</table>
	<fieldset id="FieldSet"><legend class="FieldTitle"><bean:message key="prodacs.production.productiondetails"/></legend>
	  	<table width="100%" cellpadding="0" cellspacing="0">
		<tr>
			  <td width="85" class="FieldTitle" nowrap><bean:message key="prodacs.workorder.workorder"/></td>
			  <td width="1" class="FieldTitle">:</td>
			  <td width="150" class="ViewData" nowrap><%= objProdDetails.getWoNo() %></td>

			  <td width="120" class="FieldTitle" nowrap><bean:message key="prodacs.production.totalqtysno"/></td>
			  <td width="1" class="FieldTitle">:</td>
			  <td class="ViewData" nowrap><%= objProdDetails.getProdQtySnos() %></td>

			  <td class="FieldTitle" nowrap><bean:message key="prodacs.job.jobname"/></td>
			  <td class="FieldTitle">:</td>
			  <td colspan="4" class="ViewData" nowrap><%= objProdJobDet.getJobName() %></td>
		</tr>
		<tr>
			  <td class="FieldTitle" nowrap><bean:message key="prodacs.production.startoperation"/></td>
			  <td class="FieldTitle">:</td>
			  <td class="ViewData" nowrap><%= objProdDetails.getProdStartOpn() %></td>

			  <td class="FieldTitle" nowrap><bean:message key="prodacs.production.endoperation"/></td>
			  <td class="FieldTitle">:</td>
			  <td class="ViewData" nowrap><%= objProdDetails.getProdEndOpn() %></td>

			  <td class="FieldTitle" nowrap><bean:message key="prodacs.production.worktype"/></td>
			  <td class="FieldTitle">:</td>
			  <td class="ViewData" nowrap><%= (objProdDetails.getProdWorkType().equalsIgnoreCase("R"))?"Rework":"Normal" %></td>
		</tr>
		<tr>
			  <td class="FieldTitle" nowrap><bean:message key="prodacs.production.stdhrs"/></td>
			  <td class="FieldTitle">:</td>
			  <td class="ViewData" nowrap><%= objProdDetails.getProdStdHrs() %></td>

			  <td class="FieldTitle" nowrap><bean:message key="prodacs.production.tothrs"/></td>
			  <td class="FieldTitle">:</td>
			  <td class="ViewData" nowrap><%= objProdDetails.getProdTotHrs() %></td>
		</tr>
		<tr>
		<%
			if (BuildConfig.DMODE)
				System.out.println(objProdDetails.getModifyCount());
			if (objProdDetails.getModifyCount() == 0)
			{
		%>
			<td class="FieldTitle" nowrap><bean:message key="prodacs.production.createdby"/></td>
			<td class="FieldTitle">:</td>
			<td class="ViewData" nowrap><%= objProdDetails.getCreatedBy() %></td>
		<%
			}
			else
			{
		%>
			<td class="FieldTitle" nowrap><bean:message key="prodacs.production.modifiedby"/></td>
			<td class="FieldTitle">:</td>
			<td class="ViewData" nowrap><%= objProdDetails.getModifiedBy() %></td>
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
				<td width="200" class="Header"><bean:message key="prodacs.employee.employeecode"/></td>
				<td width="75" class="Header"><bean:message key="prodacs.production.dutyhrs"/></td>
				<td width="120" class="Header"><bean:message key="prodacs.production.overtimehrs"/></td>
			</tr>
			</table>
		</div>
		<div id="table" style="Position:absolute; top:232; height:100; width:716; overflow:auto;" onscroll="scrollhori()">
		 	<table width="980" cellpadding="3" cellspacing="0">
		 	<logic:iterate id="bt1" name="vec_empDet" indexId="count">
		    	<tr>
			<%
				if(count.intValue()%2 == 0)
				{
			%>
			            <td width="150" class="TableItems"><bean:write name="bt1" property="empType"/>&nbsp;</td>
			            <td class="TableItems"><bean:write name="bt1" property="empName"/>&nbsp;</td>
						<td width="200" class="TableItems"><bean:write name="bt1" property="empCode"/>&nbsp;</td>
			            <td width="75" class="TableItems"><bean:write name="bt1" property="dutyHrs"/>&nbsp;</td>
			            <td width="120" class="TableItems"><bean:write name="bt1" property="otHrs"/></td>
			<%
				}
				else
				{
			%>
			            <td width="150" class="TableItems2"><bean:write name="bt1" property="empType"/>&nbsp;</td>
			            <td class="TableItems2"><bean:write name="bt1" property="empName"/>&nbsp;</td>
						<td width="200" class="TableItems2"><bean:write name="bt1" property="empCode"/>&nbsp;</td>
			            <td width="75" class="TableItems2"><bean:write name="bt1" property="dutyHrs"/>&nbsp;</td>
			            <td width="120" class="TableItems2"><bean:write name="bt1" property="otHrs"/></td>
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
				<html:button property="btnProdList" styleClass="Button" value="Direct Production List" onclick="javaScript:listItem();"/>&nbsp;
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
				<html:button property="prodListBtn" styleClass="Button" value="Direct Production List" onclick="javaScript:listItem();"/>&nbsp;
				<html:button property="post" value="Post" styleClass="Button" onclick="postProduction();"/></td>
				</td>
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
