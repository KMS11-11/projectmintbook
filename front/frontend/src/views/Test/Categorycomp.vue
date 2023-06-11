<template>
    <div>
        <h4>카테고리 메뉴</h4>
        {{ state}}
        <br>
        <input type="radio" @change="sendCategory()" name="categorylist" id="소설" value="1" v-model="state.pickedcategory">
        <label for="소설">소설</label><br>
        <input type="radio" @change="sendCategory()" name="categorylist" id="에세이" value="2" v-model="state.pickedcategory">
        <label for="소설">에세이</label>
    </div>
</template>

<script>
import { reactive } from 'vue'
export default {

    setup (props, context) {
        
        const state = reactive({
            pickedcategory:""
        })

        const sendCategory=()=>{
            context.emit('myevent', state.pickedcategory);
        }

        return {
            state,
            sendCategory
        }
    }
}
</script>

<style lang="css" scoped>

</style>