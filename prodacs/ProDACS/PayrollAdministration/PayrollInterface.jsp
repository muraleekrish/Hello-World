<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<%@ page import="com.savantit.prodacs.businessimplementation.securityadmin.UserAuthDetails"%>
<jsp:useBean id="frm" class="com.savantit.prodacs.presentation.tableadmin.payroll.PayrollInterfaceForm" />
<jsp:setProperty name="frm" property="*" /> 
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<%@ page import="com.savantit.prodacs.facade.SessionPayrollDetailsManager"%>
<%@ page import="com.savantit.prodacs.facade.SessionPayrollDetailsManagerHome"%>
<useradmin:userrights resource="23"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("PayRoll Interface.");
	Vector vec_payrollDet = new Vector();
	String vec_Len = "";
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
		
		HashMap payRollCycleList = payrollDetailsObj.getAllPyrlCycleStatForInterface();
		if (BuildConfig.DMODE)
			System.out.println("PayRollCycle List: "+payRollCycleList.size());
		pageContext.setAttribute("payRollCycleList",payRollCycleList);

		/* Selecting by Day/Shift or Employee*/
		if (BuildConfig.DMODE)
		{
			System.out.println("PayRollCycleId: "+frm.getCmbPayrollCycle());
			System.out.println("SortBy: "+frm.getSortby());
			System.out.println("Action: "+ frm.getFormAction());
		}
		//Vector empDet = new Vector();
		if ((!frm.getCmbPayrollCycle().equalsIgnoreCase("0")) && (!frm.getSortby().equalsIgnoreCase("0")) && (!frm.getFormAction().equalsIgnoreCase("create")))
		{
			vec_payrollDet = payrollDetailsObj.getEmployeeDetails(Integer.parseInt(frm.getCmbPayrollCycle()), Integer.parseInt(frm.getSortby()));
			if (BuildConfig.DMODE)
				System.out.println("Vec Len: "+ vec_payrollDet.size());
			vec_Len = vec_payrollDet.size()+"";
			pageContext.setAttribute("vec_payrollDet",vec_payrollDet);
		}
		else
		{
			pageContext.setAttribute("vec_payrollDet",vec_payrollDet);
		}
	}catch (Exception e)
	{
		if (BuildConfig.DMODE)
		{
			System.out.println("Problem in Payroll Interface.jsp");
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
	function sortBy(val)
	{
		var temp = document.forms[0];

		/* Must select Payroll cycle */
		if (temp.cmbPayrollCycle.value == "0")
		{
			alert ("U must Select a Payroll Cycle!");
			return false;
		}
		
		/* Must select Sort By Employee / Date&Shift */
		if (temp.sortby.value == "0")
		{
			return false;
		}
		temp.formAction.value = "load";
		temp.submit();
	}

	function loadDefault()
	{
		var temp = document.forms[0];
		var sortOrder = '<%= frm.getSortby()%>';
		temp.hidUserId.value = '<%= userName %>';
		temp.vector_Len.value = '<%= vec_Len %>';
		if(parseInt(temp.vector_Len.value) != 0)
		{
			temp.Button2.disabled = false;
		}
		else
		{
			temp.Button2.disabled = true;
		}

		if (sortOrder == "1")
		{
			tblSortEmployee.style.display = "block";
			tblSortShiftDay.style.display = "none";
		}
		else if (sortOrder == "2")
		{
			tblSortEmployee.style.display = "none";
			tblSortShiftDay.style.display = "block";
		}
	}

	function addPayrollInterface()
	{
		var temp = document.forms[0];
		if (temp.cmbPayrollCycle.value == "0")
		{
			alert ("U must Select a Payroll Cycle!")
			return false;
		}
		if (temp.sortby.value == "0")
		{
			alert ("U must Select a Order of Payroll Interface!");
			return false;
		}
		temp.formAction.value = "create";
		temp.submit();
	}
</script>
</head>

<body onload="loadDefault();">
<html:form action="frmPayrollInterfaceList" focus="cmbPayrollCycle">
<html:hidden property="formAction"/>
<html:hidden property="hidUserId"/> <!-- Holding the User Name -->
<input type="hidden" name="vector_Len"/>
	<table width="100%" cellspacing="0" cellpadding="10">
	<tr>
	<td>
		<table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
		<tr>
			<td><bean:message key="prodacs.payroll.payrollinterface"/></td>
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
			<td colspan='2'> <font size="1px" face="Verdana">
				<html:errors/></font>
			</td>
		</tr> 
		</table> 
		<br>
		<table width="100%" cellspacing="0" cellpadding="0">
		<tr> 
			<td width="100" class="FieldTitle"><bean:message key="prodacs.payroll.payrollcycle"/></td>
			<td width="1" class="FieldTitle">:</td>
			<td width="175" class="FieldTitle"><html:select property="cmbPayrollCycle" styleClass="Combo">
			<html:option value="0">-- Choose Payroll Cycle --</html:option>
			<html:options collection="payRollCycleList" property="key" labelProperty="value"/>
			</html:select></td>
			<td width="55" class="FieldTitle"><bean:message key="prodacs.payroll.sortby"/></td>
			<td width="1" class="FieldTitle">:</td>
			<td class="FieldTitle"><html:select property="sortby" styleClass="Combo" onchange="sortBy(this.value);">
			<html:option value="0">-- Sort By --</html:option>
			<html:option value="1">By Employee</html:option>
			<html:option value="2">By Date / Shift</html:option>
			</html:select></td>
		</tr>
		</table>
		<br>
		<!-- By Date&Shift -->
		<table width="100%" cellpadding="0" cellspacing="0" id="tblSortShiftDay" style="display:none">
	<%
		if (frm.getSortby().equalsIgnoreCase("2"))
		{
	%>
		<logic:iterate id="bt3" name="vec_payrollDet">
		<tr>
		<td class="FieldTitle">
		<fieldset id="FieldSet"><legend>&nbsp;<bean:define id="prodDate" name="bt3" property="prodDate"/><%= prodDate.toString().substring(0,10) %>&nbsp;/&nbsp;<bean:write name="bt3" property="shiftName"/>&nbsp;</legend><br>
			<table width="100%" cellspacing="0" cellpadding="3">
			<!--tr> 
				<td>&nbsp;</td>
				<td colspan="2" align="center" class="PageSubTitle" style="border-right:solid 1px #FFFFFF"><bean:message key="prodacs.payroll.duty"/></td>
				<td colspan="2" align="center" class="PageSubTitle" style="border-right:solid 1px #FFFFFF"><bean:message key="prodacs.payroll.overtime"/></td>
				<td>&nbsp;</td>
			</tr-->
			<tr> 
				<td class="Header"><bean:message key="prodacs.employee.employeename"/></td>
				<td width="75" class="Header"><bean:message key="prodacs.production.dutyhrs"/></td>
				<!--td width="75" class="Header"><bean:message key="prodacs.payroll.salaryhrs"/></td-->
				<td width="100" class="Header"><bean:message key="prodacs.production.overtimehrs"/></td>
				<!--td width="75" class="Header"><bean:message key="prodacs.payroll.salaryhrs"/></td-->
				<!--td width="100" class="Header"><bean:message key="prodacs.payroll.incentivesalaryhours"/></td-->
				<td width="150" class="Header"><bean:message key="prodacs.payroll.source"/></td>
			</tr>
			<logic:iterate id="bt5" name="bt3" property="employeeEmpHrsDetails" indexId="count">
		<%
			if(count.intValue()%2 == 0) 
			{
		%>
				<tr>
					<td class="TableItems"><bean:write name="bt5" property="empName"/>&nbsp;</td>
					<td class="TableItems"><bean:write name="bt5" property="dtyHrs"/>&nbsp;</td>
					<!--td class="TableItems"><bean:write name="bt5" property="dtySlryHrs"/>&nbsp;</td-->
					<td class="TableItems"><bean:write name="bt5" property="otHrs"/>&nbsp;</td>
					<!--td class="TableItems"><bean:write name="bt5" property="otSlryHrs"/>&nbsp;</td-->
					<!--td class="TableItems"><bean:write name="bt5" property="incntvSlryHrs"/>&nbsp;</td-->
					<td class="TableItems"><bean:write name="bt5" property="dataSource"/>&nbsp;</td>
				</tr>
		<%
			}
			else
			{
		%>
				<tr>
					<td class="TableItems2"><bean:write name="bt5" property="empName"/>&nbsp;</td>
					<td class="TableItems2"><bean:write name="bt5" property="dtyHrs"/>&nbsp;</td>
					<!--td class="TableItems2"><bean:write name="bt5" property="dtySlryHrs"/>&nbsp;</td-->
					<td class="TableItems2"><bean:write name="bt5" property="otHrs"/>&nbsp;</td>
					<!--td class="TableItems2"><bean:write name="bt5" property="otSlryHrs"/>&nbsp;</td-->
					<!--td class="TableItems2"><bean:write name="bt5" property="incntvSlryHrs"/>&nbsp;</td-->
					<td class="TableItems2"><bean:write name="bt5" property="dataSource"/>&nbsp;</td>
				</tr>
		<%
			}
		%>
			</logic:iterate>
			</table>
			<table width="100%" cellspacing="0" cellpadding="0">
			<tr> 
				<td width="150" class="FieldTitle"><bean:message key="prodacs.payroll.totalshiftdutyhours"/></td>
				<td width="1" class="FieldTitle">:</td>
				<td width="50" class="ViewData"><bean:write name="bt3" property="totShiftDtyHrs"/>&nbsp;</td>

				<td width="150" class="FieldTitle"><bean:message key="prodacs.payroll.totalshiftothours"/></td>
				<td width="1" class="FieldTitle">:</td>
				<td width="50" class="ViewData"><bean:write name="bt3" property="totShiftOtHrs"/>&nbsp;</td>

				<!--td width="190" class="FieldTitle"><bean:message key="prodacs.payroll.totalshiftincentivesalaryhours"/></td>
				<td width="1" class="FieldTitle">:</td>
				<td width="50" class="ViewData"><bean:write name="bt3" property="totShiftIncntvSlryHrs"/>&nbsp;</td>
				<td class="ViewData">&nbsp;</td-->
			</tr>
			</table>
			</fieldset>
		</td>
		</tr>
		</logic:iterate>
		<%
			}
		%>
	</table>
						
		<!-- Sort by Employee -->
		<table width="100%" cellpadding="0" cellspacing="0" id="tblSortEmployee" style="display:none">
		<%
			if (frm.getSortby().equalsIgnoreCase("1"))
			{
		%>								
		<logic:iterate id="bt1" name="vec_payrollDet">

				<tr> 
					<td class="FieldTitle"><fieldset id="FieldSet">
					<legend>&nbsp;<bean:write name="bt1" property="empName"/>&nbsp;</legend>
					<br>

					<table width="100%" cellspacing="0" cellpadding="3">
					<!--tr> 
						<td colspan="2">&nbsp;</td>
						<td colspan="2" align="center" class="PageSubTitle" style="border-right:solid 1px #FFFFFF"><bean:message key="prodacs.payroll.duty"/></td>
						<td colspan="2" align="center" class="PageSubTitle" style="border-right:solid 1px #FFFFFF"><bean:message key="prodacs.payroll.overtime"/></td>
						<td>&nbsp;</td>
					</tr-->
					<tr> 
						<td class="Header"><bean:message key="prodacs.job.date"/></td>
						<td width="100" class="Header"><bean:message key="prodacs.production.shift"/></td>
						<td width="75" class="Header"><bean:message key="prodacs.production.dutyhrs"/></td>
						<!--td width="75" class="Header"><bean:message key="prodacs.payroll.salaryhrs"/></td-->
						<td width="100" class="Header"><bean:message key="prodacs.production.overtimehrs"/></td>
						<!--td width="75" class="Header"><bean:message key="prodacs.payroll.salaryhrs"/></td-->
						<!--td width="100" class="Header"><bean:message key="prodacs.payroll.incentivesalaryhours"/></td-->
						<td width="150" class="Header"><bean:message key="prodacs.payroll.source"/></td>
					</tr>
			<logic:iterate id="bt2" name="bt1" property="dateShiftEmpHrsDetails" indexId="count">
			<%
				if(count.intValue()%2 == 0) 
   				{
			%>
					<tr>
						<td class="TableItems"><logic:notEmpty name="bt2" property="prodDate">
												<bean:define id="date" name="bt2" property="prodDate"/>
												<%= date.toString().substring(0,10) %></logic:notEmpty>&nbsp;</td>
						<td class="TableItems"><bean:write name="bt2" property="shiftName"/>&nbsp;</td>
						<td class="TableItems"><bean:write name="bt2" property="dtyHrs"/>&nbsp;</td>
						<!--td class="TableItems"><bean:write name="bt2" property="dtySlryHrs"/>&nbsp;</td-->
						<td class="TableItems"><bean:write name="bt2" property="otHrs"/>&nbsp;</td>
						<!--td class="TableItems"><bean:write name="bt2" property="otSlryHrs"/>&nbsp;</td-->
						<!--td class="TableItems"><bean:write name="bt2" property="incntvSlryHrs"/>&nbsp;</td-->
						<td class="TableItems"><bean:write name="bt2" property="dataSource"/>&nbsp;</td>
					</tr>
			<%
				}
				else
				{
			%>
					<tr>
						<td class="TableItems2"><logic:notEmpty name="bt2" property="prodDate">
												<bean:define id="date" name="bt2" property="prodDate"/>
												<%= date.toString().substring(0,10) %></logic:notEmpty>&nbsp;</td>
						<td class="TableItems2"><bean:write name="bt2" property="shiftName"/>&nbsp;</td>
						<td class="TableItems2"><bean:write name="bt2" property="dtyHrs"/>&nbsp;</td>
						<!--td class="TableItems2"><bean:write name="bt2" property="dtySlryHrs"/>&nbsp;</td-->
						<td class="TableItems2"><bean:write name="bt2" property="otHrs"/>&nbsp;</td>
						<!--td class="TableItems2"><bean:write name="bt2" property="otSlryHrs"/>&nbsp;</td-->
						<!--td class="TableItems2"><bean:write name="bt2" property="incntvSlryHrs"/>&nbsp;</td-->
						<td class="TableItems2"><bean:write name="bt2" property="dataSource"/>&nbsp;</td>
					</tr>
			<%
				}
			%>
			</logic:iterate>
					</table>
					<table width="100%" cellspacing="0" cellpadding="0">
					<tr> 
						<td width="130" class="FieldTitle"><bean:message key="prodacs.payroll.totaldutyhours"/></td>
						<td width="1" class="FieldTitle">:</td>
						<td width="100" class="ViewData"><bean:write name="bt1" property="totDtyHrs"/>&nbsp;</td>

						<td width="120" class="FieldTitle"><bean:message key="prodacs.payroll.totalothours"/></td>
						<td width="1" class="FieldTitle">:</td>
						<td width="100" class="ViewData"><bean:write name="bt1" property="totOtHrs"/>&nbsp;</td>

						<!--td width="190" class="FieldTitle"><bean:message key="prodacs.payroll.totalincentivesalaryhours"/></td>
						<td width="1" class="FieldTitle">:</td>
						<td width="100" class="ViewData"><bean:write name="bt1" property="totIncntvSlryHrs"/>&nbsp;</td>
						<td class="ViewData">&nbsp;</td-->
					</tr>
					</table>
					<br>
				</fieldset>
				</td>
				</tr>

		</logic:iterate>
		<%
			}
		%>
		</table>
	</td>
	</tr>
	<br>
	<table width="100%" cellpadding="0" cellspacing="5" id="createPyrl">
	<tr>
		<td>
			<table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
			<tr> 
				<td><html:button property="Button2" styleClass="Button" value="Create Payroll Interface" onclick = "addPayrollInterface();"/></td>
			</tr>
			</table>
		</td>
	</tr>
	</table>
	</table>
</html:form>
</body>
</html:html>
