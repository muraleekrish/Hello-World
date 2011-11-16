<%@ page language="java"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<jsp:useBean id="frm" class="com.savantit.prodacs.presentation.tableadmin.production.PostProductionForm" />
<jsp:setProperty name="frm" property="*" /> 
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.StringTokenizer"%>
<%@ page import="java.util.GregorianCalendar"%>
<%@ page import="com.savantit.prodacs.facade.SessionPostingDetailsHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionPostingDetails"%>
<useradmin:userrights resource="54"/>
<%
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	GregorianCalendar geFrmDate = new GregorianCalendar();
  	GregorianCalendar geToDate = new GregorianCalendar();
	Vector vec_ShipmentDet = new Vector();
		  	
	if ((request.getParameter("frmDate") != null) && (request.getParameter("toDate") != null))
	{
		frm.setWrkOrderFromDate(request.getParameter("frmDate"));
		frm.setWrkOrderToDate(request.getParameter("toDate"));
	}

		/* By Production Current Date */
		if ((!frm.getWrkOrderFromDate().equalsIgnoreCase("")) && (!frm.getWrkOrderToDate().equalsIgnoreCase("")))
		{
			/* Date Conversion for From Date */
			StringTokenizer st = new StringTokenizer(frm.getWrkOrderFromDate(),"-");
			int yr = 0;
			int month = 0;
			int day = 0;
			if(st.hasMoreTokens())
			{
				yr = Integer.parseInt(st.nextToken().trim());
			}
			if(st.hasMoreTokens())
			{
				month = Integer.parseInt(st.nextToken().trim());
			}
			if(st.hasMoreTokens())
			{		
				day = Integer.parseInt(st.nextToken().trim());
			}
			geFrmDate = new GregorianCalendar(yr,month-1,day);

			/* Date Conversion for To Date */
			StringTokenizer st1 = new StringTokenizer(frm.getWrkOrderToDate(),"-");
			yr = 0;
			month = 0;
			day = 0;
			if(st1.hasMoreTokens())
			{
				yr = Integer.parseInt(st1.nextToken().trim());
			}
			if(st1.hasMoreTokens())
			{
				month = Integer.parseInt(st1.nextToken().trim());
			}
			if(st1.hasMoreTokens())
			{		
				day = Integer.parseInt(st1.nextToken().trim());
			}
			geToDate = new GregorianCalendar(yr,month-1,day);
		}
	/* For Shipment Tab */
	try
	{
		/* 	Setting the JNDI name and Environment 	*/
	   	obj.setJndiName("SessionPostingDetailsBean");
   		obj.setEnvironment();

		/* 	Creating the Home and Remote Objects 	*/
   		SessionPostingDetailsHome postHomeObj = (SessionPostingDetailsHome)PortableRemoteObject.narrow(obj.getHome(),SessionPostingDetailsHome.class);
		SessionPostingDetails postObj = (SessionPostingDetails)PortableRemoteObject.narrow(postHomeObj.create(),SessionPostingDetails.class);

		if ((!frm.getWrkOrderFromDate().equalsIgnoreCase("")) && (!frm.getWrkOrderToDate().equalsIgnoreCase("")))
		{
			vec_ShipmentDet = postObj.viewUnPostedShipmentDetails(geFrmDate.getTime(),geToDate.getTime());
			if (BuildConfig.DMODE)
				System.out.println(vec_ShipmentDet.size());
			pageContext.setAttribute("vec_ShipmentDet",vec_ShipmentDet);
		}
		else
		{
			pageContext.setAttribute("vec_ShipmentDet",vec_ShipmentDet);
		}
	}
	catch (Exception e)
	{
		if (BuildConfig.DMODE)
		{
			e.printStackTrace();
			System.out.println("Error in Posting Jsp.");
		}
	}
%>

<html:html>
<head>
<title><bean:message key="prodacs.shipmentposting.header"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script src='<bean:message key="context"/>/library/jscripts.js'></script>
<script src='<bean:message key="context"/>/library/calendar_search.js'></script>
<script>
var oPopup;
	function init()
	{
		oPopup = window.createPopup();
	}
	
	function makeTab(clicked)
	{
		clicked.className = "TabOn";
	}	 

	function showProdType()
	{
		if(t1.className == 'TabOn')
		{
			shipment.style.display = "block";
		}
	}

	function scrollhori()
	{
		shipmentHeader.scrollLeft = shipmentTable.scrollLeft;
		shipmentHeader.scrollRight = shipmentTable.scrollRight;
	}

	function chkAllShipment()
	{
		var temp = document.forms[0];
		
		if (temp.CheckValueShipment == undefined)
			return false;

		if (temp.CheckAllShipment.checked)
		{
			var i = 0;
			if (temp.CheckValueShipment.length != undefined)
			{
				if (temp.CheckValueShipment.length != 1)
				{
					while ( i < temp.CheckValueShipment.length)
					{
						temp.CheckValueShipment[i].checked = true;
						i++;
					}
				}
			}
			else
			{
				temp.CheckValueShipment.checked = true;
			}
		}
		else
		{
			var i = 0;
			if (temp.CheckValueShipment.length != undefined)
			{
				if (temp.CheckValueShipment.length != 1)
				{
					while ( i < temp.CheckValueShipment.length)
					{
						temp.CheckValueShipment[i].checked = false;
						i++;
					}
				}
			}
			else
			{
				temp.CheckValueShipment.checked = false;
			}
		}
	}

	function filterSearch()
	{  
		temp = document.forms[0];
		temp.formAction.value="search";
		temp.submit();
	}

	function postProduction()
	{
		var temp = document.forms[0];
		var cnt = 0;
		/* This is for Posting Shipment Id's */
		var sId = "";
		temp.hidShipment.value = "";
		if (temp.CheckValueShipment != undefined)
		{
			if (temp.CheckValueShipment.length != undefined)
			{
				for(var i = 0; i < temp.CheckValueShipment.length; i++)
				{
					if (temp.CheckValueShipment.length != 1)
					{
						if (temp.CheckValueShipment[i].checked)
						{
							sId = sId +"-"+ temp.CheckValueShipment[i].value;
							cnt++;
						}
					}
				}
			}
			else if (temp.CheckValueShipment.checked)
			{
				sId = temp.CheckValueShipment.value;
				cnt++;
			}
		}

		temp.hidShipment.value = sId;
		
		if (cnt == 0)
		{
			alert ("Check any Production to Post! ");
			return false;
		}
		
		temp.formAction.value = "posting";
		temp.submit();
	}

	function viewData()
	{
		temp = document.forms[0];
		var cnt = 0;
		/* This is for Shipment Id's */
		if (temp.CheckValueShipment != undefined)
		{
			if (temp.CheckValueShipment.length != undefined)
			{
				for(var i = 0; i < temp.CheckValueShipment.length; i++)
				{
					if (temp.CheckValueShipment.length != 1)
					{
						if (temp.CheckValueShipment[i].checked)
						{
							viewId = temp.CheckValueShipment[i].value;
							cnt++;
							temp.formAction.value = "shipmentView";
						}
					}
				}
			}
			else if (temp.CheckValueShipment.checked)
			{
				viewId = temp.CheckValueShipment.value;
				cnt++;
				temp.formAction.value = "shipmentView";
			}
		}
		temp.hidView.value = viewId;
		
		if (cnt == 0)
		{
			alert ("Check aleast one entry to View! ");
			return false;
		}
		else if (cnt > 1)
		{
			alert("More than one entry cannot be viewed atonce!");
			return false;
		}
		else if (cnt == 1)
		{
			if (temp.formAction.value == "shipmentView")
			{
				temp.action = '<bean:message key="context"/>/frmShipmentEdit.do?formAction=shipmentView&id='+temp.hidView.value+'&fDate='+temp.wrkOrderFromDate.value+'&tDate='+temp.wrkOrderToDate.value;
				temp.submit();
			}
		}
	}

	function valueLoading()
	{
		temp = document.forms[0];
		if (temp.formAction.value == "searchShipment")
		{
			temp.wrkOrderFromDate.value = '<%= request.getParameter("frmDate") %>';
			temp.wrkOrderToDate.value = '<%= request.getParameter("toDate") %>';
		}
		if (temp.formAction.value == "searchShipment")
		{
			t1.className = 'TabOn';
		}
		showProdType();
	}
</script>
</head>

<body onLoad="init(); makeTab(t1);valueLoading();">
<html:form action="frmShipPost">
<html:hidden property="formAction"/>
<html:hidden property="sortField"  value="<%= frm.getSortField()%>"/>
<html:hidden property="sortOrder" value="<%= frm.getSortOrder()%>"/>
<html:hidden property="id"/>
<html:hidden property="ids"/>
<html:hidden property="hidShipment"/>
<input type="hidden" name="hidView"/>
<table width="100%" cellspacing="0" cellpadding="10">
<tr>
	<td>
		<table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
		<tr>
			<td><bean:message key="prodacs.shipmentposting.header"/></td>
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
				<td width="70"><bean:message key="prodacs.workorder.fromdate"/></td>
				<td width="1">:</td>
				<td width="130"><html:text property="wrkOrderFromDate" styleClass="TextBox" size="12" readonly="true"/>
				<img src='<bean:message key="context"/>/images/calendar.gif' width="16" height="15" align="absmiddle" onClick='show_calendar("wrkOrderFromDate",ShipmentPostProduction.wrkOrderFromDate.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"></td>
				<td width="60"><bean:message key="prodacs.production.todate"/></td>
				<td width="1">:</td>
				<td><html:text property="wrkOrderToDate" styleClass="TextBox" size="12" readonly="true"/> 
				<img src='<bean:message key="context"/>/images/calendar.gif' width="16" height="15" align="absmiddle" onClick='show_calendar("wrkOrderToDate",ShipmentPostProduction.wrkOrderToDate.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"> 
				<html:submit styleClass="Button" onclick="filterSearch();">Go</html:submit></td>
			</tr>
			</table>
			<table><tr><td>
		<html:messages id="messageid" message="true">
		<font class="message">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="messageid" /><br>
		</html:messages></font>
		</td></tr></table>
		
		<table>
		<tr> 
		<td colspan='2'> <font size="1px" face="Verdana"><html:errors/></font></td>
		</tr>
		</table>
			<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
			<%
				if (frm.getFormAction().equalsIgnoreCase("search"))
				{
			%>
				<td width="90" class="TabOff" id="t1" onClick="makeTab(this); showProdType()"><bean:message key="prodacs.postproduction.shipment"/>&nbsp;[<%=vec_ShipmentDet.size()%>]</td>
			<%
				}
				else
				{
			%>
				<td width="90" class="TabOff" id="t1" onClick="makeTab(this); showProdType()"><bean:message key="prodacs.postproduction.shipment"/></td>
			<%
				}
			%>
				<td>&nbsp;</td>
			</tr>
			<tr> 
				<td colspan="7" id="TabBase"><img src='<bean:message key="context"/>/images/spacer.gif' width="1" height="5"></td>
			</tr>
			</table>
			<fieldset id="FieldSet">

	<table width="100%" cellpadding="0"cellspacing="0" id="shipment" style="display:none">
	<tr>
		<td>
		<div id="shipmentHeader" style="width:739; height:141; overflow:scroll; "> <!-- scrollbar-3dlight-color:#FFFFFF; scrollbar-arrow-color:#FFFFFF; scrollbar-base-color:#FFFFFF; scrollbar-darkshadow-color:#FFFFFF; scrollbar-face-color:#FFFFFF; scrollbar-highlight-color:#FFFFFF; scrollbar-shadow-color:#FFFFFF; scrollbar-track-color:#FFFFFF;"> -->
		<table width="950" cellpadding="0" cellspacing="0">
		<tr> 
			<td width="25" class="Header"><input type="checkbox" id="CheckAllShipment" onclick="chkAllShipment();"></td>
			<td width="100" class="Header"><bean:message key="prodacs.production.date"/></td>
			<td width="75" class="Header"><bean:message key="prodacs.production.shift"/></td>
			<td width="100" class="Header"><bean:message key="prodacs.workorder.workorder"/></td>
			<td class="Header"><bean:message key="prodacs.job.jobname"/></td>
			<td width="80" class="Header"><bean:message key="prodacs.postproduction.serial"/></td>
			<td width="150" class="Header"><bean:message key="prodacs.shipment.deliverychallanno"/></td>
		</tr>
		</table>
		<table width="950" cellpadding="0" cellspacing="0" id="tblDespatch">
		<logic:iterate id="bt" name="vec_ShipmentDet" indexId="count">
		<tr>
		<%
			if(count.intValue()%2 == 0) 
			{
		%>		
			<td width="22" class="TableItems"><input name="CheckValueShipment" type="checkbox" value='<bean:write name="bt" property="prodId"/>' ></td>
			<td width="97" class="TableItems"><bean:define id="prodCrntDate" name="bt" property="prodCrntDate"/><%= prodCrntDate.toString().substring(0,10) %></td>
			<td width="72" class="TableItems"><bean:write name="bt" property="shiftName"/></td>
			<td width="97" class="TableItems"><bean:write name="bt" property="woNo"/></td>
			<td class="TableItems"><bean:write name="bt" property="jobName"/></td>
            <td width="77" class="TableItems"><bean:write name="bt" property="jobQtySnos"/>&nbsp;</td>
            <td width="147" class="TableItems"><bean:write name="bt" property="companyDCNo"/>&nbsp;</td>
		<%
			}
			else
			{
		%>
			<td width="22" class="TableItems2"><input name="CheckValueShipment" type="checkbox" value='<bean:write name="bt" property="prodId"/>' ></td>
			<td width="97" class="TableItems2"><bean:define id="prodCrntDate1" name="bt" property="prodCrntDate"/><%= prodCrntDate1.toString().substring(0,10) %></td>
			<td width="72" class="TableItems2"><bean:write name="bt" property="shiftName"/></td>
			<td width="97" class="TableItems2"><bean:write name="bt" property="woNo"/></td>
            <td class="TableItems2"><bean:write name="bt" property="jobName"/>&nbsp;</td>
			<td width="77" class="TableItems2"><bean:write name="bt" property="jobQtySnos"/></td>
			<td width="147" class="TableItems"><bean:write name="bt" property="companyDCNo"/>&nbsp;</td>
		<%
			}
		%>
			</tr>
	   	 	</logic:iterate>
			</table>
			</div>
		</td>
	</tr>
	</table>

	</fieldset><br>
		<table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
		<tr>
			<td><html:button property="view" value="View Details" styleClass="Button" onclick="viewData();"/>&nbsp;
			<html:button property="post" value="Post" styleClass="Button" onclick="postProduction();"/></td>
		</tr>
		</table>
	</td>
</tr>
</table>
</html:form>
</body>
</html:html>
