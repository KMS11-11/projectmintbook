<template>
    <div id="footer_total_wrap">
      <footer id="footer_content_wrap">
        <div id="footer_logo">
          <img src="../assets/Navigation/logo.png" alt="">
        </div>
        <div id="footer_content_section">
          <ul class="footer_content" id="content1">
            <li>회사소개</li>
            <li>|</li>
            <li>이용양관</li>
            <li>|</li>
            <li>개인정보처리방침</li>
            <li>|</li>
            <li>청소년보호정책</li>
            <li>|</li>
            <li>대량주문안내</li>
            <li>|</li>
            <li>채용정보</li>
            <li>|</li>
            <li>광고소개</li>
          </ul>
          <br>
          <ul class="footer_content" id="content2">
            <li>대표이사 이동호</li>
            <li>고객정보보호 책임자 이동호</li>
            <li>사업장등록 2-1-24-94025</li>
            <li>통신파매업신고 중구02214호</li>
          </ul>
          <br>
          <ul class="footer_content" id="content3">
            <li>이메일 cs@mintbook.co.kr</li>
            <li>호스팅 제공자 민트북컴퍼니</li>
            <li>(본사) 서울시 중구 서대문로 32-79</li>
          </ul>
          <br>
          <ul class="footer_content" id="content4">
            <li>Mintbook Company. All Rights Reserved.</li>
          </ul>
          <br>
          <ul class="footer_content">
            <li>
              <img src="../assets/Footer/facebook.png" alt="facebook" class="footer_sns">
            </li>
            <li>
              <img src="../assets/Footer/instagram.png" alt="instagram" class="footer_sns">
            </li>
            <li>
              <img src="../assets/Footer/twitter.png" alt="twitter" class="footer_sns">
            </li>
            <li>
              <img src="../assets/Footer/youtube.png" alt="youtube" class="footer_sns">
            </li>
          </ul>
        </div>
      </footer>
    </div>
  </template>
  
  <script>
  export default {
  
  }
  </script>
  
  <style  lang="css" scoped>
    #footer_total_wrap{
      width: 100%;
      height: 270px;
      background: rgb(244, 244, 244);
      margin-top: 100px;
    }

    #footer_content_wrap{
      width:1200px;
      margin: 0 auto;
      padding-top: 50px;
      display: grid;
      grid-template-columns: 4fr 6fr;
      color: rgb(83, 83, 83);
    }

    #footer_content_section > ul{
      margin-bottom: 14px;
    }
    .footer_content > li{
      float:left;
    }

    #content1{
      font-size: 14px;
      font-weight: bold;
    }

    #content1 > li{
      margin-right: 13px;
    }

    #content2{
      font-size: 12px;
    }

    #content2 > li{
      margin-right: 20px;
    }

    #content3{
      font-size: 12px;
    }

    #content3 > li{
      margin-right: 20px;
    }

    #content4{
      font-size: 12px;
    }

    #content4 > li{
      margin-right: 20px;
    }

    .footer_sns{
      width: 31px;
      margin-right: 20px;
    }

  </style>