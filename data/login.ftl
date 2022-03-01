<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Login form</title>
    <link rel="stylesheet" href="css/forms.css">
</head>

<body>
<main>
    <form action="/login" method="post">
        <fieldset>
            <div class="legend">
                <p>Welcome!</p>
                <img src="/images/1.jpg" alt="coins" style="display:block; width: 200px; height: 200px;">
            </div>
            <div class="form-element">
                <label for="name">email</label>
                <input type="name" name="name" id="name" placeholder="your name" required autofocus>
            </div>
            <div class="form-element">
                <label for="email">email</label>
                <input type="email" name="email" id="email" placeholder="your email" required autofocus>
            </div>
            <div class="form-element">
                <label for="login">login</label>
                <input type="login" name="login" id="login" placeholder="your login" required autofocus>
            </div>
<!--            <div class="form-element">-->
<!--                <label for="name">login</label>-->
<!--                <input type="name" name="login" id="name" placeholder="your name" required autofocus>-->
<!--            </div>-->
            <div class="form-element">
                <label for="password">password</label>
                <input type="password" name="password" id="password" placeholder="your password" required>
            </div>
            <div class="hr-line">
                <span class="details">one more step to go</span>
            </div>
            <div class="form-element">
                <button class="register-button" type="submit">Login!</button>
            </div>
        </fieldset>
    </form>
</main>
</body>

</html>