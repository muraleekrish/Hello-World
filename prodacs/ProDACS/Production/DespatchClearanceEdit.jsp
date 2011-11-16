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
<%@ page import="com.savantit.prodacs.facade.SessionDespatchClearanceDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionDespatchClearanceDetailsManager"%>
<%@ page import="com.savantit.prodacs.businessimplementation.securityadmin.UserAuthDetails"%>
<%@ page import="com.savantit.prodacs.businessimplementation.production.DespatchClearanceDetails"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<jsp:useBean id="frm" class="com.savantit.prodacs.presentation.tableadmin.production.DespatchClearanceEditForm" />
<jsp:setProperty name="frm" property="*" /> 
<useradmin:userrights resource="1051"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("Despatch Clearance Edit Form");
	UserAuthDetails userDetails = (UserAuthDetails)session.getAttribute("##userRights##");
	String userName = userDetails.getUserId();

	try
	{
		EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
		DespatchClearanceDetails objDespatchClearanceDetails = new DespatchClearanceDetails();

		/* 	Setting the JNDI name and Environment 	*/
	   	obj.setJndiName("SessionDespatchClearanceDetailsManager");
   		obj.setEnvironment();

		/* 	Creating the Home and Remote Objects 	*/
		SessionDespatchClearanceDetailsManagerHome objDespatchClearance = (SessionDespatchClearanceDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionDespatchClearanceDetailsManagerHome.class);
		SessionDespatchClearanceDetailsManager objDesClear = (SessionDespatchClearanceDetailsManager)PortableRemoteObject.narrow(objDespatchClearance.create(),SessionDespatchClearanceDetailsManager.class);

		objDespatchClearanceDetails = objDesClear.getDespatchClearanceDetails(frm.getId());	
		if (frm.getFormAction().equalsIgnoreCase("modify"))
		{
			frm.setProDate(objDespatchClearanceDetails.getDespatchCrntDate().toString().substring(0,10));
			frm.setHidWrkOrdId(objDespatchClearanceDetails.getWoId()+"");
			frm.setHidWrkOrdJobId(objDespatchClearanceDetails.getWoJbId()+"");
		}
		
		/* For loading WorkOrder */
		HashMap workOrderDet = objDesClear.getWorkOrderList();
		pageContext.setAttribute("workOrderDet",workOrderDet);
		
		/* For loading Shift through date Submission */
		HashMap dateShift = new HashMap();
		if (!frm.getProDate().trim().equalsIgnoreCase(""))
		{
			StringTokenizer stDate = new StringTokenizer(frm.getProDate().trim(),"-");
			int year = 0;
			int mon = 0;
			int date = 0;
			if(stDate.hasMoreTokens())
				year = Integer.parseInt(stDate.nextToken().trim());
			if(stDate.hasMoreTokens())
				mon = Integer.parseInt(stDate.nextToken().trim());
			if(stDate.hasMoreTokens())
				date = Integer.parseInt(stDate.nextToken().trim());
			GregorianCalendar gc = new GregorianCalendar(year,mon-1,date);
			
			dateShift = objDesClear.getShiftDefnNameList(gc.getTime());
			pageContext.setAttribute("dateShift",dateShift);
		}
		else
			pageContext.setAttribute("dateShift",dateShift);
		
		/* Loading JobDetails using WorkOrderId */
		Vector vecJobDet = new Vector();
		if (frm.getHidWrkOrdId() != "0")
		{
			vecJobDet = objDesClear.getProdnJobDetailsForUpdateByWorkOrder(Integer.parseInt(frm.getHidWrkOrdId()),0,0,0,frm.getId(),0);
			pageContext.setAttribute("vecJobDet",vecJobDet);
		}
		else
			pageContext.setAttribute("vecJobDet",vecJobDet);

		/* Loading Job Operation Details by giving WorkOrderJobId */
		Vector vecJobOpnDet = new Vector();
		if (frm.getHidWrkOrdJobId() != "0")
		{
			vecJobOpnDet = objDesClear.getDespatchOperationDetailsForUpdate(Integer.parseInt(frm.getHidWrkOrdJobId()),frm.getId());
			pageContext.setAttribute("vecJobOpnDet",vecJobOpnDet);
		}
		else
			pageContext.setAttribute("vecJobOpnDet",vecJobOpnDet);
	}
	catch(Exception e)
	{
		if (BuildConfig.DMODE)
		{
			System.out.println("Problem in Despatch Clearance Edit.jsp");
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
<script src='<bean:message key="context"/>/library/calendar_previous_submit.js'></script>
<script>
var oPopup;
	function init()
	 {
		oPopup = window.createPopup();
	 }

	function listItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/Production/DespatchClearanceList.jsp';
		document.forms[0].submit();
	}

	function addItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/Production/DespatchClearanceAdd.jsp';
		document.forms[0].submit();
	}
</script>

<!-- Script tag for Production Final operations -->
<script>
	
	/* Fn called from Calendar Script */
	function dateSubmit()
	{
		var temp = document.forms[0];
		temp.formAction.value = "dateSubmit";
		temp.submit();
	}

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
	
	/* This fn. invoked by Selecting a WorkOrder Id Combo */
	function loadJobDetByWOId(code)
	{
		var temp = document.forms[0];
		if (code != "0")
		{
			temp.hidWrkOrdId.value = code;
			temp.formAction.value = "getJobDetails";
			temp.showCount.value = "1";
			loadProdList();
			temp.submit();
		}
		else
		{
			alert ("Select a WorkOrder to View its Jobs!");
			return false;
		}
	}

	function showJobDet()
	{
		var temp = document.forms[0];
		//alert (temp.showCount.value);
		temp.hidUserId.value = '<%= userName %>';
		
		/* If the Buffer is not empty then it'll populate those values automatically */
		if (temp.hidProdList.value != "")
		{
			jobQty.style.display = 'none';
			
			var object = document.getElementById('tblBufferList').children(0);
			var arRowDet = (temp.hidProdList.value).split("$");
			for (var i = 0; i < arRowDet.length; i++)
			{
				var arColDet = arRowDet[i].split("#");
				if (i == 0)
				{
					object.children(0).children(1).children(0).value = arColDet[0];
					object.children(0).children(2).children(0).value = arColDet[1];
					object.children(0).children(3).children(0).value = arColDet[2];
					object.children(0).children(4).children(0).value = arColDet[3];
					object.children(0).children(5).children(0).value = arColDet[4];
					object.children(0).children(6).children(0).value = arColDet[5];
					
					object.children(0).children(7).children(0).value = arColDet[6]; // Shift Id
					object.children(0).children(8).children(0).value = arColDet[7]; // Work Order Id
					object.children(0).children(9).children(0).value = arColDet[8]; // Job Id
					object.children(0).children(10).children(0).value = arColDet[9]; // Despatch Qty
				}
				else
				{
					var newNode = object.children(0).cloneNode(true);
					object.appendChild(newNode);
					var len = object.children.length - 1;

					object.children(len).children(1).children(0).value = arColDet[0];
					object.children(len).children(2).children(0).value = arColDet[1];
					object.children(len).children(3).children(0).value = arColDet[2];
					object.children(len).children(4).children(0).value = arColDet[3];
					object.children(len).children(5).children(0).value = arColDet[4];
					object.children(len).children(6).children(0).value = arColDet[5];

					object.children(len).children(7).children(0).value = arColDet[6]; // Shift Id
					object.children(len).children(8).children(0).value = arColDet[7]; // Work Order Id
					object.children(len).children(9).children(0).value = arColDet[8]; // Job Id
					object.children(len).children(10).children(0).value = arColDet[9]; // Despatch Qty
				}
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
		}
		else if (temp.showCount.value == "5")
		{
			//prodInfo.style.display = 'block';
			prodShftRow.style.display = 'block';
			wrkOrder.style.display = 'block';
			jobQty.style.display = 'none';
			
			var details = '<%= request.getParameter("hidModDet") %>';
			if (details == 'null')
				details = '<%= request.getAttribute("hidModDet") %>';
			if (details == 'null')
				return false;
			var modDet = details.split("#");
			temp.proDate.value = modDet[1]; // Date

			for (var i = 0; i < temp.proShift.length; i++) // Shift
			{
				if (temp.proShift.options[i].value == modDet[6])
					temp.proShift.selectedIndex = i;
			}

			/* Validation for updating the Date and Shift */
			var obj = document.getElementById("tblProdShft");
			obj.children(2).innerText = temp.proDate.value;
			obj.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;

			for (var i = 0; i < temp.proWorkOrderHash.length; i++) // WoNo
			{
				if (temp.proWorkOrderHash.options[i].value == modDet[7])
					temp.proWorkOrderHash.selectedIndex = i;
			}

			/* Check the Particular Job */
			var obj = document.getElementById('tblWorkOrdInfo');
			for (var i = 1; i < obj.children(0).children.length; i++)
			{
				if (obj.children(0).children(i).children(0).children(0).value == modDet[8])
				{
					obj.children(0).children(i).children(0).children(0).checked = true;
					temp.hidChkdJobName.value = obj.children(0).children(i).children(1).innerText;
				}
			}

			var obj = document.getElementById("tblProdShft2");
			obj.children(2).innerText = temp.proDate.value;
			obj.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;

			var obj2 = document.getElementById("tblProdWrkOrd");
			obj2.children(2).innerText = temp.proWorkOrderHash.options[temp.proWorkOrderHash.selectedIndex].text;
			obj2.children(5).innerText = temp.hidChkdJobName.value;

			/* To Check the Job Operation Quantities */
			var jbQty = modDet[5].split(","); // Despatch Qty Snos
			var obj = document.getElementById('tblJobOperDet');
			for (var i = 1; i < obj.children(0).children.length; i++)
			{
				for (var j = 0; j < jbQty.length; j++)
				{
					if (obj.children(0).children(i).children(1).innerText == jbQty[j])
						obj.children(0).children(i).children(0).children(0).checked = true;
				}
			}
		}
	}

	/* Showing JobOperDetails by hiding other 2 tables */
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
		loadProdList();
		temp.submit();
	}

	/* Check all Fn. for Job Operation Details */
	function chkAll()
	{
		var temp = document.forms[0];
		var obj = document.getElementById("tblJobOperDet");
		if (obj.children(0).children.length > 1)
		{
			if (temp.CheckValue.length != undefined)
			{
				if (temp.checkbox2.checked)
				{
					for (var i = 0; i < temp.CheckValue.length; i++)
						temp.CheckValue[i].checked = true;
				}
				else
				{
					for (var i = 0; i < temp.CheckValue.length; i++)
						temp.CheckValue[i].checked = false;
				}
			}
			else if (temp.checkbox2.checked)
				temp.CheckValue.checked = true;
			else
				temp.CheckValue.checked = false;
		}
		else
		{
			if (temp.checkValue != undefined)
			{
				if (temp.checkbox2.checked)
					temp.CheckValue.checked = true;
				else
					temp.CheckValue.checked = false;
			}
		}
	}

	/* This fn take a backup to all Production List Details */
	function loadProdList()
	{
		var temp = document.forms[0];
		var obj = document.getElementById('tblBufferList').children(0);
		var prodDet = "";
		temp.hidProdList.value = "";
		if (obj.children(0).children(1).children(0).value == "")
			return false;
		for (var i = 0; i < obj.children.length; i++)
		{
			if (i != 0)
				prodDet = prodDet + "$";
			prodDet = prodDet
				+ obj.children(i).children(1).children(0).value // Sno
				+ "#" + obj.children(i).children(2).children(0).value // Date
				+ "#" + obj.children(i).children(3).children(0).value // Shift Name
				+ "#" + obj.children(i).children(4).children(0).value // WoNo
				+ "#" + obj.children(i).children(5).children(0).value // Job Name
				+ "#" + obj.children(i).children(6).children(0).value // Despatch Qty Snos
				+ "#" + obj.children(i).children(7).children(0).value // Shift ID
				+ "#" + obj.children(i).children(8).children(0).value // Wo Id
				+ "#" + obj.children(i).children(9).children(0).value // Job Id
				+ "#" + obj.children(i).children(10).children(0).value; // Despatch Qty
		}
		temp.hidProdList.value = prodDet;
		//alert ("Back Up: "+temp.hidProdList.value);
	}

	/* Hiding the JobDetails, JobOperation Details and show the Employee Details */
	function hideNshowBufferDetails()
	{
		var temp = document.forms[0];
		var obj = document.getElementById("tblJobOperDet").children(0); /* Picking that JobOperationDetails Table */
		var snos = "";
		var opnQty = "";
		if (obj.children.length > 1)
		{
			for (var i = 1; i < obj.children.length; i++)
			{
				if (obj.children(i).children(0).children(0).checked)
				{
					snos += obj.children(i).children(1).innerText+", ";
					opnQty = obj.children(i).children(2).children(0).value;
				}
			}
		}
		if (snos != "")
		{
			/* For Work Order Job Operation Name and Id */
			var woJobId = "";
			var woJobName = "";
			
			var wobj = document.getElementById('tblWorkOrdInfo').children(0);
			for (var i = 1; i < wobj.children.length; i++)
			{
				if (wobj.children(i).children(0).children(0).checked)
				{
					woJobId = wobj.children(i).children(0).children(0).value;
					woJobName = wobj.children(i).children(1).innerText;
				}
			}
			if (woJobId == "")
			{
				alert ("U must Select a Job First!");
				return false;
			}
			
			var object = document.getElementById('tblBufferList').children(0);
			for (var i = 0; i < object.children.length; i++)
			{
				if (temp.proWorkOrderHash.value == object.children(0).children(8).children(0).value)
				{
					alert ("You can't enter twice for the Same WorkOrder!");
					return false;
				}
			}
			var no = 1;
			if ((object.children.length == 1) && (object.children(0).children(2).children(0).value == ""))
			{
				object.children(0).children(1).children(0).value = object.children.length;
				object.children(0).children(2).children(0).value = temp.proDate.value;
				object.children(0).children(3).children(0).value = temp.proShift[temp.proShift.selectedIndex].text;
				object.children(0).children(4).children(0).value = temp.proWorkOrderHash[temp.proWorkOrderHash.selectedIndex].text;
				object.children(0).children(5).children(0).value = woJobName;
				object.children(0).children(6).children(0).value = snos.substring(0,snos.length-2);
				
				object.children(0).children(7).children(0).value = temp.proShift.value; // Shift Id
				object.children(0).children(8).children(0).value = temp.proWorkOrderHash.value; // Work Order Id
				object.children(0).children(9).children(0).value = woJobId; // Job Id
				object.children(0).children(10).children(0).value = opnQty; // Despatch Qty
			}
			else
			{
				var newNode = object.children(0).cloneNode(true);
				object.appendChild(newNode);
				var len = object.children.length - 1;

				object.children(len).children(1).children(0).value = object.children.length;
				object.children(len).children(2).children(0).value = temp.proDate.value;
				object.children(len).children(3).children(0).value = temp.proShift[temp.proShift.selectedIndex].text;
				object.children(len).children(4).children(0).value = temp.proWorkOrderHash[temp.proWorkOrderHash.selectedIndex].text;
				object.children(len).children(5).children(0).value = woJobName;
				object.children(len).children(6).children(0).value = snos.substring(0,snos.length-2);

				object.children(len).children(7).children(0).value = temp.proShift.value; // Shift Id
				object.children(len).children(8).children(0).value = temp.proWorkOrderHash.value; // Work Order Id
				object.children(len).children(9).children(0).value = woJobId; // Job Id
				object.children(len).children(10).children(0).value = opnQty; // Despatch Qty				
			}
		}
		else
		{
			alert ("Check one or more Job Quantities to Proceed!");
			return false;
		}
		
		// Clear all the Values
		/*temp.proDate.value = "";
		temp.hidWrkOrdId.value = "0";
		temp.proWorkOrderHash.value = "0";
		temp.hidWrkOrdJobId.value = "0";
		
		jobQty.style.display = 'none';
		prodInfo.style.display = 'block';
		prodShftRow.style.display = 'none';
		wrkOrder.style.display = 'none';
		jobQty.style.display = 'none';
		
		
		temp.hidModDet.value = "";
		temp.formAction.value = "post";
		temp.showCount.value = "";
		loadProdList();
		temp.submit();*/
		submitItem();
	}

	/* Check all Buffer Table */
	function chkAllTblBffer()
	{
		var temp = document.forms[0];
		if (temp.chkAllProdBufHead.checked)
		{
			for (var i = 0; i < temp.chkProdList.length; i++)
				temp.chkProdList[i].checked = true;
			if (temp.chkProdList.length == undefined)
				temp.chkProdList.checked = true;
		}
		else
		{
			for (var i = 0; i < temp.chkProdList.length; i++)
				temp.chkProdList[i].checked = false;
			if (temp.chkProdList.length == undefined)
				temp.chkProdList.checked = false;
		}
	}

	/* Modify the Details */	
	function modifyRow()
	{
		var temp = document.forms[0];
		var obj = document.getElementById('tblBufferList').children(0);
		var count = 0;
		for (var i = 0; i < obj.children.length; i++)
		{
			if (obj.children(i).children(0).children(0).checked)
				count++;
		}
		if (count > 1)
		{
			alert ("Select One Item to Modify!");
			return false;
		}
		else if (count == 0)
		{
			alert ("Select a Item to Modify!");
			return false;
		}

		temp.hidModDet.value = "";
		for (var i = 0; i < obj.children.length; i++)
		{
			if (obj.children(i).children(0).children(0).checked)
			{
				temp.hidModDet.value = 
					obj.children(i).children(1).children(0).value + "#" +
					obj.children(i).children(2).children(0).value + "#" +
					obj.children(i).children(3).children(0).value + "#" +
					obj.children(i).children(4).children(0).value + "#" +
					obj.children(i).children(5).children(0).value + "#" +
					obj.children(i).children(6).children(0).value + "#" +
					obj.children(i).children(7).children(0).value + "#" +
					obj.children(i).children(8).children(0).value + "#" +
					obj.children(i).children(9).children(0).value + "#" +
					obj.children(i).children(10).children(0).value;

				temp.hidWrkOrdId.value = obj.children(i).children(8).children(0).value;
				temp.hidWrkOrdJobId.value = obj.children(i).children(9).children(0).value;
			}
		}
		//alert (temp.hidModDet.value);
		temp.formAction.value = "modify";
		delBufferRow();
		temp.showCount.value = "5";
		loadProdList();
		temp.submit();
	}
	
	/* Delete the Selected Rows in Buffer */
	function delBufferRow()
	{
		var temp = document.forms[0];
		var obj = document.getElementById('tblBufferList').children(0);
		var count = 0;
		for (var i = 0; i < obj.children.length; i++)
		{
			if (obj.children(i).children(0).children(0).checked)
				count++;
		}
		if (count == 0)
		{
			alert ("Select One or More Despatch Entries to Delete!");
			return false;
		}

		if (obj.children.length > 1)
		{
			for (var i = 0 ; i < obj.children.length; i++)
			{
				if (obj.children(i).children(0).children(0).checked)
				{
					obj.removeChild(obj.children(i));
					i = 0;
				}
			}
			if (obj.children.length == 1)
			{
				if (obj.children(0).children(0).children(0).checked)
				{
					obj.children(0).children(1).children(0).value = "";
					obj.children(0).children(2).children(0).value = "";
					obj.children(0).children(3).children(0).value = "";
					obj.children(0).children(4).children(0).value = "";
					obj.children(0).children(5).children(0).value = "";
					obj.children(0).children(6).children(0).value = "";
					obj.children(0).children(7).children(0).value = "";
					obj.children(0).children(8).children(0).value = "";
					obj.children(0).children(9).children(0).value = "";
					obj.children(0).children(10).children(0).value = "";
					obj.children(0).children(0).children(0).checked = false;
				}
			}
		}
		else if (obj.children(0).children(0).children(0).checked)
		{
					obj.children(0).children(1).children(0).value = "";
					obj.children(0).children(2).children(0).value = "";
					obj.children(0).children(3).children(0).value = "";
					obj.children(0).children(4).children(0).value = "";
					obj.children(0).children(5).children(0).value = "";
					obj.children(0).children(6).children(0).value = "";
					obj.children(0).children(7).children(0).value = "";
					obj.children(0).children(8).children(0).value = "";
					obj.children(0).children(9).children(0).value = "";
					obj.children(0).children(10).children(0).value = "";
					obj.children(0).children(0).children(0).checked = false;
		}
		for (var i = 0; i < obj.children.length; i++)
			obj.children(i).children(1).children(0).value = i+1;
		temp.chkAllProdBufHead.checked = false;
		loadProdList();
	}

	function submitItem()
	{
		loadProdList();
		document.forms[0].formAction.value = "update";
		document.forms[0].submit();
	}

</script>
</head>

<body onload="init(); showJobDet();"><!--  -->
<html:form action="frmdespatchClearanceEdit">
<html:hidden property="formAction"/>
<html:hidden property="id"/>
<html:hidden property="ids"/>
<html:hidden property="hidWrkOrdId" value="<%= frm.getHidWrkOrdId() %>"/><!-- Handling the WorkOrder Id for Job Details -->
<html:hidden property="hidWrkOrdJobId" value="<%= frm.getHidWrkOrdJobId() %>"/><!-- Handling the WorkOrder Job Id for Job Operation Details -->
<html:hidden property="showCount" /> <!-- value="< %= frm.getShowCount() %>" Showing the appropriate Table by Count -->
<html:hidden property="hidChkdJobName" value="<%= frm.getHidChkdJobName() %>"/><!-- Holding the Selected Job name -->
<html:hidden property="hidChkdJobId" value="<%= frm.getHidChkdJobId() %>"/> <!-- Holding the Selected Job Id Index -->
<html:hidden property="hidUserId"/> <!-- Holding the User Name -->
<html:hidden property="hidProdList"/> <!-- Holding the Production List Details -->

<table width="100%"  border="0" cellspacing="0" cellpadding="10">
<tr>
<td>
	<table width="100%"  border="0" cellpadding="0" cellspacing="0" id="PageTitle">
    <tr>
    	<td>Despatch Clearance</td>
	</tr>
    </table>
	<br>
  	<table width="100" cellspacing="0" cellpadding="0" align="right">
	<tr>
		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New Production Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1049" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='List Production Info'; return true"  onMouseOut="window.status=''; return true" resourceId="49" text="[ List ]" classId="TopLnk" onClick="javaScript:listItem();"/>
	</tr>
  	</table>
	<table>
	<tr>
		<td colspan='2'> <font size="1px" face="Verdana"><html:errors/></font></td>
	</tr>
	</table>
    <br>
    <table width="100%" cellspacing="0" cellpadding="0">
    <tr id="prodInfo"><!-- Date & Shift -->
		<td valign="top" colspan="2">
			<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td width="100" class="FieldTitle"><bean:message key="prodacs.job.date"/><span class="mandatory">*</span></td>
				<td width="1" class="FieldTitle">:</td>
				<td width="200" class="FieldTitle"><html:text property="proDate" styleClass="TextBox" size="12" readonly="true"/>
							<img src='<bean:message key="context"/>/images/calendar.gif' width="16" height="15" align="absmiddle" onClick='show_calendar("proDate",DespatchClearanceAdd.proDate.value,event.clientX,event.clientY,false);' onMouseOver="this.style.cursor='hand'"></td>
				<td width="100" class="FieldTitle"><bean:message key="prodacs.production.shift"/><span class="mandatory">*</span></td>
				<td width="1" class="FieldTitle">:</td>
				<td colspan="3" class="FieldTitle"><html:select property="proShift" styleClass="Combo">
										  <html:option value="0">-- Select Shift --</html:option>
										  <html:options collection="dateShift" property="key" labelProperty="value"/>
									   </html:select>
				</td>
			</tr>
			</table>
			<br>
			<table width="100%" cellspacing="0" cellpadding="0" id="BtnBgLft">
			<tr>
				<td><html:button styleClass="Button" property="proNextBtn1" onclick="hideNshowDateShift(); ">Next</html:button></td>
			</tr>
			</table>
		</td>
    </tr> <!-- End of Date and Shift -->
	<tr id="prodShftRow" style="display:none"> <!-- Menu for WorkOrder table -->
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
			<table width="100%"  border="0" cellspacing="0" cellpadding="0" id="tblWorkOrdInfo">
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
				<td class="TableItems"><input name="rdWrkOrd" type="radio"  value="<bean:write name='bt1' property='woJbId'/>"></td>
				<td class="TableItems" title="<bean:write name='bt1' property='jobName'/>/<bean:write name='bt1' property='dwgNo'/>/<bean:write name='bt1' property='rvsnNo'/>/<bean:write name='bt1' property='matlType'/>"><bean:write name="bt1" property="jobName"/>&nbsp;</td>
				<td class="TableItems"><bean:write name="bt1" property="totQty" />&nbsp;</td>
				<td class="TableItems"><bean:write name="bt1" property="pendingQtySnos" />&nbsp;</td>
				<td class="TableItems"><bean:write name="bt1" property="postedQtySnos" />&nbsp;</td>
				<td class="TableItems"><bean:write name="bt1" property="unPostedQtySnos" />&nbsp;</td>
				<td class="TableItems"><logic:notEmpty name="bt1" property="lastProdnDate">
					<bean:define id="date3" name="bt1" property="lastProdnDate"/>
					<%= date3.toString().substring(0,10) %></logic:notEmpty>&nbsp;
				</td>
			</tr>
			</logic:iterate>
			</table>
		<br>
			<table width="100%" cellpadding="0" cellspacing="0" id="BtnBgLft">
			<tr>
				<td><html:button styleClass="Button" property="proNextBtn2" onclick="hideNshowJobOpnDet();">Next</html:button></td>
			</tr>
			</table>
    </td>
   	<td width="20" valign="top">
	<table width="100%" cellpadding="0" cellspacing="0" id="ShowHide">
	<tr>
		<td onClick="showHide(document.getElementById('prodInfo'), document.getElementById('prodShftImg'))"><img src='<bean:message key="context"/>/images/hide.gif' id="prodShftImg"></td>
	</tr>
	</table>
	</td>
    </tr>
    <tr id="wrkOrder" style="display:none">
    	<td style="border-top:dashed 1px #666666">
		<table width="100%"  border="0" cellspacing="0" cellpadding="0">
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
    <table width="100%" cellspacing="0" cellpadding="0" id="tblJobOperDet">
    <tr>
    	<td width="23" class="SortHeader"><input type="checkbox" name="checkbox2" value="checkbox" onclick="chkAll(this);"></td>
        <td class="SortHeader"><bean:message key="prodacs.workorder.jobqtysno"/></td>
        <td class="SortHeader">Despatch Pending Opn SNo</td>
	</tr>
	<logic:iterate id="bt2" name="vecJobOpnDet" indexId="count">
    <tr>
    	<td class="TableItems"><input type="checkbox" name="CheckValue" value='<bean:write name="bt2" property="jobStatId"/>'></td>
        <td class="TableItems"><bean:write name="bt2" property="jobQtySno"/></td>
        <td class="TableItems"><input type="text" class="TextBoxFull" readonly name="pendOpnSno" value='<bean:write name="bt2" property="pendingOpnSnos"/>'></td>
	</tr>
	</logic:iterate>
    </table>
    <br>
    <table width="100%" cellpadding="0" cellspacing="0" id="BtnBgLft">
    <tr>
    	<td><html:button property="proNextBtn3" styleClass="Button" onclick="hideNshowBufferDetails();">Add</html:button></td>
	</tr>
    </table>
    </td>
	<td valign="top">
		<table width="100%" cellpadding="0" cellspacing="0" id="ShowHide">
		<tr>
			<td onClick="showHide(document.getElementById('prodShftRow'), document.getElementById('prodShftRowImg'))"><img src='<bean:message key="context"/>/images/hide.gif' width="9" height="7" id="prodShftRowImg"></td>
		</tr>
		</table>
	</td>
	</tr>
	<tr id="jobQty" style="display:none">
   	<td style="border-top:dashed 1px #666666">
		<!-- Buffering the Production Values -->
		<table width="100%" cellpadding="0" cellspacing="0" id="tblBuffer">
		<tr>
		<td>
			<fieldset id="FieldSet"><legend class="FieldTitle"> Current Despatched Entries </legend>
				<table width="100%" cellspacing="0" cellpadding="5">
				<tr>
					<td class="TopLnk">
					[ <a href="#" onClick="modifyRow(document.forms[0]);"><bean:message key="prodacs.common.modify"/></a> ] [ <a href="#" onClick="delBufferRow(document.forms[0]);"><bean:message key="prodacs.common.delete"/></a> ]</td>
				</tr>
				</table>

				<table width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td width="25" class="Header"><input name="chkAllProdBufHead" type="checkbox" id="CheckAllDymSub" onClick="chkAllTblBffer();"/></td>
						<td width="30" class="Header"><bean:message key="prodacs.common.sno"/></td>
						<td width="80" class="Header"><bean:message key="prodacs.job.date"/></td>
						<td width="80" class="Header"><bean:message key="prodacs.production.shift"/></td>
						<td width="80" class="Header"><bean:message key="prodacs.workorder.workorderno"/></td>
						<td class="Header"><bean:message key="prodacs.job.jobname"/></td>
						<td width="105" class="Header">Des. Qty S.Nos</td>
					</tr>
				</table>

				<table width="100%" cellspacing="0" cellpadding="0" id="tblBufferList">
					<tr>
					  <td width="22" class="TableItems"><input type="checkbox" value="" name="chkProdList"/></td>
					  <td width="27" class="TableItems"><input readonly name="prodSno" class="TextBoxFull" /></td>
					  <td width="77" class="TableItems"><input readonly name="prodDate" class="TextBoxFull" size="10"/></td>
					  <td width="77" class="TableItems"><input readonly name="prodShift" class="TextBoxFull" /></td>
					  <td width="77" class="TableItems"><input readonly name="prodWoNo" class="TextBoxFull" /></td>
					  <td class="TableItems"><input readonly name="prodJobName" class="TextBoxFull" /></td>
					  <td width="102" class="TableItems"><input readonly name="despatchQtySno" class="TextBoxFull" /></td>
					  <td><input type="hidden" name="prodShiftId"/></td>
					  <td><input type="hidden" name="prodWoId"/></td>
					  <td><input type="hidden" name="prodJobId"/></td>
					  <td><input type="hidden" name="desQtySno"/></td>
					</tr>
				</table>
				<table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
				<tr>
				  <td><html:button property="addAll" styleClass="Button" onclick="javaScript:submitItem();">Add All</html:button></td>
				</tr>
				</table>

			</fieldset>
		</td>
		</tr>
		</table>
		<!-- End of Buffering Tables -->
	</td>
	</tr>
	</table>
	<input type="hidden" name="hidModDet"/>
</td>
</tr>
</table>

</html:form>
</body>
</html:html>
