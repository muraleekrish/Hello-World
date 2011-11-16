<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<jsp:useBean id="frm" class="com.savantit.prodacs.presentation.tableadmin.payroll.PayrollIdForm" />
<jsp:setProperty name="frm" property="*" />

<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.StringTokenizer"%>
<%@ page import="com.savantit.prodacs.infra.beans.Filter"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<%@ page import="com.savantit.prodacs.facade.SessionPayrollDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionPayrollDetailsManager"%>
<useradmin:userrights resource="41"/>
<% 
	if (BuildConfig.DMODE)
		System.out.println("Payroll Id List");
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	int totalPages = 0;
	int pageCount = 1 ;
	int columnNumber = 0;
	int totalCount = 0;
	
	boolean order = true;
	String sortFieldName = frm.getSortField();
	if (BuildConfig.DMODE)
		System.out.println(sortFieldName);
	
	/* Setting the SortFields */
	if(sortFieldName.equals("PYRL_CYCLE_STAT_FROM_DATE"))
	{
		columnNumber=0;
	}
	else if(sortFieldName.equals("PYRL_CYCLE_STAT_TO_DATE"))
	{
		columnNumber=1;
	}
	else if(sortFieldName.equals("IS_PYRL_CREATED"))
	{
		columnNumber=2;
	}
	else if(sortFieldName.equals("IS_PYRL_CLOSED"))
	{
		columnNumber=3;
	}
	
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
	try
	  {
		/* 	Setting the JNDI name and Environment 	*/
	   	obj.setJndiName("SessionPayrollDetailsManagerBean");
   		obj.setEnvironment();

		/* 	Creating the Home and Remote Objects 	*/
		SessionPayrollDetailsManagerHome objPayrollDetailsHome = (SessionPayrollDetailsManagerHome) PortableRemoteObject.narrow(obj.getHome(),SessionPayrollDetailsManagerHome.class);
		SessionPayrollDetailsManager objPayrollDetails = (SessionPayrollDetailsManager) PortableRemoteObject.narrow(objPayrollDetailsHome.create(),SessionPayrollDetailsManager.class);

		if(frm.getFormAction().equalsIgnoreCase(""))
		{  
			Filter fil[] = new Filter[0];
	 		session.setAttribute("objFilter",fil);
  		}
  		else if(frm.getFormAction().equalsIgnoreCase("search"))
  		{
			int maxItems = Integer.parseInt(frm.getMaxItems());
			
			Vector filter = new Vector();
			/* For Payroll Creation Status */
			if (BuildConfig.DMODE)
			{
				System.out.println("frm.getPayrollCreated() :"+frm.getPayrollCreated());
				System.out.println("frm.getPayrollClosed() :"+frm.getPayrollClosed());
			}
			if (frm.getPayrollCreated().equalsIgnoreCase("true"))
			{
				Filter temp = new Filter();
				temp.setFieldName("IS_PYRL_CREATED");
			   	temp.setFieldValue("1");
			   	filter.add(temp);
			}
			else if (frm.getPayrollCreated().equalsIgnoreCase("false"))
			{
				Filter temp = new Filter();
				temp.setFieldName("IS_PYRL_CREATED");
			   	temp.setFieldValue("0");
			   	filter.add(temp);
			}
			/* For Payroll Closed Status */
			if (frm.getPayrollClosed().equalsIgnoreCase("true"))
			{
				Filter temp = new Filter();
				temp.setFieldName("IS_PYRL_CLOSED");
			   	temp.setFieldValue("1");
			   	filter.add(temp);
			}
			else if (frm.getPayrollClosed().equalsIgnoreCase("false"))
			{
				Filter temp = new Filter();
				temp.setFieldName("IS_PYRL_CLOSED");
			   	temp.setFieldValue("0");
			   	filter.add(temp);
			}
			/* By PayrollId From Date */
			if (!frm.getFromDate().equalsIgnoreCase(""))
			{
				String[] month = {"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
				StringTokenizer frmDate = new StringTokenizer((frm.getFromDate()).trim(),"-");
				String tempFrmDate[] = new String[frmDate.countTokens()];
				int i=0;
				while(frmDate.hasMoreTokens())
				{
					tempFrmDate[i] = frmDate.nextToken();
					if (BuildConfig.DMODE)
						System.out.println("Frm: "+tempFrmDate[i]);
					i++;
				}
				String proFrmDate = tempFrmDate[2] + "-" + month[Integer.parseInt(tempFrmDate[1])-1] + "-" + tempFrmDate[0];
				String payDate = proFrmDate;
				if (BuildConfig.DMODE)
					System.out.println("PayrollId FrmDate: "+ payDate);
				Filter temp = new Filter();
				temp.setFieldName("PYRL_CYCLE_STAT_FROM_DATE");
				temp.setFieldValue(payDate);
				temp.setSpecialFunction("DateEqual");
				filter.add(temp);
			}
			/* By PayrollId To Date */
			if (!frm.getToDate().equalsIgnoreCase(""))
			{
				String[] month = {"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
				StringTokenizer toDate = new StringTokenizer((frm.getToDate()).trim(),"-");
				String tempToDate[] = new String[toDate.countTokens()];
				int i=0;
				while(toDate.hasMoreTokens())
				{
					tempToDate[i] = toDate.nextToken();
					if (BuildConfig.DMODE)
						System.out.println("To: "+tempToDate[i]);
					i++;
				}
				String proToDate = tempToDate[2] + "-" + month[Integer.parseInt(tempToDate[1])-1] + "-" + tempToDate[0];
				String payDate = proToDate;
				if (BuildConfig.DMODE)
					System.out.println("PayrollId ToDate: "+ payDate);
				Filter temp = new Filter();
				temp.setFieldName("PYRL_CYCLE_STAT_TO_DATE");
				temp.setFieldValue(payDate);
				temp.setSpecialFunction("DateEqual");
				filter.add(temp);
			}
			
			  Filter objFilter[] = new Filter[filter.size()];
	  		  filter.copyInto(objFilter);
	  		  session.setAttribute("objFilter",objFilter);
		}
		int maxItems = Integer.parseInt(frm.getMaxItems());
		int pagecount = Integer.parseInt(frm.getPage());

  		Filter objFilter[] = (Filter[])session.getAttribute("objFilter");

  		pageCount = Integer.parseInt(frm.getPage());
  		HashMap payrollIdDetails = objPayrollDetails.getAllPayRollIdsInfo(objFilter, frm.getSortField(),order,((pageCount-1)*maxItems)+1,maxItems);
  		if (BuildConfig.DMODE)
  			System.out.println("Payroll Id (HM) :"+payrollIdDetails);
  		
  		Vector pyrlIdDetails = (Vector)payrollIdDetails.get("PyrlDetails");
		pageContext.setAttribute("pyrlIdDetails",pyrlIdDetails);
		
	  	totalCount = Integer.parseInt((payrollIdDetails.get("TotalRecordCount")).toString());
		if (BuildConfig.DMODE)
			System.out.println("Total Record Count: "+ totalCount);

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
	catch(Exception e)
	{
		if (BuildConfig.DMODE)
		{
			System.out.println("Error in PayrollId.jsp");
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
<script src='<bean:message key="context"/>/library/calendar_search.js'></script>
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
	   frmRewrkLogList.submit;
	 }
 }
 var oPopup;
	function init()
	 {
		oPopup = window.createPopup();
	 }

</script>
<script language = "Javascript" language="text/Javascript">
	function navigation()
	{
		temp = document.forms[0];
		temp.formAction.value="search";
		temp.submit();
	}
	
	function loadDefault()
	{
		temp = document.forms[0];
	
		var formAction = '<%=frm.getFormAction()%>';
		var maxItems = '<%=frm.getMaxItems()%>';
		var page = '<%=frm.getPage()%>';
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
	}
	
	function sortTables(fm, col, field)
	{
		direction = 1;
		if(col.sortOrder == 1)
		   direction = -1;
	
		header = col.parentNode;
		tab = header.parentNode;
		for(i=1;i<(header.children.length);i++)
		{
			header.children[i].children[0].style.visibility ="hidden" ;
			header.children[i].title = "Sort by" + header.children[i].innerText +" in Ascending";
		}
	
		col.children[0].style.visibility="visible";
		
		if(direction==-1)
		{
			col.children[0].src = '<bean:message key="context"/>/images/sort.gif';
			col.title = "Sort by" + col.innerText + " in Ascending";
			fm.sortField.value = field;
			fm.sortOrder.value = 'desending';
		}
		else
		{
			col.children[0].src ='<bean:message key="context"/>/images/sort_up.gif';
			col.title = "Sort by" + col.innerText + " in Descending";
			fm.sortField.value = field;
			fm.sortOrder.value = 'ascending';
	
		}
		for(i=0;i<header.cells.length;i++)
		{
			if(header.cells[i]==col)
			{
				colNum = i;
				col.sortOrder=direction;
			}
			else
				header.cells[i].sortOrder=-1;
		}
		fm.formAction.value="sorting";
		fm.submit();
	}
	
	function pageDecrement()
	{
		temp = document.forms[0];
		var page = 1;
		page = parseInt(temp.page.value,10) - 1;
		temp.page.value  = page;
		temp.submit();
		//navigation();
	}
	
	function pageIncrement()
	{
		temp = document.forms[0];
		var page = 1;
		page = parseInt(temp.page.value,10) + 1;
		temp.page.value  = page;
		temp.submit();
		//navigation();
	}
	
	function addItem()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/PayrollAdministration/PayrollIdAdd.jsp';
		temp.submit();
	}
</script>
</head>

<body onLoad="init(); loadDefault();sortByTable('payrollIdList',<%= columnNumber%>,'<%= order%>');">
<html:form action="frmPayrollId">
<html:hidden property="formAction"/>
<html:hidden property="sortField"  value="<%= frm.getSortField()%>"/>
<html:hidden property="sortOrder" value="<%= frm.getSortOrder()%>"/>
	<table width="100%" cellspacing="0" cellpadding="10">
	<tr> 
	<td>
		<table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
		<tr>
			<td><bean:message key="prodacs.payroll.payrollid.header"/></td>
		</tr>
		</table>
	<br>
		<table width="100" cellspacing="0" cellpadding="0" align="right">
		<tr> 
	 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Modify PayrollId Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1041" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
		</tr>
		</table>
	<br>
		<table width="100%" cellspacing="0" cellpadding="0">
		<tr> 
			<td width="20" id="FilterTip">&nbsp;</td>
			<td class="FilterTitle"><a href="javascript:showFilter(document.getElementById('Filter'), document.getElementById('FilterImg'))"><img src='<bean:message key="context"/>/images/hide.gif' width="9" height="5" border="0" align="absmiddle" id="FilterImg"><bean:message key="prodacs.common.filter"/></a></td>
			<td width="20" height="20" id="FilterEnd">&nbsp;</td>
		</tr>
		</table>
		<table width="100%" cellspacing="0" cellpadding="0" id="Filter">
          	<tr> 
            	<td width="70"><bean:message key="prodacs.workorder.fromdate"/></td>
	            <td width="1">:</td>
     			<td width="200"><html:text property="fromDate" styleClass="TextBox" size="12" readonly="true"/>
            	  <img src='<bean:message key="context"/>/images/calendar.gif' width="16" height="15" align="absmiddle" onClick='show_calendar("fromDate",PayrollId.fromDate.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"></td>
			<td width="100"></td>
	            <td width="80"><bean:message key="prodacs.workorder.todate"/></td>
     			<td width="1">:</td>
            	<td width="250"><html:text property="toDate" styleClass="TextBox" size="12" readonly="true"/>
	              <img src='<bean:message key="context"/>/images/calendar.gif' width="16" height="15" align="absmiddle" onClick='show_calendar("toDate",PayrollId.toDate.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"></td>
			</tr>
			<tr>
		      <td><bean:message key="prodacs.payroll.payrollcreated"/> </td>
			  <td>:</td>
			  <td><html:select property="payrollCreated" styleClass="Combo">
               			<html:option value="0">-- Created Payroll --</html:option>
						<html:option value="true">Yes</html:option>
						<html:option value="false">No</html:option>
 					</html:select>
 			  </td>
			  <td></td>
 			  <td><bean:message key="prodacs.payroll.payrollclosed"/></td>
			  <td>:</td>
			  <td><html:select property="payrollClosed" styleClass="Combo">
	                	<html:option value="0">-- Closed Payroll --</html:option>
	                	<html:option value="true">Yes</html:option>
	                	<html:option value="false">No</html:option>
			     	</html:select> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<html:button styleClass="Button" property="search" onclick="navigation()" value="Go"/>
 			</td>
		</tr>
		</table>
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

		<table width="100%" cellpadding="0" cellspacing="0" id="payrollIdList">
		<tr height="20">
			<td width="150" class="SortHeader" onClick="sortTables(document.forms[0],this,'PYRL_CYCLE_STAT_FROM_DATE')"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"><bean:message key="prodacs.workorder.fromdate"/></td>
			<td width="150" onClick="sortTables(document.forms[0],this,'PYRL_CYCLE_STAT_TO_DATE')" class="SortHeader"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"><bean:message key="prodacs.workorder.todate"/></td>
			<td width="130" onClick="sortTables(document.forms[0],this,'IS_PYRL_CREATED')" class="SortHeader"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"><bean:message key="prodacs.payroll.payrollcreated"/></td>
			<td width="130" onClick="sortTables(document.forms[0],this,'IS_PYRL_CLOSED')" class="SortHeader"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"><bean:message key="prodacs.payroll.payrollclosed"/></td>
		</tr>
		<logic:iterate id="bt1" name="pyrlIdDetails" indexId="count">
       	<tr>
<%
	if(count.intValue()%2 == 0)
	{
%>
		<td class="TableItems"><bean:define id="payFromDate" name="bt1" property="fromDate"/><%= payFromDate.toString().substring(0,10) %>&nbsp;</td>
		<td class="TableItems"><bean:define id="payToDate" name="bt1" property="toDate"/><%= payToDate.toString().substring(0,10) %>&nbsp;</td>
		<td align="center" class="TableItems">
		<logic:equal name="bt1" property="pyrl_Created" value="false"><img src='<bean:message key="context"/>/images/inactive.gif'></logic:equal>
		<logic:equal name="bt1" property="pyrl_Created" value="true"><img src='<bean:message key="context"/>/images/active.gif'></logic:equal>&nbsp;</td>
		<td align="center" class="TableItems">
		<logic:equal name="bt1" property="pyrl_Closed" value="false"><img src='<bean:message key="context"/>/images/inactive.gif'></logic:equal>
		<logic:equal name="bt1" property="pyrl_Closed" value="true"><img src='<bean:message key="context"/>/images/active.gif'></logic:equal>&nbsp;</td>&nbsp;</td>
<%
	} 
	else
	{
%>
		<td class="TableItems2"><bean:define id="fromDate" name="bt1" property="fromDate"/><%= fromDate.toString().substring(0,10) %>&nbsp;</td>
		<td class="TableItems2"><bean:define id="toDate" name="bt1" property="toDate"/><%= toDate.toString().substring(0,10) %>&nbsp;</td>
		<td align="center" class="TableItems2">
			<logic:equal name="bt1" property="pyrl_Created" value="false"><img src='<bean:message key="context"/>/images/inactive.gif'></logic:equal>
			<logic:equal name="bt1" property="pyrl_Created" value="true"><img src='<bean:message key="context"/>/images/active.gif'></logic:equal>&nbsp;</td>
		<td align="center" class="TableItems2">
			<logic:equal name="bt1" property="pyrl_Closed" value="false"><img src='<bean:message key="context"/>/images/inactive.gif'></logic:equal>
			<logic:equal name="bt1" property="pyrl_Closed" value="true"><img src='<bean:message key="context"/>/images/active.gif'></logic:equal>&nbsp;</td>&nbsp;</td>
<%
	}
%>
		</tr>
   	 	</logic:iterate>
		</table>
		
       	<table width="100%" cellpadding="0" cellspacing="0" id="Filter">
		<tr>&nbsp;</tr>
          	<tr> 
            <td width="100"><bean:message key="prodacs.common.page"/> <%=pageCount%> <bean:message key="prodacs.common.pageof"/> <%=totalPages%> </td>
            <td><bean:message key="prodacs.common.maxitem"/>
              <html:radio property="maxItems" value="15"/>15 
              <html:radio property="maxItems" value="25"/>25 
              <html:radio property="maxItems" value="50"/>50 
              <html:radio property="maxItems" value="100"/>100 
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
            <html:select property="page" styleClass="Combo" onchange="navigation()">
      <%
			for(int i=0;i<totalPages;i++)
			{
	%>
		   	<option value='<%=(i+1)%>'><%=(i+1)%></option>
	 <%
		   	}
	 %>
              
            </html:select></td>
          	</tr>
         	</table>
	</td>
	</tr>
	</table>
</html:form>
</body>
</html:html>	
