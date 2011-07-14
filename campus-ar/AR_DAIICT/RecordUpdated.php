<html>
<title>Record update</title>
<body>

<?php

$hid=$_POST['hidenid'];

$con = mysql_connect("mrlnmd.db.5537199.hostedresource.com","mrlnmd","StHxMo3C");

if (!$con)
  {
  die('Could not connect to MySql server: ' . mysql_error());
  }

mysql_select_db("mrlnmd",$con);

  
$selected_radio = $_POST['want'];


if($selected_radio=="Upd")
{

$sql=("update pointdaiict set layartype= '$_POST[txtLType]',attribution='$_POST[txtAttr]',title='$_POST[txtTit]',latitude='$_POST[txtLat]',longitude='$_POST[txtLon]',layername='$_POST[txtLaynam]' where id=$hid ");

if (!mysql_query($sql,$con))
  {
  die('Error: ' . mysql_error());
  }

echo '<h2 align=center>Record Updated successfully</h2>';
}

else
{
$sql=("delete from pointdaiict where id=$hid ");

if (!mysql_query($sql,$con))
  {
  die('Error: ' . mysql_error());
  }

echo '<h2 align=center>Record Deleted successfully</h2>';
}
	
mysql_close($con);


?>

<table align="center">

<td><a href="http://mrl.openmap.in/AR_DAIICT/DisplayData.php"><input align="center" type= "button" name="view" value="view Records"></a>
&nbsp;<a href="http://mrl.openmap.in/AR_DAIICT/AddLocation.php"><input align="center" type= "button" name="addloc" value="Add a Location"></a></td>

</table>


</body>
</html>