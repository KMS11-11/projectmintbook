<template>
    <div id="total_wrap">
        <div id="divide_wrap">
            <div id="nav_wrap">
                <admin-menu-page></admin-menu-page>
            </div>
            <div id="content_wrap">
                <p id="maintitle">이벤트 상세</p>
                <table class="table">
                    <tbody>
                        <tr>
                            <th scope="row">이벤트 번호</th>
                            <td>{{ state.item.id }}</td>
                            <th scope="row">진행여부</th>
                            <td>진행중</td>
                        </tr>
                        <tr>
                            <th scope="row">제목</th>
                            <td colspan="3">{{ state.item.title }}</td>
                        </tr>
                        <tr>
                            <th scope="row">시작날짜</th>
                            <td>{{ state.item.startDate }}</td>
                            <th scope="row">만료날짜</th>
                            <td>{{ state.item.endDate }}</td>
                        </tr>
                        <tr>
                            <th scope="row">썸네일 이미지</th>
                            <td colspan="3">{{ state.item.listimgPath }}</td>
                        </tr>
                        <tr>
                            <th scope="row">본문 이미지</th>
                            <td colspan="3">{{ state.item.imgPath }}</td>
                        </tr>
                    </tbody>
                </table>
                <div id="register_wrap">
                    <button @click="handleUpdate(state.no)">수정</button>
                    <button @click="handleDelete(state.no)">삭제</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { reactive } from 'vue';
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

        const handleData = () => {
            axios.get(`/api/get/event/detail?id=${state.no}`).then(({data})=>{
                console.log("handleData",data);
                state.item=data;
            }).catch(()=>{
                alert('에러가 발생했습니다.');
                router.push({path:'/admin/event'})
            });
        }

        const handleUpdate=(tmp)=>{
            router.push({path:'/admin/event/edit', query:{no:tmp}})
        }

        const handleDelete=(tmp)=>{
            if(confirm('해당 이벤트를 삭제하시겠습니까?'))
            axios.delete(`/api/event/delete?id=${tmp}`).then((res)=>{
                console.log(res);
                alert('이벤트가 삭제됐습니다.');
                router.push({path:'/admin/event'})
            }).catch(()=>{
                alert('에러가 발생했습니다.');
                router.push({path:'/admin/event'})
            });
        }

        handleData();

        return {state, handleUpdate, handleDelete}
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
        margin-bottom:50px;
    }

    button{
        width: 100px;
        border-radius: 5px;
        color: white;
        background: black;
        margin-bottom:30px;
        margin-left: 10px;
    }

    #edit{
        margin-left: 50px;
        float: right;
    }

    /*콘텐츠 하단 영역 */

    .table{
        width: 100%;
        margin-top:30px;
    }

    table > tbody > tr> th{
        width: 150px;
    }

    #register_wrap{
        display: flex;
        justify-content: center;
    }
    

    
</style>