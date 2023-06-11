<template>
    <form class="total">
        <input type="checkbox" id="checktop" class="checkbox" v-model="checkAll">
        <label for="checktop"></label>
        <div v-for="(item, idx) in state.list" :key="idx">
            <input type="checkbox" :id=item v-model="state.checked" :value="item" class="checkbox">{{ item }}
            <label :for="item"></label>
        </div>
        {{ state.checked }}
    </form>
</template>

<script>
import { reactive } from 'vue'
export default {
    setup () {
        const state = reactive({
            
            list:["1","2","3"],
            checked:[]

        })

        return {
            state
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
        font-size: 15px;
        background: #3DDCA3;
        color: white;
        width: 20px;
        height: 20px;
        text-align: center;
        position: absolute;
        left: 0;
        top:0;
    }
    

    

    /* input[type="checkbox"] {
            display: none;
    }

    input[type="checkbox"] + label{
        display: inline-block;
        width: 20px;
        height: 20px;
        border:1px solid #3DDCA3;
        border-radius: 4px;
        position: relative;
    }

    input[class="custom"]:checked + label::after{
        content:'✓';
        font-size: 15px;
        background: #3DDCA3;
        color: white;
        width: 20px;
        height: 20px;
        text-align: center;
        position: absolute;
        left: 0;
        top:0;
    }*/


    .total {
        width: 200px;
        margin: 0 auto;
    } 
</style>