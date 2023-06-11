<template>
  <div id="total_wrap">
      <div id="center_wrap">
        <ul id="nav_left_wrap">
          <li id="menu_button_wrap">
            <img src="../assets/Navigation/Menu.png" id="menubutton" alt="menuButton" @click="setModal()">
          </li>
          <li id="logo_wrap">
            <a href="/"><img src="../assets/Navigation/logo.png" id="logo" alt="logo"></a>
          </li>
          <li>
            <div id="search_wrap">
              <input type="text" v-model="state.searchtext" placeholder="2023 부커상 후보! 천명관 <고래>" @keyup.enter="handleSearch()"/>
              <img src="../assets/Navigation/search.png" alt="search" @click="handleSearch()">
            </div>
          </li>
        </ul>
        <ul id="nav_right_wrap">
          <li>
            <a href="/"><img src="../assets/Navigation/profile.png" id="profile" alt="profile"></a>
          </li>
          <li>
            <a href="/cart"><img src="../assets/Navigation/cart.png" id="cart" alt="cart"></a>
          </li>
          <li><a href="/cs">고객센터</a></li>
          <li><a href="/">회원가입</a></li>
          <li><a href="/">로그인</a></li>
        </ul>
      </div>
      <div id="modal_wrap" v-if="state.modalOpen === true">
        <div id="modal_title">
            <p>도서 카테고리</p>
        </div>
          <div id="modal_content_wrap">
              <ul class="categorylist">
                  <li @click="handleGenre1(1)">소설</li>
                  <li @click="handleGenre1(2)">시/에세이</li>
                  <li @click="handleGenre1(3)">인문</li>
                  <li @click="handleGenre1(4)">가정/육아</li>
                  <li @click="handleGenre1(5)">요리</li>
                  <li @click="handleGenre1(6)">건강</li>
              </ul>
              <ul class="categorylist">
                  <li @click="handleGenre1(7)">취미/실용/스포츠</li>
                  <li @click="handleGenre1(8)">경제/경영</li>
                  <li @click="handleGenre1(9)">자기계발</li>
                  <li @click="handleGenre1(10)">정치/사회</li>
                  <li @click="handleGenre1(11)">역사/문화</li>
                  <li @click="handleGenre1(12)">종교</li>
              </ul>
              <ul class="categorylist">
                  <li @click="handleGenre1(13)">예술/대중문화</li>
                  <li @click="handleGenre1(14)">중/고등참고서</li>
                  <li @click="handleGenre1(15)">기술/공학</li>
                  <li @click="handleGenre1(16)">외국어</li>
                  <li @click="handleGenre1(17)">과학</li>
                  <li @click="handleGenre1(18)">취업/수험서</li>
              </ul>
              <ul class="categorylist">
                  <li @click="handleGenre1(19)">여행</li>
                  <li @click="handleGenre1(20)">컴퓨터/IT</li>
                  <li @click="handleGenre1(21)">잡지</li>
                  <li @click="handleGenre1(22)">청소년</li>
              </ul>
              <ul class="categorylist" id="menulist">
                  <li><router-link to="/bestseller">베스트셀러</router-link></li>
                  <li><router-link to="/new">이 주의 신간</router-link></li>
                  <li><router-link to="/editorpick">에디터's PICK</router-link></li>
                  <li><router-link to="/event">이벤트</router-link></li>
              </ul>
          </div>
      </div>
  </div>
</template>

<script>
import { reactive } from 'vue';
import { useRouter } from 'vue-router';

export default {
  setup () {

    const router = useRouter();
    
    const state = reactive({
      searchtext:"",
      modalOpen: false,
    })

    const setModal=()=>{
      state.modalOpen=!state.modalOpen;
    }

    const handleSearch=()=>{
      state.searchtext=state.searchtext.trim();
      router.push({path:'/search', query:{searchTerm:state.searchtext}});
    }

    const handleGenre1=(tmp)=>{
      router.push({path:'/category', query:{genre:tmp}});
    }

    return {
      state,
      setModal,
      handleSearch,
      handleGenre1
    }
  }
}
</script>

<style lang="css" scoped>
   /* 초기화 */
   *{
      padding:0;
      margin:0;
    }

    ul,li{
      list-style: none;
    }

    li{
      cursor: pointer;
    }

    li:hover{
      font-weight: bold;
    }

    a{
      text-decoration: none;
      color: black;
    }
    a:hover{
      text-decoration: none;
      color: black;
      font-weight: bold;
    }


    #total_wrap{
      width: 100%;
      height: 95px;
      border-bottom: 0.5px solid #3DDCA3;
      background: white;
    }

    #center_wrap{
      width: 1200px;
      height: 95px;
      margin: 0 auto;
      position: relative;
    }

    #nav_left_wrap{
      /* width: 782px; */
      height: 50px;
      float: left;
      position: absolute;
      top: 50px;
      margin-top: -25px;
      /* background-color: rgb(161, 154, 154); */
    }

    #nav_left_wrap > li{
      float: left;
    }

    #menu_button_wrap{
      margin-right: 45px;
    }

    #menubutton{
      width:38px;
      margin-top:6px;
      cursor: pointer;
    }

    #logo_wrap{
      margin-right: 10px;
    }

    #logo {
      width: 168px;
      height: 50px;  
    }

    #search_wrap{
      border: 1.5px solid #3DDCA3; 
      width: 500px;
      height: 45px;
      border-radius: 22.5px;
      position: relative;
      margin-top:1.5px;
    }

    #search_wrap > input{
      background-color: transparent;
      border: none;
      color: rgb(0, 0, 0);
      caret-color: #3DDCA3;
      font-size: 14px;
      width: 90%;
      height: 45px;
      margin-left: 30px;
      -webkit-appearance: none;  /* 브라우저별 기본 스타일링 제거 */
      -moz-appearance: none;
      appearance: none;
    }

    #search_wrap > input:focus {
      outline:none;
      width: 90%;
    }

    #search_wrap > input::placeholder{
      color:rgb(174, 173, 173);
    }

    #search_wrap > img{
      width: 21px;
      height: 21px;
      float: right;
      right: 10px;
      position: absolute;
      top:50%;
      margin-top: -10.5px;
      cursor: pointer;
    }

    #nav_right_wrap{
      /* width: 315px; */
      height: 35px;
      float: right;
      line-height: 35px;
      position: absolute;
      top: 50%;
      margin-top: -17.5px;
      right: 0px;
    }
    #nav_right_wrap > li{
      float: right;
      font-size: 13px;
      margin-left: 17px;
    }

    #cart, #profile {
      width: 35px;
      height: 35px;
    }

    /*모달영역 */
  #modal_wrap{
      width: 1200px;
      border: 1.5px solid black;
      border-radius: 25px;
      padding: 50px;
      box-sizing: border-box;
      margin: 0 auto;
      background: white;
  }

  #modal_title{
      height: 50px;
      border-bottom: 1.5px solid #3DDCA3;
      margin: 0 auto;
      font-weight: bold;
      font-size: 18px;
  }

  #modal_content_wrap{
      display: grid;
      grid-template-columns: 1fr 1fr 1fr 1fr 1fr;
      margin-top: 40px;
      margin-bottom: 30px;
  }

  .categorylist > li{
      margin-top: 10px;
      font-size: 15px;
  }

  #menulist > li> a {
      color: #3DDCA3;
  }

</style>