<%@ page language = "java" %>

<%@ taglib uri = "/WEB-INF/struts-html.tld" prefix = "html" %>
<%@ taglib uri = "/WEB-INF/struts-bean.tld" prefix = "bean" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Vector"%>

<%@ page import="com.savantit.prodacs.facade.SessionWorkCalendarDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionWorkCalendarDetailsManager"%>
<%@ page import="com.savantit.prodacs.businessimplementation.workcalendar.BaseCalendarDetails"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<jsp:useBean id="frm" class="com.savantit.prodacs.presentation.tableadmin.workcalendar.AvailCalendarEditForm" />
<jsp:setProperty name="frm" property="*" /> 

<useradmin:userrights resource="1010"/>

<%
	if (BuildConfig.DMODE)
		System.out.println("Availability Edit");
	HashMap baseCalList = new HashMap();
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	BaseCalendarDetails objBaseCalendarDetails = new BaseCalendarDetails();
	String sftDetails = "";
	Vector vec_ShiftDetails = new Vector();
	String stEdDay = "";
	try
	{
		/* 	Setting the JNDI name and Environment 	*/
	   	obj.setJndiName("SessionWorkCalendarDetailsManagerBean");
   		obj.setEnvironment();

		/* 	Creating the Home and Remote Objects 	*/
		SessionWorkCalendarDetailsManagerHome availCalHomeObj = (SessionWorkCalendarDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionWorkCalendarDetailsManagerHome.class);
		SessionWorkCalendarDetailsManager availCalDefObj = (SessionWorkCalendarDetailsManager)PortableRemoteObject.narrow(availCalHomeObj.create(),SessionWorkCalendarDetailsManager.class);

		baseCalList = availCalDefObj.getBaseCalendarNameList();
		pageContext.setAttribute("baseCalList", baseCalList);

		HashMap shiftDefList = availCalDefObj.getShiftDefnNameList();
		pageContext.setAttribute("shiftDefList",shiftDefList);

	}catch (Exception e)
	{
		if (BuildConfig.DMODE)
			System.out.println("Error In Jsp: "+ e.toString());
	}
%>
<html:html>
<head>
<title><bean:message key="prodacs.common.header"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<!-- Default Scripts and CSS for Prodacs -->
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script src='<bean:message key="context"/>/library/jscripts.js'></script>
<script src='<bean:message key="context"/>/library/calendar_search.js'></script>

<script>
	var oPopup;
	function init()
	 {
		oPopup = window.createPopup();
	 }
</script>
<!-- Only for Availability Calendar Operations -->
<script language="Javascript" type="text/Javascript"> 
	var text = "";
	var nav = "";
	var day = new Array("Sun","Mon","Tue","Wed","Thu","Fri","Sat");
	var bigDay = new Array("Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday");
	var arMon = new Array("January","February","March","April","May","June","July","August","September","October","November","December");
	var selDates = new Array(); // Backup to Selected Dates
	var calFlag = true; // calendar Hover and Other Styles
	var calStyleFlag = false;
	var nonSelDates = new Array(); // Backup for Non-Avl Dates
	var avlSelDates = new Array(); // Backup for Avl Dates
	function calGeneration(mon,year)
	{
		text = "";
		nav = "";
		nav += '<table width="205" cellpadding="2" border="0" cellspacing="0" bgcolor="#C9C9C9" class="caldays">';
		nav += '<tr><td width="10" title="Previous Year" onclick="previousYear();"><img src=<bean:message key="context"/>/images/prev.gif></td><td width="10" title="Previous Month" onclick="previousMonth();"><img src=<bean:message key="context"/>/images/prev_arw.gif></td><td class="Header1">'+arMon[mon]+', '+year+'</td><td width="10" title="Next Month" onclick="nextMonth();"><img src=<bean:message key="context"/>/images/next_arw.gif></td><td width="10" title="Next Year" onclick="nextYear();"><img src=<bean:message key="context"/>/images/next.gif></td></tr>';
		nav += '</table>';

		text += '<table width="100" cellpadding="2" border="0" cellspacing="0" id="calendar" bgcolor="#F0EEE8" class="caldays">';
		text += '<tr bgcolor="#D3D3D3"><td width=15>Sun</td><td width=15>Mon</td><td width=15>Tue</td><td width=15>Wed</td><td width=15>Thu</td><td>Fri&nbsp;</td><td width=15>Sat</td></tr>';
		
		var c = 0;
		var temp = document.forms[0];
		var day1 = "";
		var day2 = "";
		var day3 = "";
		var day4 = "";
		var day5 = "";
		var day6 = "";
		var day7 = "";
		var i = 1;
		var flg = false;
		var today = new Date(year,mon,i);
		temp.crntMnthYear.value = year+"-"+mon;
		while (today.getMonth() == mon)
		{
			day1 = " "; day2 = " "; day3 = " "; day4 = " "; day5 = " "; day6 = " "; day7 = " ";
			var k = 0;
			while (k < 7)
			{
				if (flg == true)
				{
					flg = false;
					break;
				}

				today = new Date(year,mon,i);
				if (today.getMonth() != mon)
				{
					break;
				}
				if (day[today.getDay()] == "Sun")
				{
					day1 = today.getDate();
				}
				if (day[today.getDay()] == "Mon")
				{
					day2 = today.getDate();
				}
				if (day[today.getDay()] == "Tue")
				{
					day3 = today.getDate();
				}
				if (day[today.getDay()] == "Wed")
				{
					day4 = today.getDate();
				}
				if (day[today.getDay()] == "Thu")
				{
					day5 = today.getDate();
				}
				if (day[today.getDay()] == "Fri")
				{
					day6 = today.getDate();
				}
				if (day[today.getDay()] == "Sat")
				{
					day7 = today.getDate();
					flg = true;
				}
				i++;
				k++;
			}
			if (!((day1 == " ") && (day2 == " ") && (day3 == " ") && (day4 == " ") && (day5 == " ") && (day6 == " ") && (day7 == " ")))
			{
				//alert ("Day1 "+ day1 + " \nDay2 "+ day2 + " \nDay3 "+ day3+ " \nDay4 "+ day4 + " \nDay5 "+ day5+ " \nDay6 "+ day6+ " \nDay7 "+ day7);			
				text +=  '<tr align="center"><td onmouseout="clearStyle(this);" onmouseover="showHover(this);" onclick="showDate(this);">' + day1 + '</td><td onmouseout="clearStyle(this);" onmouseover="showHover(this);" onclick="showDate(this);">' + day2 + '</td><td onmouseout="clearStyle(this);" onmouseover="showHover(this);" onclick="showDate(this);">' + day3 + '</td><td onmouseout="clearStyle(this);" onmouseover="showHover(this);" onclick="showDate(this);">' + day4 + '</td><td onmouseout="clearStyle(this);" onmouseover="showHover(this);" onclick="showDate(this);">' + day5 + '</td><td onmouseout="clearStyle(this);" onmouseover="showHover(this);" onclick="showDate(this);">' + day6 + '</td><td onmouseout="clearStyle(this);" onmouseover="showHover(this);" onclick="showDate(this);">' + day7 + '</td></tr>';
			}
			c++;
		}
		text += '</table>';
		var obj = document.getElementById('navigation');
		obj.innerHTML = nav;
		var obj = document.getElementById('ccc');
		obj.innerHTML = text;
		setStyle();
	}
	
	function resStEd(date)
	{
		var temp = document.forms[0];
		strDate = (temp.fromDate.value).split('-'); /* Start Date*/
		stDate = new Date(strDate[0], parseInt(strDate[1],10)-1, strDate[2]);
		
		edDateSt = (temp.toDate.value).split('-'); /* End Date */
		edDate = new Date(edDateSt[0],parseInt(edDateSt[1],10)-1,edDateSt[2]);

		crMnthYear = (temp.crntMnthYear.value).split('-'); /* Checked Date */
		chkdDate = new Date(crMnthYear[0], crMnthYear[1], date);
		if (!(stDate <= chkdDate))
		{
			return false;
		}
		if (!(edDate >= chkdDate))
		{
			return false;
		}
		return true;
	}

	function setStyle()
	{
		var temp = document.forms[0];
		var obj = document.getElementById('ccc');
		var baseCalDays = "";

		if (temp.bcStarnEndDay.value != "")
		{
			bcDay = (temp.bcStarnEndDay.value).split('-');
			if (!parseInt(bcDay[0],10) > parseInt(bcDay[1],10))
			{
				for (var i = parseInt(bcDay[0],10); i <= parseInt(bcDay[1],10); i++)
				{
					baseCalDays = baseCalDays + " " + i;
				}
			}
			else
			{
				for (var i = parseInt(bcDay[0],10); i <= 7; i++)
					baseCalDays+=i+" ";
				for (var i = 1; i <= parseInt(bcDay[1],10); i++)
					baseCalDays+=i+" ";
			}
			for (var i = 0; i < obj.children(0).children(0).children.length; i++)
			{
				if (baseCalDays.indexOf("1") != -1) 
					obj.children(0).children(0).children(i).children(0).className = 'test';
				else
					obj.children(0).children(0).children(i).children(0).className = 'spl';
				
				if (baseCalDays.indexOf("2") != -1) 
					obj.children(0).children(0).children(i).children(1).className = 'test';
				else 
					obj.children(0).children(0).children(i).children(1).className = 'spl';

				if (baseCalDays.indexOf("3") != -1)
					obj.children(0).children(0).children(i).children(2).className = 'test';
				else
					obj.children(0).children(0).children(i).children(2).className = 'spl';

				if (baseCalDays.indexOf("4") != -1)
					obj.children(0).children(0).children(i).children(3).className = 'test';
				else
					obj.children(0).children(0).children(i).children(3).className = 'spl';

				if (baseCalDays.indexOf("5") != -1)
					obj.children(0).children(0).children(i).children(4).className = 'test';
				else
					obj.children(0).children(0).children(i).children(4).className = 'test';

				if (baseCalDays.indexOf("6") != -1)
					obj.children(0).children(0).children(i).children(5).className = 'test';
				else
					obj.children(0).children(0).children(i).children(5).className = 'test';

				if (baseCalDays.indexOf("7") != -1)
					obj.children(0).children(0).children(i).children(6).className = 'test';
				else
					obj.children(0).children(0).children(i).children(6).className = 'spl';
			}
		}
		else
		{
			for (var i = 0; i < obj.children(0).children(0).children.length; i++)
			{
				obj.children(0).children(0).children(i).children(0).className = 'spl';
				obj.children(0).children(0).children(i).children(1).className = 'test';
				obj.children(0).children(0).children(i).children(2).className = 'test';
				obj.children(0).children(0).children(i).children(3).className = 'test';
				obj.children(0).children(0).children(i).children(4).className = 'test';
				obj.children(0).children(0).children(i).children(5).className = 'test';
				obj.children(0).children(0).children(i).children(6).className = 'spl';
			}
		}
	}

	function callSelectedDays()
	{
		var temp = document.forms[0];
		var obj = document.getElementById('ccc');
		var obj1 = document.getElementById('tblCstmAvlDet');
		var avlNonAvl = "";
		var avlNonAvlDat = "";
		for (var i = 0; i < obj1.children(0).children.length; i++)
		{
			if (obj1.children(0).children(i).children(0).children(0).checked)
			{
				//avlNonAvl = obj1.children(0).children(i).children(2).children(0).value;
				var t1 = (obj1.children(0).children(i).children(2).children(0).value).split('-');
				avlNonAvl = parseInt(t1[0],10)+'-'+(parseInt(t1[1],10)-1)+'-'+parseInt(t1[2],10);
				avlNonAvlDat = obj1.children(0).children(i).children(3).children(0).value
			}
		}
		//alert ("avlNonAvl: "+avlNonAvl);
		for (var i = 1; i < obj.children(0).children(0).children.length; i++)
		{
			crDate = (temp.crntMnthYear.value) +"-"+ parseInt(obj.children(0).children(0).children(i).children(0).innerText);
			for (var j = 0; j < selDates.length; j++)
			{
				if (selDates[j] == crDate)
				{
					//alert (obj.children(0).children(0).children(i).children(0).className);
					if ((calStyleFlag) && (avlNonAvl == crDate) && (avlNonAvlDat == "Non-Availability"))
					{
						obj.children(0).children(0).children(i).children(0).style.color = 'red';
						obj.children(0).children(0).children(i).children(0).style.background = '#FFB895';
					}
					else if ((calStyleFlag) && (avlNonAvl == crDate) && (avlNonAvlDat == "Availability"))
					{
						obj.children(0).children(0).children(i).children(0).style.color = 'black';
						obj.children(0).children(0).children(i).children(0).style.background = '#8C8C8C';
					}
					else
					{
						if (obj.children(0).children(0).children(i).children(0).className != "spl")
						{
							obj.children(0).children(0).children(i).children(0).className = 'calendarSelected';	
						}
						else
						{
							obj.children(0).children(0).children(i).children(0).className = 'calendarSelected';
							obj.children(0).children(0).children(i).children(0).style.color = 'red';
						}
					}
				}
			}

			crDate = (temp.crntMnthYear.value) +"-"+ parseInt(obj.children(0).children(0).children(i).children(1).innerText);
			for (var j = 0; j < selDates.length; j++)
			{
				if (selDates[j] == crDate)
				{
					//alert (crDate+" & "+obj.children(0).children(0).children(i).children(1).className);
					if ((calStyleFlag) && (avlNonAvl == crDate) && (avlNonAvlDat == "Non-Availability"))
					{
						obj.children(0).children(0).children(i).children(1).style.color = 'red';
						obj.children(0).children(0).children(i).children(1).style.background = '#FFB895';
						//alert (obj.children(0).children(0).children(i).children(1).parentNode.innerHTML);
					}
					else if ((calStyleFlag) && (avlNonAvl == crDate) && (avlNonAvlDat == "Availability"))
					{
						obj.children(0).children(0).children(i).children(1).style.color = 'black';
						obj.children(0).children(0).children(i).children(1).style.background = '#8C8C8C';
					}
					else
					{
						if (obj.children(0).children(0).children(i).children(1).className != "spl")
						{
							obj.children(0).children(0).children(i).children(1).className = 'calendarSelected';	
						}
						else
						{
							obj.children(0).children(0).children(i).children(1).className = 'calendarSelected';
							obj.children(0).children(0).children(i).children(1).style.color = 'red';
						}
					}
				}
			}
			
			crDate = (temp.crntMnthYear.value) +"-"+ parseInt(obj.children(0).children(0).children(i).children(2).innerText);
			for (var j = 0; j < selDates.length; j++)
			{
				if (selDates[j] == crDate)
				{
					if ((calStyleFlag) && (avlNonAvl == crDate) && (avlNonAvlDat == "Non-Availability"))
					{
						obj.children(0).children(0).children(i).children(2).style.color = 'red';
						obj.children(0).children(0).children(i).children(2).style.background = '#FFB895';
					}
					else if ((calStyleFlag) && (avlNonAvl == crDate) && (avlNonAvlDat == "Availability"))
					{
						obj.children(0).children(0).children(i).children(2).style.color = 'black';
						obj.children(0).children(0).children(i).children(2).style.background = '#8C8C8C';
					}
					else
					{
						if (obj.children(0).children(0).children(i).children(2).className != "spl")
						{
							obj.children(0).children(0).children(i).children(2).className = 'calendarSelected';	
						}
						else
						{
							obj.children(0).children(0).children(i).children(2).className = 'calendarSelected';
							obj.children(0).children(0).children(i).children(2).style.color = 'red';
						}
					}
				}
			}

			crDate = (temp.crntMnthYear.value) +"-"+ parseInt(obj.children(0).children(0).children(i).children(3).innerText);
			for (var j = 0; j < selDates.length; j++)
			{
				if (selDates[j] == crDate)
				{
					if ((calStyleFlag) && (avlNonAvl == crDate) && (avlNonAvlDat == "Non-Availability"))
					{
						obj.children(0).children(0).children(i).children(3).style.color = 'red';
						obj.children(0).children(0).children(i).children(3).style.background = '#FFB895';
					}
					else if ((calStyleFlag) && (avlNonAvl == crDate) && (avlNonAvlDat == "Availability"))
					{
						obj.children(0).children(0).children(i).children(3).style.color = 'black';
						obj.children(0).children(0).children(i).children(3).style.background = '#8C8C8C';
					}
					else
					{
						if (obj.children(0).children(0).children(i).children(3).className != "spl")
						{
							obj.children(0).children(0).children(i).children(3).className = 'calendarSelected';	
						}
						else
						{
							obj.children(0).children(0).children(i).children(3).className = 'calendarSelected';
							obj.children(0).children(0).children(i).children(3).style.color = 'red';
						}
					}
				}
			}

			crDate = (temp.crntMnthYear.value) +"-"+ parseInt(obj.children(0).children(0).children(i).children(4).innerText);
			for (var j = 0; j < selDates.length; j++)
			{
				if (selDates[j] == crDate)
				{
					if ((calStyleFlag) && (avlNonAvl == crDate) && (avlNonAvlDat == "Non-Availability"))
					{
						obj.children(0).children(0).children(i).children(4).style.color = 'red';
						obj.children(0).children(0).children(i).children(4).style.background = '#FFB895';
					}
					else if ((calStyleFlag) && (avlNonAvl == crDate) && (avlNonAvlDat == "Availability"))
					{
						obj.children(0).children(0).children(i).children(4).style.color = 'black';
						obj.children(0).children(0).children(i).children(4).style.background = '#8C8C8C';
					}
					else
					{
						if (obj.children(0).children(0).children(i).children(4).className != "spl")
						{
							obj.children(0).children(0).children(i).children(4).className = 'calendarSelected';	
						}
						else
						{
							obj.children(0).children(0).children(i).children(4).className = 'calendarSelected';
							obj.children(0).children(0).children(i).children(4).style.color = 'red';
						}
					}
				}
			}

			crDate = (temp.crntMnthYear.value) +"-"+ parseInt(obj.children(0).children(0).children(i).children(5).innerText);
			for (var j = 0; j < selDates.length; j++)
			{
				if (selDates[j] == crDate)
				{
					if ((calStyleFlag) && (avlNonAvl == crDate) && (avlNonAvlDat == "Non-Availability"))
					{
						obj.children(0).children(0).children(i).children(5).style.color = 'red';
						obj.children(0).children(0).children(i).children(5).style.background = '#FFB895';
					}
					else if ((calStyleFlag) && (avlNonAvl == crDate) && (avlNonAvlDat == "Availability"))
					{
						obj.children(0).children(0).children(i).children(5).style.color = 'black';
						obj.children(0).children(0).children(i).children(5).style.background = '#8C8C8C';
					}
					else
					{
						if (obj.children(0).children(0).children(i).children(5).className != "spl")
						{
							obj.children(0).children(0).children(i).children(5).className = 'calendarSelected';	
						}
						else
						{
							obj.children(0).children(0).children(i).children(5).className = 'calendarSelected';
							obj.children(0).children(0).children(i).children(5).style.color = 'red';
						}
					}
				}
			}

			crDate = (temp.crntMnthYear.value) +"-"+ parseInt(obj.children(0).children(0).children(i).children(6).innerText);
			for (var j = 0; j < selDates.length; j++)
			{
				if (selDates[j] == crDate)
				{
					if ((calStyleFlag) && (avlNonAvl == crDate) && (avlNonAvlDat == "Non-Availability"))
					{
						obj.children(0).children(0).children(i).children(6).style.color = 'red';
						obj.children(0).children(0).children(i).children(6).style.background = '#FFB895';
					}
					else if ((calStyleFlag) && (avlNonAvl == crDate) && (avlNonAvlDat == "Availability"))
					{
						obj.children(0).children(0).children(i).children(6).style.color = 'black';
						obj.children(0).children(0).children(i).children(6).style.background = '#8C8C8C';
					}
					else
					{
						if (obj.children(0).children(0).children(i).children(6).className != "spl")
						{
							obj.children(0).children(0).children(i).children(6).className = 'calendarSelected';	
						}
						else
						{
							obj.children(0).children(0).children(i).children(6).className = 'calendarSelected';
							obj.children(0).children(0).children(i).children(6).style.color = 'red';
						}
					}
				}
			}
		}
		calStyleFlag = false;
	}

	function showDate(obj)
	{
		var temp = document.forms[0];
		var unSelDay = "";
		if (!calFlag)// To Disable Calendar Styles
		{
			return false;
		}

		if (resStEd(obj.innerText))
		{
			if (obj.innerText == "")
			{
				obj.className = '';
				return false;
			}
			if (obj.className == 'calendarSelected')
			{
				obj.className = 'test';
				unSelDay = temp.crntMnthYear.value + "-" + obj.innerText;	
				for (var i = 0; i < selDates.length; i++)
				{
					if (selDates[i] == unSelDay)
					{
						selDates[i] = null;
						for (var k = i; k < selDates.length; k++)
						{
							selDates[k] = selDates[k+1];
						}
						selDates.length = selDates.length - 1;
						break;
					}
				}
			}
			else if ((obj.className == 'calendarNonAvlSelected') || (obj.className == 'calendarAvlSelected'))
			{
				obj.className = 'test';
				selDates[selDates.length] = temp.crntMnthYear.value + "-" + obj.innerText;
			}
			else
			{
				obj.className = 'calendarSelected';
				selDates[selDates.length] = temp.crntMnthYear.value + "-" + obj.innerText;
			}
			//alert ("selDates :"+selDates);
		}
	}
	
	function showHover(obj)
	{
		if (!calFlag)// To Disable Calendar Styles
		{
			return false;
		}
		if ((obj.className == 'calendarNonAvlSelected') || (obj.className == 'calendarAvlSelected'))
		{
			return false;
		}
		if (resStEd(obj.innerText))
		{
			if (obj.innerText == "")
			{
				obj.className = '';
				return false;
			}
			//alert (obj.className);
			if (obj.className != "calendarSelected")
			{
				if (obj.className == 'spl')
				{
					obj.className = 'calendarActive';
					obj.style.color = 'red';
				}
				else
				{
					obj.className = 'calendarActive';
				}
			}
		}
	}
	
	function clearStyle(obj)
	{
		if (!calFlag)// To Disable Calendar Styles
		{
			return false;
		}
		if ((obj.className == 'calendarNonAvlSelected') || (obj.className == 'calendarAvlSelected'))
		{
			return false;
		}
		if (resStEd(obj.innerText))
		{
			var temp = document.forms[0];
			crntDate = (temp.crntDate.value).split('-');
			dte = parseInt(obj.innerText,10);
			crntDte = new Date(crntDate[0], crntDate[1], crntDate[2]);
			if (obj.className != "calendarSelected")
			{	
				if (obj.className == 'spl')
				{
					obj.className = 'test';
					obj.style.color = 'red';
				}
				else
				{
					obj.className = 'test';
				}
			}

			if (day[crntDte.getDay()] != undefined)
			{
				if ((day[crntDte.getDay()] == 'Sun') || (day[crntDte.getDay()] == 'Sat'))
				{
					obj.style.color = 'red';
				}
			}
		}
	}

	function previousYear()
	{
		var temp = document.forms[0];
		preMnth = (temp.fromDate.value).split('-');
		crntDate = (temp.crntMnthYear.value).split('-');
		if (parseInt(crntDate[0],10) > parseInt(preMnth[0],10))
		{
			if ((parseInt(crntDate[0],10) - parseInt(preMnth[0],10)) == 1)
			{
				temp.crntMnthYear.value = (parseInt(crntDate[0],10)-1)+"-"+(parseInt(preMnth[1],10)-1);
				calGeneration((parseInt(preMnth[1],10)-1),parseInt(crntDate[0],10)-1);
			}
			else if ((parseInt(preMnth[1],10)-1) >= parseInt(crntDate[1],10))
			{
				temp.crntMnthYear.value = (parseInt(crntDate[0],10)-1)+"-"+parseInt(crntDate[1],10);
				calGeneration(parseInt(crntDate[1],10),parseInt(crntDate[0],10)-1);
			}
		}
		loadAvailCal();
		callSelectedDays();
	}

	function nextYear()
	{
		var temp = document.forms[0];
		preMnth = (temp.toDate.value).split('-');
		crntDate = (temp.crntMnthYear.value).split('-');
		
		if (parseInt(crntDate[0],10) < parseInt(preMnth[0],10))
		{
			if (parseInt(preMnth[0],10) - parseInt(crntDate[0],10) == 1)
			{
				temp.crntMnthYear.value = (parseInt(crntDate[0],10)+1)+"-"+(parseInt(preMnth[1],10)-1);
				calGeneration((parseInt(preMnth[1],10)-1),parseInt(crntDate[0],10)+1);
			}
			else if (parseInt(preMnth[1],10) >= (parseInt(crntDate[1],10)+1))
			{
				temp.crntMnthYear.value = (parseInt(crntDate[0],10)+1)+"-"+parseInt(crntDate[1],10);
				calGeneration(parseInt(crntDate[1],10),parseInt(crntDate[0],10)+1);
			}
		}
		loadAvailCal();
		callSelectedDays();
	}

	
	function previousMonth()
	{
		var temp = document.forms[0];
		preMnth = (temp.fromDate.value).split('-');
		crntDate = (temp.crntMnthYear.value).split('-');

		if (parseInt(crntDate[0],10) > parseInt(preMnth[0],10))
		{
				if (parseInt(crntDate[1],10) == 0)
				{
					temp.crntMnthYear.value = (parseInt(crntDate[0],10)-1)+"-"+11;
					calGeneration(11,parseInt(crntDate[0],10)-1);			
				}
				else
				{
					temp.crntMnthYear.value = crntDate[0]+"-"+(parseInt(crntDate[1],10)-1);
					calGeneration(parseInt(crntDate[1],10)-1,crntDate[0]);		
				}
		}
		else if (parseInt(crntDate[0],10) == parseInt(preMnth[0],10))
		{
			if (parseInt(crntDate[1],10) >= parseInt(preMnth[1],10))
			{
					temp.crntMnthYear.value = crntDate[0]+"-"+(parseInt(crntDate[1],10)-1);
					calGeneration(parseInt(crntDate[1],10)-1,crntDate[0]);		
			}
		}
		loadAvailCal();
		callSelectedDays();
	}

	function nextMonth()
	{
		var temp = document.forms[0];
		preMnth = (temp.toDate.value).split('-');
		crntDate = (temp.crntMnthYear.value).split('-');
		if (parseInt(crntDate[0],10) < parseInt(preMnth[0],10))
		{
				if (parseInt(crntDate[1],10) == 11)
				{
					temp.crntMnthYear.value = (parseInt(crntDate[0],10)+1)+"-"+0;
					calGeneration(0,parseInt(crntDate[0],10)+1);			
				}
				else
				{
					temp.crntMnthYear.value = crntDate[0]+"-"+(parseInt(crntDate[1],10)+1);
					calGeneration(parseInt(crntDate[1],10)+1,crntDate[0]);		
				}
		}
		else if (parseInt(crntDate[0],10) == parseInt(preMnth[0],10))
		{
			if ((parseInt(crntDate[1],10)+1) <= (parseInt(preMnth[1],10)-1))
			{
					temp.crntMnthYear.value = crntDate[0]+"-"+(parseInt(crntDate[1],10)+1);
					calGeneration(parseInt(crntDate[1],10)+1,crntDate[0]);		
			}
		}
		loadAvailCal();
		callSelectedDays();
	}
	
	function loadCrntDate()
	{
		var temp = document.forms[0];
		if (temp.formAction.value == "modify")
		{
			avlCalendar.style.display = 'block';
			//avlCalendarNext.style.display= 'block';
			//tblAvlDetails.style.display = 'block';
			//tblShiftDetailsHeader.style.display = 'none';
			//shftDet.style.display = 'none';
		}
		else
		{
			//tblMandatory.style.display = 'none';
			avlCalendar.style.display = 'block';
			//avlCalendarNext.style.display= 'block';
			//tblAvlDetails.style.display = 'none';
			//tblShiftDetailsHeader.style.display = 'none';
			//shftDet.style.display = 'none';
		}
		if ((temp.fromDate.value != "") && (temp.toDate.value != ""))
		{
			crntDate = (temp.fromDate.value).split('-');
			temp.crntMnthYear.value = parseInt(crntDate[0],10) +"-"+(parseInt(crntDate[1],10)-1);
			calGeneration((parseInt(crntDate[1],10)-1),parseInt(crntDate[0],10));
		}
		//alert (temp.shiftDetails.value);
	}
</script>
<script language="Javascript" type="text/Javascript">
	nonAvlFlag = false;
	checkCount = 0;

	function listItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/WorkCalender/AvailCalendarList.jsp';
		document.forms[0].submit();
	}
	
	function addItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/WorkCalender/AvailCalendarAdd.jsp';
		document.forms[0].submit();
	}

	function loadToHidden()
	{
		temp = document.forms[0];
		temp.ids.value = temp.id.value;
	}
	
	function loadAvailCal()
	{
		var temp = document.forms[0];
		var obj1 = document.getElementById('calendar');
		var obj = document.getElementById('tblCstmAvlDet');
		shftDet.style.display = 'none';
		var nonAvlDets = (temp.customNonAvlDetails.value).split("^");
		for (var i = 0; i < nonAvlDets.length; i++)
		{
			var nonAvlDetails = nonAvlDets[i].split("$");
			nonSelDates[nonSelDates.length] = nonAvlDetails[0];
		}

		var avlDets = (temp.customAvlDetails.value).split("~");
		for (var i = 0; i < avlDets.length; i++)
		{
			var avlDetails = avlDets[i].split("^");
			for (var x = 0; x < avlDetails.length; x++)
			{
				var avlDates = (avlDetails[x]).split('$');
				avlSelDates[avlSelDates.length] = avlDates[0];
			}
		}
		for (var x = 0; x < avlSelDates.length; x++)
		{
			var c_date = (avlSelDates[x]).split("-");
			var cr_date = (parseInt(c_date[0],10))+"-"+(parseInt(c_date[1],10))+"-"+(parseInt(c_date[2],10));
			var crnt_date = (temp.crntMnthYear.value).split("-");
			for (var i = 1; i < obj1.children(0).children.length; i++)
			{
				if (!obj1.children(0).children(i).children(0).innerText == "")
				{
					if ((parseInt(crnt_date[0],10)+'-'+(parseInt(crnt_date[1],10)+1)+'-'+obj1.children(0).children(i).children(0).innerText) == cr_date)
					{
						obj1.children(0).children(i).children(0).className = 'calendarNonAvlSelected';
					}
				}
				if (!obj1.children(0).children(i).children(1).innerText == "")
				{
					if ((parseInt(crnt_date[0],10)+'-'+(parseInt(crnt_date[1],10)+1)+'-'+obj1.children(0).children(i).children(1).innerText) == cr_date)
					{
						obj1.children(0).children(i).children(1).className = 'calendarNonAvlSelected';
					}
				}
				if (!obj1.children(0).children(i).children(2).innerText == "")
				{
					if ((parseInt(crnt_date[0],10)+'-'+(parseInt(crnt_date[1],10)+1)+'-'+obj1.children(0).children(i).children(2).innerText) == cr_date)
					{
						obj1.children(0).children(i).children(2).className = 'calendarNonAvlSelected';
					}
				}
				if (!obj1.children(0).children(i).children(3).innerText == "")
				{
					if ((parseInt(crnt_date[0],10)+'-'+(parseInt(crnt_date[1],10)+1)+'-'+obj1.children(0).children(i).children(3).innerText) == cr_date)
					{
						obj1.children(0).children(i).children(3).className = 'calendarNonAvlSelected';
					}
				}
				if (!obj1.children(0).children(i).children(4).innerText == "")
				{
					if ((parseInt(crnt_date[0],10)+'-'+(parseInt(crnt_date[1],10)+1)+'-'+obj1.children(0).children(i).children(4).innerText) == cr_date)
					{
						obj1.children(0).children(i).children(4).className = 'calendarNonAvlSelected';
					}
				}
				if (!obj1.children(0).children(i).children(5).innerText == "")
				{
					if ((parseInt(crnt_date[0],10)+'-'+(parseInt(crnt_date[1],10)+1)+'-'+obj1.children(0).children(i).children(5).innerText) == cr_date)
					{
						obj1.children(0).children(i).children(5).className = 'calendarNonAvlSelected';
					}
				}
				if (!obj1.children(0).children(i).children(6).innerText == "")
				{
					if ((parseInt(crnt_date[0],10)+'-'+(parseInt(crnt_date[1],10)+1)+'-'+obj1.children(0).children(i).children(6).innerText) == cr_date)
					{
						obj1.children(0).children(i).children(6).className = 'calendarNonAvlSelected';
					}
				}
			}
		}
		for (var x = 0; x < nonSelDates.length; x++)
		{
			var c_date = (nonSelDates[x]).split("-");
			var cr_date = (parseInt(c_date[0],10))+"-"+(parseInt(c_date[1],10))+"-"+(parseInt(c_date[2],10));
			var crnt_date = (temp.crntMnthYear.value).split("-");
			for (var i = 1; i < obj1.children(0).children.length; i++)
			{
				if (!obj1.children(0).children(i).children(0).innerText == "")
				{
					if ((parseInt(crnt_date[0],10)+'-'+(parseInt(crnt_date[1],10)+1)+'-'+obj1.children(0).children(i).children(0).innerText) == cr_date)
					{
						obj1.children(0).children(i).children(0).className = 'calendarAvlSelected';
					}
				}
				if (!obj1.children(0).children(i).children(1).innerText == "")
				{
					if ((parseInt(crnt_date[0],10)+'-'+(parseInt(crnt_date[1],10)+1)+'-'+obj1.children(0).children(i).children(1).innerText) == cr_date)
					{
						obj1.children(0).children(i).children(1).className = 'calendarAvlSelected';
					}
				}
				if (!obj1.children(0).children(i).children(2).innerText == "")
				{
					if ((parseInt(crnt_date[0],10)+'-'+(parseInt(crnt_date[1],10)+1)+'-'+obj1.children(0).children(i).children(2).innerText) == cr_date)
					{
						obj1.children(0).children(i).children(2).className = 'calendarAvlSelected';
					}
				}
				if (!obj1.children(0).children(i).children(3).innerText == "")
				{
					if ((parseInt(crnt_date[0],10)+'-'+(parseInt(crnt_date[1],10)+1)+'-'+obj1.children(0).children(i).children(3).innerText) == cr_date)
					{
						obj1.children(0).children(i).children(3).className = 'calendarAvlSelected';
					}
				}
				if (!obj1.children(0).children(i).children(4).innerText == "")
				{
					if ((parseInt(crnt_date[0],10)+'-'+(parseInt(crnt_date[1],10)+1)+'-'+obj1.children(0).children(i).children(4).innerText) == cr_date)
					{
						obj1.children(0).children(i).children(4).className = 'calendarAvlSelected';
					}
				}
				if (!obj1.children(0).children(i).children(5).innerText == "")
				{
					if ((parseInt(crnt_date[0],10)+'-'+(parseInt(crnt_date[1],10)+1)+'-'+obj1.children(0).children(i).children(5).innerText) == cr_date)
					{
						obj1.children(0).children(i).children(5).className = 'calendarAvlSelected';
					}
				}
				if (!obj1.children(0).children(i).children(6).innerText == "")
				{
					if ((parseInt(crnt_date[0],10)+'-'+(parseInt(crnt_date[1],10)+1)+'-'+obj1.children(0).children(i).children(6).innerText) == cr_date)
					{
						obj1.children(0).children(i).children(6).className = 'calendarAvlSelected';
					}
				}
			}
		}
		callSelectedDays();
	}
	
	function loadAvlNonAvl()
	{
		var temp = document.forms[0];
		var obj = document.getElementById('tblCstmAvlDet');
		checkCount = 0;
		if (temp.formAction.value == 'modify')
		{
			if ((temp.fromDate.value == "") || (temp.toDate.value == "") || (temp.baseCalendarName.options[temp.baseCalendarName.selectedIndex].value == "0"))
			{
				alert("Required Field(s) is/are Empty!");
				checkCount++;
				return false;
			}
		}

		tblMandatory.style.display = 'none';
		tblAvlDetails.style.display = 'block';
		avlCalendarNxt.style.display = 'none';
		shftDet.style.display = 'block';

		if (temp.customAvlDetails.value != "")
		{
			var cstmAvlDet = temp.customAvlDetails.value.split('~');	/* For loading Custom Available Details by default */
			for (var i = 0; i < cstmAvlDet.length; i++) 
			{
				var cstmPrnlAvlDet = (cstmAvlDet[i]).split('|');
				var le = (obj.children(0).children.length);
				if ((obj.children(0).children(0).children(1).children(0).value == "") && (le == 1))
				{
					var avlDate = cstmPrnlAvlDet[0].split("-");
					var newAvlDate = new Date(parseInt(avlDate[0],10),(parseInt(avlDate[1],10)-1),parseInt(avlDate[2]));
					obj.children(0).children(le-1).children(1).children(0).value = bigDay[newAvlDate.getDay()];
					obj.children(0).children(le-1).children(2).children(0).value = cstmPrnlAvlDet[0];
					obj.children(0).children(le-1).children(3).children(0).value = "Availability";
					obj.children(0).children(le-1).children(4).children(0).value = obj.children(0).children(le-1).children(1).children(0).value+"|"+cstmPrnlAvlDet[1]+"|"+newAvlDate.getDay();
					obj.children(0).children(le-1).children(5).children(0).value = "";
				}
				else
				{
					var newNode = obj.children(0).children(0).cloneNode(true);
					obj.children(0).appendChild(newNode);
					var len = obj.children(0).children.length;

					obj.children(0).children(len-1).children(0).children(0).checked = false;
					obj.children(0).children(len-1).children(1).children(0).value = "";
					obj.children(0).children(len-1).children(2).children(0).value = "";
					obj.children(0).children(len-1).children(3).children(0).value = "";
					obj.children(0).children(len-1).children(4).children(0).value = "";
					obj.children(0).children(len-1).children(5).children(0).value = "";

					var avlDate = cstmPrnlAvlDet[0].split("-");
					var newAvlDate = new Date(parseInt(avlDate[0],10),(parseInt(avlDate[1],10)-1),parseInt(avlDate[2]));
					obj.children(0).children(len-1).children(1).children(0).value = bigDay[newAvlDate.getDay()];
					obj.children(0).children(len-1).children(2).children(0).value = cstmPrnlAvlDet[0];
					obj.children(0).children(len-1).children(3).children(0).value = "Availability";
					obj.children(0).children(len-1).children(4).children(0).value = obj.children(0).children(len-1).children(1).children(0).value+"|"+cstmPrnlAvlDet[1]+"|"+newAvlDate.getDay();
					obj.children(0).children(len-1).children(5).children(0).value = "";
				}
			}
		}
		//alert("-"+temp.customNonAvlDetails.value+"-");
		if (temp.customNonAvlDetails.value != "")
		{
			var cstmNonAvlDet = temp.customNonAvlDetails.value.split('^');	/* For loading Custom Non-Available Details by default */
			for (var i = 0; i < cstmNonAvlDet.length; i++) 
			{
				var cstmPrnlNonAvlDet = (cstmNonAvlDet[i]).split('$');
				var le = (obj.children(0).children.length);
				if ((obj.children(0).children(0).children(1).children(0).value == "") && (le == 1))
				{
					var nonAvlDate = cstmPrnlNonAvlDet[0].split("-");
					var newNonAvlDate = new Date(parseInt(nonAvlDate[0],10),(parseInt(nonAvlDate[1],10)-1),parseInt(nonAvlDate[0]));
					obj.children(0).children(le-1).children(1).children(0).value = bigDay[newNonAvlDate.getDay()];
					obj.children(0).children(le-1).children(2).children(0).value = cstmPrnlNonAvlDet[0];
					obj.children(0).children(le-1).children(3).children(0).value = "Non-Availability";
					obj.children(0).children(le-1).children(4).children(0).value = "";
					obj.children(0).children(le-1).children(5).children(0).value = cstmPrnlNonAvlDet[1];
				}
				else
				{
					var newNode = obj.children(0).children(0).cloneNode(true);
					obj.children(0).appendChild(newNode);
					var len = obj.children(0).children.length;

					obj.children(0).children(len-1).children(0).children(0).checked = false;
					obj.children(0).children(len-1).children(1).children(0).value = "";
					obj.children(0).children(len-1).children(2).children(0).value = "";
					obj.children(0).children(len-1).children(3).children(0).value = "";
					obj.children(0).children(len-1).children(4).children(0).value = "";
					obj.children(0).children(len-1).children(5).children(0).value = "";

					var nonAvlDate = cstmPrnlNonAvlDet[0].split("-");
					var newNonAvlDate = new Date(parseInt(nonAvlDate[0],10),(parseInt(nonAvlDate[1],10)-1),parseInt(nonAvlDate[2]));

					obj.children(0).children(len-1).children(1).children(0).value = bigDay[newNonAvlDate.getDay()];
					obj.children(0).children(len-1).children(2).children(0).value = cstmPrnlNonAvlDet[0];
					obj.children(0).children(len-1).children(3).children(0).value = "Non-Availability";
					obj.children(0).children(len-1).children(4).children(0).value = "";
					obj.children(0).children(len-1).children(5).children(0).value = cstmPrnlNonAvlDet[1];
				}
			}
		}
	}

	function retSftName(arPrnlSftDetail)
	{
		var emptySft = "";
		for (var i = 0; i < temp.shiftName.options.length; i++)
		{
			if (arPrnlSftDetail == " ")
			{
				return emptySft;
			}
			else if (temp.shiftName[i].value == arPrnlSftDetail)
			{
				return temp.shiftName[i].text;
			}
		}
	}

	function loadAvlnNonAvl()	/* For loading the shift details of newly selected dates */
	{
		var temp = document.forms[0];
		var obj = document.getElementById('tblCstmAvlDet');
		var baseCalDays = "";

		if ((temp.formAction.value == 'modify') && (checkCount > 1))
		{
			if ((temp.fromDate.value == "") || (temp.toDate.value == "") || (temp.baseCalendarName.options[temp.baseCalendarName.selectedIndex].value == "0"))
			{
				alert("Required Field(s) is/are Empty!");
				return false;
			}
		}

		bcDay = (temp.bcStarnEndDay.value).split('-');
		for (var i = parseInt(bcDay[0],10); i <= parseInt(bcDay[1],10); i++)
		{
			baseCalDays = baseCalDays + " " + (i-1);
		}
		for (var i = 0; i < selDates.length; i++)
		{
			crDate = selDates[i].split('-');
			crntDate = new Date(parseInt(crDate[0],10),parseInt(crDate[1],10),parseInt(crDate[2],10));
			var crntSelDate = parseInt(crDate[0],10)+"-"+(parseInt(crDate[1],10)+1)+"-"+parseInt(crDate[2],10);
			arSftDetails = (temp.sftDetails.value).split('^');
			var tblSftDetails = "";
			var cnt = 0;
			for (var k = 0; k < arSftDetails.length; k++)
			{
				arPrnlSftDetails = arSftDetails[k].split('-');
				if (arPrnlSftDetails[0] == (parseInt(crntDate.getDay(),10)+1))
				{
					if (cnt != 0)
					{
						tblSftDetails = tblSftDetails + "^"
					}
					tblSftDetails = tblSftDetails + arSftDetails[k];
					cnt++;
				}
			}
			if ((obj.children(0).children(0).children(1).children(0).value == "") && (obj.children(0).children.length == 1))
			{
				obj.children(0).children(0).children(1).children(0).value = bigDay[crntDate.getDay()];
				obj.children(0).children(0).children(2).children(0).value = crntSelDate;//selDates[i];
				obj.children(0).children(0).children(3).children(0).value = (baseCalDays.indexOf(parseInt(crntDate.getDay(),10)) > 0)?("Availability"):("Non-Availability");
				obj.children(0).children(0).children(4).children(0).value = tblSftDetails;
			}
			else
			{
				var newNode = obj.children(0).children(0).cloneNode(true);
				obj.children(0).appendChild(newNode);
				var len = obj.children(0).children.length;

				obj.children(0).children(len-1).children(0).children(0).checked = false;
				obj.children(0).children(len-1).children(1).children(0).value = "";
				obj.children(0).children(len-1).children(2).children(0).value = "";
				obj.children(0).children(len-1).children(3).children(0).value = "";
				obj.children(0).children(len-1).children(4).children(0).value = "";

				obj.children(0).children(len-1).children(1).children(0).value = bigDay[crntDate.getDay()];
				obj.children(0).children(len-1).children(2).children(0).value = crntSelDate;//selDates[i];
				obj.children(0).children(len-1).children(3).children(0).value = (baseCalDays.indexOf(parseInt(crntDate.getDay(),10)) > 0)?("Availability"):("Non-Availability");
				obj.children(0).children(len-1).children(4).children(0).value = tblSftDetails;
			}

			obj.children(0).children(0).children(0).children(0).checked = true;
		}

		calFlag = false;
		/*tblMandatory.style.display = 'none';
		avlCalendarNext.style.display = 'none';
		tblAvlDetails.style.display = 'block'
		tblShiftDetailsHeader.style.display = 'none';
		shftDet.style.display = 'block';*/
	}

	function madeAvailabilty()
	{
		var temp = document.forms[0];
		var obj = document.getElementById('tblCstmAvlDet');
		var cnt = 0;
		for (var i = 0; i < obj.children(0).children.length; i++)
		{
			if ((obj.children(0).children(i).children(0).children(0).checked) && (obj.children(0).children(i).children(1).children(0).value != ""))
			{
				cnt++;
				if ((temp.availDesc[0].checked)) /* To check whether Non-Avl or Custom-Avl is Checked */
				{
					if (obj.children(0).children(i).children(3).children(0).value == "Non-Availability")
					{
						alert ("Non-Availability Not Possible!");
						return false;
					}
					tblNonAvailReason.style.display = 'block';
					shftDet.style.display = 'none';
					tblAvlDetails.style.display = 'none';
					//obj.children(0).children(i).children(3).children(0).value = "Non-Availability";
					//obj.children(0).children(i).children(4).children(0).value = "";
				}
				else 
				{
					//tblShiftDetHeader.style.display = 'block';
					//tblShiftDetails.style.display = 'block';
					tblShiftDetailsHeader.style.display = 'block';
					tblMandatory.style.display = 'none';
					tblAvlDetails.style.display = 'none';
					avlCalendar.style.display = 'block';
					shftDet.style.display = 'none';
					
					if (obj.children(0).children(i).children(3).children(0).value != "Non-Availability")
					{
						/* Loading tblShiftDetails */
						var obj1 = document.getElementById('tblSftDetails');
						
						/* Delete the Previous Values */
						if (obj1.children.length > 1)
						{
							for (var c = 0; c < obj1.children.length; c++)
							{
								if (obj1.children.length != 1)
								{
									obj1.removeChild(obj1.children(c));
									c = 0;
								}
							}
							if (obj1.children.length == 1)
							{
								obj1.children(0).children(0).children(1).children(0).value = "";
								obj1.children(0).children(0).children(2).children(0).value = "";
								obj1.children(0).children(0).children(3).children(0).value = "";
								obj1.children(0).children(0).children(4).children(0).value = "";
								obj1.children(0).children(0).children(5).children(0).value = "";
								obj1.children(0).children(0).children(6).children(0).value = "";
								obj1.children(0).children(0).children(7).children(0).value = "";
								obj1.children(0).children(0).children(8).children(0).value = "";
							}
						}
						else if (obj1.children.length == 1)
						{
							obj1.children(0).children(0).children(1).children(0).value = "";
							obj1.children(0).children(0).children(2).children(0).value = "";
							obj1.children(0).children(0).children(3).children(0).value = "";
							obj1.children(0).children(0).children(4).children(0).value = "";
							obj1.children(0).children(0).children(5).children(0).value = "";
							obj1.children(0).children(0).children(6).children(0).value = "";
							obj1.children(0).children(0).children(7).children(0).value = "";
							obj1.children(0).children(0).children(8).children(0).value = "";
						}
						//alert("children(4).value :"+obj.children(0).children(i).children(4).children(0).value);
						arSftDetail = (obj.children(0).children(i).children(4).children(0).value).split('|');
						var tFlg = 0;
						if (arSftDetail[1] != undefined)
						{
							arSftDetails = arSftDetail[1].split('^');
							tFlg = 0;
						}
						else
						{
							arSftDetails = arSftDetail[0].split('^');
							tFlg = 1;
						}
						for (var m = 0; m < arSftDetails.length; m++)
						{
							if (tFlg == '0')
							{
								var arPrnlSftDetail = arSftDetails[m].split('$');
							}
							else
							{
								var tempPrnlSftDetail = arSftDetails[m].split('-');
								var arPrnlSftDetail = new Array();
								for (var q = 1; q < (tempPrnlSftDetail.length-1); q++)
								{
									arPrnlSftDetail[q-1] = tempPrnlSftDetail[q];
								}
							}
						if ((obj1.children.length == 1) && (obj1.children(0).children(0).children(1).children(0).value == ""))
						{
							if (tFlg == '0')
								dayName = arSftDetail[0];
							else
								dayName = obj.children(0).children(i).children(1).children(0).value;

							obj1.children(0).children(0).children(1).children(0).value = dayName;
							obj1.children(0).children(0).children(2).children(0).value = arPrnlSftDetail[1];
							obj1.children(0).children(0).children(3).children(0).value = arPrnlSftDetail[2]+"-"+arPrnlSftDetail[3];
							obj1.children(0).children(0).children(4).children(0).value = arPrnlSftDetail[4]+"-"+arPrnlSftDetail[5];
							obj1.children(0).children(0).children(5).children(0).value = (arPrnlSftDetail[6] == "0" || arPrnlSftDetail[6] == "")? (retSftName(" ")) : (retSftName(arPrnlSftDetail[6]));
							switch(dayName)
							{
								case 'Sunday': { dayId = 0; break; }
								case 'Monday': { dayId = 1; break; }
								case 'Tuesday': { dayId = 2; break; }
								case 'Wednesday': { dayId = 3; break; }
								case 'Thursday': { dayId = 4; break; }
								case 'Friday': { dayId = 5; break; }
								case 'Saturday': { dayId = 6; break; }
							}
							obj1.children(0).children(0).children(6).children(0).value = dayId;
							obj1.children(0).children(0).children(7).children(0).value = arPrnlSftDetail[0];
							obj1.children(0).children(0).children(8).children(0).value = arPrnlSftDetail[6];
						}
						else
						{
							var newNode = obj1.children(0).cloneNode(true);
							obj1.appendChild(newNode);
							var len = obj1.children.length;

							obj1.children(len-1).children(0).children(1).children(0).value = "";
							obj1.children(len-1).children(0).children(2).children(0).value = "";
							obj1.children(len-1).children(0).children(3).children(0).value = "";
							obj1.children(len-1).children(0).children(4).children(0).value = "";
							obj1.children(len-1).children(0).children(5).children(0).value = "";
							obj1.children(len-1).children(0).children(6).children(0).value = "";
							obj1.children(len-1).children(0).children(7).children(0).value = "";
							obj1.children(len-1).children(0).children(8).children(0).value = "";

							if (tFlg == '0')
								dayName = arSftDetail[0];
							else
								dayName = obj.children(0).children(i).children(1).children(0).value;

							obj1.children(len-1).children(0).children(1).children(0).value = dayName;
							obj1.children(len-1).children(0).children(2).children(0).value = arPrnlSftDetail[1];
							obj1.children(len-1).children(0).children(3).children(0).value = arPrnlSftDetail[2]+"-"+arPrnlSftDetail[3];
							obj1.children(len-1).children(0).children(4).children(0).value = arPrnlSftDetail[4]+"-"+arPrnlSftDetail[5];
							obj1.children(len-1).children(0).children(5).children(0).value = (arPrnlSftDetail[6] == "0" || arPrnlSftDetail[6] == "")? (retSftName(" ")) : (retSftName(arPrnlSftDetail[6]));
							switch(dayName)
							{
								case 'Sunday': { dayId = 0; break; }
								case 'Monday': { dayId = 1; break; }
								case 'Tuesday': { dayId = 2; break; }
								case 'Wednesday': { dayId = 3; break; }
								case 'Thursday': { dayId = 4; break; }
								case 'Friday': { dayId = 5; break; }
								case 'Saturday': { dayId = 6; break; }
							}
							obj1.children(len-1).children(0).children(6).children(0).value = dayId;
							obj1.children(len-1).children(0).children(7).children(0).value = arPrnlSftDetail[0];
							obj1.children(len-1).children(0).children(8).children(0).value = arPrnlSftDetail[6];
						}
					}
					for (var c = 1; c < temp.dayofWeek.length; c++)
					{
						temp.dayofWeek[c] = null;
					}
					temp.dayofWeek[1] = new Option(bigDay[parseInt(arSftDetail[0],10)-1],arSftDetail[0]);
				}
					else // Making Non-Availability to Custom Availability
					{
						//alert ("-"+obj.children(0).children(i).children(4).children(0).value+"-" );
						if (obj.children(0).children(i).children(4).children(0).value == "")
						{
							/* Loading tblShiftDetails */
							var obj1 = document.getElementById('tblSftDetails');
							
							/* Delete the Previous Values */
							if (obj1.children.length > 1)
							{
								for (var c = 0; c < obj1.children.length; c++)
								{
									if (obj1.children.length != 1)
									{
										obj1.removeChild(obj1.children(c));
										c = 0;
									}
								}
								if (obj1.children.length == 1)
								{
									obj1.children(0).children(0).children(1).children(0).value = "";
									obj1.children(0).children(0).children(2).children(0).value = "";
									obj1.children(0).children(0).children(3).children(0).value = "";
									obj1.children(0).children(0).children(4).children(0).value = "";
									obj1.children(0).children(0).children(5).children(0).value = "";
									obj1.children(0).children(0).children(6).children(0).value = "";
									obj1.children(0).children(0).children(7).children(0).value = "";
									obj1.children(0).children(0).children(8).children(0).value = "";
								}
							}
							else if (obj1.children.length == 1)
							{
								obj1.children(0).children(0).children(1).children(0).value = "";
								obj1.children(0).children(0).children(2).children(0).value = "";
								obj1.children(0).children(0).children(3).children(0).value = "";
								obj1.children(0).children(0).children(4).children(0).value = "";
								obj1.children(0).children(0).children(5).children(0).value = "";
								obj1.children(0).children(0).children(6).children(0).value = "";
								obj1.children(0).children(0).children(7).children(0).value = "";
								obj1.children(0).children(0).children(8).children(0).value = "";
							}

							// BaseCalendar Days
							bcDay = (temp.bcStarnEndDay.value).split('-');
							var b_calDays = new Array(); 
							var cnt = 0;
							for (var cn = parseInt(bcDay[0],10); cn <= parseInt(bcDay[1],10); cn++) 
							{
								b_calDays[cnt] = (cn-1);
								cnt++;
							}

							var nonAvlDay = "";
							for (var cn = 0; cn < bigDay.length; cn++) // Calculation of Previous Days Shift End Time & Next days Start Sft Time
							{
								if (obj.children(0).children(i).children(1).children(0).value == bigDay[cn])
								{
									nonAvlDay = cn;
								}
							}
							//alert (b_calDays+'-'+nonAvlDay);
							flgC = true;
							var preDayId = parseInt(nonAvlDay,10);
							//alert(preDayId);
							while (flgC)//calculation of Previous day valid Shift
							{
								if (preDayId == 0)
								{
									preDayId = 6;
								}
								else
								{
									preDayId = preDayId - 1;
								}
								for (var cn = 0; cn < b_calDays.length; cn++)	
								{
									if (preDayId == b_calDays[cn])
									{
										flgC = false;
									}
								}
							}
							
							flgN = true;
							var nxtDayId = parseInt(nonAvlDay,10);
							while (flgN) // calculation of Next Days valid Shift
							{
								if (nxtDayId == 6)
								{
									nxtDayId = 0;
								}
								else
								{
									nxtDayId = nxtDayId + 1;
								}

								for (var cn = 0; cn < b_calDays.length; cn++)	
								{
									if (nxtDayId == b_calDays[cn])
									{
										flgN = false;
									}
								}
							}
							//alert (bigDay[preDayId] +'-'+bigDay[nxtDayId]);

							var arSftDetails = (temp.sftDetails.value).split('^'); // Calculation of Previous valid Day Last Sft Time
							var tblSftDetails = "";
							for (var k = 0; k < arSftDetails.length; k++)
							{
								arPrnlSftDetails = arSftDetails[k].split('-');
								if (arPrnlSftDetails[0] == (preDayId/*+1*/))
								{
									tblSftDetails = arSftDetails[k];
								}
							}
							arPreStTime = tblSftDetails.split('-');
							//alert (arPreStTime[4]+"-"+arPreStTime[5]);
							preDayEndTime = arPreStTime[4]+"-"+arPreStTime[5];

							var arSftDetails = (temp.sftDetails.value).split('^'); // Calculation of Next valid Day First Sft Time
							var tblSftDetails = ""; 
							var cnt = 0;
							for (var k = 0; k < arSftDetails.length; k++)
							{
								arPrnlSftDetails = arSftDetails[k].split('-');
								if (arPrnlSftDetails[0] == (nxtDayId+1))
								{
									if (cnt == 0)
									{
										tblSftDetails = arSftDetails[k];
										cnt++;
									}
								}
							}
							arNxtStTime = tblSftDetails.split('-');
							//alert (arPreStTime[4]+"-"+arPreStTime[5]);
							nxtDayStartTime = arNxtStTime[2]+"-"+arNxtStTime[3];
							//alert (preDayEndTime+' & '+nxtDayStartTime);
							
							for (var c = 1; c < temp.dayofWeek.length; c++) // Loading dayofWeek
							{
								temp.dayofWeek[c] = null;
							}
							temp.dayofWeek[1] = new Option(bigDay[parseInt(nonAvlDay,10)],(parseInt(nonAvlDay,10)+1));
							
							obj.children(0).children(i).children(3).children(0).value = "Availability";
							nonAvlFlag = true;// for Nonability to Custom Availability
						}
					}
				}
			}
		}

		if (cnt == 0)
		{
			alert ("Availability Details are Empty!");
			return false;
		}
	}

	function moveToBuffer()
	{
		var temp = document.forms[0];
		var obj = document.getElementById("tblSftDetails");
		var bffrSftDetails = "";
		if (obj.children(0).children(0).children(2).children(0).value == "")
		{
			alert("Shift Details are Empty!");
			return false;
		}

		for (var i = 0; i < obj.children.length; i++)
		{
			var stTimeDay = (obj.children(i).children(0).children(3).children(0).value).split("-");
			var edTimeDay = (obj.children(i).children(0).children(4).children(0).value).split("-");
			if (i != 0)
				bffrSftDetails = bffrSftDetails + "^";

			bffrSftDetails = bffrSftDetails + obj.children(i).children(0).children(7).children(0).value + "$" +
							 obj.children(i).children(0).children(2).children(0).value + "$" +
							 stTimeDay[0] + "$" + stTimeDay[1] + "$" + edTimeDay[0] + "$" + edTimeDay[1] + "$ " + 
							 obj.children(i).children(0).children(8).children(0).value;
		}
		switch(obj.children(0).children(0).children(1).children(0).value)
		{
			case 'Sunday': { dayId = 0; break; }
			case 'Monday': { dayId = 1; break; }
			case 'Tuesday': { dayId = 2; break; }
			case 'Wednesday': { dayId = 3; break; }
			case 'Thursday': { dayId = 4; break; }
			case 'Friday': { dayId = 5; break; }
			case 'Saturday': { dayId = 6; break; }
		}

		var sftDetails = obj.children(0).children(0).children(1).children(0).value + "|"+ 
						 bffrSftDetails + "|"+dayId;
		//alert ("bffrSftDetails :"+bffrSftDetails);
		var obj1 = document.getElementById('tblCstmAvlDet');
		for (var j = 0; j < obj1.children(0).children.length; j++) 
		{
			if (obj1.children(0).children(j).children(0).children(0).checked)
			{
				obj1.children(0).children(j).children(4).children(0).value = sftDetails;
				obj1.children(0).children(j).children(5).children(0).value = "";
			}
		}
		nonAvlFlag = false;// For Non-availability to Custom Availability
		
		avlCalendarNxt.style.display = 'none';
		tblMandatory.style.display = 'none';
		tblShiftDetailsHeader.style.display = 'none';
		tblAvlDetails.style.display = 'block';
		shftDet.style.display = 'block';

		calStyleFlag = true;
		callSelectedDays();
	}

	function addNonAvlReason()
	{
		var temp = document.forms[0];
		var obj = document.getElementById('tblCstmAvlDet');
		if (temp.nonAvlReason.value == "")
		{
			alert ("Non-Availability Reason is Mandatory Field!");
			return false;
		}
		for (var i = 0; i < obj.children(0).children.length; i++)
		{
			if (obj.children(0).children(i).children(0).children(0).checked)
			{
				obj.children(0).children(i).children(3).children(0).value = "Non-Availability";
				obj.children(0).children(i).children(4).children(0).value = "";
				obj.children(0).children(i).children(5).children(0).value = temp.nonAvlReason.value;
			}
		}
		temp.nonAvlReason.value = "";
		tblNonAvailReason.style.display = 'none';
		shftDet.style.display = 'block';
		tblAvlDetails.style.display = 'block';
		calStyleFlag = true;
		callSelectedDays();
	}


	/* This is for loading the start Days and End days in a Combo */
	function loadTimeDate()
	{
		var temp = document.forms[0];
		var stDate = 0;
		for (var i = 0; i < bigDay.length; i++)
		{
			if (bigDay[i] == temp.dayofWeek.options[temp.dayofWeek.selectedIndex].text)
			{
				stDate = i;				
			}
		}
		for (var i = 1; i < temp.startTimeDay.length; i++)
		{
			if (temp.startTimeDay.options[i] != null)
			{
				temp.startTimeDay.options[i] = null;
			}
			if (temp.endTimeDay.options[i] != null)
			{
				temp.endTimeDay.options[i] = null;
			}
		}
		for (var i = 1; i < temp.endTimeDay.length; i++)
		{
			if (temp.startTimeDay.options[i] != null)
			{
				temp.startTimeDay.options[i] = null;
			}
			if (temp.endTimeDay.options[i] != null)
			{
				temp.endTimeDay.options[i] = null;
			}
		}
		
		for (var i = 0; i < bigDay.length; i++)
		{
			if (bigDay[i] == temp.dayofWeek.options[temp.dayofWeek.selectedIndex].text)
			{
				temp.startTimeDay[0] = new Option(day[stDate],stDate);
				temp.endTimeDay[0] = new Option(day[stDate],stDate);
				if (i != 6)
				{
					temp.startTimeDay[1] = new Option(day[stDate+1],stDate+1);
					temp.endTimeDay[1] = new Option(day[stDate+1],stDate+1);
				}
			}
		}
	}

	function modRow(formObj)
	{
		var temp = document.forms[0];
		var obj = document.getElementById('tblSftDetails');
		var ct = 0;
		var stTime = new Array();
		/*for (var z = 1; z < temp.dayofWeek.length; z++)
		{
			if(temp.dayofWeek.length != null)
			{
				temp.dayofWeek.options[z] = null;
			}
		}*/
		for (var i = 0; i < obj.children.length; i++) // Find out the Correct Checked Day
		{
			if (obj.children(i).children(0).children(0).children(0).checked)
			{
				day1 = obj.children(i).children(0).children(1).children(0).value;
				break;
			}
		}
		for (var i = 0; i < obj.children.length; i++) // finding the Same days and form an array
		{
			if (day1 == obj.children(i).children(0).children(1).children(0).value)
			{
				stTime[ct] = (obj.children(i).children(0).children(0).children(0).checked)?("1"):("0");
				ct++; 
			}
		}
		var flag = 0;
		for (var i = 0; i < stTime.length; i++)
		{
			if (stTime[i] == 1)
			{
				flag = i;
				break;
			}
		}
		for (var i = flag; i < stTime.length; i++) // Is eligible for modification 
		{
			if (stTime[i] == 0)
			{
				flag = "fail";
			}
		}
		if (flag == "fail")
		{
			alert ("Modification not Possible!");
			return false;
		}
		var ct = 0;
		for (var i = 0; i < obj.children.length; i++) // Only one item can modify at a time
		{
			if (obj.children(i).children(0).children(0).children(0).checked)
			{
				ct++;
			}
		}
		if (ct > 1)
		{
			alert ("More than One Item cannot be Modified!");
			return false;
		}
		else if (ct == 0)
		{
			alert ("Check a Item to Modify");
			return false;
		}
		for (var i = 0; i < obj.children.length; i++) // Placing values to appropriate positions
		{
			if (obj.children(i).children(0).children(0).children(0).checked)
			{
				temp.dayofWeek[1] = new Option(obj.children(i).children(0).children(1).children(0).value,obj.children(i).children(0).children(6).children(0).value);
				temp.dayofWeek.selectedIndex = 1;
				for (var s = 0; s < temp.shiftName.options.length; s++)
				{
					if (temp.shiftName[s].value == obj.children(i).children(0).children(7).children(0).value)
					{
						temp.shiftName.selectedIndex = s;
						loadTimeDate();
					}
				}
				//alert("stTime :"+obj.children(i).children(0).children(3).children(0).value);
				var stTimeDay = (obj.children(i).children(0).children(3).children(0).value).split("-");
				var stHrsDet = stTimeDay[0].split(":");
				for (var j = 0; j < temp.startHours.length; j++)
				{
					if (temp.startHours.options[j].text == stHrsDet[0])
					{
						temp.startHours.selectedIndex = j;
					}
				}
				for (var j = 0; j < temp.startMins.length; j++)
				{
					if (temp.startMins.options[j].text == stHrsDet[1])
					{
						temp.startMins.selectedIndex = j;
					}
				}
				for (var j = 0; j < temp.startTimeDay.length; j++)
				{
					if (temp.startTimeDay.options[j].text == stTimeDay[1])
					{
						temp.startTimeDay.selectedIndex = j;
					}
				}
				var edTimeDay = (obj.children(i).children(0).children(4).children(0).value).split("-");
				var edHrsDet = edTimeDay[0].split(":");
				for (var j = 0; j < temp.endHours.length; j++)
				{
					if (temp.endHours.options[j].text == edHrsDet[0])
					{
						temp.endHours.selectedIndex = j;
					}
				}
				for (var j = 0; j < temp.endMins.length; j++)
				{
					if (temp.endMins.options[j].text == edHrsDet[1])
					{
						temp.endMins.selectedIndex = j;
					}
				}
				for (var j = 0; j < temp.endTimeDay.length; j++)
				{
					if (temp.endTimeDay.options[j].text == edTimeDay[1])
					{
						temp.endTimeDay.selectedIndex = j;
					}
				}
			}
		}

		for (var j = 0; j < obj.children.length; j++) // Removing that modification row
		{
			if (obj.children.length != 1)
			{
				if (obj.children(j).children(0).children(0).children(0).checked)
				{
					obj.removeChild(obj.children(j));
				}
			}
			else if (obj.children(j).children(0).children(0).children(0).checked)
			{
				obj.children(0).children(0).children(0).children(0).checked = false;
				obj.children(0).children(0).children(1).children(0).value = "";
				obj.children(0).children(0).children(2).children(0).value = "";
				obj.children(0).children(0).children(3).children(0).value = "";
				obj.children(0).children(0).children(4).children(0).value = "";
				obj.children(0).children(0).children(5).children(0).value = "";
			}
		}
	}
	
	function delRow(formObj)
	{
		var temp = document.forms[0];
		var obj = document.getElementById("tblSftDetails");
		var len = obj.children.length;
		var stTime = new Array();
		var ct = 0;
		var day = "";

		if (obj.children.length == 1)
		{
			if (obj.children(0).children(0).children(0).children(0).checked)
			{
				obj.children(0).children(0).children(0).children(0).checked = false;
				obj.children(0).children(0).children(1).children(0).value = "";
				obj.children(0).children(0).children(2).children(0).value = "";
				obj.children(0).children(0).children(3).children(0).value = "";
				obj.children(0).children(0).children(4).children(0).value = "";
				obj.children(0).children(0).children(5).children(0).value = "";
			}
		}
		for (var i = 0; i < obj.children.length; i++)
		{
			if (obj.children(i).children(0).children(0).children(0).checked)
			{
				day = obj.children(i).children(0).children(1).children(0).value;
				break;
			}
		}
		for (var i = 0; i < obj.children.length; i++)
		{
			if (day == obj.children(i).children(0).children(1).children(0).value)
			{
				stTime[ct] = (obj.children(i).children(0).children(0).children(0).checked)?("1"):("0");
				ct++; 
			}
		}
		var flag = 0;
		for (var i = 0; i < stTime.length; i++)
		{
			if (stTime[i] == 1)
			{
				flag = i;
				break;
			}
		}
		for (var i = flag; i < stTime.length; i++)
		{
			if (stTime[i] == 0)
			{
				flag = "fail";
			}
		}
		if (flag != "fail")
		{
			for (var i = 0; i < obj.children.length; i++)
			{
				if (obj.children(i).children(0).children(0).children(0).checked)
				{
					obj.removeChild(obj.children(i));
					i = 0;
				}
				if ((obj.children.length == 1) && (obj.children(0).children(0).children(0).children(0).checked))
				{
					obj.children(0).children(0).children(0).children(0).checked = false;
					obj.children(0).children(0).children(1).children(0).value = "";
					obj.children(0).children(0).children(2).children(0).value = "";
					obj.children(0).children(0).children(3).children(0).value = "";
					obj.children(0).children(0).children(4).children(0).value = "";
					obj.children(0).children(0).children(5).children(0).value = "";
					i = 0;
				}
			}
		}
		else if ((flag == "fail") && (obj.children.length != 1))
		{
			alert("Deletion of depended shifts not possible!");
			return false;
		}
	}

	function addShift(formObj)
	{
		var obj = document.getElementById("tblSftDetails");
		var temp = document.forms[0];
		var stDay = new Array("","Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday");
		var day = "0";
		var dayId = "0";
		var shiftName = "0";
		var shiftId = "0";
		var stTime = "0";
		var endTime = "0";
		var stHrs = "0";
		var stMins = "0";
		var endHrs = "0";
		var endMins = "0";
		var preShift = "0";
		//alert (formObj.dayofWeek.value);
		if (formObj.dayofWeek.value != "-1")
		{
			day = temp.dayofWeek.options[temp.dayofWeek.selectedIndex].text;
			dayId = temp.dayofWeek.options[temp.dayofWeek.selectedIndex].value;
			//alert ("dayId"+dayId);
		}
		else
		{
			alert ("Please Select a Day Of Week!");
			return false;
		}
		if (temp.shiftName.options[temp.shiftName.selectedIndex].value != "0")
		{
			shiftName = temp.shiftName.options[temp.shiftName.selectedIndex].text;
			shiftId = temp.shiftName.options[temp.shiftName.selectedIndex].value;
		}
		else
		{
			alert ("Please Select a Shift Name!");
			return false;
		}
		/*if (temp.startTimeDay.value == "0")
		{
			alert ("StartTime Day is Required!");
			return false;
		}
		if (temp.endTimeDay.value == "0")
		{
			alert ("EndTime Day is Required!");
			return false;
		}*/

		stHrs = formObj.startHours.value;
		stMins = formObj.startMins.value;
		endHrs = formObj.endHours.value;
		endMins = formObj.endMins.value;

		var sTime = stHrs+stMins;
		var eTime = endHrs+endMins;
		
		switch(day)
		{
			case "Sunday" : dayId = 1; break;
			case "Monday" : dayId = 2; break;
			case "Tuesday" : dayId = 3; break;
			case "Wednesday" : dayId = 4; break;
			case "Thursday" : dayId = 5; break;
			case "Friday" : dayId = 6; break;
			case "Saturday" : dayId = 7; break;
			default : dayId = 0; break;
		}
		
		if (nonAvlFlag) // only for Non-ability to Custom Availability
		{
			var stSDay = temp.startTimeDay.options[temp.startTimeDay.selectedIndex].text;
			var edSDay = temp.endTimeDay.options[temp.endTimeDay.selectedIndex].text;

			//alert (preDayEndTime+' - '+nxtDayStartTime);
			pEtTime = preDayEndTime.split('-');
			arStTime = pEtTime[0].split(':');
			pStTime = arStTime[0]+arStTime[1];
			pStDay = pEtTime[1];

			pStTime = nxtDayStartTime.split('-');
			arEdTime = pStTime[0].split(':');
			pEdTime = arEdTime[0]+arEdTime[1];
			pEdDay = pStTime[1];
			
			if (pStDay == stSDay)
			{
				if (pStTime > sTime)
				{
					alert ("Start Time Should be Greater than Previous Days Last Shift Time!");
					return false;
				}
			}

			if (pEdDay == edSDay)
			{
				if (pEdTime < eTime)
				{
					alert ("End Time Should be Less than Next Days First Shift Time!");
					return false;
				}
			}

			//alert (stSDay+' - '+edSDay);
			//alert (sTime+' - '+eTime); 
		}
		/* Call this to findout shift Duplications */
		var res = checkShiftTimes(day,shiftName,sTime,eTime,preShift);
		
		if (res == true)
		{
			stTime = stHrs + ":" + stMins;
			endTime = endHrs + ":" + endMins;

			/* Finding wheather it's first Row */
			if ((obj.children(0).children(0).children(1).children(0).value == "") && (obj.children(0).children(0).children(2).children(0).value == "") &&
			(obj.children(0).children(0).children(5).children(0).value == ""))
			{
					obj.children(0).children(0).children(1).children(0).value = day;
					obj.children(0).children(0).children(2).children(0).value = shiftName;
					obj.children(0).children(0).children(3).children(0).value = stTime+"-"+temp.startTimeDay.options[temp.startTimeDay.selectedIndex].text;
					obj.children(0).children(0).children(4).children(0).value = endTime +"-"+ temp.endTimeDay.options[temp.endTimeDay.selectedIndex].text;
					obj.children(0).children(0).children(5).children(0).value = "";

					obj.children(0).children(0).children(6).children(0).value = dayId;
					obj.children(0).children(0).children(7).children(0).value = shiftId;
					obj.children(0).children(0).children(8).children(0).value = "";
			}
			else
			{
				var newNode = obj.children(0).cloneNode(true);
				obj.appendChild(newNode);
				var i = obj.children.length;

				obj.children(i-1).children(0).children(1).children(0).value = day;
				obj.children(i-1).children(0).children(2).children(0).value = shiftName;
				obj.children(i-1).children(0).children(3).children(0).value = stTime+"-"+temp.startTimeDay.options[temp.startTimeDay.selectedIndex].text;
				obj.children(i-1).children(0).children(4).children(0).value = endTime +"-"+ temp.endTimeDay.options[temp.endTimeDay.selectedIndex].text;
				var preSftName = "";
				var preSftId = "";
				for (var k = 0; k < obj.children.length-1; k++)
				{
					if (day == obj.children(k).children(0).children(1).children(0).value)
					{
						preSftName = obj.children(k).children(0).children(2).children(0).value;
						preSftId = obj.children(k).children(0).children(7).children(0).value;
					}
				}
				obj.children(i-1).children(0).children(5).children(0).value = preSftName;
				obj.children(i-1).children(0).children(6).children(0).value = dayId;
				obj.children(i-1).children(0).children(7).children(0).value = shiftId;
				obj.children(i-1).children(0).children(8).children(0).value = preSftId;
			}
		}
	}

	function checkShiftTimes(day,shiftName,sTime,eTime,preShift)
	{
		var obj = document.getElementById("tblSftDetails");
		var temp = document.forms[0];
		var smallDays = new Array("Sun","Mon","Tue","Wed","Thu","Fri","Sat");

		var days = new Array();
		var sftName = new Array();
		var stTimeDay = new Array();
		var edTimeDay = new Array();
		var stTime = new Array();
		var edTime = new Array();
		var stSftDay = new Array();
		var edSftDay = new Array();
		var crntStTime = parseInt(sTime,10)+2400;
		var crntEdTime = parseInt(eTime,10)+2400;
		
		for (var i = 0; i < obj.children.length; i++) // Storing all the values to an array
		{
			days[i] = obj.children(i).children(0).children(1).children(0).value;
			sftName[i] = obj.children(i).children(0).children(2).children(0).value;
			stTimeDay[i] = obj.children(i).children(0).children(3).children(0).value;
			edTimeDay[i] = obj.children(i).children(0).children(4).children(0).value;
			var tem = (obj.children(i).children(0).children(3).children(0).value).split("-");
			var tem1 = tem[0].split(":");
			stSftDay[i] = tem[1];
			stTime[i] = tem1[0]+tem1[1];
			var tem = (obj.children(i).children(0).children(4).children(0).value).split("-");
			var tem1 = tem[0].split(":");
			edTime[i] = tem1[0]+tem1[1];
			edSftDay[i] = tem[1];
		}
		
		var flag = 0;
		for (var i = 0; i < days.length; i++) // Shift Start time should be greater than 
		{										// previous shift end time
			if (days[i] == day)
			{
				flag = 1;
			}
		}
		if (flag == 0)
		{
			var preDay = "";
			for (var i = 2; i < temp.dayofWeek.length; i++)
			{
				if (day == temp.dayofWeek.options[i].text)
				{
					preDay = temp.dayofWeek.options[i-1].text;
				}
			}
			var preDayTime = new Array();
			if (preDay != "")
			{
				var k = 0;
				for (var i = 0; i < obj.children.length; i++)
				{
					if (preDay == days[i])
					{
						if (stTime[i] > edTime[i])
						{
							if (days[i].substring(0,3) != stSftDay[i])
							{
								stTime[i] = parseInt(stTime[i],10) + 2400;
							}
							if (days[i].substring(0,3) != edSftDay[i])
							{
								edTime[i] = parseInt(edTime[i],10) + 2400;
							}
						}
						preDayTime[k] = stTime[i];
						preDayTime[k+1] = edTime[i];
						k = k+2;
					}
				}

				if (parseInt(sTime,10)+2400 < parseInt(preDayTime[preDayTime.length-1],10))
				{
					alert ("Start Time Should be Greater than Previous Shift End Time!");
					return false;
				}
			}
			//alert ("preDayTime: "+preDayTime);
		}

		/* Start time and End Time cannot be same in the same day */
		var stSDay = temp.startTimeDay.options[temp.startTimeDay.selectedIndex].text;
		var edSDay = temp.endTimeDay.options[temp.endTimeDay.selectedIndex].text;
		if ((sTime == eTime) && (stSDay == edSDay))
		{
			alert ("Start Time and End Time cannot be Same!");
			return false;
		}

		if ((obj.children.length == 1) && (obj.children(0).children(0).children(1).children(0).value == ""))
		{
			if ((eTime > sTime) && ((day.substring(0,3) != stSDay) || (day.substring(0,3) != edSDay)))
			{
				alert ("Invalid Entry!");
				return false;
			}
		}

		/* Find out the Redundant Shift Name*/
		for (var i = 0; i < obj.children.length; i++)		
		{
			if ((days[i] == day) && (sftName[i] == shiftName))
			{
				alert ("The Shift is already Assigned!");
				return false;
			}
		}

		if (sTime < eTime)
		{
			if (stSDay != edSDay)
			{
				alert ("Shift Time Limit InValid!");
				return false;
			}
		}
		
		if (sTime > eTime)
		{
			if (stSDay == edSDay)
			{
				alert ("Shift Time Limit InValid!");					
				return false;
			}
		}

		var nxtDay = "";
		for (var i = 0; i < smallDays.length; i++)
		{
			if (day.substring(0,3) == smallDays[i])
			{
				nxtDay = smallDays[i+1];
				if (nxtDay == stSDay)
				{
					if (stSDay != edSDay)
					{
						alert ("Shift Time Limit InValid!");
						return false;
					}
				}
			}
		}
		var selectDayTime = new Array();
		var selectStDay = new Array();
		var selectEdDay = new Array();
		var k  = 0;
		for (var i = 0; i < obj.children.length; i++)
		{
			if (day == days[i])
			{
				if (stTime[i] > edTime[i])
				{
					if (days[i].substring(0,3) != stSftDay[i])
					{
						stTime[i] = parseInt(stTime[i],10) + 2400;
					}
					if (days[i].substring(0,3) != edSftDay[i])
					{
						edTime[i] = parseInt(edTime[i],10) + 2400;
					}
				}
				selectDayTime[k] = stTime[i];
				selectDayTime[k+1] = edTime[i];
				k = k+2;
			}
			if (((parseInt(selectDayTime[0],10)+2400) < (parseInt(eTime,10)+2400)) && (day.substring(0,3) != edSDay))
			{
				alert ("Shift Times are InValid!");
				return false;
			}

			if ( ((parseInt(selectDayTime[0],10)+2400) < (parseInt(sTime,10)+2400)) && (day.substring(0,3) != stSDay))
			{
				alert ("Shift Times are InValid!");
				return false;
			}

			if (day.substring(0,3) != stSDay)
			{
				if ((parseInt(selectDayTime[selectDayTime.length -1],10)) > (parseInt(sTime,10)+2400))
				{
					alert ("No Such Time Interval on "+day+"!");
					return false;
				}
			}
			else
			{
				if ((parseInt(selectDayTime[selectDayTime.length -1],10)) > parseInt(sTime,10))
				{
					alert ("No Such Time Interval on "+day+"!");
					return false;
				}
			}
		}
		return true;
	}

	function delCstmRow()
	{
		var temp = document.forms[0];
		var obj = document.getElementById('tblCstmAvlDet');

		/* Delete the Previous Values */
		if (obj.children(0).children.length > 1)
		{
			for (var i = 0; i < obj.children(0).children.length; i++)
			{
				if (obj.children(0).children(i).children(0).children(0).checked)
				{
					obj.children(0).removeChild(obj.children(0).children(i));
					i = 0;
				}
			}
			if ((obj.children(0).children.length == 1) && (obj.children(0).children(0).children(0).children(0).checked))
			{
				obj.children(0).children(0).children(0).children(0).checked = false;
				obj.children(0).children(0).children(1).children(0).value = "";
				obj.children(0).children(0).children(2).children(0).value = "";
				obj.children(0).children(0).children(3).children(0).value = "";
				obj.children(0).children(0).children(4).children(0).value = "";
			}
		}
		else if ((obj.children(0).children.length == 1) && (obj.children(0).children(0).children(0).children(0).checked))
		{
				obj.children(0).children(0).children(0).children(0).checked = false;
				obj.children(0).children(0).children(1).children(0).value = "";
				obj.children(0).children(0).children(2).children(0).value = "";
				obj.children(0).children(0).children(3).children(0).value = "";
				obj.children(0).children(0).children(4).children(0).value = "";
		}
	}

	function updateAvailability()
	{
		var temp = document.forms[0];
		var obj = document.getElementById('tblCstmAvlDet');
		temp.avlDetails.value = "";
		if (obj.children(0).children(0).children(1).children(0).value == "")
		{
			alert ("Availability Details are Empty!");
			return false;
		}

		for (var i = 0; i < obj.children(0).children.length; i++)
		{
			if (i != 0)
				temp.avlDetails.value = temp.avlDetails.value +'~';
			
				temp.avlDetails.value = temp.avlDetails.value + obj.children(0).children(i).children(2).children(0).value+"#"+
					  obj.children(0).children(i).children(3).children(0).value+"#"+
					  obj.children(0).children(i).children(5).children(0).value+" # "+
					  obj.children(0).children(i).children(4).children(0).value;
		}
		//alert (temp.avlDetails.value);
		temp.formAction.value = 'update';
		temp.submit();
	}

</script>
</head>
<body onload="init(); loadToHidden(); loadCrntDate(); loadAvailCal();">
<html:form action="frmAvailClndrEdit">
<html:hidden property="formAction"/>
<html:hidden property="id"/>
<html:hidden property="ids"/>
<html:hidden property="customNonAvlDetails"/>
<html:hidden property="bcStarnEndDay"/>
<html:hidden property="customAvlDetails"/>
<html:hidden property="sftDetails"/>
<html:hidden property="avlDetails"/>
<input type="hidden" name="crntMnthYear">
<input type="hidden" name="crntDate">

  <table width="100%" cellspacing="0" cellpadding="10">
    <tr> 
      <td><table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
          <tr>
            <td><bean:message key="prodacs.workcalendar.availabilitycalendar.header"/></td>
          </tr>
        </table>
        <br> <table width="100" cellspacing="0" cellpadding="0" align="right">
          <tr> 
	 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New Availability Calendar Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1010" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
	 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='List Availability Calendar Info'; return true"  onMouseOut="window.status=''; return true" resourceId="10" text="[ List ]" classId="TopLnk" onClick="javaScript:listItem();"/>
          </tr>
        </table>
	<table>
	<tr> 
		<td colspan='2'> <font size="1px" face="Verdana">
			<html:errors/></font>
		</td>
	</tr> 
	</table><br>
         <table width="100%" cellspacing="0" cellpadding="0" id="tblMandatory">
          <tr> 
            <td width="110" class="FieldTitle"><bean:message key="prodacs.workcalendar.availabilitycalendar.calendarname"/></td>
            <td width="1" class="FieldTitle">:</td>
            <td colspan="5" class="FieldTitle"><html:text property="availCalendarName" styleClass="TextBox" size="50" readonly="true"/></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.workcalendar.availabilitycalendar.from"/></td>
            <td class="FieldTitle">:</td>
            <td width="150" class="FieldTitle"><html:text property="fromDate" styleClass="TextBox" size="12"/> <img src='<bean:message key="context"/>/images/calendar.gif' name='calImg' align='absmiddle' onclick='show_calendar("fromDate",AvailCalendarEdit.fromDate.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"></td>
            <td width="30" class="FieldTitle"><bean:message key="prodacs.workcalendar.availabilitycalendar.to"/></td>
            <td width="1" class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="toDate" styleClass="TextBox" size="12"/> <img src='<bean:message key="context"/>/images/calendar.gif' name='calImg' align='absmiddle' onclick='show_calendar("toDate",AvailCalendarEdit.toDate.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.workcalendar.availabilitycalendar.selectbasecal"/></td>
            <td class="FieldTitle">:</td>
            <td colspan="4" class="FieldTitle">
					<html:select property="baseCalendarName" styleClass="Combo">
						<html:option value="0">-- Base Calender --</html:option>
						<html:options collection="baseCalList" property="key" labelProperty="value"/>
					</html:select></td>
          </tr>
        </table> 
        <br>
        <!--table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
          <tr> 
            <td><html:button property="loadAvailCalendar" styleClass="Button" onclick="javascript:loadAvailCal()">Next</html:button>
          </tr>
        </table--></td>
    </tr>
  </table>

		<table width="100%" cellspacing="0" cellpadding="10" border="0" id="avlCalendar">
		<tr>
		<td>
			<table width="50%" cellspacing="0" cellpadding="0">
			 <tr>
					<td class="FieldTitle"><bean:message key="prodacs.workcalendar.availabilitycalendar.header"/></td>
					<td width="1" class="FieldTitle">:</td>
					<td width="100" class="FieldTitle">		
						<div id="navigation">
						</div>
						<div id="ccc">
						</div>
					</td>
			 </tr>
			</table> 
		<br><table width="100%" cellspacing="0" cellpadding="10" border="0" id="avlCalendarNxt">
		<tr>
		<td>
		<table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
		 <tr>
		  <td><html:button property="availDet" styleClass="Button" onclick="loadAvlNonAvl();loadAvlnNonAvl();"> Next </html:button></td><!--  -->
		 </tr>
		</table>
		</td>
		</tr>
		</table>
		<br><!-- For Availability Details -->
		<table width="100%" cellspacing="0" cellpadding="10" id="tblAvlDetails" style="display:none">
		<tr>
		<td>
		<table width="90%" cellspacing="0" cellpadding="10" border="0" id="tblBkEdInfo">
		<tr>
		<td class="FieldTitle" valign="top">
			<fieldset id="FieldSet"><legend>Availability Details</legend>
			<table width="100%" cellspacing="0" cellpadding="5" border="0">
				  <tr> 
				    <!--td class="TopLnk">[ <a href="#" onClick="modCstmRow(document.forms[0]);">Modify</a> ]&nbsp;[ <a href="#" onClick="delCstmRow(document.forms[0]);">Delete</a> ]</td-->
					<td class="TopLnk">[ <a href="#" onClick="delCstmRow(document.forms[0]);">Delete</a> ]</td>
				  </tr>
			</table>
            <table width="100%" cellspacing="0" cellpadding="0">
                  <tr> 
					<td width="28" class="Header"><input type="checkbox" name="CheckAll" value="checkbox" disabled/></td>
					<td width="130" class="Header"><bean:message key="prodacs.workcalendar.customavailability.day"/></td>
					<td width="130" class="Header"><bean:message key="prodacs.workcalendar.customavailability.date"/></td>
					<td class="Header"><bean:message key="prodacs.workcalendar.availability"/>&nbsp;/&nbsp;<bean:message key="prodacs.workcalendar.nonavailability"/></td>
				  </tr>
			</table>
			<table width="100%" cellspacing="0" cellpadding="0" id="tblCstmAvlDet">
				  <tr> 
					<td width="25" class="TableItems"><input type="radio" name="CheckValue" checked></td>
					<td width="127" class="TableItems"><input type="text" name="cstmAvlDay" readonly="true" Class="TextBoxFull" /></td>
					<td width="127" class="TableItems"><input type="text" name="cstmAvlDate" readonly="true" Class="TextBoxFull" /></td>
					<td class="TableItems"><input type="text" name="cstmAvlNonAvl" readonly="true" Class="TextBoxFull" /></td>
					<td><input type="hidden" name="cstmAvlSftDetails"></td>
					<td><input type="hidden" name="cstmNonAvlReason"></td>
				  </tr>
			</table>
				<br>
			<table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td width="200">&nbsp;</td>
					<td width="230" class="FieldTitle"><!--<input type="text" readonly class="AvlTextBox" name="availability" size="1">&nbsp;<bean:message key="prodacs.workcalendar.availability"/>--></td>
					<td width="275" class="FieldTitle"><input type="radio" name="availDesc" checked>&nbsp;<input type="text" readonly class="NonAvlTextBox" name="availability" size="1">&nbsp;<bean:message key="prodacs.workcalendar.nonavailability"/></td>
					<td width="300" class="FieldTitle"><input type="radio" name="availDesc" >&nbsp;<input type="text" readonly class="CusAvlTextBox" name="availability" size="1">&nbsp;<bean:message key="prodacs.workcalendar.customavailability"/></td>
				</tr>
			</table>
			</fieldset>
			<table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
			<tr> 
				<td>
					<html:button property="loadShifts" styleClass="Button" value = "Add" onclick="madeAvailabilty();" />
				</td>
			</tr>
			</table>
		</td>
		</tr>
        </table>
		</td>
		</tr>
        </table>
		<!--  Availability Details Ends -->
		
		<!-- Non-Avail Reason Starts -->
		<table width="100%" cellspacing="0" cellpadding="10" id="tblNonAvailReason" style="display:none"> 
		<tr>
		<td>
		<table width="90%" cellspacing="0" cellpadding="30"> 
		<tr>
		<td class="FieldTitle" valign="top">
		<fieldset id="FieldSet"><legend>Non Availability Reason</legend>
		<br>
			<table width="90%" cellspacing="0" cellpadding="0">
			<tr>
			<td width="60" class="FieldTitle"><bean:message key="prodacs.workorder.reason"/></td>
			<td width="1" class="FieldTitle">:</td>
			<td class="FieldTitle"><input type="text" name="nonAvlReason" class="TextBox" size="50"></td>
			<td width="220" ><html:button property="addShifts" styleClass="Button" value = "Next" onclick="addNonAvlReason();" /></td>
			<!--/td-->
			</tr>
			</table>
		</fieldset>
		</td>
		</tr>
		</table>
		</td>
		</tr>
		</table>
		<!-- Non-Avail Reason Ends -->


	
	<!--  Shift Details Starts -->
	<table width="100%" cellspacing="0" cellpadding="10" id="tblShiftDetailsHeader" style="display:none"> 
	<tr>
	<td>
		<table width="90%" cellspacing="0" cellpadding="0" id="tblShiftDetHeader">
		<tr>
		<td class="FieldTitle">
			<fieldset id="FieldSet"><legend><bean:message key="prodacs.workcalendar.basecalendar.shiftassignmentforeachday"/></legend><br>
			<table width="100%" cellspacing="0" cellpadding="3" id="shiftSelector">
			<tr> 
				<td width="130" class="Header"><bean:message key="prodacs.workcalendar.basecalendar.dayoftheweek"/></td>
				<td class="Header"><bean:message key="prodacs.workcalendar.shiftdefinition.shiftname"/></td>
				<td width="150" class="Header"><bean:message key="prodacs.workcalendar.basecalendar.starttime"/></td>
				<td width="150" class="Header"><bean:message key="prodacs.workcalendar.basecalendar.endtime"/></td>
				<!-- <td width="150" class="Header"><bean:message key="prodacs.workcalendar.basecalendar.predecessorshift"/></td> -->
			</tr>
			<tr> 
				<td class="TableItems">
					<html:select property="dayofWeek" styleClass="ComboFull" onchange="loadTimeDate();">
						<html:option value="-1">-- DayOfWeek --</html:option>
					</html:select>
				</td>
				<td class="TableItems">
					<html:select property="shiftName" styleClass="ComboFull">
						<html:option value="0">-- Choose Shift Name --</html:option>
						<html:options collection="shiftDefList" property="key" labelProperty="value"/>
					</html:select>
				</td>
				<td class="TableItems">
					<html:select property="startHours" styleClass="Combo">
						<html:option value="00">00</html:option>
						<html:option value="01">01</html:option>
						<html:option value="02">02</html:option>
						<html:option value="03">03</html:option>
						<html:option value="04">04</html:option>
						<html:option value="05">05</html:option>
						<html:option value="06">06</html:option>
						<html:option value="07">07</html:option>
						<html:option value="08">08</html:option>
						<html:option value="09">09</html:option>
						<html:option value="10">10</html:option>
						<html:option value="11">11</html:option>
						<html:option value="12">12</html:option>
						<html:option value="13">13</html:option>
						<html:option value="14">14</html:option>
						<html:option value="15">15</html:option>
						<html:option value="16">16</html:option>
						<html:option value="17">17</html:option>
						<html:option value="18">18</html:option>
						<html:option value="19">19</html:option>
						<html:option value="20">20</html:option>
						<html:option value="21">21</html:option>
						<html:option value="22">22</html:option>
						<html:option value="23">23</html:option>
					</html:select>
					<html:select property="startMins" styleClass="Combo">
						<html:option value="00">00</html:option>
						<html:option value="01">01</html:option>
						<html:option value="02">02</html:option>
						<html:option value="03">03</html:option>
						<html:option value="04">04</html:option>
						<html:option value="05">05</html:option>
						<html:option value="06">06</html:option>
						<html:option value="07">07</html:option>
						<html:option value="08">08</html:option>
						<html:option value="09">09</html:option>
						<html:option value="10">10</html:option>
						<html:option value="11">11</html:option>
						<html:option value="12">12</html:option>
						<html:option value="13">13</html:option>
						<html:option value="14">14</html:option>
						<html:option value="15">15</html:option>
						<html:option value="16">16</html:option>
						<html:option value="17">17</html:option>
						<html:option value="18">18</html:option>
						<html:option value="19">19</html:option>
						<html:option value="20">20</html:option>
						<html:option value="21">21</html:option>
						<html:option value="22">22</html:option>
						<html:option value="23">23</html:option>
						<html:option value="24">24</html:option>
						<html:option value="25">25</html:option>
						<html:option value="26">26</html:option>
						<html:option value="27">27</html:option>
						<html:option value="28">28</html:option>
						<html:option value="29">29</html:option>
						<html:option value="30">30</html:option>
						<html:option value="31">31</html:option>
						<html:option value="32">32</html:option>
						<html:option value="33">33</html:option>
						<html:option value="34">34</html:option>
						<html:option value="35">35</html:option>
						<html:option value="36">36</html:option>
						<html:option value="37">37</html:option>
						<html:option value="38">38</html:option>
						<html:option value="39">39</html:option>
						<html:option value="40">40</html:option>
						<html:option value="41">41</html:option>
						<html:option value="42">42</html:option>
						<html:option value="43">43</html:option>
						<html:option value="44">44</html:option>
						<html:option value="45">45</html:option>
						<html:option value="46">46</html:option>
						<html:option value="47">47</html:option>
						<html:option value="48">48</html:option>
						<html:option value="49">49</html:option>
						<html:option value="50">50</html:option>
						<html:option value="51">51</html:option>
						<html:option value="52">52</html:option>
						<html:option value="53">53</html:option>
						<html:option value="54">54</html:option>
						<html:option value="55">55</html:option>
						<html:option value="56">56</html:option>
						<html:option value="57">57</html:option>
						<html:option value="58">58</html:option>
						<html:option value="59">59</html:option>
					</html:select>
					<html:select property="startTimeDay" styleClass="Combo">
					</html:select>
				</td>
				<td class="TableItems">
					<html:select property="endHours" styleClass="Combo">
						<html:option value="00">00</html:option>
						<html:option value="01">01</html:option>
						<html:option value="02">02</html:option>
						<html:option value="03">03</html:option>
						<html:option value="04">04</html:option>
						<html:option value="05">05</html:option>
						<html:option value="06">06</html:option>
						<html:option value="07">07</html:option>
						<html:option value="08">08</html:option>
						<html:option value="09">09</html:option>
						<html:option value="10">10</html:option>
						<html:option value="11">11</html:option>
						<html:option value="12">12</html:option>
						<html:option value="13">13</html:option>
						<html:option value="14">14</html:option>
						<html:option value="15">15</html:option>
						<html:option value="16">16</html:option>
						<html:option value="17">17</html:option>
						<html:option value="18">18</html:option>
						<html:option value="19">19</html:option>
						<html:option value="20">20</html:option>
						<html:option value="21">21</html:option>
						<html:option value="22">22</html:option>
						<html:option value="23">23</html:option>
					</html:select>
					<html:select property="endMins" styleClass="Combo">
						<html:option value="00">00</html:option>
						<html:option value="01">01</html:option>
						<html:option value="02">02</html:option>
						<html:option value="03">03</html:option>
						<html:option value="04">04</html:option>
						<html:option value="05">05</html:option>
						<html:option value="06">06</html:option>
						<html:option value="07">07</html:option>
						<html:option value="08">08</html:option>
						<html:option value="09">09</html:option>
						<html:option value="10">10</html:option>
						<html:option value="11">11</html:option>
						<html:option value="12">12</html:option>
						<html:option value="13">13</html:option>
						<html:option value="14">14</html:option>
						<html:option value="15">15</html:option>
						<html:option value="16">16</html:option>
						<html:option value="17">17</html:option>
						<html:option value="18">18</html:option>
						<html:option value="19">19</html:option>
						<html:option value="20">20</html:option>
						<html:option value="21">21</html:option>
						<html:option value="22">22</html:option>
						<html:option value="23">23</html:option>
						<html:option value="24">24</html:option>
						<html:option value="25">25</html:option>
						<html:option value="26">26</html:option>
						<html:option value="27">27</html:option>
						<html:option value="28">28</html:option>
						<html:option value="29">29</html:option>
						<html:option value="30">30</html:option>
						<html:option value="31">31</html:option>
						<html:option value="32">32</html:option>
						<html:option value="33">33</html:option>
						<html:option value="34">34</html:option>
						<html:option value="35">35</html:option>
						<html:option value="36">36</html:option>
						<html:option value="37">37</html:option>
						<html:option value="38">38</html:option>
						<html:option value="39">39</html:option>
						<html:option value="40">40</html:option>
						<html:option value="41">41</html:option>
						<html:option value="42">42</html:option>
						<html:option value="43">43</html:option>
						<html:option value="44">44</html:option>
						<html:option value="45">45</html:option>
						<html:option value="46">46</html:option>
						<html:option value="47">47</html:option>
						<html:option value="48">48</html:option>
						<html:option value="49">49</html:option>
						<html:option value="50">50</html:option>
						<html:option value="51">51</html:option>
						<html:option value="52">52</html:option>
						<html:option value="53">53</html:option>
						<html:option value="54">54</html:option>
						<html:option value="55">55</html:option>
						<html:option value="56">56</html:option>
						<html:option value="57">57</html:option>
						<html:option value="58">58</html:option>
						<html:option value="59">59</html:option>
					</html:select>
					<html:select property="endTimeDay" styleClass="Combo">
					</html:select>
				</td>
			</tr>
			</table>
			<table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
			<tr> 
				<td>
					<html:button property="addShifts" styleClass="Button" value = "Next" onclick="addShift(document.forms[0]);" />
				</td>
			</tr>
			</table>
			</fieldset>
		</td>
		</tr>
		</table>
	<!--/td>
	</tr>
	</table-->
	
	<table width="90%" cellspacing="0" cellpadding="0" id="tblShiftDetails">
	<tr>
	<td class="FieldTitle">
		<fieldset id="FieldSet"><legend>Shift Details</legend>
			<table width="100%" cellspacing="0" cellpadding="5">
				<tr> 
					<td class="TopLnk">[ <a href="#" onClick="modRow(document.forms[0]);">Modify</a> ]&nbsp;
									   [ <a href="#" onClick="delRow(document.forms[0]);">Delete</a> ]
					</td>
				</tr>
			</table>
			<table width="100%" cellspacing="0" cellpadding="0">
			<tr> 
				<td width="27" class="Header"><input type="checkbox" name="CheckAll" value="checkbox" disabled /></td>
				<td width="110" class="Header"><bean:message key="prodacs.workcalendar.basecalendar.dayoftheweek"/></td>
				<td class="Header"><bean:message key="prodacs.workcalendar.shiftdefinition.shiftname"/></td>
				<td width="100" class="Header"><bean:message key="prodacs.workcalendar.basecalendar.starttime"/></td>
				<td width="100" class="Header"><bean:message key="prodacs.workcalendar.basecalendar.endtime"/></td>
				<td width="150" class="Header"><bean:message key="prodacs.workcalendar.basecalendar.predecessorshift"/></td>
			</tr>
			</table>
			<table width="100%" cellspacing="0" cellpadding="0" id="tblSftDetails">
			<tr> 
				<td width="24" class="TableItems"><input type="checkbox" name="CheckValue"></td>
				<td width="107" class="TableItems"><input readonly name="weekDay" class="TextBoxFull" ></td>
				<td class="TableItems"><input readonly name="sftName" class="TextBoxFull" ></td>
				<td width="97" class="TableItems"><input readonly name="startTime" class="TextBoxFull" ></td>
				<td width="97" class="TableItems"><input readonly name="endTime" class="TextBoxFull" ></td>
				<td width="147" class="TableItems"><input readonly name="predShift" class="TextBoxFull" ></td>
				<td><input type="hidden" name="dayId" ></td>
				<td><input type="hidden" name="shiftId" ></td>
				<td><input type="hidden" name="preSftId" ></td>
			</tr>
			</table>
			<table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
			<tr> 
				<td>
					<html:button property="loadShifts" styleClass="Button" value = "Add" onclick="moveToBuffer(document.forms[0]);" />
				</td>
			</tr>
			</table>
		</fieldset>
	</td>
	</tr>
	</table>
	</td>
	</tr>
	</table>
	<br>
	<table width="100%" cellpadding="0" cellspacing="10" id="shftDet">
	<tr>
	<td>
		<table width="90%" cellpadding="0" cellspacing="0" id="BtnBg">
		 <tr>
		  <td><html:button property="addAvailCalendar" styleClass="Button" onclick="updateAvailability();">Update Availability</html:button></td>
		 </tr>
		</table>
	</td>
	</tr>
	</table>
</html:form>
</body>
</html:html>
