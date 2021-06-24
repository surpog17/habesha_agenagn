
<?php
require 'db.php';
$sql = 'SELECT * FROM electronics';
$statement = $connection->prepare($sql);
$statement->execute();
$people = $statement->fetchAll(PDO::FETCH_OBJ);
?>
<?php require 'header.php'; ?>
<div class="container">
    <title>Electronics</title>
  <div class="card mt-5">
    <div class="card-header">
      <h2>All elecronics</h2>
    </div>
    <div class="card-body">
        
      <table class="table table-bordered">
        <tr>
          <th>ID</th>
          <th>Firstname</th>
          <th>Lastname</th>
          <th>Work</th>
          <th>Experience</th>
          <th>phone number</th>
          <th>Other phone</th>
          <th>Email</th>
          <th>Place</th>
          <th>Description</th>
          <th>Image</th>
           <th>Status</th>
          <th>Action</th>
          
        </tr>
        <?php foreach($people as $person): ?>
          <tr>
            <td><?= $person->eid; ?></td>

            <td><?= $person->firstname; ?></td>
            <td><?= $person->lastname; ?></td>
            <td><?= $person->work; ?></td>
            <td><?= $person->experience; ?></td>
            <td><?= $person->phone; ?></td>
            <td><?= $person->addphone; ?></td>
            <td><?= $person->email; ?></td>
            <td><?= $person->place; ?></td>
            <td><?= $person->Description; ?></td>
            <td></td>
              <td>
             <input data-id="{{$user->id}}" class="toggle-class" type="checkbox" data-onstyle="success" data-offstyle="danger" data-toggle="toggle" data-on="Active" data-off="InActive" {{ $user->status ? 'checked' : '' }}>
                     </td>
            <td>
              <a href="edit_electronics.php?eid=<?= $person->eid ?>" class="btn btn-info">Edit</a>
              <a onclick="return confirm('Are you sure you want to delete this entry?')" href="delete_electronics.php?eid=<?= $person->eid ?>" class='btn btn-danger'>Delete</a>
            </td>
          </tr>
        <?php endforeach; ?>
      </table>
    </div>
  </div>
</div>
<?php require 'footer.php'; ?>
