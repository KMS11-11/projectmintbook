<template>
    <div id="total_wrap">
        <div id="divide_wrap">
            <div id="nav_wrap">
                <admin-menu-page></admin-menu-page>
            </div>
            <div id="content_wrap">
                <p id="maintitle">검색어:"{{ state.text }}"</p>
                <p id="searchtotal">검색결과 {{state.item.totalElements}}건</p>
                <button id="register" @click="handleRegister()">이벤트 등록</button>
                <table class="table">
                    <thead>
                        <tr>
                            <th scope="col">No.</th>
                            <th scope="col">제목</th>
                            <th scope="col">등록날짜</th>
                            <th scope="col">시작날짜</th>
                            <th scope="col">만료날짜</th>
                            <th scope="col">진행여부</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(tmp,idx) of state.item.content" :key="idx" @click="handleContent(tmp.id)" style="cursor: pointer;">
                            <th scope="row">{{ idx+1 }}</th>
                            <td>{{ tmp.title }}</td>
                            <td>{{ tmp.regDate }}</td>
                            <td>{{ tmp.startDate }}</td>
                            <td>{{ tmp.endDate }}</td>
                            <td>진행중</td>
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
import { reactive } from 'vue';
import AdminMenuPage from '../../components/AdminMenuPage.vue';
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
            total:0,
            item:[],
            text:String(route.query.searchTerm),
        })

        const handleData=(pageNum)=>{
            axios.get(`/api/admin/event/search?searchTerm=${state.text}&page=${pageNum-1}`).then(({data})=>{
                console.log("handleSearchData",data);
                console.log(data);
                state.item = data;
                state.total = data.totalElements;
            }).catch(()=>{
                alert('에러가 발생했습니다.');
            });
        }

        const handleContent=(tmp)=>{
            router.push({path:'/admin/event/detail', query:{no:tmp}})
        }

        handleData();

        return {state, handleContent}
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

    /*사이드메뉴 영역 */
    #nav_wrap{
        background: rgb(223, 223, 223);
        height: 100vh;
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
        margin-bottom:30px;
    }

    #register{
        margin-left: 50px;
        float: right;
    }

    /*콘텐츠 하단 영역 */

    .table{
        width: 100%;
        margin-top:30px;
    }

    #pagination{
        display: flex;
        justify-content: center;
    }
    

    
</style>