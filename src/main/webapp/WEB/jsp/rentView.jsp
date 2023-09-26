<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!doctype html>
<html lang="en">
<head>
    <style>
        /* Mengatur tampilan grid */
        .containerr {
            display: grid;
            grid-template-columns: 250px auto; /* Kolom pertama untuk filter, kolom kedua untuk data produk */
            grid-gap: 20px; /* Jarak antara kolom */
        }

        /* Mengatur container filter */
        .filter-container {
            background-color: #f0f0f0;
            padding: 10px;
            border-right: 2px solid #a0a0a0;
        }

        /* Mengatur container data produk */
        .data-container {
            /*background-color: #e0e0e0;*/
            padding: 10px;
        }

        /* Mengatur gaya filter */
        .filter {
            margin-bottom: 10px;
        }

        /* Mengatur judul filter */
        .filter-title {
            font-weight: bold;
        }
    </style>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="author" content="Untree.co">
    <link rel="shortcut icon" href="${contextPath}/user/favicon.png">

    <meta name="description" content="" />
    <meta name="keywords" content="bootstrap, bootstrap4" />

    <!-- Bootstrap CSS -->
    <link href="${contextPath}/user/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/user/https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
    <link href="${contextPath}/user/css/tiny-slider.css" rel="stylesheet">
    <link href="${contextPath}/user/css/style.css" rel="stylesheet">
    <title>Rz Rentcos</title>
</head>

<body>
<!-- Start Header/Navigation -->
<nav class="custom-navbar navbar navbar navbar-expand-md navbar-dark bg-dark" arial-label="Furni navigation bar">

    <div class="container">
        <a class="navbar-brand" href="/home">Rz Rentcos<span>.</span></a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarsFurni" aria-controls="navbarsFurni" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarsFurni">
            <ul class="custom-navbar-nav navbar-nav ms-auto mb-2 mb-md-0">
                <li class="nav-item ">
                    <a class="nav-link" href="/home">Home</a>
                </li>
                <li class="active"><a class="nav-link" href="/rent">Rent</a></li>
                <li><a class="nav-link" href="#">About us</a></li>
            </ul>

            <ul class="custom-navbar-cta navbar-nav mb-2 mb-md-0 ms-5">
                <li><a class="nav-link" href="#"><img src="${contextPath}/user/images/user.svg"></a></li>
                <li><a class="nav-link" href="#"><img src="${contextPath}/user/images/cart.svg"></a></li>
            </ul>
        </div>
    </div>

</nav>
<!-- End Header/Navigation -->

<!-- Start Hero Section -->
<div class="hero">
    <div class="container">
        <div class="row justify-content-between">
            <div class="col-lg-5">
                <div class="intro-excerpt">
                    <h1>Rent</h1>
                </div>
            </div>
            <div class="col-lg-7">

            </div>
        </div>
    </div>
</div>
<!-- End Hero Section -->

<div class="containerr">
    <!-- Container filter di kolom pertama -->
    <div class="filter-container">
        <h2>Filter</h2>

        <!-- Filter-filter di sini -->
        <form action="/getKostum" method="GET">
            <label for="nama">Sort by:</label>
            <input type="text" name="sortby" id="sortby" placeholder="Sort by"/>
            <br>

            <label for="nama">Nama:</label>
            <input type="text" name="nama" id="nama" placeholder="Nama Kostum"/>
            <br>

            <label for="minharga">Min Harga:</label>
            <input type="text" name="minharga" id="minharga" placeholder="Harga Kostum"/>
            <br>

            <label for="maxharga">Max Harga:</label>
            <input type="text" name="maxharga" id="maxharga" placeholder="Harga Kostum"/>
            <br>

            <label for="kategori">Kategori:</label>
            <input type="text" name="kategori" id="kategori" placeholder="Kategori Kostum"/>
            <br>

            <button type="submit">Filter</button>
        </form>
    </div>

    <!-- Container data produk di kolom kedua -->
    <div class="data-container">
        <h2>Data Produk</h2>
        <div class="container untree_co-section product-section before-footer-section">
            <div class="row">
                <c:if test="${empty kostum}">
                    <p>Tidak ada hasil pencarian.</p>
                </c:if>
                <table>
                    <tr>
                        <c:forEach var="cos" items="${kostum}" varStatus="loop">
                        <td>
                            <div class="product-item" style ="margin-top: 20px">
                                <a  class="product-item" href="/getKostumDetail?id=${cos.costum_id}" >
                                    <img src="/images/${cos.foto}" class="img-fluid product-thumbnail" style="width: 220px; height: 220px; border-radius: 15px">
                                    <h3 class="product-title">${cos.costum_id}</h3>
                                    <h3 class="product-title">${cos.costum_name}</h3>
                                    <strong class="product-price">Rp. ${cos.harga}</strong>
                                </a>

                                <button class="myModul">TEKAANNNN</button>

<%--                                <a href="/tampilkanModul" >--%>
                                    <span class="icon-cross">
                                       <img src="${contextPath}/user/images/cross.svg" class="img-fluid">
                                    </span>
<%--                                </a>--%>
                            </div>
                        </td>
                        <c:if test="${loop.index % 4 == 3}">
                    <tr></tr>
                    <tr></tr>
                    </c:if>
                    </c:forEach>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</div>

<!-- Start Footer Section -->
<footer class="footer-section">
    <div class="container relative">
        <div class="border-top copyright">
            <div class="row pt-4">
                <div class="col-lg-6">
                    <p class="mb-2 text-center text-lg-start">Copyright &copy;<script>document.write(new Date().getFullYear());</script>. All Rights Reserved. &mdash; Designed with love by <a href="https://untree.co">Untree.co</a> Distributed By <a hreff="https://themewagon.com">ThemeWagon</a>  <!-- License information: https://untree.co/license/ -->
                    </p>
                </div>

                <div class="col-lg-6 text-center text-lg-end">
                    <ul class="list-unstyled d-inline-flex ms-auto">
                        <li class="me-4"><a href="#">Terms &amp; Conditions</a></li>
                        <li><a href="#">Privacy Policy</a></li>
                    </ul>
                </div>

            </div>
        </div>

    </div>
</footer>
<!-- End Footer Section -->


<script src="${contextPath}/user/js/bootstrap.bundle.min.js"></script>
<script src="${contextPath}/user/js/tiny-slider.js"></script>
<script src="${contextPath}/user/js/custom.js"></script>
<script>
    function handleImageClick() {
        // Cek status login pengguna di sini
        // var isLoggedIn = false; // Gantilah dengan status login yang sesuai dari server atau cookie

        if (${sessionScope.username==null}) {
            <jsp:include page="loginModul.jsp" />
        } else {
            window.location.href = '/home';
        }
        // alert('EEEEEEE');
    }
    const myModulButtons = document.querySelectorAll('.myModul');

    // Menambahkan event listener untuk setiap tombol
    myModulButtons.forEach(button => {
        button.addEventListener('click', () => {
            // Tampilkan modul di sini
            alert('Modul ditampilkan ketika tombol ditekan.');
            // Anda dapat menggantikan alert di atas dengan logika untuk menampilkan modul yang sesuai.
        });
    });

</script>

</body>
</html>
