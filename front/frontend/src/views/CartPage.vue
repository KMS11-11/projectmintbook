<template>
    <div class="total_wrap">
        <HeaderPage style="position:fixed;z-index: 300;"></HeaderPage>
        <div class="center_wrap">
            <div class="headerpage"></div>
            <ul class="maintitle_wrap">
                <li><img src="../assets/Navigation/cart.png" alt=""></li>
                <li id="maintitle">장바구니</li>
            </ul>
            {{ state.checked }}
            <table>
                <tr>
                    <th>
                        <input type="checkbox" id="checktop" class="checkbox" v-model="checkAll">
                        <label for="checktop"></label>
                    </th>
                    <th colspan="4" class="list_top_right">
                        <button class="button1">찜</button>
                        <button class="button2" @click="handleOrder(state.checked)">바로구매</button>
                        <button class="button3" @click="handleDelete()">삭제</button>
                    </th>
                </tr>
                <tr id="table_bottom_tr" v-for="(tmp, idx) in state.item.content" :key="idx">
                    <td style="width:20px;">
                        <input type="checkbox" :id=tmp.id v-model="state.checked" :value="tmp.id" class="checkbox">
                        <label :for="tmp.id"></label>
                    </td>
                    <td style="width:150px; text-align: center;">
                        <img :src="tmp.bookid.img" alt="boo1" id="img" @click="handleContent(tmp.bookid.id, tmp.bookid.genre)">
                    </td>
                    <td id="price_wrap">
                        <p id="title" @click="handleContent(tmp.bookid.id, tmp.bookid.genre)">{{ tmp.bookid.bookName }}</p>
                        <p id="author">{{tmp.bookid.author}}·{{tmp.bookid.publisher}}·{{tmp.bookid.pubDate}}</p>
                        <label id="dc">10%</label>
                        <label id="dc_price">{{tmp.bookid.price}}원</label>
                        <label id="price">{{tmp.bookid.price}}원</label>
                        <label id="point">|{{Number(Math.round(tmp.bookid.price*0.05)*1).toLocaleString()}}p</label>
                        <div id="description" @click="handleContent(tmp.bookid.id, tmp.bookid.genre)">{{tmp.bookid.bookInfo}}</div>
                    </td>
                    <td id="quantity">{{ tmp.count }}</td>
                    <td style="width:150px; text-align: right;">
                        <button class="button1" id="fontred">♥</button><br>
                        <button class="button4" @click="handleOrder(tmp.id)">바로구매</button><br>
                        <button class="button3" @click="handleDeleteOne(tmp.id)">삭제</button>
                    </td>
                </tr>

            </table>
            <div class="example-pagination-block">
                <el-pagination layout="prev, pager, next" :total="state.total" @current-change="load"/>
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
import router from '@/router'

export default {
    
    components:{
    HeaderPage:HeaderPage,
    FooterPage:FooterPage
    },

    setup () {
        const state = reactive({
            checked:[],
            item:[],
            memberid:3,
            total:0,
            
        })

        const load=(pageNum)=>{
            axios.get(`/api/get/cartitem/${state.memberid}?page=${pageNum-1}`).then(({data})=>{
                console.log("load",data);
                state.item = data;
                state.total = data.totalElements;
            })
        }

        const handleContent=(tmp1, tmp2)=>{
            router.push({path:'/book', query:{no:tmp1, genre:tmp2}})
        }

        const handleDelete=()=>{
            if(confirm('해당 도서를 장바구니에서 삭제하시겠습니까?'))
            axios.delete(`/api/delete/cartitem/${state.checked}`).then((res)=>{
                console.log("handleDelete",res);
                alert('해당 도서가 장바구니에서 삭제됐습니다.');
                window.location.reload(true);
            }).catch(()=>{
                alert('에러가 발생했습니다.');
                window.location.reload(true);
            });
        }

        const handleDeleteOne=(tmp)=>{
            if(confirm('해당 도서를 장바구니에서 삭제하시겠습니까?'))
            axios.delete(`/api/delete/cartitem/${tmp}`).then((res)=>{
                console.log("handleDelete",res);
                alert('해당 도서가 장바구니에서 삭제됐습니다.');
                window.location.reload(true);
            }).catch(()=>{
                alert('에러가 발생했습니다.');
                window.location.reload(true);
            });
        }

        const handleOrder=(tmp)=>{
            router.push({path:'/order', query:{ids:tmp}})
        }

        load();

        return {
            state,
            handleDelete,
            handleDeleteOne,
            handleOrder,
            load,
            handleContent
        }
    },
    computed:{
        checkAll:{
            get:function(){
                return this.state.item.length === this.state.checked.length;
            },
            set:function(e){
                let tmp_id = [];
                for(let tmp of this.state.item){
                    tmp_id.push(tmp.id);
                }
                console.log("tmp_id",tmp_id);
                
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

    /*페이지 타이틀 영역 */
    .maintitle_wrap{
        margin-top: 40px;
        margin-bottom: 50px;
    }
    .maintitle_wrap > li{
        float: left;
        margin-bottom: 40px;
    }

    .maintitle_wrap > li > img{
        width: 35px;
        margin-right: 10px;
    }

    #maintitle{
        font-size: 24px;
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
        margin-left: 5px;
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
    #fontred{
        color: red;
        font-size: 14px;
    }

    /*테이블 내부 */
    #img{
        width: 90px;
        border: 0.5px solid #C6C4C4;
    }

    #title{
        font-size: 15px;
        font-weight: bold;
        margin-bottom: 3px;
    }

    #title, #img, #description:hover{
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
    }

    #description:hover{
        text-decoration: underline;
    }

    #quantity{
        width: 150px;
        text-align: center;
    }

    /* 페이지네이션 */
    .example-pagination-block{
        width: 320px;
        margin: 0 auto;
        margin-top: 50px;
    }
</style>