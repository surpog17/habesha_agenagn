<?php
require 'db.php';
$id = $_GET['did'];
$sql = 'DELETE FROM driver WHERE did=:did';
$statement = $connection->prepare($sql);
if ($statement->execute([':did' => $id])) {
  header("Location:drivers.php");
}