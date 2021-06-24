<?php 

$con=new mysqli("localhost","root","","Habesha_agenagn");
$response=array();

 if ($_SERVER['REQUEST_METHOD']=='POST') 
 {
		if(isset($_POST['email']))
        
{
                    $email=$_POST['email'];
                    $sql="SELECT * FROM electronics WHERE email='$email'";
                    $result=$con->query($sql);
                   if($result->num_rows > 0)
                   {
                      while($row=$result->fetch_assoc())
                      {
                                    $ty=array();
									$ty['firstname']=$row["firstname"];
									$ty['lastname']=$row["lastname"];
                                    $ty['work']=$row["work"];
									$ty['experience']=$row["experience"];
                                    $ty['phone']=$row["phone"];
									$ty['email']=$row["email"];
									$ty['Description']=$row["Description"];
                                    array_push($response,$ty);
                          
                      }
                   }
                 else
	     				{
				   			$response['error']= true;
				   			$response['message']="user not Found";

	     				}

 }
 }
 else
	{
		$response['error']= true;
		$response['message']="Invalid request";
	}
echo json_encode($response);
    