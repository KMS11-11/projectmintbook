<template>
    <div id="total_wrap">
        <HeaderPage style="position:fixed;z-index: 300;"></HeaderPage>
        <div class="center_wrap">
            <div class="headerpage"></div>
            <ul class="maintitle_wrap">
                <li><img src="../assets/MainPage/editorspick.png" alt=""></li>
                <li id="maintitle">에디터's PICK</li>
            </ul>
            <br><br>
            <div id="section_divide" v-for="(tmp, idx) in state.editor.content" :key="idx">
                <div id="section_left">
                    <p id="p1">"{{tmp.title}}"</p>
                    <p id="p2" @click="handleContent(tmp.bookid.id, tmp.bookid.genre)">{{ tmp.bookid.bookName }}</p>
                    <p id="p3">{{tmp.bookid.author}}·{{tmp.bookid.publisher}}</p>
                    <img id="bookimg" :src="tmp.bookid.img" alt="book" @click="handleContent(tmp.bookid.id, tmp.bookid.genre)">
                </div>
                <div id="section_right">
                    <p>{{ tmp.content }}</p>
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
import { reactive } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

export default {

    components:{
    HeaderPage:HeaderPage,
    FooterPage:FooterPage
    },

    setup () {
        
        const router = useRouter();

        const state = reactive({
            editor:[]
        })

        const mainEditor=()=>{
            axios.get('/api/get/main/editor').then(({data})=>{
                console.log(data);
                state.editor=data;
            }).catch(()=>{
                alert('에러가 발생했습니다.');
            });
        }

        const handleContent=(tmp1, tmp2)=>{
            router.push({path:'/book', query:{no:tmp1, genre:tmp2}})
        }

        mainEditor();

        return {
            state,
            handleContent,
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

    /*리스트 영역 */

    #section_divide{
        display: grid;
        grid-template-columns: 350px auto;
        text-align: center;
    }

    #p1{
        font-size: 20px;
        color: #3DDCA3;
        margin-bottom: 10px;
    }

    #p2{
        font-size: 32px;
        margin-bottom: 10px;
        font-weight: bold;
        cursor: pointer;
    }

    #p2:hover{
        text-decoration: underline;
    }

    #p3{
        font-size: 14px;
        color: #998F8F;
        margin-bottom: 10px;
    }

    #bookimg{
        width: 174px;
        box-shadow: 0 10px 20px rgba(0,0,0,0.19), 0 6px 6px rgba(0,0,0,0.23);
        cursor: pointer;
    }

    #bookimg:hover{
        box-shadow: 0 14px 28px rgba(0,0,0,0.25), 0 20px 20px rgba(0,0,0,0.22);
    }


    #section_right{
        padding-left: 70px;
        font-size: 22px;
        text-align: left;
    }

    #section_left, #section_right{
        border-bottom: 0.5px solid #B3B3B3;
        padding-top: 50px;
        padding-bottom: 50px;
    }

</style>