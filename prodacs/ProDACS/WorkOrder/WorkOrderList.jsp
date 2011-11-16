<%@ page language = "java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri = "/WEB-INF/struts-logic.tld" prefix = "logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<jsp:useBean id="frm" class="com.savantit.prodacs.presentation.tableadmin.workorder.WorkOrderListForm" />
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
<useradmin:userrights resource="13"/>
<% 
	if (BuildConfig.DMODE)
		System.out.println("Work Order List");
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
	else if(sortFieldName.equals("CUST_NAME"))
	{
		columnNumber = 2;
	}
	else if(sortFieldName.equals("WO_STAT"))
	{
		columnNumber = 3;
	}
	else if(sortFieldName.equals("WO_DATE"))
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
/* 	Setting the JNDI name and Environment 	*/
	   obj.setJndiName("SessionWorkOrderDetailsManagerBean");
   	obj.setEnvironment();

/* 	Creating the Home and Remote Objects 	*/
		SessionWorkOrderDetailsManagerHome woHomeObj = (SessionWorkOrderDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionWorkOrderDetailsManagerHome.class);
		SessionWorkOrderDetailsManager workOrderObj = (SessionWorkOrderDetailsManager)PortableRemoteObject.narrow(woHomeObj.create(),SessionWorkOrderDetailsManager.class);
		
  		
		if(frm.getFormAction().equals("")|| frm.getFormAction().equals("add") || frm.getFormAction().equals("update"))
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
		  		temp.setFieldName("WO_ISVALID");
		  		temp.setFieldValue("0");
		  		filter.add(temp);
		  }else if (frm.getListValidEntries().equalsIgnoreCase("no"))
		  {
		  		Filter temp = new Filter();
		  		temp.setFieldName("WO_ISVALID");
		  		temp.setFieldValue("1");
		  		filter.add(temp);
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
			   if(frm.getJbNameCombo().equals("0"))
			   {
				   specialFunction = "Starts With";
			   }
			   else if(frm.getJbNameCombo().equals("1"))
			   {
				   specialFunction = "Ends With";
			   }
			   else if(frm.getJbNameCombo().equals("2"))
			   {
				   specialFunction = "Exactly";
			   }
			   else if(frm.getJbNameCombo().equals("3"))
			   {
				   specialFunction = "Anywhere";
			   }
			   tempFilter.setSpecialFunction(specialFunction);
			   filter.add(tempFilter);
			}

		  /* For Searching by Drawing No. */
			if (!frm.getDwgNo().equals(""))
			{
   			Filter tempFilter = new Filter();
	   		tempFilter.setFieldName("JB_DWG_NO");
			   tempFilter.setFieldValue(frm.getDwgNo());
			   String specialFunction="";
			   if(frm.getDwgNoCombo().equals("0"))
			   {
				   specialFunction = "Starts With";
			   }
			   else if(frm.getDwgNoCombo().equals("1"))
			   {
				   specialFunction = "Ends With";
			   }
			   else if(frm.getDwgNoCombo().equals("2"))
			   {
				   specialFunction = "Exactly";
			   }
			   else if(frm.getDwgNoCombo().equals("3"))
			   {
				   specialFunction = "Anywhere";
			   }
			   tempFilter.setSpecialFunction(specialFunction);
			   filter.add(tempFilter);
			}

		  /* For Searching WO # */
			if (!frm.getWoNo().equals(""))
			{
   			Filter tempFilter = new Filter();
	   		tempFilter.setFieldName("WO_NO");
			   tempFilter.setFieldValue(frm.getWoNo());
			   String specialFunction="";
			   if(frm.getWoCombo().equals("0"))
			   {
				   specialFunction = "Starts With";
			   }
			   else if(frm.getWoCombo().equals("1"))
			   {
				   specialFunction = "Ends With";
			   }
			   else if(frm.getWoCombo().equals("2"))
			   {
				   specialFunction = "Exactly";
			   }
			   else if(frm.getWoCombo().equals("3"))
			   {
				   specialFunction = "Anywhere";
			   }
			   tempFilter.setSpecialFunction(specialFunction);
			   filter.add(tempFilter);
			}

			/* By WorkOrder Date */
			if ((!frm.getWorkOrderFromDate().equalsIgnoreCase("")) && (!frm.getWorkOrderToDate().equalsIgnoreCase("")))
			{
				String[] month = {"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
				StringTokenizer frmDate = new StringTokenizer((frm.getWorkOrderFromDate()).trim(),"-");
				String tempFrmDate[] = new String[frmDate.countTokens()];
				int i=0;
				while(frmDate.hasMoreTokens())
				{
					tempFrmDate[i] = frmDate.nextToken();
					if (BuildConfig.DMODE)
						System.out.println("Frm: "+tempFrmDate[i]);
					i++;
				}
				
				StringTokenizer toDate = new StringTokenizer((frm.getWorkOrderToDate()).trim(),"-");
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
			/* By StatusId */
			if (!frm.getWorkOrderStatus().equalsIgnoreCase("0"))
			{
				Filter temp = new Filter();
				temp.setFieldName("WO_STAT");
				temp.setFieldValue(frm.getWorkOrderStatus());
				filter.add(temp);
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

 		HashMap workOrderDetails = workOrderObj.getAllWorkOrderDetails(objFilter, frm.getSortField(), order, ((pageCount-1)*maxItems)+1, maxItems);
		if (BuildConfig.DMODE)
			System.out.println("workOrderDetails(HM): "+workOrderDetails);
		
		Vector vec_WorkOrder = (Vector) workOrderDetails.get("WorkOrderDetails");

	 	pageContext.setAttribute("vec_WorkOrder",vec_WorkOrder);
	 	
	  	int totalCount = Integer.parseInt((workOrderDetails.get("TotalRecordCount")).toString()); 
	  	
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
<title><bean:message key="prodacs.workorder.header"/></title>
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
	   WorkOrderList.submit;
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
		temp.page.value = 1;
		if ((temp.workOrderFromDate.value != "") || (temp.workOrderToDate.value != ""))
		{
			var fDate = (temp.workOrderFromDate.value).split("-");
			var fromDate = new Date(fDate[0],parseInt(fDate[1],10)-1,fDate[2]);
			var tDate = (temp.workOrderToDate.value).split("-");
			var toDate = new Date(tDate[0],parseInt(tDate[1],10)-1,tDate[2]);
			if(fromDate > toDate)
			{
				alert("From Date cannot be greater than To Date!");
				return false;
			}
		}
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
	   document.forms[0].action = '<bean:message key="context"/>/frmWorkOrderEdit.do?formAction=view&id='+val;
	   document.forms[0].submit();
	}
	
	function addItem()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/WorkOrder/WorkOrderAdd.jsp';
		temp.submit();
	}
	
	function editItem()
	{
		var temp = document.forms[0];
		var obj = document.getElementById('wrkOrdList');
		if (temp.CheckValue.length != undefined)
		{
			if (obj.children(0).children.length == 1)
			{
				alert("There is no item in the list to modify!");
				return false;
			}
			if (obj.children(0).children.length != 0)
			{
				for (var k = 0; k < obj.children(0).children.length; k++)
				{
					if ((obj.children(0).children(k).children(0).className == "InValid") && (obj.children(0).children(k).children(0).children(0).checked))
					{
						alert("Invalid item cannot be modified!");
						return false;
					}
				}
			}
			var count = 0;
			for (var x = 0; x < temp.CheckValue.length; x++)
			{
				if (temp.CheckValue[x].checked)
				{
					count++;
				}
			}
			if (count == 0)
			{
				alert("No WorkOrder selected to modify!");
				return false;
			}
			else if (count > 1)
			{
				alert("Select one WorkOrder to modify! ");
				return false;
			}
			if (temp.CheckValue.length == undefined)
			{
				alert("No WorkOrder selected to modify!");
				return false;
			}
			else
			{
				for (var i = 0; i < temp.CheckValue.length; i++)
				{
					if (temp.CheckValue[i].checked)
					{
						val = parseInt(temp.CheckValue[i].value);
						temp.action = '<bean:message key="context"/>/frmWorkOrderEdit.do?formAction=modify&id='+val;
						temp.submit();
					}
				}
			}
		}
		else
		{
			var obj1 = temp.CheckValue;
			if (obj1.parentNode.parentNode.children(0).className != 'InValid')
			{
			val = parseInt(temp.CheckValue.value,10);
			temp.action = '<bean:message key="context"/>/frmWorkOrderEdit.do?formAction=modify&id='+val;
			temp.submit();
			}
			else
				alert ('InValid Item cannot be Modified!');
		}
	}
	
	function makeInvalidItem()
	{
		var temp = document.forms[0];
		var obj = document.getElementById('wrkOrdList');
		if (temp.CheckValue.length != undefined)
		{
			if (obj.children(0).children.length == 1)
			{
				alert("There is no item in the list to MakeInvalid!");
				return false;
			}
			var count = 0;
			for (var x = 0; x < temp.CheckValue.length; x++)
			{
				if (temp.CheckValue[x].checked)
				{
					count++;
				}
			}
			if (count == 0)
			{
				alert("No item selected to Make Invalid!");
				return false;
			}
			else
			{
				var subQuery = "";
				var queryString = '<bean:message key="context"/>/frmWorkOrderEdit.do?formAction=makeInvalid';
				for (var i = 0; i < temp.CheckValue.length; i++)
				{
					if (temp.CheckValue[i].checked)
					{
						subQuery = subQuery+'&ids='+temp.CheckValue[i].value;
					}
				}
				queryString = queryString + subQuery;
				temp.action = queryString;
				temp.submit();
			}
		}
		else
		{
			if (temp.CheckValue.checked)
			{
				var obj1 = temp.CheckValue;
				if (obj1.parentNode.parentNode.children(0).className != 'InValid')
				{
					var val = '&ids='+parseInt(temp.CheckValue.value,10);
					temp.action = '<bean:message key="context"/>/frmWorkOrderEdit.do?formAction=makeInvalid'+val;
					temp.submit();
				}
			}
			else
			{
				alert("Select an item to make Invalid!");
				return false;
			}
		}
	}
	
	function makeValidItem()
	{
		var temp = document.forms[0];
		var obj = document.getElementById('wrkOrdList');
		if (temp.CheckValue.length != undefined)
		{
			if (obj.children(0).children.length == 1)
			{
				alert("There is no item in the list to MakeValid!");
				return false;
			}
			var count = 0;
			for (var x = 0; x < temp.CheckValue.length; x++)
			{
				if (temp.CheckValue[x].checked)
				{
					count++;
				}
			}
			if (count == 0)
			{
				alert("No item selected to Make Valid!");
				return false;
			}
			else
			{
				var subQuery = "";
				var queryString = '<bean:message key="context"/>/frmWorkOrderEdit.do?formAction=makeValid';
				for (var i = 0; i < temp.CheckValue.length; i++)
				{
					if (temp.CheckValue[i].checked)
					{
						subQuery = subQuery+'&ids='+temp.CheckValue[i].value;
					}
				}
				queryString = queryString + subQuery;
				temp.action = queryString;
				temp.submit();
			}
		}
		else
		{
			if (temp.CheckValue.checked)
			{
				var obj1 = temp.CheckValue;
				if (obj1.parentNode.parentNode.children(0).className == 'InValid')
				{
					var val = '&ids='+parseInt(temp.CheckValue.value,10);
					temp.action = '<bean:message key="context"/>/frmWorkOrderEdit.do?formAction=makeValid'+val;
					temp.submit();
				}
			}
			else
			{
				alert("Select an item to make valid!");
				return false;
			}
		}
	}
	
	function reLoadData()
	{
		temp = document.forms[0];
		if (temp.formAction.value == "afterView") 
		{
			if ('<%= request.getParameter("frmDate") %>' != 'null')
				temp.workOrderFromDate.value = '<%= request.getParameter("frmDate") %>';
			else
				temp.workOrderFromDate.value = "";

			if ('<%= request.getParameter("toDate") %>' != 'null')
				temp.workOrderToDate.value = '<%= request.getParameter("toDate") %>';
			else
				temp.workOrderToDate.value = "";

			if ('<%= request.getParameter("woNo") %>' != 'null')
				temp.woNo.value = '<%= request.getParameter("woNo") %>';
			else
				temp.woNo.value = "";

			if ('<%= request.getParameter("woCombo") %>' != 'null')
				temp.woCombo.value = '<%= request.getParameter("woCombo") %>';
			else
				temp.woCombo.value = "0";

			if ('<%= request.getParameter("customer") %>' != 'null')
				temp.customer.value = '<%= request.getParameter("customer") %>';
			else
				temp.customer.value = "";

			if ('<%= request.getParameter("custCombo") %>' != 'null')
				temp.custCombo.value = '<%= request.getParameter("custCombo") %>';
			else
				temp.custCombo.value = "0";

			if ('<%= request.getParameter("woStatus") %>' != 'null')
				temp.workOrderStatus.value = '<%= request.getParameter("woStatus") %>';
			else
				temp.workOrderStatus.value = "0";

			if ('<%= request.getParameter("viewAll") %>' != 'null')
				temp.listValidEntries.value = '<%= request.getParameter("viewAll") %>';
			else
				temp.listValidEntries.value = "all";

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

			if ('<%= request.getParameter("jbName") %>' != 'null')
				temp.jbName.value = '<%= request.getParameter("jbName") %>';
			else
				temp.jbName.value = "";

			if ('<%= request.getParameter("jbNameCombo") %>' != 'null')
				temp.jbNameCombo.value = '<%= request.getParameter("jbNameCombo") %>';
			else
				temp.jbNameCombo.value = "0";
			
			if ('<%= request.getParameter("dwgNo") %>' != 'null')
				temp.dwgNo.value = '<%= request.getParameter("dwgNo") %>';
			else
				temp.dwgNo.value = "";

			if ('<%= request.getParameter("dwgNoCombo") %>' != 'null')
				temp.dwgNoCombo.value = '<%= request.getParameter("dwgNoCombo") %>';
			else
				temp.dwgNoCombo.value = "all";

			if ('<%= request.getParameter("pageNo") %>' != 'null')
				temp.page.value = '<%= request.getParameter("pageNo") %>';
		}
	}
	
	function hideNshow(val,value)
	{
		var temp = document.forms[0];
		var obj = document.getElementById(val);
		var count = 0;
		if ((obj.style.display == 'block') && (value.value == "1"))
		{
			value.src = '<bean:message key="context"/>/images/close_group.gif';
			obj.style.display = 'none';
			value.value = "0";
		}
		else
		{
			value.src = '<bean:message key="context"/>/images/open_group.gif';
			obj.style.display = 'block';
			value.value = "1";
		}
	}
</script>
</head>

<body onLoad="loadDefault();sortByTable('wrkOrdList',<%= columnNumber%>,'<%= order%>'); init(); reLoadData();">
<html:form action="frmWrkOrdList">
<html:hidden property="formAction"/>
<html:hidden property="sortField"  value="<%= frm.getSortField()%>"/>
<html:hidden property="sortOrder" value="<%= frm.getSortOrder()%>"/>
  <table width="100%" cellspacing="0" cellpadding="10">
    <tr>
      <td><table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
          <tr>
            <td><bean:message key="prodacs.workorder.header"/></td>
          </tr>
        </table>
        <br>
        <table width="100" cellspacing="0" cellpadding="0" align="right">
        <tr> 
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New WorkOrder Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1013" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Modify WorkOrder Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1013" text="[ Modify ]" classId="TopLnk" onClick="javaScript:editItem();"/>
 		<menuconfig:userRights url="#" id="mkInValid" onMouseOver="window.status='Make WorkOrder Info InValid'; return true"  onMouseOut="window.status=''; return true" resourceId="1013" text="[ MakeInValid ]" classId="TopLnk" onClick="javaScript:makeInvalidItem();"/>
 		<menuconfig:userRights url="#" id="mkValid" onMouseOver="window.status='Make WorkOrder Info Valid'; return true"  onMouseOut="window.status=''; return true" resourceId="1013" text="[ MakeValid ]" classId="TopLnk" style="display:none" onClick="javaScript:makeValidItem();"/>
        </tr>
        </table><br>
        <table width="100%" cellspacing="0" cellpadding="0">
          <tr> 
            <td width="20" id="FilterTip">&nbsp;</td>
            <td class="FilterTitle"><a href="javascript:showFilter(document.getElementById('Filter'), document.getElementById('FilterImg'))"><img src='<bean:message key="context"/>/images/hide.gif' width="9" height="5" border="0" align="absmiddle" id="FilterImg"> 
              <bean:message key="prodacs.common.filter"/></a></td>
            <td width="20" height="20" id="FilterEnd">&nbsp;</td>
          </tr>
        </table>
        <table width="100%" cellspacing="0" cellpadding="0" id="Filter" border="0">
          <tr> 
            <td width="90"><bean:message key="prodacs.workorder.fromdate"/></td>
            <td width="1">:</td>
            <td width="200"><html:text property="workOrderFromDate" styleClass="TextBox" size="12" readonly="true"/>
              <img src='<bean:message key="context"/>/images/calendar.gif' width="16" height="15" align="absmiddle" onClick='show_calendar("workOrderFromDate",WorkOrderList.workOrderFromDate.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"></td>
            <td width="55"><bean:message key="prodacs.workorder.todate"/></td>
            <td width="1">:</td>
            <td><html:text property="workOrderToDate" styleClass="TextBox" size="12" readonly="true"/>
              <img src='<bean:message key="context"/>/images/calendar.gif' width="16" height="15" align="absmiddle" onClick='show_calendar("workOrderToDate",WorkOrderList.workOrderToDate.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"> 
            </td>
				 <td width="80"><bean:message key="prodacs.workorder.wono"/></td>
            <td width="1">:</td>
            <td><html:text property="woNo" styleClass="TextBox" size="8"/>&nbsp;
            	<html:select property="woCombo" styleClass="Combo">
	               <html:option value="0">Start With</html:option>
						<html:option value="1">End With</html:option>
						<html:option value="2">Exactly</html:option>
						<html:option value="3">AnyWhere</html:option>
	 				</html:select></td>
          </tr>
          <tr>
            <td><bean:message key="prodacs.workorder.customer"/></td>
            <td>:</td>
            <td><html:text property="customer" styleClass="TextBox" size="15"/>&nbsp;
            	<html:select property="custCombo" styleClass="Combo">
	               <html:option value="0">Start With</html:option>
						<html:option value="1">End With</html:option>
						<html:option value="2">Exactly</html:option>
						<html:option value="3">AnyWhere</html:option>
	 				</html:select></td>
            <td><bean:message key="prodacs.workorder.status"/></td>
            <td>:</td>
            <td><html:select property="workOrderStatus" styleClass="Combo">
                <html:option value="0">-- Choose Status --</html:option>
                <html:option value="o">Open</html:option>
                <html:option value="a">Active</html:option>
                <html:option value="c">Completed</html:option>
		    <html:option value="d">Closed</html:option>
              </html:select></td>
            <td><bean:message key="prodacs.common.view"/>
			<td>:</td>
			<td>
              <html:select property="listValidEntries" styleClass="Combo">
                <html:option value="all">All</html:option>
                <html:option value="yes">InValid</html:option>
                <html:option value="no">Valid</html:option>
              </html:select></td>
          </tr>
		  <tr>
            <td><bean:message key="prodacs.job.jobname"/></td>
            <td>:</td>
            <td><html:text property="jbName" styleClass="TextBox" size="15"/>&nbsp;
            	<html:select property="jbNameCombo" styleClass="Combo">
	               <html:option value="0">Start With</html:option>
						<html:option value="1">End With</html:option>
						<html:option value="2">Exactly</html:option>
						<html:option value="3">AnyWhere</html:option>
	 				</html:select></td>
            <td width="190"><bean:message key="prodacs.postproduction.drawing"/></td>
            <td>:</td>
            <td><html:text property="dwgNo" styleClass="TextBox"/>&nbsp;
            	<html:select property="dwgNoCombo" styleClass="Combo">
	               <html:option value="0">Start With</html:option>
						<html:option value="1">End With</html:option>
						<html:option value="2">Exactly</html:option>
						<html:option value="3">AnyWhere</html:option>
	 				</html:select></td>
	        <td><html:button styleClass="Button" property="search" onclick="navigation()" value="Go"/></td>
		  </tr>
        </table>
        <font class="message">
        <html:messages id="messageid" message="true">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="messageid"/></html:messages>
		</font>
		<table>
			<tr><td colspan='2'> <font size="1px" face="Verdana"><html:errors/></font></td></tr>
		</table>
        <br>
        <table width="100%" cellpadding="0" cellspacing="0" id="wrkOrdList">
          <tr> 
            <td width="25" class="SortHeader"><input type="checkbox" name="CheckAll" value="checkbox" onClick="checkAll(document.WorkOrderList)"></td>
            <td width="100" class="SortHeader" onClick="sortTables(document.forms[0],this,'WO_NO')"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"> 
              <bean:message key="prodacs.workorder.workorder"/></td>
            <td onClick="sortTables(document.forms[0],this,'CUST_NAME')" class="SortHeader"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"> 
              <bean:message key="prodacs.customer.customername"/></td>
            <td onClick="sortTables(document.forms[0],this,'WO_STAT')" width="65" class="SortHeader"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"> 
              <bean:message key="prodacs.workorder.status"/></td>
            <td onClick="sortTables(document.forms[0],this,'WO_DATE')" width="90" class="SortHeader"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"> 
              <bean:message key="prodacs.workorder.wodate"/></td>
          </tr>
          <logic:iterate id="bt1" name="vec_WorkOrder" indexId="count">
          <tr>
			 <logic:equal name="bt1" property="workOrderIsValid" value="0"><td  class="InValid"></logic:equal>
	         <logic:equal name="bt1" property="workOrderIsValid" value="1"><td  class="TableItems"></logic:equal>
	         
            <input name="CheckValue" type="checkbox" id="CheckValue" value='<bean:write name="bt1" property="workOrderId"/>' onClick="isSimilar(this)"></td>
            <td class="TableItems"><img src='<bean:message key="context"/>/images/close_group.gif' width="13" height="13" align="center" value="1" onclick="hideNshow('hideNshowJobs<%= count %>',this);">&nbsp;<a href="#" onMouseOver="window.status='View WorkOrder Info'; return true"  onMouseOut="window.status=''; return true" id='<bean:write name="bt1" property="workOrderId"/>' onClick="viewItem(this.id);"><bean:write name="bt1" property="workOrderNo"/>&nbsp;</a></td>
            <td class="TableItems"><bean:write name="bt1" property="custName"/>&nbsp;</td>
			<td width="65" class="TableItems">
				<logic:equal name="bt1" property="workOrderStatus" value="O">Open</logic:equal>
				<logic:equal name="bt1" property="workOrderStatus" value="A">Active</logic:equal>
				<logic:equal name="bt1" property="workOrderStatus" value="C">Completed</logic:equal>
				<logic:equal name="bt1" property="workOrderStatus" value="D">Closed</logic:equal>
			</td>
            <td class="TableItems"><bean:define id="woDate" name="bt1" property="woCreatedDate"/><%= woDate.toString().substring(0,10) %>&nbsp;</td>
		   </tr>
            <tr id="hideNshowJobs<%= count %>" style="display:none">
				<td width="25" class="TableItems">&nbsp;</td>
				<td width="100" class="TableItems">&nbsp;</td>
				<td>
					<table cellspacing="0" cellpadding="1" id='hideNshowJobDets'>
					<tr>
						<td class="SortHeader1" width="240">Job Name</td>
						<td class="SortHeader1" width="75">Drawing No</td>
						<td class="SortHeader1" width="53">Job Qty</td>
						<td class="SortHeader1" width="75">Status</td>
					</tr>
					</table>
					<bean:define id="bt2" name="bt1" property="WOJobDetails"/>
					<logic:iterate id="bt3" name="bt2" indexId="cnt">
						<table cellspacing="0" cellpadding="2">
						<tr>
							<td width="241" class="TableItems4"><bean:write name="bt3" property="jobName"/></td>
							<td width="71" class="TableItems4"><bean:write name="bt3" property="jobDrwgNo"/></td>
							<td width="51" class="TableItems4"><bean:write name="bt3" property="jobQty"/></td>
							<td width="74" class="TableItems4">
								<logic:equal name="bt3" property="jobStatus" value="O">Open</logic:equal>
								<logic:equal name="bt3" property="jobStatus" value="A">Active</logic:equal>
								<logic:equal name="bt3" property="jobStatus" value="C">Completed</logic:equal>
								<logic:equal name="bt3" property="jobStatus" value="D">Closed</logic:equal>
							</td>
						</tr>
						</table>
					</logic:iterate>
				</td>
				<td width="65" class="TableItems">&nbsp;</td>
				<td width="90" class="TableItems">&nbsp;</td>
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
