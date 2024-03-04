<template>
  <div class="container">
    <header class="lh-1 py-3">
      <div class="row flex-nowrap justify-content-between align-items-center">
        <div class="col-4 pt-1">
        </div>
        <div class="col-4 text-center">
        </div>
        <div class="col-4 d-flex justify-content-end align-items-center">
          <router-link to="/login" class="btn btn-sm" v-if="!userNo">로그인</router-link>
          <router-link to="/register/step1" class="btn btn-sm" v-if="!userNo">회원가입</router-link>
          <div v-else>
            <router-link to="/login" class="btn btn-sm" @click="logout" >로그아웃</router-link>
            <router-link to="/mypage" class="btn btn-sm">마이페이지</router-link>
          </div>
        </div>
      </div>
      <div class="siteTitle">
        <div class="text-center">
          <a class="blog-header-logo text-body-emphasis text-decoration-none" href="/"><img src="/img/home/logo.jpg" ></a>
        </div>
      </div>
    </header>
    <div class="nav-scroller py-1 mb-3 ">
      <nav class="nav nav-underline justify-content-between">
        <a class="nav-item nav-link link-body-emphasis" href="#">Animate 소개</a>
        <a class="nav-item nav-link link-body-emphasis" href="/sheltersInfo">보호소 찾기</a>
        <a class="nav-item nav-link link-body-emphasis" href="/animal/miss_care">실종/목격 제보</a>
        <a class="nav-item nav-link link-body-emphasis" href="/adoption">입양</a>
        <a class="nav-item nav-link link-body-emphasis" href="/review">입양후기</a>
        <a class="nav-item nav-link link-body-emphasis" href="/review">공지</a>
      </nav>
    </div>
  </div>

</template>

<script>
import router from "@/scrpits/router";
import store from "@/scrpits/store";
import {onMounted, ref} from "vue";

export default {
  name: 'Header',
  setup(){
    const userNo = ref(store.state.user.userNo);
    const logout =() => {
      store.commit('setUser', 0);
      sessionStorage.removeItem("userNo");
      router.push({path:'/'})
    }
    onMounted(()=>{
      store.watch(()=> store.state.user.userNo,(newValue) =>{
        userNo.value = newValue;
      });
    });
    return {userNo,logout}
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
