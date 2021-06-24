<?php
require 'connection.php';
$message = '';
if (isset ($_POST['submit'])) {
            $firstname=$_POST['firstname'];
            $lastname=$_POST['lastname'];
            $work=$_POST['work'];
            $experience=$_POST['experience'];
            $email=$_POST['email'];
            $phone=$_POST['phone'];
            $addphone=$_POST['addphone'];
            $place=$_POST['place'];
            $description=$_POST['Description'];	   
	       $filename=$_FILES['file']['name'];
	       $fileTmpName=$_FILES['file']['tmp_name'];
	       $fileSize=$_FILES['file']['size'];
	       $fileError=$_FILES['file']['error'];
	       $fileType=$_FILES['file']['type'];
           $fileExt=explode('.',$filename);
           $fileAcutalExt=strtolower(end($fileExt));
           $allowed=array('jpg','jpeg','png','pdf');
           $dir= 'electronics/';
    if(in_array($fileAcutalExt,$allowed ))
        {
        if($fileError===0){
            if($fileSize<1000000){
                $fileNameNew=$filename;
                $fileDestination=$dir.$fileNameNew;
                move_uploaded_file( $fileTmpName,$fileDestination);
                
            }else{
                $message='your file is to big';
            }
            
        }else{
           $message='there was an error uploading your file';
        }
        }else{
            $message='you cannot upload files of this type';
        }
        
    
           $server_ip = gethostbyname(gethostname());
           $upload_url = 'http://'.$server_ip.'/Habesha_agenagn/v1/electronics/'.$filename; 
//        $server_ip = gethostbyname(gethostname());
//        $filename=$firstname.rand().".png";    
//        $upload_url = 'http://'.$server_ip.'/Habesha_agenagn/v1/elecronics/'.$filename;
//       file_put_contents("elecronics/".$filename,base64_decode($img));
$sql = "INSERT INTO `habesha_agenagn`.`electronics` (`eid`,firstname,lastname,experience,work,email,phone,addphone,place,Description, `url`) VALUES (NULL,'$firstname','$lastname','$experience','$work','$email','$phone','$addphone','$place','$description', '$upload_url')";  
     if(mysqli_query($con,$sql)){		
					//filling response array with
          $message = 'data inserted successfully';
                   mysqli_close($con);   
				}
//     $statement = $con->prepare($sql);
//    if ($statement->execute([':firstname' => $firstname,':lastname' => $lastname,':work' => $work,':phone' => $phone,':addphone' => $addphone, ':email' => $email,':Description' => $description, ':eid' => $id])) {
//  $message = 'data inserted successfully';
//  }
}
 ?>
<?php require 'header.php'; ?>
<div class="container">
  <div class="card mt-5">
    <div class="card-header">
        <title> register electronics</title>
      <h2>Create a Electronics</h2>
    </div>
    <div class="card-body">
      <?php if(!empty($message)): ?>
        <div class="alert alert-success">
          <?= $message; ?>
        </div>
      <?php endif; ?>
      <form method="post" action="create_electronics.php" enctype="multipart/form-data">
        <div class="form-group">
          <label for="name">Firstname</label>
          <input type="text" name="firstname" id="name" class="form-control" required>
            <label for="name">Lastname</label>
          <input type="text" name="lastname" id="name" class="form-control" required>
            <label for="name">Work</label>
          <input type="text" name="work" id="name" class="form-control" required>
             <label for="name">Experience</label>
          <input type="text" name="experience" id="name" class="form-control" required>
          <label for="name">Phonenumber</label>
          <input type="text" name="phone" id="name" class="form-control" required>
            <label for="name">Otherphone</label>
          <input type="text" name="addphone" id="name" class="form-control" required>
            <label for="email">Email</label>
          <input type="email" name="email" id="email" class="form-control" required>
            
             <label for="name">Place</label>
          <input type="text" name="place" id="name" class="form-control" required>
             <label for="name">Description</label>
            <textarea input type="text" name="Description" id="name" class="form-control" cols="40" 
      	rows="4" required> 
            </textarea>
            <div>
                <label for="name">Select image</label>
  	  <input type="file" name="file" required>
  	</div>
        </div>
       
        <div class="form-group">
          <button type="submit" class="btn btn-info" name="submit">Register</button>
        </div>
      </form>
    </div>
  </div>
</div>
<?php require 'footer.php'; ?>
