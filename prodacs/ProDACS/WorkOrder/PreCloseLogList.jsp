<%@ page language = "java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri = "/WEB-INF/struts-logic.tld" prefix = "logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<jsp:useBean id="frm" class="com.savantit.prodacs.presentation.tableadmin.workorder.PreCloseLogListForm" />
<jsp:setProperty name="frm" property="*" /> 

<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.savantit.prodacs.infra.beans.Filter"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<%@ page import="com.savantit.prodacs.facade.SessionWorkOrderDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionWorkOrderDetailsManager"%>
<%@ page import="java.util.StringTokenizer"%>
<useradmin:userrights resource="16"/>	
<% 
	if (BuildConfig.DMODE)
		System.out.println("PreCloseLog List");
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
	else if(sortFieldName.equals("JB_NAME"))
	{
		columnNumber = 2;
	}
	else if(sortFieldName.equals("JB_DWG_NO"))
	{
		columnNumber = 3;
	}
	else if(sortFieldName.equals("JB_RVSN_NO"))
	{
		columnNumber = 4;
	}
	else if(sortFieldName.equals("JB_MATL_TYP"))
	{
		columnNumber = 5;
	}
	else if(sortFieldName.equals("PCLOG_PCRESN"))
	{
		columnNumber = 6;
	}
	else if(sortFieldName.equals("WOJBSTAT_SNO"))
	{
		columnNumber = 7;
	}
	else if(sortFieldName.equals("PCLOG_LOGDATE"))
	{
		columnNumber = 8;
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
		
  		
		if(frm.getFormAction().equals("")|| frm.getFormAction().equals("add"))
		{  
		   frm.setFormAction("");
			Filter fil[] = new Filter[0];
	 		session.setAttribute("objFilter",fil);
  		}
  		else if(frm.getFormAction().equalsIgnoreCase("search"))
  		{
  		  int maxItems = Integer.parseInt(frm.getMaxItems());

		  /* For Valid and Invalid */
  		  Vector filter = new Vector();
		  if (frm.getListValidEntries().equalsIgnoreCase("yes"))
		  {
		  		Filter temp = new Filter();
		  		temp.setFieldName("PCLOG_ISVALID");
		  		temp.setFieldValue("0");
		  		filter.add(temp);
		  }else if (frm.getListValidEntries().equalsIgnoreCase("no"))
		  {
		  		Filter temp = new Filter();
		  		temp.setFieldName("PCLOG_ISVALID");
		  		temp.setFieldValue("1");
		  		filter.add(temp);
		  }
		  
		  /* For Searching Job Name */
			if (!frm.getJobNameText().equals(""))
			{
   			Filter tempFilter = new Filter();
	   		tempFilter.setFieldName("JB_NAME");
			   tempFilter.setFieldValue(frm.getJobNameText());
			   String specialFunction="";
			   if(frm.getPreCloseLogJobName().equals("0"))
			   {
				   specialFunction = "Starts With";
			   }
			   else if(frm.getPreCloseLogJobName().equals("1"))
			   {
				   specialFunction = "Ends With";
			   }
			   else if(frm.getPreCloseLogJobName().equals("2"))
			   {
				   specialFunction = "Exactly";
			   }
			   else if(frm.getPreCloseLogJobName().equals("3"))
			   {
				   specialFunction = "Anywhere";
			   }
			   tempFilter.setSpecialFunction(specialFunction);
			   filter.add(tempFilter);
			}

		  /* For Searching Wo.No */
			if (!frm.getWoNoText().equals(""))
			{
   			Filter tempFilter = new Filter();
	   		tempFilter.setFieldName("WO_NO");
			   tempFilter.setFieldValue(frm.getWoNoText());
			   String specialFunction="";
			   if(frm.getPreCloseLogWOHash().equals("0"))
			   {
				   specialFunction = "Starts With";
			   }
			   else if(frm.getPreCloseLogWOHash().equals("1"))
			   {
				   specialFunction = "Ends With";
			   }
			   else if(frm.getPreCloseLogWOHash().equals("2"))
			   {
				   specialFunction = "Exactly";
			   }
			   else if(frm.getPreCloseLogWOHash().equals("3"))
			   {
				   specialFunction = "Anywhere";
			   }
			   tempFilter.setSpecialFunction(specialFunction);
			   filter.add(tempFilter);
			}
			
			/* By WorkOrder Date */
			if ((!frm.getPreCloseLogFromDate().equalsIgnoreCase("")) && (!frm.getPreCloseLogToDate().equalsIgnoreCase("")))
			{
   	  			String[] month = {"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
				StringTokenizer frmDate = new StringTokenizer((frm.getPreCloseLogFromDate()).trim(),"-");
				String tempFrmDate[] = new String[frmDate.countTokens()];
				int i=0;
				while(frmDate.hasMoreTokens())
				{
					tempFrmDate[i] = frmDate.nextToken();
					if (BuildConfig.DMODE)
						System.out.println("Frm: "+tempFrmDate[i]);
					i++;
				}
				
				StringTokenizer toDate = new StringTokenizer((frm.getPreCloseLogToDate()).trim(),"-");
				String tempToDate[] = new String[toDate.countTokens()];
				i = 0;
				while (toDate.hasMoreTokens())
				{
					tempToDate[i] = toDate.nextToken();
					if (BuildConfig.DMODE)
						System.out.println("Frm: "+tempToDate[i]);
					i++;
				}
 
				String woFDate = tempFrmDate[2] + "-" + month[Integer.parseInt(tempFrmDate[1])-1] + "-" + tempFrmDate[0];
				String woTDate = tempToDate[2] + "-" + month[Integer.parseInt(tempToDate[1])-1] + "-" + tempToDate[0];
				String woDate = woFDate  + "$" + woTDate;
				if (BuildConfig.DMODE)
					System.out.println("woDate: "+ woDate);
				Filter temp = new Filter();
				temp.setFieldName("PCLOG_LOGDATE");
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

 		HashMap preCloseLog = workOrderObj.getAllPreCloseDetails(objFilter, frm.getSortField(), order, ((pageCount-1)*maxItems)+1, maxItems);
		if (BuildConfig.DMODE)
			System.out.println("preCloseLog(HM): "+preCloseLog);
		
		Vector vec_preCloseLog = (Vector) preCloseLog.get("PreCloseDetails");
		
	 	pageContext.setAttribute("vec_preCloseLog",vec_preCloseLog); 
	 	
	  	int totalCount = Integer.parseInt((preCloseLog.get("TotalRecordCount")).toString()); 
	  	
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
<title>Prodacs</title>
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
	   PreCloseLogList.submit;
	 }
 }
	var oPopup;
	function init()
	 {
		oPopup = window.createPopup();
	 }
</script>
<script language="Javascript" type="text/Javascript">
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
	
	function viewItem(val)
	{
	   document.forms[0].action = '<bean:message key="context"/>/frmPreClsLogEdit.do?formAction=view&id='+val;
	   document.forms[0].submit();
	}
	
	function addItem()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/WorkOrder/PreCloseLogAdd.jsp';
		temp.submit();
	}
	
	function reLoadData()
	{
		temp = document.forms[0];
		if (temp.formAction.value == "afterView") 
		{
			if ('<%= request.getParameter("frmDate") %>' != 'null')
				temp.preCloseLogFromDate.value = '<%= request.getParameter("frmDate") %>';
			else
				temp.preCloseLogFromDate.value = "";

			if ('<%= request.getParameter("toDate") %>' != 'null')
				temp.preCloseLogToDate.value = '<%= request.getParameter("toDate") %>';
			else
				temp.preCloseLogToDate.value = "0";

			if ('<%= request.getParameter("jbName") %>' != 'null')
				temp.jobNameText.value = '<%= request.getParameter("jbName") %>';
			else
				temp.jobNameText.value = "";

			if ('<%= request.getParameter("jbSrchTab") %>' != 'null')
				temp.preCloseLogJobName.value = '<%= request.getParameter("jbSrchTab") %>';
			else
				temp.preCloseLogJobName.value = "0";

			if ('<%= request.getParameter("woNo") %>' != 'null')
				temp.woNoText.value = '<%= request.getParameter("woNo") %>';
			else
				temp.woNoText.value = "all";

			if ('<%= request.getParameter("woNoCombo") %>' != 'null')
				temp.preCloseLogWOHash.value = '<%= request.getParameter("woNoCombo") %>';
			else
				temp.preCloseLogWOHash.value = "";
		}
	}
</script>
</head>

<body onLoad="loadDefault();sortByTable('preClsList',<%= columnNumber%>,'<%= order%>'); init();reLoadData();">
<html:form action="frmPreClsList" method="post">
<html:hidden property="formAction"/>
<html:hidden property="sortField"  value="<%= frm.getSortField()%>"/>
<html:hidden property="sortOrder" value="<%= frm.getSortOrder()%>"/>
  <table width="100%" cellspacing="0" cellpadding="10">
    <tr>
      <td><table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
          <tr>
            <td><bean:message key="prodacs.workorder.precloselog"/></td>
          </tr>
        </table>
        <br>
        <table width="100" cellspacing="0" cellpadding="0" align="right">
        <tr> 
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New PreCloseLog Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1016" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
        </tr>
        </table>
        <br> <table width="100%" cellspacing="0" cellpadding="0">
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
            <td width="200"><html:text property="preCloseLogFromDate" styleClass="TextBox" size="12" readonly="true"/>
              <img src='<bean:message key="context"/>/images/calendar.gif' width="16" height="15" align="absmiddle" onClick='show_calendar("preCloseLogFromDate",PreCloseLogList.preCloseLogFromDate.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"></td>
            <td width="50"><bean:message key="prodacs.workorder.todate"/></td>
            <td width="1">:</td>
            <td><html:text property="preCloseLogToDate" styleClass="TextBox" size="12" readonly="true"/>
              <img src='<bean:message key="context"/>/images/calendar.gif' width="16" height="15" align="absmiddle" onClick='show_calendar("preCloseLogToDate",PreCloseLogList.preCloseLogToDate.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"> 
            </td>


          </tr>
          <tr>
            <td><bean:message key="prodacs.job.jobname"/></td>
            <td>:</td>
            <td><html:text property="jobNameText" styleClass="TextBox"/>&nbsp;
            	<html:select property="preCloseLogJobName" styleClass="Combo">
  	               <html:option value="0">Start With</html:option>
						<html:option value="1">End With</html:option>
						<html:option value="2">Exactly</html:option>
						<html:option value="3">AnyWhere</html:option>
				</html:select>
			</td>

            <td><bean:message key="prodacs.workorder.workorder"/></td>
            <td>:</td>
            <td><html:text property="woNoText" styleClass="TextBox"/>&nbsp;
				<html:select property="preCloseLogWOHash" styleClass="Combo">
	               <html:option value="0">Start With</html:option>
						<html:option value="1">End With</html:option>
						<html:option value="2">Exactly</html:option>
						<html:option value="3">AnyWhere</html:option>
				</html:select>
			</td>
			<td>
				<html:button styleClass="Button" property="search" onclick="navigation()" value="Go"/>
			</td>
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
        <table width="100%" cellpadding="0" cellspacing="0" id="preClsList">
          <tr> 
            <td width="25" class="SortHeader"><input type="checkbox" name="CheckAll" value="checkbox" onClick="checkAll(document.PreCloseLogList)"></td>
            <td onClick="sortTables(document.forms[0],this,'WO_NO')" width="60" class="SortHeader"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"> <bean:message key="prodacs.workorder.workorderno"/></td>
            <td onClick="sortTables(document.forms[0],this,'JB_NAME')" width="120" class="SortHeader"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"> <bean:message key="prodacs.job.jobname"/></td>
            <td onClick="sortTables(document.forms[0],this,'JB_DWG_NO')" width="80" class="SortHeader"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"> <bean:message key="prodacs.job.drawing"/></td>
            <td onClick="sortTables(document.forms[0],this,'JB_RVSN_NO')" width="80" class="SortHeader"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"> <bean:message key="prodacs.job.revision"/></td>
            <td onClick="sortTables(document.forms[0],this,'JB_MATL_TYP')" width="80" class="SortHeader"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"> <bean:message key="prodacs.job.materialtype"/></td>
            <td onClick="sortTables(document.forms[0],this,'PCLOG_PCRESN')" class="SortHeader"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"> <bean:message key="prodacs.workorder.reason"/></td>
            <td onClick="sortTables(document.forms[0],this,'WOJBSTAT_SNO')" width="60" class="SortHeader"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"> <bean:message key="prodacs.workorder.jobqtystartsno"/></td>
            <td onClick="sortTables(document.forms[0],this,'PCLOG_LOGDATE')" width="80" class="SortHeader"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"> <bean:message key="prodacs.job.date"/></td>
          </tr>
          <logic:iterate id="bt1" name="vec_preCloseLog" indexId="count">
          <tr>
      <%
	      if(count.intValue()%2 == 0) 
   	   {
     	%>
		<logic:equal name="bt1" property="preCloseIsValid" value="0"><td  class="InValid"></logic:equal>
	      <logic:equal name="bt1" property="preCloseIsValid" value="1"><td  class="TableItems"></logic:equal>	
	         
            <input name="CheckValue" type="checkbox" id="CheckValue" value='<bean:write name="bt1" property="precloseLogId"/>'></td>
            <td class="TableItems"><a href="#" onMouseOver="window.status='View PreCloseLog Info'; return true"  onMouseOut="window.status=''; return true" id='<bean:write name="bt1" property="precloseLogId"/>' onclick="viewItem(this.id);"><bean:write name="bt1" property="workOrderNo"/>&nbsp;</a></td>
            <td class="TableItems"><bean:write name="bt1" property="jbName"/>&nbsp;</td>
            <td class="TableItems"><bean:write name="bt1" property="jbDwgNo"/>&nbsp;</td>
            <td class="TableItems"><bean:write name="bt1" property="jbRvsnNo"/>&nbsp;</td>
            <td class="TableItems"><bean:write name="bt1" property="jbMatlTyp"/>&nbsp;</td>
            <td class="TableItems"><bean:write name="bt1" property="preCloseReason"/>&nbsp;</td>
            <td class="TableItems"><bean:write name="bt1" property="workOrderJbStatSno"/>&nbsp;</td>
            <td class="TableItems"><bean:define id="preCloseLogDate" name="bt1" property="preCloseLogDate"/><%= preCloseLogDate.toString().substring(0,10) %>&nbsp;</td>
		<%
			} else
			{
		%>            
		<logic:equal name="bt1" property="preCloseIsValid" value="0"><td  class="InValid"></logic:equal>
	      <logic:equal name="bt1" property="preCloseIsValid" value="1"><td  class="TableItems2"></logic:equal>	
	         
            <input name="CheckValue" type="checkbox" id="CheckValue" value='<bean:write name="bt1" property="precloseLogId"/>'></td>
            <td class="TableItems2"><a href="#" onMouseOver="window.status='View PreCloseLog Info'; return true"  onMouseOut="window.status=''; return true" id='<bean:write name="bt1" property="precloseLogId"/>' onclick="viewItem(this.id);"><bean:write name="bt1" property="workOrderNo"/>&nbsp;</a></td>
            <td class="TableItems2"><bean:write name="bt1" property="jbName"/>&nbsp;</td>
            <td class="TableItems2"><bean:write name="bt1" property="jbDwgNo"/>&nbsp;</td>
            <td class="TableItems2"><bean:write name="bt1" property="jbRvsnNo"/>&nbsp;</td>
            <td class="TableItems2"><bean:write name="bt1" property="jbMatlTyp"/>&nbsp;</td>
            <td class="TableItems2"><bean:write name="bt1" property="preCloseReason"/>&nbsp;</td>
            <td class="TableItems2"><bean:write name="bt1" property="workOrderJbStatSno"/>&nbsp;</td>
            <td class="TableItems2"><bean:define id="preCloseLogDate1" name="bt1" property="preCloseLogDate"/><%= preCloseLogDate1.toString().substring(0,10) %>&nbsp;</td>
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
         </table></td>

    </tr>
  </table>
</html:form>
</body>
</html:html>
