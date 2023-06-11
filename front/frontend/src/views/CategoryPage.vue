<template>
    <div class="total_wrap">
        <HeaderPage style="position:fixed;z-index: 300;"></HeaderPage>
        <div class="center_wrap">
            <div class="headerpage"></div>
            <div id="divide_section">
                <div id="category">
                    <p id="category_title">카테고리</p>
                    <ul v-for="tmp of state.categorylist" :key="tmp">
                        <li id="category_list" style="cursor: pointer;" @click="handleCategory(tmp.categoryid)">{{ tmp.categoryname }}</li>
                    </ul>
                </div>
                <div id="booklist">
                    <ul class="maintitle_wrap">
                        <li><img src="../assets/CategoryPage/category.png" alt=""></li>
                        <li id="maintitle">소설</li>
                    </ul>
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
                                <img :src="tmp.img" alt="boo1" id="img" @click="handleContent(tmp.id, tmp.genre)">
                            </td>
                            <td id="price_wrap">
                                <p id="title" @click="handleContent(tmp.id, tmp.genre)">{{ tmp.bookName }}</p>
                                <p id="author">{{tmp.author}}·{{tmp.publisher}}·{{ tmp.pubDate }}</p>
                                <label id="dc">10%</label>
                                <label id="dc_price">{{Number(tmp.price).toLocaleString()}}원</label>
                                <label id="price">{{Number(Math.round(tmp.price*0.011)*100).toLocaleString()}}원</label>
                                <label id="point"> | {{Number(Math.round(tmp.price*0.05)*1).toLocaleString()}}p</label>
                                <div id="description" @click="handleContent(tmp.id, tmp.genre)">{{ tmp.bookInfo }}</div>
                            </td>
                            <td style="width:150px; text-align: right;">
                                <button class="button3" @click="handleOneCart(tmp.id)">장바구니</button><br>
                                <button class="button4" @click="handleOrder(tmp.id)">바로구매</button><br>
                                <button class="button5">♥</button>
                            </td>
                        </tr>
                    </table>
                    <div class="example-pagination-block">
                        <el-pagination layout="prev, pager, next" :total="state.total" @current-change="handleData"/>
                    </div>
                </div>
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
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios'

export default {
    
    components:{
    HeaderPage:HeaderPage,
    FooterPage:FooterPage
    },

    setup () {

        const route = useRoute();
        const router = useRouter();

        const state = reactive({
            list:["1","2","3","4","5","6","7","8","9","10"],
            categorylist:[{categoryname:"소설", categoryid:1},
                          {categoryname:"시/에세이", categoryid:2},
                          {categoryname:"인문", categoryid:3},
                          {categoryname:"가정/육아", categoryid:4},
                          {categoryname:"요리", categoryid:5},
                          {categoryname:"건강", categoryid:6},
                          {categoryname:"취미/실용/스포츠", categoryid:7},
                          {categoryname:"경제/경영", categoryid:8},
                          {categoryname:"자기계발", categoryid:9},
                          {categoryname:"정치/사회", categoryid:10},
                          {categoryname:"역사/문화", categoryid:11},
                          {categoryname:"종교", categoryid:12},
                          {categoryname:"예술/대중문화", categoryid:13},
                          {categoryname:"중/고등참고서", categoryid:14},
                          {categoryname:"기술/공학", categoryid:15},
                          {categoryname:"외국어", categoryid:16},
                          {categoryname:"과학", categoryid:17},
                          {categoryname:"취업/수험서", categoryid:18},
                          {categoryname:"여행", categoryid:19},
                          {categoryname:"컴퓨터/IT", categoryid:20},
                          {categoryname:"잡지", categoryid:21},
                          {categoryname:"청소년", categoryid:22}],
            checked:[],
            genre:Number(route.query.genre),
            total:0,
            item:[],
            memberid:3
            
        })

        const handleData=(pageNum)=>{
            axios.get(`/api/get/bestseller/genre?genre=${state.genre}&page=${pageNum-1}`).then(({data})=>{
                console.log("handleData",data);
                state.item=data;
                state.total=data.totalElements;     
            }).catch(()=>{
                alert('에러가 발생했습니다.');
            })
        }

        const handleCategory=(e,pageNum)=>{
            axios.get(`/api/get/bestseller/genre?genre=${e}&page=${pageNum-1}`).then(({data})=>{
                console.log("categoryBest",data);
                state.item=data;
                state.total=data.totalElements;
                router.push({path:'/category', query:{genre:e}});
            }).catch(()=>{
                alert('에러가 발생했습니다.');
            })
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


        handleData();

        return {
            state,
            handleCategory,
            handleContent,
            handleData,
            handleCheckedCart,
            handleOneCart,
            handleOrder
        }
    },
    computed:{
        checkAll:{
            get:function(){
                return this.state.list.length === this.state.checked.length;
            },
            set:function(e){
                this.state.checked = e ? this.state.list : [];
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

    a:hover{
        text-decoration: none;
        color: black;
        font-weight: bold;
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

    /*사이드 카테고리 영역*/
    #divide_section{
        display: grid;
        grid-template-columns: 247px auto;
    }

    #category{
        padding-top: 50px;
    }

    #category_title{
        font-size: 18px;
        font-weight: bold;
        margin-bottom: 25px;
    }

    #category_list{
        font-size: 15px;
        margin-bottom: 10px;
    }

    #category_list:hover{
        font-weight: bold;
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