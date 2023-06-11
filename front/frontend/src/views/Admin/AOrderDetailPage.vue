<template>
    <div id="total_wrap">
        <div id="divide_wrap">
            <div id="nav_wrap">
                <admin-menu-page></admin-menu-page>
            </div>
            <div id="content_wrap">
                <p id="maintitle">주문 상세</p>
                <p>주문 상품 정보</p>
                <table class="table">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>상품명</th>
                            <th>수량</th>
                            <th>가격</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(tmp,idx) in state.item.booksWCount" :key="idx">
                            <td>{{ idx+1 }}</td>
                            <td>{{tmp.bookName}}</td>
                            <td>{{ tmp.count }}</td>
                            <td>{{Number(tmp.total).toLocaleString()}}원</td>
                        </tr>
                    </tbody>
                </table>
                <p>기본/배송정보</p>
                <table class="table">
                    <tbody>
                        <tr>
                            <th>주문자명</th>
                            <td>{{ state.item.memberName }}</td>
                            <th>수령자명</th>
                            <td>{{ state.item.buyer }}</td>
                        </tr>
                        <tr>
                            <th>배송지</th>
                            <td colspan="3">{{ state.item.address }}</td>
                        </tr>
                        <tr>
                            <th>전화번호</th>
                            <td colspan="3">{{ state.item.phone }}</td>
                        </tr>
                        <tr>
                            <th>주문번호</th>
                            <td>{{ state.item.orderNum }}</td>
                            <th>배송방법</th>
                            <td>택배</td>
                        </tr>
                        <tr>
                            <th>주문접수일</th>
                            <td>{{ state.item.orderDate }}</td>
                            <th>수령예상일</th>
                            <td>{{ state.item.arrivalDate }}</td>
                        </tr>
                        <tr>
                            <th>상태{{ state.item.status }}</th>
                            <td colspan="3">
                                <select v-model="state.item.status">
                                    <option value="주문완료">주문완료</option>
                                    <option value="환불접수">환불접수</option>
                                    <option value="환불진행중">환불진행중</option>
                                    <option value="환불완료">환불완료</option>
                                    <option value="반품접수">반품접수</option>
                                    <option value="반품진행중">반품진행중</option>
                                    <option value="반품완료">반품완료</option>
                                </select>
                                <button @click="handleUpdate()">상태변경</button>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <p>결제정보</p>
                <table class="table">
                    <tr>
                        <th class="total">총 주문 금액</th>
                        <td>{{Number(state.item.totalprice).toLocaleString()}}원(상품가격 {{Number(state.item.totalprice).toLocaleString()}}원+배송료0원)</td>
                    </tr>
                    <tr>
                        <th class="total">실 결제 금액</th>
                        <td>
                            <label id="price">{{Number(state.item.totalprice).toLocaleString()}}</label><label>원(포인트사용:0원)</label>
                        </td>
                    </tr>
                    <tr class="total">
                        <th>결제방법</th>
                        <td>{{ state.item.payMethod }}</td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</template>

<script>
import { onMounted, reactive } from 'vue';
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
            item:[],
            no:Number(route.query.no),
        })

        const handleData=async()=>{

            await axios.get(`/api/get/order/detail/${state.no}`).then(({data})=>{
                console.log(data);
                state.item=data;
            })
        }

        const handleUpdate=async()=>{
            await axios.put(`/api/admin/order/update/${state.no}`,{
                status:state.item.status
            }).then(()=>{
                alert('수정됐습니다.');
                router.go();
            }).catch((err) => {
                alert("수정을 실패하였습니다.");
                console.log(err);
             });
        }

        onMounted(()=>{
            handleData();
        })

        return {
            state,
            handleUpdate
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
        margin-bottom:30px;
    }

    button{
        width: 100px;
        border-radius: 5px;
        color: white;
        background: black;
        margin-bottom:30px;
    }

    #edit{
        margin-left: 50px;
        float: right;
    }

    /*콘텐츠 하단 영역 */

    .table{
        width: 100%;
        margin-top:20px;
        margin-bottom: 30px;
        border: 1px solid black;
    }
    .total{
        width: 150px;
    }
    
    p{
        font-weight: bold;
        margin-bottom: 20px;
    }

    #price{
        font-weight: bold;
        color: #3DDCA3;
    }

    th,tr{
        padding: 10px;
        border-bottom: 1px solid black;
    }
    
</style>