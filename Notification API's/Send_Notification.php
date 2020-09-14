<?php 


function sendFCM($token,$type){
	$ch=curl_init("https://fcm.googleapis.com/fcm/send");
$header=array("Content-Type:application/json","Authorization: key=AAAAHWR4oaU:APA91bFqitJ3tl8oU7S21oasTqk776R5yMzMlNNvtCLcKlDj2Jjkv-SSerqHi2J-wqYzUwtfJ5waIvrkecTRAQVdBS4xvzYdOmj93Ua2DHCjs8EoQ5wbm_gfhsqXm9mmMZ9lG12lMtf9");
	if($token==""){

		if($type=="bigtext"){
			$data=json_encode(array("to"=>"/topics/allDevices","data"=>array("title"=>$_REQUEST['title'],"message"=>$_REQUEST['message'],"not_type"=>"bigtext","extra_data"=>"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book")));
		}
		if($type=="bigimage"){
			$data=json_encode(array("to"=>"/topics/allDevices","data"=>array("title"=>$_REQUEST['title'],"message"=>$_REQUEST['message'],"not_type"=>"bigimage","extra_data"=>"https://i.picsum.photos/id/721/200/300.jpg?hmac=6g_vLTUju_TGWN7cMKTjZgzqps-JjmHIS0KSuFsgVyc")));
		}
		if($type=="bigimage_withoutsideicon"){
			$data=json_encode(array("to"=>"/topics/allDevices","data"=>array("title"=>$_REQUEST['title'],"message"=>$_REQUEST['message'],"not_type"=>"bigimage_withoutsideicon","extra_data"=>"https://i.picsum.photos/id/721/200/300.jpg?hmac=6g_vLTUju_TGWN7cMKTjZgzqps-JjmHIS0KSuFsgVyc")));
		}
		if($type=="inbox_style"){
			$array_message=array("sundar : Hi How Are You?","Satya: I am Fine ","sundar : Are You Ok?");
			$json_message=json_encode($array_message);

			$data=json_encode(array("to"=>"/topics/allDevices","data"=>array("title"=>$_REQUEST['title'],"message"=>$_REQUEST['message'],"not_type"=>"inbox_style","extra_data"=>$json_message)));
		}
		if($type=="message_style"){

			$data=json_encode(array("to"=>"/topics/allDevices","data"=>array("title"=>$_REQUEST['title'],"message"=>$_REQUEST['message'],"not_type"=>"message_style","extra_data"=>"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s")));
		}

	 }
	else if(is_array($token)){
	$data=json_encode(array("registation_ids"=>array($token),"data"=>array("title"=>$_REQUEST['title'],"message"=>$_REQUEST['message'])));
	 }
	else{
	 	$data=json_encode(array("to"=>$token,"data"=>array("title"=>$_REQUEST['title'],"message"=>$_REQUEST['message'])));
	 }

	//now let's see data message

	curl_setopt($ch,CURLOPT_HTTPHEADER,$header);
	curl_setopt($ch,CURLOPT_SSL_VERIFYPEER,false);
	curl_setopt($ch,CURLOPT_POST,1);
	curl_setopt($ch,CURLOPT_POSTFIELDS,$data);
	curl_exec($ch);
}

//Use for Send Big Text Notification
//sendFCM("","bigtext");

//Use for Send Big Image Notification
//sendFCM("","bigimage");

//Use for Send Big Image Notification Without Side ICON
sendFCM("","bigimage_withoutsideicon");

//Use for Send Inbox Style
//sendFCM("","inbox_style");

//Use for Send Message Style
//sendFCM("","message_style");