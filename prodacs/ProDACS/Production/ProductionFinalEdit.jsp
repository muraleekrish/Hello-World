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
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.StringTokenizer"%>
<%@ page import="java.util.GregorianCalendar"%>
<%@ page import="com.savantit.prodacs.facade.SessionFinalProductionDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionFinalProductionDetailsManager"%>
<%@ page import="com.savantit.prodacs.businessimplementation.securityadmin.UserAuthDetails"%>
<%@ page import="com.savantit.prodacs.businessimplementation.production.FinalProductionDetails"%>
<%@ page import="com.savantit.prodacs.businessimplementation.production.ProductionJobDetails"%>
<%@ page import="com.savantit.prodacs.businessimplementation.production.ProductionJobQtyDetails"%>
<%@ page import="com.savantit.prodacs.businessimplementation.production.EmployeeDtyOtDetails"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<jsp:useBean id="frm" class="com.savantit.prodacs.presentation.tableadmin.production.ProductionFinalEditForm" />
<jsp:setProperty name="frm" property="*" />

<useradmin:userrights resource="1049"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("Production Final Edit.jsp Starts");
	HashMap empDetails = new HashMap();
	FinalProductionDetails objFinalProductionDetails = new FinalProductionDetails();
	ProductionJobDetails objProductionJobDetails = new ProductionJobDetails();
	ProductionJobQtyDetails objProdJobQtyDet = new ProductionJobQtyDetails();

	UserAuthDetails userDetails = (UserAuthDetails)session.getAttribute("##userRights##");
	String userName = userDetails.getUserId();

	try
	{
		EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator

		/* 	Setting the JNDI name and Environment 	*/
	   	obj.setJndiName("SessionFinalProductionDetailsManager");
   		obj.setEnvironment();

		/* 	Creating the Home and Remote Objects 	*/
		SessionFinalProductionDetailsManagerHome objFinalProdHome = (SessionFinalProductionDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionFinalProductionDetailsManagerHome.class);
		SessionFinalProductionDetailsManager objProdFinal = (SessionFinalProductionDetailsManager)PortableRemoteObject.narrow(objFinalProdHome.create(),SessionFinalProductionDetailsManager.class);

		objFinalProductionDetails = objProdFinal.getFinalProductionDetails(frm.getId());
		if (frm.getFormAction().equalsIgnoreCase("modify"))
		{
			frm.setHidWrkOrdId(objFinalProductionDetails.getWoId()+"");
			frm.setHidWrkOrdJobId(objFinalProductionDetails.getWoJbId()+"");
		}
		
		/* For loading Shifts */
		HashMap shiftDefnNameList = new HashMap();
		shiftDefnNameList = objProdFinal.getShiftDefnNameList();
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
			
			shiftDefnNameList = objProdFinal.getShiftDefnNameList(gc.getTime());
			if (BuildConfig.DMODE)
				System.out.println("Date Shift (HM) :"+shiftDefnNameList);
			pageContext.setAttribute("shiftDefnNameList",shiftDefnNameList);
		}
		else
		{
			pageContext.setAttribute("shiftDefnNameList",shiftDefnNameList);
		}
		
		/* For Loading WorkOrders */
		HashMap workOrderDet = objProdFinal.getWorkOrderList();
		pageContext.setAttribute("workOrderDet",workOrderDet);
		
		/* Loading Job Details by giving WorkOrder Id */
		Vector vecJobDet = new Vector();
		if (BuildConfig.DMODE)
			System.out.println("Wo Id: "+frm.getHidWrkOrdId());
		if (frm.getHidWrkOrdId() != "0")
		{
			vecJobDet = objProdFinal.getProdnJobDetailsByWorkOrder(Integer.parseInt(frm.getHidWrkOrdId()));
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
			vecJobOpnDet = objProdFinal.getFinalProdnJobOperationDetailsForUpdate(Integer.parseInt(frm.getHidWrkOrdJobId()),frm.getId());
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
		
		/* Loading EmployeeType and Employees */
		empDetails = objProdFinal.getAllEmployeesByTypes();

		/* Loading JobDetails for Checked JobName */
	 	objProductionJobDetails = (ProductionJobDetails)objFinalProductionDetails.getObjProductionJobDetails();
		
		/* For loading Employee Details */
			Vector vec_empDet = objFinalProductionDetails.getProdnEmpHrsDetails();
			String emp_details = "";
			if (vec_empDet.size() != 0)
			{
				emp_details = "";
				for (int b = 0; b < vec_empDet.size(); b++)
				{
					EmployeeDtyOtDetails objEmpDtyOtDet = new EmployeeDtyOtDetails();
					objEmpDtyOtDet = (EmployeeDtyOtDetails) vec_empDet.get(b);
					
					if (b != 0)
					{
						emp_details = emp_details + "^";
					}
					emp_details = emp_details + "" + objEmpDtyOtDet.getEmpType();
					emp_details = emp_details + "-" + objEmpDtyOtDet.getEmpName();
					emp_details = emp_details + "-" + objEmpDtyOtDet.getEmpTypdId();
					emp_details = emp_details + "-" + objEmpDtyOtDet.getEmpId();
				}
				frm.setHidAllEmpDet(emp_details);
			}

			if (BuildConfig.DMODE)
				System.out.println("empDetails: "+emp_details);
	}
	catch (Exception e)
	{
		if (BuildConfig.DMODE)
		{
			System.out.println("Problem in Production Final Edit.jsp");
			e.printStackTrace();
		}
	}
%>
<html:html>
<head>
<title><bean:message key="prodacs.productionfinal.header"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="<bean:message key="context"/>/styles/pmac.css" rel="stylesheet" type="text/css">
<script src='<bean:message key="context"/>/library/jscripts.js'></script>
<script src='<bean:message key="context"/>/library/calendar_previous_submit.js'></script>
<script>
var oPopup;
	function init()
	 {
		oPopup = window.createPopup();
	 }

	 function dateSubmit()
	 {
		 var temp = document.forms[0];
		 temp.formAction.value = "dateSubmit";
		 temp.submit();
	 }
	
	function loadToHidden()
	{
		temp = document.forms[0];
		temp.ids.value = temp.id.value;
	}
	 
	function addItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/Production/ProductionFinalAdd.jsp';
		document.forms[0].submit();
	}

	function listItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/Production/ProductionFinal.jsp';
		document.forms[0].submit();
	}

</script>

<!-- All Script for Production Edit -->
<script>
	var flg = 0;
	var temFlg = true;

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

		/*if (temp.showCount.value == "")
		{
			prodInfo.style.display='block';
			prodShftRow.style.display='none';
		}
		else */if (temp.showCount.value == "1")
		{
			if (temp.proShift.options[temp.proShift.selectedIndex].value != "0")
			{
				prodShftRow.style.display='block';
			}
			else
			{
				prodShftRow.style.display='none';
			}

			/* Validation for updating the Date and Shift */
			var obj = document.getElementById("tblProdShft");
			obj.children(2).innerText = temp.proDate.value;
			obj.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;
			if (temp.rdWrkOrd == undefined)
				return false;
			if (temp.rdWrkOrd.length != undefined)
			{
				temp.rdWrkOrd[temp.hidChkdJobId.value].checked = true;
			}
			else
			{
				temp.rdWrkOrd.checked = true;
			}
		}
		else if (temp.showCount.value == "2")
		{
			prodShftRow.style.display='none';
			wrkOrder.style.display='block';
			
			/* Validation for updating Date, Shift, and WorkOrderNo and Job name */
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
			{
				temp.rdWrkOrd[temp.hidChkdJobId.value].checked = true;
			}
			else
			{
				temp.rdWrkOrd.checked = true;
			}
		}
		else if (temp.showCount.value == "3")
		{
			jobQty.style.display = 'block';
						
			/* 1 st Row */
			var obj = document.getElementById("tblProdShft"); 
			obj.children(2).innerText = temp.proDate.value;
			obj.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;

			/* 2 nd Row */
			var obj = document.getElementById("tblProdShft2"); 
			obj.children(2).innerText = temp.proDate.value;
			obj.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;

			var obj2 = document.getElementById("tblProdWrkOrd");
			obj2.children(2).innerText = temp.proWorkOrderHash.options[temp.proWorkOrderHash.selectedIndex].text;
			obj2.children(5).innerText = temp.hidChkdJobName.value;

			/* 3 d Row */
			var obj1 = document.getElementById("tblProdWrkOrd2");
			obj1.children(2).innerText = temp.proWorkOrderHash.options[temp.proWorkOrderHash.selectedIndex].text;
			obj1.children(5).innerText = temp.hidChkdJobName.value;

			var obj2 = document.getElementById("tblProdJobOty");
			obj2.children(2).innerText = temp.proTotQtySnos.value;
			obj2.children(5).innerText = temp.proStartOperation.value;
			obj2.children(8).innerText = temp.proEndOperation.value;
			
			var chkdQty = (temp.proTotQtySnos.value).split(",");
			var obj = document.getElementById("tblJobOperDet");
			for (var i = 1; i < obj.children(0).children.length; i++)
			{
				for (var j = 0; j < chkdQty.length; j++)
				{
					if (obj.children(0).children(i).children(1).children(0).value == chkdQty[j])
					{
						obj.children(0).children(i).children(0).children(0).checked = true;
					}
				}
			}
			/* Select the Checked Radio Button */
			if (temp.rdWrkOrd.length != undefined)
			{
				temp.rdWrkOrd[temp.hidChkdJobId.value].checked = true;
			}
			else
			{
				temp.rdWrkOrd.checked = true;
			}
		}
	}

	/* This is for showing the Date and Shift indicate the invalid Things */
	function hideNshowDateShift()
	{
		var temp = document.forms[0];
		var obj = document.getElementById("tblProdShft");

		/* For Displaying the Production Date else indimate a Alert */
		if (temp.proDate.value != "")
		{
			obj.children(2).innerText = temp.proDate.value;
		}
		else
		{
			alert ("Please Select a Production Date!");
			prodShftRow.style.display='none'; 
			prodInfo.style.display='block';
			return false;
		}

		/* For Displaying the Shift Name else indimate a Alert */
		if (temp.proShift.options[temp.proShift.selectedIndex].value != "0")
		{
			obj.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;
		}
		else
		{
			alert ("Please Select a Shift Name for Production!");
			prodShftRow.style.display='none'; 
			prodInfo.style.display='block';
			return false;
		}
		temp.showCount.value = "1";
		temp.formAction.value = "post";
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
		var obj = document.getElementById("tblWorkOrdInfo");
		var temp = document.forms[0];
		var flag = 0;

		if (obj.children(0).children.length == 1) /* No Jobs for that WorkOrder */
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
							temp.hidChkdJobName.value = obj.children(0).children(i+1).children(1).children(0).value;
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
				temp.hidChkdJobName.value = obj.children(0).children(1).children(1).children(0).value;
				temp.hidChkdJobId.value = 0;
			}
		}
		
		if (flag != 1) /* Not even a single job is selected */
		{
			alert ("U must Select a Job!");
			return false;
		}
		temp.showCount.value = "2"; /* Increment the count value */
		temp.formAction.value = "load";
		temp.submit();
	}

	/* Hiding the JobDetails, JobOperation Details and show the Employee Details */
	function hideNshowEmpDetails(flag)
	{
		var temp = document.forms[0];

		if (temp.proStartOperation.value == "") /* Start Operation Cannot be Null */
		{
			alert ("Production Start Operation Cannot be Null!");
			return false;
		}
		if (temp.proEndOperation.value == "") /* End Operation cannot be Null */
		{
			alert ("Production End Operation Cannot be Null!");
			return false;
		}

		var start = parseInt(temp.proStartOperation.value);
		var end = parseInt(temp.proEndOperation.value);
		var srtString = "";
		if (start > end)
		{
			alert ("Start Operation can't be morethan End Operation!");
			temp.proStartOperation.value = "";
			temp.proEndOperation.value = "";
			return false;
		}

		if (start == end)
		{
			srtString = start;
		}
		else
		{
			for (var i = start; i <= end; i++)
			{
				if (i == start)
				{
					srtString = start;
				}
				else
				{
					srtString = srtString + "," + i;
				}
			}
		}
		
		var obj = document.getElementById("tblJobOperDet"); /* Picking that JobOperationDetails Table */
		var count = 0;
		temp.proTotQtySnos.value = "";
		temp.hidWrkOrdJbStIds.value = "";
		var i = 1;
		var k = 0;
		var totQty = "";
		var woJbStatIds = "";
		while (i < obj.children(0).children.length)
		{
			if (obj.children(0).children(i).children(0).children(0).checked)
			{
				if ((obj.children(0).children(i).children(2).children(0).value.search(srtString)) == -1)
				{
					alert ("Check Start/End Operations!");
					return false;
				}
				count++;
				if (k == 0)
				{
					totQty = obj.children(0).children(i).children(1).children(0).value;
					woJbStatIds = obj.children(0).children(i).children(0).children(0).value;
				}
				else
				{
					totQty = totQty + "," + obj.children(0).children(i).children(1).children(0).value;
					woJbStatIds = woJbStatIds + "-" + obj.children(0).children(i).children(0).children(0).value;
				}
				k++;
			}
			i++;
		}
		temp.proTotQtySnos.value = totQty;

		if (count == 0)/* Verify if minimum one is checked */
		{
			alert ("Check One or more Job Quantities to Proceed!");
			return false;
		}
		flg = 1;
		temFlg = true;
		temp.showCount.value = "3";
		temp.submit();
	}

	function transEmpDet(val)
	{
		var temp = document.forms[0];
		var ind = parseInt(val);
		var obj = document.getElementById("tblEmpDetails");
		var obj1 = document.getElementById("tblEmpDetMstr");

		/* Validations before shifting Employee Details */
		if (temp.proEmployeeName[ind-1].options[temp.proEmployeeName[ind-1].selectedIndex].value == "0")
		{
			alert ("Select Employee Name to Proceed!");
			return false;
		}

		if ((obj.children.length == "1") && (obj.children(0).children(0).children(1).children(0).value == ""))
		{
			/* Shifting the Employee Details from Master */
			obj.children(0).children(0).children(1).children(0).value = obj1.children(0).children(ind).children(0).children(0).value;
			obj.children(0).children(0).children(2).children(0).value = temp.proEmployeeName[ind-1].options[temp.proEmployeeName[ind-1].selectedIndex].text;
			obj.children(0).children(0).children(3).children(0).value = obj1.children(0).children(ind).children(3).children(0).value;
			obj.children(0).children(0).children(4).children(0).value = temp.proEmployeeName[ind-1].options[temp.proEmployeeName[ind-1].selectedIndex].value;

			/* Reseting the Fields */
			temp.proEmployeeName[ind-1].selectedIndex = "0";
		}
		else
		{
			/* Validate whether this is already added or not */
			for (var i = 0; i < obj.children.length; i++)
			{
				if (obj.children(i).children(0).children(4).children(0).value == temp.proEmployeeName[ind-1].options[temp.proEmployeeName[ind-1].selectedIndex].value)
				{
					var name = temp.proEmployeeName[ind-1].options[temp.proEmployeeName[ind-1].selectedIndex].text;
					alert ("Sorry! The Employee "+name+"'s details already Added!");

					/* Reseting the Fields */
					temp.proEmployeeName[ind-1].selectedIndex = "0";

					return false;
				}
			}

			/* Incrementing a Row */
			var newNode = obj.children(0).cloneNode(true);
			obj.appendChild(newNode);
			var len = obj.children.length;

			/* Reset the Previous values */
			obj.children(len-1).children(0).children(1).children(0).value = "";
			obj.children(len-1).children(0).children(2).children(0).value = "";
			obj.children(len-1).children(0).children(3).children(0).value = "";
			obj.children(len-1).children(0).children(4).children(0).value = "";

			/* Updating the Employee Details from master */
			obj.children(len-1).children(0).children(1).children(0).value = obj1.children(0).children(ind).children(0).children(0).value;
			obj.children(len-1).children(0).children(2).children(0).value = temp.proEmployeeName[ind-1].options[temp.proEmployeeName[ind-1].selectedIndex].text;
			obj.children(len-1).children(0).children(3).children(0).value = obj1.children(0).children(ind).children(3).children(0).value;
			obj.children(len-1).children(0).children(4).children(0).value = temp.proEmployeeName[ind-1].options[temp.proEmployeeName[ind-1].selectedIndex].value;

			/* Reseting the Fields */
			temp.proEmployeeName[ind-1].selectedIndex = "0";
		}
	}

	function delRow()
	{
		var temp = document.forms[0];
		var obj = document.getElementById("tblEmpDetails");
		if (obj.children.length > 1)
		{
			for (var i = 0 ; i < obj.children.length; i++)
			{
				if (temp.ckdCkBx[i].checked)
				{
					obj.removeChild(obj.children(i));
					i = 0;
				}
			}
			if (obj.children.length == 1)
			{
				if (temp.ckdCkBx[0].checked)
				{
					obj.children(0).children(0).children(1).children(0).value = "";
					obj.children(0).children(0).children(2).children(0).value = "";
					obj.children(0).children(0).children(3).children(0).value = "";
					obj.children(0).children(0).children(4).children(0).value = "";
					temp.ckdCkBx[0].checked = false;
					temp.ckAll.checked = false;
				}
			}

		}
		else if (obj.children.length == 1)
		{
				obj.children(0).children(0).children(1).children(0).value = "";
				obj.children(0).children(0).children(2).children(0).value = "";
				obj.children(0).children(0).children(3).children(0).value = "";
				obj.children(0).children(0).children(4).children(0).value = "";

				temp.ckdCkBx.checked = false;
				temp.ckAll.checked = false;
		}
	}

	function headCkAll()
	{
		var temp = document.forms[0];
		var obj = document.getElementById("tblEmpDetails");
		if (obj.children.length > 1)
		{
			if (temp.ckAll.checked)
			{
				for (var i = 0; i < temp.ckdCkBx.length; i++)
				{
					temp.ckdCkBx[i].checked = true;
				}
			}
			else
			{
				for (var i = 0; i < temp.ckdCkBx.length; i++)
				{
					temp.ckdCkBx[i].checked = false;
				}
			}
		}
		else if (temp.ckAll.checked)
		{
			temp.ckdCkBx.checked = true;
		}
		else
		{
			temp.ckdCkBx.checked = false;
		}
	}
	
	function modOperation()
	{
		var temp = document.forms[0];
		if (temp.formAction.value == "modify")
		{
			var totalQty = '<%= objFinalProductionDetails.getFinalProdQtySnos() %>'; /* Splitting the Total Qty's */
			var arTotalQty = totalQty.split(",");

			var obj = document.getElementById("tblJobOperDet");
			for (var i = 1; i < obj.children(0).children.length; i++)
			{
				for (var j = 0; j < arTotalQty.length; j++)
				{
					if (obj.children(0).children(i).children(1).children(0).value == arTotalQty[j])
						obj.children(0).children(i).children(0).children(0).checked = true;
				}
			}
			
			var chkdWrkOrdJobId = '<%= objFinalProductionDetails.getWoJbId() %>'; /* To check the Selected Job Name */
			var obj = document.getElementById("tblWorkOrdInfo");
			for(i=1; i<obj.children(0).children.length;i++)
			{
				if(chkdWrkOrdJobId == obj.children(0).children(i).children(0).children(0).value)
				{
					obj.children(0).children(i).children(0).children(0).checked = true;
				}
			}

			jobQty.style.display='block'; 

			/* Show all the table's Which are associated with Production */
			prodInfo.style.display='block';			
			prodShftRow.style.display='block';

			/* Validation for updating the Date, Shift and Machine Names */
			var obj = document.getElementById("tblProdShft");
			obj.children(2).innerText = temp.proDate.value;
			obj.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;

			wrkOrder.style.display='block';
			
			/* Validation for updating Date, Shift, Machine Name, WorkOrderNo and Job name */
			var obj = document.getElementById("tblProdShft");
			obj.children(2).innerText = temp.proDate.value;
			obj.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;

			var obj1 = document.getElementById("tblProdShft2");
			obj1.children(2).innerText = temp.proDate.value;
			obj1.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;
			
			var obj2 = document.getElementById("tblProdWrkOrd");
			obj2.children(2).innerText = temp.proWorkOrderHash.options[temp.proWorkOrderHash.selectedIndex].text;
			obj2.children(5).innerText = '<%= objProductionJobDetails.getJobName() %>';

			var obj = document.getElementById("tblProdShft3");
			obj.children(2).innerText = temp.proDate.value;
			obj.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;

			var obj1 = document.getElementById("tblProdWrkOrd2");
			obj1.children(2).innerText = temp.proWorkOrderHash.options[temp.proWorkOrderHash.selectedIndex].text;
			obj1.children(5).innerText = '<%= objProductionJobDetails.getJobName() %>';

			var obj2 = document.getElementById("tblProdJobOty");
			obj2.children(2).innerText = '<%= objFinalProductionDetails.getFinalProdQtySnos() %>';
			obj2.children(5).innerText = temp.proStartOperation.value;
			obj2.children(8).innerText = temp.proEndOperation.value;
		}
		else
		{
			return false;
		}
	}
	
	/* This Fn. is called by Body Onload. This executes only when the modification is performed. */
	function modEmpDetails()
	{
		var temp = document.forms[0];
		if (temp.formAction.value != "modify") /* If not Modification, Exit*/
			return false;
		
		var obj = document.getElementById("tblEmpDetails");
		if (temp.hidAllEmpDet.value != "")
		{
			var arEmpDet = (temp.hidAllEmpDet.value).split("^");
			for (var i = 0; i < arEmpDet.length; i++)
			{
				var arEmpPrnlDet = arEmpDet[i].split("-");
				if ((obj.children.length == 1) && (obj.children(0).children(0).children(1).children(0).value == ""))
				{
					obj.children(0).children(0).children(1).children(0).value = arEmpPrnlDet[0];
					obj.children(0).children(0).children(2).children(0).value = arEmpPrnlDet[1];
					obj.children(0).children(0).children(3).children(0).value = arEmpPrnlDet[2];
					obj.children(0).children(0).children(4).children(0).value = arEmpPrnlDet[3];
				}
				else
				{
					/* Incrementing a Row */
					var newNode = obj.children(0).cloneNode(true);
					obj.appendChild(newNode);
					var len = obj.children.length;

					obj.children(len-1).children(0).children(1).children(0).value = arEmpPrnlDet[0];
					obj.children(len-1).children(0).children(2).children(0).value = arEmpPrnlDet[1];
					obj.children(len-1).children(0).children(3).children(0).value = arEmpPrnlDet[2];
					obj.children(len-1).children(0).children(4).children(0).value = arEmpPrnlDet[3];
				}
			}
			temp.hidAllEmpDet.value = "";
		}
	}

	function enterProDet()
	{
		var temp = document.forms[0];
		temFlg = false;
		flg = 0;
		var obj = document.getElementById("tblEmpDetails");

		/* Remove the Empty values in Employee Details 
		if ((obj.children(0).children(0).children(1).children(0).value == "") &&
			(obj.children(0).children(0).children(2).children(0).value == "") &&
			(obj.children(0).children(0).children(3).children(0).value == ""))
		{
			alert ("Employee Details can't be Empty!");
			return false;
		}*/
		if (temp.proTotalHours.value == "")
		{
			alert("Total Hours cannot be left empty!");
			temp.proTotalHours.focus();
			return false;
		}

		var empDet = "";
		var i = 0;
		while (i < obj.children.length)
		{
			if (i != 0)
			{
				empDet = empDet + "^";
			}
			if (i == 0)
			{
				empDet = obj.children(0).children(0).children(1).children(0).value; // Emp Typ name 
				empDet = empDet + "-" + obj.children(0).children(0).children(3).children(0).value; // Emp Typ Id 
				empDet = empDet + "-" + obj.children(0).children(0).children(2).children(0).value; // Emp Name 
				empDet = empDet + "-" + obj.children(0).children(0).children(4).children(0).value; // Emp Name Id 
			}
			else
			{
				empDet = empDet + "" + obj.children(i).children(0).children(1).children(0).value; // EmpTyp name 
				empDet = empDet + "-" + obj.children(i).children(0).children(3).children(0).value; // Emp Typ Id 
				empDet = empDet + "-" + obj.children(i).children(0).children(2).children(0).value; // Emp Name 
				empDet = empDet + "-" + obj.children(i).children(0).children(4).children(0).value; // Emp Name Id 
			}
			i++;
		}
		if (empDet == "")
		{
			alert ("Sorry! U must select Employee's to proceed!");
			return false;
		}
		temp.hidAllEmpDet.value = empDet;
		temp.formAction.value = "update";
		temp.submit();
	}

</script>
</head>

<body onload="init(); loadToHidden(); showJobDet(); modOperation(); modEmpDetails();">
<html:form action="frmProdFinalEdit">
<html:hidden property="formAction"/>
<html:hidden property="id"/>
<html:hidden property="ids"/>
<html:hidden property="modJobDet"/> <!-- Holding the JobDetails by EJB -->
<html:hidden property="hidWrkOrdId" /><!-- value="<%= frm.getHidWrkOrdId() %>" Handling the WorkOrder Id for Job Details -->
<html:hidden property="hidWrkOrdJobId" /><!-- value="<%= frm.getHidWrkOrdJobId() %>" Handling the WorkOrder Job Id for Job Operation Details -->
<html:hidden property="showCount" /><!-- value="<%= frm.getShowCount() %>" Showing the appropriate Table by Count -->
<html:hidden property="hidChkdJobName" /><!-- value="<%= frm.getHidChkdJobName() %>" Holding the Selected Job name -->
<html:hidden property="hidChkdJobId" /> <!-- value="<%= frm.getHidChkdJobId() %>"Holding the Selected Job Id Index -->
<html:hidden property="proTotQtySnos" /> <!-- Holding the Total Qty Sno's -->
<html:hidden property="hidAllEmpDet" value="<%= frm.getHidAllEmpDet() %>" /> <!-- Holding the Employee Details -->
<html:hidden property="hidIsRadl" />
<html:hidden property="hidWrkOrdJbStIds" /> <!-- Holding the WoJbStatusIds for the operations are rework or not -->
<html:hidden property="hidUserId" /> <!-- Holding the User Name -->
<html:hidden property="hidWoJbId" value="<%= frm.getHidWoJbId() %>"/> <!-- Holding the WorkOrder Job Id -->
<html:hidden property="grpId"/> <!-- Holding the Opn.Group Id -->
<html:hidden property="sno"/> <!-- Holding the Opn.SNo -->
<html:hidden property="name"/> <!-- Holding the Opn.Name -->
<html:hidden property="stdHrs"/> <!-- Holding the Opn. StdHrs -->
<html:hidden property="gpCde"/> <!-- Holding the Group Code -->

<table width="100%"  border="0" cellspacing="0" cellpadding="10">
<tr>
<td>
	<table width="100%"  border="0" cellpadding="0" cellspacing="0" id="PageTitle">
    <tr>
    	<td><bean:message key="prodacs.productionfinal.header"/></td>
	</tr>
    </table>
    <br>
	<table width="100" cellspacing="0" cellpadding="0" align="right">
	<tr>
		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New Production Final Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1049" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='List Production Final Info'; return true"  onMouseOut="window.status=''; return true" resourceId="49" text="[ List ]" classId="TopLnk" onClick="javaScript:listItem();"/>
	</tr>
	</table><br><br>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
    <tr>
    	<td><table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr id="prodInfo">
    	<td width="100" class="FieldTitle"><bean:message key="prodacs.job.date"/></td>
        <td width="1" class="FieldTitle">:</td>
        <td width="200" class="FieldTitle"><html:text property="proDate" styleClass="TextBox"/>
        <img src='<bean:message key="context"/>/images/calendar.gif' width="16" height="15" align="absmiddle" onClick='show_calendar("proDate",ProductionFinalEdit.proDate.value,event.clientX,event.clientY,false);' onMouseOver="this.style.cursor='hand'"></td>
        <td width="100" class="FieldTitle"><bean:message key="prodacs.production.shift"/></td>
        <td width="1" class="FieldTitle">:</td>
        <td class="FieldTitle"><html:select property="proShift" styleClass="Combo" onchange="">
        							<html:option value="0">-- Choose Shift --</html:option>
									<html:options collection="shiftDefnNameList" property="key" labelProperty="value"/>
							   </html:select></td>
	</tr>
	</table>
    <br>
    <table width="100%"  border="0" cellspacing="0" cellpadding="0" id="BtnBgLft">
    <tr>
    	<td><html:button property="btnNext" styleClass="Button" value="Next" onclick="hideNshowDateShift();"/></td>
	</tr>
    </table>
   </td>
   <td>&nbsp;</td>
  </tr>
  <tr id="prodShftRow" style="display:none">
  	<td style="border-top:dashed 1px #666666">
  		<table width="100%"  border="0" cellspacing="0" cellpadding="0">
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
	        									<html:option value="0">-- Choose WorkOrder # --</html:option>
												<html:options collection="workOrderDet" property="key" labelProperty="value"/>
											 </html:select></td>
		</tr>
	    </table>
    	<br>
    	<table width="100%" cellspacing="0" cellpadding="0" id="tblWorkOrdInfo">
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
        	<td class="TableItems"><input name="rdWrkOrd" type="radio"  value="<bean:write name='bt1' property='woJbId'/>"/></td>
            <td class="TableItems" title="<bean:write name='bt1' property='jobName'/>/<bean:write name='bt1' property='dwgNo'/>/<bean:write name='bt1' property='rvsnNo'/>/<bean:write name='bt1' property='matlType'/>"><input type="text" name="prodJbName" readonly class="TextBoxFull" value='<bean:write name="bt1" property="jobName"/>'/></td>
            <td class="TableItems"><input type="text" name="prodTotQty" readonly class="TextBoxFull" value='<bean:write name="bt1" property="totQty"/>'/></td>
            <td class="TableItems"><input type="text" name="prodPendQtySno" readonly class="TextBoxFull" value='<bean:write name="bt1" property="pendingQtySnos"/>'/></td>
            <td class="TableItems"><input type="text" name="prodPostQtySno" readonly class="TextBoxFull" value='<bean:write name="bt1" property="postedQtySnos"/>'/></td>
            <td class="TableItems"><input type="text" name="prodUnpostQty" readonly class="TextBoxFull" value='<bean:write name="bt1" property="unPostedQtySnos"/>'/></td>
            <td class="TableItems"><logic:notEmpty name="bt1" property="lastProdnDate">
												<bean:define id="latPrdDae" name="bt1" property="lastProdnDate"/>
												<input type="text" name="proLastPrdDate" readonly class="TextBoxFull" value='<%= latPrdDae.toString().substring(0,10) %>'/>
											</logic:notEmpty></td>
		<%
			}
			else
			{
		%>
        	<td class="TableItems2"><input name="rdWrkOrd" type="radio"  value="<bean:write name='bt1' property='woJbId'/>"/></td>
            <td class="TableItems2" title="<bean:write name='bt1' property='jobName'/>/<bean:write name='bt1' property='dwgNo'/>/<bean:write name='bt1' property='rvsnNo'/>/<bean:write name='bt1' property='matlType'/>"><input type="text" name="prodJbName" readonly class="TextBoxFull" value='<bean:write name="bt1" property="jobName"/>'/></td>
            <td class="TableItems2"><input type="text" name="prodTotQty" readonly class="TextBoxFull" value='<bean:write name="bt1" property="totQty"/>'/></td>
            <td class="TableItems2"><input type="text" name="prodPendQtySno" readonly class="TextBoxFull" value='<bean:write name="bt1" property="pendingQtySnos"/>'/></td>
            <td class="TableItems2"><input type="text" name="prodPostQtySno" readonly class="TextBoxFull" value='<bean:write name="bt1" property="postedQtySnos"/>'/></td>
            <td class="TableItems2"><input type="text" name="prodUnpostQty" readonly class="TextBoxFull" value='<bean:write name="bt1" property="unPostedQtySnos"/>'/></td>
            <td class="TableItems2"><logic:notEmpty name="bt1" property="lastProdnDate">
												<bean:define id="latPrdDae" name="bt1" property="lastProdnDate"/>
												<input type="text" name="proLastPrdDate" readonly class="TextBoxFull" value='<%= latPrdDae.toString().substring(0,10) %>'/>
											</logic:notEmpty></td>
		<%
			}
		%>
		</tr>
		</logic:iterate>
        </table>
        <br>
		<table width="100%" cellpadding="0" cellspacing="0" id="BtnBgLft">
        <tr> 
        	<td><html:button styleClass="Button" property="proNextBtn2" onclick="hideNshowJobOpnDet();">Next</html:button></td>
		</tr>
        </table>       
		<td width="20" valign="top"> 
			<table width="100%" cellpadding="0" cellspacing="0" id="ShowHide">
				<tr> 
			  <td onClick="showHide(document.getElementById('prodInfo'), document.getElementById('prodShftImg'))"><img src='<bean:message key="context"/>/images/hide.gif' width="9" height="7" id="prodShftImg"></td><!--showHide(document.getElementById('tblProdShft'), document.getElementById('tblProdShft2'), document.getElementById('prodShftImg'), document.getElementById('prodShft2Img'))-->
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
            <td width="150" class="SortHeader"><bean:message key="prodacs.production.pendingoperationsno"/> </td>
            <td width="150" class="SortHeader"><bean:message key="prodacs.production.postedoperationcompletion"/></td>
            <td width="130" class="SortHeader"><bean:message key="prodacs.production.unpostedoperationcompletion"/></td>
            <td width="120" class="SortHeader"><bean:message key="prodacs.production.lastproductiondateshift"/></td>
		</tr>
		<logic:iterate id="bt2" name="vecJobOpnDet" indexId="count">
        <tr>
		<%
			if(count.intValue()%2 == 0)
			{
		%>
        	<td class="TableItems"><input type="checkbox" name="CheckValue" value='<bean:write name="bt2" property="jobStatId"/>'></td>
            <td class="TableItems"><input type="text" name="jobQutySno" class="TextBoxFull" readonly value='<bean:write name="bt2" property="jobQtySno"/>'></td>
            <td class="TableItems"><input type="text" class="TextBoxFull" readonly name="pendOpnSno" value='<bean:write name="bt2" property="pendingOpnSnos"/>'></td>
            <td class="TableItems"><bean:write name="bt2" property="postedOpnSnos"/>&nbsp;</td>
            <td class="TableItems"><bean:write name="bt2" property="unPostedOpnSnos"/>&nbsp;</td>
            <td class="TableItems"><logic:notEmpty name="bt2" property="lastProdDate">
												<bean:define id="lstPrdDte" name="bt2" property="lastProdDate"/>
												<%= lstPrdDte.toString().substring(0,10) %>
 										  </logic:notEmpty>&nbsp;</td>
		<%
			}
			else
			{
		%>
        	<td class="TableItems"><input type="checkbox" name="CheckValue" value='<bean:write name="bt2" property="jobStatId"/>'></td>
            <td class="TableItems"><input type="text" name="jobQutySno" class="TextBoxFull" readonly value='<bean:write name="bt2" property="jobQtySno"/>'></td>
            <td class="TableItems"><input type="text" class="TextBoxFull" readonly name="pendOpnSno" value='<bean:write name="bt2" property="pendingOpnSnos"/>'></td>
            <td class="TableItems"><bean:write name="bt2" property="postedOpnSnos"/>&nbsp;</td>
            <td class="TableItems"><bean:write name="bt2" property="unPostedOpnSnos"/>&nbsp;</td>
            <td class="TableItems"><logic:notEmpty name="bt2" property="lastProdDate">
												<bean:define id="lstPrdDte" name="bt2" property="lastProdDate"/>
												<%= lstPrdDte.toString().substring(0,10) %>
 										  </logic:notEmpty>&nbsp;</td>
		<%
			}
		%>
		</tr>
		</logic:iterate>
        </table>
        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
        <tr>
        	<td width="100" class="FieldTitle"><bean:message key="prodacs.production.startoperation"/></td>
            <td width="1" class="FieldTitle">:</td>
            <td width="200" class="FieldTitle"><html:text property="proStartOperation" styleClass="TextBox"/></td>
            <td width="100" class="FieldTitle"><bean:message key="prodacs.production.endoperation"/></td>
            <td width="1" class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="proEndOperation" styleClass="TextBox"/></td>
		</tr>
        </table>
      	<br>
      	<table width="100%" cellpadding="0" cellspacing="0" id="BtnBgLft">
		<tr> 
        	<td><html:button property="proNextBtn3" styleClass="Button" onclick="hideNshowEmpDetails();">Next</html:button></td>
		</tr>
      	</table>
	</td>
	<td valign="top">
    	<table width="100%" cellpadding="0" cellspacing="0" id="ShowHide">
        	<tr> 
            	<td onClick="showHide(document.getElementById('prodInfo'), document.getElementById('prodShft2Img'))"><img src='<bean:message key="context"/>/images/hide.gif' width="9" height="7" id="prodShft2Img"></td>
			</tr>
		</table>
        <table width="100%" cellpadding="0" cellspacing="0" id="ShowHide">
        <tr> 
        	<td onClick="showHide(document.getElementById('prodShftRow'), document.getElementById('prodShftRowImg'))"><img src='<bean:message key="context"/>/images/hide.gif' width="9" height="7" id="prodShftRowImg"></td>
		</tr>
        </table>
	</td>
    </tr>
   <tr id="jobQty" style="display:none">
   		<td style="border-top:dashed 1px #666666">
   		<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr id="tblProdShft3" style="display:none"> 
		  <td width="90" class="FieldTitle"><bean:message key="prodacs.job.date"/></td>
		  <td width="1" class="FieldTitle">:</td>
		  <td width="150" class="ViewData">&nbsp;</td>
		  <td width="50" class="FieldTitle"><bean:message key="prodacs.production.shift"/></td>
		  <td width="1" class="FieldTitle">:</td>
		  <td width="100" class="ViewData">&nbsp;</td>
		</tr>
      	<tr id="tblProdWrkOrd2">
        	<td width="100" class="FieldTitle"><bean:message key="prodacs.workorder.workorder"/></td>
            <td width="1" class="FieldTitle">:</td>
            <td width="200" class="ViewData">&nbsp;</td>
            <td width="100" class="FieldTitle"><bean:message key="prodacs.job.jobname"/></td>
            <td width="1" class="FieldTitle">:</td>
            <td class="ViewData">&nbsp;</td>
		</tr>
        <tr id="tblProdJobOty">
        	<td class="FieldTitle"><bean:message key="prodacs.workorder.jobqtysno"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData">&nbsp;</td>
        	<td class="FieldTitle"><bean:message key="prodacs.production.startoperation"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData">&nbsp;</td>
            <td class="FieldTitle"><bean:message key="prodacs.production.endoperation"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData">&nbsp;</td>
		</tr>
		<tr id="tblProdIncent">
			<td class="FieldTitle"><bean:message key="prodacs.production.totalhours"/><span class="mandatory">*</span></td>
			<td class="FieldTitle">:</td>
			<td class="ViewData"><html:text property="proTotalHours" onkeypress="return isNumber(this);" onkeyup="return isNumber(this);" maxlength="6" styleClass="TextBox" size="12"/></td>
		</tr>
        </table>
        <table width="100%"  border="0" cellspacing="0" cellpadding="3">
        <tr>
        	<td colspan="2" class="TopLnk"> [ <a href="#" onClick="delRow(document.forms[0]);"><bean:message key="prodacs.common.delete"/></a> ]</td>
		</tr>
        <tr>
        	<td width="50%" valign="top">
        		<table width="100%" cellspacing="0" cellpadding="2" id="tblEmpDetMstr">
				<tr>
		        	<td class="SortHeader"><bean:message key="prodacs.production.emptype"/></td>
		            <td class="SortHeader"><bean:message key="prodacs.production.empname"/></td>
		            <td width="20" class="SortHeader">&nbsp;</td>
				</tr>
				<%
					Iterator it = empDetails.keySet().iterator();
					HashMap empType = new HashMap();
					HashMap empDet = new HashMap();
					HashMap empTypRule = new HashMap();
					Vector vec_EmpDets = new Vector();
					int i = 0;
					while (it.hasNext())
					{
						int empTypId = 0;
						String empTypName = "";
						empType = (HashMap) it.next();
						
						//empDet = (HashMap) empDetails.get(empType);
						vec_EmpDets = (Vector) empDetails.get(empType);
						for (int x = 0; x < vec_EmpDets.size(); x++)
						{
							if (x == 0)
								empDet = (HashMap) vec_EmpDets.get(x);
							else
								empTypRule = (HashMap) vec_EmpDets.get(x);
						}
						Iterator it1 = empType.keySet().iterator();
						while (it1.hasNext())
						{
							empTypId = ((Integer)it1.next()).intValue();
							empTypName = empType.get(new Integer(empTypId)).toString();
						}
						if (BuildConfig.DMODE)
							System.out.println("EmpTypId :"+empTypId);
						pageContext.setAttribute("empDet",empDet);
						pageContext.setAttribute("empTypRule",empTypRule);
						if ((i % 2) == 0)
						{
				%>
		        <tr>
		        	<td class="TableItems"><input type="text" class="TextBoxFull" readonly name="empTyName" value="<%= empTypName %>"></td>
		            <td class="TableItems"><html:select property="proEmployeeName" styleClass="ComboFull">
												<html:option value="0">-- Employee Name --</html:option>
								 			    <html:options collection="empDet" property="key" labelProperty="value"/>
										  </html:select></td>
					<td class="TableItems"><img src='<bean:message key="context"/>/images/add.gif' width="20" height="20" border="0" value="<%= i+1 %>" onMouseOver="this.style.cursor='hand'" onclick="transEmpDet(this.value);"></td>
					<td><input type="hidden" name="empTypId" value="<%= empTypId %>"></td>
				</tr>
				<%
					}
					else
					{
				%>
		        <tr>
		        	<td class="TableItems2"><input type="text" class="TextBoxFull" readonly name="empTyName" value="<%= empTypName %>"></td>
		            <td class="TableItems2"><html:select property="proEmployeeName" styleClass="ComboFull">
												<html:option value="0">-- Employee Name --</html:option>
								 			    <html:options collection="empDet" property="key" labelProperty="value"/>
										  </html:select></td>
					<td class="TableItems2"><img src='<bean:message key="context"/>/images/add.gif' width="20" height="20" border="0" value="<%= i+1 %>" onMouseOver="this.style.cursor='hand'" onclick="transEmpDet(this.value);"></td>
					<td><input type="hidden" name="empTypId" value="<%= empTypId %>"></td>
				</tr>
				<%
					}
					i++;
				}
				%>
		        </table>
			</td>
		    <td valign="top" class="ViewData">
			<div style="height:80;overflow:auto;">
		    	<table width="100%" cellspacing="0" cellpadding="0">
				<tr>
		        	<td width="23" class="SortHeader"><input type="checkbox" name="ckAll" onclick="headCkAll(this);"></td>
		            <td width="100" class="SortHeader"><bean:message key="prodacs.production.emptype"/></td>
		            <td class="SortHeader"><bean:message key="prodacs.production.empname"/></td>
				</tr>
				</table>
				<table width="100%" cellspacing="0" cellpadding="0" id="tblEmpDetails">
				<tr> 
					<td width="20" class="TableItems"><input type="checkbox" name="ckdCkBx"></td>
					<td width="97" class="TableItems"><input type="text" class="TextBoxFull" readonly name="empTypName"></td>
					<td class="TableItems"><input type="text" class="TextBoxFull" readonly name="empName"></td>
					<td><input type="hidden" name="hidEmpTypId"></td>
					<td><input type="hidden" name="hidEmpId"></td>
				</tr>
		        </table>
		        </div>
				<br><br>
				<table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
		        <tr>
		        	<td><input type='button' class="Button" onclick="enterProDet();" value='Update'/></td>
				</tr>
		        </table>
			</td>
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
	</td>
</tr>
</table>
</td>
</tr>
</table>
</html:form>
</body>
</html:html>
