<%@ page language = "java" %>
<%@ taglib uri = "/WEB-INF/struts-html.tld" prefix = "html" %>
<%@ taglib uri = "/WEB-INF/struts-bean.tld" prefix = "bean" %>
<%@ taglib uri = "/WEB-INF/struts-logic.tld" prefix = "logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<jsp:useBean id="frm" class="com.savantit.prodacs.presentation.tableadmin.workcalendar.BaseCalendarListForm" />
<jsp:setProperty name="frm" property="*" /> 
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.savantit.prodacs.infra.beans.Filter"%>

<%@ page import="com.savantit.prodacs.facade.SessionWorkCalendarDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionWorkCalendarDetailsManager"%>
<useradmin:userrights resource="9"/>
<% 
	if(BuildConfig.DMODE)
		System.out.println("Shift Definition List");
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	int totalPages = 0;
	int pageCount = 1 ;
	int columnNumber = 1;
	String[] dayIndices = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
	
	boolean order = true;
	String sortFieldName = frm.getSortField();
	if(BuildConfig.DMODE)
		System.out.println(sortFieldName);
	
	/* Setting the SortFields */
	if(sortFieldName.equals("BASE_CAL_NAME"))
	{
		columnNumber=1;
	}
	else if(sortFieldName.equals("BASE_CAL_STARTDAY"))
	{
		columnNumber=2;
	}
	else if(sortFieldName.equals("BASE_CAL_ENDDAY"))
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
	if(BuildConfig.DMODE)
	{
		System.out.println("SortOrder: "+frm.getSortOrder());
		System.out.println("Order: "+order);
	}
	try
   {
/* 	Setting the JNDI name and Environment 	*/
	   obj.setJndiName("SessionWorkCalendarDetailsManagerBean");
   	obj.setEnvironment();

/* 	Creating the Home and Remote Objects 	*/
		SessionWorkCalendarDetailsManagerHome baseCalHomeObj = (SessionWorkCalendarDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionWorkCalendarDetailsManagerHome.class);
		SessionWorkCalendarDetailsManager baseCalDefObj = (SessionWorkCalendarDetailsManager)PortableRemoteObject.narrow(baseCalHomeObj.create(),SessionWorkCalendarDetailsManager.class);
 
		if(frm.getFormAction().equals(""))
		{  
			Filter fil[] = new Filter[0];
	 		session.setAttribute("objFilter",fil);
  		}
  		else if(frm.getFormAction().equalsIgnoreCase("search"))
  		{
  		  int maxItems = Integer.parseInt(frm.getMaxItems());
  		  
  		  Vector filter = new Vector();
		  if (frm.getListValidEntries().equalsIgnoreCase("yes"))
		  {
		  		Filter temp = new Filter();
		  		temp.setFieldName("BASE_CAL_ISVALID");
		  		temp.setFieldValue("0");
		  		filter.add(temp);
		  }else if (frm.getListValidEntries().equalsIgnoreCase("no"))
		  {
		  		Filter temp = new Filter();
		  		temp.setFieldName("BASE_CAL_ISVALID");
		  		temp.setFieldValue("1");
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

 		HashMap baseCalDetails = baseCalDefObj.getAllBaseCalendarDetails(objFilter, frm.getSortField(), order, ((pageCount-1)*maxItems)+1, maxItems);
		if(BuildConfig.DMODE)
			System.out.println("BaseCalendarDetails(HM): "+baseCalDetails);
		
		Vector vec_BaseCal = (Vector) baseCalDetails.get("BaseCalendarDetails");
		
	 	pageContext.setAttribute("vec_BaseCal",vec_BaseCal); 
	 	
	  	int totalCount = Integer.parseInt((baseCalDetails.get("TotalRecordCount")).toString()); 
	  	
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
	   if(BuildConfig.DMODE)
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
	   BaseCalendarList.submit;
	 }
 }
</script>
<script language="Javascript" type="text/Javascript">

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
	
	function viewItem(val)
	{
	   document.forms[0].action = '<bean:message key="context"/>/frmBaseClndrEdit.do?formAction=view&id='+val;
	   document.forms[0].submit();
	}
	
	function editItem()
	{
		if(modifyRow('tblBaseClndr', document.forms[0]))
		{
		   var val = getCheckedValueDetail('tblBaseClndr', document.forms[0]);
		   document.forms[0].action = '<bean:message key="context"/>/frmBaseClndrEdit.do?formAction=modify&id='+val;
		   document.forms[0].submit();
		}
	}
	
	function deleteItem()
	{
		temp = document.forms[0];
		var obj = document.getElementById('tblBaseClndr');

		if (deleteRow('tblBaseClndr', document.forms[0]))
		{
			var queryString = '<bean:message key="context"/>/frmBaseClndrEdit.do?formAction=delete';
			var val = getMultipleCheckedValue('tblBaseClndr', document.forms[0]);
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
	
	function makeInvalidItem()
	{
		if (validRow('tblBaseClndr', document.forms[0]))
		{
			var queryString = '<bean:message key="context"/>/frmBaseClndrEdit.do?formAction=makeInvalid';
			var val = getMultipleCheckedValue('tblBaseClndr', document.forms[0]);
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
		var queryString = '<bean:message key="context"/>/frmBaseClndrEdit.do?formAction=makeValid';
		var val = getMultipleCheckedValue('tblBaseClndr', document.forms[0]);
		var subQuery = '';
		for(var i=0;i<val.length;i++)
		{
		   subQuery = subQuery+'&ids='+val[i];
		}
		queryString = queryString + subQuery;
		document.forms[0].action = queryString;
		document.forms[0].submit();
	}
	
	function addItem()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/WorkCalender/BaseCalendarAdd.jsp';
		temp.submit();
	}
	
	function reLoadData()
	{
		temp = document.forms[0];
		if (temp.formAction.value == "view")
		{
			<%
				String viewAll = request.getParameter("listValidEntries");
				session.setAttribute("listValidEntries",viewAll);
			%>
		}
		else if (temp.formAction.value == "afterView") 
		{
			if ('<%= request.getParameter("viewAll") %>' != 'null')
				temp.listValidEntries.value = '<%= request.getParameter("viewAll") %>';
			else
				temp.listValidEntries.value = "all";
		}
	}

</script>
</head>

<body onLoad="loadDefault();sortByTable('tblBaseClndr',<%= columnNumber%>,'<%= order%>'); reLoadData();">
<html:form action="frmBaseClndrList">
<html:hidden property="formAction"/>
<html:hidden property="sortField"  value="<%= frm.getSortField()%>"/>
<html:hidden property="sortOrder" value="<%= frm.getSortOrder()%>"/>
  <table width="100%" height="100%" cellpadding="10" cellspacing="0">
    <tr>
      <td valign="top"><table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
          <tr>
            <td><bean:message key="prodacs.workcalendar.basecalendar.header"/></td>
          </tr>
        </table>
        <br>
        <table width="100" cellspacing="0" cellpadding="0" align="right">
        <tr> 
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New BaseCalendar Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1009" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Modify BaseCalendar Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1009" text="[ Modify ]" classId="TopLnk" onClick="javaScript:editItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Delete BaseCalendar Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1009" text="[ Delete ]" classId="TopLnk" onClick="javaScript:deleteItem();"/>
 		<menuconfig:userRights url="#" id="mkInValid" onMouseOver="window.status='Make BaseCalendar Info InValid'; return true"  onMouseOut="window.status=''; return true" resourceId="1009" text="[ MakeInValid ]" classId="TopLnk" onClick="javaScript:makeInvalidItem();"/>
 		<menuconfig:userRights url="#" id="mkValid" onMouseOver="window.status='Make BaseCalendar Info Valid'; return true"  onMouseOut="window.status=''; return true" resourceId="1009" text="[ MakeValid ]" classId="TopLnk" style="display:none" onClick="javaScript:makeValidItem();"/>
        </tr>
        </table>
        <br>
        <table width="100%" cellspacing="0" cellpadding="0">
        <tr> 
            <td width="20" id="FilterTip">&nbsp;</td>
            <td class="FilterTitle"><a href="javascript:showFilter(document.getElementById('Filter'), document.getElementById('FilterImg'))"><img src='<bean:message key="context"/>/images/hide.gif' width="9" height="5" border="0" align="absmiddle" id="FilterImg"> <bean:message key="prodacs.common.filter"/></a></td>
            <td width="20" height="20" id="FilterEnd">&nbsp;</td>
          </tr>
        </table>
        <table width="100%" cellspacing="0" cellpadding="0" id="Filter">
          <tr> 
            <td>
              <bean:message key="prodacs.common.view"/>
              <html:select property="listValidEntries" styleClass="Combo">
                <html:option value="all">All</html:option>
                <html:option value="yes">InValid</html:option>
                <html:option value="no">Valid</html:option>
              </html:select> &nbsp;
              
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
        <table width="100%" cellpadding="0" cellspacing="0" id="tblBaseClndr">
          <tr> 
            <td width="25" class="SortHeader"><input name="CheckAll" type="checkbox" id="CheckAll" value="checkbox" onClick="checkAll(document.BaseCalendarList)"></td>
            <td class="SortHeader" onClick="sortTables(document.forms[0],this,'BASE_CAL_NAME')"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"> 
              <bean:message key="prodacs.workcalendar.basecalendar.basecalendarname"/></td>
            <td width="100" class="SortHeader" onClick="sortTables(document.forms[0],this,'BASE_CAL_STARTDAY')"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"> 
              <bean:message key="prodacs.workcalendar.basecalendar.startday"/></td>
            <td width="100" class="SortHeader" onClick="sortTables(document.forms[0],this,'BASE_CAL_ENDDAY')"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"> 
              <bean:message key="prodacs.workcalendar.basecalendar.endday"/></td>
          </tr>
          <logic:iterate id="bt1" name="vec_BaseCal" indexId="count">
          <tr>
      <%
	      if(count.intValue()%2 == 0) 
   	   {
     	%>
				<logic:equal name="bt1" property="bcIsValid" value="0"><td  class="InValid"></logic:equal>
	         <logic:equal name="bt1" property="bcIsValid" value="1"><td  class="TableItems"></logic:equal>	
	         
            <input name="CheckValue" type="checkbox" id="CheckValue" value='<bean:write name="bt1" property="bcId"/>' onClick="isSimilar(this)"></td>
            <td class="TableItems"><a href="#" onMouseOver="window.status='View BaseCalendar Info'; return true"  onMouseOut="window.status=''; return true" id='<bean:write name="bt1" property="bcId"/>' onClick="viewItem(this.id)"><bean:write name="bt1" property="bcName"/>&nbsp;</td>
            <td class="TableItems"><bean:define id="stDay" name="bt1" property="bcStartDay"/><%= dayIndices[Integer.parseInt(stDay.toString())-1] %>&nbsp;</td>
            <td class="TableItems"><bean:define id="endDay" name="bt1" property="bcEndDay"/><%= dayIndices[Integer.parseInt(endDay.toString())-1] %>&nbsp;</td>
		<%
			} else
			{
		%>            
				<logic:equal name="bt1" property="bcIsValid" value="0"><td  class="InValid"></logic:equal>
	         <logic:equal name="bt1" property="bcIsValid" value="1"><td  class="TableItems2"></logic:equal>	
	         
            <input name="CheckValue" type="checkbox" id="CheckValue" value='<bean:write name="bt1" property="bcId"/>' onClick="isSimilar(this)"></td>
            <td class="TableItems2"><a href="#" onMouseOver="window.status='View BaseCalendar Info'; return true"  onMouseOut="window.status=''; return true" id='<bean:write name="bt1" property="bcId"/>' onClick="viewItem(this.id)"><bean:write name="bt1" property="bcName"/>&nbsp;</td>
            <td class="TableItems2"><bean:define id="stDay1" name="bt1" property="bcStartDay"/><%= dayIndices[Integer.parseInt(stDay1.toString())-1] %>&nbsp;</td>
            <td class="TableItems2"><bean:define id="endDay1" name="bt1" property="bcEndDay"/><%= dayIndices[Integer.parseInt(endDay1.toString())-1] %>&nbsp;</td>
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
