<?php
require 'db.php';
$id = $_GET['eid'];
$sql = 'DELETE FROM electronics WHERE eid=:eid';
$statement = $connection->prepare($sql);
if ($statement->execute([':eid' => $id])) {
  header("Location:electronics.php");
}