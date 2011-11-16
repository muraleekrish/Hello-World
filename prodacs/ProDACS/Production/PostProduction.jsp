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
<useradmin:userrights resource="21"/>
<%
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	GregorianCalendar geFrmDate = new GregorianCalendar();
  	GregorianCalendar geToDate = new GregorianCalendar();
	Vector vec_proDet = new Vector();
	Vector vec_nonProDet = new Vector();
	Vector vec_popDet = new Vector();
	Vector vec_rdlProDet = new Vector();
		  	
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

	/* For Production Tab */
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
	 		vec_proDet = postObj.viewUnPostedProductionDetails(geFrmDate.getTime(),geToDate.getTime());
			if (BuildConfig.DMODE)
				System.out.println(vec_proDet.size());
			pageContext.setAttribute("vec_proDet",vec_proDet);
		}
		else
		{
			pageContext.setAttribute("vec_proDet",vec_proDet);
		}

		if ((!frm.getWrkOrderFromDate().equalsIgnoreCase("")) && (!frm.getWrkOrderToDate().equalsIgnoreCase("")))
		{
			vec_nonProDet = postObj.viewUnPostedNonProductionDetails(geFrmDate.getTime(),geToDate.getTime());
			if (BuildConfig.DMODE)
				System.out.println(vec_nonProDet.size());
			pageContext.setAttribute("vec_nonProDet",vec_nonProDet);
		}
		else
		{
			pageContext.setAttribute("vec_nonProDet",vec_nonProDet);
		}
		

		if ((!frm.getWrkOrderFromDate().equalsIgnoreCase("")) && (!frm.getWrkOrderToDate().equalsIgnoreCase("")))
		{
			vec_popDet = postObj.viewUnPostedPopProductionDetails(geFrmDate.getTime(),geToDate.getTime());
			if (BuildConfig.DMODE)
				System.out.println(vec_popDet.size());
			pageContext.setAttribute("vec_popDet",vec_popDet);
		}
		else
		{
			pageContext.setAttribute("vec_popDet",vec_popDet);
		}

		if ((!frm.getWrkOrderFromDate().equalsIgnoreCase("")) && (!frm.getWrkOrderToDate().equalsIgnoreCase("")))
		{
			vec_rdlProDet = postObj.viewUnPostedRadlProductionDetails(geFrmDate.getTime(),geToDate.getTime());
			if (BuildConfig.DMODE)
				System.out.println(vec_rdlProDet.size());
			pageContext.setAttribute("vec_rdlProDet",vec_rdlProDet);
		}
		else
		{
			pageContext.setAttribute("vec_rdlProDet",vec_rdlProDet);
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
<title><bean:message key="prodacs.common.header"/></title>
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
function makeTab(clicked,a,b,c)
	{
		clicked.className = "TabOn";
		a.className = "TabOff";
		b.className = "TabOff";
		c.className = "TabOff";
	}

function showProdType()
	{
		if(t1.className == 'TabOn')
		{
			production.style.display = "block";
			nonProduction.style.display = "none";
			popProd.style.display = "none";
			rdlProd.style.display = "none";
		}
		else if(t2.className == 'TabOn')
		{
			production.style.display = "none";
			nonProduction.style.display = "block";
			popProd.style.display = "none";
			rdlProd.style.display = "none";
		}
		else if(t3.className == 'TabOn')
		{
			production.style.display = "none";
			nonProduction.style.display = "none";
			popProd.style.display = "block";
			rdlProd.style.display = "none";
		}
		else if(t4.className == 'TabOn')
		{
			production.style.display = "none";
			nonProduction.style.display = "none";
			popProd.style.display = "none";
			rdlProd.style.display = "block";
		}
	}
	function scrollhori()
	{
        header.scrollRight = table.scrollRight;
        header.scrollLeft = table.scrollLeft;
		nonProdHeader.scrollRight = nonProdTable.scrollRight;
		nonProdHeader.scrollLeft = nonProdTable.scrollLeft;
		popHeader.scrollRight = popTable.scrollRight;
		popHeader.scrollLeft = popTable.scrollLeft;
		rdlHeader.scrollLeft = rdlTable.scrollLeft;
		rdlHeader.scrollRight = rdlTable.scrollRight;
	}

	function chkAllPro()
	{
		var temp = document.forms[0];

		if (temp.CheckValuePro == undefined)
			return false;

		if (temp.CheckAllPro.checked)
		{
			var i = 0;
			if (temp.CheckValuePro.length != undefined)
			{
				if (temp.CheckValuePro.length != 1)
				{
					while ( i < temp.CheckValuePro.length)
					{
						temp.CheckValuePro[i].checked = true;
						i++;
					}
				}
			}
			else
			{
				temp.CheckValuePro.checked = true;
			}
		}
		else
		{
			var i = 0;
			if (temp.CheckValuePro.length != undefined)
			{
				if (temp.CheckValuePro.length != 1)
				{
					while ( i < temp.CheckValuePro.length)
					{
						temp.CheckValuePro[i].checked = false;
						i++;
					}
				}
			}
			else
			{
				temp.CheckValuePro.checked = false;
			}
		}
	}

	function chkAllNonPro()
	{
		var temp = document.forms[0];
		if (temp.CheckValueNonPro == undefined)
			return false;

		if (temp.CheckAllNonPro.checked)
		{
			var i = 0;
			if (temp.CheckValueNonPro.length != undefined)
			{
				if (temp.CheckValueNonPro.length != 1)
				{
					while ( i < temp.CheckValueNonPro.length)
					{
						temp.CheckValueNonPro[i].checked = true;
						i++;
					}
				}
			}
			else
			{
				temp.CheckValueNonPro.checked = true;
			}
		}
		else
		{
			var i = 0;
			if (temp.CheckValueNonPro.length != undefined)
			{
				if (temp.CheckValueNonPro.length != 1)
				{
					while ( i < temp.CheckValueNonPro.length)
					{
						temp.CheckValueNonPro[i].checked = false;
						i++;
					}
				}
			}
			else
			{
				temp.CheckValueNonPro.checked = false;
			}
		}
	}

	function chkAllPop()
	{
		var temp = document.forms[0];
		if (temp.CheckValuePop == undefined)
			return false;

		if (temp.CheckAllPop.checked)
		{
			var i = 0;
			if (temp.CheckValuePop.length != undefined)
			{
				if (temp.CheckValuePop.length != 1)
				{
					while ( i < temp.CheckValuePop.length)
					{
						temp.CheckValuePop[i].checked = true;
						i++;
					}
				}
			}
			else
			{
				temp.CheckValuePop.checked = true;
			}
		}
		else
		{
			var i = 0;
			if (temp.CheckValuePop.length != undefined)
			{
				if (temp.CheckValuePop.length != 1)
				{
					while ( i < temp.CheckValuePop.length)
					{
						temp.CheckValuePop[i].checked = false;
						i++;
					}
				}
			}
			else
			{
				temp.CheckValuePop.checked = false;
			}
		}
	}

	function chkAllRdl()
	{
		var temp = document.forms[0];
		
		if (temp.CheckValueRdl == undefined)
			return false;

		if (temp.CheckAllRdl.checked)
		{
			var i = 0;
			if (temp.CheckValueRdl.length != undefined)
			{
				if (temp.CheckValueRdl.length != 1)
				{
					while ( i < temp.CheckValueRdl.length)
					{
						temp.CheckValueRdl[i].checked = true;
						i++;
					}
				}
			}
			else
			{
				temp.CheckValueRdl.checked = true;
			}
		}
		else
		{
			var i = 0;
			if (temp.CheckValueRdl.length != undefined)
			{
				if (temp.CheckValueRdl.length != 1)
				{
					while ( i < temp.CheckValueRdl.length)
					{
						temp.CheckValueRdl[i].checked = false;
						i++;
					}
				}
			}
			else
			{
				temp.CheckValueRdl.checked = false;
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

		/* This is for Production Id's */
		var prodId = "";
		temp.hidProd.value = "";
		var cnt = 0;
		if (temp.CheckValuePro != undefined)
		{
			if (temp.CheckValuePro.length != undefined)
			{
				for(var i = 0; i < temp.CheckValuePro.length; i++)
				{
					if (temp.CheckValuePro.length != 1)
					{
						if (temp.CheckValuePro[i].checked)
						{
							prodId = prodId +"-"+ temp.CheckValuePro[i].value;
							cnt++;
						}
					}
				}
			}
			else if (temp.CheckValuePro.checked)
			{
				prodId = temp.CheckValuePro.value;
				cnt++;
			}
		}
		temp.hidProd.value = prodId;

		/* This is for Non-production Id's */
		var nonProdId = "";
		temp.hidNonProd.value = "";
		if (temp.CheckValueNonPro != undefined)
		{
			if (temp.CheckValueNonPro.length != undefined)
			{
				for(var i = 0; i < temp.CheckValueNonPro.length; i++)
				{
					if (temp.CheckValueNonPro.length != 1)
					{
						if (temp.CheckValueNonPro[i].checked)
						{
							nonProdId = nonProdId +"-"+ temp.CheckValueNonPro[i].value;
							cnt++;
						}
					}
				}
			}
			else if (temp.CheckValueNonPro.checked)
			{
				nonProdId = temp.CheckValueNonPro.value;
				cnt++;
			}
		}

		temp.hidNonProd.value = nonProdId;

		/* This is for Pop Id's */
		var popId = "";
		temp.hidPop.value = "";
		if (temp.CheckValuePop != undefined)
		{
			if (temp.CheckValuePop.length != undefined)
			{
				for(var i = 0; i < temp.CheckValuePop.length; i++)
				{
					if (temp.CheckValuePop.length != 1)
					{
						if (temp.CheckValuePop[i].checked)
						{
							popId = popId +"-"+ temp.CheckValuePop[i].value;
							cnt++;
						}
					}
				}
			}
			else if (temp.CheckValuePop.checked)
			{
				popId = temp.CheckValuePop.value;
				cnt++;
			}
		}

		temp.hidPop.value = popId;

		/* This is for Radial Id's */
		var rdlId = "";
		temp.hidRdl.value = "";
		if (temp.CheckValueRdl != undefined)
		{
			if (temp.CheckValueRdl.length != undefined)
			{
				for(var i = 0; i < temp.CheckValueRdl.length; i++)
				{
					if (temp.CheckValueRdl.length != 1)
					{
						if (temp.CheckValueRdl[i].checked)
						{
							rdlId = rdlId +"-"+ temp.CheckValueRdl[i].value;
							cnt++;
						}
					}
				}
			}
			else if (temp.CheckValueRdl.checked)
			{
				rdlId = temp.CheckValueRdl.value;
				cnt++;
			}
		}

		temp.hidRdl.value = rdlId;

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
		/* This is for Production Id's */
		var viewId = "";
		temp.hidView.value = "";
		var cnt = 0;
		if (temp.CheckValuePro != undefined)
		{
			if (temp.CheckValuePro.length != undefined)
			{
				for(var i = 0; i < temp.CheckValuePro.length; i++)
				{
					if (temp.CheckValuePro.length != 1)
					{
						if (temp.CheckValuePro[i].checked)
						{
							viewId = temp.CheckValuePro[i].value;
							cnt++;
							temp.formAction.value = "prodnview";
						}
					}
				}
			}
			else if(temp.CheckValuePro.checked)
			{
				viewId = temp.CheckValuePro.value;
				cnt++;
				temp.formAction.value = "prodnview";
			}
		}
		temp.hidView.value = viewId;

		/* This is for Non-production Id's */
		if (temp.CheckValueNonPro != undefined)
		{
			if (temp.CheckValueNonPro.length != undefined)
			{
				for(var i = 0; i < temp.CheckValueNonPro.length; i++)
				{
					if (temp.CheckValuePro.length != 1)
					{
						if (temp.CheckValueNonPro[i].checked)
						{
							viewId = temp.CheckValueNonPro[i].value;
							cnt++;
							temp.formAction.value = "nonprodnview";
						}
					}
				}
			}
			else if (temp.CheckValueNonPro.checked)
			{
				viewId = temp.CheckValueNonPro.value;
				cnt++;
				temp.formAction.value = "nonprodnview";
			}
		}
		temp.hidView.value = viewId;

		/* This is for Pop Id's */
		if (temp.CheckValuePop != undefined)
		{
			if (temp.CheckValuePop.length != undefined)
			{
				for(var i = 0; i < temp.CheckValuePop.length; i++)
				{
					if (temp.CheckValuePop.length != 1)
					{
						if (temp.CheckValuePop[i].checked)
						{
							viewId = temp.CheckValuePop[i].value;
							cnt++;
							temp.formAction.value = "popview";
						}
					}
				}
			}
			else if (temp.CheckValuePop.checked)
			{
				viewId = temp.CheckValuePop.value;
				cnt++;
				temp.formAction.value = "popview";
			}
		}
		temp.hidView.value = viewId;

		/* This is for Radial Id's */
		if (temp.CheckValueRdl != undefined)
		{
			if (temp.CheckValueRdl.length != undefined)
			{
				for(var i = 0; i < temp.CheckValueRdl.length; i++)
				{
					if (temp.CheckValueRdl.length != 1)
					{
						if (temp.CheckValueRdl[i].checked)
						{
							viewId = temp.CheckValueRdl[i].value;
							cnt++;
							temp.formAction.value = "radlview";
						}
					}
				}
			}
			else if (temp.CheckValueRdl.checked)
			{
				viewId = temp.CheckValueRdl.value;
				cnt++;
				temp.formAction.value = "radlview";
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
			if (temp.formAction.value == "prodnview")
			{
				temp.action = '<bean:message key="context"/>/frmProdEdit.do?formAction=prodnview&id='+temp.hidView.value+'&fDate='+temp.wrkOrderFromDate.value+'&tDate='+temp.wrkOrderToDate.value;
				temp.submit();
			}
			else if (temp.formAction.value == "nonprodnview")
			{
				temp.action = '<bean:message key="context"/>/frmNonProdEdit.do?formAction=nonprodnview&id='+temp.hidView.value+'&fDate='+temp.wrkOrderFromDate.value+'&tDate='+temp.wrkOrderToDate.value;
				temp.submit();
			}
			else if (temp.formAction.value == "popview")
			{
				temp.action = '<bean:message key="context"/>/frmPayOutProdEdit.do?formAction=popview&id='+temp.hidView.value+'&fDate='+temp.wrkOrderFromDate.value+'&tDate='+temp.wrkOrderToDate.value;
				temp.submit();
			}
			else if (temp.formAction.value == "radlview")
			{
				temp.action = '<bean:message key="context"/>/frmRdlProdEdit.do?formAction=radlview&id='+temp.hidView.value+'&fDate='+temp.wrkOrderFromDate.value+'&tDate='+temp.wrkOrderToDate.value;
				temp.submit();
			}
		}
	}

	function valueLoading()
	{
		temp = document.forms[0];
		if ((temp.formAction.value == "searchProd") ||
			(temp.formAction.value == "searchNonProd") ||
			(temp.formAction.value == "searchPop") ||
			(temp.formAction.value == "searchRdl"))
		{
			temp.wrkOrderFromDate.value = '<%= request.getParameter("frmDate") %>';
			temp.wrkOrderToDate.value = '<%= request.getParameter("toDate") %>';
			temp.viewBack.value = '<%= request.getParameter("id") %>';
		}

		if (temp.formAction.value == "searchProd")
		{
			t1.className = 'TabOn'; 
			t2.className = 'TabOff';
			t3.className = 'TabOff';
			t4.className = 'TabOff';
		}
		if (temp.formAction.value == "searchNonProd")
		{
			t1.className = 'TabOff';
			t2.className = 'TabOn';
			t3.className = 'TabOff';
			t4.className = 'TabOff';
		}
		if (temp.formAction.value == "searchPop")
		{
			t1.className = 'TabOff';
			t2.className = 'TabOff';
			t3.className = 'TabOn';
			t4.className = 'TabOff';
		}
		if (temp.formAction.value == "searchRdl")
		{
			t1.className = 'TabOff';
			t2.className = 'TabOff';
			t3.className = 'TabOff';
			t4.className = 'TabOn';
		}
		showProdType();
	}

	function checkId()
	{
		temp = document.forms[0];
		if ((temp.formAction.value == "searchProd") && (temp.viewBack.value != ""))
		{
			if (temp.CheckValuePro != undefined)
			{
				if (temp.CheckValuePro.length != undefined)
				{
					for(var i = 0; i < temp.CheckValuePro.length; i++)
					{
						if (temp.CheckValuePro.length != 1)
						{
							if (parseInt(temp.viewBack.value) == parseInt(temp.CheckValuePro[i].value))
							{
								temp.CheckValuePro[i].checked = true;
							}
						}
					}
				}
				else
				{
					if (parseInt(temp.viewBack.value) == parseInt(temp.CheckValuePro.value))
					{
						temp.CheckValuePro.checked = true;
					}
				}
			}
		}
		else if ((temp.formAction.value == "searchNonProd") && (temp.viewBack.value != ""))
		{
			if (temp.CheckValueNonPro != undefined)
			{
				if (temp.CheckValueNonPro.length != undefined)
				{
					for(var i = 0; i < temp.CheckValueNonPro.length; i++)
					{
						if (temp.CheckValueNonPro.length != 1)
						{
							if (parseInt(temp.viewBack.value) == parseInt(temp.CheckValueNonPro[i].value))
							{
								temp.CheckValueNonPro[i].checked = true;
							}
						}
					}
				}
				else
				{
					if (parseInt(temp.viewBack.value) == parseInt(temp.CheckValueNonPro.value))
					{
						temp.CheckValueNonPro.checked = true;
					}
				}
			}
		}
		else if ((temp.formAction.value == "searchPop") && (temp.viewBack.value != ""))
		{
			if (temp.CheckValuePop != undefined)
			{
				if (temp.CheckValuePop.length != undefined)
				{
					for(var i = 0; i < temp.CheckValuePop.length; i++)
					{
						if (temp.CheckValuePop.length != 1)
						{
							if (parseInt(temp.viewBack.value) == parseInt(temp.CheckValuePop[i].value))
							{
								temp.CheckValuePop[i].checked = true;
							}
						}
					}
				}
				else
				{
					if (parseInt(temp.viewBack.value) == parseInt(temp.CheckValuePop.value))
					{
						temp.CheckValuePop.checked = true;
					}
				}
			}
		}
		else if ((temp.formAction.value == "searchRdl") && (temp.viewBack.value != ""))
		{
			if (temp.CheckValueRdl != undefined)
			{
				if (temp.CheckValueRdl.length != undefined)
				{
					for(var i = 0; i < temp.CheckValueRdl.length; i++)
					{
						if (temp.CheckValueRdl.length != 1)
						{
							if (parseInt(temp.viewBack.value) == parseInt(temp.CheckValueRdl[i].value))
							{
								temp.CheckValueRdl[i].checked = true;
							}
						}
					}
				}
				else
				{
					if (parseInt(temp.viewBack.value) == parseInt(temp.CheckValueRdl.value))
					{
						temp.CheckValueRdl.checked = true;
					}
				}
			}
		}
	}
</script>
</head>

<body onLoad="init(); makeTab(t1,t2,t3,t4);valueLoading(); checkId();">
<html:form action="frmPostProd">
<html:hidden property="formAction"/>
<html:hidden property="sortField"  value="<%= frm.getSortField()%>"/>
<html:hidden property="sortOrder" value="<%= frm.getSortOrder()%>"/>
<html:hidden property="id"/>
<html:hidden property="ids"/>
<html:hidden property="hidProd"/>
<html:hidden property="hidNonProd"/>
<html:hidden property="hidPop"/>
<html:hidden property="hidRdl"/>
<input type="hidden" name="hidView"/>
<input type="hidden" name="viewBack"/>
<table width="100%" cellspacing="0" cellpadding="10">
<tr>
	<td>
		<table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
		<tr>
			<td><bean:message key="prodacs.postproduction.postproduction"/></td>
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
				<img src='<bean:message key="context"/>/images/calendar.gif' width="16" height="15" align="absmiddle" onClick='show_calendar("wrkOrderFromDate",PostProduction.wrkOrderFromDate.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"></td>
				<td width="60"><bean:message key="prodacs.production.todate"/></td>
				<td width="1">:</td>
				<td><html:text property="wrkOrderToDate" styleClass="TextBox" size="12" readonly="true"/> 
				<img src='<bean:message key="context"/>/images/calendar.gif' width="16" height="15" align="absmiddle" onClick='show_calendar("wrkOrderToDate",PostProduction.wrkOrderToDate.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"> 
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
				<td width="130" class="TabOn" id="t1" onClick="makeTab(this,t2,t3,t4); showProdType()"><bean:message key="prodacs.production.header"/>&nbsp;[<%=vec_proDet.size()%>]</td>
			<%
				}
				else
				{
			%>
				<td width="130" class="TabOn" id="t1" onClick="makeTab(this,t2,t3,t4); showProdType()"><bean:message key="prodacs.production.header"/></td>
			<%
				}
				if (frm.getFormAction().equalsIgnoreCase("search"))
				{
			%>
				<td width="120" class="TabOff" id="t2" onClick="makeTab(this,t1,t3,t4); showProdType()"><bean:message key="prodacs.nonproduction.header"/>&nbsp;[<%=vec_nonProDet.size()%>]</td>
			<%
				}
				else
				{
			%>
				<td width="120" class="TabOff" id="t2" onClick="makeTab(this,t1,t3,t4); showProdType()"><bean:message key="prodacs.nonproduction.header"/></td>
			<%
				}
				if (frm.getFormAction().equalsIgnoreCase("search"))
				{
			%>
				<td width="140" class="TabOff" id="t3" onClick="makeTab(this,t1,t2,t4); showProdType()"><bean:message key="prodacs.production.payoutsideproduction"/>&nbsp;[<%=vec_popDet.size()%>]</td>
			<%
				}
				else
				{
			%>
				<td width="140" class="TabOff" id="t3" onClick="makeTab(this,t1,t2,t4); showProdType()"><bean:message key="prodacs.production.payoutsideproduction"/></td>
			<%
				}
				if (frm.getFormAction().equalsIgnoreCase("search"))
				{
			%>
				<td width="70" class="TabOff" id="t4" onClick="makeTab(this,t1,t2,t3); showProdType()"><bean:message key="prodacs.postproduction.radial"/>&nbsp;[<%=vec_rdlProDet.size()%>]</td>
			<%
				}
				else
				{
			%>
				<td width="70" class="TabOff" id="t4" onClick="makeTab(this,t1,t2,t3); showProdType()"><bean:message key="prodacs.postproduction.radial"/></td>
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
			<table width="100%" cellpadding="0"cellspacing="0" id="production">
			<tr>
				<td>
 				<div id="header" style="width:724; height:141; overflow:scroll;"> <!-- scrollbar-3dlight-color:#FFFFFF; scrollbar-arrow-color:#FFFFFF; scrollbar-base-color:#FFFFFF; scrollbar-darkshadow-color:#FFFFFF; scrollbar-face-color:#FFFFFF; scrollbar-highlight-color:#FFFFFF; scrollbar-shadow-color:#FFFFFF; scrollbar-track-color:#FFFFFF;"-->
				<table width="2100" cellpadding="0" cellspacing="0">
				<tr> 
					<td width="25" class="Header"><input type="checkbox" id="CheckAllPro" onclick="chkAllPro();"></td>
					<td width="75" class="Header"><bean:message key="prodacs.job.date"/></td>
					<td width="100" class="Header"><bean:message key="prodacs.production.machinecode"/></td>
					<td width="75" class="Header"><bean:message key="prodacs.production.shift"/></td>
					<td width="75" class="Header"><bean:message key="prodacs.postproduction.work"/></td>
					<td width="100" class="Header"><bean:message key="prodacs.workorder.workorder"/></td>
					<td width="120" class="Header"><bean:message key="prodacs.job.jobname"/></td>
					<td width="100" class="Header"><bean:message key="prodacs.postproduction.drawing"/></td>
					<td width="100" class="Header"><bean:message key="prodacs.postproduction.revision"/></td>
					<td width="120" class="Header"><bean:message key="prodacs.job.materialtype"/></td>
					<td class="Header"><bean:message key="prodacs.postproduction.serial"/></td>
					<td width="100" class="Header"><bean:message key="prodacs.postproduction.stoperation"/></td>
					<td width="100" class="Header"><bean:message key="prodacs.production.endoperation"/></td>
					<td width="75" class="Header"><bean:message key="prodacs.production.stdhrs"/></td>
					<td width="75" class="Header"><bean:message key="prodacs.postproduction.totalhrs"/></td>
				</tr>
				</table>
				<table width="2100" cellpadding="0" cellspacing="0" id="tblProduction">
				<logic:iterate id="bt1" name="vec_proDet" indexId="count">
          			<tr>
			<%
				if(count.intValue()%2 == 0)
				{
			%>		
			            <td width="22" class="TableItems"><input name="CheckValuePro" type="checkbox" value='<bean:write name="bt1" property="prodId"/>' ></td>
			            <td width="72" class="TableItems"><bean:define id="prdCrntDate" name="bt1" property="prodCrntDate"/><%= prdCrntDate.toString().substring(0,10) %></td>
			            <td width="97" class="TableItems"><bean:write name="bt1" property="mcCode"/></td>
			            <td width="72" class="TableItems"><bean:write name="bt1" property="shiftName"/></td>
						<td width="72" class="TableItems">
							<logic:equal name="bt1" property="prodWorkType" value="N">Normal</logic:equal>
							<logic:equal name="bt1" property="prodWorkType" value="R">Rework</logic:equal>
						</td>
			            <td width="97" class="TableItems"><bean:write name="bt1" property="woNo"/></td>
			            <td width="117" class="TableItems"><bean:write name="bt1" property="jobName"/></td>
			            <td width="97" class="TableItems"><bean:write name="bt1" property="jobDrwgNo"/></td>
			            <td width="97" class="TableItems"><bean:write name="bt1" property="jobRvsnNo"/></td>
			            <td width="117" class="TableItems"><bean:write name="bt1" property="jobMatlType"/></td>
			            <td class="TableItems"><input type="text" value='<bean:write name="bt1" property="jobQtySnos"/>' class="TextBoxFull" size="5"/></td>
			            <td width="97" class="TableItems"><bean:write name="bt1" property="prodStartOpn"/></td>
			            <td width="97" class="TableItems"><bean:write name="bt1" property="prodEndOpn"/></td>
			            <td width="72" class="TableItems"><bean:write name="bt1" property="prodStdHrs"/></td>
			            <td width="72" class="TableItems"><bean:write name="bt1" property="prodTotHrs"/></td>
			<%
				}
				else
				{
			%>
			            <td width="22" class="TableItems2"><input name="CheckValuePro" type="checkbox" value='<bean:write name="bt1" property="prodId"/>' ></td>
			            <td width="72" class="TableItems2"><bean:define id="prdCrntDate1" name="bt1" property="prodCrntDate"/><%= prdCrntDate1.toString().substring(0,10) %></td>
			            <td width="97" class="TableItems2"><bean:write name="bt1" property="mcCode"/></td>
			            <td width="72" class="TableItems2"><bean:write name="bt1" property="shiftName"/></td>
						<td width="72" class="TableItems2">
							<logic:equal name="bt1" property="prodWorkType" value="N">Normal</logic:equal>
							<logic:equal name="bt1" property="prodWorkType" value="R">Rework</logic:equal>
						</td>
			            <td width="97" class="TableItems2"><bean:write name="bt1" property="woNo"/></td>
			            <td class="TableItems2"><bean:write name="bt1" property="jobName"/></td>
			            <td width="97" class="TableItems2"><bean:write name="bt1" property="jobDrwgNo"/></td>
			            <td width="97" class="TableItems2"><bean:write name="bt1" property="jobRvsnNo"/></td>
			            <td width="117" class="TableItems2"><bean:write name="bt1" property="jobMatlType"/></td>
			         	<td class="TableItems2"><input type="text" value='<bean:write name="bt1" property="jobQtySnos"/>' class="TextBoxFull" size="5"/></td>
			            <td width="97" class="TableItems2"><bean:write name="bt1" property="prodStartOpn"/></td>
			            <td width="97" class="TableItems2"><bean:write name="bt1" property="prodEndOpn"/></td>
			            <td width="72" class="TableItems2"><bean:write name="bt1" property="prodStdHrs"/></td>
			            <td width="72" class="TableItems2"><bean:write name="bt1" property="prodTotHrs"/></td>
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
		<table width="100%" cellpadding="0"cellspacing="0" id="nonProduction" style="display:none">
		<tr>
			<td>
			<div id="nonProdHeader" style="width:739; height:141; overflow:scroll; "> 
				<table width="739" cellpadding="0" cellspacing="0">
				<tr> 
					<td width="25" class="Header"><input type="checkbox" id="CheckAllNonPro" onclick="chkAllNonPro()"></td>
					<td width="75" class="Header"><bean:message key="prodacs.job.date"/></td>
					<td width="100" class="Header"><bean:message key="prodacs.machine.machinecode"/></td>
					<td width="75" class="Header"><bean:message key="prodacs.production.shift"/></td>
					<td width="75" class="Header"><bean:message key="prodacs.postproduction.totalhrs"/></td>
					<td width="120" class="Header"><bean:message key="prodacs.nonproduction.idlebreakdown"/></td>
					<td class="Header"><bean:message key="prodacs.production.reason"/></td>
				</tr>
				</table>
				<table width="739" cellpadding="0" cellspacing="0" id="tblNonPro">
				<logic:iterate id="bt3" name="vec_nonProDet" indexId="count">
		          	<tr>
		<%
			if(count.intValue()%2 == 0) 
			{
		%>		
		            <td width="22"  class="TableItems"><input name="CheckValueNonPro" type="checkbox" id="CheckValue" value='<bean:write name="bt3" property="nonProdId"/>' ></td>
		            <td width="72" class="TableItems"><bean:define id="nonProdnCrntDate" name="bt3" property="nonProdnCrntDate"/><%= nonProdnCrntDate.toString().substring(0,10) %>&nbsp;</td>
		            <td width="97" class="TableItems"><bean:write name="bt3" property="mcCode"/>&nbsp;</td>
		            <td width="72" class="TableItems"><bean:write name="bt3" property="shiftName"/>&nbsp;</td>
		            <td width="72" class="TableItems"><bean:write name="bt3" property="nprodTotHrs"/>&nbsp;</td>
		            <td width="117" class="TableItems"><bean:write name="bt3" property="idlOrBkDown"/>&nbsp;</td>
		            <td class="TableItems"><bean:write name="bt3" property="idlOrBrkDwnRsn"/>&nbsp;</td>
		<%
			}
			else
			{
		%>
		            <td width="22" class="TableItems2"><input name="CheckValueNonPro" type="checkbox" id="CheckValue" value='<bean:write name="bt3" property="nonProdId"/>' ></td>
		            <td width="72" class="TableItems2"><bean:define id="nonProdnCrntDate1" name="bt3" property="nonProdnCrntDate"/><%= nonProdnCrntDate1.toString().substring(0,10) %>&nbsp;</td>
		            <td width="97" class="TableItems2"><bean:write name="bt3" property="mcCode"/>&nbsp;</td>
		            <td width="72" class="TableItems2"><bean:write name="bt3" property="shiftName"/>&nbsp;</td>
		            <td width="72" class="TableItems2"><bean:write name="bt3" property="nprodTotHrs"/>&nbsp;</td>
		            <td width="117" class="TableItems2"><bean:write name="bt3" property="idlOrBkDown"/>&nbsp;</td>
		            <td class="TableItems2"><bean:write name="bt3" property="idlOrBrkDwnRsn"/>&nbsp;</td>
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
		<table width="100%" cellpadding="0"cellspacing="0" id="popProd" style="display:none">
		<tr>
			<td>
			<div id="popHeader" style="width:739; height:141; overflow:scroll; ">
				<table width="739" cellpadding="0" cellspacing="0">
				<tr> 
					<td width="25" class="Header"><input type="checkbox" id="CheckAllPop" onclick="chkAllPop();"></td>
					<td width="150" class="Header"><bean:message key="prodacs.job.date"/></td>
					<td width="150" class="Header"><bean:message key="prodacs.production.shift"/></td>
					<td class="Header"><bean:message key="prodacs.workorder.reason"/></td>
				</tr>
				</table>
				<table width="739" cellpadding="0" cellspacing="0" id="tblPop">
				<logic:iterate id="bt5" name="vec_popDet" indexId="count">
		      	<tr>
		<%
			if(count.intValue()%2 == 0)
			{
		%>		
				<td width="22"  class="TableItems"><input name="CheckValuePop" type="checkbox" value='<bean:write name="bt5" property="popId"/>' ></td>
				<td width="147" class="TableItems"><bean:define id="popCrntDate" name="bt5" property="popCrntDate"/><%= popCrntDate.toString().substring(0,10) %>&nbsp;</td>
				<td width="147" class="TableItems"><bean:write name="bt5" property="shiftName"/>&nbsp;</td>
				<td class="TableItems"><bean:write name="bt5" property="popRsn"/>&nbsp;</td>
		<%
			}
			else
			{
		%>
				<td width="22" class="TableItems2"><input name="CheckValuePop" type="checkbox" value='<bean:write name="bt5" property="popId"/>' ></td>
				<td width="147" class="TableItems2"><bean:define id="popCrntDate1" name="bt5" property="popCrntDate"/><%= popCrntDate1.toString().substring(0,10) %>&nbsp;</td>
				<td width="147" class="TableItems2"><bean:write name="bt5" property="shiftName"/>&nbsp;</td>
				<td class="TableItems2"><bean:write name="bt5" property="popRsn"/>&nbsp;</td>
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
		<table width="100%" cellpadding="0"cellspacing="0" id="rdlProd" style="display:none">
		<tr>
			<td>
			<div id="rdlHeader" style="width:739; height:141; overflow:scroll; "> <!-- scrollbar-3dlight-color:#FFFFFF; scrollbar-arrow-color:#FFFFFF; scrollbar-base-color:#FFFFFF; scrollbar-darkshadow-color:#FFFFFF; scrollbar-face-color:#FFFFFF; scrollbar-highlight-color:#FFFFFF; scrollbar-shadow-color:#FFFFFF; scrollbar-track-color:#FFFFFF;"> -->
			<table width="2500" cellpadding="0" cellspacing="0">
			<tr> 
				<td width="25" class="Header"><input type="checkbox" id="CheckAllRdl" onclick="chkAllRdl();"></td>
				<td width="100" class="Header"><bean:message key="prodacs.production.date"/></td>
				<td width="110" class="Header"><bean:message key="prodacs.production.machinecode"/></td>
				<td width="75" class="Header"><bean:message key="prodacs.production.shift"/></td>
				<td width="75" class="Header"><bean:message key="prodacs.postproduction.work"/></td>
				<td width="100" class="Header"><bean:message key="prodacs.workorder.workorder"/></td>
				<td width="150" class="Header"><bean:message key="prodacs.job.jobname"/></td>
				<td width="100" class="Header"><bean:message key="prodacs.postproduction.drawing"/></td>
				<td width="100" class="Header"><bean:message key="prodacs.postproduction.revision"/></td>
				<td width="120" class="Header"><bean:message key="prodacs.job.materialtype"/></td>
				<td class="Header"><bean:message key="prodacs.postproduction.serial"/></td>
				<td width="110" class="Header"><bean:message key="prodacs.postproduction.stoperation"/></td>
				<td width="110" class="Header"><bean:message key="prodacs.production.endoperation"/></td>
				<td width="80" class="Header"><bean:message key="prodacs.production.stdhrs"/></td>
				<td width="80" class="Header"><bean:message key="prodacs.postproduction.totalhrs"/></td>
				<td width="100" class="Header"><bean:message key="prodacs.postproduction.diameter"/></td>
				<td width="80" class="Header"><bean:message key="prodacs.radialproduction.length"/></td>
				<td width="110" class="Header"><bean:message key="prodacs.radialproduction.noofholes"/></td>
				<td width="110" class="Header"><bean:message key="prodacs.radialproduction.prediameter"/></td>
			</tr>
			</table>
			<table width="2500" cellpadding="0" cellspacing="0" id="tblRadl">
			<logic:iterate id="bt" name="vec_rdlProDet" indexId="count">
	      	<tr>
	<%
		if(count.intValue()%2 == 0) 
		{
	%>		
			<td width="22" class="TableItems"><input name="CheckValueRdl" type="checkbox" value='<bean:write name="bt" property="radlProdId"/>' ></td>
			<td width="97" class="TableItems"><bean:define id="rdlPrdCrntDate" name="bt" property="radlProdCrntDate"/><%= rdlPrdCrntDate.toString().substring(0,10) %></td>
			<td width="107" class="TableItems"><bean:write name="bt" property="mcCode"/></td>
			<td width="72" class="TableItems"><bean:write name="bt" property="shiftName"/></td>
			<td width="72" class="TableItems">
				<logic:equal name="bt" property="radlProdWorkType" value="N">Normal</logic:equal>
				<logic:equal name="bt" property="radlProdWorkType" value="R">Rework</logic:equal>
			&nbsp;</td>
			<td width="97" class="TableItems"><bean:write name="bt" property="woNo"/></td>
	            <td width="147" class="TableItems"><bean:write name="bt" property="jobName"/>&nbsp;</td>
	            <td width="97" class="TableItems"><bean:write name="bt" property="jobDrwgNo"/>&nbsp;</td>
	            <td width="97" class="TableItems"><bean:write name="bt" property="jobRvsnNo"/>&nbsp;</td>
	            <td width="117" class="TableItems"><bean:write name="bt" property="jobMatlType"/>&nbsp;</td>
			<td class="TableItems"><input type="text" value='<bean:write name="bt" property="radlQtySnos"/>' class="TextBoxFull"/></td>
			<td width="107" class="TableItems"><bean:write name="bt" property="radlProdStartOpn"/></td>
			<td width="107" class="TableItems"><bean:write name="bt" property="radlProdEndOpn"/></td>
			<td width="77" class="TableItems"><bean:write name="bt" property="radlProdStdHrs"/></td>
			<td width="77" class="TableItems"><bean:write name="bt" property="radlProdTotHrs"/></td>
			<td width="97" class="TableItems"><bean:write name="bt" property="radlDmtr"/></td>
			<td width="77" class="TableItems"><bean:write name="bt" property="radlLength"/></td>
			<td width="107" class="TableItems"><bean:write name="bt" property="radlNoOfHoles"/></td>
			<td width="107" class="TableItems"><bean:write name="bt" property="radlPreDmtr"/></td>
	<%
		}
		else
		{
	%>
			<td width="22" class="TableItems2"><input name="CheckValueRdl" type="checkbox" value='<bean:write name="bt" property="radlProdId"/>' ></td>
			<td width="97" class="TableItems2"><bean:define id="rdlPrdCrntDate1" name="bt" property="radlProdCrntDate"/><%= rdlPrdCrntDate1.toString().substring(0,10) %></td>
			<td width="107" class="TableItems2"><bean:write name="bt" property="mcCode"/></td>
			<td width="72" class="TableItems2"><bean:write name="bt" property="shiftName"/></td>
			<td width="72" class="TableItems2">
				<logic:equal name="bt" property="radlProdWorkType" value="N">Normal</logic:equal>
				<logic:equal name="bt" property="radlProdWorkType" value="R">Rework</logic:equal>
			&nbsp;</td>
			<td width="97" class="TableItems2"><bean:write name="bt" property="woNo"/></td>
	            <td width="147" class="TableItems2"><bean:write name="bt" property="jobName"/>&nbsp;</td>
	            <td width="97" class="TableItems2"><bean:write name="bt" property="jobDrwgNo"/>&nbsp;</td>
	            <td width="97" class="TableItems2"><bean:write name="bt" property="jobRvsnNo"/>&nbsp;</td>
	            <td width="117" class="TableItems2"><bean:write name="bt" property="jobMatlType"/>&nbsp;</td>
			<td class="TableItems2"><input type="text" value='<bean:write name="bt" property="radlQtySnos"/>' class="TextBoxFull" size="5"/></td>
			<td width="107" class="TableItems2"><bean:write name="bt" property="radlProdStartOpn"/></td>
			<td width="107" class="TableItems2"><bean:write name="bt" property="radlProdEndOpn"/></td>
			<td width="77" class="TableItems2"><bean:write name="bt" property="radlProdStdHrs"/></td>
			<td width="77" class="TableItems2"><bean:write name="bt" property="radlProdTotHrs"/></td>
			<td width="97" class="TableItems2"><bean:write name="bt" property="radlDmtr"/></td>
			<td width="77" class="TableItems2"><bean:write name="bt" property="radlLength"/></td>
			<td width="107" class="TableItems2"><bean:write name="bt" property="radlNoOfHoles"/></td>
			<td width="107" class="TableItems2"><bean:write name="bt" property="radlPreDmtr"/></td>
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
