/*
 * Created on Nov 3, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.infra.util;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import org.apache.log4j.HTMLLayout;
import org.apache.log4j.WriterAppender;

/**
 * @author kduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class LoggerOutput 
{
	public static FileDescriptor outFD = null;
	public static WriterAppender getAppender()
	{
		FileOutputStream output;
	    WriterAppender appender = null;
	    HTMLLayout layout = new HTMLLayout();
	    
		try 
		{
			Calendar cal = Calendar.getInstance();
			if(outFD==null)
			{
				output = new FileOutputStream("log_"+ cal.get(Calendar.YEAR)+"_"+(cal.get(Calendar.MONTH)+1)+"_"+cal.get(Calendar.DAY_OF_MONTH)+".html",true);
				outFD = output.getFD();
			}
			else
			{
				output = new FileOutputStream(outFD);
			}
			appender = new WriterAppender(layout,output);
		}
		catch (FileNotFoundException e1) 
		{
			System.out.println("FILE NOT FOUND EXCEPTION");
			e1.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return appender;
	}
}

/***
$Log: LoggerOutput.java,v $
Revision 1.5  2005/09/23 07:15:26  vkrishnamoorthy
Logging file output date modified.

Revision 1.4  2005/09/02 06:59:53  vkrishnamoorthy
File name changed with FileDescriptor in order to overwrite the existing output file.

Revision 1.2  2004/11/06 07:43:14  sduraisamy
Log inclusion for Filter,DBConnection,DBNull,SQLMaster,DateUtil,Debug and LoggerOutput

***/