<template>
    <div id="total_wrap">
        <HeaderPage style="position:fixed;z-index: 300;"></HeaderPage>
        <div class="center_wrap">
            <div class="headerpage"></div>
            <ul class="maintitle_wrap">
                <li><img src="../assets/OrderPage/order.png" alt=""></li>
                <li id="maintitle">주문/결제</li>
            </ul>
            <div class="orderproduct_section">
                <p class="section_title">주문상품</p>
                <table class="table">
                    <tr v-for="(tmp, idx) in state.item.booksWCount" :key="idx">
                        <td id="orderlist_left">
                            <p>{{tmp.bookName}}</p>
                            <p class="orderlist_author">{{tmp.author}} | {{ tmp.publisher }}</p>
                        </td>
                        <td id="orderlist_center">{{ tmp.count }}</td>
                        <td id="orderlist_right">{{Number(tmp.total).toLocaleString()}}원</td>
                    </tr>
                </table>
            </div>
            <div class="address_section">
                <p class="section_title" >베송지 정보</p>
                <table class="table">
                    <tr>
                        <td class="address_table_left">주문자명</td>
                        <td class="address_table_right">{{ state.item.name }}</td>
                    </tr>
                    <tr>
                        <td class="address_table_left">수령자명</td>
                        <td class="address_table_right">
                            <input type="text" v-model="state.item.buyer">
                        </td>
                    </tr>
                    <tr>
                        <td class="address_table_left">전화번호</td>
                        <td class="address_table_right">{{ state.item.phone }}</td>
                    </tr>
                    <tr>
                        <td class="address_table_left">주소</td>
                        <td class="address_table_right">
                            <input type="text" v-model="state.item.address">
                            <button id="addresssearch_btn">주소검색</button>
                            <br>
                            <input type="text" id="input_address2" v-model="state.item.addDetail">
                        </td>
                    </tr>
                </table>
            </div>
            <div class="dc_section">
                <p class="section_title">포인트</p>
                <table class="table" id="cashtable">
                    <tr>
                        <td class="dc_dt1">포인트</td>
                        <td class="dc_dt2">0원</td>
                        <td class="dc_dt3">
                            <input type="text">
                        </td>
                        <td class="dc_dt4">
                            <button class="dc_btn">포인트적용</button>
                        </td>
                        <td class="dc_dt5">사용가능한포인트: {{ state.item.point }} Point</td>
                    </tr>
                </table>
            </div>
            <div class="payment_section">
                <p class="section_title">결제수단</p>
                <table class="table">
                    <tr>
                        <td id="payment_td">
                            <input type="radio" v-model="state.paymentmethod" value="card" name="method" id="pay1" class="radiobtn">
                            <label for="pay1">신용카드</label>
                            <input type="radio" v-model="state.paymentmethod" value="deposit" name="method" id="pay2" class="radiobtn">
                            <label for="pay2">무통장입금</label>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="totalprice_section">
                <p class="section_title">최종 결제 금액</p>
                <div id="payinfo_wrap">
                    <div class="paybox">
                        <div>
                            <label class="label1">상품 주문 총액</label>
                            <label>{{state.item.totalprice}}원</label>
                        </div>
                        <div>
                            <label class="label1">배송비</label>
                            <label>0원</label>
                        </div>
                    </div>
                    <div class="calc">-</div>
                    <div class="paybox">
                        <div>
                            <label class="label2">포인트 적용</label>
                            <label>0원</label>
                        </div>
                    </div>
                    <div class="calc">=</div>
                    <div id="finalbox">
                        <p class="finaltext">최종 결제 금액</p>
                        <p class="finalprice">{{state.item.totalprice}}원</p>
                        <button id="borderbtn" @click="handleOrder()">주문하기</button>
                    </div>
                </div>
                <p id="agreetext">구매조건을 확인하였으며 결제에 동의합니다.</p>
            </div>
            {{ state.order }}
        </div>
        <FooterPage></FooterPage>
    </div>
</template>

<script>
import HeaderPage from '@/components/HeaderPage.vue'
import FooterPage from '@/components/FooterPage.vue'
import { onMounted, reactive } from 'vue'
import axios from 'axios'
import { useRoute, useRouter } from 'vue-router'

export default {

    components:{
    HeaderPage:HeaderPage,
    FooterPage:FooterPage
    },

    setup () {

        const router = useRouter();
        const route = useRoute();
        const { IMP } = window;

        const state = reactive({
            paymentmethod:"",
            member:3,
            ids:route.query.ids,
            item:[],
            id:Number(route.query.id),
            cnt:Number(route.query.cnt),
            bids:route.query.bids,
            bookids:[]//?

        })

        const handleData=()=>{
            if(window.history.state.back === '/cart'){
                axios.get(`/api/get/cart/wishorder/${state.member}/${state.ids}`).then(({data})=>{
                console.log("handleData(Cart)",data);
                state.item = data;
                }).catch(()=>{
                    alert('에러(cart)가 발생했습니다.');
                });
            }else if(router.options.history.state.back.startsWith('/book')){
                axios.get(`/api/get/detail/wishorder/${state.member}/${state.id}/${state.cnt}`).then(({data})=>{
                console.log("handleData(detail)",data);
                state.item = data;
                }).catch(()=>{
                    alert('에러(detail)가 발생했습니다.');
                });
            }else{
                axios.get(`/api/get/list/wishorder/${state.member}/${state.bids}`).then(({data})=>{
                    console.log("handleData(List)",data);
                    state.item=data;
                }).catch(()=>{
                    alert('에러(list)가 발생했습니다.');
                });
            }
        }

        const handleOrder= async()=>{

            if(state.paymentmethod == "card"){

                IMP.init("**본인이 발급받은 imp***");//본인 imp로 변경

                IMP.request_pay({ // param
                pg: "",//PG사
                pay_method: "card",//지불수단
                name: state.item.booksWCount[0].bookName, //상품명
                amount: state.item.totalprice, //가격
                buyer_email: state.item.email, //구매자 이름
                buyer_name: state.item.buyer, //구매자 이름
                buyer_tel: state.item.phone, //구매자 연락처
                buyer_addr: `${state.item.address} ${state.item.addDetail}`, //구매자 주소지
                buyer_postcode: "07222" //구매자 우편번호
                }, rsp => { // callback
                    console.log(rsp);
                    if (rsp.success) {
                        axios.post(`/api/order/total/${state.member}`,
                        {
                            booksWCount:state.item.booksWCount,
                            buyer:state.item.buyer,
                            buyerAddress:`${state.item.address} ${state.item.addDetail}`,
                            payMethod:state.paymentmethod,
                            totalprice:state.item.totalprice           
                        }).then(()=>{
                            axios.get(`/api/get/order/one/${state.member}`).then(({data})=>{
                                console.log(data);
                                alert('결제가 완료됐습니다.');
                                router.push({path:'/order-result', query:{no:data.content[0].id}})
                            }).catch(()=>{
                                alert('에러가 발생했습니다.');
                                window.location.reload(true);
                            });
                        })
                    } else {
                        alert("결제 실패");
                    }
                });
            }else if(state.paymentmethod == "deposit"){
                axios.post(`/api/order/total/${state.member}`,
                {
                    booksWCount:state.item.booksWCount,
                    buyer:state.item.buyer,
                    buyerAddress:`${state.item.address} ${state.item.addDetail}`,
                    payMethod:state.paymentmethod,
                    totalprice:state.item.totalprice           
                }).then(()=>{
                    axios.get(`/api/get/order/one/${state.member}`).then(({data})=>{
                        console.log(data);
                        alert('무통장 입금을 하셔야 결제가 완료됩니다.');
                        router.push({path:'/order-result', query:{no:data.content[0].id}})
                    }).catch(()=>{
                        alert('에러가 발생했습니다.');
                        window.location.reload(true);
                    });
                })
            }


        }

        onMounted(()=>{
            handleData();
        })

        return {
            state,
            handleOrder
            
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
    margin-bottom: 50px;
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

    /*주문상품 영역*/
    
    .orderproduct_section{
        padding-bottom: 50px;
        border-bottom: 1px solid #CCCCCC;
    }

    .section_title{
        font-size: 16px;
        font-weight: bold;
        margin-bottom: 30px;
    }

    .table{
        width: 100%;
        padding-left:50px;
        padding-right: 50px;
        padding-top: 30px;
        padding-bottom: 30px;
        box-sizing: border-box;
        border: 1px solid #3DDCA3;
        border-radius: 33px;
        font-size: 14px;
        border-spacing: 0px;
        border-collapse: separate;
    }

    #orderlist_left, #orderlist_center, #orderlist_right{
        padding-top: 20px;
        padding-bottom: 20px;
        border-bottom: 1px solid #CCCCCC;
    }
    
    #orderlist_center{
        width: 150px;
    }

    #orderlist_right{
        width: 200px;
    }

    .orderlist_author{
        color: #919191;
    }

    /*배송지정보 영역*/
    .address_section{
        margin-top: 50px;
        padding-bottom: 50px;
        border-bottom: 1px solid #CCCCCC;
    }
    .address_table_left{
        font-weight: bold;
        width: 100px;
        vertical-align : top;
    }

    .address_table_left, .address_table_right{
        padding-bottom: 10px;
        padding-top: 10px;
    }

    input[type=text]{
        height: 30px;
        width: 500px;
        outline-style: none;  /* 포커스시 발생하는 효과 제거를 원한다면 */
        -webkit-appearance: none;  /* 브라우저별 기본 스타일링 제거 */
        -moz-appearance: none;
        appearance: none;
        border: 0.5px solid #C0C0C0;
        border-radius: 6px;
        padding-left: 10px;
        box-sizing: content-box;
        padding-top: 0;
        padding-bottom: 0;
    }

    #input_address1{
        width: 500px;
        margin-right: 10px;
        margin-bottom: 10px;
    }

    #input_address2{
        width: 500px;
    }
    
    #addresssearch_btn{
        width: 130px;
        height: 39px;
        border-radius: 6px;
        font-size: 12px;
        cursor: pointer;
        border: 0.5px solid #7C7C7C;
        color: #7C7C7C;
    }

    /* 캐시/포인트 영역 */
    .dc_section{
        margin-top: 50px;
        padding-bottom: 50px;
        border-bottom: 1px solid #CCCCCC;
    }

    #cashtable{
        vertical-align: middle;
    }
    .dc_dt1{
        font-weight: bold;
        width: 100px;
        border-bottom-width: 0px;
    }

    .dc_dt2{
        font-weight: bold;
        width: 120px;
        text-align: right;
        padding-right: 20px;
        border-bottom-width: 0px;
    }
    .dc_dt3{
        width: 320px;
        border-bottom-width: 0px;
    }
    .dc_dt3 >input{
        width: 300px;
    }

    .dc_dt4{
        text-align: left;
        width: 180px;
        border-bottom-width: 0px;
    }

    .dc_dt5{
        text-align: left;
        border-bottom-width: 0px;
    }

    .dc_btn{
        width: 130px;
        height: 39px;
        border-radius: 6px;
        font-size: 12px;
        cursor: pointer;
        border: 0.5px solid #3DDCA3;
        color: white;
        background:#3DDCA3;
    }
    
    /*결제수단 영역*/
    .payment_section{
        margin-top: 50px;
        padding-bottom: 50px;
        border-bottom: 1px solid #CCCCCC;
    }

    #payment_td{
        padding-top: 10px;
        padding-bottom: 10px;
    }

    /* .paymethod_btn:hover{
        border: 0.5px solid #3DDCA3;
        background: #3DDCA3;
        color: white;
    } */

    input[type=radio]{
        display: none;
    }

    input[type=radio]+label{
        padding:15px 40px;
        border-radius: 6px;
        border: 0.5px solid #C0C0C0;
        margin-right: 20px;
    }

    input[type=radio]:checked +label{
        background: #3DDCA3;
        border: 0.5px solid #3DDCA3;
        color: white;
    }

    /* 최종 결제 금액 영역 */
    .totalprice_section{
        margin-top: 50px;
    }

    #payinfo_wrap{
        display: grid;
        grid-template-columns: 300px auto 300px auto 300px;
    }

    .paybox{
        width: 300px;
        height: 219px;
        border: 1px solid #3DDCA3;
        border-radius: 33px;
        padding: 40px;
        box-sizing: border-box;
        
    }

    .calc{
        height: 219px;
        line-height: 169px;
        font-size: 100px;
        text-align: center;
    }

    .label1{
        margin-top: 30px;
        width: 110px;
        margin-right: 20px;
        font-weight: bold;
        display: inline-block;
    }

    .label2{
        margin-top: 55px;
        width: 110px;
        margin-right: 20px;
        font-weight: bold;
        display: inline-block;
    }

    #finalbox{
        text-align: center;
        width: 300px;
        height: 219px;
        padding-top: 70px;
        border: 1px solid #3DDCA3;
        border-radius: 33px;
        box-sizing: border-box;
    }

    .finaltext{
        font-size: 20px;
        font-weight: bold;
        margin-bottom: 10px;
    }

    .finalprice{
        font-size: 20px;
        font-weight: bold;
        color: #3DDCA3;
        margin-bottom: 10px;
    }

    #borderbtn{
        width: 300px;
        height: 51px;
        margin-top: 23px;
        border-bottom-left-radius: 33px;
        border-bottom-right-radius: 33px;
        font-size: 12px;
        cursor: pointer;
        border: 0.5px solid #3DDCA3;
        background: #3DDCA3;
        color: white;
    }

    #borderbtn:hover{
        font-weight: bolder;
    }

    #agreetext{
        margin-left: 910px;
        margin-top: 20px;
        font-size: 14px;
        color: #939393;
    }
</style>