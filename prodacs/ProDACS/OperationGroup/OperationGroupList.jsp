<%@ page language = "java" %>

<%@ taglib uri = "/WEB-INF/struts-html.tld" prefix = "html" %>
<%@ taglib uri = "/WEB-INF/struts-bean.tld" prefix = "bean" %>
<%@ taglib uri = "/WEB-INF/struts-logic.tld" prefix = "logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<jsp:useBean id="frm" class="com.savantit.prodacs.presentation.tableadmin.operationgroup.OperationGroupListForm" />
<jsp:setProperty name="frm" property="*" /> 

<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.savantit.prodacs.infra.beans.Filter"%>

<%@ page import="com.savantit.prodacs.facade.SessionJobDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionJobDetailsManager"%>
<%@ page import="com.savantit.prodacs.businessimplementation.job.OperationGroupDetails"%>
<useradmin:userrights resource="7"/>
<% 
	if (BuildConfig.DMODE)
		System.out.println("Operation Group List");
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	int totalPages = 0;
	int pageCount = 1 ;
	int columnNumber = 1;
	
	boolean order = true;
	/* Object for OperationGroupDetails */
	OperationGroupDetails objOperationGroupDetails = new OperationGroupDetails();
	
	String sortFieldName = frm.getSortField();
	if (BuildConfig.DMODE)
		System.out.println(sortFieldName);
	
	/* Setting the SortFields */
	if(sortFieldName.equals("OPN_GP_CODE"))
	{
		columnNumber=1;
	}
	else if(sortFieldName.equals("OPN_GP_MCRELATED"))
	{
		columnNumber=2;
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
		SessionJobDetailsManager opGpObj = (SessionJobDetailsManager)PortableRemoteObject.narrow(jobHomeObj.create(),SessionJobDetailsManager.class);

		if(frm.getFormAction().equals(""))
		{  
			Filter fil[] = new Filter[0];
	 		session.setAttribute("objFilter",fil);
  		}
  		else if(frm.getFormAction().equalsIgnoreCase("search"))
  		{
  		  int maxItems = Integer.parseInt(frm.getMaxItems());
  		  
  		  Vector filter = new Vector();
		  if (frm.getValidEntry().equalsIgnoreCase("yes"))
		  {
		  		Filter temp = new Filter();
		  		temp.setFieldName("OPN_GP_ISVALID");
		  		temp.setFieldValue("0");
		  		filter.add(temp);
		  }else if (frm.getValidEntry().equalsIgnoreCase("no"))
		  {
		  		Filter temp = new Filter();
		  		temp.setFieldName("OPN_GP_ISVALID");
		  		temp.setFieldValue("1");
		  		filter.add(temp);
		  }
		  if (BuildConfig.DMODE)
		  	System.out.println("frm.getTxtMachineName(): "+ frm.getTxtMachineName());
			if (!frm.getTxtMachineName().equals(""))
			{
				Filter temp = new Filter();
				temp.setFieldName("MC_NAME");
					
				temp.setFieldValue(frm.getTxtMachineName());
				String specialFunction="";
			   if(frm.getMachineName().equals("0"))
			   {
				   specialFunction = "Starts With";
			   }
			   else if(frm.getMachineName().equals("1"))
			   {
				   specialFunction = "Ends With";
			   }
			   else if(frm.getMachineName().equals("2"))
			   {
				   specialFunction = "Exactly";
			   }
			   else if(frm.getMachineName().equals("3"))
			   {
				   specialFunction = "Anywhere";
			   }
			   temp.setSpecialFunction(specialFunction);
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

 		HashMap opGpDetails = opGpObj.getAllOperationGroupDetails(objFilter, frm.getSortField(), order, ((pageCount-1)*maxItems)+1, maxItems);
		if (BuildConfig.DMODE)
			System.out.println("opGpDetails(HM): "+opGpDetails);
		
		Vector opnGrp = (Vector) opGpDetails.get("OperationGroupDetails");
	 	pageContext.setAttribute("opnGrp",opnGrp);

		/* Typecasting the entire vector to Details Object */
		for (int i =0; i < opnGrp.size(); i++)
		{
			objOperationGroupDetails = (OperationGroupDetails) opnGrp.get(i);
		}
		
		/* For getting the Machine Details in HashMap */

	 	HashMap machineDetails = new HashMap();
	 	machineDetails =  objOperationGroupDetails.getHm_MachineDetails();

		/* For Loading Machine Names in Filter Query */
		HashMap machineNames = new HashMap();
		machineNames = opGpObj.getAllMachines();
		pageContext.setAttribute("machineNames",machineNames);
		
	  	int totalCount = Integer.parseInt((opGpDetails.get("TotalRecordCount")).toString()); 
	  	
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
	   OperationGroupList.submit;
	 }
 }
</script>
<script language="Javascript" type="text/Javascript">
	function viewItem(val)
	{
	   document.forms[0].action = '<bean:message key="context"/>/frmOprGrpEdit.do?formAction=view&id='+val;
	   document.forms[0].submit();
	}
	
	function editItem()
	{
		if(modifyRow('oprGrpList', document.forms[0]))
		{
		   var val = getCheckedValueDetail('oprGrpList', document.forms[0]);
		   document.forms[0].action = '<bean:message key="context"/>/frmOprGrpEdit.do?formAction=modify&id='+val;
		   document.forms[0].submit();
		}
	}
	
	function addItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/OperationGroup/OperationGroupAdd.jsp';
		document.forms[0].submit();
	}
	
	
	function makeInvalidItem()
	{
		if (validRow('oprGrpList', document.forms[0]))
		{
			var queryString = '<bean:message key="context"/>/frmOprGrpEdit.do?formAction=makeInvalid';
			var val = getMultipleCheckedValue('oprGrpList', document.forms[0]);
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
		var queryString = '<bean:message key="context"/>/frmOprGrpEdit.do?formAction=makeValid';
		var val = getMultipleCheckedValue('oprGrpList', document.forms[0]);
		var subQuery = '';
		for(var i=0;i<val.length;i++)
		{
		   subQuery = subQuery+'&ids='+val[i];
		}
		queryString = queryString + subQuery;
		document.forms[0].action = queryString;
		document.forms[0].submit();
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
	
	function reLoadData()
	{
		temp = document.forms[0];
		if (temp.formAction.value == "view")
		{
			<%
				String srchMachName = request.getParameter("txtMachineName");
				session.setAttribute("txtMachineName",srchMachName);
				String srchMachCombo = request.getParameter("machineName");
				session.setAttribute("machineName",srchMachCombo);
				String viewAll = request.getParameter("validEntry");
				session.setAttribute("validEntry",viewAll);
			%>
		}
		else if (temp.formAction.value == "afterView") 
		{
			if ('<%= request.getParameter("machName") %>' != 'null')
				temp.txtMachineName.value = '<%= request.getParameter("machName") %>';
			else
				temp.txtMachineName.value = "";

			if ('<%= request.getParameter("machSrchTab") %>' != 'null')
				temp.machineName.value = '<%= request.getParameter("machSrchTab") %>';
			else
				temp.machineName.value = "0";

			if ('<%= request.getParameter("viewAll") %>' != 'null')
				temp.validEntry.value = '<%= request.getParameter("viewAll") %>';
			else
				temp.validEntry.value = "all";
		}
	}
</script>
</head>

<body onLoad="loadDefault();sortByTable('oprGrpList',<%= columnNumber%>,'<%= order%>'); reLoadData();">
<html:form action="frmOprGrpList">
<html:hidden property="formAction"/>
<html:hidden property="sortField"  value="<%= frm.getSortField()%>"/>
<html:hidden property="sortOrder" value="<%= frm.getSortOrder()%>"/>
  <table width="100%" cellspacing="0" cellpadding="10">
    <tr>
      <td><table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
          <tr>
            <td><bean:message key="prodacs.operationgroup.header"/></td>
          </tr>
        </table>
        <br>
        <table width="100" cellspacing="0" cellpadding="0" align="right">
          <tr> 
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New OperationGroup'; return true"  onMouseOut="window.status=''; return true" resourceId="1007" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Modify OperationGroup Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1007" text="[ Modify ]" classId="TopLnk" onClick="javaScript:editItem();"/>
 		<menuconfig:userRights url="#" id="mkInValid" onMouseOver="window.status='Make OperationGroup Info InValid'; return true"  onMouseOut="window.status=''; return true" resourceId="1007" text="[ MakeInValid ]" classId="TopLnk" onClick="javaScript:makeInvalidItem();"/>
 		<menuconfig:userRights url="#" id="mkValid" onMouseOver="window.status='Make OperationGroup Info Valid'; return true"  onMouseOut="window.status=''; return true" resourceId="1007" text="[ MakeValid ]" classId="TopLnk" style="display:none" onClick="javaScript:makeValidItem();"/>
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
            <td><bean:message key="prodacs.machine.machinename"/>
            	<html:text property="txtMachineName" styleClass="TextBox"/>
              <html:select property="machineName" styleClass="Combo">
	            <html:option value="0">Start With</html:option>
					<html:option value="1">End With</html:option>
					<html:option value="2">Exactly</html:option>
					<html:option value="3">AnyWhere</html:option>
	          </html:select> &nbsp;&nbsp;&nbsp;
              <bean:message key="prodacs.common.view"/>&nbsp;&nbsp;
                <html:select property="validEntry" styleClass="Combo">
	                <html:option value="all">All</html:option>
	                <html:option value="yes">InValid</html:option>
	                <html:option value="no">Valid</html:option>
                </html:select>&nbsp;&nbsp;&nbsp;
              <html:button styleClass="Button" property="search" onclick="navigation()" value="Go"/>
            </td>
          </tr>
        </table>
        <font class="message"><html:messages id="messageid" message="true">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="messageid" />
		   </html:messages>
		   </font>
			<table>
				<tr> 
					<td colspan='2'> <font size="1px" face="Verdana"><html:errors/></font></td>
				</tr>
			</table>
        
        <br>
        <table width="100%" cellpadding="0" cellspacing="0" id="oprGrpList">
          <tr> 
            <td width="25" class="SortHeader"><input name="CheckAll" type="checkbox" id="CheckAll" value="checkbox" onClick="checkAll(document.OperationGroupList)"></td>
            <td class="SortHeader" onClick="sortTables(document.forms[0],this,'OPN_GP_CODE')"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8"> 
              <bean:message key="prodacs.operationgroup.operationgroupcode"/></td>
			<td class="SortHeader" onClick="sortTables(document.forms[0],this,'OPN_GP_MCRELATED')"><img src='<bean:message key="context"/>/images/sort.gif' width="7" height="8">Machine Related</td>
          </tr>
          <logic:iterate id="bt1" name="opnGrp" indexId="count">
          <tr>
      <%
	      if(count.intValue()%2 == 0) 
   	   {
     	%>
				<logic:equal name="bt1" property="opnGpIsValid" value="0"><td  class="InValid"></logic:equal>
	         <logic:equal name="bt1" property="opnGpIsValid" value="1"><td  class="TableItems"></logic:equal>	
	         
            <input name="CheckValue" type="checkbox" id="CheckValue" value='<bean:write name="bt1" property="operationGroupId"/>' onClick="isSimilar(this)"></td>
            <td class="TableItems"><a href="#" onMouseOver="window.status='View OperationGroup Info'; return true" onMouseOut="window.status=''; return true" id='<bean:write name="bt1" property="operationGroupId"/>' onClick="viewItem(this.id);"><bean:write name="bt1" property="operationGroupCode"/>&nbsp;</a></td>
			<td class="TableItems" align="center">
				<logic:equal name="bt1" property="machineRelated" value="false"><img src='<bean:message key="context"/>/images/inactive.gif'></logic:equal>
				<logic:equal name="bt1" property="machineRelated" value="true"><img src='<bean:message key="context"/>/images/active.gif'></logic:equal>
			&nbsp;</td>
		<%
			} else
			{
		%>            
				<logic:equal name="bt1" property="opnGpIsValid" value="0"><td  class="InValid"></logic:equal>
	         <logic:equal name="bt1" property="opnGpIsValid" value="1"><td  class="TableItems2"></logic:equal>	
	         
            <input name="CheckValue" type="checkbox" id="CheckValue" value='<bean:write name="bt1" property="operationGroupId"/>' onClick="isSimilar(this)"></td>
            <td class="TableItems2"><a href="#" onMouseOver="window.status='View OperationGroup Info'; return true" onMouseOut="window.status=''; return true" id='<bean:write name="bt1" property="operationGroupId"/>' onClick="viewItem(this.id);"><bean:write name="bt1" property="operationGroupCode"/>&nbsp;</a></td>
			<td class="TableItems2" align="center">
				<logic:equal name="bt1" property="machineRelated" value="false"><img src='<bean:message key="context"/>/images/inactive.gif'></logic:equal>
				<logic:equal name="bt1" property="machineRelated" value="true"><img src='<bean:message key="context"/>/images/active.gif'></logic:equal>
			&nbsp;</td>
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
