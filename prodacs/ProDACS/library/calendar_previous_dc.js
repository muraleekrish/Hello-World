function show_calendar_previous(str_target, str_datetime, cX, cY, showTime) 
{
	var arr_months = ["January", "February", "March", "April", "May", "June","July", "August", "September", "October", "November", "December"];
	var week_days = ["Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"];
	var n_weekstart = 1; // day week starts from (normally 0 or 1)

	var today_date = new Date();
	
	var dt_datetime = (str_datetime == null || str_datetime =="" ?  new Date() : str2dt(str_datetime));
	var dt_prev_month = new Date(dt_datetime);
	dt_prev_month.setMonth(dt_datetime.getMonth()-1);
	var dt_next_month = new Date(dt_datetime);
	dt_next_month.setMonth(dt_datetime.getMonth()+1);
	var dt_firstday = new Date(dt_datetime);
	dt_firstday.setDate(1);
	dt_firstday.setDate(1-(7+dt_firstday.getDay()-n_weekstart)%7);
	var dt_lastday = new Date(dt_next_month);
	dt_lastday.setDate(0);
	
	// html generation (feel free to tune it for your particular application)
	// print calendar header
	var str_buffer = new String (
		"<html>\n"+
		"<head>\n"+
		"	<title>Calendar</title>\n<script>var firstSel = null;</script>"+
		"<link rel=\"stylesheet\" href=\"/ProDACS/styles/default.css\">\n"+
		"</head>\n"+
		"<body bgColor=\"#DDDDDD\">\n<form name=\"cal\">\n"+
		"<table cellpadding=\"0\" cellspacing=\"1\" border=\"0\" width=\"100%\">\n"+
		"<tr><td>\n"+
		"<table cellspacing=\"1\" cellpadding=\"3\" border=\"0\" id=\"tab\" width=\"100%\" bgColor=\"#999999\">\n"+
		"<tr>\n	<td bgcolor=\"#E7E7E7\">" +
		"<img src=\"/ProDACS/images/prev.gif\" border=\"0\" style=\"cursor:pointer\""+
		" alt=\"previous month\"  onclick=\"parent.show_calendar_previous('"+str_target+"', '"+ dt2dtstr(dt_prev_month)+"'");
	if(showTime)
		str_buffer += "+document.cal.time.value";
	str_buffer += ","+cX+","+cY+");\"></td>\n"+
// month & year display
		"	<td bgcolor=\"#E7E7E7\" colspan=\"5\" align=\"center\">"+
		"<font color=\"black\" face=\"tahoma, verdana\" size=\"2\">"
		+arr_months[dt_datetime.getMonth()]+" "+dt_datetime.getFullYear()+"</font></td>\n"+
		"	<td bgcolor=\"#E7E7E7\">"+
		"<img src=\"/ProDACS/images/next.gif\" border=\"0\"  style=\"cursor:pointer\""+
		" alt=\"next month\"onclick=\"parent.show_calendar_previous('"+str_target+"', '"+ dt2dtstr(dt_next_month)+"'";
	if(showTime)
		str_buffer += "+document.cal.time.value";
	str_buffer += ","+cX+","+cY+");\"></td>\n</tr>\n";

	var dt_current_day = new Date(dt_firstday);
	// print weekdays titles
	str_buffer += "<tr>\n";
	for (var n=0; n<7; n++)
		str_buffer += "	<td bgColor=\"#BBBBBB\">"+"<font color=\"Black\" face=\"tahoma, verdana\" size=\"2\">"+	week_days[(n_weekstart+n)%7]+"</font></td>\n";
	// print calendar table
	str_buffer += "</tr>\n";
	var count = 0;
	while (dt_current_day.getMonth() == dt_datetime.getMonth() ||dt_current_day.getMonth() == dt_firstday.getMonth()) 
	{
		// print row heder
		str_buffer += "<tr>\n";
		for (var n_current_wday=0; n_current_wday<7; n_current_wday++) 
		{
			if (dt_current_day.getDate() == dt_datetime.getDate() && dt_current_day.getMonth() == dt_datetime.getMonth())
			{
					// print current date
				if(parseInt(date2Str(dt_current_day),10) <= parseInt(date2Str(today_date),10))
					str_buffer += "	<td id=\"cell"+count+"\" bgcolor=\"#BBBBBB\" style=\"cursor:pointer\" align=\"right\"";
				else
					str_buffer += "	<td id=\"cell"+count+"\" bgcolor=\"white\" align=\"right\" style=\"cursor:pointer\" ";
			}
			else
			{
					// print working days of current month
					str_buffer += "	<td id=\"cell"+count+"\" bgcolor=\"white\" align=\"right\" style=\"cursor:pointer\" ";
			}
			count++;
			
				if (dt_current_day.getMonth() ==  dt_datetime.getMonth())
				{
					// print days of current month
					str_buffer += " onclick=\""
					+ " var current = this; var i=0; for(i=0;i<42;i++) {var cell = document.getElementById('cell'+i); if(cell!=null){cell.bgColor='white';} } "
					if(parseInt(date2Str(dt_current_day),10) <= parseInt(date2Str(today_date),10))
						str_buffer+=" current.bgColor ='#BBBBBB'; document.cal.hidvalue.value='"+dt2dtstr(dt_current_day)+"'";

					if(showTime)
						str_buffer += "+document.cal.time.value"
						str_buffer += "; \"> <font color=\"black\" face=\"tahoma, verdana\" size=\"2\">";
				}
				else 
					// print days of other months
					str_buffer += "; ><font color=\"gray\" face=\"tahoma, verdana\" size=\"2\">";
				str_buffer += dt_current_day.getDate()+"</font></td>\n";
				dt_current_day.setDate(dt_current_day.getDate()+1);
		}//end of for
		// print row footer
		str_buffer += "</tr>\n<tr>";
	}//end of while
	// print calendar footer
//	alert(str_buffer);
	if(showTime)
	{
	str_buffer +=
		"<td colspan=\"6\" bgcolor=\"#BBBBBB\">"+
		"<font color=\"White\" face=\"tahoma, verdana\" size=\"2\">"+
		"<select name=\"hh\" style=\"font-family:verdana;font-size:8px\" onchange=\"time.value=this.value+time.value.substring(time.value.indexOf(':'),time.value.length);document.cal.hidvalue.value='"+dt2dtstr(dt_datetime)+"'+document.cal.time.value;\" onblur=\"document.cal.hidvalue.value='"+dt2dtstr(dt_datetime)+"'+document.cal.time.value\">";
		for(var i=0;i<24;i++)
		{
			var test="";
			if(i<10)
			{
				str_buffer += "<option  "+((i==dt_datetime.getHours())?"selected":"") +" value='0"+i+"'>0"+i+"</option>";
			}
			else
				str_buffer += "<option  "+((i==dt_datetime.getHours())?"selected":"") +" value='"+i+"'>"+i+"</option>";
		}
		str_buffer += "</select>"+
		"<select name=\"mm\" style=\"font-family:verdana;font-size:8px\" onchange=\"time.value = time.value.substring(0,time.value.indexOf(':')+1) + this.value + time.value.substring(time.value.indexOf(':',3),time.value.length);document.cal.hidvalue.value='"+dt2dtstr(dt_datetime)+"'+document.cal.time.value;\"  onblur=\"document.cal.hidvalue.value='"+dt2dtstr(dt_datetime)+"'+document.cal.time.value\">";
		for(var i=0;i<60;i++)
		{
			if(i<10)
				str_buffer += "<option "+((i==dt_datetime.getMinutes())?"selected":"") +" value='0"+i+"'>0"+i+"</option>";
			else
				str_buffer += "<option "+((i==dt_datetime.getMinutes())?"selected":"") +" value='"+i+"'>"+i+"</option>";
		}
		str_buffer += "</select>"+
		"<select name=\"ss\" style=\"font-family:verdana;font-size:8px\" onchange=\"time.value = time.value.substring(0,time.value.indexOf(':',3)+1)+this.value;document.cal.hidvalue.value='"+dt2dtstr(dt_datetime)+"'+document.cal.time.value;\"  onblur=\"document.cal.hidvalue.value='"+dt2dtstr(dt_datetime)+"'+document.cal.time.value\">";
		for(var i=0;i<60;i++)
		{
			if(i<10)
				str_buffer += "<option "+((i==dt_datetime.getSeconds())?"selected":"") +" value='0"+i+"'>0"+i+"</option>";
			else
				str_buffer += "<option  "+((i==dt_datetime.getSeconds())?"selected":"") +" value='"+i+"'>"+i+"</option>";
		}
		str_buffer += "</select>"+
		"</font>"+
		"<input type=\"hidden\" name=\"time\" value=\""+dt2tmstr(dt_datetime)+
		"\" size=\"8\" maxlength=\"8\"></td>\n" ;
	}//end of if(Show time)
	str_buffer += "<td ";
	if(!showTime)
		str_buffer += "colspan=\"7\"";
	str_buffer += "align=\"right\"><input type=\"hidden\" name=\"hidvalue\" ";

	if(showTime)
		str_buffer += " value='"+dt2dtstr(dt_datetime)+""+dt2tmstr(dt_datetime)+"'";
	else
		str_buffer += " value='"+dt2dtstr(dt_datetime)+"'";
	
	str_buffer +=">\n <a href=\"#\" style=\"color:black; text-decoration:none; font-size:12px; font-weight:bold\" onclick=\"parent.document.getElementById('"+str_target+"').value=document.cal.hidvalue.value;parent.oPopup.hide();\">Done</a>";//parent.chkVdDte(parent.document.getElementById('"+str_target+"'));

	str_buffer += "</td></tr></table>\n" +
		"</td>\n</tr>\n</table>\n</form>\n" +
		"</body>\n" +
		"</html>\n";



//	alert(str_buffer);

	var oHeight = 220;

	var oPopupBody = oPopup.document.body;
//	alert(event.clientY);
    var lefter = cY+1;
	var topper = cX+10;
//  alert(event.srcElement.innerText);
    oPopupBody.style.left = lefter;
	oPopupBody.style.background = "#DDDDDD";
	oPopupBody.style.border = "solid 1px #DDDDDD";
    oPopupBody.style.top = topper;
    oPopupBody.innerHTML = 	str_buffer;//show_calendar(str_datetime,x,y);
    oPopup.show(topper, lefter, 220, oHeight, document.body);
	document.onclick = "oPopup.hide();"
	document.onblur = "oPopup.hide();"
}
// datetime parsing and formatting routimes. modify them if you wish other datetime format	"(\d\d\d\d)/(\d\d)/(\d\d)")
function str2dt(str_datetime) 
{
	var re_dateTime = /^(\d+)\-(\d+)\-(\d+)\s+(\d+)\:(\d+)\:(\d+)$/;
	var re_dateOnly = /^(\d+)\-(\d+)\-(\d+)/;
	var timeShown = false;
	if (re_dateTime.exec(str_datetime))
	{
		timeShown = true;
	}
	else if(re_dateOnly.exec(str_datetime))
	{
		timeShown = false;
	}
	else
		return alert("Invalid Datetime format: "+ str_datetime);
	if(timeShown)
		return(new Date (RegExp.$1, RegExp.$2-1, RegExp.$3, RegExp.$4, RegExp.$5, RegExp.$6));
	else
		return(new Date (RegExp.$1, RegExp.$2-1, RegExp.$3));
}
function dt2dtstr(dt_datetime) 
{
	var to_date = new Date;
	if((to_date.getMonth())>=dt_datetime.getMonth())
	{
	
		var month = "";
		var day = "";
		var monval = dt_datetime.getMonth()+1;
		if(dt_datetime.getMonth()<9)
		{
			month = "0" + monval;
		}
		else
			month = "" + monval;
		if(dt_datetime.getDate()<10)
			day = "0" + dt_datetime.getDate();
		else
			day = "" + dt_datetime.getDate();
		return (new String (
					dt_datetime.getFullYear()+"-"+month+"-"+day+" "));
    }
	else
	{
		var month = "";
		var day = "";
		var monval = to_date.getMonth()+1;
		if(to_date.getMonth()<9)
			month = "0" + monval;
		else
			month = "" + monval;

		if(to_date.getDate()<10)
			day = "0" + to_date.getDate();
		else
			day = "" + to_date.getDate();
		return (new String (
					to_date.getFullYear()+"-"+month+"-"+day+" "));

	}

}

function chkVdDte(sel_date)
{
	var crnt_date = new Date();
	ct_date = crnt_date.getFullYear()+"-"+(((crnt_date.getMonth()+1) < 10)?("0"+(crnt_date.getMonth()+1)):(crnt_date.getMonth()+1)) +"-"+((crnt_date.getDate() < 10)?("0"+crnt_date.getDate()):(crnt_date.getDate()));
	var c_date = new Date();
	var s_date = new Date();
	c_date = ct_date;
	s_date = sel_date.value;
	if (s_date <= c_date)
	{
		sel_date.value = c_date;
	}
}

function date2Str(dtObj)
{
	var month = "";
	var day = "";
	var monval = dtObj.getMonth()+1;
	if(dtObj.getMonth()<9)
	{
		month = "0" + monval;
	}
	else
		month = "" + monval;
	if(dtObj.getDate()<10)
		day = "0" + dtObj.getDate();
	else
		day = "" + dtObj.getDate();
	return (new String (
				dtObj.getFullYear()+""+month+""+day));
}

function dt2tmstr(dt_datetime) 
{
	var hh = "";
	var min = "";
	var sec = "";
	hh = (dt_datetime.getHours()<10)?("0"+dt_datetime.getHours()):(""+dt_datetime.getHours());
	min = (dt_datetime.getMinutes()<10)?"0" + dt_datetime.getMinutes():""+dt_datetime.getMinutes();
	sec = (dt_datetime.getSeconds()<10)?"0" + dt_datetime.getSeconds():"" + dt_datetime.getSeconds();
	return (new String (
			hh + ":" + min + ":" + sec));
}

function formTime(obj)
{
	var name="";
	if(obj.name.indexOf("hh")==obj.name.length-2)
	{
		name = obj.name.substring(0,obj.name.indexOf("hh"));
	}
	else if(obj.name.indexOf("mm")==obj.name.length-2)
	{
		name = obj.name.substring(0,obj.name.indexOf("mm"));
	}
	else if(obj.name.indexOf("ss")==obj.name.length-2)
	{
		name = obj.name.substring(0,obj.name.indexOf("ss"));
	}
	else
	{
		name = obj.name.substring(0,obj.name.indexOf("day"));
		if(obj.value=="")
			obj.value="00";
	}
	document.getElementById(name).value = document.getElementById(name+"days").value +":" + document.getElementById(name+"hh").value +":"+document.getElementById(name+"mm").value+":"+document.getElementById(name+"ss").value;
}
