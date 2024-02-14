<template>
  <div class="container">
    <header class="lh-1 py-3">
      <div class="row flex-nowrap justify-content-between align-items-center">
        <div class="col-4 pt-1">
        </div>
        <div class="col-4 text-center">
        </div>
        <div class="col-4 d-flex justify-content-end align-items-center">
          <router-link to="/login" class="btn btn-sm" v-if="!$store.state.user.userNo">로그인</router-link>
          <a to="/login" class="btn btn-sm" @click="logout()" v-else>로그아웃</a>
          <a class="btn btn-sm" href="#" v-if="!$store.state.user.userNo">회원가입</a>
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
        <a class="nav-item nav-link link-body-emphasis active" href="#">Animate 소개</a>
        <a class="nav-item nav-link link-body-emphasis" href="/sheltersInfo">보호소 찾기</a>
        <a class="nav-item nav-link link-body-emphasis" href="#">실종/제보</a>
        <a class="nav-item nav-link link-body-emphasis" href="#">입양</a>
        <a class="nav-item nav-link link-body-emphasis active" href="#">입양후기</a>
      </nav>
    </div>
  </div>

</template>

<script>
import router from "@/scrpits/router";
import store from "@/scrpits/store";

export default {
  name: 'Header',
  setup(){
    const logout =() => {
      store.commit('setUser', 0);
      sessionStorage.removeItem("userNo");
      router.push({path:'/'})
    }
    return {logout}
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
