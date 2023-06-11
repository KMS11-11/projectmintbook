<template>
    <div>
        <div style="display:grid; grid-template-columns: 1fr 4fr;">
            <div>
                <Categorycomp @myevent="handleList" style="margin-left: 100px;"></Categorycomp>
            </div>
            <div>
                <h1>카테고리별 리스트 페이지</h1>
                <!-- <ul v-for="(item, no) in state.category" :key="no">
                    <li @click="handleList(item.idx)" :class="{ bold : state.isActive === true}">{{ item.name }}</li>
                </ul> -->
                <div v-for="(item, no) in state.category" :key="no">
                    <input type="radio" :id="item.name" :value="item.idx" v-model="state.picked" @change="handleList(item.idx)">
                    <label :for="item.name">{{ item.name }}</label>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import router from '@/router';
import Categorycomp from './Categorycomp.vue';
import { reactive } from 'vue';

export default {
    components : {
        Categorycomp
    },

    setup () {

        const state = reactive({
            category:[
                {"idx":"1", "name":"소설"},
                {"idx":"2", "name":"에세이"},
            ],
            isActive:false,
            picked:""
        })

        const handleList = (e) =>{
            alert(e);
            // state.isActive=true
            // router.push(`/cs/${e}`)
        }

        return {
           handleList,
           state
        }
    }
}
</script>

<style lang="css" scoped>
    .bold{
        font-weight: bold;
    }

    [type="radio"]:checked+label{
        font-weight: bold;
    }
</style>