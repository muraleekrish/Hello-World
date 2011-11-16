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
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<jsp:useBean id="frm" class="com.savantit.prodacs.presentation.tableadmin.production.ProductionFinalAddForm" />
<jsp:setProperty name="frm" property="*" />

<useradmin:userrights resource="1049"/>
<%
	if(frm.getFormAction().equalsIgnoreCase("addNew"))
		frm.setProDate("");
	if (BuildConfig.DMODE)
		System.out.println("Production Add Form");
	HashMap empDetails = new HashMap(); /* For Employee Details and Type Details */
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
		
		/* For loading WorkOrder */
		HashMap workOrderDet = objProdFinal.getWorkOrderList();
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
			
			dateShift = objProdFinal.getShiftDefnNameList(gc.getTime());
			pageContext.setAttribute("dateShift",dateShift);
		}
		else
		{
			pageContext.setAttribute("dateShift",dateShift);
		}
		
		/* Loading JobDetails using WorkOrderId */
		Vector vecJobDet = new Vector();
		if (frm.getHidWrkOrdId() != "0")
		{
			vecJobDet = objProdFinal.getProdnJobDetailsByWorkOrder(Integer.parseInt(frm.getHidWrkOrdId()));
			pageContext.setAttribute("vecJobDet",vecJobDet);
		}
		else
		{
			pageContext.setAttribute("vecJobDet",vecJobDet);
		}

		/* Loading Job Operation Details by giving WorkOrderJobId */
		Vector vecJobOpnDet = new Vector();
		if (frm.getHidWrkOrdJobId() != "0")
		{
			vecJobOpnDet = objProdFinal.getFinalProdnJobOperationDetails(Integer.parseInt(frm.getHidWrkOrdJobId()));
			pageContext.setAttribute("vecJobOpnDet",vecJobOpnDet);
		}
		else
		{
			pageContext.setAttribute("vecJobOpnDet",vecJobOpnDet);
		}

		/* For Loading Employee Types */
		empDetails = objProdFinal.getAllEmployeesByTypes();
		pageContext.setAttribute("empDetails",empDetails);

		/* Procedures for Modification */
		String[] arModDet;
		if (frm.getFormAction().equalsIgnoreCase("modify") && (request.getParameter("hidModDet") != null))
		{
			StringTokenizer stModDet = new StringTokenizer(request.getParameter("hidModDet"),"#");
			arModDet = new String[stModDet.countTokens()];
			int count = 0;
			while (stModDet.hasMoreTokens())
			{
				arModDet[count] = stModDet.nextToken();
				count++;
			}

			StringTokenizer stDate = new StringTokenizer(arModDet[0].trim(),"-");
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

			dateShift = objProdFinal.getShiftDefnNameList(gc.getTime());
			pageContext.setAttribute("dateShift",dateShift);

			vecJobDet = objProdFinal.getProdnJobDetailsByWorkOrder(Integer.parseInt(arModDet[7].trim()));
			pageContext.setAttribute("vecJobDet",vecJobDet);

			vecJobOpnDet = objProdFinal.getFinalProdnJobOperationDetails(Integer.parseInt(arModDet[9].trim()));
			pageContext.setAttribute("vecJobOpnDet",vecJobOpnDet);
		}
	}
	catch(Exception e)
	{
		if (BuildConfig.DMODE)
		{
			System.out.println("Problem in ProductionFinalAdd.jsp");
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
		document.forms[0].action = '<bean:message key="context"/>/Production/ProductionFinal.jsp';
		document.forms[0].submit();
	}

	function addItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/Production/ProductionFinalAdd.jsp?formAction=addNew';
		document.forms[0].submit();
	}
</script>

<!-- Script tag for Production Final operations -->
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

	function hideNshowDateShift()
	{
		var temp = document.forms[0];
		var obj = document.getElementById("tblProdShft");
		
		/* For displaying date and intimate an alert, if empty*/
		if (temp.proDate.value != "")
		{
			obj.children(2).innerText = temp.proDate.value;
		}
		else
		{
			alert("Select a Production Date to Continue!");
			prodShftRow.style.display='none';
			prodInfo.style.display='block';
			return false;
		}

		/* For displaying date and intimate an alert, if empty*/
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
		loadProdList();
		temp.submit();
	}

	function showJobDet()
	{
		var temp = document.forms[0];
		temp.hidUserId.value = '<%= userName %>';
		//alert(temp.showCount.value);
		if (temp.showCount.value == "")
		{
			//prodInfo.style.display='block';
			prodShftRow.style.display='none';
		}
		else if (temp.showCount.value == "1")
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
			
			/* Condition for hiding WOJbOpns while adding new entries */
			if ((temp.formAction.value == "post") && (temp.hidWrkOrdId.value != 0))
			{
				tblWorkOrdInfo.style.display = 'none';
			}
			else
			{
				tblWorkOrdInfo.style.display = 'block';
			}

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

			var obj = document.getElementById("tblProdShft");
			obj.children(2).innerText = temp.proDate.value;
			obj.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;

			var obj1 = document.getElementById("tblProdShft2");
			obj1.children(2).innerText = temp.proDate.value;
			obj1.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;

			var obj2 = document.getElementById("tblProdWrkOrd");
			obj2.children(2).innerText = temp.proWorkOrderHash.options[temp.proWorkOrderHash.selectedIndex].text;
			obj2.children(5).innerText = temp.hidChkdJobName.value;

			var obj = document.getElementById("tblProdShft3");
			obj.children(2).innerText = temp.proDate.value;
			obj.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;

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
					if (obj.children(0).children(i).children(1).innerText == chkdQty[j])
					{
						obj.children(0).children(i).children(0).children(0).checked = true;
					}
				}
			}
		}
		else if (temp.showCount.value == "5")
		{
			//prodInfo.style.display = 'block';
			prodShftRow.style.display = 'block';
			wrkOrder.style.display = 'block';
			jobQty.style.display = 'block';
			var modDet = ('<%= request.getParameter("hidModDet") %>').split("#");

			temp.proDate.value = modDet[0]; // Date

			for (var i = 0; i < temp.proShift.length; i++) // Shift
			{
				if (temp.proShift.options[i].value == modDet[7])
				{
					temp.proShift.selectedIndex = i;
				}
			}

			/* Validation for updating the Date and Shift */
			var obj = document.getElementById("tblProdShft");
			obj.children(2).innerText = temp.proDate.value;
			obj.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;

			for (var i = 0; i < temp.proWorkOrderHash.length; i++) // WoNo
			{
				if (temp.proWorkOrderHash.options[i].value == modDet[8])
				{
					temp.proWorkOrderHash.selectedIndex = i;
				}
			}

			/* Check the Particular Job */
			var obj = document.getElementById('tblWorkOrdInfo');
			for (var i = 1; i < obj.children(0).children.length; i++)
			{
				if (obj.children(0).children(i).children(0).children(0).value == modDet[9])
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
			var jbQty = modDet[10].split(",");
			var obj = document.getElementById('tblJobOperDet');
			for (var i = 1; i < obj.children(0).children.length; i++)
			{
				for (var j = 0; j < jbQty.length; j++)
				{
					if (obj.children(0).children(i).children(1).innerText == jbQty[j])
					{
						obj.children(0).children(i).children(0).children(0).checked = true;
					}
				}
			}

			temp.proStartOperation.value = modDet[4]; // Start Operation
			temp.proEndOperation.value = modDet[5]; // End Operation

			var obj1 = document.getElementById("tblProdWrkOrd2");
			obj1.children(2).innerText = temp.proWorkOrderHash.options[temp.proWorkOrderHash.selectedIndex].text;
			obj1.children(5).innerText = temp.hidChkdJobName.value;

			var obj2 = document.getElementById("tblProdJobOty");
			obj2.children(2).innerText = modDet[10];
			obj2.children(5).innerText = temp.proStartOperation.value;
			obj2.children(8).innerText = temp.proEndOperation.value;

			temp.proTotalHours.value = modDet[6];
			/* For Employee Detalis */
			var obj = document.getElementById('tblEmpDetails');
			var empDet = modDet[11].split("^");
			for (var i = 0; i < empDet.length; i++)
			{
				var empPrnlDet = empDet[i].split("-");
				if (i == 0)
				{
					obj.children(i).children(0).children(1).children(0).value = empPrnlDet[0];
					obj.children(i).children(0).children(2).children(0).value = empPrnlDet[1];
					obj.children(i).children(0).children(3).children(0).value = empPrnlDet[2];
					obj.children(i).children(0).children(4).children(0).value = empPrnlDet[3];
				}
				else
				{
					var newNode = obj.children(0).cloneNode(true);
					obj.appendChild(newNode);
					var len = obj.children.length;

					obj.children(len-1).children(0).children(1).children(0).value = empPrnlDet[0];
					obj.children(len-1).children(0).children(2).children(0).value = empPrnlDet[1];
					obj.children(len-1).children(0).children(3).children(0).value = empPrnlDet[2];
					obj.children(len-1).children(0).children(4).children(0).value = empPrnlDet[3];
				}
			}
		}
	}

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
			else if (temp.checkbox2.checked)
			{
				temp.CheckValue.checked = true;
			}
			else
			{
				temp.CheckValue.checked = false;
			}
		}
		else
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

	/* Hiding the JobDetails, JobOperation Details and show the Employee Details */
	function hideNshowEmpDetails()
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
			alert ("Start Operation can't be more than End Operation!");
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
					totQty = obj.children(0).children(i).children(1).innerText;
					woJbStatIds = obj.children(0).children(i).children(0).children(0).value;
				}
				else
				{
					totQty = totQty + "," + obj.children(0).children(i).children(1).innerText;
					woJbStatIds = woJbStatIds +"-"+ obj.children(0).children(i).children(0).children(0).value;
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
		temp.hidWrkOrdJbStIds.value = obj.children(0).children(1).children(0).children(0).value;
		temp.showCount.value = "3";
		loadProdList();
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

	function chkAllTblBffer()
	{
		var temp = document.forms[0];
		if (temp.chkAllProdBufHead.checked)
		{
			for (var i = 0; i < temp.chkProdList.length; i++)
			{
				temp.chkProdList[i].checked = true;
			}
			if (temp.chkProdList.length == undefined)
				temp.chkProdList.checked = true;
		}
		else
		{
			for (var i = 0; i < temp.chkProdList.length; i++)
			{
				temp.chkProdList[i].checked = false;
			}
			if (temp.chkProdList.length == undefined)
				temp.chkProdList.checked = false;
		}
	}

	function transProdList()
	{
		var temp = document.forms[0];
		var obj = document.getElementById('tblBufferList');
		tblBuffer.style.display = 'block';
		var object = document.getElementById("tblEmpDetails");
		if (temp.proTotalHours.value == "")
		{
			alert("Total Hours cannot be left Empty!");
			temp.proTotalHours.focus();
			return false;
		}

		/*if (flag == 1)
		{
			alert ("The Entry is already Transfered.");
			return false;
		}
		
		 Remove the Empty values in Employee Details 
		if ((object.children(0).children(0).children(1).children(0).value == "") &&
			(object.children(0).children(0).children(2).children(0).value == "") &&
			(object.children(0).children(0).children(3).children(0).value == ""))
		{
			alert ("Employee Details can't be Empty!");
			return false;
		}*/

		var empDet = "";
		var i = 0;
		while (i < object.children.length)
		{
			if (i != 0)
			{
				empDet = empDet + "^";
			}
			empDet = empDet + "" + object.children(i).children(0).children(1).children(0).value + // Emp Typ name
			"-" + object.children(i).children(0).children(2).children(0).value + // Emp name
			"-" + object.children(i).children(0).children(3).children(0).value + // Emp Typ Id
			"-" + object.children(i).children(0).children(4).children(0).value; // Emp Name Id

			i++;
		}
		//alert ("empDet: "+empDet);
		if (empDet == "")
		{
			alert ("Sorry! U must select Employee's to proceed!");
			return false;
		}

		/* Moving all the Values to Buffer */
		if ((obj.children.length == 1) && (obj.children(0).children(0).children(2).children(0).value == "") && (obj.children(0).children(0).children(2).children(0).value == ""))
		{
			obj.children(0).children(0).children(1).children(0).value = obj.children.length;
			obj.children(0).children(0).children(2).children(0).value = temp.proDate.value;
			obj.children(0).children(0).children(3).children(0).value = temp.proShift.options[temp.proShift.selectedIndex].text;
			obj.children(0).children(0).children(4).children(0).value = temp.proWorkOrderHash.options[temp.proWorkOrderHash.selectedIndex].text;
			obj.children(0).children(0).children(5).children(0).value = temp.hidChkdJobName.value;
			obj.children(0).children(0).children(6).children(0).value = temp.proStartOperation.value;
			obj.children(0).children(0).children(7).children(0).value = temp.proEndOperation.value;
			obj.children(0).children(0).children(8).children(0).value = temp.proTotalHours.value;

			obj.children(0).children(0).children(9).children(0).value = temp.proShift.value;
			obj.children(0).children(0).children(10).children(0).value = temp.proWorkOrderHash.value;
			obj.children(0).children(0).children(11).children(0).value = temp.hidWrkOrdJobId.value;
			obj.children(0).children(0).children(12).children(0).value = temp.proTotQtySnos.value;
			obj.children(0).children(0).children(13).children(0).value = empDet;
		}
		else
		{
			var newNode = obj.children(0).cloneNode(true);
			obj.appendChild(newNode);
			var len = obj.children.length;

			obj.children(len-1).children(0).children(1).children(0).value = obj.children.length;
			obj.children(len-1).children(0).children(2).children(0).value = temp.proDate.value;
			obj.children(len-1).children(0).children(3).children(0).value = temp.proShift.options[temp.proShift.selectedIndex].text;
			obj.children(len-1).children(0).children(4).children(0).value = temp.proWorkOrderHash.options[temp.proWorkOrderHash.selectedIndex].text;
			obj.children(len-1).children(0).children(5).children(0).value = temp.hidChkdJobName.value;
			obj.children(len-1).children(0).children(6).children(0).value = temp.proStartOperation.value;
			obj.children(len-1).children(0).children(7).children(0).value = temp.proEndOperation.value;
			obj.children(len-1).children(0).children(8).children(0).value = temp.proTotalHours.value;

			obj.children(len-1).children(0).children(9).children(0).value = temp.proShift.value;
			obj.children(len-1).children(0).children(10).children(0).value = temp.proWorkOrderHash.value;
			obj.children(len-1).children(0).children(11).children(0).value = temp.hidWrkOrdJobId.value;
			obj.children(len-1).children(0).children(12).children(0).value = temp.proTotQtySnos.value;
			obj.children(len-1).children(0).children(13).children(0).value = empDet;
		}
		flag = 1;
		prodShftRow.style.display = 'none';
		wrkOrder.style.display = 'none';
		jobQty.style.display = 'none';
		temp.proDate.value = "";
		temp.proShift.selectedIndex = 0;
		temp.proWorkOrderHash.selectedIndex = 0;
		temp.showCount.value = "";
	}

	function modifyRow()
	{
		var temp = document.forms[0];
		var obj = document.getElementById('tblBufferList');
		var count = 0;
		for (var i = 0; i < obj.children.length; i++)
		{
			if (obj.children(i).children(0).children(0).children(0).checked)
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
			if (obj.children(i).children(0).children(0).children(0).checked)
			{
				temp.hidModDet.value = obj.children(i).children(0).children(2).children(0).value + "#" +
					obj.children(i).children(0).children(3).children(0).value + "#" +
					obj.children(i).children(0).children(4).children(0).value + "#" +
					obj.children(i).children(0).children(5).children(0).value + "#" +
					obj.children(i).children(0).children(6).children(0).value + "#" +
					obj.children(i).children(0).children(7).children(0).value + "#" +
					obj.children(i).children(0).children(8).children(0).value + "#" +
					obj.children(i).children(0).children(9).children(0).value + "#" +
					obj.children(i).children(0).children(10).children(0).value + "#" +
					obj.children(i).children(0).children(11).children(0).value + "#" +
					obj.children(i).children(0).children(12).children(0).value + "#" +
					obj.children(i).children(0).children(13).children(0).value;

				temp.hidWrkOrdId.value = obj.children(i).children(0).children(10).children(0).value;
				temp.hidWrkOrdJobId.value = obj.children(i).children(0).children(11).children(0).value;
			}
		}
		temp.formAction.value = "modify";
		delBufferRow();
		temp.showCount.value = "5";
		loadProdList();
		temp.submit();
	}

	function delBufferRow()
	{
		var temp = document.forms[0];
		var obj = document.getElementById('tblBufferList');
		var count = 0;
		for (var i = 0; i < obj.children.length; i++)
		{
			if (obj.children(i).children(0).children(0).children(0).checked)
				count++;
		}
		if (count == 0)
		{
			alert ("Select One or More Production Entries to Delete!");
			return false;
		}

		if (obj.children.length > 1)
		{
			for (var i = 0 ; i < obj.children.length; i++)
			{
				if (obj.children(i).children(0).children(0).children(0).checked)
				{
					obj.removeChild(obj.children(i));
					i = 0;
				}
			}
			if (obj.children.length == 1)
			{
				if (obj.children(0).children(0).children(0).children(0).checked)
				{
					obj.children(0).children(0).children(1).children(0).value = "";
					obj.children(0).children(0).children(2).children(0).value = "";
					obj.children(0).children(0).children(3).children(0).value = "";
					obj.children(0).children(0).children(4).children(0).value = "";
					obj.children(0).children(0).children(5).children(0).value = "";
					obj.children(0).children(0).children(6).children(0).value = "";
					obj.children(0).children(0).children(7).children(0).value = "";
					obj.children(0).children(0).children(8).children(0).value = "";
					obj.children(0).children(0).children(9).children(0).value = "";
					obj.children(0).children(0).children(10).children(0).value = "";
					obj.children(0).children(0).children(11).children(0).value = "";
					obj.children(0).children(0).children(12).children(0).value = "";
					obj.children(0).children(0).children(13).children(0).value = "";

					obj.children(0).children(0).children(0).children(0).checked = false;
				}
			}
		}
		else if (obj.children(0).children(0).children(0).children(0).checked)
		{
					obj.children(0).children(0).children(1).children(0).value = "";
					obj.children(0).children(0).children(2).children(0).value = "";
					obj.children(0).children(0).children(3).children(0).value = "";
					obj.children(0).children(0).children(4).children(0).value = "";
					obj.children(0).children(0).children(5).children(0).value = "";
					obj.children(0).children(0).children(6).children(0).value = "";
					obj.children(0).children(0).children(7).children(0).value = "";
					obj.children(0).children(0).children(8).children(0).value = "";
					obj.children(0).children(0).children(9).children(0).value = "";
					obj.children(0).children(0).children(10).children(0).value = "";
					obj.children(0).children(0).children(11).children(0).value = "";
					obj.children(0).children(0).children(12).children(0).value = "";
					obj.children(0).children(0).children(13).children(0).value = "";

					obj.children(0).children(0).children(0).children(0).checked = false;
		}
		for (var i = 0; i < obj.children.length; i++)
		{
			obj.children(i).children(0).children(1).children(0).value = i+1;
		}
	}

	/* This fn take a backup to all Production List Details */
	function loadProdList()
	{
		var temp = document.forms[0];
		var obj = document.getElementById('tblBufferList');
		var prodDet = "";

		temp.hidProdList.value = "";
		if (obj.children(0).children(0).children(1).children(0).value == "")
			return false;

		for (var i = 0; i < obj.children.length; i++)
		{
			if (i != 0)
			{
				prodDet = prodDet + "$";
			}
			prodDet = prodDet
				 + "" + obj.children(i).children(0).children(1).children(0).value // Sno
				+ "#" + obj.children(i).children(0).children(2).children(0).value // Date
				+ "#" + obj.children(i).children(0).children(3).children(0).value // Shift Name
				+ "#" + obj.children(i).children(0).children(4).children(0).value // WoNo
				+ "#" + obj.children(i).children(0).children(5).children(0).value // Job Name
				+ "#" + obj.children(i).children(0).children(6).children(0).value // Start Operation
				+ "#" + obj.children(i).children(0).children(7).children(0).value // End Opn
				+ "#" + obj.children(i).children(0).children(8).children(0).value // Total Hours
				+ "#" + obj.children(i).children(0).children(9).children(0).value // Shift ID
				+ "#" + obj.children(i).children(0).children(10).children(0).value // Wo Id
				+ "#" + obj.children(i).children(0).children(11).children(0).value // Job Id
				+ "#" + obj.children(i).children(0).children(12).children(0).value // Job Qty Serial Nos
				+ "#" + obj.children(i).children(0).children(13).children(0).value; // Emp Details
		}
		temp.hidProdList.value = prodDet;
	}

	function submitItem()
	{
		loadProdList();
		document.forms[0].formAction.value = "add";
		document.forms[0].submit();
	}

</script>
</head>

<body onload="init(); showJobDet(); ">
<html:form action="frmProdFinalAdd">
<html:hidden property="formAction"/>
<html:hidden property="minQty" value="<%= frm.getMinQty() %>"/> <!-- Holding the Minimum required no. of emp's -->
<html:hidden property="hidWrkOrdId" value="<%= frm.getHidWrkOrdId() %>"/><!-- Handling the WorkOrder Id for Job Details -->
<html:hidden property="hidWrkOrdJobId" value="<%= frm.getHidWrkOrdJobId() %>"/><!-- Handling the WorkOrder Job Id for Job Operation Details -->
<html:hidden property="showCount" /> <!-- value="< %= frm.getShowCount() %>" Showing the appropriate Table by Count -->
<html:hidden property="hidChkdJobName" value="<%= frm.getHidChkdJobName() %>"/><!-- Holding the Selected Job name -->
<html:hidden property="hidChkdJobId" value="<%= frm.getHidChkdJobId() %>"/> <!-- Holding the Selected Job Id Index -->
<html:hidden property="proTotQtySnos" value="<%= frm.getProTotQtySnos() %>"/> <!-- Holding the Total Qty Sno's -->
<html:hidden property="hidAllEmpDet" value="<%= frm.getHidAllEmpDet() %>"/> <!-- Holding the Employee Details -->
<html:hidden property="hidIsRadl"/>
<html:hidden property="hidUserId"/> <!-- Holding the User Name -->
<html:hidden property="hidWrkOrdJbStIds"/> <!-- Holding the WoJbStatusId's for Whether the work is Rework -->
<html:hidden property="hidProdList"/> <!-- Holding the Production List Details -->
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
    <tr id="prodInfo">
		<td valign="top" colspan="2">
			<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td width="100" class="FieldTitle"><bean:message key="prodacs.job.date"/><span class="mandatory">*</span></td>
				<td width="1" class="FieldTitle">:</td>
				<td width="200" class="FieldTitle"><html:text property="proDate" styleClass="TextBox" size="12" readonly="true"/>
							<img src='<bean:message key="context"/>/images/calendar.gif' width="16" height="15" align="absmiddle" onClick='show_calendar("proDate",ProductionFinalAdd.proDate.value,event.clientX,event.clientY,false);' onMouseOver="this.style.cursor='hand'"></td>
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
			<%
				if(count.intValue()%2 == 0)
				{
			%>
				<td class="TableItems"><input name="rdWrkOrd" type="radio"  value="<bean:write name='bt1' property='woJbId'/>"></td>
				<td class="TableItems" title="<bean:write name='bt1' property='jobName'/>/<bean:write name='bt1' property='dwgNo'/>/<bean:write name='bt1' property='rvsnNo'/>/<bean:write name='bt1' property='matlType'/>"><bean:write name="bt1" property="jobName"/>&nbsp;</td>
				<td class="TableItems"><bean:write name="bt1" property="totQty" />&nbsp;</td>
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
				<td class="TableItems2"><input name="rdWrkOrd" type="radio" value="<bean:write name='bt1' property='woJbId'/>"></td>
				<td class="TableItems2" title="<bean:write name='bt1' property='jobName'/>/<bean:write name='bt1' property='dwgNo'/>/<bean:write name='bt1' property='rvsnNo'/>/<bean:write name='bt1' property='matlType'/>"><bean:write name="bt1" property="jobName"/>&nbsp;</td>
				<td class="TableItems2"><bean:write name="bt1" property="totQty" />&nbsp;</td>
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
			<table width="100%" cellpadding="0" cellspacing="0" id="BtnBgLft">
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
    <table width="100%" cellspacing="0" cellpadding="0" id="tblJobOperDet">
    <tr>
    	<td width="23" class="SortHeader"><input type="checkbox" name="checkbox2" value="checkbox" onclick="chkAll(this);"></td>
        <td class="SortHeader"><bean:message key="prodacs.workorder.jobqtysno"/></td>
        <td width="150" class="SortHeader"><bean:message key="prodacs.production.pendingoperationsno"/></td>
        <td width="130" class="SortHeader"><bean:message key="prodacs.production.postedoperationcompletion"/></td>
        <td width="120" class="SortHeader"><bean:message key="prodacs.production.unpostedoperationcompletion"/></td>
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
        <td class="TableItems"><bean:write name="bt2" property="postedOpnSnos"/>&nbsp;</td>
        <td class="TableItems"><bean:write name="bt2" property="unPostedOpnSnos"/>&nbsp;</td>
	<%
		}
		else
		{
	%>
		<td class="TableItems2"><input type="checkbox" name="CheckValue" value='<bean:write name="bt2" property="jobStatId"/>'></td>
        <td class="TableItems2"><bean:write name="bt2" property="jobQtySno"/></td>
        <td class="TableItems2"><input type="text" class="TextBoxFull" readonly name="pendOpnSno" value='<bean:write name="bt2" property="pendingOpnSnos"/>'></td>
        <td class="TableItems2"><bean:write name="bt2" property="postedOpnSnos"/>&nbsp;</td>
        <td class="TableItems2"><bean:write name="bt2" property="unPostedOpnSnos"/>&nbsp;</td>
	<%
		}
	%>
	</tr>
	</logic:iterate>
    </table><br>
    <table width="100%"  border="0" cellspacing="0" cellpadding="0">
    <tr>
    	<td class="FieldTitle"><bean:message key="prodacs.production.startoperation"/><span class="mandatory">*</span>
	                    <html:text property="proStartOperation" onkeypress="return isNumber1(this);" onkeyup="return isNumber1(this);" maxlength="10" styleClass="TextBox" size="15"/>
	                    <bean:message key="prodacs.production.endoperation"/><span class="mandatory">*</span>
	                    <html:text property="proEndOperation" onkeypress="return isNumber1(this);" onkeyup="return isNumber1(this);" maxlength="10" styleClass="TextBox" size="15"/></td>
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
		<td class="FieldTitle"><bean:message key="prodacs.workorder.workorder"/></td>
		<td class="FieldTitle">:</td>
		<td class="ViewData">&nbsp;</td>
		<td class="FieldTitle"><bean:message key="prodacs.job.jobname"/></td>
		<td class="FieldTitle">:</td>
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
    	<table width="100%"  border="0" cellspacing="0" cellpadding="2" id="tblEmpDetMstr">
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

				vec_EmpDets = (Vector) empDetails.get(empType);
				for (int x = 0; x < vec_EmpDets.size(); x++)
				{
					if (x == 0)
						empDet = (HashMap) vec_EmpDets.get(x);
				}
				Iterator it1 = empType.keySet().iterator();
				while (it1.hasNext())
				{
					empTypId = ((Integer)it1.next()).intValue();
					empTypName = empType.get(new Integer(empTypId)).toString();
				}
				pageContext.setAttribute("empDet",empDet);
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
				<table width="100%" cellspacing="0" cellpadding="3">
				<tr>
					<td width="25" class="Header"><input type="checkbox" name="ckAll" onclick="headCkAll(this);"></td>
					<td width="75" class="Header"><bean:message key="prodacs.production.emptype"/></td>
					<td class="Header"><bean:message key="prodacs.production.empname"/></td>
				</tr>
				</table>
				<table width="100%" cellspacing="0" cellpadding="0" id="tblEmpDetails">
				<tr>
	    			<td width="28" class="TableItems"><input type="checkbox" name="ckdCkBx"></td>
					<td width="78" class="TableItems"><input type="text" class="TextBoxFull" readonly name="empTypName"></td>
					<td class="TableItems"><input type="text" class="TextBoxFull" readonly name="empName"></td>
					<td><input type="hidden" name="hidEmpTypId"></td>
					<td><input type="hidden" name="hidEmpId"></td>
				</tr>
	            </table>
	         </div>
		</td>
		<br>
		<table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
		<tr>
			<td><html:button property="proNextBtn4" styleClass="Button" onclick="transProdList();">Add</html:button></td>
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

		<!-- Buffering the Production Values -->
		<table width="100%" cellpadding="0" cellspacing="0" id="tblBuffer" style="display:none">
        <tr>
		<td>
			<fieldset id="FieldSet"><legend class="FieldTitle"> Current Production Entries </legend>
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
						<td width="75" class="Header"><bean:message key="prodacs.job.date"/></td>
						<td width="70" class="Header"><bean:message key="prodacs.production.shift"/></td>
						<td width="80" class="Header"><bean:message key="prodacs.workorder.workorderno"/></td>
						<td class="Header"><bean:message key="prodacs.job.jobname"/></td>
						<td width="50" class="Header"><bean:message key="prodacs.radialproduction.stopr"/></td>
						<td width="60" class="Header"><bean:message key="prodacs.radialproduction.endpr"/></td>
						<td width="60" class="Header"><bean:message key="prodacs.production.tothrs"/></td>
					</tr>
				</table>

				<table width="100%" cellspacing="0" cellpadding="0" id="tblBufferList">
					<tr>
					  <td width="22" class="TableItems"><input type="checkbox" name="chkProdList"/></td>
					  <td width="27" class="TableItems"><input readonly name="prodSno" class="TextBoxFull" /></td>
					  <td width="72" class="TableItems"><input readonly name="prodDate" class="TextBoxFull" size="10"/></td>
					  <td width="67" class="TableItems"><input readonly name="prodShift" class="TextBoxFull" /></td>
					  <td width="77" class="TableItems"><input readonly name="prodWoNo" class="TextBoxFull" /></td>
					  <td class="TableItems"><input readonly name="prodJobName" class="TextBoxFull" /></td>
					  <td width="47" class="TableItems"><input readonly name="prodStrOpn" class="TextBoxFull" /></td>
					  <td width="57" class="TableItems"><input readonly name="prodEdOpn" class="TextBoxFull" /></td>
					  <td width="57" class="TableItems"><input readonly name="prodTotHrs" class="TextBoxFull" /></td>
					  <td><input type="hidden" name="prodShiftId"/></td>
					  <td><input type="hidden" name="prodWoId"/></td>
					  <td><input type="hidden" name="prodJobId"/></td>
					  <td><input type="hidden" name="prodChkdQty"/></td>
					  <td><input type="hidden" name="prodEmpDet"/></td>
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
