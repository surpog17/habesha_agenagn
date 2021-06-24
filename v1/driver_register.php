<?php
require_once 'connection.php';

            $firstname=$_POST['firstname'];
            $lastname=$_POST['lastname'];
            $work=$_POST['work']; 
            $email=$_POST['email'];
            $phone=$_POST['phone'];
            $addphone=$_POST['addphone'];
            $place=$_POST['place'];
            $description=$_POST['Description'];	   
            $longtude=$_POST['longtude'];
            $latitude=$_POST['latitude'];
	          $img=$_POST['upload'];


	
	//Getting the server ip 
	      $server_ip = gethostbyname(gethostname());
	
	//creating the upload url 
	      
        $filename="IMG".rand().".jpg";
        $upload_url = 'http://'.$server_ip.'/Habesha_agenagn/v1/drivers/'.$filename;  
           $emailcheck="SELECT * FROM driver WHERE email='".$email."'";
            $check=mysqli_query($con,$emailcheck);
            $checkemail=mysqli_num_rows($check);
                if($checkemail>0){
                $result["success"]=false;
                $result["message"]="you already register in Drivers";
                 echo json_encode($result);
                   mysqli_close($con); 
                }
else{
             file_put_contents("drivers/".$filename,base64_decode($img));
			$sql = "INSERT INTO `habesha_agenagn`.`driver` (`did`,firstname,lastname,work,email,phone,addphone,place,Description, `url`,longtude,latitude) VALUES (NULL,'$firstname','$lastname','$work','$email','$phone','$addphone','$place','$description', '$upload_url',$longtude,$latitude);"; 
               if(mysqli_query($con,$sql)){		
					//filling response array with values 
				$result["success"]=true;
                $result["message"]="success";
                 echo json_encode($result);
                   mysqli_close($con);   
				}else{
        $result["success"]=false;
        $result["message"]="error 
        ".$con->error;
        echo json_encode($result);
        mysqli_close($con);
		}
}
?>