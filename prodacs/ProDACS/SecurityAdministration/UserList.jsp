<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<%@ page import="com.savantit.prodacs.facade.SessionSecurityAdminManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionSecurityAdminManager"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject"%>
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.savantit.prodacs.infra.beans.Filter"%>
<%@ page import="com.savantit.prodacs.businessimplementation.securityadmin.ContactDetails" %>
<jsp:useBean id="frm" class="com.savantit.prodacs.presentation.tableadmin.securityadministration.UserListForm" />
<jsp:setProperty name="frm" property="*" />

<useradmin:userrights resource="28"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("User List");
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	int totalPages = 0;
	int pageCount = 1 ;
	int columnNumber = 1;
	
	ContactDetails obj_Contact_Details = new ContactDetails();
	boolean order = true;
	String sortFieldName = frm.getSortField();
	if (BuildConfig.DMODE)
		System.out.println(sortFieldName);
	
	/* Setting the SortFields */
	if(sortFieldName.equals("USER_NAME"))
	{
		columnNumber = 1;
	}
	else if(sortFieldName.equals("CNTCT_FNAME"))
	{
		columnNumber = 2;
	}
	else if(sortFieldName.equals("CNTCT_LNAME"))
	{
		columnNumber = 3;
	}
	else if(sortFieldName.equals("CNTCT_COMPANY"))
	{
		columnNumber = 4;
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
		/* Setting the JNDI name and Environment */
		obj.setJndiName("SessionSecurityAdminManager");
		obj.setEnvironment();
		
		/* Creation of Home and Remote Objects */
		SessionSecurityAdminManagerHome secAdminHome = (SessionSecurityAdminManagerHome) PortableRemoteObject.narrow(obj.getHome(),SessionSecurityAdminManagerHome.class);
		SessionSecurityAdminManager secAdminObj = (SessionSecurityAdminManager) PortableRemoteObject.narrow(secAdminHome.create(),com.savantit.prodacs.facade.SessionSecurityAdminManager.class);
		
 		if (BuildConfig.DMODE)
 			System.out.println("formAction:"+frm.getFormAction());
		if(frm.getFormAction().equals(""))
		{  
			Filter fil[] = new Filter[0];
	 		session.setAttribute("objFilter",fil);
  		}
  		else if(frm.getFormAction().equalsIgnoreCase("search"))
  		{
  		  int maxItems = Integer.parseInt(frm.getMaxItems());
  		  
  		  Vector filter = new Vector();
		  if (frm.getViewValidEntries().equalsIgnoreCase("yes"))
		  {
		  		Filter temp = new Filter();
		  		temp.setFieldName("USER_ISVALID");
		  		temp.setFieldValue("0");
		  		filter.add(temp);
		  }else if (frm.getViewValidEntries().equalsIgnoreCase("no"))
		  {
		  		Filter temp = new Filter();
		  		temp.setFieldName("USER_ISVALID");
		  		temp.setFieldValue("1");
		  		filter.add(temp);
		  }
			/* For UserName */
			if (!frm.getUserId().equals(""))
			{
	   			Filter tempFilter = new Filter();
		   		tempFilter.setFieldName("USER_NAME");
				tempFilter.setFieldValue(frm.getUserId());
				String specialFunction="";
				if(frm.getUidSelection().equals("0"))
				{
					specialFunction = "Starts With";
				}
				else if(frm.getUidSelection().equals("1"))
				{
					specialFunction = "Ends With";
				}
				else if(frm.getUidSelection().equals("2"))
				{
					specialFunction = "Exactly";
				}
				else if(frm.getUidSelection().equals("3"))
				{
					specialFunction = "Anywhere";
				}
				tempFilter.setSpecialFunction(specialFunction);
				filter.add(tempFilter);
			}
			/* For FirstName */
			if (!frm.getFirstName().equals(""))
			{
	   			Filter tempFilter = new Filter();
		   		tempFilter.setFieldName("CNTCT_FNAME");
				tempFilter.setFieldValue(frm.getFirstName());
				String specialFunction="";
				if(frm.getFirstNameSelect().equals("0"))
				{
					specialFunction = "Starts With";
				}
				else if(frm.getFirstNameSelect().equals("1"))
				{
					specialFunction = "Ends With";
				}
				else if(frm.getFirstNameSelect().equals("2"))
				{
					specialFunction = "Exactly";
				}
				else if(frm.getFirstNameSelect().equals("3"))
				{
					specialFunction = "Anywhere";
				}
				tempFilter.setSpecialFunction(specialFunction);
				filter.add(tempFilter);
			}
			/* For Company */
			if (!frm.getCompany().equals(""))
			{
	   			Filter tempFilter = new Filter();
		   		tempFilter.setFieldName("CNTCT_COMPANY");
				tempFilter.setFieldValue(frm.getCompany());
				String specialFunction="";
				if(frm.getCompanySelect().equals("0"))
				{
					specialFunction = "Starts With";
				}
				else if(frm.getCompanySelect().equals("1"))
				{
					specialFunction = "Ends With";
				}
				else if(frm.getCompanySelect().equals("2"))
				{
					specialFunction = "Exactly";
				}
				else if(frm.getCompanySelect().equals("3"))
				{
					specialFunction = "Anywhere";
				}
				tempFilter.setSpecialFunction(specialFunction);
				filter.add(tempFilter);
			}
		if (BuildConfig.DMODE)
			System.out.println("filter.size() :"+filter.size());
		Filter objFilter[] = new Filter[filter.size()];
		
		filter.copyInto(objFilter);
		session.setAttribute("objFilter",objFilter);
	   }
	   
		int maxItems = Integer.parseInt(frm.getMaxItems());
	   int pagecount = Integer.parseInt(frm.getPage());
		
  		Filter objFilter[] = (Filter[])session.getAttribute("objFilter");
  		pageCount = Integer.parseInt(frm.getPage());

		/* HashMap for loading User Details */
		HashMap userDetails = secAdminObj.getAllUsersDetails(objFilter, frm.getSortField(), order, ((pageCount-1)*maxItems)+1, maxItems);
		if (BuildConfig.DMODE)
			System.out.println("User Details(HM) :"+userDetails);
		
		Vector userDet = (Vector) userDetails.get("UserDetails");
		pageContext.setAttribute("userDet",userDet);
	 	
	  	int totalCount = Integer.parseInt((userDetails.get("TotalRecordCount")).toString()); 
	  	
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
	   frmUserInfoList.submit;
	 }
 }
</script>
<script>

	function addUser()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/SecurityAdministration/UserAdd.jsp';
		temp.submit();
	}
	
	function viewUser(val)
	{
		document.forms[0].action='<bean:message key="context"/>/frmUserEdit.do?formAction=view&id='+val;
		document.forms[0].submit();
	}
	
	function modifyUser()
	{
		if (modifyRow('userInfoList',document.forms[0]))
		{
			var val = getCheckedValueDetail('userInfoList',document.forms[0]);
			document.forms[0].action='<bean:message key="context"/>/frmUserEdit.do?formAction=modify&id='+val;
			document.forms[0].submit();
		}
	}
	
	function changePage()
	{
		temp = document.forms[0];
		temp.formAction.value = "search";
		temp.page.value = parseInt(temp.page.value,10);
		temp.submit();
	}
	
	function navigation()
	{
		temp = document.forms[0];
		temp.formAction.value="search";
		temp.page.value = 1;
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

	function makeValid()
	{
		var queryString = '<bean:message key="context"/>/frmUserEdit.do?formAction=makeValid';
		var val = getMultipleCheckedValue('userInfoList', document.forms[0]);
		var subQuery = '';
		for(var i=0;i<val.length;i++)
		{
		   subQuery = subQuery+'&ids='+val[i];
		}
		queryString = queryString + subQuery;
		document.forms[0].action = queryString;
		document.forms[0].submit();
	}
	
	function makeInValid()
	{
		if (validRow('userInfoList', document.forms[0]))
		{
			var queryString = '<bean:message key="context"/>/frmUserEdit.do?formAction=makeInvalid';
			var val = getMultipleCheckedValue('userInfoList', document.forms[0]);
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

	function deleteUser()
	{
		if (deleteRow('userInfoList', document.forms[0]))
		{
			var queryString = '<bean:message key="context"/>/frmUserEdit.do?formAction=delete';
			var val = getMultipleCheckedValue('userInfoList', document.forms[0]);
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
	
	function reLoadData()
	{
		temp = document.forms[0];
		if (temp.formAction.value == "view")
		{
			<%
				String srchUser = request.getParameter("userId");
				session.setAttribute("userId",srchUser);
				String srchUserCombo = request.getParameter("uidSelection");
				session.setAttribute("uidSelection",srchUserCombo);
				String srchFname = request.getParameter("firstName");
				session.setAttribute("firstName",srchFname);
				String srchFnameCombo = request.getParameter("firstNameSelect");
				session.setAttribute("firstNameSelect",srchFnameCombo);
				String srchComopany = request.getParameter("company");
				session.setAttribute("company",srchComopany);
				String srchCompanyCbo = request.getParameter("companySelect");
				session.setAttribute("companySelect",srchCompanyCbo);
				String viewAll = request.getParameter("viewValidEntries");
				session.setAttribute("viewValidEntries",viewAll);
			%>
		}
		else if (temp.formAction.value == "afterView") 
		{
			if ('<%= request.getParameter("uname") %>' != 'null')
				temp.userId.value = '<%= request.getParameter("uname") %>';
			else
				temp.userId.value = "";

			if ('<%= request.getParameter("uNameSelect") %>' != 'null')
				temp.uidSelection.value = '<%= request.getParameter("uNameSelect") %>';
			else
				temp.uidSelection.value = "0";

			if ('<%= request.getParameter("fName") %>' != 'null')
				temp.firstName.value = '<%= request.getParameter("fName") %>';
			else
				temp.firstName.value = "";

			if ('<%= request.getParameter("fNameSelect") %>' != 'null')
				temp.firstNameSelect.value = '<%= request.getParameter("fNameSelect") %>';
			else
				temp.firstNameSelect.value = "0";

			if ('<%= request.getParameter("company") %>' != 'null')
				temp.company.value = '<%= request.getParameter("company") %>';
			else
				temp.company.value = "";

			if ('<%= request.getParameter("companySelect") %>' != 'null')
				temp.companySelect.value = '<%= request.getParameter("companySelect") %>';
			else
				temp.companySelect.value = "0";

			if ('<%= request.getParameter("viewAll") %>' != 'null')
				temp.viewValidEntries.value = '<%= request.getParameter("viewAll") %>';
			else
				temp.viewValidEntries.value = "all";
		}
	}
	
</script>
</head>

<body onLoad="sortByTable('userInfoList',<%=columnNumber%>,'<%=order%>');reLoadData();">
<html:form action="frmUserInfoList">
<html:hidden property="formAction"/>
<html:hidden property="sortField"  value="<%= frm.getSortField()%>"/>
<html:hidden property="sortOrder" value="<%= frm.getSortOrder()%>"/>

<table width="100%" height="100%" cellpadding="10" cellspacing="0">
<tr>
	<td valign="top">
	<table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
	<tr>
		<td><bean:message key="prodacs.useradministration.usersinfo"/></td>
	</tr>
	</table><br>
	<table width="100" cellspacing="0" cellpadding="0" align="right">
	<tr> 
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New User Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1028" text="[ Add ]" classId="TopLnk" onClick="javaScript:addUser();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Modify User Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1028" text="[ Modify ]" classId="TopLnk" onClick="javaScript:modifyUser();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Delete User Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1028" text="[ Delete ]" classId="TopLnk" onClick="javaScript:deleteUser();"/>
 		<menuconfig:userRights url="#" id="mkInValid" onMouseOver="window.status='Make User Info InValid'; return true"  onMouseOut="window.status=''; return true" resourceId="1028" text="[ MakeInValid ]" classId="TopLnk" onClick="javaScript:makeInValid();"/>
 		<menuconfig:userRights url="#" id="mkValid" onMouseOver="window.status='Make User Info Valid'; return true"  onMouseOut="window.status=''; return true" resourceId="1028" text="[ MakeValid ]" classId="TopLnk" style="display:none" onClick="javaScript:makeValid();"/>
	</tr>
	</table><br>
	<table width="100%" cellspacing="0" cellpadding="0">
	<tr> 
		<td width="20" id="FilterTip">&nbsp;</td>
		<td class="FilterTitle"><a href="javascript:showFilter(document.getElementById('Filter'), document.getElementById('FilterImg'))"><img src='<bean:message key="context"/>/images/hide.gif' width="9" height="5" border="0" align="absmiddle" id="FilterImg"><bean:message key="prodacs.common.filter"/></a></td>
		<td width="20" height="20" id="FilterEnd">&nbsp;</td>
	</tr>
	</table>
	<table width="100%" cellspacing="0" cellpadding="0" id="Filter">
	<tr> 
		<td width="80"><bean:message key="prodacs.useradministration.username"/></td>
		<td width="1">:</td>
		<td width="220"><html:text property="userId" styleClass="TextBox"/>
				<html:select property="uidSelection" styleClass="Combo">
				<html:option value="0">Starts With</html:option>
				<html:option value="1">Ends With</html:option>
				<html:option value="2">Exactly</html:option>
				<html:option value="3">Anywhere</html:option>
				</html:select></td>
		<td width="75"><bean:message key="prodacs.useradministration.firstname"/></td>
		<td width="1">:</td>
		<td colspan="4"><html:text property="firstName" styleClass="TextBox" size="20"/>
				<html:select property="firstNameSelect" styleClass="Combo">
				<html:option value="0">Starts With</html:option>
				<html:option value="1">Ends With</html:option>
				<html:option value="2">Exactly</html:option>
				<html:option value="3">Anywhere</html:option>
				</html:select></td>
	</tr>
	<tr> 
		<td><bean:message key="prodacs.useradministration.company"/></td>
		<td>:</td>
		<td><html:text property="company" styleClass="TextBox"/>
			<html:select property="companySelect" styleClass="Combo">
			<html:option value="0">Starts With</html:option>
			<html:option value="1">Ends With</html:option>
			<html:option value="2">Exactly</html:option>
			<html:option value="3">Anywhere</html:option>
			</html:select></td>
		<td width="105"><bean:message key="prodacs.common.view"/></td>
		<td>:</td>
		<td><html:select property="viewValidEntries" styleClass="Combo">
			<html:option value="all">All</html:option>
			<html:option value="yes">InValid</html:option>
			<html:option value="no">Valid</html:option>
			</html:select>
			<html:button property="go" styleClass="Button" onclick="navigation()" value="Go"/></td>
	</tr>
	</table>
      <font class="message">
	 	<html:messages id="messageid" message="true">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="messageid" />
	   	</html:messages>
	</font>
	<table>
	<tr>
		<td colspan='2'> <font size="1px" face="Verdana"><html:errors/></font></td>
	</tr>
	</table><br>	
	<table width="100%" cellpadding="0" cellspacing="0" id="userInfoList">
	<tr> 
		<td width="25" class="SortHeader">
		<input name="CheckAll" type="checkbox" id="CheckAll" value="checkbox" onClick="checkAll(document.UserList)"></td>
		<td width="150" class="SortHeader" onClick="sortTables(document.forms[0],this,'USER_NAME')"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"><bean:message key="prodacs.useradministration.username"/></td>
		<td class="SortHeader" onClick="sortTables(document.forms[0],this,'CNTCT_FNAME')"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"><bean:message key="prodacs.useradministration.firstname"/></td>
		<td width="150" class="SortHeader" onClick="sortTables(document.forms[0],this,'CNTCT_LNAME')"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"><bean:message key="prodacs.useradministration.lastname"/></td>
		<td width="100" class="SortHeader" onClick="sortTables(document.forms[0],this,'CNTCT_COMPANY')"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"><bean:message key="prodacs.useradministration.company"/></td>
	</tr>
	<logic:iterate id="bt1" name="userDet" indexId="count">
	<bean:define id="bt2" name="bt1" property="obj_Contact_Details"/>
	<tr>
      <%
	      if(count.intValue()%2 == 0) 
   	   {
     	%>
		<logic:equal name="bt1" property="user_IsValid" value="false"><td  class="InValid"></logic:equal>
		<logic:equal name="bt1" property="user_IsValid" value="true"><td  class="TableItems"></logic:equal>			
		<input name="CheckValue" type="checkbox" id="CheckValue" value='<bean:write name="bt1" property="user_Name"/>' onClick="isSimilar(this)"></td>
		<td class="TableItems"><a href="#" onMouseOver="window.status='View User Info'; return true"  onMouseOut="window.status=''; return true" id='<bean:write name="bt1" property="user_Name"/>' onClick="viewUser(this.id);"><bean:write name="bt1" property="user_Name"/>&nbsp;</a></td>
		<td class="TableItems">&nbsp;<bean:write name="bt2" property="cntct_Fname"/></td>
		<td class="TableItems">&nbsp;<bean:write name="bt2" property="cntct_Lname"/></td>
		<td class="TableItems">&nbsp;<bean:write name="bt2" property="companyName"/></td>
	<%
		} else
		{
	%>
		<logic:equal name="bt1" property="user_IsValid" value="false"><td  class="InValid"></logic:equal>
		<logic:equal name="bt1" property="user_IsValid" value="true"><td  class="TableItems2"></logic:equal>		
		<input name="CheckValue" type="checkbox" id="CheckValue" value='<bean:write name="bt1" property="user_Name"/>' onClick="isSimilar(this)"></td>
		<td class="TableItems2"><a href="#" onMouseOver="window.status='View User Info'; return true"  onMouseOut="window.status=''; return true" id='<bean:write name="bt1" property="user_Name"/>' onClick="viewUser(this.id);"><bean:write name="bt1" property="user_Name"/>&nbsp;</a></td>
		<td class="TableItems2">&nbsp;<bean:write name="bt2" property="cntct_Fname"/></td>
		<td class="TableItems2">&nbsp;<bean:write name="bt2" property="cntct_Lname"/></td>
		<td class="TableItems2">&nbsp;<bean:write name="bt2" property="companyName"/></td>
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
         </table></td>
	
</tr>
</table>
</html:form>
</body>
</html:html>
