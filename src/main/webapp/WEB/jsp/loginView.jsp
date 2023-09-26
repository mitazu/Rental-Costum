<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en" >
<head>
    <meta charset="UTF-8">
    <title>CodePen - Responsive Signup/Login form</title>
    <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700|Raleway:300,600" rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css'>
    <link rel="stylesheet" href="${contextPath}dist/style.css">

</head>
<body>
<!-- partial:index.partial.html -->
<div class="container">
        <section>
        <div class="row m-auto" style="padding-top: 0; margin: 20px;">
            <!-- Brand Box -->
            <div class="col-sm-6 brand">
                <a href="#" class="logo">MR <span>.</span></a>
                <div class="heading">
                    <h2>Rz Rentcos</h2>
                    <p>Your Right Choice</p>
                </div>
                <div class="success-msg">
                    <p>Great! You are one of our members now</p>
                    <a href="#" class="profile">Your Profile</a>
                </div>
            </div>


            <!-- Form Box -->
            <div class="col-sm-6 form">
                <!-- Signup Form -->
                <div class="signup form-peice switched">
                    <form:form  action="/register2" modelAttribute="signup" method="post" enctype="multipart/form-data">

                        <div class="form-group">
                            <label for="name">Nama Lengkap</label>
                            <form:input path="nama_lengkap" type="text" name="username" id="name" class="name" required="required" />
                            <span class="error"></span>
                        </div>
                        <div class="form-group">
                            <label for="phone">Phone Number - <small>Optional</small></label>
                            <form:input  path="telepon" type="text" name="phone" id="phone" required="required" />
                        </div>
                        <div class="form-group">
                            <label for="email">Email Adderss</label>
                            <form:input  path="mail" type="email" name="emailAddress" id="email" class="email" required="required" />
                            <span class="error"></span>
                        </div>
<%--                        <div class="form-group">--%>
<%--                            <label for="name">Alamat</label>--%>
<%--                            <form:input path="alamat" type="text" name="username" id="name" class="name" required="required" />--%>
<%--                            <span class="error"></span>--%>
<%--                        </div>--%>
<%--                        <div class="form-group">--%>
<%--                            <label for="name">NIK</label>--%>
<%--                            <form:input path="foto_ktp" type="text" name="username" id="name" class="name" required="required" />--%>
<%--                            <span class="error"></span>--%>
<%--                        </div>--%>

                        <label class="form-control-label">Foto Member</label><br>
                        <input type="file" name="fileimage"></input>

                        <div class="form-group">
                            <label for="name">Username</label>
                            <form:input path="username" type="text" name="username" id="name" class="name" required="required" />
                            <span class="error"></span>
                        </div>
                        <div class="form-group">
                            <label for="password">Password</label>
                            <form:input path="password" type="password" name="password" id="password" class="pass" required="required" />
                            <span class="error"></span>
                        </div>

<%--                        <div class="form-group">--%>
<%--                            <label for="passwordCon">Confirm Password</label>--%>
<%--                            <input type="password" name="passwordCon" id="passwordCon" class="passConfirm">--%>
<%--                            <span class="error"></span>--%>
<%--                        </div>--%>

                        <div class="CTA">
                            <form:button type="submit" >Signup Now</form:button>
                            <a href="#" class="switch">I have an account</a>
                        </div>
                    </form:form>
                </div><!-- End Signup Form -->

                <!-- Login Form -->
                <div class="login form-peice ">
                    <form:form class="login-form" action="/login2" modelAttribute = "signin" method="post">
                        <div class="form-group" data-validate = "Username is required">
                            <label for="loginemail">Username</label>
                            <form:input path="user_name" type="text" name="loginemail" id="loginemail" required="required"/>
                        </div>

                        <div class="form-group" data-validate = "Password is required">
                            <label for="loginPassword">Password</label>
                            <form:input path="user_password" type="password" name="loginPassword" id="loginPassword" required="required" />
                        </div>

                        <div class="CTA">
                            <form:button type="submit" value="Login">Login</form:button>
                            <a href="#" class="switch">I'm New</a>
                        </div>
                    </form:form>
                </div><!-- End Login Form -->



            </div>
        </div>

    </section>
</div>
<!-- partial -->
<script src='https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js'></script>
<script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.0/jquery.min.js'></script>
<script  src="${contextPath}dist/script.js"></script>

</body>
</html>
