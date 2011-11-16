<%@ page language = "java" %>
<%@ taglib uri = "/WEB-INF/struts-html.tld" prefix = "html" %>
<%@ taglib uri = "/WEB-INF/struts-bean.tld" prefix = "bean" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.HashMap"%>

<%@ page import="com.savantit.prodacs.facade.SessionWorkCalendarDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionWorkCalendarDetailsManager"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<useradmin:userrights resource="1009"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("Shift Definition List");
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	try
   {
/* 	Setting the JNDI name and Environment 	*/
	   obj.setJndiName("SessionWorkCalendarDetailsManagerBean");
   	obj.setEnvironment();

/* 	Creating the Home and Remote Objects 	*/
		SessionWorkCalendarDetailsManagerHome baseCalHomeObj = (SessionWorkCalendarDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionWorkCalendarDetailsManagerHome.class);
		SessionWorkCalendarDetailsManager baseCalDefObj = (SessionWorkCalendarDetailsManager)PortableRemoteObject.narrow(baseCalHomeObj.create(),SessionWorkCalendarDetailsManager.class);

		HashMap shiftDefList = baseCalDefObj.getShiftDefnNameList();
		pageContext.setAttribute("shiftDefList",shiftDefList);
	}catch (Exception e)
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
<script language="Javascript" type="text/Javascript">
	var chkd = "";
	function listItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/WorkCalender/BaseCalendarList.jsp';
		document.forms[0].submit();
	}

	function addItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/WorkCalender/BaseCalendarAdd.jsp';
		document.forms[0].submit();
	}

	function chkAll(formObj)
	{
		var obj = document.getElementById("tbl");
		var len = obj.children.length;
		if (document.forms[0].CheckAll.checked == true)
		{
			for (var i = 0; i < len; i++)
			{
				obj.children(i).children(0).children(0).children(0).checked = true;
			}
		}
		else
		{
			for (var i = 0; i < len; i++)
			{
				obj.children(i).children(0).children(0).children(0).checked = false;
			}
		}
	}

	function delRow(formObj)
	{
		var temp = document.forms[0];
		var obj = document.getElementById("tbl");
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

	function loadTimeDate()
	{
		var temp = document.forms[0];
		var stDate = 0;

		var days = new Array("","Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday");
		var smallDays = new Array("","Sun","Mon","Tue","Wed","Thu","Fri","Sat");

		for (var i = 1; i < days.length; i++)
		{
			if (days[i] == temp.dayofWeek.options[temp.dayofWeek.selectedIndex].text)
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

		for (var i = 1; i < days.length; i++)
		{
			if (days[i] == temp.dayofWeek.options[temp.dayofWeek.selectedIndex].text)
			{
				temp.startTimeDay[0] = new Option(smallDays[stDate],stDate);
				temp.endTimeDay[0] = new Option(smallDays[stDate],stDate);

				if (i != 7)
				{
					temp.startTimeDay[1] = new Option(smallDays[stDate+1],stDate+1);
					temp.endTimeDay[1] = new Option(smallDays[stDate+1],stDate+1);
				}
				else
				{
					//alert (stDate +' Small '+ smallDays[1]);
					temp.startTimeDay[1] = new Option(smallDays[1],1);
					temp.endTimeDay[1] = new Option(smallDays[1],1);
				}
			}
		}

	}

	function shiftDays(formObj)
	{
		var temp = document.forms[0];
		var days = new Array("","Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday");
		var stDate = parseInt(temp.startDayofWeek.options[temp.startDayofWeek.selectedIndex].value);
		var stDay = temp.startDayofWeek.options[temp.startDayofWeek.selectedIndex].text;

		var endDate = parseInt(temp.endDayofWeek.options[temp.endDayofWeek.selectedIndex].value);
		var endDay = temp.endDayofWeek.options[temp.endDayofWeek.selectedIndex].text;
		//var tot = formObj.elements['dayofWeek'];
		for (var i = 1; i < temp.dayofWeek.length; i++)
		{
			if (temp.dayofWeek.options[i] != null)
			{
				temp.dayofWeek.options[i] = null;
			}
		}

		for (var i = 1; i < temp.dayofWeek.length; i++)
		{
			if (temp.dayofWeek.options[i] != null)
			{
				temp.dayofWeek.options[i] = null;
			}
		}

		if (stDate == endDate)
		{
			temp.dayofWeek[1] = new Option(days[stDate],stDate);
		}
		else
		{
			if (endDate > stDate)
			{
				var j = 1;
				for (var i = stDate; i <= endDate; i++)
				{
					temp.dayofWeek[j] = new Option(days[i]);
					j++;
				}
			}
			else
			{
				var j = 1;
				for (var i = stDate; i <= 7; i++)
				{
					temp.dayofWeek[j] = new Option(days[i]);
					j++;
				}
				for (var i = 1; i <= endDate; i++)
				{
					temp.dayofWeek[j] = new Option(days[i]);
					j++;
				}
			}
		}
	}

	function addShift(formObj)
	{
		var obj = document.getElementById("tbl");
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

		if (formObj.dayofWeek.value != "0")
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
		if (temp.startTimeDay.value == "0")
		{
			alert ("StartTime Day is Required!");
			return false;
		}
		if (temp.endTimeDay.value == "0")
		{
			alert ("EndTime Day is Required!");
			return false;
		}

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
		var obj = document.getElementById("tbl");
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
		//alert ("flag: "+flag);
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
			//alert ("preDay: "+preDay);
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

				//alert("sTime :"+sTime+"--"+"preDayTime :"+preDayTime[preDayTime.length-1]);
				//alert("sTime :"+(parseInt(sTime,10)+2400)+"--"+"preDayTime :"+(parseInt(preDayTime[preDayTime.length-1],10)+2400));

				if ((parseInt(sTime,10)+2400 < parseInt(preDayTime[preDayTime.length-1],10)) &&
					(parseInt(sTime,10) < parseInt(preDayTime[preDayTime.length-1],10)))
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
			//alert((parseInt(selectDayTime[0],10)+2400)+"---"+(parseInt(eTime,10)+2400));
			//alert((parseInt(selectDayTime[selectDayTime.length -1],10)+2400)+"---"+(parseInt(sTime,10)+2400));
			//alert(selectDayTime);

			if (((parseInt(selectDayTime[0],10)+2400) < (parseInt(eTime,10)+2400)) && (day.substring(0,3) != edSDay))
			{
				alert ("Shift Times are InValid!");
				return false;
			}

			if (((parseInt(selectDayTime[0],10)+2400) < (parseInt(sTime,10)+2400)) && (day.substring(0,3) != stSDay))
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
		//alert ("selectDayTime: "+selectDayTime);

		return true;
	}

	function loadShiftDetails()
	{
		var obj = document.getElementById("tbl");
		var temp = document.forms[0];
		temp.hidShiftDets.value = "";
		for (var j = 0; j < obj.children.length; j++)
		{
			if (j != 0)
			{
				temp.hidShiftDets.value = temp.hidShiftDets.value + "^";
			}
			temp.hidShiftDets.value = temp.hidShiftDets.value +
										obj.children(j).children(0).children(1).children(0).value+"#"+
										obj.children(j).children(0).children(2).children(0).value+"#"+
										obj.children(j).children(0).children(3).children(0).value+"#"+
										obj.children(j).children(0).children(4).children(0).value+"#"+
										((obj.children(j).children(0).children(5).children(0).value == "")?(" "):(obj.children(j).children(0).children(5).children(0).value))+"#"+
										obj.children(j).children(0).children(6).children(0).value+"#"+
										obj.children(j).children(0).children(7).children(0).value+"#"+
										((obj.children(j).children(0).children(8).children(0).value == "")?(" "):(obj.children(j).children(0).children(8).children(0).value));
		}
		if ((obj.children.length == 1) &&
			(obj.children(0).children(0).children(1).children(0).value == "") &&
			(obj.children(0).children(0).children(2).children(0).value == ""))
		{
			alert("No Shift Details to add!");
			return false;
		}

		//alert (temp.hidShiftDets.value);
		var flag = false;
		for (var x = 1; x < temp.dayofWeek.length; x++)
		{
			flag = false;
			for (var k = 0; k < obj.children.length; k++)
			{
				if (temp.dayofWeek.options[x].text == obj.children(k).children(0).children(1).children(0).value)
				{
					flag = true;
					break;
				}
			}
			if (!flag)
			{
				alert("Atleast one shift should be assigned for the selected day!");
				return false;
			}
		}
		temp.formAction.value = "add";
		temp.submit();
	}

	function modRow(formObj)
	{
		var temp = document.forms[0];
		var obj = document.getElementById('tbl');
		var ct = 0;
		var stTime = new Array();

		for (var i = 0; i < obj.children.length; i++) // Find out the Correct Checked Day
		{
			if (obj.children(i).children(0).children(0).children(0).checked)
			{
				day = obj.children(i).children(0).children(1).children(0).value;
				break;
			}
		}
		for (var i = 0; i < obj.children.length; i++) // finding the Same days and form an array
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
				temp.dayofWeek.selectedIndex = parseInt(obj.children(i).children(0).children(6).children(0).value,10)-1;
				for (var x = 1; x < temp.shiftName.length; x++)
				{
					if (temp.shiftName[x].value == obj.children(i).children(0).children(7).children(0).value)
						temp.shiftName.selectedIndex = x;
				}

				loadTimeDate();

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

	function loadShiftDets()
	{
		temp = document.forms[0];
		obj = document.getElementById('tbl');
		/*alert(temp.formAction.value);
		alert('<%= request.getParameter("hidShiftDets")%>');*/

		if (temp.formAction.value == "add")
		{
			var shftDets = ('<%= request.getParameter("hidShiftDets")%>').split("^");
			//alert (shftDets);
			for (var i = 0; i < shftDets.length; i++)
			{
				var shftDet = shftDets[i].split("#");
				if ((obj.children.length == 1) && (obj.children(0).children(0).children(1).children(0).value == ""))
				{
					obj.children(0).children(0).children(1).children(0).value = shftDet[0];
					obj.children(0).children(0).children(2).children(0).value = shftDet[1];
					obj.children(0).children(0).children(3).children(0).value = shftDet[2];
					obj.children(0).children(0).children(4).children(0).value = shftDet[3];
					obj.children(0).children(0).children(5).children(0).value = shftDet[4];
					obj.children(0).children(0).children(6).children(0).value = shftDet[5];
					obj.children(0).children(0).children(7).children(0).value = shftDet[6];
					obj.children(0).children(0).children(8).children(0).value = shftDet[7];
				}
				else
				{
					var newNode = obj.children(0).cloneNode(true);
					obj.appendChild(newNode);
					var len = obj.children.length;

					obj.children(len-1).children(0).children(1).children(0).value = shftDet[0];
					obj.children(len-1).children(0).children(2).children(0).value = shftDet[1];
					obj.children(len-1).children(0).children(3).children(0).value = shftDet[2];
					obj.children(len-1).children(0).children(4).children(0).value = shftDet[3];
					obj.children(len-1).children(0).children(5).children(0).value = shftDet[4];
					obj.children(len-1).children(0).children(6).children(0).value = shftDet[5];
					obj.children(len-1).children(0).children(7).children(0).value = shftDet[6];
					obj.children(len-1).children(0).children(8).children(0).value = shftDet[7];
				}
			}
		}
	}

	function invertSelection()
	{
		var temp = document.forms[0];
		var obj = document.getElementById('tbl');

		if (obj.children.length != 1)
		{
			var cnt = 0;
			var tem = "";
			for (var i = 0; i < obj.children.length; i++)
			{
				if (obj.children(i).children(0).children(0).children(0).checked)
				{
					cnt++;
					tem = i;
				}
			}
			if (cnt == 0)
			{
				chkd = "";
				return false;
			}
			else if (cnt == 1)
			{
				chkd = tem;
				return false;
			}
			if ((chkd != "") || (chkd == "0"))
			{
				for (var i = 0; i < obj.children.length; i++)
				{
					if (i != chkd)
					{
						if (obj.children(i).children(0).children(0).children(0).checked)
						{
							if ((obj.children(chkd).children(0).children(1).children(0).value) !=
								(obj.children(i).children(0).children(1).children(0).value))
							{
								obj.children(i).children(0).children(0).children(0).checked = false;
								alert ("DisSimilar Selection of Days Not Possible!");
								return false;
							}
						}
					}
				}
			}
		}
	}
</script>
</head>

<body onload="loadShiftDets();shiftDays(this.form);">
<html:form action="frmBaseClndrAdd" focus="baseCalendarName">
<html:hidden property="formAction"/>
<html:hidden property="hidShiftDets"/>
<table width="100%" cellspacing="0" cellpadding="10">
<tr>
<td>
	<table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
	<tr>
		<td><bean:message key="prodacs.workcalendar.basecalendar.header"/></td>
	</tr>
	</table><br>
	<table width="100" cellspacing="0" cellpadding="0" align="right">
	<tr>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New BaseCalendar Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1009" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='List BaseCalendar Info'; return true"  onMouseOut="window.status=''; return true" resourceId="9" text="[ List ]" classId="TopLnk" onClick="javaScript:listItem();"/>
	</tr>
	</table><br>
	<table>
	<tr>
		<td colspan='2'> <font size="1px" face="Verdana">
			<html:errors/></font>
		</td>
	</tr>
	</table>
	<table width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td width="150" class="FieldTitle"><bean:message key="prodacs.workcalendar.basecalendar.basecalendarname"/><span class="mandatory">*</span></td>
		<td width="1" class="FieldTitle">:</td>
		<td colspan="4" class="FieldTitle"><html:text property="baseCalendarName" styleClass="TextBox" size="40" maxlength="50" /></td>
	</tr>
	<tr>
		<td class="FieldTitle"><bean:message key="prodacs.workcalendar.basecalendar.startdayoftheweek"/></td>
		<td class="FieldTitle">:</td>
		<td width="100" class="FieldTitle">
			<html:select property="startDayofWeek" styleClass="Combo" onchange="shiftDays(this.form);" >
				<html:option value="1">Sunday</html:option>
				<html:option value="2">Monday</html:option>
				<html:option value="3">Tuesday</html:option>
				<html:option value="4">Wednesday</html:option>
				<html:option value="5">Thursday</html:option>
				<html:option value="6">Friday</html:option>
				<html:option value="7">Saturday</html:option>
			</html:select>
		</td>
		<td width="150" class="FieldTitle"><bean:message key="prodacs.workcalendar.basecalendar.enddayoftheweek"/></td>
		<td width="1" class="FieldTitle">:</td>
		<td class="FieldTitle">
			<html:select property="endDayofWeek" styleClass="Combo" onchange="shiftDays(this.form);">
				<html:option value="1">Sunday</html:option>
				<html:option value="2">Monday</html:option>
				<html:option value="3">Tuesday</html:option>
				<html:option value="4">Wednesday</html:option>
				<html:option value="5">Thursday</html:option>
				<html:option value="6">Friday</html:option>
				<html:option value="7">Saturday</html:option>
			</html:select>
		</td>
	</tr>
	</table>
	<table width="90%" cellspacing="0" cellpadding="0">
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
					<html:option value="0">-- DayOfWeek --</html:option>
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
			<!-- <td class="TableItems">
				<html:select property="predecessorShift" styleClass="ComboFull">
					<html:option value="0">-- Predecessor Shift --</html:option>
					<html:options collection="shiftDefList" property="key" labelProperty="value"/>
				</html:select>
			</td> -->
		</tr>
		</table>
		<table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
		<tr>
			<td>
				<html:button property="addShifts" styleClass="Button" value = "Add" onclick="addShift(document.forms[0]);" />
			</td>
		</tr>
		</table>
	</fieldset>
	</td>
	</tr>
	</table>
		<table width="90%" cellspacing="0" cellpadding="0">
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
				<table width="100%" cellspacing="0" cellpadding="0" id="tbl">
				<tr>
					<td width="24" class="TableItems"><input type="checkbox" name="CheckValue" onclick="invertSelection();"></td>
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
			</fieldset>
			</td>
		</tr>
        </table><br>
		<table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
		<tr>
			<td>
				<html:button property="addBaseCalendar" styleClass="Button" onclick="javascript:loadShiftDetails()">Add Base Calendar</html:button>
			</td>
		</tr>
        </table>
</td>
</tr>
</table>
</html:form>
</body>
</html:html>
