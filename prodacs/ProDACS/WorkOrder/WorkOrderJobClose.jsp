<%@ page language = "java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri = "/WEB-INF/struts-logic.tld" prefix = "logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<jsp:useBean id="frm" class="com.savantit.prodacs.presentation.tableadmin.workorder.WorkOrderJobCloseForm" />
<jsp:setProperty name="frm" property="*" /> 

<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.savantit.prodacs.infra.beans.Filter"%>
<%@ page import="java.util.StringTokenizer"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<%@ page import="com.savantit.prodacs.facade.SessionWorkOrderDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionWorkOrderDetailsManager"%>
<useradmin:userrights resource="15"/>	
<% 
	if (BuildConfig.DMODE)
		System.out.println("WorkOrder/Job Closing");
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	int totalPages = 0;
	int pageCount = 1 ;
	int columnNumber = 1;

	boolean order = true;
	String sortFieldName = frm.getSortField();
	if (BuildConfig.DMODE)
		System.out.println(sortFieldName);
	
	/* Setting the SortFields */
	if(sortFieldName.equals("WO_NO"))
	{
		columnNumber = 1;
	}
	else if(sortFieldName.equals("WO_DATE"))
	{
		columnNumber = 2;
	}
	else if(sortFieldName.equals("CUST_NAME"))
	{
		columnNumber = 3;
	}
	else if(sortFieldName.equals("JB_NAME"))
	{
		columnNumber = 4;
	}
	else if(sortFieldName.equals("JB_DWG_NO"))
	{
		columnNumber = 5;
	}
	else if(sortFieldName.equals("JB_RVSN_NO"))
	{
		columnNumber = 6;
	}
	else if(sortFieldName.equals("JB_MATL_TYP"))
	{
		columnNumber = 7;
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
	   obj.setJndiName("SessionWorkOrderDetailsManagerBean");
	   obj.setEnvironment();

		/* 	Creating the Home and Remote Objects 	*/
		SessionWorkOrderDetailsManagerHome woHomeObj = (SessionWorkOrderDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionWorkOrderDetailsManagerHome.class);
		SessionWorkOrderDetailsManager workOrderObj = (SessionWorkOrderDetailsManager)PortableRemoteObject.narrow(woHomeObj.create(),SessionWorkOrderDetailsManager.class);
		
		if (frm.getFormAction().equalsIgnoreCase(""))
		{
		   frm.setFormAction("");
			Filter fil[] = new Filter[0];
	 		session.setAttribute("objFilter",fil);
		}
  		else if(frm.getFormAction().equalsIgnoreCase("search"))
  		{
  		  int maxItems = Integer.parseInt(frm.getMaxItems());
  		  Vector filter = new Vector();  		  
  		  /* For Searching WO NO */
  		  	if(!frm.getWoNo().equalsIgnoreCase(""))
  		  	{
	  		  	Filter tempFilter = new Filter();
	  		  	tempFilter.setFieldName("WO_NO");
	  		  	tempFilter.setFieldValue(frm.getWoNo());
  		  		String specialFunction = "";
  		  		if (frm.getWoNoCombo().equals("0"))
  		  		{
  		  			specialFunction = "Starts With";
  		  		}
  		  		else if (frm.getWoNoCombo().equals("1"))
  		  		{
  		  			specialFunction = "Ends With";
  		  		}
  		  		else if (frm.getWoNoCombo().equals("2"))
  		  		{
  		  			specialFunction = "Exactly";
  		  		}
  		  		else if (frm.getWoNoCombo().equals("3"))
  		  		{
  		  			specialFunction ="AnyWhere";
  		  		}
  		  		tempFilter.setSpecialFunction(specialFunction);
  		  		filter.add(tempFilter);
  		  	}
		  /* For Searching Customer Name */
			if (!frm.getCustomer().equals(""))
			{
 				Filter tempFilter = new Filter();
	   			tempFilter.setFieldName("CUST_NAME");
			   	tempFilter.setFieldValue(frm.getCustomer());
			   	String specialFunction="";
			   	if(frm.getCustCombo().equals("0"))
				{
					specialFunction = "Starts With";
				}
				else if(frm.getCustCombo().equals("1"))
				{
					specialFunction = "Ends With";
				}
				else if(frm.getCustCombo().equals("2"))
				{
					specialFunction = "Exactly";
				}
				else if(frm.getCustCombo().equals("3"))
				{
					specialFunction = "Anywhere";
				}
				tempFilter.setSpecialFunction(specialFunction);
				filter.add(tempFilter);
			}
			/* For Searching Job Name */
			  if (!frm.getJbName().equals(""))
			  {
 				Filter tempFilter = new Filter();
	   			tempFilter.setFieldName("JB_NAME");
			   	tempFilter.setFieldValue(frm.getJbName());
			   	String specialFunction="";
			   	if(frm.getJbCombo().equals("0"))
				{
					specialFunction = "Starts With";
				}
				else if(frm.getJbCombo().equals("1"))
				{
					specialFunction = "Ends With";
				}
				else if(frm.getJbCombo().equals("2"))
				{
					specialFunction = "Exactly";
				}
				else if(frm.getJbCombo().equals("3"))
				{
					specialFunction = "Anywhere";
				}
				tempFilter.setSpecialFunction(specialFunction);
				filter.add(tempFilter);
			  }
			/* By WorkOrder Date */
			if ((!frm.getWoFromDate().equalsIgnoreCase("")) && (!frm.getWoToDate().equalsIgnoreCase("")))
			{
				String[] month = {"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
				StringTokenizer frmDate = new StringTokenizer((frm.getWoFromDate()).trim(),"-");
				String tempFrmDate[] = new String[frmDate.countTokens()];
				int i=0;
				while(frmDate.hasMoreTokens())
				{
					tempFrmDate[i] = frmDate.nextToken();
					if (BuildConfig.DMODE)
						System.out.println("Frm: "+tempFrmDate[i]);
					i++;
				}
				
				StringTokenizer toDate = new StringTokenizer((frm.getWoToDate()).trim(),"-");
				String tempToDate[] = new String[toDate.countTokens()];
				i = 0;
				while (toDate.hasMoreTokens())
				{
					tempToDate[i] = toDate.nextToken();
					if (BuildConfig.DMODE)
						System.out.println("Frm: "+tempToDate[i]);
					i++;
				}
				
				String proFDate = tempFrmDate[2] + "-" + month[Integer.parseInt(tempFrmDate[1])-1] + "-" + tempFrmDate[0];
				String proTDate = tempToDate[2] + "-" + month[Integer.parseInt(tempToDate[1])-1] + "-" + tempToDate[0];
				String woDate = proFDate  + "$" + proTDate;
				if (BuildConfig.DMODE)
					System.out.println("WorkOrder Date: "+ woDate);
				Filter temp = new Filter();
				temp.setFieldName("WO_DATE");
				temp.setFieldValue(woDate);
				temp.setSpecialFunction("DateBetween");
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
			
		HashMap hm_JobClose = workOrderObj.workOrderJobCloseView(objFilter, frm.getSortField(), order,  ((pageCount-1)*maxItems)+1, maxItems);
		if (BuildConfig.DMODE)
			System.out.println("HM_JobClose: "+hm_JobClose);
		
		Vector vec_JobClose = (Vector) hm_JobClose.get("WorkOrderCloseDetails");
		pageContext.setAttribute("vec_JobClose",vec_JobClose);
		
		int totalCount = Integer.parseInt((hm_JobClose.get("TotalRecordCount")).toString());
	  	
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
	}catch (Exception e)
	{
		if (BuildConfig.DMODE)
			e.printStackTrace();
	}
%>
<html:html>
<head>
<title><bean:message key="prodacs.workorder.workorderjobclosing"/></title>
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
	   WorkOrderJobClose.submit;
	 }
 }
var oPopup;
	function init()
	 {
		oPopup = window.createPopup();
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
	 
	function navigation()
	{
		temp = document.forms[0];
		temp.formAction.value="search";
		temp.page.value = 1;
		temp.submit();
	}
	
	function changePage()
	{
		temp = document.forms[0];
		temp.formAction.value = "search";
		temp.page.value = parseInt(temp.page.value,10);
		temp.submit();
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

	function submitItem()
	{
		var temp = document.forms[0];
		var queryString = '<bean:message key="context"/>/frmWrkOrdJobClose.do?formAction=post';
		var val = getMultipleCheckedValue('frmJobClose', document.forms[0]);
		if (val == "")
		{
			alert ("Select a WorkOrder/Job to Close!");
			return false;
		}
		var subQuery = '';
		for(var i=0;i<val.length;i++)
		{
		   subQuery = subQuery+'&ids='+val[i];
		}
		queryString = queryString + subQuery;
		temp.action = queryString;
		temp.submit();
	}
	
</script>
</head>

<body onload="init(); loadDefault();sortByTable('frmJobClose',<%= columnNumber%>,'<%= order%>')">
<html:form action="frmWrkOrdJobClose">
<html:hidden property = "formAction"/>
<html:hidden property="sortField"  value="<%= frm.getSortField()%>"/>
<html:hidden property="sortOrder" value="<%= frm.getSortOrder()%>"/>
<html:hidden property="ids"/>
  <table width="100%" cellspacing="0" cellpadding="10">
    <tr>
      <td><table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
          <tr>
            <td><bean:message key="prodacs.workorder.workorderjobclosing"/></td>
          </tr>
        </table>
		<br><br>
        <table width="100%" cellspacing="0" cellpadding="0">
          <tr> 
            <td width="20" id="FilterTip">&nbsp;</td>
            <td class="FilterTitle"><a href="javascript:showFilter(document.getElementById('Filter'), document.getElementById('FilterImg'))"><img src='<bean:message key="context"/>/images/hide.gif' width="9" height="5" border="0" align="absmiddle" id="FilterImg"> 
              <bean:message key="prodacs.common.filter"/></a></td>
            <td width="20" height="20" id="FilterEnd">&nbsp;</td>
          </tr>
        </table>
		<table width="100%" cellspacing="0" cellpadding="0" id="Filter">
          <tr> 
            <td width="70"><bean:message key="prodacs.workorder.fromdate"/></td>
            <td width="1">:</td>
            <td width="200"><html:text property="woFromDate" styleClass="TextBox" size="12" readonly="true"/>
              <img src='<bean:message key="context"/>/images/calendar.gif' width="16" height="15" align="absmiddle" onClick='show_calendar("woFromDate",WorkOrderJobClose.woFromDate.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"></td>
            <td width="50"><bean:message key="prodacs.workorder.todate"/></td>
            <td width="1">:</td>
            <td><html:text property="woToDate" styleClass="TextBox" size="12" readonly="true"/>
              <img src='<bean:message key="context"/>/images/calendar.gif' width="16" height="15" align="absmiddle" onClick='show_calendar("woToDate",WorkOrderJobClose.woToDate.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"></td> 
          	<td><bean:message key="prodacs.workorder.workorderno"/></td>
			<td width="1">:</td>
			<td><html:text property="woNo" styleClass="TextBox" size="12"/>
				<html:select property="woNoCombo" styleClass="Combo">
				    <html:option value="0">Start With</html:option>
					<html:option value="1">End With</html:option>
					<html:option value="2">Exactly</html:option>
					<html:option value="3">AnyWhere</html:option>
 				</html:select>&nbsp;</td>
			</tr>
		    <td><bean:message key="prodacs.workorder.customer"/></td>
            <td>:</td>
            <td><html:text property="customer" styleClass="TextBox" size="12"/>
            	<html:select property="custCombo" styleClass="Combo">
				        <html:option value="0">Start With</html:option>
						<html:option value="1">End With</html:option>
						<html:option value="2">Exactly</html:option>
						<html:option value="3">AnyWhere</html:option>
	 			</html:select>&nbsp;</td>
			<td><bean:message key="prodacs.job.jobname"/></td>
			<td>:</td>
			<td><html:text property="jbName" styleClass="TextBox" size="12"/>
				<html:select property="jbCombo" styleClass="Combo">
					<html:option value="0">Start With</html:option>
					<html:option value="1">End With</html:option>
					<html:option value="2">Exactly</html:option>
					<html:option value="3">AnyWhere</html:option>
				</html:select>&nbsp;</td>
            <td><html:button styleClass="Button" property="search" onclick="navigation()" value="Go"/></td>
          </tr>
        </table>
        <font class="message">
         	<html:messages id="messageid" message="true">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="messageid" />
		   	</html:messages>
		  </font>
		<table>
			<tr><td colspan='2'> <font size="1px" face="Verdana"><html:errors/></font></td></tr>
		</table>
        <br>
        <table width="100%" cellspacing="0" cellpadding="0" id="tblJob">
          <tr> 
              <table width="100%" cellspacing="0" cellpadding="0" id = "frmJobClose">
                <tr> 
                  <td width="25" class="SortHeader"><input name="CheckAllDym" type="checkbox" id="CheckAllDym" value="checkbox" onClick="checkAllDym(document.WorkOrderJobClose)"></td>
                  <td width="70" class="SortHeader" onClick="sortTables(document.forms[0],this,'WO_NO')"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"> 
				  <bean:message key="prodacs.workorder.workorderno"/></td>
                  <td width="100" class="SortHeader" onClick="sortTables(document.forms[0],this,'WO_DATE')"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"> 
				  <bean:message key="prodacs.workorder.workorderdate"/></td>
                  <td class="SortHeader" onClick="sortTables(document.forms[0],this,'CUST_NAME')"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"> 
				  <bean:message key="prodacs.customer.customername"/></td>
                  <td width="100" class="SortHeader" onClick="sortTables(document.forms[0],this,'JB_NAME')"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"> 
				  <bean:message key="prodacs.job.jobname"/></td>
		          <td width="100" class="SortHeader" onClick="sortTables(document.forms[0],this,'JB_DWG_NO')"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"> 
				  <bean:message key="prodacs.job.drawing"/></td>
		          <td width="100" class="SortHeader" onClick="sortTables(document.forms[0],this,'JB_RVSN_NO')"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"> 
				  <bean:message key="prodacs.job.revision"/></td>
		          <td width="100" class="SortHeader" onClick="sortTables(document.forms[0],this,'JB_MATL_TYP')"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"> 
				  <bean:message key="prodacs.job.materialtype"/></td>
                </tr>
                <logic:iterate id="bt1" name="vec_JobClose" indexId="count">
		          <tr>
				      <%
					      if(count.intValue()%2 == 0) 
				   	   {
				     	%>
				            <td class="TableItems"><input name="CheckValue" type="checkbox" id="CheckValue" value='<bean:write name="bt1" property="workOrderJbId"/>'/></td>
				            <td class="TableItems"><bean:write name="bt1" property="workOrderNo"/></td>
				            <td class="TableItems"><bean:define id="woDate" name="bt1" property="workOrderDate"/><%= woDate.toString().substring(0,10) %></td>
				            <td class="TableItems"><bean:write name="bt1" property="custName"/></td>
				            <td class="TableItems"><bean:write name="bt1" property="jobName"/></td>
				            <td class="TableItems"><bean:write name="bt1" property="drwgNo"/></td>
				            <td class="TableItems"><bean:write name="bt1" property="rvsnNo"/></td>
				            <td class="TableItems"><bean:write name="bt1" property="matlType"/></td>
						<%
							} else
							{
						%>            
				            <td class="TableItems2"><input name="CheckValue" type="checkbox" id="CheckValue" value='<bean:write name="bt1" property="workOrderJbId"/>'/></td>
				            <td class="TableItems2"><bean:write name="bt1" property="workOrderNo"/></td>
				            <td class="TableItems2"><bean:define id="woDate1" name="bt1" property="workOrderDate"/><%= woDate1.toString().substring(0,10) %></td>
				            <td class="TableItems2"><bean:write name="bt1" property="custName"/></td>
				            <td class="TableItems2"><bean:write name="bt1" property="jobName"/></td>
				            <td class="TableItems2"><bean:write name="bt1" property="drwgNo"/></td>
				            <td class="TableItems2"><bean:write name="bt1" property="rvsnNo"/></td>
				            <td class="TableItems2"><bean:write name="bt1" property="matlType"/></td>
						<%
							}
						%> 
					</tr>
			   	 </logic:iterate>
              </table>
			  <br>
              <table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
                <tr> 
                  <td><html:button property="jobClose" styleClass="Button" value = " Close " onclick="submitItem();"/></td>
                </tr>
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
              <html:select property="page" styleClass="Combo" onchange="changePage()">
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
        </table></td>
    </tr>
  </table>
</html:form>
</body>
</html:html>
