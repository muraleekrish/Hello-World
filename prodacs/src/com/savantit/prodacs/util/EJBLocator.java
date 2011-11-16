package com.savantit.prodacs.util;

import java.io.InputStream;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.savantit.prodacs.infra.util.BuildConfig;



/**
 * @author kduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class EJBLocator 
{
		 private Properties props;
		 private String initialContextFactory;
		 private String contextProviderUrl;
		 private InitialContext ctx;
		 private String jndiName;
		 private Object home;

		  public EJBLocator()
		  {
		    props = new Properties();
		    try
			{
				InputStream is = EJBLocator.class.getClassLoader().getResourceAsStream("EJBLocator.properties");
				Properties p = new Properties();
				p.load(is);
				is.close();	    	
			    initialContextFactory = p.getProperty("initialContextFactory");
			    contextProviderUrl=p.getProperty("protocol")+"://"+p.getProperty("hostname")+":"+p.getProperty("port");
			    if (BuildConfig.DMODE)
			    	System.out.println("contextProviderUrl : "+contextProviderUrl);
		    }
		    catch(Exception e)
			{
		    	e.printStackTrace();
			    initialContextFactory = "org.jnp.interfaces.NamingContextFactory";
			    contextProviderUrl="jnp://st-17:1099";
		    }
		    ctx = null;
		    jndiName = "";
		    home = null;
		  }

		  public void setInitialContextFactory(String initialContextFactory)
		  {
		    this.initialContextFactory = initialContextFactory;
		  }

		  public String getInitialContextFactory()
		  {
		     return this.initialContextFactory;
		  }

		  public void setContextProviderUrl(String contextProviderUrl)
		  {
		      this.contextProviderUrl = contextProviderUrl;
		  }

		  public String getContextProviderUrl()
		  {
		    return this.contextProviderUrl;
		  }

		  public void setJndiName(String jndiName)
		  {
		    this.jndiName = jndiName;
		  }

		  public String getJndiName()
		  {
		    return this.jndiName;
		  }

		  public Object getHome()
		  {
		    return this.home;    
		  }
		  
		  public void setEnvironment()
		  {
		    props.put(Context.INITIAL_CONTEXT_FACTORY,getInitialContextFactory());
		    props.put(Context.PROVIDER_URL,getContextProviderUrl());
		    try
			{
		    ctx = new InitialContext(props);  
		    this.home = ctx.lookup(getJndiName());
			}catch(NamingException e)
			{
				if (BuildConfig.DMODE)
					System.out.println(e);
			}
		  }
		  
		  public static void main(String args[])
		  {
		  
		  	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
		  
		  	
		  }
	}
