/*
 * Created on Apr 4, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savantit.prodacs.presentation.menuconfiguration;


import java.util.Vector;
import com.savantit.prodacs.infra.util.BuildConfig;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.savantit.prodacs.businessimplementation.securityadmin.UserAuthDetails;


/**
 * @author vkrishnamoorthy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MenuConfiguration {
	    Vector vUserAuth = new Vector();
	    public String getMenuItems(UserAuthDetails userDetails)
	    {
	        vUserAuth = userDetails.getVecUserAuth();
	        String menuItems="";
	        String result = "";
	        String footer = "]]";        
	        
	        try
	        {
	            //create an object of the Document implementation class
	            
	            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory. newInstance(); 
	            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder(); 
	            Document doc = docBuilder.parse(this.getClass().getClassLoader().getResourceAsStream("MenuConfiguration.xml")); 
	            menuItems = "[['<b><u>"+doc.getDocumentElement().getAttribute("name")+"</u></b>', null";
	            String parsed ="";
	            Vector v= new Vector();
	            parsed = printElements(doc.getChildNodes().item(0).getChildNodes(),parsed,v);
	            
	            result = menuItems+parsed+footer;
	            if (BuildConfig.DMODE)
	            {
	            	System.out.println(result);
	            }
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
	        
	        return result;
	    }
	    
	    
	    //recursive function that prints all elements of the XML document
	    
	    private String printElements(NodeList nodeList,String parsed,Vector v)
	    {
	        for(int i=0;i<nodeList.getLength();i++)
	        {
	            Node node;
	            if((node= nodeList.item(i))!=null)
	            {
	                if(node.getNodeType() == Node.ELEMENT_NODE)
	                {
	                    
	                    String name ="";
	                    String link ="";
	                    String id="0";
	                    
	                    for(int j=0;j<node.getAttributes().getLength();j++)
	                    {
	                        if(node.getAttributes().item(j).getNodeName().equals("name"))
	                            name = node.getAttributes().item(j).getNodeValue();
	                        else if(node.getAttributes().item(j).getNodeName().equals("link"))
	                            link = node.getAttributes().item(j).getNodeValue();
	                        else
	                            id= node.getAttributes().item(j).getNodeValue();
	                    }

	                    
	                    String temp="";
	                    if(link.equals("null"))
	                    {
	                        if(node.hasChildNodes())
	                        {
	                            temp = "['" + name+"',"+link;
	                            v.add(new Integer(node.getChildNodes().getLength()/2));
	                        }
	                    }
	                    else
	                    {
	                        if(vUserAuth.contains(new Integer(id))||vUserAuth.contains(new Integer(Integer.parseInt(id)+1000)))
	                            temp = "['" + name+"','"+link+"']";
	                    }                   
	                    
	                    int t = 0;
	                    if(!v.isEmpty())
	                    {
	                        if(((Integer)v.elementAt(v.size()-1)).intValue()==0)
	                        {
	                            if(vUserAuth.contains(new Integer(id))||vUserAuth.contains(new Integer(Integer.parseInt(id)+1000)))
	                                temp = temp+",]";
	                            else
	                            {
	                                temp = temp+"]";
	                                t++;
	                            }
	                            v.removeElementAt(v.size()-1);
	                        }
	                    }
	                    
	                    
	                    if(!v.isEmpty())
	                    {
	                        int val = ((Integer)v.elementAt(v.size()-1)).intValue()-1;
	                        v.removeElementAt(v.size()-1);
	                        if(val>=0)
	                            v.add(new Integer(val));
	                        else
	                        {
	                            if(vUserAuth.contains(new Integer(id))||vUserAuth.contains(new Integer(Integer.parseInt(id)+1000)))                            
	                                temp = temp+",]";
	                            else
	                            {
	                                temp = temp+"]";
	                                t++;
	                            }
	                        }
	                    }
	                    
	                    
	                    if(t==2)
	                    {
	                        parsed = parsed.substring(0 ,parsed.lastIndexOf("[")-1);
	                        temp = "]";
	                    }
	                    
	                    if(temp.equals("]"))
	                    {      
	                        if(parsed.lastIndexOf("[")>parsed.lastIndexOf("]"))
	                        {
		                       	parsed = parsed.substring(0 ,parsed.lastIndexOf("[")-1);
		                        temp = "";
	                        }

	                    }                    
	                    if(!temp.trim().equals(""))
	                        parsed = parsed +","+temp;
	                    
	                    if (BuildConfig.DMODE)
	    	            {
	                    	System.out.println("temp : "+temp);
	                    	System.out.println(parsed);
	    	            }
	                   
	                    parsed = printElements(node.getChildNodes(),parsed,v);
	                }
	            }
	        }
	        return parsed;
	    }
	    
	    
	    public static void main(String args[])
	    {
	        MenuConfiguration test = new MenuConfiguration();
	        test.getMenuItems(new UserAuthDetails());
	    }
	}

/***
$Log: MenuConfiguration.java,v $
Revision 1.4  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.3  2005/04/05 12:41:52  vkrishnamoorthy
Unwanted Println's removed.

Revision 1.2  2005/04/05 07:51:24  vkrishnamoorthy
Modified for dynamic menu.

Revision 1.1  2005/04/04 12:46:37  vkrishnamoorthy
Initial Commit on Prodacs.

***/
