<template>
    <div class="total_wrap">
        <HeaderPage style="position:fixed;z-index: 300;"></HeaderPage>
        <div class="center_wrap">
            <div class="headerpage"></div>
            <ul class="search_result_wrap">
                <li id="result">검색결과</li>
                <li class="total">(전체<label class="total" id="num">{{ state.item.totalElements }}</label>건)</li>
            </ul>
            {{ state.checked }}
            <table>
                <tr>
                    <th>
                        <input type="checkbox" id="checktop" class="checkbox" v-model="checkAll">
                        <label for="checktop"></label>
                    </th>
                    <th colspan="3" class="list_top_right">
                        <button class="button1">찜</button>
                        <button class="button1" @click="handleCheckedCart()">장바구니</button>
                        <button class="button2" @click="handleOrder(state.checked)">바로구매</button>
                        <select name="" id="">
                            <option value="">판매순</option>
                            <option value="">최신순</option>
                            <option value="">높은가격순</option>
                            <option value="">낮은가격순</option>
                        </select>
                    </th>
                </tr>

                <tr id="table_bottom_tr" v-for="(tmp, idx) in state.item.content" :key="idx">
                    <td style="width:20px;">
                        <input type="checkbox" :id=tmp.id v-model="state.checked" :value="tmp.id" class="checkbox">
                        <label :for="tmp.id"></label>
                    </td>
                    <td style="width:150px; text-align: center;">
                        <img :src="tmp.img" alt="boo1" id="img"  @click="handleContent(tmp.id, tmp.genre)">
                    </td>
                    <td id="price_wrap">
                        <p id="title" @click="handleContent(tmp.id, tmp.genre)">{{tmp.bookName}}</p>
                        <p id="author">{{tmp.author}}·{{tmp.publisher}}·{{tmp.pubDate}}</p>
                        <label id="dc">10%</label>
                        <label id="dc_price">{{Number(tmp.price).toLocaleString()}}원</label>
                        <label id="price">{{Number(Math.round(tmp.price*0.011)*100).toLocaleString()}}원</label>
                        <label id="point">|{{Number(Math.round(tmp.price*0.05)*1).toLocaleString()}}p</label>
                        <div id="description" @click="handleContent(tmp.id, tmp.genre)">{{tmp.bookInfo}}</div>
                    </td>
                    <td style="width:150px; text-align: right;">
                        <button class="button3" @click="handleOneCart(tmp.id)">장바구니</button><br>
                        <button class="button4" @click="handleOrder(tmp.id)">바로구매</button><br>
                        <button class="button5">♥</button>
                    </td>
                </tr>

            </table>
            <div class="example-pagination-block">
                <el-pagination layout="prev, pager, next" :total="state.total" @current-change="handleSearchData"/>
            </div>
            <div>
                <el-backtop :right="70" :bottom="70" style="color: #3DDCA3;"/>
            </div>
        </div>
        <FooterPage></FooterPage>
    </div>
</template>

<script>
import HeaderPage from '@/components/HeaderPage.vue'
import FooterPage from '@/components/FooterPage.vue'
import { reactive } from 'vue'
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

        const state = reactive({
            checked:[],
            page:Number(route.query.page),
            text:String(route.query.searchTerm),
            item:[],
            total:0,
            memberid:3,
            
        })

        const handleSearchData=(pageNum)=>{
            axios.get(`/api/get/search?searchTerm=${state.text}&page=${pageNum-1}`).then(({data})=>{
                console.log("handleSearchData",data);
                state.item = data;
                state.total = data.totalElements;
            }).catch(()=>{
                alert('에러가 발생했습니다.');
            });
        }

        const handleContent=(tmp1, tmp2)=>{
            router.push({path:'/book', query:{no:tmp1, genre:tmp2}})
        }

        const handleCheckedCart=()=>{
            axios.post(`/api/add/cartitem2/${state.memberid}/${state.checked}`).then((res)=>{
                console.log("handleCartCheck",res);
                if(confirm('해당 도서가 장바구니에 담겼습니다. 장바구니로 가시겠습니까?'))
                router.push('/cart');
            }).catch(()=>{
                alert('에러가 발생했습니다.');
            });
        }

        const handleOneCart=(tmp)=>{
            axios.post(`/api/add/cartitem3/${state.memberid}/${tmp}`).then((res)=>{
                console.log("handleOneCart",res);
                if(confirm('해당 도서가 장바구니에 담겼습니다. 장바구니로 가시겠습니까?'))
                router.push('/cart');
            }).catch(()=>{
                alert('에러가 발생했습니다.');
            });
        }

        const handleOrder=(tmp)=>{
            router.push({path:'/order', query:{bids:tmp}})
        }


        handleSearchData();

        return {
            state,
            handleSearchData,
            handleContent,
            handleCheckedCart,
            handleOneCart,
            handleOrder
        }
    },
    computed:{
        checkAll:{
            get:function(){
                return this.state.item.totalElements === this.state.checked.length;
            },
            set:function(e){
                let tmp_id = [];
                for(let tmp of this.state.item.content){
                    tmp_id.push(tmp.id);//[1,2,3,4,5,6]
                }
                this.state.checked = e ? tmp_id : [];
            }
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

    /*검색결과 텍스트 영역 */
    .search_result_wrap{
        height: 30px;
        margin-top: 40px;
        margin-bottom: 40px;
    }
    .search_result_wrap > li{
        float: left;
    }

    #result{
        font-size: 24px;
        font-weight: bold;
    }

    .total{
        margin-top: 5px;
        font-size: 15px;
        font-weight: bold;
    }

    #num{
        color: #3DDCA3;
    }

    /*테이블 영역*/
    
    /*테이블 레이아웃 */
    table{
        width: 100%;
        border-collapse: collapse;
    }

    tr{
        border-bottom: 1px solid #CCCCCC;
    }

    th, td{
        padding: 10px;
    }

    .list_top_right{
        text-align: right;
    }

    .list_top_right > button{
        margin-right: 5px;
    }

    td > button{
        margin-bottom: 5px;
    }

    #table_bottom_tr > td{
        padding-top: 30px;
        padding-bottom: 30px;
    }

    /*체크박스 */
    .checkbox{
        display:none;
    }

    .checkbox + label{
        display: inline-block;
        width: 20px;
        height: 20px;
        border:1px solid #3DDCA3;
        border-radius: 4px;
        position: relative;
    }

    .checkbox:checked + label::after {
        content:'✓';
        font-size: 12px;
        background: #3DDCA3;
        /* border:1px solid #3DDCA3; */
        color: white;
        border-radius: 4px;
        width: 20px;
        height: 20px;
        text-align: center;
        position: absolute;
        left: -1px;
        top:-1px;
    }
    

    /*드롭다운 */
    select{
        width: 115px;
        height: 36px;
        border-radius: 10px;
        font-size: 12px;
        text-align: center;
    }

    select > option {
        font-size: 12px;
        margin: 20px;
    }
    
    /*버튼 */
    button{
        width: 115px;
        height: 36px;
        border-radius: 10px;
        font-size: 12px;
        cursor: pointer;
    }

    .button1{
        border: 0.5px solid #B1B1B1;
        background: white;
    }

    .button2{
        border: 0.5px solid #3DDCA3;
        background: white;
        color: #3DDCA3;
    }

    .button3{
        border: 0.5px solid #DADADA;
        background: #DADADA;
    }

    .button4{
        border: 0.5px solid #3DDCA3;
        background: #3DDCA3;
        color: white;
    }
    .button5{
        border: 0.5px solid #B1B1B1;
        background: white;
        color: red;
        font-size: 14px;
    }

    /*테이블 내부 */
    #img{
        width: 90px;
        border: 0.5px solid #C6C4C4;
        cursor: pointer;
    }

    #title{
        font-size: 15px;
        font-weight: bold;
        margin-bottom: 3px;
        cursor: pointer;
    }

    #author{
        font-size: 13px;
        color: #998F8F;
        margin-bottom: 5px;
        cursor: pointer;
    }


    #dc{
        font-size: 13px;
        font-weight: bold;
        color: #3DDCA3;
    }

    #dc_price{
        font-size: 13px;
        font-weight: bold;
    }

    #price{
        font-size: 13px;
        text-decoration-line: line-through;
        color: #998F8F;
    }
    #point{
        font-size: 13px;
        color: #998F8F;
    }

    #description{
        font-size: 13px;
        margin-top: 10px;
        max-height: 40px;
        overflow: hidden;
        display: -webkit-box;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 2;
        cursor: pointer;
    }

    #description:hover{
        text-decoration: underline;
    }

    /* 페이지네이션 */
    .example-pagination-block{
        width: 320px;
        margin: 0 auto;
        margin-top: 50px;
    }
    
    
    
    

    
    

    
</style>