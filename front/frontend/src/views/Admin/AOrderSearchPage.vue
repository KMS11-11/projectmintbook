<template>
    <div id="total_wrap">
        <div id="divide_wrap">
            <div id="nav_wrap">
                <admin-menu-page></admin-menu-page>
            </div>
            <div id="content_wrap">
                <p id="maintitle">검색어:"{{state.searchTerm}}"</p>
                <p id="searchtotal">검색결과 {{state.item.totalElements}}건</p>
                <table class="table">
                    <thead>
                        <tr>
                            <th scope="col">No.</th>
                            <th scope="col">주문번호</th>
                            <th scope="col">아이디</th>
                            <th scope="col">금액</th>
                            <th scope="col">주문날짜</th>
                            <th scope="col">주문현황</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(tmp, idx) in state.item.content" :key="idx" @click="handleContent(tmp.id)" style="cursor: pointer;">
                            <th scope="row">{{ idx+1 }}</th>
                            <td>{{tmp.orderNum}}</td>
                            <td>{{ tmp.buyerEmail }}</td>
                            <td>{{Number(tmp.totalPrice).toLocaleString()}}원</td>
                            <td>{{ tmp.orderDate }}</td>
                            <td>{{tmp.status}}</td>
                        </tr>
                    </tbody>
                </table>
                <div id="pagination">
                    <el-pagination layout="prev, pager, next" :total="state.total" @current-change="handleData" />
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { onMounted, reactive } from 'vue';
import AdminMenuPage from '../../components/AdminMenuPage.vue';
import router from '@/router';
import axios from 'axios';
import { useRoute, useRouter } from 'vue-router';

export default {
    components:{
        AdminMenuPage
    },

    setup () {
        const route = useRoute();
        const router = useRouter();
        
        const state = reactive({
            num:["1","2","3","4","5","6","7","8","9","10"],
            memberid:3,
            item:[],
            searchTerm:String(route.query.searchTerm),
            total:0

        })

        const handleData=async(pageNum)=>{
            await axios.get(`/api/get/order/all?searchTerm=${state.searchTerm}&page=${pageNum-1}`).then(({data})=>{
                console.log("handleData",data);
                state.item = data;
                state.total = data.totalElements;
            })
        }

        const handleContent=(tmp)=>{
            router.push({path:'/admin/order/detail', query:{no:tmp}})
        }

        onMounted(()=>{
            handleData();
        })

        return {
            state,
            handleData,
            handleContent
        }
    }
}
</script>
import 
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

    /*전체 페이지 영역 */
    #divide_wrap{
        /* width: 1920px; */
        display: grid;
        grid-template-columns: 290px auto;
    }

    #content_wrap{
        padding-left: 30px;
        padding-right: 30px;
    }

    /*콘텐츠 상단 영역 */
    #maintitle{
        font-size: 24px;
        font-weight: bold;
        margin-top: 50px;
        margin-bottom:20px;
    }

    #searchtotal{
        font-size: 20px;
        margin-bottom:20px;
    }

    button{
        width: 100px;
        border-radius: 5px;
        color: white;
        background: black;
    }

    select{
        width: 100px;
        height: 28px;
        margin-right: 20px;
    }

    input{
        margin-right: 10px;
    }

    #register{
        margin-left: 50px;
        float:right;
    }

    /*콘텐츠 하단 영역 */
    #nav_wrap{
        background: rgb(223, 223, 223);
        height: 100vh;
    }

    .table{
        width: 100%;
        margin-top:30px;
        margin-bottom: 30px;
    }

    #pagination{
        display: flex;
        justify-content: center;
    }
    

    
</style>