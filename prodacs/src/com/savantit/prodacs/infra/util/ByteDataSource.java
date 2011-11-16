/*
 * Created on Aug 23, 2004
 */
package com.savantit.prodacs.infra.util;

/**
 * @author ksubramanian
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import javax.activation.DataSource;

/**
 * This Class is an implementation of the Interface javax.activation.DataSource and is alomost similar to the 
 * FileDataSource except that this ByteDataSource deals with the byte[] data instead of a File in a FileSystem.  
 */

public class ByteDataSource implements DataSource, Serializable
{
	private byte[] bytes;
	
	/**
	 * 
	 * @param bytes
	 */	
	public ByteDataSource(byte[] bytes)
	{
		System.out.println("ByteDataSource(byte[] bytes) called - " + bytes.length);
		this.bytes = bytes;
	//	this.inputStreamData = new ByteArrayInputStream(this.bytes);
	}
	
	/**
	 * 
	 * @param is
	 * @throws IOException
	 */
	public ByteDataSource(InputStream is) throws IOException
	{
		System.out.println("ByteDataSource(InputStream is) called");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int c;
		while ((c = is.read()) != -1)
		{
			baos.write(c);
		}
		this.bytes = baos.toByteArray();
	}

	
	
	/**
	 * Default MIME Type of any binary Data
	 */	
	public String getContentType()
	{
		return "application/octet-stream";
	}
	
	/**
	 * 
	 */		
	public InputStream getInputStream()	throws IOException
	{	
		return new ByteArrayInputStream(this.bytes);
	}
	
	/**
	 * 
	 */	
	public String getName()
	{
		return "objName"; // dummy name
	}

	public ByteArrayOutputStream getByteArrayOutputStream() throws IOException
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bos.write(bytes);
		return bos;	
	}
	
	public byte[] getBytes()
	{
		return this.bytes;
	}

	/**
	 * @see javax.activation.DataSource#getOutputStream()
	 */
	public OutputStream getOutputStream() throws IOException 
	{
		return getByteArrayOutputStream();
	}
}