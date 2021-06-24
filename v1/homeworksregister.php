<?php
require_once 'connection.php';
            $companyname=$_POST['companyname'];
            $servicetypes=$_POST['servicetypes'];
            $email=$_POST['email'];
            $phone=$_POST['phone'];
            $addphone=$_POST['addphone'];
            $place=$_POST['place'];
            $description=$_POST['Description'];	   
          $img=$_POST['upload'];
          $longtude=$_POST['longitude'];
          $latitude=$_POST['latitude'];
	//Getting the server ip 
	      $server_ip = gethostbyname(gethostname());
	
	//creating the upload url 
	      
        $filename=$companyname.rand().".jpg";
        $filenameqr="IMG".rand().".jpg";
        $upload_url = 'http://'.$server_ip.'/Habesha_agenagn/v1/homeworks/'.$filename;
           $emailcheck="SELECT * FROM homeworks WHERE email='".$email."'";
            $check=mysqli_query($con,$emailcheck);
            $checkemail=mysqli_num_rows($check);
                if($checkemail>0){
                $result["success"]=false;
                $result["message"]="you already register in HomeWorks";
                 echo json_encode($result);
                   mysqli_close($con); 
                }
else{
             file_put_contents("homeworks/".$filename,base64_decode($img));
			$sql = "INSERT INTO `habesha_agenagn`.`homeworks` (`hid`,companyname,servicetypes,email,phone,addphone,place,description, `url`,longitude,latitude) VALUES (NULL,'$companyname','$servicetypes','$email','$phone','$addphone','$place','$description', '$upload_url','$longtude','$latitude');"; 
               if(mysqli_query($con,$sql)){		
					//filling response array with values
                   
                   
                   
				$result["success"]=true;
                $result["message"]="success";
                 echo json_encode($result);
                   mysqli_close($con);   
				}else{
        $result["success"]=false;
        $result["message"]="error ".$con->error;
        echo json_encode($result);
        mysqli_close($con);
		}
}
?>