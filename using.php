<?php

if($_SERVER['REQUEST_METHOD']=='POST'){
    $firstname=$_POST['firstname'];
    $lastname=$_POST['lastname'];
    $work=$_POST['work'];
    $experience=$_POST['experience'];
    $email=$_POST['email'];
    $phone=$_POST['phone'];
    $place=$_POST['place'];
    $description=$_POST['Description'];
    $permitted_chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
    $verification=substr(str_shuffle($permitted_chars), 0, 5);
    require_once 'connection.php';
$sql="insert into electronics(firstname,lastname,experience,work,email,phone,place,Description,verification)values('$firstname','$lastname','$experience','$work','$email','$phone','$place','$description','$verification')";
if(mysqli_query($con, $sql)){
        $result["success"]=true;
        $result["message"]="success";
//    $id=mysqli_insert_id($con);
//    $sql1="select * from electronics where eid='$id'";
//    $res=$con->query($sql1);
//    $row=$res->fetch_assoc();
//    $result["ver"]=$row['verification'];
        echo json_encode($result);
        mysqli_close($con);
//    header("location:userhome1.html"); 
        
    }else{
        $result["success"]=false;
        $result["message"]="error ".$con->error;
        echo json_encode($result);
        mysqli_close($con);
    }
    
}
?> 
<?php
require_once 'dbDetails.php';

 if($_SERVER['REQUEST_METHOD'] == 'POST')
 {
 $DefaultId = 0;
 
 $ImageData = $_POST['image'];
 
 $ImageName = $_POST['name'];

 $GetOldIdSQL ="SELECT id FROM photos ORDER BY id ASC";
 
 $Query = mysqli_query($con,$GetOldIdSQL);
 
 while($row = mysqli_fetch_array($Query)){
 
 $DefaultId = $row['id'];
 }
 $server_ip = gethostbyname(gethostname());
 $ImagePath = "upload/$DefaultId.png";
 
 $ServerURL ='http://'.$server_ip.'/Habesha_agenagn/v1/'.$ImagePath; 
 
 $InsertSQL = "insert into photos (image,name) values ('$ServerURL','$ImageName')";
 
 if(mysqli_query($con, $InsertSQL)){
 file_put_contents($ImagePath,base64_decode($ImageData));

 echo "Your Image Has Been Uploaded.";
     
 }
 
 mysqli_close($con);
 }else{
 echo "Not Uploaded";
 }

?>
<?php 
	
	//importing dbDetails file 
	require_once 'dbDetails.php';
	
	  function isUserExistemail($email){
			$stmt = $this->con->prepare("SELECT did FROM Driver WHERE email = ?");
			$stmt->bind_param("s",$email);
			$stmt->execute(); 
			$stmt->store_result(); 
			return $stmt->num_rows > 0; 
		}
function getFileName(){
		$con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect...');
		$sql = "SELECT max(id) as id FROM photos";
		$result = mysqli_fetch_array(mysqli_query($con,$sql));
		
		mysqli_close($con);
		if($result['id']==null)
			return 1; 
		else 
			return $result['id']; 
	}
	
	if($_SERVER['REQUEST_METHOD']=='POST'){
//		$firstname=$_POST['firstname'];
//            $lastname=$_POST['lastname'];
//            $work=$_POST['work'];
//            $experience=$_POST['experience'];
//            $email=$_POST['email'];
//            $phone=$_POST['phone'];
//            $place=$_POST['place'];
//            $description=$_POST['Description'];
		//checking the required parameters from the request 
		if(isset($_POST['name']) and isset($_FILES['image']['name'])){
            //this is our upload folder 
	        $upload_path = 'uploads/';
	
	//Getting the server ip 
	      $server_ip = gethostbyname(gethostname());
	
	//creating the upload url 
	       $upload_url = 'http://'.$server_ip.'/Habesha_agenagn/v1/'.$upload_path; 
	
	//response array 
	         
			
			//connecting to the database 
			$con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect...');
			
			//getting name from the request 
			$name = $_POST['name'];
			
			//getting file info from the request 
			$fileinfo = pathinfo($_FILES['image']['name']);
			
			//getting the file extension 
			$extension = $fileinfo['extension'];
			
			//file url to store in the database 
			$file_url = $upload_url .getFileName() . '.' . $extension;
			
			//file path to upload in the server 
			$file_path = $upload_path .getFileName() . '.'. $extension; 
                
				move_uploaded_file($_FILES['image']['tmp_name'],$file_path);
				$sql = "INSERT INTO `habesha_agenagn`.`photos` (`id`, `url`, `name`) VALUES (NULL,'$file_url', '$name');"; 
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
        }
            
	/*
		We are generating the file name 
		so this method will return a file name for the image to be upload 
	*/
    
    ?>
<?php
require_once 'dbDetails.php';
if($_SERVER['REQUEST_METHOD']=='POST'){
   if(isset($_POST['name']) and isset($_FILES['image']['name'])){
            //this is our upload folder 
	       
	        $design=$_POST['t2'];
           $name = $_POST['name'];
	//Getting the server ip 
           $upload_path = 'uploads/';
	      $server_ip = gethostbyname(gethostname());
	
	//creating the upload url 
	       $upload_url = 'http://'.$server_ip.'/Habesha_agenagn/v1/'.$upload_path; 
           $con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect...');
			//getting file info from the request 
			$fileinfo = pathinfo($_FILES['image']['name']);
			$extension = $fileinfo['extension'];
			
			//file url to store in the database 
			$file_url = $upload_url .getFileName() . '.' . $extension;
			
			//file path to upload in the server 
			$file_path = $upload_path .getFileName() . '.'. $extension; 
       move_uploaded_file($_FILES['image']['tmp_name'],$file_path);
$sql="INSERT INTO `image` (`id`, `name`, `desig`, `image`)VALUES (NULL, '$name', '$design', '$file_url')";
if(mysqli_query($con, $sql))
           echo "File Uploaded Successfully";
			else
			 echo "Could not upload File";    
}
}
function getFileName(){
		$con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect...');
		$sql = "SELECT max(id) as id FROM image";
		$result = mysqli_fetch_array(mysqli_query($con,$sql));
		
		mysqli_close($con);
		if($result['id']==null)
			return 1; 
		else 
			return $result['id']; 
	}
?> 
