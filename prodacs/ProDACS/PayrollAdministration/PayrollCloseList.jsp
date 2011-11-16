<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<jsp:useBean id="frm" class="com.savantit.prodacs.presentation.tableadmin.payroll.PayrollCloseListForm" />
<jsp:setProperty name="frm" property="*" /> 
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<%@ page import="com.savantit.prodacs.facade.SessionPayrollDetailsManager"%>
<%@ page import="com.savantit.prodacs.facade.SessionPayrollDetailsManagerHome"%>
<useradmin:userrights resource="26"/>
<%
	if(BuildConfig.DMODE)
		System.out.println("Close payroll");
	Vector vec_payrollDet = new Vector();
	Vector vec_payrollDetails = new Vector();
	int pagecount = 1 ;
	int totalPages = 0;
	String payrollCycleName = "";
	String vec_Len = "";
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
		Set set = payRollCycleList.entrySet();
		Iterator it = set.iterator();
		while (it.hasNext())
		{
			Map.Entry me = (Map.Entry) it.next();
			String key = new String (me.getKey().toString());
			String value = new String (me.getValue().toString());
			if ((frm.getPayrollCycle().trim()).equalsIgnoreCase(key.trim()))
			{
				payrollCycleName = value;
			}
		}
		
		int maxItems = Integer.parseInt(frm.getMaxItems());
		pagecount = Integer.parseInt(frm.getPage());

		if(BuildConfig.DMODE)
		{
			System.out.println("MaxItem: "+ maxItems);
			System.out.println("Page: "+ pagecount);
			System.out.println("Start Index: "+ (((pagecount-1)*maxItems)+1));
		}
		
		/* For Filter */
		HashMap payrollDetails = payrollDetailsObj.getForClosePayRoll(Integer.parseInt(frm.getPayrollCycle()), (((pagecount-1)*maxItems)+1),maxItems);

		vec_payrollDetails = (Vector) payrollDetails.get("PayrollDetails");
		pageContext.setAttribute("vec_payrollDetails",vec_payrollDetails);
		vec_Len = vec_payrollDetails.size()+"";
		int totalCount = Integer.parseInt(payrollDetails.get("TotalRecordCount").toString());
		if(BuildConfig.DMODE)
			System.out.println("TotalRec: "+ totalCount);
  		if(totalCount%maxItems == 0)
  		{
	  		totalPages = (totalCount/maxItems);
  		}
  		else
  		{
	  		totalPages = (totalCount/maxItems) + 1;
	  	}
		if(totalPages==0)
			totalPages = 1;
	}
	catch (Exception e)
	{
		if(BuildConfig.DMODE)
		{
			System.out.println("Problem in PayrollCloseList.jsp");
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
	function chkCycle(val)
	{
		var temp = document.forms[0];
		temp.formAction.value = "load";
		temp.submit();
	}
	
	function loadDefault()
	{
		temp = document.forms[0];
	
		var formAction = '<%= frm.getFormAction()%>';
		var maxItems = '<%= frm.getMaxItems()%>';
		var page = '<%= frm.getPage()%>';
	
		temp.page.value = page;
	
		if(maxItems=="15")
		{
			temp.maxItems[0].checked = true;
		}
		else if(maxItems=="25")
		{
			temp.maxItems[1].checked = true;
		}
		else if(maxItems=="50")
		{
			temp.maxItems[2].checked = true;
		}
		else if(maxItems=="100")
		{
			temp.maxItems[3].checked = true;
		}
		else if(maxItems=="500")
		{
			temp.maxItems[4].checked = true;
		}
	}
	
	function pageDecrement()
	{
		temp = document.forms[0];
		var page = 1;
		page = parseInt(temp.page.value,10) - 1;
		temp.page.value  = page;
		temp.submit();
	}
	
	function pageIncrement()
	{
		temp = document.forms[0];
		var page = 1;
		page = parseInt(temp.page.value,10) + 1;
		temp.page.value  = page;
		temp.submit();
	}
	
	function navigation()
	{
		temp = document.forms[0];
		temp.page.value  = 1;
		temp.submit();
	}

	function changePage()
	{
		temp = document.forms[0];
		temp.page.value  = parseInt(temp.page.value,10);
		temp.submit();
	}
	
	function loadTables()
	{
		var temp = document.forms[0];
		temp.vector_Len.value = '<%= vec_Len %>';

		if ((temp.payrollCycle.value != "0") && (temp.vector_Len.value != 0))
		{
			tblPayrollCycle.style.display = "block";
		}
		else if ((temp.payrollCycle.value != "0") && (temp.vector_Len.value == 0))
		{
			alert("No Employee Details available to close Payroll!");
			tblPayrollCycle.style.display = "none";
		}
	}

	function closePayroll()
	{
		var temp = document.forms[0];
		if(confirm('Warning! \n\nDo your Really want to Close this Payroll Cycle?'))
		{
			temp.formAction.value = "close";
			temp.submit();
		}
		else
		{
			return false;
		}
	}
</script>
</head>

<body onload="loadDefault(); loadTables();">
<html:form action="frmClosePayrollList" focus="payrollCycle">
<html:hidden property="formAction"/>
<input type="hidden" name="vector_Len"/>
	<table width="100%" cellspacing="0" cellpadding="10">
	<tr>
	<td>
		<table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
		<tr>
			<td><bean:message key="prodacs.payroll.closepayroll"/></td>
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
	<br>
			<table width="100%" cellspacing="0" cellpadding="0">
			<tr> 
				<td width="100" class="FieldTitle"><bean:message key="prodacs.payroll.payrollcycle"/></td>
				<td width="1" class="FieldTitle">:</td>
				<td class="FieldTitle"><html:select property="payrollCycle" styleClass="Combo" onchange="chkCycle(this.value)">
				<html:option value="0">-- Payroll Cycle --</html:option>
				<html:options collection="payRollCycleList" property="key" labelProperty="value" />
				</html:select></td>
			</tr>
			</table>
	<br>
				<table width="100%" cellpadding="0" cellspacing="0" id="tblPayrollCycle" style="display:none">
				<tr>
					<td class="FieldTitle">
					<fieldset id="FieldSet"><legend>&nbsp;&nbsp;<%= payrollCycleName %>&nbsp;&nbsp;</legend>
					<br>
						<table width="100%" cellspacing="0" cellpadding="3">
						<tr> 
							<td width="95" class="Header"><bean:message key="prodacs.job.date"/></td>
							<td width="50" class="Header"><bean:message key="prodacs.production.shift"/></td>
							<td class="Header"><bean:message key="prodacs.production.employeename"/></td>
							<td width="50" class="Header"><bean:message key="prodacs.production.dutyhrs"/></td>
							<td width="50" class="Header"><bean:message key="prodacs.production.othrs"/></td>
							<td width="75" class="Header"><bean:message key="prodacs.payroll.regularsalaryhrs"/></td>
							<td width="65" class="Header"><bean:message key="prodacs.payroll.otsalaryhrs"/></td>
							<td width="60" class="Header"><bean:message key="prodacs.payroll.incentivesalaryhours"/></td>
							<td width="85" class="Header"><bean:message key="prodacs.payroll.nooftimesadjusted"/></td>
						</tr>
						<logic:iterate id="bt1" name="vec_payrollDetails" indexId="count">
					<%
						if ((count.intValue() % 2) == 0)
						{
					%>
						<tr> 
							<td class="TableItems"><logic:notEmpty name="bt1" property="prodDate">
													<bean:define id="proDate" name="bt1" property="prodDate"/>
													<%= proDate.toString().substring(0,10) %></logic:notEmpty>&nbsp;</td>
							<td class="TableItems"><bean:write name="bt1" property="shiftName"/>&nbsp;</td>
							<td class="TableItems"><bean:write name="bt1" property="empName"/>&nbsp;</td>
							<td class="TableItems"><bean:write name="bt1" property="dtyHrs"/>&nbsp;</td>
							<td class="TableItems"><bean:write name="bt1" property="otHrs"/>&nbsp;</td>
							<td class="TableItems"><bean:write name="bt1" property="dtySlryHrs"/>&nbsp;</td>
							<td class="TableItems"><bean:write name="bt1" property="otSlryHrs"/>&nbsp;</td>
							<td class="TableItems"><bean:write name="bt1" property="incntvSlryHrs"/>&nbsp;</td>
							<td class="TableItems"><bean:write name="bt1" property="noOfTimesAdjstd"/>&nbsp;</td>
						</tr>
					<%
						}
						else
						{
					%>	
						<tr> 
							<td class="TableItems2"><logic:notEmpty name="bt1" property="prodDate">
													<bean:define id="proDat" name="bt1" property="prodDate"/>
													<%= proDat.toString().substring(0,10) %></logic:notEmpty>&nbsp;</td>
							<td class="TableItems2"><bean:write name="bt1" property="shiftName"/>&nbsp;</td>
							<td class="TableItems2"><bean:write name="bt1" property="empName"/>&nbsp;</td>
							<td class="TableItems2"><bean:write name="bt1" property="dtyHrs"/>&nbsp;</td>
							<td class="TableItems2"><bean:write name="bt1" property="otHrs"/>&nbsp;</td>
							<td class="TableItems2"><bean:write name="bt1" property="dtySlryHrs"/>&nbsp;</td>
							<td class="TableItems2"><bean:write name="bt1" property="otSlryHrs"/>&nbsp;</td>
							<td class="TableItems2"><bean:write name="bt1" property="incntvSlryHrs"/>&nbsp;</td>
							<td class="TableItems2"><bean:write name="bt1" property="noOfTimesAdjstd"/>&nbsp;</td>
						</tr>
					<%
						}
					%>
						</logic:iterate>
						</table>
					<table width="100%" cellpadding="0" cellspacing="0" id="Filter">
					<tr>&nbsp;</tr>
					<tr> 
					<td width="100"><bean:message key="prodacs.common.page"/> <%=pagecount%> <bean:message key="prodacs.common.pageof"/> <%=totalPages%> </td>
					<td><bean:message key="prodacs.common.maxitem"/>
					  <html:radio property="maxItems" value="15"/>15 
					  <html:radio property="maxItems" value="25"/>25 
					  <html:radio property="maxItems" value="50"/>50 
					  <html:radio property="maxItems" value="100"/>100 
					  <html:radio property="maxItems" value="500"/>500 
					  <html:button property="showPage" styleClass="Button" onclick="navigation()"> Show</html:button></td>
					<td>
				<%
					if (Integer.parseInt(frm.getPage()) > 1)
					{
				%>
					<a href="javascript:pageDecrement()"><img src='<bean:message key="context"/>/images/prev.gif' width="15" height="15" border="0" align="absmiddle">
					<bean:message key="prodacs.common.previous"/></a> 
				<%
					}
					if ((Integer.parseInt(frm.getPage()) > 1) && (Integer.parseInt(frm.getPage()) < totalPages ))
					{
				%>| 
				<%
					}
					if (Integer.parseInt(frm.getPage()) < totalPages )
					{
				%>
					<a href="javascript:pageIncrement()"><bean:message key="prodacs.common.next"/>
					<img src='<bean:message key="context"/>/images/next.gif' width="15" height="15" border="0" align="absmiddle"></a> | 
				<%
					}
				%>
					<bean:message key="prodacs.common.goto"/>
					<html:select property="page" styleClass="Combo" onchange="changePage()">
				<%
					for(int i=0;i<totalPages;i++)
					{
				%>
					<option value='<%=(i+1)%>'><%=(i+1)%></option>
				<%
					}
				%>	
					</html:select>
					</td>
					</tr>
					</table>
	<br>
			<table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
			<tr>
				<td><html:button property="Button" styleClass="Button" value="Close Payroll" onclick="closePayroll();"/></td>
			</tr>
			</table>
			</fieldset>
			</td>
		</tr>
        </table>
		</td>
	</tr>
	</table>
</html:form>
</body>
</html:html>
