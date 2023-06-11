<template>
    <div>
        <input type="number" placeholder="금액입력" v-model="state.price">
        <button @click="PaymentBtn">결제</button>
    </div>
</template>

<script>
import { reactive } from 'vue'
export default {
    setup () {

        const { IMP } = window;

        const state = reactive({
        price:0,
        })

        const PaymentBtn=()=>{

            IMP.init("imp07052880");

            IMP.request_pay({ // param
                pg: "html5_inicis",//PG사
                pay_method: "card",//지불수단
                merchant_uid: 'merchant_'+new Date().getTime(), //가맹점에서 구별할 수 있는 고유한 id
                name: "모방범2", //상품명
                amount: state.price, //가격
                buyer_email: "kaka204@naver.com", //구매자 이름
                buyer_name: "김철수", //구매자 이름
                buyer_tel: "010-1234-1234", //구매자 연락처
                buyer_addr: "서울특별시 영등포구 당산동", //구매자 주소지
                buyer_postcode: "07222" //구매자 우편번호
            }, rsp => { // callback
                console.log(rsp);
                if (rsp.success) {
                alert("결제 성공");
                } else {
                    alert("결제 실패");
                }
            });

        }
        return {
            state,
            PaymentBtn
        }
    }
}
</script>

<style lang="css" scoped>

</style>