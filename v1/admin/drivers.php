
<?php
require 'db.php';
$sql = 'SELECT * FROM driver';
$statement = $connection->prepare($sql);
$statement->execute();
$people = $statement->fetchAll(PDO::FETCH_OBJ);
 ?>
<?php require 'header.php'; ?>
<div class="container">
    <title>Drivers</title>
  <div class="card mt-5">
    <div class="card-header">
      <h2>All Drivers</h2>
    </div>
    <div class="card-body">
      <table class="table table-bordered">
        <tr>
          <th>ID</th>
          <th>Firstname</th>
          <th>Lastname</th>
          <th>Work</th>
          <th>phone number</th>
          <th>Other phone</th>
          <th>Email</th>
          <th>Place</th>
          <th>Description</th>
          <th>Image</th>
          <th>Action</th>
        </tr>
        <?php foreach($people as $person): ?>
          <tr>
            <td><?= $person->did; ?></td>
            <td><?= $person->firstname; ?></td>
            <td><?= $person->lastname; ?></td>
            <td><?= $person->work; ?></td>
            <td><?= $person->phone; ?></td>
            <td><?= $person->addphone; ?></td>
            <td><?= $person->email; ?></td>
            <td><?= $person->place; ?></td>
            <td><?= $person->Description; ?></td>
            <td> </td>
            <td>
              <a href="edit_drivers.php?did=<?= $person->did ?>" class="btn btn-info">Edit</a>
              <a onclick="return confirm('Are you sure you want to delete this entry?')" href="delete_electronics.php?did=<?= $person->did ?>" class='btn btn-danger'>Delete</a>
            </td>
          </tr>
        <?php endforeach; ?>
      </table>
    </div>
  </div>
</div>
<?php require 'footer.php'; ?>
