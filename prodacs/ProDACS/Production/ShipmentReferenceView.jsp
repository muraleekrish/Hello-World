<%@ page language = "java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri = "/WEB-INF/struts-logic.tld" prefix = "logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<%@ page import="com.savantit.prodacs.facade.SessionShipmentDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionShipmentDetailsManager"%>
<%@ page import="com.savantit.prodacs.businessimplementation.production.ShipmentDetails"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<jsp:useBean id="frm" class="com.savantit.prodacs.presentation.tableadmin.production.ShipmentReferenceEditForm" />
<jsp:setProperty name="frm" property="*" />

<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject"%>
<useradmin:userrights resource="52"/>

<%
	if (BuildConfig.DMODE)
		System.out.println("Shipment Reference View");
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	ShipmentDetails objShipmentDetails = new ShipmentDetails();
	try
	{
		/* 	Setting the JNDI name and Environment 	*/
	   	obj.setJndiName("SessionShipmentDetailsManager");
   		obj.setEnvironment();

		/* 	Creating the Home and Remote Objects 	*/
		SessionShipmentDetailsManagerHome objShipmentHome = (SessionShipmentDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionShipmentDetailsManagerHome.class);
		SessionShipmentDetailsManager objShipment = (SessionShipmentDetailsManager)PortableRemoteObject.narrow(objShipmentHome.create(),SessionShipmentDetailsManager.class);
		
		objShipmentDetails = objShipment.getShipmentDetails(frm.getId());
	}
	catch(Exception e)
	{
		if (BuildConfig.DMODE)
		{
			System.out.println("Problem in ShipmentReferenceView.jsp");
			e.printStackTrace();
		}
	}
%>

<html:html>
<head>
<title><bean:message key="prodacs.shipmentreference.header"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script src='<bean:message key="context"/>/library/jscripts.js'></script>
<script language="Javascript" type="text/Javascript">
	var isValid = "<%= objShipmentDetails.getShipmentIsValid() %>";

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

	function listItem()
	{
		temp = document.forms[0];
		if (temp.viewShipment.value == "Return to PostProduction")
		{
			temp.action = '<bean:message key="context"/>/frmShipPost.do?formAction=searchShipment&frmDate='+temp.frmDate.value+'&toDate='+temp.toDate.value;
			temp.submit();
		}
		else
		{
			if ((temp.fromDate.value != "" ) || (temp.prodToDate.value != "") || (temp.woNo.value != "") || (temp.jbName.value != "") || (temp.jbCombo.value != "") || (temp.viewAll.value != "") || (temp.postedView.value != ""))
			{
				temp.action = '<bean:message key="context"/>/frmShipmentList.do?formAction=afterView&fromDate='+temp.fromDate.value+'&prodToDate='+temp.prodToDate.value+'&jbName='+temp.jbName.value+'&jbCombo='+temp.jbCombo.value+'&viewAll='+temp.viewAll.value+'&postedView='+temp.postedView.value;
				temp.submit();
			}
			else
			{
				temp.action = '<bean:message key="context"/>/Production/ShipmentReferenceList.jsp';
				temp.submit();
			}
		}
	}
	
	function addItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/Production/ShipmentReferenceAdd.jsp';
		document.forms[0].submit();
	}
	
	function makeInvalidItem()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/frmShipmentEdit.do?formAction=makeInvalid';
		temp.submit();
	}

	function makeValid()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/frmShipmentEdit.do?formAction=makeValid';
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
			document.forms[0].action = '<bean:message key="context"/>/frmShipmentEdit.do?formAction=modify&id='+<%= objShipmentDetails.getShipmentId() %>;
			document.forms[0].submit();
		}	
	}

	function actionChecking()
	{
		temp = document.forms[0];
		temp.frmDate.value = '<%= request.getParameter("fDate") %>';
		temp.toDate.value = '<%= request.getParameter("tDate") %>';
		if (temp.formAction.value == "shipmentView")
		{
			tblLink.style.display = 'none';
			temp.viewShipment.value = "Return to PostProduction";
		}
	}
	
</script>
</head>
<body onLoad="chkMkValid();loadToHidden(); actionChecking();">
<html:form action="frmShipmentEdit">
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
            <td><bean:message key="prodacs.shipmentreference.header"/></td>
          </tr>
        </table>
        <br>
        <table width="100" cellspacing="0" cellpadding="0" align="right" id="tblLink">
          <tr> 
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New Shipment Reference Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1052" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Modify Shipment Reference Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1052" text="[ Modify ]" classId="TopLnk" onClick="javaScript:editItem();"/>          
 		<menuconfig:userRights url="#" id="mkInValid" onMouseOver="window.status='Make Shipment Reference InValid'; return true"  onMouseOut="window.status=''; return true" resourceId="1052" text="[ MakeInValid ]" classId="TopLnk" onClick="javaScript:makeInvalidItem();"/>
 		<menuconfig:userRights url="#" id="mkValid" onMouseOver="window.status='Make Shipment Reference Valid'; return true"  onMouseOut="window.status=''; return true" resourceId="1052" text="[ MakeValid ]" classId="TopLnk" style="display:none" onClick="javaScript:makeValid();"/>
          </tr>
        </table>
        <br><br>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td width="130" class="FieldTitle"><bean:message key="prodacs.shipment.date"/></td>
            <td width="1" class="FieldTitle">:</td>
            <td width="130" class="ViewData"><%= objShipmentDetails.getShipmentCrntDate().toString().substring(0,10) %></td>
            <td width="80" class="FieldTitle"><bean:message key="prodacs.workcalendar.shiftdefinition.shiftname"/></td>
            <td width="1" class="FieldTitle">:</td>
            <td width="100" class="ViewData"><%= objShipmentDetails.getShiftName() %></td>
            <td width="100" class="FieldTitle"><bean:message key="prodacs.workorder.workorder.wono"/></td>
            <td width="1" class="FieldTitle">:</td>
            <td class="ViewData"><%= objShipmentDetails.getWoNo() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.job.jobname"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objShipmentDetails.getObjProductionJobDetails().getJobName() %></td>
            <td class="FieldTitle"><bean:message key="prodacs.job.drawingno"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objShipmentDetails.getObjProductionJobDetails().getDwgNo() %></td>
            <td class="FieldTitle"><bean:message key="prodacs.job.revisionno"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objShipmentDetails.getObjProductionJobDetails().getRvsnNo() %></td>
          </tr>
          <tr>
		  <%
		  if (objShipmentDetails.getShipmentQtySnos().length() == 1)
		  {
		  %>
            <td class="FieldTitle"><bean:message key="prodacs.shipment.shipmentqtysnos"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objShipmentDetails.getShipmentQtySnos() %></td>
		  <%
		  }
		  else
		  {
		  %>
            <td class="FieldTitle"><bean:message key="prodacs.shipment.shipmentqtysnos"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objShipmentDetails.getShipmentQtySnos().substring(0,1) %></td>
		  <%
		  }
		  %>
            <td class="FieldTitle"><bean:message key="prodacs.shipment.shipmenttotqty"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objShipmentDetails.getShipmentTotQty() %></td>
            <%
            if (objShipmentDetails.getModifyCount() == 0)
            {
            %>
            <td class="FieldTitle"><bean:message key="prodacs.production.createdby"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objShipmentDetails.getCreatedBy() %></td>
            <%
            }
            else
            {
            %>
            <td class="FieldTitle"><bean:message key="prodacs.production.modifiedby"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objShipmentDetails.getCreatedBy() %></td>
			<%
			}
			%>
          </tr>
          <tr>
            <td class="FieldTitle"><bean:message key="prodacs.shipment.deliverychallanno"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objShipmentDetails.getDeliveryChallanNo() %></td>
          </tr>
        </table>
        <br>
        <table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
          <tr>
            <td> 
              <html:button property="viewShipment" styleClass="Button" onclick="javascript:listItem()">Shipment Reference List</html:button></td>
          </tr>
        </table></td>
    </tr>
  </table>
</html:form>
</body>
</html:html>
