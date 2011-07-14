<?php 
$opt_arr=$_GET["operation"];
$user="mrlnmd";
$pwd="StHxMo3C";
$db="mrlnmd"; 
$host="mrlnmd.db.5537199.hostedresource.com";
$tablename="pointdaiict";
mysql_connect($host,$user,$pwd) or die("Connection Failed");
$dblink=mysql_select_db($db) or die("could not select the database");
$qry="";
$mode=substr($opt_arr,0,1);
echo "mode".$mode;
$tmp_arr=explode("?",$opt_arr);

if($mode=='s')
{
	for($i=0;$i<count($tmp_arr)-2;)
	{
			$qry="INSERT INTO `".$tablename."` VALUES ('".$tmp_arr[$i++]."', '".$tmp_arr[$i++]."', '".$tmp_arr[$i++]."', '".$tmp_arr[$i++]."', '".$tmp_arr[$i++]."', '".$tmp_arr[$i++]."', '".$tmp_arr[$i++]."', '1');";
mysql_query($qry);
	}
	echo "saved..";
}
if($mode=='u')
{
	$qry="UPDATE `".$tablename."` SET `LayarType` = 'LibrarY' WHERE `pointdaiict`.`id` =5;";
	echo "update";
}
if($mode=='d')
{
	
	$opt_arr=str_replace(" ",",",$opt_arr,$i);
	$opt_arr="(".substr($opt_arr,1,strlen($opt_arr)-2).")";
	echo "array:\n".$opt_arr;
	$qry="delete from ".$tablename." where id in".$opt_arr.";";
	mysql_query($qry);
	echo "deleted..... ";

}
//	header("Location: display.php");
//redirect("/display.php");
?>