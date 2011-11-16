<%@ page language = "java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri = "/WEB-INF/struts-logic.tld" prefix = "logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<%@ page import="com.savantit.prodacs.facade.SessionDespatchClearanceDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionDespatchClearanceDetailsManager"%>
<%@ page import="com.savantit.prodacs.businessimplementation.production.DespatchClearanceDetails"%>

<jsp:useBean id="frm" class="com.savantit.prodacs.presentation.tableadmin.production.DespatchClearanceEditForm" />
<jsp:setProperty name="frm" property="*" /> 
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject"%>
<useradmin:userrights resource="51"/>

<%
	if (BuildConfig.DMODE)
		System.out.println("Despatch Clearance View");
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	DespatchClearanceDetails objDespatchClearanceDetails = new DespatchClearanceDetails();
	try
	{
		/* 	Setting the JNDI name and Environment 	*/
	   	obj.setJndiName("SessionDespatchClearanceDetailsManager");
   		obj.setEnvironment();

		/* 	Creating the Home and Remote Objects 	*/
		SessionDespatchClearanceDetailsManagerHome objDespatchClearance = (SessionDespatchClearanceDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionDespatchClearanceDetailsManagerHome.class);
		SessionDespatchClearanceDetailsManager objDesClear = (SessionDespatchClearanceDetailsManager)PortableRemoteObject.narrow(objDespatchClearance.create(),SessionDespatchClearanceDetailsManager.class);
		
		objDespatchClearanceDetails = objDesClear.getDespatchClearanceDetails(frm.getId());
	}
	catch(Exception e)
	{
		if (BuildConfig.DMODE)
		{
			System.out.println("Problem in DespatchClearanceView.jsp");
			e.printStackTrace();
		}
	}
%>

<html:html>
<head>
<title><bean:message key="prodacs.productionfinal.header"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script src='<bean:message key="context"/>/library/jscripts.js'></script>
<script language="Javascript" type="text/Javascript">
	var isValid = "<%= objDespatchClearanceDetails.getDespatchIsValid() %>";

	function chkMkValid()
	{
		temp = document.forms[0];
		temp.fromDate.value = '<%= session.getAttribute("proFromDate") %>';
		temp.prodToDate.value = '<%= session.getAttribute("proToDate") %>';
		temp.woNo.value = '<%= session.getAttribute("proWorkOrder") %>';
		temp.jbName.value = '<%= session.getAttribute("proJobName") %>';
		temp.jbCombo.value = '<%= session.getAttribute("jobSelect") %>';
		temp.viewAll.value = '<%= session.getAttribute("listValidEntries") %>';
		temp.postedView.value = '<%= session.getAttribute("postedView") %>';

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

	function listDespatchClearance()
	{
		temp = document.forms[0];
		if (temp.viewDespatchClearanceList.value == "Return to PostProduction")
		{
			temp.action = '<bean:message key="context"/>/frmPostProdQC.do?formAction=searchDespatch&frmDate='+temp.frmDate.value+'&toDate='+temp.toDate.value;
			temp.submit();
		}
		else
		{
			if ((temp.fromDate.value != "" ) || (temp.prodToDate.value != "") || (temp.woNo.value != "") || (temp.jbName.value != "") || (temp.jbCombo.value != "") || (temp.viewAll.value != "") || (temp.postedView.value != ""))
			{
				temp.action = '<bean:message key="context"/>/frmdespatchClearanceList.do?formAction=afterView&fromDate='+temp.fromDate.value+'&prodToDate='+temp.prodToDate.value+'&jbName='+temp.jbName.value+'&jbCombo='+temp.jbCombo.value+'&viewAll='+temp.viewAll.value+'&postedView='+temp.postedView.value;
				temp.submit();
			}
			else
			{
				temp.action = '<bean:message key="context"/>/Production/DespatchClearanceList.jsp';
				temp.submit();
			}
		}
	}
	
	function addItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/Production/DespatchClearanceAdd.jsp';
		document.forms[0].submit();
	}
	
	function makeInvalidItem()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/frmdespatchClearanceEdit.do?formAction=makeInvalid';
		temp.submit();
	}

	function makeValid()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/frmdespatchClearanceEdit.do?formAction=makeValid';
		temp.submit();
	}
	
	function loadToHidden()
	{
		document.forms[0].ids.value = document.forms[0].id.value;
	}
	
	function editItem()
	{
		if (isValid == 0)
			alert("Invalid Item Cannot be Modified! ");
		else
		{
			document.forms[0].action = '<bean:message key="context"/>/frmdespatchClearanceEdit.do?formAction=modify&id='+<%= objDespatchClearanceDetails.getDespatchId() %>;
			document.forms[0].submit();
		}	
	}

	function actionChecking()
	{
		temp = document.forms[0];
		temp.frmDate.value = '<%= request.getParameter("fDate") %>';
		temp.toDate.value = '<%= request.getParameter("tDate") %>';
		if (temp.formAction.value == "despatchView")
		{
			tblLink.style.display = 'none';
			temp.viewDespatchClearanceList.value = "Return to PostProduction";
		}
	}
	
</script>
</head>
<body onLoad="chkMkValid();loadToHidden(); actionChecking();">
<html:form action="frmdespatchClearanceEdit">
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

  <table width="100%" height="100%" cellpadding="10" cellspacing="0">
    <tr> 
      <td valign="top"><table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
          <tr>
            <td><bean:message key="prodacs.despatchclearance.header"/></td>
          </tr>
        </table>
        <br>
        <table width="100" cellspacing="0" cellpadding="0" align="right" id="tblLink">
          <tr> 
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New Despatch Clearance Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1051" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Modify Despatch Clearance Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1051" text="[ Modify ]" classId="TopLnk" onClick="javaScript:editItem();"/>          
 		<menuconfig:userRights url="#" id="mkInValid" onMouseOver="window.status='Make Despatch Clearance InValid'; return true"  onMouseOut="window.status=''; return true" resourceId="1051" text="[ MakeInValid ]" classId="TopLnk" onClick="javaScript:makeInvalidItem();"/>
 		<menuconfig:userRights url="#" id="mkValid" onMouseOver="window.status='Make Despatch Clearance Valid'; return true"  onMouseOut="window.status=''; return true" resourceId="1051" text="[ MakeValid ]" classId="TopLnk" style="display:none" onClick="javaScript:makeValid();"/>
          </tr>
        </table>
        <br><br>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td width="130" class="FieldTitle"><bean:message key="prodacs.clearancedate"/></td>
            <td width="1" class="FieldTitle">:</td>
            <td width="130" class="ViewData"><%= (objDespatchClearanceDetails.getDespatchCrntDate()+"").substring(0,(objDespatchClearanceDetails.getDespatchCrntDate()+"").length()-10) %></td>
            <td width="80" class="FieldTitle"><bean:message key="prodacs.workcalendar.shiftdefinition.shiftname"/></td>
            <td width="1" class="FieldTitle">:</td>
            <td width="100" class="ViewData"><%= objDespatchClearanceDetails.getShiftName() %></td>
            <td width="100" class="FieldTitle"><bean:message key="prodacs.workorder.workorder.wono"/></td>
            <td width="1" class="FieldTitle">:</td>
            <td class="ViewData"><%= objDespatchClearanceDetails.getWoNo() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.job.jobname"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objDespatchClearanceDetails.getObjProductionJobDetails().getJobName() %></td>
            <td class="FieldTitle"><bean:message key="prodacs.job.drawingno"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objDespatchClearanceDetails.getObjProductionJobDetails().getDwgNo() %></td>
            <td class="FieldTitle"><bean:message key="prodacs.job.revisionno"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objDespatchClearanceDetails.getObjProductionJobDetails().getRvsnNo() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.despatchqtysnos"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objDespatchClearanceDetails.getDespatchQtySnos() %></td>
            <td class="FieldTitle"><bean:message key="prodacs.totaldespatchedqty"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objDespatchClearanceDetails.getDespatchTotQty() %></td>
            <td class="FieldTitle"><bean:message key="prodacs.despatchedby"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objDespatchClearanceDetails.getCreatedBy() %></td>
          </tr>
        </table>
        <br>
        <table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
          <tr>
            <td> 
              <html:button property="viewDespatchClearanceList" styleClass="Button" onclick="javascript:listDespatchClearance()">Despatch Clearance List</html:button></td>
          </tr>
        </table></td>
    </tr>
  </table>
</html:form>
</body>
</html:html>
