<template>
  <div>
    <!-- <HeaderPage style="position:fixed;z-index: 300;"></HeaderPage> -->
    <router-view :key="$route.fullPath"/>
    <!-- <FooterPage></FooterPage> -->
  </div>
</template>

<script>
//import HeaderPage from '@/components/HeaderPage.vue'
//import FooterPage from '@/components/FooterPage.vue'
export default{
  data(){
    
    return{

    }
  },
  components:{
    // HeaderPage:HeaderPage,
    //FooterPage:FooterPage
  }
}
</script>
<style>

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


</style>
