<%@ page language = "java" session="true"%>

<%@ taglib uri = "/WEB-INF/struts-html.tld" prefix = "html" %>
<%@ taglib uri = "/WEB-INF/struts-bean.tld" prefix = "bean" %>
<%@ taglib uri = "/WEB-INF/struts-logic.tld" prefix = "logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<jsp:useBean id="frm" class="com.savantit.prodacs.presentation.tableadmin.employee.EmployeeListForm" />
<jsp:setProperty name="frm" property="*" />

<%@ page import="com.savantit.prodacs.facade.SessionEmpDetailsManager"%>
<%@ page import="com.savantit.prodacs.facade.SessionEmpDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.savantit.prodacs.infra.beans.Filter"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<useradmin:userrights resource="4"/>
<% 
	if (BuildConfig.DMODE)
		System.out.println("Employee List");
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	int totalPages = 0;
	int pageCount = 1 ;
	int columnNumber = 1;
	int totalCount = 0;
	
	boolean order = true;
	String sortFieldName = frm.getSortField();
	if (BuildConfig.DMODE)
		System.out.println(sortFieldName);
	
	if(sortFieldName.equals("EMP_NAME"))
	{
		columnNumber=1;
	}
	else if(sortFieldName.equals("EMP_TYP_NAME"))
	{
		columnNumber=2;
	}
	else if(sortFieldName.equals("EMP_STAT_ID"))
	{
		columnNumber=3;
	}
	
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
	   obj.setJndiName("SessionEmpDetailsManagerBean");
   	obj.setEnvironment();

/* 	Creating the Home and Remote Objects 	*/
		SessionEmpDetailsManagerHome empHomeObj = (SessionEmpDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionEmpDetailsManagerHome.class);
		SessionEmpDetailsManager empObj = (SessionEmpDetailsManager)PortableRemoteObject.narrow(empHomeObj.create(),SessionEmpDetailsManager.class);
	  
		Vector filter = new Vector();
		if(frm.getFormAction().equals(""))
		{  
			Filter fil[] = new Filter[0];
			if (frm.getViewValidEntries().equalsIgnoreCase("no"))
			{
		  		Filter temp = new Filter();
		  		temp.setFieldName("EMP_ISVALID");
		  		temp.setFieldValue("1");
		  		filter.add(temp);
			}
			Filter objFilter[] = new Filter[filter.size()];
  			filter.copyInto(objFilter);
	 		session.setAttribute("objFilter",objFilter);
  		}
  		else if(frm.getFormAction().equalsIgnoreCase("search"))
  		{
  		  int maxItems = Integer.parseInt(frm.getMaxItems());
		  if (frm.getViewValidEntries().equalsIgnoreCase("yes"))
		  {
		  		Filter temp = new Filter();
		  		temp.setFieldName("EMP_ISVALID");
		  		temp.setFieldValue("0");
		  		filter.add(temp);
		  }else if (frm.getViewValidEntries().equalsIgnoreCase("no"))
		  {
		  		Filter temp = new Filter();
		  		temp.setFieldName("EMP_ISVALID");
		  		temp.setFieldValue("1");
		  		filter.add(temp);
		  }
			if (!frm.getEmployeeTypeNameSearch().equalsIgnoreCase("0"))
			{
				Filter temp = new Filter();
				temp.setFieldName("EMP_TYP_ID");
				temp.setFieldValue(frm.getEmployeeTypeNameSearch());
				filter.add(temp);
			}
			if (!frm.getEmployeeStatusSearch().equalsIgnoreCase("0"))
			{
				Filter temp = new Filter();
				temp.setFieldName("EMP_STAT_ID");
				temp.setFieldValue(frm.getEmployeeStatusSearch());
				filter.add(temp);
			}
			if (!frm.getEmployeeName().equals(""))
			{
   			Filter tempFilter = new Filter();
	   		tempFilter.setFieldName("EMP_NAME");
			   tempFilter.setFieldValue(frm.getEmployeeName());
			   String specialFunction="";
			   if(frm.getEmployeeNameSearchTab().equals("0"))
			   {
				   specialFunction = "Starts With";
			   }
			   else if(frm.getEmployeeNameSearchTab().equals("1"))
			   {
				   specialFunction = "Ends With";
			   }
			   else if(frm.getEmployeeNameSearchTab().equals("2"))
			   {
				   specialFunction = "Exactly";
			   }
			   else if(frm.getEmployeeNameSearchTab().equals("3"))
			   {
				   specialFunction = "Anywhere";
			   }
			   tempFilter.setSpecialFunction(specialFunction);
			   filter.add(tempFilter);
			}
			if (!frm.getEmployeeCode().equals(""))
			{
   			Filter tempFilter = new Filter();
	   		tempFilter.setFieldName("EMP_CDE");
			   tempFilter.setFieldValue(frm.getEmployeeCode());
			   String specialFunction="";
			   if(frm.getEmployeeCodeSearch().equals("0"))
			   {
				   specialFunction = "Starts With";
			   }
			   else if(frm.getEmployeeCodeSearch().equals("1"))
			   {
				   specialFunction = "Ends With";
			   }
			   else if(frm.getEmployeeCodeSearch().equals("2"))
			   {
				   specialFunction = "Exactly";
			   }
			   else if(frm.getEmployeeCodeSearch().equals("3"))
			   {
				   specialFunction = "Anywhere";
			   }
			   tempFilter.setSpecialFunction(specialFunction);
			   filter.add(tempFilter);
			}
		  Filter objFilter[] = new Filter[filter.size()];
  		  filter.copyInto(objFilter);
  		  session.setAttribute("objFilter",objFilter);
	   }

		int maxItems = Integer.parseInt(frm.getMaxItems());
	   int pagecount = Integer.parseInt(frm.getPage());

  		Filter objFilter[] = (Filter[])session.getAttribute("objFilter");

  		pageCount = Integer.parseInt(frm.getPage());
 		HashMap empDet = empObj.getAllEmployeeDetails(objFilter, frm.getSortField(),order,((pageCount-1)*maxItems)+1,maxItems);
		if (BuildConfig.DMODE)
			System.out.println("Employee Details(HM): "+ empDet);
		Vector employeeDetails = (Vector)empDet.get("EmployeeDetails");
		pageContext.setAttribute("employeeDetails",employeeDetails);
		
		/* HashMap for loading Employee Status */
		HashMap empStatus = empObj.getAllEmployeeStatus();
		pageContext.setAttribute("empStatus",empStatus);
		
		/* HashMap for loading Employee Types */
		HashMap empTypes = empObj.getAllEmployeeTypes();
		pageContext.setAttribute("empTypes",empTypes);
		
	  	totalCount = Integer.parseInt((empDet.get("TotalRecordCount")).toString());
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
	   		e.printStackTrace();
  	}
%>	     

<html:html>
<head>
<title><bean:message key="prodacs.employee.employeelist.titleheader"/></title>
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
	   EmployeeList.submit;
	 }
 }
</script>
<script language="Javascript" type="text/Javascript">
	function viewItem(val)
	{
	   document.forms[0].action = '<bean:message key="context"/>/frmEmpEdit.do?formAction=view&id='+val;
	   document.forms[0].submit();
	}

	function editItem()
	{
		if(modifyRow('empInfoList', document.forms[0]))
		{
		   var val = getCheckedValueDetail('empInfoList', document.forms[0]);
		   document.forms[0].action = '<bean:message key="context"/>/frmEmpEdit.do?formAction=modify&id='+val;
		   document.forms[0].submit();
		}
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
	
	function makeInvalidItem()
	{
		if (validRow('empInfoList', document.forms[0]))
		{
			var queryString = '<bean:message key="context"/>/frmEmpEdit.do?formAction=makeInvalid';
			var val = getMultipleCheckedValue('empInfoList', document.forms[0]);
			var subQuery = '';
			for(var i=0;i<val.length;i++)
			{
			   subQuery = subQuery+'&ids='+val[i];
			}
			queryString = queryString + subQuery;
			document.forms[0].action = queryString;
			document.forms[0].submit();		
		}
	}
	
	function makeValidItem()
	{
		var queryString = '<bean:message key="context"/>/frmEmpEdit.do?formAction=makeValid';
		var val = getMultipleCheckedValue('empInfoList', document.forms[0]);
		var subQuery = '';
		for(var i=0;i<val.length;i++)
		{
		   subQuery = subQuery+'&ids='+val[i];
		}
		queryString = queryString + subQuery;
		document.forms[0].action = queryString;
		document.forms[0].submit();
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
		temp.formAction.value="search";
		temp.page.value = parseInt(temp.page.value,10);
		temp.submit();
	}
	
	function addItem()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/Employee/EmployeeAdd.jsp';
		temp.submit();
	}
	
	function reLoadData()
	{
		temp = document.forms[0];
		if (temp.formAction.value == "view")
		{
			<%
				String srchEmpName = request.getParameter("employeeName");
				session.setAttribute("employeeName",srchEmpName);
				String srchEmpNameTab = request.getParameter("employeeNameSearchTab");
				session.setAttribute("employeeNameSearchTab",srchEmpNameTab);
				String srchEmpCde = request.getParameter("employeeCode");
				session.setAttribute("employeeCode",srchEmpCde);
				String empCodeSrch = request.getParameter("employeeCodeSearch");
				session.setAttribute("employeeCodeSearch",empCodeSrch);
				String srchViewValid = request.getParameter("viewValidEntries");
				session.setAttribute("viewValidEntries",srchViewValid);
				String srchEmpTypeNam = request.getParameter("employeeTypeNameSearch");
				session.setAttribute("employeeTypeNameSearch",srchEmpTypeNam);
				String srchEmpStat = request.getParameter("employeeStatusSearch");
				session.setAttribute("employeeStatusSearch",srchEmpStat);
			%>
		}
		else if (temp.formAction.value == "afterView") 
		{
			if ('<%= request.getParameter("empName") %>' != 'null')
				temp.employeeName.value = '<%= request.getParameter("empName") %>';
			else
				temp.employeeName.value = "";

			if ('<%= request.getParameter("empSrchTab") %>' != 'null')
				temp.employeeNameSearchTab.value = '<%= request.getParameter("empSrchTab") %>';
			else
				temp.employeeNameSearchTab.value = "0";

			if ('<%= request.getParameter("empCde") %>' != 'null')
				temp.employeeCode.value = '<%= request.getParameter("empCde") %>';
			else
				temp.employeeCode.value = "0";

			if ('<%= request.getParameter("empCdeSrch") %>' != 'null')
				temp.employeeCodeSearch.value = '<%= request.getParameter("empCdeSrch") %>';
			else
				temp.employeeCodeSearch.value = "0";

			if ('<%= request.getParameter("viewAll") %>' != 'null')
				temp.viewValidEntries.value = '<%= request.getParameter("viewAll") %>';
			else
				temp.viewValidEntries.value = "all";

			if ('<%= request.getParameter("empType") %>' != 'null')
				temp.employeeTypeNameSearch.value = '<%= request.getParameter("empType") %>';
			else
				temp.employeeTypeNameSearch.value = "";

			if ('<%= request.getParameter("empStat") %>' != 'null')
				temp.employeeStatusSearch.value = '<%= request.getParameter("empStat") %>';
			else
				temp.employeeStatusSearch.value = "0";

		}
	}
</script>
</head>

<body onLoad = "loadDefault(); sortByTable('empInfoList',<%=columnNumber%>,'<%=order%>'); reLoadData();">
<html:form action="frmEmpInfoList">
<html:hidden property="formAction"/>
<html:hidden property="sortField"  value="<%= frm.getSortField()%>"/>
<html:hidden property="sortOrder" value="<%= frm.getSortOrder()%>"/>

<table width="100%" height="100%" cellpadding="10" cellspacing="0">
  <tr>
    <td valign="top"><table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
        <tr>
            <td><bean:message key="prodacs.employee.employeelist.header"/></td>
        </tr>
      </table>
        <br>
        <table width="100" cellspacing="0" cellpadding="0" align="right">
          <tr>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New Employee Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1004" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Modify Employee Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1004" text="[ Modify ]" classId="TopLnk" onClick="javaScript:editItem();"/>
 		<menuconfig:userRights url="#" id="mkInValid" onMouseOver="window.status='Make Employee Info InValid'; return true"  onMouseOut="window.status=''; return true" resourceId="1004" text="[ MakeInValid ]" classId="TopLnk" onClick="javaScript:makeInvalidItem();"/>
 		<menuconfig:userRights url="#" id="mkValid" onMouseOver="window.status='Make Employee Info Valid'; return true"  onMouseOut="window.status=''; return true" resourceId="1004" text="[ MakeValid ]" classId="TopLnk" style="display:none" onClick="javaScript:makeValidItem();"/>
          </tr>
        </table>
        <br>
        <table width="100%" cellspacing="0" cellpadding="0">
          <tr> 
            <td width="20" id="FilterTip">&nbsp;</td>
            <td class="FilterTitle"><a href="javascript:showFilter(document.getElementById('Filter'), document.getElementById('FilterImg'))"><img src="../images/hide.gif" width="9" height="5" border="0" align="absmiddle" id="FilterImg"><bean:message key="prodacs.common.filter"/></a></td>
            <td width="20" height="20" id="FilterEnd">&nbsp;</td>
          </tr>
        </table>
        <table width="100%" cellspacing="0" cellpadding="0" id="Filter">
          <tr> 
            <td width="320"><bean:message key="prodacs.employee.employeename"/>
            <html:text property="employeeName" styleClass="TextBox" maxlength="8"/>
            <html:select property="employeeNameSearchTab" styleClass="Combo">
               <html:option value="0">Start With</html:option>
					<html:option value="1">End With</html:option>
					<html:option value="2">Exactly</html:option>
					<html:option value="3">AnyWhere</html:option>
 				</html:select></td>
            <td width="310"><bean:message key="prodacs.employee.employeecode"/>&nbsp;&nbsp;
            <html:text property="employeeCode" styleClass="TextBox"/>
            <html:select property="employeeCodeSearch" styleClass="Combo">
               <html:option value="0">Start With</html:option>
					<html:option value="1">End With</html:option>
					<html:option value="2">Exactly</html:option>
					<html:option value="3">AnyWhere</html:option>
 				</html:select></td>
			<td><bean:message key="prodacs.common.view"/>&nbsp;&nbsp;&nbsp;
             <html:select property="viewValidEntries" styleClass="Combo">
                <html:option value="all">All</html:option>
                <html:option value="yes">InValid</html:option>
                <html:option value="no">Valid</html:option>
              </html:select></td>
          </tr>
    	  <tr>
 			<td width="270"><bean:message key="prodacs.employee.employeetype"/>&nbsp;
            <html:select property="employeeTypeNameSearch" styleClass="Combo">
               <html:option value="0">-- Employee Type --</html:option>
					 <html:options collection="empTypes" property="key" labelProperty="value"/>
 				</html:select></td>
 			<td width="250"><bean:message key="prodacs.employee.employeestatus"/>
            <html:select property="employeeStatusSearch" styleClass="Combo">
               <html:option value="0">-- Employee Status --</html:option>
					 <html:options collection="empStatus" property="key" labelProperty="value"/>
 				</html:select></td>
 			<td>
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
        
        <br>
        <table width="100%" cellpadding="0" cellspacing="0" id="empInfoList">
          <tr>
            <td width="25" class="SortHeader"><input name="CheckAll" type="checkbox" id="CheckAll" value="checkbox" onClick="checkAll(document.EmployeeList)"></td>
            <td class="SortHeader" onClick="sortTables(document.forms[0],this,'EMP_NAME')"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"> <bean:message key="prodacs.employee.employeename"/></td>
            <td class="SortHeader" onClick="sortTables(document.forms[0],this,'EMP_CDE')"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"> <bean:message key="prodacs.employee.employeecode"/></td>
            <td width="120" onClick="sortTables(document.forms[0],this,'EMP_TYP_NAME')" class="SortHeader"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"> <bean:message key="prodacs.employee.employeetype"/></td>
            <td width="200" onClick="sortTables(document.forms[0],this,'EMP_STAT_ID')" class="SortHeader"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"> <bean:message key="prodacs.employee.employeestatus"/></td>
          </tr>
          <logic:iterate id="bt1" name="employeeDetails" indexId="count">
          <tr>
      <%
	      if(count.intValue()%2 == 0) 
   	   {
     	%>
				<logic:equal name="bt1" property="emp_Isvalid" value="0"><td  class="InValid"></logic:equal>
	         <logic:equal name="bt1" property="emp_Isvalid" value="1"><td  class="TableItems"></logic:equal>	
	         
            <input name="CheckValue" type="checkbox" id="CheckValue" value='<bean:write name="bt1" property="emp_Id"/>' onClick="isSimilar(this)"></td>
            <td class="TableItems"><a href="#" onMouseOver="window.status='View Employee Info'; return true"  onMouseOut="window.status=''; return true" onClick="viewItem(this.id);" id='<bean:write name="bt1" property="emp_Id"/>'><bean:write name="bt1" property="emp_Name"/>&nbsp;</a></td>
            <td class="TableItems"><bean:write name="bt1" property="emp_Code"/>&nbsp;</td>
            <td class="TableItems"><bean:write name="bt1" property="emp_Typ_Name"/>&nbsp;</td>
            <td class="TableItems"><bean:write name="bt1" property="emp_Stat_Name"/>&nbsp;</td>
		<%
			} else
			{
		%>            
				<logic:equal name="bt1" property="emp_Isvalid" value="0"><td  class="InValid"></logic:equal>
	         <logic:equal name="bt1" property="emp_Isvalid" value="1"><td  class="TableItems2"></logic:equal>	
	         
            <input name="CheckValue" type="checkbox" id="CheckValue" value='<bean:write name="bt1" property="emp_Id"/>' onClick="isSimilar(this)"></td>
            <td class="TableItems2"><a href="#" onMouseOver="window.status='View Employee Info'; return true"  onMouseOut="window.status=''; return true" onClick="viewItem(this.id);" id='<bean:write name="bt1" property="emp_Id"/>'><bean:write name="bt1" property="emp_Name"/>&nbsp;</a></td>
            <td class="TableItems2"><bean:write name="bt1" property="emp_Code"/>&nbsp;</td>
            <td class="TableItems2"><bean:write name="bt1" property="emp_Typ_Name"/>&nbsp;</td>
            <td class="TableItems2"><bean:write name="bt1" property="emp_Stat_Name"/>&nbsp;</td>
		<%
			}
		%> 
		   </tr>
   	 	</logic:iterate>  
        </table>
			<table width="100%" cellpadding="0" cellspacing="0" id="Filter">
			<tr>&nbsp;
			</tr>
          <tr> 
            <td width="100"><bean:message key="prodacs.common.page"/> <%=pageCount%> <bean:message key="prodacs.common.pageof"/> <%=totalPages%> </td>
            <td><bean:message key="prodacs.common.maxitem"/>
              <html:radio property="maxItems" value="15"/>15 
              <html:radio property="maxItems" value="25"/>25 
              <html:radio property="maxItems" value="50"/>50 
              <html:radio property="maxItems" value="100"/>100 
              <html:button property="showPage" styleClass="Button" onclick="navigation()"> Show</html:button></td>
            <td width="190">
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
         </table></td>
  </tr>
</table>
</html:form>
</body>
</html:html>
