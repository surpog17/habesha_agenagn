<?php 

$con=new mysqli("localhost","root","","Habesha_agenagn");
$response=array();

 if ($_SERVER['REQUEST_METHOD']=='POST') 
 {
		
                    $sql="SELECT * FROM homeworks";
                    $result=$con->query($sql);
                   if($result->num_rows > 0)
                   {
                      while($row=$result->fetch_assoc())
                      {
                                    $ty=array();
                            
                                    $ty['servicetypes']=$row["servicetypes"];
                                    $ty['companyname']=$row["companyname"];
									$ty['email']=$row["email"];
									$ty['phone']=$row["phone"];
                                    $ty['addphone']=$row["addphone"];
                                    $ty['place']=$row["place"];
									$ty['description']=$row["description"];
									$ty['url']=$row["url"];
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
