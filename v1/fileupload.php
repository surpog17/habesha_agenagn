<?php
$conn=mysqli_connect("localhost","root","");
mysqli_select_db($conn,"habesha_agenagn");

            $firstname=$_POST['firstname'];
            $lastname=$_POST['lastname'];
            $work=$_POST['work'];
            $experience=$_POST['experience'];
            $email=$_POST['email'];
            $phone=$_POST['phone'];
            $place=$_POST['place'];
            $description=$_POST['Description'];
	        $name=$_POST['name'];	   
	        $img=$_POST['upload'];


	
	//Getting the server ip 
	      $server_ip = gethostbyname(gethostname());
	
	//creating the upload url 
	      
        $filename="IMG".rand().".jpg";
        $upload_url = 'http://'.$server_ip.'/Habesha_agenagn/v1/uploads/'.$filename;  
	   file_put_contents("uploads/".$filename,base64_decode($img));

			$sql = "INSERT INTO `habesha_agenagn`.`driver` (`did`,firstname,lastname,experience,work,email,phone,place,Description, `url`, `name`) VALUES (NULL,'$firstname','$lastname','$experience','$work','$email','$phone','$place','$description', '$file_url', '$name');"; 

			$res=mysqli_query($conn,$sql);
			
			if($res==true)
			 echo "File Uploaded Successfully";
			else
			 echo "Could not upload File";
?>