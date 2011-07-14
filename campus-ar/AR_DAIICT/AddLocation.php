<html>
<head>
<title>AddLocation</title>
<script type="text/javascript" language="javascript">

function clearall()
	{
		var form = document.entryform;
		form.txtLayarType.value=='';
		form.txtAttribution.value = "";
		form.txtTitle.value = "";
		form.txtLatitude.value = "";
		form.txtLongitude.value="";
    		form.txtLayerName.value="";
	}


function valid()
{
       		var form = document.entryform;

		if(trimAll(form.txtLayarType.value)=='')
		{
			alert('Enter Layar Type');
			form.txtLayarType.focus();
			return false;
		}
		if(trimAll(form.txtAttribution.value) == '')
		{
			alert('Enter Attribution');
			form.txtAttribution.focus();
			return false;
		}
		if(trimAll(form.txtTitle.value)=='')
		{
			alert('Enter Title');
			form.txtTitle.focus();
			return false;
		}
		if(trimAll(form.txtLatitude.value)=='')
		{
			alert('Enter Latitude');
			form.txtLatitude.focus();
			return false;
		}
		if(trimAll(form.txtLongitude.value)=='')
		{
			alert('Enter Longitude');
			form.txtLongitude.focus();
			return false;
		}

		if(trimAll(form.txtLayerName.value)=='')
		{
			alert('Enter Layer Name');
			form.txtLayerName.focus();
			return false;
		}

	if(isNaN(form.txtLatitude.value))
	{
		alert('Latitude contains only numeric data');
		form.txtLatitude.value="";
		form.txtLatitude.focus();
		return false;
	}

	if(isNaN(form.txtLongitude.value))
	{
		alert('Longitude contains only numeric data');
		form.txtLongitude.value="";
		form.txtLongitude.focus();
		return false;
	}

	function trimAll(sString)
	{
		while (sString.substring(0,1) == ' ')
		{
			sString = sString.substring(1, sString.length);
		}
		while (sString.substring(sString.length-1, sString.length) == ' ')
		{
			sString = sString.substring(0,sString.length-1);
		}
		return sString;
	}

        return true;
}
</script>
</head>

<body onLoad="clearall()">
<strong>
<font face="Verdana, Geneva, sans-serif" color="#666666">

<form name="entryform" action="RecordAdded.php" method="post" onSubmit="return valid()">

<table width="300" align="center">

<tr>
<h3 align="center">Fill up Location Details</h3>
</tr>

<tr>
<td align="center" height="30" bgcolor="#F0F0F0">Layar Type</td>
<td>&nbsp;<input type="text" name="txtLayarType" ></td>
</tr>

<tr>
<td align="center" width="265" height="39" bgcolor="#F0F0F0" >Attribution</td>
<td width="370">&nbsp;<input type="text" name="txtAttribution" ></td>
</tr>

<tr>
<td align="center" height="30" bgcolor="#F0F0F0">Title</td>
<td>&nbsp;<input type="text" name="txtTitle" ></td>
</tr>

<tr>
<td align="center" height="34" bgcolor="#F0F0F0">Latitude</td>
<td>&nbsp;<input type="text" name="txtLatitude"></td>
</tr>

<tr>
<td align="center" height="36" bgcolor="#F0F0F0">Longitude</td>
<td>&nbsp;<input type="text" name="txtLongitude"></td>
</tr>

<tr>
<td align="center" height="36" bgcolor="#F0F0F0">Layer Name</td>
<td>&nbsp;<input type="text" name="txtLayerName"></td>
</tr>

<td colspan=2 height="72"" align="center" bgcolor="#F0F0F0"><input type= "submit" name="Submit" value="Submit" ><a href="http://mrl.openmap.in/AR_DAIICT/DisplayData.php" target="_blank"><input type= "button" name="view" value="View Records" ></a></td>

</table>
      
</form>
 
</font>
</strong>

</body>
</html>
