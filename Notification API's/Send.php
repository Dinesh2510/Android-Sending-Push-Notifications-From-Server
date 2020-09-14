<!-- dZi1dUiNS5OJM_xPL3ny2o:APA91bFQaTzLT0GmWHTjCLiXSnQbdx9iV9UN5c3ZXw49sM15BTTkpbqVUi-UF9WoFSBBUm33UTdq49DRCGoCElsu1oeRyiqeUIOIylDigWhC7Bfu_f1OLglJzspe-Oj-pYNp3hy-woeT -->


<?php 
$ch=curl_init("https://fcm.googleapis.com/fcm/send");
$header=array("Content-Type:application/json","Authorization: key=AAAAHWR4oaU:APA91bFqitJ3tl8oU7S21oasTqk776R5yMzMlNNvtCLcKlDj2Jjkv-SSerqHi2J-wqYzUwtfJ5waIvrkecTRAQVdBS4xvzYdOmj93Ua2DHCjs8EoQ5wbm_gfhsqXm9mmMZ9lG12lMtf9");
//to topic means am using all device
//$data=json_encode(array("to"=>"/topics/allDevices","notification"=>array("title"=>$_REQUEST['title'],"message"=>$_REQUEST['message'])));
//$data=json_encode(array("to"=>"fVHH4Dz_vKo:APA91bFFaxnUbX1U5IaDjJR4Hy3ewEX8QSjsCXHDTVLXPGToK0HU0dnItAUqHsPDkLTn6jGoiq5LK4mqiCyWtvrM_xPn2GwgTHbuc5rInJ0f2Us2iUmqIIwSBH_Fg1czVDPBbhJsgrnc","notification"=>array("title"=>$_REQUEST['title'],"message"=>$_REQUEST['message'])));


//now let's see data message
$data=json_encode(array("to"=>"/topics/allDevices","data"=>array("title"=>$_REQUEST['title'],"message"=>$_REQUEST['message'])));
curl_setopt($ch,CURLOPT_HTTPHEADER,$header);
curl_setopt($ch,CURLOPT_SSL_VERIFYPEER,false);
curl_setopt($ch,CURLOPT_POST,1);
curl_setopt($ch,CURLOPT_POSTFIELDS,$data);
curl_exec($ch);


/*AAAAHWR4oaU:APA91bFqitJ3tl8oU7S21oasTqk776R5yMzMlNNvtCLcKlDj2Jjkv-SSerqHi2J-wqYzUwtfJ5waIvrkecTRAQVdBS4xvzYdOmj93Ua2DHCjs8EoQ5wbm_gfhsqXm9mmMZ9lG12lMtf9*/