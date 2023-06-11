<template>
    <div id="total_wrap">
        <div id="divide_wrap">
            <div id="nav_wrap">
                <admin-menu-page></admin-menu-page>
            </div>
            <div id="content_wrap">
                <p id="maintitle">도서 상세</p>
                <button id="edit" @click="handleDelete(state.item.id)">삭제</button>
                <button id="edit" @click="handleEdit(state.item.id)">수정</button>
                <table class="table">
                    <tbody>
                        <tr>
                            <th scope="row">도서명</th>
                            <td colspan="3">{{ state.item.bookName}}</td>
                        </tr>
                        <tr>
                            <th scope="row">저자명</th>
                            <td>{{ state.item.author}}</td>
                            <th scope="row">출판사</th>
                            <td>{{ state.item.publisher}}</td>
                        </tr>
                        <tr>
                            <th scope="row">출판일</th>
                            <td>{{ state.item.pubDate}}</td>
                            <th scope="row">정가</th>
                            <td>{{Number(state.item.price).toLocaleString()}}원</td>
                        </tr>
                        <tr>
                            <th scope="row">ISBN</th>
                            <td>{{ state.item.isbn}}</td>
                            <th scope="row">카테고리</th>
                            <td>{{ state.item.genre}}</td>
                        </tr>
                        <tr>
                            <th scope="row">판매수</th>
                            <td colspan="3">{{ state.item.hit}}</td>
                            <!-- <th scope="row">재고수</th>
                            <td>5</td> -->
                        </tr>
                        <tr>
                            <th scope="row">작가소개</th>
                            <td colspan="3">{{ state.item.authorInfo}}</td>
                        </tr>
                        <tr>
                            <th scope="row">책소개</th>
                            <td colspan="3">{{ state.item.bookInfo}}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</template>

<script>
import { onMounted, reactive } from 'vue';
import AdminMenuPage from '../../components/AdminMenuPage.vue';
import { useRoute, useRouter } from 'vue-router';
import axios from 'axios';

export default {
    components:{
        AdminMenuPage
    },

    setup () {
        
        const route = useRoute();
        const router = useRouter();
        
        const state = reactive({
            item:[],
            no:Number(route.query.no)
        })

        const handleData = async() => {
            await axios.get(`/api/get/book?id=${state.no}`).then(({data})=>{
                console.log("handleData",data);
                state.item=data;
            }).catch(()=>{
                alert('에러가 발생했습니다.');
            });
        }

        const handleEdit=async(tmp)=>{
            await router.push({path:'/admin/book/edit', query:{no:tmp}})
        }

        const handleDelete=async(tmp)=>{
            if(confirm('해당 도서를 삭제하시겠습니까?'))
            await axios.delete(`/api/book/delete?id=${tmp}`).then((res)=>{
                console.log(res);
                alert('해당 도서가 삭제됐습니다.');
                router.push({path:'/admin/book'})
            }).catch(()=>{
                alert('에러가 발생했습니다.');
                router.push({path:'/admin/book'})
            });
        }


        onMounted(()=>{
            handleData();
        })

        return {
            state,
            handleEdit,
            handleDelete
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

    button{
        width: 100px;
        border-radius: 5px;
        color: white;
        background: black;
        margin-bottom:30px;
    }

    #edit{
        margin-left: 10px;
        float: right;
    }

    /*콘텐츠 하단 영역 */

    .table{
        width: 100%;
        margin-top:30px;
    }

    th{
        width:150px;
        background: rgb(184, 184, 184);
    }
    

    
</style>