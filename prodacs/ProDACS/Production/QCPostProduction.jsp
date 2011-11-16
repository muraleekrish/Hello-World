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
<useradmin:userrights resource="53"/>
<%
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	GregorianCalendar geFrmDate = new GregorianCalendar();
  	GregorianCalendar geToDate = new GregorianCalendar();
	Vector vec_finalProDet = new Vector();
	Vector vec_DespatchDet = new Vector();

	try
	{
		/* 	Setting the JNDI name and Environment 	*/
	   	obj.setJndiName("SessionPostingDetailsBean");
   		obj.setEnvironment();

		/* 	Creating the Home and Remote Objects 	*/
   		SessionPostingDetailsHome postHomeObj = (SessionPostingDetailsHome)PortableRemoteObject.narrow(obj.getHome(),SessionPostingDetailsHome.class);
		SessionPostingDetails postObj = (SessionPostingDetails)PortableRemoteObject.narrow(postHomeObj.create(),SessionPostingDetails.class);
		  	
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

		if ((!frm.getWrkOrderFromDate().equalsIgnoreCase("")) && (!frm.getWrkOrderToDate().equalsIgnoreCase("")))
		{
			vec_finalProDet = postObj.viewUnPostedFinalProductionDetails(geFrmDate.getTime(),geToDate.getTime());
			if (BuildConfig.DMODE)
				System.out.println(vec_finalProDet.size());
			pageContext.setAttribute("vec_finalProDet",vec_finalProDet);
		}
		else
		{
			pageContext.setAttribute("vec_finalProDet",vec_finalProDet);
		}

		if ((!frm.getWrkOrderFromDate().equalsIgnoreCase("")) && (!frm.getWrkOrderToDate().equalsIgnoreCase("")))
		{
			vec_DespatchDet = postObj.viewUnPostedDespatchDetails(geFrmDate.getTime(),geToDate.getTime());
			if (BuildConfig.DMODE)
				System.out.println(vec_DespatchDet.size());
			pageContext.setAttribute("vec_DespatchDet",vec_DespatchDet);
		}
		else
		{
			pageContext.setAttribute("vec_DespatchDet",vec_DespatchDet);
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
<title><bean:message key="prodacs.finalposting.header"/></title>
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

	function makeTab(clicked,a)
	{
		clicked.className = "TabOn";
		a.className = "TabOff";
	}

	function showProdType()
	{
		if(t1.className == 'TabOn')
		{
			finalProd.style.display = "block";
			despatch.style.display = "none";
		}
		else if(t2.className == 'TabOn')
		{
			finalProd.style.display = "none";
			despatch.style.display = "block";
		}
	}

	function scrollhori()
	{
		finalHeader.scrollLeft = finalTable.scrollLeft;
		finalHeader.scrollRight = finalTable.scrollRight;
		despatchHeader.scrollLeft = despatchTable.scrollLeft;
		despatchHeader.scrollRight = despatchTable.scrollRight;
	}


	function chkAllFinal()
	{
		var temp = document.forms[0];
		
		if (temp.CheckValueFinal == undefined)
			return false;

		if (temp.CheckAllFinal.checked)
		{
			var i = 0;
			if (temp.CheckValueFinal.length != undefined)
			{
				if (temp.CheckValueFinal.length != 1)
				{
					while ( i < temp.CheckValueFinal.length)
					{
						temp.CheckValueFinal[i].checked = true;
						i++;
					}
				}
			}
			else
			{
				temp.CheckValueFinal.checked = true;
			}
		}
		else
		{
			var i = 0;
			if (temp.CheckValueFinal.length != undefined)
			{
				if (temp.CheckValueFinal.length != 1)
				{
					while ( i < temp.CheckValueFinal.length)
					{
						temp.CheckValueFinal[i].checked = false;
						i++;
					}
				}
			}
			else
			{
				temp.CheckValueFinal.checked = false;
			}
		}
	}

	function chkAllDespatch()
	{
		var temp = document.forms[0];
		
		if (temp.CheckValueDespatch == undefined)
			return false;

		if (temp.CheckAllDespatch.checked)
		{
			var i = 0;
			if (temp.CheckValueDespatch.length != undefined)
			{
				if (temp.CheckValueDespatch.length != 1)
				{
					while ( i < temp.CheckValueDespatch.length)
					{
						temp.CheckValueDespatch[i].checked = true;
						i++;
					}
				}
			}
			else
			{
				temp.CheckValueDespatch.checked = true;
			}
		}
		else
		{
			var i = 0;
			if (temp.CheckValueDespatch.length != undefined)
			{
				if (temp.CheckValueDespatch.length != 1)
				{
					while ( i < temp.CheckValueDespatch.length)
					{
						temp.CheckValueDespatch[i].checked = false;
						i++;
					}
				}
			}
			else
			{
				temp.CheckValueDespatch.checked = false;
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

		/* This is for Production Final Id's */
		var finalId = "";
		temp.hidProdFinal.value = "";
		var cnt = 0;
		if (temp.CheckValueFinal != undefined)
		{
			if (temp.CheckValueFinal.length != undefined)
			{
				for(var i = 0; i < temp.CheckValueFinal.length; i++)
				{
					if (temp.CheckValueFinal.length != 1)
					{
						if (temp.CheckValueFinal[i].checked)
						{
							finalId = finalId +"-"+ temp.CheckValueFinal[i].value;
							cnt++;
						}
					}
				}
			}
			else if (temp.CheckValueFinal.checked)
			{
				finalId = temp.CheckValueFinal.value;
				cnt++;
			}
		}

		temp.hidProdFinal.value = finalId;

		/* This is for Despatch Clearance Id's */
		var desId = "";
		temp.hidDespatch.value = "";
		if (temp.CheckValueDespatch != undefined)
		{
			if (temp.CheckValueDespatch.length != undefined)
			{
				for(var i = 0; i < temp.CheckValueDespatch.length; i++)
				{
					if (temp.CheckValueDespatch.length != 1)
					{
						if (temp.CheckValueDespatch[i].checked)
						{
							desId = desId +"-"+ temp.CheckValueDespatch[i].value;
							cnt++;
						}
					}
				}
			}
			else if (temp.CheckValueDespatch.checked)
			{
				desId = temp.CheckValueDespatch.value;
				cnt++;
			}
		}

		temp.hidDespatch.value = desId;
		
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
		var viewId = "";
		temp.hidView.value = "";
		var cnt = 0;

		/* This is for Production Final Id's */
		if (temp.CheckValueFinal != undefined)
		{
			if (temp.CheckValueFinal.length != undefined)
			{
				for(var i = 0; i < temp.CheckValueFinal.length; i++)
				{
					if (temp.CheckValueFinal.length != 1)
					{
						if (temp.CheckValueFinal[i].checked)
						{
							viewId = temp.CheckValueFinal[i].value;
							cnt++;
							temp.formAction.value = "fprodnview";
						}
					}
				}
			}
			else if (temp.CheckValueFinal.checked)
			{
				viewId = temp.CheckValueFinal.value;
				cnt++;
				temp.formAction.value = "fprodnview";
			}
		}
		temp.hidView.value = viewId;
		
		/* This is for Despatch Clearance Id's */
		if (temp.CheckValueDespatch != undefined)
		{
			if (temp.CheckValueDespatch.length != undefined)
			{
				for(var i = 0; i < temp.CheckValueDespatch.length; i++)
				{
					if (temp.CheckValueDespatch.length != 1)
					{
						if (temp.CheckValueDespatch[i].checked)
						{
							viewId = temp.CheckValueDespatch[i].value;
							cnt++;
							temp.formAction.value = "despatchView";
						}
					}
				}
			}
			else if (temp.CheckValueDespatch.checked)
			{
				viewId = temp.CheckValueDespatch.value;
				cnt++;
				temp.formAction.value = "despatchView";
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
			if (temp.formAction.value == "fprodnview")
			{
				temp.action = '<bean:message key="context"/>/frmProdFinalEdit.do?formAction=fprodnview&id='+temp.hidView.value+'&fDate='+temp.wrkOrderFromDate.value+'&tDate='+temp.wrkOrderToDate.value;
				temp.submit();
			}
			else if (temp.formAction.value == "despatchView")
			{
				temp.action = '<bean:message key="context"/>/frmdespatchClearanceEdit.do?formAction=despatchView&id='+temp.hidView.value+'&fDate='+temp.wrkOrderFromDate.value+'&tDate='+temp.wrkOrderToDate.value;
				temp.submit();
			}
		}
	}

	function valueLoading()
	{
		temp = document.forms[0];
		if ((temp.formAction.value == "searchFinal") ||
			(temp.formAction.value == "searchDespatch"))
		{
			temp.wrkOrderFromDate.value = '<%= request.getParameter("frmDate") %>';
			temp.wrkOrderToDate.value = '<%= request.getParameter("toDate") %>';
		}
		if (temp.formAction.value == "searchFinal")
		{
			t1.className = 'TabOn';
			t2.className = 'TabOff';
		}
		if (temp.formAction.value == "searchDespatch")
		{
			t1.className = 'TabOff';
			t2.className = 'TabOn';
		}
		showProdType();
	}
</script>
</head>

<body onLoad="init(); makeTab(t1,t2); valueLoading();">
<html:form action="frmPostProdQC">
<html:hidden property="formAction"/>
<html:hidden property="sortField"  value="<%= frm.getSortField()%>"/>
<html:hidden property="sortOrder" value="<%= frm.getSortOrder()%>"/>
<html:hidden property="id"/>
<html:hidden property="ids"/>
<html:hidden property="hidProdFinal"/>
<html:hidden property="hidDespatch"/>
<input type="hidden" name="hidView"/>
<table width="100%" cellspacing="0" cellpadding="10">
<tr>
	<td>
		<table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
		<tr>
			<td><bean:message key="prodacs.finalposting.header"/></td>
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
				<img src='<bean:message key="context"/>/images/calendar.gif' width="16" height="15" align="absmiddle" onClick='show_calendar("wrkOrderFromDate",QCPostProduction.wrkOrderFromDate.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"></td>
				<td width="60"><bean:message key="prodacs.production.todate"/></td>
				<td width="1">:</td>
				<td><html:text property="wrkOrderToDate" styleClass="TextBox" size="12" readonly="true"/> 
				<img src='<bean:message key="context"/>/images/calendar.gif' width="16" height="15" align="absmiddle" onClick='show_calendar("wrkOrderToDate",QCPostProduction.wrkOrderToDate.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"> 
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
				<td width="120" class="TabOff" id="t1" onClick="makeTab(this,t2); showProdType()"><bean:message key="prodacs.productionfinal.header"/>&nbsp;[<%=vec_finalProDet.size()%>]</td>
			<%
				}
				else
				{
			%>
				<td width="120" class="TabOff" id="t1" onClick="makeTab(this,t2); showProdType()"><bean:message key="prodacs.productionfinal.header"/></td>
			<%
				}
				if (frm.getFormAction().equalsIgnoreCase("search"))
				{
			%>
				<td width="90" class="TabOff" id="t2" onClick="makeTab(this,t1); showProdType()"><bean:message key="prodacs.postproduction.despatch"/>&nbsp;[<%=vec_DespatchDet.size()%>]</td>
			<%
				}
				else
				{
			%>
				<td width="90" class="TabOff" id="t2" onClick="makeTab(this,t1); showProdType()"><bean:message key="prodacs.postproduction.despatch"/></td>
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

	<table width="100%" cellpadding="0"cellspacing="0" id="finalProd" style="display:none">
	<tr>
		<td>
		<div id="finalHeader" style="width:739; height:141; overflow:scroll; "> <!-- scrollbar-3dlight-color:#FFFFFF; scrollbar-arrow-color:#FFFFFF; scrollbar-base-color:#FFFFFF; scrollbar-darkshadow-color:#FFFFFF; scrollbar-face-color:#FFFFFF; scrollbar-highlight-color:#FFFFFF; scrollbar-shadow-color:#FFFFFF; scrollbar-track-color:#FFFFFF;"> -->
		<table width="950" cellpadding="0" cellspacing="0">
		<tr> 
			<td width="25" class="Header"><input type="checkbox" id="CheckAllFinal" onclick="chkAllFinal();"></td>
			<td width="100" class="Header"><bean:message key="prodacs.production.date"/></td>
			<td width="75" class="Header"><bean:message key="prodacs.production.shift"/></td>
			<td width="100" class="Header"><bean:message key="prodacs.workorder.workorder"/></td>
			<td class="Header"><bean:message key="prodacs.job.jobname"/></td>
			<td width="80" class="Header"><bean:message key="prodacs.postproduction.serial"/></td>
			<td width="110" class="Header"><bean:message key="prodacs.postproduction.stoperation"/></td>
			<td width="110" class="Header"><bean:message key="prodacs.production.endoperation"/></td>
		</tr>
		</table>
		<table width="950" cellpadding="0" cellspacing="0" id="tblFinal">
		<logic:iterate id="bt" name="vec_finalProDet" indexId="count">
		<tr>
		<%
			if(count.intValue()%2 == 0) 
			{
		%>		
			<td width="22" class="TableItems"><input name="CheckValueFinal" type="checkbox" value='<bean:write name="bt" property="prodId"/>' ></td>
			<td width="97" class="TableItems"><bean:define id="prodCrntDate" name="bt" property="prodCrntDate"/><%= prodCrntDate.toString().substring(0,10) %></td>
			<td width="72" class="TableItems"><bean:write name="bt" property="shiftName"/></td>
			<td width="97" class="TableItems"><bean:write name="bt" property="woNo"/></td>
			<td class="TableItems"><bean:write name="bt" property="jobName"/></td>
            <td width="77" class="TableItems"><bean:write name="bt" property="jobQtySnos"/>&nbsp;</td>
			<td width="107" class="TableItems"><bean:write name="bt" property="prodStartOpn"/></td>
			<td width="107" class="TableItems"><bean:write name="bt" property="prodEndOpn"/></td>
		<%
			}
			else
			{
		%>
			<td width="22" class="TableItems2"><input name="CheckValueFinal" type="checkbox" value='<bean:write name="bt" property="prodId"/>' ></td>
			<td width="97" class="TableItems2"><bean:define id="prodCrntDate1" name="bt" property="prodCrntDate"/><%= prodCrntDate1.toString().substring(0,10) %></td>
			<td width="72" class="TableItems2"><bean:write name="bt" property="shiftName"/></td>
			<td width="97" class="TableItems2"><bean:write name="bt" property="woNo"/></td>
            <td class="TableItems2"><bean:write name="bt" property="jobName"/>&nbsp;</td>
			<td width="77" class="TableItems2"><bean:write name="bt" property="jobQtySnos"/></td>
			<td width="107" class="TableItems2"><bean:write name="bt" property="prodStartOpn"/></td>
			<td width="107" class="TableItems2"><bean:write name="bt" property="prodEndOpn"/></td>
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

	<table width="100%" cellpadding="0"cellspacing="0" id="despatch" style="display:none">
	<tr>
		<td>
		<div id="despatchHeader" style="width:739; height:141; overflow:scroll; "> <!-- scrollbar-3dlight-color:#FFFFFF; scrollbar-arrow-color:#FFFFFF; scrollbar-base-color:#FFFFFF; scrollbar-darkshadow-color:#FFFFFF; scrollbar-face-color:#FFFFFF; scrollbar-highlight-color:#FFFFFF; scrollbar-shadow-color:#FFFFFF; scrollbar-track-color:#FFFFFF;"> -->
		<table width="950" cellpadding="0" cellspacing="0">
		<tr> 
			<td width="25" class="Header"><input type="checkbox" id="CheckAllDespatch" onclick="chkAllDespatch();"></td>
			<td width="100" class="Header"><bean:message key="prodacs.production.date"/></td>
			<td width="75" class="Header"><bean:message key="prodacs.production.shift"/></td>
			<td width="100" class="Header"><bean:message key="prodacs.workorder.workorder"/></td>
			<td class="Header"><bean:message key="prodacs.job.jobname"/></td>
			<td width="80" class="Header"><bean:message key="prodacs.postproduction.serial"/></td>
		</tr>
		</table>
		<table width="950" cellpadding="0" cellspacing="0" id="tblDespatch">
		<logic:iterate id="bt" name="vec_DespatchDet" indexId="count">
		<tr>
		<%
			if(count.intValue()%2 == 0) 
			{
		%>		
			<td width="22" class="TableItems"><input name="CheckValueDespatch" type="checkbox" value='<bean:write name="bt" property="prodId"/>' ></td>
			<td width="97" class="TableItems"><bean:define id="prodCrntDate" name="bt" property="prodCrntDate"/><%= prodCrntDate.toString().substring(0,10) %></td>
			<td width="72" class="TableItems"><bean:write name="bt" property="shiftName"/></td>
			<td width="97" class="TableItems"><bean:write name="bt" property="woNo"/></td>
			<td class="TableItems"><bean:write name="bt" property="jobName"/></td>
            <td width="77" class="TableItems"><bean:write name="bt" property="jobQtySnos"/>&nbsp;</td>
		<%
			}
			else
			{
		%>
			<td width="22" class="TableItems2"><input name="CheckValueDespatch" type="checkbox" value='<bean:write name="bt" property="prodId"/>' ></td>
			<td width="97" class="TableItems2"><bean:define id="prodCrntDate1" name="bt" property="prodCrntDate"/><%= prodCrntDate1.toString().substring(0,10) %></td>
			<td width="72" class="TableItems2"><bean:write name="bt" property="shiftName"/></td>
			<td width="97" class="TableItems2"><bean:write name="bt" property="woNo"/></td>
            <td class="TableItems2"><bean:write name="bt" property="jobName"/>&nbsp;</td>
			<td width="77" class="TableItems2"><bean:write name="bt" property="jobQtySnos"/></td>
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
