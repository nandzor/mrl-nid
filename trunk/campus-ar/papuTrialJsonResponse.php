<?php

$dbhost="mrlnmd.db.5537199.hostedresource.com";
$dbdata="mrlnmd";
$dbuser="mrlnmd";
$dbpass="StHxMo3C";


$keys = array( "layerName", "lat", "lon", "radius" );

$value = array();
 
try {
  
  foreach( $keys as $key ) {
 
    if ( isset($_GET[$key]) )
      $value[$key] = $_GET[$key];
    else
      throw new Exception($key ." parameter is not passed in GetPOI request.");
  }//foreach
}
catch(Exception $e) {
  echo 'Message: ' .$e->getMessage();
}

$db = new PDO( "mysql:host=$dbhost; dbname=$dbdata", $dbuser, $dbpass, array(PDO::MYSQL_ATTR_INIT_COMMAND =>  "SET NAMES utf8") );
    
    $db->setAttribute( PDO::ATTR_ERRMODE , PDO::ERRMODE_EXCEPTION );

$response = array();
    
    
    $response["layer"] = $value["layerName"];
    
    
    $response["hotspots"] = Gethotspots( $db, $value );

    
    if ( empty( $response["hotspots"] ) ) {
        $response["errorCode"] = 20;
         $response["errorString"] = "No POI found. Please adjust the range.";
    }
    else {
          $response["errorCode"] = 0;
          $response["errorString"] = "ok";
    }
 
function Gethotspots( $db, $value ) {

 
$sql = $db->prepare( "
              SELECT id,
		     attribution,
                     title,
                     latitude,
                     longitude,
                     (((acos(sin((:lat1 * pi() / 180)) * sin((latitude * pi() / 180)) +
                         cos((:lat2 * pi() / 180)) * cos((latitude * pi() / 180)) *
                       cos((:long  - longitude) * pi() / 180))
                      ) * 180 / pi()) * 60 * 1.1515 * 1.609344 * 1000) as distance
            FROM pointtable
            HAVING distance < :radius
            ORDER BY distance ASC
            LIMIT 0, 50 " );

 $sql->bindParam( ':lat1', $value['lat'], PDO::PARAM_STR );
  $sql->bindParam( ':lat2', $value['lat'], PDO::PARAM_STR );
  $sql->bindParam( ':long', $value['lon'], PDO::PARAM_STR );
  $sql->bindParam( ':radius', $value['radius'], PDO::PARAM_INT );

    
  $sql->execute();
    
  $i = 0;
 
  $pois = $sql->fetchAll(PDO::FETCH_ASSOC);
 
if ( empty($pois) ) {
      
      $response["hotspots"] = array ();
    
  }
  else {
      
      
     foreach ( $pois as $poi ) {
        
        
        $poi["actions"] = array();
        
        
       $poi["lat"] = ChangetoIntLoc( $poi["latitude"] );
        $poi["lon"] = ChangetoIntLoc( $poi["longitude"] );
    
         
        $poi["type"] = ChangetoInt( $poi["type"] );
        
        
        $poi["distance"] = ChangetoFloat( $poi["distance"] );
    
        
        $response["hotspots"][$i] = $poi;
        $i++;
      }
 
  }
 
return $response["hotspots"];

}

function ChangetoIntLoc( $value_Dec ) {

  return $value_Dec * 1000000;
 
}

function ChangetoInt( $string ) {

  if ( strlen( trim($string ))  != 0 ) {
 
    return (int)$string;
  }
  else
      return 1;
}

function ChangetoFloat( $string ) {

  if ( strlen( trim( $string ) ) != 0 ) {
 
    return (float)$string;
  }
  else
      return NULL;
}

 $jsonresponse = json_encode( $response );
    
    
    header( "Content-type: application/json; charset=utf-8" );
    
    
    echo $jsonresponse;

    
    
    $db=null;

?>

