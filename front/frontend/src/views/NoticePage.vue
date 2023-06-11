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
                        <li><a href="/notice" id="bold">공지사항</a></li>
                    </ul>
                </div>
                <div id="section">
                    <p id="maintitle">공지사항</p>
                    <table class="table">
                        <thead>
                            <tr>
                            <th scope="col" id="th_no">No.</th>
                            <th scope="col" id="th_title">제목</th>
                            <th scope="col" id="th_date">등록일</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="(tmp,idx) in state.item.content" :key="idx">
                                <th scope="row" class="td_no"> {{ tmp.id }} </th>
                                <td class="td_title" @click="handleContent(tmp.id)">{{ tmp.title }}</td>
                                <td class="td_date">{{ tmp.regDate }}</td>
                            </tr>
                        </tbody>
                    </table>
                    <div class="example-pagination-block">
                        <el-pagination layout="prev, pager, next" :total="state.total" @current-change="handleData"/>
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
            total:0
        })


        const handleData=(pageNum)=>{
            axios.get(`/api/get/notice?page=${pageNum-1}`).then(({data})=>{
                console.log("handleData",data);
                state.item = data;
                state.total=data.totalElements;
            }).catch(()=>{
                alert('에러가 발생했습니다.');
            });
        }

        const handleContent=(tmp)=>{
            router.push({path:'/notice/detail', query:{no:tmp}});
            
        }

        handleData();

        return {
            state,
            handleData,
            handleContent
        }

    },

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
        height: 30px;
        padding-bottom: 50px;
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
        margin-bottom: 50px;
        margin-top: 50px;
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

    #category > ul > li > a:hover{
        font-weight: bold;
        color: black;
    }

    #bold{
        font-weight: bold;
    }

    /*테이블 영역 */
    table{
        font-size: 14px;
        margin-bottom: 50px;
    }
    
    thead{
        text-align: center;
        border-top: 1px solid gray;
    }

    #th_no{
        width: 100px;
    }

    #th_date{
        width: 150px;
    }

    .td_no{
        text-align: center;
    }

    .td_title{
        padding-left: 20px;
        cursor: pointer;
    }

    .td_date{
        text-align: center;
    }

    tbody > tr > th, tbody > tr > td{
        padding-top: 20px;
        padding-bottom: 20px;
    }

    /* 페이지네이션 */
    .example-pagination-block{
        width: 320px;
        margin: 0 auto;
        margin-top: 50px;
    }

</style>