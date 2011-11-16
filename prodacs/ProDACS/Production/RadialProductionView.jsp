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
<%@ page import="com.savantit.prodacs.facade.SessionRadlProductionDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionRadlProductionDetailsManager"%>
<%@ page import="com.savantit.prodacs.businessimplementation.production.RadialProductionDetails"%>
<%@ page import="com.savantit.prodacs.businessimplementation.production.ProductionJobDetails"%>
<useradmin:userrights resource="19"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("Radial Production View Starts.");
	RadialProductionDetails objRdlProdDetails = new RadialProductionDetails();
	ProductionJobDetails objProdJobDet = new ProductionJobDetails();
	Vector vec_empDet = new Vector();
	try
	{
		EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
		
		/* 	Setting the JNDI name and Environment 	*/
	   	obj.setJndiName("SessionRadlProductionDetailsManagerBean");
   		obj.setEnvironment();

		/* 	Creating the Home and Remote Objects 	*/
		SessionRadlProductionDetailsManagerHome rdlProHomeObj = (SessionRadlProductionDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionRadlProductionDetailsManagerHome.class);
		SessionRadlProductionDetailsManager rdlProductionObj = (SessionRadlProductionDetailsManager)PortableRemoteObject.narrow(rdlProHomeObj.create(),SessionRadlProductionDetailsManager.class);
		
		objRdlProdDetails = rdlProductionObj.getRadlProductionDetails(Integer.parseInt(id)); /* Production Details */
		objProdJobDet = objRdlProdDetails.getObjProductionJobDetails(); /* Production Job Details */
		vec_empDet = (Vector) objRdlProdDetails.getRadlEmpHrsDetails(); /* Employee Duty & OT Details */
		pageContext.setAttribute("vec_empDet",vec_empDet);
	}catch (Exception e)
	{
		if (BuildConfig.DMODE)
		{
			e.printStackTrace();
			System.out.println("Problem in Radial Production View.jsp.");
		}
	}
%>

<html:html>
<head>
<title><bean:message key="prodacs.production.header"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script>
	var isValid = <%= objRdlProdDetails.getRadlIsValid() %>;
	
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
			temp.action = '<bean:message key="context"/>/frmPostProd.do?formAction=searchRdl&id='+temp.viewId.value+'&frmDate='+temp.frmDate.value+'&toDate='+temp.toDate.value;
			temp.submit();
		}
		else
		{
			if ((temp.fromDate.value != "" ) || (temp.proToDate.value != "") || (temp.woNo.value != "") || (temp.jbName.value != "") || (temp.jbCombo.value != "") || (temp.mcCde.value != "") || (temp.sftName.value != "") || (temp.viewAll.value != "") || (temp.postedView.value != ""))
			{
				temp.action = '<bean:message key="context"/>/frmRdlProdList.do?formAction=afterView&fromDate='+temp.fromDate.value+'&proToDate='+temp.proToDate.value+'&jbName='+temp.jbName.value+'&jbCombo='+temp.jbCombo.value+'&mcCde='+temp.mcCde.value+'&sftName='+temp.sftName.value+'&viewAll='+temp.viewAll.value+'&postedView='+temp.postedView.value+'&woNo='+temp.woNo.value;
				temp.submit();
			}
			else
			{
				temp.action = '<bean:message key="context"/>/Production/RadialProductionList.jsp';
				temp.submit();
			}
		}
	}

	function addItem()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/Production/RadialProductionAdd.jsp';
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
			document.forms[0].action = '<bean:message key="context"/>/frmRdlProdEdit.do?formAction=modify&id=<%= objRdlProdDetails.getRadlId() %>';
			document.forms[0].submit();
		}	
	}
	
	function makeInvalidItem()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/frmRdlProdEdit.do?formAction=makeInvalid';
		temp.submit();
	}
	
	function loadToHidden()
	{
		temp = document.forms[0];
		temp.ids.value = temp.id.value;

		temp.fromDate.value = '<%= session.getAttribute("proFromDate") %>';
		temp.proToDate.value = '<%= session.getAttribute("proToDate") %>';
		temp.woNo.value = '<%= session.getAttribute("proWorkOrder") %>';
		temp.jbName.value = '<%= session.getAttribute("proJobName") %>';
		temp.jbCombo.value = '<%= session.getAttribute("jobSelect") %>';
		temp.mcCde.value = '<%= session.getAttribute("proMachineCode") %>';
		temp.sftName.value = '<%= session.getAttribute("proShiftName") %>';
		temp.viewAll.value = '<%= session.getAttribute("listValidEntries") %>';
		temp.postedView.value = '<%= session.getAttribute("postedView") %>';
	}
	
	function actionChecking()
	{
		temp = document.forms[0];
		temp.frmDate.value = '<%= request.getParameter("fDate") %>';
		temp.toDate.value = '<%= request.getParameter("tDate") %>';
		temp.viewId.value = '<%= request.getParameter("id") %>';
		if (temp.formAction.value == "radlview")
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
<html:form action="frmRdlProdEdit">
<html:hidden property="id"/>
<html:hidden property="ids"/>
<html:hidden property="formAction"/>
<input type="hidden" name="frmDate"/>
<input type="hidden" name="toDate"/>
<input type="hidden" name="viewId"/>
<input type="hidden" name="fromDate"/>
<input type="hidden" name="proToDate"/>
<input type="hidden" name="jbName"/>
<input type="hidden" name="jbCombo"/>
<input type="hidden" name="viewAll"/>
<input type="hidden" name="woNo"/>
<input type="hidden" name="mcCde"/>
<input type="hidden" name="sftName"/>
<input type="hidden" name="postedView"/>
<table width="100%" cellspacing="0" cellpadding="10">
<tr>
<td>
	<table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
	<tr>
		<td><bean:message key="prodacs.radialproduction.radialproductionview"/></td>
	</tr>
	</table>
	<br>
	<table width="100" cellspacing="0" cellpadding="0" id="tblLink" align="right">
  	<tr> 
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New RadialProduction Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1019" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Modify RadialProduction Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1019" text="[ Modify ]" classId="TopLnk" onClick="javaScript:editItem();"/>
 		<menuconfig:userRights url="#" id="mkInValid" onMouseOver="window.status='Make RadialProduction Info InValid'; return true"  onMouseOut="window.status=''; return true" resourceId="1019" text="[ MakeInValid ]" classId="TopLnk" onClick="javaScript:makeInvalidItem();"/>
	</tr>
	</table>
	<br>
	<table width="100%" cellspacing="0" cellpadding="0">
	<tr> 
		<td width="100" class="FieldTitle"><bean:message key="prodacs.job.date"/></td>
		<td width="1" class="FieldTitle">:</td>
		<td class="ViewData"><%= objRdlProdDetails.getRadlCrntDate().toString().substring(0,10) %></td>

		<td width="100" class="FieldTitle"><bean:message key="prodacs.machine.machinecode"/></td>
		<td width="1" class="FieldTitle">:</td>
		<td class="ViewData"><%= objRdlProdDetails.getMcCode() %></td>

		<td width="100" class="FieldTitle"><bean:message key="prodacs.production.machineshift"/></td>
		<td width="1" class="FieldTitle">:</td>
		<td class="ViewData"><%= objRdlProdDetails.getShiftName() %></td>

		<td width="100" class="FieldTitle"></td>
		<td class="FieldTitle"></td>
		<td class="ViewData"></td>
	</tr>
	</table>
	<fieldset id="FieldSet"><legend class="FieldTitle"><bean:message key="prodacs.radialproduction.radialproductiondetails"/></legend>
	  	<table width="100%" cellpadding="0" cellspacing="0">
		<tr>
			  <td width="85" class="FieldTitle" nowrap><bean:message key="prodacs.workorder.workorder"/></td>
			  <td width="1" class="FieldTitle">:</td>
			  <td width="150" class="ViewData" nowrap><%= objRdlProdDetails.getWoNo() %></td>

			  <td width="100" class="FieldTitle" nowrap><bean:message key="prodacs.production.totalqtysno"/></td>
			  <td width="1" class="FieldTitle">:</td>
			  <td class="ViewData" nowrap><%= objRdlProdDetails.getRadlQtySnos() %></td>

		</tr>
		<tr>
			  <td class="FieldTitle" nowrap><bean:message key="prodacs.job.jobname"/></td>
			  <td class="FieldTitle">:</td>
			  <td class="ViewData" nowrap><%= objProdJobDet.getJobName() %></td>
		
			  <td class="FieldTitle" nowrap><bean:message key="prodacs.production.startoperation"/></td>
			  <td class="FieldTitle">:</td>
			  <td class="ViewData" nowrap><%= objRdlProdDetails.getRadlStartOpn() %></td>

			  <td class="FieldTitle" nowrap><bean:message key="prodacs.production.endoperation"/></td>
			  <td class="FieldTitle">:</td>
			  <td class="ViewData" nowrap><%= objRdlProdDetails.getRadlEndOpn() %></td>

		</tr>
		<tr>
			  <td class="FieldTitle" nowrap><bean:message key="prodacs.production.worktype"/></td>
			  <td class="FieldTitle">:</td>
			  <td class="ViewData" nowrap><%= (objRdlProdDetails.getRadlWorkType().equalsIgnoreCase("R"))?"Rework":"Normal" %></td>
		
			  <td class="FieldTitle" nowrap><bean:message key="prodacs.production.stdhrs"/></td>
			  <td class="FieldTitle">:</td>
			  <td class="ViewData" nowrap><%= objRdlProdDetails.getRadlStdHrs() %></td>

			  <td class="FieldTitle" nowrap><bean:message key="prodacs.production.tothrs"/></td>
			  <td class="FieldTitle">:</td>
			  <td class="ViewData" nowrap><%= objRdlProdDetails.getRadlTotHrs() %></td>

		</tr>
		<tr>
			  <td class="FieldTitle" nowrap><bean:message key="prodacs.job.materialtype"/></td>
			  <td class="FieldTitle">:</td>
			  <td class="ViewData" nowrap><%= objRdlProdDetails.getRadlMatlTypeId() %></td>
		
			  <td class="FieldTitle" nowrap><bean:message key="prodacs.radialproduction.diameters"/></td>
			  <td class="FieldTitle">:</td>
			  <td class="ViewData" nowrap><%= objRdlProdDetails.getRadlDiameter() %></td>

			  <td class="FieldTitle" nowrap><bean:message key="prodacs.radialproduction.length"/></td>
			  <td class="FieldTitle">:</td>
			  <td class="ViewData" nowrap><%= objRdlProdDetails.getRadlLength() %></td>

		</tr>
		<tr>
			  <td class="FieldTitle" nowrap><bean:message key="prodacs.radialproduction.noofholes"/></td>
			  <td class="FieldTitle">:</td>
			  <td class="ViewData" nowrap><%= objRdlProdDetails.getRadlNoOfHoles() %></td>
		
			  <td class="FieldTitle" nowrap><bean:message key="prodacs.radialproduction.prediameter"/></td>
			  <td class="FieldTitle">:</td>
			  <td class="ViewData" nowrap><%= objRdlProdDetails.getRadlPreDiameter() %></td>

			  <td class="FieldTitle" nowrap><bean:message key="prodacs.radialproduction.completestatus"/></td>
			  <td class="FieldTitle">:</td>
			  <td class="ViewData" nowrap><%= (objRdlProdDetails.isRadlCompletionFlg() == true)?("Yes"):("No") %></td>

		</tr>
		<tr>
			  <td class="FieldTitle" nowrap><bean:message key="prodacs.job.incentive"/></td>
			  <td class="FieldTitle">:</td>
			  <td class="ViewData" nowrap><%= (objRdlProdDetails.isRadlCompletionFlg() == true)?("Yes"):("No") %></td>
			  <%
			  	if (objRdlProdDetails.getModifyCount() == 0)
			  	{
			  %>
			  <td class="FieldTitle" nowrap><bean:message key="prodacs.production.createdby"/></td>
			  <td class="FieldTitle">:</td>
			  <td class="ViewData" nowrap><%= objRdlProdDetails.getCreatedBy() %></td>
			  <%
			  	}
			  	else
			  	{
			  %>
			  <td class="FieldTitle" nowrap><bean:message key="prodacs.production.modifiedby"/></td>
			  <td class="FieldTitle">:</td>
			  <td class="ViewData" nowrap><%= objRdlProdDetails.getModifiedBy() %></td>
			  <%
			  	}
			  %>
		</tr>
		</table>
		<br>
	  	<div id="header" style="width:720; overflow:scroll; scrollbar-3dlight-color:#FFFFFF; scrollbar-arrow-color:#FFFFFF; scrollbar-base-color:#FFFFFF; scrollbar-darkshadow-color:#FFFFFF; scrollbar-face-color:#FFFFFF; scrollbar-highlight-color:#FFFFFF; scrollbar-shadow-color:#FFFFFF; scrollbar-track-color:#FFFFFF;">
		  	<table width="1000" cellpadding="3" cellspacing="0">
			<tr> 
				<td width="140" class="Header"><bean:message key="prodacs.employee.employeetype"/></td>
				<td class="Header"><bean:message key="prodacs.employee.employeename"/></td>
				<td width="190" class="Header"><bean:message key="prodacs.employee.employeecode"/></td>
				<td width="100" class="Header"><bean:message key="prodacs.production.dutyhrs"/></td>
				<td width="120" class="Header"><bean:message key="prodacs.production.overtimehrs"/></td>
			</tr>
			</table>
		</div>
		<div id="table" style="Position:absolute; top:272; height:120; width:720; overflow:auto;" onscroll="scrollhori()">
		 	<table width="1000" cellpadding="3" cellspacing="0">
		 	<logic:iterate id="bt1" name="vec_empDet" indexId="count">
		    	<tr>
			<%
				if(count.intValue()%2 == 0) 
				{
			%>		
			            <td width="140" class="TableItems"><bean:write name="bt1" property="empType"/>&nbsp;</td>
			            <td class="TableItems"><bean:write name="bt1" property="empName"/>&nbsp;</td>
						<td width="190" class="TableItems"><bean:write name="bt1" property="empCode"/>&nbsp;</td>
			            <td width="100" class="TableItems"><bean:write name="bt1" property="dutyHrs"/>&nbsp;</td>
			            <td width="120" class="TableItems"><bean:write name="bt1" property="otHrs"/>&nbsp;</td>
			<%
				}
				else
				{
			%>
			            <td width="140" class="TableItems2"><bean:write name="bt1" property="empType"/>&nbsp;</td>
			            <td class="TableItems2"><bean:write name="bt1" property="empName"/>&nbsp;</td>
						<td width="190" class="TableItems2"><bean:write name="bt1" property="empCode"/>&nbsp;</td>
			            <td width="100" class="TableItems2"><bean:write name="bt1" property="dutyHrs"/>&nbsp;</td>
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
				<html:button property="btnProdList" styleClass="Button" value="Radial Production List" onclick="javaScript:listItem();"/>&nbsp;
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
				<html:button property="prodListBtn" styleClass="Button" value="Radial Production List" onclick="javaScript:listItem();"/>&nbsp;
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
