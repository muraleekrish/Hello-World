<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<jsp:useBean id="frm" class="com.savantit.prodacs.presentation.tableadmin.production.PayOutProductionAddForm" />
<jsp:setProperty name="frm" property="*" /> 
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.GregorianCalendar"%>
<%@ page import="java.util.StringTokenizer"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<%@ page import="com.savantit.prodacs.facade.SessionPopDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionPopDetailsManager"%>
<%@ page import="com.savantit.prodacs.businessimplementation.securityadmin.UserAuthDetails"%>
<useradmin:userrights resource="1020"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("PayOut Production Edit");
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	HashMap popEmpList = new HashMap();
	HashMap dtOtEmpList = new HashMap();
	UserAuthDetails userDetails = (UserAuthDetails)session.getAttribute("##userRights##");
	String userName = userDetails.getUserId();
	try
	{
		if (BuildConfig.DMODE)
			System.out.println("POP Edit Starts.");
		
		/* 	Setting the JNDI name and Environment 	*/
	   	obj.setJndiName("SessionPopDetailsManagerBean");
   		obj.setEnvironment();

		/* 	Creating the Home and Remote Objects 	*/
		SessionPopDetailsManagerHome popHomeObj = (SessionPopDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionPopDetailsManagerHome.class);
		SessionPopDetailsManager popObj = (SessionPopDetailsManager)PortableRemoteObject.narrow(popHomeObj.create(),SessionPopDetailsManager.class);
		
		/* For Shift List */
		HashMap popShiftList = popObj.getShiftDefnNameList();
		pageContext.setAttribute("popShiftList",popShiftList);

		if (!frm.getPopFromDate().trim().equalsIgnoreCase(""))
		{
			StringTokenizer stDate = new StringTokenizer(frm.getPopFromDate().trim(),"-");
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
		
			popShiftList = popObj.getShiftDefnNameList(gc.getTime());
			if (BuildConfig.DMODE)
				System.out.println("Shifts for Date(HM) :"+popShiftList);
			pageContext.setAttribute("popShiftList",popShiftList);
		}
		else
		{
			pageContext.setAttribute("popShiftList",popShiftList);
		}
		
		/* For Employee Type */
		HashMap popEmpType = popObj.getAllEmployeeTypes();
		pageContext.setAttribute("popEmpType",popEmpType);
		
		/* Loading Employee's List */
		if (!frm.getHidEmpTypId().equalsIgnoreCase(""))
		{
			popEmpList = popObj.getAllEmployeesByType(Integer.parseInt(frm.getHidEmpTypId()));
			dtOtEmpList = popObj.getAllEmployeesByTypes(Integer.parseInt(frm.getHidEmpTypId()));
			if (BuildConfig.DMODE)
				System.out.println("dtOtEmpList [HM]:"+dtOtEmpList);
			pageContext.setAttribute("popEmpList",popEmpList);
			pageContext.setAttribute("dtOtEmpList",dtOtEmpList);
		}
		else
		{
			pageContext.setAttribute("popEmpList",popEmpList);
		}
	}catch (Exception e)
	{
		if (BuildConfig.DMODE)
		{
			System.out.println("Problem in POP Edit.jsp");
			e.printStackTrace();
		}
	}
%>
<html:html>
<head>
<title><bean:message key="prodacs.production.payoutsideproduction"/></title>
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

	 function dateSubmit()
	 {
		 var temp = document.forms[0];
		 temp.formAction.value = "dateSubmit";
		 //alert(temp.formAction.value);
		 temp.submit();
	 }
	 
</script>

<script>
function chkAll(frmPage) // Check All Script for Dynamic Table List
	{
		table = dvPayOutProd.children[0].children[0];
		if(frmPage.CheckAll.checked == true)
			{
//				alert(table.innerHTML);
				for(i=0;i<table.children.length;i++)
					{
						table.children[i].children[0].children[0].checked=true;
						if(table.children[i].children[0].className == "InValid")
							{
								table.children[i].children[0].children[0].checked=false;
							}
					}
			}
		else
			{
				for(i=0;i<table.children.length;i++)
				{
					table.children[i].children[0].children[0].checked=false;
				}
			}
	}
</script>
<script>
	
	function listItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/Production/PayOutProductionList.jsp';
		document.forms[0].submit();
	}

	function addItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/Production/PayOutProductionAdd.jsp';
		document.forms[0].submit();
	}
	
	function loadEmpList()
	{
		var temp = document.forms[0];
		temp.hidEmpTypId.value = temp.employeeType.options[temp.employeeType.selectedIndex].value;
		temp.formAction.value = "load";
		loadEmpDet();
		temp.submit();
	}

	function showPop()
	{
		document.forms[0].hidUserId.value = '<%= userName %>';
		if (document.forms[0].hidEmpTypId.value == "")
			tblPayOutProdList.style.display='none';
		else
			tblPayOutProdList.style.display='block';
	}

	function transEmpDet()
	{
		var temp = document.forms[0];
		var obj = document.getElementById("tblEmpDetails");

		/* Emp Type Name can't be Empty */
		if (temp.employeeType.options[temp.employeeType.selectedIndex].value == "0")
		{
			alert ("Employee Type can't be Empty!");
			return false;
		}

		/* Emp Name can't be Empty */
		if (temp.employeeName.options[temp.employeeName.selectedIndex].value == "0")
		{
			alert ("Employee Name can't be Empty!");
			return false;
		}

		/* Duty Hrs can't be Empty */
		if (((parseFloat(temp.dutyHrs.value) + parseFloat(temp.otHrs.value)) == 0) || ((temp.dutyHrs.value + temp.otHrs.value) == "") || ((temp.dutyHrs.value + temp.otHrs.value) == 0) ||
			((temp.dutyHrs.value == "") && (temp.otHrs.value == "")))
		{
			if (('<%= request.getParameter("DT") %>' == 0) || ('<%= request.getParameter("OT") %>' == 0))
			{
				alert ("Duty + OT Hrs can't be Zero!");
				return false;
			}
		}

		/* Employee name reduntancy not allowed */
		for (var i = 0; i < obj.children.length; i++)
		{
			if (obj.children(i).children(0).children(6).children(0).value == temp.employeeName.value)
			{
				alert ("The Employee '"+temp.employeeName.options[temp.employeeName.selectedIndex].text+"' details already shifted.");
				return false;
			}
		}

		if ((obj.children.length == "1") && (obj.children(0).children(0).children(1).children(0).value == ""))
		{
			obj.children(0).children(0).children(1).children(0).value = temp.employeeType.options[temp.employeeType.selectedIndex].text; // Emp Typ Name
			obj.children(0).children(0).children(2).children(0).value = temp.employeeName.options[temp.employeeName.selectedIndex].text; // Emp Name 
			obj.children(0).children(0).children(3).children(0).value = (temp.dutyHrs.value == "")?(0):(temp.dutyHrs.value); // Duty Hours 
			obj.children(0).children(0).children(4).children(0).value = (temp.otHrs.value == "")?(0):(temp.otHrs.value); // OT Hrs
			obj.children(0).children(0).children(5).children(0).value = temp.employeeType.options[temp.employeeType.selectedIndex].value; // Emp Typ Id
			obj.children(0).children(0).children(6).children(0).value = temp.employeeName.options[temp.employeeName.selectedIndex].value; // Emp Id
		}
		else
		{
			var newNode = obj.children(0).cloneNode(true);
			obj.appendChild(newNode);
			var len = obj.children.length;

			obj.children(len-1).children(0).children(1).children(0).value = temp.employeeType.options[temp.employeeType.selectedIndex].text;
			obj.children(len-1).children(0).children(2).children(0).value = temp.employeeName.options[temp.employeeName.selectedIndex].text;
			obj.children(len-1).children(0).children(3).children(0).value = (temp.dutyHrs.value == "")?(0):(temp.dutyHrs.value); // Duty Hours 
			obj.children(len-1).children(0).children(4).children(0).value = (temp.otHrs.value == "")?(0):(temp.otHrs.value); // OT Hrs
			obj.children(len-1).children(0).children(5).children(0).value = temp.employeeType.options[temp.employeeType.selectedIndex].value;
			obj.children(len-1).children(0).children(6).children(0).value = temp.employeeName.options[temp.employeeName.selectedIndex].value;
		}

		/* Clear the Fields */
		temp.dutyHrs.value = "";
		temp.otHrs.value = "";
		temp.employeeName.selectedIndex = 0;
	}

	function delRow()
	{
		var temp = document.forms[0];
		var obj = document.getElementById("tblEmpDetails");
		if (obj.children.length > 1)
		{
			for (var i = 0 ; i < obj.children.length; i++)
			{
				if (temp.chkBx[i].checked)
				{
					obj.removeChild(obj.children(i));
					i = 0;
				}
			}
			if (obj.children.length == 1)
			{
				if (temp.chkBx[0].checked)
				{
					obj.children(0).children(0).children(1).children(0).value = "";
					obj.children(0).children(0).children(2).children(0).value = "";
					obj.children(0).children(0).children(3).children(0).value = "";
					obj.children(0).children(0).children(4).children(0).value = "";
					obj.children(0).children(0).children(5).children(0).value = "";
					obj.children(0).children(0).children(6).children(0).value = "";

					temp.chkBx.checked = false;
					temp.checkAll.checked = false;
				}
			}

		}
		else if (obj.children.length == 1)
		{
			if (temp.chkBx.checked)
			{
				obj.children(0).children(0).children(1).children(0).value = "";
				obj.children(0).children(0).children(2).children(0).value = "";
				obj.children(0).children(0).children(3).children(0).value = "";
				obj.children(0).children(0).children(4).children(0).value = "";
				obj.children(0).children(0).children(5).children(0).value = "";
				obj.children(0).children(0).children(6).children(0).value = "";

				temp.chkBx.checked = false;
				temp.checkAll.checked = false;
			}
		}

		loadEmpDet();
	}

	/* To check all the Checkbox in Employee Details */
	function chkAll() 
	{
		var temp = document.forms[0];
		var obj = document.getElementById("tblEmpDetails");
		if (obj.children.length > 1)
		{
			if (temp.checkAll.checked)
			{
				for (var i = 0; i < temp.chkBx.length; i++)
				{
					temp.chkBx[i].checked = true;
				}
			}
			else
			{
				for (var i = 0; i < temp.chkBx.length; i++)
				{
					temp.chkBx[i].checked = false;
				}
			}
		}
		else if (temp.checkAll.checked)
		{
			temp.chkBx.checked = true;
		}
		else
		{
			temp.chkBx.checked = false;
		}
	}

	function loadEmpDet()
	{
		var temp = document.forms[0];
		var obj = document.getElementById("tblEmpDetails");

		var empDet = "";
		temp.hidEmpDet.value = "";
		if ((obj.children(0).children(0).children(1).children(0).value == "") &&
			(obj.children(0).children(0).children(2).children(0).value == ""))
		{
			return false;
		}
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
				empDet = empDet + "-" + obj.children(0).children(0).children(2).children(0).value; // Emp Name 
				if (obj.children(0).children(0).children(3).children(0).value == "")
				{	t1 = 0;	}
				else {	t1 = obj.children(0).children(0).children(3).children(0).value;	}

				empDet = empDet + "-" + t1; // Duty Hrs 

				if (obj.children(0).children(0).children(4).children(0).value == "")
				{	t2 = 0;	}
				else {	t2 = obj.children(0).children(0).children(4).children(0).value;	}

				empDet = empDet + "-" + t2; // OT Hrs 
				empDet = empDet + "-" + obj.children(0).children(0).children(5).children(0).value; // Emp Typ Id 
				empDet = empDet + "-" + obj.children(0).children(0).children(6).children(0).value; // Emp Name Id 
			}
			else
			{
				empDet = empDet + "" + obj.children(i).children(0).children(1).children(0).value; // EmpTyp name 
				empDet = empDet + "-" + obj.children(i).children(0).children(2).children(0).value; // Emp Name 

				if (obj.children(i).children(0).children(3).children(0).value == "")
				{	t1 = 0;	}
				else {	t1 = obj.children(i).children(0).children(3).children(0).value;	}

				empDet = empDet + "-" + t1; // Duty Hrs 

				if (obj.children(i).children(0).children(4).children(0).value == "")
				{	t2 = 0;	}
				else {	t2 = obj.children(i).children(0).children(4).children(0).value;	}

				empDet = empDet + "-" + t2; // OT Hrs 

				empDet = empDet + "-" + obj.children(i).children(0).children(5).children(0).value; // Emp Typ Id 
				empDet = empDet + "-" + obj.children(i).children(0).children(6).children(0).value; // Emp Id 
			}
			i++;
		}
		temp.hidEmpDet.value = empDet;
		temp.formAction.value = "load";
		//alert ("Emp Det on MAIN: "+temp.hidEmpDet.value);
	}

	function loadEmpDetails()
	{
		var temp = document.forms[0];
		var obj = document.getElementById("tblEmpDetails");
		if (temp.hidEmpDet.value == "")
		{
			return false;
		}
		var arEmpDet = (temp.hidEmpDet.value).split("^");
		for (var i = 0; i < arEmpDet.length; i++)
		{
			var arEmpPrnlDet = arEmpDet[i].split("-");
			if (i == 0)
			{
				obj.children(i).children(0).children(1).children(0).value = arEmpPrnlDet[0]; // Type name
				obj.children(i).children(0).children(2).children(0).value = arEmpPrnlDet[1]; // Emp Name
				obj.children(i).children(0).children(3).children(0).value = arEmpPrnlDet[2]; // Duty
				obj.children(i).children(0).children(4).children(0).value = arEmpPrnlDet[3]; // OT
				obj.children(i).children(0).children(5).children(0).value = arEmpPrnlDet[4]; // Type Id
				obj.children(i).children(0).children(6).children(0).value = arEmpPrnlDet[5]; // Emp Id
			}
			else
			{
				/* Inserting a new Row */
				var newNode = obj.children(0).cloneNode(true);
				obj.appendChild(newNode);
				var len = obj.children.length;
				
				/* Removing the Previous values */
				obj.children(len-1).children(0).children(1).children(0).value = ""; // Type name
				obj.children(len-1).children(0).children(2).children(0).value = ""; // Emp Name
				obj.children(len-1).children(0).children(3).children(0).value = ""; // Duty
				obj.children(len-1).children(0).children(4).children(0).value = ""; // OT
				obj.children(len-1).children(0).children(5).children(0).value = ""; // Type Id
				obj.children(len-1).children(0).children(6).children(0).value = ""; // Emp Id

				/* Enter the New values */
				obj.children(len-1).children(0).children(1).children(0).value = arEmpPrnlDet[0]; // Type name
				obj.children(len-1).children(0).children(2).children(0).value = arEmpPrnlDet[1]; // Emp Name
				obj.children(len-1).children(0).children(3).children(0).value = arEmpPrnlDet[2]; // Duty
				obj.children(len-1).children(0).children(4).children(0).value = arEmpPrnlDet[3]; // OT
				obj.children(len-1).children(0).children(5).children(0).value = arEmpPrnlDet[4]; // Type Id
				obj.children(len-1).children(0).children(6).children(0).value = arEmpPrnlDet[5]; // Emp Id
			}
		}
	}

	function clearEmpDet()
	{
		var temp = document.forms[0];
		if (temp.machineShift.value == "0")
		{
			tblPayOutProdList.style.display='none';
			return false;
		}
		tblPayOutProdList.style.display='block';
		temp.checkAll.checked = true; // Make the Emp table header as checked.
		chkAll(); // To call the fn. to check it's children.
		delRow(); // Call the fn. to Delete that rows.
	}

	function addPoPDet()
	{
		var temp = document.forms[0];
		loadEmpDet(); // To hold the Emp Details in a variable.
		//alert (temp.hidEmpDet.value);
		if (temp.popFromDate.value == "") /* Pop Date can't be empty */
		{
			alert ("POP Date is Mandatory!");
			return false;
		}
		
		/* Reason can't be empty */
		if (temp.popReason.value == "")  
		{
			alert ("POP Reason is Mandatory!");
			return false;
		}

		if (temp.hidEmpDet.value == "")
		{
			alert ("No Records found in Pay Outside Production Employee Details!");
			return false;
		}

		temp.formAction.value = "update";
		temp.submit();
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

	function loadToHidden()
	{
		document.forms[0].ids.value = document.forms[0].id.value;
	}
	
	function loadEmpDetEdit()
	{
		var temp = document.forms[0];
		if (temp.formAction.value == 'modify')
		{
			temp.hidEmpDet.value = temp.hidEmpDetails.value;
			tblPayOutProdList.style.display='block';
		}
	}
</script>
</head>

<body onload="init(); showPop(); loadToHidden(); loadEmpDetEdit(); loadEmpDetails();">
<html:form action="frmPayOutProdEdit">
<html:hidden property="formAction"/>
<html:hidden property="id"/>
<html:hidden property="ids"/>
<html:hidden property="hidEmpTypId" value="<%= frm.getHidEmpTypId() %>"/> <!-- Holding the EmpTypId -->
<html:hidden property="hidEmpDet" value="<%= frm.getHidEmpDet()%>" /> <!-- Holding the Emp Details -->
<html:hidden property="hidEmpDetails" /> <!-- Holding the Emp Details in EJB -->
<html:hidden property="hidUserId"/> <!-- Holding the User Name -->

	<table width="100%" cellspacing="0" cellpadding="10">
		<tr>
			<td>
	<table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
		<tr>
			<td><bean:message key="prodacs.production.payoutsideproduction"/></td>
		</tr>
	</table>
	<br>
	<table width="100" cellspacing="0" cellpadding="0" align="right">
	<tr> 
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New PayOutProduction Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1020" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='List PayOutProduction Info'; return true"  onMouseOut="window.status=''; return true" resourceId="20" text="[ List ]" classId="TopLnk" onClick="javaScript:listItem();"/>
	</tr>
	</table>
	<br>
	<table>
	<tr> 
		<td colspan='2'> <font size="1px" face="Verdana">
		<html:errors/></font></td>
	</tr> 
	</table> 
	
	<table width="100%" cellspacing="0" cellpadding="0">
		<tr> 
			<td width="50" class="FieldTitle"><bean:message key="prodacs.production.date"/><span class="mandatory">*</span></td>
			<td width="1" class="FieldTitle">:</td>
			<td class="FieldTitle"><html:text property="popFromDate" styleClass="TextBox" size="12" readonly="true"/> 
			<img src='<bean:message key="context"/>/images/calendar.gif' width="16" height="15" align="absmiddle" onClick='show_calendar("popFromDate",PayOutProductionEdit.popFromDate.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"></td>
			
			<td width="100" >&nbsp;</td>

			<td class="FieldTitle"><bean:message key="prodacs.production.reason"/><span class="mandatory">*</span></td>
			<td class="FieldTitle">:</td>
			<td class="FieldTitle"><html:text property="popReason" styleClass="TextBox" size="25" maxlength="30"/></td>
			
		</tr> 
		<tr>
			<td class="FieldTitle"><bean:message key="prodacs.production.shift"/><span class="mandatory">*</span></td>
			<td class="FieldTitle">:</td>
			<td class="FieldTitle"><html:select property="machineShift" styleClass="Combo">
			<html:option value="0">--Choose Shift--</html:option>
			<html:options collection="popShiftList" property="key" labelProperty="value"/>
			</html:select></td>
		</tr>

	</table>
	<br>
	<hr>
		<!-- 	<table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
				<tr>
				<td><html:button property="BtnBgNext" value="Next" styleClass="Button" onclick="tblPayOutProdList.style.display='block'"/></td>
				</tr>
			</table> -->	
	<table width="100%" cellpadding="0" cellspacing="0" id="tblPayOutProdList" style="display:none">
		<tr>
			<td class="FieldTitle">
			<fieldset id="FieldSet"><legend><bean:message key="prodacs.production.payoutsideproduction"/></legend>
	<br>
	<table width="100%" cellspacing="0" cellpadding="0">
		<tr> 
			<td width="200" class="FieldTitle"><bean:message key="prodacs.production.employeetype"/><span class="mandatory">*</span></td>
			<td width="1" class="FieldTitle">:</td>
			<td width="200" class="FieldTitle"><html:select property="employeeType" styleClass="Combo" onchange="loadEmpList();">
			<html:option value="0">-- Employee Type --</html:option>
			<html:options collection="popEmpType" property="key" labelProperty="value"/>
			</html:select></td>
			<td width="200" class="FieldTitle"><bean:message key="prodacs.production.employeename"/><span class="mandatory">*</span></td>
			<td width="1" class="FieldTitle">:</td>
			<td class="FieldTitle"><html:select property="employeeName" styleClass="Combo">
			<html:option value="0">-- Employee Name --</html:option>
			<html:options collection="popEmpList" property="key" labelProperty="value"/>
			</html:select></td>
		</tr>
		<%
			boolean dtHrs = false;
		    boolean otHrs = false;
			Set set = dtOtEmpList.entrySet();
			Iterator it = set.iterator();
			while (it.hasNext())
			{
				Map.Entry me = (Map.Entry) it.next();
				if (me.getKey().equals("DT"))
				{
					dtHrs = ((Boolean)me.getValue()).booleanValue();
				    if (dtHrs == true)
				    {
		%>
		<tr> 
			<td class="FieldTitle"><bean:message key="prodacs.production.dutyhrs"/><span class="mandatory">*</span></td>
			<td class="FieldTitle">:</td>
			<td class="FieldTitle"><html:text property="dutyHrs" styleClass="TextBox" size="10" onkeypress="return isNumber();" onkeyup="return isNumber();" maxlength="6"/></td>
		<%
					}
					else
					{
		%>
			<td class="FieldTitle"><bean:message key="prodacs.production.dutyhrs"/><span class="mandatory">*</span></td>
			<td class="FieldTitle">:</td>
			<td class="FieldTitle"><html:text property="dutyHrs" size="10" onkeypress="return isNumber();" onkeyup="return isNumber();" maxlength="6" styleClass="disabledText" value="0" readonly="true"/></td>
		<%
					}
				}
				if (me.getKey().equals("OT"))
				{
					otHrs = ((Boolean)me.getValue()).booleanValue();
					if (otHrs == true)
					{
		%>
			<td class="FieldTitle"><bean:message key="prodacs.production.othrs"/><span class="mandatory">*</span></td>
			<td class="FieldTitle">:</td>
			<td class="FieldTitle"><html:text property="otHrs" styleClass="TextBox" size="10"onkeypress="return isNumber();" onkeyup="return isNumber();" maxlength="6"/></td>
		<%
					}
					else
					{
		%>
			<td class="FieldTitle"><bean:message key="prodacs.production.othrs"/><span class="mandatory">*</span></td>
			<td class="FieldTitle">:</td>
			<td class="FieldTitle"><html:text property="otHrs" size="10"onkeypress="return isNumber();" onkeyup="return isNumber();" maxlength="6" styleClass="disabledText" value="0" readonly="true"/></td>
		<%
					}
				}
			}
		%>
		</tr>
	</table>
	<table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
		<tr>
			<td><html:button property="Button" styleClass="Button" value="Move" onclick="transEmpDet();"/></td>
		</tr>
	</table>
	<br>
	<div id="dvPayOutProdList">
	<table width="100%" cellspacing="0" cellpadding="5">
    <tr> 
		<td class="TopLnk">
		[ <a href="#" onClick="delRow(document.forms[0]);"><bean:message key="prodacs.common.delete"/></a> ]</td>
    </tr>
	</table>
	<table width="100%" cellpadding="0" cellspacing="0">
		<tr> 
			<td width="25" class="Header"><input type="checkbox"  name="checkAll" value="checkbox" onclick="chkAll(document.forms[0])"/></td>
			<td width="200" class="Header"><bean:message key="prodacs.production.emptype"/></td>
			<td class="Header"><bean:message key="prodacs.production.empname"/></td>
			<td width="150" class="Header"><bean:message key="prodacs.production.dutyhrs"/></td>
			<td width="150" class="Header"><bean:message key="prodacs.production.othrs"/></td>
		</tr>
	</table>
	<div id="dvPayOutProd" style="overflow:auto; height:105">
	<table width="100%" cellspacing="0" cellpadding="0" id="tblEmpDetails">
		<tr> 
			<td width="22" class="TableItems"><input type="checkbox" name="chkBx" value="checkbox"/></td>
			<td width="197" class="TableItems"><input type="text" class="TextBoxFull" readonly name="empTypName"/></td>
			<td class="TableItems"><input type="text" class="TextBoxFull" readonly name="empName"/></td>
			<td width="147" class="TableItems"><input type="text" class="TextBoxFull" readonly name="dutyHours"/></td>
			<td width="147" class="TableItems"><input type="text" class="TextBoxFull" readonly name="otHours"/></td>
			<td><input type="hidden" name="empTypId"/></td>
			<td><input type="hidden" name="empId"/></td>
		</tr>
	</table>
	</div>
	<table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
		<tr>
			<td><html:button property="save" styleClass="Button" value="Update" onclick="addPoPDet();"/></td>
		</tr>
	</table>
	</div>
	</fieldset>
	</td>
	</tr>
	</table>
	<br>
</td>
</tr>
</table>
</html:form>
</body>
</html:html>
