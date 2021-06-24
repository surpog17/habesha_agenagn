<?php
$verification = $_POST['verification'];
$result=array();
require 'connection.php';

  $sql = "DELETE FROM electronics WHERE verification='$verification'";

if(mysqli_query($con, $sql)){

  $result['success'] = true;
  $result['message'] = "success";
  echo json_encode($result);

  mysqli_close($con);


} else {

  $result["success"] = false;
  $result["message"] = "Error!"+$con_error();
  echo json_encode($result);
  mysqli_close($con);


}
 ?>