<?php 

$con=new mysqli("localhost","root","","Habesha_agenagn");
$response=array();

 if ($_SERVER['REQUEST_METHOD']=='POST') 
 {
		
                    $sql="SELECT * FROM shopping_center";
                    $result=$con->query($sql);
                   if($result->num_rows > 0)
                   {
                      while($row=$result->fetch_assoc())
                      {
                                    $ty=array();
                            
                                    $ty['mall_name']=$row["mall_name"];
                                    $ty['address']=$row["address"];
									$ty['phone_number']=$row["phone_number"];
                                    $ty['working_hour']=$row["working_hour"];
                                    $ty['images']=$row["images"];
									$ty['longitude']=$row["longitude"];
									$ty['latitude']=$row["latitude"];
                                    array_push($response,$ty);
                          
                      }
                   }
                 else
	     				{
				   			$response['error']= true;
				   			$response['message']="user not Found";

	     				}

 }
 else
	{
		$response['error']= true;
		$response['message']="Invalid request";
	}
echo json_encode($response);
