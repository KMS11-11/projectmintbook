<template>
    <div id="total_wrap">
        <div id="divide_wrap">
            <div id="nav_wrap">
                <admin-menu-page></admin-menu-page>
            </div>
            <div id="content_wrap">
                <p id="maintitle">이벤트 수정</p>
                <table class="table">
                    <tbody>
                        <tr>
                            <th scope="row">이벤트 번호</th>
                            <td>{{ state.item.id }}</td>
                            <th scope="row">진행여부</th>
                            <td>진행중</td>
                        </tr>
                        <tr>
                            <th scope="row">시작날짜</th>
                            <td>
                                <input type="text" v-model="state.item.startDate">
                            </td>
                            <th scope="row">만료날짜</th>
                            <td>
                                <input type="text" v-model="state.item.endDate">
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">제목</th>
                            <td colspan="3">
                                <input type="text" v-model="state.item.title">
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">썸네일 이미지</th>
                            <td colspan="3">
                                <!-- <input type="file"> -->
                                <input type="text" v-model="state.item.listimgPath">
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">본문 이미지</th>
                            <td colspan="3">
                                <!-- <input type="file"> -->
                                <input type="text" v-model="state.item.imgPath">
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div id="register_wrap">
                    <button @click="handleUpdate(state.item.id)">확인</button>
                    <button @click="handleCancel(state.item.id)">취소</button>
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
            item:{
                id:null,
                startDate:null,
                endDate:null,
                title:"",
                listimgPath:"",
                imgPath:""
            },
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
            axios.put(`/api/event/edit?id=${state.no}`,state.item).then((res)=>{
                console.log(res);
                window.alert('이벤트 정보가 수정됐습니다.');
                router.push({path:'/admin/event/detail', query:{no:tmp}})
            }).catch(()=>{
                alert('에러가 발생했습니다.');
            });
        }

        const handleCancel=(tmp)=>{
            router.push({path:'/admin/event/detail', query:{no:tmp}})
        }

        handleData();

        return {state, handleUpdate, handleCancel}
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

    th{
        width: 200px;
        background: rgb(184, 184, 184);
    }

    #register_wrap{
        display: flex;
        justify-content: center;
    }

    input[type="text"]{
        width: 80%;
        border-radius: 5px;
        border: 0.5px solid rgb(184, 184, 184);
        padding-left: 10px;
    }
    

    
</style>