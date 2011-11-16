<%@ page language = "java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri = "/WEB-INF/struts-logic.tld" prefix = "logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<jsp:useBean id="frm" class="com.savantit.prodacs.presentation.tableadmin.production.ProductionAddForm" />
<jsp:setProperty name="frm" property="*" />

<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.StringTokenizer"%>
<%@ page import="java.util.GregorianCalendar"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<%@ page import="com.savantit.prodacs.facade.SessionProductionDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionProductionDetailsManager"%>
<%@ page import="com.savantit.prodacs.businessimplementation.production.ProductionDetails"%>
<%@ page import="com.savantit.prodacs.businessimplementation.production.EmployeeDtyOtDetails"%>
<%@ page import="com.savantit.prodacs.businessimplementation.securityadmin.UserAuthDetails"%>
<useradmin:userrights resource="1017"/>
<%
	if(frm.getFormAction().equalsIgnoreCase("addNew"))
		frm.setProDate("");
		frm.setResponse("");
	if (BuildConfig.DMODE)
		System.out.println("Production Add Form");
	HashMap empDetails = new HashMap(); /* For Employee Details and Type Details */
		HashMap shiftMachs = new HashMap();
	/* Getting the Production Entered Details */
	HashMap hm_Result = new HashMap();
	Vector vec_Success = new Vector();
	ProductionDetails[] objProdDetMachFail = null;
	ProductionDetails[] objProdDetEmpFail = null;
	Vector vec_MachineFail = new Vector();
	Vector vec_EmpFail = new Vector();
	Vector vec_DuplicateEntry = new Vector();
	Vector vec_DutyFail = new Vector();
	String prodList = "";
	UserAuthDetails userDetails = (UserAuthDetails)session.getAttribute("##userRights##");
	String userName = userDetails.getUserId();
	try
	{
		EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator

		/* 	Setting the JNDI name and Environment 	*/
	   	obj.setJndiName("SessionProductionDetailsManagerBean");
   		obj.setEnvironment();

		/* 	Creating the Home and Remote Objects 	*/
		SessionProductionDetailsManagerHome proHomeObj = (SessionProductionDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionProductionDetailsManagerHome.class);
		SessionProductionDetailsManager productionObj = (SessionProductionDetailsManager)PortableRemoteObject.narrow(proHomeObj.create(),SessionProductionDetailsManager.class);

		session.setAttribute("opnSno",frm.getSno());
		session.setAttribute("opnName",frm.getName());
		session.setAttribute("opnStdHrs",frm.getStdHrs());
		session.setAttribute("opnGpId",frm.getGrpId());
		session.setAttribute("opnGpCde",frm.getGpCde());

		/* For Loading WorkOrder */
		HashMap workOrderDet = productionObj.getWorkOrderList();
		pageContext.setAttribute("workOrderDet",workOrderDet);

		/* Loading Job Details by giving WorkOrder Id */
		Vector vecJobDet = new Vector();
		if (frm.getHidWrkOrdId() != "0")
		{
			vecJobDet = productionObj.getProdnJobDetailsByWorkOrder(Integer.parseInt(frm.getHidWrkOrdId()));
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
			vecJobOpnDet = productionObj.getProdnJobOperationDetails(Integer.parseInt(frm.getHidWrkOrdJobId()));
			if (BuildConfig.DMODE)
				System.out.println("VecSize: "+ vecJobOpnDet.size());
			pageContext.setAttribute("vecJobOpnDet",vecJobOpnDet);
		}
		else
		{
			pageContext.setAttribute("vecJobOpnDet",vecJobOpnDet);
		}


		/* Loading EmployeeType and Employees */
		empDetails = productionObj.getAllEmployeesByTypes();

		/* Finalizing the minimum required Quantities */
		HashMap minRqdEmp = productionObj.minReqdEmployees();
		Set set = minRqdEmp.entrySet();
		Iterator i = set.iterator();
		int c = 0;
		String minDet = "";
		while (i.hasNext())
		{
			Map.Entry me = (Map.Entry) i.next();
			Vector vec = new Vector();
			if (c != 0)
			{
				minDet = minDet + "^";
			}
			if (c == 0)
			{
				minDet = me.getKey()+"";
				vec = (Vector)me.getValue();
				for (int k = 0; k < vec.size(); k++)
				{
					minDet = minDet + "-" + vec.get(k);
				}
			}
			else
			{
				minDet = minDet + "" + me.getKey();
				vec = (Vector)me.getValue();
				for (int k = 0; k < vec.size(); k++)
				{
					minDet = minDet + "-" + vec.get(k);
				}
			}
			c++;
		}
		frm.setMinQty(minDet);
		if (BuildConfig.DMODE)
			System.out.println("Min Qty: "+ minDet);

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

			dateShift = productionObj.getShiftDefnNameList(gc.getTime());
			if (BuildConfig.DMODE)
				System.out.println("Shifts for Date(HM) :"+dateShift);
			pageContext.setAttribute("dateShift",dateShift);
		}
		else
		{
			pageContext.setAttribute("dateShift",dateShift);
		}


		if ((!frm.getProShift().equalsIgnoreCase("0")) && (!frm.getProDate().trim().equalsIgnoreCase("")))
		{
			StringTokenizer st = new StringTokenizer(frm.getProDate().trim(),"-");
			int yr = 0;
			int month = 0;
			int day = 0;
			if(st.hasMoreTokens())
			{
				yr = Integer.parseInt(st.nextToken().trim());
			}
			if(st.hasMoreTokens())
			{
				month = Integer.parseInt(st.nextToken().trim());
			}
			if(st.hasMoreTokens())
			{
				day = Integer.parseInt(st.nextToken().trim());
			}
			GregorianCalendar ge = new GregorianCalendar(yr,month-1,day);

			shiftMachs = productionObj.getAllProdMachines(ge.getTime(),Integer.parseInt(frm.getProShift()));
			if (BuildConfig.DMODE)
				System.out.println("Machines(HM):"+shiftMachs);
			pageContext.setAttribute("shiftMachs",shiftMachs);
		}
		else
		{
			pageContext.setAttribute("shiftMachs",shiftMachs);
		}

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
				if (BuildConfig.DMODE)
					System.out.println(count+". "+ arModDet[count]);
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

			dateShift = productionObj.getShiftDefnNameList(gc.getTime());
			pageContext.setAttribute("dateShift",dateShift);

			StringTokenizer st = new StringTokenizer(arModDet[0].trim(),"-");
			int yr = 0;
			int month = 0;
			int day = 0;
			if(st.hasMoreTokens())
			{
				yr = Integer.parseInt(st.nextToken().trim());
			}
			if(st.hasMoreTokens())
			{
				month = Integer.parseInt(st.nextToken().trim());
			}
			if(st.hasMoreTokens())
			{
				day = Integer.parseInt(st.nextToken().trim());
			}
			GregorianCalendar ge = new GregorianCalendar(yr,month-1,day);

			shiftMachs = productionObj.getAllProdMachines(ge.getTime(),Integer.parseInt(arModDet[9]));
			if (BuildConfig.DMODE)
				System.out.println("Machines(HM):"+shiftMachs);
			pageContext.setAttribute("shiftMachs",shiftMachs);
			
			vecJobDet = productionObj.getProdnJobDetailsByWorkOrder(Integer.parseInt(arModDet[10].trim()));
			pageContext.setAttribute("vecJobDet",vecJobDet);

			vecJobOpnDet = productionObj.getProdnJobOperationDetails(Integer.parseInt(arModDet[11].trim()));
			pageContext.setAttribute("vecJobOpnDet",vecJobOpnDet);
			Iterator it = shiftMachs.keySet().iterator();
		}


		/* Procedures for Making a Copy */
		String[] arCpyDet;
		if (frm.getFormAction().equalsIgnoreCase("makeCopy") && (request.getParameter("hidModDet") != null))
		{
			if (BuildConfig.DMODE)
				System.out.println("MakeCopy Array :"+request.getParameter("hidModDet"));
			StringTokenizer stCpyDet = new StringTokenizer(request.getParameter("hidModDet"),"#");
			arCpyDet = new String[stCpyDet.countTokens()];
			int count = 0;
			while (stCpyDet.hasMoreTokens())
			{
				arCpyDet[count] = stCpyDet.nextToken();
				if (BuildConfig.DMODE)
					System.out.println(count+". "+ arCpyDet[count]);
				count++;
			}

			StringTokenizer stDate = new StringTokenizer(arCpyDet[0].trim(),"-");
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

			dateShift = productionObj.getShiftDefnNameList(gc.getTime());
			pageContext.setAttribute("dateShift",dateShift);

			StringTokenizer st = new StringTokenizer(arCpyDet[0].trim(),"-");
			int yr = 0;
			int month = 0;
			int day = 0;
			if(st.hasMoreTokens())
			{
				yr = Integer.parseInt(st.nextToken().trim());
			}
			if(st.hasMoreTokens())
			{
				month = Integer.parseInt(st.nextToken().trim());
			}
			if(st.hasMoreTokens())
			{
				day = Integer.parseInt(st.nextToken().trim());
			}
			GregorianCalendar ge = new GregorianCalendar(yr,month-1,day);

			shiftMachs = productionObj.getAllProdMachines(ge.getTime(),Integer.parseInt(arCpyDet[9]));
			if (BuildConfig.DMODE)
				System.out.println("Machines(HM):"+shiftMachs);
			pageContext.setAttribute("shiftMachs",shiftMachs);
			
			vecJobDet = productionObj.getProdnJobDetailsByWorkOrder(Integer.parseInt(arCpyDet[10].trim()));
			pageContext.setAttribute("vecJobDet",vecJobDet);

			vecJobOpnDet = productionObj.getProdnJobOperationDetails(Integer.parseInt(arCpyDet[11].trim()));
			pageContext.setAttribute("vecJobOpnDet",vecJobOpnDet);
			Iterator it = shiftMachs.keySet().iterator();
		}
	}
	catch (Exception e)
	{
		if (BuildConfig.DMODE)
		{
			e.printStackTrace();
			System.out.println("Problem in Production Add.");
		}
	}
%>

<html:html>
<head>
<title><bean:message key="prodacs.production.header"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script src='<bean:message key="context"/>/library/jscripts.js'></script>
<script src='<bean:message key="context"/>/library/calendar_previous_submit.js'></script>
<script src='<bean:message key="context"/>/library/prototype.js'></script>
<script>
var oPopup;
var req;
var flag  = 0;
	function init()
	 {
		oPopup = window.createPopup();
	 }

	function dateSubmit()
	{
		var temp = document.forms[0];
		var proDateValue = temp.proDate.value;
		for(var x = 1; x < temp.proMachine.length; x++)
		{
			while (temp.proMachine.options[x] != null)
				temp.proMachine.options[x] = null;
		}
		if(proDateValue != "0")
		{
			var url = '/ProDACS/servlet/proservlet';
			var value = 1;
			var param = 'proDate='+proDateValue+'&value='+value;
			if (window.XMLHttpRequest) // Non-IE browsers
			{
				req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: showResponse});
			}
			else if (window.ActiveXObject) // IE
			{
				req = new ActiveXObject("Microsoft.XMLHTTP");
				if (req)
				{
					req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: showResponse});
				}
			}
		}
		else
		{
			alert("Production Date cannot be Empty!");
			return false;
		}
	}

	function showResponse(req)
	{
		//Processing the returned Text and replacing the existing <select> with response
		document.getElementById('shiftName').innerHTML = req.responseText;
	}

	function showResponse1(req)
	{
		//Processing the returned Text and replacing the existing <select> with response
		document.getElementById('machNames').innerHTML = req.responseText;
	}

	function loadMachines()
	{
		var temp = document.forms[0];
		var proDateValue = temp.proDate.value;
		var sftIndex = temp.proShift.selectedIndex;

		if((proDateValue != "") && (sftIndex != "0"))
		{
			var url = '/ProDACS/servlet/proservlet';
			var value = 2;
			var param = 'proDate='+proDateValue+'&shiftId='+sftIndex+'&value='+value;
			if (window.XMLHttpRequest) // Non-IE browsers
			{
				req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: showResponse1});
			}
			else if (window.ActiveXObject) // IE
			{
				req = new ActiveXObject("Microsoft.XMLHTTP");
				if (req)
				{
					req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: showResponse1});
				}
			}
		}
		else
		{
		alert("Production Shift cannot be Empty!");
		return false;
		}

		 /*if (temp.proShift.value != "0" && temp.formAction.value == "modify")
		 {
			 temp.showCount.value = "7";
			 temp.hidModDet.value = '<%= request.getParameter("hidModDet") %>';
			 temp.formAction.value = "modify";
			 loadProdList();
			 temp.submit();
		 }
		 else if (temp.proShift.value != "0")
		 {
			 temp.formAction.value = "dateSubmit";
			 loadProdList();
			 temp.submit();
		 }*/
	}

	 function clearAll()
	 {
		 var temp = document.forms[0];
		 temp.formAction.value = "";
		 temp.proShift.options[temp.proShift.selectedIndex].value = 0;
		 temp.proMachine.options[temp.proMachine.selectedIndex].value = 0;
	 }

	function listItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/Production/ProductionList.jsp';
		document.forms[0].submit();
	}

	function addItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/Production/ProductionAdd.jsp?formAction=addNew';
		document.forms[0].submit();
	}

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

	/* To check all the Checkbox in Employee Details */
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

	/* This is for showing the Date, Shift, Machine Name and indicate the invalid Things */
	function hideNshowDateShiftMachine()
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

		/* For Displaying the Machine name else indicate a Alert */
		if (temp.proMachine.options[temp.proMachine.selectedIndex].value != "0")
		{
			obj.children(8).innerText = temp.proMachine.options[temp.proMachine.selectedIndex].text;
		}
		else
		{
			alert ("Please Select a Machine Name for Production! ");
			prodShftRow.style.display='none';
			prodInfo.style.display='block';
			return false;
		}

		/* All the Details are correct then Show the Next Job Details */
		if ((temp.showCount.value == "1") && ((temp.proShift.options[temp.proShift.selectedIndex].value == "0")
			 || (temp.proMachine.options[temp.proMachine.selectedIndex].value == "0")))
		{
			prodShftRow.style.display='none';
			prodInfo.style.display='block';
			alert("Select a Machine Name/Shift for Production!");
			return false;
		}
		temp.showCount.value = "1";
		temp.formAction.value = "post";
		loadProdList();
		temp.submit();
	}

	function showHideTables()
	{
		var temp = document.forms[0];
		if (temp.showCount.value == "2" || temp.showCount.value == "3")
		{
			wrkOrder.style.display = 'none';
			jobQty.style.display = 'none';
		}
	}

	/* Loading Job Details by WorkOrderId */
	function loadJobDetByWOId(code)
	{
		var temp = document.forms[0];
		if ((temp.formAction.value == "makeCopy") && (temp.showCount.value == "6"))
		{
			wrkOrder.style.display = 'block';
		}
		else
		{
			wrkOrder.style.display = 'none';
		}
		var obj = document.getElementById('tblWorkOrdInfo');
		tblWorkOrdInfo.style.display = 'block';
		if(obj.children(0).children.length > 2)
		{
			for (var i = 1; i < obj.children(0).children.length; i++)
			{
				if (i == 1)
				{
					obj.children(0).children(1).children(0).children(0).value = 0;
					obj.children(0).children(1).children(0).children(0).checked = true;
					obj.children(0).children(1).children(1).children(0).value = "";
					obj.children(0).children(1).children(2).children(0).value = "";
					obj.children(0).children(1).children(3).children(0).value = "";
					obj.children(0).children(1).children(4).children(0).value = "";
					obj.children(0).children(1).children(5).children(0).value = "";
					obj.children(0).children(1).children(6).children(0).value = "";
				}
				else
				{
					obj.children(0).removeChild(obj.children(0).children(i));
					i = 1;
				}
			}
		}
		else
		{
			obj.children(0).children(1).children(0).children(0).value = 0;
			obj.children(0).children(1).children(0).children(0).checked = true;
			obj.children(0).children(1).children(1).children(0).value = "";
			obj.children(0).children(1).children(2).children(0).value = "";
			obj.children(0).children(1).children(3).children(0).value = "";
			obj.children(0).children(1).children(4).children(0).value = "";
			obj.children(0).children(1).children(5).children(0).value = "";
			obj.children(0).children(1).children(6).children(0).value = "";
		}
		if (code != "0")
		{
			var url = '/ProDACS/servlet/proservlet';
			var value = 3;
			var param = 'wrkOrdId='+code+'&value='+value;
			if (window.XMLHttpRequest) // Non-IE browsers
			{
				req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: showResponse2});
			}
			else if (window.ActiveXObject) // IE
			{
				req = new ActiveXObject("Microsoft.XMLHTTP");
				if (req)
				{
					req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: showResponse2});
				}
			}
			temp.hidChkdJobId.value = "0";
		}
		else
		{
			wrkOrder.style.display = 'none';
			alert ("Select a WorkOrder to View its Jobs!");
			return false;
		}
	}
	
	function showResponse2(req)
	{
		var temp = document.forms[0];
		var obj = document.getElementById('tblWorkOrdInfo');
		temp.response.value = req.responseText;
		var respText = (temp.response.value).split("^");
		for (var i = 0; i < respText.length; i++)
		{
			var arRespText = respText[i].split("~");

			if ((obj.children(0).children.length == 2) && (obj.children(0).children(1).children(1).children(0).value == ""))
			{
				obj.children(0).children(1).children(0).children(0).value = arRespText[0];
				if (obj.children(0).children(1).children(0).children(0).value == temp.hidWrkOrdJobId.value)
					obj.children(0).children(1).children(0).children(0).checked = true;
				else if ((obj.children(0).children(1).children(0).children(0).value != temp.hidWrkOrdJobId.value) && (temp.hidWrkOrdJobId.value == 0))
					obj.children(0).children(1).children(0).children(0).checked = false;
				else
					obj.children(0).children(1).children(0).children(0).checked = true;
				obj.children(0).children(1).children(1).children(0).value = arRespText[1];
				if (obj.children(0).children(1).children(0).children(0).checked)
				{
					if ((temp.hidChkdJobName.value != "") || (obj.children(0).children(1).children(1).children(0).value))
						temp.hidChkdJobName.value = obj.children(0).children(1).children(1).children(0).value;
				}
				obj.children(0).children(1).children(2).children(0).value = arRespText[2];
				obj.children(0).children(1).children(3).children(0).value = arRespText[3];
				obj.children(0).children(1).children(4).children(0).value = arRespText[4];
				obj.children(0).children(1).children(5).children(0).value = arRespText[5];
				obj.children(0).children(1).children(6).children(0).value = arRespText[6];
			}
			else
			{
				var newNode = obj.children(0).children(1).cloneNode(true);
				obj.children(0).appendChild(newNode);
				var len = obj.children(0).children.length;

				obj.children(0).children(len-1).children(0).children(0).value = 0;
				obj.children(0).children(len-1).children(1).children(0).value = "";
				obj.children(0).children(len-1).children(2).children(0).value = "";
				obj.children(0).children(len-1).children(3).children(0).value = "";
				obj.children(0).children(len-1).children(4).children(0).value = "";
				obj.children(0).children(len-1).children(5).children(0).value = "";
				obj.children(0).children(len-1).children(6).children(0).value = "";

				obj.children(0).children(len-1).children(0).children(0).value = arRespText[0];
				if (obj.children(0).children(len-1).children(0).children(0).value == temp.hidWrkOrdJobId.value)
					obj.children(0).children(len-1).children(0).children(0).checked = true;
				else
					obj.children(0).children(len-1).children(0).children(0).checked = false;
				obj.children(0).children(len-1).children(1).children(0).value = arRespText[1];
				if (obj.children(0).children(len-1).children(0).children(0).checked)
				{
					if ((temp.hidChkdJobName.value != "") || (obj.children(0).children(len-1).children(1).children(0).value))
						temp.hidChkdJobName.value = obj.children(0).children(len-1).children(1).children(0).value;
				}
				obj.children(0).children(len-1).children(2).children(0).value = arRespText[2];
				obj.children(0).children(len-1).children(3).children(0).value = arRespText[3];
				obj.children(0).children(len-1).children(4).children(0).value = arRespText[4];
				obj.children(0).children(len-1).children(5).children(0).value = arRespText[5];
				obj.children(0).children(len-1).children(6).children(0).value = arRespText[6];
			}
		}
		//alert("WoLoad :"+temp.hidChkdJobName.value);
		if ((temp.formAction.value != "modify") && (temp.formAction.value != "makeCopy"))
		{
			obj.children(0).children(1).children(0).children(0).checked = true;
			temp.formAction.value = "getJobDetails";
			temp.showCount.value = "2";
		}
		else
		{
			var obj2 = document.getElementById("tblProdWrkOrd");
			obj2.children(2).innerText = temp.proWorkOrderHash.options[temp.proWorkOrderHash.selectedIndex].text;
			obj2.children(5).innerText = temp.hidChkdJobName.value;
		}
	}

	/* Showing the tables according to Entries in the Production Add Screen by Onload event*/
	function showJobDet()
	{
		var temp = document.forms[0];
		/*alert("-"+temp.formAction.value+"-");
		alert ("temp.showCount.value: "+temp.showCount.value);
		alert('<%= request.getParameter("response") %>');*/
		temp.hidUserId.value = '<%= userName %>';

		if ('<%= request.getParameter("formAction")%>' != "addNew")
		{
			temp.response.value = '<%= request.getParameter("response") %>';
		}
		else
		{
			temp.response.value = "";
		}

		if (temp.formAction.value == "add")
		{
			wrkOrder.style.display='none';
			return false;
		}
		if (temp.showCount.value == "")
		{
			prodInfo.style.display='block';
			prodShftRow.style.display='none';
		}
		else if (temp.showCount.value == "1")
		{
			var obj1 = document.getElementById('tblWorkOrdInfo');

			if ((temp.proShift.options[temp.proShift.selectedIndex].value != "0") && (temp.proMachine.options[temp.proMachine.selectedIndex].value != "0"))
			{
				prodShftRow.style.display='block';
			}
			else
			{
				prodShftRow.style.display='none';
			}

			/* Validation for updating the Date, Shift and Machine Names */
			var obj = document.getElementById("tblProdShft");
			obj.children(2).innerText = temp.proDate.value;
			obj.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;
			obj.children(8).innerText = temp.proMachine.options[temp.proMachine.selectedIndex].text;

			/*To select the first radio button by default*/
			if (temp.formAction.value == "post")
			{
				tblWorkOrdInfo.style.display = 'none';
			}

			var respText = ('<%= request.getParameter("response") %>').split("^");
			for (var i = 0; i < respText.length; i++)
			{
				var arRespText = respText[i].split("~");

				if ((obj1.children(0).children.length == 2) && (obj1.children(0).children(1).children(0).children(0).value == ""))
				{
					obj1.children(0).children(1).children(0).children(0).value = arRespText[0];
					obj1.children(0).children(1).children(1).children(0).value = arRespText[1];
					obj1.children(0).children(1).children(2).children(0).value = arRespText[2];
					obj1.children(0).children(1).children(3).children(0).value = arRespText[3];
					obj1.children(0).children(1).children(4).children(0).value = arRespText[4];
					obj1.children(0).children(1).children(5).children(0).value = arRespText[5];
					obj1.children(0).children(1).children(6).children(0).value = arRespText[6];
				}
				else
				{
					var newNode = obj1.children(0).children(1).cloneNode(true);
					obj1.children(0).appendChild(newNode);
					var len = obj1.children(0).children.length;

					obj1.children(0).children(len-1).children(0).children(0).value = "";
					obj1.children(0).children(len-1).children(1).children(0).value = "";
					obj1.children(0).children(len-1).children(2).children(0).value = "";
					obj1.children(0).children(len-1).children(3).children(0).value = "";
					obj1.children(0).children(len-1).children(4).children(0).value = "";
					obj1.children(0).children(len-1).children(5).children(0).value = "";
					obj1.children(0).children(len-1).children(6).children(0).value = "";

					obj1.children(0).children(len-1).children(0).children(0).value = arRespText[0];
					obj1.children(0).children(len-1).children(1).children(0).value = arRespText[1];
					obj1.children(0).children(len-1).children(2).children(0).value = arRespText[2];
					obj1.children(0).children(len-1).children(3).children(0).value = arRespText[3];
					obj1.children(0).children(len-1).children(4).children(0).value = arRespText[4];
					obj1.children(0).children(len-1).children(5).children(0).value = arRespText[5];
					obj1.children(0).children(len-1).children(6).children(0).value = arRespText[6];
				}
			}
			if (temp.rdWrkOrd == undefined)
				return false;
			if ((temp.rdWrkOrd.length != undefined) && (tblWorkOrdInfo.style.display != 'none'))
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
			prodInfo.style.display = 'none';
			prodShftRow.style.display='block';
			wrkOrder.style.display='block';
			
			if ((temp.formAction.value == "getJobDetails") || (temp.formAction.value == "makeCopy"))
			{
				temp.proStartOperation.value = "";
				temp.proEndOperation.value = "";
			}
			/* Validation for updating Date, Shift, Machine Name, WorkOrderNo and Job name */
			var obj = document.getElementById("tblProdShft");
			obj.children(2).innerText = temp.proDate.value;
			obj.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;
			obj.children(8).innerText = temp.proMachine.options[temp.proMachine.selectedIndex].text;

			var obj1 = document.getElementById("tblProdShft2");
			obj1.children(2).innerText = temp.proDate.value;
			obj1.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;
			obj1.children(8).innerText = temp.proMachine.options[temp.proMachine.selectedIndex].text;

			var obj2 = document.getElementById("tblProdWrkOrd");
			obj2.children(2).innerText = temp.proWorkOrderHash.options[temp.proWorkOrderHash.selectedIndex].text;
			obj2.children(5).innerText = temp.hidChkdJobName.value;

			temp.proNmlorRwk[0].checked = true; /* Normal Radio is Checked by Default */

			var obj = document.getElementById('tblWorkOrdInfo');
			//temp.response.value = req.responseText;
			var respText = ('<%= request.getParameter("response") %>').split("^");
			for (var i = 0; i < respText.length; i++)
			{
				var arRespText = respText[i].split("~");
				if ((obj.children(0).children.length == 2) && (obj.children(0).children(1).children(1).children(0).value == ""))
				{
					obj.children(0).children(1).children(0).children(0).value = arRespText[0];
					obj.children(0).children(1).children(1).children(0).value = arRespText[1];
					obj.children(0).children(1).children(2).children(0).value = arRespText[2];
					obj.children(0).children(1).children(3).children(0).value = arRespText[3];
					obj.children(0).children(1).children(4).children(0).value = arRespText[4];
					obj.children(0).children(1).children(5).children(0).value = arRespText[5];
					obj.children(0).children(1).children(6).children(0).value = arRespText[6];
				}
				else
				{
					var newNode = obj.children(0).children(1).cloneNode(true);
					obj.children(0).appendChild(newNode);
					var len = obj.children(0).children.length;

					obj.children(0).children(len-1).children(0).children(0).value = "";
					obj.children(0).children(len-1).children(1).children(0).value = "";
					obj.children(0).children(len-1).children(2).children(0).value = "";
					obj.children(0).children(len-1).children(3).children(0).value = "";
					obj.children(0).children(len-1).children(4).children(0).value = "";
					obj.children(0).children(len-1).children(5).children(0).value = "";
					obj.children(0).children(len-1).children(6).children(0).value = "";

					obj.children(0).children(len-1).children(0).children(0).value = arRespText[0];
					obj.children(0).children(len-1).children(1).children(0).value = arRespText[1];
					obj.children(0).children(len-1).children(2).children(0).value = arRespText[2];
					obj.children(0).children(len-1).children(3).children(0).value = arRespText[3];
					obj.children(0).children(len-1).children(4).children(0).value = arRespText[4];
					obj.children(0).children(len-1).children(5).children(0).value = arRespText[5];
					obj.children(0).children(len-1).children(6).children(0).value = arRespText[6];
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
		else if (temp.showCount.value == "3")
		{
			prodInfo.style.display = 'block';
			prodShftRow.style.display = 'none';
			wrkOrder.style.display = 'none';
			jobQty.style.display = 'block';
			
			if (temp.formAction.value == "isRadl")
			{
				temp.proTotalHours.value = "";
				if (temp.proEmployeeName.length != undefined)
				{
					for (var x = 0; x < temp.proEmployeeName.length; x++)
					{
						temp.proEmployeeName[x].selectedIndex = 0;
						temp.proDutyHrs[x].value = "";
						temp.proOTHrs[x].value = "";
					}
				}
				else
				{
					temp.proEmployeeName.selectedIndex = 0;
					temp.proDutyHrs.value = "";
					temp.proOTHrs.value = "";
				}
			}

			var obj = document.getElementById("tblProdShft");
			obj.children(2).innerText = temp.proDate.value;
			obj.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;
			obj.children(8).innerText = temp.proMachine.options[temp.proMachine.selectedIndex].text;

			var obj1 = document.getElementById("tblProdShft2");
			obj1.children(2).innerText = temp.proDate.value;
			obj1.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;
			obj1.children(8).innerText = temp.proMachine.options[temp.proMachine.selectedIndex].text;

			var obj2 = document.getElementById("tblProdWrkOrd");
			obj2.children(2).innerText = temp.proWorkOrderHash.options[temp.proWorkOrderHash.selectedIndex].text;
			obj2.children(5).innerText = temp.hidChkdJobName.value;

			var obj = document.getElementById("tblProdShft3");
			obj.children(2).innerText = temp.proDate.value;
			obj.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;
			obj.children(8).innerText = temp.proMachine.options[temp.proMachine.selectedIndex].text;

			var obj1 = document.getElementById("tblProdWrkOrd2");
			obj1.children(2).innerText = temp.proWorkOrderHash.options[temp.proWorkOrderHash.selectedIndex].text;
			obj1.children(5).innerText = temp.hidChkdJobName.value;
			obj1.children(8).innerText = (temp.proNmlorRwk[0].checked)?"Normal":"Rework";

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

			var obj = document.getElementById('tblWorkOrdInfo');
			//temp.response.value = req.responseText;
			var respText = ('<%= request.getParameter("response") %>').split("^");
			for (var i = 0; i < respText.length; i++)
			{
				var arRespText = respText[i].split("~");

				if ((obj.children(0).children.length == 2) && (obj.children(0).children(1).children(1).children(0).value == ""))
				{
					obj.children(0).children(1).children(0).children(0).value = arRespText[0];
					obj.children(0).children(1).children(1).children(0).value = arRespText[1];
					obj.children(0).children(1).children(2).children(0).value = arRespText[2];
					obj.children(0).children(1).children(3).children(0).value = arRespText[3];
					obj.children(0).children(1).children(4).children(0).value = arRespText[4];
					obj.children(0).children(1).children(5).children(0).value = arRespText[5];
					obj.children(0).children(1).children(6).children(0).value = arRespText[6];
				}
				else
				{
					var newNode = obj.children(0).children(1).cloneNode(true);
					obj.children(0).appendChild(newNode);
					var len = obj.children(0).children.length;

					obj.children(0).children(len-1).children(0).children(0).value = "";
					obj.children(0).children(len-1).children(1).children(0).value = "";
					obj.children(0).children(len-1).children(2).children(0).value = "";
					obj.children(0).children(len-1).children(3).children(0).value = "";
					obj.children(0).children(len-1).children(4).children(0).value = "";
					obj.children(0).children(len-1).children(5).children(0).value = "";
					obj.children(0).children(len-1).children(6).children(0).value = "";

					obj.children(0).children(len-1).children(0).children(0).value = arRespText[0];
					obj.children(0).children(len-1).children(1).children(0).value = arRespText[1];
					obj.children(0).children(len-1).children(2).children(0).value = arRespText[2];
					obj.children(0).children(len-1).children(3).children(0).value = arRespText[3];
					obj.children(0).children(len-1).children(4).children(0).value = arRespText[4];
					obj.children(0).children(len-1).children(5).children(0).value = arRespText[5];
					obj.children(0).children(len-1).children(6).children(0).value = arRespText[6];
				}
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
		else if (temp.showCount.value == "5")
		{
			prodInfo.style.display = 'block';
			prodShftRow.style.display = 'block';
			wrkOrder.style.display = 'block';
			jobQty.style.display = 'block';

			var modDet = ('<%= request.getParameter("hidModDet") %>').split("#");
			temp.proDate.value = modDet[0]; // Date

			for (var i = 0; i < temp.proShift.length; i++) // Shift
			{
				if (temp.proShift.options[i].value == modDet[9])
				{
					temp.proShift.selectedIndex = i;
				}
			}

			for (var i = 0; i < temp.proMachine.length; i++) // Machine
			{
				if (temp.proMachine.options[i].value == modDet[2])
				{
					temp.proMachine.selectedIndex = i;
				}
			}
			/* Validation for updating the Date, Shift and Machine Names */
			var obj = document.getElementById("tblProdShft");
			obj.children(2).innerText = temp.proDate.value;
			obj.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;
			obj.children(8).innerText = temp.proMachine.options[temp.proMachine.selectedIndex].text;

			for (var i = 0; i < temp.proWorkOrderHash.length; i++) // WoNo
			{
				if (temp.proWorkOrderHash.options[i].value == modDet[10])
				{
					temp.proWorkOrderHash.selectedIndex = i;
				}
			}
			loadJobDetByWOId(modDet[10]);

			var obj = document.getElementById("tblProdShft2");
			obj.children(2).innerText = temp.proDate.value;
			obj.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;
			obj.children(8).innerText = temp.proMachine.options[temp.proMachine.selectedIndex].text;

			if (modDet[5] == "Normal") // Normal or Rework
				temp.proNmlorRwk[0].checked = true;
			else
				temp.proNmlorRwk[1].checked = true;

			/* To Check the Job Operation Quantities */
			var jbQty = modDet[12].split(",");
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

			temp.proStartOperation.value = modDet[6]; // Start Operation
			temp.proEndOperation.value = modDet[7]; // End Operation

			var obj1 = document.getElementById("tblProdWrkOrd2");
			obj1.children(2).innerText = temp.proWorkOrderHash.options[temp.proWorkOrderHash.selectedIndex].text;
			obj1.children(5).innerText = temp.hidChkdJobName.value;
			obj1.children(8).innerText = (temp.proNmlorRwk[0].checked)?"Normal":"Rework";

			var obj2 = document.getElementById("tblProdJobOty");
			obj2.children(2).innerText = modDet[12];
			obj2.children(5).innerText = temp.proStartOperation.value;
			obj2.children(8).innerText = temp.proEndOperation.value;

			temp.proTotalHours.value = modDet[8];// Total Hours

			/* For Employee Detalis */
			var obj = document.getElementById('tblEmpDetails');
			var empDet = modDet[13].split("^");
			for (var i = 0; i < empDet.length; i++)
			{
				var empPrnlDet = empDet[i].split("-");
				if (i == 0)
				{
					obj.children(i).children(0).children(1).children(0).value = empPrnlDet[0];
					obj.children(i).children(0).children(2).children(0).value = empPrnlDet[1];
					obj.children(i).children(0).children(3).children(0).value = empPrnlDet[2];
					obj.children(i).children(0).children(4).children(0).value = empPrnlDet[3];
					obj.children(i).children(0).children(5).children(0).value = empPrnlDet[4];
					obj.children(i).children(0).children(6).children(0).value = empPrnlDet[5];
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
					obj.children(len-1).children(0).children(5).children(0).value = empPrnlDet[4];
					obj.children(len-1).children(0).children(6).children(0).value = empPrnlDet[5];
				}
			}
		}
		else if (temp.showCount.value == "6")
		{
			prodInfo.style.display = 'block';
			prodShftRow.style.display = 'block';
			wrkOrder.style.display = 'block';
			jobQty.style.display = 'block';
			var modDet = ('<%= request.getParameter("hidModDet") %>').split("#");
			temp.proDate.value = modDet[0]; // Date

			for (var i = 0; i < temp.proShift.length; i++) // Shift
			{
				if (temp.proShift.options[i].value == modDet[9])
				{
					temp.proShift.selectedIndex = i;
				}
			}

			for (var i = 0; i < temp.proMachine.length; i++) // Machine
			{
				if (temp.proMachine.options[i].value == modDet[2])
				{
					temp.proMachine.selectedIndex = i;
				}
			}
			/* Validation for updating the Date, Shift and Machine Names */
			var obj = document.getElementById("tblProdShft");
			obj.children(2).innerText = temp.proDate.value;
			obj.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;
			obj.children(8).innerText = temp.proMachine.options[temp.proMachine.selectedIndex].text;

			for (var i = 0; i < temp.proWorkOrderHash.length; i++) // WoNo
			{
				if (temp.proWorkOrderHash.options[i].value == modDet[10])
				{
					temp.proWorkOrderHash.selectedIndex = i;
				}
			}
			loadJobDetByWOId(modDet[10]);

			var obj = document.getElementById("tblProdShft2");
			obj.children(2).innerText = temp.proDate.value;
			obj.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;
			obj.children(8).innerText = temp.proMachine.options[temp.proMachine.selectedIndex].text;

			if (modDet[5] == "Normal") // Normal or Rework
				temp.proNmlorRwk[0].checked = true;
			else
				temp.proNmlorRwk[1].checked = true;

			/* To Check the Job Operation Quantities */
			var jbQty = modDet[12].split(",");
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

			temp.proStartOperation.value = modDet[6]; // Start Operation
			temp.proEndOperation.value = modDet[7]; // End Operation

			var obj1 = document.getElementById("tblProdWrkOrd2");
			obj1.children(2).innerText = temp.proWorkOrderHash.options[temp.proWorkOrderHash.selectedIndex].text;
			obj1.children(5).innerText = temp.hidChkdJobName.value;
			obj1.children(8).innerText = (temp.proNmlorRwk[0].checked)?"Normal":"Rework";

			var obj2 = document.getElementById("tblProdJobOty");
			obj2.children(2).innerText = modDet[12];
			obj2.children(5).innerText = temp.proStartOperation.value;
			obj2.children(8).innerText = temp.proEndOperation.value;

			temp.proTotalHours.value = modDet[8];// Total Hours

			/* For Employee Detalis */
			var obj = document.getElementById('tblEmpDetails');
			var obj1 = document.getElementById('tblEmpDetMstr');
			var empDet = modDet[13].split("^");
			var empDet1 = modDet[13].split("^");
			var count = 0;
			var flg = false;
			var empDetSep = "";
			var empDet1Sep = "";
			for (var x = 0; x < empDet.length; x++)
			{
				for (var y = 0; y < empDet1.length; y++)
				{
					empDetSep = empDet[x].split("-");
					empDet1Sep = empDet1[y].split("-");
					if(empDetSep[0] == empDet1Sep[0])
					{
						count++;
					}
				}
				if (count > 1)
				{
					flg = true;
					break;
				}
				else
				{
					count = 0;
				}
			}
			if (flg == true)
			{
				for (var i = 0; i < empDet.length; i++)
				{
					var empPrnlDet = empDet[i].split("-");
					if (i == 0)
					{
						obj.children(i).children(0).children(1).children(0).value = empPrnlDet[0];
						obj.children(i).children(0).children(2).children(0).value = empPrnlDet[1];
						obj.children(i).children(0).children(3).children(0).value = empPrnlDet[2];
						obj.children(i).children(0).children(4).children(0).value = empPrnlDet[3];
						obj.children(i).children(0).children(5).children(0).value = empPrnlDet[4];
						obj.children(i).children(0).children(6).children(0).value = empPrnlDet[5];
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
						obj.children(len-1).children(0).children(5).children(0).value = empPrnlDet[4];
						obj.children(len-1).children(0).children(6).children(0).value = empPrnlDet[5];
					}
				}
			}
			else
			{
				var empPrnlDet1 = new Array();
				for (b = 1; b < obj1.children(0).children.length; b++)
				{
					for (var a = 0; a < empDet1.length; a++)
					{
						empPrnlDet1 = empDet1[a].split("-");
						if (empPrnlDet1[0] == obj1.children(0).children(b).children(0).children(0).value)
						{
							obj1.children(0).children(b).children(1).children(0).value = empPrnlDet1[5];
							obj1.children(0).children(b).children(2).children(0).value = empPrnlDet1[2];
							obj1.children(0).children(b).children(3).children(0).value = empPrnlDet1[3];
							obj1.children(0).children(b).children(4).children(0).value = b;
							obj1.children(0).children(b).children(5).children(0).value = empPrnlDet1[4];
						}
					}
				}
			}
		}
		else if ((temp.showCount.value == "7") && (temp.formAction.value == "modify") )/*&& ('<%= request.getParameter("hidModDet") %>' != "")*/
		{
			prodInfo.style.display = 'block';
			prodShftRow.style.display = 'block';
			wrkOrder.style.display = 'block';
			jobQty.style.display = 'block';
			
			var modDet = ('<%= request.getParameter("hidModDet") %>').split("#");
			temp.proDate.value = modDet[0]; // Date

			for (var i = 0; i < temp.proMachine.length; i++) // Machine
			{
				if (temp.proMachine.options[i].value == modDet[2])
					temp.proMachine.selectedIndex = i;
			}

			/* Validation for updating the Date, Shift and Machine Names */
			var obj = document.getElementById("tblProdShft");
			obj.children(2).innerText = temp.proDate.value;
			obj.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;
			obj.children(8).innerText = temp.proMachine.options[temp.proMachine.selectedIndex].text;

			for (var i = 0; i < temp.proWorkOrderHash.length; i++) // WoNo
			{
				if (temp.proWorkOrderHash.options[i].value == modDet[10])
				{
					temp.proWorkOrderHash.selectedIndex = i;
				}
			}

			/* Check the Particular Job */
			var obj = document.getElementById('tblWorkOrdInfo');
			for (var i = 1; i < obj.children(0).children.length; i++)
			{
				if (obj.children(0).children(i).children(0).children(0).value == modDet[11])
				{
					obj.children(0).children(i).children(0).children(0).checked = true;
					temp.hidChkdJobName.value = obj.children(0).children(i).children(1).innerText;
				}
			}
			var obj = document.getElementById("tblProdShft2");
			obj.children(2).innerText = temp.proDate.value;
			obj.children(5).innerText = temp.proShift.options[temp.proShift.selectedIndex].text;
			obj.children(8).innerText = temp.proMachine.options[temp.proMachine.selectedIndex].text;

			var obj2 = document.getElementById("tblProdWrkOrd");
			obj2.children(2).innerText = temp.proWorkOrderHash.options[temp.proWorkOrderHash.selectedIndex].text;
			obj2.children(5).innerText = temp.hidChkdJobName.value;

			if (modDet[5] == "Normal") // Normal or Rework
				temp.proNmlorRwk[0].checked = true;
			else
				temp.proNmlorRwk[1].checked = true;

			/* To Check the Job Operation Quantities */
			var jbQty = modDet[12].split(",");
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

			temp.proStartOperation.value = modDet[6]; // Start Operation
			temp.proEndOperation.value = modDet[7]; // End Operation

			var obj1 = document.getElementById("tblProdWrkOrd2");
			obj1.children(2).innerText = temp.proWorkOrderHash.options[temp.proWorkOrderHash.selectedIndex].text;
			obj1.children(5).innerText = temp.hidChkdJobName.value;
			obj1.children(8).innerText = (temp.proNmlorRwk[0].checked)?"Normal":"Rework";

			var obj2 = document.getElementById("tblProdJobOty");
			obj2.children(2).innerText = modDet[12];
			obj2.children(5).innerText = temp.proStartOperation.value;
			obj2.children(8).innerText = temp.proEndOperation.value;

			temp.proTotalHours.value = modDet[8];// Total Hours

			/* For Employee Detalis */
			var obj = document.getElementById('tblEmpDetails');
			var empDet = modDet[13].split("^");
			for (var i = 0; i < empDet.length; i++)
			{
				var empPrnlDet = empDet[i].split("-");
				if (i == 0)
				{
					obj.children(i).children(0).children(1).children(0).value = empPrnlDet[0];
					obj.children(i).children(0).children(2).children(0).value = empPrnlDet[1];
					obj.children(i).children(0).children(3).children(0).value = empPrnlDet[2];
					obj.children(i).children(0).children(4).children(0).value = empPrnlDet[3];
					obj.children(i).children(0).children(5).children(0).value = empPrnlDet[4];
					obj.children(i).children(0).children(6).children(0).value = empPrnlDet[5];
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
					obj.children(len-1).children(0).children(5).children(0).value = empPrnlDet[4];
					obj.children(len-1).children(0).children(6).children(0).value = empPrnlDet[5];
				}
			}
		}
		
	}

	/* Showing JobOperDetails by hiding other 2 tables */
	function hideNshowJobOpnDet()
	{
		var obj = document.getElementById("tblWorkOrdInfo");
		var temp = document.forms[0];
		var flag = 0;
		if ((temp.proDate.value == "") || (temp.proShift.value == "0") || (temp.proMachine.value == "0"))
		{
			alert("Some of the value(s) might be empty!");
			return false;
		}

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
		loadProdList();
		temp.submit();
	}

	/* Hiding the JobDetails, JobOperation Details and show the Employee Details */
	function hideNshowEmpDetails()
	{
		var temp = document.forms[0];
		var object = document.getElementById("tblWorkOrdInfo");
		var obj2 = document.getElementById("tblProdWrkOrd2");
		if ((temp.proDate.value == "") || (temp.proShift.value == "0") || (temp.proMachine.value == "0"))
		{
			alert("Some of the value(s) might be empty!");
			return false;
		}

		for (var x = 1; x < object.children(0).children.length; x++)
		{
			if (object.children(0).children(x).children(0).children(0).checked)
			{
				if (object.children(0).children(x).children(1).children(0).value != temp.hidChkdJobName.value)
				{
					alert("Select the correct Job Name to continue!");
					return false;
				}
			}
		}

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
				var rewkOpns = (obj.children(0).children(i).children(3).children(0).value).split(',');
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

				for (var x = start; x <= end; x++)
				{
					for (var y = 0; y < rewkOpns.length; y++)
					{
						if ((x == rewkOpns[y]) && (temp.proNmlorRwk[0].checked))
						{
							alert ("Sorry! In Normal Work, U can't enter Rework Operations!");
							return false;
						}
					}
				}
			}
			i++;
		}
		temp.proTotQtySnos.value = totQty;
		temp.hidWoJbStIds.value = woJbStatIds;

		var object = document.getElementById("tblBufferList");
		var buffStartOpn = "";
		var buffEndOpn = "";
		var chkdQtys = "";
		var chkdWoJbStatIds = "";

		if (object.children.length != undefined)
		{
			for (var i = 0; i < object.children.length; i++)
			{
				if (i == 0)
				{
					buffStartOpn = parseInt(object.children(0).children(0).children(9).children(0).value);
					buffEndOpn = parseInt(object.children(0).children(0).children(10).children(0).value);
					chkdQtys = object.children(0).children(0).children(15).children(0).value;
					chkdWoJbStatIds = object.children(0).children(0).children(17).children(0).value;
				}
				else
				{
					buffStartOpn = buffStartOpn + "," + parseInt(object.children(i).children(0).children(9).children(0).value);
					buffEndOpn = buffEndOpn + "," + parseInt(object.children(i).children(0).children(10).children(0).value);
					chkdQtys = chkdQtys + "," + object.children(i).children(0).children(15).children(0).value;
					chkdWoJbStatIds = chkdWoJbStatIds+ "-" +object.children(i).children(0).children(17).children(0).value;
				}
			}
			var sortStr = "";
			if (buffStartOpn == buffEndOpn)
			{
				sortStr = buffStartOpn;
			}
			else
			{
				for (var j = buffStartOpn; j <= buffEndOpn; j++)
				{
					if (j == buffStartOpn)
					{
						sortStr = buffStartOpn;
					}
					else
					{
						sortStr = sortStr + "," + j;
					}
				}
			}
			srtString +="";
			sortStr += "";
			var indChkdQtys = (chkdQtys).split(",");
			var preChkdQtys = (temp.proTotQtySnos.value).split(",");
			var indSortStr = (sortStr).split(",");
			var preSrtString = (srtString).split(",");
			var curWoJbStatIds = (temp.hidWoJbStIds.value).split("-");
			var preWoJbStatIds = (chkdWoJbStatIds).split("-");
			//alert(chkdWoJbStatIds);
			for (var b = 0; b < curWoJbStatIds.length; b++)
			{
				for (var c = 0; c < preWoJbStatIds.length; c++)
				{
					for (var x = 0; x < indChkdQtys.length; x++)
					{
						for (var y = 0; y < preChkdQtys.length; y++)
						{
							for (var z = 0; z < indSortStr.length; z++)
							{
								for (a = 0; a < preSrtString.length; a++)
								{
									if (curWoJbStatIds[b] == preWoJbStatIds[c])
									{
										if (indChkdQtys[x] == preChkdQtys[y])
										{
											if (indSortStr[z] == preSrtString[a])
											{
												alert("Some of the selected operation were already production entered!");
												return false;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		if (count == 0)/* Verify if minimum one is checked */
		{
			alert ("Check One or more Job Quantities to Proceed!");
			return false;
		}
		/* Call the special fn. if the operation is ReWork */
		if (temp.proNmlorRwk[1].checked)
		{
			temp.hidWrkOrdJbStIds.value = woJbStatIds;
		}

		temp.hidIsRadl.value = obj.children(0).children(1).children(0).children(0).value;
		temp.formAction.value = "isRadl";
		loadProdList();
		temp.submit();
	}

	function transEmpDet(val)
	{
		var temp = document.forms[0];
		var ind = parseInt(val);
		var obj = document.getElementById("tblEmpDetails");
		var obj1 = document.getElementById("tblEmpDetMstr");

		if((obj1.children(0).children(ind).children(2).children(0).value == "") && (obj1.children(0).children(ind).children(3).children(0).value == "") 
			|| (parseFloat(obj1.children(0).children(ind).children(2).children(0).value)+parseFloat(obj1.children(0).children(ind).children(3).children(0).value) == 0)
			|| (obj1.children(0).children(ind).children(2).children(0).value+obj1.children(0).children(ind).children(3).children(0).value == "")
			|| (obj1.children(0).children(ind).children(2).children(0).value+obj1.children(0).children(ind).children(3).children(0).value == 0))
		{
			alert("Duty + OT Hours cannot be Zero!");
			return false;
		}

		/* Validations before shifting Employee Details */
		if (temp.proEmployeeName[ind-1].options[temp.proEmployeeName[ind-1].selectedIndex].value == "0")
		{
			alert ("Select Employee Name to Proceed!");
			return false;
		}

		/* Duty + OT Hrs can't exceed Total Hours */
		if (temp.proTotalHours.value != "")
		{
			var empDtyHrs = obj1.children(0).children(ind).children(2).children(0).value;
			var empOtHrs = obj1.children(0).children(ind).children(3).children(0).value;
			if(empOtHrs == "")
			{
				empOtHrs = 0;
			}
			var empHrs = parseInt(empDtyHrs)+parseInt(empOtHrs);
			if (empHrs > temp.proTotalHours.value)
			{

				alert ("Duty Hrs + OT Hrs can't be morethan Total Hours!");
				obj1.children(0).children(ind).children(2).children(0).value = "";
				obj1.children(0).children(ind).children(3).children(0).value = "";

				return false;
			}
		}
		else
		{
			alert ("Enter the Total Hours!");
			return false;
		}

		if ((obj1.children(0).children(ind).children(2).children(0).value > 999) || (obj1.children(0).children(ind).children(3).children(0).value > 999))
		{
			alert("Employee Hours is Invalid!");
			obj1.children(0).children(ind).children(2).children(0).value = "";
			obj1.children(0).children(ind).children(3).children(0).value = "";
			obj1.children(0).children(ind).children(2).children(0).focus();
			return false;
		}


		if ((obj.children.length == "1") && (obj.children(0).children(0).children(1).children(0).value == ""))
		{
			/* Shifting the Employee Details from Master */
			obj.children(0).children(0).children(1).children(0).value = obj1.children(0).children(ind).children(0).children(0).value;
			obj.children(0).children(0).children(2).children(0).value = temp.proEmployeeName[ind-1].options[temp.proEmployeeName[ind-1].selectedIndex].text;
			obj.children(0).children(0).children(3).children(0).value = (obj1.children(0).children(ind).children(2).children(0).value == "")?(0):(obj1.children(0).children(ind).children(2).children(0).value);
			obj.children(0).children(0).children(4).children(0).value = (obj1.children(0).children(ind).children(3).children(0).value == "")?(0):(obj1.children(0).children(ind).children(3).children(0).value);
			obj.children(0).children(0).children(5).children(0).value = obj1.children(0).children(ind).children(5).children(0).value;
			obj.children(0).children(0).children(6).children(0).value = temp.proEmployeeName[ind-1].options[temp.proEmployeeName[ind-1].selectedIndex].value;
			//alert (obj.children(0).children(0).children(4).children(0).value+"&"+obj.children(0).children(0).children(5).children(0).value);

			/* Reseting the Fields */
			obj1.children(0).children(ind).children(2).children(0).value = "";
			obj1.children(0).children(ind).children(3).children(0).value = "";
			temp.proEmployeeName[ind-1].selectedIndex = "0";
		}
		else
		{
			/* Validate wheather this is already added or not */
			for (var i = 0; i < obj.children.length; i++)
			{
				if (obj.children(i).children(0).children(6).children(0).value == temp.proEmployeeName[ind-1].options[temp.proEmployeeName[ind-1].selectedIndex].value)
				{
					var name = temp.proEmployeeName[ind-1].options[temp.proEmployeeName[ind-1].selectedIndex].text;
					alert ("Sorry! The Employee "+name+"'s details already Added!");

					/* Reseting the Fields */
					obj1.children(0).children(ind).children(2).children(0).value = "";
					obj1.children(0).children(ind).children(3).children(0).value = "";
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
			obj.children(len-1).children(0).children(5).children(0).value = "";
			obj.children(len-1).children(0).children(6).children(0).value = "";

			/* Updating the Employee Details from master */
			obj.children(len-1).children(0).children(1).children(0).value = obj1.children(0).children(ind).children(0).children(0).value;
			obj.children(len-1).children(0).children(2).children(0).value = temp.proEmployeeName[ind-1].options[temp.proEmployeeName[ind-1].selectedIndex].text;
			obj.children(len-1).children(0).children(3).children(0).value = (obj1.children(0).children(ind).children(2).children(0).value == "")?(0):(obj1.children(0).children(ind).children(2).children(0).value);
			obj.children(len-1).children(0).children(4).children(0).value = (obj1.children(0).children(ind).children(3).children(0).value == "")?(0):(obj1.children(0).children(ind).children(3).children(0).value);
			obj.children(len-1).children(0).children(5).children(0).value = obj1.children(0).children(ind).children(5).children(0).value;
			obj.children(len-1).children(0).children(6).children(0).value = temp.proEmployeeName[ind-1].options[temp.proEmployeeName[ind-1].selectedIndex].value;

			/* Reseting the Fields */
			obj1.children(0).children(ind).children(2).children(0).value = "";
			obj1.children(0).children(ind).children(3).children(0).value = "";
			temp.proEmployeeName[ind-1].selectedIndex = "0";
		}
	}

	function enterProDet()
	{
		var temp = document.forms[0];
		var obj = document.getElementById("tblEmpDetails");

		if (temp.proTotalHours.value == "") /* Total hrs can't be empty */
		{
			alert ("Sorry! U must enter Total Hours!");
			return false;
		}

		/* Remove the Empty values in Employee Details */
		if ((obj.children(0).children(0).children(1).children(0).value == "") &&
			(obj.children(0).children(0).children(2).children(0).value == "") &&
			(obj.children(0).children(0).children(3).children(0).value == ""))
		{
			alert ("Employee Details can't be Empty!");
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
				empDet = empDet + "-" + obj.children(0).children(0).children(5).children(0).value; // Emp Typ Id
				empDet = empDet + "-" + obj.children(0).children(0).children(2).children(0).value; // Emp Name
				empDet = empDet + "-" + obj.children(0).children(0).children(6).children(0).value; // Emp Name Id
				empDet = empDet + "-" + obj.children(0).children(0).children(3).children(0).value; // Duty Hrs
				empDet = empDet + "-" + obj.children(0).children(0).children(4).children(0).value; // OT Hrs
			}
			else
			{
				empDet = empDet + "" + obj.children(i).children(0).children(1).children(0).value; // EmpTyp name
				empDet = empDet + "-" + obj.children(i).children(0).children(5).children(0).value; // Emp Typ Id
				empDet = empDet + "-" + obj.children(i).children(0).children(2).children(0).value; // Emp Name
				empDet = empDet + "-" + obj.children(i).children(0).children(6).children(0).value; // Emp Name Id
				empDet = empDet + "-" + obj.children(i).children(0).children(3).children(0).value; // Duty Hrs
				empDet = empDet + "-" + obj.children(i).children(0).children(4).children(0).value; // OT Hrs
			}
			i++;
		}
		//alert ("empDet: "+empDet);
		if (empDet == "")
		{
			alert ("Sorry! U must select Employee's to proceed!");
			return false;
		}

		/* For Checking the Minimum required Quantity of Employee's */
		var arTotEmp = (temp.minQty.value).split("^");
		for (var j = 0; j < arTotEmp.length; j++)
		{
			var arPrnlEmpDet = arTotEmp[j].split("-");
			var count = 0;
			var hlp = "";
			var i = 0;
			while (i < obj.children.length)
			{
				if (obj.children(i).children(0).children(5).children(0).value == arPrnlEmpDet[0])
				{
					count++;
				}
				i++;
			}
			if (arPrnlEmpDet[1] > count)
			{
				alert ("Atleast "+arPrnlEmpDet[1]+" "+arPrnlEmpDet[2]+" Required.");
				return false;
			}
		}

		temp.hidAllEmpDet.value = empDet;
		temp.formAction.value = "add";
		temp.submit();
	}

	/* Deleting the Checked row in Employee Details */
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
					obj.children(0).children(0).children(5).children(0).value = "";
					obj.children(0).children(0).children(6).children(0).value = "";
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
				obj.children(0).children(0).children(5).children(0).value = "";
				obj.children(0).children(0).children(6).children(0).value = "";

				temp.ckdCkBx.checked = false;
				temp.ckAll.checked = false;
		}
	}

	function loadEmp()
	{
		var temp = document.forms[0];
		if (temp.hidAllEmpDet.value != "")
		{
			var obj = document.getElementById('tblEmpDetails');
			var empDet = (temp.hidAllEmpDet.value).split('^');
			for (var i = 0 ; i < empDet.length; i++)
			{
				var empPrnlDet = empDet[i].split('-');

				if ((obj.children.length == 1) && (obj.children(0).children(0).children(5).children(0).value == ""))
				{
					obj.children(0).children(0).children(1).children(0).value = empPrnlDet[0]; // Emp Type Name
					obj.children(0).children(0).children(5).children(0).value = empPrnlDet[1]; // Emp Type Id
					obj.children(0).children(0).children(2).children(0).value = empPrnlDet[2]; // Emp Name
					obj.children(0).children(0).children(6).children(0).value = empPrnlDet[3]; // Emp Id
					obj.children(0).children(0).children(3).children(0).value = empPrnlDet[4]; // Dty Hrs
					obj.children(0).children(0).children(4).children(0).value = empPrnlDet[5]; // Ot Hrs
				}
				else
				{
					var newNode = obj.children(0).cloneNode(true);
					obj.appendChild(newNode);
					var len = obj.children.length;

					obj.children(len-1).children(0).children(1).children(0).value = ""; // Emp Type Name
					obj.children(len-1).children(0).children(5).children(0).value = ""; // Emp Type Id
					obj.children(len-1).children(0).children(2).children(0).value = ""; // Emp Name
					obj.children(len-1).children(0).children(6).children(0).value = ""; // Emp Id
					obj.children(len-1).children(0).children(3).children(0).value = ""; // Dty Hrs
					obj.children(len-1).children(0).children(4).children(0).value = ""; // Ot Hrs

					obj.children(len-1).children(0).children(1).children(0).value = empPrnlDet[0]; // Emp Type Name
					obj.children(len-1).children(0).children(5).children(0).value = empPrnlDet[1]; // Emp Type Id
					obj.children(len-1).children(0).children(2).children(0).value = empPrnlDet[2]; // Emp Name
					obj.children(len-1).children(0).children(6).children(0).value = empPrnlDet[3]; // Emp Id
					obj.children(len-1).children(0).children(3).children(0).value = empPrnlDet[4]; // Dty Hrs
					obj.children(len-1).children(0).children(4).children(0).value = empPrnlDet[5]; // Ot Hrs
				}
			}
		}
	}

	/* This fn Should gather all the Production Details and store it on Buffer */
	function transProdList()
	{
		var temp = document.forms[0];
		var obj = document.getElementById('tblBufferList');
		prodInfo.style.display = 'block';
		tblBuffer.style.display = 'block';
		if ((temp.proDate.value == "") || (temp.proShift.value == "0") || (temp.proMachine.value == "0"))
		{
			alert("Some of the value(s) might be empty!");
			return false;
		}

		var object = document.getElementById("tblEmpDetails");
		if (flag == 1)
		{
			alert ("The Entry is already Transfered.");
			return false;
		}

		if (temp.proTotalHours.value == "") /* Total hrs can't be empty */
		{
			alert ("Sorry! U must enter Total Hours!");
			return false;
		}

		if ((temp.proTotalHours.value != "") && (temp.proTotalHours.value > 999)) /* Total hrs can't be greater than 1000 */
		{
			alert("Total Hrs is Invalid!");
			temp.proTotalHours.value = "";
			temp.proTotalHours.focus();
			return false;
		}
		/* Remove the Empty values in Employee Details */
		if ((object.children(0).children(0).children(1).children(0).value == "") &&
			(object.children(0).children(0).children(2).children(0).value == "") &&
			(object.children(0).children(0).children(3).children(0).value == ""))
		{
			alert ("Employee Details can't be Empty!");
			return false;
		}

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
			"-" + object.children(i).children(0).children(3).children(0).value + // Duty Hrs
			"-" + object.children(i).children(0).children(4).children(0).value + // OT Hrs
			"-" + object.children(i).children(0).children(5).children(0).value + // Emp Typ Id
			"-" + object.children(i).children(0).children(6).children(0).value; // Emp Name Id

			i++;
		}
		//alert ("empDet: "+empDet);
		if (empDet == "")
		{
			alert ("Sorry! U must select Employee's to proceed!");
			return false;
		}

		/* For Checking the Minimum required Quantity of Employee's */
		var arTotEmp = (temp.minQty.value).split("^");
		for (var j = 0; j < arTotEmp.length; j++)
		{
			var arPrnlEmpDet = arTotEmp[j].split("-");
			var count = 0;
			var hlp = "";
			var i = 0;
			while (i < object.children.length)
			{
				if (object.children(i).children(0).children(5).children(0).value == arPrnlEmpDet[0])
				{
					count++;
				}
				i++;
			}
			if (arPrnlEmpDet[1] > count)
			{
				alert ("Atleast "+arPrnlEmpDet[1]+" "+arPrnlEmpDet[2]+" Required.");
				return false;
			}
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
		var obj2 = document.getElementById("tblJobOperDet"); /* Picking that JobOperationDetails Table */
		var count = 0;
		temp.proTotQtySnos.value = "";
		temp.hidWrkOrdJbStIds.value = "";
		var i = 1;
		var k = 0;
		var totQty = "";
		var woJbStatIds = "";
		while (i < obj2.children(0).children.length)
		{
			if (obj2.children(0).children(i).children(0).children(0).checked)
			{
				if ((obj2.children(0).children(i).children(2).children(0).value.search(srtString)) == -1)
				{
					alert ("Check Start/End Operations!");
					return false;
				}
				count++;
				var rewkOpns = (obj2.children(0).children(i).children(3).children(0).value).split(',');
				if (k == 0)
				{
					totQty = obj2.children(0).children(i).children(1).innerText;
					woJbStatIds = obj2.children(0).children(i).children(0).children(0).value;
				}
				else
				{
					totQty = totQty + "," + obj2.children(0).children(i).children(1).innerText;
					woJbStatIds = woJbStatIds +"-"+ obj2.children(0).children(i).children(0).children(0).value;
				}
				k++;

				for (var x = start; x <= end; x++)
				{
					for (var y = 0; y < rewkOpns.length; y++)
					{
						if ((x == rewkOpns[y]) && (temp.proNmlorRwk[0].checked))
						{
							alert ("Sorry! In Normal Work, U can't enter Rework Operations!");
							return false;
						}
					}
				}
			}
			i++;
		}
		temp.proTotQtySnos.value = totQty;
		temp.hidWoJbStIds.value = woJbStatIds;

		var object = document.getElementById("tblBufferList");
		var buffStartOpn = "";
		var buffEndOpn = "";
		var chkdQtys = "";
		var chkdWoJbStatIds = "";

		if (object.children.length != undefined)
		{
			for (var i = 0; i < object.children.length; i++)
			{
				if (i == 0)
				{
					buffStartOpn = parseInt(object.children(0).children(0).children(9).children(0).value);
					buffEndOpn = parseInt(object.children(0).children(0).children(10).children(0).value);
					chkdQtys = object.children(0).children(0).children(15).children(0).value;
					chkdWoJbStatIds = object.children(0).children(0).children(17).children(0).value;
				}
				else
				{
					buffStartOpn = buffStartOpn + "," + parseInt(object.children(i).children(0).children(9).children(0).value);
					buffEndOpn = buffEndOpn + "," + parseInt(object.children(i).children(0).children(10).children(0).value);
					chkdQtys = chkdQtys + "," + object.children(i).children(0).children(15).children(0).value;
					chkdWoJbStatIds = chkdWoJbStatIds+ "-" +object.children(i).children(0).children(17).children(0).value;
				}
			}
			var sortStr = "";
			if (buffStartOpn == buffEndOpn)
			{
				sortStr = buffStartOpn;
			}
			else
			{
				for (var j = buffStartOpn; j <= buffEndOpn; j++)
				{
					if (j == buffStartOpn)
					{
						sortStr = buffStartOpn;
					}
					else
					{
						sortStr = sortStr + "," + j;
					}
				}
			}
			srtString +="";
			sortStr += "";
			var indChkdQtys = (chkdQtys).split(",");
			var preChkdQtys = (temp.proTotQtySnos.value).split(",");
			var indSortStr = (sortStr).split(",");
			var preSrtString = (srtString).split(",");
			var curWoJbStatIds = (temp.hidWoJbStIds.value).split("-");
			var preWoJbStatIds = (chkdWoJbStatIds).split("-");
			//alert(chkdWoJbStatIds);
			for (var b = 0; b < curWoJbStatIds.length; b++)
			{
				for (var c = 0; c < preWoJbStatIds.length; c++)
				{
					for (var x = 0; x < indChkdQtys.length; x++)
					{
						for (var y = 0; y < preChkdQtys.length; y++)
						{
							for (var z = 0; z < indSortStr.length; z++)
							{
								for (a = 0; a < preSrtString.length; a++)
								{
									if (curWoJbStatIds[b] == preWoJbStatIds[c])
									{
										if (indChkdQtys[x] == preChkdQtys[y])
										{
											if (indSortStr[z] == preSrtString[a])
											{
												alert("Some of the selected operation were already production entered!");
												return false;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		if (count == 0)/* Verify if minimum one is checked */
		{
			alert ("Check One or more Job Quantities to Proceed!");
			return false;
		}
		/* Call the special fn. if the operation is ReWork */
		if (temp.proNmlorRwk[1].checked)
		{
			temp.hidWrkOrdJbStIds.value = woJbStatIds;
		}

		/* Moving all the Values to Buffer */
		if ((obj.children.length == 1) && (obj.children(0).children(0).children(2).children(0).value == "") && (obj.children(0).children(0).children(2).children(0).value == ""))
		{
			obj.children(0).children(0).children(1).children(0).value = obj.children.length;
			obj.children(0).children(0).children(2).children(0).value = temp.proDate.value;
			obj.children(0).children(0).children(3).children(0).value = temp.proShift.options[temp.proShift.selectedIndex].text;
			obj.children(0).children(0).children(4).children(0).value = temp.proMachine.value;
			obj.children(0).children(0).children(5).children(0).value = temp.proWorkOrderHash.options[temp.proWorkOrderHash.selectedIndex].text;
			obj.children(0).children(0).children(6).children(0).value = temp.hidChkdJobName.value;
			obj.children(0).children(0).children(7).children(0).value = (temp.proNmlorRwk[0].checked)?("Normal"):("Rework");
			//obj.children(0).children(0).children(8).children(0).value = (temp.proIncentive.checked)?("Yes"):("No");
			obj.children(0).children(0).children(9).children(0).value = temp.proStartOperation.value;
			obj.children(0).children(0).children(10).children(0).value = temp.proEndOperation.value;
			obj.children(0).children(0).children(11).children(0).value = temp.proTotalHours.value;

			obj.children(0).children(0).children(12).children(0).value = temp.proShift.value;
			obj.children(0).children(0).children(13).children(0).value = temp.proWorkOrderHash.value;
			obj.children(0).children(0).children(14).children(0).value = temp.hidWrkOrdJobId.value;
			obj.children(0).children(0).children(15).children(0).value = temp.proTotQtySnos.value;
			obj.children(0).children(0).children(16).children(0).value = empDet;
			obj.children(0).children(0).children(17).children(0).value = temp.hidWoJbStIds.value;
		}
		else
		{
			var newNode = obj.children(0).cloneNode(true);
			obj.appendChild(newNode);
			var len = obj.children.length;

			obj.children(len-1).children(0).children(1).children(0).value = obj.children.length;
			obj.children(len-1).children(0).children(2).children(0).value = temp.proDate.value;
			obj.children(len-1).children(0).children(3).children(0).value = temp.proShift.options[temp.proShift.selectedIndex].text;
			obj.children(len-1).children(0).children(4).children(0).value = temp.proMachine.value;
			obj.children(len-1).children(0).children(5).children(0).value = temp.proWorkOrderHash.options[temp.proWorkOrderHash.selectedIndex].text;
			obj.children(len-1).children(0).children(6).children(0).value = temp.hidChkdJobName.value;
			obj.children(len-1).children(0).children(7).children(0).value = (temp.proNmlorRwk[0].checked)?("Normal"):("Rework");
			//obj.children(len-1).children(0).children(8).children(0).value = (temp.proIncentive.checked)?("Yes"):("No");
			obj.children(len-1).children(0).children(9).children(0).value = temp.proStartOperation.value;
			obj.children(len-1).children(0).children(10).children(0).value = temp.proEndOperation.value;
			obj.children(len-1).children(0).children(11).children(0).value = temp.proTotalHours.value;

			obj.children(len-1).children(0).children(12).children(0).value = temp.proShift.value;
			obj.children(len-1).children(0).children(13).children(0).value = temp.proWorkOrderHash.value;
			obj.children(len-1).children(0).children(14).children(0).value = temp.hidWrkOrdJobId.value;
			obj.children(len-1).children(0).children(15).children(0).value = temp.proTotQtySnos.value;
			obj.children(len-1).children(0).children(16).children(0).value = empDet;
			obj.children(len-1).children(0).children(17).children(0).value = temp.hidWoJbStIds.value;
		}
		flag = 1;
		prodShftRow.style.display = 'none';
		wrkOrder.style.display = 'none';
		jobQty.style.display = 'none';
		temp.proWorkOrderHash.selectedIndex = 0;
		temp.proStartOperation.value = "";
		temp.proEndOperation.value = "";
		temp.proTotalHours.value = "";
		temp.showCount.value = "";
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
				+ "#" + obj.children(i).children(0).children(4).children(0).value // MachineCode
				+ "#" + obj.children(i).children(0).children(5).children(0).value // WoNo
				+ "#" + obj.children(i).children(0).children(6).children(0).value // Job Name
				+ "#" + obj.children(i).children(0).children(7).children(0).value // WorkTyp
				//+ "#" + obj.children(i).children(0).children(8).children(0).value // Incentive
				+ "#" + obj.children(i).children(0).children(9).children(0).value // Start Operation
				+ "#" + obj.children(i).children(0).children(10).children(0).value // End Opn
				+ "#" + obj.children(i).children(0).children(11).children(0).value // Tot Hrs
				+ "#" + obj.children(i).children(0).children(12).children(0).value // Shift ID
				+ "#" + obj.children(i).children(0).children(13).children(0).value // Wo Id
				+ "#" + obj.children(i).children(0).children(14).children(0).value // Job Id
				+ "#" + obj.children(i).children(0).children(15).children(0).value // Job Qty Serial Nos
				+ "#" + obj.children(i).children(0).children(16).children(0).value // Emp Details
				+ "#" + obj.children(i).children(0).children(17).children(0).value; // WO Job Stat Ids
		}
		temp.hidProdList.value = prodDet;
		//alert (temp.hidProdList.value);
	}

	/*	This function should be call before each and every submit(),
	*	to replace all the Production Details
	*/

	function replaceProdList()
	{
		var temp = document.forms[0];
		tblBuffer.style.display = 'none';
		if (temp.formAction.value == "add")
		{
			temp.hidProdList.value = "";
		<%
			if (BuildConfig.DMODE)
				System.out.println("--> "+request.getAttribute("prodresult")+"&"+frm.getFormAction());
		try
			{
			if (frm.getFormAction().equalsIgnoreCase("add") && (request.getAttribute("prodresult") != null))
			{
				hm_Result = (HashMap) request.getAttribute("prodresult");

				/* For Success */
				vec_Success = (Vector) hm_Result.get("Success");


				/* For Machine Failure */
				vec_MachineFail = (Vector) hm_Result.get("MachineFailure");
				for (int i = 0; i < vec_MachineFail.size(); i++)
				{
					ProductionDetails objProductionDetails = (ProductionDetails) vec_MachineFail.get(i);
					String empDet = "";
					if (i != 0)
					{
						prodList = prodList + "$";
					}
					GregorianCalendar gc = new GregorianCalendar();
					gc.setTime(objProductionDetails.getProdCrntDate());
					int day = gc.get(GregorianCalendar.DATE);
					int month = gc.get(GregorianCalendar.MONTH);
					int year = gc.get(GregorianCalendar.YEAR);
					if (BuildConfig.DMODE)
						System.out.println("Date: "+year+"-"+month+"-"+day);
					prodList = prodList + (i+1) +"#"+
						(year+"-"+(month+1)+"-"+day) +"#"+
						objProductionDetails.getShiftName() +"#"+
						objProductionDetails.getMcCode() +"#"+
						objProductionDetails.getWoNo() +"#"+
						objProductionDetails.getObjProductionJobDetails().getJobName() +"#"+
						((objProductionDetails.getProdWorkType().equalsIgnoreCase("N"))?("Normal"):("Rework")) +"#"+
						//(objProductionDetails.isProdIncntvFlag()?"Yes":"No") +"#"+
						objProductionDetails.getProdStartOpn() +"#"+
						objProductionDetails.getProdEndOpn() +"#"+
						objProductionDetails.getProdTotHrs() +"#"+
						objProductionDetails.getShiftId() +"#"+
						objProductionDetails.getWoId() +"#"+
						objProductionDetails.getWoJbId() +"#"+
						objProductionDetails.getProdQtySnos();
					Vector vec_EmpDet = objProductionDetails.getProdnEmpHrsDetails();
					for (int j = 0; j < vec_EmpDet.size(); j++)
					{
						EmployeeDtyOtDetails objEmployeeDtyOtDetails = new EmployeeDtyOtDetails();
						objEmployeeDtyOtDetails = (EmployeeDtyOtDetails) vec_EmpDet.get(j);
						if (j != 0)
						{
							empDet = empDet + "^";
						}
						empDet = empDet + objEmployeeDtyOtDetails.getEmpType() +"-"+
							objEmployeeDtyOtDetails.getEmpName() +"-"+
							objEmployeeDtyOtDetails.getDutyHrs() +"-"+
							objEmployeeDtyOtDetails.getOtHrs() +"-"+
							objEmployeeDtyOtDetails.getEmpTypdId() +"-"+
							objEmployeeDtyOtDetails.getEmpId();
					}
					prodList = prodList + "#" + empDet;
					if (BuildConfig.DMODE)
						System.out.println("ProdList: "+ prodList);
				}

				/* For Duplicate Entry*/
				vec_DuplicateEntry = (Vector) hm_Result.get("DuplicateEntry");
				for (int i = 0; i < vec_DuplicateEntry.size(); i++)
				{
					ProductionDetails objProductionDetails = (ProductionDetails) vec_DuplicateEntry.get(i);
					String empDet = "";
					if (i != 0)
					{
						prodList = prodList + "$";
					}
					GregorianCalendar gc = new GregorianCalendar();
					gc.setTime(objProductionDetails.getProdCrntDate());
					int day = gc.get(GregorianCalendar.DATE);
					int month = gc.get(GregorianCalendar.MONTH);
					int year = gc.get(GregorianCalendar.YEAR);
					if (BuildConfig.DMODE)
						System.out.println("Date: "+year+"-"+month+"-"+day);
					prodList = prodList + (i+1) +"#"+
						(year+"-"+(month+1)+"-"+day) +"#"+
						objProductionDetails.getShiftName() +"#"+
						objProductionDetails.getMcCode() +"#"+
						objProductionDetails.getWoNo() +"#"+
						objProductionDetails.getObjProductionJobDetails().getJobName() +"#"+
						((objProductionDetails.getProdWorkType().equalsIgnoreCase("N"))?("Normal"):("Rework")) +"#"+
						//(objProductionDetails.isProdIncntvFlag()?"Yes":"No") +"#"+
						objProductionDetails.getProdStartOpn() +"#"+
						objProductionDetails.getProdEndOpn() +"#"+
						objProductionDetails.getProdTotHrs() +"#"+
						objProductionDetails.getShiftId() +"#"+
						objProductionDetails.getWoId() +"#"+
						objProductionDetails.getWoJbId() +"#"+
						objProductionDetails.getProdQtySnos();
					Vector vec_EmpDet = objProductionDetails.getProdnEmpHrsDetails();
					for (int j = 0; j < vec_EmpDet.size(); j++)
					{
						EmployeeDtyOtDetails objEmployeeDtyOtDetails = new EmployeeDtyOtDetails();
						objEmployeeDtyOtDetails = (EmployeeDtyOtDetails) vec_EmpDet.get(j);
						if (j != 0)
						{
							empDet = empDet + "^";
						}
						empDet = empDet + objEmployeeDtyOtDetails.getEmpType() +"-"+
							objEmployeeDtyOtDetails.getEmpName() +"-"+
							objEmployeeDtyOtDetails.getDutyHrs() +"-"+
							objEmployeeDtyOtDetails.getOtHrs() +"-"+
							objEmployeeDtyOtDetails.getEmpTypdId() +"-"+
							objEmployeeDtyOtDetails.getEmpId();
					}
					prodList = prodList + "#" + empDet;
					if (BuildConfig.DMODE)
						System.out.println("ProdList: "+ prodList);
				}

				/* For DutyFailure Entry*/
				vec_DutyFail = (Vector) hm_Result.get("DutyFailure");
				for (int i = 0; i < vec_DutyFail.size(); i++)
				{
					HashMap hm_DutyFail = (HashMap) vec_DutyFail.get(i);
					Set set = hm_DutyFail.entrySet();
					Iterator it = set.iterator();
					while(it.hasNext())
					{
						Map.Entry me = (Map.Entry) it.next();

						ProductionDetails objProductionDetails = (ProductionDetails) me.getValue();
						String empDet = "";
						if (i != 0)
						{
							prodList = prodList + "$";
						}
						GregorianCalendar gc = new GregorianCalendar();
						gc.setTime(objProductionDetails.getProdCrntDate());
						int day = gc.get(GregorianCalendar.DATE);
						int month = gc.get(GregorianCalendar.MONTH);
						int year = gc.get(GregorianCalendar.YEAR);
						if (BuildConfig.DMODE)
							System.out.println("Date: "+year+"-"+month+"-"+day);
						prodList = prodList + (i+1) +"#"+
							(year+"-"+(month+1)+"-"+day) +"#"+
							objProductionDetails.getShiftName() +"#"+
							objProductionDetails.getMcCode() +"#"+
							objProductionDetails.getWoNo() +"#"+
							objProductionDetails.getObjProductionJobDetails().getJobName() +"#"+
							((objProductionDetails.getProdWorkType().equalsIgnoreCase("N"))?("Normal"):("Rework")) +"#"+
							//(objProductionDetails.isProdIncntvFlag()?"Yes":"No") +"#"+
							objProductionDetails.getProdStartOpn() +"#"+
							objProductionDetails.getProdEndOpn() +"#"+
							objProductionDetails.getProdTotHrs() +"#"+
							objProductionDetails.getShiftId() +"#"+
							objProductionDetails.getWoId() +"#"+
							objProductionDetails.getWoJbId() +"#"+
							objProductionDetails.getProdQtySnos();
						Vector vec_EmpDet = objProductionDetails.getProdnEmpHrsDetails();
						for (int j = 0; j < vec_EmpDet.size(); j++)
						{
							EmployeeDtyOtDetails objEmployeeDtyOtDetails = new EmployeeDtyOtDetails();
							objEmployeeDtyOtDetails = (EmployeeDtyOtDetails) vec_EmpDet.get(j);
							if (j != 0)
							{
								empDet = empDet + "^";
							}
							empDet = empDet + objEmployeeDtyOtDetails.getEmpType() +"-"+
								objEmployeeDtyOtDetails.getEmpName() +"-"+
								objEmployeeDtyOtDetails.getDutyHrs() +"-"+
								objEmployeeDtyOtDetails.getOtHrs() +"-"+
								objEmployeeDtyOtDetails.getEmpTypdId() +"-"+
								objEmployeeDtyOtDetails.getEmpId();
						}
						prodList = prodList + "#" + empDet;
						if (BuildConfig.DMODE)
							System.out.println("ProdList: "+ prodList);
					}
				}

				/* For Employee Failure */
				vec_EmpFail = (Vector) hm_Result.get("EmpFailure");
				for (int i = 0; i < vec_EmpFail.size(); i++)
				{
					HashMap hm_EmpFail = (HashMap) vec_EmpFail.get(i);
					Set set = hm_EmpFail.entrySet();
					Iterator it = set.iterator();
					while(it.hasNext())
					{
						Map.Entry me = (Map.Entry) it.next();

						ProductionDetails objProductionDetails = (ProductionDetails) me.getValue();
						String empDet = "";
						if (prodList != "")
						{
							prodList = prodList + "$";
						}
						GregorianCalendar gc = new GregorianCalendar();
						gc.setTime(objProductionDetails.getProdCrntDate());
						int day = gc.get(GregorianCalendar.DATE);
						int month = gc.get(GregorianCalendar.MONTH);
						int year = gc.get(GregorianCalendar.YEAR);
						prodList = prodList + (i+1) +"#"+
							(year+"-"+(month+1)+"-"+day) +"#"+
							objProductionDetails.getShiftName() +"#"+
							objProductionDetails.getMcCode() +"#"+
							objProductionDetails.getWoNo() +"#"+
							objProductionDetails.getObjProductionJobDetails().getJobName() +"#"+
							((objProductionDetails.getProdWorkType().equalsIgnoreCase("N"))?("Normal"):("Rework")) +"#"+
							//(objProductionDetails.isProdIncntvFlag()?"Yes":"No") +"#"+
							objProductionDetails.getProdStartOpn() +"#"+
							objProductionDetails.getProdEndOpn() +"#"+
							objProductionDetails.getProdTotHrs() +"#"+
							objProductionDetails.getShiftId() +"#"+
							objProductionDetails.getWoId() +"#"+
							objProductionDetails.getWoJbId() +"#"+
							objProductionDetails.getProdQtySnos();
						Vector vec_EmpDet = objProductionDetails.getProdnEmpHrsDetails();
						for (int j = 0; j < vec_EmpDet.size(); j++)
						{
							EmployeeDtyOtDetails objEmployeeDtyOtDetails = new EmployeeDtyOtDetails();
							objEmployeeDtyOtDetails = (EmployeeDtyOtDetails) vec_EmpDet.get(j);
							if (j != 0)
							{
								empDet = empDet + "^";
							}
							empDet = empDet + objEmployeeDtyOtDetails.getEmpType() +"-"+
								objEmployeeDtyOtDetails.getEmpName() +"-"+
								objEmployeeDtyOtDetails.getDutyHrs() +"-"+
								objEmployeeDtyOtDetails.getOtHrs() +"-"+
								objEmployeeDtyOtDetails.getEmpTypdId() +"-"+
								objEmployeeDtyOtDetails.getEmpId();
						}
						prodList = prodList + "#" + empDet;
					}
				}
			}
			}catch (Exception expr)
			{
				expr.printStackTrace();
			}
		%>
		temp.hidProdList.value = '<%= prodList %>';
		}
		//alert (temp.formAction.value);
		//alert (temp.hidProdList.value);
		if (temp.hidProdList.value == "")
			return false;
		tblBuffer.style.display ='block';
		var obj = document.getElementById('tblBufferList');
		var prodTotRecDet = (temp.hidProdList.value).split("$");
		for (var i = 0; i < prodTotRecDet.length; i++)
		{
			var prodRecDet = prodTotRecDet[i].split("#");
			if (i == 0)
			{
				obj.children(0).children(0).children(1).children(0).value = prodRecDet[0];
				obj.children(0).children(0).children(2).children(0).value = prodRecDet[1];
				obj.children(0).children(0).children(3).children(0).value = prodRecDet[2];
				obj.children(0).children(0).children(4).children(0).value = prodRecDet[3];
				obj.children(0).children(0).children(5).children(0).value = prodRecDet[4];
				obj.children(0).children(0).children(6).children(0).value = prodRecDet[5];
				obj.children(0).children(0).children(7).children(0).value = prodRecDet[6];
				//obj.children(0).children(0).children(8).children(0).value = prodRecDet[7];
				obj.children(0).children(0).children(9).children(0).value = prodRecDet[7];
				obj.children(0).children(0).children(10).children(0).value = prodRecDet[8];
				obj.children(0).children(0).children(11).children(0).value = prodRecDet[9];

				obj.children(0).children(0).children(12).children(0).value = prodRecDet[10];
				obj.children(0).children(0).children(13).children(0).value = prodRecDet[11];
				obj.children(0).children(0).children(14).children(0).value = prodRecDet[12];
				obj.children(0).children(0).children(15).children(0).value = prodRecDet[13];
				obj.children(0).children(0).children(16).children(0).value = prodRecDet[14];
				obj.children(0).children(0).children(17).children(0).value = prodRecDet[15];
			}
			else
			{
				var newNode = obj.children(0).cloneNode(true);
				obj.appendChild(newNode);
				var len = obj.children.length;

				obj.children(len-1).children(0).children(1).children(0).value = prodRecDet[0];
				obj.children(len-1).children(0).children(2).children(0).value = prodRecDet[1];
				obj.children(len-1).children(0).children(3).children(0).value = prodRecDet[2];
				obj.children(len-1).children(0).children(4).children(0).value = prodRecDet[3];
				obj.children(len-1).children(0).children(5).children(0).value = prodRecDet[4];
				obj.children(len-1).children(0).children(6).children(0).value = prodRecDet[5];
				obj.children(len-1).children(0).children(7).children(0).value = prodRecDet[6];
				//obj.children(len-1).children(0).children(8).children(0).value = prodRecDet[7];
				obj.children(len-1).children(0).children(9).children(0).value = prodRecDet[7];
				obj.children(len-1).children(0).children(10).children(0).value = prodRecDet[8];
				obj.children(len-1).children(0).children(11).children(0).value = prodRecDet[9];

				obj.children(len-1).children(0).children(12).children(0).value = prodRecDet[10];
				obj.children(len-1).children(0).children(13).children(0).value = prodRecDet[11];
				obj.children(len-1).children(0).children(14).children(0).value = prodRecDet[12];
				obj.children(len-1).children(0).children(15).children(0).value = prodRecDet[13];
				obj.children(len-1).children(0).children(16).children(0).value = prodRecDet[14];
				obj.children(len-1).children(0).children(17).children(0).value = prodRecDet[15];
			}
		}
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
					//obj.children(i).children(0).children(8).children(0).value + "#" +
					obj.children(i).children(0).children(9).children(0).value + "#" +
					obj.children(i).children(0).children(10).children(0).value + "#" +
					obj.children(i).children(0).children(11).children(0).value + "#" +
					obj.children(i).children(0).children(12).children(0).value + "#" +
					obj.children(i).children(0).children(13).children(0).value + "#" +
					obj.children(i).children(0).children(14).children(0).value + "#" +
					obj.children(i).children(0).children(15).children(0).value + "#" +
					obj.children(i).children(0).children(16).children(0).value + "#" +
					obj.children(i).children(0).children(17).children(0).value;

				temp.hidWrkOrdId.value = obj.children(i).children(0).children(13).children(0).value;
				temp.hidWrkOrdJobId.value = obj.children(i).children(0).children(14).children(0).value;
				temp.hidWoJbStIds.value = obj.children(i).children(0).children(17).children(0).value;
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
					//obj.children(0).children(0).children(8).children(0).value = "";
					obj.children(0).children(0).children(9).children(0).value = "";
					obj.children(0).children(0).children(10).children(0).value = "";
					obj.children(0).children(0).children(11).children(0).value = "";
					obj.children(0).children(0).children(12).children(0).value = "";
					obj.children(0).children(0).children(13).children(0).value = "";
					obj.children(0).children(0).children(14).children(0).value = "";
					obj.children(0).children(0).children(15).children(0).value = "";
					obj.children(0).children(0).children(16).children(0).value = "";

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
					//obj.children(0).children(0).children(8).children(0).value = "";
					obj.children(0).children(0).children(9).children(0).value = "";
					obj.children(0).children(0).children(10).children(0).value = "";
					obj.children(0).children(0).children(11).children(0).value = "";
					obj.children(0).children(0).children(12).children(0).value = "";
					obj.children(0).children(0).children(13).children(0).value = "";
					obj.children(0).children(0).children(14).children(0).value = "";
					obj.children(0).children(0).children(15).children(0).value = "";
					obj.children(0).children(0).children(16).children(0).value = "";
			obj.children(0).children(0).children(0).children(0).checked = false;
		}
		for (var i = 0; i < obj.children.length; i++)
		{
			obj.children(i).children(0).children(1).children(0).value = i+1;
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

	function submitItem()
	{
		loadProdList();
		document.forms[0].formAction.value = "add";
		document.forms[0].submit();
	}

	function viewOpns()
	{
		temp = document.forms[0];
		var obj = document.getElementById('tblWorkOrdInfo');
		for (var i = 1; i < obj.children(0).children.length; i++)
		{
			if (obj.children(0).children(i).children(0).children(0).checked)
			{
				temp.hidWoJbId.value = obj.children(0).children(i).children(0).children(0).value;
				temp.formAction.value = "viewOpns";
				temp.submit();
			}
		}
	}

	function operationView()
	{
		if (document.forms[0].formAction.value == "viewOpns")
		{
			var features = "toolbars=no,status=no,resizable=no,scrollbars=yes,height=550px,width=550px,top=90px,left=350px";
			window.open('<bean:message key="context"/>/Production/ViewOperations.jsp?formAction=viewOpns',"ViewOperations",features);
		}
	}


	function makeCopy()
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
			alert ("Select One Item to make it's copy!");
			return false;
		}
		else if (count == 0)
		{
			alert ("Select a Item to make it's Copy!");
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
					//obj.children(i).children(0).children(8).children(0).value + "#" +
					obj.children(i).children(0).children(9).children(0).value + "#" +
					obj.children(i).children(0).children(10).children(0).value + "#" +
					obj.children(i).children(0).children(11).children(0).value + "#" +
					obj.children(i).children(0).children(12).children(0).value + "#" +
					obj.children(i).children(0).children(13).children(0).value + "#" +
					obj.children(i).children(0).children(14).children(0).value + "#" +
					obj.children(i).children(0).children(15).children(0).value + "#" +
					obj.children(i).children(0).children(16).children(0).value + "#" +
					obj.children(i).children(0).children(17).children(0).value;

				temp.hidWrkOrdId.value = obj.children(i).children(0).children(13).children(0).value;
				temp.hidWrkOrdJobId.value = obj.children(i).children(0).children(14).children(0).value;
				temp.hidWoJbStIds.value = obj.children(i).children(0).children(17).children(0).value;
			}
		}

		temp.formAction.value = "makeCopy";
		temp.showCount.value = "6";
		loadProdList();
		temp.submit();
	}

</script>
</head>

<body onLoad="init(); showJobDet(); loadEmp(); replaceProdList();operationView();">
<html:form action="frmProdAdd">
<html:hidden property="formAction"/>
<html:hidden property="minQty" value="<%= frm.getMinQty() %>"/> <!-- Holding the Minimum required no. of emp's -->
<html:hidden property="hidWrkOrdId"/><!--  value="<%= frm.getHidWrkOrdId() %>"Handling the WorkOrder Id for Job Details -->
<html:hidden property="hidWrkOrdJobId"/><!--  value="<%= frm.getHidWrkOrdJobId() %>"Handling the WorkOrder Job Id for Job Operation Details -->
<html:hidden property="showCount" /> <!-- value="< %= frm.getShowCount() %>" Showing the appropriate Table by Count -->
<html:hidden property="hidChkdJobName"/><!--  value="<%= frm.getHidChkdJobName() %>"Holding the Selected Job name -->
<html:hidden property="hidChkdJobId"/> <!--  value="<%= frm.getHidChkdJobId() %>"Holding the Selected Job Id Index -->
<html:hidden property="proTotQtySnos" value="<%= frm.getProTotQtySnos() %>"/> <!-- Holding the Total Qty Sno's -->
<html:hidden property="hidAllEmpDet" value="<%= frm.getHidAllEmpDet() %>"/> <!-- Holding the Employee Details -->
<html:hidden property="hidIsRadl"/>
<html:hidden property="hidWrkOrdJbStIds"/> <!-- Holding the WoJbStatusId's for Whether the work is Rework -->
<html:hidden property="hidWoJbStIds"/> <!-- Holding the WoJbStatusId's for checking Production entries -->
<html:hidden property="hidProdList"/> <!-- Holding the Production List Details -->
<html:hidden property="hidUserId"/> <!-- Holding the User Name -->
<html:hidden property="hidWoJbId" value="<%= frm.getHidWoJbId() %>"/> <!-- Holding the WorkOrder Job Id -->
<html:hidden property="grpId"/> <!-- Holding the Opn.Group Id -->
<html:hidden property="sno"/> <!-- Holding the Opn.SNo -->
<html:hidden property="name"/> <!-- Holding the Opn.Name -->
<html:hidden property="stdHrs"/> <!-- Holding the Opn. StdHrs -->
<html:hidden property="gpCde"/> <!-- Holding the Group Code -->
<html:hidden property="response"/>

	<table width="100%" cellspacing="0" cellpadding="10">
    	<tr>
      <td>
      	<table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
	      <tr>
	      	<td><bean:message key="prodacs.production.header"/></td>
		</tr>
          	</table>
          	<br>
          	<table width="100" cellspacing="0" cellpadding="0" align="right">
	      <tr>
			<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New Production Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1017" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
			<menuconfig:userRights url="#" id="#" onMouseOver="window.status='List Production Info'; return true"  onMouseOut="window.status=''; return true" resourceId="17" text="[ List ]" classId="TopLnk" onClick="javaScript:listItem();"/>
	      </tr>
          	</table>
      		<table>
		<tr>
			<td colspan='2'> <font size="1px" face="Verdana"><html:errors/></font></td>
		</tr>

<%
		if (frm.getFormAction().equalsIgnoreCase("add") && request.getAttribute("prodresult") != null)
		{
			hm_Result = (HashMap) request.getAttribute("prodresult");
			if (BuildConfig.DMODE)
				System.out.println("Result: "+ hm_Result);

			/* For Success */
			vec_Success = (Vector) hm_Result.get("Success");
			if (BuildConfig.DMODE)
				System.out.println("vec_Success: "+ vec_Success.size());
			if (vec_Success.size() == 1)
			{
				out.println("<tr><td colspan='2'><font size='2px' face='Verdana' color='#3323C7'><img src='/ProDACS/images/active.gif' border='0'> "+vec_Success.size()+" Production Entry Added. </font></td></tr>");
				out.println("<tr>&nbsp;&nbsp;&nbsp;</tr>");
				frm.setHidProdList("");
			}
			else if (vec_Success.size() > 1)
			{
				out.println("<tr><td colspan='2'> <font size='2px' face='Verdana' color='#3323C7'><img src='/ProDACS/images/active.gif' border='0'> "+vec_Success.size()+" Production Entries are Added. </font></td></tr>");
				out.println("<tr>&nbsp;&nbsp;&nbsp;</tr>");
			}

			/* For Machine Failure */
			vec_MachineFail = (Vector) hm_Result.get("MachineFailure");
			if (BuildConfig.DMODE)
				System.out.println("Machine Failure: "+ vec_MachineFail.size());

			/* For Employee Failure */
			vec_EmpFail = (Vector) hm_Result.get("EmpFailure");
			if (BuildConfig.DMODE)
				System.out.println("Employee Failure: "+ vec_EmpFail.size());
				
			/* For Duplicate Entries */
			vec_DuplicateEntry = (Vector) hm_Result.get("DuplicateEntry");
			if (BuildConfig.DMODE)
				System.out.println("Duplicate Entry: "+ vec_DuplicateEntry.size());
			
			/* For Duty Failure Entries */
			vec_DutyFail = (Vector) hm_Result.get("DutyFailure");
			if (BuildConfig.DMODE)
				System.out.println("Duty Failure :"+vec_DutyFail.size());


			if (vec_MachineFail.size() == 1)
			{
				out.println("<tr><td colspan='2'><font size='2px' face='Verdana' color='#3323C7'><img src='/ProDACS/images/inactive.gif' border='0'> "+vec_MachineFail.size()+" Production Entry Not Added due to Machine Hours Invalid. </font></td></tr>");
			}
			else if (vec_MachineFail.size() > 1)
			{
				out.println("<tr><td colspan='2'> <font size='2px' face='Verdana' color='#3323C7'><img src='/ProDACS/images/inactive.gif' border='0'> "+vec_MachineFail.size()+" Production Entries are Not Added due to Machine Hours Invalid. </font></td></tr>");
			}
			if (vec_EmpFail.size() == 1)
			{
				out.println("<tr><td colspan='2'> <font size='2px' face='Verdana' color='#3323C7'><img src='/ProDACS/images/inactive.gif' border='0'> "+vec_EmpFail.size()+" Production Entry Not Added due to Employee Hours Invalid. </font></td></tr>");
				for (int x = 0; x < vec_EmpFail.size(); x++)
				{
					HashMap hm_EmpFail = (HashMap) vec_EmpFail.get(x);
					Set set = hm_EmpFail.entrySet();
					Iterator it = set.iterator();
					while(it.hasNext())
					{
						Map.Entry me = (Map.Entry) it.next();
						out.println("<tr><td colspan='2'><li><font size='1px' face='Verdana' color='#FF3300'>"+me.getKey()+"</font></li></td></tr>");
					}
				}
			}
			else if (vec_EmpFail.size() > 1)
			{
				out.println("<tr><td colspan='2'> <font size='2px' face='Verdana' color='#3323C7'><img src='/ProDACS/images/inactive.gif' border='0'> "+vec_EmpFail.size()+" Production Entries are Not Added due to Employee Hours Invalid. </font></td></tr>");
				for (int x = 0; x < vec_EmpFail.size(); x++)
				{
					HashMap hm_EmpFail = (HashMap) vec_EmpFail.get(x);
					Set set = hm_EmpFail.entrySet();
					Iterator it = set.iterator();
					while(it.hasNext())
					{
						Map.Entry me = (Map.Entry) it.next();
						out.println("<tr><td colspan='2'><li><font size='1px' face='Verdana' color='#FF3300'>"+me.getKey()+"</font></li></td></tr>");
					}
				}
			}
			if (vec_DuplicateEntry.size() == 1)
			{
				out.println("<tr><td colspan='2'> <font size='2px' face='Verdana' color='#3323C7'><img src='/ProDACS/images/inactive.gif' border='0'> "+vec_DuplicateEntry.size()+" Production Entry already made. </font></td></tr>");
			}
			else if (vec_DuplicateEntry.size() > 1)
			{
				out.println("<tr><td colspan='2'> <font size='2px' face='Verdana' color='#3323C7'><img src='/ProDACS/images/inactive.gif' border='0'> "+vec_DuplicateEntry.size()+" Production Entries already made. </font></td></tr>");
			}
			out.println("<tr>&nbsp;&nbsp;&nbsp;</tr>");
			
			if (vec_DutyFail.size() == 1)
			{
				out.println("<tr><td colspan='2'> <font size='2px' face='Verdana' color='#3323C7'><img src='/ProDACS/images/inactive.gif' border='0'> "+vec_DutyFail.size()+" Production Entry not made. Following Employee Duty Hours not allowed for multiple shifts. </font></td></tr>");
				for (int x = 0; x < vec_DutyFail.size(); x++)
				{
					HashMap hm_DutyFail = (HashMap) vec_DutyFail.get(x);
					Set set = hm_DutyFail.entrySet();
					Iterator it = set.iterator();
					while(it.hasNext())
					{
						Map.Entry me = (Map.Entry) it.next();
						out.println("<tr><td colspan='2'><li><font size='1px' face='Verdana' color='#FF3300'>"+me.getKey()+"</font></li></td></tr>");
					}
				}
			}
			else if (vec_DutyFail.size() > 1)
			{
				out.println("<tr><td colspan='2'> <font size='2px' face='Verdana' color='#3323C7'><img src='/ProDACS/images/inactive.gif' border='0'> "+vec_DutyFail.size()+" Production Entries not made. Following Employee Duty Hours not allowed for multiple shifts.</font></td></tr>");
				for (int x = 0; x < vec_DutyFail.size(); x++)
				{
					HashMap hm_DutyFail = (HashMap) vec_DutyFail.get(x);
					Set set = hm_DutyFail.entrySet();
					Iterator it = set.iterator();
					while(it.hasNext())
					{
						Map.Entry me = (Map.Entry) it.next();
						out.println("<tr><td colspan='2'><li><font size='1px' face='Verdana' color='#FF3300'>"+me.getKey()+"</font></li></td></tr>");
					}
				}
			}
			out.println("<tr>&nbsp;&nbsp;&nbsp;</tr>");
		}
%>
		</table>
        <br>
		<table width="100%" cellpadding="0" cellspacing="0">
 		<tr id="prodInfo">
            	<td>
			<table width="100%" cellspacing="0" cellpadding="0">
                	<tr>
	                  <td width="50" class="FieldTitle"><bean:message key="prodacs.job.date"/><span class="mandatory">*</span></td>
	                  <td width="1" class="FieldTitle">:</td>
	                  <td width="150" class="FieldTitle"><html:text property="proDate" styleClass="TextBox" size="12" readonly="true"/>
	                    <img src='<bean:message key="context"/>/images/calendar.gif' width="16" height="15" align="absmiddle" onClick='show_calendar("proDate",ProductionAdd.proDate.value,event.clientX,event.clientY,false);clearAll();loadProdList();' onMouseOver="this.style.cursor='hand'"></td>

	                  <td width="50" class="FieldTitle"><bean:message key="prodacs.production.shift"/><span class="mandatory">*</span></td>
	                  <td width="1" class="FieldTitle">:</td>
	                  <td width="100" class="FieldTitle">
					  <div id="shiftName">
						<html:select property="proShift" styleClass="Combo" onchange="loadMachines();">
	                      <html:option value="0">-- Select Shift --</html:option>
	                      <html:options collection="dateShift" property="key" labelProperty="value"/>
	                    </html:select>
					  </div>
					  </td>

	                  <td width="65" class="FieldTitle"><bean:message key="prodacs.production.machine"/><span class="mandatory">*</span></td>
	                  <td width="1" class="FieldTitle">:</td>
	                  <td class="FieldTitle">
					  <div id="machNames">
					  <html:select property="proMachine" styleClass="Combo">
	                        <html:option value="0">-- Select Machine --</html:option>
							<%
								Iterator it = shiftMachs.keySet().iterator();
								while(it.hasNext())
								{
									String key=it.next().toString();
							%>
							<html:option value="<%=key%>"><%=key%></html:option>
							<%
							}
							%>
	                      <!--html:options collection="shiftMachs" property="key" labelProperty="key"/-->
	                    </html:select>
					  </div>
					  </td>
			</tr>
			</table>
              	<br>
            	<table width="100%" cellpadding="0" cellspacing="0" id="BtnBgLft">
                	<tr>
	                  <td><html:button styleClass="Button" property="proNextBtn1" onclick="hideNshowDateShiftMachine(); ">Next</html:button></td>
                	</tr>
            	</table>
		</td>
      	<td valign="top">&nbsp;</td>
      	</tr>
	      <tr id="prodShftRow" style="display:none">
	      <td style="border-top:dashed 1px #666666">
			<table width="100%" cellpadding="0" cellspacing="0">
	            <tr id="tblProdShft">
	                  <td width="90" class="FieldTitle"><bean:message key="prodacs.job.date"/></td>
	                  <td width="1" class="FieldTitle">:</td>
	                  <td width="150" class="ViewData">&nbsp;</td>
	                  <td width="50" class="FieldTitle"><bean:message key="prodacs.production.shift"/></td>
	                  <td width="1" class="FieldTitle">:</td>
	                  <td width="100" class="ViewData">&nbsp;</td>
	                  <td width="65" class="FieldTitle"><bean:message key="prodacs.production.machine"/></td>
	                  <td width="1" class="FieldTitle">:</td>
	                  <td class="ViewData">&nbsp;</td>
	            </tr>
	            <tr>
	                  <td width="90" class="FieldTitle"><bean:message key="prodacs.workorder.workorder"/></td>
	                  <td width="1" class="FieldTitle">:</td>
	                  <td colspan="7" class="ViewData"><html:select property="proWorkOrderHash" styleClass="Combo" onchange="loadJobDetByWOId(this.options[this.selectedIndex].value);">
	                      <html:option value="0">-- Select WorkOrder --</html:option>
	                      <html:options collection="workOrderDet" property="key" labelProperty="value"/>
	                    </html:select></td>
	            </tr>
	            </table>
	            <br>
				<table width="100%" cellpadding="0" cellspacing="0" id="tblWorkOrdInfo">
	            <tr>
	                  <td width="25" class="Header">&nbsp;</td>
	                  <td class="Header"><bean:message key="prodacs.job.jobname"/></td>
	                  <td width="75" class="Header"><bean:message key="prodacs.production.totalqty"/></td>
	                  <td width="85" class="Header"><bean:message key="prodacs.production.pendingqtyseriel"/></td>
	                  <td width="120" class="Header"><bean:message key="prodacs.production.postedqtyseriel"/></td>
	                  <td width="120" class="Header"><bean:message key="prodacs.production.unpostedqtyseriel"/></td>
	                  <td width="120" class="Header"><bean:message key="prodacs.production.lastproductiondateshift"/></td>
	            </tr>
				<tr>
					  <td  class="TableItems"><input type=radio name="rdWrkOrd" onclick="showHideTables();"></td>
					  <td class="TableItems"><input type="text" class="TextBoxFull" name="jbName"/></td>
  					  <td class="TableItems"><input type="text" class="TextBoxFull" name="totQty"/></td>
					  <td class="TableItems"><input type="text" class="TextBoxFull" name="pendingQty"/></td>
					  <td class="TableItems"><input type="text" class="TextBoxFull" name="postedQty"/></td>
					  <td class="TableItems"><input type="text" class="TextBoxFull" name="unPostedQty"/></td>
					  <td class="TableItems"><input type="text" class="TextBoxFull" name="lProdDate"/></td>
				</tr>
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
			      <td onClick="showHideImg(document.getElementById('prodInfo'), document.getElementById('prodShftImg'))"><img src='<bean:message key="context"/>/images/hide.gif' id="prodShftImg"></td>
			      <!-- showHide(document.getElementById('tblProdShft'), document.getElementById('tblProdShft2'), document.getElementById('prodShftImg'), document.getElementById('prodShft2Img')) -->
		      </tr>
		      </table>
	      </td>
	      </tr>
		<tr id="wrkOrder" style="display:none">
	      <td style="border-top:dashed 1px #666666">
			<table width="100%" cellpadding="0" cellspacing="0">
	            <tr id="tblProdShft2">
	                  <td width="90" class="FieldTitle"><bean:message key="prodacs.job.date"/></td>
	                  <td width="1" class="FieldTitle">:</td>
	                  <td width="150" class="ViewData">&nbsp;</td>
	                  <td width="90" class="FieldTitle"><bean:message key="prodacs.production.shift"/></td>
	                  <td width="1" class="FieldTitle">:</td>
	                  <td width="150" class="ViewData">&nbsp;</td>
	                  <td width="90" class="FieldTitle"><bean:message key="prodacs.production.machine"/></td>
	                  <td width="1" class="FieldTitle">:</td>
	                  <td class="ViewData">&nbsp;</td>
	            </tr>
	            <tr id="tblProdWrkOrd">
	                  <td width="90" class="FieldTitle"><bean:message key="prodacs.workorder.workorder"/></td>
	                  <td width="1" class="FieldTitle">:</td>
	                  <td class="ViewData">&nbsp;</td>
	                  <td class="FieldTitle"><bean:message key="prodacs.job.jobname"/></td>
	                  <td class="FieldTitle">:</td>
	                  <td class="ViewData">&nbsp;</td>
	            </tr>
	            <tr>
	                  <td colspan="9" class="FieldTitle">
				  <html:radio property="proNmlorRwk" value="normal"/><bean:message key="prodacs.production.normal"/>
	                    <html:radio property="proNmlorRwk" value="rework"/><bean:message key="prodacs.production.rework"/>
	                    <!--html:checkbox property="proIncentive" value="1"/--><!--bean:message key="prodacs.production.incentive"/-->
				</td>
	            </tr>
	            </table>
	            <br>
	            <table width="200" cellspacing="0" cellpadding="0" align="right" id="viewOpns">
	            <tr>
					<td class="TopLnk">[ <a href="#" onmouseover="window.status='View Operations Info'; return true" onmouseout="window.status=''; return true" onClick="viewOpns();">View Operations</a> ]</td>
				</tr>
				</table>
	            <br>
	            <table width="100%" cellspacing="0" cellpadding="0" id="tblJobOperDet">
	            <tr>
	            	<td width="25" class="Header"><input type="checkbox" name="checkbox2" value="checkbox" onclick="chkAll(this);"></td>
	                  <td width="100" class="Header"><bean:message key="prodacs.workorder.jobqtysno"/></td>
	                  <td width="110" class="Header"><bean:message key="prodacs.production.pendingoperationsno"/></td>
	                  <td width="110" class="Header"><bean:message key="prodacs.workorder.reworkoperationsno"/></td>
	                  <td width="130" class="Header"><bean:message key="prodacs.production.postedoperationcompletion"/></td>
	                  <td class="Header"><bean:message key="prodacs.production.unpostedoperationcompletion"/></td>
	                  <td width="120" class="Header"><bean:message key="prodacs.production.lastproductiondateshift"/></td>
	            </tr>
	            <logic:iterate id="bt2" name="vecJobOpnDet" indexId="count">
			<tr>
				<%
					if(count.intValue()%2 == 0)
					{
				%>
					   <td class="TableItems"><input type="checkbox" name="CheckValue" value='<bean:write name="bt2" property="jobStatId"/>' ></td>
				            <td class="TableItems"><bean:write name="bt2" property="jobQtySno"/></td>
				            <td class="TableItems"><input type="text" class="TextBoxFull" readonly name="pendOpnSno" value='<bean:write name="bt2" property="pendingOpnSnos"/>'></td>
				            <td class="TableItems"><input type="text" class="TextBoxFull" readonly name="rwkOpnSno" value='<bean:write name="bt2" property="rwkOpns"/>'></td>
				            <td class="TableItems"><bean:write name="bt2" property="postedOpnSnos"/>&nbsp;</td>
				            <td class="TableItems"><bean:write name="bt2" property="unPostedOpnSnos"/>&nbsp;</td>
				            <td class="TableItems"><logic:notEmpty name="bt2" property="lastProdDate">
														<bean:define id="date1" name="bt2" property="lastProdDate"/>
														<%= date1.toString().substring(0,10) %>
												   </logic:notEmpty>&nbsp;</td>
				<%
					}
					else
					{
				%>
					   <td class="TableItems2"><input type="checkbox" name="CheckValue" value='<bean:write name="bt2" property="jobStatId"/>'></td>
				            <td class="TableItems2"><bean:write name="bt2" property="jobQtySno"/></td>
				            <td class="TableItems2"><input type="text" name="pendOpnSno" class="TextBoxFull" readonly value='<bean:write name="bt2" property="pendingOpnSnos"/>'></td>
				            <td class="TableItems2"><input type="text" name="rwkOpnSno" class="TextBoxFull" readonly value='<bean:write name="bt2" property="rwkOpns"/>'></td>
				            <td class="TableItems2"><bean:write name="bt2" property="postedOpnSnos"/>&nbsp;</td>
				            <td class="TableItems2"><bean:write name="bt2" property="unPostedOpnSnos"/>&nbsp;</td>
				            <td class="TableItems2"><logic:notEmpty name="bt2" property="lastProdDate">
														<bean:define id="date2" name="bt2" property="lastProdDate"/>
														<%= date2.toString().substring(0,10) %>
													</logic:notEmpty>&nbsp;</td>
				<%
					}
				%>
			</tr>
	   	 	</logic:iterate>
	            </table>
	            <br>
	            <table width="100%" cellspacing="0" cellpadding="0">
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
	                  <td onClick="showHideImg(document.getElementById('prodInfo'), document.getElementById('prodShft2Img'))"><img src='<bean:message key="context"/>/images/hide.gif' id="prodShft2Img"></td>
	            </tr>
	            </table>
	            <table width="100%" cellpadding="0" cellspacing="0" id="ShowHide">
	            <tr>
	                  <td onClick="showHideImg(document.getElementById('prodShftRow'), document.getElementById('prodShftRowImg'))"><img src='<bean:message key="context"/>/images/hide.gif' id="prodShftRowImg"></td>
	            </tr>
	        </table>
		</td>
	      </tr>
	      <tr id="jobQty" style="display:none">
	      <td style="border-top:dashed 1px #666666">
			<table width="100%" cellpadding="0" cellspacing="0">
	            <tr id="tblProdShft3" style="display:none">
	                  <td width="90" class="FieldTitle"><bean:message key="prodacs.job.date"/></td>
	                  <td width="1" class="FieldTitle">:</td>
	                  <td width="150" class="ViewData">&nbsp;</td>
	                  <td width="50" class="FieldTitle"><bean:message key="prodacs.production.shift"/></td>
	                  <td width="1" class="FieldTitle">:</td>
	                  <td width="100" class="ViewData">&nbsp;</td>
	                  <td width="65" class="FieldTitle"><bean:message key="prodacs.production.machine"/></td>
	                  <td width="1" class="FieldTitle">:</td>
	                  <td class="ViewData">&nbsp;</td>
	            </tr>
	            <tr id="tblProdWrkOrd2">
	                  <td class="FieldTitle"><bean:message key="prodacs.workorder.workorder"/></td>
	                  <td class="FieldTitle">:</td>
	                  <td class="ViewData">&nbsp;</td>
	                  <td class="FieldTitle"><bean:message key="prodacs.job.jobname"/></td>
	                  <td class="FieldTitle">:</td>
	                  <td class="ViewData">&nbsp;</td>
	                  <td class="FieldTitle"><bean:message key="prodacs.production.worktype"/></td>
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
	                  <!--td class="FieldTitle"><bean:message key="prodacs.production.incentive"/></td>
	                  <td class="FieldTitle">:</td>
	                  <td class="ViewData"><input type="checkbox" name="checkIncentive" disabled></td-->
	                  <td class="FieldTitle"><bean:message key="prodacs.production.totalhours"/><span class="mandatory">*</span></td>
	                  <td class="FieldTitle">:</td>
	                  <td class="ViewData"><html:text property="proTotalHours" onkeypress="return isNumber(this);" onkeyup="return isNumber(this);" maxlength="6" styleClass="TextBox" size="12"/></td>
			</tr>
	            </table>
	            <br>
			<table width="100%" cellspacing="0" cellpadding="5">
			<tr>
				<td class="TopLnk"> [ <a href="#" onClick="delRow(document.forms[0]);"><bean:message key="prodacs.common.delete"/></a> ]</td>
			</tr>
			</table>
			<table width="100%" cellspacing="0" cellpadding="0">
	            <tr>
	            <td width="50%" valign="top" class="ViewData">
				<table width="100%" cellspacing="0" cellpadding="3" id="tblEmpDetMstr">
	                  <tr>
	                        <td width="75" class="Header"><bean:message key="prodacs.production.emptype"/></td>
	                        <td class="Header"><bean:message key="prodacs.production.empname"/></td>
	                        <td width="60" class="Header"><bean:message key="prodacs.production.dutyhrs"/></td>
	                        <td width="50" class="Header"><bean:message key="prodacs.production.othrs"/></td>
	                        <td width="20" class="Header">&nbsp;</td>
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
		                <%
		                 boolean dtHrs = false;
		                 boolean otHrs = false;
  	                 	 dtHrs = ((Boolean)empTypRule.get("DT")).booleanValue();
  	                 	 otHrs = ((Boolean)empTypRule.get("OT")).booleanValue();

		                 if (dtHrs == true)
		                 {
		                %>
		                <td class="TableItems"><html:text property="proDutyHrs" onkeypress="return isNumber(this);" onkeyup="return isNumber(this);" maxlength="6" size="7" styleClass="TextBox"/></td>
		                <%
		                }
		                else
		                {
		                %>
		                <td class="TableItems"><html:text property="proDutyHrs" onkeypress="return isNumber(this);" onkeyup="return isNumber(this);" maxlength="6" size="7" styleClass="disabledText" value="0" readonly="true"/></td>
		                <%
		                }
		                if (otHrs == true)
		                {
		                %>
		                <td class="TableItems"><html:text property="proOTHrs" onkeypress="return isNumber(this);" onkeyup="return isNumber(this);" maxlength="6" size="7" styleClass="TextBox"/></td>
		                <%
		                }
		                else
		                {
		                %>
		                <td class="TableItems"><html:text property="proOTHrs" onkeypress="return isNumber(this);" onkeyup="return isNumber(this);" maxlength="6" size="7" styleClass="disabledText" value="0" readonly="true"/></td>
		                <%
		                }
		                %>
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
						<%
		                 boolean dtHrs = false;
		                 boolean otHrs = false;
  	                 	 dtHrs = ((Boolean)empTypRule.get("DT")).booleanValue();
  	                 	 otHrs = ((Boolean)empTypRule.get("OT")).booleanValue();

						if (dtHrs == true)
						{
						%>
						<td class="TableItems2"><html:text property="proDutyHrs" onkeypress="return isNumber(this);" onkeyup="return isNumber(this);" maxlength="6" size="7" styleClass="TextBox"/></td>
						<%
						}
						else
						{
						%>
		                <td class="TableItems2"><html:text property="proDutyHrs" onkeypress="return isNumber(this);" onkeyup="return isNumber(this);" maxlength="6" size="7" styleClass="disabledText" value="0" readonly="true"/></td>
						<%
						}
						if (otHrs == true)
						{
						%>
		                <td class="TableItems2"><html:text property="proOTHrs" onkeypress="return isNumber(this);" onkeyup="return isNumber(this);" maxlength="6" size="7" styleClass="TextBox"/></td>
						<%
						}
						else
						{
						%>
						<td class="TableItems2"><html:text property="proOTHrs" onkeypress="return isNumber(this);" onkeyup="return isNumber(this);" maxlength="6" size="7" styleClass="disabledText" value="0" readonly="true"/></td>
						<%
						}
						%>
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
						<td width="60" class="Header"><bean:message key="prodacs.production.dutyhrs"/></td>
						<td width="50" class="Header"><bean:message key="prodacs.production.othrs"/></td>
					</tr>
					</table>
					<table width="100%" cellspacing="0" cellpadding="0" id="tblEmpDetails">
					<tr>
	    			    		<td width="28" class="TableItems"><input type="checkbox" name="ckdCkBx"></td>
						<td width="78" class="TableItems"><input type="text" class="TextBoxFull" readonly name="empTypName"></td>
						<td class="TableItems"><input type="text" class="TextBoxFull" readonly name="empName"></td>
						<td width="63" class="TableItems"><input type="text" class="TextBoxFull" readonly name="empDutyHrs"></td>
						<td width="53" class="TableItems"><input type="text" class="TextBoxFull" readonly name="empOTHrs"></td>
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
	                  	<td onClick="showHideImg(document.getElementById('prodInfo'), document.getElementById('prodShft3Img'))"><img src='<bean:message key="context"/>/images/hide.gif' name="prodShft3Img" id="prodShft3Img"></td>
	                	</tr>
	              	</table>
	              	<table width="100%" cellpadding="0" cellspacing="0" id="ShowHide">
	                	<tr>
	                  	<td onClick="showHideImg(document.getElementById('prodShftRow'), document.getElementById('prodShftRow2Img'))"><img src='<bean:message key="context"/>/images/hide.gif' id="prodShftRow2Img"></td>
	                	</tr>
	              	</table>
	              	<table width="100%" cellpadding="0" cellspacing="0" id="ShowHide">
	                	<tr>
		                  <td onClick="showHideImg(document.getElementById('wrkOrder'), document.getElementById('prodShftRow3Img'))"><img src='<bean:message key="context"/>/images/hide.gif' id="prodShftRow3Img"></td>
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
               		[ <a href="#" onClick="modifyRow(document.forms[0]);"><bean:message key="prodacs.common.modify"/></a> ] [ <a href="#" onClick="delBufferRow(document.forms[0]);"><bean:message key="prodacs.common.delete"/></a> ] [ <a href="#" onClick="makeCopy(document.forms[0]);">Make Copy</a> ]</td>
				</tr>
				</table>

				<table width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td width="25" class="Header"><input name="chkAllProdBufHead" type="checkbox" id="CheckAllDymSub" onClick="chkAllTblBffer();"/></td>
						<td width="30" class="Header"><bean:message key="prodacs.common.sno"/></td>
						<td width="75" class="Header"><bean:message key="prodacs.job.date"/></td>
						<td width="70" class="Header"><bean:message key="prodacs.production.shift"/></td>
						<td width="70" class="Header"><bean:message key="prodacs.production.machine"/></td>
						<td width="40" class="Header"><bean:message key="prodacs.workorder.workorderno"/></td>
						<td class="Header"><bean:message key="prodacs.job.jobname"/></td>
						<td width="80" class="Header"><bean:message key="prodacs.production.worktype"/></td>
						<!--td width="50" class="Header"><bean:message key="prodacs.production.incntv"/></td-->
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
					  <td width="67" class="TableItems"><input readonly name="prodMachine" class="TextBoxFull" /></td>
					  <td width="37" class="TableItems"><input readonly name="prodWoNo" class="TextBoxFull" /></td>
					  <td class="TableItems"><input readonly name="prodJobName" class="TextBoxFull" /></td>
					  <td width="77" class="TableItems"><input readonly name="prodWrkTyp" class="TextBoxFull" /></td>
					  <!--td width="47" class="TableItems"><input readonly name="prodIncentive" class="TextBoxFull" /></td-->
					  <td width="47" class="TableItems"><input readonly name="prodStrOpn" class="TextBoxFull" /></td>
					  <td width="57" class="TableItems"><input readonly name="prodEdOpn" class="TextBoxFull" /></td>
					  <td width="57" class="TableItems"><input readonly name="prodTotHrs" class="TextBoxFull" /></td>
					  <td><input type="hidden" name="prodShiftId"/></td>
					  <td><input type="hidden" name="prodWoId"/></td>
					  <td><input type="hidden" name="prodJobId"/></td>
					  <td><input type="hidden" name="prodChkdQty"/></td>
					  <td><input type="hidden" name="prodEmpDet"/></td>
					  <td><input type="hidden" name="woJbStatIds"/></td>
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
