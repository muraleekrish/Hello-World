/*
 * Created on Jun 22, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savantit.prodacs.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.wutka.jox.JOXBeanInputStream;
import com.wutka.jox.JOXBeanOutputStream;

/**
 * @author vkrishnamoorthy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CastorXML {

    /**
     *  Retrieves a bean object for the
     *  received XML and matching bean class
     */
    public static Object fromXML(String xml, Class className)
    {
        ByteArrayInputStream xmlData = new ByteArrayInputStream(xml.getBytes());
        JOXBeanInputStream joxIn = new JOXBeanInputStream(xmlData);

        try
        {
            return (Object) joxIn.readObject(className);
        } catch (IOException exc)
        {
            exc.printStackTrace();
            return null;
        } finally
        {
            try
            {
                xmlData.close();
                joxIn.close();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }
    /**
     * @param fit
     * @param className
     * @return
     */
    /**
     * @param fit
     * @param className
     * @return
     */
    public static Object fromXML(InputStream fit, Class className)
    {
        
        JOXBeanInputStream joxIn = new JOXBeanInputStream(fit);

        try
        {
            return (Object) joxIn.readObject(className);
        } catch (IOException exc)
        {
            exc.printStackTrace();
            return null;
        } finally
        {
            try
            {
                joxIn.close();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }

    /**
     * @param bean
     * @return
     */
    public static String toXML(Object bean)
    {
        ByteArrayOutputStream xmlData = new ByteArrayOutputStream();
        JOXBeanOutputStream joxOut = new JOXBeanOutputStream(xmlData);
        try
        {
            joxOut.writeObject(beanName(bean), bean);
            return xmlData.toString();
        } catch (IOException exc)
        {
            exc.printStackTrace();
            return null;
        } finally
        {
            try
            {
                xmlData.close();
                joxOut.close();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     *  Find out the bean class name
     */
    private static String beanName(Object bean)
    {
        String fullClassName = bean.getClass().getName();
        String classNameTemp = fullClassName.substring(fullClassName.lastIndexOf(".") + 1,fullClassName.length());
        return classNameTemp.substring(0, 1) + classNameTemp.substring(1);
    }
}


/***
$Log: CastorXML.java,v $
Revision 1.1  2005/06/23 13:26:15  vkrishnamoorthy
Initial commit on ProDACS.

***/