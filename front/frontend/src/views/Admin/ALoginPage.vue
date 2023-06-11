<template>
    <div id="total_wrap">
        <div id="login_wrap">
            <p>Admin</p>
            <div>
                <input type="text" placeholder="아이디를 입력하세요.">
            </div>
            <div>
                <input type="password" placeholder="비밀번호를 입력하세요.">
            </div>
            
            <button>로그인</button>
        </div>
    </div>
</template>

<script>
export default {
    setup () {
        

        return {}
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

     /*로그인 영역 */

     #login_wrap{
        text-align: center;
        margin-top: 100px;
        margin-bottom: 100px;
    }

    p{
        font-weight: bolder;
        font-size: 35px;
        margin-bottom: 50px;
    }

    input{
        width: 407px;
        height: 49px;
        border: 0.5px solid #C0C0C0;
        margin-bottom: 10px;
        border-radius: 6px;
        padding-left: 20px;
    }

    button{
        width: 407px;
        height: 49px;
        border: 0.5px solid #C0C0C0;
        background: black;
        color: white;
        font-size: 20px;
        font-weight: bold;
        margin-top: 30px;
        border-radius: 6px;
    }
</style>