<template>
    <div id="total_wrap">
        <HeaderPage style="position:fixed;z-index: 300;"></HeaderPage>
        <div class="center_wrap">
            <div class="headerpage"></div>
            <ul class="maintitle_wrap">
                <li><img src="../assets/OrderResultPage/orderresult.png" alt=""></li>
                <li id="maintitle">주문완료</li>
            </ul>
            <p class="thanks">구매해주셔서 감사합니다!</p>
            <p class="thanks">배송현황 및 결제정보는 '마이페이지 > 구매내역'에서 확인 가능합니다.</p>
            <div id="orderdetail_wrap">
                <p class="minititle">기본/배송정보</p>
                <table class="ordertable">
                    <tr>
                        <td class="textbold">주문자명</td>
                        <td class="right">{{ state.item.memberName }}</td>
                        <td class="textbold">수령자명</td>
                        <td class="right">{{ state.item.buyer }}</td>
                    </tr>
                    <tr>
                        <td class="textbold">배송지</td>
                        <td colspan="3" class="right">{{ state.item.address }}</td>
                    </tr>
                    <tr>
                        <td class="textbold">전화번호</td>
                        <td class="right" colspan="3">{{ state.item.phone }}</td>
                    </tr>
                    <tr>
                        <td class="textbold">주문번호</td>
                        <td class="right">{{ state.item.orderNum }}</td>
                        <td class="textbold">배송방법</td>
                        <td class="right">택배</td>
                    </tr>
                    <tr>
                        <td class="textbold">주문접수일</td>
                        <td class="right">{{ state.item.orderDate }}</td>
                        <td class="textbold">수령예상일</td>
                        <td class="right">{{ state.item.arrivalDate }}</td>
                    </tr>
                    <tr>
                        <td class="textbold">상태</td>
                        <td colspan="3" class="right">{{ state.item.status }}<label v-if="state.item.payMethod === 'deposit'">(국민은행 10431321-1249112으로 입금해주셔야 결제가 완료됩니다.)</label></td>
                    </tr>
                </table>
            </div>
            <div id="paydetail_wrap">
                <p class="minititle" id="payinfo_title">결제정보</p>
                <table class="ordertable">
                    <tr>
                        <td class="textbold">총 주문 금액</td>
                        <td class="right">{{ state.item.totalprice }}원(상품가격{{ state.item.totalprice }}원 + 배송료0원)</td>
                    </tr>
                    <tr>
                        <td class="textbold">실 결제 금액</td>
                        <td class="right">
                            <label id="totalprice">{{ state.item.totalprice }}</label><label>원(포인트사용: 0원)</label>
                        </td>
                    </tr>
                    <tr>
                        <td class="textbold">결제방법</td>
                        <td class="right">{{ state.item.payMethod }}</td>
                    </tr>
                </table>
            </div>
            <div id="button_wrap">
                <button id="gomyorder">구매내역 확인하기</button>
            </div>
        </div>
        <FooterPage></FooterPage>
    </div>
</template>

<script>
import HeaderPage from '@/components/HeaderPage.vue'
import FooterPage from '@/components/FooterPage.vue'
import { onMounted, reactive } from 'vue'
import axios from 'axios'
import { useRoute } from 'vue-router'

export default {
    
    components:{
    HeaderPage:HeaderPage,
    FooterPage:FooterPage
    },

    setup () {
        
        const route = useRoute();

        const state = reactive({
            item:[],
            no:Number(route.query.no),
            member:3
        })

        const handleData=async()=>{
            await axios.get(`/api/get/order/detail/${state.member}/${state.no}`).then(({data})=>{
                console.log(data);
                state.item = data;
            }).catch(()=>{
                    alert('에러가 발생했습니다.');
            });
        }

        onMounted(()=>{
            handleData();
        })

        return {
            state
        }
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
    margin-top: 40px;
    margin-bottom: 40px;
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
    }

    .thanks{
        font-size: 20px;
    }

    /*주문정보 영역 */

    #orderdetail_wrap{
        margin-top: 50px;
    }
    .ordertable{
        width: 100%;
        border: 1px solid rgb(190, 190, 190);
        border-collapse: collapse;
        padding-left: 30px;
        font-size: 14px;
        margin-top: 20px;
    }
    td{
        border: 1px solid rgb(98, 98, 98);
        vertical-align : middle;
        height: 36px;
    }

    .minititle{
        font-size: 16px;
        font-weight: bold;
    }

    .textbold{
        font-weight: bold;
        color: rgb(98, 98, 98);
        width: 100px;
        text-align: center;
        background: rgb(190, 190, 190);
    }

    .right{
        padding-left: 10px;
    }

    #totalprice{
        color: #3DDCA3;
        font-weight: bold;
    }

    #payinfo_title{
        margin-top: 20px;
    }

    #gomyorder{
        width: 250px;
        height: 40px;
        background: #3DDCA3;
        border: 1px solid #3DDCA3;
        border-radius: 6px;
        color: white;
        margin-left: 475px;
        margin-top: 30px;
        cursor: pointer;
    }
</style>