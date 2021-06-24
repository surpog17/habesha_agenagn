<?php

    require_once 'connection.php';
    
     //$verification = $_POST["verification"];
     $email = $_POST["email"];
     $firstname = $_POST["firstname"];
     $lastname = $_POST["lastname"];
     $work = $_POST["work"];
     $experience= $_POST["experience"];
     $phone = $_POST["phone"];
     $Description = $_POST["Description"];
     $sql = "UPDATE electronics SET firstname='$firstname',lastname='$lastname',work='$work',experience='$experience',phone='$phone',email='$email',Description='$Description' WHERE 
     email = '$email' ";
     $result = mysqli_query($con,$sql);
     
     if($result){
         echo "Data Updated";
        
     }
     else{
         echo "Failed";
     }
     mysqli_close($con);
     
        
?>