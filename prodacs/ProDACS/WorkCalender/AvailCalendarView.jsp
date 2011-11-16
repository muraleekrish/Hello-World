<%@ page language = "java" %>
<%@ taglib uri = "/WEB-INF/struts-html.tld" prefix = "html" %>
<%@ taglib uri = "/WEB-INF/struts-bean.tld" prefix = "bean" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Map"%>

<%@ page import="com.savantit.prodacs.facade.SessionWorkCalendarDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionWorkCalendarDetailsManager"%>
<%@ page import="com.savantit.prodacs.businessimplementation.workcalendar.AvailabilityDetails"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<jsp:useBean id="frm" class="com.savantit.prodacs.presentation.tableadmin.workcalendar.AvailCalendarEditForm" />
<jsp:setProperty name="frm" property="*" /> 

<useradmin:userrights resource="10"/>
<%
	if(BuildConfig.DMODE)
		System.out.println("Availability Calendar View");
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	AvailabilityDetails objAvailabilityDetails = new AvailabilityDetails();
	HashMap shiftNames = new HashMap();
	try
	{
		/* 	Setting the JNDI name and Environment 	*/
	   	obj.setJndiName("SessionWorkCalendarDetailsManagerBean");
   		obj.setEnvironment();

		/* 	Creating the Home and Remote Objects 	*/
		SessionWorkCalendarDetailsManagerHome availCalHomeObj = (SessionWorkCalendarDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionWorkCalendarDetailsManagerHome.class);
		SessionWorkCalendarDetailsManager availCalDefObj = (SessionWorkCalendarDetailsManager)PortableRemoteObject.narrow(availCalHomeObj.create(),SessionWorkCalendarDetailsManager.class);
		
		objAvailabilityDetails = availCalDefObj.getAvailabilityDetails(frm.getId());
		shiftNames = availCalDefObj.getShiftDefnNameList();
		pageContext.setAttribute("shiftNames",shiftNames);
	}
	catch (Exception e)
	{
		if(BuildConfig.DMODE)
		{
			System.out.println("Error in AvailabiltyView.jsp");
			e.printStackTrace();
		}
	}
	
%>
<html:html>
<head>
<title><bean:message key="prodacs.common.header"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css"></head>
<script language="Javascript" type="text/Javascript">
	var isValid = <%= objAvailabilityDetails.getAvailabiltyIsValid() %>

	function chkMkValid()
	{
		temp = document.forms[0];
		temp.viewAll.value = '<%= session.getAttribute("listValidEntries") %>';

		if (document.getElementById('mkValid') != null)
		{
			if (isValid == 0)
			{
				mkValid.style.display = 'block';
				mkInValid.style.display = 'none';
			}
			else
			{
				mkValid.style.display = 'none';
				mkInValid.style.display = 'block';
			}
		}
	}
	
	function addItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/WorkCalender/AvailCalendarAdd.jsp';
		document.forms[0].submit();
	}
	
	function listItem()
	{
		temp = document.forms[0];
		if (temp.viewAll.value != "" )
		{
			temp.action = '<bean:message key="context"/>/frmAvlClndrList.do?formAction=afterView&viewAll='+temp.viewAll.value;
			temp.submit();
		}
		else
		{
			temp.action = '<bean:message key="context"/>/WorkCalender/AvailCalendarList.jsp';
			temp.submit();
		}
	}

	function editItem()
	{
		if (isValid == 0)
		{
			alert("Invalid Item Cannot be Modified! ");
		}
		else
		{	
			document.forms[0].action = '<bean:message key="context"/>/frmAvailClndrEdit.do?formAction=modify&id='+<%= objAvailabilityDetails.getAvailabilityId() %>;
			document.forms[0].submit();
		}
	}
	
	function makeInvalidItem()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/frmAvailClndrEdit.do?formAction=makeInvalid';
		temp.submit();
	}

	function makeValidItem()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/frmAvailClndrEdit.do?formAction=makeValid';
		temp.submit();
	}
	
	function deleteItem()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/frmAvailClndrEdit.do?formAction=delete';
		temp.submit();
	}
	
	function loadToHidden()
	{
		temp = document.forms[0];
		temp.ids.value = temp.id.value;
	}

	function loadAvlNonAvl()
	{
		var obj = document.getElementById('tblCstmAvlDet');
		
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
	
	function backFromAvl()
	{
		tblShiftDetails.style.display = 'none';
		returnList.style.display = 'block';
	}

	function backFromNonAvl()
	{
		tblNonAvailReason.style.display = 'none';		
		returnList.style.display = 'block';
	}

	function showAvailabilty()
	{
		var temp = document.forms[0];
		var obj = document.getElementById('tblCstmAvlDet');
		var obj1 = document.getElementById('nonAvl');
		var cnt = 0;
		for (var i = 0; i < obj.children(0).children.length; i++)
		{
			if ((obj.children(0).children(i).children(0).children(0).checked) && (obj.children(0).children(i).children(1).children(0).value != ""))
			{
				if (obj.children(0).children(i).children(3).children(0).value == "Non-Availability")
				{
					tblNonAvailReason.style.display = 'block';
					tblShiftDetails.style.display = 'none';
					returnList.style.display = 'none';

					obj1.children(0).children(0).children(0).children(0).value = obj.children(0).children(i).children(1).children(0).value;
					obj1.children(0).children(0).children(1).children(0).value = obj.children(0).children(i).children(2).children(0).value;
					obj1.children(0).children(0).children(2).children(0).value = obj.children(0).children(i).children(5).children(0).value;
				}
				else 
				{
					tblShiftDetails.style.display = 'block';
					tblNonAvailReason.style.display = 'none';
					returnList.style.display = 'none';

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
								obj1.children(0).children(0).children(0).children(0).value = "";
								obj1.children(0).children(0).children(1).children(0).value = "";
								obj1.children(0).children(0).children(2).children(0).value = "";
								obj1.children(0).children(0).children(3).children(0).value = "";
								obj1.children(0).children(0).children(4).children(0).value = "";
								/*obj1.children(0).children(0).children(5).children(0).value = "";
								obj1.children(0).children(0).children(6).children(0).value = "";
								obj1.children(0).children(0).children(7).children(0).value = "";*/
							}
						}
						else if (obj1.children.length == 1)
						{
							obj1.children(0).children(0).children(0).children(0).value = "";
							obj1.children(0).children(0).children(1).children(0).value = "";
							obj1.children(0).children(0).children(2).children(0).value = "";
							obj1.children(0).children(0).children(3).children(0).value = "";
							obj1.children(0).children(0).children(4).children(0).value = "";
							/*obj1.children(0).children(0).children(5).children(0).value = "";
							obj1.children(0).children(0).children(6).children(0).value = "";
							obj1.children(0).children(0).children(7).children(0).value = "";*/
						}
						//alert("children(4).value :"+obj.children(0).children(i).children(4).children(0).value);
						arSftDetail = (obj.children(0).children(i).children(4).children(0).value).split('|');
						arSftDetails = arSftDetail[1].split('^');
						for (var m = 0; m < arSftDetails.length; m++)
						{
							var arPrnlSftDetail = arSftDetails[m].split('$');
							if ((obj1.children.length == 1) && (obj1.children(0).children(0).children(1).children(0).value == ""))
							{
								obj1.children(0).children(0).children(0).children(0).value = arSftDetail[0];
								obj1.children(0).children(0).children(1).children(0).value = arPrnlSftDetail[1];
								obj1.children(0).children(0).children(2).children(0).value = arPrnlSftDetail[2]+"-"+arPrnlSftDetail[3];
								obj1.children(0).children(0).children(3).children(0).value = arPrnlSftDetail[4]+"-"+arPrnlSftDetail[5];
								obj1.children(0).children(0).children(4).children(0).value = (arPrnlSftDetail[6] == "0" || arPrnlSftDetail[6] == "")? (retSftName(" ")) : (retSftName(arPrnlSftDetail[6]));
								/*switch(arSftDetail[0])
								{
									case 'Sunday': { dayId = 0; break; }
									case 'Monday': { dayId = 1; break; }
									case 'Tuesday': { dayId = 2; break; }
									case 'Wednesday': { dayId = 3; break; }
									case 'Thursday': { dayId = 4; break; }
									case 'Friday': { dayId = 5; break; }
									case 'Saturday': { dayId = 6; break; }
								}
								obj1.children(0).children(0).children(5).children(0).value = dayId;
								obj1.children(0).children(0).children(6).children(0).value = arPrnlSftDetail[0];
								obj1.children(0).children(0).children(7).children(0).value = arPrnlSftDetail[6];*/
							}
							else
							{
								var newNode = obj1.children(0).cloneNode(true);
								obj1.appendChild(newNode);
								var len = obj1.children.length;

								obj1.children(len-1).children(0).children(0).children(0).value = "";
								obj1.children(len-1).children(0).children(1).children(0).value = "";
								obj1.children(len-1).children(0).children(2).children(0).value = "";
								obj1.children(len-1).children(0).children(3).children(0).value = "";
								obj1.children(len-1).children(0).children(4).children(0).value = "";
								/*obj1.children(len-1).children(0).children(5).children(0).value = "";
								obj1.children(len-1).children(0).children(6).children(0).value = "";
								obj1.children(len-1).children(0).children(7).children(0).value = "";*/

								obj1.children(len-1).children(0).children(0).children(0).value = arSftDetail[0];
								obj1.children(len-1).children(0).children(1).children(0).value = arPrnlSftDetail[1];
								obj1.children(len-1).children(0).children(2).children(0).value = arPrnlSftDetail[2]+"-"+arPrnlSftDetail[3];
								obj1.children(len-1).children(0).children(3).children(0).value = arPrnlSftDetail[4]+"-"+arPrnlSftDetail[5];
								obj1.children(len-1).children(0).children(4).children(0).value = (arPrnlSftDetail[6] == "0" || arPrnlSftDetail[6] == "")? (retSftName(" ")) : (retSftName(arPrnlSftDetail[6]));
								/*switch(arSftDetail[0])
								{
									case 'Sunday': { dayId = 0; break; }
									case 'Monday': { dayId = 1; break; }
									case 'Tuesday': { dayId = 2; break; }
									case 'Wednesday': { dayId = 3; break; }
									case 'Thursday': { dayId = 4; break; }
									case 'Friday': { dayId = 5; break; }
									case 'Saturday': { dayId = 6; break; }
								}
								obj1.children(len-1).children(0).children(5).children(0).value = dayId;
								obj1.children(len-1).children(0).children(6).children(0).value = arPrnlSftDetail[0];
								obj1.children(len-1).children(0).children(7).children(0).value = arPrnlSftDetail[6];*/
							}
						}
				}
			}
		}
	}

	function retSftName(arPrnlSftDetail)
	{
		var emptySft = "";
		<%
			Set set = shiftNames.entrySet();
			Iterator it = set.iterator();
			while (it.hasNext())
			{
				Map.Entry me = (Map.Entry)it.next();
		%>
		//alert('<%= me.getKey() %>'+"-"+'<%= me.getValue() %>');
		if (arPrnlSftDetail == " ")
		{
			return emptySft;
		}
		else if ('<%= me.getKey() %>' == arPrnlSftDetail)
		{
			return '<%= me.getValue() %>';
		}
		<%
			}
		%>
	}

</script>

<!-- For Availability Calendar -->

<script language="javascript">
	var text = "";
	var nav = "";
	var day = new Array("Sun","Mon","Tue","Wed","Thu","Fri","Sat");
	var bigDay = new Array("Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday");
	var arMon = new Array("January","February","March","April","May","June","July","August","September","October","November","December");
	var calFlag = true; // calendar Hover and Other Styles
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
				text +=  '<tr align="center"><td>' + day1 + '</td><td>' + day2 + '</td><td>' + day3 + '</td><td>' + day4 + '</td><td>' + day5 + '</td><td>' + day6 + '</td><td>' + day7 + '</td></tr>';
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
		strDate = ('<%= objAvailabilityDetails.getFromDate().toString().substring(0,10) %>').split('-'); /* Start Date*/
		stDate = new Date(strDate[0], parseInt(strDate[1],10)-1, strDate[2]);
		
		edDateSt = ('<%= objAvailabilityDetails.getToDate().toString().substring(0,10) %>').split('-'); /* End Date */
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


	function previousYear()
	{
		var temp = document.forms[0];
		preMnth = ('<%= objAvailabilityDetails.getFromDate().toString().substring(0,10) %>').split('-');
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
		//callSelectedDays();
	}

	function nextYear()
	{
		var temp = document.forms[0];
		preMnth = ('<%= objAvailabilityDetails.getFromDate().toString().substring(0,10) %>').split('-');
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
		//callSelectedDays();
	}

	
	function previousMonth()
	{
		var temp = document.forms[0];
		preMnth = ('<%= objAvailabilityDetails.getFromDate().toString().substring(0,10) %>').split('-');
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
		//callSelectedDays();
	}

	function nextMonth()
	{
		var temp = document.forms[0];
		preMnth = ('<%= objAvailabilityDetails.getToDate().toString().substring(0,10) %>').split('-');
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
		//callSelectedDays();
	}

	function loadCrntDate()
	{
		var temp = document.forms[0];
		if (temp.formAction.value == "view")
		{
			avlCalendar.style.display = 'block';
		}
		else
		{
			avlCalendar.style.display = 'block';
		}
		if ((('<%= objAvailabilityDetails.getFromDate().toString().substring(0,10) %>') != "") && (('<%= objAvailabilityDetails.getToDate().toString().substring(0,10) %>') != ""))
		{
			crntDate = ('<%= objAvailabilityDetails.getFromDate().toString().substring(0,10) %>').split('-');
			temp.crntMnthYear.value = parseInt(crntDate[0],10) +"-"+(parseInt(crntDate[1],10)-1);
			calGeneration((parseInt(crntDate[1],10)-1),parseInt(crntDate[0],10));
		}
		//alert (temp.shiftDetails.value);
	}


	function setStyle()
	{
		var temp = document.forms[0];
		var obj = document.getElementById('ccc');
		var baseCalDays = "";

		if (temp.bcStarnEndDay.value != "")
		{
			bcDay = (temp.bcStarnEndDay.value).split('-');
			for (var i = parseInt(bcDay[0],10); i <= parseInt(bcDay[1],10); i++)
			{
				baseCalDays = baseCalDays + " " + i;
			}
			for (var i = 0; i < obj.children(0).children(0).children.length; i++)
			{
				if (baseCalDays.indexOf("1") > 0) 
					obj.children(0).children(0).children(i).children(0).className = 'test';
				else
					obj.children(0).children(0).children(i).children(0).className = 'spl';
				
				if (baseCalDays.indexOf("2") > 0) 
					obj.children(0).children(0).children(i).children(1).className = 'test';
				else 
					obj.children(0).children(0).children(i).children(1).className = 'spl';

				if (baseCalDays.indexOf("3") > 0)
					obj.children(0).children(0).children(i).children(2).className = 'test';
				else
					obj.children(0).children(0).children(i).children(2).className = 'spl';

				if (baseCalDays.indexOf("4") > 0)
					obj.children(0).children(0).children(i).children(3).className = 'test';
				else
					obj.children(0).children(0).children(i).children(3).className = 'spl';

				if (baseCalDays.indexOf("5") > 0)
					obj.children(0).children(0).children(i).children(4).className = 'test';
				else
					obj.children(0).children(0).children(i).children(4).className = 'test';

				if (baseCalDays.indexOf("6") > 0)
					obj.children(0).children(0).children(i).children(5).className = 'test';
				else
					obj.children(0).children(0).children(i).children(5).className = 'test';

				if (baseCalDays.indexOf("7") > 0)
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

	function loadAvailCal()
	{
		var temp = document.forms[0];
		var obj1 = document.getElementById('calendar');
		var obj = document.getElementById('tblCstmAvlDet');

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
		//callSelectedDays();
	}

</script>
</head>

<body onload="loadToHidden(); loadCrntDate(); loadAvailCal(); loadAvlNonAvl();">
<html:form action="frmAvailClndrEdit">
<html:hidden property="formAction"/>
<html:hidden property="id"/>
<html:hidden property="ids"/>
<input type="hidden" name="crntMnthYear">
<input type="hidden" name="crntDate">
<html:hidden property="customNonAvlDetails"/>
<html:hidden property="customAvlDetails"/>
<html:hidden property="sftDetails"/>
<html:hidden property="bcStarnEndDay"/>
<input type="hidden" name="viewAll"/>

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
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Modify Availability Calendar Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1010" text="[ Modify ]" classId="TopLnk" onClick="javaScript:editItem();"/>
          </tr>
        </table>
        <br>
        <table width="100%" cellspacing="0" cellpadding="0">
          <tr> 
            <td width="150" class="FieldTitle"><bean:message key="prodacs.workcalendar.availabilitycalendar.calendarname"/></td>
            <td width="1" class="FieldTitle">:</td>
            <td colspan="5" class="ViewData"><%= objAvailabilityDetails.getAvailabilityName() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.workcalendar.availabilitycalendar.from"/></td>
            <td class="FieldTitle">:</td>
            <td width="150" class="ViewData"><%= objAvailabilityDetails.getFromDate().toString().substring(0,10) %></td>
            <td width="30" class="FieldTitle"><bean:message key="prodacs.workcalendar.availabilitycalendar.to"/></td>
            <td width="1" class="FieldTitle">:</td>
            <td class="ViewData"><%= objAvailabilityDetails.getToDate().toString().substring(0,10) %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.workcalendar.basecalendar.basecalendarname"/></td>
            <td class="FieldTitle">:</td>
            <td colspan="4" class="ViewData"><%= objAvailabilityDetails.getBaseCalName() %></td>
          </tr>
        </table>
        <br>
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
		<br><br>
		<br>
		<!-- For Availability Details -->
		<table width="100%" cellspacing="0" cellpadding="10" id="tblAvlDetails">
		<tr>
		<td>
			<table width="90%" cellspacing="0" cellpadding="10" border="0" id="tblBkEdInfo">
			<tr>
				<td class="FieldTitle" valign="top">
				<fieldset id="FieldSet"><legend>Availability Details</legend><br><br>
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
				</fieldset>
				<table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
				<tr> 
					<td>
						<html:button property="loadShifts" styleClass="Button" value = "View" onclick="showAvailabilty();" />
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
	<table width="90%" cellspacing="0" cellpadding="0" id="tblShiftDetails" style="display:none">
	<tr>
	<td class="FieldTitle">
		<fieldset id="FieldSet"><legend>Shift Details</legend><br>
			<table width="100%" cellspacing="0" cellpadding="3" align="center">
			<tr> 
				<td width="110" class="Header"><bean:message key="prodacs.workcalendar.basecalendar.dayoftheweek"/></td>
				<td width="75" class="Header"><bean:message key="prodacs.workcalendar.shiftdefinition.shiftname"/></td>
				<td width="100" class="Header"><bean:message key="prodacs.workcalendar.basecalendar.starttime"/></td>
				<td width="100" class="Header"><bean:message key="prodacs.workcalendar.basecalendar.endtime"/></td>
				<td width="130" class="Header"><bean:message key="prodacs.workcalendar.basecalendar.predecessorshift"/></td>
			</tr>
			</table>
			<table width="100%" cellspacing="0" cellpadding="3" id="tblSftDetails" align="center">
			<tr> 
				<td width="113" class="TableItems"><input readonly name="weekDay" class="TextBoxFull" ></td>
				<td width="76" class="TableItems"><input readonly name="sftName" class="TextBoxFull" ></td>
				<td width="102" class="TableItems"><input readonly name="startTime" class="TextBoxFull" ></td>
				<td width="102" class="TableItems"><input readonly name="endTime" class="TextBoxFull" ></td>
				<td width="133" class="TableItems"><input readonly name="predShift" class="TextBoxFull" ></td>
			</tr>
			</table>
			</fieldset>
			<table width="100%">
			<tr>
				<td id="BtnBg"><html:button property="backAvl" styleClass="Button" value = "Back" onclick="backFromAvl();" /></td>
			</tr>
			</table>
			</td>
		</tr>
		</table>

		<!-- Non-Avail Reason Starts -->
		<table width="100%" cellspacing="0" cellpadding="10" id="tblNonAvailReason" style="display:none"> 
		<tr>
		<td>
		<table width="90%" cellspacing="0" cellpadding="30"> 
		<tr>
		<td class="FieldTitle" valign="top">
		<fieldset id="FieldSet"><legend>Non Availability Reason</legend>
		<br>
			<table width="90%" cellspacing="0" cellpadding="3" align="center">
			<tr> 
				<td width="100" class="Header"><bean:message key="prodacs.workcalendar.customavailability.day"/></td>
				<td width="150" class="Header"><bean:message key="prodacs.workcalendar.customavailability.date"/></td>
				<td class="Header"><bean:message key="prodacs.workorder.reason"/></td>
			</tr>
			</table>
			<table width="90%" cellspacing="0" cellpadding="3" id="nonAvl" align="center">
			<tr>
				<td width="100" class="TableItems"><input readonly name="day" class="TextBoxFull"></td>
				<td width="150" class="TableItems"><input readonly name="date" class="TextBoxFull"></td>
				<td class="TableItems"><input readonly name="nonAvlReason" class="TextBoxFull"></td>
			</tr>
			</table>
		</fieldset>
		<table width="100%">
		<tr>
			<td id="BtnBg"><html:button property="backNonAvl" styleClass="Button" value = "Back" onclick="backFromNonAvl();" /></td>
		</tr>
		</table>
		</td>
		</tr>
		</table>
		</td>
		</tr>
		</table>
		<!-- Non-Avail Reason Ends -->
		<table width="100%" cellpadding="0" cellspacing="0" id="returnList">
		<tr>
		<td>
			<table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
			  <tr> 
				<td><html:button property="retAvailCalendarList" styleClass="Button" onclick="javascript:listItem()">List</html:button>
				</td>
			  </tr>
			</table>
		</td>
		</tr>
		</table>
	</td>
    </tr>
  </table>
</html:form>
</body>
</html:html>
