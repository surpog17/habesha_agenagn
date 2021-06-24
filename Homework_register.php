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
	        $img=$_POST['upload'];
	        $imgqr=$_POST['qrupload'];	
	//Getting the server ip 
	      $server_ip = gethostbyname(gethostname());
	
	//creating the upload url 
	      
        $filename=$firstname.rand().".jpg";
        $filenameqr="IMG".rand().".jpg";
        $upload_url = 'http://'.$server_ip.'/Habesha_agenagn/v1/elecronics/'.$filename; 
        $upload_qr = 'http://'.$server_ip.'/Habesha_agenagn/v1/elecronics/qr/'.$filenameqr; 
           $emailcheck="SELECT * FROM homework WHERE email='".$email."'";
            $check=mysqli_query($con,$emailcheck);
            $checkemail=mysqli_num_rows($check);
                if($checkemail>0){
                $result["success"]=false;
                $result["message"]="you already register in Electronics";
                 echo json_encode($result);
                   mysqli_close($con); 
                }
else{
             file_put_contents("homework/".$filename,base64_decode($img));
			$sql = "INSERT INTO `habesha_agenagn`.`homework` (`eid`,firstname,lastname,experience,work,email,phone,addphone,place,Description, `url`) VALUES (NULL,'$firstname','$lastname','$experience','$work','$email','$phone','$addphone','$place','$description', '$upload_url');"; 
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