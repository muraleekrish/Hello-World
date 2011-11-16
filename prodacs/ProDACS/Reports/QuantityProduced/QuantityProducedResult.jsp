<%@ page errorPage="error.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<%@ page import="java.io.InputStream" %>
<%@ page import="java.util.StringTokenizer"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="net.sf.jasperreports.engine.JasperRunManager" %>
<%@ page import="net.sf.jasperreports.engine.JasperReport" %>
<%@ page import="net.sf.jasperreports.engine.util.*" %>
<%@ page import="net.sf.jasperreports.engine.export.*" %>
<%@ page import="net.sf.jasperreports.engine.JasperPrint" %>
<%@ page import="net.sf.jasperreports.engine.JasperFillManager" %>
<%@ page import="net.sf.jasperreports.engine.JRExporterParameter" %>
<%@ page import="javax.servlet.ServletOutputStream"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<%@ page import="com.savantit.prodacs.facade.SessionReportsDetailsManager"%>
<%@ page import="com.savantit.prodacs.facade.SessionReportsDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.businessimplementation.reports.QuantityProducedDetails"%>
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="net.sf.jasperreports.engine.data.JRBeanArrayDataSource" %>

<useradmin:userrights resource="39"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("Quantity Produced Details Starts.");
	
	QuantityProducedDetails[] objQuantityProducedDetails;
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	JRBeanArrayDataSource dataSource = null;
	
	JasperReport jr = null;
	InputStream is = null;
	Map parameters = new HashMap();
	String isPdf = (request.getParameter("pdf")==null)?"":request.getParameter("pdf");
	String startDate = request.getParameter("startDate");
	String endDate = request.getParameter("endDate");
	if (BuildConfig.DMODE)
		System.out.println("Start&End Date: "+ startDate+"&"+endDate);
	
	if ((startDate != "") && (endDate != ""))
	{
		String[] month = {"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
		StringTokenizer frmDate = new StringTokenizer(startDate.trim(),"-");
		String tempFrmDate[] = new String[frmDate.countTokens()];
		int i = 0;
		while(frmDate.hasMoreTokens())
		{
			tempFrmDate[i] = frmDate.nextToken();
			if (BuildConfig.DMODE)
				System.out.println("Frm: "+tempFrmDate[i]);
			i++;
		}
		
		StringTokenizer toDate = new StringTokenizer(endDate.trim(),"-");
		String tempToDate[] = new String[toDate.countTokens()];
		i = 0;
		while (toDate.hasMoreTokens())
		{
			tempToDate[i] = toDate.nextToken();
			if (BuildConfig.DMODE)
				System.out.println("Frm: "+tempToDate[i]);
			i++;
		}
		
		String stDate = tempFrmDate[2] + "-" + month[Integer.parseInt(tempFrmDate[1])-1] + "-" + tempFrmDate[0];
		String edDate = tempToDate[2] + "-" + month[Integer.parseInt(tempToDate[1])-1] + "-" + tempToDate[0];
		
		/* For Start Date */
		StringTokenizer st = new StringTokenizer(startDate,"-");
		int yr = 0;
		int mnth = 0;
		int day = 0;
		if(st.hasMoreTokens())
		{
			yr = Integer.parseInt(st.nextToken().trim());
		}
		if(st.hasMoreTokens())
		{
			mnth = Integer.parseInt(st.nextToken().trim());
		}
		if(st.hasMoreTokens())
		{		
			day = Integer.parseInt(st.nextToken().trim());
		}
		GregorianCalendar ge = new GregorianCalendar(yr,mnth-1,day);

		/* To End Date */
		StringTokenizer st1 = new StringTokenizer(endDate,"-");
		yr = 0;
		mnth = 0;
		day = 0;
		if(st1.hasMoreTokens())
		{
			yr = Integer.parseInt(st1.nextToken().trim());
		}
		if(st1.hasMoreTokens())
		{
			mnth = Integer.parseInt(st1.nextToken().trim());
		}
		if(st1.hasMoreTokens())
		{		
			day = Integer.parseInt(st1.nextToken().trim());
		}
		GregorianCalendar ge1 = new GregorianCalendar(yr,mnth-1,day);
		
		try
		{
			/* 	Setting the JNDI name and Environment 	*/
			obj.setJndiName("SessionReportsDetailsManager");
	   		obj.setEnvironment();
	   		
			/* 	Creating the Home and Remote Objects 	*/
			SessionReportsDetailsManagerHome qtyProHomeObj = (SessionReportsDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionReportsDetailsManagerHome.class);
			SessionReportsDetailsManager qtyProObj = (SessionReportsDetailsManager)PortableRemoteObject.narrow(qtyProHomeObj.create(),SessionReportsDetailsManager.class);
		
			objQuantityProducedDetails = qtyProObj.fetchQuantityProducedDetails(ge.getTime(),ge1.getTime());
			if (BuildConfig.DMODE)
				System.out.println("objQuantityProducedDetails: "+ objQuantityProducedDetails.length);
			dataSource = new JRBeanArrayDataSource(objQuantityProducedDetails);
			
			parameters.put("Title", "Quantity Produced from "+stDate+" to "+edDate);
			parameters.put("startDate",stDate);
			parameters.put("endDate",edDate);
			is = this.getClass().getClassLoader().getResourceAsStream("/reports/QuantityProduced.jasper");
			jr = (JasperReport)JRLoader.loadObject(is);
		}catch (Exception e)
		{
			if (BuildConfig.DMODE)
				System.out.println("Error: "+ e.toString());
		}
	}
	
	if (isPdf.equals("1"))
	{
		byte[] bytes = JasperRunManager.runReportToPdf(jr,parameters, dataSource);
		response.setContentType("application/pdf");
		response.setContentLength(bytes.length);
		ServletOutputStream ouputStream = response.getOutputStream();
		ouputStream.write(bytes, 0, bytes.length);
		ouputStream.flush();
		ouputStream.close();
	}
	else
	{
%>
		<html>
		<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
		<script>
			function printReport()
			{
				printTab.style.display="none";
				window.print();
				printTab.style.display="block";
			}
			
			function pdfReport()
			{
				document.forms[0].pdf.value = "1";
				document.forms[0].action='<bean:message key="context"/>/Reports/QuantityProduced/QuantityProducedResult.jsp';
				document.forms[0].submit();
			}
			
			function closeReport()
			{
				document.forms[0].action='<bean:message key="context"/>/Reports/QuantityProduced/QuantityProduced.jsp';
				document.forms[0].submit();
			}
			
			function emailReport()
			{
				document.forms[0].action='<bean:message key="context"/>/Reports/QuantityProduced/QuantityProducedEmail.jsp';
				document.forms[0].submit();		  
			}
		</script>
		<form name="frmQtyProedRes">
			<input type='hidden' name='startDate' value='<%=request.getParameter("startDate")%>'>
			<input type='hidden' name='endDate' value='<%=request.getParameter("endDate")%>'>
			<input type='hidden' name='pdf'>
			<br>
			<table width="100%" cellpadding="0" cellspacing="0" id="printTab">
				<tr>
					<td class="TopLnk"><a href="javascript:printReport()">Print</a> | 
					<a href="javascript:emailReport()">Email</a> | 
					<a href="javascript:pdfReport()">PDF</a> | <a href="javascript:closeReport()">Close</a></td>
					<td>&nbsp;&nbsp;</td>
				</tr>
			</table>
		</form>
		</html>    
<%
		JasperPrint jasperPrint = JasperFillManager.fillReport(jr, parameters,dataSource);
		JRHtmlExporter exporter = new JRHtmlExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
		exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
		exporter.exportReport(); 
	}
%>
