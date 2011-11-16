<%@ page language = "java" %>

<%@ taglib uri = "/WEB-INF/struts-html.tld" prefix = "html" %>
<%@ taglib uri = "/WEB-INF/struts-bean.tld" prefix = "bean" %>
<%@ taglib uri = "/WEB-INF/struts-logic.tld" prefix = "logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<jsp:useBean id="frm" class="com.savantit.prodacs.presentation.tableadmin.jobmaster.JobMasterListForm" />
<jsp:setProperty name="frm" property="*" /> 

<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.savantit.prodacs.infra.beans.Filter"%>
<%@ page import="com.savantit.prodacs.facade.SessionJobDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionJobDetailsManager"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<useradmin:userrights resource="11"/>
<% 
	if (BuildConfig.DMODE)
		System.out.println("Job Master List");
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	int totalPages = 0;
	int pageCount = 1 ;
	int columnNumber = 1;
	int totalCount = 0;
	
	boolean order = true;
	String sortFieldName = frm.getSortField();
	if (BuildConfig.DMODE)
		System.out.println("SortFieldName: "+sortFieldName);
	
	/* Setting the SortFields */
	if(sortFieldName.equals("CUST_NAME"))
	{
		columnNumber=1;
	}
	else if(sortFieldName.equals("GNRL_NAME"))
	{
		columnNumber=2;
	}
	else if(sortFieldName.equals("JB_NAME"))
	{
		columnNumber=3;
	}
	else if(sortFieldName.equals("JB_DRWG_NO"))
	{
		columnNumber=4;
	}
	else if(sortFieldName.equals("JB_RVSN_NO"))
	{
		columnNumber=5;
	}
	else if(sortFieldName.equals("JB_MATL_TYP"))
	{
		columnNumber=6;
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
	   obj.setJndiName("SessionJobDetailsManagerBean");
   	obj.setEnvironment();

/* 	Creating the Home and Remote Objects 	*/
		SessionJobDetailsManagerHome jobHomeObj = (SessionJobDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionJobDetailsManagerHome.class);
		SessionJobDetailsManager jobObj = (SessionJobDetailsManager)PortableRemoteObject.narrow(jobHomeObj.create(),SessionJobDetailsManager.class);
 
		if ((frm.getFormAction().equalsIgnoreCase("add")) || (frm.getFormAction().equalsIgnoreCase("update")) || (frm.getFormAction().equalsIgnoreCase("")))
		{  
			Filter fil[] = new Filter[0];
	 		session.setAttribute("objFilter",fil);
  		}
  		else if(frm.getFormAction().equalsIgnoreCase("search"))
  		{
  		  int maxItems = Integer.parseInt(frm.getMaxItems());
  		  
  		  Vector filter = new Vector();

			/* Calculating the Valid & Invalid records */
		  if (frm.getValidEntry().equalsIgnoreCase("yes"))
		  {
		  		Filter temp = new Filter();
		  		temp.setFieldName("JB_ISVALID");
		  		temp.setFieldValue("0");
		  		filter.add(temp);
		  }else if (frm.getValidEntry().equalsIgnoreCase("no"))
		  {
		  		Filter temp = new Filter();
		  		temp.setFieldName("JB_ISVALID");
		  		temp.setFieldValue("1");
		  		filter.add(temp);
		  }

			/* Sort by Customer Name */
			if (!frm.getTxtCustomerName().equalsIgnoreCase(""))
			{
				Filter temp = new Filter();
				temp.setFieldName("CUST_NAME");
				temp.setFieldValue(frm.getTxtCustomerName());
				String specialFunction="";
			   if(frm.getSearchCustomerName().equals("0"))
			   {
				   specialFunction = "Starts With";
			   }
			   else if(frm.getSearchCustomerName().equals("1"))
			   {
				   specialFunction = "Ends With";
			   }
			   else if(frm.getSearchCustomerName().equals("2"))
			   {
				   specialFunction = "Exactly";
			   }
			   else if(frm.getSearchCustomerName().equals("3"))
			   {
				   specialFunction = "Anywhere";
			   }
			   temp.setSpecialFunction(specialFunction);
				filter.add(temp);
			}

			/* Sort by Job Name */
			if (!frm.getTxtJobName().equalsIgnoreCase(""))
			{
				Filter temp = new Filter();
				temp.setFieldName("JB_NAME");
				temp.setFieldValue(frm.getTxtJobName());
				String specialFunction="";
			   if(frm.getSearchJobName().equals("0"))
			   {
				   specialFunction = "Starts With";
			   }
			   else if(frm.getSearchJobName().equals("1"))
			   {
				   specialFunction = "Ends With";
			   }
			   else if(frm.getSearchJobName().equals("2"))
			   {
				   specialFunction = "Exactly";
			   }
			   else if(frm.getSearchJobName().equals("3"))
			   {
				   specialFunction = "Anywhere";
			   }
			   temp.setSpecialFunction(specialFunction);
				filter.add(temp);
			}

			/* Sort by Material Type */
			if (!frm.getTxtMaterial().equalsIgnoreCase(""))
			{
				Filter temp = new Filter();
				temp.setFieldName("JB_MATL_TYP");
				temp.setFieldValue(frm.getTxtMaterial());
				String specialFunction="";
			   if(frm.getSearchMaterial().equals("0"))
			   {
				   specialFunction = "Starts With";
			   }
			   else if(frm.getSearchMaterial().equals("1"))
			   {
				   specialFunction = "Ends With";
			   }
			   else if(frm.getSearchMaterial().equals("2"))
			   {
				   specialFunction = "Exactly";
			   }
			   else if(frm.getSearchMaterial().equals("3"))
			   {
				   specialFunction = "Anywhere";
			   }
			   temp.setSpecialFunction(specialFunction);
				filter.add(temp);
			}

			/* Sort by Drawing No. */
			if (!frm.getDrawingNo().equals(""))
			{
   			Filter tempFilter = new Filter();
	   		tempFilter.setFieldName("JB_DRWG_NO");
			   tempFilter.setFieldValue(frm.getDrawingNo());
			   String specialFunction="";
			   if(frm.getDrawingSearchTab().equals("0"))
			   {
				   specialFunction = "Starts With";
			   }
			   else if(frm.getDrawingSearchTab().equals("1"))
			   {
				   specialFunction = "Ends With";
			   }
			   else if(frm.getDrawingSearchTab().equals("2"))
			   {
				   specialFunction = "Exactly";
			   }
			   else if(frm.getDrawingSearchTab().equals("3"))
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
		int maxItems = 15;
		if (request.getParameter("maxItem") == null)
		{
			maxItems = Integer.parseInt(frm.getMaxItems());
		}
		else
		{
			maxItems = Integer.parseInt(request.getParameter("maxItem"));
		}

		int pagecount = 0;
		if (request.getParameter("pageNo") == null)
	   {
		    pagecount = Integer.parseInt(frm.getPage());
	   }
	   else
	   {
			frm.setPage(request.getParameter("pageNo"));
			pagecount = Integer.parseInt(frm.getPage());
	   }
		
  		Filter objFilter[] = (Filter[])session.getAttribute("objFilter");
  		pageCount = Integer.parseInt(frm.getPage());

 		HashMap jobDetails = jobObj.getAllJobDetails(objFilter, frm.getSortField(), order, ((pageCount-1)*maxItems)+1, maxItems);
		if (BuildConfig.DMODE)
			System.out.println("JobDetails(HM): "+jobDetails);

		/* Object for JobMaster List */
		Vector objJobDetails = (Vector)jobDetails.get("JobDetails");
		pageContext.setAttribute("objJobDetails",objJobDetails);
		
		/* Setting Customer Name Combo*/
		HashMap customerName = jobObj.getAllCustomers();
		pageContext.setAttribute("customerName",customerName);
			
	  	totalCount = Integer.parseInt((jobDetails.get("TotalRecordCount")).toString()); 
		
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
	   JobMasterList.submit;
	 }
 }
</script>
<script language="Javascript" type="text/Javascript">

	function addItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/JobMaster/JobMasterAdd.jsp';
		document.forms[0].submit();
	}

	function editItem()
	{
		if(modifyRow('jobList', document.forms[0]))
		{
		   var val = getCheckedValueDetail('jobList', document.forms[0]);
		   document.forms[0].action = '<bean:message key="context"/>/jobMasterEdit.do?formAction=modify&id='+val;
		   document.forms[0].submit();
		}
	}
	
	function viewItem(val)
	{
	   document.forms[0].action = '<bean:message key="context"/>/jobMasterEdit.do?formAction=view&id='+val;
	   document.forms[0].submit();
	}
	
	function deleteItem()
	{
		if (deleteRow('jobList', document.forms[0]))
		{
			var queryString = '<bean:message key="context"/>/jobMasterEdit.do?formAction=delete';
			var val = getMultipleCheckedValue('jobList', document.forms[0]);
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
	
	function makeInvalidItem()
	{
		if (validRow('jobList', document.forms[0]))
		{
			var queryString = '<bean:message key="context"/>/jobMasterEdit.do?formAction=makeInvalid';
			var val = getMultipleCheckedValue('jobList', document.forms[0]);
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
		var queryString = '<bean:message key="context"/>/jobMasterEdit.do?formAction=makeValid';
		var val = getMultipleCheckedValue('jobList', document.forms[0]);
		var subQuery = '';
		for(var i=0;i<val.length;i++)
		{
		   subQuery = subQuery+'&ids='+val[i];
		}
		queryString = queryString + subQuery;
		document.forms[0].action = queryString;
		document.forms[0].submit();
	}
	
	function reLoadData()
	{
		temp = document.forms[0];
		if (temp.formAction.value == "afterView") 
		{
			if ('<%= request.getParameter("custName") %>' != 'null')
				temp.txtCustomerName.value = '<%= request.getParameter("custName") %>';
			else
				temp.txtCustomerName.value = "";

			if ('<%= request.getParameter("custSrchTab") %>' != 'null')
				temp.searchCustomerName.value = '<%= request.getParameter("custSrchTab") %>';
			else
				temp.searchCustomerName.value = "0";

			if ('<%= request.getParameter("jbName") %>' != 'null')
				temp.txtJobName.value = '<%= request.getParameter("jbName") %>';
			else
				temp.txtJobName.value = "";

			if ('<%= request.getParameter("jbSrchTab") %>' != 'null')
				temp.searchJobName.value = '<%= request.getParameter("jbSrchTab") %>';
			else
				temp.searchJobName.value = "0";

			if ('<%= request.getParameter("viewAll") %>' != 'null')
				temp.validEntry.value = '<%= request.getParameter("viewAll") %>';
			else
				temp.validEntry.value = "all";

			if ('<%= request.getParameter("dwgNo") %>' != 'null')
				temp.drawingNo.value = '<%= request.getParameter("dwgNo") %>';
			else
				temp.drawingNo.value = "";

			if ('<%= request.getParameter("dwgSrchTab") %>' != 'null')
				temp.drawingSearchTab.value = '<%= request.getParameter("dwgSrchTab") %>';
			else
				temp.drawingSearchTab.value = "0";

			if ('<%= request.getParameter("matlType") %>' != 'null')
				temp.txtMaterial.value = '<%= request.getParameter("matlType") %>';
			else
				temp.txtMaterial.value = "";

			if ('<%= request.getParameter("matlSrchTab") %>' != 'null')
				temp.searchMaterial.value = '<%= request.getParameter("matlSrchTab") %>';
			else
				temp.searchMaterial.value = "0";

			if ('<%= request.getParameter("maxItem") %>' != 'null')
			{
				for (var i = 0; i < temp.maxItems.length; i++)
				{
					if (temp.maxItems[i].value == '<%= request.getParameter("maxItem") %>')
					{
						temp.maxItems[i].checked = true;
						temp.maxItems.value = '<%= request.getParameter("maxItem") %>';
					}
				}
			}

			if ('<%= request.getParameter("pageNo") %>' != 'null')
				temp.page.value = '<%= request.getParameter("pageNo") %>';
		}
	}
	
</script>
</head>
<body onLoad="loadDefault(); sortByTable('jobList',<%= columnNumber%>,'<%= order%>'); reLoadData();">
<html:form action="frmJobList">
<html:hidden property="formAction"/>
<html:hidden property="sortField"  value="<%= frm.getSortField()%>"/>
<html:hidden property="sortOrder" value="<%= frm.getSortOrder()%>"/>

  <table width="100%" cellspacing="0" cellpadding="10">
    <tr>
      <td><table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
          <tr>
            <td><bean:message key="prodacs.job.header"/></td>
          </tr>
        </table>
        <br> <table width="100" cellspacing="0" cellpadding="0" align="right">
          <tr> 
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New Job Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1011" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Modify Job Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1011" text="[ Modify ]" classId="TopLnk" onClick="javaScript:editItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Delete Job Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1011" text="[ Delete ]" classId="TopLnk" onClick="javaScript:deleteItem();"/>
 		<menuconfig:userRights url="#" id="mkInValid" onMouseOver="window.status='Make Job Info InValid'; return true"  onMouseOut="window.status=''; return true" resourceId="1011" text="[ MakeInValid ]" classId="TopLnk" onClick="javaScript:makeInvalidItem();"/>
 		<menuconfig:userRights url="#" id="mkValid" onMouseOver="window.status='Make Job Info Valid'; return true"  onMouseOut="window.status=''; return true" resourceId="1011" text="[ MakeValid ]" classId="TopLnk" style="display:none" onClick="javaScript:makeValidItem();"/>
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
            <td width="100"><bean:message key="prodacs.customer.customername"/></td>
            <td width="1">:</td>
            <td width="200"><html:text property="txtCustomerName" styleClass="TextBox" size="15" maxlength="15" />&nbsp;
              <html:select property="searchCustomerName" styleClass="Combo">
	               <html:option value="0">Start With</html:option>
						<html:option value="1">End With</html:option>
						<html:option value="2">Exactly</html:option>
						<html:option value="3">AnyWhere</html:option>
              </html:select></td>
            <td width="60"><bean:message key="prodacs.job.jobname"/></td>
            <td width="1">:</td>
            <td><html:text property="txtJobName" styleClass="TextBox" size="15" maxlength="15" />&nbsp;
            <html:select property="searchJobName" styleClass="Combo">
               <html:option value="0">Start With</html:option>
					<html:option value="1">End With</html:option>
					<html:option value="2">Exactly</html:option>
					<html:option value="3">AnyWhere</html:option>
              </html:select> &nbsp;&nbsp;&nbsp;
            </td>
            <td><bean:message key="prodacs.common.view"/></td>
            	<td>:</td>
            	<td>
                <html:select property="validEntry" styleClass="Combo">
	                <html:option value="all">All</html:option>
	                <html:option value="yes">InValid</html:option>
	                <html:option value="no">Valid</html:option>
                </html:select>
            	</td>
          </tr>
          <tr>
            <td><bean:message key="prodacs.job.drawingno"/></td>
            <td>:</td>
            <td><html:text property="drawingNo" styleClass="TextBox" size="15" maxlength="15" />&nbsp;
            <html:select property="drawingSearchTab" styleClass="Combo">
               <html:option value="0">Start With</html:option>
					<html:option value="1">End With</html:option>
					<html:option value="2">Exactly</html:option>
					<html:option value="3">AnyWhere</html:option>
 				</html:select></td> &nbsp;&nbsp;&nbsp;
            <td><bean:message key="prodacs.job.material"/></td>
            <td>:</td>
            <td><html:text property="txtMaterial" styleClass="TextBox" size="15" maxlength="15" />&nbsp;
              <html:select property="searchMaterial" styleClass="Combo">
               <html:option value="0">Start With</html:option>
					<html:option value="1">End With</html:option>
					<html:option value="2">Exactly</html:option>
					<html:option value="3">AnyWhere</html:option>
              </html:select></td><td>
             <html:button property="search" styleClass="Button" value="Search" onclick="navigation()" /></td>
          </tr>
        </table>
      <font class="message">
			<html:messages id="messageid" message="true">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<bean:write name="messageid" />
			</html:messages>
		</font>
		<table>
			<tr> 
				<td colspan='2'> <font size="1px" face="Verdana"><html:errors/></font></td>
			</tr>
		</table>
        <br>
        <table width="100%" cellpadding="0" cellspacing="0" id="jobList">
          <tr> 
            <td width="25" class="SortHeader"><input type="checkbox" name="CheckAll" value="checkbox" onClick="checkAll(document.JobMasterList)"></td>
            <td onClick="sortTables(document.forms[0],this,'CUST_NAME')" class="SortHeader"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"><bean:message key="prodacs.customer.customername"/></td>
            <td onClick="sortTables(document.forms[0],this,'GNRL_NAME')" width="150" class="SortHeader"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"><bean:message key="prodacs.job.generalname"/></td>
            <td onClick="sortTables(document.forms[0],this,'JB_NAME')" width="120" class="SortHeader"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"> <bean:message key="prodacs.job.jobname"/></td>
            <td onClick="sortTables(document.forms[0],this,'JB_DRWG_NO')" width="90" class="SortHeader"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"> <bean:message key="prodacs.job.drawing"/></td>
            <td onClick="sortTables(document.forms[0],this,'JB_RVSN_NO')" width="90" class="SortHeader"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"><bean:message key="prodacs.job.revision"/></td>
            <td onClick="sortTables(document.forms[0],this,'JB_MATL_TYP')" width="80" class="SortHeader"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"> <bean:message key="prodacs.job.material"/></td>
          </tr>
          <logic:iterate id="bt1" name="objJobDetails" indexId="count">
          <tr>
      <%
	      if(count.intValue()%2 == 0)
   	   {
     	%>
				<logic:equal name="bt1" property="job_IsValid" value="0"><td  class="InValid"></logic:equal>
	         <logic:equal name="bt1" property="job_IsValid" value="1"><td  class="TableItems"></logic:equal>	
	         
            <input name="CheckValue" type="checkbox" id="CheckValue" value='<bean:write name="bt1" property="job_Id"/>' onClick="isSimilar(this)"></td>
            <td class="TableItems"><a href="#" onMouseOver="window.status='View Job Info';return true;" onMouseOut="window.status='';return true;" id='<bean:write name="bt1" property="job_Id"/>' onClick="viewItem(this.id)"><bean:write name="bt1" property="cust_Name"/>&nbsp;</a></td>
            <td class="TableItems"><bean:write name="bt1" property="gnrl_Name"/>&nbsp;</td>
            <td class="TableItems"><bean:write name="bt1" property="job_Name"/>&nbsp;</td>
            <td class="TableItems"><bean:write name="bt1" property="job_Dwg_No"/>&nbsp;</td>
            <td class="TableItems"><bean:write name="bt1" property="job_Rvsn_No"/>&nbsp;</td>
            <td class="TableItems"><bean:write name="bt1" property="job_Matl_Type"/>&nbsp;</td>
		<%
			} else
			{
		%>            
				<logic:equal name="bt1" property="job_IsValid" value="0"><td  class="InValid"></logic:equal>
	         <logic:equal name="bt1" property="job_IsValid" value="1"><td  class="TableItems2"></logic:equal>	
	         
            <input name="CheckValue" type="checkbox" id="CheckValue" value='<bean:write name="bt1" property="job_Id"/>' onClick="isSimilar(this)"></td>
            <td class="TableItems2"><a href="#" onMouseOver="window.status='View Job Info';return true;" onMouseOut="window.status='';return true;" id='<bean:write name="bt1" property="job_Id"/>' onClick="viewItem(this.id)"><bean:write name="bt1" property="cust_Name"/>&nbsp;</a></td>
            <td class="TableItems2"><bean:write name="bt1" property="gnrl_Name"/>&nbsp;</td>
            <td class="TableItems2"><bean:write name="bt1" property="job_Name"/>&nbsp;</td>
            <td class="TableItems2"><bean:write name="bt1" property="job_Dwg_No"/>&nbsp;</td>
            <td class="TableItems2"><bean:write name="bt1" property="job_Rvsn_No"/>&nbsp;</td>
            <td class="TableItems2"><bean:write name="bt1" property="job_Matl_Type"/>&nbsp;</td>
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
