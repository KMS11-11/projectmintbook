<template>
    <div id="total_wrap">
        <HeaderPage style="position:fixed;z-index: 300;"></HeaderPage>
        <div id="center_wrap">
            <div class="headerpage"></div>
            <header>
                <div id="bottom_nav_wrap">
                    <ul>
                        <li>
                            <label>•</label>
                            <a href="/bestseller"> 베스트셀러</a>
                        </li>
                        <li>
                            <label>•</label>
                            <a href="/new"> 이 주의 신간</a>
                        </li>
                        <li>
                            <label>•</label>
                            <a href="/editorpick"> 에디터's PICK</a>
                        </li>
                        <li>
                            <label>•</label>
                            <a href="/event"> 이벤트</a>
                        </li>
                    </ul>
                </div>
                <div id="slider_wrap">
                    <el-carousel height="407px">
                        <el-carousel-item>
                            <router-link to="/event/detail?no=1"><img src="../assets/MainPage/slide1.png" alt="slide1"/></router-link>
                        </el-carousel-item>
                        <el-carousel-item>
                            <router-link to="/event/detail?no=2"><img src="../assets/MainPage/slide2.png" alt="slide2"/></router-link>
                        </el-carousel-item>
                        <el-carousel-item>
                            <router-link to="/event/detail?no=3"><img src="../assets/MainPage/slide3.png" alt="slide3"/></router-link>
                        </el-carousel-item>
                    </el-carousel>
                </div>
                <div id="notice_wrap">
                    <div>공지사항</div>
                    <div @click="handleNoticeContent(state.notice.content[0].id)" id="noticetop">{{ state.notice.content[0].title }}</div>
                </div>
            </header>
            <section>
                <div class="content_wrap">
                    <div class="content_top">
                        <ul class="content_left">
                            <li>
                                <img src="../assets/MainPage/bestseller.png" alt="bestseller">
                            </li>
                            <li class="content_text">베스트셀러</li>
                        </ul>
                        <div class="content_right">
                            <label><router-link to="/bestseller">더보기+</router-link></label>
                        </div>
                    </div>
                    <div class="content_bottom">
                        <div class="content_book_wrap" v-for="(tmp, idx) in state.bestseller.content" :key="idx">
                            <p class="c_ranking">{{ idx+1 }}.</p>
                            <img :src="tmp.img" alt="book1" class="c_image" @click="handleContent(tmp.id, tmp.genre)">
                            <p class="c_title">{{ tmp.bookName }}</p>
                            <p class="c_author">{{tmp.author}}·{{ tmp.publisher }}</p>
                        </div>
                    </div>
                </div>
                <div class="ad_wrap">
                    <router-link to="/event/detail?no=1"><img src="../assets/MainPage/ad1.jpg" alt="ad1" class="ad"/></router-link>
                </div>
                <div class="content_wrap">
                    <div class="content_top">
                        <ul class="content_left">
                            <li>
                                <img src="../assets/MainPage/new.png" alt="new" id="new_img">
                            </li>
                            <li class="content_text">이 주의 신간</li>
                        </ul>
                        <div class="content_right">
                            <label><router-link to="/new">더보기+</router-link></label>
                        </div>
                    </div>
                    <div class="content_bottom">
                        <div class="content_book_wrap" v-for="(tmp, idx) in state.new.content" :key="idx">
                            <img :src="tmp.img" alt="book1" class="c_image" @click="handleContent(tmp.id, tmp.genre)">
                            <p class="c_title" @click="handleContent(tmp.id, tmp.genre)">{{ tmp.bookName }}</p>
                            <p class="c_author">{{tmp.author}}·{{ tmp.publisher }}</p>
                        </div>
                    </div>
                </div>
                <div class="ad_wrap">
                    <router-link to="/event/detail?no=5"><img src="../assets/MainPage/ad2.jpg" alt="ad2" class="ad"/></router-link>
                </div>
                <div class="content_wrap">
                    <div class="content_top">
                        <ul class="content_left">
                            <li>
                                <img src="../assets/MainPage/editorspick.png" alt="new" id="new_img">
                            </li>
                            <li class="content_text">에디터's PICK</li>
                        </ul>
                        <div class="content_right">
                            <label><router-link to="/editorpick">더보기+</router-link></label>
                        </div>
                    </div>
                    <div class="content_bottom">
                        <div class="content_book_wrap" v-for="(tmp, idx) in state.editor.content" :key="idx">
                            <img :src="tmp.bookid.img" alt="book1" class="c_image" @click="handleContent(tmp.id, tmp.genre)">
                            <p class="c_title" @click="handleContent(tmp.id, tmp.genre)">{{ tmp.bookid.bookName }}</p>
                            <p class="c_author">{{ tmp.bookid.author }}·{{ tmp.bookid.publisher }}</p>
                        </div>
                    </div>
                </div>
                <div>
                    <el-backtop :right="70" :bottom="70" style="color: #3DDCA3;"/>
                </div>
            </section>
        </div>
        <FooterPage></FooterPage>
    </div>
</template>

<script>
import HeaderPage from '@/components/HeaderPage.vue'
import FooterPage from '@/components/FooterPage.vue'
import { reactive } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

export default {

    components:{
    HeaderPage:HeaderPage,
    FooterPage:FooterPage
    },

    setup () {

        const router = useRouter();

        const state = reactive({
            bestseller:[],
            new:[],
            editor:[],
            notice:[]
        })

        const bestseller=()=>{
            axios.get('/api/get/bestseller').then(({data})=>{
                console.log("best",data);
                state.bestseller=data;
            }).catch(()=>{
                alert('에러가 발생했습니다.');
            });
        }

        const mainNew=()=>{
            axios.get('/api/get/main/new').then(({data})=>{
                console.log("new",data);
                state.new=data;
            }).catch(()=>{
                alert('에러가 발생했습니다.');
            });
        }

        const mainEditor=()=>{
            axios.get('/api/get/main/editor').then(({data})=>{
                console.log("editor",data);
                state.editor=data;
            }).catch(()=>{
                alert('에러가 발생했습니다.');
            });
        }

        const handleContent=(tmp1, tmp2)=>{
            router.push({path:'/book', query:{no:tmp1, genre:tmp2}})
        }

        const handleNotice=()=>{
            axios.get('/api/get/main/notice').then(({data})=>{
                console.log("handleNotice",data);
                state.notice = data;
            }).catch(()=>{
                alert('에러가 발생했습니다.');
            });
        }

        const handleNoticeContent=(tmp)=>{
            router.push({path:'/notice/detail', query:{no:tmp}});
        }
        
        bestseller();
        mainNew();
        mainEditor();
        handleNotice();

        return {
            state,
            handleContent,
            handleNoticeContent
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
        color: black;
        font-weight: bold;
    }
    
/*HeaderPage용 공백 */
.headerpage{
    height: 95px;
}
/* header영역 */

#center_wrap{
    width: 1200px;
    margin: 0 auto;
}

#bottom_nav_wrap{
    height: 93px;
    border-bottom: 0.5px solid #e0dddd;
}

#bottom_nav_wrap > ul {
    padding-top: 50px;
}

#bottom_nav_wrap > ul > li {
    float: left;
    margin-right: 45px;
}

#bottom_nav_wrap > ul > li > a{
    font-size: 15px;
    font-weight: bold;
    color: rgb(75, 75, 75);
}


#bottom_nav_wrap > ul > li > label{
    color: #3DDCA3;
}

/* 슬라이드 영역 */
#slider_wrap{
    padding-top: 10px;
}

/* 공지사항 영역 */
#notice_wrap{
    height: 47px;
    display: grid;
    grid-template-columns: 150px auto;
    line-height: 47px;
    color: grey;
    font-size: 13px;
    margin-top: 20px;
    border-bottom: 0.5px solid #e0dddd;
}
#notice_wrap > div:first-child{
    text-align: center;
}

#noticetop{
    cursor: pointer;
}

#noticetop:hover{
    text-decoration: underline;
}

/* 베스트셀러 영역 */
.content_wrap{
    /* height: 970px; */
    margin-top: 50px;
}

.content_top{
    height: 34px;
    line-height: 34px;
}

.content_left > li {
    float:left;
    margin-right: 10px;
}

.content_text{
    font-size: 24px;
    font-weight: bold;
}

.content_left > li > img{
    height: 34px;
}

.content_right > label{
    float:right;
    font-size: 16px;
    font-weight: light;
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

/* 광고 영역 */
.ad_wrap{
    width:1200;
    height:200px;
    overflow: hidden;
    margin-top: 80px;
    margin-bottom: 80px;
    border-radius: 25px;
}
.ad{
    width:1200;
    vertical-align: bottom;
    border-radius: 25px;
}




</style>