<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<html:html>
<head>
<title>Prodacs</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script>
top.window.resizeTo(screen.availWidth,screen.availHeight)

function resizeMe()
	{
		cols=window.parentFrame.cols;
		if(event.screenX>(screen.width-30)*0.70)
			{
				window.parentFrame.cols="300px,*";
			}
		else
			{
				window.parentFrame.cols="200px,*";
			}
	}

</script>
</head>

<frameset rows="30,*,20" framespacing="1" frameborder="YES" border="1" bordercolor="#C7C7C7">
  <frame src='<bean:message key="context"/>/IndexPages/top.jsp' name="topFrame" frameborder="no" scrolling="NO" noresize >
  <frameset cols="220,*" framespacing="1" frameborder="YES" border="1" bordercolor="#ECECEC"> 
  <frame src='<bean:message key="context"/>/IndexPages/tree.jsp' name="leftFrame" frameborder="no" scrolling="Yes" noresize>
  <frame src='<bean:message key="context"/>/Default.jsp' name="mainFrame" frameborder="no">
</frameset>
  <frame src='<bean:message key="context"/>/IndexPages/base.jsp' name="bottomFrame" frameborder="no" scrolling="NO" noresize>
</frameset>
<noframes><body>

</body></noframes>
</html:html>
