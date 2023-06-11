<template>
    <div id="nav_total_wrap">
        <div id="menu_top">
            <label>
                <img src="../assets/AdminPage/adminlogo.png" alt="">
            </label>
            <label id="admintext">Admin</label>
        </div>
        <div id="menu_bottom">
            <ul>
                <li><router-link to="/admin/book">도서 관리</router-link></li>
                <li><router-link to="/admin/order">주문 관리</router-link></li>
                <li><router-link to="/admin/member">회원 관리</router-link></li>
                <li><router-link to="/admin/review">리뷰 관리</router-link></li>
                <li><router-link to="/admin/cs">1:1문의 관리</router-link></li>
                <li><router-link to="/admin/event">이벤트 관리</router-link></li>
                <li><router-link to="/admin/notice">공지 관리</router-link></li>
                <li><router-link to="/admin/stats">통계</router-link></li>
            </ul>
        </div>
    </div>
</template>

<script>
import { reactive } from 'vue'

export default {
    setup () {

        const state = reactive({
            
        })

        return {
            state,
        }
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
        color: black;
        font-weight: bold;
    }
    
    #nav_total_wrap{
        /* width: 290px; */
        /* background: rgb(194, 194, 194); */
        text-align: center;
    }
    img{
        width: 40px;
    }
    #admintext{
        font-size: 24px;
        font-weight: bold;
    }

    #menu_top{
        padding-top: 50px;
    }

    #menu_bottom{
        margin-top: 60px;
    }

    #menu_bottom > ul > li{
        padding-bottom: 20px;
    }
    
    a.router-link-active, a.router-link-exact-active{
        font-weight: bold;
    }
    
</style>