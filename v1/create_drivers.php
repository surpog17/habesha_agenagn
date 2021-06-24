<?php
require 'connection.php';
$message = '';
if (isset ($_POST['name'])  && isset($_POST['email']) ) {
  $name = $_POST['name'];
  $email = $_POST['email'];
  $sql = 'INSERT INTO people(name, email) VALUES(:name, :email)';
  $statement = $con->prepare($sql);
  if ($statement->execute([':name' => $name, ':email' => $email])) {
    $message = 'data inserted successfully';
  }
}


 ?>
<?php require 'header.php'; ?>
<div class="container">
  <div class="card mt-5">
    <div class="card-header">
      <h2>Create a drivers</h2>
    </div>
    <div class="card-body">
      <?php if(!empty($message)): ?>
        <div class="alert alert-success">
          <?= $message; ?>
        </div>
      <?php endif; ?>
      <form method="post">
        <div class="form-group">
          <label for="name">Firstname</label>
          <input type="text" name="firstname" id="name" class="form-control">
            <label for="name">Lastname</label>
          <input type="text" name="lastname" id="name" class="form-control">
            <label for="name">Work</label>
          <input type="text" name="work" id="name" class="form-control">
          <label for="name">Phonenumber</label>
          <input type="text" name="phone" id="name" class="form-control">
            <label for="name">Otherphone</label>
          <input type="text" name="otherphone" id="name" class="form-control">
        </div>
        <div class="form-group">
          <label for="email">Email</label>
          <input type="email" name="email" id="email" class="form-control">
        </div>
        <div class="form-group">
          <button type="submit" class="btn btn-info">Create a person</button>
        </div>
      </form>
    </div>
  </div>
</div>
<?php require 'footer.php'; ?>
