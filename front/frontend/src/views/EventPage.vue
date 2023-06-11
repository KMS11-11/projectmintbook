<template>
    <div id="total_wrap">
        <HeaderPage style="position:fixed;z-index: 300;"></HeaderPage>
        <div class="center_wrap">
            <div class="headerpage"></div>
            <ul class="maintitle_wrap">
                <li><img src="../assets/EventPage/event.png" alt=""></li>
                <li id="maintitle">이벤트</li>
            </ul>
            <div id="categorylist">
                <div id="eventimg" v-for="(tmp, idx) in state.item" :key="idx">
                    <img :src="tmp.listimgPath" alt="" id="img" @click="handleContent(tmp.id)">
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
import axios from 'axios'
import { reactive } from 'vue'
import { useRouter } from 'vue-router'

export default {
    
    components:{
    HeaderPage:HeaderPage,
    FooterPage:FooterPage
    },

    setup () {

        const router = useRouter();
        
        const state = reactive({
            item:[],

        })

        const handleData=()=>{
            axios.get('/api/get/event').then(({data})=>{
                console.log(data);  
                state.item = data;      
            })
        }

        const handleContent=(tmp)=>{
            router.push({path:'/event/detail', query:{no:tmp}})
        }

        handleData();

        return {
            state,
            handleContent
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

    /*페이지 타이틀 영역 */
    .maintitle_wrap{
        margin-top: 40px;
        margin-bottom: 50px;
        padding-bottom: 100px;
        border-bottom: 0.5px solid #B3B3B3;
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

    /*카테고리 리스트 영역 */
    #categorylist{
        display: grid;
        width: 100%;
        grid-template-columns: 1fr 1fr 1fr;
    }

    #eventimg{
        width: 380px;
        height: 206px;
        overflow: hidden;
        border-radius: 10px;
        box-shadow: 0 10px 20px rgba(0,0,0,0.19), 0 6px 6px rgba(0,0,0,0.23);
        margin:0 auto;
        margin-bottom: 30px;
        cursor: pointer;
    }

    #eventimg:hover{
        box-shadow: 0 14px 28px rgba(0,0,0,0.25), 0 5px 5px rgba(0,0,0,0.22);
    }

    #img{
        width:380px;
        height:206px;
    }
    

</style>