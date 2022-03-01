<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Login form</title>
    <link rel="stylesheet" href="css/forms.css">
</head>

<body>
<main>
    <form action="/register" method="post">
        <fieldset>
            <div class="legend">
                <p>Welcome!</p>
                <img src="/images/1.jpg" alt="coins" style="display:block; width: 200px; height: 200px;">
            </div>
            <div class="form-element">
                <label for="name">email</label>
                <input type="input" name="name" id="name" placeholder="your name" required autofocus>
            </div>
            <div class="form-element">
                <label for="userEmail">email</label>
                <input type="email" name="userEmail" id="userEmail" placeholder="your email" required autofocus>
            </div>
            <div class="form-element">
                <label for="login">email</label>
                <input type="input" name="login" id="login" placeholder="your login" required autofocus>
            </div>
            <div class="form-element">
                <label for="Password">password</label>
                <input type="password" name="password" id="password" placeholder="your password" required>
            </div>
            <div class="hr-line">
                <span class="details">one more step to go</span>
            </div>
            <div class="form-element">
                <button class="register-button" type="submit">Register!</button>
            </div>
        </fieldset>
    </form>
</main>
</body>

</html>