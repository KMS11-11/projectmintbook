<template>
    <div class="total_wrap">
        <HeaderPage style="position:fixed;z-index: 300;"></HeaderPage>
        <div class="center_wrap">
            <div class="headerpage"></div>
            <div id="divide_section">
                <div id="category">
                    <p id="category_title">고객센터</p>
                    <ul>
                        <li><a href="/cs/register">1:1문의접수</a></li>
                        <li><a href="/faq">자주 묻는 질문</a></li>
                        <li><a href="/notice">공지사항</a></li>
                    </ul>
                </div>
                <div id="help_section">
                    <p id="maintitle">무엇을 도와드릴까요?</p>
                    <div id="content_divide">
                        <div id="content_left">
                            <p class="box_title">1:1문의</p>
                            <div id="divide_left">
                                <div>
                                    <a href="/cs/register">
                                        <img src="../assets/CSPage/question.png" alt="" id="img">
                                        <p>1:1문의</p>
                                    </a>
                                </div>
                                <div>
                                    <a href="faq">
                                        <img src="../assets/CSPage/faq.png" alt="" id="img">
                                    <p>자주 묻는 질문</p>
                                    </a>
                                </div>
                            </div>
                        </div>
                        <div id="content_right">
                            <p class="box_title" id="call1">전화 상담</p>
                            <p id="call2">1544-8088</p>
                            <p id="call3">평일 09:00~18:00(주말 및 공휴일 휴무)</p>
                        </div>
                    </div>
                </div>
            </div>
            <div>
                <el-backtop :right="70" :bottom="70" style="color: #3DDCA3;"/>
            </div>
        </div>
        <FooterPage></FooterPage>
    </div>
</template>

<script>
import HeaderPage from '@/components/HeaderPage.vue'
import FooterPage from '@/components/FooterPage.vue'

export default {

    components:{
    HeaderPage:HeaderPage,
    FooterPage:FooterPage
    },

    setup () {
        

        return {}
    }
}
</script>

<style lang="css" scoped>

    /*초기화 */
    *{
        padding:0;
        margin:0;
    }

    ul,li{
        list-style: none;
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
    /*1200px 중앙정렬 */
     .center_wrap{
        width: 1200px;
        margin: 0 auto;
    }

    /*HeaderPage용 공백 */
    .headerpage{
    height: 95px;
    }

   /*페이지 타이틀 영역 */
   .maintitle_wrap{
        height: 30px;
        margin-top: 40px;
        margin-bottom: 50px;
    }
    .maintitle_wrap > li{
        float: left;
    }

    .maintitle_wrap > li > img{
        width: 35px;
        margin-right: 10px;
    }

    #maintitle{
        font-size: 24px;
        font-weight: bold;
    }

     /*사이드 카테고리 영역*/
     #divide_section{
        display: grid;
        grid-template-columns: 247px auto;
    }

    #category{
        padding-top: 50px;
    }

    #category_title{
        font-size: 18px;
        font-weight: bold;
        margin-bottom: 25px;
    }

    #category > ul > li{
        font-size: 15px;
        margin-bottom: 10px;
    }

    #category > ul > li >a:hover{
        font-weight: bold;
        color: black;
    }

    /*무엇을 도와드릴까요 영역 */
    #help_section{
        font-weight: bold;
    }
    
    #maintitle{
        padding-top: 50px;
        font-size: 18px;
        margin-bottom: 30px;
    }

    #content_divide{
        display: grid;
        grid-template-columns: 1fr 1fr;
    }

    #content_left, #content_right{
        border-radius: 10px;
        border: 10px;
        width: 460px;
        height: 236px;
    }

    #content_left{
        background: #F0F0F0;
    }

    #content_right{
        margin-left: 16.5px;
        border: 0.5px solid #cdcdcd;
    }

    /*왼쪽 박스 영역 */
    #content_left, #content_right{
        padding: 30px;
        box-sizing: border-box;
    }

    #divide_left{
        display: grid;
        grid-template-columns: 1fr 1fr;
        text-align: center;
        margin-top: 50px;
    }

    .box_title{
        font-size: 17px;
        color: #3DDCA3;
        font-weight: bold;
    }

    #img{
        width: 67px;
    }

    #call1{
        color: black;
    }

    #call2{
        font-size: 32px;
        color: #3DDCA3;
        margin-top: 30px;
    }

    #call3{
        font-size: 15px;
        color: rgb(133, 133, 133);
        margin-top: 30px;
    }



</style>