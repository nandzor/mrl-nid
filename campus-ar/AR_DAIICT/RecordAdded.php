<html>
<title>RecordAdded</title>
<body>

<?php

$con = mysql_connect("mrlnmd.db.5537199.hostedresource.com","mrlnmd","StHxMo3C");

if (!$con)
  {
  die('Could not connect to MySql server: ' . mysql_error());
  }

mysql_select_db("mrlnmd",$con);

  
$sql=("insert into pointdaiict (layartype,attribution,title,latitude,longitude,layername) values ('$_POST[txtLayarType]','$_POST[txtAttribution]','$_POST[txtTitle]','$_POST[txtLatitude]','$_POST[txtLongitude]','$_POST[txtLayerName]')");

if (!mysql_query($sql,$con))
  {
  die('Error in query: ' . mysql_error());
  }

echo '<h2 align=center>Record added successfully</h2>';

mysql_close($con);


?>

<table align="center">

<td><a href="http://mrl.openmap.in/AR_DAIICT/DisplayData.php"><input align="center" type= "button" name="viewr" value="view Records"></a>
&nbsp;<a href="http://mrl.openmap.in/AR_DAIICT/AddLocation.php"><input align="center" type= "button" name="addloca" value="Add a Location"></a></td>

</table>


</body>
</html>