<%@ page language = "java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri = "/WEB-INF/struts-logic.tld" prefix = "logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<jsp:useBean id="frm" class="com.savantit.prodacs.presentation.tableadmin.production.NonProductionListForm" />
<jsp:setProperty name="frm" property="*" /> 

<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.savantit.prodacs.infra.beans.Filter"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<%@ page import="com.savantit.prodacs.facade.SessionNonProductionDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionNonProductionDetailsManager"%>
<%@ page import="java.util.StringTokenizer"%>
<useradmin:userrights resource="18"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("Non Production List.");
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	int totalPages = 0;
	int pageCount = 1 ;
	int columnNumber = 1;
	double nProdTotalHrs = 0;

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

	try
	{
		if (BuildConfig.DMODE)
			System.out.println("Non Production List Start.");
		
		/* 	Setting the JNDI name and Environment 	*/
	   	obj.setJndiName("SessionNonProductionDetailsManagerBean");
   		obj.setEnvironment();

		/* 	Creating the Home and Remote Objects 	*/
		SessionNonProductionDetailsManagerHome nonProHomeObj = (SessionNonProductionDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionNonProductionDetailsManagerHome.class);
		SessionNonProductionDetailsManager nonProductionObj = (SessionNonProductionDetailsManager)PortableRemoteObject.narrow(nonProHomeObj.create(),SessionNonProductionDetailsManager.class);
		
		/* Setting the SortFields */
		if(sortFieldName.equals("MC_CDE"))
		{
			columnNumber = 1;
		}
		else if(sortFieldName.equals("NPROD_CRNT_DATE"))
		{
			columnNumber = 2;
		}
		else if(sortFieldName.equals("SHIFT_NAME"))
		{
			columnNumber = 3;
		}
		else if(sortFieldName.equals("NPROD_IDL_BRKDWN"))
		{
			columnNumber = 4;
		}
		else if(sortFieldName.equals("NPROD_TOT_HRS"))
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
		  		temp.setFieldName("NPROD_POST_FLG");
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
			  if (frm.getNonProdValid().equalsIgnoreCase("yes"))
			  {
			  		Filter temp = new Filter();
			  		temp.setFieldName("NPROD_ISVALID");
			  		temp.setFieldValue("0");
			  		filter.add(temp);
			  }else if (frm.getNonProdValid().equalsIgnoreCase("no"))
			  {
			  		Filter temp = new Filter();
			  		temp.setFieldName("NPROD_ISVALID");
			  		temp.setFieldValue("1");
			  		filter.add(temp);
			  }

			/* By Post Flag */
		  	if (frm.getPostedView().equalsIgnoreCase("no"))
		  	{
		  		Filter temp = new Filter();
		  		temp.setFieldName("NPROD_POST_FLG");
		  		temp.setFieldValue("0");
		  		filter.add(temp);
		  	}
		  	else if (frm.getPostedView().equalsIgnoreCase("yes"))
		  	{
		  		Filter temp = new Filter();
		  		temp.setFieldName("NPROD_POST_FLG");
		  		temp.setFieldValue("1");
		  		filter.add(temp);
		  	}

			/* By Machine code */
			if (!frm.getNonProMachineCode().equalsIgnoreCase("0"))
			{
				Filter temp = new Filter();
				temp.setFieldName("MC_CDE");
				temp.setFieldValue(frm.getNonProMachineCode());
				filter.add(temp);
			}
			
			/* By Employee name */
			if (!frm.getNonProdEmplName().equalsIgnoreCase(""))
			{
	   			Filter tempFilter = new Filter();
		   		tempFilter.setFieldName("EMP_NAME");
				tempFilter.setFieldValue(frm.getNonProdEmplName());
			   	String specialFunction="";
				if(frm.getEmplSelect().equals("0"))
				{
				   specialFunction = "Starts With";
				}
				else if(frm.getEmplSelect().equals("1"))
				{
				   specialFunction = "Ends With";
				}
				else if(frm.getEmplSelect().equals("2"))
				{
				   specialFunction = "Exactly";
				}
				else if(frm.getEmplSelect().equals("3"))
				{
				   specialFunction = "Anywhere";
				}
				tempFilter.setSpecialFunction(specialFunction);
				filter.add(tempFilter);
			}

			/* By Shift Name */
			if (!frm.getNonProShiftName().equalsIgnoreCase("0"))
			{
				Filter temp = new Filter();
				temp.setFieldName("SHIFT_ID");
				temp.setFieldValue(frm.getNonProShiftName());
				filter.add(temp);
			}
			
			
			/* By Non Production Current Date */
			if ((!frm.getNonProFromDate().equalsIgnoreCase("")) && (!frm.getNonProToDate().equalsIgnoreCase("")))
			{
				String[] month = {"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
				StringTokenizer frmDate = new StringTokenizer((frm.getNonProFromDate()).trim(),"-");
				String tempFrmDate[] = new String[frmDate.countTokens()];
				int i=0;
				while(frmDate.hasMoreTokens())
				{
					tempFrmDate[i] = frmDate.nextToken();
					if (BuildConfig.DMODE)
						System.out.println("Frm: "+tempFrmDate[i]);
					i++;
				}
				
				StringTokenizer toDate = new StringTokenizer((frm.getNonProToDate()).trim(),"-");
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
					System.out.println("Non Production Date: "+ proDate);
				Filter temp = new Filter();
				temp.setFieldName("NPROD_CRNT_DATE");
				temp.setFieldValue(proDate);
				temp.setSpecialFunction("DateBetween");
				filter.add(temp);
			}

			Filter objFilter[] = new Filter[filter.size()];
			if (BuildConfig.DMODE)
				System.out.println("objFilter: "+objFilter);
  			filter.copyInto(objFilter);
  			session.setAttribute("objFilter",objFilter);
		}
		int maxItems = Integer.parseInt(frm.getMaxItems());
	   	int pagecount = Integer.parseInt(frm.getPage());
	   	if (BuildConfig.DMODE)
	   		System.out.println(maxItems+" & "+pagecount);
		
  		Filter objFilter[] = (Filter[])session.getAttribute("objFilter");
  		pageCount = Integer.parseInt(frm.getPage());

 		HashMap nonProductionDetails = nonProductionObj.getAllNonProductionDetails(objFilter, frm.getSortField(), order, ((pageCount-1)*maxItems)+1, maxItems);
		if (BuildConfig.DMODE)
			System.out.println("Non Production Details(HM): "+nonProductionDetails);
		
		Vector vec_nonProDet = (Vector) nonProductionDetails.get("NonProductionDetails");
		
	 	pageContext.setAttribute("vec_nonProDet",vec_nonProDet);
	 	
	 	/* For Machine Names */
	 	HashMap nonProMachineName = new HashMap();
	 	nonProMachineName = nonProductionObj.getAllMachines();
	 	pageContext.setAttribute("nonProMachineName",nonProMachineName);

	 	/* For Shift Name */
	 	HashMap nonProShiftName = new HashMap();
	 	nonProShiftName = nonProductionObj.getShiftDefnNameList();
	 	pageContext.setAttribute("nonProShiftName",nonProShiftName);
	 	
	  	int totalCount = Integer.parseInt((nonProductionDetails.get("TotalRecordCount")).toString()); 
		nProdTotalHrs = Double.parseDouble((nonProductionDetails.get("TotalNProdHrs")).toString());

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
			System.out.println("Non Production List Ends.");
	}
	catch(Exception e)
	{
		if (BuildConfig.DMODE)
	   		e.printStackTrace();
	}
	
%>
<html:html>
<head>
<title><bean:message key="prodacs.production.header"/></title>
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
	   NonProductionList.submit;
	 }
 }
var oPopup;
	function init()
	 {
		oPopup = window.createPopup();
	 }
</script>
<script language="Javascript" type="text/Javascript">

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
	   document.forms[0].action = '<bean:message key="context"/>/frmNonProdEdit.do?formAction=view&id='+val;
	   document.forms[0].submit();
	}
	
	function editItem()
	{
		if(modifyRow('nonProdList', document.forms[0]))
		{
		   var val = getCheckedValueDetail('nonProdList', document.forms[0]);
		   document.forms[0].action = '<bean:message key="context"/>/frmNonProdEdit.do?formAction=modify&id='+val;
		   document.forms[0].submit();
		}
	}
	
	function makeInvalidItem()
	{
		if (validRow('nonProdList', document.forms[0]))
		{
			var queryString = '<bean:message key="context"/>/frmNonProdEdit.do?formAction=makeInvalid';
			var val = getMultipleCheckedValue('nonProdList', document.forms[0]);
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
		var queryString = '<bean:message key="context"/>/frmNonProdEdit.do?formAction=makeValid';
		var val = getMultipleCheckedValue('nonProdList', document.forms[0]);
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
		document.forms[0].action = '<bean:message key="context"/>/Production/NonProductionAdd.jsp';
		document.forms[0].submit();
	}	
	
	function reLoadData()
	{
		temp = document.forms[0];
		if (temp.formAction.value == "afterView") 
		{
			if ('<%= request.getParameter("fromDate") %>' != 'null')
				temp.nonProFromDate.value = '<%= request.getParameter("fromDate") %>';
			else
				temp.nonProFromDate.value = "";

			if ('<%= request.getParameter("proToDate") %>' != 'null')
				temp.nonProToDate.value = '<%= request.getParameter("proToDate") %>';
			else
				temp.nonProToDate.value = "";

			if ('<%= request.getParameter("mcCde") %>' != 'null')
				temp.nonProMachineCode.value = '<%= request.getParameter("mcCde") %>';
			else
				temp.nonProMachineCode.value = "0";

			if ('<%= request.getParameter("sftName") %>' != 'null')
				temp.nonProShiftName.value = '<%= request.getParameter("sftName") %>';
			else
				temp.nonProShiftName.value = "0";

			if ('<%= request.getParameter("postedView") %>' != 'null')
				temp.postedView.value = '<%= request.getParameter("postedView") %>';
			else
				temp.postedView.value = "all";

			if ('<%= request.getParameter("viewAll") %>' != 'null')
				temp.nonProdValid.value = '<%= request.getParameter("viewAll") %>';
			else
				temp.nonProdValid.value = "all";

			if ('<%= request.getParameter("empName") %>' != 'null')
				temp.nonProdEmplName.value = '<%= request.getParameter("empName") %>';
			else
				temp.nonProdEmplName.value = "";

			if ('<%= request.getParameter("empNameCombo") %>' != 'null')
				temp.emplSelect.value = '<%= request.getParameter("empNameCombo") %>';
			else
				temp.emplSelect.value = "0";
		}
	}
</script>
</head>

<body onLoad="init(); loadDefault(); sortByTable('nonProdList',<%= columnNumber%>,'<%= order%>'); reLoadData();">
<html:form action="frmNonProdList">
<html:hidden property="formAction"/>
<html:hidden property="sortField"  value="<%= frm.getSortField()%>"/>
<html:hidden property="sortOrder" value="<%= frm.getSortOrder()%>"/>
  	<table width="100%" cellspacing="0" cellpadding="10" id="tbNonProduction">
	<tr>
      	<td>
	  	<table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
	          	<tr>
	            	<td><bean:message key="prodacs.nonproduction.header"/></td>
		 	</tr>
	    	</table><br>		
		<table width="100" cellspacing="0" cellpadding="0" align="right">
      	<tr> 
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New NonProduction Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1018" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Modify NonProduction Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1018" text="[ Modify ]" classId="TopLnk" onClick="javaScript:editItem();"/>
 		<menuconfig:userRights url="#" id="mkInValid" onMouseOver="window.status='Make NonProduction Info InValid'; return true"  onMouseOut="window.status=''; return true" resourceId="1018" text="[ MakeInValid ]" classId="TopLnk" onClick="javaScript:makeInvalidItem();"/>
 		<menuconfig:userRights url="#" id="mkValid" onMouseOver="window.status='Make NonProduction Info Valid'; return true"  onMouseOut="window.status=''; return true" resourceId="1018" text="[ MakeValid ]" classId="TopLnk" style="display:none" onClick="javaScript:makeValidItem();"/>
		</tr>
    		</table>
		<br>
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
      	      <td width="200"><html:text property="nonProFromDate" styleClass="TextBox" size="12" readonly="true"/> 
            	  <img src='<bean:message key="context"/>/images/calendar.gif' width="16" height="15" align="absmiddle" onClick='show_calendar("nonProFromDate",NonProductionList.nonProFromDate.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"></td>
	            <td width="80"><bean:message key="prodacs.workorder.todate"/></td>
      	      <td width="1">:</td>
            	<td><html:text property="nonProToDate" styleClass="TextBox" size="12" readonly="true"/>
	              <img src='<bean:message key="context"/>/images/calendar.gif' width="16" height="15" align="absmiddle" onClick='show_calendar("nonProToDate",NonProductionList.nonProToDate.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"></td>
	            <td><bean:message key="prodacs.common.view"/>
	      	<td>:</td>
	      	<td>
              		<html:select property="nonProdValid" styleClass="Combo">
				<html:option value="all">All</html:option>
                		<html:option value="yes">InValid</html:option>
                		<html:option value="no">Valid</html:option>
              		</html:select> &nbsp;
              	</td>
		</tr>
          	<tr> 
            	<td><bean:message key="prodacs.machine.machinecode"/></td>
	            <td>:</td>
      			<td><html:select property="nonProMachineCode" styleClass="Combo">
      	      	    <html:option value="0">-- Machine Code --</html:option>
      	      	    <html:options collection="nonProMachineName" property="key" labelProperty="key"/>
	      			</html:select></td>
	            <td><bean:message key="prodacs.nonproduction.shiftname"/></td>
	            <td>:</td>
	            <td>	<html:select property="nonProShiftName" styleClass="Combo">
	                		<html:option value="0">-- Shift Name --</html:option>
	                		<html:options collection="nonProShiftName" property="key" labelProperty="value"/>
	                	</html:select></td>
	      		<td><bean:message key="prodacs.workorder.status"/>
	      		<td>:</td>
	      		<td>
	          		<html:select property="postedView" styleClass="Combo">
						<html:option value="all">All</html:option>
	            		<html:option value="yes">Posted</html:option>
	            		<html:option value="no">UnPosted</html:option>
	          		</html:select>
				</td>
          	</tr>
			<tr>
				<td><bean:message key="prodacs.employee.employeename"/></td>
				<td>:</td>
				<td><html:text property="nonProdEmplName" styleClass="TextBox" size="15"/>
					<html:select property="emplSelect" styleClass="Combo">
      	      	    	<html:option value="0">Starts With</html:option>
      	      	    	<html:option value="1">Ends With</html:option>
      	      	    	<html:option value="2">Exactly</html:option>
      	      	    	<html:option value="3">AnyWhere</html:option>
	      			</html:select>
				</td>
				<td><html:submit styleClass="Button" onclick="navigation();">Go</html:submit></td>
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
		</table><br>
		<table width="100%" cellpadding="0" cellspacing="0" id="nonProdList">
          	<tr> 
	            <td width="25" class="SortHeader"><input type="checkbox" name="CheckAll" value="checkbox" onClick="checkAll(document.NonProductionList)"></td>
	            <td class="SortHeader" onClick="sortTables(document.forms[0],this,'MC_CDE')"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"> 
	              <bean:message key="prodacs.machine.machinecode"/></td>
	            <td onClick="sortTables(document.forms[0],this,'NPROD_CRNT_DATE')" width="80" class="SortHeader"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"> 
	              <bean:message key="prodacs.job.date"/></td>
	            <td onClick="sortTables(document.forms[0],this,'SHIFT_NAME')" width="80" class="SortHeader"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"> 
	              <bean:message key="prodacs.production.shift"/></td>
	            <td width="120" class="SortHeader" onClick="sortTables(document.forms[0],this,'NPROD_IDL_BRKDWN')"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"> 
	              <bean:message key="prodacs.nonproduction.idlebreakdown"/></td>
	            <td width="105" class="SortHeader" onClick="sortTables(document.forms[0],this,'NPROD_TOT_HRS')"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"> 
	              <bean:message key="prodacs.nonproduction.nonprodhrs"/></td>
		</tr>
		<logic:iterate id="bt3" name="vec_nonProDet" indexId="count">
          	<tr>
<%
	if(count.intValue()%2 == 0) 
	{
%>		
		<logic:equal name="bt3" property="nprodIsValid" value="0"><td  class="InValid"></logic:equal>
	      <logic:equal name="bt3" property="nprodIsValid" value="1"><td  class="TableItems"></logic:equal>	
	         
            <input name="CheckValue" type="checkbox" id="CheckValue" value='<bean:write name="bt3" property="nonProdId"/>' onClick="isSimilar(this)"></td>
            <td class="TableItems"><a href="#" onMouseOver="window.status='View NonProduction Info'; return true"  onMouseOut="window.status=''; return true" id='<bean:write name="bt3" property="nonProdId"/>' onClick="viewItem(this.id)"><bean:write name="bt3" property="mcCode"/>&nbsp;</a></td>
            <td class="TableItems"><bean:define id="prodCrntDate" name="bt3" property="nonProdnCrntDate"/><%= prodCrntDate.toString().substring(0,10) %>&nbsp;</td>
            <td class="TableItems"><bean:write name="bt3" property="shiftName"/>&nbsp;</td>
            <td class="TableItems"><bean:write name="bt3" property="idlOrBkDown"/>&nbsp;</td>
            <td class="TableItems"><bean:write name="bt3" property="nprodTotHrs"/>&nbsp;</td>
<%
	}
	else
	{
%>
		<logic:equal name="bt3" property="nprodIsValid" value="0"><td  class="InValid"></logic:equal>
	      <logic:equal name="bt3" property="nprodIsValid" value="1"><td  class="TableItems2"></logic:equal>	
	         
            <input name="CheckValue" type="checkbox" id="CheckValue" value='<bean:write name="bt3" property="nonProdId"/>' onClick="isSimilar(this)"></td>
            <td class="TableItems2"><a href="#" onMouseOver="window.status='View NonProduction Info'; return true"  onMouseOut="window.status=''; return true" id='<bean:write name="bt3" property="nonProdId"/>' onClick="viewItem(this.id)"><bean:write name="bt3" property="mcCode"/>&nbsp;</a></td>
            <td class="TableItems2"><bean:define id="prodCrntDate1" name="bt3" property="nonProdnCrntDate"/><%= prodCrntDate1.toString().substring(0,10) %>&nbsp;</td>
            <td class="TableItems2"><bean:write name="bt3" property="shiftName"/>&nbsp;</td>
            <td class="TableItems2"><bean:write name="bt3" property="idlOrBkDown"/>&nbsp;</td>
			<td class="TableItems2"><bean:write name="bt3" property="nprodTotHrs"/>&nbsp;</td>
<%
	}
%>
		</tr>
   	 	</logic:iterate>
        </table>
		<table width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td width="25">&nbsp;</td>
			<td>&nbsp;</td>
			<td width="80">&nbsp;</td>
			<td width="120" class="TableItems5">&nbsp;</td>
			<td width="120" class="SortHeader2"><b><bean:message key="prodacs.postproduction.totalhrs"/></b></td>
			<td width="105" class="SortHeader2"><b><%= nProdTotalHrs %></b></td>
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
  	</table>
</html:form>
</body>
</html:html>
