<template>
  <div class="form-signin w-100 m-auto">
      <h1 class="h3 mb-3 fw-normal">로그인</h1>

      <div class="form-floating">
        <input type="text" class="form-control" id="floatingInput" placeholder="아이디" v-model="state.form.userId">
        <label for="floatingInput">아이디</label>
      </div>
      <div class="form-floating">
        <input type="password" class="form-control" id="floatingPassword" placeholder="비밀번호" v-model="state.form.userPassword">
        <label for="floatingPassword">비밀번호</label>
      </div>

      <div class="form-check text-start my-3">
        <input class="form-check-input" type="checkbox" value="remember-me" id="flexCheckDefault">
        <label class="form-check-label" for="flexCheckDefault">
          Remember me
        </label>
      </div>
      <button class="btn btn-primary w-100 py-2" @click="submit()">로그인</button>
  </div>
</template>

<script>
import {reactive} from "vue";
import axios from "axios";
import store from "@/scrpits/store";
import router from "@/scrpits/router";

export default {
  setup(){
    const state = reactive({
      form: {
        userId:"",
        userPassword:""
      }
    })
    const submit =() => {
      axios.post("/animate/user/login", state.form).then((res)=>{
        store.commit('setUser', res.data);
        console.log(res.data);
        sessionStorage.setItem("userNo", res.data);
        router.push({path:'/'});
        window.alert("로그인  완료")
      }).catch(()=>{
        window.alert("아이디 혹은 비밀번호 정보가 옳바르지 않습니다.")
      });

    }
    return {state, submit}
  }

}

</script>

<style>

.form-signin {
  max-width: 330px;
  padding: 10rem 0;
}

.form-signin .form-floating:focus-within {
  z-index: 2;
}

.form-signin input[type="email"] {
  margin-bottom: -1px;
  border-bottom-right-radius: 0;
  border-bottom-left-radius: 0;
}

.form-signin input[type="password"] {
  margin-bottom: 10px;
  border-top-left-radius: 0;
  border-top-right-radius: 0;
}
.btn-primary{
  background-color: #cfce5e;
  border-color: #cfce5e;
  --bs-btn-hover-bg: #dedd65;
  --bs-btn-hover-border-color: #dedd65;

}
</style>