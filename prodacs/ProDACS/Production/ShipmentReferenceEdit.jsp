<%@ page language = "java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri = "/WEB-INF/struts-logic.tld" prefix = "logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.StringTokenizer"%>
<%@ page import="java.util.GregorianCalendar"%>
<%@ page import="com.savantit.prodacs.facade.SessionShipmentDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionShipmentDetailsManager"%>
<%@ page import="com.savantit.prodacs.businessimplementation.securityadmin.UserAuthDetails"%>
<%@ page import="com.savantit.prodacs.businessimplementation.production.ShipmentDetails"%>
<%@ page import="com.savantit.prodacs.businessimplementation.production.ProductionJobDetails"%>
<%@ page import="com.savantit.prodacs.businessimplementation.production.ProductionJobQtyDetails"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<jsp:useBean id="frm" class="com.savantit.prodacs.presentation.tableadmin.production.ShipmentReferenceEditForm" />
<jsp:setProperty name="frm" property="*" />

<useradmin:userrights resource="1052"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("Shipment Reference Edit.jsp Starts");
	ShipmentDetails objShipmentDetails = new ShipmentDetails();
	ProductionJobDetails objProductionJobDetails = new ProductionJobDetails();
	ProductionJobQtyDetails objProdJobQtyDet = new ProductionJobQtyDetails();
	
	UserAuthDetails userDetails = (UserAuthDetails)session.getAttribute("##userRights##");
	String userName = userDetails.getUserId();

	try
	{
		EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator

		/* 	Setting the JNDI name and Environment 	*/
	   	obj.setJndiName("SessionShipmentDetailsManager");
   		obj.setEnvironment();

		/* 	Creating the Home and Remote Objects 	*/
		SessionShipmentDetailsManagerHome objShipmentHome = (SessionShipmentDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionShipmentDetailsManagerHome.class);
		SessionShipmentDetailsManager objShipment = (SessionShipmentDetailsManager)PortableRemoteObject.narrow(objShipmentHome.create(),SessionShipmentDetailsManager.class);
		
		objShipmentDetails = objShipment.getShipmentDetails(frm.getId());
		if (frm.getFormAction().equalsIgnoreCase("modify"))
		{
			frm.setHidWrkOrdId(objShipmentDetails.getWoId()+"");
			frm.setHidWrkOrdJobId(objShipmentDetails.getWoJbId()+"");
		}
		
		/* For loading Shifts */
		HashMap shiftDefnNameList = new HashMap();
		shiftDefnNameList = objShipment.getShiftDefnNameList();
		pageContext.setAttribute("shiftDefnNameList",shiftDefnNameList);
		
		/* For loading Shift through date Submission */
		if (!frm.getProDate().trim().equalsIgnoreCase(""))
		{
			StringTokenizer stDate = new StringTokenizer(frm.getProDate().trim(),"-");
			int year = 0;
			int mon = 0;
			int date = 0;
			if(stDate.hasMoreTokens())
			{
				year = Integer.parseInt(stDate.nextToken().trim());
			}
			if(stDate.hasMoreTokens())
			{
				mon = Integer.parseInt(stDate.nextToken().trim());
			}
			if(stDate.hasMoreTokens())
			{
				date = Integer.parseInt(stDate.nextToken().trim());
			}
			GregorianCalendar gc = new GregorianCalendar(year,mon-1,date);
			
			shiftDefnNameList = objShipment.getShiftDefnNameList(gc.getTime());
			if (BuildConfig.DMODE)
				System.out.println("Date Shift (HM) :"+shiftDefnNameList);
			pageContext.setAttribute("shiftDefnNameList",shiftDefnNameList);
		}
		else
		{
			pageContext.setAttribute("shiftDefnNameList",shiftDefnNameList);
		}
		
		/* For Loading WorkOrders */
		HashMap workOrderDet = objShipment.getWorkOrderList();
		pageContext.setAttribute("workOrderDet",workOrderDet);
		
		/* Loading Job Details by giving WorkOrder Id */
		Vector vecJobDet = new Vector();
		if (BuildConfig.DMODE)
			System.out.println("Wo Id: "+frm.getHidWrkOrdId());
		if (frm.getHidWrkOrdId() != "0")
		{
			vecJobDet = objShipment.getProdnJobDetailsByWorkOrder(Integer.parseInt(frm.getHidWrkOrdId()));
			for (int i = 0; i < vecJobDet.size(); i++)
			{
				objProductionJobDetails = (ProductionJobDetails) vecJobDet.get(i);
				if (BuildConfig.DMODE)
				{
					System.out.println("JobName: "+objProductionJobDetails.getJobName());
					System.out.println("JobId : "+objProductionJobDetails.getJobId());
				}
			}
			pageContext.setAttribute("vecJobDet",vecJobDet);
		}
		else
		{
			pageContext.setAttribute("vecJobDet",vecJobDet);
		}
		
		/* Loading Job Operation Details by giving WorkOrderJobId */
		Vector vecJobOpnDet = new Vector();
		if (BuildConfig.DMODE)
			System.out.println("HidWrkOrdJobId: "+frm.getHidWrkOrdJobId());
		if (frm.getHidWrkOrdJobId() != "0")
		{
			vecJobOpnDet = objShipment.getShipmentOperationDetailsForUpdate(Integer.parseInt(frm.getHidWrkOrdJobId()),frm.getId());
			for (int j = 0; j < vecJobOpnDet.size();j++)
			{
				objProdJobQtyDet = (ProductionJobQtyDetails) vecJobOpnDet.get(j);
			}
			pageContext.setAttribute("vecJobOpnDet",vecJobOpnDet);
		}
		else
		{
			pageContext.setAttribute("vecJobOpnDet",vecJobOpnDet);
		}

		/* Loading JobDetails for Checked JobName */
	 	objProductionJobDetails = (ProductionJobDetails)objShipmentDetails.getObjProductionJobDetails();

	}
	catch (Exception e)
	{
		if (BuildConfig.DMODE)
		{
			System.out.println("Problem in Shipment Reference Edit.jsp");
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
<script src='<bean:message key="context"/>/library/calendar_previous_submit.js'></script>
<script>
var oPopup;
	function init()
	 {
		oPopup = window.createPopup();
	 }

	function listItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/Production/ShipmentReferenceList.jsp';
		document.forms[0].submit();
	}

	function addItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/Production/ShipmentReferenceAdd.jsp';
		document.forms[0].submit();
	}
	
	function loadToHidden()
	{
		temp = document.forms[0];
		temp.ids.value = temp.id.value;
	}
	
</script>

<!-- Script tag for Shipment Reference Edit operations -->
<script>

	/* Numbers and Period are Allowed */
	function isNumber()
	{
		if (((event.keyCode > 47) && (event.keyCode < 58)) || (event.keyCode == 46))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/* Numbers only Allowed */
	function isNumber1()
	{
		if ((event.keyCode > 47) && (event.keyCode < 58))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	function dateSubmit()
	{
		var temp = document.forms[0];
		temp.formAction.value = "dateSubmit";
		temp.submit();
	}
	
	/* Showing the tables according to Entries in the Production Add Screen by Onload event*/
	function showJobDet()
	{
		var temp = document.forms[0];
		//alert("--"+temp.showCount.value+"--");
		temp.hidUserId.value = '<%= userName %>';

		var obj = document.getElementById("tblWorkOrdInfo");
		for (var i = 1; i < obj.children(0).children.length; i++)
		{
			if (obj.children(0).children(i).children(0).children(0).checked)
			{
				temp.hidChkdJobName.value = obj.children(0).children(i).children(1).children(0).value;
			}
		}
		
		if (temp.showCount.value == "")
		{
			//prodInfo.style.display='block';
			prodShftRow.style.display='none';
		}
		else if (temp.showCount.value == "1")
		{
			if (temp.proShift.options[temp.proShift.selectedIndex].value != "0")
				prodShftRow.style.display='block';
			else
				prodShftRow.style.display='none';

			/* Validation for updating the Date and Shift */
			var obj = document.getElementById("tblProdShft");
			obj.children(2).innerText = temp.proDate.value;
			obj.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;

			if (temp.rdWrkOrd == undefined)
				return false;
			if (temp.rdWrkOrd.length != undefined)
				temp.rdWrkOrd[temp.hidChkdJobId.value].checked = true;
			else
				temp.rdWrkOrd.checked = true;
		}
		else if (temp.showCount.value == "2")
		{
			prodShftRow.style.display='block';
			wrkOrder.style.display='block';

			/* Validation for updating Date, Shift, WorkOrderNo and Job name */
			var obj = document.getElementById("tblProdShft");
			obj.children(2).innerText = temp.proDate.value;
			obj.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;

			var obj1 = document.getElementById("tblProdShft2");
			obj1.children(2).innerText = temp.proDate.value;
			obj1.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;

			var obj2 = document.getElementById("tblProdWrkOrd");
			obj2.children(2).innerText = temp.proWorkOrderHash.options[temp.proWorkOrderHash.selectedIndex].text;
			obj2.children(5).innerText = temp.hidChkdJobName.value;

			/* Select the Checked Radio Button */
			if (temp.rdWrkOrd.length != undefined)
				temp.rdWrkOrd[temp.hidChkdJobId.value].checked = true;
			else
				temp.rdWrkOrd.checked = true;

			temp.proDCNo.value = '<%= objShipmentDetails.getDeliveryChallanNo() %>';
		}

	}

	/* This is for showing the Date and Shift indicate the invalid Things */
	function hideNshowDateShift()
	{
		var temp = document.forms[0];
		var obj = document.getElementById("tblProdShft");
		
		/* For displaying date and intimate an alert, if empty*/
		if (temp.proDate.value != "")
			obj.children(2).innerText = temp.proDate.value;
		else
		{
			alert("Select a Production Date to Continue!");
			prodShftRow.style.display='none';
			prodInfo.style.display='block';
			return false;
		}

		/* For displaying date and intimate an alert, if empty*/
		if (temp.proShift.options[temp.proShift.selectedIndex].value != "0")
			obj.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;
		else
		{
			alert ("Please Select a Shift Name for Production!");
			prodShftRow.style.display='none';
			prodInfo.style.display='block';
			return false;
		}

		/* All the Details are correct then Show the Next Job Details */
		if ((temp.showCount.value == "1") && ((temp.proShift.options[temp.proShift.selectedIndex].value == "0")))
		{
			prodShftRow.style.display='none';
			prodInfo.style.display='block';
			alert("Select a Shift for Production!");
			return false;
		}
		temp.showCount.value = "1";
		temp.formAction.value = "post";
		//loadProdList();
		temp.submit();
	}

	/* Loading Job Details by WorkOrderId */
	function loadJobDetByWOId(code)
	{
		var temp = document.forms[0];
		if (code != "0")
		{
			temp.hidWrkOrdId.value = code;
			temp.formAction.value = "getJobDetails";
			temp.showCount.value = "2";
			temp.submit();
		}
		else
		{
			alert ("Select a WorkOrder to View its Jobs!");
			return false;
		}
	}

	function hideNshowJobOpnDet()
	{
		var temp = document.forms[0];
		var obj = document.getElementById('tblWorkOrdInfo');
		if(obj.children(0).children.length == 1)
		{
			alert ("Sorry! This WorkOrder had no Jobs. Select different WorkOrder!");
			return false;
		}
		if (temp.rdWrkOrd.length != undefined)
		{
			if (temp.rdWrkOrd.length > 1) /* To get the WorkOrderJobId for selected Job */
			{
				if (temp.rdWrkOrd.length != 1)
				{
					for (var i = 0; i < temp.rdWrkOrd.length; i++)
					{
						if (temp.rdWrkOrd[i].checked == true)
						{
							flag = 1; /* One of the job must be selected */
							temp.hidWrkOrdJobId.value = obj.children(0).children(i+1).children(0).children(0).value;
							temp.hidChkdJobName.value = obj.children(0).children(i+1).children(1).innerText;
							temp.hidChkdJobId.value = i;
						}
					}
				}
			}
		}
		else /* Only one is there */
		{
			if (temp.rdWrkOrd.checked)
			{
				flag = 1;
				temp.hidWrkOrdJobId.value = obj.children(0).children(1).children(0).children(0).value;
				temp.hidChkdJobName.value = obj.children(0).children(1).children(1).innerText;
				temp.hidChkdJobId.value = 0;
			}
		}

		if (flag != 1) /* Not even a single job is selected */
		{
			alert ("U must Select a Job!");
			return false;
		}
		temp.showCount.value = "2"; /* Increment the count value */
		//loadProdList();
		temp.submit();
	}

	/* Check or Uncheck all the Records */
	function chkAll()
	{
		var temp = document.forms[0];
		var obj = document.getElementById("tblJobOperDet");
		if (obj.children(0).children.length > 2)
		{
			if (temp.checkbox2.checked)		
			{
				for (var i = 0; i < temp.CheckValue.length; i++)
				{
					temp.CheckValue[i].checked = true;
				}
			}
			else
			{
				for (var i = 0; i < temp.CheckValue.length; i++)
				{
					temp.CheckValue[i].checked = false;
				}
			}
		}
		else if (obj.children(0).children.length == 2)
		{
			if (temp.checkbox2.checked)
			{
				temp.CheckValue.checked = true;
			}
			else
			{
				temp.CheckValue.checked = false;
			}
		}
	}
	
	function modOperation()
	{
		var temp = document.forms[0];
		if (temp.formAction.value == "modify")
		{
			var totalQty = '<%= objShipmentDetails.getShipmentQtySnos() %>'; /* Splitting the Total Qty's */
			var arTotalQty = totalQty.split(",");

			var obj = document.getElementById("tblJobOperDet");
			for (var i = 1; i < obj.children(0).children.length; i++)
			{
				for (var j = 0; j < arTotalQty.length; j++)
				{
					if (obj.children(0).children(i).children(1).innerText == arTotalQty[j])
						obj.children(0).children(i).children(0).children(0).checked = true;
				}
			}
			
			var chkdWrkOrdJobId = '<%= objShipmentDetails.getWoJbId() %>'; /* To check the Selected Job Name */
			var obj = document.getElementById("tblWorkOrdInfo");
			for(i=1; i<obj.children(0).children.length;i++)
			{
				if(chkdWrkOrdJobId == obj.children(0).children(i).children(0).children(0).value)
				{
					obj.children(0).children(i).children(0).children(0).checked = true;
				}
			}

			//jobQty.style.display='block'; 

			/* Show all the table's Which are associated with Production */
			prodInfo.style.display='block';			
			prodShftRow.style.display='block';

			/* Validation for updating the Date & Shift */
			var obj = document.getElementById("tblProdShft");
			obj.children(2).innerText = temp.proDate.value;
			obj.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;

			wrkOrder.style.display='block';
			
			/* Validation for updating Date, Shift, WorkOrderNo and Job name */
			var obj = document.getElementById("tblProdShft");
			obj.children(2).innerText = temp.proDate.value;
			obj.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;

			var obj1 = document.getElementById("tblProdShft2");
			obj1.children(2).innerText = temp.proDate.value;
			obj1.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;
			
			var obj2 = document.getElementById("tblProdWrkOrd");
			obj2.children(2).innerText = temp.proWorkOrderHash.options[temp.proWorkOrderHash.selectedIndex].text;
			obj2.children(5).innerText = '<%= objProductionJobDetails.getJobName() %>';
		}
		else
		{
			return false;
		}
	}

	function updateShipment()
	{
		var temp = document.forms[0];
		var obj = document.getElementById('invisible');
		var obj1 = document.getElementById('tblJobOperDet');
		temp.hidProdList.value = "";
		var snos = "";
		obj.children(0).children(0).children(0).children(0).value = temp.proDate.value;
		obj.children(0).children(0).children(1).children(0).value = temp.proShift.options[temp.proShift.selectedIndex].value;
		obj.children(0).children(0).children(2).children(0).value = temp.proWorkOrderHash.options[temp.proWorkOrderHash.selectedIndex].value;
		obj.children(0).children(0).children(3).children(0).value = temp.hidWrkOrdJobId.value;
		for (var i = 1; i < obj1.children(0).children.length; i++)
		{
			if(obj1.children(0).children(i).children(0).children(0).checked)
			{
				snos += obj1.children(0).children(i).children(1).innerText+",";
				opnQty = obj1.children(0).children(i).children(2).children(0).value;
			}
		}
		obj.children(0).children(0).children(4).children(0).value = snos;
		obj.children(0).children(0).children(5).children(0).value = opnQty;
		obj.children(0).children(0).children(6).children(0).value = temp.proDCNo.value;
		var proDet = "";
		proDet += obj.children(0).children(0).children(0).children(0).value + "$"+
				  obj.children(0).children(0).children(1).children(0).value + "$"+
				  obj.children(0).children(0).children(2).children(0).value + "$"+
				  obj.children(0).children(0).children(3).children(0).value + "$"+
				  obj.children(0).children(0).children(4).children(0).value + "$"+
				  obj.children(0).children(0).children(5).children(0).value + "$"+
				  obj.children(0).children(0).children(6).children(0).value;
	
	temp.hidProdList.value = proDet;
	temp.formAction.value = "update";
	temp.submit();
	}
</script>
</head>

<body onload="init(); loadToHidden(); showJobDet(); modOperation();">
<html:form action="frmShipmentEdit">
<html:hidden property="formAction"/>
<html:hidden property="id"/>
<html:hidden property="ids"/>
<html:hidden property="modJobDet"/> <!-- Holding the JobDetails by EJB -->
<html:hidden property="hidWrkOrdId" value="<%= frm.getHidWrkOrdId() %>"/><!-- Handling the WorkOrder Id for Job Details -->
<html:hidden property="hidWrkOrdJobId" value="<%= frm.getHidWrkOrdJobId() %>"/><!-- Handling the WorkOrder Job Id for Job Operation Details -->
<html:hidden property="showCount" /> <!-- value="< %= frm.getShowCount() %>" Showing the appropriate Table by Count -->
<html:hidden property="hidChkdJobName" value="<%= frm.getHidChkdJobName() %>"/><!-- Holding the Selected Job name -->
<html:hidden property="hidChkdJobId" value="<%= frm.getHidChkdJobId() %>"/> <!-- Holding the Selected Job Id Index -->
<html:hidden property="proTotQtySnos" value="<%= frm.getProTotQtySnos() %>"/> <!-- Holding the Total Qty Sno's -->
<html:hidden property="hidUserId"/> <!-- Holding the User Name -->
<html:hidden property="hidWrkOrdJbStIds"/> <!-- Holding the WoJbStatusId's for Whether the work is Rework -->
<html:hidden property="hidProdList"/> <!-- Holding the Production List Details -->
<html:hidden property="hidWoJbId" value="<%= frm.getHidWoJbId() %>"/> <!-- Holding the WorkOrder Job Id -->

<table width="100%" cellspacing="0" cellpadding="10">
<tr>
<td>
	<table width="100%"  cellpadding="0" cellspacing="0" id="PageTitle">
	<tr>
		<td><bean:message key="prodacs.shipmentreference.header"/></td>
	</tr>
	</table>
    <br>
	<table width="100" cellspacing="0" cellpadding="0" align="right">
	<tr>
		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New Shipment Reference Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1049" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='List Shipment Reference Info'; return true"  onMouseOut="window.status=''; return true" resourceId="49" text="[ List ]" classId="TopLnk" onClick="javaScript:listItem();"/>
	</tr>
  	</table>
  	<br><br>
	<table>
	<tr>
		<td colspan='2'> <font size="1px" face="Verdana"><html:errors/></font></td>
	</tr>
	</table>
    <br>
	<table width="100%" cellspacing="0" cellpadding="0">
    <tr id="prodInfo">
    	<td valign="top" colspan="2">
    		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td width="100" class="FieldTitle"><bean:message key="prodacs.job.date"/><span class="mandatory">*</span></td>
                <td width="1" class="FieldTitle">:</td>
                <td width="200" class="FieldTitle"><html:text property="proDate" styleClass="TextBox" size="12" readonly="true"/>
							<img src='<bean:message key="context"/>/images/calendar.gif' width="16" height="15" align="absmiddle" onClick='show_calendar("proDate",ShipmentReferenceAdd.proDate.value,event.clientX,event.clientY,false);' onMouseOver="this.style.cursor='hand'"></td>
                <td width="100" class="FieldTitle"><bean:message key="prodacs.production.shift"/><span class="mandatory">*</span></td>
                <td width="1" class="FieldTitle">:</td>
                <td class="FieldTitle"><html:select property="proShift" styleClass="Combo">
											<html:option value="0">-- Select Shift --</html:option>
											<html:options collection="shiftDefnNameList" property="key" labelProperty="value"/>
									   </html:select></td>
			</tr>
            </table>
            <br>
            <table width="100%"  cellspacing="0" cellpadding="0" id="BtnBgLft">
            <tr>
            	<td><html:button styleClass="Button" property="proNextBtn1" onclick="hideNshowDateShift(); ">Next</html:button></td>
			</tr>
            </table>
		</td>
	</tr>
    <tr id="prodShftRow" style="display:none">
    	<td style="border-top:dashed 1px #666666">
    		<table width="100%" cellspacing="0" cellpadding="0">
			<tr id="tblProdShft">
            	<td width="100" class="FieldTitle"><bean:message key="prodacs.job.date"/></td>
                <td width="1" class="FieldTitle">:</td>
                <td width="200" class="ViewData">&nbsp;</td>
                <td width="100" class="FieldTitle"><bean:message key="prodacs.production.shift"/></td>
                <td width="1" class="FieldTitle">:</td>
                <td class="ViewData">&nbsp;</td>
			</tr>
            <tr>
            	<td class="FieldTitle"><bean:message key="prodacs.workorder.workorder"/></td>
                <td class="FieldTitle">:</td>
                <td colspan="4" class="ViewData"><html:select property="proWorkOrderHash" styleClass="Combo" onchange="loadJobDetByWOId(this.options[this.selectedIndex].value);">
													<html:option value="0">-- Select WorkOrder --</html:option>
													<html:options collection="workOrderDet" property="key" labelProperty="value"/>
												 </html:select></td>
			</tr>
            </table>
            <br>
            <table width="100%"  cellspacing="0" cellpadding="0" id="tblWorkOrdInfo">
            <tr>
            	<td width="23" class="SortHeader">&nbsp;</td>
                <td class="SortHeader"><bean:message key="prodacs.job.jobname"/></td>
                <td width="75" class="SortHeader"><bean:message key="prodacs.production.totalqty"/></td>
                <td width="100" class="SortHeader"><bean:message key="prodacs.production.pendingqtyseriel"/></td>
                <td width="120" class="SortHeader"><bean:message key="prodacs.production.postedqtyseriel"/></td>
                <td width="130" class="SortHeader"><bean:message key="prodacs.production.unpostedqtyseriel"/></td>
                <td width="120" class="SortHeader"><bean:message key="prodacs.production.lastproductiondateshift"/></td>
			</tr>
			<logic:iterate id="bt1" name="vecJobDet" indexId="count">
            <tr>
            <%
				if(count.intValue()%2 == 0)
				{
			%>
            	<td class="TableItems"><input name="rdWrkOrd" type="radio"  value="<bean:write name='bt1' property='woJbId'/>"></td>
                <td class="TableItems" title="<bean:write name='bt1' property='jobName'/>/<bean:write name='bt1' property='dwgNo'/>/<bean:write name='bt1' property='rvsnNo'/>/<bean:write name='bt1' property='matlType'/>"><bean:write name="bt1" property="jobName"/>&nbsp;</td>
                <td class="TableItems"><bean:write name="bt1" property="totQty"/>&nbsp;</td>
                <td class="TableItems"><bean:write name="bt1" property="pendingQtySnos" />&nbsp;</td>
                <td class="TableItems"><bean:write name="bt1" property="postedQtySnos" />&nbsp;</td>
                <td class="TableItems"><bean:write name="bt1" property="unPostedQtySnos" />&nbsp;</td>
                <td class="TableItems"><logic:notEmpty name="bt1" property="lastProdnDate">
											<bean:define id="date3" name="bt1" property="lastProdnDate"/>
											<%= date3.toString().substring(0,10) %>
										</logic:notEmpty>&nbsp;</td>
			<%
				}
				else
				{
			%>
            	<td class="TableItems2"><input name="rdWrkOrd" type="radio"  value="<bean:write name='bt1' property='woJbId'/>"></td>
                <td class="TableItems2" title="<bean:write name='bt1' property='jobName'/>/<bean:write name='bt1' property='dwgNo'/>/<bean:write name='bt1' property='rvsnNo'/>/<bean:write name='bt1' property='matlType'/>"><bean:write name="bt1" property="jobName"/>&nbsp;</td>
                <td class="TableItems2"><bean:write name="bt1" property="totQty"/>&nbsp;</td>
                <td class="TableItems2"><bean:write name="bt1" property="pendingQtySnos" />&nbsp;</td>
                <td class="TableItems2"><bean:write name="bt1" property="postedQtySnos" />&nbsp;</td>
                <td class="TableItems2"><bean:write name="bt1" property="unPostedQtySnos" />&nbsp;</td>
                <td class="TableItems2"><logic:notEmpty name="bt1" property="lastProdnDate">
											<bean:define id="date3" name="bt1" property="lastProdnDate"/>
											<%= date3.toString().substring(0,10) %>
										</logic:notEmpty>&nbsp;</td>
			<%
				}
			%>
			</tr>
			</logic:iterate>
            </table>
            <br>
            <table width="100%"  cellspacing="0" cellpadding="0" id="BtnBgLft">
            <tr>
            	<td><html:button styleClass="Button" property="proNextBtn2" onclick="hideNshowJobOpnDet();">Next</html:button></td>
			</tr>
            </table>
		</td>
	   	<td width="20" valign="top">
			<table width="100%" cellpadding="0" cellspacing="0" id="ShowHide">
			<tr>
				<td onClick="showHide(document.getElementById('prodInfo'), document.getElementById('prodShftImg'))"><img src='<bean:message key="context"/>/images/hide.gif' width="9" height="7" id="prodShftImg"></td>
			</tr>
			</table>
		</td>
	</tr>
    <tr id="wrkOrder" style="display:none">
    	<td style="border-top:dashed 1px #666666">
    		<table width="100%"  cellspacing="0" cellpadding="0">
			<tr id="tblProdShft2">
            	<td width="100" class="FieldTitle"><bean:message key="prodacs.job.date"/></td>
                <td width="1" class="FieldTitle">:</td>
                <td width="200" class="ViewData">&nbsp;</td>
                <td width="100" class="FieldTitle"><bean:message key="prodacs.production.shift"/></td>
                <td width="1" class="FieldTitle">:</td>
                <td class="ViewData">&nbsp;</td>
			</tr>
            <tr id="tblProdWrkOrd">
            	<td class="FieldTitle"><bean:message key="prodacs.workorder.workorder"/></td>
                <td class="FieldTitle">:</td>
                <td class="ViewData">&nbsp;</td>
                <td class="FieldTitle"><bean:message key="prodacs.job.jobname"/></td>
                <td class="FieldTitle">:</td>
                <td class="ViewData">&nbsp;</td>
			</tr>
            </table>
		    <br>
            <table width="100%"  cellspacing="0" cellpadding="0" id="tblJobOperDet">
            <tr>
            	<td width="23" class="SortHeader"><input type="checkbox" name="checkbox2" value="checkbox" onclick="chkAll(this);"></td>
                <td width="200" class="SortHeader"><bean:message key="prodacs.workorder.jobqtysno"/></td>
                <td class="SortHeader"><bean:message key="prodacs.production.pendingoperationsno"/></td>
			</tr>
            <logic:iterate id="bt2" name="vecJobOpnDet" indexId="count">
            <tr>
			<%
				if(count.intValue()%2 == 0)
				{
			%>
            	<td class="TableItems"><input type="checkbox" name="CheckValue" value='<bean:write name="bt2" property="jobStatId"/>'></td>
                <td class="TableItems"><bean:write name="bt2" property="jobQtySno"/></td>
                <td class="TableItems"><input type="text" class="TextBoxFull" readonly name="pendOpnSno" value='<bean:write name="bt2" property="pendingOpnSnos"/>'></td>
			<%
				}
				else
				{
			%>
            	<td class="TableItems"><input type="checkbox" name="CheckValue" value='<bean:write name="bt2" property="jobStatId"/>'></td>
                <td class="TableItems"><bean:write name="bt2" property="jobQtySno"/></td>
                <td class="TableItems"><input type="text" class="TextBoxFull" readonly name="pendOpnSno" value='<bean:write name="bt2" property="pendingOpnSnos"/>'></td>
            <%
            	}
            %>
			</tr>
			</logic:iterate>
            </table><br>
            <table width="100%" cellspacing="0" cellpadding="0" id="deliveryTbl">
			<tr>
                <td width="150" class="FieldTitle"><bean:message key="prodacs.shipment.deliverychallanno"/><span class="mandatory">*</span></td>
				<td width="1" class="FieldTitle">:</td>
                <td class="FieldTitle"><html:text property="proDCNo" onkeyup="return isNumber1(this);" maxlength="10" styleClass="TextBox" size="15"/></td>
			</tr>
			</table>
            <br>
            <table width="100%"  cellspacing="0" cellpadding="0" id="BtnBg">
            <tr>
            	<td><html:button property="add" styleClass="Button" onclick="updateShipment();">Update</html:button></td>
			</tr>
            </table>
			<td valign="top">
				<table width="100%" cellpadding="0" cellspacing="0" id="ShowHide">
				<tr> 
				  <td onClick="showHide(document.getElementById('prodInfo'), document.getElementById('prodShft3Img'))"><img src='<bean:message key="context"/>/images/hide.gif' name="prodShft3Img" width="9" height="7" id="prodShft3Img"></td>
				</tr>
				</table>
				<table width="100%" cellpadding="0" cellspacing="0" id="ShowHide">
				<tr> 
				  <td onClick="showHide(document.getElementById('prodShftRow'), document.getElementById('prodShftRow2Img'))"><img src='<bean:message key="context"/>/images/hide.gif' width="9" height="7" id="prodShftRow2Img"></td>
				</tr>
				</table>
				<table width="100%" cellpadding="0" cellspacing="0" id="ShowHide">
				<tr>
				  <td onClick="showHide(document.getElementById('wrkOrder'), document.getElementById('prodShftRow3Img'))"><img src='<bean:message key="context"/>/images/hide.gif' width="9" height="7" id="prodShftRow3Img"></td>
				</tr>
				</table>
			</td>
	</tr>
	</table>
	<table width="100%" id="invisible" style="display:none">
	<tr>
		<td><input name="prodDate"/></td>
		<td><input name="prodShift"/></td>
		<td><input name="prodWoNo"/></td>
		<td><input name="prodJobName"/></td>
		<td><input name="shipmentQtySno"/></td>
		<td><input name="prodShiftId"/></td>
		<td><input name="prodWoId"/></td>
		<td><input name="prodJobId"/></td>
		<td><input name="sQtySno"/></td>
		<td><input name="dcno"/></td>
	</tr>
	</table>
</td>
</tr>
</table>
</html:form>
</body>
</html:html>
