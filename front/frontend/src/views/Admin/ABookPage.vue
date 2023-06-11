<template>
    <div id="total_wrap">
        <div id="divide_wrap">
            <div id="nav_wrap">
                <admin-menu-page></admin-menu-page>
            </div>
            <div id="content_wrap">
                <p id="maintitle">도서관리</p>
                <!-- <label>
                    <select>
                        <option value="title">도서명</option>
                        <option value="author">저자명</option>
                        <option value="isbn">ISBN</option>
                        <option value="publisher">출판사</option>
                    </select>
                </label> -->
                <label>
                    <input type="text" v-model="state.text" @keyup.enter="handleSearch()">
                </label>
                <button id="search" @click="handleSearch()">검색</button>
                <button id="register"><a href="/admin/book/register">도서등록</a></button>
                <table class="table">
                    <thead>
                        <tr>
                            <th scope="col">No.</th>
                            <th scope="col">ISBN</th>
                            <th scope="col">도서명</th>
                            <th scope="col">저자명</th>
                            <th scope="col">출판사</th>
                            <th scope="col">출판일</th>
                            <th scope="col">가격</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(tmp,idx) in state.item.content" :key="idx">
                            <th scope="row">{{ idx+1 }}</th>
                            <td>{{ tmp.isbn }}</td>
                            <td @click="handleContent(tmp.id)" style="cursor: pointer;">{{ tmp.bookName }}</td>
                            <td>{{ tmp.author }}</td>
                            <td>{{ tmp.publisher }}</td>
                            <td>{{ tmp.pubDate }}</td>
                            <td>{{Number(tmp.price).toLocaleString()}}원</td>
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
import { useRouter } from 'vue-router';

export default {
    components:{
        AdminMenuPage
    },

    setup () {

        const router = useRouter();
                
        const state = reactive({
            item:[],
            total:0,
            text:"",
        })

        const handleData=(pageNum)=>{
            axios.get(`/api/admin/get/search?page=${pageNum-1}`).then(({data})=>{
                console.log(data);
                state.item = data;
                state.total = data.totalElements;
            }).catch(()=>{
                alert('에러가 발생했습니다.');
            });
        }

        const handleSearch=()=>{
            state.text=state.text.trim();
            router.push({path:'/admin/book/search', query:{searchTerm:state.text}});
        }

        const handleContent=(tmp)=>{
            router.push({path:'/admin/book/detail', query:{no:tmp}})
        }

        handleData();

        return {
            state,
            handleSearch,
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

    #register > a {
        color: white;
        font-weight: normal;
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