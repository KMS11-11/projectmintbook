<template>
    <div id="total_wrap">
        <div id="divide_wrap">
            <div id="nav_wrap">
                <admin-menu-page></admin-menu-page>
            </div>
            <div id="content_wrap">
                <p id="maintitle">도서등록</p>
                <table class="table">
                    <tbody>
                        <tr>
                            <th scope="row">도서명</th>
                            <td colspan="3">
                                <input type="text" v-model="state.item.bookName">
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">저자명</th>
                            <td>
                                <input type="text" v-model="state.item.author">
                            </td>
                            <th scope="row">출판사</th>
                            <td>
                                <input type="text" v-model="state.item.publisher">
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">출판일</th>
                            <td>
                                <input type="text" v-model="state.item.pubDate" placeholder="YYYY-MM-DD">
                            </td>
                            <th scope="row">정가</th>
                            <td>
                                <input type="text" v-model="state.item.price">
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">ISBN</th>
                            <td>
                                <input type="text" v-model="state.item.isbn">
                            </td>
                            <th scope="row">카테고리</th>
                            <td>
                                <input type="text" v-model="state.item.genre">
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">이미지 URL</th>
                            <td colspan="3">
                                <input type="text" v-model="state.item.img">
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">작가소개</th>
                            <td colspan="3">
                                <textarea v-model="state.item.authorInfo"></textarea>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">책소개</th>
                            <td colspan="3">
                                <textarea v-model="state.item.bookInfo"></textarea>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div id="register_wrap">
                    <button @click="addBook()">등록</button>
                    <button @click="handleCancel()">취소</button>
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
            item:{
                bookName:"",
                author:"",
                img:"",
                publisher:"",
                pubDate:"",
                price:null,
                isbn:"",
                genre:null,
                authorInfo:"",
                bookInfo:""
            }
        })

        const addBook=()=>{
            axios.post('/api/add/book',state.item).then((res)=>{
                console.log(res);
                window.alert('도서가 등록됐습니다.');
                router.go();
            }).catch(()=>{
                alert('에러가 발생했습니다.');
            });
        }

        return {
            state,
            addBook
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

    /*콘텐츠 하단 영역 */

    .table{
        width: 100%;
        margin-top:30px;
    }

    th{
        background: rgb(184, 184, 184);
    }

    input[type="text"]{
        width: 300px;
        border-radius: 5px;
        border: 0.5px solid rgb(184, 184, 184);
        padding-left: 10px;
    }

    textarea{
        width: 90%;
        height: 200px;
        border-radius: 5px;
        border: 0.5px solid rgb(184, 184, 184);
        padding-left: 10px;
        resize: none;
        outline: none;
        padding: 10px;
        box-sizing:border-box;
    }
    
    #register_wrap{
        display: flex;
        justify-content: center;
    }

    
</style>