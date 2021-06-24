<?php
require'connection.php';
$firstname=$_POST['firstname'];
$lastname=$_POST['lastname'];
$email=$_POST['email'];
$username=$_POST['username'];
$pass=$_POST['password'];
$password = md5($pass);
$sql="insert into admin(firstname,lastname,email,username,password)values('$firstname','$lastname','$email','$username','$password')";

if($con->query($sql)==TRUE)
{
    header("location:login.html");
}
else{
    echo"error".$con->error;
}
$query=mysql_query("Select * from admin where username='$username'");
if(mysql_num_rows($query)>0){
    echo"username exists!";
}
else{
    mysql_query("insert into admin (firstname,lastname, email,username,password)values('$firstname','$lastname','$email','$username','$password')");
    header("location:login.html");
    
}
$con->close();
?>