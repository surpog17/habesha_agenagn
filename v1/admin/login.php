<?php
session_start();
require'connection.php';
$username=$_POST['username'];
$pass=$_POST['password'];
$password = md5($pass);
$select="select * from admin where username='$username' && password='$password'";
$result=$con->query($select);
$x=mysqli_num_rows($result);
if($x>0)
{
header("location:adminhome.html");
}
else{
  header("location:login2.html");
    echo"try again";
}
$con->close();
?>