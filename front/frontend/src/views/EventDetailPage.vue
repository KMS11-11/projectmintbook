<template>
    <div id="total_wrap">
        <HeaderPage style="position:fixed;z-index: 300;"></HeaderPage>
        <div class="center_wrap">
            <div class="headerpage"></div>
            <div id="title_divide">
                <div id="title_left">
                    <p id="title">&lt;{{state.item.title}}&gt;</p>
                    <p id="period">{{state.item.startDate}}~{{state.item.endDate}}</p>
                </div>
                <div id="title_right">
                    <img src="../assets/BookDetailPage/share.png" alt="">
                </div>
            </div>
            <div id="event_content">
                <img :src="state.item.imgPath" alt="">
            </div>
            <div id="btn_wrap">
                <a href="/event"><button id="event_btn">목록으로</button></a>
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
import { reactive } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import axios from 'axios';

export default {

    components:{
    HeaderPage:HeaderPage,
    FooterPage:FooterPage
    },

    setup () {
        const route = useRoute();
        const router = useRouter();

        const state = reactive({
            item:[],
            no:Number(route.query.no)
        })

        const handleData = () => {
            axios.get(`/api/get/event/detail?id=${state.no}`).then(({data})=>{
                console.log("handleData",data);
                state.item=data;
            }).catch(()=>{
                alert('에러가 발생했습니다.');
                router.push({path:'/event'});
            });
        }

        handleData();

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
    
    /*1200px 중앙정렬 */
     .center_wrap{
        width: 1200px;
        margin: 0 auto;
    }

    /*HeaderPage용 공백 */
    .headerpage{
    height: 95px;
    }

    /*제목 영역 */
    #title_divide{
        display: grid;
        grid-template-columns: 1fr 1fr;
        margin-top: 50px;
        padding-bottom: 30px;
        border-bottom: 0.5px solid #B3B3B3;
    }

    #title{
        font-size: 24px;
        font-weight: bold;
        margin-bottom: 10px;
    }

    #period{
        font-size: 14px;
        color: #757575;
    }

    #title_right{
        text-align: right;
    }
    #title_right > img{
        width: 30px;
    }

    /*이벤트 콘텐츠 영역 */
    #event_content{
        width: 100%;
        text-align: center;
        padding-top: 50px;
        padding-bottom: 50px;
        border-bottom: 0.5px solid #B3B3B3;
    }
    
    #btn_wrap{
        width: 100%;
        margin-top: 30px;
        text-align: center;
    }

    #event_btn{
        border: none;
        background: #3DDCA3;
        width: 200px;
        height: 40px;
        color: white;
        border-radius: 5px;
        cursor: pointer;
    }
    
</style>