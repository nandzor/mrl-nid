<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Test Document</title>
 <script type="text/javascript"  language="javascript" src="http://mrl.openmap.in/Points_Manipulation/functions.js">
</script>
</head>

<body>
<?php
$user="mrlnmd";
$pwd="StHxMo3C";
$db="mrlnmd";
$tablename="pointdaiict";
$host="mrlnmd.db.5537199.hostedresource.com";
mysql_connect($host,$user,$pwd) or die("Connection Failed");
$dblink=mysql_select_db($db) or die("could not select the database");
$qry="SELECT * FROM ". $tablename;
$res_data=mysql_query($qry);
?>

<fieldset style="width:260px;">
<legend> Details: </legend>
<table width="200" border="0" cellspacing="1" cellpadding="1" >
  <?php
  // loop to create dynamic text fields
  $total_fields=mysql_num_fields($res_data);
  for($cnt=0;$cnt<$total_fields;$cnt++)
	{
		echo "<tr id='tr_".$cnt."'>";
			echo "<td><strong>".ucfirst(mysql_field_name($res_data,$cnt)).":</strong></td>";
			echo "<td><input type=\"text\" name=\"".ucfirst(mysql_field_name($res_data,$cnt)). "\" id=\"t".$cnt."\"></td>";
		echo "</tr>";
	}
  ?>
  <tr>
  	<td colspan="3"  nowrap="nowrap">
	  <div align="right">
		  <input type="button"  name="Submit" value="Save" onclick="SavaData(<?php echo $total_fields;?>);" />
		  <input type="button" name="Reset" value="Cancel" onclick="ClearAll()" />
	  </div>
  	</td>
  </tr>
</table>
</fieldset><br />

<div id ="divMain" style="width:100%;height:250px;;overflow:scroll;">
<table bordercolor="#333333" width=100% align="border="0" cellpadding="1" cellspacing="1" >
  <tr align="left" bgcolor="#999999">
<td width=52> 
	<input  type= "button" name="cmddelete" value="delete" onClick="getChecked(<?php echo $total_fields;?>)">
</td>
<?php
for($cnt=0;$cnt<mysql_num_fields($res_data);$cnt++)
{
	echo "<td><strong>".ucfirst(mysql_field_name($res_data,$cnt))."</strong></td>\n";
}
echo "</tr>";

$i = 1;
$c=0;
while($rs = mysql_fetch_array($res_data))
{
		if($c % 2==0)
		{
			echo "<tr bgcolor=\"#DDDDDD\" onMouseOver=\"ChangeColor(this,true);\" onMouseOut=\"ChangeColor(this,false,$c);\" style=\"cursor:pointer\" >\n";
		}
		else{		
			echo "<tr bgcolor=\"#CCCCCC\" onMouseOver=\"ChangeColor(this,true);\" onMouseOut=\"ChangeColor(this,false,$c);\" style=\"cursor:pointer\" >\n";
		}
	echo "<td align=\"center\"> <input type=\"checkbox\"  id= \"".$i++."\" </td>\n";
	$col_count=mysql_num_fields($res_data)+1;
	for($t=0; $t<$col_count-1;$t++)
		echo "<td id=" . $i . "  onMouseDown=\"setDataColumn(" . $i++ ."," .$col_count.")\">" . $rs[$t] . "</td>\n";
	echo "</tr>\n";
	$c++;
}
?>

</table>
<input type="hidden" value="<?php echo $i; ?>" id="maxField">
<input type="hidden" value="<?php echo $total_fields;?> "id="totalFields">
</body>
</html>

