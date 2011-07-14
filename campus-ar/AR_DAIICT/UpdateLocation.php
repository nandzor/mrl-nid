<html>
<head>
<title>Update Location</title>
<script type="text/javascript" language="javascript">

function clearall()
	{
		var form = document.updateform;
		form.txtLType.value='';
		form.txtAttr.value = "";
		form.txtTit.value = "";
		form.txtLat.value = "";
		form.txtLon.value="";
		form.txtLaynam.value = "";
	}


function valid1()
{
       		var form = document.updateform;

		if(trimAll(form.txtLType.value)=='')
		{
			alert('Enter Layar Type');
			form.txtLType.focus();
			return false;
		}
		if(trimAll(form.txtAttr.value) == '')
		{
			alert('Enter Attribution');
			form.txtAttr.focus();
			return false;
		}
		if(trimAll(form.txtTit.value)=='')
		{
			alert('Enter Title');
			form.txtTit.focus();
			return false;
		}
		if(trimAll(form.txtLat.value)=='')
		{
			alert('Enter Latitude');
			form.txtLat.focus();
			return false;
		}
		if(trimAll(form.txtLon.value)=='')
		{
			alert('Enter Longitude');
			form.txtLon.focus();
			return false;
		}

		if(trimAll(form.txtLaynam.value)=='')
		{
			alert('Enter Layer Name');
			form.txtLaynam.focus();
			return false;
		}

	if(isNaN(form.txtLat.value))
	{
		alert('Latitude contains only numeric data');
		form.txtLat.value="";
		form.txtLat.focus();
		return false;
	}

	if(isNaN(form.txtLon.value))
	{
		alert('Longitude contains only numeric data');
		form.txtLon.value="";
		form.txtLon.focus();
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
<body>
<strong>
<font face="Verdana, Geneva, sans-serif" color="#666666">

  

<?php

$sid=$_GET['id'];

$con = mysql_connect("mrlnmd.db.5537199.hostedresource.com","mrlnmd","StHxMo3C");

if (!$con)
  {
  die('Could not connect to MySql server: ' . mysql_error());
  }

mysql_select_db("mrlnmd",$con);

  
$result = mysql_query("select LayarType,Attribution,Title,Latitude,Longitude,LayerName from pointdaiict where id=$sid");

$i=0;


$ltype=mysql_result($result,$i,"LayarType");
$attr=mysql_result($result,$i,"Attribution");
$tit=mysql_result($result,$i,"Title");
$lat=mysql_result($result,$i,"Latitude");
$lon=mysql_result($result,$i,"Longitude");
$laynam=mysql_result($result,$i,"LayerName");

mysql_close($con);
?>

  <form name="updateform" method="post" action="RecordUpdated.php" onSubmit="return valid1()">

<table width="300" align="center">

<tr>
<h3 align="center">Update Location Details</h3>
</tr>

<tr>
<td align="center" height="30" bgcolor="#F0F0F0">Layar Type</td>
<td>&nbsp;<input type="text" name="txtLType" value='<?php echo $ltype ?>' ></td>
</tr>

<tr>
<td align="center" width="265" height="39" bgcolor="#F0F0F0" >Attribution</td>
<td width="370">&nbsp;<input type="text" name="txtAttr" value='<?php echo $attr ?>' ></td>
</tr>

<tr>
<td align="center" height="30" bgcolor="#F0F0F0">Title</td>
<td>&nbsp;<input type="text" name="txtTit" value='<?php echo $tit ?>'></td>
</tr>

<tr>
<td align="center" height="34" bgcolor="#F0F0F0">Latitude</td>
<td>&nbsp;<input type="text" name="txtLat" value='<?php echo $lat ?>'></td>
</tr>

<tr>
<td align="center" height="36" bgcolor="#F0F0F0">Longitude</td>
<td>&nbsp;<input type="text" name="txtLon" value='<?php echo $lon ?>'></td>
</tr>

<tr>
<td align="center" height="36" bgcolor="#F0F0F0">Layer Name</td>
<td>&nbsp;<input type="text" name="txtLaynam" value='<?php echo $laynam ?>'></td>
</tr>

<tr>
<td align="center" height="36" bgcolor="#F0F0F0">Want to</td>
<td>&nbsp;<input type="radio" name="want" value="Upd" checked /> Update<br/><br/>
&nbsp;<input type="radio" name="want" value="Del"> Delete</td>
</tr>

<input type= "hidden" name="hidenid" value="<?php echo $sid?>">

<td colspan=2 height="72"" align="center" bgcolor="#F0F0F0"><input type= "submit" name="save" value="Save">

</table>
      
</form>
 
</font>
</strong>

</body>
</html>
