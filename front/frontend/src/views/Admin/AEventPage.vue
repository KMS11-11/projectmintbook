<template>
    <div id="total_wrap">
        <div id="divide_wrap">
            <div id="nav_wrap">
                <admin-menu-page></admin-menu-page>
            </div>
            <div id="content_wrap">
                <p id="maintitle">이벤트 관리</p>
                <!-- <label>
                    <select>
                        <option value="title">진행여부</option>
                        <option value="author">제목</option>
                        <option value="isbn">작성날짜</option>
                        <option value="publisher">만료날짜</option>
                    </select>
                </label> -->
                <label>
                    <input type="text" v-model="state.text" @keyup.enter="handleSearch()">
                </label>
                <button id="search" @click="handleSearch()">검색</button>
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
import router from '@/router';
import axios from 'axios';

export default {
    components:{
        AdminMenuPage
    },

    setup () {
        const state = reactive({
            num:["1","2","3","4","5","6","7","8","9","10"],
            item:[],
            total:0,
            text:"",

        })

        const handleData=(pageNum)=>{
            axios.get(`/api/admin/event/search?page=${pageNum-1}`).then(({data})=>{
                console.log("handleData",data);
                state.item = data;
                state.total = data.totalElements; 
            }).catch(()=>{
                alert('에러가 발생했습니다.');
            });
        }

        const handleSearch=()=>{
            state.text=state.text.trim();
            router.push({path:'/admin/event/search', query:{searchTerm:state.text}});
        }

        const handleRegister=()=>{
            router.push({path:'/admin/event/register'});
        }

        const handleContent=(tmp)=>{
            router.push({path:'/admin/event/detail', query:{no:tmp}})
        }

        handleData();


        return {
            state,
            handleSearch,
            handleRegister,
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
        margin-bottom:70px;
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