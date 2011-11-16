<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<%@ page import="com.savantit.prodacs.businessimplementation.securityadmin.UserAuthDetails"%>
<jsp:useBean id="frm" class="com.savantit.prodacs.presentation.tableadmin.payroll.PayrollAdjustmentForm" />
<jsp:setProperty name="frm" property="*" /> 
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.savantit.prodacs.infra.beans.Filter"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<%@ page import="com.savantit.prodacs.facade.SessionPayrollDetailsManager"%>
<%@ page import="com.savantit.prodacs.facade.SessionPayrollDetailsManagerHome"%>
<useradmin:userrights resource="25"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("PayRoll Adjustment Starts.");
	boolean order = true;
	Vector vec_payrollAdjustment = new Vector();
	
	/* Setting the Sorting Order */
	if(frm.getSortOrder().equalsIgnoreCase("ascending"))
	{
		order = true;
	}
	else
	{
		order = false;
	}
	if (BuildConfig.DMODE)
	{
		System.out.println("SortOrder: "+frm.getSortOrder());
		System.out.println("Order: "+order);
	}
	UserAuthDetails userDetails = (UserAuthDetails)session.getAttribute("##userRights##");
	String userName = userDetails.getUserId();

	try
	{
		EJBLocator obj = new EJBLocator();
		obj.setJndiName("SessionPayrollDetailsManagerBean");
		obj.setEnvironment();
		
		/* Creation of Home and Remote object*/
		SessionPayrollDetailsManagerHome objPayroll = (SessionPayrollDetailsManagerHome) PortableRemoteObject.narrow(obj.getHome(),SessionPayrollDetailsManagerHome.class); 
		SessionPayrollDetailsManager payrollDetailsObj = (SessionPayrollDetailsManager) PortableRemoteObject.narrow(objPayroll.create(),SessionPayrollDetailsManager.class);
		
		/* For Pay Roll Cycle List */
		HashMap payRollCycleList = payrollDetailsObj.getAllPyrlCycleStatForPayroll();
		pageContext.setAttribute("payRollCycleList",payRollCycleList);
		
		/* For Shift List */
		HashMap shiftDefnList = payrollDetailsObj.getShiftDefnNameList();
		pageContext.setAttribute("shiftDefnList",shiftDefnList);

		/* For Employee Types */
		HashMap empolyeeTypes = payrollDetailsObj.getAllEmployeeTypes();
		pageContext.setAttribute("empolyeeTypes",empolyeeTypes);
		
		/* For Employee Name list */
		HashMap empolyeeNames = new HashMap();
		if (!frm.getEmployeeType().equalsIgnoreCase("0"))
		{
			empolyeeNames = payrollDetailsObj.getAllEmployeesByType(Integer.parseInt(frm.getEmployeeType()));
			pageContext.setAttribute("empolyeeNames",empolyeeNames);
		}
		else
		{
			pageContext.setAttribute("empolyeeNames",empolyeeNames);
		}
		
		/* Filter Process Starts */
		Vector filter = new Vector();
		if(frm.getFormAction().equals(""))
		{  
			Filter fil[] = new Filter[0];
	 		session.setAttribute("objFilter",fil);
  		}
		else if(frm.getFormAction().equalsIgnoreCase("search"))
  		{

			if (!frm.getShift().equalsIgnoreCase("0"))
			{
				Filter temp = new Filter();
				temp.setFieldName("SHIFT_ID");
				if (BuildConfig.DMODE)
					System.out.println("frm.getShift(): "+frm.getShift());
				temp.setFieldValue(frm.getShift());
				filter.add(temp);
			}

			if (!frm.getEmployeeType().equalsIgnoreCase("0"))
			{
				Filter temp = new Filter();
				temp.setFieldName("EMP_TYP_ID");
				temp.setFieldValue(frm.getEmployeeType());
				filter.add(temp);
			}
			if (BuildConfig.DMODE)
				System.out.println("Emp Id: "+ frm.getEmployeeName());
			if (!frm.getEmployeeName().equalsIgnoreCase("0"))
			{
				Filter temp = new Filter();
				temp.setFieldName("EMP_ID");
				temp.setFieldValue(frm.getEmployeeName());
				filter.add(temp);
			}
			Filter objFilter[] = new Filter[filter.size()];
			filter.copyInto(objFilter);
			session.setAttribute("objFilter",objFilter);
		}

		if (!frm.getPayrollCycle().equalsIgnoreCase("0"))
		{
			Filter temp = new Filter();
			temp.setFieldName("PYRL_CYCLE_STAT_ID");
			temp.setFieldValue(frm.getPayrollCycle());
			filter.add(temp);
		}

  		Filter objFilter[] = (Filter[])session.getAttribute("objFilter");
  		for (int i = 0; i < objFilter.length; i++)
  		{
  			if (BuildConfig.DMODE)
  			{
  				System.out.println("1. "+objFilter[i].getFieldName());
  				System.out.println("2. "+objFilter[i].getFieldValue());
			}
  		}
		if (BuildConfig.DMODE)
			System.out.println("Cycle Id: "+frm.getPayrollCycle());
		if (!frm.getPayrollCycle().equalsIgnoreCase("0"))
		{
	 		HashMap payrollAdjustDet = payrollDetailsObj.getForPayRollAdjstmnt(objFilter,"PROD_CRNT_DATE",order);
		 	vec_payrollAdjustment = (Vector) payrollAdjustDet.get("PyrlDetails");
		 	if (BuildConfig.DMODE)
		 		System.out.println("Count: "+payrollAdjustDet.get("TotalRecordCount"));
		 	pageContext.setAttribute("vec_payrollAdjustment",vec_payrollAdjustment);
			if (BuildConfig.DMODE)
				System.out.println("vec_payrollAdjustment: "+ vec_payrollAdjustment.size());
	 	}
	 	else
	 	{
	 		pageContext.setAttribute("vec_payrollAdjustment",vec_payrollAdjustment); 
	 	}
	}catch (Exception e)
	{
		if (BuildConfig.DMODE)
		{
			System.out.println("Problem in Payroll Adjustment.jsp");
			e.printStackTrace();
		}
	}
%>

<html:html>
<head>
<title><bean:message key="prodacs.common.header"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script src='<bean:message key="context"/>/library/jscripts.js'></script>
<script>
var pageCount = 1; // set this value from JSP
function Sort(tabName,columnIndex,ascending)
{
	 if(pageCount==1)
	 {
	   sortByTable(tabName,columnIndex,ascending);
	 }
	 else
	 {
	   //set the current page number, number of records per page, sort column index and sort order as the request parameters
	   frmPayrollAdjustList.submit;
	 }
 }
 
	function loadEmpList()
	{
		var temp = document.forms[0];
		temp.formAction.value = "load";
		temp.submit();
	}
	
	function changePage()
	{
		temp = document.forms[0];
		temp.formAction.value="search";
		temp.page.value = parseInt(temp.page.value,10);
		temp.submit();
	}

	function filterSearch()
	{  
		temp = document.forms[0];
		if (temp.payrollCycle.value == "0")
		{
			alert ("Sorry! U must Select a PayRoll Cycle to Proceed!");
			return false;
		}
		temp.formAction.value="search";
		temp.submit();
	}
	
	function loadPayrolls()
	{
		var temp = document.forms[0];
		temp.formAction.value = "load";
		temp.submit();
	}

	/* Numbers and Period are Allowed */
	function isNumber(val)
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

	function addRow()
	{
		var temp = document.forms[0];
		var obj1 = document.getElementById("tblPayrollAdjust"); /* Master Table */
		var obj = document.getElementById("tblPayrollModDetails"); /* Buffer */ 

		for (var i= 1; i < obj1.children(0).children.length; i++)
		{
			if (obj1.children(0).children(i).children(0).children(0).checked)
			{
				/* The row is Shifted already or not */
				for (var j = 1; j < obj.children(0).children.length; j++)
				{
					if (obj1.children(0).children(i).children(0).children(0).value == obj.children(0).children(j).children(0).children(0).value)
					{
						alert("This Payroll is already Shifted! If u want to override, \n\n   Delete the checked Payroll and Try Again!");
						obj.children(0).children(j).children(0).children(0).checked = true;
						return false;
					}
				}

				/* The values can't be Empty */
				if ((obj1.children(0).children(i).children(0).children(0).value == "") ||
					(obj1.children(0).children(i).children(1).children(0).value == "") ||
					(obj1.children(0).children(i).children(2).children(0).value == "") ||
					(obj1.children(0).children(i).children(3).children(0).value == "") ||
					(obj1.children(0).children(i).children(4).children(0).value == "") ||
					(obj1.children(0).children(i).children(5).children(0).value == "") ||
					(obj1.children(0).children(i).children(6).children(0).value == "") ||
					(obj1.children(0).children(i).children(7).children(0).value == "") ||
					(obj1.children(0).children(i).children(8).children(0).value == ""))
				{
					alert ("The Values are Empty!");
					return false;
				}

				/* Transfering the row values to buffer */
				if ((obj.children(0).children.length == "2") &&
					(obj.children(0).children(1).children(0).children(0).value == ""))
				{
					obj.children(0).children(1).children(0).children(0).value = obj1.children(0).children(i).children(0).children(0).value; // Payroll Id
					obj.children(0).children(1).children(1).children(0).value = obj1.children(0).children(i).children(1).children(0).value; // Date
					obj.children(0).children(1).children(2).children(0).value = obj1.children(0).children(i).children(2).children(0).value; // Shift Name
					obj.children(0).children(1).children(3).children(0).value = obj1.children(0).children(i).children(3).children(0).value; // Emp Name
					obj.children(0).children(1).children(4).children(0).value = obj1.children(0).children(i).children(4).children(0).value; // Duty Hrs
					obj.children(0).children(1).children(5).children(0).value = obj1.children(0).children(i).children(5).children(0).value; // OT Hrs
					obj.children(0).children(1).children(6).children(0).value = obj1.children(0).children(i).children(6).children(0).value; // Regular Sal. Hrs
					obj.children(0).children(1).children(7).children(0).value = obj1.children(0).children(i).children(7).children(0).value; // OT Sal Hrs
					obj.children(0).children(1).children(8).children(0).value = obj1.children(0).children(i).children(8).children(0).value; // Inc. Sal. Hrs
					obj.children(0).children(1).children(9).children(0).value = obj1.children(0).children(i).children(9).children(0).value; // No of Times Adjusted
				}
				else
				{
					var newNode = obj.children(0).children(1).cloneNode(true);
					obj.children(0).appendChild(newNode);
					var len = obj.children(0).children.length;

					obj.children(0).children(len-1).children(0).children(0).value = ""; // Payroll Id
					obj.children(0).children(len-1).children(1).children(0).value = ""; // Date
					obj.children(0).children(len-1).children(2).children(0).value = ""; // Shift Name
					obj.children(0).children(len-1).children(3).children(0).value = ""; // Emp Name
					obj.children(0).children(len-1).children(4).children(0).value = ""; // Duty Hrs
					obj.children(0).children(len-1).children(5).children(0).value = ""; // OT Hrs
					obj.children(0).children(len-1).children(6).children(0).value = ""; // Regular Sal. Hrs
					obj.children(0).children(len-1).children(7).children(0).value = ""; // OT Sal Hrs
					obj.children(0).children(len-1).children(8).children(0).value = ""; // Inc. Sal. Hrs
					obj.children(0).children(len-1).children(9).children(0).value = ""; // No of Times Adjusted

					obj.children(0).children(len-1).children(0).children(0).value = obj1.children(0).children(i).children(0).children(0).value; // Payroll Id
					obj.children(0).children(len-1).children(1).children(0).value = obj1.children(0).children(i).children(1).children(0).value; // Date
					obj.children(0).children(len-1).children(2).children(0).value = obj1.children(0).children(i).children(2).children(0).value; // Shift Name
					obj.children(0).children(len-1).children(3).children(0).value = obj1.children(0).children(i).children(3).children(0).value; // Emp Name
					obj.children(0).children(len-1).children(4).children(0).value = obj1.children(0).children(i).children(4).children(0).value; // Duty Hrs
					obj.children(0).children(len-1).children(5).children(0).value = obj1.children(0).children(i).children(5).children(0).value; // OT Hrs
					obj.children(0).children(len-1).children(6).children(0).value = obj1.children(0).children(i).children(6).children(0).value; // Regular Sal. Hrs
					obj.children(0).children(len-1).children(7).children(0).value = obj1.children(0).children(i).children(7).children(0).value; // OT Sal Hrs
					obj.children(0).children(len-1).children(8).children(0).value = obj1.children(0).children(i).children(8).children(0).value; // Inc. Sal. Hrs
					obj.children(0).children(len-1).children(9).children(0).value = obj1.children(0).children(i).children(9).children(0).value; // No of Times Adjusted
				}
			}
		}
	}

	function delRow()
	{
		var temp = document.forms[0];
		var obj = document.getElementById("tblPayrollModDetails"); /* Buffer */ 

		for (var i= 1; i < obj.children(0).children.length; i++)
		{
			if (obj.children(0).children.length == "2")
			{
				if (obj.children(0).children(i).children(0).children(0).checked)
				{
					obj.children(0).children(i).children(0).children(0).value = ""; // Payroll Id
					obj.children(0).children(i).children(1).children(0).value = ""; // Date
					obj.children(0).children(i).children(2).children(0).value = ""; // Shift Name
					obj.children(0).children(i).children(3).children(0).value = ""; // Emp Name
					obj.children(0).children(i).children(4).children(0).value = ""; // Duty Hrs
					obj.children(0).children(i).children(5).children(0).value = ""; // OT Hrs
					obj.children(0).children(i).children(6).children(0).value = ""; // Regular Sal. Hrs
					obj.children(0).children(i).children(7).children(0).value = ""; // OT Sal Hrs
					obj.children(0).children(i).children(8).children(0).value = ""; // Inc. Sal. Hrs
					obj.children(0).children(i).children(9).children(0).value = ""; // No of Times Adjusted
					
					obj.children(0).children(i).children(0).children(0).checked	= false; 
				}
			}
			else if (obj.children(0).children(i).children(0).children(0).checked)
			{
				obj.children(0).removeChild(obj.children(0).children(i));
				i = 1;
			}
			
			if (obj.children(0).children.length == "2")
			{
				if (obj.children(0).children(i).children(0).children(0).checked)
				{
					obj.children(0).children(i).children(0).children(0).value = ""; // Payroll Id
					obj.children(0).children(i).children(1).children(0).value = ""; // Date
					obj.children(0).children(i).children(2).children(0).value = ""; // Shift Name
					obj.children(0).children(i).children(3).children(0).value = ""; // Emp Name
					obj.children(0).children(i).children(4).children(0).value = ""; // Duty Hrs
					obj.children(0).children(i).children(5).children(0).value = ""; // OT Hrs
					obj.children(0).children(i).children(6).children(0).value = ""; // Regular Sal. Hrs
					obj.children(0).children(i).children(7).children(0).value = ""; // OT Sal Hrs
					obj.children(0).children(i).children(8).children(0).value = ""; // Inc. Sal. Hrs
					obj.children(0).children(i).children(9).children(0).value = ""; // No of Times Adjusted
					
					obj.children(0).children(i).children(0).children(0).checked	= false; 
				}
			}
		}
	}

	function chkAllAdjust()
	{
		var temp = document.forms[0];
		var obj = document.getElementById("tblPayrollModDetails");
		if (obj.children(0).children.length > 2)
		{
			if (temp.chkAllPayAdjust.checked)		
			{
				for (var i = 0; i < temp.chkPayAdjust.length; i++)
				{
					temp.chkPayAdjust[i].checked = true;
				}
			}
			else
			{
				for (var i = 0; i < temp.chkPayAdjust.length; i++)
				{
					temp.chkPayAdjust[i].checked = false;
				}
			}
		}
		else
		{
			if (temp.chkAllPayAdjust.checked)
			{
				temp.chkPayAdjust.checked = true;
			}
			else
			{
				temp.chkPayAdjust.checked = false;
			}
		}
	}

	function loadTables()
	{
		var temp = document.forms[0];
		temp.hidUserId.value = '<%= userName %>';		
		if (temp.payrollCycle.value != "0")
		{
			pytlMstrBfer.style.display = 'block';
		}
		else
		{
			pytlMstrBfer.style.display = 'none';
		}
	}

	function adPyrlDet()
	{
		var obj = document.getElementById("tblPayrollModDetails"); /* Buffer */ 
		var pyrlDet = "";
		if (obj.children(0).children(1).children(0).children(0).value == "")
		{
			alert ("Sorry! U must Adjust minimum one Payroll to Proceed!");
			return false;
		}
		for (var i = 1; i < obj.children(0).children.length; i++)
		{
			if (i != 1)
			{
				pyrlDet = pyrlDet + "^";
			}
			pyrlDet = pyrlDet + "" + obj.children(0).children(i).children(0).children(0).value;
			pyrlDet = pyrlDet + "-" + obj.children(0).children(i).children(1).children(0).value;
			pyrlDet = pyrlDet + "-" + obj.children(0).children(i).children(2).children(0).value;
			pyrlDet = pyrlDet + "-" + obj.children(0).children(i).children(3).children(0).value;
			pyrlDet = pyrlDet + "-" + obj.children(0).children(i).children(4).children(0).value;
			pyrlDet = pyrlDet + "-" + obj.children(0).children(i).children(5).children(0).value;
			pyrlDet = pyrlDet + "-" + obj.children(0).children(i).children(6).children(0).value;
			pyrlDet = pyrlDet + "-" + obj.children(0).children(i).children(7).children(0).value;
			pyrlDet = pyrlDet + "-" + obj.children(0).children(i).children(8).children(0).value;
			pyrlDet = pyrlDet + "-" + obj.children(0).children(i).children(9).children(0).value;
		}
		//alert (pyrlDet);
		document.forms[0].payrollDet.value = pyrlDet;
		document.forms[0].formAction.value = "adjust";
		document.forms[0].submit();
	}
</script>
</head>

<body onload="loadTables();">
<html:form action="frmPayrollAdjustment" focus="payrollCycle">
<html:hidden property="formAction"/>
<html:hidden property="payrollDet"/>
<html:hidden property="hidUserId"/> <!-- Holding the User Name -->
	<table width="100%" cellspacing="0" cellpadding="10">
	<tr>
	<td>
		<table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
		<tr>
			<td><bean:message key="prodacs.payroll.payrolladjustment"/></td>
		</tr>
		</table>
		<br>
		<font class="message">
		<html:messages id="messageid" message="true">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="messageid" />
		</html:messages>
		</font>
		<table>
			<tr> 
				<td colspan='2'> <font size="1px" face="Verdana"><html:errors/></font></td>
			</tr>
		</table>
		
		<table width="100%" cellspacing="0" cellpadding="0">
		<tr> 
			<td width="20" id="FilterTip">&nbsp;</td>
			<td class="FilterTitle"><a href="javascript:showFilter(document.getElementById('Filter'), document.getElementById('FilterImg'))"><img src='<bean:message key="context"/>/images/hide.gif' width="9" height="5" border="0" align="absmiddle" id="FilterImg"> <bean:message key="prodacs.common.filter"/></a></td>
			<td width="20" height="20" id="FilterEnd">&nbsp;</td>
		</tr>
		</table>
		
		<table width="100%" cellspacing="0" cellpadding="0" id="Filter">
		<tr> 
			<td width="120" class="FieldTitle"><bean:message key="prodacs.production.employeetype"/></td>
			<td width="1" class="FieldTitle">:</td>
			<td width="120" class="FieldTitle"><html:select property="employeeType" styleClass="Combo" onchange="loadEmpList();">
			<html:option value="0">-- Emp Type --</html:option>
			<html:options collection="empolyeeTypes" property="key" labelProperty="value"/>
			</html:select></td>
			<td width="120" class="FieldTitle"><bean:message key="prodacs.employee.employeename"/></td>
			<td width="1" class="FieldTitle">:</td>
			<td width="120" class="FieldTitle"><html:select property="employeeName" styleClass="Combo">
			<html:option value="0">-- Emp Name --</html:option>
			<html:options collection="empolyeeNames" property="key" labelProperty="value"/>
			</html:select></td>
			<td width="90" class="FieldTitle"><bean:message key="prodacs.workcalendar.shiftdefinition.shiftname"/></td>
			<td width="1" class="FieldTitle">:</td>
			<td width="120" class="FieldTitle"><html:select property="shift" styleClass="Combo">
			<html:option value="0">-- Choose Shift --</html:option>
			<html:options collection="shiftDefnList" property="key" labelProperty="value"/>
			</html:select></td>
			<td class="FieldTitle"><html:button styleClass="Button" property="search" onclick="filterSearch()" value="Go"/></td>
		</tr>
		</table>
		<table width="100%" cellspacing="0" cellpadding="0" id="Filter">
		<tr> 
			<td width="108" class="FieldTitle"><bean:message key="prodacs.payroll.payrollcycle"/><span class="mandatory">*</span></td>
			<td width="1" class="FieldTitle">:</td>
			<td class="FieldTitle"><html:select property="payrollCycle" styleClass="Combo" onchange="loadPayrolls();">
			<html:option value="0">-- Payroll Cycle --</html:option>
			<html:options collection="payRollCycleList" property="key" labelProperty="value"/>
			</html:select></td>
		</tr>
		</table>
		<br>
	<table width="100%" cellspacing="0" cellpadding="0" id="pytlMstrBfer" style="display:none">
	<tr>
	<td>
		<table width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td class="TopLnk"><a href="#" onClick="addRow()"><bean:message key="prodacs.common.add"/></a></td>
		</tr>
		</table>
		<table width="100%" cellpadding="0" cellspacing="0" id="tblPayrollAdjust">
		<tr> 
			<td width="25" class="Header">&nbsp;</td>
			<td width="65" class="Header"><bean:message key="prodacs.job.date"/></td>
			<td width="65" class="Header"><bean:message key="prodacs.production.shift"/></td>
			<td class="Header"><bean:message key="prodacs.production.employeename"/></td>
			<td width="60" class="Header"><bean:message key="prodacs.production.dutyhrs"/></td>
			<td class="Header"><bean:message key="prodacs.production.othrs"/></td>
			<td width="75" class="Header"><bean:message key="prodacs.payroll.regularsalaryhrs"/></td>
			<td width="75" class="Header"><bean:message key="prodacs.payroll.otsalaryhrs"/></td>
			<td width="75" class="Header"><bean:message key="prodacs.payroll.incentivesalaryhours"/></td>
			<td width="85" class="Header"><bean:message key="prodacs.payroll.nooftimesadjusted"/></td>
		</tr>
		<logic:iterate id="bt1" name="vec_payrollAdjustment" indexId="count">
	<%
		if ((count.intValue()%2) == 0)
		{
	%>
		<tr> 
			<td class="TableItems"><input type="radio" name="rdPayAdjust" value='<bean:write name="bt1" property="prePyrlId"/>' onclick="modfyRow('tblPayrollAdjust', document.PayrollAdjustment)"></td>
			<bean:define id="prodDate1" name="bt1" property="prodDate"/>
			<td class="TableItems"><input type="text" name="proDate" class="TextBoxFull" readonly value='<%= prodDate1.toString().substring(0,10) %>'></td>
			<td class="TableItems"><input type="text" name="sftName" class="TextBoxFull" readonly value='<bean:write name="bt1" property="shiftName"/>'></td>
			<td class="TableItems"><input type="text" name="empName" class="TextBoxFull" readonly value='<bean:write name="bt1" property="empName"/>'></td>
			<td class="TableItems"><input type="text" name="dtyHrs" class="TextBoxFull" readonly value='<bean:write name="bt1" property="dtyHrs"/>'></td>
			<td class="TableItems"><input type="text" name="otHrs" class="TextBoxFull" readonly value='<bean:write name="bt1" property="otHrs"/>'></td>
			<td class="TableItems"><input type="text" name="regularSalaryHrs" class="TextBoxFull" readonly value='<bean:write name="bt1" property="dtySlryHrs"/>' maxlength="5" onkeypress="return isNumber(this);" onkeyup="return isNumber(this);"/></td>
			<td class="TableItems"><input type="text" name="otSalaryHrs" class="TextBoxFull" readonly value='<bean:write name="bt1" property="otSlryHrs"/>' maxlength="5" onkeypress="return isNumber(this);" onkeyup="return isNumber(this);"/></td>
			<td class="TableItems"><input type="text" name="incSalHrs" class="TextBoxFull" readonly value='<bean:write name="bt1" property="incntvSlryHrs"/>' maxlength="5" onkeypress="return isNumber(this);" onkeyup="return isNumber(this);"/></td>
			<td class="TableItems"><input type="text" name="noTimesAdjust" class="TextBoxFull" readonly value='<bean:write name="bt1" property="noOfTimesAdjstd"/>'></td>
		</tr>
	<%
		}
		else
		{
	%>
		<tr> 
			<td class="TableItems2"><input type="radio" name="rdPayAdjust" value='<bean:write name="bt1" property="prePyrlId"/>' onclick="modfyRow('tblPayrollAdjust', document.PayrollAdjustment)"></td>
			<bean:define id="prodDate2" name="bt1" property="prodDate"/>
			<td class="TableItems2"><input type="text" name="proDate" class="TextBoxFull" readonly value='<%= prodDate2.toString().substring(0,10) %>'></td>
			<td class="TableItems2"><input type="text" name="sftName" class="TextBoxFull" readonly value='<bean:write name="bt1" property="shiftName"/>'></td>
			<td class="TableItems2"><input type="text" name="empName" class="TextBoxFull" readonly value='<bean:write name="bt1" property="empName"/>'></td>
			<td class="TableItems2"><input type="text" name="dtyHrs" class="TextBoxFull" readonly value='<bean:write name="bt1" property="dtyHrs"/>'></td>
			<td class="TableItems2"><input type="text" name="otHrs" class="TextBoxFull" readonly value='<bean:write name="bt1" property="otHrs"/>'></td>
			<td class="TableItems2"><input type="text" name="regularSalaryHrs" class="TextBoxFull" readonly value='<bean:write name="bt1" property="dtySlryHrs"/>' maxlength="5" onkeypress="return isNumber(this);" onkeyup="return isNumber(this);"/></td>
			<td class="TableItems2"><input type="text" name="otSalaryHrs" class="TextBoxFull" readonly value='<bean:write name="bt1" property="otSlryHrs"/>' maxlength="5" onkeypress="return isNumber(this);" onkeyup="return isNumber(this);"/></td>
			<td class="TableItems2"><input type="text" name="incSalHrs" class="TextBoxFull" readonly value='<bean:write name="bt1" property="incntvSlryHrs"/>' maxlength="5" onkeypress="return isNumber(this);" onkeyup="return isNumber(this);"/></td>
			<td class="TableItems2"><input type="text" name="noTimesAdjust" class="TextBoxFull" readonly value='<bean:write name="bt1" property="noOfTimesAdjstd"/>'></td>
		</tr>
	<%
		}
	%>
		</logic:iterate>
		</table>
		<br>
		<table width="100%" cellspacing="0" cellpadding="5">
		<tr>
			<td class="TopLnk"><a href="#" onClick="delRow()"><bean:message key="prodacs.common.delete"/></a></td>
		</tr>
		</table>
		<table width="100%" cellpadding="0" cellspacing="0" id="tblPayrollModDetails">
		<tr> 
			<td width="25" class="Header"><input type="checkbox" name="chkAllPayAdjust" onclick="chkAllAdjust();"></td>
			<td width="75" class="Header"><bean:message key="prodacs.job.date"/></td>
			<td width="65" class="Header"><bean:message key="prodacs.production.shift"/></td>
			<td class="Header"><bean:message key="prodacs.production.employeename"/></td>
			<td width="60" class="Header"><bean:message key="prodacs.production.dutyhrs"/></td>
			<td class="Header"><bean:message key="prodacs.production.othrs"/></td>
			<td width="75" class="Header"><bean:message key="prodacs.payroll.regularsalaryhrs"/></td>
			<td width="75" class="Header"><bean:message key="prodacs.payroll.otsalaryhrs"/></td>
			<td width="75" class="Header"><bean:message key="prodacs.payroll.incentivesalaryhours"/></td>
			<td width="85" class="Header"><bean:message key="prodacs.payroll.nooftimesadjusted"/></td>
		</tr>
		<tr> 
			<td class="TableItems"><input type="checkbox" name="chkPayAdjust" value=""></td>
			<td class="TableItems"><input type="text" name="proDate" class="TextBoxFull" readonly ></td>
			<td class="TableItems"><input type="text" name="sftName" class="TextBoxFull" readonly ></td>
			<td class="TableItems"><input type="text" name="empName" class="TextBoxFull" readonly ></td>
			<td class="TableItems"><input type="text" name="dtyHrs" class="TextBoxFull" readonly ></td>
			<td class="TableItems"><input type="text" name="otHrs" class="TextBoxFull" readonly ></td>
			<td class="TableItems"><input type="text" name="regularSalaryHrs" class="TextBoxFull" readonly ></td>
			<td class="TableItems"><input type="text" name="otSalaryHrs" class="TextBoxFull" readonly ></td>
			<td class="TableItems"><input type="text" name="incSalHrs" class="TextBoxFull" readonly ></td>
			<td class="TableItems"><input type="text" name="noTimesAdjust" class="TextBoxFull" readonly ></td>
		</tr>
		</table>
		<br>
		<table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
		<tr>
			<td><html:button property="Button" styleClass="Button" value="Adjust Payroll" onclick="adPyrlDet()"/></td>
		</tr>
		</table>
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