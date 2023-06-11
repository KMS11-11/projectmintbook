<template>
    <div id="total_wrap">
        <HeaderPage style="position:fixed;z-index: 300;"></HeaderPage>
        <div class="center_wrap">
            <div class="headerpage"></div>
            <div class="top_wrap">
                <div class="top_top_wrap">
                    <div id="top_top_left">
                        <p id="top_title">{{state.item.bookName}}</p>
                        <p id="top_author">{{state.item.author}}(지은이)  |  {{state.item.publisher}}  |  {{state.item.pubDate}}</p>
                    </div>
                    <div id="top_top_right">
                        <img src="../assets/BookDetailPage/share.png" alt="share">
                    </div>
                </div>
                <div class="top_bottom_wrap">
                    <div class="top_bottom_left">
                        <img :src="state.item.bookImg" alt="book">
                    </div>
                    <table class="top_bottom_right">
                        <tr>
                            <td class="right_left">정가</td>
                            <td class="right_right">{{Number(Math.round(state.item.price*0.011)*100).toLocaleString()}}원</td>
                        </tr>
                        <tr class="line">
                            <td class="right_left">판매가</td>
                            <td class="right_right">
                                <label id="price">{{Number(state.item.price).toLocaleString()}}원</label>
                                <label id="discount">(10%)</label>
                            </td>
                        </tr>
                        <tr>
                            <td class="right_left">적립금</td>
                            <td class="right_right">
                                <label>{{Number(Math.round(state.item.price*0.05)*1).toLocaleString()}}p</label>
                                <label id="point"> (5%)</label>
                            </td>
                        </tr>
                        <tr>
                            <td class="right_left">배송료</td>
                            <td class="right_right">15,000원 이상 무료배송</td>
                        </tr>
                        <tr class="line">
                            <td class="right_left">독자평점</td>
                            <td class="right_right">
                                <label id="review">{{ average.toFixed(1) }}</label><label>/5점</label>
                            </td>
                        </tr>
                        <tr class="line"> 
                            <td class="right_left">수량</td>
                            <td class="right_right">
                                <el-input-number v-model="state.cart.count" :min="1" :max="10" size="large" @change="handleChange" />
                            </td>
                        </tr>
                        <tr>
                            <td colspan='2' id="button_top">
                                <button id="alarm">알림신청</button>
                            </td>
                        </tr>
                        memberid: {{ state.memberid }}
                        <tr></tr>
                        {{ state.cart }}
                        <tr>
                            <td colspan='2' id="button_bottom">
                                <button id="like">♥</button>
                                <button id="cart" @click="handleCart()">장바구니</button>
                                <button id="buy" @click="handleOrder(state.item.id, state.cart.count)">바로구매</button>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="content">
                <div class="aboutbook">
                    <p class="content_title">책소개</p>
                    <p class="content_content">{{ state.item.bookInfo }}</p>
                </div>
                <div class="aboutauthor">
                    <p class="content_title">작가정보</p>
                    <p class="content_content">{{state.item.authorInfo}}</p>
                </div>
                <div class="aboutinfo">
                    <p class="content_title">기본정보</p>
                    <ul class="content_content">
                        <li>• ISBN: {{ state.item.isbn }}</li>
                        <li>• 출판일: {{ state.item.publishDate }}</li>
                    </ul>
                </div>
            </div>
            <div class="review_section">
                <div class="review_title">
                    <label class="content_title">독자리뷰</label>
                    <label id="review_cnt"> ({{ state.item.reviews.length }})</label>
                </div>
                <div class="review_btn">
                    <div id="review_btn_left">
                        <select name="" id="" v-model="state.review.star">
                            <option value="5" selected>5점</option>
                            <option value="4">4점</option>
                            <option value="3">3점</option>
                            <option value="2">2점</option>
                            <option value="1">1점</option>
                        </select>
                        <input type="text" id="writer" v-model="state.review.writer" placeholder="리뷰를 남기실 이름을 입력해주세요.">
                    </div>
                    <div id="review_btn_right">
                        <button id="r_register_btn" @click="addReview()">리뷰등록</button>
                    </div>
                </div>
                <textarea name="" id="" rows="10" v-model="state.review.content" placeholder="내용을 10자 이상 입력해 주세요. 주제와 무관한 댓글, 악플, 배송문의 등의 글은 임의 삭제될 수 있습니다."></textarea>
                <div class="reviewlist_top">
                    <div class="reviewlist_top_left">
                        <label class="reviewpoint" id="pointred">{{ average.toFixed(1) }}</label>
                        <label class="pointtotal">/5.0</label>
                        의 평점을 받았어요!
                    </div>
                    <div class="reviewlist_top_right">
                        <select name="" id="">
                            <option value="">최신순</option>
                            <option value="">별점순</option>
                        </select>
                 </div>
                </div>
                <table class="reviewlist">
                    <tr v-for="(tmp, idx) in state.reviewlist.content" :key="idx">
                        <td class="reviewlist_left">
                            <label class="reviewpoint">{{ tmp.star }}.0</label>
                            <label class="pointtotal">/5.0</label>
                        </td>
                        <td class="reviewlist_right">
                            <p class="reviewer">{{ tmp.writer }} | {{tmp.regDate}}</p>
                            <p>{{tmp.content}}</p>
                        </td>
                    </tr>
                </table>
                <div class="example-pagination-block">
                    <el-pagination layout="prev, pager, next" :total="state.total" @current-change="reviewByNew"/>
                 </div>
            </div>
            <div class="recommend_section">
                <p class="content_title" id="recommend_title">이런 책도 추천해요!</p>
                <div class="content_bottom">
                    <div class="content_book_wrap" v-for="(tmp,idx) in state.recommend" :key="idx">
                        <img :src="tmp.img" alt="book1" class="c_image" @click="handleContent(tmp.id, tmp.genre)">
                        <p class="c_title" @click="handleContent(tmp.id, tmp.genre)">{{ tmp.bookName }}</p>
                        <p class="c_author">{{tmp.author}}·{{tmp.publisher}}</p>
                    </div>
                </div>
            </div>
            <div class="cs_section">
                <div class="cs_top">
                    <div class="content_title">반품/교환안내</div>
                    <div class="cs_top_right">
                        <router-link to="/cs/register"><button id="csbutton">1:1문의</button></router-link>
                    </div>
                </div>
                <div class="cs_content">
                    <p>반품/교환방법</p>
                    <p>마이룸 > 주문관리 > 주문/배송내역 > 주문조회 > 반품/교환 신청, [1:1 상담 > 반품/교환/환불] 또는 고객센터 (1544-1900)* 오픈마켓, 해외배송 주문, 기프트 주문시 [1:1 상담>반품/교환/환불] 또는 고객센터 (1544-1900)</p>
                    <p class="blank"></p>
                    <p>반품/교환가능 기간</p>
                    <p>변심반품의 경우 수령 후 7일 이내,상품의 결함 및 계약내용과 다를 경우 문제점 발견 후 30일 이내</p>
                    <p class="blank"></p>
                    <p>반품/교환비용</p>
                    <p>변심 혹은 구매착오로 인한 반품/교환은 반송료 고객 부담</p>
                    <p class="blank"></p>
                    <p>반품/교환 불가 사유</p>
                    <p>1. 소비자의 책임 있는 사유로 상품 등이 손실 또는 훼손된 경우(단지 확인을 위한 포장 훼손은 제외) 2. 소비자의 사용, 포장 개봉에 의해 상품 등의 가치가 현저히 감소한 경우예 화장품, 식품, 가전제품(악세서리 포함) 등 3. 복제가 가능한 상품 등의 포장을 훼손한 경우예 음반/DVD/비디오, 소프트웨어, 만화책, 잡지, 영상 화보집4 소비자의 요청에 따라 개별적으로 주문 제작되는 상품의 경우 ((1)해외주문도서)5 디지털 컨텐츠인 eBook, 오디오북 등을 1회 이상 다운로드를 받았을 경우6. 시간의 경과에 의해 재판매가 곤란한 정도로 가치가 현저히 감소한 경우7. 전자상거래 등에서의 소비자보호에 관한 법률이 정하는 소비자 청약철회 제한 내용에 해당되는 경우</p>
                    <p class="blank"></p>
                    <p>상품 품절</p>
                    <p>공급사(출판사) 재고 사정에 의해 품절/지연될 수 있으며, 품절 시 관련 사항에 대해서는 이메일과 문자로 안내드리겠습니다.</p>
                    <p class="blank"></p>
                    <p>소비자 피해보상 환불 지연에 따른 배상</p>
                    <p>1) 상품의 불량에 의한 교환, A/S, 환불, 품질보증 및 피해보상 등에 관한 사항은 소비자분쟁 해결 기준 (공정거래위원회 고시)에 준하여 처리됨2) 대금 환불 및 환불지연에 따른 배상금 지급 조건, 절차 등은 전자상거래 등에서의 소비자 보호에 관한 법률에 따라 처리함</p>
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
import { reactive, ref } from 'vue'
import axios from 'axios'
import { useRoute, useRouter } from 'vue-router'

export default {

    components:{
    HeaderPage:HeaderPage,
    FooterPage:FooterPage
    },

    setup () {
        
        const route = useRoute();
        const router = useRouter();

        const state = reactive({
            memberid:3,
            item:[],
            no:Number(route.query.no),
            genre:Number(route.query.genre),
            review:{
                memberid:"3",
                writer:"",
                star:5,
                content:"",
            },
            reviewlist:{
                content:"",
                writer:"",
                regDate:"",
                star:0,
            },
            recommend:[],
            total:0,
            cart:{
                bookid:Number(route.query.no),
                count:ref(1),
            }            
        })

        const handleChange = (value) => {
            console.log(value);
        }

        const handleCart=()=>{
            axios.post(`/api/add/cartitem/${state.memberid}`, state.cart).then((res)=>{
                console.log("handleCart",res);
                if(confirm('해당 도서가 장바구니에 담겼습니다. 장바구니로 가시겠습니까?'))
                router.push('/cart');
            }).catch(()=>{
                alert('에러가 발생했습니다.');
            });
        }

        const handleData = () => {
            axios.get(`/api/get/book?id=${state.no}`).then(({data})=>{
                console.log("handleData",data);
                state.item=data;
                state.genre=data.genre;
            }).catch(()=>{
                alert('에러가 발생했습니다.');
            });
        }

        const addReview = () =>{
            axios.post(`/api/add/review?id=${state.no}`,state.review).then((res)=>{
                console.log(res);
                window.alert('리뷰가 등록됐습니다.');
                router.go(0);
            }).catch(()=>{
                alert('에러가 발생했습니다.');
            });
        }

        const reviewByNew = (pageNum) =>{
            axios.get(`/api/get/review?id=${state.no}&page=${pageNum-1}`).then(({data})=>{
                console.log("reviewByNew",data);
                state.reviewlist=data;
                state.total=data.totalElements
            }).catch(()=>{
                alert('에러가 발생했습니다.');
            });
        }

        const handleRecommend = () =>{
            axios.get(`/api/get/bestseller/genre?genre=${state.genre}&size=5`).then(({data})=>{
                console.log("handleRecommend",data);
                state.recommend = data.content;
            })
        }

        const handleContent=(tmp1, tmp2)=>{
            router.push({path:'/book', query:{no:tmp1, genre:tmp2}}); 
        }

        const handleOrder=(tmp1, tmp2)=>{
            router.push({path:'/order', query:{id:tmp1, cnt:tmp2 }})
        }


        handleData();
        reviewByNew();
        handleRecommend();

        return {
            state,
            handleChange,
            addReview,
            reviewByNew,
            handleContent,
            handleCart,
            handleOrder
        }
    },
    computed:{

        average(){
            
            //console.log("reviewtotal", this.state.item.reviews[0].star);
            //console.log(this.state.reviewlist.content[i].star);

            let tmp_star = [];
            for(let tmp of this.state.item.reviews){
                tmp_star.push(tmp.star);
            }
            //onsole.log("starObj",tmp_star);

            if(tmp_star.length == 0){

                return 0;
                
            }else{
                let sum = 0;
            
                for(let i=0;i<tmp_star.length;i++){
                    sum += tmp_star[i];
                }

                return sum/tmp_star.length;
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

    /*상단영역 */
    /*제목&공유 영역 */
    
    .top_wrap{
        margin-top: 50px;
        padding-bottom: 100px;
        border-bottom: 0.5px solid #BDBDBD;
    }
    
    .top_top_wrap{
        display: grid;
        grid-template-columns: 9fr 1fr;
        padding-bottom: 10px;
        border-bottom: 0.5px solid #BDBDBD;
    }

    #top_title{
        font-size: 32px;
        font-weight: bold;
    }

    #top_author{
        font-size: 16px;
        color: #6F6F6F;
        margin-top: 10px;
    }

    #top_top_right {
        text-align: right;
    }

    #top_top_right > img {
        width: 30px;
        margin-top: 20px;
    }

    /*이미지&가격 영역 */
    .top_bottom_wrap{
        display: grid;
        grid-template-columns: 1fr 1fr;
        margin-top: 30px;
    }

    .top_bottom_left > img{
        width: 419px;
        box-shadow: 0 10px 20px rgba(0,0,0,0.19), 0 6px 6px rgba(0,0,0,0.23);
    }

    .top_bottom_right{
        width: 100%;
        border-collapse: collapse;
    }

    .right_left{
        font-size: 20px;
        width: 150px
    }

    .right_right{
        font-size: 20px;
    }

    #price{
        font-size: 28px;
        color: #3DDCA3;
    }
    #discount, #point{
        color: #7C7C7C;
    }
    #review{
        font-weight: bold;
        color: #DF7474;
    }

    /*버튼 */
    #alarm{
        width: 135px;
        height: 44px;
        border: 1px solid #7C7C7C;
        border-radius: 10px;
        background: white;
    }

    #button_top{
        text-align: right;
    }

    #button_bottom > button{
        width: 193px;
        height: 62px;
        border-radius: 10px;
        font-size: 18px;
    }

    #like{
        border: 0.5px solid #b1b1b1;
        background-color: white;
        color: red;
        margin-right: 10.5px;
    }

    #cart{
        border: 0.5px solid #DADADA;
        background: #DADADA;
        margin-right: 10.5px;
    }

    #buy{
        border: 0.5px solid #3DDCA3;
        background: #3DDCA3;
        color: white;
    }

    .line{
        border-bottom: 0.5px solid #BDBDBD;
    }

    /*콘텐츠 영역 */
    .content{
        margin-top: 100px;
        border-bottom: 0.5px solid #BDBDBD;
    }
    .content_title{
        font-size: 32px;
        color: #3DDCA3;
        margin-bottom: 30px;
    }

    .content_content{
        font-size: 14px;
        color: #808080;
    }

    .content > div{
        margin-top: 50px;
        margin-bottom: 100px;
    }

    /*리뷰 영역 */

    .review_section{
        margin-top: 50px;
        padding-bottom: 200px;
    }
    .review_title{
        margin-bottom: 50px;
    }

    #review_cnt{
        color: #808080;
        font-size: 32px;
    }

    .review_btn{
        display: grid;
        grid-template-columns: 1fr 1fr;
        margin-bottom: 20px;
    }

    #writer{
        width: 300px;
        height: 36px;
        border-radius: 10px;
        border: 1px solid black;
        margin-left: 20px;
        padding-left: 10px;
        outline: none;
    }

    button{
        width: 115px;
        height: 36px;
        border-radius: 10px;
        font-size: 12px;
        cursor: pointer;
    }

    select{
        width: 130px;
        height: 36px;
        border-radius: 10px;
        font-size: 12px;
        text-align: center;
        color: #3DDCA3;
        font-weight: bold;
        
    }

    select > option {
        font-size: 12px;
        margin: 20px;
    }

    #r_register_btn{
        border: 1px solid #3DDCA3;
        background: #3DDCA3;
        color: white;
    }
    
    #review_btn_left{
        text-align: left;
    }
    
    #review_btn_right{
        text-align: right;
    }

    textarea{
        border: 1px solid black;
        border-radius: 10px;
        width: 1200px;
        height: 239px;
        resize: none;
        outline: none;
        padding: 20px;
        box-sizing:border-box;
        margin-bottom: 50px;
    }
    /*리뷰리스트영역 */
    .reviewlist_top{
        display: grid;
        grid-template-columns: 1fr 1fr;
        font-size: 24px;
        padding-bottom: 20px;
        border-bottom: 0.5px solid #BDBDBD;
    }

    .reviewpoint{
        font-size: 36px;
        color: #3DDCA3;
        font-weight: bold;
    }

    .pointtotal{
        font-size: 20px;
        color: #878787;
    }

    #pointred{
        color: #DF7474;
    }

    .reviewlist_top_right{
        text-align: right;
    }

    /* 리뷰리스트 테이블영역 */

    .reviewlist{
        width: 100%;
        border-collapse: collapse;
    }

    .reviewlist_left, .reviewlist_right{
        padding-top: 25px;
        padding-bottom: 25px;
        border-bottom: 0.5px solid #BDBDBD;
    }
    .reviewlist_left{
        width: 120px;
        
    }

    .reviewer{
        color: #818181;
        margin-bottom: 10px;
    }

    .example-pagination-block{
        width: 320px;
        margin: 0 auto;
        margin-top: 50px;
    }

    /*추천영역 */

    .recommend_section{
        padding-bottom: 100px;
        border-bottom: 0.5px solid #BDBDBD;

    }

    #recommend_title{
        color: black;
    }

    .content_bottom{
    display: grid;
    grid-template-columns: 1fr 1fr 1fr 1fr 1fr;
    margin-top: 40px;
    }

    .content_book_wrap{
        margin-bottom:20px;
    }

    .c_ranking{
        font-size: 36px;
        font-weight: bolder;
        color: #3DDCA3;
    }

    .c_image{
        width: 210px;
        height: 297px;
        border: 0.5px solid #C6C4C4;
        cursor: pointer;
    }

    .c_image:hover{
        box-shadow: 0 14px 28px rgba(0,0,0,0.25), 0 5px 5px rgba(0,0,0,0.22);
    }

    .c_title{
        font-size: 15px;
        width: 210px;
        margin-top: 10px;
        cursor: pointer;
    }

    .c_author{
        font-size: 13px;
        width: 210px;
        color: #998F8F;
    }

    /*반품교환안내 영역*/

    .cs_top{
        display: grid;
        grid-template-columns: 1fr 1fr;
        margin-top: 100px;
    }

    .cs_top_right{
        text-align: right;
    }

    #csbutton{
        border: 1px solid #3DDCA3;
        background: #3DDCA3;
        color: white;
    }
    
    .cs_content{
        width: 1200px;
        color: #818181;
    }

    .blank{
        margin-bottom: 15px;
    }


    

    

    

    
    
    

</style>