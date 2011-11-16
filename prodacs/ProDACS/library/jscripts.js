////////// JavaScript for Prodacs Version 1.0 Build 1.0.0001
////////// Copyright 2004 Savant Technologies (p) Ltd. All Rights Reserved

////////// Global Variables \\\\\\\\\\

var currentchecked = "None";
var currentcheckcount = 0;

var imgSrcHide = "/ProDACS/images/hide.gif";
var imgSrcShow = "/ProDACS/images/show.gif";

var imgPalHide = "/ProDACS/images/pal_hide.gif";
var imgPalShow = "/ProDACS/images/pal_show.gif";

////////// Global Variables \\\\\\\\\\

function showFilter(show, img) // Filter Script for Show/Hide the Palette
	{
		if(show.style.display == "none")
			{
				show.style.display = "block";
				img.src = imgSrcHide;
				img.title = "Hide";
			}
		else
			{
				show.style.display = "none";
				img.src = imgSrcShow;
				img.title = "Show";
			}
	}

function checkAlll(frmPage) // Check All Script for Table List
	{
		table = frmPage.CheckAll.parentNode.parentNode.parentNode;
		if(frmPage.CheckAll.checked == true)
			{
				for(i=1;i<table.children.length;i++)
					{
						table.children[i].children[0].children[0].checked=true;
						currentcheckcount++;
						if(table.children[i].children[0].className == "InValid")
							{
								table.children[i].children[0].children[0].checked=false;
								currentcheckcount--;
							}
					}
			}
		else
			{
				for(i=1;i<table.children.length;i++)
				{
					table.children[i].children[0].children[0].checked=false;
					currentcheckcount--;
				}
			}
	}

function checkAll(frmPage)//chkAllMsg(frmPage)
	{
		currentchecked = "None";
		currentcheckcount = 0;
		len = frmPage.elements["CheckValue"];
		if(len!=undefined)
		{
		  if(frmPage.CheckAll.checked==true)
		  {
			if(len.length==undefined)
			{
				if (len.parentNode.className != "InValid")
				{
					len.checked=true;
					return;
				}
			}
			for(i=0;i<len.length;i++)
			{
				if ((len[i].parentNode.className == "TableItems2") || (len[i].parentNode.className == "TableItems"))
				{
					len[i].checked=true;
					isSimilar(len[i]);
				}
				else
					len[i].checked =false;
				}
			}
		  else
		  {
			if(len.length==undefined)
			{
				len.checked=false;
				return;
			}
			for(i=0;i<len.length;i++)
			{
				len[i].checked=false;
				isSimilar(len[i]);
			}
			currentchecked = "None";
			currentcheckcount = 0;
			}
		}
	}

function checkAllDym(frmPage) // Check All Script for Dynamic Table List
	{
		table = frmPage.CheckAllDym.parentNode.parentNode.parentNode;
		if(frmPage.CheckAllDym.checked == true)
			{
				for(i=1;i<table.children.length;i++)
					{
						table.children[i].children[0].children[0].checked=true;
						if(table.children[i].children[0].className == "InValid")
							{
								table.children[i].children[0].children[0].checked=false;
							}
					}
			}
		else
			{
				for(i=1;i<table.children.length;i++)
				{
					table.children[i].children[0].children[0].checked=false;
				}
			}
	}

function checkAllDymSub(frmPage) // Check All Script for Dynamic Table List
	{
		table = frmPage.CheckAllDymSub.parentNode.parentNode.parentNode;
		if(frmPage.CheckAllDymSub.checked == true)
			{
				for(i=1;i<table.children.length;i++)
					{
						table.children[i].children[0].children[0].checked=true;
						if(table.children[i].children[0].className == "InValid")
							{
								table.children[i].children[0].children[0].checked=false;
							}
					}
			}
		else
			{
				for(i=1;i<table.children.length;i++)
				{
					table.children[i].children[0].children[0].checked=false;
				}
			}
	}

function modifyRow(tabId, frmPage) // Modify Script according to checked value
	{
		var obj=document.getElementById(tabId);
		var count = 0;
		var j=0;
		
		if (obj.children[0].children.length>2) 
		{
			for(var i = 1; i < obj.children[0].children.length; i++)
				{
					if(frmPage.CheckValue[j].checked)
						{
							if(obj.children[0].children[i].children[0].className == "InValid")
								{
									alert("Invalid Item cannot be Modified");
									return false;
								}
							count++;
						}
					j++;
				}

			if(count > 1)
				{
					alert("Please Select only one Item from List to Modify");
					count=0;
					return false;
				}
			else if(count < 1)
				{
					alert("Please Select a Item from List to Modify");
					count=0;
					return false;
				}
		}
		if (obj.children[0].children.length < 3)
		{
			if (frmPage.CheckValue != undefined)
			{
				if (frmPage.CheckValue.checked == false)
				{
					alert ("Please Select a Item from List to Modify");
					return false;
				}
				else if (obj.children(0).children(1).children(0).className == 'InValid')
				{
					alert("Invalid Item cannot be Modified");
					return false;
				} 
			}
			else
			{
				alert("There is/are no item(s) in list to modify!");
				return false;
			}
		}

  return true;
	}

function viewDetail(tabId, frmPage) // View Page script according to checked value
	{
		var obj=document.getElementById(tabId);
		var count = 0;
		var j=0;
		if (obj.children[0].children.length>2) 
			{
				for(var i = 1; i < obj.children[0].children.length; i++)
					{
						if(frmPage.CheckValue[j].checked)
							{
							    count++;
							}
						j++;
					}

				if(count > 1)
				{
					alert("Please Select only one Item from List to View Details");
					count=0;
					return false;
				}
				else if(count<1)
				{
					alert("Please Select a Item from List to View Details");
					count=0;
					return false;
				}
			}
		if (obj.children[0].children.length < 3)
		{
			if (frmPage.CheckValue != undefined)
			{
				if (frmPage.CheckValue.checked == false)
				{
					alert ("Please Select a Item from List to View Details");
					return false;
				}
			}
			else
			{
				alert("There is/are no item(s) in list to view!");
				return false;
			}
		}
		return true;
	}
function getCheckedValueDetail(tabId, frmPage)//chkDetail(tabId, frmPage)
	{
		var obj=document.getElementById(tabId);
		var count = 0;
		var j=0;
		var val;
		if (obj.children[0].children.length==2)
		{
			if(frmPage.CheckValue.checked)
			{
			    val = frmPage.CheckValue.value;
			}
		return val;
		} 		
		if (obj.children[0].children.length>2) 
			{
				for(var i = 1; i < obj.children[0].children.length; i++)
					{
						if(frmPage.CheckValue[j].checked)
							{
							    val = frmPage.CheckValue[j].value;
							    break;
							}
						j++;
					}
			}
		return val;
	}

function getMultipleCheckedValue(tabId, frmPage)
	{
		var obj=document.getElementById(tabId);
		var count = 0;
		var j=0;
		var c=0;
		var val = new Array();
		if (obj.children[0].children.length==2)
		{
			if(frmPage.CheckValue.checked)
			{
			    val[0] = frmPage.CheckValue.value;
			}
		return val;
		} 		
		if (obj.children[0].children.length>2) 
			{
				for(var i = 1; i < obj.children[0].children.length; i++)
					{
						if(frmPage.CheckValue[j].checked)
							{
							    val[c] = frmPage.CheckValue[j].value;
							    c++;
							}
						j++;
					}
			}
		return val;
	}	

function deleteRow(tabID, frmPage)
	{
		var count = 0;
		var obj=document.getElementById(tabID);
		var len = obj.children[0].children.length;
		var obj1 = obj.children[0];
		if(obj.children[0].children.length==1)
		{
		  alert("There is no Item in the List to Delete");
		  return false;
		}
		if (obj.children[0].children.length==2)
		{
				if(frmPage.CheckValue.checked)
				{
					if(!confirm("Warning! \n\nThe chosen item/s will be Deleted !"))
					{	
						return false;
					}
					else 
					{
						return true;
					}
				}
				else
				{
						alert("Please Select an Item from List to Delete");
						count=0;
						return false;
				}
			return true;
		} 
		
		if (obj.children[0].children.length>2) 
		{
			for(var i = 1; i <obj.children[0].children.length ; i++)
			{
    			if(frmPage.CheckValue[i-1].checked)
				{
					count++;
				}
			}
			if (count < 1)
			{
				alert("Please Select an Item to Delete");
			}
			if(count>0)
			{
				if(!confirm("Warning! \n\nThe chosen item/s will be Deleted !"))
				{	
					return false;
				}
				else
				{
					return true;
				}
			}
		}
	}





function callConfirm() 
{
	var Confirmed=0;
	
	
	if(!confirm("Warning! \n\nThe chosen item/s will be Deleted !"))
	{	
		Confirmed = 0;
	}
	else
	{
		Confirmed = 1;
	}
	return Confirmed;
}


function validRow(tabId, frmPage)
	{
		currentchecked = "None";
		currentcheckcount=0;
		var count = 0;
		var j=0;
		var obj=document.getElementById(tabId);
		var warned = false;
		var warnInfo = "";
		if(obj.children[0].children.length==1)
		{
		  alert("There is no Item in the List Make Valid / Invalid");
		  return false;
		}
		if (obj.children[0].children.length==2)
		{
				if(!frmPage.CheckValue.checked)
				{
					alert("Please Select an Item from List to Make Valid/Invalid");
					count=0;
					return false;
				}
				else
				{
					var obj1 = obj.children[0].children[1].children[0];
					if (obj1.className == "Invalid")
						warnInfo = "Warning! \n\nThe chosen item/s will become Valid !"
					else 
						warnInfo = "Warning! \n\nThe chosen item/s will become Invalid !"
					if(!confirm(warnInfo))
					{	
						return false;
					}
					else
					{
						return true;
					}
				
				}
				
			return true;
		}				
		if (obj.children[0].children.length>2) 
		{
			for(var i = 1; i < obj.children[0].children.length; i++)
			{
				if(frmPage.CheckValue[j].checked)
				{
					count++;
					var obj1 = obj.children[0].children[i].children[0];

					if (obj1.className == "Invalid")
						warnInfo = "Warning! \n\nThe chosen item/s will become Valid !"
					else 
						warnInfo = "Warning! \n\nThe chosen item/s will become Invalid !"
					if(!warned && !confirm(warnInfo))
					{	
						return false;
					}
					else
					{
						return true;
					}
				}
				j++;
			}
			if (count < 1)
			{
				alert("Please Select an Item to make Invalid");
				return false;
			}
		}
		return true;
	}


function makeInValid(tabId, frmPage) // Make In-Valid Row Script for List Page
	{
		currentchecked = "None";
		currentcheckcount=0;
		var count = 0;
		var obj=document.getElementById(tabId);
		var warned = false;
		var warnInfo = "";
		if (obj.children[0].children.length>1) 
			{
				for(var i = 1; i < obj.children[0].children.length; i++)
					{
						if(frmPage.CheckValue[i-1].checked)
							{
								count++;
								var obj1 = obj.children[0].children[i].children[0];

								if (obj1.className == "InValid")
									warnInfo = "Warning! \n\nThe chosen item/s will become Valid !"
								else 
									warnInfo = "Warning! \n\nThe chosen item/s will become Invalid !"
								if(!warned && !confirm(warnInfo))
									{	
										return false;
									}
								else
									{
										 warned = true;
										if (obj1.className == "InValid")
											{
												obj1.className = obj.children[0].children[i].children[1].className;
												obj1.children[0].checked = false;
											}
										else
											{
												obj1.className = "InValid";
												obj1.children[0].checked = false;
											}
												currentchecked = 0;
												currentchecked = "None";
									}
							}
					}
				if (count < 1)
					{
						alert("Please Select an Item to make Invalid");
					}
			}
	}

function isSimilar(currentcheckedrow) // Checking Similarities between Valid & In-Valid Rows
	{
		if (currentcheckedrow.checked==true)
			{
				if (currentcheckedrow.parentNode.className == "InValid")
					{
						if(currentchecked == "None" || currentchecked == "Invalid")
						{
							currentchecked = "Invalid";
							currentcheckcount++;
							var obj = document.getElementById('mkValid');
							if (obj != null)
							{
								mkValid.style.display = "block";
							}
							var obj = document.getElementById('mkInValid');
							if (obj != null)
							{
								mkInValid.style.display = "none";
							}
						}
						else
						{
							alert("Dissimilar selection of Items Not Possible");
							currentcheckedrow.checked = false;
							return;
						}
					}
				else if (currentcheckedrow.parentNode.className=="TableItems"||currentcheckedrow.parentNode.className=="TableItems2") //The row classnames should not be modified in the future.
					{
						if(currentchecked == "None"||currentchecked == "valid")
							{
								currentchecked = "valid";
								currentcheckcount++;
								var obj = document.getElementById('mkValid');
								if (obj != null)
								{
									mkValid.style.display = "none";
								}
								var obj = document.getElementById('mkInValid');
								if (obj != null)
								{
									mkInValid.style.display = "block";
								}
							}
						else
							{
								alert("Dissimilar selection of Items Not Possible");
								currentcheckedrow.checked = false;
								return;
							}
					}
			}
		else
			{
				currentcheckedrow.parentNode.parentNode.parentNode.children(0).children(0).children(0).checked = false;
				currentcheckcount--;
				if(currentcheckcount==0)
					currentchecked = "None";
			}
	}

function sortTable(col) // Sort Column Script for List pages
	{
		direction = 1;
		if(col.sortOrder == 1) 
		direction = -1;
		header = col.parentNode;
		tab = header.parentNode;
		for(i=1;i<(header.children.length);i++)
		{
			header.children[i].children[0].style.visibility ="hidden" ;
			header.children[i].title = "Sort by" + header.children[i].innerText +" in Ascending";
		}

		col.children[0].style.visibility="visible";
		
		if(direction==-1)
			{
				col.children[0].src = '/ProDACS/images/sort.gif';
				col.title = "Sort by" + col.innerText + " in Ascending";
			}
		else
			{
				col.children[0].src ='/ProDACS/images/sort_up.gif';
				col.title = "Sort by" + col.innerText + " in Descending";
			}
		
		for(i=0;i<header.cells.length;i++)
			{
				if(header.cells[i]==col)
					{
						colNum = i;
						col.sortOrder=direction;
					}
				else
					header.cells[i].sortOrder=0;
			}

	}

function sortByTable(tabName,columnIndex,ascending)
	{
		 var tab = document.getElementById(tabName);
		 if(ascending=="true")
			 tab.children[0].children[0].children[columnIndex].sortOrder = -1;
		else
			 tab.children[0].children[0].children[columnIndex].sortOrder = 1;

		 sortTable(tab.children[0].children[0].children[columnIndex]);
	}

function showHide(show, img) // Show/Hide Script for Palettes
	{
		if(show.style.display == "none")
			{
				show.style.display = "block";
				img.src = imgPalHide;
				img.title = "Hide";
			}
		else
			{
				show.style.display = "none";
				img.src = imgPalShow;
				img.title = "Show";
			}
	}

function showHideImg(show, img) // Show/Hide Script for Image
	{
		if(show.style.display == "none")
			{
				show.style.display = "block";
				img.src = imgSrcHide;
				img.title = "Hide";
			}
		else
			{
				show.style.display = "none";
				img.src = imgSrcShow;
				img.title = "Show";
			}
	}

//////////////Start Login Page Script\\\\\\\\\\\\\\\\\\

function openIndex()
	{
		document.frmLogin.Submit.disabled = true;
		window.open('index.html','_self');
	}
function chkLoginId()
	{
		if(document.frmLogin.txtLoginId.value == 'prodacs')
			{
				if(document.frmLogin.txtLoginPwd.value == 'prodacs')
					{
						openIndex();
					}
				else
					{
						alert("Invalid Password");
						document.frmLogin.txtLoginPwd.focus();
						document.frmLogin.txtLoginPwd.value = 'prodacs';
						return false;
					}
			}
		else
			{
				alert("Invalid Authentication");
				document.frmLogin.txtLoginId.focus();
				document.frmLogin.txtLoginId.value = 'prodacs';
				return false;
			}
	}

//////////////End Login Page Script\\\\\\\\\\\\\\\\\\

function modfyRow(tabId, frmPage) // Modify Script according to checked value
	{
		var obj=document.getElementById(tabId);
		var count = 0;
		var j = 0;
		if (obj.children[0].children.length > 2) 
			{
				for(var i = 1; i < obj.children[0].children.length; i++)
					{
						if(frmPage.rdPayAdjust[j].checked)
							{
								if(obj.children[0].children[i].children[6].children[0].readOnly == true)
									{
										obj.children[0].children[i].children[6].children[0].readOnly = false;
										obj.children[0].children[i].children[7].children[0].readOnly = false;
										obj.children[0].children[i].children[8].children[0].readOnly = false;
										obj.children[0].children[i].children[6].children[0].className = "TBoxFullBrd";
										obj.children[0].children[i].children[7].children[0].className = "TBoxFullBrd";
										obj.children[0].children[i].children[8].children[0].className = "TBoxFullBrd";
										//return false;
									}
							    count++;
							}
							else if (obj.children[0].children[i].children[6].children[0].readOnly == false)
							{
								obj.children[0].children[i].children[6].children[0].readOnly = true;
								obj.children[0].children[i].children[7].children[0].readOnly = true;
								obj.children[0].children[i].children[8].children[0].readOnly = true;
								obj.children[0].children[i].children[6].children[0].className = "TextBoxFull";
								obj.children[0].children[i].children[7].children[0].className = "TextBoxFull";
								obj.children[0].children[i].children[8].children[0].className = "TextBoxFull";
							}
						j++;
					}
				if(count < 1)
					{
						alert("Please Select a Item from List to Modify");
						count=0;
						return false;
					}
			}
			else if (obj.children[0].children.length == 2)
			{
				if (frmPage.rdPayAdjust.checked)
				{
					obj.children[0].children[1].children[6].children[0].readOnly = false;
					obj.children[0].children[1].children[7].children[0].readOnly = false;
					obj.children[0].children[1].children[8].children[0].readOnly = false;
					obj.children[0].children[1].children[6].children[0].className = "TBoxFullBrd";
					obj.children[0].children[1].children[7].children[0].className = "TBoxFullBrd";
					obj.children[0].children[1].children[8].children[0].className = "TBoxFullBrd";
					return false;
				}
			}

  return true;
	}
