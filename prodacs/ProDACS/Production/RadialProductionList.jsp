<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<jsp:useBean id="frm" class="com.savantit.prodacs.presentation.tableadmin.production.RadialProductionListForm" />
<jsp:setProperty name="frm" property="*" /> 
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.savantit.prodacs.infra.beans.Filter"%>
 
<%@ page import="com.savantit.prodacs.facade.SessionRadlProductionDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionRadlProductionDetailsManager"%>
<%@ page import="java.util.StringTokenizer"%>
<useradmin:userrights resource="19"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("Radial Production List");
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	int totalPages = 0;
	int pageCount = 1 ;
	int columnNumber = 1;
	
	boolean order = true;
	String sortFieldName = frm.getSortField();
	if (BuildConfig.DMODE)
		System.out.println(sortFieldName);
	
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
	
	Vector vec_rdlProDet = new Vector();
	try
	{
		if (BuildConfig.DMODE)
			System.out.println("Radial Production List Starts.");
		
		/* 	Setting the JNDI name and Environment 	*/
	   	obj.setJndiName("SessionRadlProductionDetailsManagerBean");
   		obj.setEnvironment();

		/* 	Creating the Home and Remote Objects 	*/
		SessionRadlProductionDetailsManagerHome rdlProHomeObj = (SessionRadlProductionDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionRadlProductionDetailsManagerHome.class);
		SessionRadlProductionDetailsManager rdlProductionObj = (SessionRadlProductionDetailsManager)PortableRemoteObject.narrow(rdlProHomeObj.create(),SessionRadlProductionDetailsManager.class);
		
		/* Setting the SortFields */
		if(sortFieldName.equals("MC_CDE"))
		{
			columnNumber = 1;
		}
		else if(sortFieldName.equals("RADL_CRNT_DATE"))
		{
			columnNumber = 2;
		}
		else if(sortFieldName.equals("SHIFT_NAME"))
		{
			columnNumber = 3;
		}
		else if(sortFieldName.equals("WO_NO"))
		{
			columnNumber = 4;
		}
		else if(sortFieldName.equals("JB_NAME"))
		{
			columnNumber = 5;
		}

    	Vector filter = new Vector();
		if(frm.getFormAction().equals("")|| frm.getFormAction().equals("add"))
		{  
			frm.setFormAction("");
		  	if (frm.getPostedView().equalsIgnoreCase("no"))
		  	{
		  		Filter temp = new Filter();
		  		temp.setFieldName("RADL_POST_FLG");
		  		temp.setFieldValue("0");
		  		filter.add(temp);
		  	}
			if (BuildConfig.DMODE)
				System.out.println("frm.getPostedView() :"+frm.getPostedView());
			Filter objFilter[] = new Filter[filter.size()];
  			filter.copyInto(objFilter);

	 		session.setAttribute("objFilter",objFilter);
  		}
  		else if(frm.getFormAction().equalsIgnoreCase("search"))
  		{
  		  	int maxItems = Integer.parseInt(frm.getMaxItems());
  		  
	  		  /* For Valid and Invalid */
			  if (frm.getListValidEntries().equalsIgnoreCase("yes"))
			  {
		  		Filter temp = new Filter();
		  		temp.setFieldName("RADL_ISVALID");
		  		temp.setFieldValue("0");
		  		filter.add(temp);
			  }else if (frm.getListValidEntries().equalsIgnoreCase("no"))
			  {
		  		Filter temp = new Filter();
		  		temp.setFieldName("RADL_ISVALID");
		  		temp.setFieldValue("1");
		  		filter.add(temp);
			  }
		  
			/* By WorkOrderNo */
			if (!frm.getProWorkOrder().equalsIgnoreCase("0"))
			{
				Filter temp = new Filter();
				temp.setFieldName("WO_ID");
				temp.setFieldValue(frm.getProWorkOrder());
				filter.add(temp);
			}

			/* By Job name */
			if (!frm.getProJobName().equalsIgnoreCase(""))
			{
	   			Filter tempFilter = new Filter();
		   		tempFilter.setFieldName("JB_NAME");
				tempFilter.setFieldValue(frm.getProJobName());
			   	String specialFunction="";
				if(frm.getJobSelect().equals("0"))
				{
				   specialFunction = "Starts With";
				}
				else if(frm.getJobSelect().equals("1"))
				{
				   specialFunction = "Ends With";
				}
				else if(frm.getJobSelect().equals("2"))
				{
				   specialFunction = "Exactly";
				}
				else if(frm.getJobSelect().equals("3"))
				{
				   specialFunction = "Anywhere";
				}
				tempFilter.setSpecialFunction(specialFunction);
				filter.add(tempFilter);
			}

			/* By Post Flag */
		  	if (frm.getPostedView().equalsIgnoreCase("no"))
		  	{
		  		Filter temp = new Filter();
		  		temp.setFieldName("RADL_POST_FLG");
		  		temp.setFieldValue("0");
		  		filter.add(temp);
		  	}
		  	else if (frm.getPostedView().equalsIgnoreCase("yes"))
		  	{
		  		Filter temp = new Filter();
		  		temp.setFieldName("RADL_POST_FLG");
		  		temp.setFieldValue("1");
		  		filter.add(temp);
		  	}

			/* By Machine code */
			if (!frm.getProMachineCode().equalsIgnoreCase("0"))
			{
				Filter temp = new Filter();
				temp.setFieldName("MC_CDE");
				temp.setFieldValue(frm.getProMachineCode());
				filter.add(temp);
			}

			/* By Shift Name */
			if (!frm.getProShiftName().equalsIgnoreCase("0"))
			{
				Filter temp = new Filter();
				temp.setFieldName("SHIFT_ID");
				temp.setFieldValue(frm.getProShiftName());
				filter.add(temp);
			}
			
			/* By Production Current Date */
			if ((!frm.getProFromDate().equalsIgnoreCase("")) && (!frm.getProToDate().equalsIgnoreCase("")))
			{
				String[] month = {"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
				StringTokenizer frmDate = new StringTokenizer((frm.getProFromDate()).trim(),"-");
				String tempFrmDate[] = new String[frmDate.countTokens()];
				int i=0;
				while(frmDate.hasMoreTokens())
				{
					tempFrmDate[i] = frmDate.nextToken();
					if (BuildConfig.DMODE)
						System.out.println("Frm: "+tempFrmDate[i]);
					i++;
				}
				
				StringTokenizer toDate = new StringTokenizer((frm.getProToDate()).trim(),"-");
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
				String proDate = proFDate  + "$" + proTDate;
				if (BuildConfig.DMODE)
					System.out.println("Production Date: "+ proDate);
				Filter temp = new Filter();
				temp.setFieldName("RADL_CRNT_DATE");
				temp.setFieldValue(proDate);
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

 		HashMap rdlProObj = rdlProductionObj.getAllRadialProductionDetails(objFilter, frm.getSortField(), order, ((pageCount-1)*maxItems)+1, maxItems);
		if (BuildConfig.DMODE)
			System.out.println("Radial Production Details(HM): "+rdlProObj.size());
		
		vec_rdlProDet = (Vector) rdlProObj.get("RadialProductionDetails");
		
	 	pageContext.setAttribute("vec_rdlProDet",vec_rdlProDet);
	 	
	 	/* For WorkOrder No */
	 	HashMap proWOList = new HashMap();
	 	proWOList = rdlProductionObj.getWorkOrderList();
	 	pageContext.setAttribute("proWOList",proWOList);
	 	
	 	/* For Job Name */
	 	HashMap proJobName = new HashMap();
	 	proJobName = rdlProductionObj.getAllJobs();
	 	pageContext.setAttribute("proJobName",proJobName);
	 	
	 	/* For Machine Names */
	 	HashMap proMachineName = new HashMap();
	 	proMachineName = rdlProductionObj.getAllRadialMachines();
	 	pageContext.setAttribute("proMachineName",proMachineName);

	 	/* For Shift Name */
	 	HashMap proShiftName = new HashMap();
	 	proShiftName = rdlProductionObj.getShiftDefnNameList();
	 	pageContext.setAttribute("proShiftName",proShiftName);	 	
	 	
	  	int totalCount = Integer.parseInt((rdlProObj.get("TotalRecordCount")).toString()); 
	  	
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
			
		if (BuildConfig.DMODE)
			System.out.println("Radial Production List Ends.");
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
	   frmRdlProdList.submit;
	 }
 }
var oPopup;
	function init()
	 {
		oPopup = window.createPopup();
	 }
</script>
<script>
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
		
	function viewItem(val)
	{
	   document.forms[0].action = '<bean:message key="context"/>/frmRdlProdEdit.do?formAction=view&id='+val;
	   document.forms[0].submit();
	}
	
	function editItem()
	{
		if(modifyRow('rdlProdList', document.forms[0]))
		{
		   var val = getCheckedValueDetail('rdlProdList', document.forms[0]);
		   document.forms[0].action = '<bean:message key="context"/>/frmRdlProdEdit.do?formAction=modify&id='+val;
		   document.forms[0].submit();
		}
	}
	
	function makeInvalidItem()
	{
		if (validRow('rdlProdList', document.forms[0]))
		{
			var queryString = '<bean:message key="context"/>/frmRdlProdEdit.do?formAction=makeInvalid';
			var val = getMultipleCheckedValue('rdlProdList', document.forms[0]);
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
		var queryString = '<bean:message key="context"/>/frmRdlProdEdit.do?formAction=makeValid';
		var val = getMultipleCheckedValue('rdlProdList', document.forms[0]);
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
		temp.action = '<bean:message key="context"/>/Production/RadialProductionAdd.jsp';
		temp.submit();
	}
	
	function reLoadData()
	{
		temp = document.forms[0];
		if (temp.formAction.value == "view")
		{
			<%
				String srchFrmDate = request.getParameter("proFromDate");
				session.setAttribute("proFromDate",srchFrmDate);
				String srchToDate = request.getParameter("proToDate");
				session.setAttribute("proToDate",srchToDate);
				String srchWo = request.getParameter("proWorkOrder");
				session.setAttribute("proWorkOrder",srchWo);
				String srchJob = request.getParameter("proJobName");
				session.setAttribute("proJobName",srchJob);
				String srchJobCombo = request.getParameter("jobSelect");
				session.setAttribute("jobSelect",srchJobCombo);
				String srchMcCode = request.getParameter("proMachineCode");
				session.setAttribute("proMachineCode",srchMcCode);
				String srchSftName = request.getParameter("proShiftName");
				session.setAttribute("proShiftName",srchSftName);
				String viewAll = request.getParameter("listValidEntries");
				session.setAttribute("listValidEntries",viewAll);
				String srchPosted = request.getParameter("postedView");
				session.setAttribute("postedView",srchPosted);
			%>
		}
		else if (temp.formAction.value == "afterView") 
		{
			if ('<%= request.getParameter("fromDate") %>' != 'null')
				temp.proFromDate.value = '<%= request.getParameter("fromDate") %>';
			else
				temp.proFromDate.value = "";

			if ('<%= request.getParameter("proToDate") %>' != 'null')
				temp.proToDate.value = '<%= request.getParameter("proToDate") %>';
			else
				temp.proToDate.value = "";

			if ('<%= request.getParameter("woNo") %>' != 'null')
				temp.proWorkOrder.value = '<%= request.getParameter("woNo") %>';
			else
				temp.proWorkOrder.value = "0";

			if ('<%= request.getParameter("jbName") %>' != 'null')
				temp.proJobName.value = '<%= request.getParameter("jbName") %>';
			else
				temp.proJobName.value = "";

			if ('<%= request.getParameter("jbCombo") %>' != 'null')
				temp.jobSelect.value = '<%= request.getParameter("jbCombo") %>';
			else
				temp.jobSelect.value = "0";

			if ('<%= request.getParameter("mcCde") %>' != 'null')
				temp.proMachineCode.value = '<%= request.getParameter("mcCde") %>';
			else
				temp.proMachineCode.value = "0";

			if ('<%= request.getParameter("sftName") %>' != 'null')
				temp.proShiftName.value = '<%= request.getParameter("sftName") %>';
			else
				temp.proShiftName.value = "0";

			if ('<%= request.getParameter("viewAll") %>' != 'null')
				temp.listValidEntries.value = '<%= request.getParameter("viewAll") %>';
			else
				temp.listValidEntries.value = "all";

			if ('<%= request.getParameter("postedView") %>' != 'null')
				temp.postedView.value = '<%= request.getParameter("postedView") %>';
			else
				temp.postedView.value = "all";
		}
	}
	
</script>
</head>

<body onLoad="loadDefault(); init(); sortByTable('rdlProdList',<%= columnNumber%>,'<%= order%>'); reLoadData();">
<html:form action="frmRdlProdList">
<html:hidden property="formAction"/>
<html:hidden property="sortField"  value="<%= frm.getSortField()%>"/>
<html:hidden property="sortOrder" value="<%= frm.getSortOrder()%>"/>

<table width="100%" cellspacing="0" cellpadding="10">
<tr>
<td>
	<table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
	<tr>
		<td><bean:message key="prodacs.radialproduction.radialproduction"/></td>
	</tr>
	</table>
	<br>
	<table width="100" cellspacing="0" cellpadding="0" align="right">
	<tr> 
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New RadialProduction Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1019" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Modify RadialProduction Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1019" text="[ Modify ]" classId="TopLnk" onClick="javaScript:editItem();"/>
 		<menuconfig:userRights url="#" id="mkInValid" onMouseOver="window.status='Make RadialProduction Info InValid'; return true"  onMouseOut="window.status=''; return true" resourceId="1019" text="[ MakeInValid ]" classId="TopLnk" onClick="javaScript:makeInvalidItem();"/>
 		<menuconfig:userRights url="#" id="mkValid" onMouseOver="window.status='Make RadialProduction Info Valid'; return true"  onMouseOut="window.status=''; return true" resourceId="1019" text="[ MakeValid ]" classId="TopLnk" style="display:none" onClick="javaScript:makeValidItem();"/>
	</tr>
	</table>
	<br>
	<table width="100%" cellspacing="0" cellpadding="0">
	<tr> 
		<td width="20" id="FilterTip">&nbsp;</td>
		<td class="FilterTitle"><a href="javascript:showFilter(document.getElementById('Filter'), document.getElementById('FilterImg'))"><img src='<bean:message key="context"/>/images/hide.gif' width="9" height="5" border="0" align="absmiddle" id="FilterImg"><bean:message key="prodacs.production.filter"/></a></td>
		<td width="20" height="20" id="FilterEnd">&nbsp;</td>
	</tr>
	</table>
	<table width="100%" cellspacing="0" cellpadding="0" id="Filter">
      	<tr> 
        	<td width="70"><bean:message key="prodacs.workorder.fromdate"/></td>
	<td width="1">:</td>
  	<td width="200"><html:text property="proFromDate" styleClass="TextBox" size="12" readonly="true"/> 
        		<img src='<bean:message key="context"/>/images/calendar.gif' width="16" height="15" align="absmiddle" onClick='show_calendar("proFromDate",RadialProductionList.proFromDate.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"></td>
         <td width="80"><bean:message key="prodacs.workorder.todate"/></td>
  	<td width="1">:</td>
        	<td><html:text property="proToDate" styleClass="TextBox" size="12" readonly="true"/>
         	<img src='<bean:message key="context"/>/images/calendar.gif' width="16" height="15" align="absmiddle" onClick='show_calendar("proToDate",RadialProductionList.proToDate.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"></td>
	<td><bean:message key="prodacs.workorder.workorder"/></td>
         <td>:</td>
         <td><html:select property="proWorkOrder" styleClass="Combo">
		<html:option value="0">-- WO No --</html:option>
		<html:options collection="proWOList" property="key" labelProperty="value"/>
		</html:select> 
	</td>
	</tr>
      	<tr> 
        	<td><bean:message key="prodacs.job.jobname"/></td>
         <td>:</td>
      			<td><html:text property="proJobName" styleClass="TextBox"/>
				<html:select property="jobSelect" styleClass="Combo">
      	      	    	<html:option value="0">Starts With</html:option>
      	      	    	<html:option value="1">Ends With</html:option>
      	      	    	<html:option value="2">Exactly</html:option>
      	      	    	<html:option value="3">AnyWhere</html:option>
	      			</html:select></td>
        	<td><bean:message key="prodacs.machine.machinecode"/></td>
	<td>:</td>
  	<td><html:select property="proMachineCode" styleClass="Combo">
  	      	<html:option value="0">-- Machine Code --</html:option>
  	      	<html:options collection="proMachineName" property="key" labelProperty="key"/>
      		</html:select></td>
	<td><bean:message key="prodacs.nonproduction.shiftname"/></td>
	<td>:</td>
	<td><html:select property="proShiftName" styleClass="Combo">
				<html:option value="0">-- Shift Name --</html:option>
				<html:options collection="proShiftName" property="key" labelProperty="value"/>
			</html:select></td>
      	</tr>
      	<tr>
			<!--td colspan="6">&nbsp;</td-->
      	<td><bean:message key="prodacs.common.view"/>
      	<td>:</td>
      	<td>
      		<html:select property="listValidEntries" styleClass="Combo">
		<html:option value="all">All</html:option>
        		<html:option value="yes">InValid</html:option>
        		<html:option value="no">Valid</html:option>
      		</html:select> &nbsp;
      	</td>

      		<td><bean:message key="prodacs.workorder.status"/>
      		<td>:</td>
      		<td>
          		<html:select property="postedView" styleClass="Combo">
					<html:option value="all">All</html:option>
            		<html:option value="yes">Posted</html:option>
            		<html:option value="no">UnPosted</html:option>
          		</html:select>
			<html:submit styleClass="Button" onclick="navigation();">Go</html:submit></td>
		</tr>
    	</table><br>
	<table>
	<tr>
		<td>
		<html:messages id="messageid" message="true">
			<font class="message">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="messageid" /><br>
		</html:messages></font>
		</td>
	</tr>
	</table>
	<table>
	<tr> 
		<td colspan='2'> <font size="1px" face="Verdana"><html:errors/></font></td>
	</tr>
	</table>
	<table width="100%" cellpadding="0" cellspacing="0" id="rdlProdList">
      	<tr> 
		<td width="25" class="SortHeader"><input type="checkbox" name="CheckAll" value="checkbox" onClick="checkAll(document.RadialProductionList)"></td>
		<td width="130" class="SortHeader" onClick="sortTables(document.forms[0],this,'MC_CDE')"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"> 
		<bean:message key="prodacs.machine.machinecode"/></td>
		<td onClick="sortTables(document.forms[0],this,'RADL_CRNT_DATE')" width="110" class="SortHeader"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"> 
		<bean:message key="prodacs.job.date"/></td>
		<td onClick="sortTables(document.forms[0],this,'SHIFT_NAME')" width="110" class="SortHeader"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"> 
		<bean:message key="prodacs.production.shift"/></td>
		<td width="130" class="SortHeader" onClick="sortTables(document.forms[0],this,'WO_NO')"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"> 
		<bean:message key="prodacs.workorder.workorder"/></td>
		<td onClick="sortTables(document.forms[0],this,'JB_NAME')" class="SortHeader"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"> 
		<bean:message key="prodacs.job.jobname"/></td>
	</tr>
		<logic:iterate id="bt1" name="vec_rdlProDet" indexId="count">
		<bean:define id="bt2" name="bt1" property="objProductionJobDetails" />
      	<tr>
<%
	if(count.intValue()%2 == 0) 
	{
%>		
		<logic:equal name="bt1" property="radlIsValid" value="0"><td  class="InValid"></logic:equal>
	      	<logic:equal name="bt1" property="radlIsValid" value="1"><td  class="TableItems"></logic:equal>	
         
		<input name="CheckValue" type="checkbox" value='<bean:write name="bt1" property="radlId"/>' onClick="isSimilar(this)"></td>
		<td class="TableItems"><a href="#" onMouseOver="window.status='View RadialProduction Info'; return true"  onMouseOut="window.status=''; return true" id='<bean:write name="bt1" property="radlId"/>' onClick="viewItem(this.id);"><bean:write name="bt1" property="mcCode"/>&nbsp;</a></td>
		<td class="TableItems"><bean:define id="rdlCrntDate" name="bt1" property="radlCrntDate"/><%= rdlCrntDate.toString().substring(0,10) %>&nbsp;</td>
		<td class="TableItems"><bean:write name="bt1" property="shiftName"/>&nbsp;</td>
		<td class="TableItems"><bean:write name="bt1" property="woNo"/>&nbsp;</td>
		<td class="TableItems"><bean:write name="bt2" property="jobName"/>&nbsp;</td>
<%
	}
	else
	{
%>
		<logic:equal name="bt1" property="radlIsValid" value="0"><td  class="InValid"></logic:equal>
		<logic:equal name="bt1" property="radlIsValid" value="1"><td  class="TableItems2"></logic:equal>
         
		<input name="CheckValue" type="checkbox" value='<bean:write name="bt1" property="radlId"/>' onClick="isSimilar(this)"></td>
		<td class="TableItems2"><a href="#" onMouseOver="window.status='View RadialProduction Info'; return true"  onMouseOut="window.status=''; return true" id='<bean:write name="bt1" property="radlId"/>' onClick="viewItem(this.id);"><bean:write name="bt1" property="mcCode"/>&nbsp;</a></td>
		<td class="TableItems2"><bean:define id="rdlCrntDate1" name="bt1" property="radlCrntDate"/><%= rdlCrntDate1.toString().substring(0,10) %>&nbsp;</td>
		<td class="TableItems2"><bean:write name="bt1" property="shiftName"/>&nbsp;</td>
		<td class="TableItems2"><bean:write name="bt1" property="woNo"/>&nbsp;</td>
		<td class="TableItems2"><bean:write name="bt2" property="jobName"/>&nbsp;</td>
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
	</table>
</html:form>
</body>
</html:html>
