<html>
<title>DisplayData</title>
<body>

<?php

$con = mysql_connect("mrlnmd.db.5537199.hostedresource.com","mrlnmd","StHxMo3C");

if (!$con)
  {
  die('Could not connect to MySql server: ' . mysql_error());
  }

mysql_select_db("mrlnmd",$con);
  
$result = mysql_query("SELECT * FROM pointdaiict");

echo "<table border='1'>
<tr>
<th align=center>Id</th>
<th align=center>Layar Type</th>
<th align=center>Attribution </th>
<th align=center>Title</th>
<th align=center>Latitude</th>
<th align=center>Longitude</th>
<th align=center>Layar Name</th>
</tr>";

while($row = mysql_fetch_array($result))
  {
	echo "<tr>";
	echo "<td>" . '<a href="http://mrl.openmap.in/AR_DAIICT/UpdateLocation.php?id='.$row['id'].'">'. $row['id'] .'</a>' .  "</td>";
	echo "<td>" . $row['LayarType'] . "</td>";
	echo "<td>" . $row['Attribution'] . "</td>";
	echo "<td>" . $row['Title'] . "</td>";
	echo "<td>" . $row['Latitude'] . "</td>";
	echo "<td>" . $row['Longitude'] . "</td>";
	echo "<td>" . $row['LayerName'] . "</td>";
  echo "</tr>";
  }
echo "</table>";


mysql_close($con);


?>


</body>
</html>