<?php
require 'db.php';
$id = $_GET['did'];
$sql = 'SELECT * FROM driver WHERE did=:did';
$statement = $connection->prepare($sql);
$statement->execute([':did' => $id ]);
$person = $statement->fetch(PDO::FETCH_OBJ);
if (isset ($_POST['firstname'])  && isset($_POST['email']) ) {
  $firstname = $_POST['firstname'];
  $lastname = $_POST['lastname'];
  $work = $_POST['work'];
  $phone= $_POST['phone'];
  $addphone = $_POST['addphone'];
  $email = $_POST['email'];
  $description = $_POST['Description'];
  $sql = 'UPDATE electronics SET firstname=:firstname,lastname=:lastname,work=:work ,phone=:phone,addphone=:addphone, email=:email,Description=:Description WHERE eid=:eid';
  $statement = $connection->prepare($sql);
  if ($statement->execute([':firstname' => $firstname,':lastname' => $lastname,':work' => $work,':phone' => $phone,':addphone' => $addphone, ':email' => $email,':Description' => $description, ':did' => $id])) {
   header("location:drivers.php");
  }

}


 ?>
<?php require 'header.php'; ?>
<div class="container">
  <div class="card mt-5">
    <div class="card-header">
      <h2>Update Drivers</h2>
    </div>
    <div class="card-body">
      <?php if(!empty($message)): ?>
        <div class="alert alert-success">
          <?= $message; ?>
        </div>
      <?php endif; ?>
      <form method="post">
        <div class="form-group">
          <label for="firstname">firstname</label>
          <input value="<?= $person->firstname; ?>" type="text" name="firstname" id="name" class="form-control">
          <label for="lastname">lastname</label>
          <input value="<?= $person->lastname; ?>" type="text" name="lastname" id="name" class="form-control">
          <label for="work">work</label>
          <input value="<?= $person->work; ?>" type="text" name="work" id="name" class="form-control">
            <label for="phone">phone</label>
          <input value="<?= $person->phone; ?>" type="text" name="phone" id="name" class="form-control">
            <label for="ohter phone">other phone</label>
          <input value="<?= $person->addphone; ?>" type="text" name="addphone" id="name" class="form-control">
            <label for="email">email</label>
          <input type="email" value="<?= $person->email; ?>" name="email" id="email" class="form-control">
            <label for="Description">Description</label>
          <input value="<?= $person->Description; ?>" type="text" name="Description" id="name" class="form-control">
        </div>
        
        <div class="form-group">
          <button type="submit" class="btn btn-info">Update Info</button>
        </div>
      </form>
    </div>
  </div>
</div>
<?php require 'footer.php'; ?>
