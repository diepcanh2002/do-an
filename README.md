<html lang="vi">
 <head>
  <meta charset="utf-8"/>
  <meta content="width=device-width, initial-scale=1" name="viewport"/>
  <title>
   Sports Booking
  </title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet"/>
  <link href="https://fonts.googleapis.com/css2?family=Roboto&amp;display=swap" rel="stylesheet"/>
  <style>
   body {
      font-family: "Roboto", sans-serif;
      background-color: #1f7a2e;
      padding-bottom: 70px;
    }
    .scroll-x {
      overflow-x: auto;
      white-space: nowrap;
    }
    .scroll-x > div {
      display: inline-block;
      white-space: normal;
    }
    .btn-outline-white {
      color: white;
      border-color: white;
    }
    .btn-outline-white:hover {
      background-color: white;
      color: #1f7a2e;
    }
    .text-green {
      color: #1f7a2e;
    }
    .bg-green {
      background-color: #1f7a2e;
    }
    .bg-yellow {
      background-color: #d4a62a;
    }
    .text-yellow {
      color: #d4a62a;
    }
    .bg-purple {
      background-color: #d46ad4;
    }
    .text-red {
      color: #d43f3f;
    }
    .bg-light-green {
      background-color: #e6f4ea;
    }
    .btn-yellow {
      background-color: #d4a62a;
      color: white;
      font-weight: 700;
    }
    .btn-yellow:hover {
      background-color: #b08f1a;
      color: white;
    }
    .btn-green {
      background-color: #1f7a2e;
      color: white;
      font-weight: 700;
    }
    .btn-green:hover {
      background-color: #15521a;
      color: white;
    }
    .rounded-xl {
      border-radius: 1rem !important;
    }
    .text-truncate-2 {
      display: -webkit-box;
      -webkit-line-clamp: 2;
      -webkit-box-orient: vertical;  
      overflow: hidden;
    }
    .bottom-nav {
      background-color: #1f7a2e;
      color: white;
      position: fixed;
      bottom: 0;
      left: 0;
      right: 0;
      height: 60px;
      z-index: 1030;
      display: flex;
      justify-content: space-around;
      align-items: center;
      font-weight: 600;
      font-size: 0.875rem;
    }
    .bottom-nav .nav-item {
      text-align: center;
      flex-grow: 1;
    }
    .bottom-nav .nav-link {
      color: white;
      display: flex;
      flex-direction: column;
      align-items: center;
      font-weight: 600;
      font-size: 0.875rem;
      padding: 0;
    }
    .bottom-nav .nav-link.active {
      color: #d4a62a;
    }
    .bottom-nav .nav-link i {
      font-size: 1.25rem;
    }
    .btn-pill {
      border-radius: 50rem !important;
    }
    .btn-sm-pill {
      border-radius: 50rem !important;
      font-size: 0.75rem;
      padding: 0.25rem 0.75rem;
    }
    .card-img-top {
      height: 180px;
      object-fit: cover;
    }
    .position-absolute.top-2 {
      top: 0.5rem !important;
    }
    .position-absolute.right-3 {
      right: 0.75rem !important;
    }
    .position-absolute.right-14 {
      right: 3.5rem !important;
    }
    .position-absolute.bottom-4 {
      bottom: 1rem !important;
    }
  </style>
 </head>
 <body>
  <!-- Top bar with time and icons -->
  <div class="d-flex justify-content-between px-3 pt-2 text-white small fw-semibold">
   <div class="d-flex align-items-center gap-2">
    <span>
     14:17
    </span>
    <i class="fas fa-comment-alt"></i>
    <i class="fas fa-key"></i>
    <i class="fab fa-google-play"></i>
   </div>
   <div class="d-flex align-items-center gap-2">
    <i class="far fa-clock"></i>
    <i class="fab fa-bluetooth-b"></i>
    <i class="fas fa-wifi"></i>
    <div class="d-flex flex-column fw-bold small lh-1" style="margin-left:-0.25rem;">
     <span style="line-height:1;">Vo</span>
     <span style="margin-top:-0.25rem;">LTE</span>
    </div>
    <div class="d-flex align-items-center gap-1">
     <i class="fas fa-signal"></i>
     <div class="small fw-semibold border border-white rounded px-1" style="line-height:1;">
      73
     </div>
    </div>
   </div>
  </div>
  <!-- Header with logo, date, login/register -->
  <div class="d-flex align-items-center px-3 pt-3 pb-4 gap-3 position-relative">
   <img alt="Logo with green background and abstract shape" class="rounded-3" height="48" src="https://storage.googleapis.com/a1aa/image/7da75a9c-4cdf-4edf-b55c-203b7a8c2c6a.jpg" width="48"/>
   <div class="flex-grow-1 text-white fs-5 fw-normal">
    Chủ Nhật, 27/07/2025
   </div>
   <button class="btn btn-white text-green fw-bold rounded-3 px-4 py-2 fs-6">
    Đăng nhập
   </button>
   <button class="btn btn-outline-white fw-bold rounded-3 px-4 py-2 fs-6">
    Đăng kí
   </button>
   <div aria-hidden="true" class="position-absolute top-2 end-3 bg-danger rounded-circle d-flex justify-content-center align-items-center" style="width:24px; height:24px;">
    <svg class="w-50 h-50" fill="yellow" viewbox="0 0 512 512" xmlns="http://www.w3.org/2000/svg">
     <path d="M256 8C119 8 8 119 8 256s111 248 248 248 248-111 248-248S393 8 256 8zM256 472c-119 0-216-97-216-216S137 40 256 40s216 97 216 216-97 216-216 216z"></path>
     <path d="M256 128a128 128 0 1 0 128 128A128.14 128.14 0 0 0 256 128zm0 224a96 96 0 1 1 96-96 96.11 96.11 0 0 1-96 96z"></path>
    </svg>
   </div>
  </div>
  <!-- Search bar -->
  <div class="d-flex align-items-center gap-2 px-3 mb-3">
   <div class="input-group shadow-sm rounded-3 flex-grow-1">
    <span class="input-group-text bg-white border-0 rounded-start-3">
     <img alt="Green abstract logo icon" height="24" src="https://storage.googleapis.com/a1aa/image/a38034c1-a8c7-4bc3-3fa9-619a9032ebed.jpg" width="24"/>
    </span>
    <input aria-label="Tìm kiếm" class="form-control border-0 rounded-0" placeholder="Tìm kiếm" type="search"/>
    <button class="btn btn-white text-green rounded-end-3" type="button">
     <i class="fas fa-search fs-5"></i>
    </button>
   </div>
   <button aria-label="Favorites" class="btn btn-white rounded-3 shadow-sm p-3">
    <i class="far fa-heart text-green fs-4"></i>
   </button>
  </div>
  <!-- Quick search buttons -->
  <div class="d-flex gap-3 px-3 mb-3 flex-wrap">
   <button class="btn btn-light text-green rounded-3 flex-grow-1 flex-sm-grow-0" style="min-width:130px;">
    Cầu lông gần tôi
   </button>
   <button class="btn btn-light text-green rounded-3 flex-grow-1 flex-sm-grow-0" style="min-width:130px;">
    Pickleball gần tôi
   </button>
   <button class="btn btn-light text-green rounded-3 flex-grow-1 flex-sm-grow-0" style="min-width:130px;">
    Xé vé gần tôi
   </button>
  </div>
  <!-- Sports icons row -->
  <div class="scroll-x px-3 mb-4 pb-1">
   <div class="text-center me-3" style="width:64px;">
    <div class="border border-secondary rounded-3 p-2 bg-white d-flex justify-content-center align-items-center" style="width:56px; height:56px;">
     <img alt="Pickleball icon with blue background and white paddle and ball" height="32" src="https://storage.googleapis.com/a1aa/image/009d9c42-df59-44ca-aeba-084a2593c7f7.jpg" width="32"/>
    </div>
    <div class="text-green small mt-1">
     Pickleball
    </div>
   </div>
   <div class="text-center me-3" style="width:64px;">
    <div class="border border-secondary rounded-3 p-2 bg-white d-flex justify-content-center align-items-center" style="width:56px; height:56px;">
     <img alt="Cầu lông icon with teal background and white shuttlecock" height="32" src="https://storage.googleapis.com/a1aa/image/ca6ff22d-ecac-4773-6904-8faca662ef71.jpg" width="32"/>
    </div>
    <div class="text-green small mt-1">
     Cầu lông
    </div>
   </div>
   <div class="text-center me-3" style="width:64px;">
    <div class="border border-secondary rounded-3 p-2 bg-white d-flex justify-content-center align-items-center" style="width:56px; height:56px;">
     <img alt="Bóng đá icon with green background and white soccer ball" height="32" src="https://storage.googleapis.com/a1aa/image/a1bc50d7-c9a9-478e-ef54-b381f6f2233d.jpg" width="32"/>
    </div>
    <div class="text-green small mt-1">
     Bóng đá
    </div>
   </div>
   <div class="text-center me-3" style="width:64px;">
    <div class="border border-secondary rounded-3 p-2 bg-white d-flex justify-content-center align-items-center" style="width:56px; height:56px;">
     <img alt="Tennis icon with brown background and white tennis racket and ball" height="32" src="https://storage.googleapis.com/a1aa/image/878531c3-1186-4a42-e25c-1c4a35dc3d83.jpg" width="32"/>
    </div>
    <div class="text-green small mt-1">
     Tennis
    </div>
   </div>
   <div class="text-center me-3" style="width:64px;">
    <div class="border border-secondary rounded-3 p-2 bg-white d-flex justify-content-center align-items-center" style="width:56px; height:56px;">
     <img alt="B.Chuyền icon with yellow background and white volleyball" height="32" src="https://storage.googleapis.com/a1aa/image/b6a11bec-b49c-48c1-a24e-b2c7860317f1.jpg" width="32"/>
    </div>
    <div class="text-green small mt-1">
     B.Chuyền
    </div>
   </div>
   <div class="text-center me-3" style="width:64px;">
    <div class="border border-secondary rounded-3 p-2 bg-white d-flex justify-content-center align-items-center" style="width:56px; height:56px;">
     <img alt="Bóng rổ icon with orange background and white basketball" height="32" src="https://storage.googleapis.com/a1aa/image/77b56096-0ee9-4a89-b739-93bf7c5423d6.jpg" width="32"/>
    </div>
    <div class="text-green small mt-1">
     Bóng rổ
    </div>
   </div>
  </div>
  <!-- Search more section -->
  <div class="bg-light-green rounded-xl mx-3 p-3 mb-4 text-green">
   <div class="d-flex justify-content-between align-items-center mb-3">
    <div class="d-flex align-items-center gap-2 fw-bold text-red fs-5">
     <i class="fas fa-globe-americas fs-4"></i>
     <span>
      Bạn muốn tìm kiếm nhiều hơn
     </span>
    </div>
    <button aria-label="Filter settings" class="btn p-0 text-green border-0">
     <svg class="w-6 h-6" fill="none" stroke="#1f7a2e" stroke-width="2" viewbox="0 0 24 24" xmlns="http://www.w3.org/2000/svg" style="width:24px; height:24px;">
      <path d="M3 4a1 1 0 011-1h16a1 1 0 011 1v2a1 1 0 01-1 1H4a1 1 0 01-1-1V4zM3 10a1 1 0 011-1h10a1 1 0 011 1v2a1 1 0 01-1 1H4a1 1 0 01-1-1v-2zM3 16a1 1 0 011-1h6a1 1 0 011 1v2a1 1 0 01-1 1H4a1 1 0 01-1-1v-2z" stroke-linecap="round" stroke-linejoin="round"></path>
     </svg>
    </button>
   </div>
   <form class="row g-2 mb-2">
    <div class="col-6">
     <select class="form-select text-green" aria-label="Tỉnh/TP">
      <option selected disabled>
       Tỉnh/TP
      </option>
      <option>
       Hà Nội
      </option>
      <option>
       Hồ Chí Minh
      </option>
     </select>
    </div>
    <div class="col-6">
     <select class="form-select text-green" aria-label="Quận/Huyện">
      <option selected disabled>
       Quận/Huyện
      </option>
      <option>
       Quận 1
      </option>
      <option>
       Quận 2
      </option>
     </select>
    </div>
   </form>
   <form class="row g-2 align-items-center">
    <div class="col-4">
     <select class="form-select text-green" aria-label="Loại lịch">
      <option selected disabled>
       Loại lịch
      </option>
      <option>
       Đơn ngày
      </option>
      <option>
       Đặt trước
      </option>
     </select>
    </div>
    <div class="col-4">
     <select class="form-select text-green" aria-label="Thời gian">
      <option selected disabled>
       Thời gian
      </option>
      <option>
       Sáng
      </option>
      <option>
       Chiều
      </option>
     </select>
    </div>
    <div class="col-2 d-grid">
     <button class="btn btn-green" type="submit">
      Tìm kiếm
     </button>
    </div>
    <div class="col-2 d-grid">
     <button aria-label="Reset search" class="btn btn-yellow" type="reset">
      <i class="fas fa-sync-alt"></i>
     </button>
    </div>
   </form>
  </div>
  <!-- Card 1 -->
  <div class="card mx-3 mb-4 rounded-xl shadow position-relative overflow-hidden">
   <img alt="Blue building with curved roof and green park with fenced courts and trees" class="card-img-top" src="https://storage.googleapis.com/a1aa/image/458c85e3-7e64-4e4e-cfa5-1136b9750e85.jpg"/>
   <div class="position-absolute top-2 start-2 d-flex gap-2 text-white text-nowrap fs-7 fw-semibold rounded-pill px-3 py-1">
    <div class="bg-green rounded-pill d-flex align-items-center gap-1 px-3 py-1">
     <i class="far fa-star"></i>
     <span>Đơn ngày</span>
    </div>
    <div class="bg-purple rounded-pill px-3 py-1">
     Sự kiện
    </div>
   </div>
   <button aria-label="Favorite" class="btn btn-white rounded-circle shadow position-absolute top-2 right-14 p-2" style="right:3.5rem;">
    <i class="far fa-heart text-green fs-5"></i>
   </button>
   <button aria-label="Share" class="btn btn-white rounded-circle shadow position-absolute top-2 right-3 p-2" style="right:0.75rem;">
    <svg class="text-green" fill="none" stroke="currentColor" stroke-width="2" style="width:20px; height:20px;" viewbox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
     <path d="M15 8a3 3 0 11-2.83-4H7a2 2 0 00-2 2v8a2 2 0 002 2h5.17A3 3 0 1115 8z" stroke-linecap="round" stroke-linejoin="round"></path>
    </svg>
   </button>
   <div class="card-body position-relative">
    <div class="d-flex align-items-center gap-3">
     <img alt="Xanh Melody logo green with flower icon" class="rounded-3" height="32" src="https://storage.googleapis.com/a1aa/image/b5a590e7-0de3-43a0-9d83-243ac0bd6c0e.jpg" width="32"/>
     <h3 class="card-title fw-bold text-green mb-0 fs-5">
      Xanh Melody
     </h3>
     <span class="text-warning fs-6">
      (6.8km)
     </span>
    </div>
    <p class="card-text text-truncate-2 mt-1" style="color:#4a4a4a;">
     AMDI Green City, Việt Đoàn, Tiên Du, Bắc Ninh
    </p>
    <div class="d-flex align-items-center gap-3 mt-2 text-secondary small">
     <div class="d-flex align-items-center gap-1">
      <i class="far fa-clock text-green"></i>
      <span>06:00 - 22:00</span>
     </div>
     <div class="d-flex align-items-center gap-1">
      <i class="fas fa-phone-alt text-green"></i>
      <a class="text-green fw-semibold text-decoration-none" href="tel:0982569588">
       0982569588
      </a>
     </div>
    </div>
    <button class="btn btn-yellow position-absolute bottom-4 end-4 px-3 py-2 fs-7 fw-bold">
     ĐẶT LỊCH
    </button>
   </div>
  </div>
  <!-- Card 2 -->
  <div class="card mx-3 mb-5 rounded-xl shadow position-relative overflow-hidden">
   <img alt="Deli Pickleball logo on dark background with gold text and pickleball paddles" class="card-img-top" src="https://storage.googleapis.com/a1aa/image/a98c5a89-4e25-4b3b-7057-4339f8e0b8c0.jpg"/>
   <div class="position-absolute top-2 start-2 d-flex gap-2 text-white text-nowrap fs-7 fw-semibold rounded-pill px-3 py-1">
    <div class="bg-yellow rounded-pill d-flex align-items-center gap-1 px-3 py-1">
     <i class="fas fa-star"></i>
     <span>5.0</span>
    </div>
    <div class="bg-green rounded-pill d-flex align-items-center gap-1 px-3 py-1">
     <span>Đơn ngày</span>
    </div>
    <div class="bg-purple rounded-pill px-3 py-1">
     Sự kiện
    </div>
   </div>
   <button aria-label="Favorite" class="btn btn-white rounded-circle shadow position-absolute top-2 right-14 p-2" style="right:3.5rem;">
    <i class="far fa-heart text-green fs-5"></i>
   </button>
   <button aria-label="Share" class="btn btn-white rounded-circle shadow position-absolute top-2 right-3 p-2" style="right:0.75rem;">
    <svg class="text-green" fill="none" stroke="currentColor" stroke-width="2" style="width:20px; height:20px;" viewbox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
     <path d="M15 8a3 3 0 11-2.83-4H7a2 2 0 00-2 2v8a2 2 0 002 2h5.17A3 3 0 1115 8z" stroke-linecap="round" stroke-linejoin="round"></path>
    </svg>
   </button>
   <div class="card-body position-relative">
    <div class="d-flex align-items-center gap-3">
     <img alt="Deli Pickleball logo gold and black circular emblem" class="rounded-circle" height="32" src="https://storage.googleapis.com/a1aa/image/f5bd4e23-b11c-48ba-eba2-07150b706be4.jpg" width="32"/>
     <h3 class="card-title fw-bold text-green mb-0 fs-5">
      Deli Pickleball
     </h3>
     <span class="text-warning fs-6">
      (12.0km)
     </span>
    </div>
    <p class="card-text text-truncate-2 mt-1" style="color:#4a4a4a;">
     Khu thể thao S văn hoá, khu phố mới, phường ...
    </p>
    <div class="d-flex align-items-center gap-3 mt-2 text-secondary small">
     <div class="d-flex align-items-center gap-1">
      <i class="far fa-clock text-green"></i>
      <span>06:00 - 22:00</span>
     </div>
     <div class="d-flex align-items-center gap-1">
      <i class="fas fa-phone-alt text-green"></i>
      <a class="text-green fw-semibold text-decoration-none" href="tel:0982569588">
       0982569588
      </a>
     </div>
    </div>
    <button class="btn btn-yellow position-absolute bottom-4 end-4 px-3 py-2 fs-7 fw-bold">
     ĐẶT LỊCH
    </button>
   </div>
  </div>
  <!-- Bottom navigation -->
  <nav class="bottom-nav">
   <div class="nav-item">
    <a aria-current="page" class="nav-link active" href="#">
     <i class="fas fa-home"></i>
     <span>Trang chủ</span>
    </a>
   </div>
   <div class="nav-item">
    <a class="nav-link" href="#">
     <i class="fas fa-map"></i>
     <span>Bản đồ</span>
    </a>
   </div>
   <div class="nav-item">
    <a class="nav-link" href="#">
     <i class="far fa-newspaper"></i>
     <span>Nổi bật</span>
    </a>
   </div>
   <div class="nav-item">
    <a class="nav-link" href="#">
     <i class="far fa-user"></i>
     <span>Tài khoản</span>
    </a>
   </div>
  </nav>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js">
  </script>
 </body>
</html>
